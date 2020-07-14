package com.ule.uhj.provider.yitu.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import com.alibaba.fastjson.JSONObject;
import com.ule.uhj.provider.yitu.util.HttpUtils;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sld.util.MD5;
import com.ule.uhj.sldProxy.util.Check;
import com.ule.uhj.sldProxy.util.PropertiesHelper;

public class SuanhuaService {
	protected static Log log = LogFactory.getLog(SuanhuaService.class);
	
	protected static String host=PropertiesHelper.getDfs("SUAN_HUA_IP");
	protected static String orgcode=PropertiesHelper.getDfs("ORG_CODE");
	protected static String key=PropertiesHelper.getDfs("KEY");
	
	protected static String S_H_INIT="/sp/authslops/init";							//任务创建(初始化)
	protected static String S_H_LOGINFORM="/sp/authslops/loginform";				//获取登录表单
	protected static String S_H_FORMPOST="/sp/authslops/formpost";					//提交表单验证
	protected static String S_H_SMSSEND ="/sp/authslops/smssend";					//获取短信验证码
	protected static String S_H_IMAGEREFRESH ="/sp/authslops/imagerefresh"; 		//刷新图片验证码
	protected static String S_H_REPORTBYSTOKEN ="/sp/reportByStoken";				//获取已查得报告
	
	
	
//	/**
//	 * 申请报告查询
//	 * @param param
//	 * @return
//	 * @throws Exception
//	 */
//	public String applyReport(Map<String,Object> param)throws Exception {
//		String url=host+PropertiesHelper.getDfs("APPLY_REPORT");
//		String orgcode=Convert.toStr(param.get("orgcode"));
//		Map<String, Object> paramMap =new HashMap<String, Object>();
//		String idCard=Convert.toStr(param.get("idCard"));
//		String name=Convert.toStr(param.get("name"));
//		String data=Convert.toStr(param.get("data"));
//		String md5=Convert.toStr(param.get("md5"));
//		String hash=MD5.md5Code(md5);
//		paramMap.put("orgcode", orgcode);
//		paramMap.put("name", name);
//		paramMap.put("idCard", idCard);
//		paramMap.put("data", data);
//		paramMap.put("hash", hash);
//		log.info("applyReport param:"+paramMap);
//		return sendRequest(url,null,"POST",paramMap);
//	}
	
	/**
	 * 查询报告
	 * @param param
	 * @return
	 * @throws Exception
	 */
//	public String queryReport(Map<String,Object> param)throws Exception {
//		String url = host+PropertiesHelper.getDfs("APPLY_REPORT");
//		String orgcode=PropertiesHelper.getDfs("ORG_CODE");
//		String key=PropertiesHelper.getDfs("KEY");
//		String name=Convert.toStr(param.get("name"));
//		String idCard=Convert.toStr(param.get("idCard"));
//		String md5=idCard+name+orgcode+key;
//		String hash=MD5.md5Code(md5);
//		Map<String, Object> paramMap =new HashMap<String, Object>();
//		paramMap.put("orgcode", orgcode);
//		paramMap.put("name", name);
//		paramMap.put("idCard", idCard);
//		paramMap.put("hash", hash);
//		paramMap.put("tranzCode", Convert.toStr(param.get("tranzCode")));
//		log.info("queryReport param:"+paramMap);
//		return sendRequestToData(url,null,"POST",paramMap);
//	}
	
	/**
	 * 批量查询报告
	 * @param param
	 * @return
	 * @throws Exception
	 */
//	public String queryBatchReport(Map<String,Object> param)throws Exception {
//		String url = host+PropertiesHelper.getDfs("BATCH_REPORT");
//		String orgcode=PropertiesHelper.getDfs("ORG_CODE");
//		String key=PropertiesHelper.getDfs("KEY");
//		String from=Convert.toStr(param.get("from"));
//		String to =Convert.toStr(param.get("to"));
//		String md5=from+orgcode+to+key;
//		String hash=MD5.md5Code(md5);
//		Map<String, Object> paramMap =new HashMap<String, Object>();
//		paramMap.put("orgcode", orgcode);
//		paramMap.put("from", from);
//		paramMap.put("to", to);
//		paramMap.put("hash", hash);
//		log.info("queryBatchReport param:"+paramMap);
//		return sendRequest(url,null,"POST",paramMap);
//	}
	
	/**
	 * 通过crpId查询报告
	 * @param param
	 * @return
	 * @throws Exception
	 */
//	public String queryReportByCrpId(Map<String,Object> param)throws Exception {
//		String url = host+PropertiesHelper.getDfs("REPORT_BY_CRPID");
//		String orgcode=PropertiesHelper.getDfs("ORG_CODE");
//		String key=PropertiesHelper.getDfs("KEY");
//		String crpId=Convert.toStr(param.get("crpId"));
//		String md5=crpId+orgcode+key;
//		String hash=MD5.md5Code(md5);
//		Map<String, Object> paramMap =new HashMap<String, Object>();
//		paramMap.put("orgcode", orgcode);
//		paramMap.put("crpId", crpId);
//		paramMap.put("hash", hash);
//		log.info("queryReportByCrpId param:"+paramMap);
//		return sendRequest(url,null,"POST",paramMap);
//	}
	
	/**
	 * 密码重置
	 * @param param
	 * @return
	 * @throws Exception
	 */
//	public String pwdReset(Map<String,Object> param)throws Exception {
//		String url = host+PropertiesHelper.getDfs("PWDRESET");
//		String orgcode=PropertiesHelper.getDfs("ORG_CODE");
////		String phone=Convert.toStr(param.get("phone"));
////		String iid=Convert.toStr(param.get("iid"));
////		String data=JsonResult.getInstance().add("phone", phone).toJsonStr();
//		String data=Convert.toStr(param.get("data"));
//		String md5=Convert.toStr(param.get("md5"));
//		String hash=MD5.md5Code(md5);
//		Map<String, Object> paramMap =new HashMap<String, Object>();
//		paramMap.put("orgcode", orgcode);
//		paramMap.put("data", data);
//		paramMap.put("hash", hash);
//		log.info("pwdReset param:"+paramMap);
//		return sendRequest(url,null,"POST",paramMap);
//	}
	
	/**
	 * 发送请求
	 * @param path
	 * @param method
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String sendRequest(String url,String path,String method,Map<String, Object> paramMap)throws Exception{
		Map<String, String> headers = new HashMap<String, String>();
	    String bodys = null;
    	HttpResponse response = HttpUtils.doPost(url, path, method, headers, paramMap, bodys);
    	HttpEntity entity = response.getEntity();
		if(200 != response.getStatusLine().getStatusCode() || Check.isBlank(entity)){
			StringBuilder sb =  new StringBuilder("response code:").append(response.getStatusLine().getStatusCode()).append(", response entity:").append(entity);
			log.info(sb.toString());
    	}
    	String entityStr = EntityUtils.toString(entity);
    	String responseEntity = entityStr;
    	if(!Check.isBlank(responseEntity)){
    		try {
    			Map map = new ObjectMapper().readValue(responseEntity, Map.class);
	    		System.out.println("****************"+map);
	    		return JSONObject.toJSONString(map);
			} catch (JsonParseException e) {
				System.out.println(e.getMessage());
			}
    	}
		return null;
	}
	
//	@SuppressWarnings("rawtypes")
//	public String sendRequestToData(String url,String path,String method,Map<String, Object> paramMap)throws Exception{
//		Map<String, String> headers = new HashMap<String, String>();
//	    String bodys = null;
//    	HttpResponse response = HttpUtils.doPost(url, path, method, headers, paramMap, bodys);
//    	HttpEntity entity = response.getEntity();
//		if(200 != response.getStatusLine().getStatusCode() || Check.isBlank(entity)){
//    		System.out.println(String.format("response code:%s, response entity:%s", new Object[]{response.getStatusLine().getStatusCode(), entity}));
//    	}
//    	String entityStr = EntityUtils.toString(entity);
//    	String responseEntity = entityStr;
//    	int restAccessCount = 10;
//    	if(!Check.isBlank(responseEntity)){
//    		try {
//    			if(restAccessCount>0){
//    				//入库
//	    			Map<String, Object> paMap = new HashMap<String, Object>();
//	    			String userOnlyId = Convert.toStr(paramMap.get("userOnlyId"));
//	    			String tranzCode = Convert.toStr(paramMap.get("tranzCode"));
//	    			paMap.put("userOnlyId", userOnlyId);
//	    			paMap.put("tranzCode", tranzCode);
////	    			juxinliService.saveResponseData(paMap,paMap);
//    			}else{
//    				//入库
//	    			Map<String, Object> paMap = new HashMap<String, Object>();
//	    			String userOnlyId = Convert.toStr(paramMap.get("userOnlyId"));
//	    			String tranzCode = Convert.toStr(paramMap.get("tranzCode"));
//	    			paMap.put("userOnlyId", userOnlyId);
//	    			paMap.put("tranzCode", tranzCode);
//	    			paMap.put("restAccessCount", restAccessCount);
////	    			juxinliService.saveResponseData(paMap,paMap);
//    			}
//    			Map map = new ObjectMapper().readValue(responseEntity, Map.class);
//	    		System.out.println("****************"+map);
//	    		return JSONObject.toJSONString(map);
//			} catch (JsonParseException e) {
//				System.out.println(e.getMessage());
//			}
//    	}else{
//    		restAccessCount--;
//    	}
//		return null;
//	}
		
	/**
	 * 任务创建(初始化)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String init(Map<String,Object> param)throws Exception {
		String url		=	host +S_H_INIT;
		String phone	=	Convert.toStr(param.get("phone"));		
		String hash		=	MD5.md5Code(orgcode + phone + key);
		
		Map<String, Object> paramMap =new HashMap<String, Object>();
		paramMap.put("orgcode", orgcode);
		paramMap.put("phone", phone);
		paramMap.put("hash", hash);
		
		
		log.info(" SuanhuaService init request url:"+url+",paramMap："+paramMap.toString());
		
		String rs= sendRequest(url,null,"POST",paramMap);
		
		log.info(" SuanhuaService init response:"+rs);
		
		return rs; 
		
/*		返回结果：
 		{
			stoken=SH_20170921_49f2ebb33652e1a506fef541c4b1a3bd, 
			data={suppliers=[{itemName=上海移动, 
							  itemCode=OPERATOR_YIDONGSHANGHAI, 
							  itemStatus=ENABLE, 
							  itemDesc=服务密码/短信验证码由6位或8位数字组成，如有疑问，请联系运营商核实, 
							  loginTypes=[{code=UDP, 
										   name=服务密码}]
							}]}, 
			message=请求处理成功, 
			statusCode=001
		}*/
		
	}
	/**
	 * 获取登录表单
	 * @param param {stoken,itemCode,loginTypeCode} init接口返回结果里有
	 * @return
	 * @throws Exception
	 */
	public String loginform(Map<String,Object> param)throws Exception {
		
		String url			=	host +S_H_LOGINFORM;
		String stoken		=	Convert.toStr(param.get("stoken"));
		String itemCode		=	Convert.toStr(param.get("itemCode"));
		String loginTypeCode=	Convert.toStr(param.get("loginTypeCode"));
		String hash			=	MD5.md5Code(itemCode + loginTypeCode + orgcode + stoken + key);
		
		Map<String, Object> paramMap =new HashMap<String, Object>();
		paramMap.put("orgcode", orgcode);
		paramMap.put("stoken", stoken);
		paramMap.put("itemCode", itemCode);
		paramMap.put("loginTypeCode", loginTypeCode);
		paramMap.put("hash", hash);
		
		String rs= sendRequest(url,null,"POST",paramMap);
		
		log.info(" SuanhuaService loginform:"+rs);
		
		return rs;
		
/*		{	
			stoken=SH_20170921_49f2ebb33652e1a506fef541c4b1a3bd, 
			data={itemCode=OPERATOR_YIDONGSHANGHAI, 
				  loginTypes=[{loginTypeCode=UDP, 
							   loginFields=[{	readonly=false, 
												name=phoneNo, 
												label=手机号码, 
												placeholder=11位手机号码, 
												type=text, 
												value=18201857659
											}, 
											{
												readonly=false, 
												name=servicePassword, 
												label=服务密码, 
												placeholder=6位或8位服务密码, 
												type=password
											}, 
											{
												readonly=false, 
												name=smsCode, 
												label=短信验证码, 
												placeholder=短信验证码, 
												type=text
											}], 
							   loginTypeDesc=服务密码}]}, 
			message=请求处理成功, 
			statusCode=001
		}*/
	}
	
	
	/**
	 * 提交表单验证
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String formpost(Map<String,Object> param)throws Exception {
		
		String url			=	host +S_H_FORMPOST;
		String stoken		=	Convert.toStr(param.get("stoken"));
		String step			=	Convert.toStr(param.get("step"));
		String form			=	Convert.toStr(param.get("form"));
		String hash			=	MD5.md5Code(form + orgcode + step + stoken + key);
		
		Map<String, Object> paramMap =new HashMap<String, Object>();
		
		paramMap.put("orgcode", orgcode);
		paramMap.put("stoken", stoken);
		paramMap.put("step", step);
		paramMap.put("form", form);
		paramMap.put("hash", hash);
		
		String rs= sendRequest(url,null,"POST",paramMap);
		
		log.info(" SuanhuaService formpost:"+rs);
		
		return rs;
	}
	
	/**
	 * 获取短信验证码
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String smssend(Map<String,Object> param)throws Exception {
		
		String url			=	host +S_H_SMSSEND;
		String stoken		=	Convert.toStr(param.get("stoken"));
		String step			=	Convert.toStr(param.get("step"));
		String hash			=	MD5.md5Code(orgcode + step + stoken + key);
		
		Map<String, Object> paramMap =new HashMap<String, Object>();
		paramMap.put("orgcode", orgcode);
		paramMap.put("stoken", stoken);
		paramMap.put("step", step);
		paramMap.put("hash", hash);
		
		String rs= sendRequest(url,null,"POST",paramMap);
		
		log.info(" SuanhuaService smssend:"+rs);
		
		return rs;
	}
	
	
	/**
	 * 刷新图片验证码
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String imagerefresh(Map<String,Object> param)throws Exception {
		
		String url			=	host +S_H_IMAGEREFRESH;
		String stoken		=	Convert.toStr(param.get("stoken"));
		String step			=	Convert.toStr(param.get("step"));
		String hash			=	MD5.md5Code(orgcode+ step+ stoken + key);
		
		Map<String, Object> paramMap =new HashMap<String, Object>();
		paramMap.put("orgcode", orgcode);
		paramMap.put("stoken", stoken);
		paramMap.put("step", step);
		paramMap.put("hash", hash);
		
		
		
		String rs= sendRequest(url,null,"POST",paramMap);
		
		log.info(" SuanhuaService imagerefresh:"+rs);
		
		return rs;
	}
	
	
	
	/**
	 * 获取已查得报告
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String reportByStoken(Map<String,Object> param)throws Exception {
		
		String url			=	host +S_H_REPORTBYSTOKEN;
		String stoken		=	Convert.toStr(param.get("stoken"));
		String hash			=	MD5.md5Code(orgcode + stoken + key);
		
		Map<String, Object> paramMap =new HashMap<String, Object>();
		paramMap.put("orgcode", orgcode);
		paramMap.put("stoken", stoken);
		paramMap.put("hash", hash);
		
		return sendRequest(url,null,"POST",paramMap);
	}
	
//	public static void main(String[] args) throws Exception {
//		String type=null;	
//		
//		type="init";
////		type="loginform";
////		type="formpost";
////		type="smssend";
////		type="imagerefresh";
////		type="reportByStoken";
//		
//
//		
//		SuanhuaService s = new SuanhuaService();
//		Map<String, Object> param =new HashMap<String, Object>();
//		
////		String phone	=	"18201857659";
////		String servicePassword=	"861477";
////		String name		=	"伍海涛";
////		String idCard	=	"340881198609226517";
//		
////		String phone	=	"18516329918";
////		String servicePassword=	"914129";
////		String name		=	"余兵兵";
////		String idCard	=	"362226199009140632";
//		
//		String phone	=	"13399533011";
//		String servicePassword=	"810235";
//		String name		=	"潘星";
//		String idCard	=	"340221199009050031";
//		
//		// 二次验证测试
////		String phone	=	"18087486480";
////		String servicePassword=	"150310";
////		String name		=	"余兵兵";
////		String idCard	=	"362226199009140632";
//		
//
//		String stoken    ="SH_20170922_e019ada13c87f8e4fdd75f2047262ef3";
//		if("init".equals(type)){
//			//init方法测试
//			param.put("idCard", idCard);
//			param.put("name", name);
//			param.put("phone", phone);
//			String loginform =s.init(param);
//		}else if("loginform".equals(type)){
//			//获取登录表单
//			String itemCode	 ="OPERATOR_DIANXINYUNNAN";
//			String loginTypeCode ="UP";
//			param.put("stoken", stoken);
//			param.put("itemCode", itemCode);
//			param.put("loginTypeCode", loginTypeCode);
//			s.loginform(param);
//		}else if("formpost".equals(type)){
//			//提交表单验证
//			String captcha		=	"";
//			String smsCode		=	"";
//			
//			StringBuffer form=new StringBuffer("{");
//			if(StringUtils.isNotBlank(captcha)){
//				form.append("'captcha':'"+captcha+"',");				
//			}
//			if(StringUtils.isNotBlank(servicePassword)){
//				form.append("'servicePassword':'"+servicePassword+"',");				
//			}
//			if(StringUtils.isNotBlank(smsCode)){
//				form.append("'smsCode':'"+smsCode+"',");				
//			}
//			form.append("'phoneNo':'"+phone+"'}");
//			
//			param.put("form", form.toString());
//			param.put("stoken", stoken);
//			param.put("step", "1");
//			s.formpost(param);
//		}else if("smssend".equals(type)){
//			//获取短信验证码
//			param.put("stoken", stoken);
//			param.put("step", "1");
//			s.smssend(param);
//			
//		}else if("imagerefresh".equals(type)){
//			//刷新图片验证码
//			param.put("stoken", stoken);
//			param.put("step", "1");
//			s.imagerefresh(param);
//			
//		}else if("reportByStoken".equals(type)){
//			//获取已查得报告
//			param.put("stoken", stoken);
//			s.reportByStoken(param);
//		}else{
//			String strImg = "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";  
//			boolean img =GenerateImage(strImg, "D:\\wangyc.jpg");  
//		}
//	}
	
}
