package com.ule.uhj.app.zgd.service;

import java.util.List;
import java.util.Map;

public interface ForecastService {
	
	public String getBusinessScope(String userOnlyId);
	/**
	 *  * 1、通过useronlyid查询出经营范围记录，通过经营范围记录ID 查询经营范围编码与行业编码货预测码关联
	 * 查询出预测码或者vpsID（如果是预测码要再次查询Vps行业与预测码关联表查询出对应的vpsid） 通过vpsid 查询出对应的行业id，名称；
	 * 2、通过useronlyid没有查询出经营范围记录或者通过经营范围记录ID 没有查询经营范围编码与行业编码货预测码关联记录
	 * 通过useronlyid查询预测码表，然后查询Vps行业与预测码关联表查询出对应的vpsid 通过vpsid 查询出对应的行业id，名称
	 * @param userOnlyId
	 * @return
	 */
	public List<Map<String, String>> getForecastKyeWord(String userOnlyId);

}
