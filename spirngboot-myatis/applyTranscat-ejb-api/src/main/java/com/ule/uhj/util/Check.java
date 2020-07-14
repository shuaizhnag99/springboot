package com.ule.uhj.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {

	/**
	 * 空白符返回true
	 * @param obj
	 * @return
	 */
	public static boolean isBlank(Object obj){
		return (obj == null || obj.toString().trim().length() == 0);
	}
	/**
	 * 空白符或者0返回true
	 * @param obj
	 * @return
	 */
	public static boolean isBlankOrZero(Object obj){
		return (obj == null || obj.toString().trim().length() == 0 || "0".equals(obj.toString()));
	}
	/**
	 * 空格返回false
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj){
		return (obj == null || obj.toString().length() == 0);
	}

	/**
	 * 空白符返回true
	 * @param obj
	 * @return
	 */
	public static boolean haveBlank(Object ... objs){
		for(Object obj : objs){
			if(obj == null || obj.toString().trim().length() == 0){
				return true;
			}
		}
		return false;
	}

	/**
	 * 空格返回false
	 * @param obj
	 * @return
	 */
	public static boolean haveEmpty(Object ... objs){
		for(Object obj : objs){
			if(obj == null || obj.toString().length() == 0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 空格返回false
	 * @param obj
	 * @return
	 */
	public static boolean haveNull(Object ... objs){
		for(Object obj : objs){
			if(obj == null){
				return true;
			}
		}
		return false;
	}
	
	private static Pattern pattern_isPhone = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
	/**
	 * 校验是否是手机号码
	 * @param obj
	 * @return
	 */
	public static boolean isPhone(Object phone){
		if(!isBlank(phone)){
			Matcher m = pattern_isPhone.matcher(String.valueOf(phone));
			return m.matches();
		}  
		return false;
	}
	
	/**
	 * 校验是否是数字型
	 * @param obj
	 * @return
	 */
	public static boolean isNumber(Object obj){
        Pattern pattern = Pattern.compile("^[1-9]\\d*$");
        Matcher match = pattern.matcher(String.valueOf(obj).trim());
        if(match.matches()==false){
             return false;
        }else{
             return true;
        }
    }
}
