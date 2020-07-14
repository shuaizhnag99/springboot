package com.ule.uhj.ejb.client.ycZgd;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.uhj.dto.zgd.MonitorInfoDto;
import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("MonitoringInfoBean")
public interface MonitoringInfoClient {
	
	String saveMonitoringLog(Map<String,Object> map);
	
	String saveMonitoringInfo(MonitorInfoDto dto);
	
	String saveBatchMonitoringInfo(List<MonitorInfoDto> list);
}
