package com.tzg.wap;

import java.io.File;
import java.io.FileInputStream;
import com.qiniu.util.*;
import okhttp3.*;

public class QiniuTest {
	String ak = "enchuqUr0dVnB_QzjnONINpEkqcaAfJJ_uiaAp0h";
	String sk = "_Xx683DsD4fKQB3bt4HVYPDVHvInkugaC9gEX0ua"; // 密钥配置
	Auth auth = Auth.create(ak, sk); // TODO Auto-generated constructor stub
	String bucketname = "qufenpic"; // 空间名
	String key = "base641"; // 上传的图片名

	public String getUpToken() {
		return auth.uploadToken(bucketname, null, 3600, new StringMap().put("insertOnly", 1));
	}

	public void put64image() throws Exception {
		String file = "D:\\1.jpg";// 图片路径
		FileInputStream fis = null;
		int l = (int) (new File(file).length());
		byte[] src = new byte[l];
		fis = new FileInputStream(new File(file));
		fis.read(src);
		String file64 = Base64.encodeToString(src, 0);
		System.err.println(file64);
		//l = file64.length();
		String url = "http://upload-z2.qiniu.com/putb64/" + l + "/key/" + UrlSafeBase64.encodeToString(key);
		// 非华东空间需要根据注意事项 1 修改上传域名
		RequestBody rb = RequestBody.create(null, file64);
		Request request = new Request.Builder().url(url).addHeader("Content-Type", "application/octet-stream")
				.addHeader("Authorization", "UpToken " + getUpToken()).post(rb).build();
		System.out.println(request.headers());
		OkHttpClient client = new OkHttpClient();
		okhttp3.Response response = client.newCall(request).execute();
		System.out.println(response);
	}

	public static void main(String[] args) throws Exception {
		new QiniuTest().put64image();
	}
}
