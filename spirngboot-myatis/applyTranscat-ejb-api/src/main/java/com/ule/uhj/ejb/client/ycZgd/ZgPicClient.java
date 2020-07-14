package com.ule.uhj.ejb.client.ycZgd;

import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;
@Remote
@BeanName("ZgPicBean")
public interface ZgPicClient {

	/**
	 * 判断是否显示掌柜贷链接
	 * @param paras userOnlyId
	 * @return Map<String, Object>
	 * key: result 
	 * value: 0:不显示 , 1:显示
	 */
	public Map<String, Object> checkZgPicLink(String userOnlyId);

	/** 
	 * 判断申请状态
	 * @param userOnlyId 
	 * @return Map<String, Object>
	 * key: result 
	 * value:
	 * 1: 显示图片上传页面
	 * 2: 显示等待审核页面
	 * 3: 显示审核成功页面
	 * 4: 未知
	 */
	public Map<String, Object> checkApplyStatus(String userOnlyId);

	/**
	 * 调用接口查询当前图片上传进度信息
	 * @param paras
	 * userOnlyId : 用户id
	 * @return Map<String, Object>
	 * key : busLience 营业执照照片,  store 店铺外照片,  storeInner 店铺内照片, certHold 手持身份证照片, certPos 身份证正面, certNeg 身份证反面, storePropertyType 店铺房产证明
	 * value : Map<String, String> tv
	 * 			tv.Entry  key: show    value: 0 可选,  1 必须 
	 * 			tv.Entry  key: haveUpload   value: 0 未上传, 1 已上传
	 * 			tv.Entry  key: auditResult  value: 0 未审核, 1 审核成功, 2 审核失败
	 * 			tv.Entry  key: errMsg  value: 审核失败原因  只有auditResult=2 时候才有该字段
	 */
	public Map<String, Object> queryPicsUpInfo(Map<String, Object> paras);

	/**
	 * 调用接口保存url, 审核成功后不再接受新的覆盖更新
	 * @param paras
	 * key : busLience 营业执照照片,  store 店铺外照片,  storeInner 店铺内照片, certHold 手持身份证照片, certPos 身份证正面, certNeg 身份证反面, storePropertyType 店铺房产证明
	 * value : 对应的url
	 * @return Map<String, Object>
	 * code:0000或者1000   --0000(成功)  1000(失败)
	 * msg:失败原因或者成功
	 */
	public Map<String, Object> saveOrUpdatePics(Map<String, Object> paras);
	
	/**
	 * 调用接口查询当前图片上传状态信息  queryPicsUpInfo版本的优化
	 * @param paras
	 * userOnlyId : 用户id
	 * @return Map<String, Object>
	 * key : busLience 营业执照照片,  store 店铺外照片,  storeInner 店铺内照片, certHold 手持身份证照片, certPos 身份证正面, certNeg 身份证反面, storePropertyType 店铺房产证明
	 * value : Map<String, String> tv
	 * 			tv.Entry  key: show    value: 0 不显示,  1 显示
	 * 			tv.Entry  key: upStatus   value: 1 可替换, 2 必须上传
	 * 			tv.Entry  key: errMsg  value: 审核失败原因  只有upStatus=2 时候才可能有该字段
	 */
	
	public Map<String,Object>queryPicsUpStatus(Map<String, Object> paras);
	
	
}
