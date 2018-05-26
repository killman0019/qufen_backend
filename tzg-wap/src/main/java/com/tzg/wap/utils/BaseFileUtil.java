package com.tzg.wap.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 基本文件函数
 */
public class BaseFileUtil {
	
	/**
	 * 下载保存文件
	 * @param destUrl 目标URL
	 * @param fileDir 保存地址+文件名
	 */
	public static void saveToFile(String destUrl,String fileDir){
		try {
			File file = new File(fileDir);
			if(!file.exists()){
				URL url = new URL(destUrl);
				HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
				try {
					httpUrl.connect();
				} catch (IOException e) {
					e.printStackTrace();
				}
				BufferedInputStream bis = new BufferedInputStream(httpUrl.getInputStream());
				OutputStream out = new FileOutputStream(fileDir);
				byte[] buf = new byte[1024];
				int len;
				while ((len = bis.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				bis.close();
				out.close();
			}
		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
