package com.ule.uhj.util;

import java.math.BigDecimal;

public class Convert {

	/**
	 * toBigDecimal
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal toBigDecimal(Object obj) throws Exception{
		if(obj == null){
			return null;
		}
		return new BigDecimal(obj.toString());
	}
	
	/**
	 * toBigDecimal 如果有异常返回默认值
	 * @param obj
	 * @param defaul
	 * @return
	 */
	public static BigDecimal toBigDecimal(Object obj, BigDecimal defaul){
		try {
			return new BigDecimal(obj.toString());
		} catch (Exception e) {
			return defaul; 
		}
	}
	
	/**
	 * null 返回null而不是 "null"
	 * @param obj
	 * @return
	 */
	public static String toStr(Object obj){
		return toStr(obj, null);
	}

	/**
	 * 如果null 返回默认值
	 * @param obj
	 * @param defaul
	 * @return
	 */
	public static String toStr(Object obj, String defaul){
		return obj == null ? defaul : obj.toString();
	}
	
	/**
	 * 如果null 返回默认值
	 * @param obj
	 * @param defaul
	 * @return
	 */
	public static String toStrTrim(Object obj, String defaul){
		return obj == null ? defaul : obj.toString().trim();
	}
	
	/**
	 * toInteger
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Integer toInt(Object obj){
		if(obj == null){
			return null;
		}
		return new Integer(obj.toString());
	}
	
	/**
	 * toInteger 如果有异常返回默认值
	 * @param obj
	 * @param defaul
	 * @return
	 */
	public static Integer toInt(Object obj, Integer defaul){
		try {
			return new Integer(obj.toString());
		} catch (Exception e) {
			return defaul; 
		}
	}
	
	/**
	 * toShort
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Short toShort(Object obj) throws Exception{
		if(obj == null){
			return null;
		}
		return new Short(obj.toString());
	}
	
	/**
	 * toShort 如果有异常返回默认值
	 * @param obj
	 * @param defaul
	 * @return
	 */
	public static Short toShort(Object obj, Short defaul){
		try {
			return new Short(obj.toString());
		} catch (Exception e) {
			return defaul; 
		}
	}
	
	/**
	 * toDouble
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Double toDouble(Object obj) throws Exception{
		if(obj == null){
			return null;
		}
		return new Double(obj.toString());
	}
	
	/**
	 * toDouble 如果有异常返回默认值
	 * @param obj
	 * @param defaul
	 * @return
	 */
	public static Double toDouble(Object obj, Double defaul){
		try {
			return new Double(obj.toString());
		} catch (Exception e) {
			return defaul; 
		}
	}
	
	/**
	 * toLong
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Long toLong(Object obj){
		if(obj == null){
			return null;
		}
		try{
			return new Long(obj.toString());
		}catch (Exception e){
			return 0l;
		}

	}
	
	/**
	 * toLong 如果有异常返回默认值
	 * @param obj
	 * @param defaul
	 * @return
	 */
	public static Long toLong(Object obj, Long defaul){
		try {
			return new Long(obj.toString());
		} catch (Exception e) {
			return defaul; 
		}
	}
}
