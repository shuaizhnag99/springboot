package com.ule.uld.api;

import com.ule.uhj.ejb.client.WildflyBeanFactory;

import java.util.HashMap;
import java.util.Map;

public class MyResourceTest {
	public static void main(String args[]) throws Exception{
		Map<String,Object> ruleParamer = new HashMap<String, Object>();
		ruleParamer.put("ruleSetId", "rejgssjSet01");
		ruleParamer.put("OrgProvinceName", "");
		ruleParamer.put("storeCity", "");
		ruleParamer.put("householdRegister", 1);
		ruleParamer.put("isSuper", "1");
		ruleParamer.put("storeBusinessType", "便利店超市");
		ruleParamer.put("gsRegistrationStatus", "0");
		ruleParamer.put("isXuQi", "0");
		ruleParamer.put("channelCode", "");
		Map<String,Object> rulesRetMap = WildflyBeanFactory.getZgdClient().zgdRuleSetRun(ruleParamer);
	}
//	private WelabLoanClientImpl clien = new WelabLoanClientImpl();
//	UleParnerResp resp = null;
//	
//	@Test
//	public void ttt(){
////		WelabInfoLookPayClient welabInfoLookPayClient = (WelabInfoLookPayClient)Client.
////				getInstance().lookup("127.0.0.1:1099", "WelabInfoLookPayBeanJndi_JNDI");
//		System.out.println();
//	}
	
//	@Override
//	protected Application configure() {
//		// 服务类所在的包路径
//		// return new RestApplication();
//		return new ResourceConfig().packages("com.ule.uld.api.resource")
//				.register(LoggingFilter.class).register(EncodingFilter.class)
//				.register(ExceptionHandler.class)
//				.register(JacksonFeature.class);
//
//	}
//
//	/**
//	 * Test to see that the message "Got it!" is sent in the response.
//	 */
//	@Test
//	public void testGetIt() {
//		// final String responseMsg = target().path("myresource").request()
//		// .get(String.class);
//		Response response = target().path("myresource").
//		request(MediaType.APPLICATION_JSON).get();
//		System.out.println(response.readEntity(String.class));
//		Assert.assertEquals(response.getStatus(), 200);
//		// assertEquals("Hello, Heroku!", responseMsg);
//	}
//
//	 @Test
//	public void uleLoanResourceTest() {
//		 
//		Form form = new Form();
//		form.param("providerCode", "333");
//		form.param("providerPrdCode", "3331");
//		form.param("ule_customer_id", "888");
//		form.param("status", "1");
//		form.param("sign", "agc");
//		Response response = target().path("welab/openLoan").
//		request(MediaType.APPLICATION_JSON).post(Entity.form(form));
//		System.out.println(response.readEntity(String.class));
//		Assert.assertEquals(response.getStatus(), 200);
//	}
	
	/*@Test
	public void queryLoanInstallmentTest() throws Exception {
		WelabInstallmentReq welabInstallmentReq = new WelabInstallmentReq();
		welabInstallmentReq.setAmount(new BigDecimal(1));
		welabInstallmentReq.setProviderCode("001");
		welabInstallmentReq.setProviderPrdCode("001");
		welabInstallmentReq.setUle_customer_id("001");
		resp = clien.queryLoanInstallment(welabInstallmentReq);
		System.out.println(resp);
	}
	*/
	
	/*@Test
	public void test(){
		String str = UldAPIConstants.WELAB_SERVER_API;
		System.out.println(str);
	}*/
	
}
