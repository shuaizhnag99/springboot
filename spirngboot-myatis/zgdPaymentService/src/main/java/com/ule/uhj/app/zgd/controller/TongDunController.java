package com.ule.uhj.app.zgd.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.zgd.dao.CreditApplyMapper;
import com.ule.uhj.app.zgd.model.CreditApply;
import com.ule.uhj.app.zgd.model.CreditApplyExample;
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
@RequestMapping("/tongdunAuth")
public class TongDunController {
	protected static Log log = LogFactory.getLog(TongDunController.class);
	
//	@Autowired
//	private CarrierOperatorAuthService carrieroperatorService;
//	@Autowired
//	private CustomerInfoService customerInfoService;
	@Autowired
	private InterfaceAccessInfoService interfaceAccessInfoService;
	
	@Autowired
	private CreditApplyMapper creditApplyMapper;
	
	
	private static String appkey_url = PropertiesHelper.getDfs("app_interface_url");
	
	private String getUserOnlyId(HttpServletRequest request) throws Exception {
		String usronlyId =CommonHelper.getUserOnlyId(request);
//		String usronlyId ="10000026049";
		return usronlyId;
	}
	

	
	public static void main(String[] args) throws Exception {
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		String user_mobile	=	"13399533011";
		String user_name	=	"13399533011";
		String user_pass=	"810235";
		String real_name		=	"潘星";
		String identity_code	=	"340221199009050031";
		String userOnlyId = "10000026049";
		
		Map<String, String> paramMap =new HashMap<String, String>();
		paramMap.put("user_mobile", user_mobile);
		paramMap.put("real_name", real_name);
		paramMap.put("identity_code", identity_code);
		paramMap.put("user_name", user_name);
		paramMap.put("user_pass", user_pass);
		paramMap.put("userOnlyId", userOnlyId);
		paramMap.put("tranzCode", UhjConstant.transCode.TONGDUN_FIRSTACQUIRE);
		log.info("TongDunController firstAcquire param:"+paramMap.toString());
		String acquireResult = HttpClientUtil.sendPost(appkey_url, paramMap);
		log.info("TongDunController firstAcquire result:"+acquireResult);
		resultMap = getMapFromJsonString1(acquireResult);
		
		if("100".equals(Convert.toStr(resultMap.get("code")))){
			String bodyStr = Convert.toStr(resultMap.get("bodyStr"));
			log.info("firstAcquire bodyStr:"+bodyStr);
			if(StringUtils.isNotBlank(bodyStr)){
				paramMap.clear();
				paramMap = getMapbySring1(bodyStr);
				paramMap.put("sms_code", "null");
				paramMap.put("userOnlyId", userOnlyId);
				paramMap.put("tranzCode", UhjConstant.transCode.TONGDUN_ACQUIRE);
				log.info("TongDunController firstAcquire 15miao param:"+paramMap.toString());
				Thread.sleep(15000);
				acquireResult = HttpClientUtil.sendPost(appkey_url, paramMap);
				log.info("TongDunController firstAcquire 15miao result:"+acquireResult);
				
				resultMap = getMapFromJsonString1(acquireResult);
				
				if("100".equals(Convert.toStr(resultMap.get("code")))){
					log.info("TongDunController firstAcquire 30miao param:"+paramMap.toString());
					Thread.sleep(15000);
					acquireResult = HttpClientUtil.sendPost(appkey_url, paramMap);
					log.info("TongDunController firstAcquire 30miao result:"+acquireResult);
					resultMap = getMapFromJsonString1(acquireResult);
					
				}
			}
		}

	}

	
	/**
	 * 重试验证码
	 * http://money.beta.ule.com:8080/lendvps/tongdunAuth/retry.do?jsoncallback=jsonp_1505196829886_48655&task_id
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/retry")
	@ResponseBody
	public JSONPObject retry(HttpServletRequest request,@RequestParam String jsoncallback){
		String retryResult=null;
		Map<String,Object> ResultMap = new HashMap<String, Object>();
		try{
			System.out.println("------------------------------tongdunAuth/retry.do 开始-----------------------------------");
			String userOnlyId = getUserOnlyId(request);
			String task_id = Convert.toStr(request.getParameter("task_id"));

			
			Map<String, String> paramMap =new HashMap<String, String>();
			paramMap.put("task_id", task_id);
			paramMap.put("userOnlyId", userOnlyId);
			paramMap.put("tranzCode", UhjConstant.transCode.TONGDUN_RETRY);
			log.info("retry param:"+paramMap.toString());
			retryResult = HttpClientUtil.sendPost(appkey_url, paramMap);
			log.info("retry result:"+retryResult);
			
			ResultMap=getMapFromJsonString(retryResult);
			
			ResultMap.put("msg", ResultMap.get("message"));
			
		}catch(Exception e){
			log.error("loginform error!",e);
		}
		log.info("loginform resultMap:"+ResultMap.toString());
		System.out.println("------------------------------tongdunAuth/retry.do 结束-----------------------------------");
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(ResultMap));
	}
	
	
	/**
	 * 重置服务密码(创建)
	 * http://money.beta.ule.com:8080/lendvps/tongdunAuth/rstpwdCreate.do?jsoncallback=jsonp_1505196829886_48655&task_id
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/rstpwdCreate")
	@ResponseBody
	public JSONPObject rstpwdCreate(HttpServletRequest request,@RequestParam String jsoncallback){
		String rstpwdCreateResult=null;
		Map<String,Object> ResultMap = new HashMap<String, Object>();
		try{
			System.out.println("------------------------------tongdunAuth/rstpwdCreate.do 开始-----------------------------------");
			String userOnlyId = getUserOnlyId(request);
			String user_mobile = Convert.toStr(request.getParameter("user_mobile"));

			
			Map<String, String> paramMap =new HashMap<String, String>();
			paramMap.put("user_mobile", user_mobile);
			paramMap.put("userOnlyId", userOnlyId);
			paramMap.put("tranzCode", UhjConstant.transCode.TONGDUN_RSTPDWCREATE);
			log.info("rstpwdCreate param:"+paramMap.toString());
			rstpwdCreateResult = HttpClientUtil.sendPost(appkey_url, paramMap);
			log.info("rstpwdCreate result:"+rstpwdCreateResult);
			
			ResultMap=getMapFromJsonString(rstpwdCreateResult);
			ResultMap.put("msg", ResultMap.get("message"));
			
		}catch(Exception e){
			log.error("rstpwdCreate error!",e);
		}
		log.info("rstpwdCreate resultMap:"+ResultMap.toString());
		System.out.println("------------------------------tongdunAuth/rstpwdCreate.do 结束-----------------------------------");
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(ResultMap));
	}
	
	
	/**
	 * 重置服务密码(验证)
	 * http://money.beta.ule.com:8080/lendvps/tongdunAuth/rstpwdSubmit.do?jsoncallback=jsonp_1505196829886_48655&task_id
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/rstpwdSubmit")
	@ResponseBody
	public JSONPObject rstpwdSubmit(HttpServletRequest request,@RequestParam String jsoncallback){
		String rstpwdSubmitResult=null;
		Map<String,Object> ResultMap = new HashMap<String, Object>();
		try{
			System.out.println("------------------------------tongdunAuth/rstpwdSubmit.do 开始-----------------------------------");
			String userOnlyId = getUserOnlyId(request);
			
			String task_id	=	Convert.toStr(request.getParameter("task_id"));
			String sms_code	=	Convert.toStr(request.getParameter("sms_code"));					//手机验证码
			String auth_code	=	Convert.toStr(request.getParameter("auth_code"));				//图片验证码
			String identity_code	=	Convert.toStr(request.getParameter("identity_code"));		//身份证号码
			String real_name	=	Convert.toStr(request.getParameter("real_name"));				//真实姓名
			String user_pass	=	Convert.toStr(request.getParameter("user_pass"));				//新服务密码
			String task_stage	=	Convert.toStr(request.getParameter("task_stage"));
			
			
			Map<String, String> paramMap =new HashMap<String, String>();
			paramMap.put("task_id", task_id);
			paramMap.put("sms_code", sms_code);
			paramMap.put("auth_code", auth_code);
			paramMap.put("identity_code", identity_code);
			paramMap.put("real_name", real_name);
			paramMap.put("user_pass", user_pass);
			paramMap.put("task_stage", task_stage);
			paramMap.put("userOnlyId", userOnlyId);
			paramMap.put("tranzCode", UhjConstant.transCode.TONGDUN_RSTPDWCREATE);
			log.info("rstpwdSubmit param:"+paramMap.toString());
			rstpwdSubmitResult = HttpClientUtil.sendPost(appkey_url, paramMap);
			log.info("rstpwdSubmit result:"+rstpwdSubmitResult);
			
			ResultMap=getMapFromJsonString(rstpwdSubmitResult);
			ResultMap.put("msg", ResultMap.get("message"));
			
		}catch(Exception e){
			log.error("rstpwdSubmit error!",e);
		}
		log.info("rstpwdSubmit resultMap:"+ResultMap.toString());
		System.out.println("------------------------------tongdunAuth/rstpwdSubmit.do 结束-----------------------------------");
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(ResultMap));
	}

	/**
	 * 同盾初始化验证
	 * http://money.beta.ule.com:8080/lendvps/tongdunAuth/firstAcquire.do?jsoncallback=321321&user_mobile=18621840499&real_name=伍海涛&identity_code=340881198609226517&user_pass=861477
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/firstAcquire")
	@ResponseBody
	public JSONPObject firstAcquire(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try{
			log.info("------------------------------tongdunAuth/firstAcquire.do 开始-----------------------------------");
			String userOnlyId = getUserOnlyId(request);
			
			String user_mobile = Convert.toStr(request.getParameter("user_mobile"));
			String real_name = Convert.toStr(request.getParameter("real_name"));
			String identity_code = Convert.toStr(request.getParameter("identity_code"));
			String user_name =user_mobile;
			String user_pass = Convert.toStr(request.getParameter("user_pass"));
						
			Map<String, String> paramMap =new HashMap<String, String>();
			paramMap.put("user_mobile", user_mobile);
			paramMap.put("real_name", real_name);
			paramMap.put("identity_code", identity_code);
			paramMap.put("user_name", user_name);
			paramMap.put("user_pass", user_pass);
			paramMap.put("userOnlyId", userOnlyId);
			paramMap.put("tranzCode", UhjConstant.transCode.TONGDUN_FIRSTACQUIRE);
			log.info("TongDunController firstAcquire param:"+paramMap.toString());
			String acquireResult = HttpClientUtil.sendPost(appkey_url, paramMap);
			log.info("TongDunController firstAcquire result:"+acquireResult);
			resultMap = getMapFromJsonString(acquireResult);
			
			if("100".equals(Convert.toStr(resultMap.get("code")))){
				String bodyStr = Convert.toStr(resultMap.get("bodyStr"));
				log.info("firstAcquire bodyStr:"+bodyStr);
				if(StringUtils.isNotBlank(bodyStr)){
					paramMap.clear();
					paramMap = getMapbySring(bodyStr);
					paramMap.put("sms_code", "null");
					paramMap.put("userOnlyId", userOnlyId);
					paramMap.put("tranzCode", UhjConstant.transCode.TONGDUN_ACQUIRE);
					log.info("TongDunController firstAcquire 15miao param:"+paramMap.toString());
					Thread.sleep(20000);
					acquireResult = HttpClientUtil.sendPost(appkey_url, paramMap);
					log.info("TongDunController firstAcquire 15miao result:"+acquireResult);
					resultMap = getMapFromJsonString(acquireResult);
					
					if("100".equals(Convert.toStr(resultMap.get("code")))){
						log.info("TongDunController firstAcquire 30miao param:"+paramMap.toString());
						Thread.sleep(15000);
						acquireResult = HttpClientUtil.sendPost(appkey_url, paramMap);
						log.info("TongDunController firstAcquire 30miao result:"+acquireResult);
						resultMap = getMapFromJsonString(acquireResult);
						if("100".equals(Convert.toStr(resultMap.get("code")))){
							log.info("TongDunController firstAcquire 30miao param:"+paramMap.toString());
							Thread.sleep(15000);
							acquireResult = HttpClientUtil.sendPost(appkey_url, paramMap);
							log.info("TongDunController firstAcquire 30miao result:"+acquireResult);
							resultMap = getMapFromJsonString(acquireResult);
							
							if("100".equals(Convert.toStr(resultMap.get("code")))){
								log.info("TongDunController firstAcquire 45miao param:"+paramMap.toString());
								Thread.sleep(15000);
								acquireResult = HttpClientUtil.sendPost(appkey_url, paramMap);
								log.info("TongDunController firstAcquire 45miao result:"+acquireResult);
								resultMap = getMapFromJsonString(acquireResult);
								
							}
						}
					}
				}
			}
			resultMap.put("msg", resultMap.get("message"));
			if("100".equals(Convert.toStr(resultMap.get("code")))){
				resultMap.put("msg", "网络异常，请稍后退出再试");
			}

			if("137".equals( Convert.toStr(resultMap.get("code"))) || "2007".equals(Convert.toStr(resultMap.get("code")))){
				resultMap.put("code", "0000");
				log.info("-------------保存同盾运营商数据--------------userOnlyId："+userOnlyId);
				
				CreditApplyExample applyExample = new CreditApplyExample();
				applyExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
				List<CreditApply> applys = creditApplyMapper.selectByExample(applyExample);
				String applyId="";
				if(applys!=null&&applys.size()>0){
					applyId = applys.get(0).getId();
				}
				
				InterfaceAccessInfoWithBLOBs interfaceAccess = new InterfaceAccessInfoWithBLOBs();
				interfaceAccess.setUserOnlyId(userOnlyId);
				interfaceAccess.setAppId(applyId);
				interfaceAccess.setInterfaceType(UhjConstant.transCode.TONGDUN_QUERY);
				interfaceAccess.setRequestInfo(JSONObject.fromObject(resultMap).toString().getBytes());
				interfaceAccess.setResponseInfo(null);
				interfaceAccess.setCreateTime(DateUtil.currTimeStr());
				interfaceAccess.setUpdateTime(DateUtil.currTimeStr());
				interfaceAccess.setStatus(UhjConstant.interfaceStutas.holdon);
				interfaceAccessInfoService.saveInterfaceData(interfaceAccess);
				
				//修改申请表状态为 待审核
				if(applys!=null&&applys.size()>0){
					CreditApply apply = applys.get(0);
					apply.setStatus(UhjConstant.applyStatus.APPLY_STATUS_NORMAL);
					apply.setUpdateTime(DateUtil.currTimeStr());
					creditApplyMapper.updateByExampleSelective(apply, applyExample);
				}
			}

		}catch(Exception e){
			log.error("TongDunController firstAcquire error!",e);
			resultMap.put("code", "9999");
			resultMap.put("msg", "网络异常，请稍后再试");
			
		}
		log.info("TongDunController firstAcquire resultMap:"+resultMap.toString());
		log.info("------------------------------tongdunAuth/firstAcquire.do 结束-----------------------------------");
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
	}
	




	/**
	 * 同盾验证
	 * http://money.beta.ule.com:8080/lendvps/tongdunAuth/acquire.do?jsoncallback=321321&task_id=&user_name=18201857659&user_pass=&sms_code=&task_stage=
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/acquire")
	@ResponseBody
	public JSONPObject acquire(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try{
			log.info("------------------------------tongdunAuth/acquire.do 开始-----------------------------------");
			String userOnlyId = getUserOnlyId(request);
			
			String task_id	=	Convert.toStr(request.getParameter("task_id"));
			String user_name	=	Convert.toStr(request.getParameter("user_name"));
			String user_pass	=	Convert.toStr(request.getParameter("user_pass"));
			String sms_code	=	Convert.toStr(request.getParameter("sms_code"));		
			String task_stage	=	Convert.toStr(request.getParameter("task_stage"));
			String request_type	=	Convert.toStr(request.getParameter("request_type"));
			
			if(StringUtils.isBlank(request_type)){
				request_type ="submit";
			}
			
			Map<String, String> paramMap =new HashMap<String, String>();
			paramMap.put("task_id", task_id);
			paramMap.put("user_name", user_name);
			paramMap.put("user_pass", user_pass);
			paramMap.put("sms_code", sms_code);
			paramMap.put("task_stage", task_stage);
			paramMap.put("request_type", request_type);
			paramMap.put("tranzCode", UhjConstant.transCode.TONGDUN_ACQUIRE);
			log.info("TongDunController acquire param:"+paramMap.toString());
			String acquireResult = HttpClientUtil.sendPost(appkey_url, paramMap);
			log.info("TongDunController acquire result:"+acquireResult);
			resultMap = getMapFromJsonString(acquireResult);
			resultMap.put("msg", resultMap.get("message"));
			
			if("137".equals( Convert.toStr(resultMap.get("code"))) || "2007".equals(Convert.toStr(resultMap.get("code")))){
				resultMap.put("code", "0000");
				log.info("-------------保存同盾运营商数据--------------userOnlyId："+userOnlyId);
				
				CreditApplyExample applyExample = new CreditApplyExample();
				applyExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
				List<CreditApply> applys = creditApplyMapper.selectByExample(applyExample);
				String applyId="";
				if(applys!=null&&applys.size()>0){
					applyId = applys.get(0).getId();
				}
				
				InterfaceAccessInfoWithBLOBs interfaceAccess = new InterfaceAccessInfoWithBLOBs();
				interfaceAccess.setUserOnlyId(userOnlyId);
				interfaceAccess.setAppId(applyId);
				interfaceAccess.setInterfaceType(UhjConstant.transCode.TONGDUN_QUERY);
				interfaceAccess.setRequestInfo(JSONObject.fromObject(paramMap).toString().getBytes());
				interfaceAccess.setResponseInfo(null);
				interfaceAccess.setCreateTime(DateUtil.currTimeStr());
				interfaceAccess.setUpdateTime(DateUtil.currTimeStr());
				interfaceAccess.setStatus(UhjConstant.interfaceStutas.holdon);
				interfaceAccessInfoService.saveInterfaceData(interfaceAccess);
				
				//修改申请表状态为 待审核
				if(applys!=null&&applys.size()>0){
					CreditApply apply = applys.get(0);
					apply.setStatus(UhjConstant.applyStatus.APPLY_STATUS_NORMAL);
					apply.setUpdateTime(DateUtil.currTimeStr());
					creditApplyMapper.updateByExampleSelective(apply, applyExample);
				}
			}

		}catch(Exception e){
			log.error("acquire error!",e);
			resultMap.put("code", "9999");
			resultMap.put("msg", "网络异常，请稍后再试");
			
		}
		log.info("TongDunController acquire resultMap:"+resultMap.toString());
		log.info("------------------------------tongdunAuth/acquire.do 结束-----------------------------------");
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
	}
	

	 private Map getMapFromJsonString(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();

		Map valueMap = new HashMap();
		while (keyIter.hasNext()) {
			String key = (String) keyIter.next();
			Object value = jsonObject.get(key);
			valueMap.put(key, value);
		}
		return valueMap;
	}
	 
	 
	 private static Map getMapFromJsonString1(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();

		Map valueMap = new HashMap();
		while (keyIter.hasNext()) {
			String key = (String) keyIter.next();
			Object value = jsonObject.get(key);
			valueMap.put(key, value);
		}
		return valueMap;
	}
	 
	 private static Map<String, String> getMapbySring1(String bodyStr) {
			//task_id=TASKYYS100000201801111628320711030561&user_name=18156899322&user_pass=844381&task_stage=INIT&request_type=query
			
			Map<String, String> map =new HashMap<String, String>();
			
			String[] bodyArr = bodyStr.split("&");

			for (String a : bodyArr) {
				String [] arr=a.split("=");
				map.put(arr[0], arr[1]);
	        }
			return map;
		}
	 
	 private JSONObject getJSONObjectFromJosn(JSONObject json,String key){
			JSONObject value=null;
			if(null!=json&&json.containsKey(key)){
				if(json.getJSONObject(key)!=null&&!json.getJSONObject(key).isEmpty()){
					
					value=json.getJSONObject(key);
				}
			}
			return value;
		}
	 
		private Map<String, String> getMapbySring(String bodyStr) {
			//task_id=TASKYYS100000201801111628320711030561&user_name=18156899322&user_pass=844381&task_stage=INIT&request_type=query
			
			Map<String, String> map =new HashMap<String, String>();
			
			String[] bodyArr = bodyStr.split("&");

			for (String a : bodyArr) {
				String [] arr=a.split("=");
				map.put(arr[0], arr[1]);
	        }
			return map;
		}
}
