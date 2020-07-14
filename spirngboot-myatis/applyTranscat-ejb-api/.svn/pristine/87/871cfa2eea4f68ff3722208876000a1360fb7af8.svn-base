package com.ule.uhj.apienums;


import org.apache.commons.lang.StringUtils;

/**
 * 一级渠道
 */
public enum ChannelCodeEnum {
    POST("C00", "邮政"),
    PSBC("C01", "邮储"),
    ULE("C02", "邮乐"),

    ;

    private String code;
    private String desc;

    ChannelCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ChannelCodeEnum getEnum(String code) {
        for (ChannelCodeEnum e : ChannelCodeEnum.values()) {
            if (StringUtils.equals(code, e.getCode())) {
                return e;
            }
        }
        return null;
    }

    public static String getDesc(String code) {
        for (ChannelCodeEnum e : ChannelCodeEnum.values()) {
            if (StringUtils.equals(code, e.getCode())) {
                return e.getDesc();
            }
        }
        return null;
    }
}
