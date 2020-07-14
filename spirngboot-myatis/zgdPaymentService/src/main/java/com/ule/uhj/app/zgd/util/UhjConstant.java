package com.ule.uhj.app.zgd.util;

import java.math.BigDecimal;


public class UhjConstant {
	
	public static final Integer  time_out=30000;
	
	/**
	 * 申请状态
	 * @author Administrator
	 *
	 */
	public static class applyStatus {
		/**
		 * A：快速建额预处理
		 */
		public static final String APPLY_STATUS_A = "A";
		/**
		 * 0：申请中
		 */
		public static final String APPLY_STATUS_UNACTIVE = "0";
		/**
		 * 1：运营商授权中
		 */
		public static final String APPLY_STATUS_NORMAL = "1";
		/**
		 * 2：运营商授权失败
		 */
		public static final String APPLY_STATUS_OPERATOR = "2";
		/**
		 * 3：等待审核
		 */
		public static final String APPLY_STATUS_REVIEW = "3";
		/**
		 * 4：审核被打回重新申请(显示打回原因)
		 */
		public static final String APPLY_STATUS_RETURN = "4";
		/**
		 * 5 审核拒绝 
		 */
		public static final String APPLY_STATUS_REFUSE="5";
		/**
		 * 88 审核通过 
		 */
		public static final String APPLY_STATUS_PASS="88";
		/**
		 * 发邮储接口阻塞中
		 */
		public static final String SIGN_SUBMIT_BLOCKING="6";
		
	}
	
	/**
	 * 规则类型
	 * @author Administrator
	 *
	 */
	public static class contactCertNo {
		/**
		 * 
		 */
		public static final String contactCert_marriage="0";
		public static final String contactCert_household="1";
		
	}
	/**
	 * 规则类型
	 * @author Administrator
	 *
	 */
	public static class ruleType {
		/**
		 * 掌柜贷申请   6-逻辑校验
		 */
		public static final String app_rule_type="6";
		/**
		 * 邮助手地推实名   Y 是否实名校验
		 */
		public static final String yzs_rule_type="Y";
		
	}
	/**
	 * 规则相关ID
	 * @author Administrator
	 *
	 */
	public static class ruleRefId {
		/**
		 * 人脸识别
		 */
		public static final String face_recognition="APPCHECK_001";
		/**
		 * 绑定银行卡
		 */
		public static final String binding_bank_card="APPCHECK_002";
		/**
		 * 联系人状态
		 */
		public static final String contact_state="APPCHECK_003";
		/**
		 * 店铺信息填写
		 */
		public static final String shops_fill="APPCHECK_004";
		/**
		 * 配偶授权
		 */
		public static final String spouse_authorization="APPCHECK_005";
		/**
		 *  运营商授权
		 */
		public static final String operator_authorization="APPCHECK_006";
		
		/**
		 *  运营商授权(算话 or 聚信立)
		 */
		public static final String authorization_org="APPCHECK_006_1";
		/**
		 *  活体检测结果  活体检测成功/活体检测失败
		 */
		public static final String live_test="APPCHECK_007";
		/**
		 *  掌柜身份证号与姓名是否匹配  ：true/false
		 *  
		 */
		public static final String certno_name_matches="APPCHECK_008";
		/**
		 *  掌柜公安部登记照片与活体检测截图相似度 例如75.00分
		 */
		public static final String photos_living_similarity="APPCHECK_009";
		/**
		 * 掌柜人脸比对结果  ：通过/不通过
		 */
		public static final String face_compare="APPCHECK_010";
		/**
		 * 店铺内部照片是否符合要求
		 */
		public static final String store_inside_photos="APPCHECK_011";
		/**
		 * 店铺外部照片是否符合要求
		 */
		public static final String store_outside_photos="APPCHECK_012";
		/**
		 *  配偶公安部登记照片与活体检测截图相似度 例如75.00分
		 */
		public static final String spouse_photos_living_similarity="APPCHECK_013";
		/**
		 * 营业执照编号是否正确
		 */
		public static final String business_license="APPCHECK_014";
		/**
		 *  依图店铺合影和人脸对比相似度 例如75.00分
		 */
		public static final String store_inside_similarity="APPCHECK_015";
		/**
		 *  依图店铺合影和人脸对比相似度 例如75.00分
		 */
		public static final String store_outside_similarity="APPCHECK_016";
		/**
		 * 身份证识别地址结果  ：通过/不通过
		 */
		public static final String cert_photos="APPCHECK_017";

		/**
		 * 百度店铺内部照片邮助手相似度 例如75.00分
		 */
		public static final String baidu_inyzs_similarity="APPCHECK_018";
		
		/**
		 * 百度店铺外部照片邮助手相似度 例如75.00分
		 */
		public static final String baidu_outyzs_similarity="APPCHECK_019";
		
		/**
		 * 店铺内部照片错误原因
		 */
		public static final String store_inside_reason="APPCHECK_020";
		
		/**
		 * 店铺外部照片错误原因
		 */
		public static final String store_outside_reason="APPCHECK_021";
		/**
		 * 百度店铺内部照片掌柜相似度 例如75.00分
		 */
		public static final String baidu_inuser_similarity="APPCHECK_022";
		
		/**
		 * 百度店铺外部照片掌柜相似度 例如75.00分
		 */
		public static final String baidu_outuser_similarity="APPCHECK_023";
		/**
		 * 依图店铺内部照片邮助手相似度 例如75.00分
		 */
		public static final String yitu_inyzs_similarity="APPCHECK_024";
		
		/**
		 * 依图店铺外部照片邮助手相似度 例如75.00分
		 */
		public static final String yitu_outyzs_similarity="APPCHECK_025";
		
		
		
		/**
		 * 邮助手地推人脸照片  ：通过/不通过
		 */
		public static final String yzs_postmember_face="APPCHECK_100";
		
		/**
		 * 批销额度申请结果
		 */
		public static final String px_limit_apply="APPCHECK_200";
	}
	/**
	 * 规则输出结果
	 * @author Administrator
	 *
	 */
	public static class ruleOutput {
		/**
		 * 规则输出结果  true
		 */
		public static final String ruleOutput_true="true";
		/**
		 * 规则输出结果  false
		 */
		public static final String ruleOutput_false="false";
		
		/**
		 * 规则输出结果  skip
		 */
		public static final String ruleOutput_skip="skip";
		/**
		 * 规则输出结果  skip2py
		 */
		public static final String ruleOutput_skip2py="skip2py";
		/**
		 * 规则输出结果  1   刷脸失败次数
		 */
		public static final String ruleOutput_one="1";
		/**
		 * 规则输出结果  2 刷脸失败次数
		 */
		public static final String ruleOutput_two="2";
		/**
		 * 规则输出结果  3 刷脸失败次数
		 */
		public static final String ruleOutput_three="3";
		//其他的值自己输入
		
	}
	/**
	 * 店铺房产类型 0自有 1：租赁 
	 * propertyType
	 * @author Administrator
	 *
	 */
	public static class propertyType{
		/**
		 *1：租赁 
		 */
		public static final String store_rent="1";
		/**
		 * 0自有
		 */
		public static final String store_own="0";
	}
	
	
	/**
	 * 联系人类型
	 * 1父母、2子女、3兄弟、4姐妹、0妻子
	 * @author Administrator
	 *
	 */
	public static class contactType {
		/**
		 * 0配偶
		 */
		public static final String spouse="0";
		/**
		 * 1父母
		 */
		public static final String parents="1";
		/**
		 * 2子女
		 */
		public static final String children="2";
		/**
		 * 3兄弟
		 */
		public static final String brother="3";
		/**
		 * 4姐妹
		 */
		public static final String sister="4";
		/**
		 * 5朋友
		 */
//		public static final String friend="5";

	}
	/**
	 * 婚姻状态
	 * 已婚、未婚、离异、丧偶
	 * @author Administrator
	 *
	 */
	public static class maritalStatus{
		/**
		 * 未婚
		 */
		public static final BigDecimal unmarried=new BigDecimal("10");
		/**
		 * 已婚
		 */
		public static final BigDecimal married=new BigDecimal("20");
		/**
		 * 丧偶
		 */
		public static final BigDecimal deathSpouse=new BigDecimal("30");
		/**
		 * 离异
		 */
		public static final BigDecimal divorced=new BigDecimal("40");
		
	}
	/**
	 * 用户类型
	 * @author Administrator
	 *
	 */
	public static class customerType{
		/**
		 * 借款人
		 */
		public static final String loanor="1";
		/**
		 * 联系人
		 */
		public static final String contact="2";
	}
	/**
	 * 地址类型
	 * @author Administrator
	 *'1-户籍地址 2-家庭地址 3-店铺地址 4-公司地址 9-其他地址';
	 */
	public static class addressType{
		/**
		 * 1-户籍地址
		 */
		public static final String registration_address="1";
		/**
		 * 2-家庭地址
		 */
		public static final String home_address="2";
		/**
		 * 3-店铺地址 
		 */
		public static final String store_address="3";
		/**
		 * 4-公司地址
		 */
		public static final String company_address="4";
		/**
		 *  9-其他地址'
		 */
		public static final String other_address="9";
	}
	/**
	 * 证件类型
	 * @author Administrator
	 *
	 */
	public static class certType{
		/**
		 * 掌柜身份证
		 */
		public static final String idcard="10";
		/**
		 * 配偶身份证
		 */
		public static final String spouse_idcard="20";
		/**
		 * 营业执照(不需要了)
		 */
		public static final String business_license="30";
		
	}
	/**
	 * 上传图片的类型
	 * @author Administrator
	 *上传图片的类型： 
	 *			app_IdCardPositive  身份证正面   
	 *			app_IdCardOpposite  身份证反面
	 *	   		app_spouseICPositive  配偶身份证正面   
	 *			app_spouseICOpposite  配偶身份证反面
	 *			app_storeInner    店铺内部照片
	 *			app_storeOutside  店铺外部照片
	 *			app_selfFace  本人刷脸照
	 *			app_spouseFace  配偶刷脸照
	 */
	public static class imageType{
		/**
		 * 掌柜身份证正面
		 */
		public static final String app_IdCardPositive="app_IdCardPositive";
		/**
		 * 掌柜身份证反面
		 */
		public static final String app_IdCardOpposite="app_IdCardOpposite";
		/**
		 * 配偶身份证正面
		 */
		public static final String app_spouseICPositive="app_spouseICPositive";
		/**
		 * 配偶身份证反面
		 */
		public static final String app_spouseICOpposite="app_spouseICOpposite";
		/**
		 * 店铺内部照片
		 */
		public static final String app_storeInner="app_storeInner";
		/**
		 * 店铺外部照片
		 */
		public static final String app_storeOutside="app_storeOutside";
		/**
		 *本人刷脸照
		 */
		public static final String app_selfFace="app_selfFace";
		/**
		 * 配偶刷脸照
		 */
		public static final String app_spouseFace="app_spouseFace";
		/**
		 * 个人中心头像
		 */
		public static final String app_personalCenter="app_personalCenter";
		/**
		 * 手持银行卡照片
		 */
		public static final String app_handheldIDCard="app_handheldbankCard";
		/**
		 * 邮助手人脸照片
		 */
		public static final String yzs_postmember_face="yzs_postmember_face";
		/**
		 * 确认借款
		 */
		public static final String confirm_loan_page="confirm_loan_page";
		
		
		
	}
	
	/**
	 * 产品编码
	 * @author Administrator
	 *
	 */
	public static class productCode {
		/**
		 * app申请掌柜贷
		 */
		public static final String app_code="300";
		
	}
	/**
	 * 性别
	 * @author Administrator
	 *
	 */
	public static class gender {
		/**
		 * 1 男
		 */
		public static final String male="1";
		/**
		 * 2女
		 */
		public static final String female="2";
		
	}
	
	/**
	 * 电话号码类型
	 * @author Administrator
	 *
	 */
	public static class phoneType{
		/**
		 * 随身
		 */
		public static final String carryOn="1";
		/**
		 * 家庭
		 */
		public static final String home="2";
		/**
		 * 店铺
		 */
		public static final String store="3";
		/**
		 * 公司
		 */
		public static final String company="4";
		/**
		 * 其他
		 */
		public static final String other="9";
	}
	
	/**
	 * 电话号码类型
	 * @author Administrator
	 *
	 */
	public static class phoneNoType{
		/**
		 * 手机
		 */
		public static final String mobile="1";
		/**
		 * 固话
		 */
		public static final String telephone="2";
	}
	
	
	/**
	 * 是否设为默认信息
	 * @author Administrator
	 *
	 */
	public static class permanentFlag{
		/**
		 * 是
		 */
		public static final String yes="1";
		/**
		 * 否
		 */
		public static final String no="0";
	}
	
	/**
	 * 接口代号
	 * @author Administrator
	 *
	 */
	public static class transCode{
		/**
		 * 亿图识别用户提交的身份证信息
		 */
		public static final String OcrPersonIdentify="3101";
		
		/**
		 * 亿图两张照片相似度比较
		 */
		public static final String SampleCodeV2PairVerify="3103";
		/**
		 * 联网核查姓名和身份证是否匹配, 匹配成功时获取公安部联网水印照
		 */
		public static final String PersonIdentify="3104";
		/**
		 * 上传文件
		 */
		public static final String UploadFileServer="4100";
		/**
		 * 微软的店铺内部分析
		 */
		public static final String StoreInnerAnalysis="4101";
		/**
		 * 阿里的店招OCR识别接口
		 */
		public static final String StoreOutsideAnalysis="4102";
		/**
		 * 微软的图片人脸识别
		 */
		public static final String Face_Detection="4107";
		/**
		 * 聚信力获取数据源
		 */
		public static final String DATA_SOURCE="5101";
		/**
		 * 获取回执
		 */
		public static final String GET_RECEIPT="5102";
		/**
		 * 采集数据
		 */
		public static final String COLLECT_MSG="5103";
		/**
		 * 跳过当前数据源
		 */
		public static final String SKIP_REQUEST="5104";
		/**
		 * 重置密码
		 */
		public static final String RESET_PDW="5105";
		/**
		 * 算话申请报告
		 */
		
		
		/**
		 * 算话-任务创建
		 */
		public static final String INIT="6201";
		/**
		 * 算话-获取登录表单
		 */
		public static final String LOGIN_FORM="6202";
		/**
		 * 算话-提交表单验证
		 */
		public static final String FORM_POST="6203";
		/**
		 * 算话-获取短信验证码
		 */
		public static final String SMS_SEND="6204";
		/**
		 * 算话-刷新图片验证码
		 */
		public static final String IMAGE_REFRESH="6205";
		/**
		 * 算话-获取已查得报告
		 */
		public static final String REPORT_BY_STOKEN="6206";
		
		
		/**
		 * 同盾-任务创建、登录验证、验证码、重置密码创建、重置密码验证、查询任务结果
		 */
		public static final String TONGDUN_FIRSTACQUIRE="6301";
		public static final String TONGDUN_ACQUIRE="6302";
		public static final String TONGDUN_RETRY="6303";
		public static final String TONGDUN_RSTPDWCREATE="6304";
		public static final String TONGDUN_RSTPDWSUBMIT="6305";
		public static final String TONGDUN_QUERY="6306";		
		
		
		public static final String APPLY_REPORT="6101";
		/**
		 * 发送验证码
		 */
//		public static final String SEND_PASSWORD="6102";
		/**
		 * 输入验证码
		 */
//		public static final String INPUT_PASSWORD="6103";
		/**
		 * 查询报告
		 */
		public static final String QUERY_REPORT="6104";
		/**
		 * 批量查询报告
		 */
		public static final String BETCH_REPORT="6105";
		/**
		 * 通过crpId查询报告
		 */
		public static final String QUERY_REPORT_BY_CRPID="6106";
		/**
		 * 密码重置
		 */
		public static final String PDW_RESET="6107";
		/**
		 * 重置密码新密码验证
		 */
		public static final String CHECK_PDW_RESET="6108";
		/**
		 * 获得安全凭证码
		 */
		public static final String REPORT_TOKEN="7101";
		/**
		 * 根据用户基本信息返回用户报告数据
		 */
		public static final String REPORT_DATA="7102";
		/**
		 * 根据用户基本信息返回运营商原始数据
		 */
		public static final String RAW_DATA="7103";
		/**
		 * 根据用户基本信息返回电商原始数据
		 */
		public static final String BUSSINESS_RAW_DATA="7104";
		/**
		 * 根据用户基本信息获取报告状态
		 */
		public static final String ACCESS_JOB_STATUS="7105";
		/**
		 * 根据坐标查询百度gps信息
		 */
		public static final String BAIDU_GPS_INFO="8101";
		//前海征信接口——手机三要素、在网时长
		public static final String QIAN_HAI_VERIFYPHONE="9101";
		public static final String QIAN_HAI_ENTMGRINC="9102";
		//鹏元接口
		public static final String PENGYUAN_VERIFYPHONE="1306";
		public static final String PENGYUAN_ENTMGRINC="1304";
		
		//百融接口——手机三要素、在网时长
		public static final String BAIRONG_TELCHECK="1102";
	}
	
	/**
	 * 接口信息状态
	 * @author Administrator
	 *
	 */
	public static class interfaceStutas{
		/**
		 * 初始化
		 */
		public static final String init="0";
		/**
		 * 返回成功
		 */
		public static final String success="1";
		/**
		 * 请求失败
		 */
		public static final String faild="2";
		/**
		 * 等待查询
		 */
		public static final String holdon="3";
		/**
		 * 工作流提交失败
		 */
		public static final String submitFaild="4";
	}
	/**
	 * 渠道代码
	 *
	 */
	public static class channelCode{
		/**
		 * 邮乐掌柜贷渠道 C0001
		 */
		public static final String ZGD_CHANNEL="C0001";
		/**
		 * 河北掌柜贷渠道 C0002
		 */
		public static final String ZGD_HEBEI_CHANNEL="C0002";
		/**
		 * 上海格力专享   GREE_SH_001
		 */
		public static final String GREE_SH="GREE_SH_001";
		/**
		 * 江西美的专享   MIDEA_JX_001
		 */
		public static final String MIDEA_JX="MIDEA_JX_001";
	}
	
	/**
	 * 绑卡账户表的状态
	 *00-无效 01-审核中 10-有效非常用 11-有效常用
	 */
	public static class CustomerAccountStatus{
		/**
		 * 00-无效
		 */
		public static final String valid="00";
		/**
		 * 01-审核中
		 */
		public static final String invalid="01";
		/**
		 *10-有效非常用
		 */
		public static final String valid_nouseful="10";
		/**
		 * 11-有效常用
		 */
		public static final String valid_useful="11";
	}
	
}
