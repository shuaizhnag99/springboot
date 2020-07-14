package com.ule.uhj.provider.yitu.util.baiduface;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ule.uhj.provider.yitu.util.ImgUrlUtils;

/**
 * 文件读取工具类
 */
public class FileUtil {
	protected static Log log = LogFactory.getLog(FileUtil.class);
    /**
     * 读取文件内容，作为字符串返回
     */
    public static String readFileAsString(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } 

        if (file.length() > 1024 * 1024 * 1024) {
            throw new IOException("File is too large");
        } 

        StringBuilder sb = new StringBuilder((int) (file.length()));
        // 创建字节输入流  
        
        try (FileInputStream fis = new FileInputStream(filePath); ) {
        	// 创建一个长度为10240的Buffer
            byte[] bbuf = new byte[10240];  
            // 用于保存实际读取的字节数  
            int hasRead = 0;  
            while ( (hasRead = fis.read(bbuf)) > 0 ) {  
                sb.append(new String(bbuf, 0, hasRead));  
            }  
    	}
        return sb.toString();
    }

    /**
     * 根据文件路径读取byte[] 数组
     */
    public static byte[] readFileByBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = null;

            try {
                in = new BufferedInputStream(new FileInputStream(file));
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len1);
                }

                byte[] var7 = bos.toByteArray();
                return var7;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var14) {
                	log.error("Exception error.", var14);
                }

                bos.close();
            }
        }
    }
    
	public static byte[] getBase46StringByUrl(String imgUrl) {

		URL url = null;
		InputStream is = null;
		ByteArrayOutputStream outStream = null;
		HttpURLConnection httpUrl = null;
		try {
			url = new URL(ImgUrlUtils.toInnerImgUrl(imgUrl));
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
			return outStream.toByteArray();
		} catch (Exception e) {
			log.error("Exception error.", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("Exception error.", e);
				}
			}
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					log.error("Exception error.", e);
				}
			}
			if (httpUrl != null) {
				httpUrl.disconnect();
			}
		}

		return null;
	}
}
