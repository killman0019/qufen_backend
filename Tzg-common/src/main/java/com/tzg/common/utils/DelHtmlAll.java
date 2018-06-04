package com.tzg.common.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DelHtmlAll {

	public static String delHtmlAll(String html) {

		System.out.println();
		System.out.println("======================去标签begin===============================");
		System.out.println();
		String text1 = null;
		String text = null;

	
		System.out.println(html);
		
		// <p>段落替换为换行 
		//html = html.replaceAll("<p .*?>", "\r\n"); 
	    // <br><br/>替换为换行 
		//html = html.replaceAll("<br\\s*/?>", "\r\n"); 
	    // 去掉其它的<>之间的东西 \\<.*?>
		html = html.replaceAll("</?[^>]+>", ""); 
	    // 去掉空格 
		//html = html.replaceAll(" ", ""); 
		
		System.out.println(html);
		System.out.println();
		System.out.println("======================去标签end===============================");
		System.out.println();
		return text;

	}

	public static String delHtmlAll2(String html) {

		System.out.println();
		System.out.println("======================去标签begin===============================");
		System.out.println();
		String text1 = null;

		Document parse = Jsoup.parse(html);
		System.out.println(html);
		String text = parse.body().text();
		System.out.println(text);
		System.out.println();
		System.out.println("======================去标签end===============================");
		System.out.println();
		return text;

	}

	public static String delHtmlAll1(String html) {

		System.out.println();
		System.out.println("======================去标签begin===============================");
		System.out.println();
		String text1 = null;

		// 去图片
		html = html.replace("&nbsp;", "");
		text1 = WorkHtmlRegexpUtil.overviewMakeHtml(html);
		System.out.println("text1====" + text1);
		text1 = WorkHtmlRegexpUtil.removeHtmlTag(text1);
		System.out.println("text1++++" + text1);
		String text = WorkHtmlRegexpUtil.delHTMLTag(text1.replaceAll("<([p,P]+)[^<>]*>(.*?)</\\1>", "\n$2\n")).replace("&nbsp;", " ").replace("&lt;p&gt;", "")
				.replace("&lt;/p&gt;", "");
		text.replace("&amp;nbsp;", "");
		text.replace("&amp;nbsp;", " ");
		System.out.println();
		System.out.println("=====================================================================");
		System.out.println();
		System.out.println("text+++" + text);

		System.out.println();
		System.out.println("======================去标签end===============================");
		System.out.println();
		return text;

	}

}
