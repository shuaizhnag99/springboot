package com.ule.uhj.dto.zgd;

import java.io.Serializable;

public class PromoterFeedbackDto implements Serializable {
	private static final long serialVersionUID = -2147672876964060487L;

	private Long id;//id
	
	private String taskNo;//任务编号
	 
	private String promoterId;//地推人员ID
	
	private String userOnlyId;//掌柜ID
	
	private String promoterName;//地推人员姓名
	
	private String promoterJobNo;//地推人员工号
	
	private String promoterPhone;//地推人员手机号
	
	private Integer taskType;//任务类型[1：掌柜贷款逾期提醒、2：掌柜经营业绩下滑、3：掌柜贷推广反馈]
	
	private Integer loanApplyStatus;//掌柜借款申请状态[0:审核拒绝,88:审核通过,12:已退回,-1:未申请],当TASK_TYPE值为3时,此列不能为空
	
	private String feedbackContent;//地推人员反馈内容[如果TASK_TYPE值为1 or 2时，该列保存掌柜反馈内容]  
	
	private Integer isAnewApply;//是否重新申请,1:已申请、0：未申请[如果TASK_TYPE值为3、LOAN_APPLY_STATUS值为12时，该列保存掌柜是否重新申请]
	
	private String useTime;//支用时间[如果TASK_TYPE值为3、LOAN_APPLY_STATUS值为88时，该列保存掌柜何时使用该笔借款]
	
	private Integer use;//支用用途：1：经营周转、2：扩大经营、3：购买农资农具、4：借给他人、5：日常消费、6：红白喜事、7：其它
	
	private Integer isWish;	//是否有意愿，1(有意愿)，0：无意愿[如果TASK_TYPE值为3、LOAN_APPLY_STATUS值为0 or -1时，该列保存掌柜是否有意愿借款]
	
	private Integer noWishReason;//无意愿借款原因
	
	private Integer isOther;//是否其他选项，1是、null or 其它为否[如果TASK_TYPE值为3、LOAN_APPLY_STATUS值为0 or -1时、IS_WISH值为0时，该列表示是否为其他选项]
	
	private String otherContent;//其他选项对应内容
	
	private String nextPatrolTime;//下一巡店日期
	
	private String predictLoanTime;//预计贷款时间[如果TASK_TYPE值为3、LOAN_APPLY_STATUS值为0 or -1时、IS_WISH值为Y时，该列表示掌柜预计贷款时间]
	
	private String createTime;//信息创建时间
	
	private String provinceName;//省
	
	private String cityName;//市
	
	private String countyName;//区 or 县
	
	private String orgCode;//机构号
	
	private String username;//掌柜姓名

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getPromoterId() {
		return promoterId;
	}

	public void setPromoterId(String promoterId) {
		this.promoterId = promoterId;
	}

	public String getUserOnlyId() {
		return userOnlyId;
	}

	public void setUserOnlyId(String userOnlyId) {
		this.userOnlyId = userOnlyId;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Integer getLoanApplyStatus() {
		return loanApplyStatus;
	}

	public void setLoanApplyStatus(Integer loanApplyStatus) {
		this.loanApplyStatus = loanApplyStatus;
	}

	public String getFeedbackContent() {
		return feedbackContent;
	}

	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}

	public Integer getIsAnewApply() {
		return isAnewApply;
	}

	public void setIsAnewApply(Integer isAnewApply) {
		this.isAnewApply = isAnewApply;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	public Integer getIsWish() {
		return isWish;
	}

	public void setIsWish(Integer isWish) {
		this.isWish = isWish;
	}

	public Integer getIsOther() {
		return isOther;
	}

	public void setIsOther(Integer isOther) {
		this.isOther = isOther;
	}

	public String getOtherContent() {
		return otherContent;
	}

	public void setOtherContent(String otherContent) {
		this.otherContent = otherContent;
	}

	public String getNextPatrolTime() {
		return nextPatrolTime;
	}

	public void setNextPatrolTime(String nextPatrolTime) {
		this.nextPatrolTime = nextPatrolTime;
	}

	public String getPredictLoanTime() {
		return predictLoanTime;
	}

	public void setPredictLoanTime(String predictLoanTime) {
		this.predictLoanTime = predictLoanTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	
	public Integer getNoWishReason() {
		return noWishReason;
	}

	public void setNoWishReason(Integer noWishReason) {
		this.noWishReason = noWishReason;
	}

	public Integer getUse() {
		return use;
	}

	public void setUse(Integer use) {
		this.use = use;
	}

	public String getPromoterName() {
		return promoterName;
	}

	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}

	public String getPromoterJobNo() {
		return promoterJobNo;
	}

	public void setPromoterJobNo(String promoterJobNo) {
		this.promoterJobNo = promoterJobNo;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPromoterPhone() {
		return promoterPhone;
	}

	public void setPromoterPhone(String promoterPhone) {
		this.promoterPhone = promoterPhone;
	}
}
