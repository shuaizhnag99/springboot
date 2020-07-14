package com.ule.uhj.sldProxy.sldBiz;

import java.lang.reflect.Field;
import java.util.*;

import com.alibaba.druid.support.json.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ule.tools.creditService.bean.BrTerBean;
import com.ule.tools.creditService.client.CreditServiceTools;
import com.ule.uhj.provider.yitu.service.BaiRongService;
import com.ule.uhj.sld.biz.dto.Request;
import com.ule.uhj.sld.biz.dto.Response;
import com.ule.uhj.sld.biz.service.impl.DefaultSldService;
import com.ule.uhj.sld.constant.BRTransCodeEnum;
import com.ule.uhj.sld.model.SldOperateLog;
import com.ule.uhj.sld.util.Check;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sldProxy.dao.SldOperateLogMapper;
import com.ule.uhj.sldProxy.service.IApplyBrService;
import com.ule.uhj.sldProxy.service.InterfaceAccessInfoService;
import com.ule.uhj.sldProxy.util.BaiRongBlackRule;
import com.ule.uhj.sldProxy.util.BaiRongConstant;
import com.ule.uhj.sldProxy.util.CodeMsg;
import com.ule.uhj.sldProxy.util.Constants;
import com.ule.uhj.sldProxy.util.CreditException;
import com.ule.uhj.sldProxy.util.PropertiesHelper;

/**
 * 百融征信接口-内部对接服务
 */
@Component(value="BRSldService")
public class BRSldService  extends DefaultSldService {
    @Autowired
    SldOperateLogMapper sldOperateLogMapper;
    @Autowired
    IApplyBrService applyBrService;
    @Autowired
    BetaInterHelperService betaInterHelperService;
    @Autowired
    private InterfaceAccessInfoService interfaceAccessInfoService;
    //搬运自崔甜甜，具体释义不明
    private static final String type="type";
    private static final String rule="rule";
    /***
     * 百融征信发生异常，查询成功
     */
    public static  final String SUCCESS = "000000";

    /***
     * 用户姓名未传入时的错误代码
     */
    public  static final String NOT_NAME = "000001";

    /***
     * 身份证未传入时的错误代码
     */
    public  static final String NOT_ID = "000002";

    /***
     * 手机号码传入时的错误代码
     */
    public  static final String NOT_CELL = "000003";

    /***
     * 没有获取到查询所需的签证
     */
    public  static final String NOT_SIGN = "000004";

    /***
     * 百融征信发生异常，查询失败
     */
    public static  final String QUERY_FAILED = "000005";

    /***
     * 银行卡传入时的错误代码
     */
    public static  final String NOT_CARD = "000006";
    /***
     * 接口返回无数据时的错误代码
     */
    public static  final String NO_RESULT = "000007";

    /***
     * 解析百融征信返回数据异常
     */
    public static final String DE_DATA_ERROR = "900001";

    @SuppressWarnings("unchecked")
    @Override
    public Response doService(Request request){
        log.info("百融征信：开始，request="+request.toString());
        //查询签证
//        String sign = "";
        //查询结果
        String BRresult ;
        //返回结果
        Response response = new Response();
        //传入参数
        Map<String,Object> dataMap = request.getDataMap();

        //beta数据走挡板
        try{
//          String betaResponse =betaInterHelperService.getBetaInterfaceResponse(request.getTransCode());
            String betaResponse =betaInterHelperService.getBlockTextByCondition(request.getUserOnlyId(),request.getTransCode(),null,Convert.toStr(request.getDataMap().get("id")));
            if(!Check.isBlank(betaResponse)){
                log.info("doService transCode="+request.getTransCode()+";betaResponse="+betaResponse);
                response.setResponseCode(SUCCESS);
                response.setMessage("百融征信：查询成功!");
                updateTransBrInfo(request);
                response.setResponseMap((Map)JSONObject.parse(betaResponse));
                return response;
            }
        }catch(Exception ex){
            log.error("doService block error...",ex);
        }

       /* //通过百融接口查询企业工商信息
        if(BRTransCodeEnum.BR_COMP_QUERY.getTransCode().equals(request.getTransCode())){
        	//公司名称和营业执照号码至少有一个不为空
        	String bizWorkfor = Convert.toStr(dataMap.get("bizWorkfor"));
        	String bizRegnum = Convert.toStr(dataMap.get("bizRegnum"));
        	if(StringUtils.isBlank(bizWorkfor)&&StringUtils.isBlank(bizRegnum)){
        		log.info("百融征信：公司名称和营业执照号码全为空！无法查询！");
                response.setResponseCode(NOT_ID);
                response.setMessage("百融征信：公司名称和营业执照号码全为空！无法查询！");
                return response;
        	}
            try{
            	log.info("doService ZX_SIGN_KEY  "+PropertiesHelper.getDfs("ZX_SIGN_KEY")+" bizWorkfor  "+bizWorkfor+"  bizRegnum  "+bizRegnum);
                BRresult = CreditServiceTools.brBizInfo(PropertiesHelper.getDfs("ZX_APP_KEY"), PropertiesHelper.getDfs("ZX_SIGN_KEY"),"SOA_sldProxy",bizWorkfor,bizRegnum);
                log.info("百融征信：企业基本信息征信查询返回结果="+BRresult);
            }catch (Exception e){
                log.error("百融征信：发起查询失败！", e);
                response.setResponseCode(QUERY_FAILED);
                response.setMessage("百融征信：企业基本信息查询失败！调用百融API时发生异常："+e);
                return response;
            }

        }else*/ if(BRTransCodeEnum.BR_CRIME_QUERY.getTransCode().equals(request.getTransCode())){//brCrime个人不良信息查询
            String cid = Convert.toStr(dataMap.get("id"));
            String cell = Convert.toStr(dataMap.get("cell"));
            String name = Convert.toStr(dataMap.get("name"));
            //身份证未传
            if(StringUtils.isBlank(cid)){
                log.info("百融征信：身份证未传入！无法查询！");
                response.setResponseCode(NOT_ID);
                response.setMessage("百融征信：身份证未传入！无法查询！");
                return response;
            }
            //用户姓名未传
            if(StringUtils.isBlank(name)){
                log.info("百融征信：用户姓名未传入！无法查询！");
                response.setResponseCode(NOT_NAME);
                response.setMessage("百融征信：用户姓名未传入！无法查询！");
                return response;
            }
            //手机号码未传
            if(StringUtils.isBlank(cell)){
                log.info("百融征信：手机号码未传入！无法查询！");
                response.setResponseCode(NOT_CELL);
                response.setMessage("百融征信：手机号码未传入！无法查询！");
                return response;
            }
            try{
            	/*log.info("doService ZX_SIGN_KEY  "+PropertiesHelper.getDfs("ZX_SIGN_KEY")+" cid  "+cid+" cell "+cell+"  name  "+name);
            	BaiRongService br = new BaiRongService();
            	Map<String, Object> param =new HashMap<String, Object>();
        		param.put("idCard", cid);
        		param.put("name", name);
        		param.put("phone", cell);
        		BRresult =br.badInfo(param); // 第三方接口调整by wuhaitao
*/                BRresult = CreditServiceTools.brBadInfo(cid,cell,name);
                log.info("百融征信：个人不良信息征信查询返回结果="+BRresult);
            }catch (Exception e){
                log.error("百融征信：发起查询失败！", e);
                response.setResponseCode(QUERY_FAILED);
                response.setMessage("百融征信：个人不良信息查询失败！调用百融API时发生异常："+e);
                return response;
            }
        }/*else if(BRTransCodeEnum.BR_RESIDENCE_QUERY.getTransCode().equals(request.getTransCode())){//brResidence户籍信息查询
        	String cid = Convert.toStr(dataMap.get("cid"));
        	String cell = Convert.toStr(dataMap.get("cell"));
        	String name = Convert.toStr(dataMap.get("name"));
        	//身份证未传
            if(StringUtils.isBlank(cid)){
                log.info("百融征信：身份证未传入！无法查询！");
                response.setResponseCode(NOT_ID);
                response.setMessage("百融征信：身份证未传入！无法查询！");
                return response;
            }
            //用户姓名未传
            if(StringUtils.isBlank(name)){
                log.info("百融征信：用户姓名未传入！无法查询！");
                response.setResponseCode(NOT_NAME);
                response.setMessage("百融征信：用户姓名未传入！无法查询！");
                return response;
            }
            //手机号码未传
            if(StringUtils.isBlank(cell)){
                log.info("百融征信：手机号码未传入！无法查询！");
                response.setResponseCode(NOT_CELL);
                response.setMessage("百融征信：手机号码未传入！无法查询！");
                return response;
            }
            try{
            	log.info("doService ZX_SIGN_KEY  "+PropertiesHelper.getDfs("ZX_SIGN_KEY")+" cid  "+cid+" cell "+cell+"  name  "+name);
                BRresult = CreditServiceTools.brResidence(PropertiesHelper.getDfs("ZX_APP_KEY"), PropertiesHelper.getDfs("ZX_SIGN_KEY"),"SOA_sldProxy",cid,cell,name);
                log.info("百融征信：户籍信息征信查询返回结果="+BRresult);
            }catch (Exception e){
                log.error("百融征信：发起查询失败！", e);
                response.setResponseCode(QUERY_FAILED);
                response.setMessage("百融征信：户籍信息查询失败！调用百融API时发生异常："+e);
                return response;
            }
        }*/
        /*else if(BRTransCodeEnum.BR_PER_INVEST.getTransCode().equals(request.getTransCode())){//brPerInvest个人对外投资信息查询
        	String cid = Convert.toStr(dataMap.get("id"));
        	String cell = Convert.toStr(dataMap.get("cell"));
        	String name = Convert.toStr(dataMap.get("name"));
        	//身份证未传
            if(StringUtils.isBlank(cid)){
                log.info("百融征信：身份证未传入！无法查询！");
                response.setResponseCode(NOT_ID);
                response.setMessage("百融征信：身份证未传入！无法查询！");
                return response;
            }
            //用户姓名未传
            if(StringUtils.isBlank(name)){
                log.info("百融征信：用户姓名未传入！无法查询！");
                response.setResponseCode(NOT_NAME);
                response.setMessage("百融征信：用户姓名未传入！无法查询！");
                return response;
            }
            //手机号码未传
            if(StringUtils.isBlank(cell)){
                log.info("百融征信：手机号码未传入！无法查询！");
                response.setResponseCode(NOT_CELL);
                response.setMessage("百融征信：手机号码未传入！无法查询！");
                return response;
            }
            try{
            	log.info("doService ZX_SIGN_KEY  "+PropertiesHelper.getDfs("ZX_SIGN_KEY")+" cid  "+cid+" cell "+cell+"  name  "+name);
                BRresult = CreditServiceTools.brPerInvest(PropertiesHelper.getDfs("ZX_APP_KEY"), PropertiesHelper.getDfs("ZX_SIGN_KEY"),"SOA_sldProxy",cid,cell,name);
                log.info("百融征信：个人对外投资信息征信查询返回结果="+BRresult);
            }catch (Exception e){
                log.error("百融征信：发起查询失败！", e);
                response.setResponseCode(QUERY_FAILED);
                response.setMessage("百融征信：个人对外投资信息查询失败！调用百融API时发生异常："+e);
                return response;
            }
        }*/else if(BRTransCodeEnum.BR_BANK_FOUR_PRO.getTransCode().equals(request.getTransCode())){
            String cid = Convert.toStr(dataMap.get("id"));
            String cell = Convert.toStr(dataMap.get("cell"));
            String name = Convert.toStr(dataMap.get("name"));
            String bankId = Convert.toStr(dataMap.get("bankId"));
            //身份证未传
            if(StringUtils.isBlank(cid)){
                log.info("百融征信：身份证未传入！无法查询！");
                response.setResponseCode(NOT_ID);
                response.setMessage("百融征信：身份证未传入！无法查询！");
                return response;
            }
            //用户姓名未传
            if(StringUtils.isBlank(name)){
                log.info("百融征信：用户姓名未传入！无法查询！");
                response.setResponseCode(NOT_NAME);
                response.setMessage("百融征信：用户姓名未传入！无法查询！");
                return response;
            }
            //手机号码未传
            if(StringUtils.isBlank(cell)){
                log.info("百融征信：手机号码未传入！无法查询！");
                response.setResponseCode(NOT_CELL);
                response.setMessage("百融征信：手机号码未传入！无法查询！");
                return response;
            }
            //手机号码未传
            if(StringUtils.isBlank(bankId)){
                log.info("百融征信：银行卡未传入！无法查询！");
                response.setResponseCode(NOT_CARD);
                response.setMessage("百融征信：手机号码未传入！无法查询！");
                return response;
            }
            /*Long start = System.currentTimeMillis();
    		String applicationId = "";
    		try{
    			applicationId = UUID.randomUUID().toString().replaceAll("-", "");
    		}catch(Exception e) {
    			log.error("UUID Exception:", e);
    			applicationId = start + "";
    		}
    		JSONObject reqData = new JSONObject();
    		JSONObject jso = new JSONObject();
            try{
            	reqData.put("id", cid);
    			reqData.put("cell", cell);
    			reqData.put("name", name);
    			reqData.put("bank_id", bankId);
    			reqData.put("meal", Constants.BANK_FOUR);
    			jso.put("apiName", Constants.FRAUD_API_NAME);
    			jso.put("reqData", reqData);
    			BRresult = applyBrService.requestMethod(applicationId, jso);
                log.info("百融征信：银行卡四要素查询返回结果="+BRresult);*/
            try{
                BRresult=CreditServiceTools.brBankFourPro(cid,cell,name,bankId);
                log.info("百融征信：银行卡四要素查询返回结果="+BRresult);
            }catch (Exception e){
                log.error("百融征信：发起查询失败！", e);
                response.setResponseCode(QUERY_FAILED);
                response.setMessage("百融征信：银行卡四要素查询返回失败！调用百融API时发生异常："+e);
                return response;
            }
        }else{
            //通过百融接口查询信用记录
            //百融数据传输模型
            BrTerBean brTerBean = new BrTerBean();
            map2brterbean(brTerBean,dataMap);
            //身份证未传
            if(StringUtils.isBlank(brTerBean.getId())){
                log.info("百融征信：身份证未传入！无法查询！");
                response.setResponseCode(NOT_ID);
                response.setMessage("百融征信：身份证未传入！无法查询！");
                return response;
            }
            //用户姓名未传
            if(StringUtils.isBlank(brTerBean.getName())){
                log.info("百融征信：用户姓名未传入！无法查询！");
                response.setResponseCode(NOT_NAME);
                response.setMessage("百融征信：用户姓名未传入！无法查询！");
                return response;
            }
            //手机号码未传
            if(brTerBean.getCell().size()<=0){
                log.info("百融征信：手机号码未传入！无法查询！");
                response.setResponseCode(NOT_CELL);
                response.setMessage("百融征信：手机号码未传入！无法查询！");
                return response;
            }

            log.info("百融征信：本次查询产品为" + brTerBean.getMeal());

            try{
            	log.info("百融征信：调用基础服务部参数 id+" + brTerBean.getId()+"name"+brTerBean.getName()+"meal"+brTerBean.getMeal().toString()+"cell"+brTerBean.getCell().toString());
                BRresult = CreditServiceTools.brCredit(brTerBean);
                log.info("百融征信：征信验证查询返回结果="+BRresult);
            }catch (Exception e){
                log.error("百融征信：发起查询失败！", e);
                response.setResponseCode(QUERY_FAILED);
                response.setMessage("百融征信：查询失败！调用百融API时发生异常："+e);
                return response;
            }
        }
        response.setResponseCode(SUCCESS);
        response.setMessage("百融征信：查询成功!");
        updateTransBrInfo(request);
        try{
//        	BRresult = "{\"code\":\"9000\",\"msg\":\"请求频率过快，请稍后再试！\",\"data\":\"\"}";
            log.info("BRSldService 百荣查询接口 entityStr"+BRresult.toString());
            JSONObject jsonObject = JSONObject.parseObject(BRresult);
            if(StringUtils.isNotBlank(jsonObject.getString("data"))){
                String entityStr = jsonObject.get("data").toString();
                log.info("BRSldService entityStr"+entityStr);
                response.setResponseMap((Map)JSONObject.parse(entityStr));
            }

        }catch (Exception e){
            log.error("百融征信：解析百融征信数据时异常！", e);
            response.setResponseCode(DE_DATA_ERROR);
            response.setMessage("百融征信：解析百融征信数据时异常！"+e);
        }

        //保存接口记录
        interfaceAccessInfoService.saveInterfaceRecord(JSONObject.toJSONString(request), JSONObject.toJSONString(response),request.getUserOnlyId(),request.getTransCode(),"300");
        return response;
    }

    /***
     * 传入参数复制到百融征信指定的传输模型
     * @param brTerBean
     * @param dataMap
     */
    private void map2brterbean(BrTerBean brTerBean,Map<String,Object> dataMap){
        log.info("百融征信：反射阶段，dataMap="+dataMap);
        Class BrTerBeanClass = brTerBean.getClass();
        Field[] fields = BrTerBeanClass.getDeclaredFields();
        for(String key : dataMap.keySet()){
            for(Field field : fields){
                if(key.equals(field.getName())){
                    field.setAccessible(true);
                    try{
                        /***
                         * 2019-05-22
                         * 判断是否是List类型，处理上线任务要求，临时补丁，日后优化
                         * By.ZhengXiu
                         */
                        if(List.class.isAssignableFrom(field.getType())){
                            Object origin_value = dataMap.get(key);
                            if(origin_value instanceof List){
                                field.set(brTerBean,dataMap.get(key));
                                log.info("百融征信：反射阶段" + field.getName() + "字段赋值成功！传入参数：" + key + "，传入值：" + dataMap.get(key));
                            } else if(origin_value instanceof String) {
                                String[] origin_value_arr = Convert.toStr(((String) origin_value).trim(), "").split(",");
                                field.set(brTerBean,Arrays.asList(origin_value_arr));
                                log.info("百融征信：反射阶段" + field.getName() + "字段赋值成功！传入参数：" + key + "，传入值：" + dataMap.get(key));

                            }
                            continue;
                        }
                        field.set(brTerBean,dataMap.get(key));
                        log.info("百融征信：反射阶段" + field.getName() + "字段赋值成功！传入参数：" + key + "，传入值：" + dataMap.get(key));
                    }catch (Exception e){
                        log.error("百融征信：反射阶段"+field.getName()+"字段赋值失败！传入参数："+key+"，传入值："+dataMap.get(key), e);
                    }
                    break;
                }
            }
        }
    }

/*    public static void main(String args[]){
        BRSldService brSldService = new BRSldService();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("cell", "51626262626");
        BrTerBean brTerBean = new BrTerBean();
        brSldService.map2brterbean(brTerBean, dataMap);
    }*/

    private Map<String,Object> getZxInfo(Map<String,Object> info,String certNo,String mobile){
        Map<String,Object>  reMap=new HashMap<String,Object>();
        try {
            if("00".equals(Convert.toStr(info.get("code")))){
                reMap.put("zhiXing", info.get(BaiRongConstant.sl_id_court_executed)==null?0:1);
                reMap.put("shiXin", info.get(BaiRongConstant.sl_id_court_bad)==null?0:1);
                //解析是否银行在黑名单
                List<Map<String,Object>> bankBlackList=isInBankBlackList(info,certNo,mobile);
                reMap.put("bankBlackList", bankBlackList);
                //解析是否在非银行黑名单
                List<Map<String,Object>> noBankBlackList=isInNoBankBlackList(info,certNo,mobile);
                reMap.put("noBankBlackList", noBankBlackList);
                //解析外部机构申贷数量
                List<Map<String,Object>> bankManyTimeList=isorgnumManyTimes(info);
                reMap.put("manyTimeList", bankManyTimeList);
                //解析三个月申贷记录次数
                List<Map<String,Object>> manyTimeList= isManyTimes(info);
                reMap.put("bankManyTimeList", manyTimeList);
                log.info("BankBlackList"+bankBlackList+"noBankBlackList"+noBankBlackList);
            }

        } catch (Exception e) {
            log.error("百融接口 error", e);
        }
        reMap.put("code",Convert.toStr(info.get("code")));
        return reMap;
    }

    /**
     * 解析是否在银行黑名单中-搬运自崔甜甜
     * @param info
     * @param certNo
     * @param mobile
     * @return
     */
    private List<Map<String,Object>> isInBankBlackList(Map<String,Object> info,String certNo,String mobile){
        List<Map<String,Object>> bankBlakList =new ArrayList<Map<String,Object>>();
        //银行不良
        blackList(BaiRongBlackRule.sl_id_bank_bad, BaiRongBlackRule.sl_cell_bank_bad,bankBlakList, info,certNo, mobile);
        //银行欺诈
        blackList(BaiRongBlackRule.sl_id_bank_fraud, BaiRongBlackRule.sl_cell_bank_fraud,bankBlakList, info,certNo, mobile);
        //银行短时逾期或拒绝
        blackList(BaiRongBlackRule.sl_id_bank_overdue, BaiRongBlackRule.sl_cell_bank_overdue,bankBlakList, info,certNo, mobile);
        //银行失联
        blackList(BaiRongBlackRule.sl_id_bank_lost, BaiRongBlackRule.sl_cell_bank_lost,bankBlakList, info,certNo, mobile);
        return bankBlakList;
    }

    /**
     * 解析是否在非银行黑名单中-搬运自崔甜甜
     * @param info
     * @param certNo
     * @param mobile
     * @return
     */
    private List<Map<String,Object>> isInNoBankBlackList(Map<String,Object> info,String certNo,String mobile){
        List<Map<String,Object>> noBankBlakList =new ArrayList<Map<String,Object>>();
        //电信欠费
        blackList(BaiRongBlackRule.sl_id_phone_overdue, BaiRongBlackRule.sl_cell_phone_overdue,noBankBlakList, info,certNo, mobile);
        //P2P不良
        blackList(BaiRongBlackRule.sl_id_nbank_p2p_bad, BaiRongBlackRule.sl_cell_nbank_p2p_bad,noBankBlakList, info,certNo, mobile);
        //P2P拒绝
        blackList(BaiRongBlackRule.sl_id_nbank_p2p_refuse, BaiRongBlackRule.sl_cell_nbank_p2p_refuse,noBankBlakList, info,certNo, mobile);
        //P2P短时逾期
        blackList(BaiRongBlackRule.sl_id_nbank_p2p_overdue, BaiRongBlackRule.sl_cell_nbank_p2p_overdue,noBankBlakList, info,certNo, mobile);
        //小贷不良
        blackList(BaiRongBlackRule.sl_id_nbank_mc_bad, BaiRongBlackRule.sl_cell_nbank_mc_bad,noBankBlakList, info,certNo, mobile);
        //小贷拒绝
        blackList(BaiRongBlackRule.sl_id_nbank_mc_refuse, BaiRongBlackRule.sl_cell_nbank_mc_refuse,noBankBlakList, info,certNo, mobile);
        //小贷短时逾期
        blackList(BaiRongBlackRule.sl_id_nbank_mc_overdue, BaiRongBlackRule.sl_cell_nbank_mc_overdue,noBankBlakList, info,certNo, mobile);
        //消费类分期不良
        blackList(BaiRongBlackRule.sl_id_nbank_cf_bad, BaiRongBlackRule.sl_cell_nbank_cf_bad,noBankBlakList, info,certNo, mobile);
        //费类分期短时逾期
        blackList(BaiRongBlackRule.sl_id_nbank_cf_overdue, BaiRongBlackRule.sl_cell_nbank_cf_overdue,noBankBlakList, info,certNo, mobile);
        //非银其他不良
        blackList(BaiRongBlackRule.sl_id_nbank_other_bad, BaiRongBlackRule.sl_cell_nbank_other_bad,noBankBlakList, info,certNo, mobile);
        //非银其他拒绝
        blackList(BaiRongBlackRule.sl_id_nbank_other_refuse, BaiRongBlackRule.sl_cell_nbank_other_refuse,noBankBlakList, info,certNo, mobile);
        //非银其他短时逾期
        blackList(BaiRongBlackRule.sl_id_nbank_other_overdue, BaiRongBlackRule.sl_cell_nbank_other_overdue,noBankBlakList, info,certNo, mobile);
        //现金类分期不良
        blackList(BaiRongBlackRule.sl_id_nbank_ca_bad, BaiRongBlackRule.sl_cell_nbank_ca_bad,noBankBlakList, info,certNo, mobile);
        //现金类分期拒绝
        blackList(BaiRongBlackRule.sl_id_nbank_ca_refuse, BaiRongBlackRule.sl_cell_nbank_ca_refuse,noBankBlakList, info,certNo, mobile);
        //现金类分期短时逾期
        blackList(BaiRongBlackRule.sl_id_nbank_ca_overdue, BaiRongBlackRule.sl_cell_nbank_ca_overdue,noBankBlakList, info,certNo, mobile);
        //代偿类分期不良
        blackList(BaiRongBlackRule.sl_id_nbank_com_bad, BaiRongBlackRule.sl_cell_nbank_com_bad,noBankBlakList, info,certNo, mobile);
        //代偿类分期拒绝
        blackList(BaiRongBlackRule.sl_id_nbank_com_refuse, BaiRongBlackRule.sl_cell_nbank_com_refuse,noBankBlakList, info,certNo, mobile);
        //代偿类分期短时逾期
        blackList(BaiRongBlackRule.sl_id_nbank_com_overdue, BaiRongBlackRule.sl_cell_nbank_com_overdue,noBankBlakList, info,certNo, mobile);
        //P2P失联
        blackList(BaiRongBlackRule.sl_id_nbank_p2p_lost, BaiRongBlackRule.sl_cell_nbank_p2p_lost,noBankBlakList, info,certNo, mobile);
        //小贷失联
        blackList(BaiRongBlackRule.sl_id_nbank_mc_lost, BaiRongBlackRule.sl_cell_nbank_mc_lost,noBankBlakList, info,certNo, mobile);
        //消费类分期失联
        blackList(BaiRongBlackRule.sl_id_nbank_cf_lost, BaiRongBlackRule.sl_cell_nbank_cf_lost,noBankBlakList, info,certNo, mobile);
        //现金类分期失联
        blackList(BaiRongBlackRule.sl_id_nbank_ca_lost, BaiRongBlackRule.sl_cell_nbank_ca_lost,noBankBlakList, info,certNo, mobile);
        //代偿类分期失联
        blackList(BaiRongBlackRule.sl_id_nbank_com_lost, BaiRongBlackRule.sl_cell_nbank_com_lost,noBankBlakList, info,certNo, mobile);
        //非银其他失联
        blackList(BaiRongBlackRule.sl_id_nbank_other_lost, BaiRongBlackRule.sl_cell_nbank_other_lost,noBankBlakList, info,certNo, mobile);
        return noBankBlakList;
    }

    /***
     * 解析外部机构申请贷款次数-搬运自崔甜甜
     * @param info
     * @return
     */
    private List<Map<String,Object>> isorgnumManyTimes(Map<String,Object> info){
        List<Map<String,Object>> resultList =new ArrayList<Map<String,Object>>();
        Map<String,Object> idMap=new HashMap<String, Object>();
        Map<String,Object> cellMap=new HashMap<String, Object>();
        //通过身份证号查询最近7天申请机构数量
        queryDetail(info, idMap,BaiRongConstant.id,BaiRongConstant.d7,BaiRongConstant.orgnum);
        //通过身份证号查询最近15天申请机构数量
        queryDetail(info, idMap,BaiRongConstant.id,BaiRongConstant.d15,BaiRongConstant.orgnum);
        //通过身份证号查询最近1个月申请机构数量
        queryDetail(info, idMap,BaiRongConstant.id,BaiRongConstant.m1,BaiRongConstant.orgnum);
        //通过身份证号查询最近3个月申请机构数量
        queryDetail(info, idMap,BaiRongConstant.id,BaiRongConstant.m3,BaiRongConstant.orgnum);
        if(idMap.size()>0){
            fullFillMap(idMap);
            resultList.add(idMap);
        }
        //通过手机号号查询最近7天月申请机构数量
        queryDetail(info, cellMap,BaiRongConstant.cell,BaiRongConstant.d7,BaiRongConstant.orgnum);
        //通过手机号号查询最近15天月申请机构数量
        queryDetail(info, cellMap,BaiRongConstant.cell,BaiRongConstant.d15,BaiRongConstant.orgnum);
        //通过手机号号查询最近1个月申请机构数量
        queryDetail(info, cellMap,BaiRongConstant.cell,BaiRongConstant.m1,BaiRongConstant.orgnum);
        //通过手机号号查询最近3个月申请机构数量
        queryDetail(info, cellMap,BaiRongConstant.cell,BaiRongConstant.m3,BaiRongConstant.orgnum);
        if(cellMap.size()>0){
            fullFillMap(cellMap);
            resultList.add(cellMap);
        }
//		 }
        return resultList;
    }

    /***
     * 解析近三月内贷款次数-搬运自崔甜甜
     * @param info
     * @return
     */
    private List<Map<String,Object>> isManyTimes(Map<String,Object> info){
        List<Map<String,Object>> resultList =new ArrayList<Map<String,Object>>();
        Map<String,Object> idMap=new HashMap<String, Object>();
        Map<String,Object> cellMap=new HashMap<String, Object>();
        //通过身份证号查询最近7天申请次数
        queryDetail(info, idMap,BaiRongConstant.id,BaiRongConstant.d7,BaiRongConstant.allnum);
        //通过身份证号查询最近15天申请次数
        queryDetail(info, idMap,BaiRongConstant.id,BaiRongConstant.d15,BaiRongConstant.allnum);
        //通过身份证号查询最近1个月申请次数
        queryDetail(info, idMap,BaiRongConstant.id,BaiRongConstant.m1,BaiRongConstant.allnum);
        //通过身份证号查询最近3个月申请次数
        queryDetail(info, idMap,BaiRongConstant.id,BaiRongConstant.m3,BaiRongConstant.allnum);
        if(idMap.size()>0){
            fullFillMap(idMap);
            resultList.add(idMap);
        }
        //通过手机号号查询最近7天月申请次数
        queryDetail(info, cellMap,BaiRongConstant.cell,BaiRongConstant.d7,BaiRongConstant.allnum);
        //通过手机号号查询最近15天月申请次数
        queryDetail(info, cellMap,BaiRongConstant.cell,BaiRongConstant.d15,BaiRongConstant.allnum);
        //通过手机号号查询最近1个月申请次数
        queryDetail(info, cellMap,BaiRongConstant.cell,BaiRongConstant.m1,BaiRongConstant.allnum);
        //通过手机号号查询最近3个月申请次数
        queryDetail(info, cellMap,BaiRongConstant.cell,BaiRongConstant.m3,BaiRongConstant.allnum);
        if(cellMap.size()>0){
            fullFillMap(cellMap);
            resultList.add(cellMap);
        }
//		 }
        return resultList;
    }

    /***
     * 搬运自崔甜甜，具体释义不明
     * 猜测为：通过两种不同的规则，将指定数据加入黑名单队列
     * @param idName
     * @param mobileName
     * @param resultist
     * @param info
     * @param certNo
     * @param mobile
     */
    private void blackList(BaiRongBlackRule idName,BaiRongBlackRule mobileName,List<Map<String,Object>> resultist,Map<String,Object> info,String certNo,String mobile){
        addList(resultist, info, idName, certNo, mobile);
        addList(resultist, info, mobileName, certNo, mobile);
    }

    /***
     * 搬运自崔甜甜，具体释义不明。
     * 猜测为：一旦发现传入数据中，特定项触发传入规则，则加入队列
     * @param resultist 规则队列
     * @param info
     * @param name
     * @param certNo
     * @param mobile
     */
    private void  addList(List<Map<String,Object>> resultist,Map<String,Object> info,BaiRongBlackRule name,String certNo,String mobile){
        if(info.get(name.getCode())!=null){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put(type, getCertNoOrMobile(name.getType(),certNo,mobile));
            map.put(rule, name.getMsg());
            resultist.add(map);
        }
    }

    /***
     * 搬运自崔甜甜
     * 根据传入Type值返回身份证号或手机号
     * @param type
     * @param certNo
     * @param mobile
     * @return
     */
    private  String getCertNoOrMobile(String type,String certNo,String mobile){
        if("0".equals(type)){
            return certNo;
        }else{
            return mobile;
        }
    }

    /***
     * 搬运自崔甜甜，具体释义不明
     * 猜测为：根据触发规则，统计外部贷款次数
     * @param info
     * @param idMap
     * @param ty
     * @param str
     * @param type1
     */
    private void queryDetail(Map<String,Object> info,Map<String,Object> idMap,String ty,String str,String  type1){
        StringBuffer   buffer=new StringBuffer();
//		 //银行多次申请
        putBuffer(info, buffer, BaiRongConstant.als+str+ty+BaiRongConstant.bank+type1, "银行");
        //p2p多次申请
        putBuffer(info, buffer, BaiRongConstant.als+str+ty+BaiRongConstant.p2p+type1, "p2p");
        //小贷多次申请
        putBuffer(info, buffer, BaiRongConstant.als+str+ty+BaiRongConstant.mc+type1, "小贷");
        //现金类多次申请
        putBuffer(info, buffer, BaiRongConstant.als+str+ty+BaiRongConstant.ca+type1, "现金类分期");
        //p2p多次申请
        putBuffer(info, buffer, BaiRongConstant.als+str+ty+BaiRongConstant.cf+type1, "消费类分期");
        //代偿类多次申请
        putBuffer(info, buffer, BaiRongConstant.als+str+ty+BaiRongConstant.com+type1, "代偿类分期");
        //其他申请
        putBuffer(info, buffer, BaiRongConstant.als+str+ty+BaiRongConstant.oth+type1, "其他申请");
        if(buffer.length()>0){
            idMap.put(str, buffer.toString());
            if(BaiRongConstant.cell.equals(ty)){
                idMap.put(type, "通过手机号查得");
            }else{
                idMap.put(type, "通过身份证查得");
            }
        }
    }

    private void putBuffer(Map<String,Object> info,StringBuffer buffer,String rule,String str){
        if(info.get(rule)!=null){
            buffer.append(str+info.get(rule)+"次;");
            buffer.append("\r\n");
        }
    }

    private void fullFillMap(Map<String,Object> idMap){
        idMap.put(BaiRongConstant.d7, idMap.get(BaiRongConstant.d7)==null? "":idMap.get(BaiRongConstant.d7));
        idMap.put(BaiRongConstant.d15, idMap.get(BaiRongConstant.d15)==null? "":idMap.get(BaiRongConstant.d15));
        idMap.put(BaiRongConstant.m1, idMap.get(BaiRongConstant.m1)==null? "":idMap.get(BaiRongConstant.m1));
        idMap.put(BaiRongConstant.m3, idMap.get(BaiRongConstant.m3)==null? "":idMap.get(BaiRongConstant.m3));
    }

    /***
     * 保存征信缓存
     * @param request
     * @return
     */
    private void updateTransBrInfo(Request request){
        log.info("BRSldService类updateTransBrInfo方法 ：开始。");
        try{
            SldOperateLog sldOperateLog = request.getOpeartor();
            sldOperateLog.setOperationContent("调用征信接口"+request.getTransCode());
            sldOperateLogMapper.insert(sldOperateLog);
            log.info("BRSldService类updateTransBrInfo方法：插入操作记录成功！");
        }catch (Exception e){
            log.error("BRSldService类updateTransBrInfo方法：插入操作记录时异常！"+e.getMessage(),e);
        }
    }

    public String creditExceptionResult(CreditException e) {
        JSONObject returnJso = new JSONObject();
        if((CodeMsg.SUCCESS.getCode()).equals(e.getCode())) {
            return e.getMsg();
        }
        returnJso.put("code", e.getCode());
        returnJso.put("msg", e.getMsg());
        return returnJso.toString();
    }
}
