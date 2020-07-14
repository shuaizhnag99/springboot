import com.ule.uhj.ejb.client.WildflyBeanFactory;

public class test {
    public static final String sql_apply = "select '申请中' as mystate,\n" +
            "       tapply.user_only_id,\n" +
            "       case\n" +
            "         when twhite.org_code is not null then\n" +
            "          twhite.org_code\n" +
            "         else\n" +
            "          tcinfo.org_code\n" +
            "       end as orgcode,\n" +
            "       case\n" +
            "         when twhite.province_name is not null then\n" +
            "          twhite.province_name\n" +
            "         else\n" +
            "          taddress.province\n" +
            "       end as province,\n" +
            "       case\n" +
            "         when twhite.city_name is not null then\n" +
            "          twhite.city_name\n" +
            "         else\n" +
            "          taddress.city\n" +
            "       end as city,\n" +
            "       case\n" +
            "         when twhite.county_name is not null then\n" +
            "          twhite.county_name\n" +
            "         else\n" +
            "          taddress.area\n" +
            "       end as area,\n" +
            "       tinfo.acc_name,\n" +
            "       tapply.update_time,\n" +
            "       tinfo.credit_limit,\n" +
            "       tinfo.avail_balance,\n" +
            "       tinfo.loan_num,\n" +
            "       tinfo.historical_loan,\n" +
            "       tinfo.expire_date,\n" +
            "       cpost1.mobile,\n" +
            "       cpost2.mobile,\n" +
            "       tapply.user_only_id,\n" +
            "       tinfo.loan_officer_name,\n" +
            "       tinfo.loan_officer_org,\n" +
            "       tinfo.loan_officer_phone,\n" +
            "       case\n" +
            "         when cpost1.mobile is not null then\n" +
            "          '1'\n" +
            "         else\n" +
            "          '0'\n" +
            "       end as bind,\n" +
            "  tcapply.apply_time, \n"+
            "  twork.first_time "+
            "  from uleapp_financecr.t_j_apply_detail tapply\n" +
            "  left join uleapp_financecr.t_j_customer_info tcinfo\n" +
            "    on tcinfo.user_only_id = tapply.user_only_id\n" +
            "  left join uleapp_financecr.t_j_zgd_white twhite\n" +
            "    on twhite.user_only_id = tapply.user_only_id\n" +
            "  left join uleapp_financecr.t_j_account_info tinfo\n" +
            "    on tinfo.user_only_id = tapply.user_only_id\n" +
            "  left join uleapp_financecr.t_j_customer_address taddress\n" +
            "    on taddress.user_only_id = tapply.user_only_id\n" +
            "  left join uleapp_financecr.t_j_credit_postmember cpost1\n" +
            "    on (cpost1.user_only_id = tapply.user_only_id and cpost1.member_type = 1)\n" +
            "  left join uleapp_financecr.t_j_credit_postmember cpost2\n" +
            "    on (cpost2.user_only_id = tapply.user_only_id and cpost2.member_type = 2)\n" +
            "  left join uleapp_financecr.t_j_credit_apply tcapply\n" +
            "    on tcapply.user_only_id = tapply.user_only_id\n" +
            "  left join (select row_number() over (partition by twork.business_key order by twork.create_time)rn,twork.business_key as appId,to_char(twork.create_time,'yyyy-MM-dd hh:mm:ss') as first_time\n" +
            "  from uleapp_financecr.t_w_work_flow_log twork \n" +
            "  where twork.log_content like '%autoInvoke%') twork\n" +
            "    on twork.appId = tcapply.id and twork.rn = 1 "+
            " where (taddress.address_type = 3 or taddress.address_type is null)";
    public static void main(String[] args) throws Exception{
            Object Handle = WildflyBeanFactory.getZgdQueryClient();
    }
}
