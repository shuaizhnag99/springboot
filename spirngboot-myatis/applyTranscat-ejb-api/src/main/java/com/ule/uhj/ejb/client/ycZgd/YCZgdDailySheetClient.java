package com.ule.uhj.ejb.client.ycZgd;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;

@Remote
@BeanName("YCZgdDailySheetBean")
public interface YCZgdDailySheetClient {
	
	Map<String,Object> getOpcDailySheet(Map<String,Object> paramers);
	
	Map<String,Object> preQuerySheet(Map<String,Object> paMap);
	/**
	 * 保存用户所选的需要显示的信息
	 * @param param
	 */
	String saveUserChoose(Map<String,Object> paMap);
	
	Map<String,Object> queryUserSheet(Map<String,Object> paras);
	
	Map<String,Object> preQueryUserSheet(Map<String,Object> paras);
	/**
	 * 定时任务  批处理当日运营
	 * @param param
	 * @throws Exception 
	 */
	public String   generateDailySheetTimer(String  date) throws Exception;

}
