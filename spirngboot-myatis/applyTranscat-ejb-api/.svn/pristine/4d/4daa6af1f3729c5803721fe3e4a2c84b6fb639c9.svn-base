package com.ule.uhj.Dcoffee.tools.inner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhengxin on 2018/3/28.
 */
public class DateUtil {
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>();
    public static final String TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE = "yyyy-MM-dd";

    public static String currentTime(){
        return create(TIME);
    }
    public static String currentDate(){
        return create(DATE);
    }
    public static String create(String pattrn){
        SimpleDateFormat simpleDateFormat = THREAD_LOCAL.get();
        if(simpleDateFormat==null){
            simpleDateFormat = new SimpleDateFormat(pattrn);
            THREAD_LOCAL.set(simpleDateFormat);
        }
        return simpleDateFormat.format(new Date());
    }
}
