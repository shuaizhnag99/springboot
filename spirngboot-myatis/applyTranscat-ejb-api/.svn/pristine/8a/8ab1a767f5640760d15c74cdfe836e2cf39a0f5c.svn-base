package com.ule.uhj.apienums;

import org.apache.commons.lang.StringUtils;

/**
 * 用户认证类型
 */
public enum CheckTypeEnum {
    CHECK10("check10", "实名认证"),
    CHECK20("check20", "申请人本人认证"),
    CHECK30("check30", "店铺营业执照认证"),
    CHECK40("check40", "身份证信息认证"),
    CHECK50("check50", "银行卡认证"),
    CHECK60("check60", "联系人信息完整认证"),
    CHECK70("check70", "配偶信息认证"),
    CHECK80("check80", "店铺信息认证"),
    CHECK90("check90", "渠道认证"),
    CHECK100("check100", "店铺实景认证"),

    ;

    private String code;
    private String desc;

    CheckTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static CheckTypeEnum getEnum(String code) {
        for (CheckTypeEnum e : CheckTypeEnum.values()) {
            if (StringUtils.equals(code, e.getCode())) {
                return e;
            }
        }
        return null;
    }

    public static String getDesc(String code) {
        for (CheckTypeEnum e : CheckTypeEnum.values()) {
            if (StringUtils.equals(code, e.getCode())) {
                return e.getDesc();
            }
        }
        return null;
    }
}
