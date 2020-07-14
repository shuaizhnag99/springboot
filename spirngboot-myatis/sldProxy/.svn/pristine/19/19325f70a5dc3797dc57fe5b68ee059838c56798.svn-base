package com.ule.uhj.provider.yitu.util.baiduface;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.ule.tools.creditService.client.CreditServiceTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
* 人脸更新
*/
public class FaceUpdate {
	protected static Log log = LogFactory.getLog(FaceUpdate.class);
    /**
    * 重要提示代码中所需工具类
    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
    * 下载
    */
    public static String update(String accessToken,String groupId,String userOnlyId,String imgBase64) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/update";
        try {
            // 本地文件路径
//            String filePath = "http://pic.ule.com/pic/user_1006871942/product/prd20170823/app_selfface1503474668551833.jpg";
            //imgBase64="http://dfsread-service.http.beta.uledns.com/file/app_lendvps/file10000037478/app_selfface15664641343365209.jpg";
        	if(imgBase64.startsWith("http")) {
        		byte[] imgData = FileUtil.getBase46StringByUrl(imgBase64);
        		imgBase64 = Base64Util.encode(imgData);
        	}

        	log.info("编码之前的64"+imgBase64);

            String imgParam = URLEncoder.encode(imgBase64, "UTF-8");

            /*String param = "uid=" + userOnlyId +
    						"&user_info=" + userOnlyId + 
    						"&group_id=" + groupId + 
    						"&action_type=replace" +
    						"&images=" + imgParam;*/

            
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
//          String accessToken = "[调用鉴权接口获取的token]";
//          accessToken="24.4fa7aee7b810890f9ca907dedba650a6.2592000.1522207701.282335-10849913";

            Map<String, Object> rs= new HashMap<String, Object>();
            Map<String, Object> map = new HashMap<>();
            map.put("image", imgParam);
            map.put("group_id", groupId);
            map.put("user_id", userOnlyId);
            map.put("image_type", "BASE64");
            map.put("action_type","REPLACE");
            log.info("已进去人脸更新方法addFace  map"+map);
            String responseStr= CreditServiceTools.bdFaceSet(map);
            log.info("调用基础服务部人脸更新方法addFace  response"+responseStr);
            Map<String, Object> object = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(responseStr);
            if(object.get("code").equals("0000") && !object.get("data").equals("")){
                log.info("addFace result="+object.get("data"));
                rs.put("status", "success");
                rs.put("data", object.get("data").toString());
            }


            /*String result = HttpUtil.post(url, accessToken, param);*/
            log.info("FaceUpdate result:"+rs);
            return rs.toString();
        } catch (Exception e) {
            log.error("FaceUpdate fail", e);
        }
        return null;
    }

//    public static void main(String[] args) {
    	
//      String filePath = "http://pic.ule.com/pic/user_140075/product/prd20180315/yzs_postmember_face15211036055365182.jpg";
////      filePath = "http://pic.ule.com/pic/user_1006871942/product/prd20170823/app_selfface1503474668551833.jpg";
//      byte[] imgData = FileUtil.getBase46StringByUrl(filePath);
//      String imgBase64 = Base64Util.encode(imgData);
//    	
//      String auth="24.14f8e1570c8435c5dbaacf86d051c6de.2592000.1523095865.282335-10897948";
//      
//        FaceUpdate.update(auth,"bangZGface","wuhaitao",imgBase64);
    	
    	
//    }
}
