package com.ule.uhj.sldProxy.service.impl;

import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.ule.uhj.sld.model.SldOperateLog;
import com.ule.uhj.sldProxy.dao.SldOperateLogMapper;
import com.ule.uhj.sldProxy.service.SldOperateLogService;

@Service("sldOperateLogService")
public class SldOperateLogServiceImpl implements SldOperateLogService {
	private static Log log = LogFactory.getLog(SldOperateLogServiceImpl.class);
	@Autowired
	SldOperateLogMapper dao;
	
	@Async
	public Future<Boolean> saveOperateLog(SldOperateLog operateLog) {
		try {
			dao.insert(operateLog);
			log.info("saveOperateLog success");
			return new AsyncResult<Boolean>(true);
		} catch (Exception e) {
			log.error("saveOperateLog failure", e);
			return new AsyncResult<Boolean>(false);
		}
	}
}
