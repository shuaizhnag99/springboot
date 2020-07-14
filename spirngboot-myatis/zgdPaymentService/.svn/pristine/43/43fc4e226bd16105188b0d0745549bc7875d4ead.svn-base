package com.ule.uhj.zgd.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ule.dfs.client.util.UploadFile;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.ycZgd.YCZgdClient;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.web.util.Tools;

import net.sf.json.JSONObject;

@Controller
public class ImageUploadController {

	private Log log = LogFactory.getLog(ImageUploadController.class);
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/uploadImg")
	@ResponseBody
	public String fileUpload(HttpServletRequest request,HttpServletResponse response){
		log.info("进入文件上传。。。。。");
		//定义结果集
		ObjectMapper mapper=new ObjectMapper();
		try {
//			String userId=CommonHelper.getUserOnlyId(request);
//			log.info("userId is :" + userId);
			String picType = request.getParameter("picType");
			String[] str = picType.split("_");
			picType = str[0];
			String userOnlyId = str[1];
			log.info("picType is :" + picType + ";userOnlyId=" + userOnlyId);
			//创建通用多部分文件解析器
			CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver();
			//判断request是否有文件上传
			if (commonsMultipartResolver.isMultipart(request)) {
				log.info("请求内有文件 上传begin。。。。。");
				//转换成多部分request
				MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
				//迭代文件
				Iterator<String> fileNames=multipartHttpServletRequest.getFileNames();
				if (!fileNames.hasNext()) {
					return null;
				}
				MultipartFile file=null;//文件
				String fileName=null;//文件名称
				//获取request中的每个文件
				for (Iterator<String> it = fileNames;it.hasNext();) {
					file=multipartHttpServletRequest.getFile(it.next());//获取文件
					Map<String, Object> tempMap=new HashMap<String, Object>();
					if (null!=file) {
						fileName=file.getOriginalFilename();//获取文件名称
						tempMap.put("fileName", fileName);
//						String fullName = "/user_"+ userOnlyId
//								+"/product/prd#/*.jpg";
						String uploadURL = PropertiesHelper.getDfs("uploadURL");
						String bussinessUnit = PropertiesHelper
								.getDfs("bussinessUnit");
						String fullName = "/app_uhj/uppic" + userOnlyId + "/"
								+ picType + new Date().getTime()
								+ (int) (Math.random() * 10000) + "."
								+ fileName.substring(fileName.lastIndexOf(".") + 1);
						String process = PropertiesHelper.getDfs("process");
						//String fullName = "/user_9991212/product/prd#/*.jpg";
						try {
							String uploadRp = UploadFile.upload(uploadURL, bussinessUnit,
									fullName, file.getInputStream(), new String[]{process});
							log.info("-------------image upload response content:"+uploadRp);
							Map<String, Object> tempJson=mapper.readValue(uploadRp, Map.class);
//							JSONObject tempJson = JSONObject.fromObject(uploadRp);
							if("success".equals(tempJson.get("status"))){
								// upload ok
								log.info("upload ok : " + uploadRp);
								JSONObject obj = JSONObject.fromObject(uploadRp);
								obj.put("code", "0000");
								// 存在url
								String url = obj.get("url").toString()
										.replace("_78x78.", ".");
								obj.remove("url");
								obj.put("url", url);
								obj.put("msg", "success");
								obj.remove("status");
								// 图片地址保存到数据库
								log.info("image url save begin.......");
								try {
									Map<String, Object> param = new HashMap<String, Object>();
									String usronlyid = Convert.toStr(userOnlyId);
									param.put("userOnlyId",
											Convert.toStr(usronlyid, ""));
									param.put("picType", Convert.toStr(picType, ""));
									param.put("imageUrl", Convert.toStr(url, ""));
									param.put("ip", Tools.getIpAddr(request));
									YCZgdClient client = WildflyBeanFactory.getYCZgdClient();
									client.saveUploadImage(param);
								} catch (Exception e) {
									log.info("image url save error.......", e);
								}
								log.info("image url save end.......");
								response.getWriter().print(obj);
								response.flushBuffer();
							}else{
								setError(response, "服务器响应中断，请再试一次.");
								log.debug("error:服务器响应中断，请再试一次。");
							}
						} catch (Exception e) {
							setError(response, "upload failure");
							log.error("UploadFile.upload error", e);
						}
					}else{
						setError(response, "upload failure");
						log.error("file is null");
					}
				}
				log.info("请求内有文件 上传end。。。。。");
			}
		} catch (Exception e) {
			log.error(" updoad error", e);
		}
		log.info(" 文件上传结束。。。。。");
		return null;
	}
	@RequestMapping("/ssssuploadImg")
	@ResponseBody
	public String uploadImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("WelabApplyUpload uploadPic begin");

		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		String userId=CommonHelper.getUserOnlyId(request);
		log.info("userId is :" + userId);
		String picType = request.getParameter("picType");
		String[] str = picType.split("_");
		picType = str[0];
		String userOnlyId = str[1];
		log.info("picType is :" + picType + ";userOnlyId=" + userOnlyId);
		if (!"busLience".equals(picType) && !"store".equals(picType)
				&& !"storeInner".equals(picType) && !"certHold".equals(picType)
				&& !"certPos".equals(picType) && !"certNeg".equals(picType)
				&& !"storePropertyType".equals(picType)) {
			setError(response, "图片名字错误");
			return null;
		}

		List<FileItem> fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			log.error(ex);
			setError(response, "parse the upload file error.");
			return null;
		}

		Iterator<FileItem> it = fileList.iterator();
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				String name = item.getName();
				long size = item.getSize();
				if (name == null || name.trim().equals("")) {
					continue;
				}
				try {
					if (size > 10485760) {
						setError(response, "图片大小不能大于10M.");
						return null;
					}
				} catch (Exception e) {
					log.error(e);
				}
				InputStream in = null;
				try {
					in = item.getInputStream();
					if (in == null) {
						throw new Exception("error FileItem item is null");
					}
					String uploadURL = PropertiesHelper.getDfs("uploadURL");
					String bussinessUnit = PropertiesHelper
							.getDfs("bussinessUnit");
					String fullName = "/app_uhj/uppic" + userOnlyId + "/"
							+ picType + new Date().getTime()
							+ (int) (Math.random() * 10000) + "."
							+ name.substring(name.lastIndexOf(".") + 1);
					String process = PropertiesHelper.getDfs("process");
					String result = UploadFile.upload(uploadURL, bussinessUnit,
							fullName, in, new String[] { process });

					if (result == null) {
						setError(response, "服务器响应中断，请再试一次.");
						log.debug("error:服务器响应中断，请再试一次。");
						return null;
					} else {
						// upload ok
						log.info("upload ok : " + result);
						JSONObject obj = JSONObject.fromObject(result);
						if ("success".equals(obj.get("status"))) {
							obj.put("code", "0000");
							// 存在url
							String url = obj.get("url").toString()
									.replace("_78x78.", ".");
							obj.remove("url");
							obj.put("url", url);
							obj.put("msg", "success");
							obj.remove("status");
							// 图片地址保存到数据库
							log.info("image url save begin.......");
							try {
								Map<String, Object> param = new HashMap<String, Object>();
								String usronlyid = Convert.toStr(userOnlyId);
								param.put("userOnlyId",
										Convert.toStr(usronlyid, ""));
								param.put("picType", Convert.toStr(picType, ""));
								param.put("imageUrl", Convert.toStr(url, ""));
								param.put("ip", Tools.getIpAddr(request));
								YCZgdClient client = WildflyBeanFactory.getYCZgdClient();
								client.saveUploadImage(param);
							} catch (Exception e) {
								log.info("image url save error.......", e);
							}
							log.info("image url save end.......");

						} else {
							obj.put("code", "1000");
							obj.put("msg", obj.get("reason"));
							obj.remove("reason");
							obj.remove("status");
						}
						response.getWriter().print(obj);
						response.flushBuffer();
					}
				} catch (Exception e) {
					log.error(e);
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (Exception ex) {
							log.error(ex, ex);
						}
						in = null;
					}
				}
			}
		}
		return null;

	}

	private void setError(HttpServletResponse response, String msg) {
		JSONObject obj = new JSONObject();
		obj.put("code", "1000");
		obj.put("msg", msg);
		try {
			response.getWriter().print(obj.toString());
			response.flushBuffer();
		} catch (IOException e) {
			log.error("write error code to response error.", e);
		}
	}

}
