package com.ule.uhj.provider.yitu.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ule.tools.creditService.client.CreditServiceTools;
import com.ule.uhj.provider.yitu.util.ImgUrlUtils;

public class SampleCodeV3PairVerify extends AbstractYiTu {

	private static Logger log = LoggerFactory
			.getLogger(SampleCodeV3PairVerify.class);

	private static String yitu_url = ip + "/face/composite/verification";

	private static String yitu_url2 = ip + "/face/v1/algorithm/recognition/face_pair_verification";


//	public static void main(String[] args) throws Exception {
//		PairVerifyImgOrImg3(null);
//	}
//	public static String PairVerifyImgOrImg(Map<String, Object> paras)
//			throws Exception {
//
//		String idCardImgUrl = (String) paras.get("idCardImgUrl");// 身份证照的url地址，需要转换成base64
//		// String verifyImgUrl = (String) paras.get("verifyImgUrl");
//		String idCardImage_content = getBase46StringByUrl(idCardImgUrl);
//		String iverifyImage_content = (String) paras.get("verifyImg_content");// verifyImg_content
//		// base64
//
//		// 测试
//		// String idCardImage_content =
//		// getBase46StringByUrl("http://pic.beta.ule.com/pic/user_10000026049/product/prd20170825/app_idcardpositive15036261590742602.jpg");
//		// String iverifyImage_content =
//		// getBase46StringByUrl("http://pic.beta.ule.com/pic/user_10000026049/product/prd20170825/app_selfface15036262170074961.jpg");
//
//		// 身份证翻拍照Base64编码
//		JSONObject upload_idcard_image = new JSONObject();
//		JSONObject user_info = new JSONObject();
//		JSONObject options = new JSONObject();
//		user_info.put("image_content", idCardImage_content);
//		options.put("auto_rotate", true);
//		upload_idcard_image.put("user_info", user_info);
//		upload_idcard_image.put("options", options);
//
//		// 现场查询照Base64编码
//		JSONObject verify_query_image = new JSONObject();
//		JSONObject verify_user_info = new JSONObject();
//		JSONObject verify_options = new JSONObject();
//
//		verify_user_info.put("image_content", iverifyImage_content);
//		verify_options.put("auto_rotate", true);
//		verify_options.put("true_negative_rate", "99.99");
//		verify_query_image.put("user_info", verify_user_info);
//		verify_query_image.put("options", verify_options);
//
//		JSONObject requestBody = new JSONObject();
//		requestBody.put("upload_idcard_image", upload_idcard_image);
//		requestBody.put("verify_query_image", verify_query_image);
//		String result = send(yitu_url, requestBody.toString());
//		System.out.println(result);
//		return result;
//	}



	//	{
//		"database_image_content": "BASE64编码的JPG登记照片图片内容",
//		"database_image_type": 101,
//		"query_image_content": "BASE64编码的JPG比对照片图片内容",
//		"query_image_type": 301,
//		"true_negative_rate": "99.9"
//		}
//
	public static String PairVerifyImgOrImg2(Map<String, Object> paras)
			throws Exception {

		String appSelffaceImgUrl = (String) paras.get("appSelffaceImgUrl");// 人脸照片的url地址，需要转换成base64
		String appSelfface_content = getBase46StringByUrl(appSelffaceImgUrl);
		String iverifyImage_content = (String) paras.get("verifyImg_content");// verifyImg_content  base64

		// 测试
//		 String appSelfface_content =
//		 getBase46StringByUrl("http://pic.beta.ule.com/pic/user_10000026049/product/prd20170825/app_selfface15036262170074961.jpg");
//		 String iverifyImage_content =
//		 getBase46StringByUrl("http://pic.beta.ule.com/pic/user_10000026049/product/prd20170825/app_selfface15036262170074961.jpg");

		JSONObject requestBody = new JSONObject();
		requestBody.put("database_image_content", appSelfface_content);		//BASE64编码的JPG登记照片图片内容
		requestBody.put("database_image_type", 3);
		requestBody.put("query_image_content", iverifyImage_content);		//BASE64编码的JPG比对照片图片内容
		requestBody.put("query_image_type", 301);
		requestBody.put("true_negative_rate", "99.99");						//期望的负例的recall

		String result = send(yitu_url2, requestBody.toString());
		System.out.println(result);
		return result;
	}

	/**
	 * 店铺照片与人脸照比对
	 * @param paras
	 * @return
	 * pair_verify_similarity 	【double】		相似值
	 * verify_detail：
	 * 		score_list 			【array】			查询照每张人脸分别和登记照人脸比对的分数，按从高到低排序
	 * 		query_face_rect		【array】			查询照每张人脸的矩形位置列表，与score_list中的每个比对分数一一对应
	 * @throws Exception
	 */
	public static String PairVerifyImgOrImg3(Map<String, Object> paras)
			throws Exception {

		String appSelffaceImgUrl = (String) paras.get("appSelffaceImgUrl");		//人脸照片的url地址，需要转换成base64
		String appStoreinnerImgUrl = (String) paras.get("appStoreinnerImgUrl");	//店铺内照片的url地址，需要转换成base64

		String appSelfface_content =getBase46StringByUrl(appSelffaceImgUrl);
		String iverifyImage_content =getBase46StringByUrl(appStoreinnerImgUrl);

		//测试
//		String appSelfface_content =getBase46StringByUrl("http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_selfface15032154005222726.jpg");
//		String iverifyImage_content =getBase46StringByUrl("http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg");

		JSONObject requestBody = new JSONObject();
		requestBody.put("database_image_content", appSelfface_content);		//BASE64编码的JPG登记照片图片内容
		requestBody.put("database_image_type", 3);
		requestBody.put("query_image_content", iverifyImage_content);		//BASE64编码的JPG比对照片图片内容
		requestBody.put("query_image_type", 301);


		requestBody.put("max_faces_allowed", 2);							//查询照片中至多有几个人脸
		requestBody.put("enable_verify_detail", true);						//是否返回比对的详细信息
		requestBody.put("return_face_rect", true);							//是否返回查询照每张人脸的位置
		requestBody.put("true_negative_rate", "99.99");						//期望的负例的recall
		requestBody.put("auto_rotate_for_query", true);						//开启查询照的自动旋转识别
		requestBody.put("auto_rotate_for_database", true);					//开启登记照的自动旋转识别
		/*String result = send(yitu_url2, requestBody.toString());
		return result;*/

		String result=CreditServiceTools.imageCompare(appSelfface_content, iverifyImage_content);
		log.info("yitu 3107 图片地址-人脸图与合影图校验相识度结果 result="+result);
		log.info("PairVerifyImgOrImg3  基础服务部返回 entityStr"+result.toString());
		return result;
//		JSONObject jsonObject = JSONObject.fromObject(result);
//		if(jsonObject.get("code").equals("200") && !jsonObject.get("data").equals("")){
//			String entityStr = jsonObject.get("data").toString();
//	    	log.info("PairVerifyImgOrImg3 entityStr"+entityStr);
//			return entityStr;
//		}else{
//			throw new Exception("yitu 3107 图片地址-人脸图与合影图校验相识度结果查询失败");
//		}
	}

	public static String getBase46StringByUrl(String imgUrl) {

		String image_content = null;
		URL url = null;
		InputStream is = null;
		ByteArrayOutputStream outStream = null;
		HttpURLConnection httpUrl = null;
		try {
			url = new URL(ImgUrlUtils.toInnerImgUrl(imgUrl));
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

		return image_content;
	}
}