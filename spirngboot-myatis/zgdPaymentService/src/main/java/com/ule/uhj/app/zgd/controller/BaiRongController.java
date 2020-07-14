package com.ule.uhj.app.zgd.controller;

import java.util.HashMap;
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

import com.ule.uhj.app.zgd.model.InterfaceAccessInfoWithBLOBs;
import com.ule.uhj.app.zgd.service.InterfaceAccessInfoService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.uhj.util.http.HttpClientUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/baiRong")
public class BaiRongController {
	protected static Log log = LogFactory.getLog(BaiRongController.class);
	
//	@Autowired
//	private CarrierOperatorAuthService carrieroperatorService;
//	@Autowired
//	private CustomerInfoService customerInfoService;
	@Autowired
	private InterfaceAccessInfoService interfaceAccessInfoService;
	
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
	 * 百融获取手机三要素和在网时长
	 * http://localhost:8080/lendvps/baiRong/telCheckAndTelPeriod.do?jsoncallback=jsonp_1505196829886_48655&idCard=340881198609226517&name=伍海涛&phone=18201857659
	 * http://money.beta.ule.com:8080/lendvps/baiRong/telCheckAndTelPeriod.do?jsoncallback=jsonp_1505196829886_48655&idCard= &name= &phone=
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/telCheckAndTelPeriod")
	@ResponseBody
	public JSONPObject telCheckAndTelPeriod(HttpServletRequest request,@RequestParam String jsoncallback){
		String result=null;
		Map<String,Object> ResultMap = new HashMap<String, Object>();
		log.info("------------------------------baiRong/telCheckAndTelPeriod.do 开始-----------------------------------");
		try{
			String userOnlyId = getUserOnlyId(request);
			String idCard = Convert.toStr(request.getParameter("idCard"));
			String name = Convert.toStr(request.getParameter("name"));
			String phone = Convert.toStr(request.getParameter("phone"));
			if(name!=null&&name!=""){
				name = new String(name.getBytes("iso-8859-1"),"UTF-8");				
			}
			
			Map<String, String> param =new HashMap<String, String>();
			param.put("idCard", idCard);
			param.put("name", name);
			param.put("phone", phone);
			param.put("userOnlyId", userOnlyId);
			param.put("tranzCode", UhjConstant.transCode.BAIRONG_TELCHECK);
			log.info("telCheckAndTelPeriod param:"+param.toString());
			result = HttpClientUtil.sendPost(appkey_url, param);
			log.info("telCheckAndTelPeriod result:"+result);
			
			
			InterfaceAccessInfoWithBLOBs interfaceAccessBlob = new InterfaceAccessInfoWithBLOBs();
			interfaceAccessBlob.setUserOnlyId(userOnlyId);
			interfaceAccessBlob.setInterfaceType(UhjConstant.transCode.BAIRONG_TELCHECK);
			interfaceAccessBlob.setRequestInfo(JSONObject.fromObject(param).toString().getBytes());
			interfaceAccessBlob.setResponseInfo(JSONObject.fromObject(result).toString().getBytes());
			interfaceAccessBlob.setCreateTime(DateUtil.currTimeStr());
			interfaceAccessBlob.setUpdateTime(DateUtil.currTimeStr());
			interfaceAccessBlob.setStatus(UhjConstant.interfaceStutas.success);
			interfaceAccessInfoService.saveInterfaceData(interfaceAccessBlob);
			
			ResultMap.put("code", "0000");
			ResultMap.put("msg", "成功");
		}catch(Exception e){
			log.error("telCheckAndTelPeriod error!",e);
			ResultMap.put("code", "0001");
			ResultMap.put("msg", "失败："+e);
			
		}
		log.info("telCheckAndTelPeriod resultMap:"+ResultMap.toString());
		log.info("------------------------------baiRong/telCheckAndTelPeriod.do 结束-----------------------------------");
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(ResultMap));
	}
}
