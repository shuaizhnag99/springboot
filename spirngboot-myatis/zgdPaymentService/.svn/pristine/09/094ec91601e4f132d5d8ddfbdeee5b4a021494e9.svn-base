package com.ule.uhj.app.yzs.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.yzs.constant.YzsConstants;
import com.ule.uhj.app.yzs.exception.YZSException;
import com.ule.uhj.app.yzs.service.IIdentifyService;
import com.ule.uhj.app.yzs.service.YzsPostmemberService;
import com.ule.uhj.app.yzs.util.ResultUtil;
import com.ule.uhj.app.yzs.util.YZSExceptionUtil;
import com.ule.uhj.app.zgd.model.CreditPostmember;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.UhjWebJsonUtil;

/**
 * 身份验证controller
 * @author weisihua
 * @date 2017年11月1日
 */
@Controller
@RequestMapping("identify")
public class IdentifyCnontroller {

	private static Log log = LogFactory.getLog(IdentifyCnontroller.class);
	
	@Autowired
	private IIdentifyService identifyService;
	@Autowired
	private YzsPostmemberService yzsPostmemberService;
	
	/**
	 * 点击邮助手按钮时，决定跳转到哪个页面
	 * @param bzgId 绑掌柜id
	 * @return 0：身份验证，1：首页列表
	 */
	@ResponseBody
	@RequestMapping("decidePath")
	public JSONPObject decidePath(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String, Object> map = ResultUtil.successMap();
		log.info("进入方法decidePath()");
		try {
			String bzgId = CommonHelper.getBangZGId(request);
			//String bzgId = "222";
			// 查询T_J_CREDIT_POSTMEMBER 中是否有当前用户的数据来确定
			int result = identifyService.checkUserIdentify(bzgId);
			map.put(YzsConstants.DATA, result);
			log.info("退出方法decidePath()且方法执行成功！");
		} catch (Exception e) {
			log.error("方法decidePath() 执行失败！", e);
			map = YZSExceptionUtil.handleException(e);
		}
		
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 查询地推人员信息--身份信息核实页面
	 * @param bzgId 绑掌柜id
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("queryUserInfo")
	public JSONPObject queryUserInfo(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String, Object> map = ResultUtil.successMap();
		log.info("进入方法queryUserInfo()");
		try {
			String bzgId = CommonHelper.getBangZGId(request);
			//String bzgId = "222";
			Map<String, Object> userInfoMap = identifyService.queryUserInfoByBzgId(bzgId);
			map.put(YzsConstants.DATA, userInfoMap);
			log.info("退出方法queryUserInfo()且方法执行成功！");
		} catch (Exception e) {
			log.error("方法queryUserInfo() 执行失败！", e);
			map = YZSExceptionUtil.handleException(e);
		}
		
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 保存地推人员信息--身份信息核实页面
	 * @param 
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("saveUserInfo")
	public JSONPObject saveUserInfo(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String, Object> map = ResultUtil.successMap();
		try {
			String bzgId = CommonHelper.getBangZGId(request);
			//String bzgId = "222";
			String certNo = request.getParameter("certNo");
			String telephone = request.getParameter("telephone");
			String name = request.getParameter("name");
			String orgProvinceName = request.getParameter("orgProvinceName");
			String orgCityName = request.getParameter("orgCityName");
			String orgAreaName = request.getParameter("orgAreaName");
			String orgTownName = request.getParameter("orgTownName");
			
			
			String status =yzsPostmemberService.queryYzsPostmemberStatus(bzgId);
			if("1".equals(status)){
				String result=JsonResult.getInstance().addError("请勿重复操作，请返回首页重新进入").toString();
				log.info("yzs_personIdentify bangZGId "+bzgId+"; result:"+result);
				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result)); 
			}
			
			log.info("进入方法saveUserInfo() bzgId is:"+bzgId+" certNo is: "+certNo);
			if(StringUtils.isEmpty(certNo)) {
				throw new YZSException("身份证号码不能为空！");
			}
			
			CreditPostmember member = new CreditPostmember();
			member.setStaffId(bzgId);
			member.setCertNo(certNo);
			member.setMobile(telephone);
			member.setName(name);
			member.setOrgProvince(orgProvinceName);
			member.setOrgCity(orgCityName);
			member.setOrgArea(orgAreaName);
			member.setOrgTown(orgTownName);
			
			identifyService.saveCreditPostMember(member);
			log.info("退出方法saveUserInfo()且方法执行成功！");
		} catch (Exception e) {
			log.error("方法saveUserInfo() 执行失败！", e);
			map = YZSExceptionUtil.handleException(e);
		}
		
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 更改掌柜人员信息--详细信息页面
	 * @param 
	 * @return 
	 */
	@Deprecated
	@RequestMapping("saveZgInfo")
	public JSONPObject saveZgInfo(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String, Object> map = ResultUtil.successMap();
		try {
			String userOnlyId = request.getParameter("userOnlyId");
			String orgCode = request.getParameter("orgCode");
			String idcard = request.getParameter("idcard");
			String name = request.getParameter("name");
			log.info("进入方法saveZgInfo() userOnlyId is:"+userOnlyId+" orgCode is: "+orgCode+" name is: "+name+" idcard is: "+idcard);
			
			// 组装请求参数，并调用远程接口
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orgCode", orgCode);
			param.put("usrOnlyid", userOnlyId);
			param.put("cartNo", idcard);
			param.put("usrName", name);
			
			identifyService.updateZgInfo(param);
			log.info("退出方法saveZgInfo()且方法执行成功！");
		} catch (Exception e) {
			log.error("方法saveZgInfo() 执行失败！", e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
}
