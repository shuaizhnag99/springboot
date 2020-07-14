package com.ule.uhj.app.zgd.util.baiduface;

import java.net.URLEncoder;

/**
* 人脸查找——识别
*/
public class FaceIdentify {

    /**
    * 重要提示代码中所需工具类
    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
    * 下载
    */
    public static String identify(String accessToken,String filePath) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/identify";
        try {
            // 本地文件路径
//        	String filePath = "http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg";
            byte[] imgData = FileUtil.getBase46StringByUrl(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

//            String filePath2 = "C:/Users/wuhaitao/Desktop/22222.jpg";
//            byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
//            String imgStr2 = Base64Util.encode(imgData2);
//            String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");

            String param = "group_id=" + "uleface,bangZGface" + "&user_top_num=" + "1" + "&face_top_num=" + "2" + "&images=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
//            String accessToken = "[调用鉴权接口获取的token]";
//            accessToken="24.4fa7aee7b810890f9ca907dedba650a6.2592000.1522207701.282335-10849913";

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }
    
    public static String multiIdentify(String accessToken,String filePath) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/multi-identify";
        try {
            // 本地文件路径
//        	String filePath = "http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg";
            byte[] imgData = FileUtil.getBase46StringByUrl(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

//            String filePath2 = "C:/Users/wuhaitao/Desktop/22222.jpg";
//            byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
//            String imgStr2 = Base64Util.encode(imgData2);
//            String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");

            String param = "group_id=" + "uleface,bangZGface" + "&user_top_num=" + "20" + "&detect_top_num=" + "2" + "&images=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
//            String accessToken = "[调用鉴权接口获取的token]";
//            accessToken="24.4fa7aee7b810890f9ca907dedba650a6.2592000.1522207701.282335-10849913";

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        FaceIdentify.identify("","");
//    	String filePath = "http://pic.ule.com/pic/user_140075/product/prd20180315/yzs_postmember_face15211036055365182.jpg";
//        FaceIdentify.multiIdentify("24.14f8e1570c8435c5dbaacf86d051c6de.2592000.1523095865.282335-10897948",filePath);
        
        
//        {"result":[{"uid":"userOnlyIdBBBB","scores":[93.537071228027],"group_id":"uleface","user_info":"userOnlyIdBBBB"},//店主
//                   {"uid":"test_user_5","scores":[93.537071228027],"group_id":"test_group_2","user_info":"userInfo5"}],//地推人员
//         "result_num":2,
//         "log_id":2315312320022614}
        
//        {"result":[{"uid":"userOnlyIdBBBB","scores":[93.537071228027],"group_id":"uleface","user_info":"userOnlyIdBBBB"},
//                   {"uid":"test_user_5","scores":[93.537071228027],"group_id":"test_group_2","user_info":"userInfo5"},
//                   {"uid":"1006693123","scores":[49.182418823242],"group_id":"uleface","user_info":""},
//                   {"uid":"zhanggui","scores":[23.715877532959],"group_id":"uleface","user_info":""},
//                   {"uid":"1004932348","scores":[11.273410797119],"group_id":"uleface","user_info":""}],
//         "result_num":5,
//         "log_id":2405072913022614}
        
//        {"result":[{"uid":"10000026049","scores":[95.247100830078],"group_id":"uleface","user_info":"10000026049"},
//                   {"uid":"1006871942","scores":[31.503519058228],"group_id":"uleface","user_info":"1006871942"}],
//        	"result_num":2,
//        	"log_id":4075216551030518}
        
        
        
//        {
//            "result": [
//                {
//                    "uid": "10000026049",
//                    "scores": [
//                        95.247100830078
//                    ],
//                    "group_id": "uleface",
//                    "user_info": "10000026049",
//                    "position": {
//                        "left": 59.291656494141,
//                        "top": 331.5007019043,
//                        "width": 505,
//                        "height": 526,
//                        "degree": 5,
//                        "prob": 1
//                    }
//                },
//                {
//                    "uid": "wuhaitao",
//                    "scores": [
//                        96.654350280762
//                    ],
//                    "group_id": "bangZGface",
//                    "user_info": "",
//                    "position": {
//                        "left": 583.82531738281,
//                        "top": 592.94506835938,
//                        "width": 352,
//                        "height": 334,
//                        "degree": -8,
//                        "prob": 1
//                    }
//                }
//            ],
//            "result_num": 2,
//            "log_id": 4140545402030518
//        }
        
    }

	public static String multiIdentify(String accessToken, String groupId,String filePath) {
		 // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/multi-identify";
        try {
            // 本地文件路径
//        	String filePath = "http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg";
            byte[] imgData = FileUtil.getBase46StringByUrl(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

//            String filePath2 = "C:/Users/wuhaitao/Desktop/22222.jpg";
//            byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
//            String imgStr2 = Base64Util.encode(imgData2);
//            String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");

            String param = "group_id=" + groupId + "&user_top_num=" + "2" + "&detect_top_num=" + "2" + "&images=" + imgParam;

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
	}
}
