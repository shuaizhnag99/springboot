package com.ule.uhj.util;

import com.ule.wildfly.util.StringUtils;

public class SldContants {
	
	 /**
     * 如果是商乐贷用户短信内容替换部分描述 add by zhangshuai 2019-07-29
     * @param userOnlyId
     * @param content
     * @return
     */
    public static String checkUser(String userOnlyId,String content) {
    	if(StringUtils.isNotBlank(userOnlyId)&&userOnlyId.contains("sld")) {
    		content=content.replace("掌柜贷", "商乐贷").replace("邮掌柜", "邮乐商家版");
    	}
    	return content;
    }
    
    
  

}
