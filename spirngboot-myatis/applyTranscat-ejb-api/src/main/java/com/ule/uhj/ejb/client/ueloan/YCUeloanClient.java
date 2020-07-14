package com.ule.uhj.ejb.client.ueloan;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.ule.wildfly.annotation.BeanName;




/**
 * @author panxing
 *
 */
@Remote
@BeanName("YcUeloanBean")
public interface YCUeloanClient {
	
	Map<String, Object> queryUeloanInfo(Map<String,Object> params) throws Exception;

	List<Map<String, Object>> exportUeloanInfo(Map<String, Object> params) throws Exception;
}
