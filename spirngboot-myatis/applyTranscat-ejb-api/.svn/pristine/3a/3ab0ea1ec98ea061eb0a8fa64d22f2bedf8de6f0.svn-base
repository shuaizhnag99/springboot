package com.ule.uhj.dto.zgd;

import java.io.Serializable;
import java.math.BigDecimal;


public class ApplyAuditDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5554830635703196264L;
	
	/**
	 * 主键 年月+ Seq 6位 循环 定长
	 */
	private String id;
	/**
	 * 第三方用户id
	 */
	private String userOnlyId;
	/**
	 * 第三方系统编号
	 */
	private String outSysCode;
	/**
	 * 掌柜姓名
	 */
	private String userName;
	/**
	 * 申请者定位信息与帮掌柜店铺位置信息不一致
	 */
	private Integer applyPositionSP;
	/**
	 * 姓名、身份证、邮储卡是否一致
	 */
	private Integer nameCertnoPhoto;
	/**
	 * 姓名、店铺名称、地址与工商数据
	 */
	private Integer nameStAdBuiness;
//	/**
//	 * 人脸识别
//	 */
//	private Integer faceRecognition;
	/**
	 * 营业执照信息与上传营业执照、店铺照片是否一致
	 */
	private Integer businessLiIn;
	
	/**
	 * 房产证明
	 *10  照片清晰
	 *11 上传照片不正确，非房产证明
	 *12 房产证明字迹模糊、看不清
	 *13 未上传房产证明
	 */
	private Integer houseOwnership;
	/**
	 * 房产证明异常描述
	 */
	private String houseOwnDesc;
	/**
	 * 近一年行政处罚
	 */
	private Integer admPenalty;
	
	/**
	 * 行政处罚描述
	 */
	private String admPenaltyDesc;
	/**
	 * 近一年经营异常
	 */
	private Integer operatingAnomalies;
	
	/**
	 * 近一年经营异常
	 */
	private String operatingAnomaliesDesc;
	/**
	 * 存在风险状态（催债、嗜赌等）
	 */
	private Integer riskStatus;
	
	/**
	 * 风险状态描述
	 */
	private String riskSDesc;

	/**
	 * 是否存在外部黑名单
	 */
	private Integer externalBL;
	
	/**
	 * 黑名单描述
	 */
	private String blackListDesc;
	/**
	 * 外部贷款数+邮储贷款+人行贷款次数>3
	 */
	private Integer loanNum;
//	/**
//	 * 邮储黑名单或不良客户
//	 */
//	private Integer postalSavingsBL;
	/**
	 * 实名验证是否通过
	 */
	private  Integer realNameAuth;
	/**
	 * 姓名、身份证与照片比对
	 */
	private Integer nameCertImg;
	
	/**
     * 预审身份证正面照片状态
     * 	10、照片清晰
	 * 	11、上传照片不正确，非身份证正面照
	 * 	12、身份证照片模糊、看不清
	 * 	13、身份证姓名不清晰或有遮挡
	 * 	14、身份证住址不清晰或有遮挡
	 * 	15、身份证号码不清晰或有遮挡
     */
	private Integer certPositiveImg;
	/**
     * 预审身份证反面照片状态
     * 10、 照片清晰 
	 * 11、上传照片不正确，非身份证反面照 
	 * 12、身份证有限期限不清晰或有遮挡 
     */
	private Integer certNegativeImg;
	/**
	 * 身份证有效期
	 * 
	 */
	private String valiadIdCert;
	/**
     * 预审手持身份证照片状态
     * 10、 照片清晰 
	 * 11、上传照片不正确，非手持身份证照片
	 * 12、本人未出现在照片里
	 * 13、手中身份证姓名、地址、号码模糊、看不清
     */
	private Integer certHoldImg;
	
	/**
     * 预审营业执照照片状态
     * 
     * 	10、 照片清晰 
     * 	11、上传照片不正确，非营业执照
     * 	12、营业执照副本未拍全
     * 	13、营业执照照片模糊、看不清
     * 	14、营业执照注册号不清晰或有遮挡
     * 	15、营业执照名称不清晰或有遮挡
     * 	16、营业执照经营者不清晰或有遮挡
     * 	17、营业执照注册日期不清晰或有遮挡
     * 	18、营业执照经营场所不清晰或有遮挡
     * 	19、营业执照非原件
     */
	private Integer busLiecnceImg;
	/**
	 * 店铺名称
	 */
	private String storeName;
	/**
	 * 店铺名称是否一致
	 */
	private Integer storeNameAgree;
	/**
	 * 经营者
	 */
	private String storeOperator;
	
	/**
	 * 经营者是否一致
	 */
	private Integer storeOperatorAgree;
	

	/**
	 * 经营场所
	 */
	private String businessPremisese;
	/**
	 * 经营场所是否一致
	 */
	private Integer businessPremiseseAgree;
	
	/**
	 * 成立日期
	 */
	private String establishmentTime;
	/**
     * 预审店铺外照片状态
     * 
     * 	10、 照片清晰 
     * 	11、上传照片不正确，非店铺外照片
     * 	12、店铺外照片模糊、看不清
     */
	private Integer storeIMgeAgree;
	

	/**
     * 预审店铺内照片状态
     * 
     * 	10、 照片清晰 
     * 	11、上传照片不正确，非店铺内照片
     * 	12、店铺内照片模糊，看不清
     */
	private Integer storeInnerImgeAgree;
	
//	/**
//	 * 人行征信逾期超1个月
//	 */
//	private Integer creditInveOver;
	/**
	 * 创建时间
	 */
	private  String createTime;
	/**
	 * 创建人
	 */
	private String createPerson;
	/**
	 * 修改时间
	 */
	private String updateTime;
	/**
	 * 修改人
	 */
	private String updatePerson;
	/**
	 * 身份证号
	 */
	private String certNo;
	
	/**
	 * 手机号
	 */
	private String mobileNo;
	
	/**
	 * 机构号
	 */
	private String orgCode;
	
	
	/**
	 * 所属邮政机构
	 */
	
	private String orgAddress;
	/**
	 * 审核结果状态  1 全部通过 0 没有全部通过 2 审核拒绝
	 */
	private Integer allPass;
	/**
	 * 审核拒绝原因汇总
	 */
	private String refusalReason;
	
	/**
	 * 是否本区县   60 是 61 否
	 */
	private Integer acountCityCoun;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 负债系数
	 */
	private String liabCoeff;
	
	/**
	 * 近3个月在三个以上的外部机构（非人行征信机构）申贷 60 是 61 否决
	 */
	private Integer thMonExterLoan;
	
	/**
	 * (welab)姓名和身份证是否一致
	 */
	private  String     isNameIdMatch;
	/**
	 * (welab)身份证照片和生活照识别
	 */
	private  String     isFaceDetectionMatch;
	/**
	 * (welab)身份证有效期（数字）
	 */
	private  String     idPeriod;
	/**
	 * (welab)身份证是否在有效期内（状态）
	 */
	private  String     isIdWithinPeriod;
	
	/**
	 * (welab)风险 状态提示语
	 */
	private  String riskStatusRemark;
	/**
	 * (welab)风险状态短信条数
	 */
	private  Long     smsHitRiskKeywordCnt;
	/**
	 * (welab)风险状态短信内容（需显示对方号码，对方姓名，收信or发信，短信全文）
	 */
	private  String     smsHitRiskKeywordItems;
	/**
	 * (welab)是否存在风险状态
	 */
	private  String     isInRiskStatus;
	/**
	 * (welab)近3个月有三个以上的申贷记录
	 */
	private  Long     loanCnt;
	/**
	 * (welab)是否存在外部黑名单
	 */
	private  String     isInBlacklist;
	/**
	 * (welab)定位信息与邮掌柜店铺位置一致
	 */
	private  String     isGpsMatchShopLocation;
	/**
	 * (welab)掌柜店铺位置
	 */
	private  String     shopLocation;
	/**
	 * (welab)掌柜定位位置
	 */
	private  String     shopGPS;
	/**
	 * (welab)非本人手机
	 */
	private  Long     isNotSelfPhone;
	/**
	 * (welab)非本人手机提示语
	 */
	private  String     isNotSelfPhoneMsg;
	/**
	 * (welab)营业执照审核
	 */
	private  String     isBusinessLicenseVerified;
	/**
	 * (welab)企业名称(工商信息)
	 */
	private  String     enterpriseName;
	/**
	 * (welab)经营者(工商信息)
	 */
	private  String     businessMan;
	/**
	 * (welab)经营场所(工商信息)
	 */
	private  String     businessPlace;
	/**
	 * (welab)成立日期(工商信息)
	 */
	private  String     establishedDate;
	/**
	 * (welab)近一年有无行政处罚
	 */
	private  String     hasPunishment;
	/**
	 * (welab)近一年有无经营异常
	 */
	private  String     hasBusinessAbnormal;
	/**
	 * (welab)房产证明是否真实
	 */
	private  String     isHouseReal;
	/**
	 * (welab)户口是否本区（县）
	 */
	private  String     isResidenceLocal;
	/**
	 * (welab)客户姓名比对
	 */
	private  String     isUserNameVerified;
	/**
	 * (welab)店铺名称比对
	 */
	private  String     isShopNameVerified;
	/**
	 * (welab)店铺地址比对
	 */
	private  String     isShopPlaceVerified;
	/**
	 * (welab)网查风险
	 */
	private  String     hasNetRisk;
	/**
	 * (welab)人法风险
	 */
	private  String     hasLawRisk;
	/**
	 * (welab)sdk风险
	 */
	private  String     hasSdkRisk;
	/**
	 * (welab)失信网风险
	 */
	private  String     hasShiXinRisk;
	/**
	 * (welab)营业执照审核备注
	 */
	private  String     businLiVerRemark;
	/**
	 * (welab)近一年有无行政处罚备注
	 */
	private  String     punishmentRemark;
	/**
	 * (welab)近一年有无经营异常备注
	 */
	private  String     businessAbnormalRemark;
	/**
	 * (welab)房产证明是否真实备注
	 */
	private  String     houseRealRemark;
	/**
	 * (welab)户口是否本区（县）备注
	 */
	private  String     residenceLocalRemark;
	/**
	 * (welab)客户姓名比对备注
	 */
	private  String     userNameVerifiedRemark;
	/**
	 * (welab)店铺名称比对备注
	 */
	private  String     shopNameVerifiedRemark;
	/**
	 * (welab)店铺地址比对备注
	 */
	private  String     shopPlaceVerifiedRemark;
	/**
	 * (welab)网查风险备注
	 */
	private  String     netRiskRemark;
	/**
	 * (welab)人法风险备注
	 */
	private  String     lawRiskRemark;
	/**
	 * (welab)sdk风险备注
	 */
	private  String     sdkRiskRemark;
	/**
	 * (welab)失信网风险备注
	 */
	private  String     shiXinRiskRemark;
	/**
	 * (welab)额度基数
	 */
	private  BigDecimal     quotaBase;
	/**
	 * (welab)婚姻状况选项
	 */
	private  String     marriedStatus;
	/**
	 * (welab)经营年限选项
	 */
	private  String     yearsOfBusiness;
	/**
	 * (welab)物业情况选项
	 */
	private  String     shopStatus;
	/**
	 * (welab)资产情况选项
	 */
	private  String     assetsStatus;
	/**
	 * (welab)负债情况选项
	 */
	private  String     debtStatus;
	/**
	 * (welab)年龄系数值
	 */
	private  BigDecimal     ageFactor;
	/**
	 * (welab)婚姻状况系数值
	 */
	private  BigDecimal     marryFactor;
	/**
	 * (welab)经营年限系数值
	 */
	private  BigDecimal     businessYearsFactor;
	/**
	 * (welab)物业系数值
	 */
	private  BigDecimal     shopFactor;
	/**
	 * (welab)户口系数值
	 */
	private  BigDecimal     residenceFactor;
	/**
	 * (welab)客户基本情况系数
	 */
	private  BigDecimal     baseFactor;
	/**
	 * (welab)客户经营状况系数
	 */
	private  BigDecimal     businessFactor;
	/**
	 * (welab)经营结构系数
	 */
	private  BigDecimal     businessStructureFactor;
	/**
	 * (welab)进销存系数
	 */
	private  BigDecimal     jxcFactor;
	/**
	 * (welab)会员系数
	 */
	private  BigDecimal     memberFactor;
	/**
	 * (welab)资产情况系数
	 */
	private  BigDecimal     assetsFactor;
	
	/**
	 * (welab)外部负债系数
	 */
	private BigDecimal  indebtedAssetsFactor;
	/**
	 * (welab)试算额度
	 */
	private  BigDecimal     trialQuota;
	/**
	 * (welab)额度系数
	 */
	private  BigDecimal     QuotaFactor;
	/**
	 * (welab)建议额度按1000元取整）
	 */
	private  BigDecimal     suggestionFactor;
	/**
	 * (welab)初审人
	 */
	private  String     initApprover;
	/**
	 * (welab)终审人
	 */
	private  String     finalApprover;
	/**
	 * (welab)初审结果
	 */
	private  String     initApprovalResult;
	/**
	 * (welab)初审代码1
	 */
	private  String     initApprovalCode1;
	/**
	 * (welab)初审代码2
	 */
	private  String     initApprovalCode2;
	/**
	 * (welab)初审代码3
	 */
	private  String     initApprovalCode3;
	/**
	 * (welab)初审原因
	 */
	private  String     initApprovalReason;
	/**
	 * (welab)终审结果
	 */
	private  String     finalApprovalResult;
	/**
	 * (welab)终审代码1
	 */
	private  String     finalApprovalCode1;
	/**
	 * (welab)终审代码2
	 */
	private  String     finalApprovalCode2;
	/**
	 * (welab)终审代码3
	 */
	private  String     finalApprovalCode3;
	/**
	 * (welab)终审原因
	 */
	private  String     finalApprovalReason;
	/**
	 * (welab)其他证明文件（图片）
	 */
	private  String     otherRelevantDocuments;
	/**
	 * (welab)当前贷款状态值
	 */
	private  String     loanApplicationStatus;
	/**
	 * (welab)初审文本1
	 */
	private String  initApprovalRemark1;
	/**
	 * (welab)初审文本2
	 */
	private String  initApprovalRemark2;
	/**
	 * (welab)初审文本3
	 */
	private String  initApprovalRemark3;
	/**
	 * (welab)终审文本1
	 */
	private String  finalApprovalRemark1;
	/**
	 * (welab)终审文本2
	 */
	private String  finalApprovalRemark2;
	/**
	 * (welab)终审文本3
	 */
	private String  finalApprovalRemark3;
	
	/**
	 * (welab)户口是否本区县选项
	 */
	private String residenceIsDistrictsre;
	/**
	 * 平效测算结果
	 * @return
	 */
	private String revernueRegressResult;
	
	public Integer getAcountCityCoun() {
		return acountCityCoun;
	}
	public void setAcountCityCoun(Integer acountCityCoun) {
		this.acountCityCoun = acountCityCoun;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLiabCoeff() {
		return liabCoeff;
	}
	public void setLiabCoeff(String liabCoeff) {
		this.liabCoeff = liabCoeff;
	}
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
	public String getOutSysCode() {
		return outSysCode;
	}
	public void setOutSysCode(String outSysCode) {
		this.outSysCode = outSysCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getApplyPositionSP() {
		return applyPositionSP;
	}
	public void setApplyPositionSP(Integer applyPositionSP) {
		this.applyPositionSP = applyPositionSP;
	}
	public Integer getNameCertnoPhoto() {
		return nameCertnoPhoto;
	}
	public void setNameCertnoPhoto(Integer nameCertnoPhoto) {
		this.nameCertnoPhoto = nameCertnoPhoto;
	}
	public Integer getNameStAdBuiness() {
		return nameStAdBuiness;
	}
	public void setNameStAdBuiness(Integer nameStAdBuiness) {
		this.nameStAdBuiness = nameStAdBuiness;
	}
//	public Integer getFaceRecognition() {
//		return faceRecognition;
//	}
//	public void setFaceRecognition(Integer faceRecognition) {
//		this.faceRecognition = faceRecognition;
//	}
	public Integer getBusinessLiIn() {
		return businessLiIn;
	}
	public void setBusinessLiIn(Integer businessLiIn) {
		this.businessLiIn = businessLiIn;
	}
	public Integer getHouseOwnership() {
		return houseOwnership;
	}
	public void setHouseOwnership(Integer houseOwnership) {
		this.houseOwnership = houseOwnership;
	}
	public String getHouseOwnDesc() {
		return houseOwnDesc;
	}
	public void setHouseOwnDesc(String houseOwnDesc) {
		this.houseOwnDesc = houseOwnDesc;
	}
	public Integer getAdmPenalty() {
		return admPenalty;
	}
	public void setAdmPenalty(Integer admPenalty) {
		this.admPenalty = admPenalty;
	}
	public String getAdmPenaltyDesc() {
		return admPenaltyDesc;
	}
	public void setAdmPenaltyDesc(String admPenaltyDesc) {
		this.admPenaltyDesc = admPenaltyDesc;
	}
	public Integer getOperatingAnomalies() {
		return operatingAnomalies;
	}
	public void setOperatingAnomalies(Integer operatingAnomalies) {
		this.operatingAnomalies = operatingAnomalies;
	}
	public String getOperatingAnomaliesDesc() {
		return operatingAnomaliesDesc;
	}
	public void setOperatingAnomaliesDesc(String operatingAnomaliesDesc) {
		this.operatingAnomaliesDesc = operatingAnomaliesDesc;
	}
	public Integer getRiskStatus() {
		return riskStatus;
	}
	public void setRiskStatus(Integer riskStatus) {
		this.riskStatus = riskStatus;
	}
	public String getRiskSDesc() {
		return riskSDesc;
	}
	public void setRiskSDesc(String riskSDesc) {
		this.riskSDesc = riskSDesc;
	}
	public Integer getExternalBL() {
		return externalBL;
	}
	public void setExternalBL(Integer externalBL) {
		this.externalBL = externalBL;
	}

	public String getBlackListDesc() {
		return blackListDesc;
	}
	public void setBlackListDesc(String blackListDesc) {
		this.blackListDesc = blackListDesc;
	}
	public Integer getLoanNum() {
		return loanNum;
	}
	public void setLoanNum(Integer loanNum) {
		this.loanNum = loanNum;
	}
//	public Integer getPostalSavingsBL() {
//		return postalSavingsBL;
//	}
//	public void setPostalSavingsBL(Integer postalSavingsBL) {
//		this.postalSavingsBL = postalSavingsBL;
//	}
	public Integer getRealNameAuth() {
		return realNameAuth;
	}
	public void setRealNameAuth(Integer realNameAuth) {
		this.realNameAuth = realNameAuth;
	}
	public Integer getNameCertImg() {
		return nameCertImg;
	}
	public void setNameCertImg(Integer nameCertImg) {
		this.nameCertImg = nameCertImg;
	}
	
	public Integer getCertPositiveImg() {
		return certPositiveImg;
	}
	public void setCertPositiveImg(Integer certPositiveImg) {
		this.certPositiveImg = certPositiveImg;
	}
	public Integer getCertNegativeImg() {
		return certNegativeImg;
	}
	public void setCertNegativeImg(Integer certNegativeImg) {
		this.certNegativeImg = certNegativeImg;
	}
	public String getValiadIdCert() {
		return valiadIdCert;
	}
	public void setValiadIdCert(String valiadIdCert) {
		this.valiadIdCert = valiadIdCert;
	}
	public Integer getCertHoldImg() {
		return certHoldImg;
	}
	public void setCertHoldImg(Integer certHoldImg) {
		this.certHoldImg = certHoldImg;
	}
	public Integer getBusLiecnceImg() {
		return busLiecnceImg;
	}
	public void setBusLiecnceImg(Integer busLiecnceImg) {
		this.busLiecnceImg = busLiecnceImg;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public Integer getStoreNameAgree() {
		return storeNameAgree;
	}
	public void setStoreNameAgree(Integer storeNameAgree) {
		this.storeNameAgree = storeNameAgree;
	}
	public String getStoreOperator() {
		return storeOperator;
	}
	public void setStoreOperator(String storeOperator) {
		this.storeOperator = storeOperator;
	}
	public Integer getStoreOperatorAgree() {
		return storeOperatorAgree;
	}
	public void setStoreOperatorAgree(Integer storeOperatorAgree) {
		this.storeOperatorAgree = storeOperatorAgree;
	}
	public String getBusinessPremisese() {
		return businessPremisese;
	}
	public void setBusinessPremisese(String businessPremisese) {
		this.businessPremisese = businessPremisese;
	}
	public Integer getBusinessPremiseseAgree() {
		return businessPremiseseAgree;
	}
	public void setBusinessPremiseseAgree(Integer businessPremiseseAgree) {
		this.businessPremiseseAgree = businessPremiseseAgree;
	}
	public String getEstablishmentTime() {
		return establishmentTime;
	}
	public void setEstablishmentTime(String establishmentTime) {
		this.establishmentTime = establishmentTime;
	}
	public Integer getStoreIMgeAgree() {
		return storeIMgeAgree;
	}
	public void setStoreIMgeAgree(Integer storeIMgeAgree) {
		this.storeIMgeAgree = storeIMgeAgree;
	}
	public Integer getStoreInnerImgeAgree() {
		return storeInnerImgeAgree;
	}
	public void setStoreInnerImgeAgree(Integer storeInnerImgeAgree) {
		this.storeInnerImgeAgree = storeInnerImgeAgree;
	}
//	public Integer getCreditInveOver() {
//		return creditInveOver;
//	}
//	public void setCreditInveOver(Integer creditInveOver) {
//		this.creditInveOver = creditInveOver;
//	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdatePerson() {
		return updatePerson;
	}
	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgAddress() {
		return orgAddress;
	}
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	public Integer getAllPass() {
		return allPass;
	}
	public void setAllPass(Integer allPass) {
		this.allPass = allPass;
	}
	public String getRefusalReason() {
		return refusalReason;
	}
	public void setRefusalReason(String refusalReason) {
		this.refusalReason = refusalReason;
	}
	public Integer getThMonExterLoan() {
		return thMonExterLoan;
	}
	public void setThMonExterLoan(Integer thMonExterLoan) {
		this.thMonExterLoan = thMonExterLoan;
	}
	public String getIsNameIdMatch() {
		return isNameIdMatch;
	}
	public void setIsNameIdMatch(String isNameIdMatch) {
		this.isNameIdMatch = isNameIdMatch;
	}
	public String getIsFaceDetectionMatch() {
		return isFaceDetectionMatch;
	}
	public void setIsFaceDetectionMatch(String isFaceDetectionMatch) {
		this.isFaceDetectionMatch = isFaceDetectionMatch;
	}
	public String getIdPeriod() {
		return idPeriod;
	}
	public void setIdPeriod(String idPeriod) {
		this.idPeriod = idPeriod;
	}
	public String getIsIdWithinPeriod() {
		return isIdWithinPeriod;
	}
	public void setIsIdWithinPeriod(String isIdWithinPeriod) {
		this.isIdWithinPeriod = isIdWithinPeriod;
	}
	
	public String getRiskStatusRemark() {
		return riskStatusRemark;
	}
	public void setRiskStatusRemark(String riskStatusRemark) {
		this.riskStatusRemark = riskStatusRemark;
	}
	public Long getSmsHitRiskKeywordCnt() {
		return smsHitRiskKeywordCnt;
	}
	public void setSmsHitRiskKeywordCnt(Long smsHitRiskKeywordCnt) {
		this.smsHitRiskKeywordCnt = smsHitRiskKeywordCnt;
	}
	public String getSmsHitRiskKeywordItems() {
		return smsHitRiskKeywordItems;
	}
	public void setSmsHitRiskKeywordItems(String smsHitRiskKeywordItems) {
		this.smsHitRiskKeywordItems = smsHitRiskKeywordItems;
	}
	public String getIsInRiskStatus() {
		return isInRiskStatus;
	}
	public void setIsInRiskStatus(String isInRiskStatus) {
		this.isInRiskStatus = isInRiskStatus;
	}
	public Long getLoanCnt() {
		return loanCnt;
	}
	public void setLoanCnt(Long loanCnt) {
		this.loanCnt = loanCnt;
	}
	public String getIsInBlacklist() {
		return isInBlacklist;
	}
	public void setIsInBlacklist(String isInBlacklist) {
		this.isInBlacklist = isInBlacklist;
	}
	public String getIsGpsMatchShopLocation() {
		return isGpsMatchShopLocation;
	}
	public void setIsGpsMatchShopLocation(String isGpsMatchShopLocation) {
		this.isGpsMatchShopLocation = isGpsMatchShopLocation;
	}
	public String getShopLocation() {
		return shopLocation;
	}
	public void setShopLocation(String shopLocation) {
		this.shopLocation = shopLocation;
	}
	public String getShopGPS() {
		return shopGPS;
	}
	public void setShopGPS(String shopGPS) {
		this.shopGPS = shopGPS;
	}
	public Long getIsNotSelfPhone() {
		return isNotSelfPhone;
	}
	public void setIsNotSelfPhone(Long isNotSelfPhone) {
		this.isNotSelfPhone = isNotSelfPhone;
	}
	public String getIsNotSelfPhoneMsg() {
		return isNotSelfPhoneMsg;
	}
	public void setIsNotSelfPhoneMsg(String isNotSelfPhoneMsg) {
		this.isNotSelfPhoneMsg = isNotSelfPhoneMsg;
	}
	public String getIsBusinessLicenseVerified() {
		return isBusinessLicenseVerified;
	}
	public void setIsBusinessLicenseVerified(String isBusinessLicenseVerified) {
		this.isBusinessLicenseVerified = isBusinessLicenseVerified;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getBusinessMan() {
		return businessMan;
	}
	public void setBusinessMan(String businessMan) {
		this.businessMan = businessMan;
	}
	public String getBusinessPlace() {
		return businessPlace;
	}
	public void setBusinessPlace(String businessPlace) {
		this.businessPlace = businessPlace;
	}
	public String getEstablishedDate() {
		return establishedDate;
	}
	public void setEstablishedDate(String establishedDate) {
		this.establishedDate = establishedDate;
	}
	public String getHasPunishment() {
		return hasPunishment;
	}
	public void setHasPunishment(String hasPunishment) {
		this.hasPunishment = hasPunishment;
	}
	public String getHasBusinessAbnormal() {
		return hasBusinessAbnormal;
	}
	public void setHasBusinessAbnormal(String hasBusinessAbnormal) {
		this.hasBusinessAbnormal = hasBusinessAbnormal;
	}
	public String getIsHouseReal() {
		return isHouseReal;
	}
	public void setIsHouseReal(String isHouseReal) {
		this.isHouseReal = isHouseReal;
	}
	public String getIsResidenceLocal() {
		return isResidenceLocal;
	}
	public void setIsResidenceLocal(String isResidenceLocal) {
		this.isResidenceLocal = isResidenceLocal;
	}
	public String getIsUserNameVerified() {
		return isUserNameVerified;
	}
	public void setIsUserNameVerified(String isUserNameVerified) {
		this.isUserNameVerified = isUserNameVerified;
	}
	public String getIsShopNameVerified() {
		return isShopNameVerified;
	}
	public void setIsShopNameVerified(String isShopNameVerified) {
		this.isShopNameVerified = isShopNameVerified;
	}
	public String getIsShopPlaceVerified() {
		return isShopPlaceVerified;
	}
	public void setIsShopPlaceVerified(String isShopPlaceVerified) {
		this.isShopPlaceVerified = isShopPlaceVerified;
	}
	public String getHasNetRisk() {
		return hasNetRisk;
	}
	public void setHasNetRisk(String hasNetRisk) {
		this.hasNetRisk = hasNetRisk;
	}
	public String getHasLawRisk() {
		return hasLawRisk;
	}
	public void setHasLawRisk(String hasLawRisk) {
		this.hasLawRisk = hasLawRisk;
	}
	public String getHasSdkRisk() {
		return hasSdkRisk;
	}
	public void setHasSdkRisk(String hasSdkRisk) {
		this.hasSdkRisk = hasSdkRisk;
	}
	public String getHasShiXinRisk() {
		return hasShiXinRisk;
	}
	public void setHasShiXinRisk(String hasShiXinRisk) {
		this.hasShiXinRisk = hasShiXinRisk;
	}
	public String getBusinLiVerRemark() {
		return businLiVerRemark;
	}
	public void setBusinLiVerRemark(String businLiVerRemark) {
		this.businLiVerRemark = businLiVerRemark;
	}
	public String getPunishmentRemark() {
		return punishmentRemark;
	}
	public void setPunishmentRemark(String punishmentRemark) {
		this.punishmentRemark = punishmentRemark;
	}
	public String getBusinessAbnormalRemark() {
		return businessAbnormalRemark;
	}
	public void setBusinessAbnormalRemark(String businessAbnormalRemark) {
		this.businessAbnormalRemark = businessAbnormalRemark;
	}
	public String getHouseRealRemark() {
		return houseRealRemark;
	}
	public void setHouseRealRemark(String houseRealRemark) {
		this.houseRealRemark = houseRealRemark;
	}
	public String getResidenceLocalRemark() {
		return residenceLocalRemark;
	}
	public void setResidenceLocalRemark(String residenceLocalRemark) {
		this.residenceLocalRemark = residenceLocalRemark;
	}
	public String getUserNameVerifiedRemark() {
		return userNameVerifiedRemark;
	}
	public void setUserNameVerifiedRemark(String userNameVerifiedRemark) {
		this.userNameVerifiedRemark = userNameVerifiedRemark;
	}
	public String getShopNameVerifiedRemark() {
		return shopNameVerifiedRemark;
	}
	public void setShopNameVerifiedRemark(String shopNameVerifiedRemark) {
		this.shopNameVerifiedRemark = shopNameVerifiedRemark;
	}
	public String getShopPlaceVerifiedRemark() {
		return shopPlaceVerifiedRemark;
	}
	public void setShopPlaceVerifiedRemark(String shopPlaceVerifiedRemark) {
		this.shopPlaceVerifiedRemark = shopPlaceVerifiedRemark;
	}
	public String getNetRiskRemark() {
		return netRiskRemark;
	}
	public void setNetRiskRemark(String netRiskRemark) {
		this.netRiskRemark = netRiskRemark;
	}
	public String getLawRiskRemark() {
		return lawRiskRemark;
	}
	public void setLawRiskRemark(String lawRiskRemark) {
		this.lawRiskRemark = lawRiskRemark;
	}
	public String getSdkRiskRemark() {
		return sdkRiskRemark;
	}
	public void setSdkRiskRemark(String sdkRiskRemark) {
		this.sdkRiskRemark = sdkRiskRemark;
	}
	public String getShiXinRiskRemark() {
		return shiXinRiskRemark;
	}
	public void setShiXinRiskRemark(String shiXinRiskRemark) {
		this.shiXinRiskRemark = shiXinRiskRemark;
	}
	public BigDecimal getQuotaBase() {
		return quotaBase;
	}
	public void setQuotaBase(BigDecimal quotaBase) {
		this.quotaBase = quotaBase;
	}
	public String getMarriedStatus() {
		return marriedStatus;
	}
	public void setMarriedStatus(String marriedStatus) {
		this.marriedStatus = marriedStatus;
	}
	public String getYearsOfBusiness() {
		return yearsOfBusiness;
	}
	public void setYearsOfBusiness(String yearsOfBusiness) {
		this.yearsOfBusiness = yearsOfBusiness;
	}
	public String getShopStatus() {
		return shopStatus;
	}
	public void setShopStatus(String shopStatus) {
		this.shopStatus = shopStatus;
	}
	public String getAssetsStatus() {
		return assetsStatus;
	}
	public void setAssetsStatus(String assetsStatus) {
		this.assetsStatus = assetsStatus;
	}
	public String getDebtStatus() {
		return debtStatus;
	}
	public void setDebtStatus(String debtStatus) {
		this.debtStatus = debtStatus;
	}
	public BigDecimal getAgeFactor() {
		return ageFactor;
	}
	public void setAgeFactor(BigDecimal ageFactor) {
		this.ageFactor = ageFactor;
	}
	public BigDecimal getMarryFactor() {
		return marryFactor;
	}
	public void setMarryFactor(BigDecimal marryFactor) {
		this.marryFactor = marryFactor;
	}
	public BigDecimal getBusinessYearsFactor() {
		return businessYearsFactor;
	}
	public void setBusinessYearsFactor(BigDecimal businessYearsFactor) {
		this.businessYearsFactor = businessYearsFactor;
	}
	public BigDecimal getShopFactor() {
		return shopFactor;
	}
	public void setShopFactor(BigDecimal shopFactor) {
		this.shopFactor = shopFactor;
	}
	public BigDecimal getResidenceFactor() {
		return residenceFactor;
	}
	public void setResidenceFactor(BigDecimal residenceFactor) {
		this.residenceFactor = residenceFactor;
	}
	public BigDecimal getBaseFactor() {
		return baseFactor;
	}
	public void setBaseFactor(BigDecimal baseFactor) {
		this.baseFactor = baseFactor;
	}
	public BigDecimal getBusinessFactor() {
		return businessFactor;
	}
	public void setBusinessFactor(BigDecimal businessFactor) {
		this.businessFactor = businessFactor;
	}
	public BigDecimal getBusinessStructureFactor() {
		return businessStructureFactor;
	}
	public void setBusinessStructureFactor(BigDecimal businessStructureFactor) {
		this.businessStructureFactor = businessStructureFactor;
	}
	public BigDecimal getJxcFactor() {
		return jxcFactor;
	}
	public void setJxcFactor(BigDecimal jxcFactor) {
		this.jxcFactor = jxcFactor;
	}
	public BigDecimal getMemberFactor() {
		return memberFactor;
	}
	public void setMemberFactor(BigDecimal memberFactor) {
		this.memberFactor = memberFactor;
	}
	public BigDecimal getAssetsFactor() {
		return assetsFactor;
	}
	public void setAssetsFactor(BigDecimal assetsFactor) {
		this.assetsFactor = assetsFactor;
	}
	
	public BigDecimal getIndebtedAssetsFactor() {
		return indebtedAssetsFactor;
	}
	public void setIndebtedAssetsFactor(BigDecimal indebtedAssetsFactor) {
		this.indebtedAssetsFactor = indebtedAssetsFactor;
	}
	public BigDecimal getTrialQuota() {
		return trialQuota;
	}
	public void setTrialQuota(BigDecimal trialQuota) {
		this.trialQuota = trialQuota;
	}
	public BigDecimal getQuotaFactor() {
		return QuotaFactor;
	}
	public void setQuotaFactor(BigDecimal quotaFactor) {
		QuotaFactor = quotaFactor;
	}
	public BigDecimal getSuggestionFactor() {
		return suggestionFactor;
	}
	public void setSuggestionFactor(BigDecimal suggestionFactor) {
		this.suggestionFactor = suggestionFactor;
	}
	public String getInitApprover() {
		return initApprover;
	}
	public void setInitApprover(String initApprover) {
		this.initApprover = initApprover;
	}
	public String getFinalApprover() {
		return finalApprover;
	}
	public void setFinalApprover(String finalApprover) {
		this.finalApprover = finalApprover;
	}
	public String getInitApprovalResult() {
		return initApprovalResult;
	}
	public void setInitApprovalResult(String initApprovalResult) {
		this.initApprovalResult = initApprovalResult;
	}
	public String getInitApprovalCode1() {
		return initApprovalCode1;
	}
	public void setInitApprovalCode1(String initApprovalCode1) {
		this.initApprovalCode1 = initApprovalCode1;
	}
	public String getInitApprovalCode2() {
		return initApprovalCode2;
	}
	public void setInitApprovalCode2(String initApprovalCode2) {
		this.initApprovalCode2 = initApprovalCode2;
	}
	public String getInitApprovalCode3() {
		return initApprovalCode3;
	}
	public void setInitApprovalCode3(String initApprovalCode3) {
		this.initApprovalCode3 = initApprovalCode3;
	}
	public String getInitApprovalReason() {
		return initApprovalReason;
	}
	public void setInitApprovalReason(String initApprovalReason) {
		this.initApprovalReason = initApprovalReason;
	}
	public String getFinalApprovalResult() {
		return finalApprovalResult;
	}
	public void setFinalApprovalResult(String finalApprovalResult) {
		this.finalApprovalResult = finalApprovalResult;
	}
	public String getFinalApprovalCode1() {
		return finalApprovalCode1;
	}
	public void setFinalApprovalCode1(String finalApprovalCode1) {
		this.finalApprovalCode1 = finalApprovalCode1;
	}
	public String getFinalApprovalCode2() {
		return finalApprovalCode2;
	}
	public void setFinalApprovalCode2(String finalApprovalCode2) {
		this.finalApprovalCode2 = finalApprovalCode2;
	}
	public String getFinalApprovalCode3() {
		return finalApprovalCode3;
	}
	public void setFinalApprovalCode3(String finalApprovalCode3) {
		this.finalApprovalCode3 = finalApprovalCode3;
	}
	public String getFinalApprovalReason() {
		return finalApprovalReason;
	}
	public void setFinalApprovalReason(String finalApprovalReason) {
		this.finalApprovalReason = finalApprovalReason;
	}
	public String getOtherRelevantDocuments() {
		return otherRelevantDocuments;
	}
	public void setOtherRelevantDocuments(String otherRelevantDocuments) {
		this.otherRelevantDocuments = otherRelevantDocuments;
	}
	public String getLoanApplicationStatus() {
		return loanApplicationStatus;
	}
	public void setLoanApplicationStatus(String loanApplicationStatus) {
		this.loanApplicationStatus = loanApplicationStatus;
	}
	public String getInitApprovalRemark1() {
		return initApprovalRemark1;
	}
	public void setInitApprovalRemark1(String initApprovalRemark1) {
		this.initApprovalRemark1 = initApprovalRemark1;
	}
	public String getInitApprovalRemark2() {
		return initApprovalRemark2;
	}
	public void setInitApprovalRemark2(String initApprovalRemark2) {
		this.initApprovalRemark2 = initApprovalRemark2;
	}
	public String getInitApprovalRemark3() {
		return initApprovalRemark3;
	}
	public void setInitApprovalRemark3(String initApprovalRemark3) {
		this.initApprovalRemark3 = initApprovalRemark3;
	}
	public String getFinalApprovalRemark1() {
		return finalApprovalRemark1;
	}
	public void setFinalApprovalRemark1(String finalApprovalRemark1) {
		this.finalApprovalRemark1 = finalApprovalRemark1;
	}
	public String getFinalApprovalRemark2() {
		return finalApprovalRemark2;
	}
	public void setFinalApprovalRemark2(String finalApprovalRemark2) {
		this.finalApprovalRemark2 = finalApprovalRemark2;
	}
	public String getFinalApprovalRemark3() {
		return finalApprovalRemark3;
	}
	public void setFinalApprovalRemark3(String finalApprovalRemark3) {
		this.finalApprovalRemark3 = finalApprovalRemark3;
	}
	public String getResidenceIsDistrictsre() {
		return residenceIsDistrictsre;
	}
	public void setResidenceIsDistrictsre(String residenceIsDistrictsre) {
		this.residenceIsDistrictsre = residenceIsDistrictsre;
	}
	public String getRevernueRegressResult() {
		return revernueRegressResult;
	}
	public void setRevernueRegressResult(String revernueRegressResult) {
		this.revernueRegressResult = revernueRegressResult;
	}
	
	

}
