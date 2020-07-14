package com.ule.uhj.zgd.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.object.model.interaction.coffee.Coffee;
import com.ule.uhj.Dcoffee.object.model.interaction.coffeeBeans.CoffeeBean;
import com.ule.uhj.Dcoffee.object.model.interaction.coffeeBeans.StandardBean;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.uleOperate.UleOperateClient;
import com.ule.uhj.ejb.client.zgd.ZgdQueryClient;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.util.Constant;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.uhj.zgd.service.WebCoffeeMaker;
import com.ule.vpsUser.api.chinapost.impl.ChinaPostOrgunitManager;

@Controller
@RequestMapping("/ule")
public class UleOperateController {
	private static Logger log = LoggerFactory.getLogger(UleOperateController.class);
	
	 @Autowired
	 private WebCoffeeMaker coffeeMaker;
	/**
	 * 本方法为邮乐运营数据中心展示指定区域的掌柜贷款情况
	 * @param request
	 * @return
	 */
	@RequestMapping("/operateTotalDate")
	@ResponseBody
	public JSONPObject operateTotalDate(HttpServletRequest request,@RequestParam String jsonpCallback){
		log.info("operateTotalDate begin.");
		Map<String,Object> reMap=new HashMap<String, Object>();
		//		String totalAmount="0";//当年全年贷款金额
		//		String countCredit="0";//已授信掌柜数
		//		String totalManager="0";//当年全年贷款掌柜数
		//		String postalIncome="0";//当年预计邮政收入
		//		String totalBaddebts="0";//目前坏账金额
		
		try {
			BigDecimal totalManager=new BigDecimal(0);
			BigDecimal countCredit=new BigDecimal(0);
			BigDecimal userCount=new BigDecimal(0);//区域内邮掌柜用户总数
			
			String china=Convert.toStr(request.getParameter("chinaPostId"), "");
			if(StringUtils.isBlank(china)){
				log.info("chinaPostId is null");
				reMap.clear();
				reMap.put("status", "1001");
				reMap.put("msg", "chinaPostId is null"+china);
				return new JSONPObject(jsonpCallback, UhjWebJsonUtil.parseObjToJson(reMap));
			}
			try {
				 String uleOperate=getConstantValue("uleOperate");
				 log.info(china+"operateTotalDate uleOperate"+uleOperate);
				String datatotal = WildflyBeanFactory.getUleOperateClient().getCacheCloudData(china + Constant.uleOperate.TOTAL_DATA);
				datatotal=Convert.toStr(datatotal,"");
				log.info(china+"operateTotalDate 缓存获取数据 datatotal"+ datatotal);
//				log.info("operateTotalDate 缓存获取数据 JSONPObject"+ new JSONPObject(jsonpCallback, UhjWebJsonUtil.parseObjToJson(datatotal)).toString());
				if ("true".equals(uleOperate)) {//是否使用缓存
					//检查缓存中是否存在数据，如果有则从缓存中获取
					if (StringUtils.isNotBlank(datatotal)) {
						return new JSONPObject(jsonpCallback,UhjWebJsonUtil.parseObjToJson(datatotal));
					}
				}
			} catch (Exception e) {
				log.error("operateTotalDate cash error", e);
			}
			//获取区域范围ID
			Long chinaPostId=Convert.toLong(china);	
			if(null!=chinaPostId){
				if(chinaPostId==100){//全国id
					
					userCount=getAllUserCount();//WildflyBeanFactory.getUleOperateClient().getUserCount(chinaPostId+"");
				}else{
					userCount=Convert.toBigDecimal(WildflyBeanFactory.getUleOperateClient().getUserCount(chinaPostId+""),new BigDecimal(0));
				}
				log.info("userCount...."+userCount);
				
//				if(StringUtils.isNotBlank(count)){
//					userCount=Convert.toBigDecimal(count);
//				}
				
				com.ule.vpsUser.api.common.vo.ChinaPostOrgunit cho=ChinaPostOrgunitManager.getInstance().getChinaPostOrgunitById(chinaPostId);
				log.info("cho"+JSONObject.toJSONString(cho));
				String levelName=null!=cho?cho.getLevelName():"";
				if(null==cho||StringUtils.isBlank(levelName)){
					log.info(String.format("id %s vps 没有相关的组织机构数据", chinaPostId));
					reMap.clear();
					reMap.put("status", "1002");
					reMap.put("msg", "no data from vps by id"+chinaPostId);
					return new JSONPObject(jsonpCallback, UhjWebJsonUtil.parseObjToJson(reMap));
				}
				//sendInfoToKaFka("zgd", chinaPostId+"", cho.getName());
				//根据id 和级别查询数据统计。。。。
				String levelFlag=getflagByname(levelName);
				String data[]={"totalAmount","badDog","openAccount","showMeMoney"};
				for(String s:data){
					//调用郑鑫的接口获取数据
					log.info("调用数据源"+s);
					String dataSetId = s;
					CoffeeBean bean = new StandardBean();
					bean.setID(dataSetId);
					Parameter currentParameter = new Parameter("areaType",levelFlag);
					log.info("areaType"+levelFlag);
					Parameter currentParameter1=null;
					
					if("badDog".equals(s)){
						 currentParameter1 = new Parameter("loan_time","");
					}else if("openAccount".equals(s)){
						currentParameter1 = new Parameter("loan_time",DateUtil.currDateStr());
					}else{
						currentParameter1 = new Parameter("loan_time",getYear());
					}
					Parameter currentParameter2 = new Parameter("chinaPostId",chinaPostId+"");
					
					bean.putParamer(currentParameter);
					bean.putParamer(currentParameter1);
					bean.putParamer(currentParameter2);
					log.info(s+"parameter bean."+JSONObject.toJSONString(bean));
					Coffee coffee = coffeeMaker.make(bean);
					Map<String,Object> re=(Map<String, Object>) coffee.getExtension();
					if("totalAmount".equals(s)){
						 totalManager= Convert.toBigDecimal(re.get("totalManager"),null);
					}
					if("openAccount".equals(s)){
						countCredit= Convert.toBigDecimal(re.get("countCredit"),null);
					}
					log.info(s+"coffee...."+JSONObject.toJSONString(coffee.getExtension()));
					reMap.putAll(re);
					
				}
				log.info("totalManager.."+totalManager+"...countCredit.."+countCredit+"userCount..."+userCount);
				BigDecimal countCreditRatio=new BigDecimal(0);
				BigDecimal totalManagerRatio=new BigDecimal(0);
				if(userCount.compareTo(new BigDecimal(0))>0&&null!=totalManager&&totalManager.compareTo(new BigDecimal(0))>0){
					totalManagerRatio=totalManager.divide(userCount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
				}
                 if(userCount.compareTo(new BigDecimal(0))>0&&null!=countCredit&&countCredit.compareTo(new BigDecimal(0))>0){
                	 countCreditRatio=countCredit.divide(userCount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
				}
                 log.info("countCreditRatio.."+countCreditRatio+"..totalManagerRatio..."+totalManagerRatio);
                if(totalManagerRatio.compareTo(new BigDecimal(100))>0||totalManagerRatio.compareTo(new BigDecimal(0))==0){
                	totalManagerRatio=new BigDecimal(0);
                }
                if(countCreditRatio.compareTo(new BigDecimal(100))>0||countCreditRatio.compareTo(new BigDecimal(0))==0){
                	countCreditRatio=new BigDecimal(0);
                }
                reMap.put("countCreditRatio", countCreditRatio);//已授信掌柜数占比
                reMap.put("totalManagerRatio", totalManagerRatio);//全年贷款掌柜数占比
                reMap.put("statisticsDate", DateUtil.preDateStr());//数据统计日期
				reMap.put("status", "0000");
				reMap.put("msg", "sucess");
				 
			}else{
				log.info("没有获取到组织机构ID，无法返还数据");
				reMap.clear();
				reMap.put("status", "1003");
				reMap.put("msg", "没有获取到组织机构ID，无法返还数据");
				reMap.put("totalAmount", "0");//当年全年贷款金额
				reMap.put("countCredit", "0");//已授信掌柜数
				reMap.put("totalManager", "0");//当年全年贷款掌柜数
				reMap.put("postalIncome", "0");//当年预计邮政收入
				reMap.put("totalBaddebts", "0");//目前坏账金额
				reMap.put("countCreditRatio", 0);//已授信掌柜数占比
	            reMap.put("totalManagerRatio", 0);//全年贷款掌柜数占比
	            reMap.put("statisticsDate", DateUtil.preDateStr());//数据统计日期
			}
			
			
		} catch (Exception e) {
			log.error("operateTotalDate error!",e);
			reMap.clear();
			reMap.put("status", "1004");
			reMap.put("msg", "系统异常，请刷新页面");
			reMap.put("totalAmount", "0");//当年全年贷款金额
			reMap.put("countCredit", "0");//已授信掌柜数
			reMap.put("totalManager", "0");//当年全年贷款掌柜数
			reMap.put("postalIncome", "0");//当年预计邮政收入
			reMap.put("totalBaddebts", "0");//目前坏账金额
			reMap.put("countCreditRatio", 0);//已授信掌柜数占比
            reMap.put("totalManagerRatio", 0);//全年贷款掌柜数占比
//			return new JSONPObject(jsonpCallback, UhjWebJsonUtil.parseObjToJson(reMap));
		}
		log.info("operateTotalDate result.."+reMap.toString());
		return  new JSONPObject(jsonpCallback, UhjWebJsonUtil.parseObjToJson(reMap));
	}
	/**
	 * 获取全国邮掌柜总数，并更新缓存（每天第一次更新）
	 * @return
	 */
	private BigDecimal getAllUserCount(){
		BigDecimal total=new BigDecimal(0);
	 
		try {
			UleOperateClient client=WildflyBeanFactory.getUleOperateClient();
			String count = client.getUserCount("100");
			log.info("getAllUserCount....."+count);
			if (StringUtils.isNotBlank(count)) {
				log.info("count...." + count);
				String countDate = getConstantValue("opcUserCountDate");
				String nowDate =DateUtil.currDateStr() ;
				log.info(String.format("countDate, %s,nowDate, %s", countDate,nowDate));
				if (countDate.equals(nowDate)) {
					total = Convert.toBigDecimal(count);
                     return total;
				} 

			} 
			log.info("country count update.....");
				List<Map<String,Object>> postList=getPostList(100L);
				if(null!=postList&&postList.size()>0){
					BigDecimal pcount=new BigDecimal(0);
					String id="";
					for(Map<String,Object> m:postList){
						 id=Convert.toStr(m.get("id"));
						pcount  = Convert.toBigDecimal(client.getUserCount(id),new BigDecimal(0));
						log.info(String.format("id %s,pcount,%s",id, pcount));
						total= total.add(pcount);
					}
					
				}
				if(total.compareTo(new BigDecimal(0))>0){
					
					client.setUserCount("100", total+"");//更新缓存中全国邮掌柜总数,并更新常量日期
				}
				log.info("getAllUserCount..total..."+total);
			
		} catch (Exception e) {
			total=new BigDecimal(0);
			log.error("getAllUserCount error", e);
		}
		return total;
	}
	/**
	 * 本方法为邮乐运营数据中心展示指定区域的掌柜贷款情况
	 * operateTotalDate 的备用程序
	 * @param request
	 * @param jsonpCallback
	 * @return
	 */
	@RequestMapping("/operateTotalDateEjb")
	@ResponseBody
	public JSONPObject operateTotalDateEjb(HttpServletRequest request,@RequestParam String jsonpCallback){
		log.info("operateTotalDate begin.");
		Map<String,Object> reMap=new HashMap<String, Object>();
		try {
			////数据组织
			//获取区域范围ID
			String chinaPostId=Convert.toStr(request.getParameter("chinaPostId"), "");	
			//ejb备用程序
			if(StringUtils.isNotBlank(chinaPostId)){
				reMap=WildflyBeanFactory.getUleOperateClient().operateTotalDate(chinaPostId);
				log.info("ejb restult"+reMap.toString());
				 
			}else{
				log.info("没有获取到组织机构ID，无法返还数据");
				reMap.clear();
				reMap.put("status", "1000");
				reMap.put("msg", "没有获取到组织机构ID，无法返还数据");
				reMap.put("totalAmount", "0");//当年全年贷款金额
				reMap.put("countCredit", "0");//已授信掌柜数
				reMap.put("totalManager", "0");//当年全年贷款掌柜数
				reMap.put("postalIncome", "0");//当年预计邮政收入
				reMap.put("totalBaddebts", "0");//目前坏账金额
			}
			
			
		} catch (Exception e) {
			log.error("operateTotalDate error!",e);
			reMap.clear();
			reMap.put("status", "1000");
			reMap.put("msg", "service is error");
			reMap.put("totalAmount", "0");//当年全年贷款金额
			reMap.put("countCredit", "0");//已授信掌柜数
			reMap.put("totalManager", "0");//当年全年贷款掌柜数
			reMap.put("postalIncome", "0");//当年预计邮政收入
			reMap.put("totalBaddebts", "0");
			 new JSONPObject(jsonpCallback, UhjWebJsonUtil.parseObjToJson(reMap));
		}
		log.info("result.."+reMap.toString());
		return  new JSONPObject(jsonpCallback, UhjWebJsonUtil.parseObjToJson(reMap));
	}
	/**
	 * 本方法为邮乐运营数据中心展示指定区域的掌柜贷款情况
	 * @param request
	 * @return
	 */
	@RequestMapping("/operateDeatil")
	@ResponseBody
	public JSONPObject operateDeatil(HttpServletRequest request,@RequestParam String jsonpCallback){
		log.info("operateDeatil begin.");
		Map<String,Object> reMap=new HashMap<String, Object>();
		try {
	        ////数据组织
			Long chinaPostId=Convert.toLong(request.getParameter("chinaPostId"));
			
			try{
			String uleOperate=getConstantValue("uleOperate");
			log.info("operateDeatil uleOperate"+uleOperate);
			String datatotal=WildflyBeanFactory.getUleOperateClient().getCacheCloudData(chinaPostId+Constant.uleOperate.DATA_DETAIL);
			datatotal=Convert.toStr(datatotal,"");
			log.info(chinaPostId+"operateDeatil 缓存获取数据 datatotal"+ datatotal);
//			log.info("operateDeatil 缓存获取数据 JSONPObject"+ new JSONPObject(jsonpCallback, UhjWebJsonUtil.parseObjToJson(datatotal)).toString());
			if("true".equals(uleOperate)){
				//检查缓存中是否存在数据，如果有则从缓存中获取
				if(StringUtils.isNotBlank(datatotal)){
					log.info("operateDeatil 缓存获取数据 return"+chinaPostId+Constant.uleOperate.DATA_DETAIL);
					return new JSONPObject(jsonpCallback, UhjWebJsonUtil.parseObjToJson(datatotal));	
				}
				
			}
			}catch(Exception e1){
				log.error("operateDeatil cash error", e1);
			}
			if(null!=chinaPostId){
				com.ule.vpsUser.api.common.vo.ChinaPostOrgunit cho=ChinaPostOrgunitManager.getInstance().getChinaPostOrgunitById(chinaPostId);
				log.info("cho:"+JSONObject.toJSONString(cho));
				String levelName=null!=cho?cho.getLevelName():"";
				String areaName=null!=cho?cho.getName():"";
				if(null==cho||StringUtils.isBlank(levelName)){
					log.info(String.format("id %s vps 没有相关的组织机构数据", chinaPostId));
					reMap.clear();
					reMap.put("status", "1000");
					reMap.put("msg", "no data from vps by id"+chinaPostId);
					reMap.put("data",  new ArrayList<Map<String, Object>>());
					reMap.put("postList",  new ArrayList<Map<String, Object>>());
					return new JSONPObject(jsonpCallback, UhjWebJsonUtil.parseObjToJson(reMap));
				}
				//sendInfoToKaFka("zgd", chinaPostId+"", areaName);
				String levelFlag=getflagByname(levelName);
				reMap.put("levelName", levelName);
				reMap.put("areaName", areaName);
				reMap.put("parentId", cho.getParentId());
				List<Map<String,Object>> postList=getPostList(chinaPostId);//new ArrayList<Map<String,Object>>();
				//根据区域ID获取下级区域
//				List<com.ule.vpsUser.api.common.vo.ChinaPostOrgunit> chList=ChinaPostOrgunitManager.getInstance().getChinaPostOrgunitsByParentId(chinaPostId);
//				log.info("chList"+JSONObject.toJSONString(chList));
//				if(null!=chList&&chList.size()>0){
//					for(com.ule.vpsUser.api.common.vo.ChinaPostOrgunit co:chList){
//						Map<String,Object> m=new HashMap<String, Object>();
//						m.put("id", Convert.toStr(co.getId(), ""));
//						m.put("name",Convert.toStr(co.getName(), ""));
//						postList.add(m);
//						
//					}
//				}
				log.info("postList:"+JSONObject.toJSONString(postList));
				reMap.put("postList", postList);
				
				//组织详情数据  调用郑鑫程序
				CoffeeBean bean = new StandardBean();
				if("area".equals(levelFlag)){
					bean.setID("targetAreaOrg");//郑鑫来了，需要配新的数据源
					reMap.put("postList",  new ArrayList<Map<String, Object>>());//到县级，就不再让客户选择下级区域了。
				}else{
					bean.setID("everyOrg");
				}
				Parameter currentParameter = new Parameter("areaType",levelFlag);
				Parameter currentParameter1=new Parameter("loan_time",getYear());;
			 
				Parameter currentParameter2 = new Parameter("chinaPostId",chinaPostId+"");
				
				bean.putParamer(currentParameter);
				bean.putParamer(currentParameter1);
				bean.putParamer(currentParameter2);
				Coffee coffee = coffeeMaker.make(bean);
				log.info("everyOrg coffee...."+JSONObject.toJSONString(coffee.getExtension()));
				if(null!=coffee.getExtension()){
					List<Map<String,Object>> reList=(List<Map<String, Object>>) coffee.getExtension();
					String area="";
					String areaId="";
					List<Map<String,Object>> rmList=new ArrayList<Map<String,Object>>();
					String st=getConstantValue("opcIdNotTotal");
					for(Map<String,Object> m:reList){
						areaId=Convert.toStr(m.get("area"),"");
						m.put("areaId", areaId);
						//因为部分县级的的支局是没有的，后台SQLgroup BY 的时候归类到县级，所以要和县级的ID比较
						if(StringUtils.isNotBlank(areaId)&&areaId.equals(chinaPostId+"")){
							area=areaName;
						}else{
							area=getName(postList, areaId);
						}
						m.put("area", area);
						if(!checkId(st,areaId)){
							rmList.add(m);
						}
						
					}
					if(rmList.size()>0){
						
						reList.removeAll(rmList);
					}
					reMap.put("data", reList);
					
				}
				
				reMap.put("status", "0000");
				reMap.put("msg", "sucess");
			}else{
				log.info("没有获取到组织机构ID，无法返还数据");
				reMap.clear();
				reMap.put("status", "1000");
				reMap.put("msg", "没有获取到组织机构ID，无法返还数据");
				reMap.put("data",  new ArrayList<Map<String, Object>>());
				reMap.put("postList",  new ArrayList<Map<String, Object>>());
			}
		 
		 
			
		} catch (Exception e) {
			log.error("operateDeatil error!",e);
			reMap.clear();
			reMap.put("status", "1000");
			reMap.put("msg", "service is error");
			reMap.put("data",  new ArrayList<Map<String, Object>>());
			reMap.put("postList",  new ArrayList<Map<String, Object>>());
			return new JSONPObject(jsonpCallback, "系统异常");		 
		}
		log.info("operateDeatil result:"+JSONObject.toJSONString(reMap));
		return new JSONPObject(jsonpCallback, UhjWebJsonUtil.parseObjToJson(reMap));
	}
	
	/**
	 * 本方法为邮乐运营数据中心展示指定区域的掌柜贷款情况  operateDeatil 的 备用程序
	 * @param request
	 * @return
	 */
	@RequestMapping("/operateDeatilEjb")
	@ResponseBody
	public JSONPObject operateDeatilEjb(HttpServletRequest request,@RequestParam String jsonpCallback){
		log.info("operateDeatil begin.");
 
		Map<String,Object> reMap=new HashMap<String, Object>();
		try {
			String year=getYear();
	        ////数据组织
			String chinaPostId=Convert.toStr(request.getParameter("chinaPostId"), "");	
			if(StringUtils.isNotBlank(chinaPostId)){
				//ejb 备用程序
			   reMap=WildflyBeanFactory.getUleOperateClient().operateDeatil(chinaPostId+"");
				log.info("ejb restult"+reMap.toString());
				
				reMap.put("status", "0000");
				reMap.put("msg", "sucess");
			}else{
				log.info("没有获取到组织机构ID，无法返还数据");
				reMap.clear();
				reMap.put("status", "1000");
				reMap.put("msg", "没有获取到组织机构ID，无法返还数据");
				reMap.put("data",  new ArrayList<Map<String, Object>>());
				reMap.put("postList",  new ArrayList<Map<String, Object>>());
			}
			
		} catch (Exception e) {
			log.error("operateDeatil error!",e);
			reMap.clear();
			reMap.put("status", "1000");
			reMap.put("msg", "service is error");
			reMap.put("data",  new ArrayList<Map<String, Object>>());
			reMap.put("postList",  new ArrayList<Map<String, Object>>());
			return new JSONPObject(jsonpCallback, "系统异常");		 
		}
		log.info("result:"+JSONObject.toJSONString(reMap));
		return new JSONPObject(jsonpCallback, UhjWebJsonUtil.parseObjToJson(reMap));
	}
	private String getName(List<Map<String, Object>> postList,String id){
		String re="";
		if(StringUtils.isNotBlank(id)&&null!=postList&&postList.size()>0){
			id=id.trim();
			log.info("list size"+postList.size());
			log.info(String.format("id %s", id));
			for(Map<String, Object> m:postList){
				String mid=Convert.toStr(m.get("id"), "");
				
				if(mid.equals(id)){
//					log.info("mid id is same"+mid);
					re=Convert.toStr(m.get("name"));
					break;
				}
				
			}
		}
		log.info(String.format(" id%s,name%s", id,re));
		return re;
		
	}
	private String getflagByname(String levelName){
		String flag="all";//默认国家级别
		if(StringUtils.isNotBlank(levelName)){
			
		    if(levelName.contains("省")){
		    flag="province";
			}else if(levelName.contains("市")){
				flag="city";
			}else if(levelName.contains("县")){
				flag="area";
			}else if(levelName.contains("支局")){
				flag="town";
			}
		}
		return flag;
	}
	private String getYear(){
		String re="2018-01-01";
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH,0);
		c.set(Calendar.DATE, 1);
		re=DateUtil.format(c.getTime());
		
		
		return re;
	}
	public static void main(String args[]){
		List<com.ule.vpsUser.api.common.vo.ChinaPostOrgunit> unitInfos;
		try {
			unitInfos = ChinaPostOrgunitManager.getInstance().getChinaPostOrgunitsByParentId(100l);
			System.out.println("sss"+JSONObject.toJSON(unitInfos));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//  e.printStackTrace();
		}
//		
//		
//		try {
//			String date=DateUtil.currDateStr();
//			System.out.println(date);
//			
//			Calendar c = Calendar.getInstance();
//			c.set(Calendar.MONTH,0);
//			c.set(Calendar.DATE, 1);
//			String ss=DateUtil.format(c.getTime());
//			 
//			System.out.println(ss);
//			 
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	@RequestMapping("/{chinaPostIdstr}/{type}/test")
	@ResponseBody
	public String chinaPost(HttpServletRequest request,@PathVariable String chinaPostIdstr,@PathVariable String type){
		log.info("operateDeatil begin.");
		JSONObject js=new JSONObject();
	 
		 
		try {
			Long chinaPostId=Convert.toLong(chinaPostIdstr,100l);
			js.put("type", type);
			if("1".equals(type)){
				if(null!=chinaPostId){
					
					List<Map<String,Object>> postList=getPostList(chinaPostId);
					log.info("postList:"+JSONObject.toJSONString(postList));
					js.put("postList", postList);
					
					com.ule.vpsUser.api.common.vo.ChinaPostOrgunit cho=ChinaPostOrgunitManager.getInstance().getChinaPostOrgunitById(chinaPostId);
					log.info("cho:"+JSONObject.toJSONString(cho));
					js.put("cho", cho);
					
				}
			}else if("2".equals(type)){
				
				Map<String,Object>  re=WildflyBeanFactory.getUleOperateClient().operateTotalDate(chinaPostId+"");
				log.info("ejb restult"+re.toString());
				js.put("totaldate:", re);
				Map<String,Object>  re2=WildflyBeanFactory.getUleOperateClient().operateDeatil(chinaPostId+"");
				js.put("dateDetail:", re2);
				log.info("ejb restult2"+re2.toString());
				String count=WildflyBeanFactory.getUleOperateClient().getUserCount(chinaPostId+"");
				log.info("count...."+count);
				js.put("allUser", count);
			}else if("3".equals(type)){
				JSONPObject result1=operateTotalDate(request, "jsonpCallback");
				log.info("operateTotalDate result1...."+result1);
				js.put("operateTotalDate", result1);
				JSONPObject result2=operateDeatil(request, "jsonpCallback");
				js.put("operateDeatil", result2);
				log.info("operateDeatil result2...."+result2.toString());
			}
			
		 
		} catch (Exception e) {
			log.error("operateDeatil error!",e);
			 
			js.put("msg", "service is error");
	 
			return js.toJSONString();			 
		}
		log.info("result:"+js.toJSONString());
		return js.toJSONString();
	}
	
	private List<Map<String,Object>> getPostList(Long chinaPostId){
		List<Map<String,Object>> postList=new ArrayList<Map<String,Object>>();
		try {
			//根据区域ID获取下级区域                                            getChinaPostOrgunitsByParentId
			List<com.ule.vpsUser.api.common.vo.ChinaPostOrgunit> chList = ChinaPostOrgunitManager.getInstance().getChinaPostOrgunitsByParentId(chinaPostId);
			/***
			 * 2019-07-01
			 * 四级机构改造，需要额外调用一个接口，获取非招商机构
			 * By.ZhengXin
			 */
			try{
				List chList_ext = ChinaPostOrgunitManager.getInstance().getChinaPostOrgunitsByParentId(chinaPostId, 1l, 1l);
				if(chList!=null && chList_ext!=null && chList_ext.size()>0){
					chList.addAll(chList_ext);
				}
			}catch (Exception e){
				log.info("getChinaPostOrgunitsByParentId 获取非招商机构接口调用失败了！", e);
			}
			log.info("chList" + JSONObject.toJSONString(chList));
			
			if (null != chList && chList.size() > 0) {
				String st=getConstantValue("opcIdNotTotal");
				for (com.ule.vpsUser.api.common.vo.ChinaPostOrgunit co : chList) {
					Map<String, Object> m = new HashMap<String, Object>();
					String id=Convert.toStr(co.getId(), "");
					if(checkId(st,id)){//邮乐海外、邮乐农品排除
						m.put("id", id);
						m.put("name", Convert.toStr(co.getName(), ""));
						postList.add(m);
						
					}

				}
			}
		} catch (Exception e) {
			log.error("getPostList...."+chinaPostId, e);
			postList=new ArrayList<Map<String,Object>>();
		}
		return postList;
	}
	private boolean checkId(String st,String id){
		boolean flag=true;
//		String st=getConstantValue("opcIdNotTotal");
		log.info("checkId..."+st);
		if(StringUtils.isNotBlank(st)&&StringUtils.isNotBlank(id)){
			String ids[]=st.split(",");
			
			for(String dest:ids){
				if(dest.equals(id)){
					flag=false;
					break;
				}
			}
			
		}
		log.info("checkId..result."+flag);
		return flag;
	}
	private String getConstantValue(String key){
		String re="";
		
		try {
			Map<String, Class<?>> constantMap = new HashMap<String, Class<?>>();
			constantMap.put(key, String.class);
			ZgdQueryClient zgdQueryClient = WildflyBeanFactory.getZgdQueryClient();
			Map<String, Object> conMap = zgdQueryClient.queryZgdConstantValue2(constantMap);
			List<String> provinceCodeList = (List<String>) conMap.get(key);//邮掌柜总数统计
			if (null != provinceCodeList && provinceCodeList.size() > 0) {
				log.info("showTown provinceCodeList:" + provinceCodeList.toString());
				re = Convert.toStr(provinceCodeList.get(0));
			}
		} catch (Exception e) {
			re="";
			log.error("getConstantValue error", e);
		}
		log.info(key+"..key-value.."+re);
		return re;
	}
}
