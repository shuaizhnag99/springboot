package com.ule.uhj.app.act.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.act.constant.AccountMoneyUtil;
import com.ule.uhj.app.act.constant.ActTicketConstant;
import com.ule.uhj.app.act.service.SaleActTicketService;
import com.ule.uhj.app.zgd.dao.CreditApplyMapper;
import com.ule.uhj.app.zgd.dao.CustomerInfoMapper;
import com.ule.uhj.app.zgd.dao.OrderInfoMapper;
import com.ule.uhj.app.zgd.dao.SaleActTicketTypeMapper;
import com.ule.uhj.app.zgd.dao.SaleTicketHistoryMapper;
import com.ule.uhj.app.zgd.dao.SaleTicketInfoMapper;
import com.ule.uhj.app.zgd.dao.SaleTicketOrderMapper;
import com.ule.uhj.app.zgd.dao.SaleTicketTypeMapper;
import com.ule.uhj.app.zgd.dao.ZgdWhiteMapper;
import com.ule.uhj.app.zgd.model.CustomerInfo;
import com.ule.uhj.app.zgd.model.CustomerInfoExample;
import com.ule.uhj.app.zgd.model.OrderInfo;
import com.ule.uhj.app.zgd.model.SaleActTicketType;
import com.ule.uhj.app.zgd.model.SaleActTicketTypeExample;
import com.ule.uhj.app.zgd.model.SaleTicketHistory;
import com.ule.uhj.app.zgd.model.SaleTicketHistoryExample;
import com.ule.uhj.app.zgd.model.SaleTicketInfo;
import com.ule.uhj.app.zgd.model.SaleTicketInfoExample;
import com.ule.uhj.app.zgd.model.SaleTicketOrder;
import com.ule.uhj.app.zgd.model.SaleTicketOrderExample;
import com.ule.uhj.app.zgd.model.SaleTicketType;
import com.ule.uhj.app.zgd.model.ZgdWhite;
import com.ule.uhj.app.zgd.model.ZgdWhiteExample;
import com.ule.uhj.app.zgd.service.CustomerInfoService;
import com.ule.uhj.app.zgd.util.DateUtil;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.app.zgd.util.VpsInfoService;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.app.ZgdAppClient;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.PropertiesHelper;

@Service("saleActTicketService")
public class SaleActTicketServiceImpl implements SaleActTicketService{
	private static Logger log = LoggerFactory.getLogger(SaleActTicketServiceImpl.class);

	@Autowired
	private CreditApplyMapper creditApplyMapper;
	
	@Autowired
	private SaleTicketInfoMapper saleTicketInfoMapper;
	
	@Autowired
	private SaleActTicketTypeMapper saleActTicketTypeMapper;
	
	@Autowired
	private SaleTicketTypeMapper saleTicketTypeMapper;
	
	@Autowired
	private SaleTicketHistoryMapper saleTicketHistoryMapper;
	
	@Autowired
	private SaleTicketOrderMapper saleTicketOrderMapper;
	
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	
	@Autowired
	private CustomerInfoService customerInfoService;
	
	@Autowired
	private ZgdWhiteMapper zgdWhiteMapper;
	
	private   final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	private   final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	/**
	 * 给掌柜发放邮利券
	 * @param request
	 * @return
	 */
	@Override
	public String saveSaleTicket(Map<String, Object> map) throws Exception {
		List<String> userList=(List<String>) map.get("userList");
		String code=Convert.toStr(map.get("code"));
		String batchno=Convert.toStr(map.get("batchno"));
		String name=Convert.toStr(map.get("name"));
		String ticketTYpeCode=Convert.toStr(map.get("ticketTYpeCode"));
		SaleActTicketTypeExample saleActTicketTypeExample =new SaleActTicketTypeExample();
		saleActTicketTypeExample.createCriteria().andActCodeEqualTo(code).andTicketTypeCodeEqualTo(ticketTYpeCode);
		List<SaleActTicketType> saleActTicketTypeList=saleActTicketTypeMapper.selectByExample(saleActTicketTypeExample);
		if(saleActTicketTypeList!=null && saleActTicketTypeList.size()>0){//取发放数量和营销活动关联表ID
			SaleActTicketType type =saleActTicketTypeList.get(0);
			BigDecimal number=type.getGrantQuantity();
			BigDecimal id=type.getId();
			//配置的发放数量和用户列表的数量做判断，发放的邮利券不能大于配置发放数量
			int array=Convert.toInt(number);
			if(array>userList.size()){
				array=userList.size();
			}
			for (int i = 0; i < array; i++) {
				String userOnlyId=userList.get(i);
				//TODO   给掌柜分配邮利券，还需完善
				SaleTicketInfoExample saleTicketInfoExample =new SaleTicketInfoExample();
				saleTicketInfoExample.createCriteria().andActTicketTypeIdEqualTo(id).andHolderEqualTo(userOnlyId);
				List<SaleTicketInfo> SaleTicketInfoList=saleTicketInfoMapper.selectByExample(saleTicketInfoExample);
				if(SaleTicketInfoList==null || SaleTicketInfoList.size()<=0){
					SaleTicketInfo info =new SaleTicketInfo();
//					info.setTicketno(ticketno);//
					info.setHolder(userOnlyId);
					info.setActTicketTypeId(id);//营销活动关联ID
//					info.setBatchno(batchno);//批次号
					info.setCreateTime(DateUtil.currTimeStr());
					info.setCreateUser(name);
					info.setEndorseCount(BigDecimal.ZERO);//转让次数
					info.setGenerator(name);//生成者
					info.setGeneratorType(ActTicketConstant.HOLDER_TYPE.ULE_YY);//生成者类型
					info.setGranter(name);//发放者
					info.setGranterType(ActTicketConstant.HOLDER_TYPE.ULE_YY);//发放者类型
					info.setHolder(userOnlyId);//当前持有者
					info.setHolderType(ActTicketConstant.HOLDER_TYPE.SHOP_OWNER);//当前持有者类型
					info.setLastEndorseDate(DateUtil.currDateStr());//最后领取或转让时间
//					info.setPrevHolder(prevHolder);//上一持有者
//					info.setPrevHolderType(prevHolderType);//上一持有者类型
					info.setStatus("1");//状态
//					info.setUpdateTime(updateTime);
//					info.setUpdateUser(updateUser);
					saleTicketInfoMapper.insertSelective(info);
				}else{
					//TODO   不确定可不可以发两张，可以发的话for循环要改
				}
			}
		}else{
			return JsonResult.getInstance().addError("没有查询到活动信息").toString();
		}
		return JsonResult.getInstance().addOk().toString();
	}
	
	/**
	 * 查询掌柜邮利券红点数量
	 * @param userOnlyId
	 * @return 邮利券红点数量
	 */
	@Override
	public Integer queryRedSpotsFlag(String userOnlyId) throws Exception {
		try{
			SaleTicketInfoExample saleTicketInfoExample =new SaleTicketInfoExample();
			saleTicketInfoExample.createCriteria().andHolderEqualTo(userOnlyId);//.andRedSpotsFlagEqualTo("0");
			List<SaleTicketInfo> SaleTicketInfoList=saleTicketInfoMapper.selectByExample(saleTicketInfoExample);
			if(SaleTicketInfoList!=null && SaleTicketInfoList.size()>0){
				log.info("queryRedSpotsFlag SaleTicketInfoList  userOnlyId:"+userOnlyId+"size:"+SaleTicketInfoList.size());
				return SaleTicketInfoList.size();
			}
		}catch(Exception e){
			log.error("queryRedSpotsFlag error userOnlyId:"+userOnlyId,e);
		}
		return 0;
	}

	@Override
	public SaleTicketInfo queryAvailableTransferTicket(
			Map<String, Object> map) throws Exception {
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String repayType=Convert.toStr(map.get("repayType"));
		BigDecimal applyAmount=Convert.toBigDecimal(map.get("applyAmount"));
		String periods=Convert.toStr(map.get("periods"));
		
		String province="";
		ZgdWhiteExample whiteExample =new ZgdWhiteExample();
		whiteExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<ZgdWhite> zgdWhitelist=zgdWhiteMapper.selectByExample(whiteExample);
		if(zgdWhitelist!=null && zgdWhitelist.size()>0){
			ZgdWhite white= zgdWhitelist.get(0);
			if(!Check.isBlank(white.getProvinceName())){
				province=white.getProvinceName();
			}
		}
		if(Check.isBlank(province)){
			Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
			province= Convert.toStr(vps.get(VpsInfoService.OrgProvinceName),"");
		}
		
		List<BigDecimal>  ticketTypeId=new ArrayList<BigDecimal>();
		SaleActTicketTypeExample saleActTicketTypeExample =new SaleActTicketTypeExample();
		saleActTicketTypeExample.createCriteria().andStatusEqualTo(ActTicketConstant.ticket_type_status.USABLE)
		.andRepayTypeLike("%"+repayType+"%").andApplyAmountGreaterThanOrEqualTo(applyAmount).andPeriodsLike("%"+periods+"%");
		saleActTicketTypeExample.setOrderByClause(" apply_Amount asc ");
		List<SaleActTicketType> saleActTicketTypeList=saleActTicketTypeMapper.selectByExample(saleActTicketTypeExample);
		if(saleActTicketTypeList!=null && saleActTicketTypeList.size()>0){
			log.info("queryAvailableTransferTicket SaleActTicketTypeList  userOnlyId:"+userOnlyId+"size:"+saleActTicketTypeList.size());
			for(SaleActTicketType type:saleActTicketTypeList){
				ticketTypeId.add(type.getId());
				SaleTicketInfoExample saleTicketInfoExample =new SaleTicketInfoExample();
				saleTicketInfoExample.createCriteria().andHolderNotEqualTo(userOnlyId).andStatusEqualTo(ActTicketConstant.STATUS.TRANSFERING)
				.andActTicketTypeIdIn(ticketTypeId).andEndDateGreaterThanOrEqualTo(DateUtil.currDateStr());
				List<SaleTicketInfo> SaleTicketInfoList=saleTicketInfoMapper.selectByExample(saleTicketInfoExample);
				if(SaleTicketInfoList!=null && SaleTicketInfoList.size()>0){
					log.info("queryAvailableTransferTicket SaleTicketInfoList  userOnlyId:"+userOnlyId+"SaleTicketInfo:"+SaleTicketInfoList.get(0));
					for(SaleTicketInfo info:SaleTicketInfoList){
						//先取受让规则判断是否可以受让
						String rule_url = PropertiesHelper.getDfs("RULE_SET_URL");
						JSONObject js =new JSONObject();
						JSONObject setmap = new JSONObject();
						setmap.put("ruleSetId", "tkt_endorsed_rule");
						setmap.put("userOnlyId", userOnlyId);
						setmap.put("loanAmount", Convert.toDouble(applyAmount));
//						setmap.put("channelCode", Convert.toDouble(applyAmount));
						setmap.put("OrgProvinceName", province);
						setmap.put("intrate", Convert.toDouble(map.get("yearRate")));
						js.put("data", setmap.toString());
						log.info("queryAvailableTransferTicket 受让规则 sendPostJson:"+js.toString());
						String res = com.ule.uhj.util.http.HttpClientUtil.sendPostJson(
								rule_url, js.toString(), UhjConstant.time_out);
						log.info("queryAvailableTransferTicket userOnlyId:"+userOnlyId+"; rule res:"+res);
						
						JSONObject resJs=JSONObject.fromObject(res);
						if("000000".equals(resJs.get("code"))){
							JSONObject object=(JSONObject) resJs.get("object");
							JSONArray data=object.getJSONArray("data");
							JSONObject strs=(JSONObject) data.toArray()[0];
							String ruleOutputResult=Convert.toStr(strs.get("ruleOutputResult"));
							log.info("queryAvailableTransferTicket 受让规则 userOnlyId:"+userOnlyId+";ruleOutputResult:"+ruleOutputResult);
							if("true".equals(ruleOutputResult)){
								return info;
							}
						}
					}
				}
			}
		}
		return null;
	}
	@Override
	public SaleTicketType querySaleTicketType(String ticketno)throws Exception {
		SaleTicketInfo info=saleTicketInfoMapper.selectByPrimaryKey(ticketno);
		if(info!=null){
			SaleActTicketType saleActTicketType = saleActTicketTypeMapper.selectByPrimaryKey(info.getActTicketTypeId());
			if(null != saleActTicketType) {
				SaleTicketType saleTicketType = saleTicketTypeMapper.selectByPrimaryKey(saleActTicketType.getTicketTypeCode());
				return saleTicketType;
			}
		}
		return null;
	}
	/**
	 * 逻辑很复杂，代码写的很low
	 */
	@Override
	public Map<String, Object> queryUsefulTickets4Mine(String userOnlyId,String flag) throws Exception {
		log.info("进入方法 queryUsefulTickets4Mine userOnlyId："+userOnlyId);
		List<SaleTicketInfo> result = new ArrayList<SaleTicketInfo>();
		Map<String, Object> map = new HashMap<String, Object>();
		int useableCount = 0;//可用的邮利券数量
		//查询该用户名下所有可用和不可用的邮利券
		List<SaleTicketInfo> list=null;
		if(flag!=null && "popUp".equals(flag)){
			SaleTicketInfoExample example = new SaleTicketInfoExample();
			example.createCriteria().andHolderEqualTo(userOnlyId).andHolderTypeEqualTo(ActTicketConstant.HOLDER_TYPE.SHOP_OWNER)
			.andStatusEqualTo(ActTicketConstant.STATUS.ACTIVATED).andPopUpEqualTo("0").andEndDateGreaterThanOrEqualTo(DateUtil.currDateStr());
			list = saleTicketInfoMapper.selectByExample(example);
			
			saleTicketInfoMapper.updatePopUpByUserOnlyId(userOnlyId);
		}else{
			SaleTicketInfoExample example = new SaleTicketInfoExample();
			example.createCriteria().andHolderEqualTo(userOnlyId).andHolderTypeEqualTo(ActTicketConstant.HOLDER_TYPE.SHOP_OWNER)
			.andStatusEqualTo(ActTicketConstant.STATUS.ACTIVATED).andEndDateGreaterThanOrEqualTo(DateUtil.currDateStr());
			list = saleTicketInfoMapper.selectByExample(example);
		}
		
		
		if(null != list && list.size() > 0) {
			log.info("查询当前用户所有的优惠券，数量为："+list.size());
			for(SaleTicketInfo info : list) {
				//查询活动关联信息	T_J_SALE_ACT_TICKET_TYPE
				SaleActTicketType saleActTicketType = saleActTicketTypeMapper.selectByPrimaryKey(info.getActTicketTypeId());
				//查询券种信息
				SaleTicketType saleTicketType = null;
				if(null != saleActTicketType) {
					info.setSaleActTicketType(saleActTicketType);
					saleTicketType = saleTicketTypeMapper.selectByPrimaryKey(saleActTicketType.getTicketTypeCode());
				}
				
				if(null != saleTicketType && null != saleActTicketType) {
					//去掉过期的券
					if(DATE_FORMAT.parse(info.getEndDate()).before(new Date())) {
						continue;
					}
					//去掉没资质的券
					String resultStr = checkTicketUsable(saleActTicketType, userOnlyId);
					if(StringUtils.isBlank(resultStr)) {
						continue;
					}
					info.setRuleOutputResult(resultStr);
					//设置券状态
					if(DATE_FORMAT.parse(info.getBeginDate()).before(new Date())
							&& DATE_FORMAT.parse(saleTicketType.getEndDate()).after(new Date())) {
						info.setState(ActTicketConstant.PAGE_TICKET_STATE.USABLE);
					}
					else {
						info.setState(ActTicketConstant.PAGE_TICKET_STATE.UNUSABLE);
					}
					//设置券类型
					if(ActTicketConstant.TICKET_TYPE_2.DISCOUNT_TICKET.equals(saleTicketType.getType2())) {
						info.setType(ActTicketConstant.PAGE_TICKET_TYPE.DISCOUNT_TICKET);
					}
					else if(ActTicketConstant.TICKET_TYPE_2.REDUCTION_TICKET.equals(saleTicketType.getType2())){
						info.setType(ActTicketConstant.PAGE_TICKET_TYPE.REDUCTION_TICKET);
					}
					//设置开始日期和结束日期
//					info.setBeginDate(saleTicketType.getBeginDate());
//					info.setEndDate(saleTicketType.getEndDate());
					//设置点数
//					info.setDiscount(saleTicketType.getOffValue().toString());
					info.setDiscount(saleTicketType.getOffValue().toString());
					info.setApplyAmountStr(saleActTicketType.getApplyAmount()+"元以下可用");
					info.setEndorseFlag(saleTicketType.getEndorseFlag());//券是否可转让 0-否 1-是
					//设置支用上限、使用规则说明
					//调用规则组，获得使用上限和规则说明
					String rule_url = PropertiesHelper.getDfs("RULE_SET_URL");
					String ruleSetId = saleActTicketType.getUseRule();
					JSONObject js =new JSONObject();
					JSONObject setmap = new JSONObject();
					setmap.put("ruleSetId", ruleSetId);
					setmap.put("applyAmount", 0);//支用金额
					setmap.put("repayType", "");//还款方式
					setmap.put("periods", "");//还款期数
					
					js.put("data", setmap.toString());
					log.info("执行当前"+info.getTicketno()+"的规则执行参数:"+js.toString());
					String res = com.ule.uhj.util.http.HttpClientUtil.sendPostJson(
							rule_url, js.toString(), UhjConstant.time_out);
					log.info("执行当前"+info.getTicketno()+"的规则执行结果是:"+res);
					String useRuleDesc = "";
					if(null != res) {
						JSONObject resJs=JSONObject.fromObject(res);
						if("000000".equals(resJs.get("code"))){
							JSONObject object=(JSONObject) resJs.get("object");
							JSONArray data=object.getJSONArray("data");
							for(int i = 0; i < data.size(); i++) {
								JSONObject resultObject = data.getJSONObject(i);
								if(null != resultObject) {
									String ruleOutputResult = resultObject.getString("ruleOutputResult");
									useRuleDesc += ruleOutputResult + ",";
								}
							}
						}
					}
					info.setUseRuleDesc(useRuleDesc);
					//设置最多节省钱数
					info.setSavingCount(saleActTicketType.getEstimatedCost().toString());
					//设置可用数量
					if(ActTicketConstant.PAGE_TICKET_STATE.USABLE.equals(info.getState())) {
						useableCount++;
					}
					result.add(info);
				}
			}
		}
		Collections.sort(result, new Comparator<SaleTicketInfo>() {
			@Override
			public int compare(SaleTicketInfo o1, SaleTicketInfo o2) {
				int a = 0;
				if(null != o1.getState() && null != o2.getState()) {
					a = Integer.parseInt(o1.getState()) - Integer.parseInt(o2.getState());
				}
				return -a;
			}
		});
		map.put("count", useableCount);
		map.put("list", result);
		log.info("退出方法 queryUsefulTickets4Mine");
		return map;
	}

	/**
	 * 逻辑很复杂，代码写的很low
	 */
	@Override
	public Map<String, Object> queryUsefulTickets4ZY(String userOnlyId, Map<String, Object> param) throws Exception {
		log.info("进入方法 queryUsefulTickets4ZY userOnlyId："+userOnlyId);
		Map<String, Object> map = new HashMap<String, Object>();
		int useableCount = 0;//可用的邮利券数量
		List<SaleTicketInfo> result = new ArrayList<SaleTicketInfo>();
		//查询该用户名下所有可用和不可用的邮利券
		SaleTicketInfoExample example = new SaleTicketInfoExample();
		example.createCriteria().andHolderEqualTo(userOnlyId).andHolderTypeEqualTo(ActTicketConstant.HOLDER_TYPE.SHOP_OWNER)
		.andStatusEqualTo(ActTicketConstant.STATUS.ACTIVATED).andEndDateGreaterThanOrEqualTo(DateUtil.currDateStr());
		List<SaleTicketInfo> list = saleTicketInfoMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			log.info("查询当前用户所有的优惠券，数量为："+list.size());
			for(SaleTicketInfo info : list) {
				//查询活动关联信息	T_J_SALE_ACT_TICKET_TYPE
				SaleActTicketType saleActTicketType = saleActTicketTypeMapper.selectByPrimaryKey(info.getActTicketTypeId());
				//查询券种信息
				SaleTicketType saleTicketType = null;
				if(null != saleActTicketType) {
					info.setSaleActTicketType(saleActTicketType);
					saleTicketType = saleTicketTypeMapper.selectByPrimaryKey(saleActTicketType.getTicketTypeCode());
				}
				
				if(null != saleTicketType && null != saleActTicketType) {
					//去掉过期的券
					if(DATE_FORMAT.parse(info.getEndDate()).before(new Date())) {
						continue;
					}
					//去掉没资质的券
					String resultStr = checkTicketUsable(saleActTicketType, userOnlyId);
					if(StringUtils.isBlank(resultStr)) {
						continue;
					}
					info.setRuleOutputResult(resultStr);
					//未到期设置不可用
					if(DATE_FORMAT.parse(info.getBeginDate()).after(new Date())) {
						info.setState(ActTicketConstant.PAGE_TICKET_STATE.UNUSABLE);
					}
					//设置券类型
					if(ActTicketConstant.TICKET_TYPE_2.DISCOUNT_TICKET.equals(saleTicketType.getType2())) {
						info.setType(ActTicketConstant.PAGE_TICKET_TYPE.DISCOUNT_TICKET);
					}
					else if(ActTicketConstant.TICKET_TYPE_2.REDUCTION_TICKET.equals(saleTicketType.getType2())){
						info.setType(ActTicketConstant.PAGE_TICKET_TYPE.REDUCTION_TICKET);
					}
					//设置开始日期和结束日期
//					info.setBeginDate(saleTicketType.getBeginDate());
//					info.setEndDate(saleTicketType.getEndDate());
					//设置点数
					info.setDiscount(saleTicketType.getOffValue().toString());
					info.setApplyAmountStr(saleActTicketType.getApplyAmount()+"元以下可用");
					info.setEndorseFlag(saleTicketType.getEndorseFlag());//券是否可转让 0-否 1-是
					//设置支用上限、使用规则说明
					//调用规则组，获得使用上限和规则说明
					String rule_url = PropertiesHelper.getDfs("RULE_SET_URL");
					String ruleSetId = saleActTicketType.getUseRule();
					JSONObject js =new JSONObject();
					JSONObject setmap = new JSONObject();
					setmap.put("ruleSetId", ruleSetId);
					setmap.put("applyAmount", Double.parseDouble(Convert.toStr(param.get("applyAmount"))));//支用期限
					setmap.put("repayType", Convert.toStr(param.get("repayType")));//还款方式
					setmap.put("periods", Convert.toStr(param.get("periods")));//还款期数
					
					js.put("data", setmap.toString());
					log.info("执行当前"+info.getTicketno()+"的规则执行参数:"+js.toString());
					String res = com.ule.uhj.util.http.HttpClientUtil.sendPostJson(
							rule_url, js.toString(), UhjConstant.time_out);
					log.info("执行当前"+info.getTicketno()+"的规则执行结果是:"+res);
					String useRuleDesc = "";
					String uselessRuleDesc = "";
					if(null != res) {
						JSONObject resJs=JSONObject.fromObject(res);
						if("000000".equals(resJs.get("code"))){
							JSONObject object=(JSONObject) resJs.get("object");
							JSONArray data=object.getJSONArray("data");
							for(int i = 0; i < data.size(); i++) {
								JSONObject resultObject = data.getJSONObject(i);
								if(null != resultObject) {
									//String setDecisionResult = resultObject.getString("setDecisionResult");//规则组表达式执行结果
									String ruleDecisionResult = resultObject.getString("ruleDecisionResult");//单规则表达式执行结果
									//String ruleId = resultObject.getString("ruleId");//单规则编号
									String ruleOutputResult = resultObject.getString("ruleOutputResult");//单规则执行结果
									String setOutputResult = resultObject.getString("setOutputResult");//规则组执行结果【目前只有一个规则】
									if(!ActTicketConstant.PAGE_TICKET_STATE.UNUSABLE.equals(info.getState())) {
										if("true".equals(setOutputResult)) {
											info.setState(ActTicketConstant.PAGE_TICKET_STATE.USABLE);
										}
										else {
											info.setState(ActTicketConstant.PAGE_TICKET_STATE.PART_USABLE);
											//设置不通过原因
											if("false".equals(ruleDecisionResult)) {
												uselessRuleDesc += ruleOutputResult + ",";
											}
										}
										
									}
									useRuleDesc += ruleOutputResult + ",";
								}
							}
						}
					}
					info.setUseRuleDesc(useRuleDesc);
					info.setUselessRuleDesc(uselessRuleDesc);
					//设置最多节省钱数
					info.setSavingCount(saleActTicketType.getEstimatedCost().toString());
					result.add(info);
					//设置可用数量
					if(ActTicketConstant.PAGE_TICKET_STATE.USABLE.equals(info.getState())) {
						useableCount++;
					}
				}
			}
		}
		//纯粹为了排序，不要多想
		Collections.sort(result, new Comparator<SaleTicketInfo>() {
			@Override
			public int compare(SaleTicketInfo o1, SaleTicketInfo o2) {
				int a = 0;
				if(null != o1.getState() && null != o2.getState()) {
					int temp1 = Integer.parseInt(o1.getState()) == 1 ? 3 : Integer.parseInt(o1.getState());
					int temp2 = Integer.parseInt(o2.getState()) == 1 ? 3 : Integer.parseInt(o2.getState());
					a = temp1 - temp2;
				}
				return -a;
			}
		});
		map.put("count", useableCount);
		map.put("list", result);
		log.info("退出方法 queryUsefulTickets4Mine");
		return map;
	}

	@Override
	public List<SaleTicketInfo> queryTransferingTickets(String userOnlyId) throws Exception {
		List<SaleTicketInfo> list = new ArrayList<SaleTicketInfo>();
		//查询自己转让中
		SaleTicketInfoExample example = new SaleTicketInfoExample();
		example.createCriteria().andHolderTypeEqualTo(ActTicketConstant.HOLDER_TYPE.SHOP_OWNER).andHolderEqualTo(userOnlyId)
		.andStatusEqualTo(ActTicketConstant.STATUS.TRANSFERING).andEndDateGreaterThanOrEqualTo(DateUtil.currDateStr());
		list = saleTicketInfoMapper.selectByExample(example);
		//查询自己转让且别人使用中
		List<SaleTicketInfo> list2 = saleTicketInfoMapper.selectUsingTickets(userOnlyId);
		if(null != list2 && list2.size() > 0) {
			list.addAll(list2);
		}
		
		if(null != list && list.size() > 0) {
			log.info("查询当前用户转让中的优惠券，数量为："+list.size());
			for(SaleTicketInfo info : list) {
				
				//查询活动关联信息	T_J_SALE_ACT_TICKET_TYPE
				SaleActTicketType saleActTicketType = saleActTicketTypeMapper.selectByPrimaryKey(info.getActTicketTypeId());
				//查询券种信息
				SaleTicketType saleTicketType = null;
				if(null != saleActTicketType) {
					saleTicketType = saleTicketTypeMapper.selectByPrimaryKey(saleActTicketType.getTicketTypeCode());
				}
				
				if(null != saleTicketType && null != saleActTicketType) {
					//设置券类型
					if(ActTicketConstant.TICKET_TYPE_2.DISCOUNT_TICKET.equals(saleTicketType.getType2())) {
						info.setType(ActTicketConstant.PAGE_TICKET_TYPE.DISCOUNT_TICKET);
					}
					else if(ActTicketConstant.TICKET_TYPE_2.REDUCTION_TICKET.equals(saleTicketType.getType2())){
						info.setType(ActTicketConstant.PAGE_TICKET_TYPE.REDUCTION_TICKET);
					}
					//设置开始日期和结束日期
//					info.setBeginDate(saleTicketType.getBeginDate());
//					info.setEndDate(saleTicketType.getEndDate());
					//设置点数
					info.setDiscount(saleTicketType.getOffValue().toString());
					info.setApplyAmountStr("支用"+saleActTicketType.getApplyAmount()+"元以下可用");
					//设置支用上限、使用规则说明
					//调用规则组，获得使用上限和规则说明
					String rule_url = PropertiesHelper.getDfs("RULE_SET_URL");
					String ruleSetId = saleActTicketType.getUseRule();
					JSONObject js =new JSONObject();
					JSONObject setmap = new JSONObject();
					setmap.put("ruleSetId", ruleSetId);
					setmap.put("applyAmount", 0);//支用期限
					setmap.put("repayType", "");//还款方式
					setmap.put("periods", "");//还款期数
					
					js.put("data", setmap.toString());
					log.info("执行当前"+info.getTicketno()+"的规则执行参数:"+js.toString());
					String res = com.ule.uhj.util.http.HttpClientUtil.sendPostJson(
							rule_url, js.toString(), UhjConstant.time_out);
					log.info("执行当前"+info.getTicketno()+"的规则执行结果是:"+res);
					String useRuleDesc = "";
					if(null != res) {
						JSONObject resJs=JSONObject.fromObject(res);
						if("000000".equals(resJs.get("code"))){
							JSONObject object=(JSONObject) resJs.get("object");
							JSONArray data=object.getJSONArray("data");
							for(int i = 0; i < data.size(); i++) {
								JSONObject resultObject = data.getJSONObject(i);
								if(null != resultObject) {
									String ruleOutputResult = resultObject.getString("ruleOutputResult");
									useRuleDesc += ruleOutputResult + ",";
								}
							}
						}
					}
					info.setUseRuleDesc(useRuleDesc);
					//设置最多节省钱数
					info.setSavingCount(saleActTicketType.getEstimatedCost().toString());
				}
			}
		}
		return list;
	}

	@Override
	public List<Map<String, String>> queryTransferedTickets(String userOnlyId) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		SaleTicketHistoryExample example = new SaleTicketHistoryExample();
		example.createCriteria().andPrevHolderEqualTo(userOnlyId).andPrevHolderTypeEqualTo(ActTicketConstant.HOLDER_TYPE.SHOP_OWNER)
		.andStatusEqualTo(ActTicketConstant.STATUS.TRANSFED);
		example.setOrderByClause(" create_time desc");
		List<SaleTicketHistory> historyList = saleTicketHistoryMapper.selectByExample(example);
		if(null != historyList && historyList.size() > 0) {
			for(SaleTicketHistory history : historyList) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("transferDate", history.getCreateTime());
				//查询券种信息
				SaleTicketInfoExample infoExample = new SaleTicketInfoExample();
				infoExample.createCriteria().andTicketnoEqualTo(history.getTicketno());
				List<SaleTicketInfo> ticketInfoList = saleTicketInfoMapper.selectByExample(infoExample);
				if(null != ticketInfoList && ticketInfoList.size() > 0) {
					String ticketDesc = "";
					SaleTicketInfo saleTicketInfo = ticketInfoList.get(0);
					SaleActTicketType saleActTicketType = saleActTicketTypeMapper.selectByPrimaryKey(saleTicketInfo.getActTicketTypeId());
					SaleTicketType saleTicketType = null;
					if(null != saleActTicketType) {
						saleTicketType = saleTicketTypeMapper.selectByPrimaryKey(saleActTicketType.getTicketTypeCode());
						if(ActTicketConstant.TICKET_TYPE_2.DISCOUNT_TICKET.equals(saleTicketType.getType2())) {
							ticketDesc = (saleTicketType.getOffValue().multiply(new BigDecimal("10"))).stripTrailingZeros().toPlainString()+"折利率折扣券";
						}
						else if(ActTicketConstant.TICKET_TYPE_2.REDUCTION_TICKET.equals(saleTicketType.getType2())){
							ticketDesc = saleTicketType.getOffValue()+"%利率降息券";
						}
					}
					map.put("ticketDesc", ticketDesc);
				}
				//查询订单信息
				SaleTicketOrderExample orderExample = new SaleTicketOrderExample();
				orderExample.createCriteria().andTicketnoEqualTo(history.getTicketno())
				.andTicketTypeEqualTo(ActTicketConstant.TICKET_ORDER_TYPE.TICKET)
				.andStatusEqualTo(ActTicketConstant.TICKET_ORDER_STATUS.SUCCESS);
				List<SaleTicketOrder> ticketOrderList = saleTicketOrderMapper.selectByExample(orderExample);
				if(null != ticketOrderList && ticketOrderList.size() > 0) {
					String amount = "";
					OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(ticketOrderList.get(0).getOrderId());
					if(null != orderInfo) {
						amount = orderInfo.getOrderAmount().toString();
					}
					map.put("amount", amount);
				}
				list.add(map);
			}
		}
		return list;
	}

	@Override
	public List<Map<String, String>> queryBuyedTickets(String userOnlyId) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		SaleTicketHistoryExample example = new SaleTicketHistoryExample();
		example.createCriteria().andHolderEqualTo(userOnlyId).andHolderTypeEqualTo(ActTicketConstant.HOLDER_TYPE.SHOP_OWNER)
		.andStatusEqualTo(ActTicketConstant.STATUS.USED);
		example.setOrderByClause(" create_time desc");
		List<SaleTicketHistory> historyList = saleTicketHistoryMapper.selectByExample(example);
		if(null != historyList && historyList.size() > 0) {
			for(SaleTicketHistory history : historyList) {
				if(history.getPrevHolder() == null) {
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("transferDate", history.getCreateTime());
				//查询券种信息
				SaleTicketInfoExample infoExample = new SaleTicketInfoExample();
				infoExample.createCriteria().andTicketnoEqualTo(history.getTicketno());
				List<SaleTicketInfo> ticketInfoList = saleTicketInfoMapper.selectByExample(infoExample);
				if(null != ticketInfoList && ticketInfoList.size() > 0) {
					String ticketDesc = "";
					SaleTicketInfo saleTicketInfo = ticketInfoList.get(0);
					SaleActTicketType saleActTicketType = saleActTicketTypeMapper.selectByPrimaryKey(saleTicketInfo.getActTicketTypeId());
					SaleTicketType saleTicketType = null;
					if(null != saleActTicketType) {
						saleTicketType = saleTicketTypeMapper.selectByPrimaryKey(saleActTicketType.getTicketTypeCode());
						if(ActTicketConstant.TICKET_TYPE_2.DISCOUNT_TICKET.equals(saleTicketType.getType2())) {
							ticketDesc = (saleTicketType.getOffValue().multiply(new BigDecimal("10"))).stripTrailingZeros().toPlainString()+"折利率折扣券";
						}
						else if(ActTicketConstant.TICKET_TYPE_2.REDUCTION_TICKET.equals(saleTicketType.getType2())){
							ticketDesc = saleTicketType.getOffValue()+"%利率降息券";
						}
					}
					map.put("ticketDesc", ticketDesc);
				}
				//查询券订单信息
				SaleTicketOrderExample orderExample = new SaleTicketOrderExample();
				orderExample.createCriteria().andTicketnoEqualTo(history.getTicketno()).andStatusEqualTo(ActTicketConstant.TICKET_ORDER_STATUS.SUCCESS);
				List<SaleTicketOrder> ticketOrderList = saleTicketOrderMapper.selectByExample(orderExample);
				if(null != ticketOrderList && ticketOrderList.size() > 0) {
					for(SaleTicketOrder ticketOrder : ticketOrderList) {
						if(ActTicketConstant.TICKET_ORDER_TYPE.TICKET.equals(ticketOrder.getTicketType())) {
							OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(ticketOrder.getOrderId());
							if(null != orderInfo) {
								map.put("buyAmount", orderInfo.getOrderAmount().toString());
							}
						}
						else if(ActTicketConstant.TICKET_ORDER_TYPE.CASH.equals(ticketOrder.getTicketType())) {
							OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(ticketOrder.getOrderId());
							if(null != orderInfo) {
								map.put("loanAmount", orderInfo.getOrderAmount().toString());
								map.put("orderId", orderInfo.getId());
							}
						}
					}
					
				}
				
				list.add(map);
			}
		}
		return list;
	}

	@Override
	public void transferTicket(String ticketNo, String userOnlyId) throws Exception {
		log.info("transferTicket ticketNo:"+ticketNo+", userOnlyId : "+userOnlyId);
		//先查询该用户是否开户
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userOnlyId", userOnlyId);
		String result = AccountMoneyUtil.isOpenAccount(paraMap);
		log.info("账号开户信息是："+result);
		if(null != result) {
			JSONObject json = JSONObject.fromObject(result);
			if(null != json && json.containsKey("code")) {
				if(!"0000".equals(json.getString("code"))) {
					throw new Exception(json.getString("returnMsg"));
				}
				else {
					if(!json.toString().contains("A004")) {
						throw new Exception("NOT_OPEN");
					}
				}
			}
		}
		//先查询是否已经转让
		SaleTicketInfoExample infoExample = new SaleTicketInfoExample();
		infoExample.createCriteria().andTicketnoEqualTo(ticketNo)
		.andHolderTypeEqualTo(ActTicketConstant.HOLDER_TYPE.SHOP_OWNER)
		.andHolderEqualTo(userOnlyId)
		.andStatusEqualTo(ActTicketConstant.STATUS.TRANSFERING);
		List<SaleTicketInfo> infoList = saleTicketInfoMapper.selectByExample(infoExample);
		if(null != infoList && infoList.size() > 0) {
			throw new Exception("您已转让该券");
		}
		//判断该券是否支持转让
		SaleTicketInfoExample ccexample = new SaleTicketInfoExample();
		ccexample.createCriteria().andTicketnoEqualTo(ticketNo);
		List<SaleTicketInfo> list = saleTicketInfoMapper.selectByExample(ccexample);
		if(null != list && list.size() > 0) {
			SaleTicketInfo info=list.get(0);
			//查询活动关联信息	T_J_SALE_ACT_TICKET_TYPE
			SaleActTicketType saleActTicketType = saleActTicketTypeMapper.selectByPrimaryKey(info.getActTicketTypeId());
			//查询券种信息
			SaleTicketType saleTicketType = null;
			if(null != saleActTicketType) {
				saleTicketType = saleTicketTypeMapper.selectByPrimaryKey(saleActTicketType.getTicketTypeCode());
				if(null != saleTicketType && null != saleActTicketType) {
					if("0".equals(saleTicketType.getEndorseFlag())){
						throw new Exception("该券不支持转让");
					}
				}
			}
		}
		
		if(null != ticketNo) {
			SaleTicketInfo info = new SaleTicketInfo();
			info.setTicketno(ticketNo);
			info.setStatus(ActTicketConstant.STATUS.TRANSFERING);
			info.setUpdateTime(TIME_FORMAT.format(new Date()));
			info.setUpdateUser(userOnlyId);
			saleTicketInfoMapper.updateByPrimaryKeySelective(info);
			log.info("更改券状态为转让中成功,userOnlyId："+userOnlyId);
			//插入一条历史数据
			SaleTicketHistory historyInfo = new SaleTicketHistory();
			//先查询出之前最新的一条记录
			SaleTicketHistoryExample example = new SaleTicketHistoryExample();
			example.createCriteria().andTicketnoEqualTo(ticketNo);
			example.setOrderByClause(" create_time desc");
			List<SaleTicketHistory> historyList = saleTicketHistoryMapper.selectByExample(example);
			if(null != historyList && historyList.size() > 0) {
				SaleTicketHistory history = historyList.get(0);
				historyInfo.setHistoryId(history.getId());
				historyInfo.setPrevHolderType(ActTicketConstant.HOLDER_TYPE.SHOP_OWNER);
				historyInfo.setPrevHolder(userOnlyId);
			}
			historyInfo.setHolderType(ActTicketConstant.HOLDER_TYPE.SHOP_OWNER);
			historyInfo.setHolder(userOnlyId);
			historyInfo.setEndorseDate(DATE_FORMAT.format(new Date()));
			historyInfo.setTicketno(ticketNo);
			historyInfo.setCreateTime(TIME_FORMAT.format(new Date()));
			historyInfo.setTransType(ActTicketConstant.TRANS_TYPE.TRANSFERING);
			historyInfo.setStatus(ActTicketConstant.STATUS.TRANSFERING);
			saleTicketHistoryMapper.insertSelective(historyInfo);
			log.info("插入新的转让历史记录成功,userOnlyId："+userOnlyId);
		}
	}

	@Override
	public void cancelTransTicket(String ticketNo, String userOnlyId) throws Exception {
		log.info("cancelTransTicket ticketNo:"+ticketNo+", userOnlyId : "+userOnlyId);
		if(null != ticketNo) {
			//先查询是否已经取消转让
			SaleTicketInfoExample infoExample = new SaleTicketInfoExample();
			infoExample.createCriteria().andStatusEqualTo(ActTicketConstant.STATUS.ACTIVATED)
			.andTicketnoEqualTo(ticketNo)
			.andHolderTypeEqualTo(ActTicketConstant.HOLDER_TYPE.SHOP_OWNER)
			.andHolderEqualTo(userOnlyId);
			List<SaleTicketInfo> list = saleTicketInfoMapper.selectByExample(infoExample);
			if(null != list && list.size() > 0) {
				throw new Exception("您已取消转让该券");
			}
			//先查询是否可以取消转让
			SaleTicketInfoExample infoExample2 = new SaleTicketInfoExample();
			infoExample2.createCriteria().andStatusEqualTo(ActTicketConstant.STATUS.USING)
			.andHolderTypeEqualTo(ActTicketConstant.HOLDER_TYPE.SHOP_OWNER)
			.andTicketnoEqualTo(ticketNo)
			.andHolderEqualTo(userOnlyId);
			List<SaleTicketInfo> list2 = saleTicketInfoMapper.selectByExample(infoExample2);
			if(null != list2 && list2.size() > 0) {
				throw new Exception("该券使用中，不能取消转让！");
			}
			
			SaleTicketInfo info = new SaleTicketInfo();
			info.setTicketno(ticketNo);
			info.setStatus(ActTicketConstant.STATUS.ACTIVATED);
			info.setUpdateTime(TIME_FORMAT.format(new Date()));
			info.setUpdateUser(userOnlyId);
			saleTicketInfoMapper.updateByPrimaryKeySelective(info);
			log.info("更改券状态为已激活成功,userOnlyId："+userOnlyId);
			//插入一条历史数据
			SaleTicketHistory historyInfo = new SaleTicketHistory();
			//先查询出之前最新的一条记录
			SaleTicketHistoryExample example = new SaleTicketHistoryExample();
			example.createCriteria().andTicketnoEqualTo(ticketNo);
			example.setOrderByClause(" create_time desc");
			List<SaleTicketHistory> historyList = saleTicketHistoryMapper.selectByExample(example);
			if(null != historyList && historyList.size() > 0) {
				SaleTicketHistory history = historyList.get(0);
				historyInfo.setHistoryId(history.getId());
				historyInfo.setPrevHolderType(ActTicketConstant.HOLDER_TYPE.SHOP_OWNER);
				historyInfo.setPrevHolder(userOnlyId);
			}
			historyInfo.setHolderType(ActTicketConstant.HOLDER_TYPE.SHOP_OWNER);
			historyInfo.setHolder(userOnlyId);
			historyInfo.setEndorseDate(DATE_FORMAT.format(new Date()));
			historyInfo.setTicketno(ticketNo);
			historyInfo.setCreateTime(TIME_FORMAT.format(new Date()));
			historyInfo.setTransType(ActTicketConstant.TRANS_TYPE.CANCEL_TRANSFERING);
			historyInfo.setStatus(ActTicketConstant.STATUS.ACTIVATED);
			saleTicketHistoryMapper.insertSelective(historyInfo);
			log.info("插入新的取消转让历史记录成功,userOnlyId："+userOnlyId);
		}
	}

	@Override
	public void openAccount(String userOnlyId) throws Exception {
		String userName = "";
		CustomerInfoExample example = new CustomerInfoExample();
		example.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CustomerInfo> list = customerInfoMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			userName = list.get(0).getCustomerName();
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userOnlyId", userOnlyId);
		paraMap.put("userName", userName);
		String result = AccountMoneyUtil.openAccount(paraMap);
		if(null != result) {
			JSONObject json = JSONObject.fromObject(result);
			if(null != json && json.containsKey("code")) {
				if(!"0000".equals(json.getString("code"))) {
					throw new Exception(json.getString("returnMsg"));
				}
			}
		}
	}

	@Override
	public Map<String, String> findAccRuleExeInfo(String userOnlyId) throws Exception {
		Map<String, String> resultMap = new HashMap<String,String>();
		//查询用户基础信息
		log.info("findAccRuleExeInfo userOnlyId:"+userOnlyId);
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("userOnlyId", userOnlyId);
		String result = WildflyBeanFactory.getZgdAppClient().queryAppAccOverview(param);
		log.info("findAccRuleExeInfo userOnlyId:"+userOnlyId+";result:"+result);
		
		JSONObject resultJs=JSONObject.fromObject(result);
		//执行规则获取该用户的支用规则
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userOnlyId", userOnlyId);
		map.put("certType", UhjConstant.certType.idcard);
		map=customerInfoService.queryCustomerInfo(map);
		String channelCode=Convert.toStr(map.get("channelCode"));
		if(Check.isBlank(channelCode)){
			channelCode="C0001";
		}
		String useType ="1";//产品用途是现金
		String ruleSetId = "storezgdSet001";
		Integer dueDay=Convert.toInt(resultJs.get("dueDay"),0);
		Integer dueNumber=Convert.toInt(resultJs.get("dueNumber"),0);
		String rule_url = PropertiesHelper.getDfs("RULE_SET_URL");
		JSONObject js =new JSONObject();
		JSONObject setmap = new JSONObject();
		setmap.put("ruleSetId", ruleSetId);
		setmap.put("iFirstLoanBeforeTodayNum", dueDay);
		setmap.put("userOnlyId", userOnlyId);
		setmap.put("channelCode", channelCode);
		setmap.put("useType", useType);
		setmap.put("dueNumber", dueNumber);//支用笔数
		try{
			setmap.put("c1_360", Convert.toDouble(resultJs.get("c1_360"),0.00));
			setmap.put("c2_360", Convert.toDouble(resultJs.get("c2_360"),0.00));
			setmap.put("c3_360", Convert.toDouble(resultJs.get("c3_360"),0.00));
			setmap.put("c1_200", Convert.toDouble(resultJs.get("c1_200"),0.00));
			setmap.put("c2_200", Convert.toDouble(resultJs.get("c2_200"),0.00));
			setmap.put("c3_200", Convert.toDouble(resultJs.get("c3_200"),0.00));
			setmap.put("creditLimit", Convert.toDouble(resultJs.get("creditLimit"),999999.00));
			setmap.put("totalRepayAmount360", Convert.toDouble(resultJs.get("totalRepayAmount360"),0.00));
			setmap.put("totalRepayAmount200", Convert.toDouble(resultJs.get("totalRepayAmount200"),0.00));
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		js.put("data", setmap.toString());
		log.info("findAccRuleExeInfo sendPostJson:"+js.toString());
		String res = com.ule.uhj.util.http.HttpClientUtil.sendPostJson(
				rule_url, js.toString(), UhjConstant.time_out);
		log.info("findAccRuleExeInfo userOnlyId:"+userOnlyId+"; rule result:"+res);
		
		JSONObject resJs=JSONObject.fromObject(res);
		if("000000".equals(resJs.get("code"))){
			JSONObject object=(JSONObject) resJs.get("object");
			JSONArray data=object.getJSONArray("data");
			JSONObject strs=(JSONObject) data.toArray()[0];
			String ruleOutputResult=Convert.toStr(strs.get("ruleOutputResult"));
			log.info("findAccRuleExeInfo最终执行结果为 userOnlyId:"+userOnlyId+";ruleOutputResult:"+ruleOutputResult);
			if(!StringUtils.isBlank(ruleOutputResult)) {
				//3:01,02;6:02;9:02;12:02;
				String[] tempArray = ruleOutputResult.split(";");
				for(String temp : tempArray) {
					String periods = temp.split(":")[0];
					String repayType = temp.split(":")[1];
					resultMap.put(periods, repayType);
				}
			}
		}
		return resultMap;
	}

	/**
	 * 检查确认该用户的券使用状态【可用 or不可用】
	 * @param type
	 * @param userOnlyId
	 * @return 如果没有资质则返回 null,否则返回拼接的结果如：3:01,02;6:02;9:02;12:02;
	 * @throws Exception 
	 */
	private String checkTicketUsable(SaleActTicketType type, String userOnlyId) throws Exception {
		String result = "";
		String repayType = type.getRepayType();
		String periods = type.getPeriods();
		Map<String, String> map = findAccRuleExeInfo(userOnlyId);
		log.info("-------------------检查结果的map："+map+"---当前repayType："+repayType+", periods："+periods);
		String[] periodArray = periods.replace("，", ",").split(",");
		String[] repayTypeArray = repayType.replace("，", ",").split(",");
		for(String period : periodArray) {
			if(map.containsKey(period)) {
				String repayTypeRule = map.get(period);
				for(String repayTypeTemp : repayTypeArray) {
					if(repayTypeRule.contains(repayTypeTemp)) {
						String temp = period + ":" + (repayType.length() >= repayTypeRule.length() ? repayTypeRule : repayType) +";";
						result += temp;
						break;
					}
				}
			}
		}
		log.info("#########返回结果是："+result);
		return result;
	}
}
