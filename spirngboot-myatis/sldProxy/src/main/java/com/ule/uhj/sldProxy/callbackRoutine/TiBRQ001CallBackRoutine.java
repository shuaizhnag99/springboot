package com.ule.uhj.sldProxy.callbackRoutine;

import com.ule.uhj.sld.service.ThirdInterfaceCallBackRoutine;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 百融-借贷意向验证
 *
 * ApplyLoanStr借贷意向验证 + SpecialList_c特殊名单验证 + RuleSpecialList未知 + RuleApplyLoan未知
 * 目前发现该TranzCode码是这四种产品打包到一起调用的，不光是借贷意向验证，按借贷意向验证统计数据会不准确
 */
@Component
@Scope(value = "prototype")
public class TiBRQ001CallBackRoutine implements ThirdInterfaceCallBackRoutine {

    private String result;

    @Override
    public Boolean isSuccess() {
		JSONObject obj = JSONObject.fromObject(result);
		String response = obj.getString("responseCode");
		if("000000".equals(response)){
			JSONObject objs = obj.getJSONObject("responseMap");
			String status = objs.getString("code");
			return "100002".equals(status)||"00".equals(status);
		}
        return false;
    }

    @Override
    public void init(String s) {
        this.result = s;
    }

    @Override
    public String secondOpinion() {
        return null;
    }

    @Override
    public String getSign() {
        return null;
    }
    
//    public static void main(String[] args) {
//		String results = "{\"message\":\"百融征信：查询成功!\",\"responseCode\":\"000000\",\"responseMap\":{\"als_m12_id_nbank_min_inteday\":\"101\",\"als_lst_cell_nbank_consnum\":\"1\",\"als_m12_cell_max_inteday\":\"77\",\"als_m12_id_nbank_selfnum\":\"0\",\"Rule_weight_QJF045\":\"10\",\"als_m6_id_tot_mons\":\"1\",\"als_m12_id_max_monnum\":\"2\",\"als_m12_id_nbank_allnum\":\"2\",\"als_m6_id_bank_orgnum\":\"1\",\"als_m6_cell_bank_allnum\":\"1\",\"flag_ruleapplyloan\":\"1\",\"als_m12_cell_min_inteday\":\"48\",\"als_lst_id_nbank_csinteday\":\"1\",\"als_m12_cell_avg_monnum\":\"1.00\",\"als_m12_cell_nbank_min_inteday\":\"\",\"als_m6_cell_tot_mons\":\"1\",\"als_m12_cell_nbank_tot_mons\":\"1\",\"als_m12_cell_nbank_max_inteday\":\"\",\"als_m12_cell_nbank_p2p_orgnum\":\"1\",\"als_m12_id_bank_tot_mons\":\"3\",\"als_m12_cell_nbank_avg_monnum\":\"1.00\",\"als_m6_cell_bank_max_inteday\":\"\",\"als_fst_id_bank_inteday\":\"274\",\"als_m12_id_nbank_cf_orgnum\":\"1\",\"als_m12_id_nbank_max_monnum\":\"1\",\"als_lst_cell_bank_csinteday\":\"1\",\"als_m12_id_nbank_tot_mons\":\"2\",\"als_lst_cell_bank_inteday\":\"149\",\"als_m6_cell_min_inteday\":\"\",\"als_m12_id_min_inteday\":\"13\",\"als_m12_cell_nbank_min_monnum\":\"0\",\"flag_specialList_c\":\"0\",\"als_m12_id_bank_selfnum\":\"0\",\"als_lst_cell_bank_consnum\":\"1\",\"als_m6_cell_max_inteday\":\"\",\"als_m6_id_bank_avg_monnum\":\"1.00\",\"als_lst_id_bank_inteday\":\"149\",\"als_m12_id_bank_avg_monnum\":\"1.00\",\"als_m12_cell_max_monnum\":\"1\",\"als_m12_cell_bank_min_monnum\":\"0\",\"als_m6_cell_bank_min_monnum\":\"0\",\"als_m12_id_nbank_min_monnum\":\"0\",\"als_m12_id_nbank_cf_allnum\":\"1\",\"als_m12_id_nbank_orgnum\":\"2\",\"als_m12_id_nbank_p2p_orgnum\":\"1\",\"als_m6_id_bank_max_monnum\":\"1\",\"als_m12_cell_tot_mons\":\"4\",\"als_m12_cell_bank_allnum\":\"3\",\"als_m12_cell_bank_selfnum\":\"0\",\"als_lst_id_nbank_consnum\":\"1\",\"als_m12_id_avg_monnum\":\"1.25\",\"als_m12_id_bank_max_monnum\":\"1\",\"als_m6_id_bank_allnum\":\"1\",\"als_m12_cell_bank_min_inteday\":\"48\",\"als_m12_id_tot_mons\":\"4\",\"als_m12_cell_min_monnum\":\"0\",\"als_lst_id_bank_consnum\":\"1\",\"als_m6_cell_bank_selfnum\":\"0\",\"als_lst_cell_nbank_inteday\":\"340\",\"flag_rulespeciallist\":\"0\",\"flag_applyloanstr\":\"1\",\"Rule_name_QJF045\":\"在非银机构多次申请-低度\",\"als_m12_id_max_inteday\":\"77\",\"als_m6_id_min_inteday\":\"\",\"als_lst_id_bank_csinteday\":\"1\",\"als_m12_cell_nbank_p2p_allnum\":\"1\",\"als_m12_cell_bank_max_monnum\":\"1\",\"Rule_final_decision\":\"Review\",\"als_m12_id_bank_allnum\":\"3\",\"als_m6_cell_avg_monnum\":\"1.00\",\"als_lst_id_nbank_inteday\":\"239\",\"als_m6_id_max_monnum\":\"1\",\"als_m12_cell_nbank_max_monnum\":\"1\",\"als_m12_id_nbank_p2p_allnum\":\"1\",\"code\":\"00\",\"als_m12_cell_nbank_selfnum\":\"0\",\"als_fst_cell_nbank_inteday\":\"340\",\"Rule_final_weight\":\"20\",\"als_m6_id_avg_monnum\":\"1.00\",\"als_m6_id_bank_min_monnum\":\"0\",\"als_m6_cell_min_monnum\":\"0\",\"als_m12_id_bank_min_inteday\":\"48\",\"als_m6_id_bank_max_inteday\":\"\",\"als_m6_cell_bank_tot_mons\":\"1\",\"swift_number\":\"3000379_20180917111031_8773\",\"Rule_name_QJF025\":\"在银行多次申请-低度\",\"als_m6_cell_max_monnum\":\"1\",\"als_m12_cell_bank_orgnum\":\"3\",\"als_m12_cell_bank_avg_monnum\":\"1.00\",\"als_m6_id_min_monnum\":\"0\",\"als_m6_id_bank_tot_mons\":\"1\",\"als_m12_id_nbank_avg_monnum\":\"1.00\",\"als_m12_cell_nbank_allnum\":\"1\",\"als_m12_cell_nbank_orgnum\":\"1\",\"als_m12_cell_bank_max_inteday\":\"77\",\"als_m6_cell_bank_orgnum\":\"1\",\"als_fst_cell_bank_inteday\":\"274\",\"als_lst_cell_nbank_csinteday\":\"1\",\"als_m6_id_max_inteday\":\"\",\"als_m12_id_bank_max_inteday\":\"77\",\"als_m6_cell_bank_max_monnum\":\"1\",\"als_m12_cell_bank_tot_mons\":\"3\",\"als_fst_id_nbank_inteday\":\"340\",\"als_m12_id_min_monnum\":\"0\",\"als_m6_cell_bank_avg_monnum\":\"1.00\",\"als_m12_id_nbank_max_inteday\":\"101\",\"als_m12_id_bank_orgnum\":\"3\",\"Rule_weight_QJF025\":\"10\",\"als_m6_cell_bank_min_inteday\":\"\",\"als_m6_id_bank_min_inteday\":\"\",\"als_m6_id_bank_selfnum\":\"0\",\"als_m12_id_bank_min_monnum\":\"0\"}}";
//		JSONObject obj = JSONObject.fromObject(results);
//		String response = obj.getString("responseCode");
//		if("000000".equals(response)){
//			JSONObject objs = obj.getJSONObject("responseMap");
//			String status = objs.getString("code");
//			System.out.println("100002".equals(status)||"00".equals(status));
//		}
//		
//	}
}
