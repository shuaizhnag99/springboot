package com.ule.uhj.app.zgd.model;

public class UserControl {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_USER_CONTROL.ID
     *
     * @mbggenerated Wed May 30 16:41:54 CST 2018
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_USER_CONTROL.USER_ONLY_ID
     *
     * @mbggenerated Wed May 30 16:41:54 CST 2018
     */
    private String userOnlyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_USER_CONTROL.CREATE_TIME
     *
     * @mbggenerated Wed May 30 16:41:54 CST 2018
     */
    private String createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_USER_CONTROL.UPDATE_TIME
     *
     * @mbggenerated Wed May 30 16:41:54 CST 2018
     */
    private String updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_USER_CONTROL.ID
     *
     * @return the value of T_J_USER_CONTROL.ID
     *
     * @mbggenerated Wed May 30 16:41:54 CST 2018
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_USER_CONTROL.ID
     *
     * @param id the value for T_J_USER_CONTROL.ID
     *
     * @mbggenerated Wed May 30 16:41:54 CST 2018
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_USER_CONTROL.USER_ONLY_ID
     *
     * @return the value of T_J_USER_CONTROL.USER_ONLY_ID
     *
     * @mbggenerated Wed May 30 16:41:54 CST 2018
     */
    public String getUserOnlyId() {
        return userOnlyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_USER_CONTROL.USER_ONLY_ID
     *
     * @param userOnlyId the value for T_J_USER_CONTROL.USER_ONLY_ID
     *
     * @mbggenerated Wed May 30 16:41:54 CST 2018
     */
    public void setUserOnlyId(String userOnlyId) {
        this.userOnlyId = userOnlyId == null ? null : userOnlyId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_USER_CONTROL.CREATE_TIME
     *
     * @return the value of T_J_USER_CONTROL.CREATE_TIME
     *
     * @mbggenerated Wed May 30 16:41:54 CST 2018
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_USER_CONTROL.CREATE_TIME
     *
     * @param createTime the value for T_J_USER_CONTROL.CREATE_TIME
     *
     * @mbggenerated Wed May 30 16:41:54 CST 2018
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_USER_CONTROL.UPDATE_TIME
     *
     * @return the value of T_J_USER_CONTROL.UPDATE_TIME
     *
     * @mbggenerated Wed May 30 16:41:54 CST 2018
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_USER_CONTROL.UPDATE_TIME
     *
     * @param updateTime the value for T_J_USER_CONTROL.UPDATE_TIME
     *
     * @mbggenerated Wed May 30 16:41:54 CST 2018
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }
}