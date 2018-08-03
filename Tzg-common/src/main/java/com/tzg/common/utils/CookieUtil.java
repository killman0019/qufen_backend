package com.tzg.common.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统对Cookie的操作
 * 
 * @author fallenLeaves
 */
public class CookieUtil {

	/**
	 * 设置cookie
	 * 
	 * @param res
	 * @param name
	 *            cookie名字
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            cookie生命周期 以秒为单位
	 */
	public static void addCookie(HttpServletResponse res, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		res.addCookie(cookie);
	}

	/**
	 * 根据名字获取cookie
	 * 
	 * @param req
	 * @param name
	 *            cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest req, String name) {
		Map<String, Cookie> cookieMap = readCookieMap(req);
		if (cookieMap.containsKey(name)) {
			return (Cookie) cookieMap.get(name);
		} else {
			return null;
		}
	}

	/**
	 * 根据名字获取cookie值
	 * 
	 * @param req
	 * @param name
	 *            cookie名字
	 * @return
	 */
	public static String getCookieValueByName(HttpServletRequest req, String name) {
		Cookie cookie = getCookieByName(req, name);
		return null == cookie ? null : cookie.getValue();
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param req
	 * @return
	 */
	private static Map<String, Cookie> readCookieMap(HttpServletRequest req) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = req.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	/**
	 * 清除cookie
	 * 
	 * @param req
	 * @param res
	 * @param name
	 */
	public static void remove(HttpServletRequest req, HttpServletResponse res, String name) {
		Cookie cookie = getCookieByName(req, name);
		if (null != cookie) {
			cookie.setValue(null);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			res.addCookie(cookie);
		}
	}

	/**
	 * 清除所有cookie
	 * 
	 * @param req
	 * @param res
	 */
	public static void clear(HttpServletRequest req, HttpServletResponse res) {
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if (null != cookie) {
				cookie.setValue(null);
				cookie.setMaxAge(0);
				cookie.setPath("/");
				res.addCookie(cookie);
			}
		}
	}
}
