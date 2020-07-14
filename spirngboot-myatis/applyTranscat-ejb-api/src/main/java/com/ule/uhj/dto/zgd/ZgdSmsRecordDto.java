package com.ule.uhj.dto.zgd;

import java.io.Serializable;

public class ZgdSmsRecordDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4981459574024527479L;
	
	public static final String para_phone = "phone";
	public static final String para_userName = "userName";
	public static final String para_sendBeginDate = "sendBeginDate"; 
	public static final String para_sendEndDate = "sendEndDate";
	public static final String para_sendDate = "sendDate";
	

	/**
	 * 用户id
	 */
	private String userId;
	
	/**
	 * 账户id
	 */
	private String accNo;
	
	/**
	 *掌柜姓名
	 */
	private String userName;
	/**
	 * 电话号码
	 */
	private String phone;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private String sendDate;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 短信编号：对应UhjMessageUtil里的短信编号
	 */
	private String boardId;
	/**
	 * 短信类型
	 */
	private String type;
	/**
	 * 创建时间
	 */
	private String remark;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
}
