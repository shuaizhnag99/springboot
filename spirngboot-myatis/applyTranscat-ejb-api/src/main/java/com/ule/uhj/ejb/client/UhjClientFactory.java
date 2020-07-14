//package com.ule.uhj.ejb.client;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//
//import com.ule.jboss.HAJNDIContext;
//import com.ule.jboss.HAJNDIContextManager;
//import com.ule.uhj.ejb.client.ycZgd.YCZgdQueryClient;
//
//public class UhjClientFactory {
//	
//	private static Logger log = Logger.getLogger(UhjClientFactory.class);
//	
//	private static HAJNDIContext context;
//	
//	private static UhjClientFactory instance;
//	
//	static{
//		try {
//			log.info("pre load uhj-uhjEar-partition.properties");
//			context = HAJNDIContextManager.load("/uhj-uhjear-partition.properties");
//			instance = new UhjClientFactory();
//			log.info("load uhj-uhjEar-Partition.properties end");
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		} 
//		
//	}
//	
//	public static UhjClientFactory getInstance(){
//		return instance;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public <T> T getHJClient(Class<T> clazz) throws Exception{
//		return (T) context.lookup(clazz.getSimpleName() + "Jndi");
//	}
//	
//	public static void main(String[] args) throws Exception {
////		Collection<Map> result = UhjClientFactory.getHJClient(ZgdQueryClient.class).querySMSFromCSEByType("sms", 1);
////		System.out.println(result.size());
////		System.out.println("hello");
//		
//		Map<String, Object> m = new HashMap<String, Object>();
//		//paras.userOnlyId 用户id paras.currPage 当前页 paras.pageSize 每页条数
//		m.put("lastRepayDate", "2017-10-09");
//		m.put("lendAmount", "10000");
//		String queryDues = UhjClientFactory.getInstance().getHJClient(YCZgdQueryClient.class).initBiappPlans(m);
//		System.out.println(queryDues);
//	}
//	
//}
