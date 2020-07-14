package com.ule.uhj.dto.zgd;

import java.io.Serializable;

public class YCZgdDailySheetDto implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -789012425041338138L;
	
	/**
	 * 开始时间
	 */
	private String beginDate;
	/**
	 * 结束时间
	 */
	private String endDate;
	/**
	 * 省份
	 */
	private String provinceName;
	/**
	 * 城市
	 */
	private String cityName;
	/**
	 * 地区
	 */
	private String countyName;
	/**
	 * 报表名称
	 */
	private String sheetName;
	/**
	 * 字段中文名
	 */
	private String fieldCnName;
	
	private String userLoginName;
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getFieldCnName() {
		return fieldCnName;
	}
	public void setFieldCnName(String fieldCnName) {
		this.fieldCnName = fieldCnName;
	}
	public String getUserLoginName() {
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	
}
