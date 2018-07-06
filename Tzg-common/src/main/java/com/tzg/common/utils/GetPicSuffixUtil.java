package com.tzg.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GetPicSuffixUtil {
	/**
	 * 根据url获取字节数组
	 * 
	 * @param strUrl
	 * @return
	 */

	public static final Map<String, Object> mFileTypes = new HashMap<String, Object>();
	static {
		mFileTypes.put("FFD8FF", ".jpg");
		mFileTypes.put("89504E47", ".png");
		mFileTypes.put("47494638", ".gif");
		mFileTypes.put("49492A00", ".tif");
		mFileTypes.put("424D", ".bmp");
		mFileTypes.put("41433130", ".dwg"); // CAD
		mFileTypes.put("38425053", ".psd");
		mFileTypes.put("7B5C727466", ".rtf"); // 日记本
		mFileTypes.put("3C3F786D6C", ".xml");
		mFileTypes.put("68746D6C3E", ".html");
		mFileTypes.put("44656C69766572792D646174653A", ".eml"); // 邮件
		mFileTypes.put("D0CF11E0", ".doc");
		mFileTypes.put("D0CF11E0", ".xls");// excel2003版本文件
		mFileTypes.put("5374616E64617264204A", ".mdb");
		mFileTypes.put("252150532D41646F6265", ".ps");
		mFileTypes.put("255044462D312E", ".pdf");
		mFileTypes.put("504B0304", ".docx");
		mFileTypes.put("504B0304", ".xlsx");// excel2007以上版本文件
		mFileTypes.put("52617221", ".rar");
		mFileTypes.put("57415645", ".wav");
		mFileTypes.put("41564920", ".avi");
		mFileTypes.put("2E524D46", ".rm");
		mFileTypes.put("000001BA", ".mpg");
		mFileTypes.put("000001B3", ".mpg");
		mFileTypes.put("6D6F6F76", ".mov");
		mFileTypes.put("3026B2758E66CF11", ".asf");
		mFileTypes.put("4D546864", ".mid");
		mFileTypes.put("1F8B08", ".gz");
		//mFileTypes.put("52494646", "webp");
	}

	public static String getimagSuffix(String strUrl) {
		String suf = "";
		byte[] imagebyte = getImagesuffixFromNetByUrl(strUrl);
		if (null != imagebyte) {
			String imagStr = byte2HexStr(imagebyte);
			for (String in : mFileTypes.keySet()) {
				if (imagStr.indexOf(in) == 0) {
					// System.out.println("imagStr" + imagStr + "=====" + "in" + in);
					suf = (String) mFileTypes.get(in);
				}

			}
		}

		return suf;
	}

	public static byte[] getImagesuffixFromNetByUrl(String strUrl) {
		try {
			URL url = new URL(strUrl);// 创建url对象
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // 打开url链接
			conn.setRequestMethod("GET");// 设置链接方式
			conn.setConnectTimeout(5 * 1000);// 设置超时时间
			InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
			byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
			return btImg;
		} catch (Exception e) {
			System.err.println("url获取图片后缀失败!");
			
		}
		return null;
	}

	/**
	 * 将字节数组转化成字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2HexStr(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < 9; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	/**
	 * 根据流转换成字节数组
	 * 
	 * @param is
	 * @return
	 */
	public static byte[] readInputStream(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = -1;
		try {
			while ((length = is.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}
			baos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = baos.toByteArray();
		try {
			is.close();
			baos.close();
		} catch (IOException e) {
			System.err.println("流转化成字节数组失败");
			
		}
		return data;
	}

	public static void main(String[] args) {
		String str = "http://p9.pstatp.com/large/pgc-image/153076627078120c425acbd";

		String str2 = "https://bihu2001.oss-cn-shanghai.aliyuncs.com/img/f87e96af3f14ea63b7e2c91a1fe295fd.jpg?x-oss-process=style/size_md";
		str2 = "https://oss02.bihu.com/img/c4ea35c7e75b2e5ce372919e2371fbec.jpg?x-oss-process=style/size_md";
		str2 = "https://oss02.bihu.com/img/18e7583409d9b634d372c0518a97663f.jpg?x-oss-process=style/size_md";
		str2 = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2469074034,2810444930&fm=27&gp=0.jpg";
		str2 = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3487866323,3474022882&fm=27&gp=0.jpg";
		str2 = "https://gss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/3b292df5e0fe99257f2d97a83fa85edf8db17121.png";
		// str2 =
		// "http://t10.baidu.com/it/u=428259324,1560836670&fm=170&s=FBAC3063D2837CEA485CDCCE0100E0A1&w=640&h=480&img.JPEG";
		System.err.println(getimagSuffix(str2));
	}
}
