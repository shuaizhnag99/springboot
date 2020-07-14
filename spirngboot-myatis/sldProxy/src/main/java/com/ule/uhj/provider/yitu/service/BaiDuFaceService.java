package com.ule.uhj.provider.yitu.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import com.ule.tools.creditService.client.CreditServiceTools;
import com.ule.uhj.provider.yitu.util.baiduface.*;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.support.json.JSONUtils;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sldProxy.util.PropertiesHelper;


public class BaiDuFaceService{
	private static Logger log = LoggerFactory.getLogger(BaiDuFaceService.class);
	
	private static String BaiDuAI_Face ="face";
	private static String BaiDuAI_OCR ="OCR";
	
	public static final String APIKey=PropertiesHelper.getDfs("BaiDuFace_API_Key");
	public static final String secretKey=PropertiesHelper.getDfs("BaiDuFace_Secret_Key");
	
	public static final String BaiDuAIOCRKey=PropertiesHelper.getDfs("BaiDuAI_OCR_API_Key");
	public static final String BaiDuAIOCRSecretKey=PropertiesHelper.getDfs("BaiDuAI_OCR_Secret_Key");
	
	
	/**
	 * 人脸更新[百度人脸库中]
	 * param groupId  用户组
	 * param userOnlyId
	 * param imgBase64
	 * @return
	 */
	public static String addFace(Map<String, Object> param) {

		log.info("已进去人脸更新方法addFace  ");
		String groupId = Convert.toStr(param.get("groupId"));
		if("beta".equals(PropertiesHelper.getDfs("env"))|| "testing".equals(PropertiesHelper.getDfs("env"))){
			String[] ids=groupId.split(",");
			for(int i=0;i<ids.length;i++){
				ids[i]=ids[i]+"_test";
			}
			groupId=String.join(",", ids);
		}
		String userOnlyId = Convert.toStr(param.get("userOnlyId"));
		String imgurl = Convert.toStr(param.get("imgurl"));
		Map<String, Object> rs= new HashMap<String, Object>();
		try {
			if(imgurl.startsWith("http")) {
				byte[] imgData = FileUtil.getBase46StringByUrl(imgurl);
				imgurl = Base64Util.encode(imgData);
			}
			Map<String, Object> map = new HashMap<>();
			map.put("image", imgurl);
			map.put("group_id", groupId);
			map.put("user_id", userOnlyId);
			map.put("image_type", "BASE64");
			map.put("action_type","REPLACE");
			String responseStr=CreditServiceTools.bdFaceSet(map);
			log.info("调用基础服务部人脸更新方法addFace  response"+responseStr);
			Map<String, Object> object = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(responseStr);
			if(object.get("code").equals("0000") && !object.get("data").equals("")){
				log.info("addFace result="+object.get("data"));
				rs.put("status", "success");
				rs.put("data", object.get("data").toString());
			}
		} catch (Exception e) {
			rs.put("status", "fail");
			rs.put("msg", "百度人脸库更新失败");
		}
		return JSONUtils.toJSONString(rs);
	}
	
	
	/**
	 * 人脸查找[百度人脸库中] 1:N
	 * param imgurl 图片地址  "http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg";
	 * @return
	 */
	public static String faceIdentify(Map<String, Object> param) {
		String imgurl = Convert.toStr(param.get("imgurl"));
		
		Map<String, Object> rs= new HashMap<String, Object>();
		try {
			String accessToken =getAuth(BaiDuAI_Face);
			log.info("faceIdentify {accessToken="+accessToken+", imgurl="+imgurl);
			
			String data = FaceIdentify.identify(accessToken, imgurl);
			
			rs.put("status", "success");
			rs.put("data", data);
		} catch (Exception e) {
			rs.put("status", "fail");
			rs.put("msg", "人脸查找失败");
		}
		return JSONUtils.toJSONString(rs);
	}
	
	/**
	 * 人脸查找[百度人脸库中] M:N
	 * param imgurl 图片地址  "http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg";
	 * @return
	 */
	public static String faceMultiIdentify(Map<String, Object> param) {
		String imgurl = Convert.toStr(param.get("imgurl"));
		String groupId = Convert.toStr(param.get("groupId"));
		if("beta".equals(PropertiesHelper.getDfs("env"))|| "testing".equals(PropertiesHelper.getDfs("env"))){
			String[] ids=groupId.split(",");
			for(int i=0;i<ids.length;i++){
				ids[i]=ids[i]+"_test";
			}
			groupId=String.join(",", ids);
		}
		Map<String, Object> rs= new HashMap<String, Object>();
		try {
			if(imgurl.startsWith("http")) {
				byte[] imgData = FileUtil.getBase46StringByUrl(imgurl);
				imgurl = Base64Util.encode(imgData);
			}
			Map<String, Object> map = new HashMap<>();
			map.put("image", imgurl);
			map.put("group_id_list", groupId);
			map.put("image_type", "BASE64");
			map.put("max_face_num", 2);
			map.put("match_threshold", 30);
			map.put("liveness_control", "NONE");
			map.put("quality_control", "NONE");
			map.put("max_user_num", 20);
			log.info("人脸查找[百度人脸库中] M:N param"+groupId);
			String responseStr=CreditServiceTools.bdFaceMultiSearch(map);
			log.info("调用基础服务部百度智能 人脸识别-人脸搜索（m：n） faceMultiIdentify   response"+responseStr);
			Map<String, Object> object = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(responseStr);
			if(object.get("code").equals("0000") && !object.get("data").equals("")){
				log.info("faceMultiIdentify result="+object.get("data"));
				rs.put("status", "success");
				rs.put("data", object.get("data").toString());
			}else{
				rs.put("status", "fail");
				rs.put("msg", "人脸查找失败");
				rs.put("data", object.get("data").toString());
			}
		} catch (Exception e) {
			rs.put("status", "fail");
			rs.put("msg", "人脸查找失败");
		}
		return JSONUtils.toJSONString(rs);
	}
	
	/**
	 * 人脸比对 1:1
	 * param imgurl1 图片地址  "http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg";
	 *        imgurl2 图片地址  "http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg";
	 *        imgtype请求对比的两张图片的类型，示例：“7，13”
			  			7表示生活照：通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等
						11表示身份证芯片照：二代身份证内置芯片中的人像照片
						12表示带水印证件照：一般为带水印的小图，如公安网小图
						13表示证件照片：如拍摄的身份证、工卡、护照、学生证等证件图片，注：需要确保人脸部分不可太小，通常为100px*100px
	 * @return
	 */
	public static String match(Map<String, Object> param) {
		String filePath1 = Convert.toStr(param.get("filePath1"));
		String filePath2 = Convert.toStr(param.get("filePath2"));
		String imgtype = Convert.toStr(param.get("imgtype"));

		Map<String, Object> rs= new HashMap<String, Object>();
		try {
//			String accessToken =getAuth(BaiDuAI_Face);
			
//			log.info("match {accessToken="+accessToken+", filePath1="+filePath1+",filePath2"+filePath2);
			
//			String data = FaceMatch.match(accessToken, filePath1, filePath2,imgtype);

			byte[] imgData = FileUtil.getBase46StringByUrl(filePath1);
			String imgStr = Base64Util.encode(imgData);
//			String imgParam = URLEncoder.encode(imgStr, "UTF-8");

//            String filePath2 = "[本地文件路径]";
			byte[] imgData2 = FileUtil.getBase46StringByUrl(filePath2);
			String imgStr2 = Base64Util.encode(imgData2);
//			String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");
			List<Map<String, Object>> images = new ArrayList<>();
			Map<String, Object> map1 = new HashMap<>();
			map1.put("image", imgStr);
			map1.put("image_type", "BASE64");
			map1.put("face_type", "LIVE");
			map1.put("quality_control", "LOW");
			map1.put("liveness_control", "NORMAL");

			Map<String, Object> map2 = new HashMap<>();
			map2.put("image", imgStr2);
			map2.put("image_type", "BASE64");
			map2.put("face_type", "LIVE");
			map2.put("quality_control", "LOW");
			map2.put("liveness_control", "NORMAL");
			images.add(map1);
			images.add(map2);
			String responseStr=CreditServiceTools.bdFaceIdentification(images);
			Map<String, Object> object = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(responseStr);
			if(object.get("code").equals("0000") && !object.get("data").equals("")){
				log.info("match result="+object.get("data"));
				rs.put("status", "success");
				rs.put("data",object.get("data").toString());
			}
		} catch (Exception e) {
			rs.put("status", "fail");
			rs.put("msg", "人脸查找失败");
		}
		return JSONUtils.toJSONString(rs);
	}
	
	/**
	 * 人脸查找[地推黑名单] M:N
	 * param imgurl 图片地址  "http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg";
	 * param groupName 如 anhuiditui
	 * @return
	 */
	public static String blackfaceMultiIdentify(Map<String, Object> param) {
		String imgurl = Convert.toStr(param.get("imgurl"));
		String groupName = Convert.toStr(param.get("groupName"));
		
		Map<String, Object> rs= new HashMap<String, Object>();
		try {
			String accessToken =getAuth(BaiDuAI_Face);
			
			log.info("faceIdentify {accessToken="+accessToken+", imgurl="+imgurl);
			
			String data = FaceIdentify.multiIdentify(accessToken,groupName, imgurl);
			
			rs.put("status", "success");
			rs.put("data", data);
		} catch (Exception e) {
			rs.put("status", "fail");
			rs.put("msg", "人脸查找失败");
		}
		return JSONUtils.toJSONString(rs);
	}
	
	/**
	 * 营业执照文字识别
	 * param imgurl 图片地址  "http://pic.beta.ule.com/pic/user_10000029150/product/prd20180821/app_storebusiness15348362241706902.jpg";
	 * @return
	 */
	public static String identifyLicense(Map<String, Object> param) {
		String imgurl = Convert.toStr(param.get("imgurl"));
		Map<String, Object> rs= new HashMap<String, Object>();
		try {
			String imgStr = imgurl;
			if(imgurl.startsWith("http")){
				byte[] imgData = FileUtil.getBase46StringByUrl(imgurl);
				imgStr = Base64Util.encode(imgData);
			}
			String responseStr=CreditServiceTools.bdOcrBusinessLicense(imgStr, "", "");
			Map<String, Object> object = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(responseStr);
			if(!object.get("code").equals("0000") || object.get("data").equals("")){
				rs.put("status", "fail");
				rs.put("msg", "营业执照文字识别失败,请重新拍照！");
				JSONUtils.toJSONString(rs);
			}
            JSONObject jsonObject = JSONObject.fromObject(object.get("data"));
            JSONObject wordsResult =jsonObject.getJSONObject("words_result");
    		Iterator keyIter = wordsResult.keys();
    		String key;
    		Object value;
    		while (keyIter.hasNext()) {
    			key = (String) keyIter.next();
    			value = wordsResult.get(key);
    			JSONObject jsonValue =JSONObject.fromObject(value);
    			Object words = jsonValue.get("words");
    			rs.put(key, words);
    		}
    		rs.put("status", "success");
		} catch (Exception e) {
			rs.put("status", "fail");
			rs.put("msg", "营业执照文字识别失败,请重新拍照！");
		}
		return JSONUtils.toJSONString(rs);
	}

//	public static void main(String[] args) throws Exception {
//		
//		/*String filePath1="http://pic.ule.com/pic/user_1015459506/product/prd20180511/app_storeinner15260178281704542.jpg";
//		
//		filePath1="http://pic.ule.com/pic/user_1004537825/product/prd20170818/app_selfface15030179148994301.jpg";
//		filePath1="http://pic.ule.com/pic/user_1006808204/product/prd20170821/app_selfface15032813899828869.jpg";
//    	String filePath2="http://pic.ule.com/pic/user_1006779796/product/prd20170823/app_selfface15034672778756427.jpg";	
//    	
//    	String filePath3 = "http://pic.beta.ule.com/pic/user_10000029150/product/prd20180821/app_storebusiness15348362241706902.jpg";
//    	
//
//		baiduMap.put("tranzCode", "8301");
//		baiduMap.put("groupId", "bangZGface");
//		baiduMap.put("userOnlyId", "88888888");
//		baiduMap.put("imgBase64", getBase64StringByUrl("http://pic.ule.com/pic/user_1006808204/product/prd20170821/app_selfface15032813899828869.jpg"));*/
//		Map<String, Object> baiduMap = new HashMap<String, Object>();
//		String imgurl="D:\\baidu\\20191029163006.jpg";
//		baiduMap.put("imgurl", imgurl);
//		baiduMap.put("groupId","uleface,ulefinance");
//
//		faceMultiIdentify(baiduMap);
//
//	}
	

	
	/**
	 * 从t_j_constant表中取BaiDu.assess_token,如果时间超过29天重新获取assess_token 返回，并保存到t_j_constant
	 * @return assess_token
	 */
	public static String getAuth(String baiduAIType) {
		String rs ="";
		if(BaiDuAI_Face.equals(baiduAIType)){
			log.info("getAuth param[APIKey="+APIKey+",secretKey="+secretKey+"]");
			rs = AuthService.getAuth(APIKey,secretKey);		
		}else if(BaiDuAI_OCR.equals(baiduAIType)){
			log.info("getAuth param[BaiDuAIOCRKey="+BaiDuAIOCRKey+",BaiDuAIOCRSecretKey="+BaiDuAIOCRSecretKey+"]");
			rs = AuthService.getAuth(BaiDuAIOCRKey,BaiDuAIOCRSecretKey);
		}
		log.info("getAuth result="+rs);
		return rs;
//		try {
//			Map<String, Object> paramMap =new HashMap<String, Object>();
//			if(BaiDuAI_Face.equals(baiduAIType)){
//				paramMap.put("key", "BaiDu.assess_token");				
//			}else if(BaiDuAI_OCR.equals(baiduAIType)){
//				paramMap.put("key", "BaiDuAI_OCR");	
//			}
//			log.info("getAuth param:"+paramMap.toString());
//			
////			Constant constant =constantService.query(paramMap);
//			
//			Map<String,String> elementValueMap=getMapByString(constant.getElementValue(), ",", ":");
//			String valid_date=elementValueMap.get("valid_date");//有效日期  yyyyMMdd
//			
//			if(StringUtils.isNotBlank(valid_date)&& DateUtil.diffDays(valid_date, DateUtil.currDateSimpleStr(),DateUtil.YMD_SIMPLE)>0 ){
//				rs=elementValueMap.get("assess_token");				
//			}else{
//				//已失效了
//				if(BaiDuAI_Face.equals(baiduAIType)){
//					rs = AuthService.getAuth(APIKey,secretKey);		
//				}else if(BaiDuAI_OCR.equals(baiduAIType)){
//					rs = AuthService.getAuth(BaiDuAIOCRKey,BaiDuAIOCRSecretKey);
//				}
//				String value = "assess_token:"+rs+",valid_date:"+DateUtil.getDateAfterTime(DateUtil.currentDate(), 29, "0", DateUtil.YMD_SIMPLE);
//				
//				paramMap.put("value", value);
//				paramMap.put("status", "1");
////				constantService.update(paramMap);
//			}
//			System.out.println(rs);
//		} catch (Exception e) {
//			log.error("getAuth erro:",e);
//		}
//		return rs;
	}
	
	public static String getAuth() {
		return getAuth(BaiDuAI_Face);
	}
	
	
	/*
	 * Step 1 生成HTTP body 
	 */
//	String databaseImageContent = FileHelper.getImageBase64Content("D:/dou/works/ule2/selfUtil/src/main/resources/sample_images/id.jpg"); //  设置上传图片并编码为base64字符串
//	int databaseImageType = 1; // 设置上传图片的类型，类型编号请参见接口文档

//	String queryImageContent = FileHelper.getImageBase64Content("D:/dou/works/ule2/selfUtil/src/main/resources/sample_images/id_test.jpg"); // 设置查询照图片并编码为base64字符串
//	int queryImageType = 301; // 设置查询照图片类型
	
	
	public static String getBase64StringByUrl(String imgUrl) {
		String image_content=null;
		URL url = null;
		InputStream is = null;
		ByteArrayOutputStream outStream = null;
		HttpURLConnection httpUrl = null;
		int length=0;
		try {
			url = new URL(imgUrl);
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
		return image_content;
	}
	
	
}
