package com.ule.uhj.dto.zgd;

import java.io.Serializable;

public class SecondRepaySourceDto implements Serializable {
	
	private static final long serialVersionUID = 790206604922915358L;

	/**
	 *记录id
	*/
	private String recordId;

	/**
	 *用户id
	*/
	private String userOnlyId;

	/**
	 *用户名	
	*/
	private String userName;

	/**
	 *年收入	
	*/
	private String yearIncome;

	/**
	 *职业	
	*/
	private String industries;

	/**
	 *职位	
	*/
	private String profession;

	/**
	 *从业年限
	*/
	private String year;
	
	/**
	 *记录创建时间	
	*/
	private String createTime;

	/**
	 *记录更新时间	
	*/
	private String updateTime;

	/**
	 *备注	
	*/
	private String remark;

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getUserOnlyId() {
		return userOnlyId;
	}

	public void setUserOnlyId(String userOnlyId) {
		this.userOnlyId = userOnlyId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getYearIncome() {
		return yearIncome;
	}

	public void setYearIncome(String yearIncome) {
		this.yearIncome = yearIncome;
	}

	public String getIndustries() {
		return industries;
	}

	public void setIndustries(String industries) {
		this.industries = industries;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
