package com.ule.uhj.app.yzs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ule.uhj.app.yzs.exception.YZSException;
import com.ule.uhj.app.yzs.service.IIdentifyService;
import com.ule.uhj.app.zgd.dao.CreditPostmemberMapper;
import com.ule.uhj.app.zgd.model.CreditPostmember;
import com.ule.uhj.app.zgd.model.CreditPostmemberExample;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.ycZgd.BangZGClient;
import com.ule.uhj.util.http.HttpClientUtil;

import net.sf.json.JSONObject;

@Service("identifyService")
public class IdentifyServiceImpl implements IIdentifyService {

	private static Log log = LogFactory.getLog(IdentifyServiceImpl.class);
	
	@Autowired
	private CreditPostmemberMapper creditPostmemberMapper;
	
	/**
	 * vps请求地址[更改掌柜信息]
	 */
	//private String requestUrl = "https://vps.ule.com/vpsUzsMobile/yzs/chinapost/updateVpsByData.do";
	private String requestUrl = "https://vps.beta.ule.com/vpsUzsMobile/yzs/chinapost/updateVpsByData.do";
	
	@Override
	public int checkUserIdentify(String bzgId) throws Exception {
		
		if(StringUtils.isEmpty(bzgId)) {
			throw new YZSException("编号为空！");
		}
			
		// 查询 T_J_CREDIT_POSTMEMBER 中是否有该地推人员数据
		CreditPostmemberExample example = new CreditPostmemberExample();
		example.createCriteria().andStaffIdEqualTo(bzgId);
		List<CreditPostmember> list = creditPostmemberMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)) {
			return 1;
		}
			
		return 0;
	}

	@Override
	public Map<String, Object> queryUserInfoByBzgId(String bzgId) throws Exception {
		
		if(StringUtils.isEmpty(bzgId)) {
			throw new YZSException("编号为空！");
		}
		// 查询地推人员信息
		Map<String, Object> bzgInfoMap = WildflyBeanFactory.getBangZGClient().queryBzgInfoByBzgId(bzgId);
		// 查询所属机构
		Map<String, Object> orgInfoMap = WildflyBeanFactory.getBangZGClient().queryBzgOrgInfoByBzgId(bzgId);
		
		bzgInfoMap.putAll(orgInfoMap);
		log.info("queryUserInfoByBzgId  bzgInfo is: "+bzgInfoMap);
		
		return bzgInfoMap;
	}

	@Override
	public void saveCreditPostMember(CreditPostmember member) throws Exception {
		// 先查询该地推人员信息是否存在，存在则更新，不存在则保存
		if(StringUtils.isEmpty(member.getName()) || StringUtils.isEmpty(member.getMobile())) {
			throw new YZSException("姓名或电话号码为空，无法更改信息！");
		}
		CreditPostmemberExample example = new CreditPostmemberExample();
		example.createCriteria().andStaffIdEqualTo(member.getStaffId());
		List<CreditPostmember> list = creditPostmemberMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)) {
			// 更新
			for(CreditPostmember memberNew : list) {
				memberNew.setCertNo(member.getCertNo());
				memberNew.setMobile(member.getMobile());
				memberNew.setName(member.getName());
				memberNew.setOrgProvince(member.getOrgProvince());
				memberNew.setOrgCity(member.getOrgCity());
				memberNew.setOrgArea(member.getOrgArea());
				memberNew.setOrgTown(member.getOrgTown());
				creditPostmemberMapper.updateByPrimaryKeySelective(memberNew);
			}
		}
		else {
			// 插入
			creditPostmemberMapper.insertSelective(member);
		}
	}

	@Override
	public void updateZgInfo(Map<String, Object> param) throws Exception {
		log.info("updateZgInfo 参数信息是："+param);
		if(!CollectionUtils.isEmpty(param)) {
			if(null == param.get("usrOnlyid") || null == param.get("orgCode")) {
				throw new YZSException("传入参数不全，无法更改信息！");
			}
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("data", JSONObject.fromObject(param).toString());
			paramMap.put("jsoncallback", "jsoncallback");
			String result = HttpClientUtil.sendPost(requestUrl, paramMap);
			log.info("updateZgInfo 接口请求结果是："+result);
			if(!result.contains("0000")) {
				throw new YZSException("更改掌柜信息失败！");
			}
		}
	}

	
}
