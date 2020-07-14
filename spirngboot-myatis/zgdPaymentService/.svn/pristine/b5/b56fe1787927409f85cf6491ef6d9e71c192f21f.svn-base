package com.ule.uhj.app.act.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.object.model.interaction.coffee.Coffee;
import com.ule.uhj.Dcoffee.object.model.interaction.coffeeBeans.CoffeeBean;
import com.ule.uhj.Dcoffee.object.model.interaction.coffeeBeans.StandardBean;
import com.ule.uhj.app.act.constant.AccountMoneyUtil;
import com.ule.uhj.app.act.constant.ActTicketConstant;
import com.ule.uhj.app.act.service.SaleActTicketService;
import com.ule.uhj.app.yzs.constant.YzsConstants;
import com.ule.uhj.app.yzs.util.ResultUtil;
import com.ule.uhj.app.yzs.util.YZSExceptionUtil;
import com.ule.uhj.app.zgd.model.SaleTicketInfo;
import com.ule.uhj.app.zgd.model.SaleTicketType;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.app.ZgdAppClient;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.uhj.zgd.service.WebCoffeeMaker;

@Controller
@RequestMapping("/sale")
public class SaleActTicketController {

	private static Logger log = LoggerFactory.getLogger(SaleActTicketController.class);
	@Autowired
	private WebCoffeeMaker coffeeMaker;
	@Autowired
	private SaleActTicketService saleActTicketService;
	
	/*@RequestMapping("/isOpenAccount")
	@ResponseBody
	public String isOpenAccount(HttpServletRequest request){
		log.info("isOpenAccount begin.");
		try {
			String result = JsonResult.getInstance().addOk().toJsonStr();
			Map<String, Object> param=new HashMap<String, Object>();
			String userOnlyId=request.getParameter("userOnlyId");
			param.put("userOnlyId", userOnlyId);
			result=AccountMoneyUtil.isOpenAccount(param);
			return result;
		} catch (Exception e) {
			log.error("isOpenAccount error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return result;
		}
	}*/
	
//	@RequestMapping("/openAccount")
//	@ResponseBody
//	public String openAccount(HttpServletRequest request){
//		log.info("openAccount begin.");
//		try {
//			String result = JsonResult.getInstance().addOk().toJsonStr();
//			Map<String, Object> param=new HashMap<String, Object>();
//			//String userOnlyId=request.getParameter("userOnlyId");
//			String userOnlyId=getUserOnlyId(request);
//			String userName=request.getParameter("userName");
//			param.put("userOnlyId", userOnlyId);
//			param.put("userName", userName);
//			result=AccountMoneyUtil.openAccount(param);
//			return result;
//		} catch (Exception e) {
//			log.error("openAccount error!",e);
//			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
//			return result;
//		}
//	}
	
	/*@RequestMapping("/queryAccountMoneyAndSettle")
	@ResponseBody
	public String queryAccountMoneyAndSettle(HttpServletRequest request){
		log.info("queryAccountMoneyAndSettle begin.");
		try {
			String result = JsonResult.getInstance().addOk().toJsonStr();
			Map<String, Object> param=new HashMap<String, Object>();
			String orderId=request.getParameter("orderId");
			param.put("orderId", orderId);
			result=AccountMoneyUtil.queryAccountMoneyAndSettle(param);
			return result;
		} catch (Exception e) {
			log.error("queryAccountMoneyAndSettle error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return result;
		}
	}
	
	@RequestMapping("/addAccountMoneyAndSettle")
	@ResponseBody
	public String addAccountMoneyAndSettle(HttpServletRequest request){
		log.info("addAccountMoneyAndSettle begin.");
		try {
			String result = JsonResult.getInstance().addOk().toJsonStr();
			Map<String, Object> param=new HashMap<String, Object>();
			String orderId=request.getParameter("orderId");
			param.put("orderId", orderId);
			String userOnlyId=request.getParameter("userOnlyId");
			param.put("userOnlyId", userOnlyId);
			param.put("orderTime", "2018-05-02 14:42:00");
			param.put("orderMoney", "13.1");
			param.put("refundOrderId", orderId);
			param.put("userName", "zhaojie");
			result=AccountMoneyUtil.addAccountMoneyAndSettle(param);
			return result;
		} catch (Exception e) {
			log.error("addAccountMoneyAndSettle error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return result;
		}
	}*/
	/**
	 * 给掌柜发放邮利券
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/sale_sendActTicket")
	@ResponseBody
	public String sale_sendActTicket(HttpServletRequest request){
		log.info("sale_sendActTicket begin.");
		try {
			String code=request.getParameter("code");//营销活动代码
			String sendId=request.getParameter("sendId");//动态发放规则SQL的主键
			String batchno=request.getParameter("batchno");//邮利券的批次号
			String name=request.getParameter("name");//生成者，发放者姓名
			String ticketTYpeCode=request.getParameter("ticketTYpeCode");//券种代码
			String result = JsonResult.getInstance().addOk().toJsonStr();
			//获取郑鑫那边发放规则的动态SQL
			CoffeeBean bean = new StandardBean();
			bean.setID(sendId);
			Parameter parameter = new Parameter("code",code);
			bean.putParamer(parameter);
			Coffee coffee = coffeeMaker.make(bean);
			List<String> userList=(List<String>) coffee.getExtension();//得到可以发放邮利券的用户ID列表
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("code", code);
			map.put("userList", userList);
			map.put("batchno", batchno);
			map.put("name", name);
			map.put("ticketTYpeCode", ticketTYpeCode);
			result=saleActTicketService.saveSaleTicket(map);//给用户发放邮利券
			return result;
		} catch (Exception e) {
			log.error("sale_sendActTicket error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return result;
		}
	}
	/**
	 * 借款页面查询用户可用的邮利券数量
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryAvailableTicket")
	@ResponseBody
	public JSONPObject queryAvailableTicket(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		String result = JsonResult.getInstance().addOk().toJsonStr();
		try{
			String userOnlyId=getUserOnlyId(request);
			BigDecimal applyAmount=Convert.toBigDecimal(request.getParameter("applyAmount"));
			Integer periods=Convert.toInt(request.getParameter("periods"));
			String fixedRepayDate=Convert.toStr(request.getParameter("fixedRepayDate"));
			String repayType=Convert.toStr(request.getParameter("repayType"));
			BigDecimal sumInter=Convert.toBigDecimal(request.getParameter("sumInter"));//掌柜的还款计划总的利息
			BigDecimal yearRate=Convert.toBigDecimal(request.getParameter("yearRate"));//年利率
			log.info("queryAvailableTicket 查询优惠券 repayType:"+repayType);
//			if("1100".equals(repayType)){
//				repayType="01";
//			}else if("0200".equals(repayType)){
//				repayType="02";
//			}else{
//				result = JsonResult.getInstance().addError("还款方式不匹配！").toJsonStr();
//				return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
//			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("applyAmount", applyAmount);//支用金额
			param.put("repayType", repayType);//还款方式
			param.put("periods", periods);//还款期数
			param.put("fixedRepayDate", fixedRepayDate);//固定还款日期
			param.put("userOnlyId", userOnlyId);//
			param.put("sumInter", sumInter);//
			param.put("yearRate", yearRate);//
			Integer number=0;
			log.info("queryAvailableTicket 查询优惠券 param:"+param);
			Map<String, Object> map=saleActTicketService.queryUsefulTickets4ZY(userOnlyId, param);//查询借款时用户可用的邮利券-----四化的接口
			log.info("queryAvailableTicket map:"+map);
			number=Convert.toInt(map.get("count"),0);//可用数量
			BigDecimal ticketInter=BigDecimal.ZERO;//折扣后的利息
			BigDecimal ticketRate=BigDecimal.ZERO;//折扣后的利率
			BigDecimal endorseAmount=BigDecimal.ZERO;//转让金额
			BigDecimal sumAmount=BigDecimal.ZERO;//折扣后的总金额
			String lastRepayDate=null;
			List<Map<String, String>> ticketList=null;
			String ticketno=null;
			List<SaleTicketInfo> list = new ArrayList<SaleTicketInfo>();
			if(number==0){//如果可用券为0查询可转让的券
				//查询可受让的券
				SaleTicketInfo info=saleActTicketService.queryAvailableTransferTicket(param);
				if(info!=null){
					param.put("ticketno", info.getTicketno());
					Map<String, Object> discountMap =discountInterAvailableTicket(param);
					log.info("queryAvailableTicket 查询优惠券 discountMap:"+discountMap);
					ticketInter=Convert.toBigDecimal(discountMap.get("ticketInter"));
					ticketRate=Convert.toBigDecimal(discountMap.get("ticketRate"));
					endorseAmount=Convert.toBigDecimal(discountMap.get("endorseAmount"));
					ticketList=(List<Map<String, String>>) discountMap.get("ticketList");
					sumInter=Convert.toBigDecimal(discountMap.get("sumInter"));
					sumAmount=Convert.toBigDecimal(discountMap.get("sumAmount"));
					lastRepayDate=Convert.toStr(discountMap.get("lastRepayDate"));
				}
			}else if(number==1){
				list=(List<SaleTicketInfo>) map.get("list");
				if(list!=null && list.size()>0){
					SaleTicketInfo info=list.get(0);
					if(info.getEndorseFlag()!=null && "0".equals(info.getEndorseFlag())){//券是否可转让 0-否 1-是
						ticketno=info.getTicketno();
					}
				}
			}
			result=JsonResult.getInstance().addOk().add("number", number).add("ticketInter", ticketInter).add("ticketno", ticketno).add("ticketRate", ticketRate)
					.add("endorseAmount", endorseAmount).add("ticketList", ticketList).add("sumInter", sumInter).add("sumAmount", sumAmount)
					.add("userOnlyId", userOnlyId).add("lastRepayDate", lastRepayDate).toJsonStr();
			log.info("queryAvailableTicket 查询优惠券 result:"+result);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		}catch(Exception e){
			log.error("queryAvailableTicket error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 查询用户使用或者受让的邮利券的折扣金额和还款计划
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	private Map<String, Object> discountInterAvailableTicket(Map<String, Object> param) throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		BigDecimal sumInter=Convert.toBigDecimal(param.get("sumInter"));
		BigDecimal yearRate=Convert.toBigDecimal(param.get("yearRate"));
		String ticketno=Convert.toStr(param.get("ticketno"));//券号
		SaleTicketType type=saleActTicketService.querySaleTicketType(ticketno);
		log.info("discountInterAvailableTicket type:"+type);
		if(type!=null){
			BigDecimal ticketRate=BigDecimal.ZERO;
			if(type.getType2().equals(ActTicketConstant.TICKET_TYPE_2.DISCOUNT_TICKET)){
				ticketRate=yearRate.multiply(type.getOffValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
			}else if(type.getType2().equals(ActTicketConstant.TICKET_TYPE_2.REDUCTION_TICKET)){
				ticketRate=yearRate.subtract(type.getOffValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
			}
			if(ticketRate.compareTo(new BigDecimal("9"))<0){
				ticketRate=new BigDecimal("9");
			}
			BigDecimal discountRate=yearRate.subtract(ticketRate);
			param.put("discountRate", discountRate);//ticket
			param.put("ticketRate", ticketRate);//
			if("01".equals(param.get("repayType"))){
				param.put("repayType", "1100");
			}else if("02".equals(param.get("repayType"))){
				param.put("repayType", "0200");
			}
			String result = WildflyBeanFactory.getZgdAppClient().queryAppRepayPlan(param);
			log.info("discountInterAvailableTicket result:"+result);
			JSONObject js =JSONObject.fromObject(result);
			log.info("discountInterAvailableTicket js:"+js+";sumInter:"+js.get("sumInter"));
			BigDecimal ticketInter=Convert.toBigDecimal(js.get("sumInter"),BigDecimal.ZERO);
			String lastRepayDate=Convert.toStr(js.get("lastRepayDate"));
			BigDecimal discountInter=sumInter.subtract(ticketInter);
			BigDecimal endorseAmount=discountInter.multiply(new BigDecimal("0.3")).setScale(2,BigDecimal.ROUND_HALF_UP);
			if(ticketInter.compareTo(BigDecimal.ZERO)<=0){
				discountInter=BigDecimal.ZERO;
				endorseAmount=BigDecimal.ZERO;
			}
			map.put("ticketInter", ticketInter);
			map.put("ticketRate", ticketRate);
			map.put("discountRate", discountRate);
			map.put("discountInter", discountInter);
			map.put("ticketList", js.get("list"));
			map.put("endorseAmount", endorseAmount);
			map.put("sumInter", Convert.toBigDecimal(js.get("sumInter"),BigDecimal.ZERO));
			map.put("sumAmount", Convert.toBigDecimal(js.get("sumAmount"),BigDecimal.ZERO));
			map.put("lastRepayDate", lastRepayDate);
		}
		return map;
	}
	/**
	 * 查询使用邮利券后的优惠金额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/userAvailableTicket")
	@ResponseBody
	public JSONPObject userAvailableTicket(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		String result = JsonResult.getInstance().addOk().toJsonStr();
		try{
			String userOnlyId=getUserOnlyId(request);
			BigDecimal applyAmount=Convert.toBigDecimal(request.getParameter("applyAmount"));
			Integer periods=Convert.toInt(request.getParameter("periods"));
			String fixedRepayDate=Convert.toStr(request.getParameter("fixedRepayDate"));
			String repayType=Convert.toStr(request.getParameter("repayType"));
			BigDecimal sumInter=Convert.toBigDecimal(request.getParameter("sumInter"));//掌柜的还款计划总的利息
			BigDecimal yearRate=Convert.toBigDecimal(request.getParameter("yearRate"));//年利率
			String ticketno=Convert.toStr(request.getParameter("ticketno"));//券号
			BigDecimal sumAmount=BigDecimal.ZERO;
			log.info("userAvailableTicket 使用优惠券 repayType:"+repayType);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("applyAmount", applyAmount);//支用金额
			param.put("repayType", repayType);//还款方式
			param.put("periods", periods);//还款期数
			param.put("fixedRepayDate", fixedRepayDate);//固定还款日期
			param.put("userOnlyId", userOnlyId);//
			param.put("sumInter", sumInter);//
			param.put("yearRate", yearRate);//
			param.put("ticketno", ticketno);//
			log.info("userAvailableTicket 使用优惠券 param:"+param);
			Map<String, Object> discountMap =discountInterAvailableTicket(param);
			BigDecimal ticketInter=Convert.toBigDecimal(discountMap.get("ticketInter"));
			BigDecimal ticketRate=Convert.toBigDecimal(discountMap.get("ticketRate"));
			sumInter=Convert.toBigDecimal(discountMap.get("sumInter"));
			sumAmount=Convert.toBigDecimal(discountMap.get("sumAmount"));
			List<Map<String, String>> ticketList=(List<Map<String, String>>) discountMap.get("ticketList");
			result=JsonResult.getInstance().addOk().add("ticketInter", ticketInter).add("ticketRate", ticketRate)
					.add("ticketList", ticketList).add("sumInter", sumInter).add("sumAmount", sumAmount)
					.add("ticketno", ticketno).add("userOnlyId", userOnlyId).toJsonStr();
			log.info("queryAvailableTicket 使用优惠券 result:"+result);
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		}catch(Exception e){
			log.error("queryAvailableTicket 使用优惠券 error", e);
			result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 查询我的邮利券（我的邮利券）
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryTicket4Mine")
	public JSONPObject queryTicket4Mine(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String, Object> map = ResultUtil.successMap();
		try {
			String userOnlyId = getUserOnlyId(request);
			log.info("进入方法queryTicket4Mine(),userOnlyId："+userOnlyId);
			String flag=Convert.toStr(request.getParameter("flag"));
			log.info("进入方法queryTicket4Mine(),userOnlyId："+userOnlyId+";flag:"+flag);
			Map<String, Object> result = saleActTicketService.queryUsefulTickets4Mine(userOnlyId,flag);
			map.put(YzsConstants.DATA, result);
			log.info("退出方法queryTicket4Mine()且方法执行成功！");
		} catch (Exception e) {
			log.error("方法queryTicket4Mine() 执行失败！", e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 查询用于支用的邮利券（支用页面）
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryTicket4ZY")
	public JSONPObject queryTicket4ZY(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String, Object> map = ResultUtil.successMap();
		try {
			String userOnlyId = getUserOnlyId(request);
			String applyAmount = request.getParameter("applyAmount");
			String repayType = request.getParameter("repayType");
			String periods = request.getParameter("periods");
			log.info("进入方法queryTicket4ZY(),userOnlyId："+userOnlyId);
			//组装调用使用规则的参数
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("applyAmount", applyAmount);//支用期限
			param.put("repayType", repayType);//还款方式
			param.put("periods", periods);//还款期数
			Map<String, Object> result = saleActTicketService.queryUsefulTickets4ZY(userOnlyId, param);
			map.put(YzsConstants.DATA, result);
			log.info("退出方法queryTicket4ZY()且方法执行成功！");
		} catch (Exception e) {
			log.error("方法queryTicket4ZY() 执行失败！", e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 转让邮利券
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("transferTicket")
	public JSONPObject transferTicket(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String, Object> map = ResultUtil.successMap();
		try {
			String userOnlyId = getUserOnlyId(request);
			String ticketNo = request.getParameter("ticketNo");
			log.info("进入方法transferTicket(),userOnlyId: "+userOnlyId+"ticketNo: "+ticketNo);
			//组装调用使用规则的参数
			saleActTicketService.transferTicket(ticketNo, userOnlyId);
			log.info("退出方法transferTicket()且方法执行成功！");
		} catch (Exception e) {
			log.error("方法transferTicket() 执行失败！", e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 查询转让中的邮利券
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryTransferingTickets")
	public JSONPObject queryTransferingTickets(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String, Object> map = ResultUtil.successMap();
		try {
			String userOnlyId = getUserOnlyId(request);
			log.info("进入方法queryTransferingTickets(),userOnlyId: "+userOnlyId);
			//组装调用使用规则的参数
			List<SaleTicketInfo> list = saleActTicketService.queryTransferingTickets(userOnlyId);
			map.put(YzsConstants.DATA, list);
			log.info("退出方法queryTransferingTickets()且方法执行成功！");
		} catch (Exception e) {
			log.error("方法queryTransferingTickets() 执行失败！", e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 查询已转让的邮利券
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryTransferedTickets")
	public JSONPObject queryTransferedTickets(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String, Object> map = ResultUtil.successMap();
		try {
			String userOnlyId = getUserOnlyId(request);
			log.info("进入方法queryTransferedTickets(),userOnlyId: "+userOnlyId);
			//组装调用使用规则的参数
			List<Map<String, String>> list = saleActTicketService.queryTransferedTickets(userOnlyId);
			map.put(YzsConstants.DATA, list);
			log.info("退出方法queryTransferedTickets()且方法执行成功！");
		} catch (Exception e) {
			log.error("方法queryTransferedTickets() 执行失败！", e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 查询已购买的邮利券
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryBuyedTickets")
	public JSONPObject queryBuyedTickets(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String, Object> map = ResultUtil.successMap();
		try {
			String userOnlyId = getUserOnlyId(request);
			log.info("进入方法queryBuyedTickets(),userOnlyId: "+userOnlyId);
			//组装调用使用规则的参数
			List<Map<String, String>> list = saleActTicketService.queryBuyedTickets(userOnlyId);
			map.put(YzsConstants.DATA, list);
			log.info("退出方法queryBuyedTickets()且方法执行成功！");
		} catch (Exception e) {
			log.error("方法queryBuyedTickets() 执行失败！", e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 取消转让
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("cancelTransTicket")
	public JSONPObject cancelTransTicket(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String, Object> map = ResultUtil.successMap();
		try {
			String userOnlyId = getUserOnlyId(request);
			String ticketNo = request.getParameter("ticketNo");
			log.info("进入方法cancelTransTicket(),userOnlyId: "+userOnlyId+"ticketNo: "+ticketNo);
			//组装调用使用规则的参数
			saleActTicketService.cancelTransTicket(ticketNo, userOnlyId);
			log.info("退出方法cancelTransTicket()且方法执行成功！");
		} catch (Exception e) {
			log.error("方法cancelTransTicket() 执行失败！", e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	/**
	 * 用户开户
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("openAccount")
	public JSONPObject openAccount(HttpServletRequest request, @RequestParam String jsoncallback){
		Map<String, Object> map = ResultUtil.successMap();
		try {
			String userOnlyId = getUserOnlyId(request);
			log.info("进入方法openAccount(),userOnlyId: "+userOnlyId);
			saleActTicketService.openAccount(userOnlyId);
			log.info("退出方法openAccount()且方法执行成功！");
		} catch (Exception e) {
			log.error("方法openAccount() 执行失败！", e);
			map = YZSExceptionUtil.handleException(e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	private String getUserOnlyId(HttpServletRequest request) throws Exception {
		String usronlyId =CommonHelper.getUserOnlyId(request);
		//String usronlyId="10000024271";
		return usronlyId;
	}
}
