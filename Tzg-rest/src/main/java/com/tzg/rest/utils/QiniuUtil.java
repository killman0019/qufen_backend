package com.tzg.rest.utils;

import org.apache.log4j.Logger;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

public class QiniuUtil {
	private static Logger log = Logger.getLogger(QiniuUtil.class);

	// @Value("#{paramConfig['qiniu.bucket']}")
	public static String BUCKETNAME = "qufenpic";

	// @Value("#{paramConfig['qiniu.cdns']}")
	public static String DOMAIN = "http://pic.qufen.top";

	// @Value("#{paramConfig['qiniu.accesskey']}")
	public static String ACCESS_KEY = "enchuqUr0dVnB_QzjnONINpEkqcaAfJJ_uiaAp0h";

	// @Value("#{paramConfig['qiniu.secretkey']}")
	public static String SECRET_KEY = "_Xx683DsD4fKQB3bt4HVYPDVHvInkugaC9gEX0ua";

	// 是否可以断点续传（true:可以；false:不可以）
	public static boolean breakingPoint = false;

	public static Zone getZone() {
		return Zone.zone2();
	}

	public static UploadManager getUploadManager() {
		FileRecorder fileRecorder = null;
		if (breakingPoint) {
			try {
				fileRecorder = new FileRecorder(Paths.get(System.getenv("java.io.tmpdir"), BUCKETNAME).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 构造一个带指定 Zone 对象的配置类
		Configuration cfg = new Configuration(getZone());
		// 声明上传类
		return new UploadManager(cfg, fileRecorder);
	}

	// 获取凭证
	public static String getUpToken() {
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		String upToken = auth.uploadToken(BUCKETNAME, null, 3600, new StringMap().put("callbackUrl", "http://qufen.top/qiniu/upload/callback")
				.put("callbackBody", "key=$(key)&hash=$(etag)&bucket=$(bucket)&fsize=$(fsize)"));
		return upToken;
	}

	public static String uploadFileStr(File uploadPictureFile, String pictureName) {
		try {
			Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
			// 上传
			Response response = getUploadManager().put(uploadPictureFile, pictureName, getUpToken());
			return response.bodyString();
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
			return null;
		}
	}

	public static String upload(File uploadPictureFile, String pictureName) {
		try {
			Response response = getUploadManager().put(uploadPictureFile, pictureName, getUpToken());
			return response.bodyString();
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
			return null;
		}
	}

	public static String uploadStream(InputStream stream, String fileName) {
		Configuration cfg = new Configuration(getZone());
		UploadManager uploadManager = new UploadManager(cfg);
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		String token = auth.uploadToken(BUCKETNAME);

		try {
			Response response = getUploadManager().put(stream, fileName, token, null, null);
		} catch (QiniuException e) {
			e.printStackTrace();
		}
		
		String path = DOMAIN+"/"+fileName;
		return path;
	}
}
