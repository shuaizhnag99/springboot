package com.ule.uhj.app.act.service;

import java.util.List;
import java.util.Map;

import com.ule.uhj.app.zgd.model.SaleTicketInfo;
import com.ule.uhj.app.zgd.model.SaleTicketType;


public interface SaleActTicketService {
	
	public String saveSaleTicket(Map<String, Object> map)throws Exception;
	
	public Integer queryRedSpotsFlag(String userOnlyId)throws Exception;
	
	public SaleTicketInfo queryAvailableTransferTicket(Map<String, Object> map)throws Exception;
	
	public SaleTicketType querySaleTicketType(String ticketno)throws Exception;
	
	/**
	 * 查询我的邮利券
	 * @param userOnlyId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryUsefulTickets4Mine(String userOnlyId,String flag) throws Exception;
	
	/**
	 * 从支用页面跳入我的邮利券接口
	 * @param userOnlyId
	 * @param param 规则引擎执行参数
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryUsefulTickets4ZY(String userOnlyId, Map<String, Object> param) throws Exception;
	
	/**
	 * 查询转让中的邮利券。
	 * @param userOnlyId
	 * @return
	 * @throws Exception
	 */
	public List<SaleTicketInfo> queryTransferingTickets(String userOnlyId) throws Exception;
	
	/**
	 * 查询已转让的邮利券
	 * @param userOnlyId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> queryTransferedTickets(String userOnlyId) throws Exception;
	
	/**
	 * 查询已购买的邮利券
	 * @param userOnlyId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> queryBuyedTickets(String userOnlyId) throws Exception;
	
	/**
	 * 转让邮利券
	 * @param ticketNo
	 * @param userOnlyId
	 * @throws Exception
	 */
	public void transferTicket(String ticketNo, String userOnlyId) throws Exception;
	
	/**
	 * 取消转让
	 * @param ticketNo
	 * @param userOnlyId
	 * @throws Exception
	 */
	public void cancelTransTicket(String ticketNo, String userOnlyId) throws Exception;
	
	/**
	 * 用户开户
	 * @param userOnlyId
	 * @throws Exception
	 */
	public void openAccount(String userOnlyId) throws Exception;
	
	/**
	 * 查询该用户现金支用规则信息【eg:3:01,02;6:02;】
	 * @return [{periods:3,repayType:01,02},{periods:6,repayType:02}]
	 * @throws Exception
	 */
	public Map<String, String> findAccRuleExeInfo(String userOnlyId) throws Exception;
}
