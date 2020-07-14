package com.ule.uhj.app.zgd.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.zgd.bo.ForecastResultSaveBO;
import com.ule.uhj.app.zgd.dao.CreditApplyMapper;
import com.ule.uhj.app.zgd.dao.ForecastResultMapper;
import com.ule.uhj.app.zgd.model.CreditApply;
import com.ule.uhj.app.zgd.model.CreditApplyExample;
import com.ule.uhj.app.zgd.model.ForecastResult;
import com.ule.uhj.app.zgd.service.ForecastResultService;

/**
 * @author zhangbowei
 * @date 2018/6/25
 */
@Service
public class ForecastResultServiceImpl implements ForecastResultService {

    private final static Logger log = LoggerFactory.getLogger(ForecastResultServiceImpl.class);

    @Autowired
    private ForecastResultMapper forecastResultMapper;
    @Autowired
	private CreditApplyMapper creditApplyMapper;

    @Override
    public boolean saveScreeningScope(ForecastResultSaveBO forecastResultSaveBO) {
        try {
            log.info("经营范围筛选结果保存入参：{}", forecastResultSaveBO);
            String userOnlyId = forecastResultSaveBO.getUserOnlyId();
            String applyId = forecastResultSaveBO.getApplyId();
            if (StringUtils.isBlank(userOnlyId) || StringUtils.isBlank(applyId)) {
                return false;
            }
            ForecastResult selectResult = forecastResultMapper.selectByUserOnlyIdAndApplyId(userOnlyId, applyId);
            log.info("经营范围筛选结果保存入参：{}, 查询结果: {}", forecastResultSaveBO, selectResult);
            ForecastResult forecastResult = new ForecastResult();
            BeanUtils.copyProperties(forecastResultSaveBO, forecastResult);
            int result;
            if (selectResult == null) {
                result = forecastResultMapper.insert(forecastResult);
            } else {
                result = forecastResultMapper.update(forecastResult);
            }
            log.info("经营范围筛选结果保存结果: {}", result > 0);
            return result > 0;
        } catch (Exception e) {
            log.error("经营范围筛选结果保存异常, error: ", e);
            return false;
        }
    }

    @Override
    public boolean saveChooseResult(String userOnlyId, String chooseResult) {
        try {
            log.info("用户所选行业保存入参，userOnlyid: " + userOnlyId + ",chooseResult: " + chooseResult);
        	//获取申请的ID
    		String applyId="";
    		CreditApplyExample caExample =new CreditApplyExample();
    		caExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
    		List<CreditApply> calist=creditApplyMapper.selectByExample(caExample);
    		if(calist!=null && calist.size()>0){
    			applyId=calist.get(0).getId();
    		}
            ForecastResult forecastResult = new ForecastResult();
            forecastResult.setUserOnlyId(userOnlyId);
            forecastResult.setApplyId(applyId);
            forecastResult.setChooseResult(chooseResult);
            log.info("用户所选行业保存：{}", forecastResult);
            int result = forecastResultMapper.update(forecastResult);
            log.info("用户所选行业保存结果：{}", result > 0);
            return result > 0;
        } catch (Exception e) {
            log.error("用户所选行业保存异常, error: ", e);
            return false;
        }
    }
}
