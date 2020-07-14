package com.ule.uhj.dto.zgd;

import java.io.Serializable;

public class CustomerVisitDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5494400413040492807L;
	/**
	 *id(yyyymmdd+6位seqs)	
	*/
	private String id;
	/**
	 * 用户id
	 */
	private String userOnlyId;
	
	/**
	 *被呼叫人类型 contact 联系人self 本人
	 */
	private String calleType;
	/**
	 * 接听时间
	 */
	private String calleTime;
	/**
	 *是否接听 0 未接听1已接听
	 */
	private String isAnswer;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 备注
	 */
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserOnlyId() {
		return userOnlyId;
	}
	public void setUserOnlyId(String userOnlyId) {
		this.userOnlyId = userOnlyId;
	}
	public String getCalleType() {
		return calleType;
	}
	public void setCalleType(String calleType) {
		this.calleType = calleType;
	}
	public String getCalleTime() {
		return calleTime;
	}
	public void setCalleTime(String calleTime) {
		this.calleTime = calleTime;
	}
	public String getIsAnswer() {
		return isAnswer;
	}
	public void setIsAnswer(String isAnswer) {
		this.isAnswer = isAnswer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
