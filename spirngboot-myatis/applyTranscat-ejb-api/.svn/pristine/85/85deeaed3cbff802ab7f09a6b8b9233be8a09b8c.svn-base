package com.ule.uhj.ejb.client.yzq;

import com.ule.wildfly.annotation.BeanName;

import javax.ejb.Remote;
import java.util.List;
import java.util.Map;

@Remote
@BeanName("YzqBean")
public interface YzqClient {
    Map<String, Object> zgQuery(Map<String, String> map);

    Map<String, String> zgImport(List<Map<String, String>> mapList);

    Map<String, String> zgModifer(Map<String, String> map);

    Map<String, String> zgFreeze(Map<String, String> map);

    Map<String, Object> getStatus(String key);

    Map<String, Object> orderQuery(Map<String, String> map);
    
    public String checkYzqLend(String userOnlyId,String applyAmount);
	
	public String queryYzqRepayPlan(Map<String, Object> map);
	
	public String confirmYzqLoanApply(Map<String, Object> map);
	
	public String queryYzqLendInfo(Map<String, Object> map);
}
