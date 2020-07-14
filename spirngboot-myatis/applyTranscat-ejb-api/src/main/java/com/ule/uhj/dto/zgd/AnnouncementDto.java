package com.ule.uhj.dto.zgd;

import java.io.Serializable;

public class AnnouncementDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 *主键ID,值自增	
	*/
	private Long id;

	/**
	 *公告类型,1:逾期处罚,2:通知	
	*/
	private String type;

	/**
	 *标题	
	*/
	private String title;

	/**
	 *公告日期	
	*/
	private String nounceDate;

	/**
	 *内容	
	*/
	private String content;

	/**
	 *是否在首页展示 1:展示,0:不展示	
	*/
	private String isShowFp;
	/**
	 * 规则执行id
	 */
	private String ruleSetId;
	/**
	 *1:有效,0:无效
	*/
	private String status;
	/**
	 *创建者
	 */
	private String creater;
	/**
	 *创建者
	 */
	private String createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNounceDate() {
		return nounceDate;
	}
	public void setNounceDate(String nounceDate) {
		this.nounceDate = nounceDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIsShowFp() {
		return isShowFp;
	}
	public void setIsShowFp(String isShowFp) {
		this.isShowFp = isShowFp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the ruleSetId
	 */
	public String getRuleSetId() {
		return ruleSetId;
	}
	/**
	 * @param ruleSetId the ruleSetId to set
	 */
	public void setRuleSetId(String ruleSetId) {
		this.ruleSetId = ruleSetId;
	}
	
	
	
}
