package com.ule.uhj.dto.export;

import java.io.Serializable;
import java.util.List;

/**
 * 导出类型
 * 
 * @author zhangyaou
 *
 */
public class ExportUnitDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7019618359219008608L;
	/**
	 * 导出类型id
	 */
	String exportId;
	/**
	 * 查询sql
	 */
	String sql;

	/**
	 * 导出类型title
	 */
	String title;

	/**
	 * 状态 0:无效,1:有效
	 */
	String status;
	String remark;
	String creator;
	String updator;

	List<ExportItemDto> items;
	List<ExportFilterValueDto> filterValues;

	public String getExportId() {
		return exportId;
	}

	public void setExportId(String exportId) {
		this.exportId = exportId;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public List<ExportItemDto> getItems() {
		return items;
	}

	public void setItems(List<ExportItemDto> items) {
		this.items = items;
	}

	public List<ExportFilterValueDto> getFilterValues() {
		return filterValues;
	}

	public void setFilterValues(List<ExportFilterValueDto> filterValues) {
		this.filterValues = filterValues;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
