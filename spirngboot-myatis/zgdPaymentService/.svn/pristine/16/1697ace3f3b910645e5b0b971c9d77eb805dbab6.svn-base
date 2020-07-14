package com.ule.uhj.app.zgd.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.ycZgd.BangZGClient;
import com.ule.uhj.ejb.client.ycZgd.YzgVpsInfoClient;


public class VpsInfoService {

	private static Logger log = LoggerFactory.getLogger(VpsInfoService.class);
	
	public static final String UsrOnlyid = "UsrOnlyid"; 
	public static final String OrgCode = "OrgCode";
	public static final String UsrName = "UsrName";
	public static final String OrgProvinceName = "OrgProvinceName";
	public static final String OrgCityName = "OrgCityName";
	public static final String OrgAreaName = "OrgAreaName";
	public static final String OrgProvince = "OrgProvince";
	public static final String OrgCity = "OrgCity";
	public static final String OrgArea = "OrgArea";
	public static final String PcActiveTime = "PcActiveTime";
	public static final String MobileActiveTime = "MobileActiveTime";
	public static final String StationName = "StationName";
	public static final String CertNo = "CertNo";
	public static final String StationAddress = "StationAddress";
	public static final String VisitSaleCode = "VisitSaleCode";
	public static final String MobileNumber = "MobileNumber";
	public static final String StationInfo3 = "StationInfo3";
	public static final String CheckoutProvince = "CheckoutProvince";
	public static final String CheckoutCity = "CheckoutCity";
	public static final String CheckoutArea = "CheckoutArea";
	public static final String CheckoutAddress = "CheckoutAddress";
	public static final String LeagueChannel = "LeagueChannel";
	public static final String MainBusiness1 = "MainBusiness1";
	
	
	/**
	 * 参考wiki: wiki.uletm.com/pages/viewpage.action?pageId=22310605
	 * 根据用户ID查询掌柜信息
	 * 返回对象中 visit_sale_code 地推人员ID
	 * @param userOnlyId
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, Object> getVpsInfoByUserOnlyId(String userOnlyId) throws Exception{
		Map<String, Object> vps = WildflyBeanFactory.getYzgVpsInfoClient().getVpsInfoByUserOnlyId(userOnlyId);
		log.info("getVpsInfoByUserOnlyId  vps:"+vps);
		return vps;
	}
	
	/**
	 * 根据用户ID获取地推人员信息
	 * @param userOnlyId
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, Object> queryBzgByUserOnlyId(String userOnlyId) throws Exception{
		Map<String, Object> vpsBzg = WildflyBeanFactory.getBangZGClient().queryBzgByUserOnlyId(userOnlyId);
		log.info("queryBzgByUserOnlyId  vpsBzg:"+vpsBzg);
		return vpsBzg;
	}
	
	
	/**
	 * 根据地推人员ID获取绑定的所有用户信息
	 * @param userOnlyId
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, Object> queryUserListByBzgId(String bzgId) throws Exception{
		Map<String, Object> userInfoList = WildflyBeanFactory.getBangZGClient().queryUserListByBzgId(bzgId);
		log.info("queryUserListByBzgId  userInfoList:"+userInfoList);
		return userInfoList;
	}
	
	public static Map<String, Object> getVillageAddressInfoByUserOnlyId(String userOnlyId) throws Exception{
		Map<String, Object> VillageAddress = WildflyBeanFactory.getYzgVpsInfoClient().getVillageAddressInfoByUserOnlyId(userOnlyId);
		log.info("getVillageAddressInfoByUserOnlyId  VillageAddress:"+VillageAddress);
		return VillageAddress;
	}
	/**
	 * 判断用户是否有掌柜贷App权限
	 * @param userOnlyId
	 * @return
	 * @throws Exception
	 */
	public static String queryShowInfo(String userOnlyId) throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userOnlyId", userOnlyId);
		String result = WildflyBeanFactory.getYzgVpsInfoClient().queryShowInfo(map);
		log.info("queryShowInfo  result:"+result);
		return result;
	}
	
	
}
