package com.tzg.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class getHttpClientUrl {

	public static JSONObject getHttpResponseJson(String url, Map<String, String> param) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		JSONObject jsonObject = null;

		try {
			// 请求发起客户端
			httpclient = HttpClients.createDefault();
			// 参数集合
			List postParams = new ArrayList();
			// 遍历参数并添加到集合
			for (Map.Entry<String, String> entry : param.entrySet()) {
				postParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			// 通过post方式访问
			HttpPost post = new HttpPost(url);
			HttpEntity paramEntity = new UrlEncodedFormEntity(postParams, "UTF-8");
			post.setEntity(paramEntity);
			response = httpclient.execute(post);
			HttpEntity valueEntity = response.getEntity();
			String content = EntityUtils.toString(valueEntity);
			jsonObject = JSONObject.parseObject(content);

			return jsonObject;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return jsonObject;
	}

	public static String getResponse(String requsetUrl, String content) {
		try {
			URL url = new URL(requsetUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoOutput(true); // 使用 URL 连接进行输出
			httpConn.setDoInput(true); // 使用 URL 连接进行输入
			httpConn.setUseCaches(false); // 忽略缓存
			httpConn.setRequestMethod("POST"); // 设置URL请求方法
			OutputStream outputStream = httpConn.getOutputStream();
			outputStream.write(content.getBytes("UTF-8"));
			outputStream.close();
			BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String readLine;
			StringBuffer responseSb = new StringBuffer();
			while ((readLine = responseReader.readLine()) != null) {
				responseSb.append(readLine);
			}
			return responseSb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "ERROR";
		}

	}

	public static void get() {
		String url = "https://data.block.cc/api/v1/markets";
		System.out.println("URL：" + url);
		StringBuffer json = new StringBuffer();
		try {
			// 实例一个url和URLConnection
			URL oracle = new URL(url);
			// 打开链接
			URLConnection yc = oracle.openConnection();
			// 输入流作参数传进InputStreamReader并用BufferedReader接受
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine = null;
			// 一直读到空，并设置流编码是UTF8
			while ((inputLine = in.readLine()) != null) {
				json.append(new String(inputLine.getBytes(), "GBK"));
			}
			// 记得关闭连接
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			JSONArray jn = JSONArray.fromObject(json.toString());

			System.out.println(jn);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("连接超时！");
		}
	}

	public static String getPostDateByUrl(String url, Map<String, String> array) {
		String data = null;
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建post方法的实例
		PostMethod postMethod = new PostMethod(url);
		// 设置头信息
		postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:39.0) Gecko/20100101 Firefox/39.0");
		postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		// 遍历设置要提交的参数
		Iterator it = array.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry) it.next();
			String key = entry.getKey();
			String value = entry.getValue().trim();
			postMethod.setParameter(key, value);
		}
		// 使用系统提供的默认的恢复策略
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		try {
			// 执行postMethod
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed:" + postMethod.getStatusLine());
			}
			// 读取内容
			byte[] responseBody = postMethod.getResponseBody();
			// 处理内容
			data = new String(responseBody);
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			data = "";
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			data = "";
			e.printStackTrace();
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		System.out.println(data);
		return data;
	}

	public static String getGetDateByUrl(String url) {

		String data = null;
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod(url);
		// 设置头信息：如果不设置User-Agent可能会报405，导致取不到数据
		getMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:39.0) Gecko/20100101 Firefox/39.0");
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		try {
			// 开始执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed:" + getMethod.getStatusLine());
			}
			// 读取内容
			byte[] responseBody = getMethod.getResponseBody();
			// 处理内容
			data = new String(responseBody);
		} catch (HttpException e) {
			// 发生异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			data = "";
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			data = "";
			e.printStackTrace();
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return data;
	}

}
