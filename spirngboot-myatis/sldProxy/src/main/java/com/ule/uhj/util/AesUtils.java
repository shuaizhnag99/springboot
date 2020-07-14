package com.ule.uhj.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Carrot on 1/4/2018.
 */
public class AesUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(AesUtils.class);

	private static final String KEY_ALGORITHM = "AES";
	//private static final String PKCS5Padding = "AES/ECB/PKCS5Padding";// 默认的加密算法

	/**
	 * AES 加密操作
	 * 
	 * @param content
	 *            待加密内容
	 * @param password
	 *            加密密码
	 * @return 返回Base64转码后的加密数据
	 */
	public static String encrypt(String content, String password) {
		try {
			String PKCS5Padding = "AES/ECB/PKCS5Padding";
			Cipher cipher = Cipher.getInstance(PKCS5Padding);// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));// 初始化为加密模式的密码器
			byte[] result = cipher.doFinal(byteContent);// 加密
			return Base64.encodeBase64String(result);// 通过Base64转码返回
		} catch (Exception ex) {
			logger.info(ex.toString());
		}

		return null;
	}

	/**
	 * AES 解密操作
	 *
	 * @param content
	 * @param password
	 * @return
	 */
	public static String decrypt(String content, String password) {
		try {
			// 实例化
			String PKCS5Padding = "AES/ECB/PKCS5Padding";
			Cipher cipher = Cipher.getInstance(PKCS5Padding);
			// 使用密钥初始化，设置为解密模式
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));
			// 执行操作
			byte[] result = cipher.doFinal(Base64.decodeBase64(content));
			return new String(result, "utf-8");
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 生成加密秘钥
	 *
	 * @return
	 */
	private static SecretKeySpec getSecretKey(final String password) {
		// 返回生成指定算法密钥生成器的 KeyGenerator 对象
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(KEY_ALGORITHM);
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			// AES 要求密钥长度为 128
			kg.init(128, random);
			// 生成一个密钥
			SecretKey secretKey = kg.generateKey();
			return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
		} catch (NoSuchAlgorithmException ex) {
			logger.info(ex.toString());
		}

		return null;
	}

//	public static void main(String[] args) {
//		String s = "hello,您好";
//		System.out.println("s:" + s);
//		String s1 = AesUtils.encrypt(s, "1234");
//		System.out.println("s1:" + s1);
//		System.out.println("s2:" + AesUtils.decrypt(s1, "1234"));
//	}

}
