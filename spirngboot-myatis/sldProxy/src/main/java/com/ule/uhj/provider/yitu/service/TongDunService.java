package com.ule.uhj.provider.yitu.service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.ule.uhj.provider.yitu.util.tongdon.HttpUtils;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sldProxy.util.PropertiesHelper;

public class TongDunService {
	protected static Log log = LogFactory.getLog(TongDunService.class);
	
	protected static String host=PropertiesHelper.getDfs("TONG_DON_IP");
//	public static String partner_code=PropertiesHelper.getDfs("ORG_CODE");
//	public static String partner_key=PropertiesHelper.getDfs("KEY");
	
	protected static String partner_code="zgd_mohe";
	protected static String partner_key="bbcee42b572841e1a9562e058a6fbfbc";
	
	
	//方法
	protected static String create="/task.unify.create/v3";						//创建任务
	protected static String acquire="/yys.unify.acquire/v3";						//登录验证
	protected static String retry ="/task.unify.retry/v3";							//验证码
	protected static String query="/task.unify.query/v3";							//查询任务结果
	
	protected static String moHeReport="/report.task.query/v2";					//魔盒报告
	
	protected static String rstCreate ="/yys.rstpwd.create/v1"; 				//重置密码(创建)
	protected static String rstSubmit ="/yys.rstpwd.submit/v1"; 				//重置密码(验证)
	
	
	/**
	 * 提供给web端使用 直接验证
	 * @param param(user_mobile=user_name、real_name、identity_code、user_pass)
	 * @return
	 * @throws Exception
	 */
	public String firstAcquire(Map<String,Object> param)throws Exception {
		
		String createResult =create(param);
		
		Map<String,Object> createMap = getMapFromJsonString(createResult);
		String code=Convert.toStr(createMap.get("code"));
		
		if(!"0".equals(code)){
			return createResult;
		}
		String task_id=Convert.toStr(createMap.get("task_id"));
		
		param.put("task_id", task_id);
		String acquireResult =acquire(param);
		
		return acquireResult;
	}
	
	
	
	/**
	 * 任务创建
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String create(Map<String,Object> param)throws Exception {
		String url		=	host +create;
		String queryParam = "?partner_code="+partner_code+"&partner_key="+partner_key;
		Map<String, String> headers=null;
		
		String user_mobile	=	Convert.toStr(param.get("user_mobile"));
		String real_name		=	Convert.toStr(param.get("real_name"));
		String identity_code	=	Convert.toStr(param.get("identity_code"));
		  
		String body = "channel_code=100000&channel_type=YYS&real_name="+real_name+"&identity_code="+identity_code+"&user_mobile="+user_mobile;
		
		log.info(" TongDonService create request url:"+url+"queryParam:"+queryParam+",body："+body);
		
		String rs =HttpUtils.executeHttpPost(url, queryParam, headers, body);
		
		log.info(" TongDonService create response:"+rs);
		
		return rs; 		
		
		
//		{
//			"code":0,
//			"data":{
//			"channel_code":"100000",
//			"channel_type":"YYS",
//			"created_time":"2017-12-18 10:14:04",
//			"identity_code":"340881198609226517",
//			"real_name":"伍海涛",
//			"user_mobile":"18201857659"
//			},
//			"message":"任务创建成功",
//			"task_id":"TASKYYS100000201712181014040711030317"
//			}
	}
	
	/**
	 * 登录验证
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String acquire(Map<String,Object> param)throws Exception {
		String url		=	host +acquire;
		String queryParam = "?partner_code="+partner_code+"&partner_key="+partner_key;
		Map<String, String> headers=null;
		
		String task_id	=	Convert.toStr(param.get("task_id"));
		String user_name	=	Convert.toStr(param.get("user_name"));
		String user_pass	=	Convert.toStr(param.get("user_pass"));
		String sms_code	=	Convert.toStr(param.get("sms_code"));		
		String task_stage	=	Convert.toStr(param.get("task_stage"));
		String request_type = Convert.toStr(param.get("request_type"));
		
		if("null".equals(sms_code)){
			sms_code="";
		}
		
		if(StringUtils.isBlank(task_stage)){
			task_stage ="INIT";
		}
		if(StringUtils.isBlank(request_type)){
			request_type="submit";
		}
		
		StringBuilder body = new StringBuilder("task_id=");
			body.append(task_id);
		if(StringUtils.isNotBlank(user_name)){
			body.append("&user_name=");
			body.append(user_name);			
		}
		if(StringUtils.isNotBlank(user_pass)){
			body.append("&user_pass=");
			body.append(user_pass);			
		}
		if(StringUtils.isNotBlank(sms_code)){
			body.append("&sms_code=");
			body.append(sms_code);			
		}
			body.append("&task_stage=");
			body.append(task_stage);
			body.append("&request_type=");
			body.append(request_type);
		
		String bodyStr =body.toString();
			
		log.info(" TongDonService acquire request url:"+url+"queryParam:"+queryParam+",body："+bodyStr);
		String submitRs =HttpUtils.executeHttpPost(url, queryParam, headers, bodyStr);
		log.info(" TongDonService acquire response:"+submitRs);
		
		
		Map<String,Object> createMap = getMapFromJsonString(submitRs);
		String code=Convert.toStr(createMap.get("code"));
		
		if(!"100".equals(code)){
			return submitRs;
		}
		
		Thread.sleep(10000);
		
		bodyStr =bodyStr.replace("submit", "query");
		log.info(" TongDonService 10miao acquire request url:"+url+"queryParam:"+queryParam+",body："+bodyStr);
		String queryRs =HttpUtils.executeHttpPost(url, queryParam, headers, bodyStr);
		log.info(" TongDonService 10miao acquire response:"+queryRs);
		
		Map<String,Object> queryMap = getMapFromJsonString(queryRs);
		String queryCode=Convert.toStr(queryMap.get("code"));
		if("100".equals(queryCode)){
				queryMap.put("bodyStr", bodyStr);
				queryRs=JSON.toJSONString(queryMap);
		}
		log.info(" TongDonService acquire callback:"+queryRs);
		return queryRs; 		
		
	}
	
	/**
	 * 验证码
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String retry(Map<String,Object> param)throws Exception {
		String url		=	host +retry;
		String queryParam = "?partner_code="+partner_code+"&partner_key="+partner_key;
		Map<String, String> headers=null;
		
		String task_id	=	Convert.toStr(param.get("task_id"));
		
		String body = "task_id="+task_id;
		
		log.info(" TongDonService create request url:"+url+"queryParam:"+queryParam+",body："+body);
		String rs =HttpUtils.executeHttpPost(url, queryParam, headers, body);
		log.info(" TongDonService create response:"+rs);
		
		return rs; 		
	}
	
	/**
	 * 查询任务结果
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String moHeReport(Map<String,Object> param)throws Exception {
		String url		=	host +moHeReport;
		String queryParam = "?partner_code="+partner_code+"&partner_key="+partner_key;
		Map<String, String> headers=null;
		
		String task_id	=	Convert.toStr(param.get("task_id"));
		
		String body = "task_id="+task_id;
		
		log.info(" TongDonService moHeReport request url:"+url+"queryParam:"+queryParam+",body："+body);
		String rs =HttpUtils.executeHttpPost(url, queryParam, headers, body);
		log.info(" TongDonService moHeReport response:"+rs);
		
		return rs; 		
	}
	
	
	/**
	 * 查询任务结果
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String query(Map<String,Object> param)throws Exception {
		String url		=	host +query;
		String queryParam = "?partner_code="+partner_code+"&partner_key="+partner_key;
		Map<String, String> headers=null;
		
		String task_id	=	Convert.toStr(param.get("task_id"));
		
		String body = "task_id="+task_id;
		
		log.info(" TongDonService create request url:"+url+"queryParam:"+queryParam+",body："+body);
		String rs =HttpUtils.executeHttpPost(url, queryParam, headers, body);
		log.info(" TongDonService create response:"+rs);
		
		return rs; 		
	}
	
	
	/**
	 * 重置密码(创建)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String rstpwdCreate(Map<String,Object> param)throws Exception {
		String url		=	host +rstCreate;
		String queryParam = "?partner_code="+partner_code+"&partner_key="+partner_key;
		Map<String, String> headers=null;
		
		String user_mobile	=	Convert.toStr(param.get("user_mobile"));
		
		String body = "user_mobile="+user_mobile;
		
		log.info(" TongDonService rstpwdCreate request url:"+url+"queryParam:"+queryParam+",body："+body);
		String rs =HttpUtils.executeHttpPost(url, queryParam, headers, body);
		log.info(" TongDonService rstpwdCreate response:"+rs);
		
		Map<String,Object> createMap = getMapFromJsonString(rs);
		String code=Convert.toStr(createMap.get("code"));
		
		if(!"0".equals(code)){
			return rs;
		}
		
		rs =rstpwdSubmit(createMap);
				
		return rs; 		
		
//		{
//		    "code": 0,
//		    "data": {
//		        "created_time": "2017-12-18 15:20:09",
//		        "user_mobile": "18201857659"
//		    },
//		    "message": "任务创建成功",
//		    "task_id": "TASKRSTPWD100000201712181520090730014"
//		}
		
		
//		{
//		    "code": 139,
//		    "data": {
//		        "data_message": "发送免费短信指令 “MMCZ” 到10086 进行密码重置"
//		    },
//		    "message": "请根据提示信息重置密码",
//		    "task_id": "TASKRSTPWD100000201712181520410740016"
//		}
		
	}
	
	
	/**
	 * 重置密码(验证)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String rstpwdSubmit(Map<String,Object> param)throws Exception {
		String url		=	host +rstSubmit;
		String queryParam = "?partner_code="+partner_code+"&partner_key="+partner_key;
		Map<String, String> headers=null;
		
		String task_id	=	Convert.toStr(param.get("task_id"));
		String sms_code	=	Convert.toStr(param.get("sms_code"));					//手机验证码
		String auth_code	=	Convert.toStr(param.get("auth_code"));				//图片验证码
		String identity_code	=	Convert.toStr(param.get("identity_code"));		//身份证号码
		String real_name	=	Convert.toStr(param.get("real_name"));				//真实姓名
		String user_pass	=	Convert.toStr(param.get("user_pass"));				//新服务密码
		String task_stage	=	Convert.toStr(param.get("task_stage"));
		
		if(StringUtils.isBlank(task_stage)){
			task_stage ="INIT";
		}
		
		StringBuilder body = new StringBuilder("task_id=");
			body.append(task_id);
		if(StringUtils.isNotBlank(sms_code)){
			body.append("&sms_code=");
			body.append(sms_code);			
		}
		if(StringUtils.isNotBlank(auth_code)){
			body.append("&auth_code=");
			body.append(auth_code);			
		}
		if(StringUtils.isNotBlank(identity_code)){
			body.append("&identity_code=");
			body.append(identity_code);			
		}
		if(StringUtils.isNotBlank(real_name)){
			body.append("&real_name=");
			body.append(real_name);			
		}
		if(StringUtils.isNotBlank(user_pass)){
			body.append("&user_pass=");
			body.append(user_pass);			
		}
			body.append("&task_stage=");
			body.append(task_stage);
		
		String bodyStr = body.toString();
		
		log.info(" TongDonService rstpwdSubmit request url:"+url+"queryParam:"+queryParam+",body："+bodyStr);
		String rs =HttpUtils.executeHttpPost(url, queryParam, headers, bodyStr);
		log.info(" TongDonService rstpwdSubmit response:"+rs);
		
		return rs; 		
	}
	
	
	

	
//	public static void main(String[] args) throws Exception {
//		String type=null;	
//		
//		type="create";
//		type="acquire";
////		type="retry";
////		type="query";	
////		type="rstpwdCreate";
////		type="rstpwdSubmit";
//		type ="moHeReport";
//
//		
//		TongDunService s = new TongDunService();
//		Map<String, Object> param =new HashMap<String, Object>();
//		
////		String phone	=	"18201857659";
////		String servicePassword=	"861477";
//		String name		=	"伍海涛";
//		String idCard	=	"340881198609226517";
//		
////		String phone	=	"18516329918";
////		String servicePassword=	"914129";
////		String name		=	"余兵兵";
////		String idCard	=	"362226199009140632";
//		
//		String phone	=	"13399533011";
//		String servicePassword=	"810235";
////		String name		=	"潘星";
////		String idCard	=	"340221199009050031";
//		
//		// 二次验证测试
////		String phone	=	"18087486480";
////		String servicePassword=	"150310";
////		String name		=	"余兵兵";
////		String idCard	=	"362226199009140632";
//		
//
//		String task_id    ="TASKYYS100000201801261538190661530970";
//		if("create".equals(type)){
//			//init方法测试
//			param.put("identity_code", idCard);
//			param.put("real_name", name);
//			param.put("user_mobile", phone);
//			param.put("user_name", phone);
//			param.put("user_pass", servicePassword);			
//			
//			String loginform =s.firstAcquire(param);
//			
//		}else if("acquire".equals(type)){
//			//提交表单验证
//			String sms_code="383704";
//			String auth_code="";
//			
//			String task_stage = "QUERY";
//			String request_type ="submit";
//			param.put("task_id", task_id);
//			param.put("user_name", phone);
//			param.put("user_pass", servicePassword);
//			param.put("sms_code", sms_code);
//			param.put("auth_code", auth_code);
//			param.put("task_stage", task_stage);
//			param.put("request_type", request_type);
//			s.acquire(param);
//		}else if("retry".equals(type)){
//			//验证码
//			param.put("task_id", task_id);
//			s.retry(param);
//		}else if("query".equals(type)){
//			//查询报告
//			param.put("task_id", task_id);
//			s.query(param);
//		}else if("rstpwdCreate".equals(type)){
//			//重置密码(创建)
//			param.put("user_mobile", phone);
//			s.rstpwdCreate(param);
//		}else if("rstpwdSubmit".equals(type)){
//			//重置密码(验证)
//			
//			String sms_code		=	"";				//手机验证码
//			String auth_code	=	"";				//图片验证码
//			String identity_code=	"";				//身份证号码
//			String real_name	=	"";				//真实姓名
//			String user_pass	=	"";				//新服务密码
//			String task_stage	=	"";
//			
//			
//			param.put("task_id", task_id);
//			param.put("sms_code", sms_code);
//			param.put("auth_code", auth_code);
//			param.put("identity_code", identity_code);
//			param.put("real_name", real_name);
//			param.put("user_pass", user_pass);
//			param.put("task_stage", task_stage);
//			s.rstpwdSubmit(param);
//		}else if("moHeReport".equals(type)){
//			//查询魔盒报告
//			param.put("task_id", task_id);
//			
//			String str = "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";  
//			
//			String sss = gunzip(str);
//			
//			System.out.println(sss);
//		}
//	}
	
	 
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
	 
	 
	 private JSONObject getJSONObjectFromJosn(JSONObject json,String key){
			JSONObject value=null;
			if(null!=json&&json.containsKey(key)){
				if(json.getJSONObject(key)!=null&&!json.getJSONObject(key).isEmpty()){
					
					value=json.getJSONObject(key);
				}
			}
			return value;
		}
	 

	 /**
	 *
	 * <p>Description:使用gzip进行解压缩</p>
	 * @param compressedStr
	 * @return
	 * @throws IOException 
	 */
	 public static String gunzip(String compressedStr) throws IOException{
		 try (ByteArrayOutputStream out= new ByteArrayOutputStream();) {
			 ByteArrayInputStream in=null;
			 GZIPInputStream ginzip=null;
			 byte[] compressed=null;
			 String decompressed = null;
			 try {
			 // 对返回数据BASE64解码
			 compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
			 in=new ByteArrayInputStream(compressed);
			 ginzip=new GZIPInputStream(in);
			 
			 // 解码后对数据gzip解压缩
			 byte[] buffer = new byte[1024];
			 int offset = -1;
			 while ((offset = ginzip.read(buffer)) != -1) {
			 out.write(buffer, 0, offset);
			 }
			 
			 // 最后对数据进行utf-8转码
			 decompressed=out.toString("utf-8");
			 return decompressed;
			} catch (IOException e) {
				log.error("gunzip error ", e);
				return "";
			}
		 }
	 }
}