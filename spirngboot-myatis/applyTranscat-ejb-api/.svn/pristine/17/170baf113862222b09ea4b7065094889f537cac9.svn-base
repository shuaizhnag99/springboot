package com.ule.uhj.dto.export;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author zhangyaou
 *
 */
public class ExportFilterValueDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8228145387100107050L;
	/**
	 * 
	 */
	String id;
	/**
	 * exportType.exportId
	 */
	String exportId;
	/**
	 * 过滤条件中文名
	 */
	String cnName;
	/**
	 * 过滤条件英文名
	 */
	String enName;
	/**
	 *操作符号 
	 */
	String operate;
	/**
	 * 下拉框的defaultValue使用 key:value; 形式表现
	 */
	String defaultValue;
	/**
	 * 过滤条件类型 1：输入框;2：日期;3：下拉框
	 */
	String type;
	
	/**
	 * 序列
	 */
	String index;
	
	/**
	 * 状态 0:无效,1:有效
	 */
	String status;
	
	String remark;
	String creator;
	String updator;
	
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExportId() {
		return exportId;
	}
	public void setExportId(String exportId) {
		this.exportId = exportId;
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
	
	public Map<String, String> getDefaultValues(){
		Map<String, String> result = new Hashtable<String, String>();
		if(defaultValue != null && defaultValue.trim().length() > 0){
			String[] entry = null;
			for(String value : defaultValue.split("\\;")){
				System.out.println(value);
				entry = value.split("\\:");
				result.put(entry[0], entry[1]);
			}
		}
		return result;
	}
}
