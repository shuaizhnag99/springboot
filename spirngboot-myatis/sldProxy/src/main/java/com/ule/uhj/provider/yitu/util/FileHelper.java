package com.ule.uhj.provider.yitu.util;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileHelper {
	private static Log log = LogFactory.getLog(FileHelper.class);
	
	/**
	 * 按字符读取文件
	 * 
	 * @param fileName
	 * @return 字符串的文件内容
	 */
	public static String readFile(String fileName) {

		String result = "";
		File file = new File(fileName);
		if (file.exists()) {
			try (Reader reader = new InputStreamReader(new FileInputStream(file))){
				// 一次读一个字符
				int tempchar;
				while ((tempchar = reader.read()) != -1) {
					result = result + (char) (tempchar);
				}
				reader.close();
			} catch (Exception e) {
				log.error("readFile error:", e);
			}
		} else {
			System.out.println("No such file");
		}

		return result;
	}

	/**
	 * 按字符保存
	 * 
	 * @param content
	 *            , 保存内容
	 * @param filepath
	 *            , 文件路径
	 */
	public static void saveFile(final String content, final String filepath) {

		try (FileWriter fw = new FileWriter(filepath, true);
				BufferedWriter bw = new BufferedWriter(fw)){
			
			bw.write(content);
			bw.close();
			fw.close();
		} catch (IOException e) {
			log.error("readFile error:", e);
		}
	}

	/**
	 * 按二进制方式读取文件
	 * 
	 * @param fileName
	 * @return 二进制数组的文件内容
	 */
	public static byte[] readBinaryFile(String fileName) {

		byte[] result = null;
		byte[] tempBytes = new byte[256];
		File file = new File(fileName);
		if (file.exists()) {
			try (InputStream in = new FileInputStream(file)){
				// 一次读一个字符
				int byteread = 0;
				while ((byteread = in.read(tempBytes)) != -1) {
					result = new byte[byteread];
					System.arraycopy(tempBytes, 0, result, 0, byteread);
				}
				in.close();
			} catch (Exception e) {
				log.error("readFile error:", e);
			}
		} else {
		}
		return result;
	}

	/**
	 * 按二进制保存文件
	 * 
	 * @param content
	 *            , 保存内容
	 * @param filepath
	 *            , 文件路径
	 */
	public static void saveBinaryFile(final byte[] content,
			final String filepath) {
		File file = new File(filepath);
		try (BufferedOutputStream fos = new BufferedOutputStream(
				new FileOutputStream(file))){
			fos.write(content, 0, content.length);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			log.error("readFile error:", e);
		}
	}

	/**
	 * 读取图片文件内容，并转为Base64编码
	 * 
	 * @param filePath 文件路径
	 * @return 图片内容Base64编码的字符串
	 */
	public static String getImageBase64Content(String filePath)
			throws Exception {
		File imgFile = new File(filePath);
		byte[] bytes = null;
		try (InputStream is = new FileInputStream(imgFile)){
			
			long length = imgFile.length();
			bytes = new byte[(int) length];

			int offset = 0, numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			is.close();
		} catch (Exception e) {
			log.error("Exception=", e);
		}
		return Base64.encodeBase64String(bytes);
	}
	
	//base64字符串转化成图片
    public static boolean saveImageStr2File(String imgStr, String filePath)
    {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null || imgStr.trim().length() == 0) //图像数据为空
            return false;
        try(OutputStream out = new FileOutputStream(filePath)){
            //Base64解码
            byte[] b = Base64.decodeBase64(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
//            String imgFilePath = "d://222.jpg";//新生成的图片
             
            out.write(b);
            out.flush();
            out.close();
            return true;
        } 
        catch (Exception e) 
        {	
        	log.error("Exception=", e);
            return false;
        }
    }
}