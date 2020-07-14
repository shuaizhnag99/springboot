package com.ule.uhj.app.act.constant;

/**
 * 邮利券常量类
 * @author weisihua
 * @date 2018年5月3日
 */
public interface ActTicketConstant {

	/***********************************数据库常量值************************************/
	/**
	 * 当前持有人类型
	 * 生成者类型
	 * 发放者类型
	 * 上一持有人类型
	 * @author weisihua
	 * @date 2018年5月3日
	 */
	interface HOLDER_TYPE{
		
		String ULE_YY = "1";		//邮乐互金部运营
		String PSBC_DT = "2";		//邮政地推
		String PSBC_LOCAL = "3";	//当地邮政局
		String SHOP_OWNER = "8";	//掌柜
		String OTHER = "9";			//其他
	}
	
	/**
	 * 券状态
	 * @author weisihua
	 * @date 2018年5月3日
	 */
	interface STATUS{
		
		String UNACTIVATED = "0";	//未激活
		String ACTIVATED = "1";		//已激活
		String TRANSFERING = "2";	//转让中
		String USED = "3";			//已使用
		String USING = "4";			//使用中
		String TRANSFED = "5";		//已转让
		String DESTROYED = "9";		//失效
	}
	
	/**
	 * 券转让类型
	 * @author weisihua
	 * @date 2018年5月10日
	 */
	interface TRANS_TYPE{
		
		String GENERATED = "00";			//生成
		String PUBLISHED = "01";			//发放
		String RECEIVED = "02";				//领取
		String ACTIVATED = "03";			//激活
		String TRANSFERING = "04";			//转让中
		String CANCEL_TRANSFERING = "05";	//取消转让中
		String TRANSFERED = "06";			//转让
		String USED = "07";					//使用
		String DESTROYED = "08";			//失效
	}
	
	/**
	 * 券大类
	 * @author weisihua
	 * @date 2018年5月3日
	 */
	interface TICKET_TYPE_1{
		
		String INTEREST_RATE = "1";	//利率优惠券
		String LIMIT_RATE = "2";	//额度优惠券
	}
	
	/**
	 * 券小类
	 * @author weisihua
	 * @date 2018年5月3日
	 */
	interface TICKET_TYPE_2{
		
		String DISCOUNT_TICKET = "1";	//折扣券
		String REDUCTION_TICKET = "2";	//降息券
	}
	
	/**
	 * 邮利券订单类型
	 * @author weisihua
	 * @date 2018年5月11日
	 */
	interface TICKET_ORDER_TYPE{
		
		String CASH = "0";				//现金
		String TICKET = "1";			//邮利券
	}
	
	/**
	 * 邮利券订单类型
	 * @author weisihua
	 * @date 2018年5月11日
	 */
	interface TICKET_ORDER_STATUS{
		
		String CREATED = "0";			//创建支用中
		String SUCCESS = "1";			//支用成功
		String FAILED = "2";			//支用失败(可再次发邮储申请)
		String INVALID = "3";			//无效的支用
	}
	
	/**
	 * 还款方式
	 * @author weisihua
	 * @date 2018年5月21日
	 */
	interface REPAY_TYPE{
		String BENEFIT_FIRST = "01";	//先息后本
		String BENEFIT_AVG = "02";		//等额本息
	}
	
	/***********************************自定义常量值************************************/
	
	/**
	 * 返回给页面的优惠券类型
	 * @author weisihua
	 * @date 2018年5月3日
	 */
	interface PAGE_TICKET_TYPE{
		
		String DISCOUNT_TICKET = "1";	//折扣券
		String REDUCTION_TICKET = "2";	//降息券
	}
	
	/**
	 * 返回给页面的优惠券状态
	 * @author weisihua
	 * @date 2018年5月3日
	 */
	interface PAGE_TICKET_STATE{
		
		String UNUSABLE = "0";	//不可用
		String USABLE = "1";	//可用
		String PART_USABLE = "2";	//部分可用
	}
	
	/**
	 * 返回给页面的优惠券状态
	 * @author weisihua
	 * @date 2018年5月3日
	 */
	interface ticket_type_status{
		
		String UNUSABLE = "0";	//未执行不可用
		String USABLE = "1";	//已执行可用
	}
}
