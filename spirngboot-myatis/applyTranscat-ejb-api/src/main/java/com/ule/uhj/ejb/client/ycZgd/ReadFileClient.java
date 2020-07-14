package com.ule.uhj.ejb.client.ycZgd;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("ReadFileBean")
public interface ReadFileClient {
	/**
	 * 电话审核表导入记录
	 * @param map
	 * @return
	 */
	String callAuditRecords(Map<String,Object> map)throws Exception;
	/**
	 * 第二还款来源表导入记录
	 * @param map
	 * @return
	 */
	String secordRepaySource(Map<String,Object> map)throws Exception;
	/**
	 * 负债导入记录
	 * @param map
	 * @return
	 */
	String debt(Map<String,Object> map)throws Exception;

}
