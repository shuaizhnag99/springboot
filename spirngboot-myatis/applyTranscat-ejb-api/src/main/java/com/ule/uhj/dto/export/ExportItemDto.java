package com.ule.uhj.dto.export;

import java.io.Serializable;

/**
 * 导出列
 * @author zhangyaou
 *
 */
public class ExportItemDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8257580782124110175L;
	String itemId;
	/**
	 * exportType.exportId
	 */
	String exportId;
	String enName;
	String cnName;
	/**
	 * 序号
	 */
	String index;
	/**
	 * 状态 0:无效,1:有效
	 */
	String status;
	String remark;
	String creator;
	String updator;
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getExportId() {
		return exportId;
	}
	public void setExportId(String exportId) {
		this.exportId = exportId;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
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
	
}
