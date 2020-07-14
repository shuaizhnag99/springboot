package com.ule.uhj.app.zgd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ule.uhj.app.zgd.dao.ActivityInfoMapper;
import com.ule.uhj.app.zgd.model.ActivityInfo;
import com.ule.uhj.app.zgd.model.ActivityInfoExample;
import com.ule.uhj.app.zgd.model.ActivityInfoExample.Criteria;
import com.ule.uhj.app.zgd.service.ActivityInfoService;
import com.ule.uhj.app.zgd.util.DateUtil;
import com.ule.vpsUser.api.common.util.StringUtil;

@Service
public class ActivityInfoServiceImpl implements ActivityInfoService {

	@Autowired
	private ActivityInfoMapper activityInfoMapper;
	
	@Override
	public void saveActivityInfo(ActivityInfo activity) throws Exception {

		if(StringUtils.isEmpty(activity.getUserOnlyId())) {
			throw new Exception("userOnlyId为空！");
		}
		// 先查询该活动是否已经存在
		ActivityInfoExample example = new ActivityInfoExample();
		Criteria cr=example.createCriteria();
		cr.andUserOnlyIdEqualTo(activity.getUserOnlyId());
		if(StringUtil.isNotBlank(activity.getActivityCode())){
			cr.andActivityCodeEqualTo(activity.getActivityCode());
		}
		List<ActivityInfo> list = activityInfoMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)) {
			throw new Exception("该掌柜已领过奖品！");
		}
		activity.setPromotionDate(DateUtil.currTimeStr());
		activity.setCreateTime(DateUtil.currTimeStr());
		activityInfoMapper.insertSelective(activity);
	}

}
