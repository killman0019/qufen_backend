package com.tzg.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class HtmlUtils {

	public static JSONObject getRequestContent(HttpServletRequest req) {
		JSONObject data = null;
		try {
			InputStream is = req.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String line = null;
			StringBuffer content = new StringBuffer();
			while ((line = br.readLine()) != null) {
//				System.out.println(line);
				content.append(line);
//				System.out.println(content);
			}
			String reqStr = content.toString().trim();
			if (StringUtils.isEmpty(reqStr)) {
				return new JSONObject();
			}
			if (reqStr.contains("=")) {
				reqStr = reqStr.replaceAll("=", ":");
			}
			if (!reqStr.startsWith("{")) {
				reqStr = "{" + reqStr;
			}
			if (!reqStr.endsWith("}")) {
				reqStr = reqStr + "}";
			}
			data = JSONObject.parseObject(reqStr);
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONObject();
		}
		return data;
	}
}
