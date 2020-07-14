package com.ule.uhj.ejb.client.ycZgd;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;


/**
 * @author panxing
 *
 */
@Remote
@BeanName("CuishouBean")
public interface CuishouClient {

	Map<String, Object> queryCuishouInfo(Map<String, Object> map) throws Exception;

	Map<String, Object> queryOverDueInfoByUserOnlyId(String userOnlyId) throws Exception;

	Map<String, Object> saveCuishouRecord(Map<String, Object> request) throws Exception;

	Map<String, Object> queryCuishouRecordInfo(String userOnlyId) throws Exception;

	Map<String, Object> queryCuishouHistory(Map<String, Object> map) throws Exception;

	Map<String, Object> queryCuishouRecordDetail(Map<String, Object> map) throws Exception;

	Map<String, Object> queryCuishouSavePageInitData() throws Exception;
	

}
