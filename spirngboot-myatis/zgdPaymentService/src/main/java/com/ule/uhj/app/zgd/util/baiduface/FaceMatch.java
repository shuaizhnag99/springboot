package com.ule.uhj.app.zgd.util.baiduface;

import java.net.URLEncoder;

/**
* 人脸对比
*/
public class FaceMatch {

    /**
    * 重要提示代码中所需工具类
    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
    * 下载
    */
    public static String match(String accessToken,String filePath1,String filePath2,String imgtype) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/match";
        try {
            // 本地文件路径
//            String filePath = "[本地文件路径]";
//            byte[] imgData = FileUtil.readFileByBytes(filePath1);
            
            
            byte[] imgData = FileUtil.getBase46StringByUrl(filePath1);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

//            String filePath2 = "[本地文件路径]";
            byte[] imgData2 = FileUtil.getBase46StringByUrl(filePath2);
            String imgStr2 = Base64Util.encode(imgData2);
            String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");

            String param = "images=" + imgParam + "," + imgParam2+ "&type="+imgtype;

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }
//
//    public static void main(String[] args) {
//    	String accessToken="24.14f8e1570c8435c5dbaacf86d051c6de.2592000.1523095865.282335-10897948";
//    	String filePath1="http://pic.ule.com/pic/user_1006779796/product/prd20170823/app_storeinner15034688524863525.jpg";
//    	String filePath2="http://pic.ule.com/pic/user_1006779796/product/prd20170823/app_selfface15034672778756427.jpg";
//    	
//        FaceMatch.match(accessToken,filePath1,filePath2,"7,13");
//    }
}

