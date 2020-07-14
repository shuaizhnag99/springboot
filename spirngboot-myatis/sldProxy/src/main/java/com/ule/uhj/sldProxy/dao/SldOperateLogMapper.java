package com.ule.uhj.sldProxy.dao;

import java.util.List;

import com.ule.uhj.sld.model.SldOperateLog;
import com.ule.uhj.sld.model.SldOperateLogExample;

public interface SldOperateLogMapper {
	
	/**
	 * @param example
	 * @return
	 */
	List<SldOperateLog> selectByExample(SldOperateLogExample example);
	
	/**
	 * @param id
	 * @return
	 */
	SldOperateLog selectByPrimaryKey(Integer id);
	
	/**
	 * @param record
	 * @return
	 */
	int insert(SldOperateLog record);
	
	/**
	 * @param example
	 * @return
	 */
	int countByExample(SldOperateLogExample example);
}