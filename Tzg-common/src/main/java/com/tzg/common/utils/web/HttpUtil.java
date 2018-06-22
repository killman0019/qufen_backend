package com.tzg.common.utils.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtil {

	private static Logger logger = Logger.getLogger(HttpUtil.class);
	
	/**
	 * 连接超时时间
	 */
	private static final int TIMEOUT = 5000;

	/**
	 * 数据传输超时时间
	 */
	private static final int SO_TIMEOUT = 6000;

	private static CloseableHttpClient getHttpClient() {

		return HttpClients.createDefault();

	}

	/**
	 * 向HTTPS地址发送put请求
	 * 
	 * @param reqURL 请求地址
	 * @param params 请求参数
	 * @return 响应内容
	 */
	@SuppressWarnings("finally")
	public static HttpResp post(String reqURL, String params) {
		HttpResp resultInf = new HttpResp();
		long responseLength = 0;                         // 响应长度
		String responseContent = null;                   // 响应内容
		CloseableHttpClient httpClient = HttpClients.createDefault(); // 创建默认的httpClient实例

		HttpPost httpPost = new HttpPost(reqURL);                        // 创建HttpPost
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT).setConnectTimeout(SO_TIMEOUT).build();//设置请求和传输超时时间
		httpPost.setConfig(requestConfig);
		try {
			StringEntity myEntity = new StringEntity(params, "UTF-8");
//			httpPost.addHeader("Content-Type", "text/xml");
			httpPost.addHeader("Content-Type","application/json");
			httpPost.setEntity(myEntity);

			HttpResponse response = httpClient.execute(httpPost); // 执行POST请求
			HttpEntity entity = response.getEntity();             // 获取响应实体
			if (null != entity) {
				responseLength = entity.getContentLength();
				responseContent = entity2String(entity, Charset.forName("UTF-8"));
				EntityUtils.consume(entity); // Consume response content
			}
			resultInf.setStatus("" + response.getStatusLine().getStatusCode());
			resultInf.setLength(responseLength);
			resultInf.setContent(responseContent);
			logger.info("请求地址: " + httpPost.getURI() + "---响应状态: " + response.getStatusLine().getStatusCode());
			logger.info("响应内容: " + responseContent);
		}
		catch (ConnectTimeoutException cte) {
			resultInf.setStatus("101");
			logger.info(reqURL+"--连接超时");
		}
		catch (SocketTimeoutException ste) {
			resultInf.setStatus("102");
			logger.info(reqURL+"--数据传输超时");
		}
		catch (ClientProtocolException e) {
			resultInf.setStatus("103");
		}
		catch (IOException e) {
			resultInf.setStatus("104");
		}
		finally {
			httpPost.releaseConnection();
			return resultInf;
		}
	}

	@SuppressWarnings("finally")
	public static HttpResp post(String reqURL, Map<String, String> params) {
		HttpResp resultInf = new HttpResp();
		long responseLength = 0;                         // 响应长度
		String responseContent = null;                   // 响应内容
		HttpClient httpClient = getHttpClient(); // 创建默认的httpClient实例
		HttpPost httpPost = new HttpPost(reqURL);                        // 创建HttpPost
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT).setConnectTimeout(SO_TIMEOUT).build();//设置请求和传输超时时间
		httpPost.setConfig(requestConfig);
		try {
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			// 构建POST请求的表单参数
			for (Map.Entry<String, String> entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost); // 执行POST请求
			HttpEntity entity = response.getEntity();             // 获取响应实体
			if (null != entity) {
				responseLength = entity.getContentLength();
				responseContent = entity2String(entity, Charset.forName("UTF-8"));
				EntityUtils.consume(entity); // Consume response content
			}
			resultInf.setStatus("" + response.getStatusLine().getStatusCode());
			resultInf.setLength(responseLength);
			resultInf.setContent(responseContent);
			logger.info("请求地址: " + httpPost.getURI() + "---响应状态: " + response.getStatusLine().getStatusCode());
			logger.info("响应内容: " + responseContent);
		}
		catch (ConnectTimeoutException cte) {
			resultInf.setStatus("101");
			logger.info(reqURL+"--连接超时");
		}
		catch (SocketTimeoutException ste) {
			resultInf.setStatus("102");
			logger.info(reqURL+"--数据传输超时");
		}
		catch (ClientProtocolException e) {
			resultInf.setStatus("103");
		}
		catch (IOException e) {
			resultInf.setStatus("104");
		}
		finally {
			httpPost.releaseConnection();
			return resultInf;
		}
	}

	
	public static String entity2String(HttpEntity entity, Charset defaultCharset) throws IOException, ParseException {
		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}
		InputStream instream = entity.getContent();
		if (instream == null) {
			return null;
		}
		try {
			if (entity.getContentLength() > Integer.MAX_VALUE) {
				throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
			}
			int i = (int) entity.getContentLength();
			if (i < 0) {
				i = 4096;
			}
			Reader reader = new InputStreamReader(instream, defaultCharset);
			CharArrayBuffer buffer = new CharArrayBuffer(i);
			char[] tmp = new char[1024];
			int l;
			while ((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
			return buffer.toString();
		}
		finally {
			instream.close();
		}
	}

	public static class HttpResp {

		private String status;

		private Long length;

		private String content;

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Long getLength() {
			return length;
		}

		public void setLength(Long length) {
			this.length = length;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String toString() {
			StringBuffer resp = new StringBuffer(512);
			resp.append("status:").append(this.status).append(",content:").append(this.content);
			return resp.toString();
		}

	}
	
	 public static String sendGet(String url, String param) {
	        String result = "";
	        BufferedReader in = null;
	        try {
	            String urlNameString = url + "?" + param;
	            URL realUrl = new URL(urlNameString);
	            // 打开和URL之间的连接
	            URLConnection connection = realUrl.openConnection();
	            // 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            // 建立实际的连接
	            connection.connect();
	            // 获取所有响应头字段
	            Map<String, List<String>> map = connection.getHeaderFields();
	            // 遍历所有的响应头字段
	            for (String key : map.keySet()) {
	                System.out.println(key + "--->" + map.get(key));
	            }
	            // 定义 BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }

	        } catch (Exception e) {
	            System.out.println("发送GET请求出现异常！" + e);
	            e.printStackTrace();
	        }
	        // 使用finally块来关闭输入流
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
	        return result;
	    }
	
}