package com.ule.uhj.provider.yitu.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ule.tools.creditService.client.CreditServiceTools;
import com.ule.uhj.provider.yitu.util.FileHelper;
import com.ule.uhj.provider.yitu.util.ImgUrlUtils;

public class SampleCodeV1PairVerify extends AbstractYiTu{
	
	private static Logger log = LoggerFactory.getLogger(SampleCodeV1PairVerify.class);

	private static String yitu_url = ip + "/face/composite/verification";
	
	/**
	 * 身份证照片与人脸大礼包相似度比较
	 */
	public static String PairVerify(Map<String, Object> paras) throws Exception {
		
		String idCardImgUrl = (String) paras.get("idCardImgUrl");//身份证照的url地址，需要转换成base64
		
		idCardImgUrl =ImgUrlUtils.toInnerImgUrl(idCardImgUrl);
		
		log.info("PairVerify idCardImgUrl:"+idCardImgUrl);
		String query_image_package = (String) paras.get("query_image_package");
		
		StringBuilder sb =  new StringBuilder("databaseImageContent size:").append(idCardImgUrl.length()).append(", queryImageContent size:").append(query_image_package.length());
		log.info(sb.toString());
		
		if(idCardImgUrl.trim().length() == 0 || query_image_package.trim().length() == 0){
			throw new Exception("PairVerify image str is empty!");
		}
		String image_content=null;
		URL url = null;
		InputStream is = null;
		ByteArrayOutputStream outStream = null;
		HttpURLConnection httpUrl = null;
		try {
			url = new URL(idCardImgUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			httpUrl.getInputStream();
			is = httpUrl.getInputStream();
			outStream = new ByteArrayOutputStream();
			// 创建一个Buffer字符串
			byte[] buffer = new byte[1024];
			// 每次读取的字符串长度，如果为-1，代表全部读取完毕
			int len = 0;
			// 使用一个输入流从buffer里把数据读取出来
			while ((len = is.read(buffer)) != -1) {
				// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
			// 对字节数组Base64编码
			image_content = Base64.encodeBase64String(outStream.toByteArray());
		} catch (Exception e) {
			log.error("Exception error.", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("Exception error.", e);
				}
			}
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					log.error("Exception error.", e);
				}
			}
			if (httpUrl != null) {
				httpUrl.disconnect();
			}
		}
		JSONObject upload_idcard_image=new JSONObject();
		JSONObject user_info=new JSONObject();
		JSONObject options=new JSONObject();
		user_info.put("image_content", image_content);
		options.put("auto_rotate", true);
		upload_idcard_image.put("user_info", user_info);
		upload_idcard_image.put("options", options);
		
		JSONObject verify_query_image=new JSONObject();
		JSONObject verify_user_info=new JSONObject();
		JSONObject verify_options=new JSONObject();
		verify_user_info.put("query_image_package", query_image_package);
		verify_options.put("auto_rotate", true);
		verify_options.put("auto_flip", true);
		verify_options.put("true_negative_rate", "99.99");
		verify_options.put("query_image_package_return_image_list", true);
		verify_options.put("query_image_package_check_same_person", true);
		verify_options.put("query_image_package_check_anti_screen", true);
		verify_options.put("query_image_package_check_anti_screen_threshold", "high");
		verify_query_image.put("user_info", verify_user_info);
		verify_query_image.put("options", verify_options);
		
		JSONObject requestBody=new JSONObject();
		requestBody.put("upload_idcard_image", upload_idcard_image);
		requestBody.put("verify_query_image", verify_query_image);
	    String result = send(yitu_url, requestBody.toString());
	    return result;
	}
	
	/**
	 * 亿图姓名、身份证号与人脸大礼包相似度比较
	 */
	public static String PairVerifyForName(Map<String, Object> paras) throws Exception {
		
		String name=(String) paras.get("name");
		String citizen_id=(String) paras.get("citizen_id");
		log.info("PairVerifyForName name:"+name+";citizen_id:"+citizen_id);
		String query_image_package = (String) paras.get("query_image_package");
		
//		JSONObject upload_idcard_image=new JSONObject();
//		JSONObject user_info=new JSONObject();
//		JSONObject options=new JSONObject();
//		user_info.put("name", name);
//		user_info.put("citizen_id", citizen_id);
//		options.put("auto_rotate", true);
//		upload_idcard_image.put("user_info", user_info);
//		upload_idcard_image.put("options", options);
//		
//		JSONObject verify_query_image=new JSONObject();
//		JSONObject verify_user_info=new JSONObject();
//		JSONObject verify_options=new JSONObject();
//		verify_user_info.put("query_image_package", query_image_package);
//		verify_options.put("auto_rotate", true);
//		verify_options.put("auto_flip", true);
//		verify_options.put("true_negative_rate", "99.99");
//		verify_options.put("query_image_package_return_image_list", true);
//		verify_options.put("query_image_package_check_same_person", true);
//		verify_options.put("query_image_package_check_anti_screen", true);
//		verify_options.put("query_image_package_check_anti_screen_threshold", "high");
//		verify_query_image.put("user_info", verify_user_info);
//		verify_query_image.put("options", verify_options);
//		
//		JSONObject requestBody=new JSONObject();
//		requestBody.put("upload_idcard_image", upload_idcard_image);
//		requestBody.put("verify_query_image", verify_query_image);
//	    String result = send(yitu_url, requestBody.toString());
//	    return result;
		
		
		
		String result=CreditServiceTools.twoElementFace(name,citizen_id,"face",query_image_package);
		log.info("yitu 3100 亿图姓名和身份证号与身份证图片(或者人脸)校验是否一致，也可校验姓名与身份证号是否一致结果 result="+result);
		log.info("PairVerify 基础服务部返回 entityStr"+result.toString());
		JSONObject jsonObject = JSONObject.fromObject(result);
		if(jsonObject.get("code").equals("200") && !jsonObject.get("data").equals("")){
			String entityStr = jsonObject.get("data").toString();
			log.info("PairVerify entityStr"+entityStr);
			return entityStr;
		}else{
			throw new Exception("yitu 3100 亿图姓名和身份证号与身份证图片(或者人脸)校验是否一致，也可校验姓名与身份证号是否一致结果查询失败");
		}
	}


//	public static void main(String[] args) throws Exception {
//		String idCardImgUrl = "http://pic.beta.ule.com/pic/user_10000025652/product/prd20170602/app_idcardpositive14963893086341500.jpg"; //  设置上传图片并编码为base64字符串
//		String query_image_package = FileHelper.getImageBase64Content("D:/zhaojie.jpg"); // 设置查询照图片并编码为base64字符串
//		Map<String, Object> paras = new HashMap<String, Object>();
//		paras.put("idCardImgUrl", idCardImgUrl);
//		paras.put("query_image_package", query_image_package);
//		String result = new SampleCodeV1PairVerify().PairVerify(paras);
//		System.out.println(result);
//	}
}