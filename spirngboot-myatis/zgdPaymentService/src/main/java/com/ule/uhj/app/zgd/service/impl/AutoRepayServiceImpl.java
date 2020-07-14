package com.ule.uhj.app.zgd.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ule.uhj.app.zgd.constant.AutoSettingConstant;
import com.ule.uhj.app.zgd.controller.AutoRepayController;
import com.ule.uhj.app.zgd.dao.AutoRepayLogInfoMapper;
import com.ule.uhj.app.zgd.dao.AutoRepaySettingInfoMapper;
import com.ule.uhj.app.zgd.dao.OrderInfoMapper;
import com.ule.uhj.app.zgd.model.AutoRepayLogInfo;
import com.ule.uhj.app.zgd.model.AutoRepaySettingInfo;
import com.ule.uhj.app.zgd.model.AutoRepaySettingInfoExample;
import com.ule.uhj.app.zgd.service.IAutoRepayService;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.app.ZgdAppClient;

import net.sf.json.JSONObject;

@Service
public class AutoRepayServiceImpl implements IAutoRepayService {

	@Autowired
	private AutoRepaySettingInfoMapper autoRepaySettingInfoMapper;
	
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	
	@Autowired
	private AutoRepayLogInfoMapper autoRepayLogInfoMapper;
	
	private   final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	protected static Log log = LogFactory.getLog(AutoRepayServiceImpl.class);
	
	@Override
	public AutoRepaySettingInfo queryAutoSettingInfo(String userOnlyId) throws Exception {
		AutoRepaySettingInfo info = null;
		if(StringUtils.isEmpty(userOnlyId)) {
			throw new Exception("用户编号为空！");
		}
		AutoRepaySettingInfoExample example = new AutoRepaySettingInfoExample();
		example.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andStateEqualTo(AutoSettingConstant.STATE.OPEN);
		List<AutoRepaySettingInfo> list = autoRepaySettingInfoMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			info = list.get(0);
		}
		return info;
	}

	@Override
	public void saveAutoSettingInfo(String userOnlyId, int amount) throws Exception {
		if(StringUtils.isEmpty(userOnlyId)) {
			throw new Exception("用户编号为空！");
		}
		//首先关闭自动还款
		closeAutoSetting(userOnlyId);
		//重新插入一条新数据
		AutoRepaySettingInfo info = new AutoRepaySettingInfo();
		info.setAmount(amount);
		info.setUserOnlyId(userOnlyId);
		info.setState(AutoSettingConstant.STATE.OPEN);
		info.setCreateTime(sdf.format(new Date()));
		info.setCreateUser(userOnlyId);
		autoRepaySettingInfoMapper.insertSelective(info);
	}

	@Override
	public void closeAutoSetting(String userOnlyId) throws Exception {
		if(StringUtils.isEmpty(userOnlyId)) {
			throw new Exception("用户编号为空！");
		}
		//将设置信息的状态全部设置为0
		AutoRepaySettingInfoExample example = new AutoRepaySettingInfoExample();
		example.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andStateEqualTo(AutoSettingConstant.STATE.OPEN);
		List<AutoRepaySettingInfo> list = autoRepaySettingInfoMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			for(AutoRepaySettingInfo info : list) {
				info.setState(AutoSettingConstant.STATE.CLOSED);
				info.setUpdateTime(sdf.format(new Date()));
				info.setCreateUser(userOnlyId);
				autoRepaySettingInfoMapper.updateByPrimaryKeySelective(info);
			}
		}
	}

	@Override
	public List<Map<String, Object>> queryAllRepayInfo() throws Exception {
		log.info("进入方法 queryAllRepayInfo()");
		//最终符合自动扣款条件的集合
		List<Map<String, Object>> autoRepayList = new ArrayList<Map<String, Object>>();
		ZgdAppClient client = WildflyBeanFactory.getZgdAppClient();
		//先查询出所有需要自动扣款的用户设置信息
		AutoRepaySettingInfoExample example = new AutoRepaySettingInfoExample();
		example.createCriteria().andStateEqualTo(AutoSettingConstant.STATE.OPEN);
		List<AutoRepaySettingInfo> settingInfoList = autoRepaySettingInfoMapper.selectByExample(example);
		if(null != settingInfoList && settingInfoList.size() > 0) {
			for(AutoRepaySettingInfo setting : settingInfoList) {
				//查询该用户下所有的借据
				String userId = "100" + org.apache.commons.lang.StringUtils.leftPad(setting.getUserOnlyId(), 20, "0");
				log.info("当前用户编号userId是："+userId+"并且 userOnlyId是："+setting.getUserOnlyId());
				//按日期从早到晚并且没有逾期的借据编号
				List<String> dueIdList = orderInfoMapper.selectDueIds(userId);
				log.info("所有需要还的借据号大小是："+dueIdList.size());
				if(null != dueIdList && dueIdList.size() > 0) {
					
					BigDecimal settingAmount = new BigDecimal(setting.getAmount());
					BigDecimal tempAmount = settingAmount;
					for(String dueId : dueIdList) {
						//因为后面对settingAmount做减法。所以先判断
						if(tempAmount.compareTo(BigDecimal.ZERO) > 0) {
							log.info("当前【"+dueId+"】所对应的settingAmount是："+tempAmount);
							//查询借据还款信息
							Map<String, Object> param = new HashMap<String, Object>();
							param.put("userOnlyId", setting.getUserOnlyId());
							param.put("dueId", dueId);
							String result = client.queryCashEarlyPay(param);
							if(null != result) {
								JSONObject json = JSONObject.fromObject(result);
								if(json.containsKey("code") && "0000".equals(json.get("code"))) {
									BigDecimal preRepayAmount = new BigDecimal(json.getString("preRepayAmount"));
									BigDecimal needAmount = new BigDecimal(json.getString("needAmount"));
									log.info("当前借据【"+dueId+"】的应还金额为："+preRepayAmount+",最低还款为："+needAmount);
									//如果设置的金额小于该借据的最低还款金额，则不触发自动扣款。直接跳过此人的自动还款。查询下一个人是否符合条件
									if(tempAmount.compareTo(needAmount) < 0) {
										break;
									}
									//如果设置的金额小于借据应还金额。只还第一个借据，其他跳过。
									if(tempAmount.compareTo(preRepayAmount) < 0) {
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("userOnlyId", setting.getUserOnlyId());
										map.put("dueId", dueId);
										map.put("earlyAmt", tempAmount);
										map.put("settingId", setting.getId());
										map.put("preRepayAmount", preRepayAmount);
										autoRepayList.add(map);
										break;
									}
									//第一个借据金额小于设置的金额，则先还当前借据剩下的金额向后面的借据继续还款
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("userOnlyId", setting.getUserOnlyId());
									map.put("dueId", dueId);
									map.put("earlyAmt", preRepayAmount);
									map.put("settingId", setting.getId());
									map.put("preRepayAmount", preRepayAmount);
									autoRepayList.add(map);
									//将设置金额重置为扣掉当前借据金额的值，然后继续循环
									tempAmount = tempAmount.subtract(preRepayAmount);
								}
							}
						}
					}
				}
			}
		}
		//日志打印，上线注释掉
		log.info("所有符合条件的借据信息如下：===========================");
		for(Map<String, Object> map : autoRepayList) {
			log.info(map.toString());
		}
		log.info("所有符合条件的借据信息如上：===========================");
		return autoRepayList;
	}

	@Override
	public void autoRepay() throws Exception {
		log.info("进入方法：autoRepay()");
		List<Map<String, Object>> autoRepayList = queryAllRepayInfo();
		log.info("满足自动还款条件的借据数："+autoRepayList.size());
		ZgdAppClient client = WildflyBeanFactory.getZgdAppClient();
		if(null != autoRepayList && autoRepayList.size() > 0) {
			for(Map<String, Object> map : autoRepayList) {
				String result = client.confimCashEarlyPay(map);
				log.info("查询出当前借据【"+map.get("dueId")+"】的接口返回结果是："+result);
				//接口调用成功之后存日志
				if(null != result) {
					JSONObject json = JSONObject.fromObject(result);
					if(null != json && json.containsKey("code")) {
						if("0000".equals(json.getString("code"))) {
							AutoRepayLogInfo logInfo = new AutoRepayLogInfo();
							logInfo.setSettingId(((BigDecimal) map.get("settingId")).toString());
							logInfo.setAmount((BigDecimal) map.get("earlyAmt"));
							logInfo.setCreateTime(sdf.format(new Date()));
							logInfo.setDueId((String) map.get("dueId"));
							logInfo.setPreRepayAmount((BigDecimal) map.get("preRepayAmount"));
							logInfo.setUserOnlyId((String) map.get("userOnlyId"));
							//拼接accNo -> record.setAccNo("200"+order.getUserId());
							String accNo = "200100" + org.apache.commons.lang.StringUtils.leftPad((String) map.get("userOnlyId"), 20, "0");
							logInfo.setAccNo(accNo);
							autoRepayLogInfoMapper.insertSelective(logInfo);
							log.info("当前借据【"+map.get("dueId")+"】的日志保存成功！");
						}
					}
				}
			}
		}
		log.info("退出方法autoRepay()");
	}

}