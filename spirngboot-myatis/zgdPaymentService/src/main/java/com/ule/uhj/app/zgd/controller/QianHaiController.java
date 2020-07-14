package com.ule.uhj.app.zgd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.zgd.dao.CustomerInfoMapper;
import com.ule.uhj.app.zgd.model.CustomerInfo;
import com.ule.uhj.app.zgd.model.CustomerInfoExample;
import com.ule.uhj.app.zgd.model.InterfaceAccessInfoWithBLOBs;
import com.ule.uhj.app.zgd.service.InterfaceAccessInfoService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.zgd.ZgdQueryClient;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.uhj.util.http.HttpClientUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/qianHai")
public class QianHaiController {
	protected static Log log = LogFactory.getLog(QianHaiController.class);
	
	@Autowired
	private InterfaceAccessInfoService interfaceAccessInfoService;
	
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	
	private static String appkey_url = PropertiesHelper.getDfs("app_interface_url");
	
	private String getUserOnlyId(HttpServletRequest request) throws Exception {
		String usronlyId =CommonHelper.getUserOnlyId(request);
		return usronlyId;
	}
	

	
	public static void main(String[] args) {
		
		String formpost = null;		
//		formpost ="{'stoken'='SH_20170927_27ddcb27da516c9bb6b06a964fd906ea','data'={'nextStep'='2','step'='1','loginTypes'=[{'defaultForm'='true','loginTypeCode'='UP','loginFields'=[{'readonly'='false','name'='name','label'='姓名','placeholder'='开卡人的姓名','type'='text'},{'readonly'='false','name'='idCardNo','label'='身份证号码','placeholder'='开卡人的身份证号码','type'='text'},{'readonly'='false','name'='smsCode','label'='短信验证码','placeholder'='短信验证码', 'type'='number'}], 'loginTypeDesc'='服务密码'}],'maxStep'='2'},'message'='请求处理成功','statusCode'='001'}";
//		formpost ="{'stoken'='SH_20170922_e019ada13c87f8e4fdd75f2047262ef3', 'data'={'nextStep'='N/A', 'step'='1', 'maxStep'='1'}, 'message'='请求处理成功', 'statusCode'='001'}";
//		
//		BaiRongController sh= new BaiRongController();
//		sh.getFormPostMapByJSON(formpost);
	}
	
	/**
	 * 前海征信获取手机三要素和在网时长
	 * http://money.beta.ule.com/lendvps/qianHai/verifyPhone.do?jsoncallback=jsonp_1505196829886_48655&certNo= &mobileNo= &appId=
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/verifyPhone")
	@ResponseBody
	public JSONPObject verifyPhone(HttpServletRequest request,@RequestParam String jsoncallback){
		String result=null;
		Map<String,Object> ResultMap = new HashMap<String, Object>();
		log.info("------------------------------qianHai/verifyPhone.do 开始-----------------------------------");
		try{
			String userOnlyId = getUserOnlyId(request);
			String appId = Convert.toStr(request.getParameter("appId"));	//申请编号
			String idCard = Convert.toStr(request.getParameter("certNo"));	//身份证号
			String name = null;	//姓名
			String phone = Convert.toStr(request.getParameter("mobileNo"));	//手机号码

			
			List<CustomerInfo> customerInfos = new ArrayList<CustomerInfo>();
			CustomerInfoExample customerInfoExample = new CustomerInfoExample();
			customerInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
			customerInfos = customerInfoMapper.selectByExample(customerInfoExample);
			if(customerInfos!= null && customerInfos.size()>0){
				name=customerInfos.get(0).getCustomerName();
			}
			
			String mobileOperatorsType = "";
			try{
				Map<String,Class<?>> paramMap = new HashMap<String, Class<?>>();
				paramMap.put("MobileOperatorsType",String.class);
				Map<String,Object> constantMap = WildflyBeanFactory.getZgdQueryClient().queryZgdConstantValue(paramMap);
				if(constantMap!=null){
					mobileOperatorsType = ((List<String>)constantMap.get("MobileOperatorsType")).get(0);
				}
			}catch (Exception e) {
				log.error("verifyPhone query mobileOperatorsType error!",e);
			}
			
			Map<String, String> param =new HashMap<String, String>();
			if("PY".equals(mobileOperatorsType)){
				//鹏元在网时长接口
				param.put("appId", appId);
				param.put("documentNo", idCard);
				param.put("name", name);
				param.put("phone", phone);
				param.put("userOnlyId", userOnlyId);
				param.put("tranzCode", UhjConstant.transCode.PENGYUAN_VERIFYPHONE);
			}else{
				//前海在网时长接口
				param.put("appId", appId);
				param.put("idCard", idCard);
				param.put("name", name);
				param.put("phone", phone);
				param.put("userOnlyId", userOnlyId);
				param.put("tranzCode", UhjConstant.transCode.QIAN_HAI_VERIFYPHONE);
			}
			
			try {				
				log.info("verifyPhone param:"+param.toString());
				result = HttpClientUtil.sendPost(appkey_url, param);
				log.info("verifyPhone result:"+result);
			} catch (Exception e) {
				result ="fail";
			}
			
			InterfaceAccessInfoWithBLOBs ifABlob = new InterfaceAccessInfoWithBLOBs();
			ifABlob.setUserOnlyId(userOnlyId);
			ifABlob.setAppId(appId);
			ifABlob.setInterfaceType("PY".equals(mobileOperatorsType)?UhjConstant.transCode.PENGYUAN_VERIFYPHONE:UhjConstant.transCode.QIAN_HAI_VERIFYPHONE);
			ifABlob.setRequestInfo(JSONObject.fromObject(param).toString().getBytes());
			ifABlob.setResponseInfo(JSONObject.fromObject(result).toString().getBytes());
			ifABlob.setCreateTime(DateUtil.currTimeStr());
			ifABlob.setUpdateTime(DateUtil.currTimeStr());
			ifABlob.setStatus(UhjConstant.interfaceStutas.success);
			interfaceAccessInfoService.saveInterfaceData(ifABlob);
			
			ResultMap.put("code", "0000");
			ResultMap.put("msg", "成功");
		}catch(Exception e){
			log.error("verifyPhone error!",e);
			ResultMap.put("code", "0001");
			ResultMap.put("msg", "失败："+e);
			
		}
		log.info("verifyPhone resultMap:"+ResultMap.toString());
		log.info("------------------------------qianHai/verifyPhone.do 结束-----------------------------------");
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(ResultMap));
	}
	
	
	/**
	 * 前海征信获取手机三要素和在网时长
	 * http://money.beta.ule.com/lendvps/qianHai/entMgrInc.do?jsoncallback=jsonp_1505196829886_48655&certNo= &appId=
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/entMgrInc")
	@ResponseBody
	public JSONPObject entMgrInc(HttpServletRequest request,@RequestParam String jsoncallback){
		String result=null;
		Map<String,Object> ResultMap = new HashMap<String, Object>();
		log.info("------------------------------qianHai/verifyPhone.do 开始-----------------------------------");
		try{
			String userOnlyId = getUserOnlyId(request);
			String appId = Convert.toStr(request.getParameter("appId"));	//申请编号
			String idCard = Convert.toStr(request.getParameter("certNo"));	//身份证号
			String name = null;	//姓名
			
			List<CustomerInfo> customerInfos = new ArrayList<CustomerInfo>();
			CustomerInfoExample customerInfoExample = new CustomerInfoExample();
			customerInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
			customerInfos = customerInfoMapper.selectByExample(customerInfoExample);
			if(customerInfos!= null && customerInfos.size()>0){
				name=customerInfos.get(0).getCustomerName();
			}
			
			
			Map<String, String> param =new HashMap<String, String>();
			param.put("appId", appId);
			param.put("idCard", idCard);
			param.put("name", name);
			param.put("userOnlyId", userOnlyId);
			param.put("tranzCode", UhjConstant.transCode.QIAN_HAI_ENTMGRINC);
			log.info("entMgrInc param:"+param.toString());
			result = HttpClientUtil.sendPost(appkey_url, param);
			log.info("entMgrInc result:"+result);
			
			
//			InterfaceAccessInfoWithBLOBs ifABlob = new InterfaceAccessInfoWithBLOBs();
//			ifABlob.setUserOnlyId(userOnlyId);
//			ifABlob.setAppId(appId);
//			ifABlob.setInterfaceType(UhjConstant.transCode.QIAN_HAI_ENTMGRINC);
//			ifABlob.setRequestInfo(JSONObject.fromObject(param).toString().getBytes());
//			ifABlob.setResponseInfo(JSONObject.fromObject(result).toString().getBytes());
//			ifABlob.setCreateTime(DateUtil.currTimeStr());
//			ifABlob.setUpdateTime(DateUtil.currTimeStr());
//			ifABlob.setStatus(UhjConstant.interfaceStutas.success);
//			interfaceAccessInfoService.saveInterfaceData(ifABlob);
			
			ResultMap.put("code", "0000");
			ResultMap.put("msg", "成功");
			ResultMap.put("result", result);
		}catch(Exception e){
			log.error("verifyPhone error!",e);
			ResultMap.put("code", "0001");
			ResultMap.put("msg", "失败："+e);
			
		}
		log.info("entMgrInc resultMap:"+ResultMap.toString());
		log.info("------------------------------qianHai/entMgrInc.do 结束-----------------------------------");
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(ResultMap));
	}
}
