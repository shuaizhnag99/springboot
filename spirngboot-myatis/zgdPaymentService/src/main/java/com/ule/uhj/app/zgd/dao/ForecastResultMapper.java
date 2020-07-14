package com.ule.uhj.app.zgd.dao;

import com.ule.uhj.app.zgd.model.ForecastResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangbowei
 * @date 2018/6/25
 */
public interface ForecastResultMapper {

    /**
     * 插入
     * @param forecastResult
     * @return
     */
    int insert(ForecastResult forecastResult);

    /**
     * 更新
     * @param forecastResult
     * @return
     */
    int update(ForecastResult forecastResult);

    /**
     * 根据userOnlyId,applyId查询
     * @param userOnlyId
     * @param applyId
     * @return
     */
    ForecastResult selectByUserOnlyIdAndApplyId(@Param("userOnlyId") String userOnlyId, @Param("applyId") String applyId);

    /**
     * 一般查询
     * @param forecastResult
     * @return
     */
    List<ForecastResult> select(ForecastResult forecastResult);
}
