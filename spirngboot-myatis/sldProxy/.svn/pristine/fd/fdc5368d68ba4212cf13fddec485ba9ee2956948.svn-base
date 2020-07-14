package com.ule.uhj.provider.yitu.service;

import java.security.MessageDigest;
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
import com.ule.uhj.sldProxy.util.HttpClientUtil;

public class ShangHuService {
	protected static Log log = LogFactory.getLog(ShangHuService.class);
	
	protected static String host 		=	"http://shenzhen.dev.jclinx.com/";
	protected static String key		=	"522ED5F7FE3E50FA367376C68CE786C2";
	protected static String sequence	=	"nFdZMkwlLYiHpPP6gknkmQ";
	
	protected static String merpicture="/merdata/merpicture"; 		//商户图片数据接口
	protected static String wxmerpipel="/merdata/wxmerpipel"; 		//商户微信流水接口
	protected static String alimerpipel="/merdata/alimerpipel"; 	//商户支付宝流水接口
	protected static String wingmerpipel="/merdata/wingmerpipel";	//商户翼支付流水接口
	
	
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
    	String response = HttpClientUtil.httpPostJson(url, paramMap);
//    	HttpEntity entity = response.getEntity();
//		if(200 != response.getStatusLine().getStatusCode() || Check.isBlank(entity)){
//    		System.out.println(String.format("response code:%s, response entity:%s", new Object[]{response.getStatusLine().getStatusCode(), entity}));
//    	}
//    	String entityStr = EntityUtils.toString(entity);
//    	String responseEntity = entityStr;
//    	if(!Check.isBlank(responseEntity)){
//    		try {
//    			Map map = new ObjectMapper().readValue(responseEntity, Map.class);
	    		System.out.println("****************"+response);
//	    		return JSONObject.toJSONString(map);
//			} catch (JsonParseException e) {
//				System.out.println(e.getMessage());
//			}
//    	}
		return response;
	}
	
		
	/**
	 * 商户图片数据接口
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String merpicture(Map<String,Object> param)throws Exception {
		String url		=	host+merpicture;
		String shopname	=	Convert.toStr(param.get("shopname"));	
		String signStr	=	"sequence="+	sequence	+"&"+
							"shopname="+	shopname	+"&"+
							"key="+key;
		
		String sign		=	MD5.md5Code(signStr).toUpperCase();
		
		log.info(" ShangHuService merpicture sign:"+sign);
		
		Map<String, Object> paramMap =new HashMap<String, Object>();
		paramMap.put("shopname", shopname);
		paramMap.put("sequence", sequence);
		paramMap.put("sign", sign);
		
		
		log.info(" ShangHuService merpicture request url:"+url+",paramMap："+paramMap.toString());
		String rs= sendRequest(url,null,"POST",paramMap);
		log.info(" ShangHuService merpicture response:"+rs);
		
		return rs; 
	}
	
	/**
	 * 商户微信流水接口
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String wxmerpipel(Map<String,Object> param)throws Exception {
		String url		=	host+wxmerpipel;
		String shopname	=	Convert.toStr(param.get("shopname"));	
		String starttime=	Convert.toStr(param.get("starttime"));	
		String endtime	=	Convert.toStr(param.get("endtime"));	
		
		String signStr	=	"endtime="	+	endtime		+"&"+
							"sequence="	+	sequence	+"&"+
							"shopname="	+	shopname	+"&"+
							"starttime="+	starttime	+"&"+							
							"key="+key;
//		String sign		=	MD5.md5Code(signStr).toUpperCase();
		String sign		=	MD5(signStr).toUpperCase();
		
		log.info(" ShangHuService merpicture sign:"+sign);
		
		Map<String, Object> paramMap =new HashMap<String, Object>();
		paramMap.put("shopname", shopname);
		paramMap.put("starttime", starttime);
		paramMap.put("endtime", endtime);
		paramMap.put("sequence", sequence);
		paramMap.put("sign", sign);
		
		
		log.info(" ShangHuService wxmerpipel request url:"+url+",paramMap："+paramMap.toString());
		String rs= sendRequest(url,null,"POST",paramMap);
		log.info(" ShangHuService wxmerpipel response:"+rs);
		
		return rs; 
	}
	
	/**
	 * 商户支付宝流水接口
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String alimerpipel(Map<String,Object> param)throws Exception {
		String url		=	host+alimerpipel;
		String shopname	=	Convert.toStr(param.get("shopname"));	
		String starttime	=	Convert.toStr(param.get("starttime"));	
		String endtime	=	Convert.toStr(param.get("endtime"));	
		
		String signStr	=	"endtime="	+	endtime		+"&"+
							"sequence="	+	sequence	+"&"+
							"shopname="	+	shopname	+"&"+
							"starttime="+	starttime	+"&"+							
							"key="+key;
		
		String sign		=	MD5.md5Code(signStr).toUpperCase();
		
		log.info(" ShangHuService alimerpipel sign:"+sign);
		
		Map<String, Object> paramMap =new HashMap<String, Object>();
		paramMap.put("shopname", shopname);
		paramMap.put("starttime", starttime);
		paramMap.put("endtime", endtime);
		paramMap.put("sequence", sequence);
		paramMap.put("sign", sign);
		
		
		log.info(" ShangHuService alimerpipel request url:"+url+",paramMap："+paramMap.toString());
		String rs= sendRequest(url,null,"POST",paramMap);
		log.info(" ShangHuService alimerpipel response:"+rs);
		
		return rs; 
	}
	
	/**
	 * 商户翼支付流水接口
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String wingmerpipel(Map<String,Object> param)throws Exception {
		String url		=	host+wingmerpipel;
		String shopname	=	Convert.toStr(param.get("shopname"));	
		String starttime	=	Convert.toStr(param.get("starttime"));	
		String endtime	=	Convert.toStr(param.get("endtime"));	
		
		String signStr	=	"endtime="	+	endtime		+"&"+
							"sequence="	+	sequence	+"&"+
							"shopname="	+	shopname	+"&"+
							"starttime="+	starttime	+"&"+							
							"key="+key;
		String sign		=	MD5.md5Code(signStr).toUpperCase();
		
		log.info(" ShangHuService wingmerpipel sign:"+sign);
		
		Map<String, Object> paramMap =new HashMap<String, Object>();
		paramMap.put("shopname", shopname);
		paramMap.put("starttime", starttime);
		paramMap.put("endtime", endtime);
		paramMap.put("sequence", sequence);
		paramMap.put("sign", sign);
		
		
		log.info(" ShangHuService wingmerpipel request url:"+url+",paramMap："+paramMap.toString());
		String rs= sendRequest(url,null,"POST",paramMap);
		log.info(" ShangHuService wingmerpipel response:"+rs);
		
		return rs; 
	}
	

	
//	public static void main(String[] args) throws Exception {
//		String type = "";	
//		String result = "";
////		type="merpicture"; 		//商户图片数据接口
//		type="wxmerpipel"; 		//商户微信流水接口
////		type="alimerpipel"; 	//商户支付宝流水接口
////		type="wingmerpipel";	//商户翼支付流水接口		
//		
//		ShangHuService s = new ShangHuService();
//		
//		
//		String[] starttimes = {"20180301000000","20180201000000","20180101000000","20171201000000","20171101000000","20171001000000"};
//		String[] endtimes = {"20180330000000","20180230000000","20180130000000","20171230000000","20171130000000","20171030000000"};
//		String[] inters = {"merpicture","wxmerpipel","alimerpipel","wingmerpipel"};
//		for(String inter : inters) {
//			
//			String name="";
//			if("merpicture".equals(inter)){
//				name="商户图片";
//			}else if("wxmerpipel".equals(inter)){
//				name="微信";
//			}else if("alimerpipel".equals(inter)){
//				name="支付宝";
//			}else if("wingmerpipel".equals(inter)){
//				name="翼支付";
//			}
//			System.out.println(name+"半年内的数据是：[ \n");
//			for(int i=0; i < starttimes.length; i++) {
//				String starttime = starttimes[i];
//				String endtime = endtimes[i];
//				Map<String, Object> param =new HashMap<String, Object>();
//				param.put("shopname", "春风十里不如你");
//				param.put("starttime", starttime);
//				param.put("endtime", endtime);
//				
//				if("merpicture".equals(inter)){
//					result = s.merpicture(param);
//				}else if("wxmerpipel".equals(inter)){
//					result = s.wxmerpipel(param);
//				}else if("alimerpipel".equals(inter)){
//					result = s.alimerpipel(param);
//				}else if("wingmerpipel".equals(inter)){
//					result = s.wingmerpipel(param);
//				}
//				System.out.println(result+"\n");
//				
//			}
//			System.out.println("\n ]\n-------------------------------------------------");
//		}
//	}
	
	 
	private static String MD5(String s) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] bytes = md.digest(s.getBytes("utf-8"));
	        return toHex(bytes);
	    }
	    catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	private static String toHex(byte[] bytes) {

    final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    StringBuilder ret = new StringBuilder(bytes.length * 2);
	    for (int i=0; i<bytes.length; i++) {
	        ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
	        ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
	    }
    return ret.toString();
	}
}
