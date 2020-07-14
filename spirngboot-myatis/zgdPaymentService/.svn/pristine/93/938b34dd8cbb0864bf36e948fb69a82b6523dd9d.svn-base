package com.ule.uhj.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class UserTools {
	private static Logger log = Logger.getLogger(UserTools.class);

	/**
	 * 把对象转换成String，如果对象为null返回空字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		try {
			if (null != obj) {
				return String.valueOf(obj).trim();
			}
		} catch (Exception e) {
			//  e.printStackTrace();
		}
		return "";
	}

	/**
	 * 把对象转换成Long<br />
	 * 如果对象为null返回0L
	 * 
	 * @param obj
	 * @return
	 */
	public static Long toLong(Object obj) {
		Long value = 0L;
		try {
			String str = toString(obj);
			if (StringUtils.isNotBlank(str)) {
				value = Long.parseLong(toString(obj));
			}
		} catch (Exception e) {
			//  e.printStackTrace();
		}
		return value;
	}

	/**
	 * 把对象转换成long，<BR/>
	 * 如果对象为null返回null
	 * 
	 * @param obj
	 * @return
	 */
	public static Long toLongNull(Object obj) {
		try {
			String str = toString(obj);
			if (StringUtils.isNotBlank(str)) {
				return Long.parseLong(toString(obj));
			}
		} catch (Exception e) {
			//  e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把对象转换成日期<BR/>
	 * obj为空时返回NULL
	 * 
	 * @param obj
	 * @param format
	 *            日期格式（默认为yyyy-MM-dd HH:mm:ss）
	 * @return
	 */
	public static Date toDate(Object obj, String format) {
		try {
			if (StringUtils.isBlank(toString(obj))) {
				return null;
			} else {
				if (StringUtils.isBlank(format)) {
					format = "yyyy-MM-dd HH:mm:ss";
				}
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				return sdf.parse(toString(obj));
			}
		} catch (ParseException e) {
			//  e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把对象转换成Integer<br />
	 * 如果对象为null返回0
	 * 
	 * @param obj
	 * @return
	 */
	public static Integer toIntegerZero(Object obj) {
		Integer value = 0;
		try {
			String str = toString(obj);
			if (StringUtils.isNotBlank(str)) {
				value = Integer.parseInt(toString(obj));
			}
		} catch (Exception e) {
			//  e.printStackTrace();
		}
		return value;
	}

	/**
	 * 把对象转换成Integer<br />
	 * 如果对象为null返回null
	 * 
	 * @param obj
	 * @return
	 */
	public static Integer toIntegerNull(Object obj) throws Exception {
		Integer value = null;
		String str = toString(obj);
		if (StringUtils.isNotBlank(str)) {
			value = Integer.parseInt(toString(obj));
		}
		return value;
	}

	/**
	 * 取得随机key
	 * 
	 * @param busiName
	 *            业务名称（可为空）
	 * @param merchantId
	 *            商家ID（可为空）
	 * @return
	 */
	public static String createUUID(String busiName, Long merchantId) {
		String uuid = UUID.randomUUID().toString();
		if (null != merchantId) {
			uuid = merchantId + "-" + uuid;
		}
		if (StringUtils.isNotBlank(busiName)) {
			uuid = busiName + "-" + uuid;
		}
		return uuid;
	}

	public static void printPojo(Object obj) {
		StringBuffer output = new StringBuffer();
		String className = obj.getClass().getName();
		output.append(className + "\n");
		Method[] methods = obj.getClass().getDeclaredMethods();
		// obj.getClass().getFields(); //获取public成员变量
		for (Method method : methods) {
			// System.out.println(method.getName() + " | " +
			// method.getParameterTypes().length);
			String mName = method.getName();
			int tempIndex = mName.indexOf("get");
			if (tempIndex != -1 && method.getParameterTypes().length == 0) {
				Object result = null;
				try {
					result = method.invoke(obj, new Object[] {});
				} catch (Exception e) {
					//  e.printStackTrace();
				}
				String outName = mName.substring(tempIndex + 3);
				output.append(outName + " : " + result + "\n");
			}
		}
		log.info(output.toString());
	}

	/**
	 * 把对象转换成Double类型，如果obj=null，返回0D
	 * 
	 * @param obj
	 * @return
	 */
	public static double toDouble(Object obj) {
		Double value = 0D;
		try {
			String str = obj.toString();
			if (StringUtils.isNotBlank(str)) {
				value = Double.parseDouble(str);
			}
		} catch (Exception e) {
			//  e.printStackTrace();
		}
		return value.doubleValue();
	}
	
	public static boolean isBlank(Object obj){
		return (obj == null || obj.toString().trim().length() == 0) ? true : false;
	}
	
	/**
	 * 把对象转换成Double类型，如果obj=null，返回0D
	 * 
	 * @param obj
	 * @return
	 */
	public static double toDoubleDefault(Object obj, double defaultValue) {
		try {
			return Double.parseDouble(obj.toString());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 把对象转换成String，如果对象为null返回null
	 * 
	 * @param obj
	 * @return
	 */
	public static String toStringNull(Object obj) {
		try {
			if (null != obj) {
				return String.valueOf(obj).trim();
			}
		} catch (Exception e) {
			//  e.printStackTrace();
		}
		return null;
	}

	public static double doubleMultiply(String value, String multiplicand)
			throws Exception {
		BigDecimal b1 = new BigDecimal(value);
		BigDecimal b2 = new BigDecimal(multiplicand);
		return b1.multiply(b2).doubleValue();
	}

	public static double doubleDivide(String value, String divisor)
			throws Exception {
		BigDecimal b1 = new BigDecimal(divisor);
		BigDecimal b2 = new BigDecimal(value);
		return b2.divide(b1).doubleValue();
	}

	public static double doubleAdd(String value, String augend) {
		BigDecimal b1 = new BigDecimal(value);
		BigDecimal b2 = new BigDecimal(augend);
		return b1.add(b2).doubleValue();
	}

	public static double doubleSubtract(String value, String subtrahend) {
		BigDecimal b1 = new BigDecimal(value);
		BigDecimal b2 = new BigDecimal(subtrahend);
		return b1.subtract(b2).doubleValue();
	}
	/**
	 * 在最后一个symbol字符前插入字符串
	 * 
	 * @param value 字符串
	 * * @param symbol 字符
	 * * @param str 插入的字符串
	 * @return
	 */
	public static String insterStrBeforeSymbol(String value, String symbol,String str) {
		StringBuilder sb=new StringBuilder(value);
		sb.insert(value.lastIndexOf(symbol),str);
		return sb.toString();
	}
	/**
	 * 在第一个symbol字符之前的字符串
	 * 
	 * @param value 字符串
	 * * @param symbol 字符
	 * * @param str 插入的字符串
	 * @return
	 */
	public static String subStrBeforeSymbol(String value, String symbol) {
		if(value.indexOf(symbol)>0)
			return value.substring(0, value.indexOf(symbol));
		return value;
	}
	/**
	 * 获取当前日期
	 * 日期格式 yyyy-MM-dd
	 * @return
	 * @throws UlbException 
	 */
	public static Date  getCurrentDate() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(df.format(new Date()));
	}
	
	/**
	 * 获取当前日期的字符串
	 * 日期格式 yyyy-MM-dd
	 * @return
	 * @throws UlbException 
	 */
	public static String  getStrCurrentDate() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getStrCurrentDate());
	}
}
