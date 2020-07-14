package com.ule.uhj.sldProxy.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.tools.creditService.client.CreditServiceTools;
import com.ule.uhj.provider.yitu.service.SampleCodeV3PairVerify;
import com.ule.uhj.provider.yitu.util.baiduface.AuthService;
import com.ule.uhj.provider.yitu.util.baiduface.GsonUtils;
import com.ule.uhj.provider.yitu.util.baiduface.HttpUtil;
import com.ule.uhj.sld.util.Check;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sldProxy.service.InterfaceAccessInfoService;
import com.ule.uhj.sldProxy.util.PropertiesHelper;

@Controller
@RequestMapping("/offer")
public class InterfaceOfferController {
	private static Logger log = LoggerFactory.getLogger(InterfaceOfferController.class);
	
	@Autowired
	private InterfaceAccessInfoService interfaceAccessInfoService;
	
	public static final String APP_ID_OCR = PropertiesHelper.getDfs("APP_ID_OCR");
	public static final String API_KEY_OCR = PropertiesHelper.getDfs("API_KEY_OCR");
	public static final String SECRET_KEY_OCR = PropertiesHelper.getDfs("SECRET_KEY_OCR");

	public static final String APP_ID_FACE = PropertiesHelper.getDfs("APP_ID_FACE");
	public static final String API_KEY_FACE = PropertiesHelper.getDfs("API_KEY_FACE");
	public static final String SECRET_KEY_FACE = PropertiesHelper.getDfs("SECRET_KEY_FACE");
	
	@RequestMapping("/queryIdCardOcrInfo")
    @ResponseBody
    public String queryIdCardOcrInfo(@RequestBody Map param) throws Exception{
		//String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        String imageUrl = Convert.toStr(param.get("imageUrl"));
        String imgStr = SampleCodeV3PairVerify.getBase46StringByUrl(imageUrl);
        String idCardSide =Convert.toStr(param.get("sideType"));
        // 识别身份证正面id_card_side=front;识别身份证背面id_card_side=back;
        if(Check.isBlank(imageUrl)||Check.isBlank(idCardSide)){
        	throw new Exception("queryIdCardOcrInfo error...参数不能为空");
        }
//        String params = "id_card_side="+idCardSide+"&" + URLEncoder.encode("image", "UTF-8") + "="
//                + URLEncoder.encode(imgStr, "UTF-8");
//        String accessToken = AuthService.getAuth(API_KEY_OCR, SECRET_KEY_OCR);
//        String result = HttpUtil.post(idcardIdentificate, accessToken, params);
        
		String responseStr=CreditServiceTools.bdOcrIdcard(imgStr,idCardSide,"","");
		Map<String, Object> object = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(responseStr);
		String result="";
		if(object.get("data")!=null && !object.get("data").equals("")){
			log.info("queryIdCardOcrInfo result="+object.get("data"));
			result = object.get("data").toString();
		}else{
			result ="调用基础服务部， 百度智能 文字识别-身份证识别接口失败！";
		}
        
        log.info("queryIdCardOcrInfo result="+result);

        String merchantId = Convert.toStr(param.get("merchantId"));
    	interfaceAccessInfoService.saveInterfaceRecord(com.alibaba.fastjson.JSONObject.toJSONString(param), result,Convert.toStr(param.get("userOnlyId")),"8102",merchantId.equals("02")?"邮乐卡":"海外业务");
        return result;
    }
	
	@RequestMapping("/idCardAuth")
    @ResponseBody
    public String idCardAuth(@RequestBody Map param) throws Exception{
//		String url = "https://aip.baidubce.com/rest/2.0/face/v3/person/verify";
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("quality_control", "NORMAL");
	    options.put("liveness_control", "LOW");
	    String image = Convert.toStr(param.get("imageUrl"));
	    if(image!=null){
	    	String imageContent = SampleCodeV3PairVerify.getBase46StringByUrl(image);
//	    	long t2 = System.currentTimeMillis();
//			log.info("idCardAuth cost t2-t1 ="+(t2-t1));
		    Map<String, Object> map = new HashMap<String, Object>();
	        map.put("image", imageContent);
	        map.put("image_type", "BASE64");
	        map.put("id_card_number", Convert.toStr(param.get("certNo")));
	        map.put("liveness_control", "NONE");
	        map.put("name", Convert.toStr(param.get("userName")));
	        map.put("quality_control", "LOW");
			String responseStr=CreditServiceTools.bdFacePersonVerify(map);
			log.info("调用基础服务部公安人脸比对identityAuth result="+responseStr);
			Map<String, Object> object = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(responseStr);
			String result="";
			if(object.get("data")!=null && !object.get("data").equals("")){
				log.info("identityAuth result="+object.get("data"));
				result =object.get("data").toString();
			}else{
				result = "调用基础服务部， 百度智能 人脸识别-身份验证-公安验证接口失败！";
			}
	        
	        //保存接口记录
	        String merchantId = Convert.toStr(param.get("merchantId"));
	        String requestStr =com.alibaba.fastjson.JSONObject.toJSONString(param);
	        String userOnlyId =Convert.toStr(param.get("userOnlyId"));
	        String source = merchantId.equals("02")?"邮乐卡":"海外业务";
	    	interfaceAccessInfoService.saveInterfaceRecord(requestStr, result,userOnlyId,"8104",source);
	    	return result;
	    }else{
	    	log.info("imageUrl can not be null...");
	    	throw new Exception("imageUrl can not be null...");
	    }
    }
	
//	public static void main(String[] args) {
//		String accessToken = AuthService.getAuth(API_KEY_FACE, SECRET_KEY_FACE);
//		System.out.println(accessToken);
//		String accessToken1 = AuthService.getAuth(API_KEY_FACE, SECRET_KEY_FACE);
//		System.out.println(accessToken);
		
//		InterfaceOfferController o = new InterfaceOfferController();
//		Map<String,Object> param = new HashMap<String,Object>();
		
		
//		String imageUrl ="http://file.beta.ule.com/file/app_uhj/uppic10000021530/certpos14627759518923541.jpg";
//		String sideType ="front";
//		String userOnlyId = "555555";
//		param.put("userOnlyId", userOnlyId);
//		param.put("imageUrl", imageUrl);
//		param.put("sideType", sideType);
		
		
//		String imageUrl ="http://file.beta.ule.com/file/app_uhj/uppic10000021530/certpos14627759518923541.jpg";
//		String userOnlyId = "555555";
//		param.put("userOnlyId", userOnlyId);
//		param.put("imageUrl", imageUrl);
//		param.put("certNo", "340221199009050031");
//		param.put("userName", "张爱华");
//		
//		try {
//			String rs = o.idCardAuth(param);
//			System.out.println(rs);
//		} catch (Exception e) {
//		}
//	}
}
