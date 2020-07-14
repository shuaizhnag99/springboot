package com.ule.uhj.dto.zgd;

import java.io.Serializable;

public class OrderDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 *借款订单号(订单类型+年月日+9为sequence)	
	*/
	private String id;

	/**
	 *系统内用户id唯一区分	
	*/
	private String userId;
	
	/**
	 * 申请人姓名
	 */
	private String userName;
	
	/**
	 * 申请人身份证号
	 */
	private String certNo;
	
	/**
	 * 申请人待放款卡的银行名称
	 */
	private String cardBankName;
	
	/**
	 * 申请人待放款卡的银行卡号
	 */
	private String cardNo;
	
	/**
	 *申请借款时间	
	*/
	private String orderTime;

	/**
	 *申请借款金额	
	*/
	private String orderAmount;

	/**
	 *状态(0:申请创建,1:申请通过,等待放款,2:申请被拒,3:已放款,4:放款成功,5:放款失败,6已生成借据,7已还清)	
	*/
	private String status;

	/**
	 *放款人员	
	*/
	private String loanor;

	/**
	 *放款时间	
	*/
	private String loanTime;

	/**
	 *放款金额	
	*/
	private String loanAmount;
	

	/**
	 * 掌柜所在省市
	 */
	private String provinceCity;
	/**
	 * 可借余额
	 */
	private String availBalance;
	/**
	 * 剩余余额
	 */
	
	private String remainingBalance;
	
	/**
	 *利率	
	*/
	private String interRate;	
	/**
	 * 期限
	 */
	private String lastRepayDate;
	
	
	private String userOnlyId;
	/**
	 * 审核日期
	 */
	private String reviewTime;
	/**
	 * 期数
	 */
	private String periods;
	/**
	 * 交易流水Id
	 */
	private String transactionId;
	
 
	private String accType; //账户类型
	
	private String accTypeText; //账户类型 文本
	
	private String repayType;//还款类型
	
	/**
	 * 邮储给出的信息结果（邮储信息）
	*/
	private String ycMessage;
	
	
	public String getYcMessage() {
		return ycMessage;
	}

	public void setYcMessage(String ycMessage) {
		this.ycMessage = ycMessage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getCardBankName() {
		return cardBankName;
	}

	public void setCardBankName(String cardBankName) {
		this.cardBankName = cardBankName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLoanor() {
		return loanor;
	}

	public void setLoanor(String loanor) {
		this.loanor = loanor;
	}

	public String getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(String loanTime) {
		this.loanTime = loanTime;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getProvinceCity() {
		return provinceCity;
	}

	public void setProvinceCity(String provinceCity) {
		this.provinceCity = provinceCity;
	}

	public String getAvailBalance() {
		return availBalance;
	}

	public void setAvailBalance(String availBalance) {
		this.availBalance = availBalance;
	}

	public String getRemainingBalance() {
		return remainingBalance;
	}

	public void setRemainingBalance(String remainingBalance) {
		this.remainingBalance = remainingBalance;
	}

	public String getInterRate() {
		return interRate;
	}

	public void setInterRate(String interRate) {
		this.interRate = interRate;
	}

	public String getLastRepayDate() {
		return lastRepayDate;
	}

	public void setLastRepayDate(String lastRepayDate) {
		this.lastRepayDate = lastRepayDate;
	}

	public String getUserOnlyId() {
		return userOnlyId;
	}

	public void setUserOnlyId(String userOnlyId) {
		this.userOnlyId = userOnlyId;
	}

	public String getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(String reviewTime) {
		this.reviewTime = reviewTime;
	}

	public String getPeriods() {
		return periods;
	}

	public void setPeriods(String periods) {
		this.periods = periods;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getAccTypeText() {
		return accTypeText;
	}

	public void setAccTypeText(String accTypeText) {
		this.accTypeText = accTypeText;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}
	
	
}
