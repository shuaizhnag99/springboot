package com.ule.uhj.ejb.client.cs;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("CsAuthBean")
public interface CsAuthClient {
	
	public static final String link = "Link";
	public static final String uhjcsUser = "UhjcsUser";
	public static final String operate = "Operate";
	
	/***
	 * 添加cs后台操作记录
	 */
	public void addCsOperateLog(Map<String,String> params);
	/**
	 * 创建link链接
	 * @param params   linkAddr, linkAuth, linkName, parentId, linkLevel, sortNum
	 * @return
	 */
	public String creatLink(Map<String, Object> params);
	
	/**
	 * 修改link链接
	 * @param params   linkId, linkAddr, linkAuth, linkName, parentId, linkLevel, sortNum
	 * @return
	 */
	public String modifyLink(Map<String, Object> params);
	
	/**
	 * 删除link链接
	 * @param params   linkId
	 * @return
	 */
	public String deleteLink(Map<String, Object> params);
	
	/**
	 * 查询link链接
	 * @param params   linkId  linkName  linkAddr  parentLinkName
	 * @return
	 */
	public String findLink(Map<String, Object> params);
	
	/**
	 * 查询link链接列表
	 * @param params   
	 * @return
	 */
	public String queryLinks(Map<String, Object> params);
	
	/**
	 * 创建csUser
	 * @param params  userId, userName, passWord, roles, admin, status, mobile
	 * @return
	 */
	public String creatCsUser(Map<String, Object> params);
	
	/**
	 * 用户登录 加载用户菜单 与链接ids
	 * @param params  userName, passWord
	 * @return map
	 * code : 0000 成功
	 * msg :
	 * csUserDto :    
	 * userId, userName, passWord, roles, admin, status, mobile, menus
	 */
	public Map<String, Object> csUserLogin(Map<String, Object> params);
	
	/**
	 * 加载数据
	 * @param params
	 * @return
	 */
	public String loadData(Map<String, Object> params); 
	
}

