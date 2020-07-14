package com.ule.uhj.provider.yitu.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ule.tools.creditService.client.CreditServiceTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.FaceVerifyRequest;

import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sldProxy.util.PropertiesHelper;

public class BaiDuAiService {
	protected static Log log = LogFactory.getLog(BaiDuAiService.class);

	public static final String APP_ID_OCR = PropertiesHelper.getDfs("APP_ID_OCR");
	public static final String API_KEY_OCR = PropertiesHelper.getDfs("API_KEY_OCR");
	public static final String SECRET_KEY_OCR = PropertiesHelper.getDfs("SECRET_KEY_OCR");

	public static final String APP_ID_FACE = PropertiesHelper.getDfs("APP_ID_FACE");
	public static final String API_KEY_FACE = PropertiesHelper.getDfs("API_KEY_FACE");
	public static final String SECRET_KEY_FACE = PropertiesHelper.getDfs("SECRET_KEY_FACE");

	/**
	 * 身份证ocr识别
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static String queryIdCardOcrInfo(Map<String, Object> param)
			throws Exception {
		 // 身份证识别url
        String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        String imageUrl = Convert.toStr(param.get("imageUrl"));
        String imgStr = SampleCodeV3PairVerify.getBase46StringByUrl(imageUrl);
        String idCardSide =Convert.toStr(param.get("sideType"));
        
        // 识别身份证正面id_card_side=front;识别身份证背面id_card_side=back;
        /*String params = "id_card_side="+idCardSide+"&" + URLEncoder.encode("image", "UTF-8") + "="
                + URLEncoder.encode(imgStr, "UTF-8");
        String accessToken = AuthService.getAuth(API_KEY_OCR, SECRET_KEY_OCR);
        String result = HttpUtil.post(idcardIdentificate, accessToken, params);*/
		String responseStr=CreditServiceTools.bdOcrIdcard(imgStr,idCardSide,"","");
		Map<String, Object> object = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(responseStr);
		log.info("CreditServiceTools.bdOcrIdcard object="+object);
		if("0000".equals(object.get("code")) && !"".equals(object.get("data"))){
			log.info("queryIdCardOcrInfo result="+object.get("data"));
			return object.get("data").toString();
		}else{
			throw new Exception("调用基础服务部， 百度智能 文字识别-身份证识别接口失败！");
		}
		/*AipOcr client = new AipOcr(APP_ID_OCR, API_KEY_OCR, SECRET_KEY_OCR);
		client.setConnectionTimeoutInMillis(2000);
	    client.setSocketTimeoutInMillis(60000);

	    HashMap<String, String> options = new HashMap<String, String>();
	    String idCardSide ="";
	    options.put("detect_direction", "true");
	    options.put("detect_risk", "true");
	    if("app_IdCardPositive".equals(Convert.toStr(param.get("sideType")))){
	    	idCardSide = "front";
	    }else if("app_IdCardOpposite".equals(Convert.toStr(param.get("sideType")))){
	    	idCardSide = "back";
	    }else{
	    	throw new Exception("queryIdCardOcrInfo 身份证正反面类型错误！！！");
	    }
	    String image_content = Convert.toStr(param.get("imageContent"));
        byte[] imageByte = Base64TImgUtil.getByteByNotBase64(image_content);  
        
        JSONObject res = client.idcard(imageByte, idCardSide, options);
		return JSONObject.valueToString(res);*/
	}
	
	/**
	 * 在线活体检测
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static String livingBodyDetection(Map<String, Object> param)
			throws Exception {
		AipFace client = new AipFace(APP_ID_FACE, API_KEY_FACE, SECRET_KEY_FACE);
		client.setConnectionTimeoutInMillis(2000);
	    client.setSocketTimeoutInMillis(60000);

	    String image_content = Convert.toStr(param.get("imageContent"));
	    FaceVerifyRequest req = new FaceVerifyRequest(image_content, "BASE64");
	    ArrayList<FaceVerifyRequest> list = new ArrayList<FaceVerifyRequest>();
	    list.add(req);
	    JSONObject res = client.faceverify(list);
		return JSONObject.valueToString(res);
	}
	
	/**
	 * 身份认证（公安照片对比） 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static String identityAuth(Map<String, Object> param)
			throws Exception {
		log.info("调用基础服务部公安人脸比对begin");
//		AipFace client = new AipFace(APP_ID_FACE, API_KEY_FACE, SECRET_KEY_FACE);
//		client.setConnectionTimeoutInMillis(2000);
//	    client.setSocketTimeoutInMillis(60000);
	    String image = Convert.toStr(param.get("imageContent"));
	    if(image.startsWith("http")){
	    	image = SampleCodeV3PairVerify.getBase46StringByUrl(image);
	    }
	    String imageType = "BASE64";
	    String idCardNumber = Convert.toStr(param.get("certNo"));
	    String name = Convert.toStr(param.get("userName"));

		Map<String, Object> map = new HashMap<>();
		map.put("image", image);
		map.put("image_type", imageType);
		map.put("id_card_number", idCardNumber);
		map.put("liveness_control", "NONE");
		map.put("name", name);
		map.put("quality_control", "LOW");
		String responseStr=CreditServiceTools.bdFacePersonVerify(map);
		log.info("调用基础服务部公安人脸比对identityAuth result="+responseStr);
		Map<String, Object> object = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(responseStr);
		if(object.get("code").equals("0000") && !object.get("data").equals("")){
			log.info("identityAuth result="+object.get("data"));
			return object.get("data").toString();
		}else{
			throw new Exception("调用基础服务部， 百度智能 人脸识别-身份验证-公安验证接口失败！");
		}
	}
	
	/**
	 * 身份认证token（公安照片对比） 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static String identityAuthWithToken(Map<String, Object> param)
			throws Exception {
		String url = "https://aip.baidubce.com/rest/2.0/face/v3/person/verify";
	    
	    String image = Convert.toStr(param.get("imageContent"));
	    if(image.startsWith("http")){
	    	image = SampleCodeV3PairVerify.getBase46StringByUrl(image);
	    }

		Map<String, Object> map = new HashMap<>();
		map.put("image", image);
		map.put("image_type", "BASE64");
		map.put("id_card_number", Convert.toStr(param.get("certNo")));
		map.put("liveness_control", "NONE");
		map.put("name", Convert.toStr(param.get("userName")));
		map.put("quality_control", "LOW");
		String responseStr=CreditServiceTools.bdFacePersonVerify(map);
		Map<String, Object> object = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(responseStr);
		log.info("CreditServiceTools.bdFacePersonVerify object="+JSON.toJSONString(object));
		if(object.get("code").equals("0000") && !object.get("data").equals("")){
			log.info("identityAuthWithToken result="+object.get("data"));
			return object.get("data").toString();
		}else{
			throw new Exception("调用基础服务部， 百度智能 人脸识别-身份验证-公安验证接口失败！");
		}
	}
	
	/**
	 * 姓名身份证一致性校验
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static String idCardNoMatch(Map<String, Object> param)
			throws Exception {
		String url = "https://aip.baidubce.com/rest/2.0/face/v3/person/idmatch";
	    Map<String, Object> map = new HashMap<String, Object>();
        map.put("id_card_number", Convert.toStr(param.get("idCardNumber")));
        map.put("name", Convert.toStr(param.get("name")));
        /*String params = GsonUtils.toJson(map);
        String accessToken = AuthService.getAuth(API_KEY_FACE, SECRET_KEY_FACE) ;
        String result = HttpUtil.post(url, accessToken, "application/json", params);*/
		String responseStr=CreditServiceTools.bdFaceIdmatch(map);
		Map<String, Object> object = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(responseStr);
		if(object.get("code").equals("0000") && !object.get("data").equals("")){
			log.info("queryBDGpsInfo result="+object.get("data"));
			return object.get("data").toString();
		}else{
			throw new Exception("调用基础服务部， 百度智能 人脸识别-身份验证-身份证与名字比对接口失败！");
		}
	}
	
	/**
	 * 人脸对比
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static String faceContrast(Map<String, Object> param)
			throws Exception {
		/*AipFace client = new AipFace(APP_ID_FACE, API_KEY_FACE, SECRET_KEY_FACE);
		client.setConnectionTimeoutInMillis(2000);
	    client.setSocketTimeoutInMillis(60000);*/

	    String image1 = Convert.toStr(param.get("imageContent1"));
	    String image2 = Convert.toStr(param.get("imageContent2"));
	    
	    /*MatchRequest req1 = new MatchRequest(image1, "BASE64");
	    MatchRequest req2 = new MatchRequest(image2, "BASE64");
	    ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
	    requests.add(req1);
	    requests.add(req2);*/
		List<Map<String, Object>> images = new ArrayList<>();
		Map<String, Object> map1 = new HashMap<>();
		map1.put("image", image1);
		map1.put("image_type", "BASE64");
		map1.put("face_type", "LIVE");
		map1.put("quality_control", "LOW");
		map1.put("liveness_control", "NORMAL");

		Map<String, Object> map2 = new HashMap<>();
		map2.put("image", image2);
		map2.put("image_type", "BASE64");
		map2.put("face_type", "LIVE");
		map2.put("quality_control", "LOW");
		map2.put("liveness_control", "NORMAL");

		images.add(map1);
		images.add(map2);
		String responseStr=CreditServiceTools.bdFaceIdentification(images);
		Map<String, Object> object = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(responseStr);
		if(object.get("code").equals("0000") && !object.get("data").equals("")){
			log.info("faceContrast result="+object.get("data"));
			return object.get("data").toString();
		}else{
			throw new Exception("调用基础服务部， 百度智能 人脸识别-人脸比对接口失败！");
		}
	}
	
//	public static void main(String[] args) throws Exception {
//		Map<String,Object> param = new HashMap<String, Object>();
//		param.put("id_card_number", "340221199009050031");
//		param.put("name", "张爱华");
//		String ret = BaiDuAiService.idCardNoMatch(param);
//		System.out.println(ret);
//	}

}
