package com.ule.uhj.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 掌柜再激活相关
 * Created by yfzx-sh-zhengxin on 2017/1/17.
 */
public enum ReActiveEnums {
    Z001("Z001",180,"经营主体工商网有行政处罚信息"),
    Z002("Z002",365,"经营主体有店铺转让，出租等负面信息"),
    Z003("Z003",180,"经营主体工商网登记成立时间不满1年"),
    Z004("Z004",360,"申请人征信查询结果不符合要求，不允许申请掌柜贷"),
    Z005("Z005",360,"申请人人法网有执行信息，且无法提供有效的结案证明"),
    Z006("Z006",360,"申请人命中高风险信息（当前逾期，历史严重逾期和黑名单）"),
    Z007("Z007",180,"申请人负债过高"),
    Z008("Z008",360,"联系人命中高风险信息（当前逾期，历史严重逾期和黑名单）"),
    Z009("Z009",31,"互斥产品在途"),
    Z010("Z010",360,"资料造假"),
    Z011("Z011",360,"非商超型掌柜"),
    Z012("Z012",90,"进销存销售额过大，有刷单行为"),
    Z013("Z013",90,"经营情况较差，授信额度过低"),
    Z014("Z014",180,"申请人及配偶近期申贷次数频繁"),
    Z015("Z015",180,"电核内容与申请资料不符"),
    Z016("Z016",30,"多次电核无人接听或不配合");

    private String code;
    private Integer time;
    private String desc;

    ReActiveEnums(String code, Integer time, String desc) {
        this.code = code;
        this.time = time;
        this.desc = desc;
    }

    public static List<Map<String,String>> getReActiveMap(){
        List<Map<String,String>> resultList = new ArrayList<Map<String, String>>();
        for(ReActiveEnums re : ReActiveEnums.values()){
            Map<String,String> reMap = new HashMap<String, String>();
            reMap.put("value",re.getCode());
            reMap.put("desc",re.getDesc());
            resultList.add(reMap);
        }
        return resultList;
    }
    public  static Map<String,Object> getReActiveEnums(String code){
    	Map<String,Object> reMap = new HashMap<String, Object>();
    	 for(ReActiveEnums re : ReActiveEnums.values()){
    		 if(re.getCode().equals(code)){
    			 reMap.put("value",re.getCode());
    			 reMap.put("desc",re.getDesc());
    			 reMap.put("time",re.getTime());
    			 
    		 }
              
         }
    	 return reMap;
   }
    public String getCode() {
        return code;
    }

//    public void setCode(String code) {
//        this.code = code;
//    }

    public Integer getTime() {
        return time;
    }

//    public void setTime(Integer time) {
//        this.time = time;
//    }

    public String getDesc() {
        return desc;
    }

//    public void setDesc(String desc) {
//        this.desc = desc;
//    }
}
