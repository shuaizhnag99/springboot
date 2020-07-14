package com.ule.uhj.init.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import  org.apache.commons.lang3.StringUtils;
import com.ule.uhj.init.service.AnalysisService;

@Controller
@RequestMapping("/analysisController")
public class AnalysisController {
	protected static Log log = LogFactory.getLog(AnalysisController.class);
	
	@Autowired
	private AnalysisService analysisService;		
	
		/**
		 * http://money.beta.ule.com:8080/lendvps/analysisController/analysisInterface.do?startMonth=2017-11 
		 * @param request
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping("/analysisInterface")
		@ResponseBody
		public void analysisInterface(HttpServletRequest request){
			log.info("--------------analysisController/analysisInterface.do 开始-----------------------------------");
						
			String startMonth = request.getParameter("startMonth");// yyyy-mm-dd
			Map<String,String> param=null;
			try {
				param =changeoverDate(startMonth);
			} catch (ParseException e) {
				log.error("--------------analysisController/analysisInterface.do 解析参数日期错误-----------------------------------");
				return;
			}
			
			analysisService.analysisInterface(param);
			
			
			
			log.info("--------------analysisController/analysisInterface.do 结束-----------------------------------");
		}
		
		/**
		 * 计算解析报文的时间范围
		 * @param startMonth
		 * @return
		 * startMonth=null 				return startDate(当前日期-3天)
		 * startMonth="2017-11-12"			return startDate(2017-11-12) endDate(2017-12-13)
		 * 
		 * 
		 * @throws ParseException
		 */
		private Map<String, String> changeoverDate(String startMonth) throws ParseException {
			Map<String,String> rs =new HashMap<String,String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar now = Calendar.getInstance();
			if(StringUtils.isBlank(startMonth)){
				now.add(Calendar.DATE, -3);//周
				rs.put("startDate", sdf.format(now.getTime()));
			}else{
				Date date = sdf.parse(startMonth);				
				now.setTime(date);
				rs.put("startDate", sdf.format(now.getTime()));
				now.add(Calendar.DATE, 1); 				
				rs.put("endDate", sdf.format(now.getTime()));
			}
			return rs;
		}
	
}
