package com.ule.uhj.zgd.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.ycZgd.QianBaoClient;
import com.ule.uhj.util.JsonResult;

@Controller
@RequestMapping("/uhj")
public class QianBaoController {
	private static Logger log = LoggerFactory
			.getLogger(QianBaoController.class);

	/**
	 * 查询掌柜是否开户
	 * 
	 * @return
	 * @author cuitiantian
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/qianBao_queryZgdInfo")
	@ResponseBody
	public String queryZgdInfo(HttpServletRequest request) throws Exception {
		log.info("queryZgdInfo begin*********");
		QianBaoClient client = WildflyBeanFactory.getQianBaoClient();
		Map<String, Object> map = client.queryZgdInfo(request.getParameter("usrOnlyid"));
		
		log.info("queryZgdInfo  userOnlyId"+request.getParameter("usrOnlyid"));
		log.info("queryZgdInfo end*********");
		return JsonResult.getInstance().add("data", map).toJsonStr();// 1004052926

	}
}
