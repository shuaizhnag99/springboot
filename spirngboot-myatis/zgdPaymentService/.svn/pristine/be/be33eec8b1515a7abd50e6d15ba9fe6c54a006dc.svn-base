package com.ule.uhj.util;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


public class StringUtil {
	//生成随机数
	public static String getCharAndNumr(int length) {
		String val = "";
		Random random = new Random(2);
		Random random1 = new Random(10);
		Random random2 = new Random(26);
		for (int i = 0; i < length; i++) {
			// 输出字母还是数字
//			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			String charOrNum ="num";
			// 字符串
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 取得大写字母还是小写字母
				int choice = random.nextInt() % 2 == 0 ? 65 : 97;
				val += (char) (choice + random2.nextInt());
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
				val += String.valueOf(random1.nextInt());
			}
		}
		return val;
	}
	
	/**
	 * 格式化查询条件
	 * @param searchParams Map字符串
	 * @return
	 */
	public static Map<String, Object> formatSearchParams(String searchParams){
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(searchParams)) {
				searchParams=new String(searchParams.getBytes("iso-8859-1"),"UTF-8"); //转码
				String regex = "\\{(.+?)\\}";//匹配{}中间的部分
				String[] group=null; // ","分割数组
				String[] params=null;//key、value数组
				Matcher matcher = Pattern.compile(regex).matcher(searchParams);
				if (matcher.matches()) {
					List<String> str=new ArrayList<String>();
					group = matcher.group(1).split(",");
					for (int i = 0; i < group.length; i++) {
						params=group[i].split("=");
						if (!str.contains(params[0].trim())) {
							if (params.length>1) {
								map.put(params[0].trim(),params[1].trim());
							}else{
								map.put(params[0].trim(),"");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			//  e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取客户端请求IP
	 * @param request
	 * @return
	 */
	public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (!checkIP(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!checkIP(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!checkIP(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    private static boolean checkIP(String ip) {
        if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip) || ip.split("\\.").length != 4) {
            return false;
        }
        return true;
    }
    
    /**
	 * 判断对象是否不为空
	 */
	public static <T> boolean isNotEmpty(Object value) {
        if (null != value) {
            if (StringUtil.isEmpty(value.toString())) {
                return false;
            }
            return true;
        }
        return false;
    }
	
	 /**
     * 判断Object是否不为空,先判断是否不为null，为null返回false;不为null 继续判断toString()是否不为空
     * 
     * */
    public static <T> boolean isEmpty(Object value) {
        if (null == value) {
            return true;
        } else if (StringUtil.isEmpty(value.toString())) {
            return true;
        }
        return false;
    }
    
    /**
     * 判断字符串是否为空
     * 
     * */
    public static boolean isEmpty(String datas) {
    	if (null == datas) {
            return true;
        } else if ("".equals(datas.trim())) {
            return true;
        } else if (StringUtils.isBlank(datas)) {
            return true;
        }
        return false;
    }
    
    @SuppressWarnings("rawtypes")
	public static Map<String, Object> getRequestMap(HttpServletRequest request){
    	Map<String, Object> resultMap=new HashMap<String, Object>();
        Enumeration paramNames = request.getParameterNames();  
        while (paramNames.hasMoreElements()) {  
            String paramName =paramNames.nextElement().toString().trim();  
            String[] paramValues = request.getParameterValues(paramName);  
            if (paramValues.length == 1) {  
                String paramValue = decodeStr(paramValues[0].trim());  
                if (paramValue.length() != 0) {  
                	resultMap.put(paramName, paramValue);  
                }  
            }  
        }  
        return resultMap;
    }
    
    public static String decodeStr(String str){
    	try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (Exception e) {
			return str;
		}
    }
    
    
    /**
     * 字符串拆分为map
     * @param operateStr = "default:3,YD:0,LT:0,DX:0"
     * @param split1     = ","
     * @param split2     = ":"
     * @return  Map 
     * 		key :	value
     * 		default		3
     * 		YD			0
     * 		LT			0
     */
    public static Map<String, String> getMapByString(String operateStr,String split1,String split2){
    		Map<String,String> result = new HashMap<String, String>();
    		String[] operateArr = operateStr.split(split1);
    		for (String operate : operateArr) {
    			String [] arr=operate.split(split2);
    			result.put(arr[0], arr[1]);
            }
    		return result;
    }
    
    /**
     * 字符串拆分为map
     * @param operateStr = "default,YD,LT,DX"
     * @param split     = ","
     * @return  List 
     * 		default		
     * 		YD			
     * 		LT
     * 		DX			
     */
    public static List<String> getListByString(String operateStr,String split){
    		List<String> rs =null;
    		if(!isEmpty(operateStr)){
    			String[] operateArr = operateStr.split(split);
    			rs = Arrays.asList(operateArr);
    			
    		}
    		return rs;
    }
    
    public static Boolean checkParam(String... params) {
		for(String param:params) {
			if(StringUtils.isBlank(param)) return false;
		}
		return true;
	}
    
    public static void main(String[] args) {
		String s ="";
		List<String> ss = getListByString(s,",");
		System.out.println(ss);
	}
}
