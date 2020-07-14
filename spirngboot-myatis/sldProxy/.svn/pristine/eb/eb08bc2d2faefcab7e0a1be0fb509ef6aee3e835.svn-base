package com.ule.uhj.provider.yitu.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import com.ule.tools.creditService.client.CreditServiceTools;
import com.ule.uhj.provider.yitu.util.FileHelper;

/**
 * OCR 通用接口
 * 识别用户上传的证件照片并返回识别结果
 * @author zhangyaou
 *
 */
public class OcrPersonIdentify extends AbstractYiTu{

	protected static Log log = LogFactory.getLog(OcrPersonIdentify.class);
	private static String url = ip + "/face/basic/ocr";


	public static String ocrPersonIdentifyImage(Map<String, Object> paras) throws Exception {
		String image_content = (String)paras.get("imageContent");
		String type = (String)paras.get("type");
		String result;
		String entityStr;
		if(type.equals("app_IdCardPositive") || type.equals("app_spouseICPositive")){
			result=CreditServiceTools.ocrFront(image_content);
		}else{
			result=CreditServiceTools.ocrBack(image_content);
		}
		log.info("ocrPersonIdentifyImage 基础服务部返回 result"+result.toString());
		return result;
//		JSONObject jsonObject = JSONObject.fromObject(result);
//		if(jsonObject.get("code").equals("200") && !jsonObject.get("data").equals("")){
//			entityStr = jsonObject.get("data").toString();
//			log.info("ocrPersonIdentifyImage result"+entityStr);
//			log.info("yitu 3101 身份证识别结果 entityStr="+entityStr);
//			return entityStr;
//		}else{
//			throw new Exception("图片内容-亿图身份证OCR正反面-已接基础服务组");
//		}

	/*
		 * Step 1 生成HTTP body

//		String databaseImageContent = FileHelper.getImageBase64Content("D:/dou/works/ule2/selfUtil/src/main/resources/sample_images/id.jpg"); //  设置上传图片并编码为base64字符串
//		int databaseImageType = 1; // 设置上传图片的类型，类型编号请参见接口文档

//		String queryImageContent = FileHelper.getImageBase64Content("D:/dou/works/ule2/selfUtil/src/main/resources/sample_images/id_test.jpg"); // 设置查询照图片并编码为base64字符串
//		int queryImageType = 301; // 设置查询照图片类型

	    ObjectMapper mapper = new ObjectMapper();
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


	    String result = send(url, requestBodyString);*/

		/*
		 * Step 4 校验答案

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
*/	}



	//识别用户上传的证件照片并返回识别结果
//	ocr_type int 必填 识别的类型， 1: 翻拍身份证照  
//	auto_rotate bool 非必填   开启自动旋转矫正(目前只支持身份证OCR) 否false
//	ocr_mode int   当ocr_type = 1时 必填，1: 身份证正面识别 人员信息页面   2: 身份证背面识别  国徽  3: auto 自动区分身份证正面背面
//	public static void main(String[] args) throws Exception {
//		String filePath = "D:/certpos14750572197288854.jpg";
////		String imageContent = FileHelper.getImageBase64Content(String.format(filePath, new Object[]{"linfang_zheng"}));
//		String imageContent = FileHelper.getImageBase64Content(String.format(filePath, new Object[]{"linfang_fan"}));
//		//{"global_request_id": "eSkrdIMOyQkhSwUAIxMAAAAAAAAdegEA", 
////		"idcard_ocr_result": {"agency": "东台市公安局", "idcard_type": 2, "valid_date_begin": "20070515", "valid_date_end": "20270515"}, 
////		"message": "OK", "rtn": 0}
//
////		{"global_request_id": "eBqNVKiB/R0hSwUAMkIAAAAAAADEjQEA", 
////			"idcard_ocr_result": {"address": "江苏省东台市南沈灶镇常灶村三组61号", "birthday": "1966.04.28", 
////		"citizen_id": "320919196604284480", "gender": "女", "idcard_type": 1, "name": "林芳", "nation": "汉"}, 
////			"message": "OK", "rtn": 0}
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("imageContent", imageContent);
//		map.put("type", "app_IdCardPositive");
//		String result = new OcrPersonIdentify().ocrPersonIdentifyImage(map);
//		System.out.println(result);
//	}

}
