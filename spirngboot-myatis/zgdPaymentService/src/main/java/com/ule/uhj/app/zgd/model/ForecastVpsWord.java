package com.ule.uhj.app.zgd.model;

import java.math.BigDecimal;

public class ForecastVpsWord {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_FORECAST_VPS_WORD.ID
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    private BigDecimal id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_FORECAST_VPS_WORD.FORECAST_CODE
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    private String forecastCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_FORECAST_VPS_WORD.VPS_CODE
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    private String vpsCode;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_FORECAST_VPS_WORD.ID
     *
     * @return the value of T_J_FORECAST_VPS_WORD.ID
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_FORECAST_VPS_WORD.ID
     *
     * @param id the value for T_J_FORECAST_VPS_WORD.ID
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_FORECAST_VPS_WORD.FORECAST_CODE
     *
     * @return the value of T_J_FORECAST_VPS_WORD.FORECAST_CODE
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    public String getForecastCode() {
        return forecastCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_FORECAST_VPS_WORD.FORECAST_CODE
     *
     * @param forecastCode the value for T_J_FORECAST_VPS_WORD.FORECAST_CODE
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    public void setForecastCode(String forecastCode) {
        this.forecastCode = forecastCode == null ? null : forecastCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_FORECAST_VPS_WORD.VPS_CODE
     *
     * @return the value of T_J_FORECAST_VPS_WORD.VPS_CODE
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    public String getVpsCode() {
        return vpsCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_FORECAST_VPS_WORD.VPS_CODE
     *
     * @param vpsCode the value for T_J_FORECAST_VPS_WORD.VPS_CODE
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    public void setVpsCode(String vpsCode) {
        this.vpsCode = vpsCode == null ? null : vpsCode.trim();
    }
}