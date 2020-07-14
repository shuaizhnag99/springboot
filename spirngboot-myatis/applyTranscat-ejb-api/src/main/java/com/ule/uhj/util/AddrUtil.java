package com.ule.uhj.util;

public class AddrUtil {
    private static final String[] PROVINCE_ARR = {"河北", "山西", "吉林", "辽宁", "黑龙江", "陕西", "甘肃", "青海", "山东", "福建", "浙江",
            "河南", "湖北", "湖南", "江西", "江苏", "安徽", "广东", "海南", "四川", "贵州", "云南",
            "北京", "上海", "天津", "重庆", "内蒙古", "新疆", "宁夏", "广西", "西藏", "澳门", "香港", "台湾"};

    /**
     * 简化省份名称
     * @param provinceName
     * @return
     */
    public static String simpleProvince(String provinceName) {
        if (provinceName == null || "".equals(provinceName)) {
            return provinceName;
        }
        for (String p : PROVINCE_ARR) {
            if (provinceName.startsWith(p)) {
                return p;
            }
        }
        return provinceName;
    }

    /**
     * 判断是否为简化后的省份名称
     * @param provinceName
     * @return
     */
    public static boolean isSimpleProvince(String provinceName) {
        if (provinceName == null || "".equals(provinceName)) {
            return false;
        }
        for (String p : PROVINCE_ARR) {
            if (p.equals(provinceName)) {
                return true;
            }
        }
        return false;
    }
}
