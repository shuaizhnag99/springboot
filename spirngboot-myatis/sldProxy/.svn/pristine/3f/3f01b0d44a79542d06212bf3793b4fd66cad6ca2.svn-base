package com.ule.uhj.provider.yitu.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ule.dfs.client.util.UploadFile;
import com.ule.uhj.provider.yitu.util.DateUtil;
import com.ule.uhj.provider.yitu.util.FileHelper;
import com.ule.uhj.provider.yitu.util.FileUtil;
import com.ule.uhj.sldProxy.util.PropertiesHelper;

/**
 * 上传文件
 * 
 * @author zhaojie
 * 
 */
public class UploadFileServer {
	private static Logger log = LoggerFactory.getLogger(UploadFileServer.class);

	public static String uploadFileBase64String(Map<String, Object> paras)
			throws Exception {
		String base64String=(String) paras.get("base64String");
		String userOnlyId=(String) paras.get("userOnlyId");
		String type=(String) paras.get("type");
		log.info("uploadFileBase64String userOnlyId:"+userOnlyId+";type:"+type);
		byte[] b = Base64.decodeBase64(base64String);
		InputStream stream = new ByteArrayInputStream(b);
		
		// 上传图片
		String uploadURL = PropertiesHelper.getDfs("uploadURL");
		String bussinessUnit = PropertiesHelper.getDfs("bussinessUnit");
//		String fullName = "/app_uhj/uppic_zgdapp/" + 2123 + new Date().getTime()
//				+ (int) (Math.random() * 10000) + ".jpg";
		String fullName="/app_lendvps/file"+userOnlyId+"/"+type+new Date().getTime()+ (int) (Math.random() * 10000) + ".jpg";
		String date=DateUtil.currDateSimpleStr();
//		String fullName = "";
//		if(userOnlyId.startsWith("sld")){
//			fullName = "/user_"+ userOnlyId.substring(3)+"/product/prd"+date+"/sld_"+type+new Date().getTime()+ (int) (Math.random() * 10000) + ".jpg";
//		}else{
//			fullName = "/user_"+ userOnlyId+"/product/prd"+date+"/"+type+new Date().getTime()+ (int) (Math.random() * 10000) + ".jpg";
//		}
		
		log.info("uploadFileBase64String fullName:"+fullName);
		String process = PropertiesHelper.getDfs("process");
		String uploadRp = UploadFile.upload(uploadURL, bussinessUnit, fullName,
				stream, new String[] { });
		log.info("UploadFileBase64String uploadRp:" + uploadRp);
		return uploadRp;

	}
	
	
//	public static void main(String[] args) throws Exception {
//		Map<String, Object> paras = new HashMap<String, Object>();
//		String filePath = "D:/zhuhua1.jpg";
//		String imageContent = FileHelper.getImageBase64Content(filePath);
//		FileHelper.saveImageStr2File(imageContent, "D:/1234.png");
//		paras.put("base64String", imageContent);
//		paras.put("userOnlyId", "10000000391");
//		paras.put("type", "app_storeOutside");
//		uploadFileBase64String(paras);
//	}
}
