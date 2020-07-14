package com.ule.uhj.dic;

import org.apache.commons.lang.StringUtils;

/**
 * 支付方式 批销、机具、邮赊购（一期使用，邮赊购后期优化前使用）repaymentMethod 来源uhjconstants
 * @author zhangshuai
 *
 */
public enum PaymentDic {
	px01("px01","px",7.2,"1200","一次性还本付息",1,"普通批销"),
	px02("px02","px",7.2,"0200","等额本息",24,"机具商品"),
	ysg01("ysg01","ysg",18.0,"0200","等额本息",3,"邮赊购"),
	ysg02("ysg02","ysg",18.0,"0200","等额本息",6,"邮赊购");
	private String code;
	private String category;
	private double interate;
	private String repaymentMethod;
	private String repaymentMethodDes;
	private int periods;
	private String desc;
	private PaymentDic(String code,String category,double interate,String repaymentMethod,String repaymentMethodDes,int periods,String desc) {
		this.code=code;
		this.category=category;
		this.interate=interate;
		this.repaymentMethod=repaymentMethod;
		this.repaymentMethodDes=repaymentMethodDes;
		this.periods=periods;
		this.desc=desc;
	}
	public String getCode() {
		return code;
	}
//	public void setCode(String code) {
//		this.code = code;
//	}
	public String getCategory() {
		return category;
	}
//	public void setCategory(String category) {
//		this.category = category;
//	}
	public double getInterate() {
		return interate;
	}
//	public void setInterate(double interate) {
//		this.interate = interate;
//	}
	public String getRepaymentMethod() {
		return repaymentMethod;
	}
//	public void setRepaymentMethod(String repaymentMethod) {
//		this.repaymentMethod = repaymentMethod;
//	}
	
	public String getRepaymentMethodDes() {
		return repaymentMethodDes;
	}
//	public void setRepaymentMethodDes(String repaymentMethodDes) {
//		this.repaymentMethodDes = repaymentMethodDes;
//	}
	public int getPeriods() {
		return periods;
	}
//	public void setPeriods(int periods) {
//		this.periods = periods;
//	}
	 
	
    public String getDesc() {
		return desc;
	}
//	public void setDesc(String desc) {
//		this.desc = desc;
//	}
	public static PaymentDic getByCode(String code) {
    	if(StringUtils.isNotBlank(code)) {
    		for(PaymentDic py: PaymentDic.values()) {
    			if(code.equals(py.getCode())) {
    				return py;
    			}
    		}
    		
    	}
    	return null;
    }
	
//	public static void main(String[] agrs) {
//		PaymentDic d=getByCode("ysg01");
//		System.out.println(d.name());
//	}
}
