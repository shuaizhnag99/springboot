package com.ule.uhj.app.zgd.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.dfs.client.util.UploadFile;
import com.ule.uhj.app.zgd.service.ApplyImageService;
import com.ule.uhj.app.zgd.service.PdfService;
import com.ule.uhj.app.zgd.util.PdfUtils;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.JsonUtil;
import com.ule.uhj.util.PropertiesHelper;

@Service
public class PdfServiceImpl implements PdfService{
	private static Logger log = LoggerFactory.getLogger(PdfServiceImpl.class);
	@Autowired
	private ApplyImageService applyImageService;

	@SuppressWarnings("rawtypes")
	@Override
	public void saveContact(Map<String,Object> param) throws Exception{
		String userOnlyId = Convert.toStr(param.get("userOnlyId"));
		String businessType = Convert.toStr(param.get("businessType"));
		String html = Convert.toStr(param.get("html"));
		log.info("saveContact userOnlyId:"+userOnlyId+" businessType:"+businessType);
		String fileName = "";
		String type = "";
		if("limit".equals(businessType)){
			fileName =  URLEncoder.encode("个人信息查询及留存授权书.pdf","UTF-8");
			type = "per_info_reta_power";
		}else if("2".equals(businessType)){
			fileName =  URLEncoder.encode("邮乐快捷支付协议.pdf","UTF-8");
			type = "quick_pay_order";
		}else if("3".equals(businessType)){
			fileName =  URLEncoder.encode("掌柜贷咨询服务协议.pdf","UTF-8");
			type = "view_fwktxy";
		}else if("loan".equals(businessType)){
			fileName =  URLEncoder.encode("中国邮政储蓄银行小额贷款额度借款支用单.pdf","UTF-8");
			type = "view_petty_loan_bill";
		}else if("line".equals(businessType)){
			fileName =  URLEncoder.encode("小额贷款额度借款合同.pdf","UTF-8");
			type = "loan_contract";
		}
		String url = null;
		byte[] pdf = PdfUtils.html2pdf(html);
		InputStream in = new ByteArrayInputStream(pdf); 
		String uploadURL = PropertiesHelper.getDfs("uploadURL");
		String bussinessUnit = PropertiesHelper.getDfs("bussinessUnit");
		String date=DateUtil.currDateSimpleStr();
		String fullName="/app_"+ userOnlyId+"/file"+date+"/"+new Date().getTime()+ (int) (Math.random() * 10000) +"."+ fileName.substring(fileName.lastIndexOf(".") + 1);
		String process  = PropertiesHelper.getDfs("process");
	    log.info("begin upload..."+userOnlyId);
		String result = UploadFile.upload(uploadURL, bussinessUnit, fullName, in, new String[]{process});
		log.info("upload end result is:" + result);
		JsonResult result1 = null;
		Map<String,Object> paras = new HashMap<String, Object>();
		if (result != null) {
			log.info("upload ok : " + result);
			Map obj=JsonUtil.getMapFromJsonString(result);
			if("success".equals(obj.get("status"))){
				result1 = JsonResult.success();
				result1.add("url", obj.get("url"));
				//存在url
				url = obj.get("url").toString().replace("_78x78.", ".");
				//根据type保存图片地址
				paras.clear();
				paras.put("type", type);
				paras.put("url", url);
				paras.put("userOnlyId", userOnlyId);
				paras.put("flag", businessType);
				applyImageService.saveAppPdf(paras);
			}
		}
	}

}
