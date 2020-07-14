package com.ule.uhj.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ule.uhj.sld.util.SldSign;

public class SecurityVerfiy {
	public static void securityVerfiy(HttpServletRequest request, String secret) throws Exception{
		Map<String,String> map = getRequestMap(request);
		
		if(!SldSign.verify(map, secret)){
			throw new Exception("mgt sms security verfiy error!" + map);
		}
	}
	
   	public static Map<String, String> getRequestMap(HttpServletRequest request){
	   Map<String, String> resultMap=new HashMap<String, String>();
	   Enumeration paramNames = request.getParameterNames();  
	   while (paramNames.hasMoreElements()) {  
	       String paramName =paramNames.nextElement().toString().trim();  
	       String paramValue = request.getParameter(paramName);  
	       resultMap.put(paramName, paramValue);  
	   }  
	   return resultMap;
    }


}
