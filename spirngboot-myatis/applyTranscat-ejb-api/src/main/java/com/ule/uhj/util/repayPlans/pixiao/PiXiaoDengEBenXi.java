package com.ule.uhj.util.repayPlans.pixiao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.repayPlans.Period;


/**
 * 等额本息还款计划,选定还款期数,固定还款日
 * 贷款方式：等额本息
 * 贷款利率：6%（年化）
 * 还款日：若客户无固定还款日，则支用当天为还款日，若客户已有固定还款日设客户所选还款日为N，支用时间为M，则
 * a) M-N<15天，则第一期为支用时间后距离支用时间最近月下月的还款日，举例：若客户1月1日支用，还款日为10日，则首次还款日为2月10日，首期按日计息，还款日为：2月10日、3月10日、4月10日
 * b) M-N>=15，则第一期为支用时间后距离支用时间最近月的还款日，举例：若客户1月1日支用，还款日为16日，则首次还款日为1月16日，首期按日计息，还款日为：1月16日、2月16日、3月16日
 * 每期还款金额：设贷款总额为Y，贷款期数为n，贷款利率为i（对应的月利率，季利率，年利率，半年利率，半月利率，单周利率，双周利率）,当前期数第k期，则：
 * 分期还款额 = Y*i*(1+i)^n / ((1+i)^n - 1)
 * 分期还本金额 = Y*i*(1+i)^(k-1) / ((1+i)^n - 1)
 * 分期利息=分期还款额-分期还本金额
 * 首期利息按日计算
 * 末期本金=本金总额-已还本金
 * 末期利息=当期还款总额-末期本金
 * @author zhangyaou
 */
public class PiXiaoDengEBenXi {

	private static final Logger log = Logger.getLogger(PiXiaoDengEBenXi.class);
	/** 
	 * 一次性还本付息(批销用) 
	 * 根据用户选择的最后还款日,预测放款日,借款金额和利率 
	 * 生成初始还款计划 
	 * @param loanDate 预测放款日   yyyy-mm-dd  默认申请日当天放款
	 * @param fixRepayDate 固定还款日
	 * @param loanAmount 借款金额
	 * @param yearRate 年利率
	 * @return Map<String, Object>
	 * @author cuitiantian 2017-02-22	    
	 * @throws Exception
	 */
	public static Map<String, Object> createPlansOneTimeServicing (String loanDate, String fixRepayDate,BigDecimal loanAmount, BigDecimal yearRate) throws Exception{
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		BigDecimal sumInter=BigDecimal.ZERO;
		Map<String, Object> result =new HashMap<String, Object>();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sd.parse(loanDate));
		cal.add(Calendar.MONTH, 2);
		String date2month = sd.format(cal.getTime());
		if(fixRepayDate.length()==1){
			fixRepayDate="0"+fixRepayDate;
			
		}
		String repayDate=date2month.substring(0,8)+fixRepayDate;
		cal.setTime(sd.parse(repayDate));
		
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(sd.parse(loanDate));
		int days=diffDays(cal,cal2);
		sumInter=loanAmount.multiply(yearRate).multiply(new BigDecimal(days)).divide(new BigDecimal(daysOfYear),BigDecimal.ROUND_HALF_UP).setScale(2,RoundingMode.HALF_UP);
		result.put("index","1");//期数
		result.put("inter",sumInter);//利息
		result.put("princ",loanAmount);//本金
		result.put("endDate",repayDate);//还款日期
		result.put("princInter",sumInter.add(loanAmount));//应还金额
		result.put("startDate",loanDate);//开始日期
		return result;	
	}
	/**
	 * 等额本息 
	 * 根据用户选择的最后还款日,放款日,借款金额,借款期数和利率 
	 * 生成初始还款计划 
	 * 固定借款月份时长
	 * @param loanDate 放款日   yyyy-mm-dd
	 * @param fixRepayDate 固定还款日
	 * @param fixedPeriods 月数
	 * @param loanAmount 借款金额
	 * @param yearRate 年利率
	 * @return List<PeriodScope>
	 * @throws Exception
	 */
	public static List<Period> createPlans(String loanDate, String fixRepayDate,  int fixedPeriods, BigDecimal loanAmount, BigDecimal yearRate) throws Exception{
		if(Check.haveBlank(new Object[]{loanDate, fixedPeriods, loanAmount, yearRate})){
			log.info(String.format("business参数校验失败:applyDate:%s, period:%s, lendAmount:%s, interRate:%s", loanDate, fixedPeriods, loanAmount, yearRate));
			throw new Exception(String.format("business参数校验失败:applyDate:%s, period:%s, lendAmount:%s, interRate:%s", loanDate, fixedPeriods, loanAmount, yearRate));
		}
		//商乐贷固定还款日按照放款日的日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(!loanDate.equals(sdf.format(sdf.parse(loanDate)))){
			throw new Exception(String.format("放款日期%s必须为yyyy-MM-dd", loanDate));
		}else{
			if(fixRepayDate == null || fixRepayDate.toString().trim().length() == 0){
				fixRepayDate = new SimpleDateFormat("dd").format(sdf.parse(loanDate));
			}
		}
		log.info(String.format("fixRepayDate:%s", fixRepayDate));
		List<Period> pss = initPeriods(fixRepayDate, loanDate, fixedPeriods);
		BigDecimal repidAmount = BigDecimal.ZERO;
		//计算每期金额
		BigDecimal princAndInter = null;
		BigDecimal princ = null;
		BigDecimal inter = null;
		
		for(int k = 0; k < pss.size(); k++){
			princAndInter = loanAmount.multiply(getMonthRate(yearRate)).multiply(BigDecimal.valueOf(Math.pow(BigDecimal.ONE.add(getMonthRate(yearRate)).doubleValue(), fixedPeriods)))
					.divide(BigDecimal.valueOf(Math.pow(BigDecimal.ONE.add(getMonthRate(yearRate)).doubleValue(), fixedPeriods)).subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
			princ = loanAmount.multiply(getMonthRate(yearRate)).multiply(BigDecimal.valueOf(Math.pow(BigDecimal.ONE.add(getMonthRate(yearRate)).doubleValue(), Convert.toDouble(pss.get(k).getIndex()-1) )))
					.divide(BigDecimal.valueOf(Math.pow(BigDecimal.ONE.add(getMonthRate(yearRate)).doubleValue(), fixedPeriods)).subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
			inter = princAndInter.subtract(princ);
			
			pss.get(k).setCurrAmount(princAndInter);
			if(k == pss.size() - 1){ //最后一期
				pss.get(k).setCurrPrinc(loanAmount.subtract(repidAmount));//本金重新计算 总本金 - 前面所有期本金
				pss.get(k).setCurrInter(pss.get(k).getCurrAmount().subtract(pss.get(k).getCurrPrinc()));//最后一期利息 使用最后一期本息和 - 最后一期本金
			}else{
				pss.get(k).setCurrPrinc(princ);
				pss.get(k).setCurrInter(inter);
			}
			pss.get(k).setCurrBasePrin(loanAmount.subtract(repidAmount));
			repidAmount = repidAmount.add(princ);
//			log.info(pss.get(k).toString());
		}
		
		//首期利息按日计算
		pss.get(0).setCurrInter(BigDecimal.valueOf(diffDays(pss.get(0).getEnd(), pss.get(0).getStart()))
				.multiply(getDayRate(yearRate)).multiply(loanAmount).setScale(2, RoundingMode.HALF_UP));
		pss.get(0).setCurrAmount(pss.get(0).getCurrPrinc().add(pss.get(0).getCurrInter()));
		return pss;
	}
	
	/** 
	 * 等额本息
	 * 根据用户选择的最后还款日,预测放款日,借款金额和利率 
	 * 生成初始还款计划 
	 * 固定借款月份时长
	 * @param loanDate 预测放款日   yyyy-mm-dd  默认申请日当天放款
	 * @param fixRepayDate 固定还款日
	 * @param fixedPeriods 月数
	 * @param loanAmount 借款金额
	 * @param yearRate 年利率
	 * @return Map<String, Object>
	 * 		    还款计划数据   list
	 * 		    还款计划总利息	 sumInter
	 * @throws Exception
	 */
	public static Map<String, Object> createPlansJsonResult(String loanDate, String fixRepayDate,  int fixedPeriods, BigDecimal loanAmount, BigDecimal yearRate) throws Exception{
		BigDecimal sumInter=BigDecimal.ZERO;
		BigDecimal sumPrinc=BigDecimal.ZERO;
		Map<String, Object> result =new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		List<Period> pss = createPlans(loanDate, fixRepayDate, fixedPeriods, loanAmount, yearRate);
		//计算每期信息
		for (Period ps : pss) {
			String startDate=new SimpleDateFormat("yyyy-MM-dd").format(ps.getStart().getTime());
			String endDate= new SimpleDateFormat("yyyy-MM-dd").format(ps.getEnd().getTime());
			Map<String, String> map =new HashMap<String, String>();
			map.put("index", ""+ps.getIndex());
			map.put("startDate", startDate);
			map.put("endDate",   endDate);
			map.put("princ", ps.getCurrPrinc().toString());  
			map.put("inter",ps.getCurrInter().toString());
			map.put("princInter",ps.getCurrInter().add(ps.getCurrPrinc()).toString());
			map.put("day", diffDays(ps.getEnd(), ps.getStart()) + "");
			sumInter=sumInter.add(ps.getCurrInter());
			sumPrinc=sumPrinc.add(ps.getCurrPrinc());
			list.add(map);
		}
		result.put("list", list);
		result.put("sumInter", sumInter);
		result.put("sumPrinc", sumPrinc);
		result.put("lastRepayDate", new SimpleDateFormat("yyyy-MM-dd").format(pss.get(pss.size() - 1).getEnd().getTime()));
		result.put("code", "0000");
		return result;	
	}
	
	/**
	 * 选定借款期数, 构造每期参数  当天放款 借款当月不还款 (还款日可能是用户自己设定1~28 修改后借款期限范围应该随之更新)
	 * @param fixedRepayDate 固定还款日 1~28 yyyy-MM-dd
	 * @param loanBeginDate 借款生效时间(放款时间)
	 * @param fixedPeriods 借款月数
	 * @return List<Period>
	 * @throws Exception
	 */
	public static List<Period> initPeriods(String fixedRepayDate, String loanBeginDate, int fixedPeriods) throws Exception {
		if (Integer.parseInt(fixedRepayDate) < 1 || Integer.parseInt(fixedRepayDate) > 28) {
			throw new Exception("固定还款日必须为1~28");
		}
		if (fixedPeriods < 1) {
			throw new Exception("期数必须大于1");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(loanBeginDate == null || loanBeginDate.toString().trim().length() == 0 || !loanBeginDate.equals(sdf.format(sdf.parse(loanBeginDate)))){
			throw new Exception(String.format("请检查放款日期%s", loanBeginDate));
		}
		List<Period> pss = new ArrayList<Period>();
		// 获取借据开始日期
		Calendar s = Calendar.getInstance();
		s.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(loanBeginDate));
		Calendar e = newCalendar(s.get(Calendar.YEAR), s.get(Calendar.MONTH), Integer.parseInt(fixedRepayDate));
		if(diffDays(e, s) < 15){
			e = addCalendar(e, Calendar.MONTH, 1);
		}
		pss.add(new Period());
		((Period)pss.get(0)).setIndex(1);
		((Period)pss.get(0)).setStart(s);
		((Period)pss.get(0)).setEnd(e);
		for(int i = 1; i < fixedPeriods; i++){
			//第一期还款时间不小于15天,借款当天计息
			pss.add(i, new Period());
			((Period)pss.get(i)).setIndex(i + 1);
			((Period)pss.get(i)).setStart(((Period)pss.get(i-1)).getEnd());
			((Period)pss.get(i)).setEnd(addCalendar(((Period)pss.get(i)).getStart(), Calendar.MONTH, 1));
		}
		return pss;
	}
	
	private static Calendar newCalendar(int year, int month, int date) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, date, 0, 0, 0);
		return c;
	}
	
	private static Calendar addCalendar(Calendar calendar, int fieldType, int add) {
		Calendar c = Calendar.getInstance();
		c.setTime(calendar.getTime());
		c.add(fieldType, add);
		return c;
	}
	
	/**
     * 移除时分秒
     * @param c
     */
    public static void removeHMS(Calendar c){
    	if(c == null){
    		return;
    	}
    	c.set(Calendar.HOUR_OF_DAY, 0);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
    }
    
    /**
     * 日期差
     * @param after 减数
     * @param before 被减数
     * @return 日期差 单位天
     */
    public static int diffDays(Calendar after, Calendar before){
    	removeHMS(after);
    	removeHMS(before);
    	return (int) ((after.getTimeInMillis() - before.getTimeInMillis())/(1000*3600*24));
    }
	
	/**
	 * 年利率默认值
	 */
    public static final BigDecimal defaultYearRate = BigDecimal.valueOf(0.0720);
	/**
	 * 利率按照一年多少天算  365
	 */
    public static final int daysOfYear = 365; 
	/**
	 * 月利率精确到的小数位  20
	 */
	private static int xiaoShuMonth = 20;
	/**
	 * 日利率精确到的小数位  20
	 */
	private static int xiaoShuDay = 20;
	
	/* 
	 * 月利率
	 */
	protected static  BigDecimal getMonthRate(BigDecimal paraYearRate) {
		if(paraYearRate == null || BigDecimal.ZERO.compareTo(paraYearRate) >= 0){
			return defaultYearRate.divide(BigDecimal.valueOf(12), xiaoShuMonth, RoundingMode.HALF_UP);
		}
		return paraYearRate.divide(BigDecimal.valueOf(12), xiaoShuMonth, RoundingMode.HALF_UP);
	}
	/**
	 * 日利率
	 */
	public static BigDecimal getDayRate(BigDecimal paraYearRate){
		if(paraYearRate == null || BigDecimal.ZERO.compareTo(paraYearRate) >= 0){
			return defaultYearRate.divide(BigDecimal.valueOf(daysOfYear), xiaoShuDay, RoundingMode.HALF_UP);
		}
		return paraYearRate.divide(BigDecimal.valueOf(daysOfYear), xiaoShuDay, RoundingMode.HALF_UP);
	}
	
	public static void main(String[] args) throws Exception {
//		new PiXiaoDengEBenXi().createPlans("2016-10-27", "25", 3, new BigDecimal("50000"), new BigDecimal("0.06"));
//		new DengEBenXiWithFixedPeriod().createPlans("2016-10-27", "25", 3, new BigDecimal("1001"), new BigDecimal("0.12"));
		
//		List<Period> initPeriods = initPeriods("10", "2016-01-01", 3);
//		List<Period> initPeriods = initPeriods("16", "2016-01-01", 3);
//		for (Period period : initPeriods) {
//			System.out.println(period);
//		}
//		System.out.println(createPlansOneTimeServicing("2017-04-25", "1", new BigDecimal(3000), new BigDecimal(0.9)));
//		System.out.println(createPlansOneTimeServicing("2017-03-08", "12", new BigDecimal(10000),new BigDecimal(0.09)));
	}
}
