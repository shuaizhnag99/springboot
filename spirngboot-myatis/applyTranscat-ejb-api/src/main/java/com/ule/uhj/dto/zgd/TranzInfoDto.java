package com.ule.uhj.dto.zgd;

import java.io.Serializable;

public class TranzInfoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8985208007303616374L;
	/**
	 * 交易流水号
	 */
	private String transSrialId;
	/**
	 * 掌柜姓名
	 */
	private String userName;
	/**
	 * 掌柜ID
	 */
	private String userOnlyId;
	/**
	 * 省市
	 */
	private String provinceName;
	/**
	 * 还款时间
	 */
	private String tranzTime;
	/**
	 * 还款期数
	 */
	private String currPeriod;
	/**
	 * 总期数
	 */
	private String totalPeriod;
	/**
	 * 还款总金额
	 */
	private String tranzAmount;
	/**
	 * 其中本金
	 */
	private String rdPrinc;
	/**
	 * 其中利息
	 */
	private String rdInter;
	/**
	 * 逾期本金
	 */
	private String rdPrincOver;
	/**
	 * 逾期利息
	 */
	private String rdInterOver;
 
	public String getTransSrialId() {
		return transSrialId;
	}
	public void setTransSrialId(String transSrialId) {
		this.transSrialId = transSrialId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	 
	public String getUserOnlyId() {
		return userOnlyId;
	}
	public void setUserOnlyId(String userOnlyId) {
		this.userOnlyId = userOnlyId;
	}
	 
	public String getTranzTime() {
		return tranzTime;
	}
	public void setTranzTime(String tranzTime) {
		this.tranzTime = tranzTime;
	}
	public String getCurrPeriod() {
		return currPeriod;
	}
	public void setCurrPeriod(String currPeriod) {
		this.currPeriod = currPeriod;
	}
	public String getTotalPeriod() {
		return totalPeriod;
	}
	public void setTotalPeriod(String totalPeriod) {
		this.totalPeriod = totalPeriod;
	}
	public String getTranzAmount() {
		return tranzAmount;
	}
	public void setTranzAmount(String tranzAmount) {
		this.tranzAmount = tranzAmount;
	}
	public String getRdPrinc() {
		return rdPrinc;
	}
	public void setRdPrinc(String rdPrinc) {
		this.rdPrinc = rdPrinc;
	}
	public String getRdInter() {
		return rdInter;
	}
	public void setRdInter(String rdInter) {
		this.rdInter = rdInter;
	}
	public String getRdPrincOver() {
		return rdPrincOver;
	}
	public void setRdPrincOver(String rdPrincOver) {
		this.rdPrincOver = rdPrincOver;
	}
	public String getRdInterOver() {
		return rdInterOver;
	}
	public void setRdInterOver(String rdInterOver) {
		this.rdInterOver = rdInterOver;
	}
	
	

}
