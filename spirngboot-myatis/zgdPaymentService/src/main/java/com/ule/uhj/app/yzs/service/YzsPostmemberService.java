package com.ule.uhj.app.yzs.service;

import java.util.Map;


/**
 * 
 * @author zhaojie
 * @date 2017年11月13日
 */
public interface YzsPostmemberService {

	/**
	 * 查询邮助手地推的状态，显示对应的页面
	 * @param bzgId
	 * @return
	 */
	String queryYzsPostmemberStatus(String bzgId) throws Exception;
	
	String queryYzsPostmemberInfo(String bzgId) throws Exception;
	
	Map<String, Object> queryCreditPostMemberByBzgId(String bzgId) throws Exception;
	
}
