package com.ule.uhj.util.data;

import java.util.Arrays;
import java.util.List;

public class PrdCodeChoose {

	static List<String> ule_ule_provinces = Arrays.asList("邮乐");
	static List<String> welab_psbc_provinces = Arrays.asList("浙江省邮政公司");
	static List<String> ule_psbc_provinces = Arrays.asList("江苏省邮政公司", "河北省邮政局",
			"河南省邮政局", "安徽省邮政局", "吉林省邮政局", "江西省邮政公司", "山东省邮政局", "福建省邮政公司",
			"湖北省邮政公司", "辽宁省邮政公司");
	static List<String> simple_ule_ule_provinces = Arrays.asList("邮乐");
	static List<String> simple_welab_psbc_provinces = Arrays.asList("浙江");
	static List<String> simple_ule_psbc_provinces = Arrays.asList("江苏", "河北",
			"河南", "安徽", "吉林", "江西", "山东", "福建", "湖北", "辽宁", "云南", "北京", "四川",
			"山西", "新疆", "甘肃", "陕西", "青海", "重庆");

	//江苏省、河北省、河南省、安徽省、吉林省、江西省、山东省、福建省、湖北省
	
//	"">全部省 	"175357">电商运营中心 	"38727">内蒙古自治区邮政公司	"43897">山西省邮政公司	"14202">河北省邮政局	"1775">北京邮政公司
//	"37023">辽宁省邮政公司	"30272">吉林省邮政局	"24476">黑龙江省邮政公司	"46967">上海市邮政公司	"31219">江苏省邮政公司	"101">安徽省邮政局
//	"40634">山东省邮政局	"50397">天津市邮政公司（省）	"54291">浙江省邮政公司	"35262">江西省邮政公司	"2732">福建省邮政公司
//	"57083">重庆市邮政公司	"27959">湖南省邮政公司	"26008">湖北省邮政公司	"16382">河南省邮政局	"4776">广东省邮政公司
//	"10302">广西区邮政公司	"11809">贵州省邮政公司	"13866">海南省邮政公司	"47552">四川省邮政公司	"52169">云南省邮政局
//	"45146">陕西省邮政公司	"4062">甘肃省邮政公司	"40199">宁夏邮政公司	"418453">澳门邮政555	"40433">青海省邮政局
//	"51178">新疆维吾尔自治区邮政公司	"50884">西藏邮政公司	"416980">邮乐海外	"58094">邮乐农品	"58093">邮乐
	
	/**
	 * 通过省份名称获取对应的掌柜贷产品编码
	 * @param provinceName
	 * @return
	 */
	public static PrdCode getPrdCodeByProvinceName(String provinceName){
		if(provinceName != null 
				&& provinceName.trim().length() != 0){
			String subProvinceName = provinceName.trim().substring(0,2);
			if(simple_welab_psbc_provinces.contains(subProvinceName)){
				return PrdCode.zgd_welab_psbc;
			}else if(simple_ule_ule_provinces.contains(subProvinceName)){
				return PrdCode.zgd_ule_ule;
			}else{
				return PrdCode.zgd_ule_yc;
			}
		}
		return null;
	}
//	public static void main(String[] args) {
//		System.out.println(getPrdCodeByProvinceName("是的省邮政局").getCode());
//	}

}