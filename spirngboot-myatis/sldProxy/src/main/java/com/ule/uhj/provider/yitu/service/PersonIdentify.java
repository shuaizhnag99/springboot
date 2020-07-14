package com.ule.uhj.provider.yitu.service;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.ule.uhj.provider.yitu.util.FileHelper;

/**
 * CS104.1 接口功能
验证身份证号码和姓名是否匹配, 匹配成功时获取公安部联网水印照
注意：此接口调用成功即计费
 * @author zhangyaou
 *
 */
public class PersonIdentify extends AbstractYiTu{
	
	private static String url = ip + "/face/v1/application/person_identify";
	
	public static String getIdentifyImage(Map<String, Object> paras) throws Exception {
		String name =(String)paras.get("name");
		String citizen_id=(String)paras.get("citizen_id");
		boolean remove_watermark=Boolean.parseBoolean(paras.get("remove_watermark").toString());  ;
		/*
		 * "李国庆", "420704197409260058", true
		 * Step 1 生成HTTP body 
		 */
//		String databaseImageContent = FileHelper.getImageBase64Content("D:/dou/works/ule2/selfUtil/src/main/resources/sample_images/id.jpg"); //  设置上传图片并编码为base64字符串
//		int databaseImageType = 1; // 设置上传图片的类型，类型编号请参见接口文档

//		String queryImageContent = FileHelper.getImageBase64Content("D:/dou/works/ule2/selfUtil/src/main/resources/sample_images/id_test.jpg"); // 设置查询照图片并编码为base64字符串
//		int queryImageType = 301; // 设置查询照图片类型
		
	    ObjectMapper mapper = new ObjectMapper();
	    Map<String, Object> requestData = new HashMap<String, Object>();
	    requestData.put("name", name);
	    requestData.put("citizen_id", citizen_id);
	    requestData.put("remove_watermark", remove_watermark);
	    
	    String requestBodyString = mapper.writeValueAsString(requestData);
		
		/*
		 * Step 3 发送 HTTP请求 
		 */
	    
	    String result = send(url, requestBodyString);
	    return result;
		/*
		 * Step 4 校验答案
		 */
//		JSONObject responseJson = new JSONObject(result);
//		Map<?, ?> responseJson = new ObjectMapper().readValue(result, Map.class);
//		System.out.println(responseJson);
//		int rtn = (Integer) responseJson.get("rtn");
//		String message = (String)responseJson.get("message");
//		boolean identify_result = (Boolean)responseJson.get("identify_result");
//		String origin_image_content = (String)responseJson.get("origin_image_content");
//		String processed_image_content = (String)responseJson.get("processed_image_content");
//		
//		if (rtn == 0) { //0表示OK，<0表示服务器发生错误。
//			System.out.println(String.format("name match identifyNo: %s", new Object[]{identify_result}));
//			FileHelper.saveImageStr2File(origin_image_content, "D:/tmp/yitu/origin");
//			FileHelper.saveImageStr2File(processed_image_content, "D:/tmp/yitu/process");
//		} else {
//			System.err.println(message);
//		}
	}


//	CS104.1 接口功能
//	验证身份证号码和姓名是否匹配, 匹配成功时获取公安部联网水印照
//	public static void main(String[] args) throws Exception {
//		Map<String, Object> paras =new HashMap<String, Object>();
//		paras.put("name", "赵杰");
//		paras.put("citizen_id", "34032119871001825X");
//		paras.put("remove_watermark", false);
		//马含玉 430201199206207129  康幼荷 610526198912268546
//		System.out.println(new PersonIdentify().getIdentifyImage("李国庆", "420704197409260058", false));
//		System.out.println(new PersonIdentify().getIdentifyImage("刘明辉", "320829196808131037", false));
//		System.out.println(new PersonIdentify().getIdentifyImage("朱华", "320919197510215470", false));
//		String result = new PersonIdentify().getIdentifyImage("朱华", "320919197510215470", true);
//		String result = new PersonIdentify().getIdentifyImage("刘明辉", "320829196808131037", true);
//		String result = new PersonIdentify().getIdentifyImage("娄文娥", "330622197208125726", true);
//		String result = new PersonIdentify().getIdentifyImage("丁如军", "32091919621231373X", true);
//		String result = new PersonIdentify().getIdentifyImage(paras);
		
		
//		FileHelper.saveImageStr2File(new PersonIdentify().getIdentifyImage("朱华", "320919197510215470", false), "D:/tmp/zhuhua.jpg");
		
		
//		Map<?, ?> responseJson = new ObjectMapper().readValue(result, Map.class);
//		System.out.println(responseJson);
//		int rtn = (Integer) responseJson.get("rtn");
//		String message = (String)responseJson.get("message");
//		boolean identify_result = (Boolean)responseJson.get("identify_result");
//		String origin_image_content = (String)responseJson.get("origin_image_content");
//		String processed_image_content = (String)responseJson.get("processed_image_content");
//		
//		if (rtn == 0) { //0表示OK，<0表示服务器发生错误。
//			System.out.println(String.format("name match identifyNo: %s", new Object[]{identify_result}));
//			FileHelper.saveImageStr2File(origin_image_content, "D:/520.jpg");
//			FileHelper.saveImageStr2File(processed_image_content, "D:/521.jpg");
//		} else {
//			System.err.println(message);
//		}
//		
//		
//		System.out.println("end");
//		
//	}
	
}
