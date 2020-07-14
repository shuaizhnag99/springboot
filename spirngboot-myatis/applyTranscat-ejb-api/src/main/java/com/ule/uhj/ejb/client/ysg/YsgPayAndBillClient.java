package com.ule.uhj.ejb.client.ysg;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;

@Remote
@BeanName("YsgPayAndBillBean")
public interface YsgPayAndBillClient {
	/**
	 * 邮赊购订单产生账单
	 * @return
	 */
     public String createBill();
     /**
 	 * 邮赊购订单产生支用
 	 * @return
 	 */
     public String ysgApplyLoan();
     /**
        校验赊购订单是否可以提前结清，如果可以返回订单金额、订单详情
      * @param map
      * @return
      */
     public String checkYsgOrderInfo(Map<String,Object> map);
     /**
               * 处理kafka返回信息
      * @param key
      * @param value
      * @return
      */
     public String handleYsgKafkaInfo(String key,String value) ;
     /**
      * 根据用户查询赊购账单和订单
      * @param userOnlyId
      * @return
      */
     public Map<String ,Object> queryYsgPxinitOrder(String userOnlyId);
     /**
      * 保存支付流水
      * @param map
      */
     public void insertPaymentVoucher(Map<String,Object> map);
     /**
      * 更新支付流水
      * @param map
      */
     public void updatePaymentVoucher(Map<String,Object> map);
     /**
      * 同步支付结果（结果来自支付组通知）
      * @param map
      */
     public String syncPayResult(Map<String,Object> map);
     
     /**
      * 检查支付状态（支付请求流水号PAYMENT_FLOW_ID、订单号PX_ORDER_ID两个不能同时为空）
      * @param map 
      * @return 1 支付成功、2支付中 3 支付失败 4 参数为空或者没有记录 5 系统异常
      */
     public int checkPayStatus(Map<String,Object> map);
     
     /**
      * 同步支付结果（定时查询）支付超过指定时间未有结果的轮询去支付组查询支付结果
      * @param map
      */
     public String syncPayResultByTask();
     /**
      * 开通邮赊购账户
      * @param map
      * @return
      */
     public String openYsgAccount(Map<String,Object> map);
     
     /**
      * 检查用户是否可用邮赊购  
      * @param userOnlyId
      * @return
      * 0 非白名单区域不可开通 
      * 1 掌柜已开通邮赊购
      * 2 白名单区域 掌柜未开通邮赊购
      */
     public String checkUserYsgOPen(String userOnlyId);
     
     /**
               * 邮赊购订单结算发送批销消息回执
      * @param key
      * @param value
      * @return
      */
     public String ysgKafkaInvoking(String key,String value);
     
     /**
      * 邮赊购退款
      * @param map
      * @return
      */
     public String YsgRefun(Map<String,Object> map);
     
     /**
      * 邮赊购退款结果更新，超过指定时间仍未有结果的轮询去支付组查询退款结果
      * @param map
      * @return
      */
     public String YsgRefunSelcet(Map<String,Object> map);
     
     /**
      * 邮赊购退款通知
      * @param map
      * @return
      */
     public String YsgRefunNotice(Map<String,Object> map);
     /**
      * 获取流水号 merchantId(8位长度)+yyyyMMddHHmm（时间戳 12位长度）+流水(8位长度)
      * @param merchantId
      * @return
      */
     public String getTranscationId(String merchantId);
     
     
     
     /**
      * 检查退款状态（退款请求流水号REFUND_FLOW_ID、订单号PX_ORDER_ID两个不能同时为空）
      * @param map 
      * @return 1 支付成功、2支付中 3 支付失败 4 参数为空或者没有记录 5 系统异常
      */
     public int checkRefundStatus(Map<String,Object> map);
     /**
      * 发送短信
      * 邮赊购免息期临近时发送短信到掌柜
      * @return
      */
     public String sendYsgMsgToUser();
     /**
      * 查看账单下订单详情信息
      * @param billId
      * @return
      */
     public String queryBillOrderInfo(String billId,String userOnlyId);
     
     /**
               * 重复发送订单结算kafka消息发送批销组
      * @param KafkaRecordId
      * @return
      */
     public String resendInfoToPx(String KafkaRecordId);
     
     /**
                * 查询kafka消息记录
      * @param map
      *  id  消息Id
      *  userOnlyId
      *  sendInfo
      *  receiveInfo
      *  infoType  01 邮赊购
      *  keyWords
      *  status  0 处理失败 1 处理成功 2 已发送信息
      *  createTime
      * @return  List<Map<String,Object>>  
      * Map
      *   id  消息Id
      *  userOnlyId
      *  sendInfo
      *  receiveInfo
      *  infoType  01 邮赊购
      *  keyWords
      *  status  0 处理失败 1 处理成功 2 已发送信息
      *  createTime
      */
     public List<Map<String,Object>> queryKafkaRecord(Map<String,Object> map);
     
}
