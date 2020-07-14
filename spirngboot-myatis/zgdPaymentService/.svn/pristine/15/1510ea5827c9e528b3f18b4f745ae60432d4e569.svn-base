package com.ule.uhj.app.zgd.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.ule.uhj.dto.zgd.ProductInfoN;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.zgd.ZgdQueryClient;

public class ProductInfoNService {
	
	private static Logger log = LoggerFactory.getLogger(ProductInfoNService.class);
	
	public static ProductInfoN getProductInfoN(String channelCode,String useType){
		ProductInfoN rs =null;
		
		ProductInfoN info = new ProductInfoN();
		info.setChannelCode(channelCode);
		info.setUseType(useType);
		
		ZgdQueryClient zgdQueryClient;
		try {
			zgdQueryClient = WildflyBeanFactory.getZgdQueryClient();
			List<ProductInfoN> list = zgdQueryClient.queryProductInfo(info);
			if(!CollectionUtils.isEmpty(list)){
				rs=list.get(0);
			}
		} catch (Exception e) {
			log.error("getProductInfoN error:", e);
		}
		return rs;
	}
}
