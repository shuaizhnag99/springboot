package com.ule.uhj.app.zgd.service;

import java.util.Map;

public interface BaiDuFaceService {
	
	/**
	 * 人脸更新[百度人脸库中]
	 * @param groupId  用户组
	 * @param userOnlyId 
	 * @param imgBase64 
	 * @return
	 */
	public Map<String,Object> addFace(String groupId,String userOnlyId,String imgBase64);
	
	/**
	 * 人脸查找[百度人脸库中]
	 * @param imgurl 图片地址  "http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg";
	 * @return
	 */
	public Map<String, Object> faceIdentify(String imgurl);

	public Map<String, Object> faceMultiIdentify(String imgurl);
	
	public Map<String,Object> match(String filePath1,String filePath2,String imgtype);
	public Map<String, Object> blackfaceMultiIdentify(String imgurl);
}
