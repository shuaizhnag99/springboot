package com.ule.uhj.app.zgd.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.cookie.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.zgd.service.BaiDuFaceService;
import com.ule.uhj.app.zgd.util.DateUtil;
import com.ule.uhj.app.zgd.util.baiduface.AuthService;
import com.ule.uhj.app.zgd.util.baiduface.FaceIdentify;
import com.ule.uhj.app.zgd.util.baiduface.FaceMatch;
import com.ule.uhj.app.zgd.util.baiduface.FaceUpdate;
import com.ule.uhj.init.modle.Constant;
import com.ule.uhj.init.service.ConstantService;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.StringUtil;

@Service("BaiDuFaceService")
public class BaiDuFaceServiceImpl implements BaiDuFaceService{
	private static Logger log = LoggerFactory.getLogger(BaiDuFaceServiceImpl.class);
	
	private static String APIKey=PropertiesHelper.getDfs("BaiDuFace_API_Key");
	private static String secretKey=PropertiesHelper.getDfs("BaiDuFace_Secret_Key");
	
	@Autowired
	private ConstantService constantService;
	
	
	/**
	 * 人脸更新[百度人脸库中]
	 * @param groupId  用户组
	 * @param userOnlyId 
	 * @param imgBase64 
	 * @return
	 */
	@Override
	public Map<String, Object> addFace(String groupId,String userOnlyId,String imgBase64) {
		Map<String, Object> rs= new HashMap<String, Object>();
		try {
			String accessToken =getAuth();
			log.info("addFace {accessToken="+accessToken+", groupId="+groupId+", userOnlyId="+userOnlyId);
			String data =FaceUpdate.update(accessToken, groupId, userOnlyId, imgBase64);
			rs.put("status", "success");
			rs.put("data", data);
		} catch (Exception e) {
			rs.put("status", "fail");
			rs.put("msg", "百度人脸库更新失败");
		}
		return rs;
	}
	
	
	/**
	 * 人脸查找[百度人脸库中] 1:N
	 * @param imgurl 图片地址  "http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg";
	 * @return
	 */
	@Override
	public Map<String, Object> faceIdentify(String imgurl) {
		Map<String, Object> rs= new HashMap<String, Object>();
		try {
			String accessToken =getAuth();
			log.info("faceIdentify {accessToken="+accessToken+", imgurl="+imgurl);
			
			String data = FaceIdentify.identify(accessToken, imgurl);
			
			rs.put("status", "success");
			rs.put("data", data);
		} catch (Exception e) {
			rs.put("status", "fail");
			rs.put("msg", "人脸查找失败");
		}
		return rs;
	}
	
	/**
	 * 人脸查找[百度人脸库中] M:N
	 * @param imgurl 图片地址  "http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg";
	 * @return
	 */
	@Override
	public Map<String, Object> faceMultiIdentify(String imgurl) {
		Map<String, Object> rs= new HashMap<String, Object>();
		try {
			String accessToken =getAuth();
			
			log.info("faceIdentify {accessToken="+accessToken+", imgurl="+imgurl);
			
			String data = FaceIdentify.multiIdentify(accessToken, imgurl);
			
			rs.put("status", "success");
			rs.put("data", data);
		} catch (Exception e) {
			rs.put("status", "fail");
			rs.put("msg", "人脸查找失败");
		}
		return rs;
	}
	
	
	/**
	 * 人脸比对 1:1
	 * @param imgurl1 图片地址  "http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg";
	 *        imgurl2 图片地址  "http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg";
	 *        imgtype请求对比的两张图片的类型，示例：“7，13”
			  			7表示生活照：通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等
						11表示身份证芯片照：二代身份证内置芯片中的人像照片
						12表示带水印证件照：一般为带水印的小图，如公安网小图
						13表示证件照片：如拍摄的身份证、工卡、护照、学生证等证件图片，注：需要确保人脸部分不可太小，通常为100px*100px
	 * @return
	 */
	@Override
	public Map<String, Object> match(String filePath1,String filePath2,String imgtype) {
		Map<String, Object> rs= new HashMap<String, Object>();
		try {
			String accessToken =getAuth();
			
			log.info("match {accessToken="+accessToken+", filePath1="+filePath1+",filePath2"+filePath2);
			
			String data = FaceMatch.match(accessToken, filePath1, filePath2,imgtype);
			
			rs.put("status", "success");
			rs.put("data", data);
		} catch (Exception e) {
			rs.put("status", "fail");
			rs.put("msg", "人脸查找失败");
		}
		return rs;
	}
	
	/**
	 * 人脸查找[地推黑名单] M:N
	 * @param imgurl 图片地址  "http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg";
	 * @return
	 */
	@Override
	public Map<String, Object> blackfaceMultiIdentify(String imgurl) {
		Map<String, Object> rs= new HashMap<String, Object>();
		try {
			String accessToken =getAuth();
			
			log.info("faceIdentify {accessToken="+accessToken+", imgurl="+imgurl);
			
			String data = FaceIdentify.multiIdentify(accessToken,"anhuiditui", imgurl);
			
			rs.put("status", "success");
			rs.put("data", data);
		} catch (Exception e) {
			rs.put("status", "fail");
			rs.put("msg", "人脸查找失败");
		}
		return rs;
	}
	
	public static void main(String[] args) throws Exception {
		
		BaiDuFaceServiceImpl bs = new BaiDuFaceServiceImpl();
		String filePath1="http://pic.ule.com/pic/user_1015459506/product/prd20180511/app_storeinner15260178281704542.jpg";
    	String filePath2="http://pic.ule.com/pic/user_1006779796/product/prd20170823/app_selfface15034672778756427.jpg";
		bs.blackfaceMultiIdentify(filePath1);
		
		
		
	}
	

	
	/**
	 * 从t_j_constant表中取BaiDu.assess_token,如果时间超过29天重新获取assess_token 返回，并保存到t_j_constant
	 * @return assess_token
	 */
	public String getAuth() {
		String rs ="";
		
		try {
			Map<String, Object> paramMap =new HashMap<String, Object>();
			paramMap.put("key", "BaiDu.assess_token");
			log.info("getAuth param:"+paramMap.toString());
			
			Constant constant =constantService.query(paramMap);
			
			Map<String,String> elementValueMap=StringUtil.getMapByString(constant.getElementValue(), ",", ":");
			String valid_date=elementValueMap.get("valid_date");//有效日期  yyyyMMdd
			
			if(StringUtils.isNotBlank(valid_date)&& DateUtil.diffDays(valid_date, DateUtil.currDateSimpleStr(),DateUtil.YMD_SIMPLE)>0 ){
				rs=elementValueMap.get("assess_token");				
			}else{
				//已失效了
				rs = AuthService.getAuth(APIKey,secretKey);
		
				String value = "assess_token:"+rs+",valid_date:"+DateUtil.getDateAfterTime(DateUtil.currentDate(), 29, "0", DateUtil.YMD_SIMPLE);
				
				paramMap.put("value", value);
				paramMap.put("status", "1");
				constantService.update(paramMap);
			}
			System.out.println(rs);
		} catch (Exception e) {
			log.error("getAuth erro:",e);
		}
		return rs;
	}
}
