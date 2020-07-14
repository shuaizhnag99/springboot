package com.ule.uhj.app.zgd.dao;

import com.ule.uhj.app.zgd.model.ForecastVps;
import com.ule.uhj.app.zgd.model.ForecastVpsExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ForecastVpsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int countByExample(ForecastVpsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int deleteByExample(ForecastVpsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int deleteByPrimaryKey(String code);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int insert(ForecastVps record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int insertSelective(ForecastVps record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    List<ForecastVps> selectByExample(ForecastVpsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    ForecastVps selectByPrimaryKey(String code);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int updateByExampleSelective(@Param("record") ForecastVps record, @Param("example") ForecastVpsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int updateByExample(@Param("record") ForecastVps record, @Param("example") ForecastVpsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int updateByPrimaryKeySelective(ForecastVps record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int updateByPrimaryKey(ForecastVps record);
    
    List<Map<String,String>> queryListBycode(List<String> list);
}