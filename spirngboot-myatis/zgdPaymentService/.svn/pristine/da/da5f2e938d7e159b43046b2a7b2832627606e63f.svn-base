package com.ule.uhj.zgd.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.ycZgd.YCZgdQueryClient;
import com.ule.uhj.ejb.client.ycZgd.YcLimitRenewClient;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.JsonUtil;

import net.sf.json.JSONObject;
/***
 * 掌柜贷额度续约Web层相关接口集合
 * @author yfzx-sh-zhengxin
 *
 */
@Controller
@RequestMapping("/renew")
public class YcLimitRenewController {
	private static Logger log = LoggerFactory.getLogger(YcLimitRenewController.class);
	//系统异常时返回的代码
	private static String CODE_SYS_ERROR = "999999";
	//用户未登陆时返回的代码
	private static String CODE_USER_NOT_LOGIN = "900001";
	//用户账户状态异常时返回的代码
	private static String CODE_USER_ACCOUNT_STATUS_ERROR = "900002";
	//用户状态为正在重新中时返回的代码
	private static String CODE_USER_REAPPLYING = "000001";
	//需要去手机APP上借款
	private static final String CODE_APP_APPLY = "100008";
	//业务处理成功时返回的代码
	private static String CODE_SUCCESS = "000000";
	
	/***
	 * 用户点击“我要借款”后，应从此接口获悉其状态。
     * 先查询用户账户状态，若不为老用户再次申请中状态，则应进行阶段1的验证。否则应直接给出阶段3的等待响应。
	 * @param request
	 * @param jsoncallback
	 * @return json
	 */
	@RequestMapping("/qualificationsCheck")
	@ResponseBody
	public JSONPObject qualificationsCheck(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("掌柜贷额度续约Web接口集-我要借款被单击：开始.");
		//响应结果
		Map<String,Object> responseMap = new HashMap<String,Object>();
		//用户唯一标识
		String userOnlyId = null;
		//用户账户状态
		String YCUserStatus = null;
		//掌柜贷额度续约EJB接口集
		YcLimitRenewClient ycLimitRenewClient = null;
		try {
			ycLimitRenewClient = WildflyBeanFactory.getYcLimitRenewClient();
		} catch (Exception e) {
			log.error("掌柜贷额度续约Web接口集-我要借款被单击:获取EJB服务接口失败!",e);
			responseMap.put("code", CODE_SYS_ERROR);
			responseMap.put("message","网络连接异常。");
			return new JSONPObject(jsoncallback, JsonUtil.getJsonStringFromMap(responseMap));
		}
		
		try {
			userOnlyId = CommonHelper.getUserOnlyId(request);
			if(StringUtils.isBlank(userOnlyId)){
				throw new Exception("用户唯一标识为空!");
			}
		} catch (Exception e) {
			log.error("掌柜贷额度续约Web接口集-我要借款被单击：获取用户useronlyid异常，检查该用户是否登陆。",e);
			responseMap.put("code", CODE_USER_NOT_LOGIN);
			responseMap.put("message","用户未登陆。");
			return new JSONPObject(jsoncallback, JsonUtil.getJsonStringFromMap(responseMap));
		}
		
		try{
			YCUserStatus = getYCUserStatus(userOnlyId);
		}catch(Exception e){
			log.error("掌柜贷额度续约Web接口集-我要借款被单击：获取用户账户状态异常。",e);
			responseMap.put("code", CODE_USER_ACCOUNT_STATUS_ERROR);
			responseMap.put("message","用户账户状态异常。");
			return new JSONPObject(jsoncallback, JsonUtil.getJsonStringFromMap(responseMap));
		}
		
		//该用户是否是老用户正在申请中--改为提示用户去手机上申请--2017-10-24
		if("101".equals(YCUserStatus)){
			responseMap.put("code", CODE_APP_APPLY);
			responseMap.put("message", "该用户正在重新申请资格中，不提供借款。");
			log.info("掌柜贷额度续约Web接口集-我要借款被单击：useronlyid="+userOnlyId+"的用户当前正在重新申请资格中，不提供借款。");
			return new JSONPObject(jsoncallback, JsonUtil.getJsonStringFromMap(responseMap));
		}
		//开始阶段一
		log.info("掌柜贷额度续约Web接口集-我要借款被单击：评测阶段开始");
		responseMap = ycLimitRenewClient.evaluating(userOnlyId);
		log.info("掌柜贷额度续约Web接口集-我要借款被单击：评测阶段结束，处理结果:"+JsonUtil.getJsonStringFromMap(responseMap));
		return new JSONPObject(jsoncallback, JsonUtil.getJsonStringFromMap(responseMap));
	}
	
	/***
	 * 用户点击“立即申请”后，应提交至此接口。
	 * 连续的进行掌柜贷额度续约的阶段2、阶段3，此时对用户不可见(?待定)。
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/immediateApply")
	@ResponseBody
	public JSONPObject immediateApply(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("掌柜贷额度续约Web接口集-立即申请被单击：开始.");
		//响应结果
		Map<String,Object> responseMap = new HashMap<String,Object>();
		//用户唯一标识
		String userOnlyId = null;
		//掌柜贷额度续约EJB接口集
		YcLimitRenewClient ycLimitRenewClient = null;
		try {
			ycLimitRenewClient = WildflyBeanFactory.getYcLimitRenewClient();
		} catch (Exception e) {
			log.error("掌柜贷额度续约Web接口集-立即申请被单击:获取EJB服务接口失败!",e);
			responseMap.put("code", CODE_SYS_ERROR);
			responseMap.put("message","网络连接异常。");
			return new JSONPObject(jsoncallback, JsonUtil.getJsonStringFromMap(responseMap));
		}
		try {
			userOnlyId = CommonHelper.getUserOnlyId(request);
			if(StringUtils.isBlank(userOnlyId)){
				throw new Exception("用户唯一标识为空!");
			}
		}catch(Exception e) {
			log.error("掌柜贷额度续约Web接口集-立即申请被单击：获取用户useronlyid异常，检查该用户是否登陆。",e);
			responseMap.put("code", CODE_USER_NOT_LOGIN);
			responseMap.put("message","用户未登陆。");
			return new JSONPObject(jsoncallback, JsonUtil.getJsonStringFromMap(responseMap));
		}
		//开始阶段二
		log.info("掌柜贷额度续约Web接口集-立即申请被单击：数据采集阶段开始");
		responseMap = ycLimitRenewClient.dataCollection(userOnlyId);
		log.info("掌柜贷额度续约Web接口集-立即申请被单击：数据采集阶段结束，处理结果："+JsonUtil.getJsonStringFromMap(responseMap));
		//验证阶段二处理结果
		if(responseMap.get("code").toString().equals(CODE_SUCCESS)){
			//阶段二评测无误，转入阶段三处理
			log.info("掌柜贷额度续约Web接口集-立即申请被单击：第三方认证阶段开始");
			responseMap = ycLimitRenewClient.thirdPartyVerification(userOnlyId);
			log.info("掌柜贷额度续约Web接口集-立即申请被单击：第三方认证阶段结束，处理结果："+JsonUtil.getJsonStringFromMap(responseMap));
			return new JSONPObject(jsoncallback, JsonUtil.getJsonStringFromMap(responseMap));
		}else{
			//阶段二发现风险，停止阶段推进
			log.info("掌柜贷额度续约Web接口集-立即申请被单击：阶段二发现风险，停止阶段推进。");
			return new JSONPObject(jsoncallback, JsonUtil.getJsonStringFromMap(responseMap));
		}
	}
	
	/**
	 * 查询用户状态-copy自赵杰YcZgdController
	 * @return
	 * @throws Exception
	 */
	private String getYCUserStatus(String userOnlyId) throws Exception{
		String flag = WildflyBeanFactory.getYCZgdQueryClient().queryZgdUserUseStatus(userOnlyId);
		JSONObject js = JSONObject.fromObject(flag);
		if ("0000".equals(js.get("code").toString())){
			log.info("user " + userOnlyId + ", queryStatus " + flag);
			return js.get("status").toString();
		}else{
			throw new Exception("query user status error!" + flag);
		}
	}
}
