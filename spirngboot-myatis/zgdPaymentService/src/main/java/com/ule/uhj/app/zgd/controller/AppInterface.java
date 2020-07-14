package com.ule.uhj.app.zgd.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.zgd.service.ApplyImageService;
import com.ule.uhj.app.zgd.service.BaiDuFaceService;
import com.ule.uhj.app.zgd.service.BindCardService;
import com.ule.uhj.app.zgd.service.CreditApplyService;
import com.ule.uhj.app.zgd.service.CreditRuleService;
import com.ule.uhj.app.zgd.service.CustomerInfoService;
import com.ule.uhj.app.zgd.service.UserInfoService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.app.zgd.util.VpsInfoService;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.RequestJsonUtil;
import com.ule.uhj.util.SecureUtil;
import com.ule.uhj.util.StringUtil;

/**
 * @author zhaojie
 *
 *
 *给手机组后台和其他项目组调用的接口，与给前端WD的不同
 *
 */
@Controller
@RequestMapping("/interface")
public class AppInterface {

	private static Logger log = LoggerFactory.getLogger(AppInterface.class);
	private static final String MOBILE_KEY = "uhjSignKey_mobile";
	
	@Autowired
	private CreditApplyService cerditApplyService;
	@Autowired
	private ApplyImageService applyImageService;
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private CreditRuleService creditRuleService;
	@Autowired
	private BindCardService bindCardService;
	@Autowired
	private BaiDuFaceService baiDuFaceService;
	@Autowired
	private UserInfoService userInfoService;
	
	
	/**
	 * 
	 * 邮掌柜是否显示掌柜贷按钮
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/common_queryShowInfo")
	@ResponseBody
	public String queryShowInfo(HttpServletRequest request,HttpServletResponse response){
//		String env = PropertiesHelper.getDfs("env");
//		if("beta".equals(env)){
//			 return JsonResult.getInstance().add("returnCode","0000").add("returnMessage","success").add("showFlag", "1").toString();
//		}
		
		log.info("queryShowInfo begin.");
		try {
			Map<String,Object> paras = new HashMap<String, Object>();
			if(request.getContentType()!=null && request.getContentType().contains("form-urlencoded")){
				paras.put("userOnlyId", request.getParameter("userOnlyId"));
			}else{
				paras = RequestJsonUtil.getRequestMap(request);
				if(paras == null || paras.size()<=0){
					throw new Exception("json数据未接收!");
				}
			}
			String userOnlyId  =Convert.toStr(paras.get("userOnlyId"));
			log.info("queryShowInfo userOnlyId "+userOnlyId);
			
			String result= VpsInfoService.queryShowInfo(userOnlyId);
			log.info("queryShowInfo userOnlyId "+userOnlyId +":result:"+result);
			return result;
		} catch (Exception e) {
			log.error("queryShowInfo error!",e);
		}
		return JsonResult.getInstance().add("returnCode","1000").add("returnMessage","网络异常,请稍后再试").add("showFlag", "0").toString();
	}
	
	/**
	 * 
	 * 亿图识别用户提交的身份证信息(正反面)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/common_ocrPersonIdentifyImage")
	@ResponseBody
	public String ocrPersonIdentifyImage(HttpServletRequest request,HttpServletResponse response){
		log.info("ocrPersonIdentifyImage begin.");
		try {
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			
			Map<String,Object> paras = new HashMap<String, Object>();
			if(request.getContentType().contains("form-urlencoded")){
				paras.put("tranzCode", request.getParameter("tranzCode"));
				paras.put("imageContent", request.getParameter("imageContent"));
				paras.put("type", request.getParameter("type"));
				paras.put("userOnlyId", request.getParameter("userOnlyId"));
			}else{
				paras = RequestJsonUtil.getRequestMap(request);
				if(paras == null || paras.size()<=0){
					throw new Exception("json数据未接收!");
				}
			}
			
			String tranzCode  =Convert.toStr(paras.get("tranzCode"));
			String imageContent =Convert.toStr(paras.get("imageContent"));
			String type  =Convert.toStr(paras.get("type"));
			String userOnlyId  =Convert.toStr(paras.get("userOnlyId"));
			log.info("ocrPersonIdentifyImage userOnlyId "+userOnlyId+";type "+type);
			//校验签证
			if(!verify(paras,request.getParameter("sign"))){
				return JsonResult.getInstance().add("returnCode","1001").add("returnMessage","签证校验失败!").toString();
			}

			Map<String, String> headers = new HashMap<String, String>();
			Map<String, String> params = new HashMap<String, String>();
			params.put("tranzCode", "4100");
			params.put("base64String", imageContent);
			params.put("userOnlyId", userOnlyId);
			params.put("type", type);
			String	imgUrl = com.ule.uhj.util.http.HttpClientUtil.sendPost(
					appkey_url, headers, params, UhjConstant.time_out);
			JSONObject js=JSONObject.fromObject(imgUrl);
			if("success".equals(js.get("status"))){
				//根据type保存图片地址
				paras.clear();
				paras.put("type", type);
				paras.put("url", js.get("url"));
				paras.put("userOnlyId", userOnlyId);
				applyImageService.saveApplyImage(paras);
				
				params.clear();
				params.put("tranzCode", tranzCode);
				params.put("imageContent", imageContent);
				params.put("userOnlyId", userOnlyId);
				String	res = com.ule.uhj.util.http.HttpClientUtil.sendPost(
							appkey_url, headers, params, UhjConstant.time_out);
				JSONObject ob =JSONObject.fromObject(res);
				JSONObject idcard_ocr_result=JSONObject.fromObject(ob.get("idcard_ocr_result"));
				if("-1".equals(Convert.toStr(idcard_ocr_result.get("idcard_type")))){
					return JsonResult.getInstance().add("returnCode","1000").add("returnMessage","身份证信息解析失败，请重新拍照").toString();
				}
				String result= JsonResult.getInstance().add("returnCode","0000").add("returnMessage","success").add("data",ob).add("url",js.get("url")).toString();
				log.info("ocrPersonIdentifyImage userOnlyId "+userOnlyId+"; result:"+result);
				return result;
			}else{
				return JsonResult.getInstance().add("returnCode","1000").add("returnMessage","身份证信息解析失败，请重新拍照").toString();
			}
		} catch (Exception e) {
			log.error("commonInterFace error!",e);
		}
		return JsonResult.getInstance().add("returnCode","1000").add("returnMessage","网络异常,请稍后再试").toString();
	}
	
	
	/**
	 * 新接口
	 * 掌柜本人人脸识别接口       配偶授权接口
	 * 取本人身份证照片或者配偶身份证照片
	 * 调用亿图接口两张照片（人脸和身份证照）相似度比较
	 * flag 1 本人    2  配偶
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/common_personIdentify")
	@ResponseBody
	public String personIdentify(HttpServletRequest request,HttpServletResponse response){
		log.info("personIdentify begin.");
		String result=null;
		try {
			Map<String, String> headers = new HashMap<String, String>();
			Map<String, String> params = new HashMap<String, String>();
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			Map<String,Object> paras = new HashMap<String, Object>();
			if(request.getContentType().contains("form-urlencoded")){
				paras.put("tranzCode", request.getParameter("tranzCode"));
				paras.put("faceImageContent", request.getParameter("faceImageContent"));
				paras.put("userOnlyId", request.getParameter("userOnlyId"));
				paras.put("flag", request.getParameter("flag"));
			}else{
				paras = RequestJsonUtil.getRequestMap(request);
				if(paras == null || paras.size()<=0){
					throw new Exception("json数据未接收!");
				}
			}

			//校验签证
			if(!verify(paras,request.getParameter("sign"))){
				return JsonResult.getInstance().add("returnCode","1001").add("returnMessage","签证校验失败!").toString();
			}

//			String tranzCode  =Convert.toStr(paras.get("tranzCode"));
			String faceImageContent =Convert.toStr(paras.get("faceImageContent"));
			String userOnlyId  =Convert.toStr(paras.get("userOnlyId"));
			String flag  =Convert.toStr(paras.get("flag"));
			String type="";//上传照片的类型  刷脸照
			String imageType="";//上传照片的类型  身份证水印照
			String name="";
			String certNo="";
			log.info("personIdentify userOnlyId "+userOnlyId+";flag:"+flag);
			if("1".equals(flag)){//本人
				type=UhjConstant.imageType.app_selfFace;//本人刷脸照
				imageType=UhjConstant.imageType.app_IdCardPositive;//本人身份证照
				paras.clear();
				paras.put("userOnlyId", userOnlyId);
				paras.put("certType", UhjConstant.certType.idcard);
				paras=customerInfoService.queryCustomerInfo(paras);//查询掌柜姓名及证件类型下的号码
				name=Convert.toStr(paras.get("name"));
				certNo=Convert.toStr(paras.get("certNo"));
			}else if("2".equals(flag)){//配偶
				type=UhjConstant.imageType.app_spouseFace;//配偶刷脸照
				imageType=UhjConstant.imageType.app_spouseICPositive;//配偶身份证照
				paras.clear();
				paras.put("userOnlyId", userOnlyId);
				paras.put("contactType", UhjConstant.contactType.spouse);
				paras=customerInfoService.queryCustomerSpouseInfo(paras);//根据联系人类型查询掌柜联系人姓名及证件号码
				name=Convert.toStr(paras.get("name"));
				certNo=Convert.toStr(paras.get("certNo"));
			}
			paras.clear();
			paras.put("userOnlyId", userOnlyId);
			paras.put("imageType", imageType);
			log.info("personIdentify paras:"+paras);
			String idCardImgUrl=applyImageService.queryApplyImageService(paras);
			if(idCardImgUrl ==null){
				result=JsonResult.getInstance().add("returnCode","9999").add("returnMessage","刷脸未通过，返回首页重新申请").toString();
				log.info("personIdentify  userOnlyId "+userOnlyId+"; result:"+result);
				return result;
			}
			log.info("personIdentify  userOnlyId "+userOnlyId+"; idCardImgUrl:"+idCardImgUrl);
			
			//调用亿图接口亿图姓名、身份证号与人脸大礼包相似度比较
			Map<String, String> map = new HashMap<String, String>();
			map.put("tranzCode", "3100");
			map.put("query_image_package", faceImageContent);
//			map.put("idCardImgUrl", idCardImgUrl);
			map.put("name", name);
			map.put("citizen_id", certNo);
			map.put("userOnlyId", userOnlyId);
			String res = com.ule.uhj.util.http.HttpClientUtil.sendPost(
					appkey_url, headers, map, UhjConstant.time_out);
//			log.info("personIdentify res:"+res);
			//处理相识度结果
			JSONObject data =JSONObject.fromObject(res);
			JSONObject verify_result =JSONObject.fromObject(data.get("verify_result"));
			String rtn_verify=Convert.toStr(verify_result.get("rtn"));
			if(!"0".equals(rtn_verify)){
				result=JsonResult.getInstance().add("returnCode","9999").add("returnMessage","刷脸未通过,比对结果异常").toString();
				log.info("personIdentify  result:"+result);
				if(verify_result.get("is_pass")==null)
					return result;
			}
			BigDecimal final_verify_score=Convert.toBigDecimal(verify_result.get("final_verify_score"),BigDecimal.ZERO);
			boolean is_pass=(Boolean) verify_result.get("is_pass");
			
			JSONObject query_image_package_result =JSONObject.fromObject(data.get("query_image_package_result"));
			String rtn_query=Convert.toStr(query_image_package_result.get("rtn"));
			if(!"0".equals(rtn_query)){
				result=JsonResult.getInstance().add("returnCode","9999").add("returnMessage","刷脸未通过,大礼包解析结果异常").toString();
				log.info("personIdentify  userOnlyId "+userOnlyId+";result:"+result);
				return result;
			}
			
			String array= query_image_package_result.getString("query_image_contents"); //获取list的值 

		    JSONArray jsonArray = JSONArray.fromObject(array); //list的值转为json数组对象 

		    Object[] strs = jsonArray.toArray(); //json转为数组 

			String faceImageStr=(String) strs[0];
			//上传文件服务器  取返回的第一张照片作为人脸照上传
			params.clear();
			params.put("tranzCode", "4100");
			params.put("base64String", faceImageStr);
			params.put("userOnlyId", userOnlyId);
			params.put("type", type);
			String	imgUrl = com.ule.uhj.util.http.HttpClientUtil.sendPost(
					appkey_url, headers, params, UhjConstant.time_out);
			JSONObject js=JSONObject.fromObject(imgUrl);
			if(!"success".equals(js.get("status"))){
				throw new Exception("人脸识别图片上传出错！");
			}
			
			JSONObject identify_result =JSONObject.fromObject(data.get("identify_result"));
			String rtn_identify=Convert.toStr(identify_result.get("rtn"));
			if(("6300".equals(rtn_identify) || "6301".equals(rtn_identify) ) && !"0".equals(rtn_identify)){//身份证号和姓名不一致
				if("1".equals(flag)){
					paras.clear();
					paras.put("userOnlyId", userOnlyId);
					paras.put("ruleRefId", UhjConstant.ruleRefId.certno_name_matches);
					paras.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(paras);
				}
				//调用百度一比一对比接口 判断是否是同一个人
				BigDecimal score=baiduCheckAPPFace(idCardImgUrl,Convert.toStr(js.get("url")),userOnlyId,name,certNo);
				log.info("personIdentify baiduCheckAPPFace userOnlyId "+userOnlyId+"; score:"+score);
				if(score!=null && score.compareTo(new BigDecimal("75"))>=0){
					final_verify_score=score;
					is_pass=true;
				}else{
					result=JsonResult.getInstance().add("returnCode","1000").add("returnMessage","刷脸认证未通过，您的身份信息有误").toString();
					log.info("personIdentify  userOnlyId "+userOnlyId+"; result:"+result);
					return result;
				}
			}
			
			
			
			//根据type 保存地址   
			paras.clear();
			paras.put("type", type);
			paras.put("url", js.get("url"));
			paras.put("userOnlyId", userOnlyId);
			applyImageService.saveApplyImage(paras);
			
			if(is_pass && final_verify_score.compareTo(new BigDecimal("75"))>=0){
				if("1".equals(flag)){//本人
					paras.clear();
					paras.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
					// 人脸比对结果  ：通过/不通过
					paras.put("ruleRefId", UhjConstant.ruleRefId.face_compare);
					paras.put("userOnlyId", userOnlyId);
					creditRuleService.saveCreditRuleService(paras);
					//公安部登记照片与活体检测截图相似度 例如75.00分
					paras.clear();
					paras.put("userOnlyId", userOnlyId);
					paras.put("ruleRefId", UhjConstant.ruleRefId.photos_living_similarity);
					paras.put("ruleOutput", Convert.toStr(final_verify_score));
					creditRuleService.saveCreditRuleService(paras);
					
					//更新到百度人脸库[店铺老板人脸]
					baiDuFaceService.addFace("uleface", userOnlyId, faceImageStr);
					
				}else{
					//配偶公安部登记照片与活体检测截图相似度 例如75.00分
					paras.clear();
					paras.put("userOnlyId", userOnlyId);
					paras.put("ruleRefId", UhjConstant.ruleRefId.spouse_photos_living_similarity);
					paras.put("ruleOutput", Convert.toStr(final_verify_score));
					creditRuleService.saveCreditRuleService(paras);
				}
				result=JsonResult.getInstance().add("returnCode","0000").add("returnMessage","Success").toString();
			}else{
				if("1".equals(flag)){//本人
					paras.clear();
					paras.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					// 人脸比对结果  ：通过/不通过
					paras.put("ruleRefId", UhjConstant.ruleRefId.face_compare);
					paras.put("userOnlyId", userOnlyId);
					String returnFlag=creditRuleService.saveCreditRuleService(paras);
					//公安部登记照片与活体检测截图相似度 例如75.00分
					paras.clear();
					paras.put("userOnlyId", userOnlyId);
					paras.put("ruleRefId", UhjConstant.ruleRefId.photos_living_similarity);
					paras.put("ruleOutput", Convert.toStr(final_verify_score));
					creditRuleService.saveCreditRuleService(paras);
					if("1".equals(returnFlag)){
						result=JsonResult.getInstance().add("returnCode","0000").add("returnMessage","Success").toString();
						
						//更新到百度人脸库[店铺老板人脸]
						baiDuFaceService.addFace("uleface", userOnlyId, faceImageStr);
						
					}else{
						result=JsonResult.getInstance().add("returnCode","2000").add("returnMessage","刷脸未通过，请确认是本人申请").toString();
					}
				}else{
					//配偶公安部登记照片与活体检测截图相似度 例如75.00分
					paras.clear();
					paras.put("userOnlyId", userOnlyId);
					paras.put("ruleRefId", UhjConstant.ruleRefId.spouse_photos_living_similarity);
					paras.put("ruleOutput", Convert.toStr(final_verify_score));
					creditRuleService.saveCreditRuleService(paras);
					result=JsonResult.getInstance().add("returnCode","2000").add("returnMessage","刷脸未通过，请确认是本人申请").toString();
				}
			}
			//配偶授权放在后面的上传证件照那里了
			JSONObject object=JSONObject.fromObject(result);
			if("0000".equals(object.get("returnCode"))){
				if("1".equals(flag)){//本人
					//保存掌柜整个人脸识别过程结束
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.face_recognition);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
					creditRuleService.saveCreditRuleService(ruleMap);
				}else{
//					//由于安卓和ios版本不一致  现加如下代码进行控制
//					String appVersionNo = Convert.toStr(request.getParameter("appVersionNo"));
//					if(appVersionNo==null||"".equals(appVersionNo)||appVersionNo.compareTo("168")<0){
//						//保存配偶授权过程结束
//						Map<String,Object> ruleMap = new HashMap<String, Object>();
//						ruleMap.put("userOnlyId", userOnlyId);
//						ruleMap.put("ruleRefId", UhjConstant.ruleRefId.spouse_authorization);
//						ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
//						creditRuleService.saveCreditRuleService(ruleMap);
//					}
				}
			}else{
				if("1".equals(flag)){//本人
					//保存掌柜整个人脸识别过程结束
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.face_recognition);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(ruleMap);
				}else{
					//保存配偶授权过程结束
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.spouse_authorization);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(ruleMap);
				}
			}
		} catch (Exception e) {
			log.error("personIdentify error!",e);
			result=JsonResult.getInstance().add("returnCode","9999").add("returnMessage","网络异常,请稍后再试").toString();
		}
		log.info("personIdentify result "+result);
		return result; 
	}
	//调用阿里身份证实名认证接口
	private String checkIdCardNumber(String idCardImgUrl,String name,String certNo,String userOnlyId){
		try{
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			Map<String, String> headers = new HashMap<String, String>();
			Map<String, String> paras = new HashMap<String, String>();
			paras.put("tranzCode", "1104");
			paras.put("idCardImgUrl", idCardImgUrl);
			paras.put("name", name);
			paras.put("citizen_id", certNo);
			paras.put("userOnlyId", userOnlyId);
			log.info("checkIdCardNumber 阿里身份证实名认证接口日志 paras:"+paras);
			String ret = com.ule.uhj.util.http.HttpClientUtil.sendPost(
					appkey_url, headers, paras, UhjConstant.time_out);
			log.info("checkIdCardNumber 阿里身份证实名认证依图接口日志 ret:"+ret);
			JSONObject ject=JSONObject.fromObject(ret);
			String status=Convert.toStr(ject.get("status"));
			return status;
		} catch (Exception e) {
			log.error("checkIdCardNumber error",e);
		}
		return "02";
	}
	//先调用阿里身份证实名认证接口,再调用百度一比一对比
	private BigDecimal baiduCheckAPPFace(String idCardImgUrl,String faceImgUrl,String userOnlyId,String name,String certNo){
		try {
			log.info("baiduCheckAPPFace userOnlyId:"+userOnlyId+" idCardImgUrl:"+idCardImgUrl+";faceImgUrl:"+faceImgUrl);
			String status=checkIdCardNumber(idCardImgUrl, name, certNo, userOnlyId);
			log.info("baiduCheckAPPFace userOnlyId "+userOnlyId+" status:"+status);
			if(!"01".equals(status)){
				return BigDecimal.ZERO;
			}
			Map<String, Object> map=baiDuFaceService.match(faceImgUrl, idCardImgUrl,"7,13");
			String data=Convert.toStr(map.get("data"));
			log.info("baiduCheckAPPFace userOnlyId "+userOnlyId+" data:"+data);
			if(data!=null){
				JSONObject js=JSONObject.fromObject(data);
				int result_num=Convert.toInt(js.get("result_num"),0);
				if(result_num>0){
					JSONArray ja=js.getJSONArray("result");
					JSONObject jsScore=ja.getJSONObject(0);
					BigDecimal score=Convert.toBigDecimal(jsScore.get("score"));
					return score;
				}
			}
		} catch (Exception e) {
			log.error("baiduCheckAPPFace userOnlyId "+userOnlyId+" error",e);
		}
		return BigDecimal.ZERO;
		
	}
	
/*	
	*//**
	 * 掌柜本人人脸识别接口       配偶授权接口
	 * 取本人身份证照片或者配偶身份证照片
	 * 调用亿图接口两张照片（人脸和身份证照）相似度比较
	 * flag 1 本人    2  配偶
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequestMapping("/common_personIdentify")
	@ResponseBody
	public String personIdentify(HttpServletRequest request,HttpServletResponse response){
		log.info("personIdentify begin.");
		String result=null;
		try {
			Map<String, String> headers = new HashMap<String, String>();
			Map<String, String> params = new HashMap<String, String>();
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			Map<String,Object> paras = new HashMap<String, Object>();
			if(request.getContentType().contains("form-urlencoded")){
				paras.put("tranzCode", request.getParameter("tranzCode"));
				paras.put("faceImageContent", request.getParameter("faceImageContent"));
				paras.put("userOnlyId", request.getParameter("userOnlyId"));
				paras.put("flag", request.getParameter("flag"));
			}else{
				paras = RequestJsonUtil.getRequestMap(request);
				if(paras == null || paras.size()<=0){
					throw new Exception("json数据未接收!");
				}
			}

			//校验签证
			if(!verify(paras,request.getParameter("sign"))){
				return JsonResult.getInstance().add("returnCode","1001").add("returnMessage","签证校验失败!").toString();
			}

//			String tranzCode  =Convert.toStr(paras.get("tranzCode"));
			String faceImageContent =Convert.toStr(paras.get("faceImageContent"));
			String userOnlyId  =Convert.toStr(paras.get("userOnlyId"));
			String flag  =Convert.toStr(paras.get("flag"));
			String type="";//上传照片的类型  刷脸照
			String imageType="";//上传照片的类型  身份证水印照
			String idCardImgUrl="";
			log.info("personIdentify userOnlyId "+userOnlyId);
			if("1".equals(flag)){//本人
				type=UhjConstant.imageType.app_selfFace;//本人刷脸照
				imageType=UhjConstant.imageType.app_IdCardPositive;//本人身份证照
			}else if("2".equals(flag)){//配偶
				type=UhjConstant.imageType.app_spouseFace;//配偶刷脸照
				imageType=UhjConstant.imageType.app_spouseICPositive;//配偶身份证照
			}
			paras.clear();
			paras.put("userOnlyId", userOnlyId);
			paras.put("imageType", imageType);
			idCardImgUrl=applyImageService.queryApplyImageService(paras);
			if(idCardImgUrl ==null){
				result=JsonResult.getInstance().add("returnCode","9999").add("returnMessage","刷脸未通过，返回首页重新申请").toString();
				log.info("personIdentify  result:"+result);
				return result;
			}
			log.info("personIdentify  idCardImgUrl:"+idCardImgUrl);
			
			//调用亿图接口两张照片（人脸和身份证照）相似度比较
			Map<String, String> map = new HashMap<String, String>();
			map.put("tranzCode", "3102");
			map.put("queryImageContent", faceImageContent);
			map.put("databaseImageContent", idCardImgUrl);
			map.put("userOnlyId", userOnlyId);
			String res = com.ule.uhj.util.http.HttpClientUtil.sendPost(
					appkey_url, headers, map, UhjConstant.time_out);
//			log.info("personIdentify res:"+res);
			//处理相识度结果
			JSONObject ob=JSONObject.fromObject(res);
			if("0000".equals(ob.get("returnCode"))){
				JSONObject data =JSONObject.fromObject(ob.get("data"));
				JSONObject json =JSONObject.fromObject(data.get("query_image_package_result"));
				String array= json.getString("query_image_contents"); //获取list的值 

			    JSONArray jsonArray = JSONArray.fromObject(array); //吧list的值转为json数组对象 

			    Object[] strs = jsonArray.toArray(); //json转为数组 

				String faceImageStr=(String) strs[0];
				
				//上传文件服务器  取返回的第一张照片作为人脸照上传
				params.clear();
				params.put("tranzCode", "4100");
				params.put("base64String", faceImageStr);
				params.put("userOnlyId", userOnlyId);
				params.put("type", type);
				String	imgUrl = com.ule.uhj.util.http.HttpClientUtil.sendPost(
						appkey_url, headers, params, UhjConstant.time_out);
				JSONObject js=JSONObject.fromObject(imgUrl);
				if(!"success".equals(js.get("status"))){
					throw new Exception("人脸识别图片上传出错！");
				}
				
				//根据type 保存地址   
				paras.clear();
				paras.put("type", type);
				paras.put("url", js.get("url"));
				paras.put("userOnlyId", userOnlyId);
				applyImageService.saveApplyImage(paras);
				
				String pair_verify_result=Convert.toStr(data.get("pair_verify_result"));//识别结果是否是同一个人 0 是  1否
				BigDecimal percent=Convert.toBigDecimal(data.get("pair_verify_similarity"));
				if(!"0".equals(pair_verify_result)){
					paras.clear();
					paras.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					// 人脸比对结果  ：通过/不通过
					paras.put("ruleRefId", UhjConstant.ruleRefId.face_compare);
					paras.put("userOnlyId", userOnlyId);
					String returnFlag=creditRuleService.saveCreditRuleService(paras);
					//公安部登记照片与活体检测截图相似度 例如75.00分
					paras.clear();
					paras.put("userOnlyId", userOnlyId);
					paras.put("ruleRefId", UhjConstant.ruleRefId.photos_living_similarity);
					paras.put("ruleOutput", Convert.toStr(percent));
					creditRuleService.saveCreditRuleService(paras);
					if("1".equals(returnFlag)){
						result=JsonResult.getInstance().add("returnCode","0000").add("returnMessage","Success").toString();
					}else{
						result=JsonResult.getInstance().add("returnCode","2000").add("returnMessage","刷脸未通过，请确认是本人申请").toString();
					}
				}else{
					paras.clear();
					paras.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
					// 人脸比对结果  ：通过/不通过
					paras.put("ruleRefId", UhjConstant.ruleRefId.face_compare);
					paras.put("userOnlyId", userOnlyId);
					creditRuleService.saveCreditRuleService(paras);
					//公安部登记照片与活体检测截图相似度 例如75.00分
					paras.clear();
					paras.put("userOnlyId", userOnlyId);
					paras.put("ruleRefId", UhjConstant.ruleRefId.photos_living_similarity);
					paras.put("ruleOutput", Convert.toStr(percent));
					creditRuleService.saveCreditRuleService(paras);
					result=JsonResult.getInstance().add("returnCode","0000").add("returnMessage","Success").toString();
				}
			}else{
				result=JsonResult.getInstance().add("returnCode","9999").add("returnMessage","网络异常,稍后再试").toString();
			}
			
			JSONObject object=JSONObject.fromObject(result);
			if("0000".equals(object.get("returnCode"))){
				if("1".equals(flag)){//本人
					//保存整个人脸识别过程结束
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.face_recognition);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
					creditRuleService.saveCreditRuleService(ruleMap);
				}else{
					//保存配偶人脸识别过程结束
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.spouse_authorization);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
					creditRuleService.saveCreditRuleService(ruleMap);
				}
				
			}else{
				if("1".equals(flag)){//本人
					//保存整个人脸识别过程结束
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.face_recognition);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(ruleMap);
				}else{
					//保存配偶人脸识别过程结束
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.spouse_authorization);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(ruleMap);
				}
				
			}
		} catch (Exception e) {
			log.error("personIdentify error!",e);
			result=JsonResult.getInstance().add("returnCode","9999").add("returnMessage","网络异常,请稍后再试").toString();
		}
		log.info("personIdentify result "+result);
		return result; 
	}*/
	/**
	 * 
	 * 联网核查姓名和身份证是否匹配, 
	 * 匹配成功时获取公安部联网水印照,调用亿图接口两张照片（人脸和公安部水印照）相似度比较
	 * flag 1 本人    2  配偶
	 * @param request
	 * @param response
	 * @return
	 */
	/*@RequestMapping("/common_personIdentify")
	@ResponseBody
	public String personIdentify(HttpServletRequest request,HttpServletResponse response){
		log.info("personIdentify begin.");
		String result=null;
		try {
			Map<String, String> headers = new HashMap<String, String>();
			Map<String, String> params = new HashMap<String, String>();
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			Map<String,Object> paras = new HashMap<String, Object>();
			if(request.getContentType().contains("form-urlencoded")){
				paras.put("tranzCode", request.getParameter("tranzCode"));
				paras.put("faceImageContent", request.getParameter("faceImageContent"));
				paras.put("userOnlyId", request.getParameter("userOnlyId"));
				paras.put("flag", request.getParameter("flag"));
			}else{
				paras = RequestJsonUtil.getRequestMap(request);
				if(paras == null || paras.size()<=0){
					throw new Exception("json数据未接收!");
				}
			}

			//校验签证
			if(!verify(paras,request.getParameter("sign"))){
				return JsonResult.getInstance().add("returnCode","1001").add("returnMessage","签证校验失败!").toString();
			}

			String tranzCode  =Convert.toStr(paras.get("tranzCode"));
			String faceImageContent =Convert.toStr(paras.get("faceImageContent"));
			String userOnlyId  =Convert.toStr(paras.get("userOnlyId"));
			String flag  =Convert.toStr(paras.get("flag"));
			String type="";//上传照片的类型  刷脸照
			String IdCardType="";//上传照片的类型  身份证水印照
			String name="";
			String certNo="";
			log.info("personIdentify userOnlyId "+userOnlyId);
			if("1".equals(flag)){//本人
				type="app_selfFace";//本人刷脸照
				IdCardType="app_selfICWater";//本人公安部水印照
				paras.clear();
				paras.put("userOnlyId", userOnlyId);
				paras.put("certType", UhjConstant.certType.idcard);
				paras=customerInfoService.queryCustomerInfo(paras);//查询掌柜姓名及证件类型下的号码
				name=Convert.toStr(paras.get("name"));
				certNo=Convert.toStr(paras.get("certNo"));
			}else if("2".equals(flag)){//配偶
				type="app_spouseFace";//配偶刷脸照
				IdCardType="app_spouseICWater";//配偶公安部水印照
				paras.clear();
				paras.put("userOnlyId", userOnlyId);
				paras.put("contactType", UhjConstant.contactType.spouse);
				paras=customerInfoService.queryCustomerSpouseInfo(paras);//根据联系人类型查询掌柜联系人姓名及证件号码
				name=Convert.toStr(paras.get("name"));
				certNo=Convert.toStr(paras.get("certNo"));
			}
			log.info("personIdentify  name:"+name);
			log.info("personIdentify   certNo:"+certNo);
			
			params.clear();
			params.put("tranzCode", tranzCode);
			params.put("name", name);
			params.put("citizen_id", certNo);
			params.put("remove_watermark", "true");
			params.put("userOnlyId", userOnlyId);
			String	res = com.ule.uhj.util.http.HttpClientUtil.sendPost(
						appkey_url, headers, params, UhjConstant.time_out);
			log.info("personIdentify 联网核查 res:"+res);
			Map<?, ?> responseJson = new ObjectMapper().readValue(res, Map.class);
			int rtn = (Integer) responseJson.get("rtn");
			String message = (String)responseJson.get("message");
			boolean identify_result = (Boolean)responseJson.get("identify_result");
			String origin_image_content = (String)responseJson.get("origin_image_content");
//			String processed_image_content = (String)responseJson.get("processed_image_content");
			if (rtn == 0) { //0表示OK，<0表示服务器发生错误。
				if(!identify_result){
					paras.clear();
					paras.put("userOnlyId", userOnlyId);
					paras.put("ruleRefId", UhjConstant.ruleRefId.certno_name_matches);
					paras.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(paras);
					result=JsonResult.getInstance().add("returnCode","1000").add("returnMessage","刷脸认证未通过，您的身份信息有误").toString();
//					log.info("personIdentify result "+result);
					return result;
				}else{
					paras.clear();
					paras.put("userOnlyId", userOnlyId);
					paras.put("ruleRefId", UhjConstant.ruleRefId.certno_name_matches);
					paras.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
					creditRuleService.saveCreditRuleService(paras);
				}
				
				params.clear();
				params.put("tranzCode", "4100");
				params.put("base64String", origin_image_content);
				params.put("userOnlyId", userOnlyId);
				params.put("type", IdCardType);
				String	idCardImgUrl = com.ule.uhj.util.http.HttpClientUtil.sendPost(
						appkey_url, headers, params, UhjConstant.time_out);
				JSONObject object=JSONObject.fromObject(idCardImgUrl);
				if(!"success".equals(object.get("status"))){
					throw new Exception("公安部水印图片上传出错！");
				}
				//根据type 保存地址   
				paras.clear();
				paras.put("type", IdCardType);
				paras.put("url", object.get("url"));
				paras.put("userOnlyId", userOnlyId);
				applyImageService.saveApplyImage(paras);
				
				//调用亿图接口两张照片（人脸和公安部水印照）相似度比较
				Map<String, String> map = new HashMap<String, String>();
				map.put("tranzCode", "3103");
				map.put("queryImageContent", faceImageContent);
				map.put("databaseImageContent", origin_image_content);
				map.put("userOnlyId", userOnlyId);
				res = com.ule.uhj.util.http.HttpClientUtil.sendPost(
						appkey_url, headers, map, UhjConstant.time_out);
				//处理相识度结果
				JSONObject ob=JSONObject.fromObject(res);
				if("0000".equals(ob.get("returnCode"))){
					JSONObject data =JSONObject.fromObject(ob.get("data"));
					JSONObject json =JSONObject.fromObject(data.get("query_image_package_result"));
					String array= json.getString("query_image_contents"); //获取list的值 

				    JSONArray jsonArray = JSONArray.fromObject(array); //吧list的值转为json数组对象 

				    Object[] strs = jsonArray.toArray(); //json转为数组 

					String faceImageStr=(String) strs[0];
					
					//上传文件服务器  取返回的第一张照片作为人脸照上传
					params.clear();
					params.put("tranzCode", "4100");
					params.put("base64String", faceImageStr);
					params.put("userOnlyId", userOnlyId);
					params.put("type", type);
					String	imgUrl = com.ule.uhj.util.http.HttpClientUtil.sendPost(
							appkey_url, headers, params, UhjConstant.time_out);
					JSONObject js=JSONObject.fromObject(imgUrl);
					if(!"success".equals(js.get("status"))){
						throw new Exception("人脸识别图片上传出错！");
					}
					
					//根据type 保存地址   
					paras.clear();
					paras.put("type", type);
					paras.put("url", js.get("url"));
					paras.put("userOnlyId", userOnlyId);
					applyImageService.saveApplyImage(paras);
					
					String pair_verify_result=Convert.toStr(data.get("pair_verify_result"));//识别结果是否是同一个人 0 是  1否
					BigDecimal percent=Convert.toBigDecimal(data.get("pair_verify_similarity"));
					if(!"0".equals(pair_verify_result)){
						paras.clear();
						paras.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
						// 人脸比对结果  ：通过/不通过
						paras.put("ruleRefId", UhjConstant.ruleRefId.face_compare);
						paras.put("userOnlyId", userOnlyId);
						String returnFlag=creditRuleService.saveCreditRuleService(paras);
						//公安部登记照片与活体检测截图相似度 例如75.00分
						paras.clear();
						paras.put("userOnlyId", userOnlyId);
						paras.put("ruleRefId", UhjConstant.ruleRefId.photos_living_similarity);
						paras.put("ruleOutput", Convert.toStr(percent));
						creditRuleService.saveCreditRuleService(paras);
						if("1".equals(returnFlag)){
							result=JsonResult.getInstance().add("returnCode","0000").add("returnMessage","Success").toString();
						}else{
							result=JsonResult.getInstance().add("returnCode","2000").add("returnMessage","刷脸未通过，请确认是本人申请").toString();
						}
					}else{
//						log.info("personIdentify调用亿图接口两张照片 res:"+res);
						paras.clear();
						paras.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
						// 人脸比对结果  ：通过/不通过
						paras.put("ruleRefId", UhjConstant.ruleRefId.face_compare);
						paras.put("userOnlyId", userOnlyId);
						creditRuleService.saveCreditRuleService(paras);
						//公安部登记照片与活体检测截图相似度 例如75.00分
						paras.clear();
						paras.put("userOnlyId", userOnlyId);
						paras.put("ruleRefId", UhjConstant.ruleRefId.photos_living_similarity);
						paras.put("ruleOutput", Convert.toStr(percent));
						creditRuleService.saveCreditRuleService(paras);
						result=JsonResult.getInstance().add("returnCode","0000").add("returnMessage","Success").toString();
					}
					// 人脸比对结果  ：通过/不通过
					paras.put("ruleRefId", UhjConstant.ruleRefId.face_compare);
					paras.put("userOnlyId", userOnlyId);
					creditRuleService.saveCreditRuleService(paras);
					//公安部登记照片与活体检测截图相似度 例如75.00分
					paras.clear();
					paras.put("userOnlyId", userOnlyId);
					paras.put("ruleRefId", UhjConstant.ruleRefId.photos_living_similarity);
					paras.put("ruleOutput", Convert.toStr(percent));
					String returnFlag=creditRuleService.saveCreditRuleService(paras);
					if("1".equals(returnFlag)){
						result=JsonResult.getInstance().addOk().toString();
					}
				}else{
					result=JsonResult.getInstance().add("returnCode","9999").add("returnMessage","网络异常,稍后再试").toString();
				}
			} else {
				result=JsonResult.getInstance().add("returnCode","1000").add("returnMessage","刷脸认证未通过，您的身份信息有误").toString();
			}
			
			JSONObject object=JSONObject.fromObject(result);
			if("0000".equals(object.get("returnCode"))){
				if("1".equals(flag)){//本人
					//保存整个人脸识别过程结束
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.face_recognition);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
					creditRuleService.saveCreditRuleService(ruleMap);
				}else{
					//保存配偶人脸识别过程结束
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.spouse_authorization);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
					creditRuleService.saveCreditRuleService(ruleMap);
				}
				
			}else{
				if("1".equals(flag)){//本人
					//保存整个人脸识别过程结束
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.face_recognition);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(ruleMap);
				}else{
					//保存配偶人脸识别过程结束
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.spouse_authorization);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(ruleMap);
				}
				
			}
		} catch (Exception e) {
			log.error("personIdentify error!",e);
			result=JsonResult.getInstance().add("returnCode","9999").add("returnMessage","网络异常,请稍后再试").toString();
		}
		log.info("personIdentify result "+result);
		return result; 
	}
	*/
	
	
	/**
	 * 
	 * 图片上传并根据类型保存地址
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/common_imageUpload")
	@ResponseBody
	public String imageUpload(HttpServletRequest request,HttpServletResponse response){
		log.info("imageUpload begin.");
		try {
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			
			Map<String,Object> paras = new HashMap<String, Object>();
			if(request.getContentType().contains("form-urlencoded")){
				paras.put("tranzCode", request.getParameter("tranzCode"));
				paras.put("base64String", request.getParameter("base64String"));
				paras.put("type", request.getParameter("type"));
				paras.put("userOnlyId", request.getParameter("userOnlyId"));
			}else{
				paras = RequestJsonUtil.getRequestMap(request);
				if(paras == null || paras.size()<=0){
					throw new Exception("json数据未接收!");
				}
			}
			String tranzCode  =Convert.toStr(paras.get("tranzCode"));
			String base64String =Convert.toStr(paras.get("base64String"));
			String type  =Convert.toStr(paras.get("type"));
			String userOnlyId  =Convert.toStr(paras.get("userOnlyId"));
			log.info("imageUpload userOnlyId "+userOnlyId+";tranzCode:"+tranzCode+";type:"+type);
			
			Map<String, String> headers = new HashMap<String, String>();
			Map<String, String> params = new HashMap<String, String>();
			params.put("tranzCode", tranzCode);
			params.put("base64String", base64String);
			params.put("userOnlyId", userOnlyId);
			params.put("type", type);
			String	imgUrl = com.ule.uhj.util.http.HttpClientUtil.sendPost(
					appkey_url, headers, params, UhjConstant.time_out);
			JSONObject js=JSONObject.fromObject(imgUrl);
			if("success".equals(js.get("status"))){
				//根据type 保存地址   
				paras.clear();
				paras.put("type", type);
				paras.put("url", js.get("url"));
				paras.put("userOnlyId", userOnlyId);
				applyImageService.saveApplyImage(paras);
				String result= JsonResult.getInstance().add("returnCode","0000").add("returnMessage","图片上传成功")
						.add("url", js.get("url")).toString();
				log.info("imageUpload  userOnlyId "+userOnlyId+";result:"+result);
				return result;
			}
		} catch (Exception e) {
			log.error("imageUpload error!",e);
		}
		return JsonResult.getInstance().add("returnCode","1000").add("returnMessage","网络异常,请稍后再试").toString();
	}
	/**
	 * 
	 * 绑卡信息保存
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/upload_smsContent")
	@ResponseBody
	public String uploadSmsContent(HttpServletRequest request,HttpServletResponse response){
		log.info("uploadSmsContent begin.");
		try {
			Map<String,Object> paras = new HashMap<String, Object>();
			if(request.getContentType().contains("form-urlencoded")){
				paras.put("imei", request.getParameter("imei"));//移动设备识别码（IMEI）
				paras.put("phoneModel", request.getParameter("phoneModel"));//手机型号
				paras.put("successFlag", request.getParameter("successFlag"));//是否成功抓取短信
				paras.put("sendPhoneNo", request.getParameter("sendPhoneNo"));//发送号码	
				paras.put("smsContent", request.getParameter("smsContent"));//短信内容
				paras.put("userOnlyId", request.getParameter("userOnlyId"));
			}else{
				paras = RequestJsonUtil.getRequestMap(request);
				if(paras == null || paras.size()<=0){
					throw new Exception("json数据未接收!");
				}
			}
			log.info("uploadSmsContent paras "+paras.toString());
			
//			String imei  =Convert.toStr(paras.get("imei"));
//			String phoneModel =Convert.toStr(paras.get("phoneModel"));
//			String successFlag  =Convert.toStr(paras.get("successFlag"));
//			String sendPhoneNo  =Convert.toStr(paras.get("sendPhoneNo"));
//			String smsContent  =Convert.toStr(paras.get("smsContent"));
			String userOnlyId  =Convert.toStr(paras.get("userOnlyId"));
			log.info("uploadSmsContent userOnlyId "+userOnlyId);
			//校验签证
			if(!verify(paras,request.getParameter("sign"))){
				return JsonResult.getInstance().add("returnCode","1000").add("returnMessage","签证校验失败!").toString();
			}
			bindCardService.saveSmsContent(paras);
			return JsonResult.getInstance().add("returnCode","0000").add("returnMessage","信息保存成功!").toString();
		} catch (Exception e) {
			log.error("uploadSmsContent error!",e);
		}
		return JsonResult.getInstance().add("returnCode","1000").add("returnMessage","网络异常,请稍后再试").toString();
	}

	
	/**
	 * 
	 * 保存屏幕截图
	 * type  引导页面  guide_page
	 * 		 运营商授权  operator_author_page
	 * 		 确认借款   confirm_loan_page
	 * 		 绑卡        bind_card_page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/saveScreenCapture")
	@ResponseBody
	public String saveScreenCapture(HttpServletRequest request,HttpServletResponse response){
		log.info("saveScreenCapture begin.");
		try {
			Map<String,Object> paras = new HashMap<String, Object>();
			paras.put("url", request.getParameter("url"));
			paras.put("type", request.getParameter("type"));
			paras.put("userOnlyId", request.getParameter("userOnlyId"));
			log.info("saveScreenCapture paras "+paras.toString());
			//校验签证
			if(!verify(paras,request.getParameter("sign"))){
				return JsonResult.getInstance().add("returnCode","1000").add("returnMessage","签证校验失败!").toString();
			}
			//根据type 保存地址   
			applyImageService.saveApplyImage(paras);
			String result= JsonResult.getInstance().add("returnCode","0000").add("returnMessage","截屏图片保存成功").toString();
			log.info("saveScreenCapture  userOnlyId "+ request.getParameter("userOnlyId")+";result:"+result);
			return result;
		} catch (Exception e) {
			log.error("saveScreenCapture error!",e);
		}
		return JsonResult.getInstance().add("returnCode","1000").add("returnMessage","网络异常,请稍后再试").toString();
	}
	/**
	 * 
	 * 查询订单的逾期记录
	 * village_no   机构号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryOverDueDay")
	@ResponseBody
	public String queryOverDueDay(HttpServletRequest request,HttpServletResponse response){
		log.info("queryOverDueDay begin.");
		try {
			Map<String,Object> paras = new HashMap<String, Object>();
			paras.put("village_no", request.getParameter("village_no"));
			log.info("queryOverDueDay paras "+paras.toString());
			//校验签证
			if(!verify(paras,request.getParameter("sign"))){
				return JsonResult.getInstance().add("returnCode","1000").add("returnMessage","签证校验失败!").toString();
			}
			//根据type 保存地址   
			Integer overDueDay= userInfoService.queryOverDueDay(paras);
			String result= JsonResult.getInstance().add("returnCode","0000").add("returnMessage","SUCCESS").add("overDueDay",overDueDay).toString();
			log.info("queryOverDueDay  userOnlyId "+ request.getParameter("userOnlyId")+";result:"+result);
			return result;
		} catch (Exception e) {
			log.error("queryOverDueDay error!",e);
		}
		return JsonResult.getInstance().add("returnCode","1000").add("returnMessage","网络异常,请稍后再试").toString();
	}
	/***
	 * 与手机组签证校验
	 * @param paramer
	 * @param sign
	 * @return
	 */
	private boolean verify(Map<String,Object>paramer,String sign){
		String key = PropertiesHelper.getSecurityKey(MOBILE_KEY);
		log.info("签证校验开始，sign="+sign+",key="+key);
		if(StringUtil.isEmpty(sign) || StringUtil.isEmpty(key)){
			log.info("签证校验失败，签证或key为空.");
			return false;
		}
		//防止签证字段被重复打包
		paramer.put("sign",null);
		return SecureUtil.verify(paramer,key,sign);
	}
}
