package com.ule.uhj.app.zgd.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ule.uhj.app.zgd.dao.AccountInfoMapper;
import com.ule.uhj.app.zgd.model.AccountInfo;
import com.ule.uhj.app.zgd.model.AccountInfoExample;
import com.ule.uhj.app.zgd.model.UserControl;
import com.ule.uhj.app.zgd.service.CustomerInfoService;
import com.ule.uhj.app.zgd.service.UserInfoService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.dto.zgd.ProductInfoN;
import com.ule.uhj.dto.zgd.ProductRule;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.app.ZgdAppClient;
import com.ule.uhj.ejb.client.pixiao.PiXiaoBusinessClient;
import com.ule.uhj.ejb.client.ycZgd.SendMessageClient;
import com.ule.uhj.ejb.client.zgd.ZgdQueryClient;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.web.util.Tools;

/**
 * 
 * 手机APP借款页面
 * @author zhaojie
 *
 */
@Controller
@RequestMapping("/loan")
public class ZgdLoanController{
	private static Logger log = LoggerFactory.getLogger(ZgdLoanController.class);
	@Autowired
	private PdfController pdfController;
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private AccountInfoMapper accountInfoMapper;
	
	@Autowired
	private UserInfoService userInfoService;
	/**
	 * 借现金首页
	 * @return none
	 * response json
	 * code 校验码  0000(成功)
	 */
	@RequestMapping("/queryAppAccOverviewtest")
	@ResponseBody
	public String queryAppAccOverviewtest(HttpServletRequest request) {
		String result= "";
		try {
			String userOnlyId="10000024271";
			log.info("queryAppAccOverview userOnlyId:"+userOnlyId);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			result = WildflyBeanFactory.getZgdAppClient().queryAppAccOverview(map);
			log.info("queryAppAccOverview userOnlyId:"+userOnlyId+";result:"+result);
			
			JSONObject resultJs=JSONObject.fromObject(result);
			map.clear();
			map.put("userOnlyId", userOnlyId);
			map.put("certType", UhjConstant.certType.idcard);
			map=customerInfoService.queryCustomerInfo(map);
			log.info("queryAppAccOverview userOnlyId:"+userOnlyId+";map:"+map);
			String channelCode=Convert.toStr(map.get("channelCode"));
			if(Check.isBlank(channelCode)){
				channelCode="C0001";
			}
			String useType ="1";//产品用途是现金

			//读产品表.取还款方式 和期限规则 periodRule
			ProductInfoN productInfoN = getProductInfoN(channelCode,useType);
		    log.info("queryAppAccOverview 查询产品结果:"+JSON.toJSONString(productInfoN));
			resultJs.put("userOnlyId", userOnlyId);
			log.info("queryAppAccOverview userOnlyId:"+userOnlyId+";resultJs.toString():"+resultJs.toString());
			return resultJs.toString();
		} catch (Exception e) {
			log.error("queryAppAccOverview error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return result;
		}
	}
	/**
	 * 借现金首页
	 * @return none
	 * response json
	 * code 校验码  0000(成功)
	 */
	@RequestMapping("/queryAppAccOverview")
	@ResponseBody
	public JSONPObject queryAppAccOverview(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("queryAppAccOverview userOnlyId:"+userOnlyId);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			result = WildflyBeanFactory.getZgdAppClient().queryAppAccOverview(map);
			log.info("queryAppAccOverview userOnlyId:"+userOnlyId+";result:"+result);
			
			JSONObject resultJs=JSONObject.fromObject(result);
			String ruleOutputResult="3:02;";
			try{
				map.clear();
				map.put("userOnlyId", userOnlyId);
				map.put("certType", UhjConstant.certType.idcard);
				map=customerInfoService.queryCustomerInfo(map);
				String channelCode=Convert.toStr(map.get("channelCode"));
				if(Check.isBlank(channelCode)){
					channelCode="C0001";
				}
				String useType ="1";//产品用途是现金

				String ruleSetId = "storezgdSet001";
				//读产品表.取还款方式 和期限规则 periodRule
				ProductInfoN productInfoN = getProductInfoN(channelCode,useType);
			    log.info("queryAppAccOverview 查询产品结果:"+JSON.toJSONString(productInfoN));
			    if(productInfoN!=null && productInfoN.getPeriodRule()!=null){
			    	ProductRule periodRule =productInfoN.getPeriodRule();
			    	ruleSetId = periodRule.getModelCode();
			    }
			    log.info("queryAppAccOverview ruleSetId:"+ruleSetId);
				Integer dueDay=Convert.toInt(resultJs.get("dueDay"),0);
				Integer dueNumber=Convert.toInt(resultJs.get("dueNumber"),0);
				String rule_url = PropertiesHelper.getDfs("RULE_SET_URL");
				JSONObject js =new JSONObject();
				JSONObject setmap = new JSONObject();
				setmap.put("ruleSetId", ruleSetId);
				setmap.put("iFirstLoanBeforeTodayNum", dueDay);
				setmap.put("userOnlyId", userOnlyId);
				setmap.put("channelCode", channelCode);
				setmap.put("useType", useType);
				setmap.put("dueNumber", dueNumber);//支用笔数
				try{
					setmap.put("c1_360", Convert.toDouble(resultJs.get("c1_360"),0.00));
					setmap.put("c2_360", Convert.toDouble(resultJs.get("c2_360"),0.00));
					setmap.put("c3_360", Convert.toDouble(resultJs.get("c3_360"),0.00));
					setmap.put("c1_200", Convert.toDouble(resultJs.get("c1_200"),0.00));
					setmap.put("c2_200", Convert.toDouble(resultJs.get("c2_200"),0.00));
					setmap.put("c3_200", Convert.toDouble(resultJs.get("c3_200"),0.00));
					setmap.put("creditLimit", Convert.toDouble(resultJs.get("creditLimit"),999999.00));
					setmap.put("totalRepayAmount360", Convert.toDouble(resultJs.get("totalRepayAmount360"),0.00));
					setmap.put("totalRepayAmount200", Convert.toDouble(resultJs.get("totalRepayAmount200"),0.00));
				}catch(Exception e){
					log.error(e.getMessage(), e);
				}
				
				js.put("data", setmap.toString());
				log.info("queryAppAccOverview sendPostJson:"+js.toString());
				String res = com.ule.uhj.util.http.HttpClientUtil.sendPostJson(
						rule_url, js.toString(), UhjConstant.time_out);
				log.info("queryAppAccOverview userOnlyId:"+userOnlyId+"; rule res:"+res);
				
				JSONObject resJs=JSONObject.fromObject(res);
				if("000000".equals(resJs.get("code"))){
					JSONObject object=(JSONObject) resJs.get("object");
					JSONArray data=object.getJSONArray("data");
					JSONObject strs=(JSONObject) data.toArray()[0];
					ruleOutputResult=Convert.toStr(strs.get("ruleOutputResult"));
					log.info("queryAppAccOverview userOnlyId:"+userOnlyId+";ruleOutputResult:"+ruleOutputResult);
				}
			}catch (Exception e) {
				log.error("queryAppAccOverview ruleOutputResult error", e);
			}
			resultJs.put("ruleOutputResult", ruleOutputResult);
			String env = PropertiesHelper.getDfs("env");
//			if("beta".equals(env)){
//				resultJs.put("userOnlyId", "1006152109");
//			}else{
//				resultJs.put("userOnlyId", userOnlyId);
//			}
			resultJs.put("userOnlyId", userOnlyId);
			log.info("queryAppAccOverview userOnlyId:"+userOnlyId+";resultJs.toString():"+resultJs.toString());
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(resultJs.toString()));
		} catch (Exception e) {
			log.error("queryAppAccOverview error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	
	/**
	 * 查询还款计划详情(借款页面的还款计划测算)
	 * @return none
	 * response json
	 * code 校验码  0000(成功)
	 */
	@RequestMapping("/queryAppRepayPlan")
	@ResponseBody
	public JSONPObject queryAppRepayPlan(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("queryAppRepayPlan userOnlyId:"+userOnlyId);
			Map<String, Object> map=new HashMap<String, Object>();
			BigDecimal applyAmount=Convert.toBigDecimal(request.getParameter("applyAmount"));
			Integer periods=Convert.toInt(request.getParameter("periods"));
			String fixedRepayDate=Convert.toStr(request.getParameter("fixedRepayDate"));
			String repayType=Convert.toStr(request.getParameter("repayType"));
			map.put("userOnlyId", userOnlyId);
			map.put("applyAmount", applyAmount);
			map.put("periods", periods);
			map.put("fixedRepayDate", fixedRepayDate);
			map.put("repayType", repayType);
			log.info("queryAppRepayPlan userOnlyId:"+userOnlyId+";map:"+map);
			result = WildflyBeanFactory.getZgdAppClient().queryAppRepayPlan(map);
			log.info("queryAppRepayPlan userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryAppRepayPlan error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	/**
	 * 确认借款
	 * @return none
	 * response json
	 * code 校验码  0000(成功)
	 */
	@RequestMapping("/confirmAppLoan")
	@ResponseBody
	public JSONPObject confirmAppLoan(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("confirmAppLoan userOnlyId:"+userOnlyId);
			Map<String, Object> map=new HashMap<String, Object>();
			BigDecimal applyAmount=Convert.toBigDecimal(request.getParameter("applyAmount"));
			Integer periods=Convert.toInt(request.getParameter("periods"));
			String fixedRepayDate=Convert.toStr(request.getParameter("fixedRepayDate"));
			String repayType=Convert.toStr(request.getParameter("repayType"));
			String loanRemark=Convert.toStr(request.getParameter("loanRemark"));
			BigDecimal endorseAmount=Convert.toBigDecimal(request.getParameter("endorseAmount"),BigDecimal.ZERO);
			String ticketno=Convert.toStr(request.getParameter("ticketno"));
			String ticketRate=Convert.toStr(request.getParameter("ticketRate"));
			map.put("userOnlyId", userOnlyId);
			map.put("applyAmount", applyAmount);
			map.put("periods", periods);
			map.put("fixedRepayDate", fixedRepayDate);
			map.put("repayType", repayType);
			map.put("applyIp", Tools.getIpAddr(request));
			map.put("loanRemark", loanRemark);//借款用途
			map.put("endorseAmount", endorseAmount);//转让金额
			map.put("ticketno", ticketno);//券号
			map.put("ticketRate", ticketRate);//折后利率
			
			//部分指定用户不可用支用
			String value=customerInfoService.getIsSuperKeyWord("cannot_loan_user");
			log.info(" confirmAppLoan  userOnlyId:"+userOnlyId+" 支用判断用户的设置  "+value);
			if(!Check.isBlank(value)){
				String values[]=value.split("@");
				for(String key:values){
					//判断是否是配置表省份的用户
					if(userOnlyId!=null && userOnlyId.equals(key)){
						List<AccountInfo> accountInfos = new ArrayList<AccountInfo>();
						AccountInfoExample accountInfoExample = new AccountInfoExample();
						accountInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
						accountInfos = accountInfoMapper.selectByExample(accountInfoExample);
						if(accountInfos!=null && accountInfos.size()>0){
							AccountInfo info=accountInfos.get(0);
							if(info.getBanlance().compareTo(info.getCreditLimit())==0){
								result=JsonResult.getInstance().addError("error：javax.ejb.EJBTransactionRolledbackException: org.hibernate.exception.GenericJDBCException: could not execute query——"+info.getAccName()).toJsonStr();
								log.info("confirmAppLoan 成功命中用户，此用户不可以支用 userOnlyId:"+userOnlyId+";result:"+result);
								return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
							}
						}
						result=JsonResult.getInstance().addError("您有未结清的贷款，请结清后再试！").toJsonStr();
						log.info("confirmAppLoan 成功命中用户，此用户不可以支用 userOnlyId:"+userOnlyId+";result:"+result);
						return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
					}
				}
			}
			
			UserControl userControl=userInfoService.queryUserControl(userOnlyId);
			if(userControl!=null){
				List<AccountInfo> accountInfos = new ArrayList<AccountInfo>();
				AccountInfoExample accountInfoExample = new AccountInfoExample();
				accountInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
				accountInfos = accountInfoMapper.selectByExample(accountInfoExample);
				if(accountInfos!=null && accountInfos.size()>0){
					AccountInfo info=accountInfos.get(0);
					if(info.getBanlance().compareTo(info.getCreditLimit())==0){
						result=JsonResult.getInstance().addError("error：javax.ejb.EJBTransactionRolledbackException: org.hibernate.exception.GenericJDBCException: could not execute query——"+info.getAccName()).toJsonStr();
						log.info("confirmAppLoan 成功命中用户，此用户不可以支用 userOnlyId:"+userOnlyId+";result:"+result);
						return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
					}
				}
				result=JsonResult.getInstance().addError("您有未结清的贷款，请结清后再试！").toJsonStr();
				log.info("confirmAppLoan 成功命中用户，此用户不可以支用 userOnlyId:"+userOnlyId+";result:"+result);
				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
			}
			
			log.info("confirmAppLoan userOnlyId:"+userOnlyId+";map:"+map);
			result = WildflyBeanFactory.getZgdAppClient().confirmAppLoan(map);
			log.info("confirmAppLoan userOnlyId:"+userOnlyId+";result:"+result);
			//上传合同到服务器
//			JSONObject js=JSONObject.fromObject(result);
//			String code=Convert.toStr(js.get("code"));
//			if(code!=null&&"0000".equals(code)){
//				String loanId = Convert.toStr(js.get("loanId"));
//				String orderId = Convert.toStr(js.get("orderId"));
//				String lineId = Convert.toStr(js.get("lineId"));
//				try{
//					String firstLoan=Convert.toStr(request.getParameter("firstLoan"));
//					Map<String,Object> params = new HashMap<String, Object>();
//					params.put("userOnlyId", userOnlyId);
//					if(!Check.isBlank(firstLoan)&&"1".equals(firstLoan)){
//						params.put("businessType", "line");
//						params.put("businessId", lineId);
//						params.put("orderId", orderId);
//						pdfController.saveContact(params);
//					}
//					params.clear();
//					params.put("userOnlyId", userOnlyId);
//					params.put("businessType", "loan");
//					params.put("businessId", loanId);
//					params.put("orderId", orderId);
//					pdfController.saveContact(params);
//				}catch(Exception e){
//					log.error("saveRequestData 333 error!",e);
//				}
//			}
//			result = JsonResult.getInstance().addOk().toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("confirmAppLoan error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	
	/**
	 * 查询应还款的首页计划
	 * @return none
	 * response json
	 * code 校验码  0000(成功)
	 */
	@RequestMapping("/queryAppNeedRepay")
	@ResponseBody
	public JSONPObject queryAppNeedRepay(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("queryAppNeedRepay userOnlyId:"+userOnlyId);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			result = WildflyBeanFactory.getZgdAppClient().queryAppNeedRepay(map);
			log.info("queryAppNeedRepay userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryAppNeedRepay error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 查询现金的还款计划
	 * @return none
	 * response json
	 * code 校验码  0000(成功)
	 */
	@RequestMapping("/queryCashRepayPlan")
	@ResponseBody
	public JSONPObject queryCashRepayPlan(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("queryCashRepayPlan userOnlyId:"+userOnlyId);
			String dueId=Convert.toStr(request.getParameter("dueId"));
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("dueId", dueId);
			log.info("queryCashRepayPlan userOnlyId:"+userOnlyId+";map:"+map);
			result = WildflyBeanFactory.getZgdAppClient().queryCashRepayPlan(map);
			log.info("queryCashRepayPlan userOnlyId:"+userOnlyId+";result:"+result);
			JSONObject resultJs=JSONObject.fromObject(result);
			String env = PropertiesHelper.getDfs("env");
//			if("beta".equals(env)){
//				resultJs.put("userOnlyId", "1006152109");
//			}else{
//				resultJs.put("userOnlyId", userOnlyId);
//			}
			resultJs.put("userOnlyId", userOnlyId);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(resultJs));
		} catch (Exception e) {
			log.error("queryCashRepayPlan error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 查询现金应还款的提前还款计划
	 * @return none
	 * response json
	 * code 校验码  0000(成功)
	 */
	@RequestMapping("/queryCashEarlyPay")
	@ResponseBody
	public JSONPObject queryCashEarlyPay(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("queryCashEarlyPay userOnlyId:"+userOnlyId);
			String dueId=Convert.toStr(request.getParameter("dueId"));
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("dueId", dueId);
			log.info("queryCashEarlyPay userOnlyId:"+userOnlyId+"; map:"+map);
			result = WildflyBeanFactory.getZgdAppClient().queryCashEarlyPay(map);
			log.info("queryCashEarlyPay userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryCashEarlyPay error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 查询现金应还款的提前还款计划
	 * @return none
	 * response json
	 * code 校验码  0000(成功)
	 */
	@RequestMapping("/queryCashEarlyPayManual")
	@ResponseBody
	public JSONPObject queryCashEarlyPayManual(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
		try {
			String userOnlyId=Convert.toStr(request.getParameter("userOnlyId"));
			log.info("queryCashEarlyPayManual userOnlyId:"+userOnlyId);
			String dueId=Convert.toStr(request.getParameter("dueId"));
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("dueId", dueId);
			log.info("queryCashEarlyPayManual userOnlyId:"+userOnlyId+"; map:"+map);
			result = WildflyBeanFactory.getZgdAppClient().queryCashEarlyPay(map);
			log.info("queryCashEarlyPayManual userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryCashEarlyPayManual error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 确认现金的提前还款
	 * @return none
	 * response json
	 * code 校验码  0000(成功)
	 */
	@RequestMapping("/confimCashEarlyPayManual")
	@ResponseBody
	public JSONPObject confimCashEarlyPayManul(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
		try {
			String userOnlyId=Convert.toStr(request.getParameter("userOnlyId"));
			log.info("confimCashEarlyPay userOnlyId:"+userOnlyId);
			String dueId=Convert.toStr(request.getParameter("dueId"));
			String earlyAmt=Convert.toStr(request.getParameter("earlyAmt"));
			String channel=Convert.toStr(request.getParameter("channel"),"");
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("dueId", dueId);
			map.put("earlyAmt", earlyAmt);
			map.put("channel", channel);
			log.info("queryCashEarlyPay userOnlyId:"+userOnlyId+";map:"+map);
			result = WildflyBeanFactory.getZgdAppClient().confimCashEarlyPay(map);
			log.info("confimCashEarlyPay userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("confimCashEarlyPay error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	
	/**
	 * 确认现金的提前还款
	 * @return none
	 * response json
	 * code 校验码  0000(成功)
	 */
	@RequestMapping("/confimCashEarlyPay")
	@ResponseBody
	public JSONPObject confimCashEarlyPay(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("confimCashEarlyPay userOnlyId:"+userOnlyId);
			String dueId=Convert.toStr(request.getParameter("dueId"));
			String earlyAmt=Convert.toStr(request.getParameter("earlyAmt"));
			String channel=Convert.toStr(request.getParameter("channel"),"");
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("dueId", dueId);
			map.put("earlyAmt", earlyAmt);
			map.put("channel", channel);
			log.info("queryCashEarlyPay userOnlyId:"+userOnlyId+";map:"+map);
			result = WildflyBeanFactory.getZgdAppClient().confimCashEarlyPay(map);
			log.info("confimCashEarlyPay userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("confimCashEarlyPay error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	/**
	 * 查询批销的还款计划
	 * @return none
	 * response json
	 * code 校验码  0000(成功)
	 */
	@RequestMapping("/queryPXRepayPlan")
	@ResponseBody
	public JSONPObject queryPXRepayPlan(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("queryPXRepayPlan userOnlyId:"+userOnlyId);
			String dueIds=Convert.toStr(request.getParameter("dueIds"));
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("dueIds", dueIds);
			log.info("queryPXRepayPlan userOnlyId:"+userOnlyId+";map:"+map);
			result = WildflyBeanFactory.getZgdAppClient().queryPXRepayPlan(map);
			log.info("queryPXRepayPlan userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryPXRepayPlan error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 查询批销应还款的提前还款计划
	 * @return none
	 * response json
	 * code 校验码  0000(成功)
	 */
	@RequestMapping("/queryPXEarlyPayPlan")
	@ResponseBody
	public JSONPObject queryPXEarlyPayPlan(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("queryPXEarlyPayPlan userOnlyId:"+userOnlyId);
			String dueIds=Convert.toStr(request.getParameter("dueIds"));
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("dueIds", dueIds);
			log.info("queryPXEarlyPayPlan userOnlyId:"+userOnlyId+";map:"+map);
//			result = WildflyBeanFactory.getZgdAppClient().queryPXEarlyPayPlan(map);
			PiXiaoBusinessClient client = WildflyBeanFactory.getPiXiaoBusinessClient();
			result=client.querySignEarlyRepay(map);
			log.info("queryPXEarlyPayPlan userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryPXEarlyPayPlan error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	
	/**
	 * 批销的提前还款
	 * @return none
	 * response json
	 * code 校验码  0000(成功)
	 */
	@RequestMapping("/confimPXEarlyPay")
	@ResponseBody
	public JSONPObject confimPXEarlyPay(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("confimPXEarlyPay userOnlyId:"+userOnlyId);
			String dueIds=Convert.toStr(request.getParameter("dueIds"));
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("dueIds", dueIds);
			log.info("confimPXEarlyPay userOnlyId:"+userOnlyId+";map:"+map);
			PiXiaoBusinessClient client = WildflyBeanFactory.getPiXiaoBusinessClient();
			result=client.signEarlyRepay(map);
			log.info("confimPXEarlyPay userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("confimPXEarlyPay error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	/**
	 * 批销进货的订单商品明细
	 * @return none
	 * response json
	 * code 校验码  0000(成功)
	 */
	@RequestMapping("/queryPXPayOrder")
	@ResponseBody
	public JSONPObject queryPXPayOrder(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("queryPXPayOrder userOnlyId:"+userOnlyId);
			String dueId=Convert.toStr(request.getParameter("dueId"));
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("dueId", dueId);
			log.info("queryPXPayOrder userOnlyId:"+userOnlyId+";map:"+map);
			result = WildflyBeanFactory.getZgdAppClient().queryPXPayOrder(map);
			log.info("queryPXPayOrder userOnlyId:"+userOnlyId+";result:"+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryPXPayOrder error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	/**
	 * 向掌柜手机发送短信
	 * @param phone
	 * @return none
	 * response json
	 * returnCode 0000(成功)
	 * randomCode 校验码
	 */
	@RequestMapping("/smsSendRandomCode")
	@ResponseBody
	public JSONPObject smsSendRandomCode(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
		try {
			String phone = request.getParameter("phone");
			log.info("smsSendRandomCode -->" + phone);
			String ret = WildflyBeanFactory.getSendMessageClient().smsSendRandomCode(phone);
			log.info("smsSendRandomCode ret:"+ret);
			JSONObject js=JSONObject.fromObject(ret);
			result=JsonResult.getInstance().add("code", js.get("returnCode")).add("msg", js.get("returnMessage")).toJsonStr();
			log.info("smsSendRandomCode --> result" + result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("smsSendRandomCode error", e);
			result = JsonResult.getInstance().addError(e.getMessage()).toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 验证手机校验码是否正确
	 * @param phone   手机号
	 * @param validCode 验证码
	 * @return none
	 * response json
	 * returnCode  0000 成功
	 * returnMessage 
	 * verifyResult  true or false
	 *  {"returnCode":"0000","returnMessage":"操作成功","verifyResult":"true"}
	 */
	@RequestMapping("/sendmsg_verifyRandomCode")
	@ResponseBody
	public JSONPObject verifyRandomCode(HttpServletRequest request,@RequestParam String jsoncallback) {
		try {
			String phone = request.getParameter("phone");
			String validCode = request.getParameter("validatecode");
			log.info("verifyRandomCode -->" + phone + ":" + validCode);
			String result = WildflyBeanFactory.getSendMessageClient().verifyRandomCode(phone, validCode);
			log.info("verifyRandomCode -->" + result);
			JSONObject js=JSONObject.fromObject(result);
			result=JsonResult.getInstance().add("code", js.get("returnCode")).add("msg", js.get("returnMessage")).toJsonStr();
			log.info("verifyRandomCode --> result" + result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("verifyRandomCode error", e);
			String result = JsonResult.getInstance().addError(e.getMessage()).toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	private String getUserOnlyId(HttpServletRequest request) throws Exception {
		String usronlyId =CommonHelper.getUserOnlyId(request);
		return usronlyId;
	}
	
	public ProductInfoN getProductInfoN(String channelCode,String useType){
		ProductInfoN rs =null;
		
		ProductInfoN info = new ProductInfoN();
		info.setChannelCode(channelCode);
		info.setUseType(useType);
		
		ZgdQueryClient zgdQueryClient;
		try {
			zgdQueryClient = WildflyBeanFactory.getZgdQueryClient();
			List<ProductInfoN> list = zgdQueryClient.queryProductInfo(info);
			if(!CollectionUtils.isEmpty(list)){
				rs=list.get(0);
			}
		} catch (Exception e) {
			log.error("getProductInfoN error:", e);
		}
		return rs;
	}
}
