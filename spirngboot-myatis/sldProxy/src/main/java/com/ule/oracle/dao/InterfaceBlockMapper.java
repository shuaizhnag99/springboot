package com.ule.oracle.dao;

import com.ule.oracle.model.InterfaceBlock;
import com.ule.oracle.model.InterfaceBlockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InterfaceBlockMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    int countByExample(InterfaceBlockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    int deleteByExample(InterfaceBlockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    int insert(InterfaceBlock record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    int insertSelective(InterfaceBlock record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    List<InterfaceBlock> selectByExampleWithBLOBs(InterfaceBlockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    List<InterfaceBlock> selectByExample(InterfaceBlockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    InterfaceBlock selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    int updateByExampleSelective(@Param("record") InterfaceBlock record, @Param("example") InterfaceBlockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    int updateByExampleWithBLOBs(@Param("record") InterfaceBlock record, @Param("example") InterfaceBlockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    int updateByExample(@Param("record") InterfaceBlock record, @Param("example") InterfaceBlockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    int updateByPrimaryKeySelective(InterfaceBlock record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    int updateByPrimaryKeyWithBLOBs(InterfaceBlock record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_INTERFACE_BLOCK
     *
     * @mbggenerated Mon Jun 29 17:19:17 CST 2020
     */
    int updateByPrimaryKey(InterfaceBlock record);
}