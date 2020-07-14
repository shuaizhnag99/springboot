package com.ule.uhj.apienums;

/**
 * 用户认证状态
 */
public enum CheckStatusEnum {
    STATUS_0(0, "初始默认值"),
    STATUS_10(10, "信息未实名"),
    STATUS_20(21, "认证失败-未超限"),
    STATUS_22(22, "认证中-需人工核实"),
    STATUS_31(31, "认证中-未超限"),
    STATUS_32(32, "认证失败-超限未查得"),
    STATUS_88(88, "认证成功"),
    STATUS_99(99, "认证失败"),
    STATUS_91(91, "认证跳过"),
    STATUS_101(101, "续期合影过期"),

    ;

    private int code;
    private String desc;

    CheckStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static CheckStatusEnum getEnum(int code) {
        for (CheckStatusEnum e : CheckStatusEnum.values()) {
            if (code == e.getCode()) {
                return e;
            }
        }
        return null;
    }

    public static String getDesc(int code) {
        for (CheckStatusEnum e : CheckStatusEnum.values()) {
            if (code == e.getCode()) {
                return e.getDesc();
            }
        }
        return null;
    }
}