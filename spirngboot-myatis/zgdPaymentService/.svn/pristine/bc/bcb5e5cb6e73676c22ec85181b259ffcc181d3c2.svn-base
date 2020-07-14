package com.ule.uhj.init.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.zgd.service.BaiDuFaceService;
import com.ule.uhj.app.zgd.util.baiduface.Base64Util;
import com.ule.uhj.init.dao.ConstantMapper;
import com.ule.uhj.init.modle.Constant;
import com.ule.uhj.init.service.ConstantService;
import com.ule.uhj.sld.util.Convert;



@Service("constantService")
public class ConstantServiceImpl implements ConstantService {
	private static final Log log = LogFactory.getLog(ConstantServiceImpl.class);

	@Autowired
	private ConstantMapper constantMapper;
	@Autowired
	private BaiDuFaceService baiDuFaceService;
	
	@Override
	public void update(Map<String, Object> map) {
		constantMapper.update(map);
	}

	@Override
	public Constant query(Map<String, Object> map) {
		
		return constantMapper.query(map);
	}

	@Override
	public void updateBaiDuFace(Map<String, Object> param) {
		
		List<Map<String,Object>> faceList = constantMapper.queryFace(param);
		
		log.info("queryFace size:"+faceList.size());
		
		for(Map<String,Object> face:faceList){
			String userOnlyId = Convert.toStr(face.get("userOnlyId"));
			String imageUrl = Convert.toStr(face.get("imageUrl"));
			
			System.out.println(userOnlyId+":"+imageUrl);
			
			
			String facegroup = "";
			if("app_selfFace".equals(param.get("imagetype"))){
				facegroup = "uleface";
			}else if("yzs_postmember_face".equals(param.get("imagetype"))){
				facegroup = "bangZGface";
			}else{
				return;
			}
			//更新到百度人脸库
			baiDuFaceService.addFace(facegroup, userOnlyId, getBase46StringByUrl(imageUrl).toString());
			
		}
	}
	
	
	@Override
	public int executeSql(String sqlstr,String type) {
		int count =0;
		if("insert".equals(type)){
			count = constantMapper.insertSql(sqlstr);
		}else if("delete".equals(type)){
			count = constantMapper.deleteSql(sqlstr);
		}else if("update".equals(type)){
			count = constantMapper.updateSql(sqlstr);
		}
		return count;
	}
	
	public String getBase46StringByUrl(String imgUrl) {

		URL url = null;
		InputStream is = null;
		ByteArrayOutputStream outStream = null;
		HttpURLConnection httpUrl = null;
		try {
			url = new URL(imgUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			httpUrl.getInputStream();
			is = httpUrl.getInputStream();
			outStream = new ByteArrayOutputStream();
			// 创建一个Buffer字符串
			byte[] buffer = new byte[1024];
			// 每次读取的字符串长度，如果为-1，代表全部读取完毕
			int len = 0;
			// 使用一个输入流从buffer里把数据读取出来
			while ((len = is.read(buffer)) != -1) {
				// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
			// 对字节数组Base64编码
			return Base64Util.encode(outStream.toByteArray());
		} catch (Exception e) {
			//  e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					//  e.printStackTrace();
				}
			}
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					//  e.printStackTrace();
				}
			}
			if (httpUrl != null) {
				httpUrl.disconnect();
			}
		}

		return null;
	}
}
