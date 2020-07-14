package com.ule.uhj.app.zgd.bo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 经营范围筛选结果保存
 * @author zhangbowei
 * @date 2018/6/25
 */
public class ForecastResultSaveBO {

    /** 用户唯一标识 */
    private String userOnlyId;

    /** 申请订单id */
    private String applyId;

    /** 经营范围中筛选出的行业code值（多个结果以逗号分隔 10,11,12,...）*/
    private String forecastResult;

    /** 需向页面展示的行业code值（多个结果以逗号分隔 10,11,12,...）*/
    private String forecastResultShow;

    /** 经营范围 */
    private String businessScope;

    public String getUserOnlyId() {
        return userOnlyId;
    }

    public void setUserOnlyId(String userOnlyId) {
        this.userOnlyId = userOnlyId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getForecastResult() {
        return forecastResult;
    }

    public void setForecastResult(String forecastResult) {
        this.forecastResult = forecastResult;
    }

    public String getForecastResultShow() {
        return forecastResultShow;
    }

    public void setForecastResultShow(String forecastResultShow) {
        this.forecastResultShow = forecastResultShow;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
