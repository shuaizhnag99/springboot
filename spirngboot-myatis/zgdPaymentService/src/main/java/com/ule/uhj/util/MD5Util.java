package com.ule.uhj.util;

import java.security.MessageDigest;

public class MD5Util {
	 /*** 
     * MD5加码 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
    	 char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
		    'a', 'b', 'c', 'd', 'e', 'f' };  
		  try {  
		   byte[] strTemp = inStr.getBytes();  
		   MessageDigest mdTemp = MessageDigest.getInstance("MD5");  
		   mdTemp.update(strTemp);  
		   byte[] md = mdTemp.digest();  
		   int j = md.length;  
		   char str[] = new char[j * 2];  
		   int k = 0;  
		   for (int i = 0; i < j; i++) {  
		    byte byte0 = md[i];  
		    str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
		    str[k++] = hexDigits[byte0 & 0xf];  
		   }  
		   return new String(str);  
		  } catch (Exception e) {  
		   return null;  
		  }  
    }  
  
    /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    public static String convertMD5(String inStr){  
  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
  
    }  
  
    // 测试主函数  
    public static void main(String args[]) {  
        String s = new String("123456");  
        System.out.println("原始：" + s);  
        System.out.println("MD5后：" + string2MD5(s));  
        System.out.println("加密的：" + convertMD5(s));  
        System.out.println("解密的：" + convertMD5(convertMD5(s)));  
  
    }  
}
