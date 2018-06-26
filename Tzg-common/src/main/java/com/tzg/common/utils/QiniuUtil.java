package com.tzg.common.utils;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
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
	public static String DOMAIN = "https://pic.qufen.top";

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
		String upToken = auth.uploadToken(
				BUCKETNAME,
				null,
				3600,
				new StringMap().put("callbackUrl", "http://qufen.top/qiniu/upload/callback").put("callbackBody",
						"key=$(key)&hash=$(etag)&bucket=$(bucket)&fsize=$(fsize)"));
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

		String path = DOMAIN + "/" + fileName;
		return path;
	}

	/**
	 * 根据外网的url上传到七牛的服务器上产生七牛服务器上的url
	 * 
	 * @param url
	 *            外网的url
	 * @param fileName
	 *            文件的名字
	 * @return DefaultPutRet putret =
	 * @throws QiniuException
	 */
	public static String changeToLocalUrl(String url, String fileName) {
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		Configuration cfg = new Configuration(getZone());
		BucketManager bucketManager = new BucketManager(auth, cfg);
		try {
			bucketManager.fetch(url, BUCKETNAME, fileName);
		} catch (QiniuException e) {
			System.out.println("抽取外链图片失败原因:" + e.getMessage());
			System.out.println("外链图片抽取失败!原图片url为:" + url);
			return "false";

		}
		String newUrl = DOMAIN + "/" + fileName;
		System.out.println("七牛服务器上的url:" + newUrl);
		System.out.println("succeed upload image");
		return newUrl;
	}

	/**
	 * 本地地址上传到七牛云服务器
	 * 
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static String uploadLocalPic(String path, String fileName) {
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		Configuration cfg = new Configuration(getZone());
		UploadManager uploadManager = new UploadManager(cfg);
		String upToken = auth.uploadToken(BUCKETNAME);
		System.out.println("进入七牛云---path" + path);
		System.out.println("进入七牛云---fileName" + fileName);
		try {
			Response response = uploadManager.put(path, fileName, upToken);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			System.out.println(putRet.key);
			System.out.println(putRet.hash);
		} catch (QiniuException ex) {
			System.out.println("本地图片上传失败!");
			Response r = ex.response;
			System.err.println(r.toString());
			return null;
		}
		return DOMAIN + "/" + fileName;
	}
}
