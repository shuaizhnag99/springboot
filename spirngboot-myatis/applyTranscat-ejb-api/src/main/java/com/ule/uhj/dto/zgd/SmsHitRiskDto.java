package com.ule.uhj.dto.zgd;

import java.io.Serializable;
public class SmsHitRiskDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 *主键	
	*/
	private Long id;
	/**
	 * 用户id
	 */
	private String userOnlyId;
	/**
	 *联系人
	 */
	private String smsName;
	
	/**
	 * 本机电话号码
	 */
	private String selfPhoneNum;
	
	/**
	 * 电话号码
	 */
	private String phoneNum;
	/**
	 *短信记录_消息类型,1:收信箱,2:发信箱
	 */
	private String smsType;
	/**
	 * 内容
	 */
	private String smsBody;
	
	/**
	 * 风险内容
	 */
	private String riskContent;
	
	/**
	 * 风险词版本
	 */
	private String riskVer;
	
	/**
	 * 短信记录_发送时间
	 */
	private String smsDate;
	
	/**
	 * 推送搜索采集时间
	 */
	private String collectionTime;
	
	/**
	 * 入库时间
	 */
	private String createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserOnlyId() {
		return userOnlyId;
	}

	public void setUserOnlyId(String userOnlyId) {
		this.userOnlyId = userOnlyId;
	}

	public String getSmsName() {
		return smsName;
	}

	public void setSmsName(String smsName) {
		this.smsName = smsName;
	}

	public String getSelfPhoneNum() {
		return selfPhoneNum;
	}

	public void setSelfPhoneNum(String selfPhoneNum) {
		this.selfPhoneNum = selfPhoneNum;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public String getSmsBody() {
		return smsBody;
	}

	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
	}

	public String getRiskContent() {
		return riskContent;
	}

	public void setRiskContent(String riskContent) {
		this.riskContent = riskContent;
	}

	public String getRiskVer() {
		return riskVer;
	}

	public void setRiskVer(String riskVer) {
		this.riskVer = riskVer;
	}

	public String getSmsDate() {
		return smsDate;
	}

	public void setSmsDate(String smsDate) {
		this.smsDate = smsDate;
	}

	public String getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	
	
}
