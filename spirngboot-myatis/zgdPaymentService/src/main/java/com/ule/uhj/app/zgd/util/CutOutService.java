package com.ule.uhj.app.zgd.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ule.uhj.app.zgd.model.Face;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CutOutService {
	private static Logger log = LoggerFactory.getLogger(CutOutService.class);

	/**
	 * 抠图 参数图片url地址、人脸位置 返回抠图后图片
	 * 
	 * @throws IOException
	 */
	public List<BufferedImage> cutout(String imgUrl, String ret)
			throws IOException {
		log.info("---------------抠图开始------------------");
		log.info("    原图片地址:" + imgUrl);
		log.info("    人脸位置    :" + ret);
		List<BufferedImage> rs = new ArrayList<BufferedImage>();
		String imgetype = imgUrl.substring(imgUrl.lastIndexOf(".") + 1).trim()
				.toLowerCase();
		byte[] imgBuffer = null;
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
			imgBuffer = outStream.toByteArray();
			// sourceImage = ImageIO.read(new ByteArrayInputStream(imgBuffer));

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
			if (httpUrl != null) {
				httpUrl.disconnect();
			}
		}

		// String 转化实体对象
		JSONArray array = JSONArray.fromObject(ret);
		List<Face> faces = new ArrayList<Face>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			faces.add((Face) JSONObject.toBean(jsonObject, Face.class));// 转成实体对象
		}

		for (Face face : faces) {
			ImageInputStream rectImage = ImageIO
					.createImageInputStream(new ByteArrayInputStream(imgBuffer));

			int top = face.getFaceRectangle().getTop();
			int left = face.getFaceRectangle().getLeft();
			int width = face.getFaceRectangle().getWidth();
			int height = face.getFaceRectangle().getHeight();

			Rectangle rect = new Rectangle(left, top, width, height);
			ImageReader reader = ImageIO.getImageReadersBySuffix(imgetype)
					.next();
			reader.setInput(rectImage, true);
			ImageReadParam param = reader.getDefaultReadParam();
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);

			rs.add(bi);
		}
		return rs;
	}
}
