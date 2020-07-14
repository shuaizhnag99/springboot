package com.ule.uhj.dto.zgd;

import java.io.Serializable;
import java.util.Date;

public class UserShopPhotoDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2168196907260885439L;
	private long id;
	private String villageNo;
	private String userOnlyId;
	private String status;
	private String auditStatus;
	private String photoCompressUrl;
	private String photoUrl;
	private Date createTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getVillageNo() {
		return villageNo;
	}
	public void setVillageNo(String villageNo) {
		this.villageNo = villageNo;
	}
	public String getUserOnlyId() {
		return userOnlyId;
	}
	public void setUserOnlyId(String userOnlyId) {
		this.userOnlyId = userOnlyId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getPhotoCompressUrl() {
		return photoCompressUrl;
	}
	public void setPhotoCompressUrl(String photoCompressUrl) {
		this.photoCompressUrl = photoCompressUrl;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
