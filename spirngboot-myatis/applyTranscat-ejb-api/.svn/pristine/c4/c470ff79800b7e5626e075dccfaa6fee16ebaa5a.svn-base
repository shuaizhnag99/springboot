package com.ule.uhj.util.data;

/**
 * @author zhangyaou
 *
 */
public enum PromotionRule {

	/**
	 * 启动有礼
	 */
	zgd_open_rule("1000", "审批通过奖励地推人员10元"),
	/**
	 * 推动有奖
	 */
	zgd_promote_rule1("1001", "每季度日均贷款余额大于等于1万元且小于5万元，奖励地推人员20元"),
	/**
	 * 推动有奖
	 */
	zgd_promote_rule2("1002", "每季度日均贷款余额大于等于5万元，奖励地推人员100元"),
	/**
	 * 有效进件
	 */
	zgd_effective_rule("1003", "有效进件-预审岗审核通过，奖励地推人员10元");

	private PromotionRule(String key, String text) {
		this.code = key;
		this.text = text;
	}

	private String code;

	private String text;

	public String getCode() {
		return code;
	}



	public String getText() {
		return text;
	}



	/**
	 * 根据code获取text
	 * 
	 * @param code
	 * @return
	 */
	public static String getText(String code) {
		for (PromotionRule pc : PromotionRule.values()) {
			if (pc.getCode().equals(code)) {
				return pc.getText();
			}
		}
		return null;
	}

	/**
	 * 判断是否存在该code
	 * 
	 * @param code
	 * @return
	 */
	public static boolean isExist(String code) {
		return getText(code) != null ? true : false;
	}

	@Override
	public String toString() {
		return String.format("PromotionRule[code=%s,text=%s]", this.code, this.text);
	}

}
