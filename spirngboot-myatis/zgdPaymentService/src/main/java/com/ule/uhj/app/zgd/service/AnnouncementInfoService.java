package com.ule.uhj.app.zgd.service;

import java.util.List;
import java.util.Map;

public interface AnnouncementInfoService {
	
	public Map<String,Object> queryAnnounceShow(String userOnlyId,List<String> prdCode)throws Exception;
	
	public Map<String,Object> queryAnnounceList(String userOnlyId,List<String> prdCode)throws Exception;
	
	public Map<String,Object> saveAnnounceRecord(Map<String,Object> param)throws Exception;

}
