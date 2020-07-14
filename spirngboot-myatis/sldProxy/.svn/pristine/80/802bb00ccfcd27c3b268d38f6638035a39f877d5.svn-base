package com.ule.uhj.provider.yitu.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil {
	protected static Log log = LogFactory.getLog(FileUtil.class);
	public static String createFaceCode(File photo) throws Exception {
		byte[] imageByte = toByteArray(photo);
		String humanFaceCode = Base64.encodeBase64String(imageByte);
		return humanFaceCode;
	}
	
	public static String inToBase64(ByteArrayOutputStream photo) throws Exception {
		InputStream input = new ByteArrayInputStream(photo.toByteArray());  
		byte[] imageByte = toByteArray(input);
		String humanFaceCode = Base64.encodeBase64String(imageByte);
		return humanFaceCode;
	}
	
	public static byte[] inToByteArray(String base64String) throws Exception {
		byte[] humanFaceCode = Base64.decodeBase64(base64String);
		return humanFaceCode;
	}
	
	private static byte[] toByteArray(InputStream imageFile) throws Exception {
		BufferedImage img = ImageIO.read(imageFile);
		ByteArrayOutputStream buf = new ByteArrayOutputStream(
				(int) imageFile.available());
		try {
			ImageIO.write(img, "jpg", buf);
		} catch (Exception e) {
			log.error("Exception error.", e);
			return null;
		}
		return buf.toByteArray();
	}
	
	private static byte[] toByteArray(File imageFile) throws Exception {
		BufferedImage img = ImageIO.read(imageFile);
		ByteArrayOutputStream buf = new ByteArrayOutputStream(
				(int) imageFile.length());
		try {
			ImageIO.write(img, "jpg", buf);
		} catch (Exception e) {
			log.error("Exception error.", e);
			return null;
		}
		return buf.toByteArray();
	}
}
