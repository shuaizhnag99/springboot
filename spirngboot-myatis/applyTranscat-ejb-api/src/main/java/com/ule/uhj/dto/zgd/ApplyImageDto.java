package com.ule.uhj.dto.zgd;

import java.io.Serializable;

public class ApplyImageDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6309528050011593430L;
	/**
	 * 主键  Seq 
	 */
	private String id;
	/**
	 * 用户Id
	 */
	private String userOnlyId;
	/**
	 * 图片类型
	 */
	private String imageType;
	/**
	 * 图片URL
	 */
	private String imageUrl;
	/**
	 * 图片状态
	 */
	private Integer status;
	/**
	 * 退回原因
	 */
	private String backReason;
	/**
	 * 上传手机系统类型
	 */
	private String phoneType;
	/**
	 * 是否手机上传，1 是0 不是
	 */
	private Integer isMobileUpload;
	/**
	 * 上传IP
	 */
	private String applyIp;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * '创建人
	 */
	private String creator;
	/**
	 * 修改时间
	 */
	private String updateTime;
	/**
	 * 修改人
	 */
	private String updator;
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
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
 
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getBackReason() {
		return backReason;
	}
	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}
	
	public String getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	public Integer getIsMobileUpload() {
		return isMobileUpload;
	}
	public void setIsMobileUpload(Integer isMobileUpload) {
		this.isMobileUpload = isMobileUpload;
	}
	public String getApplyIp() {
		return applyIp;
	}
	public void setApplyIp(String applyIp) {
		this.applyIp = applyIp;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdator() {
		return updator;
	}
	public void setUpdator(String updator) {
		this.updator = updator;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
