package com.ule.uhj.app.yzs.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.yzs.service.YzsPostmemberService;
import com.ule.uhj.app.zgd.dao.AccountInfoMapper;
import com.ule.uhj.app.zgd.dao.ApplyDetailMapper;
import com.ule.uhj.app.zgd.dao.CreditApplyMapper;
import com.ule.uhj.app.zgd.dao.CreditAuditMapper;
import com.ule.uhj.app.zgd.dao.CreditPostmemberMapper;
import com.ule.uhj.app.zgd.dao.CreditRuleMapper;
import com.ule.uhj.app.zgd.dao.CustomerCertMapper;
import com.ule.uhj.app.zgd.dao.CustomerInfoMapper;
import com.ule.uhj.app.zgd.dao.CustomerPhoneMapper;
import com.ule.uhj.app.zgd.dao.OrderInfoMapper;
import com.ule.uhj.app.zgd.dao.RuleExeSetMapper;
import com.ule.uhj.app.zgd.dao.UserInfoMapper;
import com.ule.uhj.app.zgd.model.AccountInfo;
import com.ule.uhj.app.zgd.model.AccountInfoExample;
import com.ule.uhj.app.zgd.model.ApplyDetail;
import com.ule.uhj.app.zgd.model.ApplyDetailExample;
import com.ule.uhj.app.zgd.model.CreditApply;
import com.ule.uhj.app.zgd.model.CreditApplyExample;
import com.ule.uhj.app.zgd.model.CreditAudit;
import com.ule.uhj.app.zgd.model.CreditPostmember;
import com.ule.uhj.app.zgd.model.CreditPostmemberExample;
import com.ule.uhj.app.zgd.model.CreditRule;
import com.ule.uhj.app.zgd.model.CreditRuleExample;
import com.ule.uhj.app.zgd.model.CustomerCert;
import com.ule.uhj.app.zgd.model.CustomerCertExample;
import com.ule.uhj.app.zgd.model.CustomerInfo;
import com.ule.uhj.app.zgd.model.CustomerInfoExample;
import com.ule.uhj.app.zgd.model.CustomerPhone;
import com.ule.uhj.app.zgd.model.CustomerPhoneExample;
import com.ule.uhj.app.zgd.model.OrderInfo;
import com.ule.uhj.app.zgd.model.OrderInfoExample;
import com.ule.uhj.app.zgd.model.RuleExeSet;
import com.ule.uhj.app.zgd.model.RuleExeSetExample;
import com.ule.uhj.app.zgd.model.UserInfo;
import com.ule.uhj.app.zgd.model.UserInfoExample;
import com.ule.uhj.app.zgd.util.DateUtil;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.app.zgd.util.VpsInfoService;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.ycZgd.YCZgdQueryClient;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.DesUtil;
import com.ule.uhj.util.JsonResult;

@Service("yzsPostmemberService")
public class YzsPostmemberServiceImpl implements YzsPostmemberService {

	private static Log log = LogFactory.getLog(YzsPostmemberServiceImpl.class);
	
	@Autowired
	private CreditRuleMapper creditRuleMapper;
	@Autowired
	private CreditPostmemberMapper creditPostMapper;
	@Autowired
	private AccountInfoMapper accountInfoMapper;
	@Autowired
	private CreditApplyMapper creditApplyMapper;
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	@Autowired
	private CustomerCertMapper customerCertMapper;
	@Autowired
	private CustomerPhoneMapper customerPhoneMapper;
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private ApplyDetailMapper applyDetailMapper;
	@Autowired
    private CreditAuditMapper creditAuditMapper;
    @Autowired
    private RuleExeSetMapper ruleExeSetMapper;
	

	
	@Override
	public Map<String, Object> queryCreditPostMemberByBzgId(String bzgId) throws Exception {
		log.info("queryCreditPostMemberByBzgId bzgId:"+bzgId);
		Map<String, Object> map =new HashMap<String, Object>();
		CreditPostmemberExample example = new CreditPostmemberExample();
		example.createCriteria().andStaffIdEqualTo(bzgId);
		List<CreditPostmember> creditPostmember = creditPostMapper.selectByExample(example);
		if(creditPostmember!=null&&creditPostmember.size()>0){
			map.put("name", creditPostmember.get(0).getName());
			map.put("certNo", creditPostmember.get(0).getCertNo());
			return map;
		}
		return null;
	}
	@Override
	public String queryYzsPostmemberStatus(String bzgId) throws Exception {
		log.info("queryYzsPostmemberStatus bzgId:"+bzgId);
		String status="0";
		CreditRuleExample ruleExample =new CreditRuleExample();
		ruleExample.createCriteria().andUserOnlyIdEqualTo(bzgId).andRuleRefIdEqualTo(UhjConstant.ruleRefId.yzs_postmember_face);
		List<CreditRule> rulelist=creditRuleMapper.selectByExample(ruleExample);
		if(rulelist!=null && rulelist.size()>0){
			String outPut=rulelist.get(0).getRuleOutput();
			if("true".equals(outPut)){
				status="1";
			}
		}
		return status;
	}
	//status 0 未开通   3 审核中   、4、审核退回 、 5  审核拒绝  、10 额度到期  、11、暂无借款、12逾期、13 还款中、14冻结
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public String queryYzsPostmemberInfo(String bzgId) throws Exception {
		//未开通
		List<Map<String, Object>> notOpenedList=new ArrayList<Map<String,Object>>();
		//开通中
		List<Map<String, Object>> processOpenedList=new ArrayList<Map<String,Object>>();
		//已经开通
		List<Map<String, Object>> alreadyOpenedList=new ArrayList<Map<String,Object>>();
		
		//去帮掌柜系统查询帮掌柜ID下所有的掌柜用户ID
		Map<String, Object> bzgUserMap=VpsInfoService.queryUserListByBzgId(bzgId);
		List<String> bzgUserList =(List<String>) bzgUserMap.get("list");
		
		String orgProvince="";
		String orgCity ="";
		String orgArea="";
		String orgTown="";
		//在掌柜贷系统查询帮掌柜ID下所有的掌柜用户ID
		List<String> zgdUserList =new ArrayList<String>();
		CreditPostmemberExample example = new CreditPostmemberExample();
		example.createCriteria().andStaffIdEqualTo(bzgId);
		List<CreditPostmember> creditPostmemberList = creditPostMapper.selectByExample(example);
		if(creditPostmemberList!=null&&creditPostmemberList.size()>0){
			for(CreditPostmember bzg:creditPostmemberList){
				zgdUserList.add(bzg.getUserOnlyId());
				orgProvince=bzg.getOrgProvince();
				orgCity=bzg.getOrgCity();
				orgArea=bzg.getOrgArea();
				orgTown=bzg.getOrgTown();
			}
		}
		
		if(!Check.isBlank(orgTown) && !Check.isBlank(orgArea) && !Check.isBlank(orgCity) && !Check.isBlank(orgProvince)){
			//只绑定到支局的用户，相同支局的地推都可以看到此掌柜
			CreditPostmemberExample creditPostmemberExample = new CreditPostmemberExample();
			creditPostmemberExample.createCriteria().andOrgProvinceEqualTo(orgProvince).andOrgCityEqualTo(orgCity)
			.andOrgAreaEqualTo(orgArea).andOrgTownEqualTo(orgTown).andJobNoIsNull().andStaffIdIsNull().andMobileIsNull();
			List<CreditPostmember> creditList = creditPostMapper.selectByExample(creditPostmemberExample);
			if(creditList!=null&&creditList.size()>0){
				for(CreditPostmember bzg:creditList){
					zgdUserList.add(bzg.getUserOnlyId());
				}
			}
		}
		
		//bzgUserList、zgdUserList合并去重
		if(zgdUserList==null){
			zgdUserList=bzgUserList;
		}else if(bzgUserList!=null){
			zgdUserList.addAll(bzgUserList);
		}
		List<String>  newList = new ArrayList<String>(new HashSet<String>(zgdUserList)); //去重
		if(newList!=null && newList.size()>0)
		for(String userOnlyId:newList){
			if(!Check.isBlank(userOnlyId)){
				CreditApplyExample applyExample =new CreditApplyExample();
				applyExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
				List<CreditApply> creditApplyList=creditApplyMapper.selectByExample(applyExample);
				
				AccountInfoExample accountInfoExample = new AccountInfoExample();
				accountInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
				List<AccountInfo> accountInfoList = accountInfoMapper.selectByExample(accountInfoExample);
				if((accountInfoList!= null && accountInfoList.size()>0) || 
						(creditApplyList!=null && creditApplyList.size()>0) 
//							|| (!Check.isBlank(accountInfoList.get(0).getCreditLimit()) 
//									&& accountInfoList.get(0).getCreditLimit().compareTo(BigDecimal.ZERO)>0)
									){//做过申请的用户或者有过额度的用户
					Map<String, Object> processOpenedMap=new HashMap<String, Object>();
					Map<String, Object> alreadyOpenedMap=new HashMap<String, Object>();
					if(accountInfoList!= null && accountInfoList.size()>0){
						AccountInfo account=accountInfoList.get(0);
						BigDecimal loanBalance=account.getCreditLimit().subtract(account.getBanlance());
						String applyStatus=Convert.toStr(account.getApplyStatus(),"0");
						if("5".equals(applyStatus)){
							String auditMessage="";
							ApplyDetailExample detailExample = new ApplyDetailExample();
							detailExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
							List<ApplyDetail> detailList = applyDetailMapper.selectByExample(detailExample);
							if(detailList!=null && detailList.size()>0){		
								auditMessage=detailList.get(0).getRefusalReason();
							}
							String res=WildflyBeanFactory.getYCZgdQueryClient().queryUserMessage(userOnlyId);
							JSONObject js=JSONObject.fromObject(res);
							String reactivatedDate=Convert.toStr(js.get("reactivatedDate"));
							Map<String, Object> paramer=new HashMap<String, Object>();
							paramer.put("userOnlyId", userOnlyId);
							paramer.put("option", reactivatedDate);
							paramer.put("auditMessage", auditMessage);
							String reason=RefuseMessage(paramer);
							processOpenedMap=queryUserNameCertPhone(userOnlyId,"5");
							processOpenedMap.put("status", applyStatus);
							processOpenedMap.put("reactivatedDate", reactivatedDate);
							processOpenedMap.put("reason", reason);
							processOpenedList.add(processOpenedMap);
						}else if("4".equals(applyStatus)){
							processOpenedMap=queryUserNameCertPhone(userOnlyId,"4");
							processOpenedMap.put("status", applyStatus);
							processOpenedList.add(processOpenedMap);
						}else if("3".equals(applyStatus)){
							if(creditApplyList!=null && creditApplyList.size()>0 && UhjConstant.applyStatus.APPLY_STATUS_RETURN.equals(creditApplyList.get(0).getStatus())){
								processOpenedMap=queryUserNameCertPhone(userOnlyId,"4");
								processOpenedMap.put("status", "4");
								processOpenedList.add(processOpenedMap);
							}else{
								processOpenedMap=queryUserNameCertPhone(userOnlyId,"3");
								processOpenedMap.put("status", applyStatus);
								processOpenedList.add(processOpenedMap);
							}
						}else if("101".equals(applyStatus)){
							//额度续期    做了申请的就要变成申请中
							alreadyOpenedMap=queryUserNameCertPhone(userOnlyId,"10");
							alreadyOpenedMap.put("status", "10");
							alreadyOpenedMap.put("creditLimit",account.getCreditLimit());
							alreadyOpenedMap.put("availBalance", account.getBanlance());
							alreadyOpenedMap.put("loanBalance", loanBalance);
							alreadyOpenedList.add(alreadyOpenedMap);
//							processOpenedMap=queryUserNameCertPhone(userOnlyId);
//							String ststus=queryUserRuleStatus(userOnlyId);
//							processOpenedMap.put("status", ststus);
//							processOpenedList.add(processOpenedMap);
						}else if("1".equals(applyStatus)){
							String userId="100" + StringUtils.leftPad(userOnlyId, 20, "0");
							OrderInfoExample orderInfoExample =new OrderInfoExample();
							orderInfoExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo((short) 55);
							List<OrderInfo> orderInfoList=orderInfoMapper.selectByExample(orderInfoExample);
							if(orderInfoList!=null && orderInfoList.size()>0){
								//逾期
								alreadyOpenedMap=queryUserNameCertPhone(userOnlyId,"12");
								alreadyOpenedMap.put("status", "12");
								alreadyOpenedMap.put("creditLimit",account.getCreditLimit());
								alreadyOpenedMap.put("availBalance", account.getBanlance());
								alreadyOpenedMap.put("loanBalance", loanBalance);
								alreadyOpenedList.add(alreadyOpenedMap);
							}else if("2".equals(Convert.toStr(account.getStatus()))){
								//冻结
								alreadyOpenedMap=queryUserNameCertPhone(userOnlyId,"14");
								alreadyOpenedMap.put("status", "14");
								alreadyOpenedMap.put("creditLimit",account.getCreditLimit());
								alreadyOpenedMap.put("availBalance", account.getBanlance());
								alreadyOpenedMap.put("loanBalance", loanBalance);
								alreadyOpenedList.add(alreadyOpenedMap);
							}else if(account.getExpireDate()!=null && DateUtil.diffDays(DateUtil.currDateStr(),account.getExpireDate() , DateUtil.YMD)>=0){
								//额度到期
								alreadyOpenedMap=queryUserNameCertPhone(userOnlyId,"10");
								alreadyOpenedMap.put("status", "10");
								alreadyOpenedMap.put("creditLimit",account.getCreditLimit());
								alreadyOpenedMap.put("availBalance", account.getBanlance());
								alreadyOpenedMap.put("loanBalance", loanBalance);
								alreadyOpenedList.add(alreadyOpenedMap);
							}else{
								//逾期    还款中   暂无借款
								if(loanBalance.compareTo(BigDecimal.ZERO)==0){
									//暂无借款
									alreadyOpenedMap=queryUserNameCertPhone(userOnlyId,"11");
									alreadyOpenedMap.put("status", "11");
									alreadyOpenedMap.put("creditLimit",account.getCreditLimit());
									alreadyOpenedMap.put("availBalance", account.getBanlance());
									alreadyOpenedMap.put("loanBalance", loanBalance);
									alreadyOpenedList.add(alreadyOpenedMap);
								}else{
//									OrderInfoExample orderInfoExample =new OrderInfoExample();
//									orderInfoExample.createCriteria().andUserIdEqualTo(userOnlyId).andStatusEqualTo((short) 55);
//									List<OrderInfo> orderInfoList=orderInfoMapper.selectByExample(orderInfoExample);
//									if(orderInfoList!=null && orderInfoList.size()>0){
//										//逾期
//										alreadyOpenedMap=queryUserNameCertPhone(userOnlyId,"12");
//										alreadyOpenedMap.put("status", "12");
//										alreadyOpenedMap.put("creditLimit",account.getCreditLimit());
//										alreadyOpenedMap.put("availBalance", account.getBanlance());
//										alreadyOpenedMap.put("loanBalance", loanBalance);
//										alreadyOpenedList.add(alreadyOpenedMap);
//									}else{
										//有借款
										alreadyOpenedMap=queryUserNameCertPhone(userOnlyId,"13");
										alreadyOpenedMap.put("status", "13");
										alreadyOpenedMap.put("creditLimit",account.getCreditLimit());
										alreadyOpenedMap.put("availBalance", account.getBanlance());
										alreadyOpenedMap.put("loanBalance", loanBalance);
										alreadyOpenedList.add(alreadyOpenedMap);
//									}
								}
							}
						}else if(creditApplyList!=null && creditApplyList.size()>0 ){
							//申请中
							String ststus=queryUserRuleStatus(userOnlyId,creditApplyList);
							processOpenedMap=queryUserNameCertPhone(userOnlyId,ststus);
							processOpenedMap.put("status", ststus);
							processOpenedList.add(processOpenedMap);
						}else{//未开通的用户
							//绑定了地推就算申请中、放在身份证识别阶段
							CreditPostmemberExample postmemberExample = new CreditPostmemberExample();
							postmemberExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andMemberTypeEqualTo("1");
							List<CreditPostmember> postmemberList = creditPostMapper.selectByExample(postmemberExample);
							if(postmemberList!=null&&postmemberList.size()>0){
								//申请中
//								String ststus=queryUserRuleStatus(userOnlyId,creditApplyList);
								processOpenedMap=queryUserNameCertPhone(userOnlyId,"APPCHECK_000");
								processOpenedMap.put("status", "APPCHECK_000");
								processOpenedList.add(processOpenedMap);
							}else{
								Map<String, Object> vps=VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
								if(vps!=null){
									Map<String, Object> notOpenedMap=new HashMap<String, Object>();
									String vps_name=Convert.toStr(vps.get(VpsInfoService.UsrName));
									String vps_certNo=Convert.toStr(vps.get(VpsInfoService.CertNo));
									String vps_mobileNo=Convert.toStr(vps.get(VpsInfoService.MobileNumber));
									notOpenedMap.put("userName", vps_name);
									notOpenedMap.put("certNo", vps_certNo);
									notOpenedMap.put("mobileNo", vps_mobileNo);
									notOpenedMap.put("storeName", vps_name+"的小店");
									notOpenedMap.put("status", "0");//未开通
									notOpenedMap.put("orgCode", Convert.toStr(vps.get(VpsInfoService.OrgCode)));//未开通
									notOpenedMap.put("userOnlyId", userOnlyId);//未开通
									notOpenedList.add(notOpenedMap);
								}
							}
						}
					}else{//申请中
						String ststus=queryUserRuleStatus(userOnlyId,creditApplyList);
						processOpenedMap=queryUserNameCertPhone(userOnlyId,ststus);
						processOpenedMap.put("status", ststus);
						processOpenedList.add(processOpenedMap);
					}
				}else{//未开通的用户
					//绑定了地推就算申请中、放在身份证识别阶段
					CreditPostmemberExample postmemberExample = new CreditPostmemberExample();
					postmemberExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andMemberTypeEqualTo("1");
					List<CreditPostmember> postmemberList = creditPostMapper.selectByExample(postmemberExample);
					if(postmemberList!=null&&postmemberList.size()>0){
						//申请中
//						String ststus=queryUserRuleStatus(userOnlyId,creditApplyList);
						Map<String, Object> processOpenedMap=new HashMap<String, Object>();
						processOpenedMap=queryUserNameCertPhone(userOnlyId,"APPCHECK_000");
						processOpenedMap.put("status", "APPCHECK_000");
						processOpenedList.add(processOpenedMap);
					}else{
						Map<String, Object> vps=VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
						if(vps!=null){
							Map<String, Object> notOpenedMap=new HashMap<String, Object>();
							String vps_name=Convert.toStr(vps.get(VpsInfoService.UsrName));
							String vps_certNo=Convert.toStr(vps.get(VpsInfoService.CertNo));
							String vps_mobileNo=Convert.toStr(vps.get(VpsInfoService.MobileNumber));
							notOpenedMap.put("userName", vps_name);
							notOpenedMap.put("certNo", vps_certNo);
							notOpenedMap.put("mobileNo", vps_mobileNo);
							notOpenedMap.put("storeName", vps_name+"的小店");
							notOpenedMap.put("status", "0");//未开通
							notOpenedMap.put("orgCode", Convert.toStr(vps.get(VpsInfoService.OrgCode)));//未开通
							notOpenedMap.put("userOnlyId", userOnlyId);//未开通
							notOpenedList.add(notOpenedMap);
						}
					}
				}
			}
		}
		return JsonResult.getInstance().addOk().add("notOpenedList",notOpenedList).add("notOpenedSize",notOpenedList.size())
				.add("processOpenedList",processOpenedList).add("processOpenedSize",processOpenedList.size())
				.add("alreadyOpenedList",alreadyOpenedList).add("alreadyOpenedSize",alreadyOpenedList.size()).toString();
	}
	
	private Map<String, Object>  queryUserNameCertPhone(String userOnlyId,String status) throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		String userName="";
		String certNo="";
		String mobileNo="";
		String orgCode="";
		UserInfoExample userInfoExample = new UserInfoExample();
		userInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoExample);
		if(userInfos!=null&&userInfos.size()>0){
			UserInfo user=userInfos.get(0);
			userName=user.getUserName();
			mobileNo=user.getMobileNo();
			certNo=user.getCertNo();
			if(certNo!=null){
				certNo=DesUtil.decrypt(certNo);
			}
		}
		if(Check.isBlank(userName) || Check.isBlank(certNo) || Check.isBlank(mobileNo)){
			CustomerInfoExample infoExample = new CustomerInfoExample();
			infoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
			List<CustomerInfo> customerInfos = customerInfoMapper.selectByExample(infoExample);
			if(customerInfos!=null&&customerInfos.size()>0){
				userName=customerInfos.get(0).getCustomerName();
				
				map.put("orgCode", Convert.toStr(customerInfos.get(0).getOrgCode()));
				
				List<CustomerCert> customerCerts = new ArrayList<CustomerCert>();
				CustomerCertExample customerCertExample = new CustomerCertExample();
				customerCertExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andCertTypeEqualTo(UhjConstant.certType.idcard);
				customerCerts = customerCertMapper.selectByExample(customerCertExample);
				if(customerCerts!= null && customerCerts.size()>0){
					certNo=customerCerts.get(0).getCertNo();
					
					List<CustomerPhone> customerPhones = new ArrayList<CustomerPhone>();
					CustomerPhoneExample customerPhoneExample = new CustomerPhoneExample();
					customerPhoneExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andCustomerTypeEqualTo(UhjConstant.customerType.loanor);
					customerPhones = customerPhoneMapper.selectByExample(customerPhoneExample);
					if(customerPhones!= null && customerPhones.size()>0){
						mobileNo=customerPhones.get(0).getPhoneNo();
					}
				}
			}
		}
		if(Check.isBlank(userName) || Check.isBlank(certNo) || Check.isBlank(mobileNo) || "APPCHECK_000".equals(status)){
			Map<String, Object> vps=VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
			if(vps!=null){
				 userName=Convert.toStr(vps.get(VpsInfoService.UsrName));
				 certNo=Convert.toStr(vps.get(VpsInfoService.CertNo));
				 mobileNo=Convert.toStr(vps.get(VpsInfoService.MobileNumber));
				 orgCode=Convert.toStr(vps.get(VpsInfoService.OrgCode));
				 map.put("orgCode", orgCode);
			}
		}
		map.put("userOnlyId", userOnlyId);
		map.put("userName", userName);
		map.put("certNo", certNo);
		map.put("mobileNo", mobileNo);
		map.put("storeName", userName+"的小店");
		return map;
	}
	
	private String queryUserRuleStatus(String userOnlyId,List<CreditApply> creditApplyList) throws Exception{
		if(creditApplyList!=null && creditApplyList.size()>0){
			String creditApplyStatus=creditApplyList.get(0).getStatus();
			if(UhjConstant.applyStatus.APPLY_STATUS_NORMAL.equals(creditApplyStatus) || UhjConstant.applyStatus.APPLY_STATUS_REVIEW.equals(creditApplyStatus)){
				return UhjConstant.applyStatus.APPLY_STATUS_REVIEW;
			}
			if(UhjConstant.applyStatus.APPLY_STATUS_RETURN.equals(creditApplyStatus)){
				return UhjConstant.applyStatus.APPLY_STATUS_RETURN;
			}
		}
		String status="APPCHECK_001";
		String flag ="false";
		List<String> statusList = new ArrayList<String>();
    	statusList.add("APPCHECK_001");
    	statusList.add("APPCHECK_002");
    	statusList.add("APPCHECK_003");
    	statusList.add("APPCHECK_004");
    	statusList.add("APPCHECK_005");
    	statusList.add("APPCHECK_006");
		CreditRuleExample ruleExample =new CreditRuleExample();
		ruleExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andRuleRefIdIn(statusList).andRuleOutputEqualTo(UhjConstant.ruleOutput.ruleOutput_true);
		List<CreditRule> rulelist=creditRuleMapper.selectByExample(ruleExample);
		if(rulelist!=null && rulelist.size()>0){
			for(CreditRule rule:rulelist){
				if(rule.getRuleRefId().compareTo(status)>=0){
					status=rule.getRuleRefId();
					flag=rule.getRuleOutput();
				}
			}
		}
		if("APPCHECK_001".equals(status) && "false".equals(flag)){
			status="APPCHECK_000";
		}
		return status;
	}
	
	
	private String RefuseMessage(Map<String, Object> paramer) throws Exception{
		log.info("RefuseMessage paramer "+paramer);
		String userOnlyId=Convert.toStr(paramer.get("userOnlyId"));
		String reactivatedDate=Convert.toStr(paramer.get("option"));
		String auditMessage=Convert.toStr(paramer.get("auditMessage"));
		String  normalRefuse="尊敬的客户，您的掌柜贷申请未通过，可能由于经营情况、还款能力、负债比、信用记录等多原因造成的，请于"+reactivatedDate+"再次申请掌柜贷，谢谢";
		if("RJ01".equals(auditMessage)){
			log.info("RefuseMessage hunyinqinglkuang "+userOnlyId);
			normalRefuse="尊敬的客户，因您填写的婚姻情况有误，您的掌柜贷申请未通过，请于"+reactivatedDate+"重新申请, 感谢您！";
			 return normalRefuse;
		}else if("RJ02".equals(auditMessage)){
			log.info("RefuseMessage lianxiren "+userOnlyId);
			normalRefuse="尊敬的客户，因您填写的联系人有误，您的掌柜贷申请未通过，请于"+reactivatedDate+"重新申请, 感谢您！";
			 return normalRefuse;
		}else if("RJ7".equals(auditMessage)){
			log.info("RefuseMessage dianhewurenjieting==="+userOnlyId);
			normalRefuse="尊敬的客户，因与您多次电核无人接听或不配合，您的掌柜贷申请未通过，请于"+reactivatedDate+"重新申请, 感谢您！";
			 return normalRefuse;
		}else if("RJ180".equals(auditMessage)){
			 //经营年限拒绝
			log.info("nianxian refuse===========userOnlyId=="+userOnlyId);
			 normalRefuse="尊敬的客户，因为您的店铺经营年限未达到掌柜贷的要求，您的掌柜贷申请未通过，请于"+reactivatedDate+"之后再次申请掌柜贷，感谢您的申请！";
			 return normalRefuse;
		}else{
			//先判断是不是企业状态拒绝
			CreditRuleExample ruleExample =new CreditRuleExample();
			ruleExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andRuleTypeEqualTo("A");
			ruleExample.setOrderByClause("create_Time desc ");
			List<CreditRule>  ruleList=creditRuleMapper.selectByExample(ruleExample);
			if(ruleList!=null&&ruleList.size()>0){
				 String ruleRefId=ruleList.get(0).getRuleRefId();
				 if("<standard1W".equals(ruleRefId) || "autoApproveRejectByCert".equals(ruleRefId)){
					 normalRefuse="尊敬的客户，您的掌柜贷申请未通过，可能由于经营情况、还款能力、负债比、信用记录等多原因造成的，请于"+reactivatedDate+"再次申请掌柜贷，谢谢";
					 return normalRefuse;
				 }
				 String[] fields = ruleRefId.split(",");
				 List<BigDecimal> qList=new ArrayList<BigDecimal>();
				 for(String f:fields){
					 qList.add(new BigDecimal(f));
				 }
				 RuleExeSetExample resExample=new RuleExeSetExample();
				 resExample.createCriteria().andIdIn(qList);
				 List<RuleExeSet> resList=ruleExeSetMapper.selectByExample(resExample);
				 if(resList!=null&&resList.size()>0){
					 String decId=Convert.toStr(resList.get(0).getDecisionId());
					 if("1021".equals(decId)){
						 normalRefuse="尊敬的客户，因您填写的日营业额有虚假嫌疑，您的掌柜贷申请未通过，请于"+reactivatedDate+"重新申请, 感谢您！";
						 log.info("yingyee refuse===========reactivatedDate==="+reactivatedDate+"userOnlyId=="+userOnlyId); 
						 return normalRefuse;
					 }
					 if("1010".equals(decId)){
						 String evalue=resList.get(0).getElementValue();
						 JSONObject  jasonObject = JSONObject.fromObject(evalue);
						 Map returnMap = (Map)jasonObject;
						 String status=Convert.toStr(returnMap.get("gsRegistrationStatus"));
						//企业状态拒绝
						 normalRefuse="尊敬的客户，因营业执照状态为"+status+"，您的掌柜贷申请未通过，感谢您的申请!";
						 log.info("qiyezhuangtai refuse===========status==="+status+"userOnlyId=="+userOnlyId); 
						 return normalRefuse;
					 }
				 }
			}
			//=================判断是不是企业状态拒绝end================
			log.info("RefuseMessage userOnlyId =="+userOnlyId+"reactivatedDate=="+reactivatedDate);
			CreditAudit creditAudit=new CreditAudit();
	    	creditAudit.setUserOnlyId(userOnlyId);
	    	creditAudit.setRuleRefId("autoApproveReject01");
	    	List<CreditAudit> list=creditAuditMapper.selectByParam(creditAudit);
	    	if(list!=null&&list.size()>0){
	    		String manualResult=list.get(0).getManualResult();
	    		if("通过".equals(manualResult)){
	    			//一般拒绝
	    			normalRefuse="尊敬的客户，您的掌柜贷申请未通过，可能由于经营情况、还款能力、负债比、信用记录等多原因造成的，请于"+reactivatedDate+"再次申请掌柜贷，谢谢";
	    			return normalRefuse;
	    		}
	    	}
			log.info("nianxian content========");
			CreditRuleExample crExample =new CreditRuleExample();
			crExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andRuleTypeEqualTo("2");
			crExample.setOrderByClause("create_Time desc");
			List<CreditRule>  crList=creditRuleMapper.selectByExample(crExample);
			if(crList!=null&&crList.size()>0){
				log.info("RefuseMessage crList.size>0  userOnlyId=="+userOnlyId);
				 String ruleRefId=crList.get(0).getRuleRefId();
				 String[] fields = ruleRefId.split(",");
				 List<BigDecimal> qList=new ArrayList<BigDecimal>();
				 for(String f:fields){
					 qList.add(new BigDecimal(f));
				 }
				 RuleExeSetExample resExample=new RuleExeSetExample();
				 resExample.createCriteria().andIdIn(qList).andRuleIdEqualTo("rejgssj01").andRuleOutputResultEqualTo("rejected");
				 List<RuleExeSet> resList=ruleExeSetMapper.selectByExample(resExample);
				 if(resList!=null&&resList.size()>0){
					 String  decisionId =Convert.toStr(resList.get(0).getDecisionId());
					 if("1013".equals(decisionId)){
						//经营范围拒绝
						 normalRefuse="尊敬的客户，因为您的店铺暂时不属于掌柜贷的准入行业，您的掌柜贷申请未通过，感谢您的申请！";
						 log.info("fanwei refuse===========userOnlyId=="+userOnlyId);
						 return normalRefuse;
					 }else{
						 //经营年限拒绝
						 normalRefuse="尊敬的客户，因为您的店铺经营年限未达到掌柜贷的要求，您的掌柜贷申请未通过，请于"+reactivatedDate+"之后再次申请掌柜贷，感谢您的申请！";
						 log.info("nianxian refuse===========userOnlyId=="+userOnlyId);
						 return normalRefuse;
					 }        					 
				 }
			}
		 log.info("一般拒绝 content========"+normalRefuse+"userOnlyId==="+userOnlyId);
		}
		return normalRefuse;
	}
}
