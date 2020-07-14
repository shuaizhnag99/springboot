package com.ule.uhj.dto.zgd;

import java.io.Serializable;

public class ExtendPicsDto implements Serializable {
 

	/**
	 * 
	 */
	private static final long serialVersionUID = 2889433018216751675L;

	/**
	 *  
	 */
	private String picid;
	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 用户id
	 */
	private String userOnlyId;

	/**
	 * 图片url
	 */
    private String picUrl;
	

	public String getPicid() {
		return picid;
	}

	public void setPicid(String picid) {
		this.picid = picid;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUserOnlyId() {
		return userOnlyId;
	}

	public void setUserOnlyId(String userOnlyId) {
		this.userOnlyId = userOnlyId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

}