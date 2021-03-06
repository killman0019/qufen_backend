package com.tzg.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownImgGoodUtil {
	public static Boolean downloadPicture(String urlList, String path) {
		try {
			URL url = null;

			url = new URL(urlList);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"); // 防止报403错误。
			DataInputStream dataInputStream = new DataInputStream(url.openStream());

			FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];
			int length;

			while ((length = dataInputStream.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
			fileOutputStream.write(output.toByteArray());
			dataInputStream.close();
			fileOutputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 抽离图片失败
			System.out.println("抽离图片失败!!!!fuck you!!!");
			return false;
		}
		return true;

	}

}
