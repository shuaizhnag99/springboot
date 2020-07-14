package com.ule.uhj.ejb.client.zgd;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.uhj.dto.zgd.BlockChainDto;
import com.ule.wildfly.annotation.BeanName;

/**
 * 
 * @author panxing
 *
 */
@Remote
@BeanName("BlockChainBean")
public interface BlockChainClient{
	
	/**
	 * 保存区块链数据
	 * @param userOnlyId
	 * @param chainCode
	 * @return
	 * @throws Exception 
	 */
	public String saveBlockChainData(String userOnlyId,BlockChainDto blockChainDto,Boolean isDirectStorage) throws Exception;	
	
	/**
	 * 查询客户区块链信息DTO
	 * @param userOnlyId
	 * @param txCode
	 * @return
	 */
	public BlockChainDto queryBlockChainDtoByTx(String userOnlyId,String txCode)  throws Exception;
	
	/**
	 * 查询客户区块链信息DTO
	 * @param userOnlyId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<BlockChainDto> queryBlockChainDtoByUserOnlyId(String userOnlyId, long startTime, long endTime)  throws Exception;

	
	/**
	 * 保存区块链数据
	 * @param userOnlyId
	 * @param blockChainInfo
	 */
	String saveBlockChainInfo(String userOnlyId, String blockChainInfo) throws Exception;

	/**
	 * 查询指定交易数据
	 * @param userOnlyId
	 * @param txCode
	 * @return 
	 */
	String queryBlockChainInfoByTx(String userOnlyId, String txCode) throws Exception;

	/**
	 * 链码查询历史记录，全部数据（无法分页，不推荐）
	 * @param userOnlyId
	 * @param startTime
	 * @param endTime
	 */
	String queryBlockChainHistoryByUserOnlyId(String userOnlyId) throws Exception;

	/**
	 * 走Es查询历史记录（可按时间分页）
	 * @param userOnlyId
	 * @return
	 */
	String queryLatestBlockChainInfoByUserOnlyId(String userOnlyId) throws Exception;
	/**
	 * 走Es查询历史记录（可按时间分页）
	 * @param userOnlyId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	String queryESBlockChainHistoryByUserOnlyId(String userOnlyId, long startTime, long endTime) throws Exception;

	/**
	 * 注册区块链普通用户
	 */
	String registerBlockChainUser(Map<String, Object> map) throws Exception;
	
	/**
	 * 区块链用户证书延期
	 */
	String extendBlockChainUserCert(Map<String, Object> map) throws Exception;

}
