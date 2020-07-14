package com.ule.uhj.app.zgd.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * 
 * =====================================================
 * ====将标准Base64中的「+」和「/」分别改成了「-」和「_」，免去了在URL编解码和数据库存储时所要作的转换
 * =====================================================
 * 
 * 
 * 支付平台Base64 特殊字符编码处理工具类 将标准Base64中的「+」和「/」分别改成了「-」和「_」，免去了在URL编解码和数据库存储时所要作的转换
 * Created by carlos on 17-8-29.
 */
public class SpecialBASE64 {

	/*
	 * charset 将标准Base64中的「+」和「/」分别改成了「-」和「_」，免去了在URL编解码和数据库存储时所要作的转换
	 */
	private static final char[] CHARSET = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', '-', '_' };

	/*
	 * charset used to decode.
	 */
	private static final int[] DECODE_CHARSET = new int[128];

	static {
		for (int i = 0; i < CHARSET.length; i++) {
			DECODE_CHARSET[CHARSET[i]] = i;
		}
	}

	/**
	 * 编码
	 * 
	 * @param inputStr
	 * @param charset
	 * @param padding
	 *            true: 在末尾填充'='号,false:不增加'='号
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String inputStr, String charset, boolean padding)
			throws UnsupportedEncodingException {
		byte[] bytes = inputStr.getBytes(charset);
		return encode(bytes, padding);
	}

	/**
	 * base64 编码
	 * 
	 * @param bytes
	 * @param padding
	 * @return
	 */
	private static String encode(byte[] bytes, boolean padding) {
		// 4 6-bit groups
		int group_6bit_1, group_6bit_2, group_6bit_3, group_6bit_4;

		// bytes of a group
		int byte_1, byte_2, byte_3;

		// number of 3-byte groups
		int groups = bytes.length / 3;
		// at last, there might be 0, 1, or 2 byte(s) remained,
		// which needs to be encoded individually.
		int tail = bytes.length % 3;

		StringBuilder sb = new StringBuilder(groups * 4 + 4);

		// handle each 3-byte group
		for (int i = 0; i < groups; i++) {
			byte_1 = bytes[3 * i] & 0xFF;
			byte_2 = bytes[3 * i + 1] & 0xFF;
			byte_3 = bytes[3 * i + 2] & 0xFF;

			group_6bit_1 = byte_1 >>> 2;
			group_6bit_2 = (byte_1 & 0x03) << 4 | byte_2 >>> 4;
			group_6bit_3 = (byte_2 & 0x0F) << 2 | byte_3 >>> 6;
			group_6bit_4 = byte_3 & 0x3F;

			sb.append(CHARSET[group_6bit_1]).append(CHARSET[group_6bit_2])
					.append(CHARSET[group_6bit_3])
					.append(CHARSET[group_6bit_4]);
		}

		// handle last 1 or 2 byte(s)
		if (tail == 1) {
			byte_1 = bytes[bytes.length - 1] & 0xFF;

			group_6bit_1 = byte_1 >>> 2;
			group_6bit_2 = (byte_1 & 0x03) << 4;

			sb.append(CHARSET[group_6bit_1]).append(CHARSET[group_6bit_2]);

			if (padding) {
				sb.append('=').append('=');
			}
		} else if (tail == 2) {
			byte_1 = bytes[bytes.length - 2] & 0xFF;
			byte_2 = bytes[bytes.length - 1] & 0xFF;

			group_6bit_1 = byte_1 >>> 2;
			group_6bit_2 = (byte_1 & 0x03) << 4 | byte_2 >>> 4;
			group_6bit_3 = (byte_2 & 0x0F) << 2;

			sb.append(CHARSET[group_6bit_1]).append(CHARSET[group_6bit_2])
					.append(CHARSET[group_6bit_3]);

			if (padding) {
				sb.append('=');
			}
		}

		return sb.toString();
	}

	/**
	 * base64 解码
	 * 
	 * @param code
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decode(String code)
			throws UnsupportedEncodingException {
		char[] chars = code.toCharArray();

		int group_6bit_1, group_6bit_2, group_6bit_3, group_6bit_4;

		int byte_1, byte_2, byte_3;

		int len = chars.length;
		// ignore last '='s
		if (chars[chars.length - 1] == '=') {
			len--;
		}
		if (chars[chars.length - 2] == '=') {
			len--;
		}

		int groups = len / 4;
		int tail = len % 4;

		// each group of characters (4 characters) will be converted into 3
		// bytes,
		// and last 2 or 3 characters will be converted into 1 or 2 byte(s).
		byte[] bytes = new byte[groups * 3 + (tail > 0 ? tail - 1 : 0)];

		int byteIdx = 0;

		// decode each group
		for (int i = 0; i < groups; i++) {
			group_6bit_1 = DECODE_CHARSET[chars[4 * i]];
			group_6bit_2 = DECODE_CHARSET[chars[4 * i + 1]];
			group_6bit_3 = DECODE_CHARSET[chars[4 * i + 2]];
			group_6bit_4 = DECODE_CHARSET[chars[4 * i + 3]];

			byte_1 = group_6bit_1 << 2 | group_6bit_2 >>> 4;
			byte_2 = (group_6bit_2 & 0x0F) << 4 | group_6bit_3 >>> 2;
			byte_3 = (group_6bit_3 & 0x03) << 6 | group_6bit_4;

			bytes[byteIdx++] = (byte) byte_1;
			bytes[byteIdx++] = (byte) byte_2;
			bytes[byteIdx++] = (byte) byte_3;
		}

		// decode last 2 or 3 characters
		if (tail == 2) {
			group_6bit_1 = DECODE_CHARSET[chars[len - 2]];
			group_6bit_2 = DECODE_CHARSET[chars[len - 1]];

			byte_1 = group_6bit_1 << 2 | group_6bit_2 >>> 4;
			bytes[byteIdx] = (byte) byte_1;
		} else if (tail == 3) {
			group_6bit_1 = DECODE_CHARSET[chars[len - 3]];
			group_6bit_2 = DECODE_CHARSET[chars[len - 2]];
			group_6bit_3 = DECODE_CHARSET[chars[len - 1]];

			byte_1 = group_6bit_1 << 2 | group_6bit_2 >>> 4;
			byte_2 = (group_6bit_2 & 0x0F) << 4 | group_6bit_3 >>> 2;

			bytes[byteIdx++] = (byte) byte_1;
			bytes[byteIdx] = (byte) byte_2;
		}

		return new String(bytes, "utf-8");
	}

	/**
	 * Test.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * Base64 base64 = new Base64(); String str =
		 * "Man is distinguished, not only by his reason, but by this singular passion from other animals, which is a lust of the mind, that by a perseverance of delight in the continued and indefatigable generation of knowledge, exceeds the short vehemence of any carnal pleasure."
		 * ; System.out.println(str); try { String encodeStr =
		 * base64.encode(str, "GBK", false); System.out.println(encodeStr);
		 * String decodeStr = base64.decode(encodeStr);
		 * System.out.println(decodeStr); } catch (UnsupportedEncodingException
		 * e) { e.printStackTrace(); }
		 */

		test();
	}

	private static void test() {
		try {
			for (int i = 0; i < 100; i++) {
				String testData = buildTestData();
				
				System.out.println("是否含有= ："+ testData.contains("="));
				System.out.println("是否含有= ："+ testData.indexOf("="));

				System.out.println(testData);
				String encodeStr = SpecialBASE64
						.encode(testData, "utf-8", true);
				System.out.println(encodeStr);
				/*
				 * if(encodeStr.indexOf("+") >= 0 || encodeStr.indexOf("/") >=
				 * 0){ System.out.println("出现特殊字符:" + testData); }
				 */
				String decodeStr = SpecialBASE64.decode(encodeStr);
				System.out.println(decodeStr);
				/*
				 * if(!decodeStr.equals(testData)){
				 * System.out.println("Base64 decode 失败:" + decodeStr); }
				 */
				System.out
						.println("====================================================================");
			}

		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
		}
	}

	private static String buildTestData() {
		char[] TEST_CHARSET = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
				'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
				'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', ' ',
				' ', ' ', ' ', ' ', ' ', '4', '5', '6', '7', '8', '9', '-',
				'_', '测', '试', '字', '符', '特', '殊', '牛', '逼', '中', '国', '人',
				'*', '!', '#', '@', '$', '%', '^', '&', '*', '(', ')', '-',
				'=', '|', ':', ',', '.', '\'', ' ' };
		Random r = new Random(TEST_CHARSET.length - 1);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 100; i++) {
			sb.append(TEST_CHARSET[r.nextInt()]);
		}
		return sb.toString();
	}

}