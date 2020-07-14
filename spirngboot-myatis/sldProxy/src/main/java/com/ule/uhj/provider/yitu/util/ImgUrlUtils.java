package com.ule.uhj.provider.yitu.util;

import org.apache.commons.lang.StringUtils;

public class ImgUrlUtils {

	/**
	 * 影像中心图片 取Base64String 需要改变地址
	 * 测试：http://pic.beta.ule.com/pic/user_10000024271/product/prd20180713/app_storeinner15314634206461057.jpg
	 * 生产：http://pic.ule.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg
	 *      http://file.beta.ule.com/file/app_uhj/uppic10000009097/certpos14615496756372668.jpg
	 *      http://file.ule.com/file/app_uhj/uppic1003221420/certpos14590495109619184.jpg
	 * 
	 * 测试：http://dfsread-service.http.beta.uledns.com/pic/user_10000024271/product/prd20180713/app_storeinner15314634206461057.jpg
	 * 生产：http://dfsread-service.http.prd.uledns.com/pic/user_1001247319/product/prd20170820/app_storeoutside15032193422313285.jpg
	 *      http://dfsread-service.http.beta.uledns.com/file/app_uhj/uppic10000009097/certpos14615496756372668.jpg
	 *      http://dfsread-service.http.prd.uledns.com/file/app_uhj/uppic1003221420/certpos14590495109619184.jpg
	 * @param imgUrl
	 * @return imgUrl
	 */
	public static String toInnerImgUrl(String imgUrl){
		String resultUrl =imgUrl;
		if(StringUtils.isNotBlank(imgUrl)){
			if(imgUrl.startsWith("http://pic.beta.ule.com")){
				resultUrl = imgUrl.replace("http://pic.beta.ule.com", "http://dfsread-service.http.beta.uledns.com");
			}else if(imgUrl.startsWith("http://pic.ule.com")){
				resultUrl = imgUrl.replace("http://pic.ule.com", "http://dfsread-service.http.prd.uledns.com");
			}else if(imgUrl.startsWith("http://file.beta.ule.com")){
				resultUrl = imgUrl.replace("http://file.beta.ule.com", "http://dfsread-service.http.beta.uledns.com");
			}else if(imgUrl.startsWith("http://file.ule.com")){
				resultUrl = imgUrl.replace("http://file.ule.com", "http://dfsread-service.http.prd.uledns.com");
			}else if(imgUrl.startsWith("http://pic.testing.ule.com")){
				resultUrl = imgUrl.replace("http://pic.testing.ule.com", "http://dfsread-service.http.testing.uledns.com");
			}else if(imgUrl.startsWith("http://file.testing.ule.com")){
				resultUrl = imgUrl.replace("http://file.testing.ule.com", "http://dfsread-service.http.testing.uledns.com");
			}
		}
		return resultUrl;
	}
}
