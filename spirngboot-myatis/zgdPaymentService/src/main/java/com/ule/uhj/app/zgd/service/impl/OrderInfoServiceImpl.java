package com.ule.uhj.app.zgd.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ule.uhj.Dcoffee.object.model.inner.util.Log;
import com.ule.uhj.app.zgd.dao.ActivityInfoMapper;
import com.ule.uhj.app.zgd.dao.ApplyDetailMapper;
import com.ule.uhj.app.zgd.dao.OrderInfoMapper;
import com.ule.uhj.app.zgd.model.ActivityInfo;
import com.ule.uhj.app.zgd.model.ActivityInfoExample;
import com.ule.uhj.app.zgd.model.ApplyDetail;
import com.ule.uhj.app.zgd.model.ApplyDetailExample;
import com.ule.uhj.app.zgd.model.OrderInfo;
import com.ule.uhj.app.zgd.service.CustomerInfoService;
import com.ule.uhj.app.zgd.service.OrderInfoService;
import com.ule.uhj.app.zgd.util.DateUtil;
import com.ule.vpsUser.api.common.util.StringUtil;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

	@Autowired
	private OrderInfoMapper orderInfoMapper;
	
	@Autowired
	private ApplyDetailMapper applyDetailMapper;
	
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private ActivityInfoMapper activityInfoMapper;
	
	@Override
	public Map<String, Object> queryActivityOrderInfo(String userOnlyId) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isEmpty(userOnlyId)) {
			throw new Exception("userOnlyId为空！");
		}
		//查询设置的活动起始和结束时间
		String beginTime = customerInfoService.getIsSuperKeyWord("zj_act_date_begin");
		String endTime = customerInfoService.getIsSuperKeyWord("zj_act_date_end");
		// 判断是否展示页面
		List<OrderInfo> showOrderList = orderInfoMapper.selectShowActivity(userOnlyId,beginTime,endTime);
		// 判断是否送红包
		List<OrderInfo> orderList = orderInfoMapper.selectActivityOrderInfo(userOnlyId,beginTime,endTime);
		
		// 有参加活动的资格,即弹出活动页
		map.put("showPage", false);
		if(null != showOrderList && showOrderList.size() > 0) {
			map.put("showPage", true);
			// 查询出用户的信息
			ApplyDetailExample example = new ApplyDetailExample();
			example.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
			List<ApplyDetail> list = applyDetailMapper.selectByExample(example);
			if(!CollectionUtils.isEmpty(list)) {
				ApplyDetail ad = list.get(0);
				map.put("name", ad.getUserName());
				map.put("phone", ad.getMobileNo());
				map.put("address", ad.getStoreAddress());
				map.put("userOnlyId", userOnlyId);
			}
		}
		// 是否显示红包
		map.put("showCashPrize", false);
		if(null != orderList && orderList.size() > 0) {
			OrderInfo orderInfo = orderList.get(0);
			// 判断是否展示红包
			BigDecimal orderAmount = orderInfo.getOrderAmount();
			if(null != orderAmount && orderAmount.compareTo(new BigDecimal("10000")) >= 0) {
				map.put("showCashPrize", true);
				// 计算红包金额
				BigDecimal amount = new BigDecimal("2.5");
				int days = DateUtil.diffDays(orderInfo.getActualSettleDate(), orderInfo.getLendDate(), DateUtil.YMD);
				if(days > 7) {
					amount = new BigDecimal("17.50");
				}
				else {
					amount = amount.multiply(BigDecimal.valueOf(days));
				}
				map.put("amount", amount.setScale(2,BigDecimal.ROUND_HALF_UP));
			}
		}
		
		return map;
	}
	
	public static void main(String[] args) throws Exception {
		String a = "2018-01-02 21:00:25";
		String a2 = "2018-01-03 01:00:25";
		int result = DateUtil.diffDays(a2, a, DateUtil.YMD);
		System.out.println(result);
	}

	@Override
	public Map<String, Object> checkZJActivity(String userOnlyId)
			throws Exception {
		Log.info("checkZJActivity begin..."+userOnlyId);
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isEmpty(userOnlyId)) {
			throw new Exception("userOnlyId为空！");
		}
		//查询设置的活动起始和结束时间
		String beginTime = customerInfoService.getIsSuperKeyWord("zj_act2_date_begin");
		String endTime = customerInfoService.getIsSuperKeyWord("zj_act2_date_end");
		if(StringUtil.isNotBlank(beginTime)&&StringUtil.isNotBlank(endTime)){
			
			map.put("userOnlyId", userOnlyId);
			map.put("beginTime", beginTime);
			map.put("endTime", endTime);
			List<Map<String, Object>> list=orderInfoMapper.checkActivity(map);
			
			ActivityInfoExample example =new ActivityInfoExample();
			example.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andActivityCodeEqualTo("ZJ_20180625");
			List<ActivityInfo> alist=activityInfoMapper.selectByExample(example);
			
			if(null!=list&&list.size()>0&&(null==alist||alist.size()<1)){
				result.put("showRedPacket", true);
			}else{
				result.put("showRedPacket", false);
			}
			result.put("amount", 10);
		}else{
			result.put("showRedPacket", false);
		}
		Log.info("checkZJActivity begin..."+map.toString());
		return result;
	}

}
