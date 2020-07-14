package com.ule.uhj.util;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
public class AesUtils {
	private static final String KEY_ALGORITHM = "AES";
	private static final String CIPHER_ALGORITHM = "AES/GCM/NoPadding";//"AES/ECB/PKCS5Padding";
	private static String defaultKey = "pmoBOYQ8qzS0H5fYU3NYVw==";

	public static String initkey() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance("AES");
		kg.init(128);
		SecretKey secretKey = kg.generateKey();
		return new String(Base64.decodeBase64(secretKey.getEncoded()));//encodeBase64String(secretKey.getEncoded());
	}

	private static Key toKey(byte[] key) throws Exception {
		return new SecretKeySpec(key, "AES");
	}

	public static String encrypt(String data) throws Exception {
		Key k = toKey(Base64.decodeBase64(defaultKey.getBytes()));
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");// Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(1, k);
		return new String(Base64.encodeBase64(cipher.doFinal(data.getBytes())));//encodeBase64String(cipher.doFinal(data.getBytes()));
	}

	public static String decrypt(String data) throws IllegalBlockSizeException,Exception{  
        Key k = toKey(Base64.decodeBase64(defaultKey.getBytes()));  
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);  
        cipher.init(Cipher.DECRYPT_MODE, k);                          //初始化Cipher对象，设置为解密模式   
        return new String(cipher.doFinal(Base64.decodeBase64(data.getBytes()))); //执行解密操作   
    }  
	
	

	public static String parseByte2HexStr(byte[] buf) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < buf.length; ++i) {
			String hex = Integer.toHexString(buf[i] & 255);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}

		return sb.toString();
	}

	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		} else {
			byte[] result = new byte[hexStr.length() / 2];
			for (int i = 0; i < hexStr.length() / 2; ++i) {
				int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1),
						16);
				int low = Integer.parseInt(
						hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
				result[i] = (byte) (high * 16 + low);
			}

			return result;
		}
	}
}
