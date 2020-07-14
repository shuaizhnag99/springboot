package com.ule.uhj.ejb.client.ycZgd;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.uhj.dto.zgd.AnnouncementDto;
import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("AnnouncementBean")
public interface AnnouncementClient {
	public	Map<String, Object> queryAnnouncementShow(String userOnlyId) throws Exception;
	public  List<Map<String,Object>> queryAnnouncement(String userOnlyId,String type) throws Exception;
	public  Map<String,Object>  queryAnnouncementById(String id) throws Exception;
	public  void  addAnnouncement(AnnouncementDto announcementDto) throws Exception;
	public  void  updateAnnouncement(AnnouncementDto a) throws Exception;
	public Map<String,Object> queryAnnouncePage(Map<String, Object> arg0)  throws Exception;
}
