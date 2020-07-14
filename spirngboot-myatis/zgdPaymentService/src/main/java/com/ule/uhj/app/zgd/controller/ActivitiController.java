package com.ule.uhj.app.zgd.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.http.HttpClientUtil;

@Controller
@RequestMapping("/activitiController")
public class ActivitiController {
	protected static Log log = LogFactory.getLog(ActivitiController.class);
	
	private static String startflow_url = PropertiesHelper.getDfs("STARTFLOW_URL");
	
	
	/**
	 * 清理尹刚测试数据
	 * http://money.beta.ule.com:8080/lendvps/activitiController/starflow.do
	 * processKey,businessKey,orgCode,appId,userOnlyId,customerName 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/starflow")
	@ResponseBody
	public String starflow(HttpServletRequest request){
		String result="启用工作流成功";
		try{
			log.info("------------------------------activitiController/starflow.do 开始-----------------------------------");
			
			
			String  processKey= request.getParameter("processKey");
			String  businessKey= request.getParameter("businessKey");
			String  orgCode= request.getParameter("orgCode");
			String  appId= request.getParameter("appId");
			String  userOnlyId= request.getParameter("userOnlyId");
			String  customerName= request.getParameter("customerName");
			
			
			
			Map<String, String> param =new HashMap<String, String>();
			
			
			param.put("processKey", processKey);
			param.put("businessKey", businessKey);
			param.put("orgCode", orgCode);
			param.put("appId", appId);
			param.put("userOnlyId", userOnlyId);
			param.put("customerName", customerName);
			param.put("status", "3");
			
			log.info("starflow param:"+param.toString());
			result = HttpClientUtil.sendPost(startflow_url, param);
			log.info("starflow result:"+result);
			

		}catch(Exception e){
			log.error("starflow error!",e);
			result="启用工作流失败，原因："+e.toString();
		}
		log.info("starflow result:"+result.toString());
		log.info("------------------------------activitiController/starflow.do 结束-----------------------------------");
		return result;
	}
	
	
	
}
