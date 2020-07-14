package com.ule.uhj.app.zgd.service;

import com.ule.uhj.app.zgd.bo.ForecastResultSaveBO;

/**
 * @author zhangbowei
 * @date 2018/6/25
 */
public interface ForecastResultService {
    /**
     * 经营范围筛选结果保存
     * @param forecastResultSaveBO 入参
     * @return true：保存成功，false：保存失败
     */
    boolean saveScreeningScope(ForecastResultSaveBO forecastResultSaveBO);

    /**
     * 用户所选行业保存
     * @param userOnlyId 用户唯一标识
     * @param chooseResult 用户选择的行业code值（多选以逗号分隔 10,11,12,...）
     * @return true：保存成功，false：保存失败
     */
    boolean saveChooseResult(String userOnlyId, String chooseResult);
}
