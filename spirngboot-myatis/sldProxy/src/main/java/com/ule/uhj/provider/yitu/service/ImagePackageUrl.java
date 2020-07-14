package com.ule.uhj.provider.yitu.service;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ule.tools.creditService.client.CreditServiceTools;
import com.ule.tools.creditService.util.CreditException;

public class ImagePackageUrl {
	protected static Log log = LogFactory.getLog(ImagePackageUrl.class);
	
	public static String getImagePackageUrl(Map<String, Object> paras){
		String imagePackage=(String)paras.get("imagePackage");
		String resule="";
	try {
		 resule=CreditServiceTools.imagePackage(imagePackage);
	} catch (CreditException e) {
		log.error("Exception error.", e);
	}
	
	return resule;
	}

}