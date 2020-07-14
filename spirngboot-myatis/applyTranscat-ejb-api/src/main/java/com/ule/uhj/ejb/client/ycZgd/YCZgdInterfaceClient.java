package com.ule.uhj.ejb.client.ycZgd;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;

@Remote
@BeanName("YCZgdInterfaceBean")
public interface YCZgdInterfaceClient {
	/**
	 * 查询邮储LPR利率并本息本地数据
	 * @param effDate
	 */
	public void queryLprAndUpdateConstant(String effDate);
	/**
	 * 上传图片到邮储  返回邮储图片ID并更新数据库，为邮储CAFA验证准备数据
	 * 上传图片时Map 中useronlyId imageType 必须输入，其他 如果不经查询已经拿到，直接传入，否则查询自动填充
	 * @param map
	 * useronlyId 用户ID
	 * 
	 * cardName 姓名
	 * cardCode 身份证号码
	 * cardDate 证件到期日期
	 * imageType 图片类型  01-身份证正面，02-身份证反面03-配偶身份证正面 04-配偶身份证反面 05-收入证明  061-掌柜贷申请人脸识别   062-掌柜贷支用刷脸
	 * imageData 图片流  Base64编码的图片信息串 注1：单张图片不大于2M
	 * loanType  客户类型
	 * 
	 * @return 邮储图片ID
	 */
	public String uploadImageToPSBC(Map<String,Object> map);
	/**
	 * 组装CAFA所需要的ProofInfo 信息
	 * @param map
	 * useronlyId 用户ID
	 * type ：320001 额度申请  320002  贷款申请   320010  预授信额度审批
	 * @return
	 * 
	 *   {
			"id_front":"",           --身份证正面图片id(邮储返回)
			"id_back":"",			 --身份证反面图片id邮储返回)
			"face_image":"",		 --人脸识别图片id邮储返回)
			"face_request":"",       --人脸识别（含联网核查）请求报文
			"face_response":"",      --人脸识别（含联网核查）响应报文
			"card_elements":"",      --邮储借记卡四要素 
			 {"name":"张三","idCardNo":"41282805060026","bankCardNumber":"xxxxxxxxx","phoneNumber":"13666666666" }  
			"message_request":"",    --短信验证码请求报文
			"message_response":""    --短信验证码响应报文
       } 
	 */
	public Map<String,Object> getProofInfo(Map<String,Object> map);

}
