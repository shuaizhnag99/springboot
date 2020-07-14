package com.ule.uhj.timer;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

//import com.ule.uhj.ejb.client.tickettask.TicketTaskClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ule.uhj.ejb.client.WildflyBeanFactory;
//import com.ule.uhj.ejb.client.UhjClientFactory;
//import com.ule.uhj.ejb.client.acc.AccountTerminalClient;
//import com.ule.uhj.ejb.client.pixiao.PiXiaoYcLoanClient;
//import com.ule.uhj.ejb.client.ycZgd.BangZGClient;
//import com.ule.uhj.ejb.client.ycZgd.BangZGTaskClient;
//import com.ule.uhj.ejb.client.ycZgd.IntpriceClient;
//import com.ule.uhj.ejb.client.ycZgd.VoiceMessageClient;
//import com.ule.uhj.ejb.client.ycZgd.YCZgdClient;
//import com.ule.uhj.ejb.client.ycZgd.YCZgdDailySheetClient;
//import com.ule.uhj.ejb.client.ycZgd.YCZgdRepayDetailClient;
//import com.ule.uhj.ejb.client.ycZgd.YzgMobileClient;
//import com.ule.uhj.ejb.client.ycZgd.ZgdWhiteClient;
//import com.ule.uhj.ejb.client.zgd.YcNoticeUleClient;
//import com.ule.uhj.ejb.client.zgd.ZgdClient;

/**
 * 掌柜贷定时任务
 */
public class Run {
	private static Log log = LogFactory.getLog(Run.class);

	/**
	 * -t表示timer 数字表示任务
	 * -t1表示根据已放款订单生成借据和还款计划(每10分钟执行)
	 * -t2更新逾期的账单费用(凌晨3点执行一次)
	 * -t3撤销昨天生成单未还款的账单(凌晨1点和6点各执行一次)
	 */
	private static final String info = "Usage:java -jar <filename.jar> "
			+ "[-t 1|2|3|4]  传参-t1表示根据已放款订单生成借据和还款计划(每10分钟执行); "
			+ "传参-t2更新逾期的账单费用(凌晨3点执行一次);传参-t3撤销昨天生成单未还款的账单(凌晨1点和6点各执行一次)";

	public static void main(String[] args) {
		try {
//			args = new String[]{"-t18"};
			Long timeStart = Calendar.getInstance().getTimeInMillis();
			Map<String, String> options = preArgs(args);
			if (options.get("-h") != null) {
				log.info(info);
				return;
			}
			log.info("uhjTimer[Run.main] start");
			run(options.get("-t"), options);
			log.info("uhjTimer[Run.main] end!   costs: "
					+ (Calendar.getInstance().getTimeInMillis() - timeStart)
					+ "ms");
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error(info);
		}
//		System.exit(0);
	}

	/**
	 * 执行任务
	 * @param task任务名称
	 * @throws InterruptedException
	 */
	private static void run(String task, Map<String, String> options)
			throws InterruptedException {
		log.info("task:" + task);
		String result = null;
		try {
			log.info(taskInfo.get(task) + " started");
			if ("1".equals(task)) { //根据已放款订单生成借据和还款计划(每10分钟执行一次)以及welab进件   获取语音短信回馈
//				result = UhjClientFactory.getInstance().getHJClient(ZgdClient.class).createDueAfterLoan();
				log.info("timer 1 createDueAfterLoan begin");
				result=WildflyBeanFactory.getZgdClient().createDueAfterLoan();
				log.info("timer 1 createDueAfterLoan end");
				System.exit(0);
			}else if ("2".equals(task)) {//(凌晨3点执行一次) 1、更新逾期的账单费用  2、重新激活掌柜 3、批销邮乐放款更新每天的利息(崔甜甜)
//				result = UhjClientFactory.getInstance().getHJClient(ZgdClient.class).biappUpdateDaily();
				log.info("timer 2 biappUpdateDaily begin");
				result=WildflyBeanFactory.getZgdClient().biappUpdateDaily();
				log.info("timer 2 biappUpdateDaily end");
				System.exit(0);
			}else if ("3".equals(task)) {// 撤销昨天生成单未还款的账单(凌晨1点和6点各执行一次) (已经无用)
//				result = UhjClientFactory.getInstance().getHJClient(ZgdClient.class).cancelRepayBillTimer();
			}else if ("4".equals(task)) {// 更新账期 每天凌晨12点执行 
//				UhjClientFactory.getInstance().getHJClient(AccountTerminalClient.class).updatePeriodTime();
				result = HttpClientUtil.httpPost(TimerConstants.ifadminHost + TimerConstants.get("replyAmountUrl"), true, TimerConstants.get("uhjSmsSecurity"), null);
				//同步白名单机构数据
				try {
					result = HttpClientUtil.httpPost(TimerConstants.ifadminHost + TimerConstants.get("synZgdWhiteUrl"), false, TimerConstants.get("uhjMgtTaskSecurity"), null);
				} catch (Exception e) {
					log.info("执行同步白名单机构数据失败！", e);
				}
				log.info("timer 4 updatePeriodTime begin");
				WildflyBeanFactory.getAccountTerminalClient().updatePeriodTime();
				log.info("timer 4 updatePeriodTime end");
				System.exit(0);
			}else if ("5".equals(task)) {// // 凌晨1点、2点、3点各执行一次 添加统计操作(保存部分)
//				UhjClientFactory.getInstance().getHJClient(AccountTerminalClient.class).saveAccountTerminalByAccountPeriod();
				log.info("timer 5 saveAccountTerminalByAccountPeriod begin");
				WildflyBeanFactory.getAccountTerminalClient().saveAccountTerminalByAccountPeriod();
				log.info("timer 5 saveAccountTerminalByAccountPeriod end");
				System.exit(0);
			}else if ("6".equals(task)) {// // 早上6点执行一次// 添加统计操作(更新部分)
//				UhjClientFactory.getInstance().getHJClient(AccountTerminalClient.class).updateAccountTerminalByAccountPeriod();
				log.info("timer 6 updateAccountTerminalByAccountPeriod begin");
				WildflyBeanFactory.getAccountTerminalClient().updateAccountTerminalByAccountPeriod();
				log.info("timer 6 updateAccountTerminalByAccountPeriod begin");
				System.exit(0);
			}else if ("7".equals(task)){//额度申请定时查询 10分钟一次，批销特殊商品产生订单
//				UhjClientFactory.getInstance().getHJClient(YcNoticeUleClient.class).limitApplyQuery();
//				UhjClientFactory.getInstance().getHJClient(PiXiaoYcLoanClient.class).PxJiJuLoanApply();
				log.info("timer 7 limitApplyQuery begin");
				WildflyBeanFactory.getYcNoticeUleClient().limitApplyQuery();
				log.info("timer 7 limitApplyQuery end");
//				log.info("timer 7 PxJiJuLoanApply begin");
//				WildflyBeanFactory.getPiXiaoYcLoanClient().PxJiJuLoanApply();
//				log.info("timer 7 PxJiJuLoanApply end");
				System.exit(0);
			}else if ("8".equals(task)){//贷款申请定时查询 10分钟一次
//				UhjClientFactory.getInstance().getHJClient(YcNoticeUleClient.class).loanApplyQuery();
				log.info("timer 8 loanApplyQuery begin");
				WildflyBeanFactory.getYcNoticeUleClient().loanApplyQuery();
				log.info("timer 8 loanApplyQuery end");
				System.exit(0);
			}else if ("9".equals(task)){
				//  每天早上四点半执行一次 1、还款明细查询订单单号  
				//和task=15  执行完之后执行其他的如： 2、在同步还款明细前先统计下当日应还当日剩余额度   3、运营日报表统计(统计前一天的运营数据)  
				//4、记录逾期的记录以及变更逾期或结清状态   5、贷款余额 \逾期冻结规则 \催收
//				UhjClientFactory.getInstance().getHJClient(YCZgdRepayDetailClient.class).synchrepayInfo();
				log.info("timer 9 synchrepayInfo begin");
				WildflyBeanFactory.getYCZgdRepayDetailClient().synchrepayInfo();
				log.info("timer 9 synchrepayInfo end");
				System.exit(0);
			}else if ("10".equals(task)){//早上6点执行  1、保证金扣款查询 2、还款计划同步  4、统计每日需要发送的语音短信  
//				UhjClientFactory.getInstance().getHJClient(YCZgdClient.class).bondRepaymentQueryTimer();
				log.info("timer 10 bondRepaymentQueryTimer begin");
				WildflyBeanFactory.getYCZgdClient().bondRepaymentQueryTimer();
				log.info("timer 10 bondRepaymentQueryTimer end");
				System.exit(0);
			}else if ("11".equals(task)){//逾期超10天的还款计划代扣接口 9点、12点、16点、18点各执行一次
//				UhjClientFactory.getInstance().getHJClient(YCZgdClient.class).withHoldOverRepayPlanTimer();
				log.info("timer 11 withHoldOverRepayPlanTimer begin");
				WildflyBeanFactory.getYCZgdClient().withHoldOverRepayPlanTimer();
				log.info("timer 11 withHoldOverRepayPlanTimer end");
				System.exit(0);
			}else if ("12".equals(task)){// 每天早上7点执行一次   （无内容）    数据看板缓存
				result = HttpClientUtil.httpPost(TimerConstants.ifadminHost + TimerConstants.get("synUleOperateDataUrl"), true, TimerConstants.get("uhjSmsSecurity"), null);
//				UhjClientFactory.getInstance().getHJClient(YCZgdDailySheetClient.class).generateDailySheetTimer();
			}else if ("13".equals(task)){// 7点半执行一次  批量统计单日运营数据
				try {
//					UhjClientFactory.getInstance().getHJClient(BangZGClient.class).searchAndStroeSkdaily();
					log.info("timer 13 searchAndStroeSkdaily begin");
					WildflyBeanFactory.getBangZGClient().searchAndStroeSkdaily();
					log.info("timer 13 searchAndStroeSkdaily end");
//					System.exit(0);
				} catch (Exception e) {
					log.error("批量统计单日运营数据异常：", e);
				}
				//每天早上7:30点执行一次，[给昨天21点及之后，到今天7:30点前)，券发放成功或券转让成功的用户推送提示
//				UhjClientFactory.getInstance().getHJClient(TicketTaskClient.class).sendTransTicketTask();
				log.info("timer 13 sendTransTicketTask begin");
				WildflyBeanFactory.getTicketTaskClient().sendTransTicketTask();
				log.info("timer 13 sendTransTicketTask end");
				System.exit(0);
			}else if ("14".equals(task)){// 10点 发送语音短信
//				UhjClientFactory.getInstance().getHJClient(VoiceMessageClient.class).SendVoiceMessage();
				log.info("timer 14 SendVoiceMessage begin");
				WildflyBeanFactory.getVoiceMessageClient().SendVoiceMessage();
				log.info("timer 14 SendVoiceMessage end");
				System.exit(0);
			}else if ("15".equals(task)){// 每天4点执行获取前一天的邮E贷贷款信息 \还款明细查询订单双号和 task=9有关联
//				UhjClientFactory.getInstance().getHJClient(YCZgdClient.class).queryAndStoreUeloanOrders(null);
				log.info("timer 15 queryAndStoreUeloanOrders begin");
				WildflyBeanFactory.getYCZgdClient().queryAndStoreUeloanOrders(null);
				log.info("timer 15 queryAndStoreUeloanOrders end");
				System.exit(0);
			}else if ("16".equals(task)){// 每小时执行一次    1、同步已经开户但是没有VPS用户附属信息的掌柜
//				UhjClientFactory.getInstance().getHJClient(ZgdWhiteClient.class).synZgdUserInfoBatch();
				//早上8点下午3点发送短信（程序内部判断时间点）
				HttpClientUtil.httpPost(TimerConstants.ifadminHost + TimerConstants.get("accountChangeMessageUrl"), true, TimerConstants.get("uhjSmsSecurity"), null);
				log.info("timer 16 synZgdUserInfoBatch begin");
				WildflyBeanFactory.getZgdWhiteClient().synZgdUserInfoBatch();//不需要同步了，邮掌柜的信息可以直接查询邮掌柜的表
				log.info("timer 16 synZgdUserInfoBatch end");
				log.info("timer 16 changeAccountInfoPxFlag begin");
				//批销退款/批销支付金额》=50% 批销不可用
				WildflyBeanFactory.getPiXiaoPayClient().changeAccountInfoPxFlag();
				log.info("timer 16 changeAccountInfoPxFlag end");
				System.exit(0);
			}else if ("17".equals(task)){// 每天早上1点执行一次,邮助手任务入库及推送
//				UhjClientFactory.getInstance().getHJClient(BangZGTaskClient.class).generateYouZhuShouTask();
				log.info("timer 17 generateYouZhuShouTask begin");
				WildflyBeanFactory.getBangZGTaskClient().generateYouZhuShouTask();
				log.info("timer 17 generateYouZhuShouTask end");
				System.exit(0);
			}else if ("18".equals(task)){// 每天早上10点执行一次,创建还款提醒和逾期提醒短信记录，三方分润
				try {
					//每天早上10点执行一次，三方分润
					log.info("timer 18 shareProfitDetail begin");
					WildflyBeanFactory.getYCZgdRepayDetailClient().shareProfitDetail();
				} catch (Exception e) {
					log.error("三方分润的定时异常：", e);
				}
				try {
					//每天早上10点执行一次，对名下有两天后过期的邮利券掌柜批量推送提示
//					UhjClientFactory.getInstance().getHJClient(TicketTaskClient.class).expireTicketTask();
					log.info("timer 18 expireTicketTask begin");
					WildflyBeanFactory.getTicketTaskClient().expireTicketTask();
				} catch (Exception e) {
					log.error("即将过期邮利券定时异常：", e);
				}
				log.info("timer 18 expireTicketTask end");
				result = HttpClientUtil.httpPost(TimerConstants.ifadminHost + TimerConstants.get("createSldSendTask"), true, TimerConstants.get("uhjMgtTaskSecurity"), null);
				try {
					log.info("早上10点开始执行自动还款....");
					result = HttpClientUtil.httpPost(TimerConstants.lendvpsHost + TimerConstants.get("autoRepayUrl"), false, TimerConstants.get("uhjLendvpsSecurity"), null);
					log.info("自动还款执行成功,结果是："+result);
				} catch (Exception e) {
					log.error("自动还款定时器执行失败！", e);
				}
				System.exit(0);
			}else if ("19".equals(task)){// 每10分钟执行一次,商乐贷提醒短信发送任务
				log.info("timer 19 ifadminHost begin");
				result = HttpClientUtil.httpPost(TimerConstants.ifadminHost + TimerConstants.get("generateSldSendTask"), true, TimerConstants.get("uhjMgtTaskSecurity"), null);
				
			}else if ("20".equals(task)){// 商乐贷最新授信金额更新（每天执行一次） 3点
//				result = HttpClientUtil.httpPost(TimerConstants.lendMerchantHost + TimerConstants.get("sldCreditLimitTaskUrl"), true, TimerConstants.get("uhjRootTaskSecurity"), null);
				//商乐贷批销数据同步及资质审批
//				result = HttpClientUtil.httpPost(TimerConstants.lendMerchantHost + TimerConstants.get("synBiInfoAndCheckQualify"), true, TimerConstants.get("uhjRootTaskSecurity"), null);
			}else if ("21".equals(task)){// 商乐贷逾期状态和罚息计算更新（每日执行一次） 3点
//				result = HttpClientUtil.httpPost(TimerConstants.lendMerchantHost + TimerConstants.get("sldPenalInterTaskUrl"), true, TimerConstants.get("uhjRootTaskSecurity"), null);
			}else if ("22".equals(task)){// 9点半执行一次     推送通知消息给3天后应该还款的用户 9点30
//				UhjClientFactory.getInstance().getHJClient(YzgMobileClient.class).sendYzgRepayMessage();
				log.info("timer 22 sendYzgRepayMessage begin");
				WildflyBeanFactory.getYzgMobileClient().sendYzgRepayMessage();
				log.info("timer 22 sendYzgRepayMessage end");
				
				System.exit(0);
			}else if ("23".equals(task)){// 中邮证券 基金收益率定时查询、订单状态同步定时入库  每天一次 2点
// 				result = HttpClientUtil.httpPost(TimerConstants.chinaPostHost + TimerConstants.get("prodIncomeYieldUrl"), true, TimerConstants.get("uhjZhonguSecurity"), null);
// 				result = HttpClientUtil.httpPost(TimerConstants.chinaPostHost + TimerConstants.get("orderQuartzUrl"), true, TimerConstants.get("uhjZhonguSecurity"), null);
			}else if ("24".equals(task)){// 中邮证券 份额定时查询入库、 基金收益明细定时入库   每天一次 2点
//				result = HttpClientUtil.httpPost(TimerConstants.chinaPostHost + TimerConstants.get("prodShareUrl"), true, TimerConstants.get("uhjZhonguSecurity"), null);
//				result = HttpClientUtil.httpPost(TimerConstants.chinaPostHost + TimerConstants.get("earningsQuartzUrl"), true, TimerConstants.get("uhjZhonguSecurity"), null);
			}else if ("25".equals(task)){// 每天早上1点执行一次,同步授信客户，
				result = HttpClientUtil.httpPost(TimerConstants.ifadminHost + TimerConstants.get("syncCreditCustomerUrl"), true, TimerConstants.get("uhjSmsSecurity"), null);
				

			}else if ("26".equals(task)){// 每天早上2点执行一次,同步授信客户短信
				result = HttpClientUtil.httpPost(TimerConstants.ifadminHost + TimerConstants.get("syncCustomerSmsUrl"), true, TimerConstants.get("uhjSmsSecurity"), null);
			}else if ("27".equals(task)){// 每天早上4点执行一次,匹配短信模板
				result = HttpClientUtil.httpPost(TimerConstants.ifadminHost + TimerConstants.get("batchSmsTemplateUrl"), true, TimerConstants.get("uhjSmsSecurity"), null);
			}else if ("28".equals(task)){// 每隔4分钟执行一次,查询报告数据
				result = HttpClientUtil.httpPost(TimerConstants.lendvpsHost + TimerConstants.get("saveJuXinLiResponseDataUrl"), true, TimerConstants.get("uhjLendvpsSecurity"), null);
			}else if ("29".equals(task)){// 每隔5分钟执行一次,循环检例未接收的对外接口数据
				result = HttpClientUtil.httpPost(TimerConstants.ifadminHost + TimerConstants.get("synInterfaceData"), true, TimerConstants.get("uhjMgtTaskSecurity"), null);
			}else if ("30".equals(task)){// 每天下午17点执行一次
//				result =UhjClientFactory.getInstance().getHJClient(PiXiaoYcLoanClient.class).PxLoanApply();
//				log.info("timer 30 PxLoanApply begin");
//				WildflyBeanFactory.getPiXiaoYcLoanClient().PxLoanApply();
//				log.info("timer 30 PxLoanApply end");
//				System.exit(0);
			}else if ("31".equals(task)) {// 每天下午22点执行一次
//				result = UhjClientFactory.getInstance().getHJClient(YcNoticeUleClient.class).noticeTest("19");
				log.info("timer 31 noticeTest begin");
				WildflyBeanFactory.getYcNoticeUleClient().noticeTest("19");
				log.info("timer 31 noticeTest end");
				System.exit(0);
			}else if ("32".equals(task)) {// 每天早上6点30执行一次 浮动利率规则
//				result =  UhjClientFactory.getInstance().getHJClient(IntpriceClient.class).handlIntprice();
				log.info("timer 32 handlIntprice begin");
				WildflyBeanFactory.getIntpriceClient().handlIntprice();
				log.info("timer 32 handlIntprice end");
				System.exit(0);
			}else if ("33".equals(task)) {// 每天晚上23点执行一次
				result = HttpClientUtil.httpPost(TimerConstants.ifadminHost + TimerConstants.get("synPostMemberUrl"), true, TimerConstants.get("uhjMgtTaskSecurity"), null);
			}else{
				log.info("didn't find task :" + task);
			}
			log.info(taskInfo.get(task) + " finished" + result);
		} catch (Exception e) {
			log.error("task " + task + " error!",e);
		}	
	}
	//任务名称描述
	static Map<String, String> taskInfo = new HashMap<String, String>();
	static{
		taskInfo.put("1", "createDueAfterLoan");
		taskInfo.put("2", "overDue update daily");
		taskInfo.put("3", "cancel repayBill");
		taskInfo.put("4", "update Period");
		taskInfo.put("5", "saveAccountTerminal");
		taskInfo.put("6", "updateAccountTerminal");
		taskInfo.put("7", "limitApplyQuery");
		taskInfo.put("8", "loanApplyQuery");
		taskInfo.put("12", "generateDailySheetTimer");
	}

	/**
	 * 命令行参数处理
	 * 
	 * @param args
	 *            参数
	 * @return Map(key/value)
	 */
	private static Map<String, String> preArgs(String[] args) {
		Map<String, String> options = new HashMap<String, String>();
		if (args == null)
			return options;
		StringBuffer buf = new StringBuffer(50);
		for (String string : args) {
			buf.append(string);
		}
		String array = buf.toString();
		array = array.replaceAll("\\-", "#-");
		for (String arg : array.split("#")) {
			if (arg == null || arg.length() < 2)
				continue;
			options.put(arg.substring(0, 2), arg.replaceAll("\\-[a-z]", "")
					.trim());
		}
		return options;
	}
	
	public static String mapToLine(Map<String, Object> map){
		StringBuilder builder = new StringBuilder();
		if(map.size() > 0){
			for(String s : map.keySet()){
				builder.append("[").append(s).append("=").append(map.get(s)).append("];");
			}
		}
		builder.setLength(builder.length() - 1);
		return builder.toString();
	}
}