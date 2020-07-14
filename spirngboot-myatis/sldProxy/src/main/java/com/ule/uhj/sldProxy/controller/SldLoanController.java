package com.ule.uhj.sldProxy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/loan")
public class SldLoanController {
	private static Logger log = LoggerFactory.getLogger(SldLoanController.class);
	
	/**
	 * 借款订单查询
	 * @return
	 */
	@RequestMapping("/listLoanOrders")
	@ResponseBody
	public String listLoanOrders() {
		return "{\"hello\":\"word\"}";
	}
	
	@RequestMapping("/listRepayDetails")
	@ResponseBody
	public String listRepayDetails(){
		return "{\"save ok,\":\"" + "\"}";
	}
	
}
