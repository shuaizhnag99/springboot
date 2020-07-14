package com.ule.uhj.dto.zgd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageDto<T> implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5289025852379267444L;

	public static final int DEFAULT_PAGE_SIZE = 20;

	/**
	 * 每页显示条数， 默认为20
	 */
	private int pageSize = 20;

	/**
	 * 当前页码， 默认1
	 */
	private int currPage = 1;

	/**
	 * 总数
	 */
	private int total;

	/**
	 * 查询结果
	 */
	private List<T> result = new ArrayList<T>();

	/**
	 * 导出模式
	 */
	private boolean exportModel = false;

	private Map<String, Object> filters = new HashMap<String, Object>();

	public boolean getExportModel() {
		return exportModel;
	}

	public void setExportModel(boolean exportModel) {
		this.exportModel = exportModel;
	}

	public void setResult(List<T> newResult) {
		result = newResult;
	}

	public List<T> getResult() {
		return result;
	}

	public int getPageSize() {
		if(pageSize <= 0){
			pageSize = DEFAULT_PAGE_SIZE; 
		}
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrPage() {
		if(currPage <= 0){
			currPage = 1;
		}
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}
}
