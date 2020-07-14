package com.ule.uhj.yzs.enums;



public enum TaskTypeEnum {
	OVERDUE_WARN(1, "掌柜贷款逾期提醒"),
	INCOME_REDUCE(2, "掌柜经营业绩下滑"),
	GENERALIZE_FEEDBACK(3, "掌柜贷推广反馈");
	
	private Integer code;
	private String name;
	
	private TaskTypeEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

 

	public String getName() {
		return name;
	}

 
	
	public static boolean containsCode(Integer code){
		for (TaskTypeEnum tt : TaskTypeEnum.values()) {
			if (code.equals(tt.getCode())) {
				return true;
			}
		}
		return false;
	}
}
