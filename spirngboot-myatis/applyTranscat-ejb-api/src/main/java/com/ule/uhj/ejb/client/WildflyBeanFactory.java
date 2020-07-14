package com.ule.uhj.ejb.client;

import com.ule.uhj.ejb.client.acc.AccountClient;
import com.ule.uhj.ejb.client.acc.AccountTerminalClient;
import com.ule.uhj.ejb.client.act.ActClient;
import com.ule.uhj.ejb.client.app.SldAppClient;
import com.ule.uhj.ejb.client.app.ZgdAppClient;
import com.ule.uhj.ejb.client.cs.CsAuthClient;
import com.ule.uhj.ejb.client.cs.PromoterFeedbackClient;
import com.ule.uhj.ejb.client.cs.UserRuleLockCsClient;
import com.ule.uhj.ejb.client.cs.ZgdCSWebClient;
import com.ule.uhj.ejb.client.cs.ZgdCsClient;
import com.ule.uhj.ejb.client.databoard.DataBoardClient;
import com.ule.uhj.ejb.client.gp.GPClient;
import com.ule.uhj.ejb.client.pixiao.BalanceAccClient;
import com.ule.uhj.ejb.client.pixiao.LoanInterfaceClient;
import com.ule.uhj.ejb.client.pixiao.PiXiaoBusinessClient;
import com.ule.uhj.ejb.client.pixiao.PiXiaoCSClient;
import com.ule.uhj.ejb.client.pixiao.PiXiaoPayClient;
import com.ule.uhj.ejb.client.pixiao.PiXiaoYcLoanClient;
import com.ule.uhj.ejb.client.pixiao.RepayInterfaceClient;
import com.ule.uhj.ejb.client.quartz.QuartzClient;
import com.ule.uhj.ejb.client.risk.RiskMsgClient;
import com.ule.uhj.ejb.client.sendMessage.SendMessageTemplateClient;
import com.ule.uhj.ejb.client.sms.CreditCustomerClient;
import com.ule.uhj.ejb.client.tickettask.TicketTaskClient;
import com.ule.uhj.ejb.client.ueloan.YCUeloanClient;
import com.ule.uhj.ejb.client.uleOperate.UleOperateClient;
import com.ule.uhj.ejb.client.ycWelab.ZgdWelabClient;
import com.ule.uhj.ejb.client.ycZgd.*;
import com.ule.uhj.ejb.client.ysg.YsgPayAndBillClient;
import com.ule.uhj.ejb.client.yzq.YzqClient;
import com.ule.uhj.ejb.client.zgd.BlockChainClient;
import com.ule.uhj.ejb.client.zgd.YcNoticeUleClient;
import com.ule.uhj.ejb.client.zgd.ZgdCacheClient;
import com.ule.uhj.ejb.client.zgd.ZgdClient;
import com.ule.uhj.ejb.client.zgd.ZgdQueryClient;
import com.ule.wildfly.WildflyContext;
import com.ule.wildfly.WildflyContextManager;
 
public class WildflyBeanFactory {
	public static final String moduleName="if-vps-loan";
	public static final String appName="applyTransact-ejb-service";
	
    public static AccountClient getAccountClient() throws Exception {
        return initBeanWithClz(AccountClient.class);
    }
    public static AccountTerminalClient getAccountTerminalClient() throws Exception {
        return initBeanWithClz(AccountTerminalClient.class);
    }
    public static ActClient getActClient() throws Exception {
        return initBeanWithClz(ActClient.class);
    }
    public static SldAppClient getSldAppClient() throws Exception {
        return initBeanWithClz(SldAppClient.class);
    }
    public static ZgdAppClient getZgdAppClient() throws Exception {
        return initBeanWithClz(ZgdAppClient.class);
    }
    public static CsAuthClient getCsAuthClient() throws Exception {
        return initBeanWithClz(CsAuthClient.class);
    }
    public static PromoterFeedbackClient getPromoterFeedbackClient() throws Exception {
        return initBeanWithClz(PromoterFeedbackClient.class);
    }
    public static UserRuleLockCsClient getUserRuleLockCsClient() throws Exception {
        return initBeanWithClz(UserRuleLockCsClient.class);
    }    
    public static ZgdCsClient getZgdCsClient() throws Exception {
        return initBeanWithClz(ZgdCsClient.class);
    }
    public static ZgdCSWebClient getZgdCSWebClient() throws Exception {
        return initBeanWithClz(ZgdCSWebClient.class);
    }
    public static BalanceAccClient getBalanceAccClient() throws Exception {
        return initBeanWithClz(BalanceAccClient.class);
    }
    public static LoanInterfaceClient getLoanInterfaceClient() throws Exception {
        return initBeanWithClz(LoanInterfaceClient.class);
    }
    public static PiXiaoBusinessClient getPiXiaoBusinessClient() throws Exception {
        return initBeanWithClz(PiXiaoBusinessClient.class);
    }
    public static PiXiaoCSClient getPiXiaoCSClient() throws Exception {
        return initBeanWithClz(PiXiaoCSClient.class);
    }
    public static PiXiaoPayClient getPiXiaoPayClient() throws Exception {
        return initBeanWithClz(PiXiaoPayClient.class);
    }
    public static PiXiaoYcLoanClient getPiXiaoYcLoanClient() throws Exception {
        return initBeanWithClz(PiXiaoYcLoanClient.class);
    }  
    public static RepayInterfaceClient getRepayInterfaceClient() throws Exception {
        return initBeanWithClz(RepayInterfaceClient.class);
    }
    public static QuartzClient getQuartzClient() throws Exception {
        return initBeanWithClz(QuartzClient.class);
    }
    public static RiskMsgClient getRiskMsgClient() throws Exception {
        return initBeanWithClz(RiskMsgClient.class);
    }
    public static CreditCustomerClient getCreditCustomerClient() throws Exception {
        return initBeanWithClz(CreditCustomerClient.class);
    }
    public static TicketTaskClient getTicketTaskClient() throws Exception {
        return initBeanWithClz(TicketTaskClient.class);
    }
    public static YCUeloanClient getYCUeloanClient() throws Exception {
        return initBeanWithClz(YCUeloanClient.class);
    }
    public static UleOperateClient getUleOperateClient() throws Exception {
        return initBeanWithClz(UleOperateClient.class);
    }
    public static ZgdWelabClient getZgdWelabClient() throws Exception {
        return initBeanWithClz(ZgdWelabClient.class);
    }   
    public static AnnouncementClient getAnnouncementClient() throws Exception {
        return initBeanWithClz(AnnouncementClient.class);
    }
    public static BangZGClient getBangZGClient() throws Exception {
        return initBeanWithClz(BangZGClient.class);
    }
    public static BangZGTaskClient getBangZGTaskClient() throws Exception {
        return initBeanWithClz(BangZGTaskClient.class);
    }
    public static BIDataSyncClient getBIDataSyncClient() throws Exception {
        return initBeanWithClz(BIDataSyncClient.class);
    }
    public static CuishouClient getCuishouClient() throws Exception {
        return initBeanWithClz(CuishouClient.class);
    }
    public static IntpriceClient getIntpriceClient() throws Exception {
        return initBeanWithClz(IntpriceClient.class);
    }
    public static MonitoringInfoClient getMonitoringInfoClient() throws Exception {
        return initBeanWithClz(MonitoringInfoClient.class);
    }
    public static OpcLimitQueryClient getOpcLimitQueryClient() throws Exception {
        return initBeanWithClz(OpcLimitQueryClient.class);
    } 
    public static PostMemberManageClient getPostMemberManageClient() throws Exception {
        return initBeanWithClz(PostMemberManageClient.class);
    }
    public static PXZgdClient getPXZgdClient() throws Exception {
        return initBeanWithClz(PXZgdClient.class);
    }
    public static QianBaoClient getQianBaoClient() throws Exception {
        return initBeanWithClz(QianBaoClient.class);
    }
    public static ReadFileClient getReadFileClient() throws Exception {
        return initBeanWithClz(ReadFileClient.class);
    }
    public static SendMessageClient getSendMessageClient() throws Exception {
        return initBeanWithClz(SendMessageClient.class);
    }
    public static UleHelperFinancerClient getUleHelperFinancerClient() throws Exception {
        return initBeanWithClz(UleHelperFinancerClient.class);
    }
    public static VoiceMessageClient getVoiceMessageClient() throws Exception {
        return initBeanWithClz(VoiceMessageClient.class);
    }
    public static YcLimitRenewClient getYcLimitRenewClient() throws Exception {
        return initBeanWithClz(YcLimitRenewClient.class);
    }   
    public static YCRequestClient getYCRequestClient() throws Exception {
        return initBeanWithClz(YCRequestClient.class);
    }
    public static YCZgdClient getYCZgdClient() throws Exception {
        return initBeanWithClz(YCZgdClient.class);
    }
    public static YCZgdDailySheetClient getYCZgdDailySheetClient() throws Exception {
        return initBeanWithClz(YCZgdDailySheetClient.class);
    }
    public static YCZgdQueryClient getYCZgdQueryClient() throws Exception {
        return initBeanWithClz(YCZgdQueryClient.class);
    }
    public static YCZgdRepayDetailClient getYCZgdRepayDetailClient() throws Exception {
        return initBeanWithClz(YCZgdRepayDetailClient.class);
    }
    public static YzgMobileClient getYzgMobileClient() throws Exception {
        return initBeanWithClz(YzgMobileClient.class);
    }
    public static YzgVpsInfoClient getYzgVpsInfoClient() throws Exception {
        return initBeanWithClz(YzgVpsInfoClient.class);
    }
    public static ZgdWhiteClient getZgdWhiteClient() throws Exception {
        return initBeanWithClz(ZgdWhiteClient.class);
    } 
    public static ZgPicClient getZgPicClient() throws Exception {
        return initBeanWithClz(ZgPicClient.class);
    }
    public static YcNoticeUleClient getYcNoticeUleClient() throws Exception {
        return initBeanWithClz(YcNoticeUleClient.class);
    }
    public static ZgdCacheClient getZgdCacheClient() throws Exception {
        return initBeanWithClz(ZgdCacheClient.class);
    }
    public static ZgdClient getZgdClient() throws Exception {
        return initBeanWithClz(ZgdClient.class);
    }
    public static ZgdQueryClient getZgdQueryClient() throws Exception {
        return initBeanWithClz(ZgdQueryClient.class);
    }
    public static BlockChainClient getBlockChainClient() throws Exception {
        return initBeanWithClz(BlockChainClient.class);
    }             
    public static YsgPayAndBillClient getYsgPayAndBillClient()throws Exception{
    	return initBeanWithClz(YsgPayAndBillClient.class);
    }
    public static GPClient getGPClient() throws Exception {
        return initBeanWithClz(GPClient.class);
    }
    public static YzqClient getYzqClient() throws Exception {
        return initBeanWithClz(YzqClient.class);
    }
    public static ShareProfitClient getShareProfitClient() throws Exception {
        return initBeanWithClz(ShareProfitClient.class);
    }
    
    public static SendMessageTemplateClient getSendMessageTemplateClient() throws Exception {
        return initBeanWithClz(SendMessageTemplateClient.class);
    }

    public static DataBoardClient getDataBoardClient() throws Exception {
        return initBeanWithClz(DataBoardClient.class);
    }
    
    public static  YCZgdInterfaceClient getYCZgdInterfaceClient()throws Exception{
    	return initBeanWithClz(YCZgdInterfaceClient.class);
    }
    /**
     * 仅使用接口类即可推算完整的 JNDI 路径，但是需要接口添加 @BeanName 注解，并指定实际的 BeanName 实现类名称，本质上
     * 与下面的没有区别
     */
    public static <T> T initBeanWithClz(Class<?> clz) throws Exception {
        if (clz == null) {
            throw new NullPointerException("clz can not be null!!!");
        }
        WildflyContext context = WildflyContextManager.load(moduleName, appName);
        return (T) context.lookup(clz);
    }
}