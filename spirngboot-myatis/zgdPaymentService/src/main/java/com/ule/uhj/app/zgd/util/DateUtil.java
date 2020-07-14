/*
 * DateUtil.java
 *
 * Copyright (c)2006��2007 TOM online, Inc.
 * Zhongguancun South Street, Haidian District, Beijing, the People's Republic of China, 100081
 * All Rights Reserved.
 *
 * This software is designed for the CTOC project.
 * any companies and individuals should not copy it to anywhere
 */
package com.ule.uhj.app.zgd.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ule.uhj.util.Check;

/**
 * Date utility,可以进行以下方式的日期格式化
 * 
 */
public class DateUtil {

	private static Log log = LogFactory.getLog(DateUtil.class);
	
    /**
     * yyyyMM
     */
    public static final String YM_SIMPLE = "yyyyMM";
    /**
     * yyyy-MM-dd
     */
    public static final String YMD = "yyyy-MM-dd";
    /**
     * HH:mm:ss
     */
    public static final String HMS = "HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyyMMddHHmmss
     */
    public static final String YMDHMS_SIMPLE = "yyyyMMddHHmmss";

    /**
     * yyyyMMddHH24miss
     */
    public static final String YMDHMS_ORACLE_LIST_PATTERN = "yyyyMMddHH24miss";
    
    /**
     * yyyyMMdd
     */
    public static final String YMD_SIMPLE = "yyyyMMdd";

    /**
     * yyyy/MM/dd HH:mm
     */
    public static final String YMDHM_PATTERN = "yyyy/MM/dd HH:mm";
    
    /**
     * yyyy/MM/dd
     */
    public static final String YMD_PATTERN = "yyyy/MM/dd";

    /**
     * yyyy/MM/dd HH:mm:ss
     */
    public static final String YMDHMS_PATTERN_2 = "yyyy/MM/dd HH:mm:ss";


    /**
     * yyyy-MM-dd
     */
    public static final String DEFAULT_PATTERN = YMD;

    
    /**
     * 是否是指定类型日期字符串
     * @param dateStr
     * @param pattern
     * @return
     */
    public static boolean isDate(String dateStr, String pattern){
    	if(dateStr == null || pattern == null){
    		return false;
    	}
    	try {
			if(dateStr.equals(format(parse(dateStr, pattern), pattern))){
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
    }

    /**
     * 获取系统当前时间
     * @return 系统当前时间
     */
    public static Date currentDate() throws Exception {
        return new Date();
    }

    /**
     * 返回给定格式pattern的日期，类型为String
     * @param pattern 日期格式
     * @return 字符串形式的日期
     */
    public static String currDateStr(final String pattern)
            throws Exception {
        return format(currentDate(), pattern);
    }

    /**
     * 默认date格式的字符串型日期，默认格式的日期格式为yyyy-MM-dd
     * @return 字符串型日期
     */
    public static String currDateStr() throws Exception {
        return format(currentDate(), DEFAULT_PATTERN);
    }
    
    /**
     * 默认date格式的字符串型日期，默认格式的日期格式为yyyy-MM-dd,但是日期固定为每月1日
     * @return 字符串型日期
     */
    public static String currDateStrOneDay() throws Exception {
    	String date = format(currentDate(), DEFAULT_PATTERN);
    	date = date.substring(0,date.length()-3) + "-01";
        return date;
    }
    
    /**
     * 默认date格式的字符串型日期，默认格式的日期格式为yyyyMMdd
     * @return 字符串型日期
     */
    public static String currDateSimpleStr() throws Exception {
        return format(currentDate(), YMD_SIMPLE);
    }
    /**
     * 默认time格式的字符串型日期，默认格式的日期格式为yyyy-MM-dd HH:mm:ss
     * @return 字符串型日期
     */
    public static String currTimeStr() throws Exception{
        return format(currentDate(), YMDHMS);
    }

    /**
     * 字符串形式的日期，格式为yyyyMMddHHmmss
     * @return 字符串类型的日期
     */
    public static String currTimeSimpleStr() throws Exception {
        return format(currentDate(), YMDHMS_SIMPLE);
    }

    /**
     * 前一天 默认date格式的字符串型日期，默认格式的日期格式为yyyy-MM-dd
     * @return 字符串型日期
     */
    public static String preDateStr() {
    	Calendar c = Calendar.getInstance();
    	c.setTime(new Date());
    	c.add(Calendar.DAY_OF_MONTH, -1);
        return format(c.getTime(), DEFAULT_PATTERN);
    }
    
    /**
     * 昨天 默认格式的日期格式为yyyy-MM-dd
     * @return 字符串型日期
     */
    public static String preDateStr(String strFormat) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(new Date());
    	c.add(Calendar.DAY_OF_MONTH, -1);
        return format(c.getTime(), strFormat);
    }
    
    /**
     * 根据传入的日期格式化pattern将传入的日期格式化成字符串。
     * 
     * @param date 要格式化的日期对象
     * @param pattern 日期格式化pattern
     * @return 格式化后的日期字符串
     */
    public static String format(final Date date, final String pattern) {
    	if (date == null){
    		return null;
    	}
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 将传入的日期按照默认形势转换成字符串(yyyy-MM-dd)
     * 
     * @param date 要格式化的日期对象
     * @return 格式化后的日期字符串
     */
    public static String format(final Date date) {
        return format(date, YMD);
    }

    /**
     * 根据传入的日期格式化patter将传入的字符串转换成日期对象
     * 
     * @param dateStr 要转换的字符串
     * @param pattern 日期格式化pattern
     * @return 转换后的日期对象
     */
    public static Date parse(final String dateStr, final String pattern) throws Exception {
    	if (StringUtils.isEmpty(dateStr)){
    		return null;
    	}
        DateFormat df = new SimpleDateFormat(pattern);
        return df.parse(dateStr);
    }

    /**
     * 将传入的字符串按照默认格式转换为日期对象(yyyy-MM-dd)
     * 
     * @param dateStr 要转换的字符串
     * @return 转换后的日期对象
     */
    public static Date parse(final String dateStr) throws Exception {
        return parse(dateStr, YMD);
    }
    
    /**
     * 
     * 
     * @param dateStr 要转换的字符串
     * @return 转换后的日期对象
     */
    public static Calendar newCalendar(String dateStr, String format) throws Exception {
    	Calendar c = Calendar.getInstance();
    	c.setTime(parse(dateStr, format));
        return c;
    }
    
    /**
     * 移除时分秒
     * @param c
     */
    public static void removeHMS(Calendar c){
    	if(c == null){
    		return;
    	}
    	c.set(Calendar.HOUR_OF_DAY, 0);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
    }
    
    /**
     * 日期差
     * @param after 被减数
     * @param before 减数
     * @return 日期差 单位天
     */
    public static int diffDays(Calendar after, Calendar before){
    	removeHMS(after);
    	removeHMS(before);
    	return (int) ((after.getTimeInMillis() - before.getTimeInMillis())/(1000*3600*24));
    }
    
    /**
     * 日期差 
     * @param after 被减数  
     * @param beforeStr 减数 
     * @param format 日期格式
     * @return 日期差 单位天
     * @throws Exception 
     */
    public static int diffDays(String afterInt, String beforeInt, String format) throws Exception{
    	Calendar after = Calendar.getInstance();
    	Calendar before = Calendar.getInstance();
    	after.setTime(parse(afterInt, format));
    	before.setTime(parse(beforeInt, format));
    	return diffDays(after, before);
    }
    
    /**
     * 将yyyy-MM-dd HH:mm:ss 格式的时间转换为日期 yyyy-MM-dd
     * @param timeStr  yyyy-MM-dd HH:mm:ss
     * @return  yyyy-MM-dd
     * @throws Exception
     */
    public static String time2Date(String timeStr) throws Exception{
    	if(isDate(timeStr, YMDHMS)){
    		return timeStr.substring(0,10);
    	}
    	throw new Exception("parameter " + timeStr + " is not a time!");
    }
    
    /**
	 * 日期计算
	 * 返回格式字符串：yyyy-MM-dd
	 * @param date
	 *            起始日期
	 * @param yearNum
	 *            年增减数
	 * @param monthNum
	 *            月增减数
	 * @param dateNum
	 *            日增减数
	 * @throws Exception 
	 */
	public static String calDate(String date, int yearNum, int monthNum,
			int dateNum) throws Exception {
		String result = null;
		try {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sd.parse(date));
			cal.add(Calendar.MONTH, monthNum);
			cal.add(Calendar.YEAR, yearNum);
			cal.add(Calendar.DATE, dateNum);
			result = sd.format(cal.getTime());
//			result = cal.getTime();
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	/**
	 * 返回计算过的日期字段
	 * @param dateStr 日期字符串
	 * @param format 日期格式
	 * @param field 日期进行计算的字段属性
	 * @param amount 日期进行计算的值
	 * @return
	 * @throws Exception 
	 */
	public static String calDateStr(String dateStr, String format, int field, int amount) throws Exception{
		Calendar c = Calendar.getInstance();
		c.setTime(parse(dateStr, format));
		c.add(field, amount);
		return format(c.getTime(), format);
	}
	
	/**
	 * 根据传入日期计算年龄
	 * @param dateStr 日期字符串  yyyy-MM-dd
	 * @return
	 * @throws Exception 
	 */
	public static String yearAge(String dateStr) throws Exception{
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date=new Date();       
		Date mydate= myFormatter.parse(dateStr); 
		long day=(date.getTime()-mydate.getTime())/(24*60*60*1000) + 1; 
		String year=new java.text.DecimalFormat("#.00").format(day/365f);
		log.info("niu:" + year);
		return year;
	}
	
	/**
	 * 根据传入日期计算年龄
	 * 只计算用户的实际月份  不考虑天   
	 * 然后用总月份除以12 
	 * @param dateStr 日期字符串  yyyy-MM-dd
	 * @return
	 * @throws Exception 
	 */
	public static double getAge(String dateStr, String dateFormatStr, int decimalLength) throws Exception{
		if(Check.isBlank(dateStr) || Check.isBlank(dateFormatStr) || decimalLength < 0){
			throw new Exception("please check paras!" + dateStr + "" + dateFormatStr + "" + decimalLength);
		}
		SimpleDateFormat f = new SimpleDateFormat(dateFormatStr); 
		Calendar l = Calendar.getInstance();
		l.setTime(f.parse(dateStr));
		Calendar c = Calendar.getInstance();
		int y = 0, m = 0;
		y = c.get(Calendar.YEAR) - l.get(Calendar.YEAR);
		m = c.get(Calendar.MONTH) - l.get(Calendar.MONTH);
//		log.info("出生年月日:" + dateStr + ";年龄:" + BigDecimal.valueOf(y).add(BigDecimal.valueOf(m).divide(BigDecimal.valueOf(12), decimalLength,  RoundingMode.HALF_UP)).doubleValue());
		return BigDecimal.valueOf(y).add(BigDecimal.valueOf(m).divide(BigDecimal.valueOf(12), decimalLength,  RoundingMode.HALF_UP)).doubleValue();
	}
    
    /** 
     *  afterDate - beforeDate 比较差值，单位为年或者月,舍位处理.
     *  比如差 1.99年 返回1年
     *  同理如果是 -1.99 也会返回-1
     * @param afterDate 被比较时间  
     * @param beforeDate 比较的初始时间  
     * @param diffType 比较类型   1为多少个月，2为多少年  
     * @return  
	 * @throws Exception 
     */ 
    public static int diffYearOrMonth(Date afterDate, Date beforeDate, int diffType) throws Exception{
    	log.info("afterDate"+afterDate+"..beforeDate..."+beforeDate+"..diffType."+diffType);
		if(afterDate != null && beforeDate != null){
			Calendar after = Calendar.getInstance();
			Calendar before = Calendar.getInstance();
			after.setTimeInMillis(afterDate.getTime());
			before.setTimeInMillis(beforeDate.getTime());
			boolean positive = afterDate.getTime() - beforeDate.getTime() > 0 ? true : false;
			if(!positive){
				Calendar tmp = after;
				after = before;
				before = tmp;
			}
			
			int diffY = after.get(Calendar.YEAR) - before.get(Calendar.YEAR);
			int diffM = after.get(Calendar.MONTH) - before.get(Calendar.MONTH);
			if(2 == diffType){
				before.add(Calendar.YEAR, diffY);
				if(before.getTimeInMillis() - after.getTimeInMillis() > 0){
					diffY--;
				}
				if(!positive){
					return 0 - diffY;
				}
				return diffY;
			}else if (1 == diffType) {
				before.add(Calendar.MONTH, diffY * 12 + diffM);
				if(before.getTimeInMillis() - after.getTimeInMillis() > 0){
					diffM--;
				}
				if(!positive){
					return 0 - (diffY * 12 + diffM);
				}
				return diffY * 12 + diffM;
			}else{
				throw new Exception("unknow diffType:" + diffType);
			}
		}else{
			throw new Exception("please check parameters:" + afterDate + ", " + beforeDate);
		}
    }
    /**
     * 获取time number天、月、年后的时间
     * @param time 时间 null 为系统当前时间
     * @param number 间隔数
     * @param type 0 天 1 月 2年
     * @param formate 返回时间格式 默认yyyy-MM-dd
     * @return
     */
    public static String getDateAfterTime(Date time,int number,String type,String formate){
    	String rey="";
    	if(null==time)time=new Date();
    	if(StringUtils.isBlank(formate))formate=YMD;
    	
    	if(StringUtils.isNotBlank(type)){
    		Calendar before = Calendar.getInstance();
    		before.setTimeInMillis(time.getTime());
    	
    		if("0".equals(type)){
    			before.add(Calendar.DATE, number);
    		}
    		else if("1".equals(type)){
    			before.add(Calendar.MONTH, number);
    		}else if("2".equals(type)){
    			before.add(Calendar.YEAR, number);
    		}
    		
    		rey=format(before.getTime(), formate);
    		
    	}
    	
    	return rey;
    }
    /**
     * 计算两个日期直接多少年（365天）带三位小数，为了后台计算系数、额度使用
     * @param afterDate
     * @param beforeDate
     * @return
     * @throws Exception
     */
    public static String getYears(Date afterDate,Date beforeDate) throws Exception{
    	 BigDecimal year=new BigDecimal(0);
   	 if(null!=afterDate&&null!=beforeDate){
   		 Calendar after = Calendar.getInstance();
   		 Calendar before = Calendar.getInstance();
   		 after.setTimeInMillis(afterDate.getTime());
   		 before.setTimeInMillis(beforeDate.getTime());
   		 boolean positive = afterDate.getTime() - beforeDate.getTime() > 0 ? true : false;
   		 if(!positive){
   			 Calendar tmp = after;
   			 after = before;
   			 before = tmp;
   		 }
   		 
   		 long  diffD = after.getTimeInMillis()-before.getTimeInMillis();
   		 double days = diffD / 1000d / 60 / 60 / 24;
   		 year=BigDecimal.valueOf(days).divide(BigDecimal.valueOf(365), 3, BigDecimal.ROUND_HALF_UP);
   		 
   	 }else{
   		throw new Exception("please check parameters:" + afterDate + ", " + beforeDate);
   	 }
    	return year.toString();
    }
    /**
     * 将时分秒的时间转换成秒数
     * @param time 时分秒的时间   格式 00:00:00
     * @return totalSec  秒数
     */
    public static String  formatTotalSec(String time) {
        String[] my =time.split(":");
        if(my.length<3)
        	return "0";
        int hour =Integer.parseInt(my[0]);
        int min =Integer.parseInt(my[1]);
        int sec =Integer.parseInt(my[2]);

        Long totalSec =(long) (hour*3600+min*60+sec);
		return totalSec.toString();

    }
    
    /**
     * 获取上个月月份
     * @return 上个月月份
     * @throws ParseException 
     */
    public static String  getPreMonth() {
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.MONTH, -1);
		return Integer.valueOf(new SimpleDateFormat("MM").format(c.getTime())).toString();
    }
    public static void main(String[] args) throws Exception {
    	System.out.println(DateUtil.parse("20151114", "yyyy-MM-dd"));
    	int cj=diffDays(DateUtil.currDateSimpleStr(),"2017-04-28", DateUtil.YMD_SIMPLE);
//    	SimpleDateFormat fal=	new SimpleDateFormat("yyyyMMdd");
//    	Date s=fal.parse("2017-04-28");
//    	String ss=fal.format(s);	
    	System.out.println(cj);
//    	System.out.println(calDate(DateUtil.currDateStr(), 0, 0, 30));
//    	
//    	
//    	
////		log.info("result:" + diffYearOrMonth(parse("2016-01-25 11:32:00", YMDHMS), parse("2016-03-25 11:33:00", YMDHMS), 1));
//		log.info("--------------");
////		log.info("result:" + getYears(parse("2016-03-25 11:33:00", YMDHMS), parse("2015-03-25 10:33:00", YMDHMS)));
//		log.info("--------------");
//		log.info("result:" + diffYearOrMonth(parse("2016-10-12 10:33:00", YMD_SIMPLE), parse("1984-12-11", YMD_SIMPLE), 2));
////		log.info("--------------");
////		log.info("result:" + diffYearOrMonth(parse("2016-03-25 11:33:00", YMDHMS), parse("2015-03-25 12:33:00", YMDHMS), 2));

	}
    

}
