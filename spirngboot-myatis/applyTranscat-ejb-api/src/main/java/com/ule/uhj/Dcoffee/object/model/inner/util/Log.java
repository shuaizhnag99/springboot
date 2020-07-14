package com.ule.uhj.Dcoffee.object.model.inner.util;

/**
 * Created by zhengxin on 2018/3/13.
 */
public class Log {
    public static final int LEVEL_INFO = 1;
    public static final int LEVEL_WARN = 2;
    public static final int LEVEL_ERROR = 0;
    public static final int LEVEL_DEBUG = 3;

    private int level;

    private String logMessage;

    public Log(int level,String logMessage){
        this.level = level;
        this.logMessage = logMessage;
    }

    @Override
    public String toString() {
        return "Log{" +
                "level=" + level +
                ", logMessage='" + logMessage + '\'' +
                '}';
    }

    public static Log info(String logMessage){
        return new Log(Log.LEVEL_INFO,logMessage);
    }

    public static Log debug(String logMessage){
        return new Log(Log.LEVEL_DEBUG,logMessage);
    }

    public static Log warn(String logMessage){
        return new Log(Log.LEVEL_WARN,logMessage);
    }

    public static Log error(String logMessage,Throwable e){
        return new Log(Log.LEVEL_ERROR,logMessage);
    }

}
