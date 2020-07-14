package com.ule.uhj.util.data;

/**
 * @author zhangyaou
 *
 */
public enum PrdCode {

	/**
	 * ule审核ule放款
	 */
	zgd_ule_ule("100", "ule审核ule放款"),
	/**
	 * welab审核 邮储放款
	 */
	zgd_welab_psbc("200", "welab审核邮储放款"),
	/**
	 * ule审核 邮储放款
	 */
	zgd_ule_yc("300", "ule审核邮储放款");

	private PrdCode(String key, String text) {
		this.code = key;
		this.text = text;
	}

	private String code;

	private String text;

	public String getCode() {
		return code;
	}

//	public void setCode(String code) {
//		this.code = code;
//	}

	public String getText() {
		return text;
	}

//	public void setText(String text) {
//		this.text = text;
//	}

	/**
	 * 根据code获取text
	 * 
	 * @param code
	 * @return
	 */
	public static String getText(String code) {
		for (PrdCode pc : PrdCode.values()) {
			if (pc.getCode().equals(code)) {
				return pc.getText();
			}
		}
		return null;
	}

	/**
	 * 判断是否存在该code的PrdCode
	 * 
	 * @param code
	 * @return
	 */
	public static boolean isExist(String code) {
		return getText(code) != null ? true : false;
	}

	@Override
	public String toString() {
		return String.format("PrdCode[code=%s,text=%s]", this.code, this.text);
	}

}