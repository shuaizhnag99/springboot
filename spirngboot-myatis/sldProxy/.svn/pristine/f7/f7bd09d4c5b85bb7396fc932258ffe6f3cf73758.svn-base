package com.ule.uhj.provider.yitu.util.baiduface;

import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
* 人脸对比
*/
public class IdentifyBusinessLicense {
	protected static Log log = LogFactory.getLog(IdentifyBusinessLicense.class);

    public static String identifyLicense(String accessToken,String filePath1) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/business_license";
        try {
        	String imgStr = filePath1;
        	if(filePath1.startsWith("http")){
        		byte[] imgData = FileUtil.getBase46StringByUrl(filePath1);
                imgStr = Base64Util.encode(imgData);
        	}
        	String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam + "&detect_direction=true";
            log.info("identifyLicense filePath:"+filePath1);
            String result = HttpUtil.post(url, accessToken, param);
            log.info("identifyLicense result:"+result);
            return result;
        } catch (Exception e) {
            log.error("identifyLicense fail", e);
        }
        return null;
    }

//    public static void main(String[] args) {
//    	String accessToken="24.b271461ba85b3897a9c3bd08844e2a82.2592000.1533976078.282335-11528043";
//    	String filePath1="http://chuantu.biz/t6/341/1531448543x-1566673243.jpg";
//        IdentifyBusinessLicense.identifyLicense(accessToken, filePath1);
//    }
}

