package com.ule.uhj.app.zgd.dao;

import com.ule.uhj.app.zgd.model.SaleTicketHistory;
import com.ule.uhj.app.zgd.model.SaleTicketHistoryExample;
import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SaleTicketHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_SALE_TICKET_HISTORY
     *
     * @mbggenerated Mon May 14 09:47:59 CST 2018
     */
    int countByExample(SaleTicketHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_SALE_TICKET_HISTORY
     *
     * @mbggenerated Mon May 14 09:47:59 CST 2018
     */
    int deleteByExample(SaleTicketHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_SALE_TICKET_HISTORY
     *
     * @mbggenerated Mon May 14 09:47:59 CST 2018
     */
    int deleteByPrimaryKey(BigDecimal id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_SALE_TICKET_HISTORY
     *
     * @mbggenerated Mon May 14 09:47:59 CST 2018
     */
    int insert(SaleTicketHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_SALE_TICKET_HISTORY
     *
     * @mbggenerated Mon May 14 09:47:59 CST 2018
     */
    int insertSelective(SaleTicketHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_SALE_TICKET_HISTORY
     *
     * @mbggenerated Mon May 14 09:47:59 CST 2018
     */
    List<SaleTicketHistory> selectByExample(SaleTicketHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_SALE_TICKET_HISTORY
     *
     * @mbggenerated Mon May 14 09:47:59 CST 2018
     */
    SaleTicketHistory selectByPrimaryKey(BigDecimal id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_SALE_TICKET_HISTORY
     *
     * @mbggenerated Mon May 14 09:47:59 CST 2018
     */
    int updateByExampleSelective(@Param("record") SaleTicketHistory record, @Param("example") SaleTicketHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_SALE_TICKET_HISTORY
     *
     * @mbggenerated Mon May 14 09:47:59 CST 2018
     */
    int updateByExample(@Param("record") SaleTicketHistory record, @Param("example") SaleTicketHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_SALE_TICKET_HISTORY
     *
     * @mbggenerated Mon May 14 09:47:59 CST 2018
     */
    int updateByPrimaryKeySelective(SaleTicketHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_SALE_TICKET_HISTORY
     *
     * @mbggenerated Mon May 14 09:47:59 CST 2018
     */
    int updateByPrimaryKey(SaleTicketHistory record);
}