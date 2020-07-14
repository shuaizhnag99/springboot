package com.ule.uhj.dto.zgd;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductRule implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1606942220826889847L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_RULE.id
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    private BigDecimal id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_RULE.prd_code
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    private String prdCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_RULE.model_code
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    private String modelCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_RULE.type
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_RULE.create_user
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    private String createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_RULE.create_time
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    private String createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_RULE.update_user
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    private String updateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_PRODUCT_RULE.update_time
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    private String updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_RULE.id
     *
     * @return the value of T_J_PRODUCT_RULE.id
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_RULE.id
     *
     * @param id the value for T_J_PRODUCT_RULE.id
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_RULE.prd_code
     *
     * @return the value of T_J_PRODUCT_RULE.prd_code
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    public String getPrdCode() {
        return prdCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_RULE.prd_code
     *
     * @param prdCode the value for T_J_PRODUCT_RULE.prd_code
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    public void setPrdCode(String prdCode) {
        this.prdCode = prdCode == null ? null : prdCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_RULE.model_code
     *
     * @return the value of T_J_PRODUCT_RULE.model_code
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    public String getModelCode() {
        return modelCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_RULE.model_code
     *
     * @param modelCode the value for T_J_PRODUCT_RULE.model_code
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    public void setModelCode(String modelCode) {
        this.modelCode = modelCode == null ? null : modelCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_RULE.type
     *
     * @return the value of T_J_PRODUCT_RULE.type
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_RULE.type
     *
     * @param type the value for T_J_PRODUCT_RULE.type
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_RULE.create_user
     *
     * @return the value of T_J_PRODUCT_RULE.create_user
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_RULE.create_user
     *
     * @param createUser the value for T_J_PRODUCT_RULE.create_user
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_RULE.create_time
     *
     * @return the value of T_J_PRODUCT_RULE.create_time
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_RULE.create_time
     *
     * @param createTime the value for T_J_PRODUCT_RULE.create_time
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_RULE.update_user
     *
     * @return the value of T_J_PRODUCT_RULE.update_user
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_RULE.update_user
     *
     * @param updateUser the value for T_J_PRODUCT_RULE.update_user
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_PRODUCT_RULE.update_time
     *
     * @return the value of T_J_PRODUCT_RULE.update_time
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_PRODUCT_RULE.update_time
     *
     * @param updateTime the value for T_J_PRODUCT_RULE.update_time
     *
     * @mbggenerated Thu Jan 11 14:09:22 CST 2018
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }
}