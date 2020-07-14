package com.ule.uhj.provider.yitu.util.baiduface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
* 人脸注册
*/
public class FaceAdd {
	protected static Log log = LogFactory.getLog(FaceAdd.class);
    /**
    * 重要提示代码中所需工具类
    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
    * 下载
    */
    public static String add() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";
        try {
            // 本地文件路径
            String filePath = "http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_selfface15032154005222726.jpg";
            byte[] imgData = FileUtil.getBase46StringByUrl(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

//            String filePath2 = "D:\test\333.JPG";
//            byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
//            String imgStr2 = Base64Util.encode(imgData2);
//            String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");

            String param = "uid=" + "userOnlyIdBBBB" + 
            				"&user_info=" + "userOnlyIdBBBB" + 
            		        "&group_id=" + "uleface" + 
            				"&images=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "[调用鉴权接口获取的token]";
            accessToken="24.4fa7aee7b810890f9ca907dedba650a6.2592000.1522207701.282335-10849913";

            String result = HttpUtil.post(url, accessToken, param);
            log.info("add result:"+result);
            return result;
        } catch (Exception e) {
        	log.error("add fail", e);
        }
        return null;
    }
    
//    public static void main(String[] args) {
//        FaceAdd.add();
//    }
}
