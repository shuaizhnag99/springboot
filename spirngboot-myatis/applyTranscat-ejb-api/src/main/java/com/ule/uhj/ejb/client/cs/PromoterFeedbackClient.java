package com.ule.uhj.ejb.client.cs;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.uhj.dto.zgd.PromoterFeedbackDto;
import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("PromoterFeedbackBean")
public interface PromoterFeedbackClient {
	
	public void savePromoterFeedback(PromoterFeedbackDto feedbackDto) throws Exception;
	
	/**
	 * 反馈查询[分页]
	 * @param map
	 * @return 
	 * @throws Exception
	 */
	public Map<String ,Object> queryFeedbacks(Map<String,Object> paramsMap)throws Exception;
	
	
	/**
	 * 查询反馈数据总条数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Long  queryFeedbackCount(Map<String,Object> paramsMap)throws Exception;
	
	
	/**
	 * 反馈查询
	 * @param map
	 * @return 
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryFeedbackByCondition(Map<String,Object> paramsMap)throws Exception;
	
	
	/**
	 * 根据任务编号判断是否存在反馈信息
	 * @param taskNo
	 * @return
	 * @throws Exception
	 */
	public boolean feedbackIsExists(String taskNo)throws Exception;
}
