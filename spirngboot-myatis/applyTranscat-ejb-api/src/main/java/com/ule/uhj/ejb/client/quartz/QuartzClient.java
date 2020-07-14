package com.ule.uhj.ejb.client.quartz;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.uhj.dto.quartz.QuartzDto;
import com.ule.wildfly.annotation.BeanName;

@Remote
@BeanName("QuartzBean")
public interface QuartzClient {
	/**
	 * 保存新任务
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public void saveTask(QuartzDto record)throws Exception;
	
	
	/**
	 * 修改任务
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public void updateTask(QuartzDto record)throws Exception;
	
	/**
	 * 修改联系地址
	 * @param address
	 * @throws Exception
	 */
	public void updateAddress(String address)throws Exception;
	
	/**
	 * 根据任务主键ID查询单个任务
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public QuartzDto getQuartzById(String id)throws Exception;
	
	/**
	 * 任务查询[分页]
	 * @param map
	 * @return 
	 * @throws Exception
	 */
	public Map<String ,Object> queryQuartzs(Map<String,Object> paramsMap)throws Exception;
	
	/**
	 * 判断当前任务是否可用启动
	 * @param id
	 * @return
	 */
	public boolean isStart(String id) throws Exception;
	
	/**
	 * 当前任务执行前
	 * @param id
	 * @throws Exception
	 */
	public void befor(String id) throws Exception;
	
	
	/**
	 * 当前任务执行完成之后
	 * @param id
	 * @throws Exception
	 */
	public void after(String id) throws Exception;
	
	/**
	 * 任务分发
	 * @param task
	 * @throws Exception
	 */
	public void taskDispatcher(String task) throws Exception;
}
