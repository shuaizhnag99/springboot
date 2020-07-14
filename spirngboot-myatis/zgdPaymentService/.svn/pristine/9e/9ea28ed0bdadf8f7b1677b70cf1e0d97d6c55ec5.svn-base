package com.ule.uhj.app.zgd.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ule.uhj.app.zgd.dao.ApplyDetailMapper;
import com.ule.uhj.app.zgd.dao.CustomerCertMapper;
import com.ule.uhj.app.zgd.dao.CustomerContactMapper;
import com.ule.uhj.app.zgd.dao.CustomerInfoMapper;
import com.ule.uhj.app.zgd.dao.CustomerPhoneMapper;
import com.ule.uhj.app.zgd.dao.OrderInfoMapper;
import com.ule.uhj.app.zgd.model.ApplyDetail;
import com.ule.uhj.app.zgd.model.ApplyDetailExample;
import com.ule.uhj.app.zgd.model.CustomerCert;
import com.ule.uhj.app.zgd.model.CustomerCertExample;
import com.ule.uhj.app.zgd.model.CustomerContact;
import com.ule.uhj.app.zgd.model.CustomerContactExample;
import com.ule.uhj.app.zgd.model.CustomerInfo;
import com.ule.uhj.app.zgd.model.CustomerInfoExample;
import com.ule.uhj.app.zgd.model.CustomerPhone;
import com.ule.uhj.app.zgd.model.CustomerPhoneExample;
import com.ule.uhj.app.zgd.service.ApplyImageService;
import com.ule.uhj.app.zgd.service.PdfService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.app.ZgdAppClient;
import com.ule.uhj.ejb.client.zgd.YcNoticeUleClient;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonUtil;
import com.ule.uhj.util.UhjWebJsonUtil;

@Controller
@RequestMapping(value = "/framework/pdf")
public class PdfController {
	private static Logger log = LoggerFactory.getLogger(PdfController.class);
	
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	@Autowired
	private CustomerCertMapper customerCertMapper;
	@Autowired
	private CustomerContactMapper customerContactMapper;
	@Autowired
	private ApplyImageService applyImageService;
	@Autowired
	private CustomerPhoneMapper customerPhoneMapper;
	@Autowired
	private PdfService pdfService;
	@Autowired
	private ApplyDetailMapper applyMapper;
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	/**
	 * jsp动态协议生成
	 * @param request
	 * @param mv
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/contractInfo")
	@ResponseBody
	public ModelAndView contractInfo(HttpServletRequest request, ModelMap mv){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			String userOnlyId = Convert.toStr(request.getParameter("userOnlyId"));
			String flag = Convert.toStr(request.getParameter("flag"));
			log.info("contractInfo userOnlyId:"+userOnlyId+" flag:"+flag);
			String customerName = "";
			String certNo = "";
			//查询姓名
			List<CustomerInfo> customerInfos = new ArrayList<CustomerInfo>();
			CustomerInfoExample infoExample = new CustomerInfoExample();
			infoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
			customerInfos =  customerInfoMapper.selectByExample(infoExample);
			if(customerInfos!=null&&customerInfos.size()>0){
				customerName = customerInfos.get(0).getCustomerName();		//用户姓名
			}
			//查询个人身份信息
			List<CustomerCert> customerCerts = new ArrayList<CustomerCert>();
			CustomerCertExample customerCertExample = new CustomerCertExample();
			customerCertExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andCertTypeEqualTo(UhjConstant.certType.idcard);
			customerCerts = customerCertMapper.selectByExample(customerCertExample);
			if(customerCerts!= null && customerCerts.size()>0){
				certNo = customerCerts.get(0).getCertNo();
			}
			map.put("customerName", customerName);
			map.put("certNo", certNo);
			String date = DateUtil.currDateStr();
			map.put("year", date.substring(0, 4));
			map.put("month", date.substring(6, 7));
			map.put("day", date.substring(9, 10));
			map.put("nowTime", new Date().getTime());
			map.put("version", "ZGDFW-1.1.170606");
			if("1".equals(flag)){
				mv.putAll(map);
				return new ModelAndView("/yc/app/perInfoRetaPower");
			}else if("2".equals(flag)){
				mv.putAll(map);
				return new ModelAndView("/yc/app/quickPayBill");
			}else if("3".equals(flag)){
				mv.putAll(map);
				return new ModelAndView("/yc/app/viewFwktxy");
			}else if("4".equals(flag)){
				String applyAmount=Convert.toStr(request.getParameter("applyAmount"));
				String periods=Convert.toStr(request.getParameter("periods"));
				String loanRemark=Convert.toStr(request.getParameter("loanRemark"));
				String repayType=Convert.toStr(request.getParameter("repayType"));
				Map<String, Object> paramMap=new HashMap<String, Object>();
				paramMap.put("userOnlyId", userOnlyId);
				paramMap.put("applyAmount", applyAmount);
				paramMap.put("periods", periods);
				paramMap.put("loanRemark", loanRemark);
				paramMap.put("repayType", repayType);
				log.info("loanContractBill map:"+paramMap);
				String result = WildflyBeanFactory.getZgdAppClient().loanContractBill(paramMap);
				Map<String,Object> billMap = JsonUtil.getMapFromJsonString(result);
				map.put("billInfo", billMap);
				mv.putAll(map);
				return new ModelAndView("/yc/app/viewPettyLoanBill");
			}else if("5".equals(flag)){
				//查询个人电话信息
				List<CustomerPhone> customerPhones = new ArrayList<CustomerPhone>();
				CustomerPhoneExample customerPhoneExample = new CustomerPhoneExample();
				customerPhoneExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andCustomerTypeEqualTo(UhjConstant.customerType.loanor);
				customerPhones = customerPhoneMapper.selectByExample(customerPhoneExample);
				if(customerPhones!= null && customerPhones.size()>0){
					map.put("phone", customerPhones.get(0).getPhoneNo());					//本人手机号
				}
				//查询联系人信息
				List<CustomerContact> contactContacts = new ArrayList<CustomerContact>();
				CustomerContactExample customerContactExample = new CustomerContactExample();
				customerContactExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andContactTypeEqualTo(UhjConstant.contactType.spouse);
				contactContacts = customerContactMapper.selectByExample(customerContactExample);
				if(contactContacts!= null && contactContacts.size()>0){
					map.put("contactType", contactContacts.get(0).getContactType());	//联系人类型					
					map.put("contactName", contactContacts.get(0).getContactName());	//联系人姓名(加密)
				}
				//查询联系人身份信息
				List<CustomerCert> contactCerts = new ArrayList<CustomerCert>();
				CustomerCertExample contactCertExample = new CustomerCertExample();
				contactCertExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andCertTypeEqualTo(UhjConstant.certType.spouse_idcard);
				contactCerts = customerCertMapper.selectByExample(contactCertExample);
				if(contactCerts!= null && contactCerts.size()>0){
					map.put("contactCertNo", contactCerts.get(0).getCertNo());							//联系人身份证号
				}
				ZgdAppClient client = WildflyBeanFactory.getZgdAppClient();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("userOnlyId", userOnlyId);
				String contractInfo = client.loanContract(param);
				Map<String,Object> contractMap = JsonUtil.getMapFromJsonString(contractInfo);
				map.put("contractInfo", contractMap);
				mv.putAll(map);
				return new ModelAndView("/yc/app/viewPettyLoan");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ModelAndView("/common/error");
		}
		return new ModelAndView("");
	}
	
	/**
	 * pdf下载
	 * @param request
	 * @param response
	 */
//	@RequestMapping("/contractLoadDown")
//	@ResponseBody
//	public void contractLoadDown(HttpServletRequest request, HttpServletResponse response){
//		GetMethod method = null;
//		String responseStr = null;
//		try {
//			String userOnlyId = Convert.toStr(request.getParameter("userOnlyId"));
//			String flag = Convert.toStr(request.getParameter("flag"));
//			log.info("contractLoadDown userOnlyId:"+userOnlyId+" flag:"+flag);
//			HttpClientParams httpParams = new HttpClientParams();
//			for(Object param:request.getParameterMap().keySet()){
//				httpParams.setParameter(param.toString(), request.getParameter(param.toString()));
//			}
//			HttpClient client = new HttpClient(httpParams,new SimpleHttpConnectionManager(true));
//			StringBuffer path = request.getRequestURL();
//			if("4".equals(flag)){
//				String applyAmount=Convert.toStr(request.getParameter("applyAmount"));
//				String periods=Convert.toStr(request.getParameter("periods"));
//				String loanRemark=Convert.toStr(request.getParameter("loanRemark"));
//				String repayType=Convert.toStr(request.getParameter("repayType"));
//				method = new GetMethod(path.replace(path.indexOf("/lendvps"), path.length(), "/lendvps/framework/pdf/contractInfo.do?userOnlyId="+userOnlyId+"&flag="+flag+"&applyAmount="+applyAmount+"&periods="+periods+"&loanRemark="+loanRemark+"&repayType="+repayType).toString());
//			}else{
//				method = new GetMethod(path.replace(path.indexOf("/lendvps"), path.length(), "/lendvps/framework/pdf/contractInfo.do?userOnlyId="+userOnlyId+"&flag="+flag).toString());
//			}
//			client.executeMethod(method);
//			responseStr = method.getResponseBodyAsString();
//			log.info("请求链接地址。。。");
//			byte[] pdf = PdfUtils.html2pdf(responseStr);
//			log.info("html转pdf。。。");
//			response.setContentType("application/pdf");
//			response.setHeader("Content-Length",String.valueOf(pdf.length));
//			response.setHeader("Connection","keep-alive");
//			response.setHeader("Accept-Ranges","none");
//			response.setHeader("X-Frame-Options","DENY");
//			String fileName = "";
//			if("1".equals(flag)){
//				fileName =  URLEncoder.encode("个人信息查询及留存授权书.pdf","UTF-8");
//			}else if("2".equals(flag)){
//				fileName =  URLEncoder.encode("小额贷款额度借款合同.pdf","UTF-8");
//			}else if("3".equals(flag)){
//				fileName =  URLEncoder.encode("掌柜贷咨询服务协议.pdf","UTF-8");
//			}
//			log.info("fileName:"+fileName);
//			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
//			OutputStream out = response.getOutputStream();
//			out.write(pdf);
//			out.flush();
//		} catch (Exception e){
//            log.error("contractLoadDown error", e);
//        }finally{
//            if(method!=null){
//            	method.releaseConnection(); 
//            } 	
//        }
//	}
	
	/**
	 * pdf上传
	 * @param request
	 * @param response
	 */
	@RequestMapping("/contractPdfUpload")
	@ResponseBody
	public String contractPdfUpload(HttpServletRequest request, Map<String,Object> map){
		log.info("contractPdfUpload map"+map.toString());
		GetMethod method = null;
		String responseStr = null;
		String url = null;
		try {
			String userOnlyId = Convert.toStr(map.get("userOnlyId"));
			String flag = Convert.toStr(map.get("flag"));
			log.info("contractPdfUpload userOnlyId"+userOnlyId+" flag:"+flag);
			HttpClientParams httpParams = new HttpClientParams();
			for(Object param:request.getParameterMap().keySet()){
				httpParams.setParameter(param.toString(), request.getParameter(param.toString()));
			}
			HttpClient client = new HttpClient(httpParams,new SimpleHttpConnectionManager(true));
			StringBuffer path = request.getRequestURL();
			if("4".equals(flag)){
				String applyAmount=Convert.toStr(map.get("applyAmount"));
				String periods=Convert.toStr(map.get("periods"));
				String loanRemark=Convert.toStr(map.get("loanRemark"));
				String repayType=Convert.toStr(map.get("repayType"));
				log.info("contractPdfUpload applyAmount:"+applyAmount+" periods:"+periods+" loanRemark:"+loanRemark+" repayType:"+repayType);
				method = new GetMethod(path.replace(path.indexOf("/lendvps"), path.length(), "/lendvps/framework/pdf/contractInfo.do?userOnlyId="+userOnlyId+"&flag="+flag+"&applyAmount="+applyAmount+"&periods="+periods+"&loanRemark="+loanRemark+"&repayType="+repayType).toString());
			}else{
				method = new GetMethod(path.replace(path.indexOf("/lendvps"), path.length(), "/lendvps/framework/pdf/contractInfo.do?userOnlyId="+userOnlyId+"&flag="+flag).toString());
			}
			client.executeMethod(method);
			responseStr = method.getResponseBodyAsString();
			log.info("请求链接地址。。。");
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("userOnlyId", userOnlyId);
			param.put("businessType", flag);
			param.put("html", responseStr);
			pdfService.saveContact(param);
		} catch (Exception e){
            log.error("contractPdfUpload error", e);
        }finally{
            if(method!=null){
            	method.releaseConnection(); 
            } 	
        }
		return url;
	}
	
	/**
	 * 查询邮储银行返回的合同(征信协议、额度单)
	 * @param html
	 * @param userOnlyId                                                                                                                                                    
	 * @param flag
	 */
	@RequestMapping("/queryContact")
	@ResponseBody
	public JSONPObject queryContact(HttpServletRequest request,@RequestParam String jsoncallback){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> param = new HashMap<String, Object>();
		try {
			String userOnlyId = getUserOnlyId(request);
			String businessType = Convert.toStr(request.getParameter("businessType"));
			String businessId = Convert.toStr(request.getParameter("businessId"));
			String orderId = Convert.toStr(request.getParameter("orderId"));
			log.info("queryContact userOnlyId:"+userOnlyId+" businessType:"+businessType+" businessId:"+businessId+" orderId:"+orderId);
			YcNoticeUleClient client = WildflyBeanFactory.getYcNoticeUleClient();
			Map<String, Object> result = client.entranceView(false, userOnlyId, businessId, businessType,orderId,param);
			log.info("queryContact result:"+result.toString());
			String code = result.get("code").toString();
			if(code.equals("000000")){
				String htmlStr = result.get("data").toString();
				resultMap.put("code", "0000");
				resultMap.put("msg", "查询成功");
				resultMap.put("htmlStr", htmlStr);
			}
		} catch (Exception e) {
			log.error("queryContact error", e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
	}
	
	/**
	 * 查询支用单
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/queryLoanContact")
	@ResponseBody
	public JSONPObject queryLoanContact(HttpServletRequest request,@RequestParam String jsoncallback){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String,Object> param = new HashMap<String, Object>();
			String userOnlyId = getUserOnlyId(request);
			String businessType = Convert.toStr(request.getParameter("businessType"));
			//支用金额
			String loanAmountLower = Convert.toStr(request.getParameter("loanAmount"));
			//借款期限
			String loanLimit = Convert.toStr(request.getParameter("loanLimit"));
			//归还贷款本息方式
			String loanBackType = Convert.toStr(request.getParameter("loanRepayType"));
			//还款账户户名
			String loanAccount = Convert.toStr(request.getParameter("loanAccount"));
			//还款账号
			String loanAccountNo = Convert.toStr(request.getParameter("cardNo"));
			//申请借款
			String loanBorrow = Convert.toStr(request.getParameter("loanAmount"));
			//期限
			String loan2Limit = Convert.toStr(request.getParameter("loan2Limit"));
			//还款方式
			String loanPayType = Convert.toStr(request.getParameter("loanRepayType"));
			//贷款年利率
			String loanRate = Convert.toStr(request.getParameter("loanRate"));
			log.info("queryLoanContact userOnlyId:"+userOnlyId+" businessType:"+businessType);
			param.put("userOnlyId", userOnlyId);
			param.put("businessType", businessType);
			param.put("loanAmountLower", loanAmountLower);
			param.put("loanLimit", loanLimit);
			param.put("loanBackType", loanBackType);
			param.put("loanAccount", loanAccount);
			param.put("loanAccountNo", loanAccountNo);
			param.put("loanBorrow", loanBorrow);
			param.put("loan2Limit", loan2Limit);
			param.put("loanPayType", loanPayType);
			param.put("loanRate", loanRate);
			log.info("queryLoanContact param:"+param.toString());
			YcNoticeUleClient client = WildflyBeanFactory.getYcNoticeUleClient();
			Map<String, Object> result = client.entranceView(false, userOnlyId, null, businessType,null,param);
			log.info("queryContact result:"+result.toString());
			String code = result.get("code").toString();
			if(code.equals("000000")){
				String htmlStr = result.get("data").toString();
				resultMap.put("code", "0000");
				resultMap.put("msg", "查询成功");
				resultMap.put("htmlStr", htmlStr);
			}
		} catch (Exception e) {
			log.error("queryContact error", e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
	}
	
	/**
	 * 保存邮储银行返回的合同
	 * @param html
	 * @param userOnlyId
	 * @param flag
	 */
	@RequestMapping("/saveContact")
	@ResponseBody
	public void saveContact(){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();//系统当前时间
        calendar.add(Calendar.DATE, -1);    //得到前一天
        String date=new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()).toString();
        log.info("queryBadOrderInfo begin **************");
		try {
			//查看并保存到服务器征信协议
			log.info("saveContact 保存征信协议。。。。begin");
			ApplyDetailExample applyDetailExample = new ApplyDetailExample();
			applyDetailExample.createCriteria().andCreditApplyIdIsNotNull().andCreditTimeLike(date);
			applyDetailExample.setOrderByClause(" credit_time desc");
			List<ApplyDetail> details = applyMapper.selectByExample(applyDetailExample);
			log.info("saveContact 保存征信协议。。。。details:"+details.size());
			if(details!=null&&details.size()>0){
				for(ApplyDetail detail:details){
					String userOnlyId = detail.getUserOnlyId();
					String creditApplyId = detail.getCreditApplyId();
					log.info("saveContact userOnlyId:"+userOnlyId+" businessId:"+creditApplyId);
					Map<String,Object> param = new HashMap<String, Object>();
					YcNoticeUleClient client = WildflyBeanFactory.getYcNoticeUleClient();
					Map<String, Object> responseStr = client.entranceView(false, userOnlyId, creditApplyId, "limit",null,param);
					String queryCode = responseStr.get("code").toString();
					log.info("saveContact 保存征信协议。。。。result："+responseStr.toString());
					if(queryCode.equals("000000")){
						String htmlStr = responseStr.get("data").toString();
						log.info("htmlStr:"+htmlStr);
						resultMap.put("code", "0000");
						resultMap.put("msg", "查询成功");
						resultMap.put("htmlStr", htmlStr);
						param.put("html", htmlStr);
						param.put("userOnlyId", userOnlyId);
						param.put("businessType", "limit");
						pdfService.saveContact(param);
					}
				}
			}
			log.info("saveContact 保存征信协议。。。。end");
		}catch (Exception e) {
			log.error("saveContact error", e);
		}
		try{	
			log.info("saveContact 保存额度协议。。。。begin");
			//查看并保存到服务器额度协议
			ApplyDetailExample applyExample = new ApplyDetailExample();
			applyExample.createCriteria().andLineProgressEqualTo((short)1).andLineBeginDateLike(date);
			applyExample.setOrderByClause(" line_begin_date desc");
			List<ApplyDetail> applyDetails = applyMapper.selectByExample(applyExample);
			log.info("saveContact 保存额度协议。。。。applyDetails:"+applyDetails.size());
			if(applyDetails!=null&&applyDetails.size()>0){
				for(ApplyDetail detail:applyDetails){
					String userOnlyId = detail.getUserOnlyId();
					String lineId = detail.getLineId();
					log.info("saveContact userOnlyId:"+userOnlyId+" businessId:"+lineId);
					Map<String,Object> param = new HashMap<String, Object>();
					YcNoticeUleClient client = WildflyBeanFactory.getYcNoticeUleClient();
					Map<String, Object> responseStr = client.entranceView(false, userOnlyId, lineId, "line",null,param);
					log.info("saveContact 保存额度协议。。。。result："+responseStr.toString());
					String queryCode = responseStr.get("code").toString();
					if(queryCode.equals("000000")){
						String htmlStr = responseStr.get("data").toString();
						log.info("htmlStr:"+htmlStr);
						resultMap.put("code", "0000");
						resultMap.put("msg", "查询成功");
						resultMap.put("htmlStr", htmlStr);
						param.put("html", htmlStr);
						param.put("userOnlyId", userOnlyId);
						param.put("businessType", "line");
						pdfService.saveContact(param);
					}
				}
			}
			log.info("saveContact 保存额度协议。。。。end");
		}catch (Exception e) {
			log.error("saveContact error", e);
		}
		try{
			log.info("saveContact 保存支用协议。。。。begin");
			//查看并保存到服务器支用协议
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("status", 5);
			param.put("loanTime", date);
			log.info("saveContact 保存支用协议。。。。param:"+param.toString());
			List<Map<String,Object>> orders = orderInfoMapper.selectByExampleInOrder(param);
			log.info("saveContact 保存支用协议。。。。orders:"+orders.size());
			if(orders!=null&&orders.size()>0){
				for(int i = 0 ; i < orders.size() ; i ++){
					Map<String,Object> orderMap = orders.get(i);
					String userOnlyId = Convert.toStr(orderMap.get("userOnlyId"));
					String orderId = Convert.toStr(orderMap.get("orderId"));
					String loanId = Convert.toStr(orderMap.get("loanId"));
					log.info("saveContact userOnlyId:"+userOnlyId+" orderId:"+orderId+" loanId:"+loanId);
					Map<String,Object> para = new HashMap<String, Object>();
					YcNoticeUleClient client = WildflyBeanFactory.getYcNoticeUleClient();
					Map<String, Object> responseStr = client.entranceView(false, userOnlyId, loanId, "loan",orderId,para);
					log.info("saveContact 保存支用协议。。。。result："+responseStr.toString());
					String queryCode = responseStr.get("code").toString();
					if(queryCode.equals("000000")){
						String htmlStr = responseStr.get("data").toString();
						log.info("htmlStr:"+htmlStr);
						resultMap.put("code", "0000");
						resultMap.put("msg", "查询成功");
						resultMap.put("htmlStr", htmlStr);
						param.put("html", htmlStr);
						param.put("userOnlyId", userOnlyId);
						param.put("businessType", "loan");
						pdfService.saveContact(param);
					}
				}
			}
			log.info("saveContact 保存支用协议。。。。end");
		} catch (Exception e) {
			log.error("saveContact error", e);
		}
	}
	
	private String getUserOnlyId(HttpServletRequest request) throws Exception {
		String usronlyId =CommonHelper.getUserOnlyId(request);
//		String usronlyId="10000026049";
		return usronlyId;
	}
}
