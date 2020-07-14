package com.ule.uhj.app.zgd.model;

import java.math.BigDecimal;

public class CreditReturn {
    /**
     * ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     * null
    length:22, scale:0
     */
    private BigDecimal id;

    /**
     * ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     * 授信申请ID
    length:35, scale:0
     */
    private String appId;

    /**
     * ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     * 用户ID
    length:30, scale:0
     */
    private String userOnlyId;

    /**
     * ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     * 退回原因
    length:512, scale:0
     */
    private String reason;

    /**
     * ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     * 退回意见
    length:512, scale:0
     */
    private String opinion;

    /**
     * ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     * 创建操作员
    length:30, scale:0
     */
    private String createUser;

    /**
     * ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     * 创建时间
    length:19, scale:0
     */
    private String createTime;

    /**
     * ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     * 最后更新时间
    length:19, scale:0
     */
    private String updateTime;

    /**
     * ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     * 最后更新操作时间
    length:30, scale:0
     */
    private String updateUser;

    /**
     * null
     * @return the value of ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * null
     * @param id the value for ULEAPP_FINANCECR.T_J_CREDIT_RETURN. length:22, scale:0
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * 授信申请ID
     * @return the value of ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 授信申请ID
     * @param appId the value for ULEAPP_FINANCECR.T_J_CREDIT_RETURN. length:35, scale:0
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * 用户ID
     * @return the value of ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     */
    public String getUserOnlyId() {
        return userOnlyId;
    }

    /**
     * 用户ID
     * @param userOnlyId the value for ULEAPP_FINANCECR.T_J_CREDIT_RETURN. length:30, scale:0
     */
    public void setUserOnlyId(String userOnlyId) {
        this.userOnlyId = userOnlyId == null ? null : userOnlyId.trim();
    }

    /**
     * 退回原因
     * @return the value of ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     */
    public String getReason() {
        return reason;
    }

    /**
     * 退回原因
     * @param reason the value for ULEAPP_FINANCECR.T_J_CREDIT_RETURN. length:512, scale:0
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * 退回意见
     * @return the value of ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     */
    public String getOpinion() {
        return opinion;
    }

    /**
     * 退回意见
     * @param opinion the value for ULEAPP_FINANCECR.T_J_CREDIT_RETURN. length:512, scale:0
     */
    public void setOpinion(String opinion) {
        this.opinion = opinion == null ? null : opinion.trim();
    }

    /**
     * 创建操作员
     * @return the value of ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 创建操作员
     * @param createUser the value for ULEAPP_FINANCECR.T_J_CREDIT_RETURN. length:30, scale:0
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 创建时间
     * @return the value of ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime the value for ULEAPP_FINANCECR.T_J_CREDIT_RETURN. length:19, scale:0
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * 最后更新时间
     * @return the value of ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 最后更新时间
     * @param updateTime the value for ULEAPP_FINANCECR.T_J_CREDIT_RETURN. length:19, scale:0
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    /**
     * 最后更新操作时间
     * @return the value of ULEAPP_FINANCECR.T_J_CREDIT_RETURN
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 最后更新操作时间
     * @param updateUser the value for ULEAPP_FINANCECR.T_J_CREDIT_RETURN. length:30, scale:0
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}