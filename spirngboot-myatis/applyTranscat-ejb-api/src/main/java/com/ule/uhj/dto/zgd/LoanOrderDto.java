package com.ule.uhj.dto.zgd;

import java.io.Serializable;
import java.util.List;

public class LoanOrderDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7393078465824350879L;
	
	/**
	 * 第三方用户id
	 */
	private String userOnlyId;
	/**
	 * 借款金额 
	 */
	private String applyAmount;
	
	/**
	 * 最多可借金额
	 */
	private String availBalance;
	
	/**
	 * 借款期数(可以不填)（邮储掌柜贷要传3）
	 */
	private String periods;
	
	/**
	 * 借款期限(多少个月  暂不用)
	 */
	private String timeLimit;
	
	/**
	 * 还款方式(100:按月付息到期还本)	
	 */
	private String repayType;
	/**
	 * 日利率
	 */
	private String interRate;
	/**
	 * 还款日(1-28)
	 */
	private String fixedRepayDate;
	/**
	 * 收款账户卡号
	 */
	private String receiveAcc;
	/**
	 * 收款账卡号集合
	 */
	private List<String> receiveAccs;
	/**
	 *IP地址
	 */
	private String applyIp;
	/**
	 * 合同版本号(第一版掌柜贷合同版本号为10001；邮储掌柜贷20001)	
	 */
	private String contractVer;
	/**
	 * 最低借款金额
	 */
	private String minApplyAmount;
	/**
	 * 是否还清借款 1还清  0未还清
	 */
	private String paidAll;
	
	/**
	 * 是否第一次签署合同 1已签署 0未签署（邮储掌柜贷使用）
	 * 邮储掌柜贷有3个合同，第一次全部要签署，之后只要签署两个合同
	 */
	private String signContract;
	
	/**
	 * 最后还款日期(最后偿还本金日期)
	 */
	private String lastRepayDate;
	/**
	 * 贷款利率
	 */
	private String loanRate;
	/**
	 * 服务费率
	 */
	private String serveRate;
	
	/**
	 * 费用预估
	 */
	private String forecast;
	
	/**
	 * 费用预估
	 */
	private String serviceCharge;
	
	public String getUserOnlyId() {
		return userOnlyId;
	}
	public void setUserOnlyId(String userOnlyId) {
		this.userOnlyId = userOnlyId;
	}
	public String getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(String applyAmount) {
		this.applyAmount = applyAmount;
	}
	public String getAvailBalance() {
		return availBalance;
	}
	public void setAvailBalance(String availBalance) {
		this.availBalance = availBalance;
	}
	public String getPeriods() {
		return periods;
	}
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String getRepayType() {
		return repayType;
	}
	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}
	public String getApplyIp() {
		return applyIp;
	}
	public void setApplyIp(String applyIp) {
		this.applyIp = applyIp;
	}
	public String getContractVer() {
		return contractVer;
	}
	public void setContractVer(String contractVer) {
		this.contractVer = contractVer;
	}
	public String getInterRate() {
		return interRate;
	}
	public void setInterRate(String interRate) {
		this.interRate = interRate;
	}
	public String getFixedRepayDate() {
		return fixedRepayDate;
	}
	public void setFixedRepayDate(String fixedRepayDate) {
		this.fixedRepayDate = fixedRepayDate;
	}
	public String getMinApplyAmount() {
		return minApplyAmount;
	}
	public void setMinApplyAmount(String minApplyAmount) {
		this.minApplyAmount = minApplyAmount;
	}
	public String getLastRepayDate() {
		return lastRepayDate;
	}
	public void setLastRepayDate(String lastRepayDate) {
		this.lastRepayDate = lastRepayDate;
	}
	public String getReceiveAcc() {
		return receiveAcc;
	}
	public void setReceiveAcc(String receiveAcc) {
		this.receiveAcc = receiveAcc;
	}
	public String getPaidAll() {
		return paidAll;
	}
	public void setPaidAll(String paidAll) {
		this.paidAll = paidAll;
	}
	public String getSignContract() {
		return signContract;
	}
	public void setSignContract(String signContract) {
		this.signContract = signContract;
	}
	public List<String> getReceiveAccs() {
		return receiveAccs;
	}
	public void setReceiveAccs(List<String> receiveAccs) {
		this.receiveAccs = receiveAccs;
	}
	public String getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(String loanRate) {
		this.loanRate = loanRate;
	}
	public String getServeRate() {
		return serveRate;
	}
	public void setServeRate(String serveRate) {
		this.serveRate = serveRate;
	}
	public String getForecast() {
		return forecast;
	}
	public void setForecast(String forecast) {
		this.forecast = forecast;
	}
	public String getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(String serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
}
