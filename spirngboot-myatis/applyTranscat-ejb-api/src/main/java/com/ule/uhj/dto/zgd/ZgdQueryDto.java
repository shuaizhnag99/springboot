package com.ule.uhj.dto.zgd;

import java.io.Serializable;

public class ZgdQueryDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long index;
	
	private Integer firstResult;//数据起始位置
	
	private Integer maxResults;//数据条目数
	
	private String userName;//掌柜名称
	
	private String certNo;//掌柜身份证号

	private String startDate;//开始日期
	
	private String endDate;//截至日期

	private String orgProvince;//省份代码
	
	private String province;//省

	private String city;//市

	private String county;//县、区
	
	private String town;

	private String townOrgCode;//支局机构号 为什么查不到
	
	private String firstStatus;//一级状态
	
	private String secondStatus;//二级状态
	
	private String orgCode;//机构号

	private String area;//区域
	
	private String lastUpdateTime;//最后更新时间
	
	private String balance;//授信额度
	
	private String availBalance;//可用额度
	
	private String totalPayCount;//累计支用次数
	
	private String totalPayAmount;//累计支用金额
	
	private String balanceDeadline;//授信截至日期
	
	private String reApplyNumber;//授信次数
	
	private String LastBalance;//上次授信额度
	
	private String remark;//备注
	
	private String pixiaoDjAmount;//批销冻结额度
	
	private String pixiaoYyAmount;//批销已用额度
	
	private String sortBy;//排序字段
	
	private String mobile;//地推人员手机号
	
	private String youZhuShouMobile;//地推人员手机号
	
	private String userOnlyId;//用户id
	
    private String LoanOfficerName;//信贷员名字
	
	private String LoanOfficerOrg;//信贷员机构
	
	private String LoanOfficerPhone;//信贷员手机号
	
	private String sfdt; //是否绑定掌柜贷地推
	
	private String applyTimeBegin; // 申请开始时间
	
	private String applyTimeEnd; // 申请结束时间
	
	private String importTimeBegin; // 进件开始时间
	
	private String importTimeEnd; // 进件结束时间
	
	private String channelCode; // 渠道号

	private String limitType; // 
	private String rate; // 
	private String channel; // 
	
	private String secondchannelCode; // 二级渠道号

	private String productType; //申请产品
	private String amountType; //额度类型

	private String leagueChannel;//是否自助注册 '注册渠道 0:邮乐渠道,1:邮政渠道,3:web自主注册,4:pc端自主注册,5:app自主注册,6:掌柜贷自主注册'
	
	private String creditCount;//授信次数
	
	private String zgdName;//邮掌柜地推人员姓名
	
	private String yzsName;//邮助手地推人员姓名

	private String effectiveStartDate;//额度生效开始日期

	private String effectiveEndDate;//额度生效结束日期

	private String effectiveDate;//额度生效日期

	private String pixiaoStatus;//是否开通进货版

	public String getCreditCount() {
		return creditCount;
	}

	public void setCreditCount(String creditCount) {
		this.creditCount = creditCount;
	}

	public String getTownOrgCode() {
		return townOrgCode;
	}

	public void setTownOrgCode(String townOrgCode) {
		this.townOrgCode = townOrgCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	public String getApplyTimeBegin() {
		return applyTimeBegin;
	}

	public void setApplyTimeBegin(String applyTimeBegin) {
		this.applyTimeBegin = applyTimeBegin;
	}

	public String getApplyTimeEnd() {
		return applyTimeEnd;
	}

	public void setApplyTimeEnd(String applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}

	public String getImportTimeBegin() {
		return importTimeBegin;
	}

	public void setImportTimeBegin(String importTimeBegin) {
		this.importTimeBegin = importTimeBegin;
	}

	public String getImportTimeEnd() {
		return importTimeEnd;
	}

	public void setImportTimeEnd(String importTimeEnd) {
		this.importTimeEnd = importTimeEnd;
	}

	public String getSfdt() {
		return sfdt;
	}

	public void setSfdt(String sfdt) {
		this.sfdt = sfdt;
	}

	public Integer getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}


	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getFirstStatus() {
		return firstStatus;
	}

	public void setFirstStatus(String firstStatus) {
		this.firstStatus = firstStatus;
	}

	public String getSecondStatus() {
		return secondStatus;
	}

	public void setSecondStatus(String secondStatus) {
		this.secondStatus = secondStatus;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getAvailBalance() {
		return availBalance;
	}

	public void setAvailBalance(String availBalance) {
		this.availBalance = availBalance;
	}

	public String getTotalPayCount() {
		return totalPayCount;
	}

	public void setTotalPayCount(String totalPayCount) {
		this.totalPayCount = totalPayCount;
	}

	public String getTotalPayAmount() {
		return totalPayAmount;
	}

	public void setTotalPayAmount(String totalPayAmount) {
		this.totalPayAmount = totalPayAmount;
	}

	public String getBalanceDeadline() {
		return balanceDeadline;
	}

	public void setBalanceDeadline(String balanceDeadline) {
		this.balanceDeadline = balanceDeadline;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getOrgProvince() {
		return orgProvince;
	}

	public void setOrgProvince(String orgProvince) {
		this.orgProvince = orgProvince;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getReApplyNumber() {
		return reApplyNumber;
	}

	public void setReApplyNumber(String reApplyNumber) {
		this.reApplyNumber = reApplyNumber;
	}

	public String getLastBalance() {
		return LastBalance;
	}

	public void setLastBalance(String lastBalance) {
		LastBalance = lastBalance;
	}

	public String getPixiaoDjAmount() {
		return pixiaoDjAmount;
	}

	public void setPixiaoDjAmount(String pixiaoDjAmount) {
		this.pixiaoDjAmount = pixiaoDjAmount;
	}

	public String getPixiaoYyAmount() {
		return pixiaoYyAmount;
	}

	public void setPixiaoYyAmount(String pixiaoYyAmount) {
		this.pixiaoYyAmount = pixiaoYyAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getYouZhuShouMobile() {
		return youZhuShouMobile;
	}

	public void setYouZhuShouMobile(String youZhuShouMobile) {
		this.youZhuShouMobile = youZhuShouMobile;
	}

	public String getUserOnlyId() {
		return userOnlyId;
	}

	public void setUserOnlyId(String userOnlyId) {
		this.userOnlyId = userOnlyId;
	}

	public String getLoanOfficerName() {
		return LoanOfficerName;
	}

	public void setLoanOfficerName(String loanOfficerName) {
		LoanOfficerName = loanOfficerName;
	}

	public String getLoanOfficerOrg() {
		return LoanOfficerOrg;
	}

	public void setLoanOfficerOrg(String loanOfficerOrg) {
		LoanOfficerOrg = loanOfficerOrg;
	}

	public String getLoanOfficerPhone() {
		return LoanOfficerPhone;
	}

	public void setLoanOfficerPhone(String loanOfficerPhone) {
		LoanOfficerPhone = loanOfficerPhone;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSecondchannelCode() {
		return secondchannelCode;
	}

	public void setSecondchannelCode(String secondchannelCode) {
		this.secondchannelCode = secondchannelCode;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public String getLeagueChannel() {
		return leagueChannel;
	}

	public void setLeagueChannel(String leagueChannel) {
		this.leagueChannel = leagueChannel;
	}

	public String getYzsName() {
		return yzsName;
	}

	public void setYzsName(String yzsName) {
		this.yzsName = yzsName;
	}

	public String getZgdName() {
		return zgdName;
	}

	public void setZgdName(String zgdName) {
		this.zgdName = zgdName;
	}

	public String getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(String effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public String getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(String effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getPixiaoStatus() {
		return pixiaoStatus;
	}

	public void setPixiaoStatus(String pixiaoStatus) {
		this.pixiaoStatus = pixiaoStatus;
	}
}