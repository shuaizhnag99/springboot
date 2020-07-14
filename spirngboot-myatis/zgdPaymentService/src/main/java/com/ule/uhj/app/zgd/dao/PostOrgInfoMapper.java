package com.ule.uhj.app.zgd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ule.uhj.app.zgd.model.PostOrgInfo;
import com.ule.uhj.app.zgd.model.PostOrgInfoExample;

public interface PostOrgInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_POST_ORG
     *
     * @mbggenerated Sun Nov 26 14:50:09 CST 2017
     */
    int countByExample(PostOrgInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_POST_ORG
     *
     * @mbggenerated Sun Nov 26 14:50:09 CST 2017
     */
    int deleteByExample(PostOrgInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_POST_ORG
     *
     * @mbggenerated Sun Nov 26 14:50:09 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_POST_ORG
     *
     * @mbggenerated Sun Nov 26 14:50:09 CST 2017
     */
    int insert(PostOrgInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_POST_ORG
     *
     * @mbggenerated Sun Nov 26 14:50:09 CST 2017
     */
    int insertSelective(PostOrgInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_POST_ORG
     *
     * @mbggenerated Sun Nov 26 14:50:09 CST 2017
     */
    List<PostOrgInfo> selectByExample(PostOrgInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_POST_ORG
     *
     * @mbggenerated Sun Nov 26 14:50:09 CST 2017
     */
    PostOrgInfo selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_POST_ORG
     *
     * @mbggenerated Sun Nov 26 14:50:09 CST 2017
     */
    int updateByExampleSelective(@Param("record") PostOrgInfo record, @Param("example") PostOrgInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_POST_ORG
     *
     * @mbggenerated Sun Nov 26 14:50:09 CST 2017
     */
    int updateByExample(@Param("record") PostOrgInfo record, @Param("example") PostOrgInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_POST_ORG
     *
     * @mbggenerated Sun Nov 26 14:50:09 CST 2017
     */
    int updateByPrimaryKeySelective(PostOrgInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_J_POST_ORG
     *
     * @mbggenerated Sun Nov 26 14:50:09 CST 2017
     */
    int updateByPrimaryKey(PostOrgInfo record);
    
    /**
     * 查询所有数据
     * @return
     */
    List<PostOrgInfo> selectAll();
    
}