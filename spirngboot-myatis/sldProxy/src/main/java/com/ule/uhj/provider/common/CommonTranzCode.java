package com.ule.uhj.provider.common;

import java.util.HashSet;
import java.util.Set;

import com.ule.uhj.provider.pengyuan.service.PengyuanQueryService;
import com.ule.uhj.provider.qhcs.QianHaiService;
import com.ule.uhj.provider.yitu.service.AliQueryService;
import com.ule.uhj.provider.yitu.service.BaiDuAiService;
import com.ule.uhj.provider.yitu.service.BaiDuFaceService;
import com.ule.uhj.provider.yitu.service.BaiDuMapService;
import com.ule.uhj.provider.yitu.service.BaiRongService;
import com.ule.uhj.provider.yitu.service.BrQueryService;
import com.ule.uhj.provider.yitu.service.EnterpriseInformation;
import com.ule.uhj.provider.yitu.service.EnterpriseSimpleInfo;
import com.ule.uhj.provider.yitu.service.FaceDetection;
import com.ule.uhj.provider.yitu.service.CustomerAssetService;
import com.ule.uhj.provider.yitu.service.ImagePackageUrl;
import com.ule.uhj.provider.yitu.service.JuXinLiService;
import com.ule.uhj.provider.yitu.service.OcrPersonIdentify;
import com.ule.uhj.provider.yitu.service.OcrPersonIdentifyUrl;
import com.ule.uhj.provider.yitu.service.PersonIdentify;
import com.ule.uhj.provider.yitu.service.SampleCodeV1PairVerify;
import com.ule.uhj.provider.yitu.service.SampleCodeV2PairVerify;
import com.ule.uhj.provider.yitu.service.SampleCodeV3PairVerify;
import com.ule.uhj.provider.yitu.service.ShangHuService;
import com.ule.uhj.provider.yitu.service.StoreInnerAnalysis;
import com.ule.uhj.provider.yitu.service.StoreOutsideAnalysis;
import com.ule.uhj.provider.yitu.service.SuanhuaService;
import com.ule.uhj.provider.yitu.service.TongDunService;
import com.ule.uhj.provider.yitu.service.UploadFileServer;

/**
 * @author zhangyaou
 * tranzCode 编码规范
 * 4位长度,前两位为提供方编码,后两位为提供方内顺序编码
 * 所有接口需要保存到接口表的都必须传userOnlyId
 */
public enum CommonTranzCode {

	YITU_OCR_PERSON_IDENTIFY(3101, new String[]{"imageContent","type"}, "图片内容-亿图身份证OCR正反面-已接基础服务组", OcrPersonIdentify.class, "ocrPersonIdentifyImage"),
	YITU_SAMPLE_PAIR_VERIFY0(3100, new String[]{"name", "citizen_id", "query_image_package"}, "亿图姓名、身份证号与人脸大礼包相似度比较", SampleCodeV1PairVerify.class, "PairVerifyForName"),
	YITU_SAMPLE_PAIR_VERIFY2(3103, new String[]{"idCardImgUrl", "name", "citizen_id"}, "亿图姓名和身份证号与身份证图片(或者人脸)校验是否一致，也可校验姓名与身份证号是否一致-已接基础服务部", SampleCodeV2PairVerify.class, "PairVerify"),
	YITU_SAMPLE_PAIR_VERIFY7(3107, new String[]{"appSelffaceImgUrl", "appStoreinnerImgUrl"}, "图片地址-人脸图与合影图校验相识度-已接基础服务部", SampleCodeV3PairVerify.class, "PairVerifyImgOrImg3"),
	YITU_SAMPLE_PAIR_VERIFY8(3108, new String[]{"idCardImgUrl"}, "图片地址-亿图身份证OCR正面-已接基础服务组", OcrPersonIdentifyUrl.class, "ocrPersonIdentifyImage"),
	YITU_SAMPLE_PAIR_VERIFY10(3110, new String[]{"imagePackage"}, "大礼包信息解密，返回图片列表-已接基础服务组", ImagePackageUrl.class, "getImagePackageUrl"),
	
	YITU_SAMPLE_PAIR_VERIFY9(3109, new String[]{"name", "citizen_id", "image_content"}, "亿图姓名、身份证号与人脸图片相似度比较（已废弃）", SampleCodeV2PairVerify.class, "PairVerifyForNameCert"),
	YITU_SAMPLE_PAIR_VERIFY3(3105, new String[]{"idCardImgUrl", "verifyImg_content"}, "亿图图片与身份证图片校验真实有效（已废弃）", SampleCodeV3PairVerify.class, "PairVerifyImgOrImg"),
	YITU_SAMPLE_PAIR_VERIFY(3102, new String[]{"idCardImgUrl", "query_image_package"}, "亿图两张照片相似度比较-新接口（已废弃）", SampleCodeV1PairVerify.class, "PairVerify"),
	YITU_SAMPLE_PAIR_VERIFY4(3106, new String[]{"appSelffaceImgUrl", "verifyImg_content"}, "图与图校验相识度（已废弃）", SampleCodeV3PairVerify.class, "PairVerifyImgOrImg2"),
	YITU_PERSON_IDENTIFY(3104, new String[]{"name", "citizen_id"}, "联网核查姓名和身份证是否匹配, 匹配成功时获取公安部联网水印照（已废弃）", PersonIdentify.class, "getIdentifyImage"),
	
	Upload_File_Server(4100, new String[]{"base64String", "userOnlyId", "type"}, "上传文件", UploadFileServer.class, "uploadFileBase64String"),
	
	Store_Inner_Analysis(4101, new String[]{"url"}, "微软的店铺内部分析（已废弃）", StoreInnerAnalysis.class, "getStoreInnerAnalysis"),
	Store_Outside_Analysis(4102, new String[]{"url"}, "阿里的店招OCR识别接口（已废弃）", StoreOutsideAnalysis.class, "aliOcrSignatureByUrl"),
	
	Enterprise_Information(4103, new String[]{"keyWord"}, "企查查企业关键字精确获取详细信息-已接基础服务组", EnterpriseInformation.class, "getDetailsByName"),
	Enterprise_Simple_Info(4104, new String[]{"keyWord"}, "企业关键字模糊查询-精简版-已接基础服务组", EnterpriseInformation.class, "getSimpleDetailsByName"),
	Enterprise_Op_Exception(4105, new String[]{"keyWord"}, "企查查企业经营异常信息", EnterpriseInformation.class, "getOpException"),
	Enterprise_YEAR_REPORT(4106, new String[]{"keyWord"}, "企查查企业年报信息", EnterpriseInformation.class, "getYearReport"),
	
	Face_Detection(4107, new String[]{"url"}, "微软的图片的人脸识别", FaceDetection.class, "faceDetectionAnalysis"),
//	QianHai_EnterpriseInformation(4108, new String[]{"idNo", "name"}, "前海的好信高管通，查询用户名下的企业信息", QianHaiEnterpriseInformation.class, "getQianHaiEnterpriseInfor"),
	//聚信立
	DATA_SOURCE(5101, new String[]{}, "获取支持的数据源列表", JuXinLiService.class, "datasources"),
	GET_RECEIPT(5102, new String[]{"name","id_card_num","cell_phone_num"}, "提交申请表单获取回执信息", JuXinLiService.class, "getReceipt"),
	COLLECT_MSG(5103, new String[]{"token","account"}, "提交数据源采集接口", JuXinLiService.class, "queryCollectMsg"),
	SKIP_REQUEST(5104, new String[]{"token"}, "提交跳过当前数据源接口", JuXinLiService.class, "skipRequest"),
	RESET_PWD(5105, new String[]{"account"}, "重置密码", JuXinLiService.class, "resetPwd"),
	//算话
	APPLY_REPORT(6101, new String[]{"name","idCard"}, "申请报告查询", SuanhuaService.class, "applyReport"),
//	SEND_PASSWORD(6102, new String[]{"idCard","name","phone","iid"}, "发送密码", SuanhuaService.class, "sendPassword"),
//	INPUT_PASSWORD(6103, new String[]{"idCard","name","phone","servicecode","iid","code"}, "输入密码以登陆", SuanhuaService.class, "inputPassword"),
	QUERY_REPORT(6104, new String[]{"idCard","name"}, "查询报告", SuanhuaService.class, "queryReport"),
	BETCH_REPORT(6105, new String[]{"from","to"}, "批量查询报告", SuanhuaService.class, "queryBatchReport"),
	QUERY_REPORT_BY_CRPID(6106, new String[]{"crpId"}, "通过crpId查询报告", SuanhuaService.class, "queryReportByCrpId"),
	PWD_RESET(6107, new String[]{}, "密码重置", SuanhuaService.class, "pwdReset"),
	CHECK_PWD_RESET(6108, new String[]{"phone","iid","oldservicecode","newservicecode"}, "重置密码新密码验证", SuanhuaService.class, "checkPwdReset"),
	
	INIT(6201, new String[]{"phone"}, "任务创建", SuanhuaService.class, "init"),
	LOGIN_FORM(6202, new String[]{"stoken","itemCode","loginTypeCode"}, "获取登录表单", SuanhuaService.class, "loginform"),
	FORM_POST(6203, new String[]{"stoken","step","form"}, "提交表单验证", SuanhuaService.class, "formpost"),
	SMS_SEND(6204, new String[]{"stoken","step"}, "获取短信验证码", SuanhuaService.class, "smssend"),
	IMAGE_REFRESH(6205, new String[]{"stoken","step"}, "刷新图片验证码", SuanhuaService.class, "imagerefresh"),
	REPORT_BY_STOKEN(6206, new String[]{"stoken"}, "获取已查得报告", SuanhuaService.class, "reportByStoken"),
	
	//同盾
	TONGDUN_FIRSTACQUIRE(6301, new String[]{"user_mobile","user_name","real_name","identity_code","user_pass"}, "首次登录验证", TongDunService.class, "firstAcquire"),
	TONGDUN_ACQUIRE(6302, new String[]{"task_id","user_name","user_pass","sms_code","task_stage","request_type"}, "二次验证", TongDunService.class, "acquire"),
	TONGDUN_RETRY(6303, new String[]{"task_id"}, "重试密码", TongDunService.class, "retry"),
	TONGDUN_RSTPWDCREATE(6304, new String[]{"user_mobile"}, "重置密码创建", TongDunService.class, "rstpwdCreate"),
	TONGDUN_RSTPWDSUBMIT(6305, new String[]{"task_id","sms_code","auth_code","identity_code","real_name","user_pass"}, "重置密码验证", TongDunService.class, "rstpwdSubmit"),
	TONGDUN_QUERY(6306, new String[]{"task_id"}, "查询任务结果", TongDunService.class, "query"),
	
	//聚信力数据接口
	REPORT_TOKEN(7101, new String[]{"org_name","client_secret"}, "获得安全凭证码", JuXinLiService.class, "accessReportToken"),
	REPORT_DATA(7102, new String[]{"org_name","client_secret","name","idCard","phone"}, "根据用户基本信息返回用户报告数据", JuXinLiService.class, "accessReportData"),
	RAW_DATA(7103, new String[]{"org_name","client_secret","name","idCard","phone"}, "根据用户基本信息返回运营商原始数据", JuXinLiService.class, "accessRawData"),
	BUSSINESS_RAW_DATA(7104, new String[]{"org_name","client_secret","name","idCard","phone"}, "根据用户基本信息返回电商原始数据", JuXinLiService.class, "accessBinessRawData"),
	ACCESS_JOB_STATUS(7105, new String[]{"client_secret","name","idCard","phone"}, "根据用户基本信息获取报告状态", JuXinLiService.class, "accessJobStatus"),
	//百度地图查询接口
	BAIDU_GPS_INFO(8101, new String[]{"location"}, "百度查询gps", BaiDuMapService.class, "queryBDGpsInfo"),
	BAIDU_IDCARD_OCR(8102, new String[]{"imageUrl","sideType"}, "身份证OCR识别", BaiDuAiService.class, "queryIdCardOcrInfo"),
	BAIDU_LIVING_BODY_DETECT(8103, new String[]{"imageContent"}, "在线活体检测", BaiDuAiService.class, "livingBodyDetection"),
	BAIDU_IDENTITY_AUTH(8106, new String[]{"imageContent","certNo","userName"}, "身份认证（公安内部照片对比）", BaiDuAiService.class, "identityAuth"),
	BAIDU_FACE_CONTRAST(8105, new String[]{"imageContent1","imageContent2"}, "人脸对比", BaiDuAiService.class, "faceContrast"),
	BAIDU_IDENTITY_AUTH_WITH_TOKEN(8104, new String[]{"imageContent","certNo","userName"}, "身份认证（公安内部照片对比）", BaiDuAiService.class, "identityAuthWithToken"),
	BAIDU_IDMATCH_WITH_TOKEN(8107, new String[]{"idCardNumber","name"}, "姓名身份证一致性校验", BaiDuAiService.class, "idCardNoMatch"),
	
	
	//百度营业执照识别 占用 8201 请大家不要用写在web项目里了
	BAIDU_FACE_identifyLicense(8201, new String[]{"imgurl"}, "百度营业执照识别", BaiDuFaceService.class, "identifyLicense"),
	BAIDU_FACE_addFace(8301, new String[]{"groupId","userOnlyId","imgurl"}, "人脸更新[百度人脸库中]", BaiDuFaceService.class, "addFace"),
	BAIDU_FACE_faceIdentify(8302, new String[]{"imgurl"}, "人脸查找[百度人脸库中] 1:N", BaiDuFaceService.class, "faceIdentify"),
	BAIDU_FACE_faceMultiIdentify(8303, new String[]{"imgurl","groupId"}, "人脸查找[百度人脸库中] M:N", BaiDuFaceService.class, "faceMultiIdentify"),
	BAIDU_FACE_match(8304, new String[]{"filePath1","filePath2","imgtype"}, "人脸比对 1:1", BaiDuFaceService.class, "match"),
	BAIDU_FACE_blackfaceMultiIdentify(8305, new String[]{"imgurl","groupName"}, "人脸查找[地推黑名单] M:N", BaiDuFaceService.class, "blackfaceMultiIdentify"),
	
	
	//前海征信
	QIAN_HAI_VERIFYPHONE(9101, new String[]{"appId","idCard","name","phone"}, "前海征信手机验证三要素、在网时长", QianHaiService.class, "verifyPhone"),
	QIAN_HAI_ENTMGRINC(9102, new String[]{"appId","idCard","name"}, "前海征信好信高管通", QianHaiService.class, "entMgrInc"),
	
	//百融
	BR_BANK_FOUR_PRO(1101, new String[]{"id","cell","name","bankId"}, "百融银行卡四要素一致性查询", BrQueryService.class, "queryBankFourInfo"),
	BAIRONG_TELCHECK(1102, new String[]{"idCard","name","phone"}, "百融手机三要素在网时长", BaiRongService.class, "telCheckAndTelPeriod"),
	ALI_BANK_FOUR_PRO(1103, new String[]{"id","cell","name","bankId"}, "阿里云银行卡四要素一致性查询", AliQueryService.class, "queryAliBankFourInfo"),
	ALI_IDCARD_AUTH(1104, new String[]{"idCardImgUrl", "name", "citizen_id"}, "阿里云身份证实名认证", AliQueryService.class, "queryAliIdCardAuth"),
	BAIRONG_BADINFO(1105, new String[]{"idCard","name","phone"}, "百融自然人", BaiRongService.class, "badInfo"),
	
	//鹏元征信接口
	PY_PERSONAL_BASIC_INFO(1301, new String[]{"name","documentNo"}, "个人基本信息", PengyuanQueryService.class, "personalBaseInfo"),
	PY_IDENTITY_AUTH(1302, new String[]{"name","documentNo"}, "身份认证", PengyuanQueryService.class, "identityAuth"),
	PY_ENTERPRISE_CREDIT_REPORT(1303, new String[]{"corpName","orgCode","areaDesc","registerNo","address"}, "企业信用报告", PengyuanQueryService.class, "enterpriseCreditReport"),
	PY_PERSONAL_INFO_REPORT(1304, new String[]{"name","documentNo"}, "个人信息报告", PengyuanQueryService.class, "personalInfoReport"),
	PY_PERSONAL_BANK_FOUR(1305, new String[]{"name","documentNo","accountNo","mobile"}, "个人银行卡四要素核查", PengyuanQueryService.class, "personalBankFour"),
	PY_MOBILE_VERIFY_AND_STATE_LENGTH(1306, new String[]{"name","documentNo","phone"}, "手机号码核查及状态时长", PengyuanQueryService.class, "mobileVerifyAndStateLength"),
	PY_PERSONAL_RISK_INFO(1307, new String[]{"name","documentNo"}, "个人风险信息", PengyuanQueryService.class, "personalRiskInfo"),
	PY_PERSONAL_ANTI_FRAUD_ANALYSIS_REPORT(1308, new String[]{"name","documentNo","phone"}, "个人反欺诈分析报告", PengyuanQueryService.class, "personalAntiFraudAnalysisReport"),
//	PY_DX_MOBILE_VERIFY_AND_STATE_QUERY(1309, new String[]{""}, "电信手机号码核查及状态查询", PengyuanQueryService.class, "dxMobileVerifyAndStateQuery"),
//	PY_LT_MOBILE_VERIFY_AND_STATE_QUERY(1310, new String[]{""}, "联通手机号码核查及状态查询", PengyuanQueryService.class, "ltMobileVerifyAndStateQuery"),
//	PY_YD_MOBILE_VERIFY_AND_STATE_QUERY(1311, new String[]{""}, "移动手机号码核查及状态查询", PengyuanQueryService.class, "ydMobileVerifyAndStateQuery"),
	
	//商户数据
	MERDATA_MERPICTURE(1201, new String[]{"shopname"}, "商户图片数据接口", ShangHuService.class, "merpicture"),
	MERDATA_WXMERPIPEL(1202, new String[]{"shopname","starttime","endtime"}, "商户微信流水接口", ShangHuService.class, "wxmerpipel"),
	MERDATA_ALIMERPIPEL(1203, new String[]{"shopname","starttime","endtime"}, "商户支付宝流水接口", ShangHuService.class, "alimerpipel"),
	MERDATA_WINGMERPIPEL(1204, new String[]{"shopname","starttime","endtime"}, "商户翼支付流水接口", ShangHuService.class, "wingmerpipel"),
	//湖南_商贸客户在邮储银行的资产数据
	HUNAN_ASSET_DATA(1401, new String[]{"name","idCard"}, "湖南掌柜资产数据", CustomerAssetService.class, "queryHuNanAssetData"),
	JIANGXI_JDZ_ASSET_DATA(1402, new String[]{"name","idCard"}, "江西景德镇掌柜资产数据", CustomerAssetService.class, "queryJiangXiJdzAssetData"),
	
	//测试环境挡板，没有set方法，做个假的
	BETA_BLOCK_BAIDU_IDCARD_OCR_1(81022, new String[]{"imageUrl","sideType"}, "身份证OCR识别", BaiDuAiService.class, "queryIdCardOcrInfo"),
	BETA_BLOCK_BAIDU_IDCARD_OCR_2(81021, new String[]{"imageUrl","sideType"}, "身份证OCR识别", BaiDuAiService.class, "queryIdCardOcrInfo");
	
	private Integer tranzCode;
	private String[] mandatoryFields;
	private String descript;
	private Class<?> clazz;
	private String methodName;
	private static Set<Integer> tranzCodes;
	public Integer getTranzCode() {
		return tranzCode;
	}

	public String[] getMandatoryFields() {
		return mandatoryFields;
	}
	public String getDescript() {
		return descript;
	}
	public Class<?> getClazz() {
		return clazz;
	}
	public String getMethodName() {
		return methodName;
	}
	private CommonTranzCode(Integer tranzCode, String[] mandatoryFields,
			String descript, Class<?> clazz, String methodName) {
		this.tranzCode = tranzCode;
		this.mandatoryFields = mandatoryFields;
		this.descript = descript;
		this.clazz = clazz;
		this.methodName = methodName;
	}
	
	public static Set<Integer> getTranzCodes(){
		if(tranzCodes == null){
			synchronized (YITU_OCR_PERSON_IDENTIFY) {
				tranzCodes = new HashSet<Integer>();
				for(CommonTranzCode ctc : values()){
					tranzCodes.add(ctc.getTranzCode());
				}
			}
		}
		return tranzCodes;
	}
	
	/**
	 * 检查请求的tranzCode是否是允许的
	 * @param tranzCode
	 * @return
	 */
	public static boolean containTranzCode(Integer tranzCode){
		return getTranzCodes().contains(tranzCode);
	}
	
	/**
	 * 通过tranzCode 获取对应的枚举对象
	 * @param tranzCode
	 * @return
	 */
	public static CommonTranzCode getTranzCode(Integer tranzCode){
		if(tranzCode != null){
			for(CommonTranzCode ctc : values()){
				if(tranzCode.intValue() == ctc.getTranzCode()){
					return ctc;
				}
			}
		}
		return null;
	}

}
