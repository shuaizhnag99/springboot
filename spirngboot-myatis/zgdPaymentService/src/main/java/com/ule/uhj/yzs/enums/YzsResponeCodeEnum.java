package com.ule.uhj.yzs.enums;

public enum YzsResponeCodeEnum {
	SUCCESS("000000","接口调用成功!"),
	EXCEPTION("999999","系统发生未知异常!"),
	JSON_DATA_NULL("000001","未接收到前端数据!"),
	CLIENT_NOT_FOUND("000002","未找到指定业务处理元!"),
	USER_ONLY_ID_NULL("000003","用户标识不可为空!"),
	DATA_NOT_FOUND("000004","未查询到任何数据!"),
	PROXY_CALL_FAILED("000005","网络异常!请稍候重试"),
	CODE_NAME_CHECK_NOT_PASS("000006","掌柜姓名与身份证不为同一人，请重新填写。"),
	REG_CODE_NOT_FOUND("000007","营业执照号码不存在，请重新填写。"),
	REG_CODE_NOT_SAME_PERSON("000008","营业执照注册人与掌柜姓名不符，请修改。"),
	REG_CODE_END_DATE_LESS("000009","营业执照有效期不满一年，无法申请掌柜贷。"),
	CALL_IDC_FAILED("000010","征信查询身份证信息失败!"),
	CALL_REG_FAILED("000011","征信查询营业执照信息失败!"),
	ACCOUNT_INFO_MISSED("000012","掌柜信息不全无法提交!"),
	FILE_MISSED("000013","掌柜信息不全无法提交!"),
	CERT_POS_MISSED("000014","缺失身份证正面照!"),
	CERT_NEG_MISSED("000015","缺失身份证反面照!"),
	CERT_HOLD_MISSED("000016","缺失手持身份证照!"),
	BUS_MISSED("000017","缺失营业执照!"),
	OUTSHOP_MISSED("000018","缺失店铺外照片!"),
	INNERSHOP_MISSED("000019","缺失店铺内照片!"),
	IMG_SIZE_LARGE("000020","图片不可以大于10M!"),
	IMG_SERVER_ERROR("000021","连接图片服务器失败，请稍候重试!"),
	SAVE_USER_INFO_ERROR("000022","保存用户信息失败!"),
	YIWEI_REC_IDC_ERROR("000023","营业执照身份证校验异常!"),
	FEEDBACK_REPAET_ERROR("000024","该任务信息已反馈!"),
	FEEDBACK_INFO_ERROR("000025","该任务信息反馈信息有误!"),
	OVERAMOUNT_REPAYED("000026","该掌柜逾期金额已还清!"),
	OPERATION_CHECK_ERROR("000027","地推人员操作该掌柜不合法");
	
	
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
 
	//错误代码
	private String code;
	//错误描述
	private String message;
	YzsResponeCodeEnum(String code,String message){
		this.code = code;
		this.message = message;
	}
}