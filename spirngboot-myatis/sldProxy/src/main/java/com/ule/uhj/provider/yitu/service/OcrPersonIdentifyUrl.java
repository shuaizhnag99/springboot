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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import com.ule.tools.creditService.client.CreditServiceTools;
import com.ule.uhj.provider.yitu.util.ImgUrlUtils;

/**
 * OCR 通用接口
 * 识别用户上传的证件照片并返回识别结果
 * @author zhangyaou
 *
 */
public class OcrPersonIdentifyUrl extends AbstractYiTu{
	protected static Log log = LogFactory.getLog(OcrPersonIdentifyUrl.class);

	private static String ocr_url = ip + "/face/basic/ocr";


	public static String ocrPersonIdentifyImage(Map<String, Object> paras) throws Exception {
		String idCardImgUrl= (String)paras.get("idCardImgUrl");
		idCardImgUrl = ImgUrlUtils.toInnerImgUrl(idCardImgUrl);
		String image_content =null;

		/*
		 * Step 1 生成HTTP body
		 */
//		String databaseImageContent = FileHelper.getImageBase64Content("D:/dou/works/ule2/selfUtil/src/main/resources/sample_images/id.jpg"); //  设置上传图片并编码为base64字符串
//		int databaseImageType = 1; // 设置上传图片的类型，类型编号请参见接口文档

//		String queryImageContent = FileHelper.getImageBase64Content("D:/dou/works/ule2/selfUtil/src/main/resources/sample_images/id_test.jpg"); // 设置查询照图片并编码为base64字符串
//		int queryImageType = 301; // 设置查询照图片类型

		URL url = null;
		InputStream is = null;
		ByteArrayOutputStream outStream = null;
		HttpURLConnection httpUrl = null;
		int length=0;
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
			length=outStream.toByteArray().length;
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
		System.out.println("image_content.length()"+length);
		if(length>2000000){
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("rtn", "1000");
			res.put("msg", "图片过大，需要重新拍照");
			System.out.println("image_content.length()"+res.toString());
			return res.toString();
		}

	    /*ObjectMapper mapper = new ObjectMapper();
	    Map<String, Object> requestData = new HashMap<String, Object>();
	    Map<String, Object> user_info = new HashMap<String, Object>();
	    Map<String, Object> options = new HashMap<String, Object>();
	    user_info.put("image_content", image_content);
	    options.put("ocr_type", 1);
	    options.put("auto_rotate", true);
	    options.put("ocr_mode", 3);
	    requestData.put("user_info", user_info);
	    requestData.put("options", options);
	    String requestBodyString = mapper.writeValueAsString(requestData);
//		System.out.println(requestBodyString);

		 * Step 3 发送 HTTP请求
		 */

//	    String result = send(ocr_url, requestBodyString);
		String result=CreditServiceTools.ocrFront(image_content);
		log.info("yitu 3108 身份证正面识别结果 result="+result);
		log.info("ocrPersonIdentifyImage 基础服务部返回 entityStr"+result.toString());
		JSONObject jsonObject = JSONObject.fromObject(result);
		if(jsonObject.get("code").equals("200") && !jsonObject.get("data").equals("")){
			String entityStr = jsonObject.get("data").toString();
			log.info("ocrPersonIdentifyImage entityStr"+entityStr);
			return entityStr;
		}else{
			throw new Exception("yitu 3108 身份证正面识别结果查询失败");
		}

		/*
		 * Step 4 校验答案
		 */
//		JSONObject responseJson = new JSONObject(result);
//		Map<?, ?> responseJson = new ObjectMapper().readValue(result, Map.class);
//		System.out.println(responseJson);
//		int rtn = (Integer) responseJson.get("rtn");
//		String message = (String)responseJson.get("message");
//
//		Map<?,?> idcard_ocr_result = (Map<?, ?>) responseJson.get("idcard_ocr_result");
//		if (rtn == 0) { //0表示OK，<0表示服务器发生错误。
//			System.out.println(idcard_ocr_result);
//		} else {
//			System.err.println(message);
//		}
	}



	//识别用户上传的证件照片并返回识别结果
//	ocr_type int 必填 识别的类型， 1: 翻拍身份证照  
//	auto_rotate bool 非必填   开启自动旋转矫正(目前只支持身份证OCR) 否false
//	ocr_mode int   当ocr_type = 1时 必填，1: 身份证正面识别 人员信息页面   2: 身份证背面识别  国徽  3: auto 自动区分身份证正面背面
//	public static void main(String[] args) throws Exception {
//		String filePath = "D:/id.jpg";
////		String imageContent = FileHelper.getImageBase64Content(String.format(filePath, new Object[]{"linfang_zheng"}));
//		String idCardImgUrl = "http://file.ule.com/file/app_uhj/uppic1003099307/certpos14736505626413224.jpg";
////		String idCardImgUrl = "http://pic.ule.com/pic/user_1006135739/product/prd20171017/app_idcardpositive15082073335956447.jpg";
//		//{"global_request_id": "eSkrdIMOyQkhSwUAIxMAAAAAAAAdegEA", 
////		"idcard_ocr_result": {"agency": "东台市公安局", "idcard_type": 2, "valid_date_begin": "20070515", "valid_date_end": "20270515"}, 
////		"message": "OK", "rtn": 0}
//
////		{"global_request_id": "eBqNVKiB/R0hSwUAMkIAAAAAAADEjQEA", 
////			"idcard_ocr_result": {"address": "江苏省东台市南沈灶镇常灶村三组61号", "birthday": "1966.04.28", 
////		"citizen_id": "320919196604284480", "gender": "女", "idcard_type": 1, "name": "林芳", "nation": "汉"}, 
////			"message": "OK", "rtn": 0}
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("idCardImgUrl", idCardImgUrl);
//		String result = new OcrPersonIdentifyUrl().ocrPersonIdentifyImage(map);
//		System.out.println(result);
//	}

}