	package com.ule.uhj.zgd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.ycZgd.BangZGClient;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.CookieUtil;
import com.ule.uhj.util.JsonResult;

@Controller
@RequestMapping("/uhj")
public class BangZGController {
	private static Logger log = LoggerFactory.getLogger(BangZGController.class);
	String applyProcess = "bzg/bangZgApplyProcess";
	String zgManage = "bzg/zgManage";
//	String zgManage = "bzg/zgManage2";
	String bangZgNoPermission = "bzg/bangZgNoPermission";
	String bangZgPushTaskQuery = "bzg/bangZgPushTaskQuery";
	String bangZgNoQualify = "bzg/bangZgNoQualify";
	String ERROR = "common/error";
	
	/**
	 * 查询掌柜贷申请进度
	 * @return
	 * @author yubb
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/bzg_queryZGApplyProcess")
	public String queryZGApplyProcess(HttpServletRequest request){
		try{
			log.info("queryZGApplyProcess begin*********");
			String userOnlyId = decryptDES(request.getParameter("userOnlyId"));
			String bangZGId = decryptDES(request.getParameter("bangZGId"));
			log.info("parameter userOnlyId="+userOnlyId+" bangZGId="+bangZGId);
			if(Check.isBlank(userOnlyId) || Check.isBlank(bangZGId)){
				return ERROR;
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("userOnlyId", userOnlyId);
			param.put("bangZGId", bangZGId);
			BangZGClient client = WildflyBeanFactory.getBangZGClient();
			Map<String,Object> result = client.queryApplyProcess(param);
			log.info("queryUserStatusProcess result:" + result);
			if("1000".equals(result.get("code"))){
				request.setAttribute("message1", "对不起，此掌柜暂无申请资格。");
				request.setAttribute("message2", "连续三个月评级为A方可申请掌柜贷。");
				request.setAttribute("message3","");
				request.setAttribute("message4", "");
				return bangZgNoQualify;
			}else if("2000".equals(result.get("code")) || "9999".equals(result.get("code"))){
				return bangZgNoPermission;
			}else if("1001".equals(result.get("code"))){
				request.setAttribute("message1", "对不起，该掌柜预估额度太低，");
				request.setAttribute("message2", "暂时不能申请，快去帮助掌柜提");
				request.setAttribute("message3", "升进销存、批发、代购、充值缴");
				request.setAttribute("message4", "费业绩吧");
				return bangZgNoQualify;
			}
			Integer status = Convert.toInt(result.get("adStatus"));
			if(status != null){
//				1、10：待实名认证
//				2、12：已实名认证、待提交个人信息
//				3、14：已实名认证、已提交个人信息、待上传认证资料
//				4、20：已实名认证、已提交个人信息、已上传照片、审批中
//				5、88：已实名认证、已提交个人信息、已上传照片、审批中、审批成功
//				6、0：已实名认证、已提交个人信息、已上传照片、审批中、审批拒绝
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("operationTime", ((Map<String,Object>)((List<Map<String,Object>>)result.get("reList")).get(0)).get("operationTime"));
				map.put("opetime", ((Map<String,Object>)((List<Map<String,Object>>)result.get("reList")).get(0)).get("opeTime"));
				if(10 == status){
					map.put("shouldOperateContent", "待实名认证");
				} else if(12 == status){
					map.put("shouldOperateContent", "待提交个人信息");
				} else if(14 == status){
					map.put("shouldOperateContent", "待上传认证资料");
				} else if(20 <= status && status <= 88){
					map.put("shouldOperateContent", "审批中");
				}
				if(!Check.isBlank(map.get("shouldOperateContent"))){
					request.setAttribute("preLine", map);
				}
			}
			request.setAttribute("result", result);
		}catch(Exception e){
			log.error("queryZGApplyProcess error",e);
			return "error";
		}
		return applyProcess;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/bzg_queryZGManageInfo")
	public String queryZGManageInfo(HttpServletRequest request,Integer sortRule){
		try {
			log.info("queryZGManageInfo begin*********");
			String mobileCookie = CookieUtil.getCookie(request, "mobileCookie");
			String bangZGId = CookieUtil.cookieDec("mobileCookie", "yzs", mobileCookie);
			//String bangZGId = "222";
			log.info("parameter bangZGId="+bangZGId);
			if(Check.isBlank(bangZGId)){
				return ERROR;
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("bangZGId", bangZGId);
			param.put("sortRule", sortRule==null?"0":sortRule);
			BangZGClient client = WildflyBeanFactory.getBangZGClient();
			//UhjClientFactoryUrl.getInstance("jnp://127.0.0.1:1099").getHJClient(BangZGClient.class);
			Map<String,Object> resultMap = client.queryZgManageInfo(param);

			if("0000".equals(resultMap.get("code"))){
				Map<String,Object> result = (Map<String, Object>) resultMap.get("data");
				List<Map<String,Object>> zgInfoList = (List<Map<String,Object>>) result.get("zgInfo");
				//逾期待写入
				//map.put("yuqi_userid", result.get("yuqi_userid"));
				request.setAttribute("result", result);
				request.setAttribute("zgInfo", zgInfoList);
				log.info("queryZGManageInfo result:" + result);
			}else{
				return bangZgNoPermission;
			}
		} catch (Exception e) {
			log.error("queryZGManageInfo error",e);
			return "error";
//			return zgManage;
		}finally{
			log.info("queryZGManageInfo end*********");
		}
		return zgManage;
	}
	
//	@SuppressWarnings("unchecked")
//	@RequestMapping("/bzg_topage")
//	public String topage(HttpServletRequest request){
//		try {
//			List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
//			list=(List<Map<String,Object>>)request.getParameter("bangZGId");
//		} catch (Exception e) {
//			log.error("queryZGManageInfo error",e);
//			return "error";
//		}finally{
//			log.info("queryZGManageInfo end*********");
//		}
//		return zgManage;
//	}
	/**
	 * 帮掌柜推送任务
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/bzg_queryBangZgTask", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryBangZgTask(HttpServletRequest request){
		try {
			log.info("queryBangZgTask begin*********");
			log.info("parameter bangZGId="+request.getParameter("bangZGId"));
			String bangZGId =decryptDES(request.getParameter("bangZGId"));//request.getParameter("bangZGId");////request.getParameter("bangZGId");// decryptDES(request.getParameter("bangZGId"));//
			log.info("parameter bangZGId="+bangZGId);
			if(Check.isBlank(bangZGId)){
				return JsonResult.getInstance().addError("操作失败,bangZGId为空").toJsonStr();
			}
//			 List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
//			return JsonResult.getInstance().addOk().add("data",resultList).toJsonStr();
			BangZGClient client = WildflyBeanFactory.getBangZGClient();
			Map<String,Object>  resultMap = client.queryZgdPopularize(bangZGId);
//			log.info("count"+resultMap.get("count")+"resultMap"+resultMap);
			if("0000".equals(resultMap.get("code"))){
				List<Map<String,Object>> zgInfoList = (List<Map<String,Object>>) resultMap.get("data");
//				request.setAttribute("result", zgInfoList);
				return JsonResult.getInstance().addOk().add("data",zgInfoList).add("count",resultMap.get("count")).toJsonStr();
			}else {
				return JsonResult.getInstance().addError("操作失败").toJsonStr();
			}
			
//			return bangZgPushTaskQuery;
		} catch (Exception e) {
			log.error("queryZGManageInfo error",e);
			return JsonResult.getInstance().addError("操作失败").toJsonStr();
		}
	}
	  static final String decryptKey = "6fd4b7f4"; // 密钥
	  static final  byte[] iv = { 13, 8, 3, 16, 23, 6, 11, 5 };  //向量
	
	//DES解密
	public static String decryptDES(String encryptString) throws Exception {
		if(encryptString==null || "".equals(encryptString)){
			return encryptString;
		}
		try {
			byte str2[] = Base64.decodeBase64(encryptString.getBytes("UTF-8"));
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes("UTF-8"), "DES");
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			byte[] str3 = cipher.doFinal(str2);
			String res = encryptString;
			if (str3 != null) {
				res = new String(str3, "UTF-8");
			}
			return res;
		} catch (Exception e) {
			log.error("解密失败：" + encryptString);
			throw  e;
		}
	}

}
