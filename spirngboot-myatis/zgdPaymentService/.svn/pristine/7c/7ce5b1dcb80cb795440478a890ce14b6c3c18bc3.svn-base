package com.ule.uhj.yzq.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.zgd.dao.AccountInfoMapper;
import com.ule.uhj.app.zgd.model.AccountInfo;
import com.ule.uhj.app.zgd.model.AccountInfoExample;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.yzq.YzqClient;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.JsonResult;

@Controller
@RequestMapping("/yzq")
public class YzqRepayByStagesController {
	private static Logger log = LoggerFactory.getLogger(YzqRepayByStagesController.class);

	@Autowired
	private AccountInfoMapper accountInfoMapper;

	@ResponseBody
	@RequestMapping("checkInstalmentCreditAuth")
	public String checkInstalmentCreditAuth(HttpServletRequest request, @RequestBody Map params) {
		log.info("YzqRepayByStagesController checkInstalmentCreditAuth params=" + params);
		try {
			String userOnlyId = Convert.toStr(params.get("userOnlyId"));
			BigDecimal loanAmount = Convert.toBigDecimal(params.get("loanAmount"));
			if (Check.isBlank(userOnlyId) || Check.isBlank(loanAmount)) {
				log.info("YzqRepayByStagesController checkInstalmentCreditAuth 数据参数不全！");
				return JsonResult.getInstance().addError("数据参数不全").toJsonStr();
			}
			AccountInfoExample accountInfoExample = new AccountInfoExample();
			accountInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andStatusEqualTo(Short.valueOf("1"))
					.andApplyStatusEqualTo(Short.valueOf("1"));
			List<AccountInfo> list = accountInfoMapper.selectByExample(accountInfoExample);
			if (list != null && list.size() > 0) {
				AccountInfo accountInfo = list.get(0);
				if (loanAmount.compareTo(accountInfo.getBanlance()) < 0) {
					return JsonResult.getInstance().addOk().add("isAuth", true).toJsonStr();
				}
			}
		} catch (Exception ex) {
			log.error("YzqRepayByStagesController checkInstalmentCreditAuth error...", ex);
			return JsonResult.getInstance().addError("系统异常！！").toJsonStr();
		}
		return JsonResult.getInstance().addOk().add("isAuth", false).toJsonStr();
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("queryRepayByStagesPlanDetail")
	public String queryRepayByStagesPlanDetail(HttpServletRequest request, @RequestBody Map params) throws Exception {
		log.info("YzqRepayByStagesController queryRepayByStagesPlanDetail params=" + params);
		YzqClient client = WildflyBeanFactory.getYzqClient();
		String result = client.queryYzqRepayPlan(params);
		return result;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("repayByStages")
	public String repayByStages(HttpServletRequest request, @RequestBody Map params) throws Exception {
		log.info("YzqRepayByStagesController repayByStages params=" + params);
		YzqClient client = WildflyBeanFactory.getYzqClient();
		String result = client.confirmYzqLoanApply(params);
		return result;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("repayPageInfo")
	public String repayPageInfo(HttpServletRequest request, @RequestBody Map params) throws Exception {
		log.info("YzqRepayByStagesController repayPageInfo params=" + params);
		YzqClient client = WildflyBeanFactory.getYzqClient();
		String result = client.queryYzqLendInfo(params);
		return result;
	}
}
