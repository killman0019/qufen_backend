package com.tzg.common.utils;

public class H5AgainDeltagsUtil {
	public static String h5AgainDeltags(String html) {
		// 除去 &amp;nbsp;
		html = html.replace("&amp;nbsp;", " ");
		// 除去&lt;h1&gt;
		// 除去&ldquo;
		html = html.replace("&ldquo;", " ");
		System.out.println("=============去标签begin==================");
		// 去除&rdquo;
		html = html.replace("&rdquo;", " ");
		// 除去&lt;h1&gt;
		html = html.replace("&lt;h1&gt;", " ");
		System.out.println(html);
		System.out.println("=============去标签end==================");
		return html;

	}
}