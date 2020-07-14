package com.ule.uhj.init.dao;

import java.util.List;
import java.util.Map;

import com.ule.uhj.init.modle.CorpChangeInfo;
import com.ule.uhj.init.modle.CropInfo;

/**
 * 模板
 * Created by wuhaitao on 2017/9/18.
 */
public interface AnalysisMapper {
    /***
     * 查询
     * @param 
     * @return 查询需要解析的报文的id
     */
	public List<String> queryInterfaceId(Map<String, String> map);

	public void saveCropChange(List<CorpChangeInfo> changes);

	public void saveCrop(CropInfo crop);

}
