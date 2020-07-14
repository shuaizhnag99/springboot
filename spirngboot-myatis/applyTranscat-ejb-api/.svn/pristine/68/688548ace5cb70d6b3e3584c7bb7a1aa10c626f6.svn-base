package com.ule.uhj.ejb.client.ycZgd;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.uhj.dto.YC.Request;
import com.ule.uhj.dto.YC.Response;
import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("YCRequestUtil")
public interface YCRequestClient {
	/***
	 * 来自内部
	 */
	public static String IN = "IN";
	
	/***
	 * 来自外部
	 */
	public static String OUT = "OUT";
	
	/***
	 * 获取指定交易号的request对象
	 * @param transCode
	 * @return
	 */
	@Deprecated
	Request getRequest(String transCode);
	
	/***
	 * 获取指定交易号的request对象,并指定发送方向
	 * @param transCode
	 * @param distance
	 * @return
	 */
	Request getRequest(String transCode,String distance);
	
	/***
	 * 发送request
	 * @param request
	 * @return
	 */
	Response sendRequest(Request request);
	
	/***
	 * 接收request
	 * @param request
	 * @return
	 */
	Response revicedRequest(Request request);
	
	/***
	 * 获取全部接口信息
	 * @return
	 */
	List<Map<String, Object>> getAllRequestType();
	
	/***
	 * 获取通信记录
	 * @return
	 */
	Map<String,Object> getRequestLogs(Map<String,Object> paramer);
}
