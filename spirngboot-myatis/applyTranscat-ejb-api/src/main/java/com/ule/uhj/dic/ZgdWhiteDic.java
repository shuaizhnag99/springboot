package com.ule.uhj.dic;


public interface ZgdWhiteDic {

	public enum Status {
		dongJie(0, "冻结，无资质"), 
		dongJieReActiveAble(5, "拒绝,满时效后允许再次发起"),
		chuShiDaoRu(10, "初始导入"), 
		tongBuVPS(101, "同步vps信息"), 
		xinXiQueRen(11, "信息已确认"), 
		yiBangKa(12, "已绑卡"), 
		jiBenXinXiShangChuan(14, "基本信息上传"), 
		tuPianShangChuan(20, "图片上传待审核"), 
		yuShenTongGuo(22, "预审通过"), 
		faSongWelab(30, "发送我来贷"), 
		welabFanHuiJieGuo(32, "我来贷返回结果"), 
		tuiHuiWelab(24, "退回我来贷重申"), 
		chuShenTongGuo(46, "邮乐初审通过"), 
		faSongYouChuYuShouXin(50, "已发送邮储预授信"), 
		yuShouXinFanHui(52, "邮储预授信返回结果"), 
		zhongShenTongGuo(62, "邮乐终审通过"), 
		faSongYouChuJianE(70, "发送邮储建额"), 
		uleKaiHu(88, "额度生效通过邮乐开户"), 
		huanKuanZhong(89, "还款中"), 
		quanBuJieQing(90, "全部结清"), 
		yuQi(91, "逾期")
		;
		private Integer code;
		private String name;
		private Status(Integer code, String name) {
			this.code = code;
			this.name = name;
		}
		public Integer getCode() {
			return code;
		}
	 
		public String getName() {
			return name;
		}
	 
		public static String getStatusName(Integer code){
			for(Status status : Status.values()){
				if(code.equals(status.getCode())){
					return status.getName();
				}
			}
			return null;
		}
		
	}

}