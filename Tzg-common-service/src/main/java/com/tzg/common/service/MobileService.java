//package com.tzg.common.service;
//
//import com.tzg.common.utils.MD5Util;
//import com.tzg.entitys.smssend.Smssend;
//
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpStatus;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.lang3.StringUtils;
//import org.dom4j.Document;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 
// * @ClassName: SmssenndMobileService
// * @Description: 发送短信
// * @author wuws
// *
// */
//public class MobileService {
//	// private static String sn = "SDK-WSS-010-06839";
//	// private static String pwd = "53cbA67e";
//	// private static String md5pwd = MD5Util.md5Hex(sn + pwd).toUpperCase();
//	// private static String mtURL =
//	// "http://sdk.entinfo.cn:8061/webservice.asmx/mdsmssend";
//
//	private static String sn = null;
//	private static String pwd = null;
//	private static String md5pwd = null;
//	private static String mtURL = null;
//
//	public static void init(String sn, String pwd, String mtURL) {
//		MobileService.sn = sn;
//		MobileService.pwd = pwd;
//		MobileService.md5pwd = MD5Util.md5Hex(
//				MobileService.sn + MobileService.pwd).toUpperCase();
//		MobileService.mtURL = mtURL;
//	}
//
//	public static String sendSms(Smssend smsSend) throws Exception {
//		return sendMSG(smsSend.getVcPhone(), smsSend.getVcContent(),
//				3 == (smsSend.getItype().intValue()) ? "1" : null, null, null,
//				null);
//	}
//
//	public static String sendMSG(String mobile, String content, String ext,
//			String stime, String rrid, String msgfmt) throws Exception {
//		if (StringUtils.isBlank(content)) {
//			return null;
//		}
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new NameValuePair("sn", sn));
//		params.add(new NameValuePair("pwd", md5pwd));
//		params.add(new NameValuePair("mobile", mobile));
//		params.add(new NameValuePair("content", URLEncoder.encode(content,
//				"utf-8")));
//		params.add(new NameValuePair("ext", ext == null ? "" : ext));
//		params.add(new NameValuePair("stime", stime == null ? "" : stime));
//		params.add(new NameValuePair("rrid", rrid == null ? "" : rrid));
//		params.add(new NameValuePair("msgfmt", msgfmt == null ? "" : msgfmt));
//
//		String responseStr = sendHttpMsg(mtURL,
//				params.toArray(new NameValuePair[params.size()]));
//		String parseResult = parseXML(responseStr);
//
//		return parseResult;
//	}
//
//	public static String sendSmsByJZ(Smssend smsSend) throws Exception {
//		return sendMSGByJZ(smsSend.getVcPhone(), smsSend.getVcContent());
//	}
//
//	public static String sendMSGByJZ(String destmobile, String msgText)
//			throws Exception {
//		if (StringUtils.isBlank(msgText)) {
//			return null;
//		}
//		HttpClient client = new HttpClient();
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new NameValuePair("account", sn));
//		params.add(new NameValuePair("password", pwd));
//		params.add(new NameValuePair("destmobile", destmobile));
//		params.add(new NameValuePair("msgText", msgText));
//		PostMethod postMethod = new PostMethod(mtURL);
//		postMethod.getParams().setContentCharset("utf-8");
//		postMethod.setRequestBody(params.toArray(new NameValuePair[params
//				.size()]));
//		String result = null;
//		int statusCode = 0;
//		statusCode = client.executeMethod(postMethod);
//		if (statusCode == HttpStatus.SC_OK) {
//			result = postMethod.getResponseBodyAsString();
//		}
//		postMethod.releaseConnection();
//		System.out.println("result:" + result);
//		return result;
//	}
//
//	private static String sendHttpMsg(String url, NameValuePair[] params)
//			throws Exception {
//		String result = null;
//		HttpClient client = new HttpClient();
//		PostMethod postMethod = new PostMethod(url);
//		postMethod.setRequestBody(params);
//
//		int statusCode = 0;
//		statusCode = client.executeMethod(postMethod);
//		if (statusCode == HttpStatus.SC_OK) {
//			result = postMethod.getResponseBodyAsString();
//			return result;
//		}
//		postMethod.releaseConnection();
//		return result;
//	}
//
//	private static String parseXML(String result) throws Exception {
//		String temp = result;
//		if (result != null) {
//			Document doc = null;
//			doc = DocumentHelper.parseText(temp);
//			Element res = doc.getRootElement();
//			return res.getText();
//		}
//
//		return temp;
//	}
//}
