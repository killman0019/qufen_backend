package com.tzg.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.tzg.common.utils.sysGlobals;

public class getParamFromUrlUtil {

	public static String interfaceUtil(String path) {
		BufferedReader in = null;
		StringBuffer result = null;
		try {
			URL url = new URL(path);
			// 打开和url之间的连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Charset", "utf-8");
			connection.setConnectTimeout(6000000);
			connection.setReadTimeout(6000000);
			connection.connect();

			result = new StringBuffer();
			// 读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;

	}

	public static String loadJson(String url) {
		StringBuilder json = new StringBuilder();
		try {
			URL urlObject = new URL(url);
			HttpURLConnection uc = (HttpURLConnection) urlObject.openConnection();
			int contentLength = uc.getContentLength();
			// System.out.println(contentLength);
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "utf-8"));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
			uc.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json.toString();

	}

	public static void main(String[] arg) {
		String interfaceUtil = interfaceUtil("https://data.block.cc/api/v1/ticker?market=bitfinex&symbol_pair=BTC_USD ");
		System.err.println(interfaceUtil);
		if (StringUtils.isNotEmpty(interfaceUtil)) {
			JSONObject jsonObject = new JSONObject(interfaceUtil);
			if (null != jsonObject) {
				Integer code = (Integer) jsonObject.get("code");
				System.out.println(code);
			}
		}
	}
}
