package com.ule.uhj.app.zgd.model;

import java.math.BigDecimal;

public class CreditAudit {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_AUDIT.ID
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    private BigDecimal id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_AUDIT.APP_ID
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    private String appId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_AUDIT.USER_ONLY_ID
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    private String userOnlyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_AUDIT.RULE_REF_ID
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    private String ruleRefId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_AUDIT.MANUAL_RESULT
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    private String manualResult;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_AUDIT.OPINION
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    private String opinion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_AUDIT.ROLE
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    private String role;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_AUDIT.OPERATOR
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    private String operator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_AUDIT.UPDATE_USER
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    private String updateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_AUDIT.CREATE_USER
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    private String createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_AUDIT.CREATE_TIME
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    private String createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_CREDIT_AUDIT.UPDATE_TIME
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    private String updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_AUDIT.ID
     *
     * @return the value of T_J_CREDIT_AUDIT.ID
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_AUDIT.ID
     *
     * @param id the value for T_J_CREDIT_AUDIT.ID
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_AUDIT.APP_ID
     *
     * @return the value of T_J_CREDIT_AUDIT.APP_ID
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public String getAppId() {
        return appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_AUDIT.APP_ID
     *
     * @param appId the value for T_J_CREDIT_AUDIT.APP_ID
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_AUDIT.USER_ONLY_ID
     *
     * @return the value of T_J_CREDIT_AUDIT.USER_ONLY_ID
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public String getUserOnlyId() {
        return userOnlyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_AUDIT.USER_ONLY_ID
     *
     * @param userOnlyId the value for T_J_CREDIT_AUDIT.USER_ONLY_ID
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public void setUserOnlyId(String userOnlyId) {
        this.userOnlyId = userOnlyId == null ? null : userOnlyId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_AUDIT.RULE_REF_ID
     *
     * @return the value of T_J_CREDIT_AUDIT.RULE_REF_ID
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public String getRuleRefId() {
        return ruleRefId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_AUDIT.RULE_REF_ID
     *
     * @param ruleRefId the value for T_J_CREDIT_AUDIT.RULE_REF_ID
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public void setRuleRefId(String ruleRefId) {
        this.ruleRefId = ruleRefId == null ? null : ruleRefId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_AUDIT.MANUAL_RESULT
     *
     * @return the value of T_J_CREDIT_AUDIT.MANUAL_RESULT
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public String getManualResult() {
        return manualResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_AUDIT.MANUAL_RESULT
     *
     * @param manualResult the value for T_J_CREDIT_AUDIT.MANUAL_RESULT
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public void setManualResult(String manualResult) {
        this.manualResult = manualResult == null ? null : manualResult.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_AUDIT.OPINION
     *
     * @return the value of T_J_CREDIT_AUDIT.OPINION
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public String getOpinion() {
        return opinion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_AUDIT.OPINION
     *
     * @param opinion the value for T_J_CREDIT_AUDIT.OPINION
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public void setOpinion(String opinion) {
        this.opinion = opinion == null ? null : opinion.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_AUDIT.ROLE
     *
     * @return the value of T_J_CREDIT_AUDIT.ROLE
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public String getRole() {
        return role;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_AUDIT.ROLE
     *
     * @param role the value for T_J_CREDIT_AUDIT.ROLE
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_AUDIT.OPERATOR
     *
     * @return the value of T_J_CREDIT_AUDIT.OPERATOR
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public String getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_AUDIT.OPERATOR
     *
     * @param operator the value for T_J_CREDIT_AUDIT.OPERATOR
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_AUDIT.UPDATE_USER
     *
     * @return the value of T_J_CREDIT_AUDIT.UPDATE_USER
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_AUDIT.UPDATE_USER
     *
     * @param updateUser the value for T_J_CREDIT_AUDIT.UPDATE_USER
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_AUDIT.CREATE_USER
     *
     * @return the value of T_J_CREDIT_AUDIT.CREATE_USER
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_AUDIT.CREATE_USER
     *
     * @param createUser the value for T_J_CREDIT_AUDIT.CREATE_USER
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_AUDIT.CREATE_TIME
     *
     * @return the value of T_J_CREDIT_AUDIT.CREATE_TIME
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_AUDIT.CREATE_TIME
     *
     * @param createTime the value for T_J_CREDIT_AUDIT.CREATE_TIME
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_CREDIT_AUDIT.UPDATE_TIME
     *
     * @return the value of T_J_CREDIT_AUDIT.UPDATE_TIME
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_CREDIT_AUDIT.UPDATE_TIME
     *
     * @param updateTime the value for T_J_CREDIT_AUDIT.UPDATE_TIME
     *
     * @mbggenerated Tue Mar 27 10:15:17 CST 2018
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }
}