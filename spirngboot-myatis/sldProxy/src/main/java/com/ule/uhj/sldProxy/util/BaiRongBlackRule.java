package com.ule.uhj.sldProxy.util;

public enum BaiRongBlackRule { 
	//通过身份证号查询 黑名单type 0
	sl_id_abnormal("sl_id_abnormal","通过身份证号查询疑似欺诈","0"),
	sl_id_phone_overdue("sl_id_phone_overdue","通过身份证号查询电信欠费","0"),
	sl_id_court_bad("sl_id_court_bad","通过身份证号查询法院失信人","0"),
	sl_id_court_executed("sl_id_court_executed","通过身份证号查询法院被执行人","0"),
	sl_id_bank_bad("sl_id_bank_bad","通过身份证号查询银行不良","0"),
	sl_id_bank_overdue("sl_id_bank_overdue","通过身份证号查询银行短时逾期","0"),
	sl_id_bank_fraud("sl_id_bank_fraud","通过身份证号查询银行欺诈","0"),
	sl_id_bank_lost("sl_id_bank_lost","通过身份证号查询银行失联","0"),
	sl_id_bank_refuse("sl_id_bank_refuse","通过身份证号查询银行拒绝","0"),
	sl_id_p2p_bad("sl_id_p2p_bad","通过身份证号查询非银不良","0"),
	sl_id_p2p_overdue("sl_id_p2p_overdue","通过身份证号查询非银短时逾期","0"),
	sl_id_p2p_fraud("sl_id_p2p_fraud","通过身份证号查询非银欺诈","0"),
	sl_id_p2p_lost("sl_id_p2p_lost","通过身份证号查询非银失联","0"),
	sl_id_p2p_refuse("sl_id_p2p_refuse","通过身份证号查询非银拒绝","0"),
	sl_id_nbank_p2p_bad("sl_id_nbank_p2p_bad","通过身份证号查询P2P不良","0"),
	sl_id_nbank_p2p_overdue("sl_id_nbank_p2p_overdue","通过身份证号查询P2P短时逾期","0"),
	sl_id_nbank_p2p_fraud("sl_id_nbank_p2p_fraud","通过身份证号查询P2P欺诈","0"),
	sl_id_nbank_p2p_lost("sl_id_nbank_p2p_lost","通过身份证号查询P2P失联","0"),
	sl_id_nbank_p2p_refuse("sl_id_nbank_p2p_refuse","通过身份证号查询P2P拒绝","0"),
	sl_id_nbank_mc_bad("sl_id_nbank_mc_bad","通过身份证号查询小贷不良","0"),
	sl_id_nbank_mc_overdue("sl_id_nbank_mc_overdue","通过身份证号查询小贷短时逾期","0"),
	sl_id_nbank_mc_fraud("sl_id_nbank_mc_fraud","通过身份证号查询小贷欺诈","0"),
	sl_id_nbank_mc_lost("sl_id_nbank_mc_lost","通过身份证号查询小贷失联","0"),
	sl_id_nbank_mc_refuse("sl_id_nbank_mc_refuse","通过身份证号查询小贷拒绝","0"),
	sl_id_nbank_ca_bad("sl_id_nbank_ca_bad","通过身份证号查询现金类分期不良","0"),
	sl_id_nbank_ca_overdue("sl_id_nbank_ca_overdue","通过身份证号查询现金类分期短时逾期","0"),
	sl_id_nbank_ca_fraud("sl_id_nbank_ca_fraud","通过身份证号查询现金类分期欺诈","0"),
	sl_id_nbank_ca_lost("sl_id_nbank_ca_lost","通过身份证号查询现金类分期失联","0"),
	sl_id_nbank_ca_refuse("sl_id_nbank_ca_refuse","通过身份证号查询现金类分期拒绝","0"),
	sl_id_nbank_com_bad("sl_id_nbank_com_bad","通过身份证号查询代偿类分期不良","0"),
	sl_id_nbank_com_overdue("sl_id_nbank_com_overdue","通过身份证号查询代偿类分期短时逾期","0"),
	sl_id_nbank_com_fraud("sl_id_nbank_com_fraud","通过身份证号查询代偿类分期欺诈","0"),
	sl_id_nbank_com_lost("sl_id_nbank_com_lost","通过身份证号查询代偿类分期失联","0"),
	sl_id_nbank_com_refuse("sl_id_nbank_com_refuse","通过身份证号查询代偿类分期拒绝","0"),
	sl_id_nbank_cf_bad("sl_id_nbank_cf_bad","通过身份证号查询消费类分期不良","0"),
	sl_id_nbank_cf_overdue("sl_id_nbank_cf_overdue","通过身份证号查询消费类分期短时逾期","0"),
	sl_id_nbank_cf_fraud("sl_id_nbank_cf_fraud","通过身份证号查询消费类分期欺诈","0"),
	sl_id_nbank_cf_lost("sl_id_nbank_cf_lost","通过身份证号查询消费类分期失联","0"),
	sl_id_nbank_cf_refuse("sl_id_nbank_cf_refuse","通过身份证号查询消费类分期拒绝","0"),
	sl_id_nbank_other_bad("sl_id_nbank_other_bad","通过身份证号查询非银其他不良","0"),
	sl_id_nbank_other_overdue("sl_id_nbank_other_overdue","通过身份证号查询非银其他短时逾期","0"),
	sl_id_nbank_other_fraud("sl_id_nbank_other_fraud","通过身份证号查询非银其他欺诈","0"),
	sl_id_nbank_other_lost("sl_id_nbank_other_lost","通过身份证号查询非银其他失联","0"),
	sl_id_nbank_other_refuse("sl_id_nbank_other_refuse","通过身份证号查询非银其他拒绝","0"),
     //通过手机号查询黑名单
	sl_cell_abnormal("sl_cell_abnormal","通过手机号查询疑似欺诈","1"),
	sl_cell_phone_overdue("sl_cell_phone_overdue","通过手机号查询电信欠费","1"),
	sl_cell_bank_bad("sl_cell_bank_bad","通过手机号查询银行不良","1"),
	sl_cell_bank_overdue("sl_cell_bank_overdue","通过手机号查询银行短时逾期","1"),
	sl_cell_bank_fraud("sl_cell_bank_fraud","通过手机号查询银行欺诈","1"),
	sl_cell_bank_lost("sl_cell_bank_lost","通过手机号查询银行失联","1"),
	sl_cell_bank_refuse("sl_cell_bank_refuse","通过手机号查询银行拒绝","1"),
	sl_cell_p2p_bad("sl_cell_p2p_bad","通过手机号查询非银不良","1"),
	sl_cell_p2p_overdue("sl_cell_p2p_overdue","通过手机号查询非银短时逾期","1"),
	sl_cell_p2p_fraud("sl_cell_p2p_fraud","通过手机号查询非银欺诈","1"),
	sl_cell_p2p_lost("sl_cell_p2p_lost","通过手机号查询非银失联","1"),
	sl_cell_p2p_refuse("sl_cell_p2p_refuse","通过手机号查询非银拒绝","1"),
	sl_cell_nbank_p2p_bad("sl_cell_nbank_p2p_bad","通过手机号查询P2P不良","1"),
	sl_cell_nbank_p2p_overdue("sl_cell_nbank_p2p_overdue","通过手机号查询P2P短时逾期","1"),
	sl_cell_nbank_p2p_fraud("sl_cell_nbank_p2p_fraud","通过手机号查询P2P欺诈","1"),
	sl_cell_nbank_p2p_lost("sl_cell_nbank_p2p_lost","通过手机号查询P2P失联","1"),
	sl_cell_nbank_p2p_refuse("sl_cell_nbank_p2p_refuse","通过手机号查询P2P拒绝","1"),
	sl_cell_nbank_mc_bad("sl_cell_nbank_mc_bad","通过手机号查询小贷不良","1"),
	sl_cell_nbank_mc_overdue("sl_cell_nbank_mc_overdue","通过手机号查询小贷短时逾期","1"),
	sl_cell_nbank_mc_fraud("sl_cell_nbank_mc_fraud","通过手机号查询小贷欺诈","1"),
	sl_cell_nbank_mc_lost("sl_cell_nbank_mc_lost","通过手机号查询小贷失联","1"),
	sl_cell_nbank_mc_refuse("sl_cell_nbank_mc_refuse","通过手机号查询小贷拒绝","1"),
	sl_cell_nbank_ca_bad("sl_cell_nbank_ca_bad","通过手机号查询现金类分期不良","1"),
	sl_cell_nbank_ca_overdue("sl_cell_nbank_ca_overdue","通过手机号查询现金类分期短时逾期","1"),
	sl_cell_nbank_ca_fraud("sl_cell_nbank_ca_fraud","通过手机号查询现金类分期欺诈","1"),
	sl_cell_nbank_ca_lost("sl_cell_nbank_ca_lost","通过手机号查询现金类分期失联","1"),
	sl_cell_nbank_ca_refuse("sl_cell_nbank_ca_refuse","通过手机号查询现金类分期拒绝","1"),
	sl_cell_nbank_com_bad("sl_cell_nbank_com_bad","通过手机号查询代偿类分期不良","1"),
	sl_cell_nbank_com_overdue("sl_cell_nbank_com_overdue","通过手机号查询代偿类分期短时逾期","1"),
	sl_cell_nbank_com_fraud("sl_cell_nbank_com_fraud","通过手机号查询代偿类分期欺诈","1"),
	sl_cell_nbank_com_lost("sl_cell_nbank_com_lost","通过手机号查询代偿类分期失联","1"),
	sl_cell_nbank_com_refuse("sl_cell_nbank_com_refuse","通过手机号查询代偿类分期拒绝","1"),
	sl_cell_nbank_cf_bad("sl_cell_nbank_cf_bad","通过手机号查询消费类分期不良","1"),
	sl_cell_nbank_cf_overdue("sl_cell_nbank_cf_overdue","通过手机号查询消费类分期短时逾期","1"),
	sl_cell_nbank_cf_fraud("sl_cell_nbank_cf_fraud","通过手机号查询消费类分期欺诈","1"),
	sl_cell_nbank_cf_lost("sl_cell_nbank_cf_lost","通过手机号查询消费类分期失联","1"),
	sl_cell_nbank_cf_refuse("sl_cell_nbank_cf_refuse","通过手机号查询消费类分期拒绝","1"),
	sl_cell_nbank_other_bad("sl_cell_nbank_other_bad","通过手机号查询非银其他不良","1"),
	sl_cell_nbank_other_overdue("sl_cell_nbank_other_overdue","通过手机号查询非银其他短时逾期","1"),
	sl_cell_nbank_other_fraud("sl_cell_nbank_other_fraud","通过手机号查询非银其他欺诈","1"),
	sl_cell_nbank_other_lost("sl_cell_nbank_other_lost","通过手机号查询非银其他失联","1"),
	sl_cell_nbank_other_refuse("sl_cell_nbank_other_refuse","通过手机号查询非银其他拒绝","1");
	
	
	private String code;
	private String msg;
	private String type;
	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	public String getType() {
		return type;
	}

	private BaiRongBlackRule(String code, String msg,String type) {
		this.code = code;
		this.msg = msg;
		this.type = type;
	}
	
	/**
	 * 根据code获取对应的MerStatus
	 * @param code
	 * @return
	 */
	public static BaiRongBlackRule get(String code){
		for(BaiRongBlackRule ms : BaiRongBlackRule.values()){
			if(ms.getCode().equals(code)){
				return ms;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return getCode();
	}
	
//	public static void main(String[] args) {
//		get("200").msg = "fawewae";
//		System.out.println(get("200").getMsg());
//		System.out.println(get("210"));
//		
//	}
}
