package com.ule.uhj.dto.zgd;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ProductInfoN implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4755182265886912507L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.ID
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private BigDecimal id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.PRD_CODE
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String prdCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.PRD_NAME
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String prdName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.DESCRIPTION
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.CHANNEL_CODE
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String channelCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.USE_TYPE
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String useType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.GUARANTEE_WAY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String guaranteeWay;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.MIN_INTEREST
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private BigDecimal minInterest;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.MAX_INTEREST
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private BigDecimal maxInterest;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.MIN_LIMIT
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private BigDecimal minLimit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.MAX_LIMIT
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private BigDecimal maxLimit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.MIN_PERIOD
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private BigDecimal minPeriod;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.MAX_PERIOD
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private BigDecimal maxPeriod;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.PAY_WAY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String payWay;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.SCOPE_BELONG_APPLY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String scopeBelongApply;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.ACHIEVEMENT_BELONG_APPLY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String achievementBelongApply;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.LOAN_BELONG_APPLY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String loanBelongApply;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.PHOTO_CHECK
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String photoCheck;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.BIND_STAFF
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String bindStaff;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.APPROVAL_PROGRESS
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String approvalProgress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.CREATE_USER
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.CREATE_TIME
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.UPDATE_USER
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String updateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.UPDATE_TIME
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.FLOAT_LIMIT
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String floatLimit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.APPLY_PROGRESS
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String applyProgress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.PERSON_BELONG_APPLY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String personBelongApply;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.SCOPE_BELONG_LOAN
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String scopeBelongLoan;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_INFO_N.PERSON_BELONG_LOAN
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    private String personBelongLoan;
    
    /**
     * 基础利率规则
     */
    private ProductRule interestRule;
    
    /**
     * 期限规则
     */
    private ProductRule periodRule;
    
    /**
     * 还款方式规则
     */
    private ProductRule repayWayRule;
    
    /**
     * 受托信息列表
     */
    private List<ProductEntrustInfo> entrustList;
    

    public List<ProductEntrustInfo> getEntrustList() {
		return entrustList;
	}

	public void setEntrustList(List<ProductEntrustInfo> entrustList) {
		this.entrustList = entrustList;
	}

	public ProductRule getInterestRule() {
		return interestRule;
	}

	public void setInterestRule(ProductRule interestRule) {
		this.interestRule = interestRule;
	}

	public ProductRule getPeriodRule() {
		return periodRule;
	}

	public void setPeriodRule(ProductRule periodRule) {
		this.periodRule = periodRule;
	}

	public ProductRule getRepayWayRule() {
		return repayWayRule;
	}

	public void setRepayWayRule(ProductRule repayWayRule) {
		this.repayWayRule = repayWayRule;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.ID
     *
     * @return the value of T_J_PRODUCT_INFO_N.ID
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.ID
     *
     * @param id the value for T_J_PRODUCT_INFO_N.ID
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.PRD_CODE
     *
     * @return the value of T_J_PRODUCT_INFO_N.PRD_CODE
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getPrdCode() {
        return prdCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.PRD_CODE
     *
     * @param prdCode the value for T_J_PRODUCT_INFO_N.PRD_CODE
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setPrdCode(String prdCode) {
        this.prdCode = prdCode == null ? null : prdCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.PRD_NAME
     *
     * @return the value of T_J_PRODUCT_INFO_N.PRD_NAME
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getPrdName() {
        return prdName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.PRD_NAME
     *
     * @param prdName the value for T_J_PRODUCT_INFO_N.PRD_NAME
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setPrdName(String prdName) {
        this.prdName = prdName == null ? null : prdName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.DESCRIPTION
     *
     * @return the value of T_J_PRODUCT_INFO_N.DESCRIPTION
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.DESCRIPTION
     *
     * @param description the value for T_J_PRODUCT_INFO_N.DESCRIPTION
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.CHANNEL_CODE
     *
     * @return the value of T_J_PRODUCT_INFO_N.CHANNEL_CODE
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.CHANNEL_CODE
     *
     * @param channelCode the value for T_J_PRODUCT_INFO_N.CHANNEL_CODE
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.USE_TYPE
     *
     * @return the value of T_J_PRODUCT_INFO_N.USE_TYPE
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getUseType() {
        return useType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.USE_TYPE
     *
     * @param useType the value for T_J_PRODUCT_INFO_N.USE_TYPE
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setUseType(String useType) {
        this.useType = useType == null ? null : useType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.GUARANTEE_WAY
     *
     * @return the value of T_J_PRODUCT_INFO_N.GUARANTEE_WAY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getGuaranteeWay() {
        return guaranteeWay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.GUARANTEE_WAY
     *
     * @param guaranteeWay the value for T_J_PRODUCT_INFO_N.GUARANTEE_WAY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setGuaranteeWay(String guaranteeWay) {
        this.guaranteeWay = guaranteeWay == null ? null : guaranteeWay.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.MIN_INTEREST
     *
     * @return the value of T_J_PRODUCT_INFO_N.MIN_INTEREST
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public BigDecimal getMinInterest() {
        return minInterest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.MIN_INTEREST
     *
     * @param minInterest the value for T_J_PRODUCT_INFO_N.MIN_INTEREST
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setMinInterest(BigDecimal minInterest) {
        this.minInterest = minInterest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.MAX_INTEREST
     *
     * @return the value of T_J_PRODUCT_INFO_N.MAX_INTEREST
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public BigDecimal getMaxInterest() {
        return maxInterest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.MAX_INTEREST
     *
     * @param maxInterest the value for T_J_PRODUCT_INFO_N.MAX_INTEREST
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setMaxInterest(BigDecimal maxInterest) {
        this.maxInterest = maxInterest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.MIN_LIMIT
     *
     * @return the value of T_J_PRODUCT_INFO_N.MIN_LIMIT
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public BigDecimal getMinLimit() {
        return minLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.MIN_LIMIT
     *
     * @param minLimit the value for T_J_PRODUCT_INFO_N.MIN_LIMIT
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setMinLimit(BigDecimal minLimit) {
        this.minLimit = minLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.MAX_LIMIT
     *
     * @return the value of T_J_PRODUCT_INFO_N.MAX_LIMIT
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public BigDecimal getMaxLimit() {
        return maxLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.MAX_LIMIT
     *
     * @param maxLimit the value for T_J_PRODUCT_INFO_N.MAX_LIMIT
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setMaxLimit(BigDecimal maxLimit) {
        this.maxLimit = maxLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.MIN_PERIOD
     *
     * @return the value of T_J_PRODUCT_INFO_N.MIN_PERIOD
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public BigDecimal getMinPeriod() {
        return minPeriod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.MIN_PERIOD
     *
     * @param minPeriod the value for T_J_PRODUCT_INFO_N.MIN_PERIOD
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setMinPeriod(BigDecimal minPeriod) {
        this.minPeriod = minPeriod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.MAX_PERIOD
     *
     * @return the value of T_J_PRODUCT_INFO_N.MAX_PERIOD
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public BigDecimal getMaxPeriod() {
        return maxPeriod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.MAX_PERIOD
     *
     * @param maxPeriod the value for T_J_PRODUCT_INFO_N.MAX_PERIOD
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setMaxPeriod(BigDecimal maxPeriod) {
        this.maxPeriod = maxPeriod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.PAY_WAY
     *
     * @return the value of T_J_PRODUCT_INFO_N.PAY_WAY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getPayWay() {
        return payWay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.PAY_WAY
     *
     * @param payWay the value for T_J_PRODUCT_INFO_N.PAY_WAY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setPayWay(String payWay) {
        this.payWay = payWay == null ? null : payWay.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.SCOPE_BELONG_APPLY
     *
     * @return the value of T_J_PRODUCT_INFO_N.SCOPE_BELONG_APPLY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getScopeBelongApply() {
        return scopeBelongApply;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.SCOPE_BELONG_APPLY
     *
     * @param scopeBelongApply the value for T_J_PRODUCT_INFO_N.SCOPE_BELONG_APPLY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setScopeBelongApply(String scopeBelongApply) {
        this.scopeBelongApply = scopeBelongApply == null ? null : scopeBelongApply.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.ACHIEVEMENT_BELONG_APPLY
     *
     * @return the value of T_J_PRODUCT_INFO_N.ACHIEVEMENT_BELONG_APPLY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getAchievementBelongApply() {
        return achievementBelongApply;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.ACHIEVEMENT_BELONG_APPLY
     *
     * @param achievementBelongApply the value for T_J_PRODUCT_INFO_N.ACHIEVEMENT_BELONG_APPLY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setAchievementBelongApply(String achievementBelongApply) {
        this.achievementBelongApply = achievementBelongApply == null ? null : achievementBelongApply.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.LOAN_BELONG_APPLY
     *
     * @return the value of T_J_PRODUCT_INFO_N.LOAN_BELONG_APPLY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getLoanBelongApply() {
        return loanBelongApply;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.LOAN_BELONG_APPLY
     *
     * @param loanBelongApply the value for T_J_PRODUCT_INFO_N.LOAN_BELONG_APPLY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setLoanBelongApply(String loanBelongApply) {
        this.loanBelongApply = loanBelongApply == null ? null : loanBelongApply.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.PHOTO_CHECK
     *
     * @return the value of T_J_PRODUCT_INFO_N.PHOTO_CHECK
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getPhotoCheck() {
        return photoCheck;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.PHOTO_CHECK
     *
     * @param photoCheck the value for T_J_PRODUCT_INFO_N.PHOTO_CHECK
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setPhotoCheck(String photoCheck) {
        this.photoCheck = photoCheck == null ? null : photoCheck.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.BIND_STAFF
     *
     * @return the value of T_J_PRODUCT_INFO_N.BIND_STAFF
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getBindStaff() {
        return bindStaff;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.BIND_STAFF
     *
     * @param bindStaff the value for T_J_PRODUCT_INFO_N.BIND_STAFF
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setBindStaff(String bindStaff) {
        this.bindStaff = bindStaff == null ? null : bindStaff.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.APPROVAL_PROGRESS
     *
     * @return the value of T_J_PRODUCT_INFO_N.APPROVAL_PROGRESS
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getApprovalProgress() {
        return approvalProgress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.APPROVAL_PROGRESS
     *
     * @param approvalProgress the value for T_J_PRODUCT_INFO_N.APPROVAL_PROGRESS
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setApprovalProgress(String approvalProgress) {
        this.approvalProgress = approvalProgress == null ? null : approvalProgress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.CREATE_USER
     *
     * @return the value of T_J_PRODUCT_INFO_N.CREATE_USER
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.CREATE_USER
     *
     * @param createUser the value for T_J_PRODUCT_INFO_N.CREATE_USER
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.CREATE_TIME
     *
     * @return the value of T_J_PRODUCT_INFO_N.CREATE_TIME
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.CREATE_TIME
     *
     * @param createTime the value for T_J_PRODUCT_INFO_N.CREATE_TIME
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.UPDATE_USER
     *
     * @return the value of T_J_PRODUCT_INFO_N.UPDATE_USER
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.UPDATE_USER
     *
     * @param updateUser the value for T_J_PRODUCT_INFO_N.UPDATE_USER
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.UPDATE_TIME
     *
     * @return the value of T_J_PRODUCT_INFO_N.UPDATE_TIME
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.UPDATE_TIME
     *
     * @param updateTime the value for T_J_PRODUCT_INFO_N.UPDATE_TIME
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.FLOAT_LIMIT
     *
     * @return the value of T_J_PRODUCT_INFO_N.FLOAT_LIMIT
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getFloatLimit() {
        return floatLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.FLOAT_LIMIT
     *
     * @param floatLimit the value for T_J_PRODUCT_INFO_N.FLOAT_LIMIT
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setFloatLimit(String floatLimit) {
        this.floatLimit = floatLimit == null ? null : floatLimit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.APPLY_PROGRESS
     *
     * @return the value of T_J_PRODUCT_INFO_N.APPLY_PROGRESS
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getApplyProgress() {
        return applyProgress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.APPLY_PROGRESS
     *
     * @param applyProgress the value for T_J_PRODUCT_INFO_N.APPLY_PROGRESS
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setApplyProgress(String applyProgress) {
        this.applyProgress = applyProgress == null ? null : applyProgress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.PERSON_BELONG_APPLY
     *
     * @return the value of T_J_PRODUCT_INFO_N.PERSON_BELONG_APPLY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getPersonBelongApply() {
        return personBelongApply;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.PERSON_BELONG_APPLY
     *
     * @param personBelongApply the value for T_J_PRODUCT_INFO_N.PERSON_BELONG_APPLY
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setPersonBelongApply(String personBelongApply) {
        this.personBelongApply = personBelongApply == null ? null : personBelongApply.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.SCOPE_BELONG_LOAN
     *
     * @return the value of T_J_PRODUCT_INFO_N.SCOPE_BELONG_LOAN
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getScopeBelongLoan() {
        return scopeBelongLoan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.SCOPE_BELONG_LOAN
     *
     * @param scopeBelongLoan the value for T_J_PRODUCT_INFO_N.SCOPE_BELONG_LOAN
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setScopeBelongLoan(String scopeBelongLoan) {
        this.scopeBelongLoan = scopeBelongLoan == null ? null : scopeBelongLoan.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_INFO_N.PERSON_BELONG_LOAN
     *
     * @return the value of T_J_PRODUCT_INFO_N.PERSON_BELONG_LOAN
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public String getPersonBelongLoan() {
        return personBelongLoan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_INFO_N.PERSON_BELONG_LOAN
     *
     * @param personBelongLoan the value for T_J_PRODUCT_INFO_N.PERSON_BELONG_LOAN
     *
     * @mbggenerated Fri Mar 30 16:46:24 CST 2018
     */
    public void setPersonBelongLoan(String personBelongLoan) {
        this.personBelongLoan = personBelongLoan == null ? null : personBelongLoan.trim();
    }
}