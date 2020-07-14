package com.ule.uhj.ejb.client.ycZgd;

import com.ule.uhj.dto.opc.LimitQueryDto;
import com.ule.uhj.dto.opc.ShareProfitQueryDto;
import com.ule.uhj.dto.zgd.PostOrgInfo;
import com.ule.uhj.dto.zgd.ZgdQueryDto;
import com.ule.wildfly.annotation.BeanName;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
@Remote
@BeanName("OpcLimitQueryBean")
public interface OpcLimitQueryClient {
	/***
	 * 还款分润报表查询
	 * @param dto
	 * @return
	 */
	public ShareProfitQueryDto shareProfitQuery(ShareProfitQueryDto dto);
	/***
	 * 根据条件查询指定掌柜用户名下的所有账户信息
	 * @param zgdQueryDto 需拼装掌柜名、掌柜身份证号、机构代码、省代码至该类
	 * @return Map<String,String>
	 * result:状态，000为成功，其他为失败
	 * total:记录数量
	 * List<ZgdQueryDto> 返回所有符合条件的账户信息
	 * @throws Exception
	 */
	public Map<String,Object> queryZgdAccountByUserInfo(ZgdQueryDto zgdQueryDto) throws Exception;

	/***
	 * 查询指定条件的掌柜的userOnlyId
	 * @param dto
	 * @return
	 */
	public LimitQueryDto queryOpcLimitApplyInfo(LimitQueryDto dto);

	/***
	 * 2019-09-10
	 * OPC授信查询 第二版
	 * By.ZhengXin
	 * @param dto
	 * @return
	 */
	public LimitQueryDto queryOpcLimitApplyInfoV2(LimitQueryDto dto);
	
	/**
	 * 根据parentCode查询子机构
	 * @param parentCode
	 * @return
	 * @throws Exception
	 */
	public List<PostOrgInfo> queryChildrenByParent(String parentCode) throws Exception;

	/**
	 * opc报表--查询额度生效的掌柜
	 * @param params
	 * @return
	 */
	Map<String, Object> queryEffectiveLimit(Map<String, String> params);
	
}
