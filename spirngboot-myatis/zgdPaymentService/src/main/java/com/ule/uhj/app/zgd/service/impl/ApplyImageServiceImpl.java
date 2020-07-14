package com.ule.uhj.app.zgd.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.zgd.dao.ApplyImageMapper;
import com.ule.uhj.app.zgd.model.ApplyImage;
import com.ule.uhj.app.zgd.model.ApplyImageExample;
import com.ule.uhj.app.zgd.service.ApplyImageService;
import com.ule.uhj.app.zgd.util.DateUtil;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;

@Service
public class ApplyImageServiceImpl implements ApplyImageService {
	
	private static Logger log = LoggerFactory.getLogger(ApplyImageServiceImpl.class);
	@Autowired
	private ApplyImageMapper applyImageMapper;
	
	

	@Override
	public String saveApplyImage(Map<String, Object> map) throws Exception {
		String type=Convert.toStr(map.get("type"));
		String url=Convert.toStr(map.get("url"));//
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		if(Check.isBlank(type) || Check.isBlank(url) || Check.isBlank(userOnlyId)){
			log.info("saveApplyImage type:"+type+";url:"+url+";userOnlyId:"+userOnlyId);
			return null;
		}
		Short status=10;
		Short isMobileUpload=1;
		if(UhjConstant.imageType.confirm_loan_page.equals(type)){
			ApplyImage cc =new ApplyImage();
			cc.setCreateTime(DateUtil.currTimeStr());
			cc.setImageType(type);
			cc.setImageUrl(url);
			cc.setIsMobileUpload(isMobileUpload);
			cc.setStatus(status);
			cc.setUserOnlyId(userOnlyId);
			cc.setUpdateTime(cc.getCreateTime());
			applyImageMapper.insertSelective(cc);
		}else{
			ApplyImageExample ccExample =new ApplyImageExample();
			ccExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andImageTypeEqualTo(type);
			List<ApplyImage> cclist=applyImageMapper.selectByExample(ccExample);
			if(cclist==null || cclist.size()<=0){
				ApplyImage cc =new ApplyImage();
				cc.setCreateTime(DateUtil.currTimeStr());
				cc.setImageType(type);
				cc.setImageUrl(url);
				cc.setIsMobileUpload(isMobileUpload);
				cc.setStatus(status);
				cc.setUserOnlyId(userOnlyId);
				cc.setUpdateTime(cc.getCreateTime());
				applyImageMapper.insertSelective(cc);
			}else{
				ApplyImage cc =cclist.get(0);
				cc.setImageType(type);
				cc.setImageUrl(url);
				cc.setIsMobileUpload(isMobileUpload);
				cc.setStatus(status);
				cc.setUserOnlyId(userOnlyId);
				cc.setUpdateTime(DateUtil.currTimeStr());
				applyImageMapper.updateByExampleSelective(cc, ccExample);
			}
		}
		return JsonResult.getInstance().addOk().toString();
	}
	
	
	@Override
	public String queryApplyImageService(Map<String, Object> map)
			throws Exception {
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String imageType=Convert.toStr(map.get("imageType"));
		ApplyImageExample ccExample =new ApplyImageExample();
		ccExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andImageTypeEqualTo(imageType);
		List<ApplyImage> cclist=applyImageMapper.selectByExample(ccExample);
		if(cclist!=null && cclist.size()>0){
			String imageUrl=cclist.get(0).getImageUrl();
			log.info("queryApplyImageService imageUrl "+imageUrl);
			return imageUrl;
		}
		return null;
	}


	@Override
	public String saveAppPdf(Map<String, Object> map) throws Exception {
		log.info("saveAppPdf map:"+map.toString());
		String flag = Convert.toStr(map.get("flag"));
		if(!Check.isBlank(flag)&&"4".equals(flag)){
			String type=Convert.toStr(map.get("type"));
			String url=Convert.toStr(map.get("url"));//
			String userOnlyId=Convert.toStr(map.get("userOnlyId"));
			if(Check.isBlank(type) || Check.isBlank(url) || Check.isBlank(userOnlyId)){
				log.info("saveApplyImage type:"+type+";url:"+url+";userOnlyId:"+userOnlyId);
				return null;
			}
			Short status=10;
			Short isMobileUpload=1;
			ApplyImage cc =new ApplyImage();
			cc.setCreateTime(DateUtil.currTimeStr());
			cc.setImageType(type);
			cc.setImageUrl(url);
			cc.setIsMobileUpload(isMobileUpload);
			cc.setStatus(status);
			cc.setUserOnlyId(userOnlyId);
			cc.setUpdateTime(cc.getCreateTime());
			applyImageMapper.insertSelective(cc);
		}else{
			saveApplyImage(map);
		}
		return JsonResult.getInstance().addOk().toString();
	}

}
