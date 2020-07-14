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

import org.apache.commons.lang3.StringUtils;
import com.ule.uhj.app.zgd.dao.CreditApplyMapper;
import com.ule.uhj.app.zgd.model.CreditApply;
import com.ule.uhj.app.zgd.model.CreditApplyExample;
import com.ule.uhj.app.zgd.model.InterfaceAccessInfoWithBLOBs;
import com.ule.uhj.app.zgd.service.CarrierOperatorAuthService;
import com.ule.uhj.app.zgd.service.CustomerInfoService;
import com.ule.uhj.app.zgd.service.InterfaceAccessInfoService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.JsonUtil;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.uhj.util.http.HttpClientUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/suanhuaAuth")
public class SuanHuaAuthController {
	protected static Log log = LogFactory.getLog(SuanHuaAuthController.class);
	
	@Autowired
	private CarrierOperatorAuthService carrieroperatorService;
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private InterfaceAccessInfoService interfaceAccessInfoService;
	
	@Autowired
	private CreditApplyMapper creditApplyMapper;
	
	
	private static String appkey_url = PropertiesHelper.getDfs("app_interface_url");
	
	private String getUserOnlyId(HttpServletRequest request) throws Exception {
		String usronlyId =CommonHelper.getUserOnlyId(request);
		return usronlyId;
	}
	

	
	public static void main(String[] args) throws Exception {
		
//		String formpost = null;		
//		formpost ="{'stoken'='SH_20170927_27ddcb27da516c9bb6b06a964fd906ea','data'={'nextStep'='2','step'='1','loginTypes'=[{'defaultForm'='true','loginTypeCode'='UP','loginFields'=[{'readonly'='false','name'='name','label'='姓名','placeholder'='开卡人的姓名','type'='text'},{'readonly'='false','name'='idCardNo','label'='身份证号码','placeholder'='开卡人的身份证号码','type'='text'},{'readonly'='false','name'='smsCode','label'='短信验证码','placeholder'='短信验证码', 'type'='number'}], 'loginTypeDesc'='服务密码'}],'maxStep'='2'},'message'='请求处理成功','statusCode'='001'}";
//		formpost ="{'stoken'='SH_20170922_e019ada13c87f8e4fdd75f2047262ef3', 'data'={'nextStep'='N/A', 'step'='1', 'maxStep'='1'}, 'message'='请求处理成功', 'statusCode'='001'}";
//		
//		SuanHuaAuthController sh= new SuanHuaAuthController();
//		sh.getFormPostMapByJSON(formpost);
	}
	
	
	/**
	 * 获取短信验证码
	 * http://money.beta.ule.com:8080/lendvps/suanhuaAuth/smssend.do?jsoncallback=jsonp_1505196829886_48655&stoken= &step=
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/smssend")
	@ResponseBody
	public JSONPObject smssend(HttpServletRequest request,@RequestParam String jsoncallback){
		String smssendResult=null;
		Map<String,Object> ResultMap = new HashMap<String, Object>();
		try{
			System.out.println("------------------------------suanhuaAuth/smssend.do 开始-----------------------------------");
			String userOnlyId = getUserOnlyId(request);
			String stoken = Convert.toStr(request.getParameter("stoken"));
			String step="1";
			if(StringUtils.isNotBlank(Convert.toStr(request.getParameter("step")))){
				step = Convert.toStr(request.getParameter("step"));				
			}
			
			Map<String, String> paramMap =new HashMap<String, String>();
			paramMap.put("stoken", stoken);
			paramMap.put("step", step);
			paramMap.put("userOnlyId", userOnlyId);
			paramMap.put("tranzCode", UhjConstant.transCode.SMS_SEND);
			log.info("smssend param:"+paramMap.toString());
			smssendResult = HttpClientUtil.sendPost(appkey_url, paramMap);
			log.info("smssend result:"+smssendResult);
			
			ResultMap=getResultMapByJSON(smssendResult);
			
			//为了前段短信公共方法(成功都是返回0000)
			if("001".equals(ResultMap.get("code"))){
			 ResultMap.put("code", "0000");				 
			}
			
		}catch(Exception e){
			log.error("loginform error!",e);
		}
		log.info("loginform resultMap:"+ResultMap.toString());
		System.out.println("------------------------------suanhuaAuth/smssend.do 结束-----------------------------------");
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(ResultMap));
	}
	/**
	 * 刷新图片验证码
	 * http://money.beta.ule.com:8080/lendvps/suanhuaAuth/imagerefresh.do?jsoncallback=jsonp_1505196829886_48655&stoken=SH_20170922_e019ada13c87f8e4fdd75f2047262ef3&step=1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/imagerefresh")
	@ResponseBody
	public JSONPObject imagerefresh(HttpServletRequest request,@RequestParam String jsoncallback){
		String imagerefreshResult=null;
		Map<String,Object> ResultMap = new HashMap<String, Object>();
		try{
			System.out.println("------------------------------suanhuaAuth/imagerefresh.do 开始-----------------------------------");
			String userOnlyId = getUserOnlyId(request);
			String stoken = Convert.toStr(request.getParameter("stoken"));
			String step="1";
			if(StringUtils.isNotBlank(Convert.toStr(request.getParameter("step")))){
				step = Convert.toStr(request.getParameter("step"));				
			}
			
			Map<String, String> paramMap =new HashMap<String, String>();
			paramMap.put("stoken", stoken);
			paramMap.put("step", step);
			paramMap.put("userOnlyId", userOnlyId);
			paramMap.put("tranzCode", UhjConstant.transCode.IMAGE_REFRESH);
			log.info("smssend param:"+paramMap.toString());
			imagerefreshResult = HttpClientUtil.sendPost(appkey_url, paramMap);
			log.info("smssend result:"+imagerefreshResult);
			
			ResultMap=getResultMapByJSON(imagerefreshResult);
			
		}catch(Exception e){
			log.error("loginform error!",e);
		}
		log.info("loginform resultMap:"+ResultMap.toString());
		System.out.println("------------------------------suanhuaAuth/imagerefresh.do 结束-----------------------------------");
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(ResultMap));
	}
	
	/**
	 * 提交表单验证
	 * http://money.beta.ule.com:8080/lendvps/suanhuaAuth/formpost.do?jsoncallback=jsonp_1505196829886_48655&stoken= 
	 * &step=  				(选项)步数
	 * &phone=				(选项)手机号码
	 * &captcha=			(选项)图片验证码
	 * &idCardNo=			(选项)手机属主身份证
	 * &name=				(选项)手机属主姓名
	 * &servicePassword=	(选项)服务密码
	 * &smsCode=			(选项)短信验证码
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/formpost")
	@ResponseBody
	public JSONPObject formpost(HttpServletRequest request,@RequestParam String jsoncallback){
		String formpostResult=null;
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try{
			System.out.println("------------------------------suanhuaAuth/formpost.do 开始-----------------------------------");
			String userOnlyId = getUserOnlyId(request);
			String stoken = Convert.toStr(request.getParameter("stoken"));
			String phone = Convert.toStr(request.getParameter("phone"));
			String captcha = Convert.toStr(request.getParameter("captcha"));
			String idCardNo = Convert.toStr(request.getParameter("idCardNo"));
			String name = request.getParameter("name");

			
			String servicePassword = Convert.toStr(request.getParameter("servicePassword"));
			String smsCode = Convert.toStr(request.getParameter("smsCode"));
			String step="1";
			if(StringUtils.isNotBlank(Convert.toStr(request.getParameter("step")))){
				step = Convert.toStr(request.getParameter("step"));				
			}
			StringBuffer form=new StringBuffer("{");
			if(captcha!=null&&captcha!=""){
				form.append("'captcha':'"+captcha+"',");				
			}
			if(idCardNo!=null&&idCardNo!=""){
				form.append("'idCardNo':'"+idCardNo+"',");				
			}
			if(name!=null&&name!=""){
				form.append("'name':'"+name+"',");				
			}
			if(servicePassword!=null&&servicePassword!=""){
				form.append("'servicePassword':'"+servicePassword+"',");				
			}
			if(smsCode!=null&&smsCode!=""){
				form.append("'smsCode':'"+smsCode+"',");				
			}
			form.append("'phoneNo':'"+phone+"'}");
			
			Map<String, String> paramMap =new HashMap<String, String>();
			paramMap.put("form", form.toString());
			paramMap.put("stoken", stoken);
			paramMap.put("step", step);
			paramMap.put("userOnlyId", userOnlyId);
			paramMap.put("tranzCode", UhjConstant.transCode.FORM_POST);
			log.info("formpost param:"+paramMap.toString());
			formpostResult = HttpClientUtil.sendPost(appkey_url, paramMap);
			log.info("formpost result:"+formpostResult);
			
			resultMap=getFormPostMapByJSON(formpostResult);
			
			//将请求成功数据保存到t_j_Interface_Access_Info,位取报告做准备
			if("001".equals(resultMap.get("code"))&&"N/A".equals(resultMap.get("nextStep"))){
				System.out.println("-------------保存数据--------------");
				
				CreditApplyExample applyExample = new CreditApplyExample();
				applyExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
				List<CreditApply> applys = creditApplyMapper.selectByExample(applyExample);
				String applyId="";
				if(applys!=null&&applys.size()>0){
					applyId = applys.get(0).getId();
				}
				
				InterfaceAccessInfoWithBLOBs interfaceAccessBlob = new InterfaceAccessInfoWithBLOBs();
				interfaceAccessBlob.setUserOnlyId(userOnlyId);
				interfaceAccessBlob.setAppId(applyId);
				interfaceAccessBlob.setInterfaceType(UhjConstant.transCode.REPORT_BY_STOKEN);
				interfaceAccessBlob.setRequestInfo(JSONObject.fromObject(paramMap).toString().getBytes());
				interfaceAccessBlob.setResponseInfo(null);
				interfaceAccessBlob.setCreateTime(DateUtil.currTimeStr());
				interfaceAccessBlob.setUpdateTime(DateUtil.currTimeStr());
				interfaceAccessBlob.setStatus(UhjConstant.interfaceStutas.holdon);
				interfaceAccessInfoService.saveInterfaceData(interfaceAccessBlob);
				
				//修改申请表状态为 待审核
				if(applys!=null&&applys.size()>0){
					CreditApply apply = applys.get(0);
					apply.setStatus(UhjConstant.applyStatus.APPLY_STATUS_NORMAL);
					apply.setUpdateTime(DateUtil.currTimeStr());
					creditApplyMapper.updateByExampleSelective(apply, applyExample);
				}
			}

		}catch(Exception e){
			log.error("formpost error!",e);
		}
		log.info("formpost resultMap:"+resultMap.toString());
		System.out.println("------------------------------suanhuaAuth/formpost.do 结束-----------------------------------");
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
	}
	
	/**
	 * 算话初始化表单
	 * http://localhost:8080/lendvps/suanhuaAuth/loginform.do?jsoncallback=321321&phone=18621840499
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/loginform")
	@ResponseBody
	public JSONPObject loginform(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try{
			System.out.println("------------------------------suanhuaAuth/loginform.do 开始-----------------------------------");
			String userOnlyId = getUserOnlyId(request);
			String phone = Convert.toStr(request.getParameter("phone"));
			
			//init初始化
			Map<String, String> paramMap =new HashMap<String, String>();
			paramMap.put("phone", phone);
			paramMap.put("userOnlyId", userOnlyId);
			paramMap.put("tranzCode", UhjConstant.transCode.INIT);
			log.info("initResult param:"+paramMap.toString());
			String initResult = HttpClientUtil.sendPost(appkey_url, paramMap);
			log.info("initResult result:"+initResult);
			Map<String,Object> initResultMap = getinitResultMapByJSON(initResult);
			String code = (String)initResultMap.get("code");
			if(!"001".equals(code)){				
				String result = JsonResult.getInstance().addError(initResultMap.get("msg").toString()).toJsonStr();
				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
			}
			
			//获取登录表单
			String stoken = (String)initResultMap.get("stoken");
			String itemCode = (String)initResultMap.get("itemCode");
			String loginType = (String)initResultMap.get("loginType");
			paramMap.clear();
			paramMap.put("stoken", stoken);
			paramMap.put("itemCode", itemCode);
			paramMap.put("loginTypeCode", loginType);
			paramMap.put("userOnlyId", userOnlyId);
			paramMap.put("tranzCode", UhjConstant.transCode.LOGIN_FORM);
			log.info("loginform param:"+paramMap.toString());
			String loginformResult = HttpClientUtil.sendPost(appkey_url, paramMap);
			log.info("loginform result:"+loginformResult);
			resultMap = getLoginFormMapByJSON(loginformResult);
			
			//运营商
			if(resultMap.get("loginFields")!=null){
				String loginFieldsStr =resultMap.get("loginFields").toString();
				if(loginFieldsStr.contains("servicePassword")){
					itemCode=updateOperator(itemCode);
					resultMap.put("itemCode", itemCode);
				}				
			}
			
			code = (String)resultMap.get("code");
			if(!"001".equals(code)){				
				String result = JsonResult.getInstance().addError(resultMap.get("msg").toString()).toJsonStr();
				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
			}
		}catch(Exception e){
			log.error("loginform error!",e);
		}
		log.info("loginform resultMap:"+resultMap.toString());
		System.out.println("------------------------------suanhuaAuth/loginform.do 结束-----------------------------------");
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
	}
	
	private String updateOperator(String itemCode) {
		String rs=itemCode;
		//name:'HNYD', 湖南移动
		//name:'HNDX', 湖南电信
		//name:'JSYD', 江苏移动
		//name:'YD', 其他移动
		//name:'DX', 其他电信
		
		if("OPERATOR_YIDONGHUNAN".equals(itemCode)){
			return "HNYD";
		}
		if("OPERATOR_DIANXINHUNAN".equals(itemCode)){
			return "HNDX";
		}
		if("OPERATOR_YIDONGJIANGSU".equals(itemCode)){
			return "JSYD";
		}
		if(itemCode.contains("OPERATOR_YIDONG")){
			return "YD";
		}
		if(itemCode.contains("OPERATOR_DIANXIN")){
			return "DX";
		}
		return itemCode;
	}



	public Map<String,Object> getinitResultMapByJSON(String loginform){
	Map<String,Object> resultMap = new HashMap<String, Object>();
	
	Map<String,Object> loginformMap = JsonUtil.getMapFromJsonString(loginform);
	if(loginformMap!=null&&loginformMap.containsKey("data")){
		String stoken = Convert.toStr(loginformMap.get("stoken"));
		resultMap.put("stoken", stoken);	//stoken
		List<String> loginTypeList = new ArrayList<String>();
		
		String dataStr = Convert.toStr(loginformMap.get("data"));
		Map<String,Object> dataMap = JsonUtil.getMapFromJsonString(dataStr);
		
		String suppliersStr = Convert.toStr(dataMap.get("suppliers"));
		JSONArray suppliersJSONArray = JSONArray.fromObject(suppliersStr);
		suppliersStr= suppliersJSONArray.get(0).toString();
		Map<String,Object> suppliersMap = JsonUtil.getMapFromJsonString(suppliersStr);
		
		resultMap.put("itemCode", suppliersMap.get("itemCode"));	//itemCode
		
		String loginTypesStr = Convert.toStr(suppliersMap.get("loginTypes"));
		JSONArray loginTypesJSONArray = JSONArray.fromObject(loginTypesStr);
		
        //循环取出命中内容，放到list里面  
        for(int i=0;i<loginTypesJSONArray.size();i++){  
            String loginType = Convert.toStr(loginTypesJSONArray.get(i));
            Map<String,Object> loginTypeMap = JsonUtil.getMapFromJsonString(loginType);
            loginTypeList.add(Convert.toStr(loginTypeMap.get("code")));
        } 
		
		if(loginTypeList.contains("UP")){
			resultMap.put("loginType", "UP");
		}else{
			resultMap.put("loginType", loginTypeList.get(0));
		}			
	}
	resultMap.put("msg",  null!=loginformMap?loginformMap.get("message"):"");
	resultMap.put("code", null!=loginformMap?loginformMap.get("statusCode"):"");
	System.out.println(resultMap);
	return resultMap;
	}
	
	public Map<String,Object> getLoginFormMapByJSON(String loginform){
	Map<String,Object> resultMap = new HashMap<String, Object>();
	
	Map<String,Object> loginformMap = JsonUtil.getMapFromJsonString(loginform);
	resultMap.put("stoken", loginformMap.get("stoken"));
	if(loginformMap!=null&&loginformMap.containsKey("data")){		
		String dataStr = Convert.toStr(loginformMap.get("data"));
		Map<String,Object> dataMap = JsonUtil.getMapFromJsonString(dataStr);
		String loginTypes = Convert.toStr(dataMap.get("loginTypes"));		
		JSONArray loginTypesJSONArray = JSONArray.fromObject(loginTypes);
		Map<String,Object> loginTypesMap = JsonUtil.getMapFromJsonString(loginTypesJSONArray.get(0).toString());
		String loginFields = Convert.toStr(loginTypesMap.get("loginFields"));
		resultMap.put("loginFields", loginFields);
	}
	resultMap.put("msg", loginformMap.get("message"));
	resultMap.put("code", loginformMap.get("statusCode"));
	
	System.out.println(resultMap);
	return resultMap;
	}
	
	public Map<String,Object> getFormPostMapByJSON(String formPost){
	Map<String,Object> resultMap = new HashMap<String, Object>();	
	Map<String,Object> formPostMap = JsonUtil.getMapFromJsonString(formPost);
	resultMap.put("stoken", formPostMap.get("stoken"));
	
	String statusCode =Convert.toStr(formPostMap.get("statusCode"));
	resultMap.put("code", formPostMap.get("statusCode"));
	
	if("001".equals(statusCode)&&formPostMap.containsKey("data")){		
		String dataStr = Convert.toStr(formPostMap.get("data"));
		Map<String,Object> dataMap = JsonUtil.getMapFromJsonString(dataStr);
		String nextStep =Convert.toStr(dataMap.get("nextStep"));
		resultMap.put("nextStep", nextStep);
		if(!"N/A".equals(nextStep)){
			String loginTypes = Convert.toStr(dataMap.get("loginTypes"));					
			JSONArray loginTypesJSONArray = JSONArray.fromObject(loginTypes);
			Map<String,Object> loginTypesMap = JsonUtil.getMapFromJsonString(loginTypesJSONArray.get(0).toString());
			String loginFields = Convert.toStr(loginTypesMap.get("loginFields"));
			resultMap.put("loginFields", loginFields);
		}		
	}	
	resultMap.put("msg", formPostMap.get("message"));
	
	System.out.println(resultMap);
	return resultMap;
	}
	
	
	public Map<String,Object> getResultMapByJSON(String formPost){
	Map<String,Object> resultMap = JsonUtil.getMapFromJsonString(formPost);
	
	resultMap.put("code", resultMap.get("statusCode"));
	resultMap.put("msg", resultMap.get("message"));
	resultMap.remove("statusCode");
	resultMap.remove("message");
	System.out.println(resultMap);
	return resultMap;
	}
}
