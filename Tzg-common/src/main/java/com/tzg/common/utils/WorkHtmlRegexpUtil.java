package com.tzg.common.utils;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkHtmlRegexpUtil {

	private static String LateXBStart = "[LaTeXB]";
	private static String LateXBRepStart = "<br /><img src='http://gs.xueleyun.com/cgi-bin/mathtex.cgi?\\png ";
	private static String LateXBRepStartHttps = "<br /><img src='https://gs.xueleyun.com/cgi-bin/mathtex.cgi?\\png ";

	private static String LateXBEnd = "[/LaTeXB]";
	private static String LateXBRepEnd = "' /><br/>";
	private static String LaTeXIStart = "[LaTeXI]";
	private static String LaTeXIRepStart = "<img src='http://gs.xueleyun.com/cgi-bin/mathtex.cgi?\\png ";
	private static String LaTeXIRepStartHttps = "<img src='https://gs.xueleyun.com/cgi-bin/mathtex.cgi?\\png ";

	private static String LaTeXIEnd = "[/LaTeXI]";
	private static String LaTeXIRepEnd = "' />";
	private static String LabelExpect = "img|br|a href=\"http://www.w3school.com.cn\"";
	private static String LabelExpectSecond = "img|br|a";
	private static String CDATASTART = "<![CDATA[";
	private static String CDATAEND = "]]>";
	private static String Img = "http://gs.xueleyun.com/cgi-bin/mathtex.cgi?\\png ";
	private static String ImgHttps = "https://gs.xueleyun.com/cgi-bin/mathtex.cgi?\\png ";

	private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

	private static final String regP_html = "<p[^>]*>";

	/**
	 * 过滤除img、br外的标签，并处理下划线、公式及空格
	 * 
	 * @param content
	 *            待清除标签内容
	 * @return
	 */
	public static String makeHtml(String content) {
		if (StringUtils.isEmpty(content))
			return "";
		String oriContent = content;
		content = dealP(content);
		if (content.startsWith("\n")) {
			content = content.substring(2);
		}
		// content = content.replace("<img", "[[img]]");
		// content = content.replace("<br", "[[br]]");
		// content = content.replace("/>", "[[/]]");
		// String inputStr = "<\\s*input\\s*[^>]*\\s*>";
		// content = content.replaceAll(inputStr, "[[a]]");//将<input>标签替换为[[a]]
		// content = content.replace(" ", "&nbsp;");
		// content = delHTMLTag(content);
		// content = removeHtmlTag(content);
		content = content.replace(LateXBStart, LateXBRepStart);
		content = content.replace(LateXBEnd, LateXBRepEnd);
		content = content.replace(LaTeXIStart, LaTeXIRepStart);
		content = content.replace(LaTeXIEnd, LaTeXIRepEnd);
		content = makeHtmlXLLatex(oriContent, content);
		content = removeHtmlTag(content);
		content = content.replace("[[a]]", "[Space]");
		// content = content.replace("[[img]]", "<img");
		// content = content.replace("[[br]]", "<br");
		// content = content.replace("[[/]]", "/>");
		content = content.replace("\r", "");
		content = content.replace("&nbsp;", " ");
		content = content.replace("&lt;", "<");
		content = content.replace("&gt;", ">");
		// 替换<br> 为 <br/>
		content = content.replace("<br>", "<br/>");

		// return makeHtmlXLLatex(oriContent,content);
		return content;
	}

	/**
	 * 过滤除img、br外的标签，并处理下划线、公式及空格 公式服务器地址的http替换为https
	 * 
	 * @param content
	 *            待清除标签内容
	 * @return
	 */
	public static String makeHtmlHttps(String content) {
		if (StringUtils.isEmpty(content))
			return "";
		String oriContent = content;
		content = dealP(content);
		if (content.startsWith("\n")) {
			content = content.substring(2);
		}
		// content = content.replace("<img", "[[img]]");
		// content = content.replace("<br", "[[br]]");
		// content = content.replace("/>", "[[/]]");
		// String inputStr = "<\\s*input\\s*[^>]*\\s*>";
		// content = content.replaceAll(inputStr, "[[a]]");//将<input>标签替换为[[a]]
		// content = content.replace(" ", "&nbsp;");
		// content = delHTMLTag(content);
		// content = removeHtmlTag(content);
		content = content.replace(LateXBStart, LateXBRepStartHttps);
		content = content.replace(LateXBEnd, LateXBRepEnd);
		content = content.replace(LaTeXIStart, LaTeXIRepStartHttps);
		content = content.replace(LaTeXIEnd, LaTeXIRepEnd);
		// 手机端要求的特殊处理不需要给智慧课堂H5页面
		// content = makeHtmlXLLatexHttps(oriContent,content);
		content = removeHtmlTag(content);
		content = content.replace("[[a]]", "[Space]");
		// content = content.replace("[[img]]", "<img");
		// content = content.replace("[[br]]", "<br");
		// content = content.replace("[[/]]", "/>");
		content = content.replace("\r", "");

		content = content.replace("&nbsp;", " ");
		// 20180521北京H5不需要转换&lt;&gt;
		// content = content.replace("&lt;", "<");
		// content = content.replace("&gt;", ">");
		// 但是对&lt;img 要处理一下
		content = content.replace("<", "&lt;");
		content = content.replace("&lt;img", "<img");
		content = content.replace("&lt; img", "<img");
		content = content.replace("/&gt;", "/>");
		content = content.replace("/ &gt;", "/>");
		content = content.replace("\"&gt;", "\"/>");
		// 替换<br> 为 <br/>
		content = content.replace("<br>", "<br/>");
		// 替换http为https
		content = content.replace("http:", "https:");
		// return makeHtmlXLLatex(oriContent,content);
		return content;
	}

	/**
	 * 过滤除img、br外的标签，并处理下划线、公式及空格(历史方法)
	 * 
	 * @param content
	 *            待清除标签内容
	 * @return
	 */
	/*private static String oldMakeHtml(String content){
	    if(StringUtils.isEmpty(content)) return "";
	    content = dealP(content);
	    if(content.startsWith("\n")){
	        content = content.substring(2);
	    }
	    content = content.replace("<img", "[[img]]");
	    content = content.replace("<br", "[[br]]");
	    content = content.replace("/>", "[[/]]");
	    String inputStr = "<\\s*input\\s*[^>]*\\s*>";
	    content = content.replaceAll(inputStr, "[[a]]");//将<input>标签替换为[[a]]
	    content = delHTMLTag(content);
	    content = content.replace("[[img]]", "<img");
	    content = content.replace("[[br]]", "<br");
	    content = content.replace("[[/]]", "/>");
	    content = content.replace("[[a]]", "[Space]");
	    content = content.replace(LateXBStart, LateXBRepStart);
	    content = content.replace(LateXBEnd, LateXBRepEnd);
	    content = content.replace(LaTeXIStart, LaTeXIRepStart);
	    content = content.replace(LaTeXIEnd, LaTeXIRepEnd);
	    content = content.replace("\r", "");
	    content = content.replace("&nbsp;", " ");
	    content = content.replace("&lt;", "<");
	    content = content.replace("&gt;", ">");
	    //替换<br> 为 <br/>
	    content = content.replace("<br>","<br/>");

	    return content;
	}*/

	/**
	 * 和makeHtml方法类似，但是把将LaTeXB标签替换为[公式]，将img标签替换为[图片]
	 */
	public static String overviewMakeHtml(String content) {
		if (StringUtils.isEmpty(content))
			return "";
		content = dealP(content);
		if (content.startsWith("\n")) {
			content = content.substring(2);
		}
		content = content.replace("<img", "[[img]]");
		content = content.replace("<br", "[[br]]");
		content = content.replace("/>", "[[/]]");
		String inputStr = "<\\s*input\\s*[^>]*\\s*>";
		content = content.replaceAll(inputStr, "_____ ");// 将<input>标签替换为_____
		content = content.replace("[Space]", "_____ ");
		// content = delHTMLTag(content);
		content = removeHtmlTag(content);
		content = content.replace("[[img]]", "<img");
		content = content.replace("[[br]]", "<br");
		content = content.replace("[[/]]", "/>");
		inputStr = "\\[LaTeXB][^\\[]*[^L]*[^a]*[^T]*[^e]*[^X]*[^B]*[^]]*\\[/LaTeXB]";
		content = content.replaceAll(inputStr, "[公式]");// 将LaTeXB标签替换为[公式]
		inputStr = "\\[LaTeXI][^\\[]*[^L]*[^a]*[^T]*[^e]*[^X]*[^I]*[^]]*\\[/LaTeXI]";
		content = content.replaceAll(inputStr, "[公式]");// 将LaTeXB标签替换为[公式]
		// inputStr = "<img[^>]+>";
		inputStr = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
		content = content.replace("&lt;", "<");
		content = content.replace("&gt;", ">");
		content = content.replaceAll(inputStr, "[图片]");// 将img标签替换为[图片]
		content = content.replace("\r", "");
		content = content.replace("&nbsp;", " ");
		content = content.replace("<br />", "");
		return content;
	}

	/**
	 * 处理p标签，替换为换行符 \n
	 * 
	 * @return
	 */
	public static String dealP(String htmlStr) {
		// Pattern p_html = Pattern.compile(regP_html,
		// Pattern.CASE_INSENSITIVE);
		// Matcher m_html = p_html.matcher(htmlStr);
		// htmlStr = m_html.replaceAll("$2"); // 过滤html标签
		htmlStr = htmlStr.replaceAll("<([p,P]+)[^<>]*>(.*?)</\\1>", "\n$2\n"); // 过滤html标签
		return htmlStr.trim(); // 返回文本字符串
	}

	/**
	 * 删除html标签
	 * 
	 * @param content
	 * @return
	 */
	public static String removeHtmlTag(String content) {
		// String patten = "</?(?!img|input)[^>]+>";
		/*String patten = "<([a-zA-Z]+)[^<>]*>(.*?)</\\1>";
		Pattern p = Pattern.compile(patten);
		Matcher m = p.matcher(content);
		if (m.find()) {
		    content = content.replaceAll(patten, "$2");
		    content = removeHtmlTag(content);
		}
		return content;*/
		// return
		// Pattern.compile("</?(?!img|input)[a-zA-Z][a-zA-Z0-9]*[^<>]*>").matcher(content).replaceAll("");
		// content =
		// Pattern.compile("</?(?!img|input|br)[a-zA-Z](?:[a-zA-Z0-9]*(?::[a-zA-Z0-9]+)*)(?:\\s+[^>]+|)>").matcher(content).replaceAll("");
		// content =
		// Pattern.compile("</?(?!img|input|br)[a-zA-Z](?:[a-zA-Z0-9]*(?::[a-zA-Z0-9]+)*)((?:(?=\\s+)[^>/]+)*|\\s*/)>").matcher(content).replaceAll("");
		content = Pattern.compile("</?(?!img|input|br)[a-zA-Z](?:[a-zA-Z0-9]*(?::[a-zA-Z0-9]+)*)(?:(?=\\s+)[^>/]+)?s*/?>").matcher(content).replaceAll("");
		String inputStr = "<\\s*input\\s*[^>]*\\s*>";
		content = content.replaceAll(inputStr, "[[a]]");// 将<input>标签替换为[[a]]
		return content;
	}

	public static String delHTMLTag(String htmlStr) {
		// Pattern p_html = Pattern.compile(regEx_html,
		// Pattern.CASE_INSENSITIVE);
		// Matcher m_html = p_html.matcher(htmlStr);
		// htmlStr = m_html.replaceAll(""); // 过滤html标签
		/*htmlStr = Jsoup.clean(htmlStr, Whitelist.none().
		        addTags(new String[]{"img"}).
		        addAttributes("img", new String[]{"align", "alt", "height", "src", "title", "width"}).
		        addProtocols("img", "src", new String[]{"http", "https"}));*/
		htmlStr = htmlStr.replace(" ", "&nbsp;");
		htmlStr = Jsoup.clean(htmlStr, Whitelist.none());
		return htmlStr.trim(); // 返回文本字符串
	}

	/**
	 * 图片转为公式代码
	 * 
	 * @param content
	 * @return
	 */
	public static String makeXLLatex(String content) {
		Pattern p = Pattern.compile("<img\\s.+\\sXLLatex\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");// <img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
		Matcher m = p.matcher(content);
		while (m.find()) {
			if (m.groupCount() == 1)
				content = content.replace(m.group(), m.group(1));
		}
		// System.out.println(content);
		return content;
	}

	/**
	 * 公式转img图片（增加XLLatex属性放原有公式，跟makeHtml()结合内部用）
	 * 
	 * @param content
	 * @return
	 */
	public static String makeHtmlXLLatex(String content, String makeHtml) {
		// String html = makeHtml(content);
		// Pattern pLaTeXI =
		// Pattern.compile("\\[LaTeXI][^\\[]*[^L]*[^a]*[^T]*[^e]*[^X]*[^I]*[^]]*\\[/LaTeXI]");//<img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
		Pattern pLaTeXI = Pattern
				.compile("\\[LaTeXI][^\\[]*[^L]*[^a]*[^T]*[^e]*[^X]*[^I]*[^]]*\\[/LaTeXI]|\\[LaTeXB][^\\[]*[^L]*[^a]*[^T]*[^e]*[^X]*[^I]*[^]]*\\[/LaTeXB]");
		Matcher mLaTeXI = pLaTeXI.matcher(content);
		String img;
		Map<String, String> labMap = new HashMap<>();
		List<String> srcList = new ArrayList<>();
		String rImg;
		while (mLaTeXI.find()) {
			img = mLaTeXI.group();
			rImg = img.replace(LateXBStart, Img).replace(LateXBEnd, "").replace(LaTeXIStart, Img).replace(LaTeXIEnd, "");
			// System.out.println(rImg+"-------------↓↓↓↓↓↓");
			srcList.add(rImg);
			labMap.put(rImg, img);
			// System.out.println(img+"-------------↓↓↓↓↓↓");
		}
		Pattern p1 = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");// <img[^>]+src\s*=\s*['"]([^'"]+)['"][^>]*>
		Matcher m1 = p1.matcher(makeHtml);
		String src;
		while (m1.find()) {
			img = m1.group();
			// System.out.println(img+"img-------------↓↓↓↓↓↓");
			rImg = img;
			if (m1.groupCount() == 1) {
				src = m1.group(1);
				// System.out.println(src+"---src-------------↓↓↓↓↓↓");
				for (String a : srcList) {
					if (src.equals(a)) {
						// System.out.println(a+"---a-------------↓↓↓↓↓↓");
						rImg = img.replace("/>", " XLLatex='" + labMap.get(a) + "' /> ");
						break;
					}
				}

			}
			rImg = rImg.replace("<", "&lt;").replace(">", "&gt;");
			makeHtml = makeHtml.replace(img, rImg);
		}
		// System.out.println("a1-----------------↓↓↓↓↓↓");
		// System.out.println(html);
		return makeHtml;
	}

	/**
	 * 公式转img图片（增加XLLatex属性放原有公式，跟makeHtml()结合内部用）
	 * 
	 * @param content
	 * @return
	 */
	public static String makeHtmlXLLatexHttps(String content, String makeHtml) {
		// String html = makeHtml(content);
		// Pattern pLaTeXI =
		// Pattern.compile("\\[LaTeXI][^\\[]*[^L]*[^a]*[^T]*[^e]*[^X]*[^I]*[^]]*\\[/LaTeXI]");//<img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
		Pattern pLaTeXI = Pattern
				.compile("\\[LaTeXI][^\\[]*[^L]*[^a]*[^T]*[^e]*[^X]*[^I]*[^]]*\\[/LaTeXI]|\\[LaTeXB][^\\[]*[^L]*[^a]*[^T]*[^e]*[^X]*[^I]*[^]]*\\[/LaTeXB]");
		Matcher mLaTeXI = pLaTeXI.matcher(content);
		String img;
		Map<String, String> labMap = new HashMap<>();
		List<String> srcList = new ArrayList<>();
		String rImg;
		while (mLaTeXI.find()) {
			img = mLaTeXI.group();
			rImg = img.replace(LateXBStart, ImgHttps).replace(LateXBEnd, "").replace(LaTeXIStart, ImgHttps).replace(LaTeXIEnd, "");
			// System.out.println(rImg+"-------------↓↓↓↓↓↓");
			srcList.add(rImg);
			labMap.put(rImg, img);
			// System.out.println(img+"-------------↓↓↓↓↓↓");
		}
		Pattern p1 = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");// <img[^>]+src\s*=\s*['"]([^'"]+)['"][^>]*>
		Matcher m1 = p1.matcher(makeHtml);
		String src;
		while (m1.find()) {
			img = m1.group();
			// System.out.println(img+"img-------------↓↓↓↓↓↓");
			rImg = img;
			if (m1.groupCount() == 1) {
				src = m1.group(1);
				// System.out.println(src+"---src-------------↓↓↓↓↓↓");
				for (String a : srcList) {
					if (src.equals(a)) {
						// System.out.println(a+"---a-------------↓↓↓↓↓↓");
						rImg = img.replace("/>", " XLLatex='" + labMap.get(a) + "' /> ");
						break;
					}
				}

			}
			rImg = rImg.replace("<", "&lt;").replace(">", "&gt;");
			makeHtml = makeHtml.replace(img, rImg);
		}
		// System.out.println("a1-----------------↓↓↓↓↓↓");
		// System.out.println(html);
		// 替换http为https
		content = content.replace("http:", "https:");
		return makeHtml;
	}

	/**
	 * 4.2.0以前历史版本提示
	 * 
	 * @param content
	 * @param appVersion
	 * @return
	 */
	/*public static String markSpaceG(String content ,String appVersion,Integer appType,Integer platform){
	    String[] versions = {"4.2.0","","1.1.0"};
	    //4.1.0线上 4.2.0即将月底发布
	    if (StringUtils.isNotBlank(appVersion)) {
	        if (!OpenApiStringUtils.isAfterVersionAndType(appVersion,versions,appType)){
	            if (platform == 2){
	                content = content.replaceAll("\\[SpaceG\\]", "<font color='#9e2927'>客户端版本过低，请升级后答题</font>");
	            }else{
	                content = content.replaceAll("\\[SpaceG\\]", "客户端版本过低，请升级后答题");
	            }
	        }
	    }
	    return content;
	}*/
	/**
	 * 4.2.0以前历史版本删除选项
	 * 
	 * @param content
	 * @param appVersion
	 * @return
	 */

	/*public static String deleteSpaceG(Boolean hasSpaceG, String content, String appVersion, Integer appType) { // 不含spaceG标签
		if (!hasSpaceG) {
			return makeHtml(content);
		}
		String[] versions = { "4.2.0", "", "1.1.0" }; // 4.1.0线上4.2.0即将月底发布
		if (StringUtils.isNotBlank(appVersion)) {
			if (!OpenApiStringUtils.isAfterVersionAndType(appVersion, versions, appType)) {
				return null;
			}
		}
		return makeHtml(content);
	}*/

	public static void main(String[] args) {
		String result1 = "<img src='http://gs.xueleyun.com/cgi-bin/mathtex.cgi?\\png 0:2<xy>1' />公式：[LaTeXI]0:2<xy>1[/LaTeXI],1:x<y,y>1<p>2:x<y,x>1</p><p>3:x<1,x>y</p><o:1>缓缓<o:p>4:x<1,x>y</o:p>";
		System.out.println(WorkHtmlRegexpUtil.makeHtmlHttps(result1));

		System.out.println(WorkHtmlRegexpUtil.makeHtml(result1));
		System.out.println("################################################################");
		String a = "http://gs.xueleyun.com/cgi-bin/mathtex.cgi?\\png  < / img> [SpaceG/]dddddss[SpaceG/]aqwwww";
		// String
		// b="http://gs.xueleyun.com/cgi-bin/mathtex.cgi?\\png  <  / img>";
		// System.out.println(a.replaceAll("\\[SpaceG\\]", "客户端版本过低，请升级后答题"));
		// System.out.println(a.indexOf("[SpaceG]"));
		// String unsafe = "[LaTeXI]-5<a<-2[/LaTeXI]";
		// System.out.println(delHTMLTag(a));
		// System.out.println(removeHtmlTag(a));
		String unsafe = " [LaTeXI] < img>[/LaTeXI][LaTeXI] <  / img>[/LaTeXI] [LaTeXB]2<x>|[/LaTeXB][LaTeXI]-5<a<-2[/LaTeXI]aaaaa<bbbb><img>aaaa</img>";
		System.out.println(makeHtml(unsafe));
		String safe = Jsoup.clean(unsafe, Whitelist.none());
		// System.out.println(safe);
		// System.out.println(removeHtmlTag(unsafe));
		// 输出 :
		// <p><a href="http://www.oschina.net/" rel="nofollow"> 开源中国社区 </a></p>

		String context = "<img src='http://dl.xueleyun.com/files/aef0526e18566dd9b4e39e06b3b3a76c.png' style=\\\"width: 217px; height: 134px;\\\">[LaTeXI] < img>[/LaTeXI][LaTeXI] <  / img>[/LaTeXI]";
		context = "单选纯公式[LaTeXI]\\\\begin{cases} x>1.\\\\chi < 1\\\\\\\\ y\\\\leq |y\\\\geq 1\\\\\\\\ Z\\\\geq 1.Z\\\\leq 1\\\\\\\\ p < 1.p>1\\\\end{cases} \\[/LaTeXI]";
		// System.out.println(context);
		System.out.println("-------------------------");
		// System.out.println(makeHtml(context));
		System.out.println("-------------------------");
		context = "[LaTeXI]-5<a<-2[/LaTeXI]";
		String result = "<p></p> <code href=\\\"http://www.baidu.com/gaoji/preferences.html\\\"name=\\\"tj_setting\\\">搜索设置</code>[LaTeXI]-5<a<-2[/LaTeXI]公式：[LaTeXI]2<x>|[/LaTeXI]aaaaa<bbbb>aaaa<p><img width=\"199\" src=\"_image/12/label\" alt=\"FDFDFDF\"/></p>缓缓";
		// System.out.println(removeHtmlTag(result));
		System.out.println(makeHtml(result));
		System.out.println("-------------------------");
		// System.out.println(markSpaceG(context, "4.2.0"));
		String s = "单选纯公式 <img src=\"http://gs.xueleyun.com/cgi-bin/mathtex.cgi?\\png \\begin{cases} x>1.\\chi < 1\\\\ y\\leq |y\\geq 1\\\\ Z\\geq 1.Z\\leq 1\\\\ p < 1.p>1\\end{cases} \" /> ";
		// System.out.println(removeHtmlTag(s));
		System.out.println("-----------------------");
		// System.out.println(overviewMakeHtml(s));
		// System.out.println(matchLabel(s));
		// s =
		// "<img src=\"http://gs.xueleyun.com/cgi-bin/mathtex.cgi?\\png x>5\" XLLatex=\"[LaTeXI]x>5[/LaTeXI]\"/>ddd<img src=\"http://gs.xueleyun.com/cgi-bin/mathtex.cgi?\\png x>5\" />";

	}

	/**
	 * 获取img中的src属性值
	 * 
	 * @param content
	 * @return
	 */
	public static List<String> getImgSrc(String content) {
		List<String> list = new ArrayList<String>();
		// 目前img标签标示有3种表达式
		// <img alt="" src="1.jpg"/> <img alt="" src="1.jpg"></img> <img alt=""
		// src="1.jpg">
		// 开始匹配content中的<img />标签
		Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
		Matcher m_img = p_img.matcher(content);
		boolean result_img = m_img.find();
		if (result_img) {
			while (result_img) {
				// 获取到匹配的<img />标签中的内容
				String str_img = m_img.group(2);
				// 开始匹配<img />标签中的src
				Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
				Matcher m_src = p_src.matcher(str_img);
				if (m_src.find()) {
					String str_src = m_src.group(3);
					list.add(str_src);
				}
				// 结束匹配<img />标签中的src
				// 匹配content中是否存在下一个<img />标签，有则继续以上步骤匹配<img />标签中的src
				result_img = m_img.find();
			}
		}
		return list;
	}

}
