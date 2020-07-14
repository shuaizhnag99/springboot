package com.ule.uhj.dto.zgd;

import java.io.Serializable;


/**
 * @author zhangyaou
 *
 */
public class UserInfoDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 *用户在第三方系统内id	
	*/
	private String userOnlyId;

	/**
	 *第三方系统编号(邮乐用户100)	
	*/
	private String outSysCode;

	public String getUserOnlyId() {
		return userOnlyId;
	}

	public void setUserOnlyId(String userOnlyId) {
		this.userOnlyId = userOnlyId;
	}

	public String getOutSysCode() {
		return outSysCode;
	}

	public void setOutSysCode(String outSysCode) {
		this.outSysCode = outSysCode;
	}

//	/**
//	 *用户名	
//	*/
//	private String userName;
//
//	/**
//	 *身份证号(加密)	
//	*/
//	private String certNo;
//
//	/**
//	 *页面显示身份证号(前3后四?被试出来)	
//	*/
//	private String certNoView;
//
//	/**
//	 *手机号(加密)	
//	*/
//	private String mobileNo;
//
//	/**
//	 *页面显示手机号	
//	*/
//	private String mobileNoView;
//
//	/**
//	 *固话区号	
//	*/
//	private String telAera;
//
//	/**
//	 *固话	
//	*/
//	private String telNo;
//
//	/**
//	 *是否实名认证(对应一张认证信息表 谁 什么时间 认证了哪些信息)	
//	*/
//	private Integer isRealName;
//
//	/**
//	 *密码(加密)	
//	*/
//	private String password;
//
//	/**
//	 *0:冻结, 1:有效	
//	*/
//	private Integer status;
//
//	/**
//	 *记录创建时间	
//	*/
//	private String createTime;
//
//	/**
//	 *创建人	
//	*/
//	private String creator;
//
//	/**
//	 *记录更新时间	
//	*/
//	private String updateTime;
//
//	/**
//	 *更新人	
//	*/
//	private String updator;
//
//	/**
//	 *备注	
//	*/
//	private String remark;



	
	
}
