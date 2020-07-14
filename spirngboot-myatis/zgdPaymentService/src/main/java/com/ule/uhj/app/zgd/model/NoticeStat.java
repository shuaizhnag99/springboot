package com.ule.uhj.app.zgd.model;

import java.math.BigDecimal;

public class NoticeStat {
    /**
     * ULEAPP_FINANCECR.T_J_NOTICE_STAT
     * null
    length:22, scale:0
     */
    private BigDecimal id;

    /**
     * ULEAPP_FINANCECR.T_J_NOTICE_STAT
     * 公告ID
    length:22, scale:0
     */
    private BigDecimal noticeId;

    /**
     * ULEAPP_FINANCECR.T_J_NOTICE_STAT
     * 用户内部唯一标示
    length:20, scale:0
     */
    private String userOnlyId;

    /**
     * ULEAPP_FINANCECR.T_J_NOTICE_STAT
     * 创建时间
    length:19, scale:0
     */
    private String createTime;

    /**
     * ULEAPP_FINANCECR.T_J_NOTICE_STAT
     * 创建操作员
    length:30, scale:0
     */
    private String createUser;

    /**
     * ULEAPP_FINANCECR.T_J_NOTICE_STAT
     * 最后更新时间
    length:19, scale:0
     */
    private String updateTime;

    /**
     * ULEAPP_FINANCECR.T_J_NOTICE_STAT
     * 最后更新操作员
    length:30, scale:0
     */
    private String updateUser;

    /**
     * null
     * @return the value of ULEAPP_FINANCECR.T_J_NOTICE_STAT
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * null
     * @param id the value for ULEAPP_FINANCECR.T_J_NOTICE_STAT. length:22, scale:0
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * 公告ID
     * @return the value of ULEAPP_FINANCECR.T_J_NOTICE_STAT
     */
    public BigDecimal getNoticeId() {
        return noticeId;
    }

    /**
     * 公告ID
     * @param noticeId the value for ULEAPP_FINANCECR.T_J_NOTICE_STAT. length:22, scale:0
     */
    public void setNoticeId(BigDecimal noticeId) {
        this.noticeId = noticeId;
    }

    /**
     * 用户内部唯一标示
     * @return the value of ULEAPP_FINANCECR.T_J_NOTICE_STAT
     */
    public String getUserOnlyId() {
        return userOnlyId;
    }

    /**
     * 用户内部唯一标示
     * @param userOnlyId the value for ULEAPP_FINANCECR.T_J_NOTICE_STAT. length:20, scale:0
     */
    public void setUserOnlyId(String userOnlyId) {
        this.userOnlyId = userOnlyId == null ? null : userOnlyId.trim();
    }

    /**
     * 创建时间
     * @return the value of ULEAPP_FINANCECR.T_J_NOTICE_STAT
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime the value for ULEAPP_FINANCECR.T_J_NOTICE_STAT. length:18, scale:0
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * 创建操作员
     * @return the value of ULEAPP_FINANCECR.T_J_NOTICE_STAT
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 创建操作员
     * @param createUser the value for ULEAPP_FINANCECR.T_J_NOTICE_STAT. length:30, scale:0
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 最后更新时间
     * @return the value of ULEAPP_FINANCECR.T_J_NOTICE_STAT
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 最后更新时间
     * @param updateTime the value for ULEAPP_FINANCECR.T_J_NOTICE_STAT. length:18, scale:0
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    /**
     * 最后更新操作员
     * @return the value of ULEAPP_FINANCECR.T_J_NOTICE_STAT
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 最后更新操作员
     * @param updateUser the value for ULEAPP_FINANCECR.T_J_NOTICE_STAT. length:30, scale:0
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}