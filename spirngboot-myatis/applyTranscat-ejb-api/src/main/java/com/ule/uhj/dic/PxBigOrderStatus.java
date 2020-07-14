package com.ule.uhj.dic;

public enum PxBigOrderStatus {
	//10初始 20邮储受理成功 30 邮储受理失败 31 邮储支用拒绝或失败 50 退款中 55 退款成功 60 批销支付成功 ';
	/**
	 * 10初始
	 */
	init(10,"初始状态"),
	/**
	 * 20邮储受理成功
	 */
	psbc_accept_sucess(20,"邮储受理成功"),
	/**
	 * 30 邮储受理失败
	 */
	psbc_accept_faile(30,"邮储受理失败"),
	/**
	 * 31 邮储支用拒绝或失败
	 */
	psbc_lend_faile(31,"邮储支用拒绝或失败 "),
	/**
	 * 50 退款中 
	 */
	refunding(50,"退款中"),
	/**
	 *  55 退款成功 
	 */
	refund_sucess(55,"退款成功"),
	/**
	 * 60 批销支付成功
	 */
	psbc_lend_sucess(60,"邮储放款成功");
	
	private int status;
	private String statusdes;
	private PxBigOrderStatus(int status,String des) {
		this.status=status;
		this.statusdes=des;
	}
	public int getStatus() {
		return status;
	}
//	public void setStatus(int status) {
//		this.status = status;
//	}
	public String getStatusdes() {
		return statusdes;
	}
//	public void setStatusdes(String statusdes) {
//		this.statusdes = statusdes;
//	}
   
}