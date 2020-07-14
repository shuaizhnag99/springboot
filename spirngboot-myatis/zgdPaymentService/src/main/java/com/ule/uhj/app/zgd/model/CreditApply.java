package com.ule.uhj.app.zgd.model;

import java.math.BigDecimal;

public class CreditApply {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_APPLY.ID
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_APPLY.USER_ONLY_ID
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    private String userOnlyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_APPLY.PRODUCT_CODE
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    private String productCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_APPLY.APPLY_TIME
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    private String applyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_APPLY.STATUS
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_APPLY.CREATE_USER
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    private String createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_APPLY.CREATE_TIME
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    private String createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_APPLY.UPDATE_TIME
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    private String updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_APPLY.UPDATE_USER
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    private String updateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_APPLY.PROCESS_INSTANCE_ID
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    private String processInstanceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_APPLY.REVERNUE_REGRESS_RESULT
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    private String revernueRegressResult;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_APPLY.CREDIT_BASE_TYPE
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    private String creditBaseType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_APPLY.CREDIT_BASE_AMOUNT
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    private BigDecimal creditBaseAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_APPLY.CREDIT_LIMIT
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    private BigDecimal creditLimit;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_APPLY.ID
     *
     * @return the value of T_J_CREDIT_APPLY.ID
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_APPLY.ID
     *
     * @param id the value for T_J_CREDIT_APPLY.ID
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_APPLY.USER_ONLY_ID
     *
     * @return the value of T_J_CREDIT_APPLY.USER_ONLY_ID
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public String getUserOnlyId() {
        return userOnlyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_APPLY.USER_ONLY_ID
     *
     * @param userOnlyId the value for T_J_CREDIT_APPLY.USER_ONLY_ID
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public void setUserOnlyId(String userOnlyId) {
        this.userOnlyId = userOnlyId == null ? null : userOnlyId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_APPLY.PRODUCT_CODE
     *
     * @return the value of T_J_CREDIT_APPLY.PRODUCT_CODE
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_APPLY.PRODUCT_CODE
     *
     * @param productCode the value for T_J_CREDIT_APPLY.PRODUCT_CODE
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_APPLY.APPLY_TIME
     *
     * @return the value of T_J_CREDIT_APPLY.APPLY_TIME
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public String getApplyTime() {
        return applyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_APPLY.APPLY_TIME
     *
     * @param applyTime the value for T_J_CREDIT_APPLY.APPLY_TIME
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime == null ? null : applyTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_APPLY.STATUS
     *
     * @return the value of T_J_CREDIT_APPLY.STATUS
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_APPLY.STATUS
     *
     * @param status the value for T_J_CREDIT_APPLY.STATUS
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_APPLY.CREATE_USER
     *
     * @return the value of T_J_CREDIT_APPLY.CREATE_USER
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_APPLY.CREATE_USER
     *
     * @param createUser the value for T_J_CREDIT_APPLY.CREATE_USER
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_APPLY.CREATE_TIME
     *
     * @return the value of T_J_CREDIT_APPLY.CREATE_TIME
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_APPLY.CREATE_TIME
     *
     * @param createTime the value for T_J_CREDIT_APPLY.CREATE_TIME
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_APPLY.UPDATE_TIME
     *
     * @return the value of T_J_CREDIT_APPLY.UPDATE_TIME
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_APPLY.UPDATE_TIME
     *
     * @param updateTime the value for T_J_CREDIT_APPLY.UPDATE_TIME
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_APPLY.UPDATE_USER
     *
     * @return the value of T_J_CREDIT_APPLY.UPDATE_USER
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_APPLY.UPDATE_USER
     *
     * @param updateUser the value for T_J_CREDIT_APPLY.UPDATE_USER
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_APPLY.PROCESS_INSTANCE_ID
     *
     * @return the value of T_J_CREDIT_APPLY.PROCESS_INSTANCE_ID
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_APPLY.PROCESS_INSTANCE_ID
     *
     * @param processInstanceId the value for T_J_CREDIT_APPLY.PROCESS_INSTANCE_ID
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId == null ? null : processInstanceId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_APPLY.REVERNUE_REGRESS_RESULT
     *
     * @return the value of T_J_CREDIT_APPLY.REVERNUE_REGRESS_RESULT
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public String getRevernueRegressResult() {
        return revernueRegressResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_APPLY.REVERNUE_REGRESS_RESULT
     *
     * @param revernueRegressResult the value for T_J_CREDIT_APPLY.REVERNUE_REGRESS_RESULT
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public void setRevernueRegressResult(String revernueRegressResult) {
        this.revernueRegressResult = revernueRegressResult == null ? null : revernueRegressResult.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_APPLY.CREDIT_BASE_TYPE
     *
     * @return the value of T_J_CREDIT_APPLY.CREDIT_BASE_TYPE
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public String getCreditBaseType() {
        return creditBaseType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_APPLY.CREDIT_BASE_TYPE
     *
     * @param creditBaseType the value for T_J_CREDIT_APPLY.CREDIT_BASE_TYPE
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public void setCreditBaseType(String creditBaseType) {
        this.creditBaseType = creditBaseType == null ? null : creditBaseType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_APPLY.CREDIT_BASE_AMOUNT
     *
     * @return the value of T_J_CREDIT_APPLY.CREDIT_BASE_AMOUNT
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public BigDecimal getCreditBaseAmount() {
        return creditBaseAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_APPLY.CREDIT_BASE_AMOUNT
     *
     * @param creditBaseAmount the value for T_J_CREDIT_APPLY.CREDIT_BASE_AMOUNT
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public void setCreditBaseAmount(BigDecimal creditBaseAmount) {
        this.creditBaseAmount = creditBaseAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_APPLY.CREDIT_LIMIT
     *
     * @return the value of T_J_CREDIT_APPLY.CREDIT_LIMIT
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_APPLY.CREDIT_LIMIT
     *
     * @param creditLimit the value for T_J_CREDIT_APPLY.CREDIT_LIMIT
     *
     * @mbggenerated Mon Feb 05 15:47:41 CST 2018
     */
    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }
}