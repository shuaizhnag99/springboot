package com.ule.uhj.dto.zgd;

import java.io.Serializable;

public class OperateLogDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3807551212959588574L;
	/**
	 * 主键 年月+ Seq 6位 循环 定长
	 */
	private String id;
	/**
	 * 操作角色
	 */
	private String role;
	/**
	 * 操作时间
	 */
	private String operationTime;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 被操作人
	 */
	private String destCust;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 操作内容
	 */
	private String operationContent;
	/**
	 * 备注
	 */
	private String reamrk;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getDestCust() {
		return destCust;
	}
	public void setDestCust(String destCust) {
		this.destCust = destCust;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOperationContent() {
		return operationContent;
	}
	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}
	public String getReamrk() {
		return reamrk;
	}
	public void setReamrk(String reamrk) {
		this.reamrk = reamrk;
	}
	
	
}
