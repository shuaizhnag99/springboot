package com.ule.uhj.app.zgd.dao;

import com.ule.uhj.app.zgd.model.ForecastVpsWord;
import com.ule.uhj.app.zgd.model.ForecastVpsWordExample;
import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ForecastVpsWordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS_WORD
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int countByExample(ForecastVpsWordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS_WORD
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int deleteByExample(ForecastVpsWordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS_WORD
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int deleteByPrimaryKey(BigDecimal id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS_WORD
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int insert(ForecastVpsWord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS_WORD
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int insertSelective(ForecastVpsWord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS_WORD
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    List<ForecastVpsWord> selectByExample(ForecastVpsWordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS_WORD
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    ForecastVpsWord selectByPrimaryKey(BigDecimal id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS_WORD
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int updateByExampleSelective(@Param("record") ForecastVpsWord record, @Param("example") ForecastVpsWordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS_WORD
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int updateByExample(@Param("record") ForecastVpsWord record, @Param("example") ForecastVpsWordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS_WORD
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int updateByPrimaryKeySelective(ForecastVpsWord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_FORECAST_VPS_WORD
     *
     * @mbggenerated Thu Jun 21 14:35:47 CST 2018
     */
    int updateByPrimaryKey(ForecastVpsWord record);
    
    List<String> queryListByWord(List<String> list);
    
    List<ForecastVpsWord> queryObjsByWord(List<String> list);
}