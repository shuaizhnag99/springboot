package com.ule.uhj.init.dao;

import java.util.List;
import java.util.Map;

import com.ule.uhj.init.modle.Constant;

/**
 * 模板
 * Created by wuhaitao on 2017/9/18.
 */
public interface ConstantMapper {
	
	public void update(Map<String, Object> param);
	public Constant query(Map<String, Object> param);
	public List<Map<String, Object>> queryFace(Map<String, Object> param);	
	public int insertSql(String sqlstr);
	public int updateSql(String sqlstr);
	public int deleteSql(String sqlstr);
}
