package com.ule.uhj.apienums;

import org.apache.commons.lang.StringUtils;

/**
 * 助手身份识别
 */
public enum IdentityIdEnum {
    CHANNEL_PLATFORM("0", "我是渠道平台地推"),
    AGENCY_FINANCE("1", "我是代理金融地推"),

    ;

    private String code;
    private String desc;

    IdentityIdEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static IdentityIdEnum getEnum(String code) {
        for (IdentityIdEnum e : IdentityIdEnum.values()) {
            if (StringUtils.equals(code, e.getCode())) {
                return e;
            }
        }
        return null;
    }

    public static String getDesc(String code) {
        for (IdentityIdEnum e : IdentityIdEnum.values()) {
            if (StringUtils.equals(code, e.getCode())) {
                return e.getDesc();
            }
        }
        return null;
    }
}
