package com.tzg.wap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import org.apache.axis2.util.UUIDGenerator;
import org.springframework.web.multipart.MultipartFile;

import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;

import sun.misc.BASE64Decoder;

public class TestUtil {

	static String ak = "enchuqUr0dVnB_QzjnONINpEkqcaAfJJ_uiaAp0h";
	static String sk = "_Xx683DsD4fKQB3bt4HVYPDVHvInkugaC9gEX0ua"; // 密钥配置
	static Auth auth = Auth.create(ak, sk); // TODO Auto-generated constructor stub
	static String bucketname = "qufenpic"; // 空间名
	static String key = "base64129"; // 上传的图片名

	public static MultipartFile base64ToMultipart(String base64) {
		try {
			String[] baseStrs = base64.split(",");

			BASE64Decoder decoder = new BASE64Decoder();
			byte[] b = new byte[0];
			b = decoder.decodeBuffer(base64);

			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}

			return new BASE64DecodedMultipartFile(b, baseStrs[0]);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		String file = "D:\\1.jpg";// 图片路径
		FileInputStream fis = null;
		int l = (int) (new File(file).length());
		byte[] src = new byte[l];
		fis = new FileInputStream(new File(file));
		fis.read(src);
		String file64 = Base64.encodeToString(src, 0);
		System.err.println(file64);
		MultipartFile multfile = base64ToMultipart(file64);

		String fileName = multfile.getOriginalFilename();
		// 获取文件后缀
		String prefix = fileName.substring(fileName.lastIndexOf("."));
		// 用uuid作为文件名，防止生成的临时文件重复
		final File excelFile = File.createTempFile("12234", "." + prefix);
		// MultipartFile to File
		multfile.transferTo(excelFile);

		// 你的业务逻辑
		l = (int) (excelFile.length());
		System.err.println(l);
		String url = "http://upload-z2.qiniu.com/putb64/" + l + "/key/" + UrlSafeBase64.encodeToString(key);
		// 非华东空间需要根据注意事项 1 修改上传域名
		RequestBody rb = RequestBody.create(null, file64);
		Request request = new Request.Builder().url(url).addHeader("Content-Type", "application/octet-stream")
				.addHeader("Authorization", "UpToken " + getUpToken()).post(rb).build();
		System.out.println(request.headers());
		OkHttpClient client = new OkHttpClient();
		okhttp3.Response response = client.newCall(request).execute();
		System.out.println(response);

		// 程序结束时，删除临时文件
		deleteFile(excelFile);

	}

	public static String getUpToken() {
		return auth.uploadToken(bucketname, null, 3600, new StringMap().put("insertOnly", 1));
	}

	/**
	 * 删除
	 * 
	 * @param files
	 */
	private static void deleteFile(File... files) {
		for (File file : files) {
			if (file.exists()) {
				file.delete();
			}
		}

	}
}
