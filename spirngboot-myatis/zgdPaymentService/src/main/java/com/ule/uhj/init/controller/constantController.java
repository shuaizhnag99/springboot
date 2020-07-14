package com.ule.uhj.init.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.init.service.ConstantService;
import com.ule.uhj.util.CusAccessObjectUtil;

@Controller
@RequestMapping("/constantController")
public class constantController {
	protected static Log log = LogFactory.getLog(constantController.class);
	
	@Autowired
	private ConstantService constantService;
	
	/**
	 * 更新人脸库
	 * http://money.beta.ule.com:8080/lendvps/constantController/updateBaiDuFace.do?imagetype=yzs_postmember_face
	 * app_selfFace,yzs_postmember_face
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateBaiDuFace")
	@ResponseBody
	public String updateBaiDuFace(HttpServletRequest request){
		String result="更新人脸库成功";
		try{
			log.info("------------------------------constantController/updateBaiDuFace.do 开始-----------------------------------");
						
			String imagetype = request.getParameter("imagetype");
			
			Map<String, Object> param =new HashMap<String, Object>();
			param.put("imagetype", imagetype);
			constantService.updateBaiDuFace(param);

		}catch(Exception e){
			log.error("loginform error!",e);
			result="更新人脸库失败，原因："+e.toString();
		}
		log.info("update result:"+result.toString());
		log.info("------------------------------constantController/updateBaiDuFace.do 结束-----------------------------------");
		return result;
	}
	
	/**
	 * 
	 * http://money.beta.ule.com:8080/lendvps/constantController/executeSql.do?sqlstr=update ULEAPP_FINANCECR.t_j_constant c set c.element_value='1' where c.element_key='operate' 
	 * 
	 * http:/192.168.112.141:8080/lendvps/constantController/executeSql.do?sqlstr=update t_j_wuhaitao w set w.element_value='3' where w.element_key='operate'
		http:/192.168.112.141:8080/lendvps/constantController/executeSql.do?sqlstr=delete from t_j_wuhaitao w where w.element_key='AAAA'
		http:/192.168.112.141:8080/lendvps/constantController/executeSql.do?sqlstr=insert into t_j_wuhaitao (ID, TYPE_CODE, TYPE_NAME, ELEMENT_KEY, ELEMENT_VALUE, CREATE_TIME, CREATOR, UPDATE_TIME, UPDATOR, REMARK, STATUS)values (seq_t_j_constant.nextval, 'varchar', 'BBBBB', 'ceshi', '3', '2017-09-12 11:37:00', '', '', '', 'DDD', 1)
		http:/192.168.112.141:8080/lendvps/constantController/executeSql.do?sqlstr=select w.element_value from t_j_wuhaitao w where w.element_key='operate1'

	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/executeSql")
	@ResponseBody
	public String executeSql(HttpServletRequest request){
		String result="成功";
		String requestIP="";
		String type ="其他";
		try{
			log.info("------------------------------constantController/.do 开始-----------------------------------");
						
			String sqlstr = request.getParameter("sqlstr");
			
			requestIP = CusAccessObjectUtil.getIpAddress(request);
			log.info("constantController-IP："+requestIP);
			type = sqlstr.toLowerCase().substring(0, 6);
			
			int count =constantService.executeSql(sqlstr,type);
			
			result="执行成功,"+type+":"+count+"条";
			
		}catch(Exception e){
			log.error(" error!",e);
			result="执行失败，原因："+e.toString();
		}
		result="用户("+requestIP+") "+result.toString();
		log.info("------------------------------constantController/.do 结束-----------------------------------");
		return result;
	}
}
