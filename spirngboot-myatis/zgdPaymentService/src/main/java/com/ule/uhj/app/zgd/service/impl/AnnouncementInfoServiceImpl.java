package com.ule.uhj.app.zgd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.zgd.dao.CustomerInfoMapper;
import com.ule.uhj.app.zgd.dao.NoticeMapper;
import com.ule.uhj.app.zgd.dao.NoticeStatMapper;
import com.ule.uhj.app.zgd.model.CustomerInfo;
import com.ule.uhj.app.zgd.model.CustomerInfoExample;
import com.ule.uhj.app.zgd.model.Notice;
import com.ule.uhj.app.zgd.model.NoticeExample;
import com.ule.uhj.app.zgd.model.NoticeStat;
import com.ule.uhj.app.zgd.model.NoticeStatExample;
import com.ule.uhj.app.zgd.service.AnnouncementInfoService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.http.HttpClientUtil;

@Service
public class AnnouncementInfoServiceImpl implements AnnouncementInfoService{
	private static Logger log = LoggerFactory.getLogger(AnnouncementInfoServiceImpl.class);
	
	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private NoticeStatMapper noticeStatMapper;
	@Autowired
	private CustomerInfoMapper customerInfoMapper;

	/**
	 * 查询轮播的公告列表
	 */
	@Override
	public Map<String, Object> queryAnnounceShow(String userOnlyId,List<String> prdCode) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Notice> notices = new ArrayList<Notice>();
		NoticeExample example = new NoticeExample();
		example.setOrderByClause(" id desc");
		example.createCriteria().andCarouselEqualTo("1").andProCodeIn(prdCode);
		notices = noticeMapper.selectByExample(example);
		log.info("queryAnnounceShow notices:"+notices.size());
		
		//调用公告规则
		notices =noticeRule(userOnlyId,notices);
		
		resultMap.put("carouselNotice", notices);
		return resultMap;
	}

	/**
	 * 查询所有公告，该用户已读和未读公告
	 */
	@Override
	public Map<String, Object> queryAnnounceList(String userOnlyId,List<String> prdCode) throws Exception {
		log.info("queryAnnounceList userOnlyId:"+userOnlyId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//查询所有公告
		List<Notice> notices = new ArrayList<Notice>();
		NoticeExample example = new NoticeExample();
		example.createCriteria().andProCodeIn(prdCode);
		notices = noticeMapper.selectByExample(example);
		log.info("queryAnnounceList notices:"+notices.size());
		//调用公告规则
		notices =noticeRule(userOnlyId,notices);
		resultMap.put("allNotice", notices);
		//查询该用户所有已读公告
		List<NoticeStat> noticeStats = new ArrayList<NoticeStat>();
		NoticeStatExample noticeStatexample = new NoticeStatExample();
		noticeStatexample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		noticeStats = noticeStatMapper.selectByExample(noticeStatexample);
		log.info("queryAnnounceList noticeStats:"+noticeStats.size());
		resultMap.put("readNotice", noticeStats);
		return resultMap;
	}

	/**
	 * 保存用户读取的公告记录
	 */
	@Override
	public Map<String, Object> saveAnnounceRecord(Map<String, Object> param) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.info("saveAnnounceRecord param:"+param.toString());
		String userOnlyId = Convert.toStr(param.get("userOnlyId"));
		String noticeId = Convert.toStr(param.get("noticeId"));
		if(Check.isBlank(userOnlyId)){
			resultMap.put("code", "1000");
			resultMap.put("msg", "请重新登陆");
		}else if(Check.isBlank(noticeId)){
			resultMap.put("code", "1000");
			resultMap.put("msg", "请重新阅读本公告");
		}else{
			List<NoticeStat> noticeStats = new ArrayList<NoticeStat>();
			NoticeStatExample noticeStatexample = new NoticeStatExample();
			noticeStatexample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andNoticeIdEqualTo(Convert.toBigDecimal(noticeId));
			noticeStats = noticeStatMapper.selectByExample(noticeStatexample);
			if(noticeStats!=null&&noticeStats.size()>0){
				resultMap.put("code", "1000");
				resultMap.put("msg", "该公告已阅读");
			}else{
				NoticeStat noticeStat = new NoticeStat();
				noticeStat.setNoticeId(Convert.toBigDecimal(noticeId));
				noticeStat.setUserOnlyId(userOnlyId);
				noticeStat.setCreateTime(DateUtil.currTimeStr());
				noticeStatMapper.insert(noticeStat);
				resultMap.put("code", "0000");
				resultMap.put("msg", "保存成功");
			}
		}
		log.info("saveAnnounceRecord result:"+resultMap.toString());
		return resultMap;
	}

	
	public List<Notice> noticeRule(String userOnlyId,List<Notice> notices){
		if(CollectionUtils.isNotEmpty(notices)){
		// 准备调用规则组参数,循环判断公告是否展示
		log.info("queryAnnounceShow step1 准备参数");
		String channelCode="";
		List<CustomerInfo> customerInfos = new ArrayList<CustomerInfo>();
		CustomerInfoExample ci = new CustomerInfoExample();
		ci.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		customerInfos = customerInfoMapper.selectByExample(ci);
		if(customerInfos!= null && customerInfos.size()>0){
			channelCode =customerInfos.get(0).getChannelCode();
		}
		String rule_url = PropertiesHelper.getDfs("RULE_SET_URL");
		
		log.info("queryAnnounceShow step2 循环判断公告 channelCode:"+channelCode);
		for(int i=notices.size()-1;i>=0;i--){
			Notice notice =notices.get(i);
			if(StringUtils.isNotBlank(notice.getRuleSetId())){
				String ruleOutputResult="";
				JSONObject js =new JSONObject();
				JSONObject setmap = new JSONObject();
				setmap.put("ruleSetId", notice.getRuleSetId());
				setmap.put("userOnlyId", userOnlyId);
				setmap.put("channelCode", channelCode);	
				js.put("data", setmap.toString());
				log.info("queryAnnounceShow sendPostJson:"+js.toString());
				
				try {
					String res = HttpClientUtil.sendPostJson(rule_url, js.toString(), UhjConstant.time_out);
					log.info("queryAnnounceShow userOnlyId:"+userOnlyId+"; rule res:"+res);
					JSONObject resJs=JSONObject.fromObject(res);
					if("000000".equals(resJs.get("code"))){
						JSONObject object=(JSONObject) resJs.get("object");
						JSONArray data=object.getJSONArray("data");
						JSONObject strs=(JSONObject) data.toArray()[0];
						ruleOutputResult=Convert.toStr(strs.get("ruleOutputResult"));
						log.info("queryAnnounceShow userOnlyId:"+userOnlyId+";ruleOutputResult:"+ruleOutputResult);
						
						if("false".equals(ruleOutputResult)){
							notices.remove(i);
						}
					}
				} catch (Exception e) {
					log.error("noticeShow error", e);
				}
			}
		}
		}
		return notices;
	}
}
