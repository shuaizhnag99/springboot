package com.ule.uhj.app.zgd.model;

public class SaleActProductKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_SALE_ACT_PRODUCT.CODE
     *
     * @mbggenerated Thu May 10 15:20:08 CST 2018
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_SALE_ACT_PRODUCT.PRD_CODE
     *
     * @mbggenerated Thu May 10 15:20:08 CST 2018
     */
    private String prdCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_SALE_ACT_PRODUCT.CHANNEL_CODE
     *
     * @mbggenerated Thu May 10 15:20:08 CST 2018
     */
    private String channelCode;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_SALE_ACT_PRODUCT.CODE
     *
     * @return the value of T_J_SALE_ACT_PRODUCT.CODE
     *
     * @mbggenerated Thu May 10 15:20:08 CST 2018
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_SALE_ACT_PRODUCT.CODE
     *
     * @param code the value for T_J_SALE_ACT_PRODUCT.CODE
     *
     * @mbggenerated Thu May 10 15:20:08 CST 2018
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_SALE_ACT_PRODUCT.PRD_CODE
     *
     * @return the value of T_J_SALE_ACT_PRODUCT.PRD_CODE
     *
     * @mbggenerated Thu May 10 15:20:08 CST 2018
     */
    public String getPrdCode() {
        return prdCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_SALE_ACT_PRODUCT.PRD_CODE
     *
     * @param prdCode the value for T_J_SALE_ACT_PRODUCT.PRD_CODE
     *
     * @mbggenerated Thu May 10 15:20:08 CST 2018
     */
    public void setPrdCode(String prdCode) {
        this.prdCode = prdCode == null ? null : prdCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_SALE_ACT_PRODUCT.CHANNEL_CODE
     *
     * @return the value of T_J_SALE_ACT_PRODUCT.CHANNEL_CODE
     *
     * @mbggenerated Thu May 10 15:20:08 CST 2018
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_SALE_ACT_PRODUCT.CHANNEL_CODE
     *
     * @param channelCode the value for T_J_SALE_ACT_PRODUCT.CHANNEL_CODE
     *
     * @mbggenerated Thu May 10 15:20:08 CST 2018
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }
}