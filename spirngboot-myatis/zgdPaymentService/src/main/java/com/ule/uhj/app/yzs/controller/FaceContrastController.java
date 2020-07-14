package com.ule.uhj.app.yzs.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.yzs.service.YzsPostmemberService;
import com.ule.uhj.app.zgd.service.ApplyImageService;
import com.ule.uhj.app.zgd.service.BaiDuFaceService;
import com.ule.uhj.app.zgd.service.CreditRuleService;
import com.ule.uhj.app.zgd.service.CustomerInfoService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.util.Check;
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
 *给邮助手手机组后台调用的接口，与给前端WD的不同
 */
@Controller
@RequestMapping("/yzs")
public class FaceContrastController  {

	private static Logger log = LoggerFactory.getLogger(FaceContrastController.class);
	private static final String MOBILE_KEY = "uhjSignKey_mobile";
	
	@Autowired
	private ApplyImageService applyImageService;
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private CreditRuleService creditRuleService;
	@Autowired
	private YzsPostmemberService yzsPostmemberService;
	
	@Autowired
	private BaiDuFaceService baiDuFaceService;
	
	
	
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
	@RequestMapping("/yzs_personIdentify")
	@ResponseBody
	public String yzs_personIdentify(HttpServletRequest request,HttpServletResponse response){
		String result=null;
		try {
			log.info("yzs_personIdentify begin bangZGId:"+ request.getParameter("bangZGId"));
			Map<String, String> headers = new HashMap<String, String>();
			Map<String, String> params = new HashMap<String, String>();
			String appkey_url = PropertiesHelper.getDfs("app_interface_url");
			Map<String,Object> paras = new HashMap<String, Object>();
			if(request.getContentType()!=null && request.getContentType().contains("form-urlencoded")){
				paras.put("faceImageContent", request.getParameter("faceImageContent"));
				paras.put("bangZGId", request.getParameter("bangZGId"));
			}else{
				paras = RequestJsonUtil.getRequestMap(request);
				if(paras == null || paras.size()<=0){
					result=JsonResult.getInstance().add("returnCode","9999").add("returnMessage","没有接收到参数！").toString();
					log.info("yzs_personIdentify  result:"+result);
					return result; 
				}
			}
			//校验签证
			if(!verify(paras,request.getParameter("sign"))){
//				return JsonResult.getInstance().add("returnCode","9999").add("returnMessage","签证校验失败!").toString();
			}
			
			String bangZGId  =Convert.toStr(paras.get("bangZGId"));
			log.info("yzs_personIdentify  bangZGId "+bangZGId);
			if(Check.isBlank(bangZGId)){
				result=JsonResult.getInstance().add("returnCode","9999").add("returnMessage","地推ID参数为空！").toString();
				log.info("yzs_personIdentify   result:"+result);
				return result;
			}
			String faceImageContent =Convert.toStr(paras.get("faceImageContent"));
			if(Check.isBlank(faceImageContent)){
				result=JsonResult.getInstance().add("returnCode","9999").add("returnMessage","地推图片参数为空 ！").toString();
				log.info("yzs_personIdentify  bangZGId "+bangZGId+" result:"+result);
				return result;
			}
			String type=UhjConstant.imageType.yzs_postmember_face;
			
			String status =yzsPostmemberService.queryYzsPostmemberStatus(bangZGId);
			if("1".equals(status)){
				result=JsonResult.getInstance().add("returnCode","0000").add("returnMessage","已经刷脸验证过了，不需要再验证了。").toString();
				log.info("yzs_personIdentify bangZGId "+bangZGId+"; result:"+result);
				return result; 
			}
			paras=yzsPostmemberService.queryCreditPostMemberByBzgId(bangZGId);//根据联系人类型查询掌柜联系人姓名及证件号码
			if(paras==null){
				result=JsonResult.getInstance().add("returnCode","9999").add("returnMessage","没有查询到地推人员的身份信息，请重新录入身份证").toString();
				log.info("yzs_personIdentify  result:"+result);
				return result; 
			}
			String name=Convert.toStr(paras.get("name"));
			String certNo=Convert.toStr(paras.get("certNo"));
			
			params.put("tranzCode", "4100");
			params.put("base64String", faceImageContent);
			params.put("userOnlyId", bangZGId);
			params.put("type", type);
			String	imgUrl = com.ule.uhj.util.http.HttpClientUtil.sendPost(
					appkey_url, headers, params, UhjConstant.time_out);
			log.info("yzs_personIdentify imageUpload  bangZGId "+bangZGId+";imgUrl:"+imgUrl);
			JSONObject js=JSONObject.fromObject(imgUrl);
			if(!"success".equals(js.get("status"))){
				result=JsonResult.getInstance().add("returnCode","9999").add("returnMessage","图片上传失败！").toString();
				log.info("yzs_personIdentify bangZGId "+bangZGId+"; result:"+result);
				return result; 
			}
			
			//根据type 保存地址   
			paras.clear();
			paras.put("type", type);
			paras.put("url", js.get("url"));
			paras.put("userOnlyId", bangZGId);
			log.info("yzs_personIdentify saveApplyImage  bangZGId "+bangZGId+";paras:"+paras);
			applyImageService.saveApplyImage(paras);
			
			//调用亿图接口亿图姓名、身份证号与人脸照片相似度比较
			Map<String, String> map = new HashMap<String, String>();
			map.put("tranzCode", "3103");
			map.put("idCardImgUrl", Convert.toStr(js.get("url")));
			map.put("name", name);
			map.put("citizen_id", certNo);
			map.put("userOnlyId", bangZGId);
			String res = com.ule.uhj.util.http.HttpClientUtil.sendPost(
					appkey_url, headers, map, UhjConstant.time_out);
			log.info("yzs_personIdentify bangZGId "+bangZGId+"; res:"+res);
			//处理相识度结果
			JSONObject data=JSONObject.fromObject(res);
			JSONObject identify_result =JSONObject.fromObject(data.get("identify_result"));
			String rtn_identify=Convert.toStr(identify_result.get("rtn"));
			if("6300".equals(rtn_identify) && !"0".equals(rtn_identify)){//身份证号和姓名不一致
				paras.clear();
				paras.put("userOnlyId", bangZGId);
				paras.put("ruleRefId", UhjConstant.ruleRefId.yzs_postmember_face);
				paras.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
				paras.put("ruleType", UhjConstant.ruleType.yzs_rule_type);
				creditRuleService.saveCreditRuleService(paras);
				result=JsonResult.getInstance().add("returnCode","1000").add("returnMessage","刷脸认证未通过，您的身份信息有误").toString();
				log.info("yzs_personIdentify bangZGId "+bangZGId+"; result:"+result);
				return result;
			}
			JSONObject verify_result =JSONObject.fromObject(data.get("verify_result"));
			String rtn_verify=Convert.toStr(verify_result.get("rtn"));
			if(!"0".equals(rtn_verify)){
				paras.clear();
				paras.put("userOnlyId", bangZGId);
				paras.put("ruleRefId", UhjConstant.ruleRefId.yzs_postmember_face);
				paras.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
				paras.put("ruleType", UhjConstant.ruleType.yzs_rule_type);
				creditRuleService.saveCreditRuleService(paras);
				result=JsonResult.getInstance().add("returnCode","9999").add("returnMessage","刷脸未通过,比对结果异常").toString();
				log.info("yzs_personIdentify bangZGId "+bangZGId+"; result:"+result);
				return result;
			}
			BigDecimal final_verify_score=Convert.toBigDecimal(verify_result.get("final_verify_score"),BigDecimal.ZERO);
			boolean is_pass=(Boolean) verify_result.get("is_pass");
			
			if(is_pass && final_verify_score.compareTo(new BigDecimal("75"))>=0){
				//保存刷脸对比结果
				paras.clear();
				paras.put("userOnlyId", bangZGId);
				paras.put("ruleRefId", UhjConstant.ruleRefId.yzs_postmember_face);
				paras.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
				paras.put("ruleType", UhjConstant.ruleType.yzs_rule_type);
				creditRuleService.saveCreditRuleService(paras);
				result=JsonResult.getInstance().add("returnCode","0000").add("returnMessage","Success").toString();
				
				//更新到百度人脸库[地推人员]
				baiDuFaceService.addFace("bangZGface", bangZGId, faceImageContent);
				
			}else{
				//保存刷脸对比结果
				paras.clear();
				paras.put("userOnlyId", bangZGId);
				paras.put("ruleRefId", UhjConstant.ruleRefId.yzs_postmember_face);
				paras.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
				paras.put("ruleType", UhjConstant.ruleType.yzs_rule_type);
				creditRuleService.saveCreditRuleService(paras);
				result=JsonResult.getInstance().add("returnCode","2000").add("returnMessage","刷脸未通过，请确认是本人申请").toString();
				log.info("yzs_personIdentify bangZGId "+bangZGId+"; result:"+result);
			}
		} catch (Exception e) {
			log.error("yzs_personIdentify error!",e);
			result=JsonResult.getInstance().add("returnCode","9999").add("returnMessage","网络异常,请稍后再试").toString();
		}
		log.info("yzs_personIdentify  result "+result);
		return result; 
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
