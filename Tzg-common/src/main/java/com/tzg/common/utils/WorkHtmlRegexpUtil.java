package com.tzg.common.utils;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
		htmlStr = htmlStr.replace("&nbsp;", "  ");
		htmlStr= htmlStr.replace("&nbsp", "  ");
		htmlStr = htmlStr.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
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
		/*		String result1 = "<img src='http://gs.xueleyun.com/cgi-bin/mathtex.cgi?\\png 0:2<xy>1' />公式：[LaTeXI]0:2<xy>1[/LaTeXI],1:x<y,y>1<p>2:x<y,x>1</p><p>3:x<1,x>y</p><o:1>缓缓<o:p>4:x<1,x>y</o:p>";
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
				System.out.println("-----------------------");*/
		// System.out.println(overviewMakeHtml(s));
		// System.out.println(matchLabel(s));
		// s =
		// "<img src=\"http://gs.xueleyun.com/cgi-bin/mathtex.cgi?\\png x>5\" XLLatex=\"[LaTeXI]x>5[/LaTeXI]\"/>ddd<img src=\"http://gs.xueleyun.com/cgi-bin/mathtex.cgi?\\png x>5\" />";

		String html = "未关注题外话：&nbsp;钢铁侠昨天的一篇文章得罪了一批人，截止今天早上8点那篇文章一共收获了126个踩，而且评论区骂人的此起披伏，简直不堪入目。不但打破了钢铁侠写文章以来的最高踩纪录（之前最高好像是30个），这个纪录也能排上币乎公测以来的第二名。钢铁侠要感谢踩我的人，因为你们真是太看得起我了。当一篇文章有几个踩或者10几个踩的时候都是正常，因为芒格说过，很遗憾这个世界不可能每个人都喜欢你，这个时候其实没有什么感觉。当有30个左右踩的时候，开始感觉有些难受了，当达到40-50个踩的时候，感觉非常难受、伤心和痛苦，当达到50-60个踩的时候，负面情绪达到了极限，愤怒、悲伤等各种负面情绪几乎要使人达到崩溃的地步。这就是昨天中午12点左右的感觉。可是中午睡了一觉，3点钟左右再看的时候，踩数又翻了一倍，居然达到了吃惊的108个，但是这个时候突然没有任何负面情绪了，开始感到莫名的兴奋，甚至是开心，正面情绪迅速攀升。&nbsp;为什么会这样，想起来王东岳老师说过，任何人的长期正面和负面情绪之和都是零，零就是无聊，无聊才是这个世界的终极。因此，你能抵抗的负面情绪有多大，正面情绪才会有多大，突然想起范爷的一句话，你能承受多大的诋毁，才能经得起多大的赞美。当一个人的负面情绪达到极限的时候，如果能顺利扛过来，就突破了，突破了之前的心里承受能力的极限。所以，钢铁侠今天要郑重感谢昨天踩我的120多个人，是你们让我顺利突破了心里承受能力的极限，钢铁侠的铠甲更厚重了。————————————————————————————————————下面是正文：钢铁侠今天介绍一个看好的项目，通证宝（tokenpos），这个项目目前还没有白皮书也没有官网，只是在创始人王玮的微信朋友圈里面简单介绍过几次。王玮是志顶科技创始人，他同时拥有金融、数学和计算机背景，可以说是天生适合做区块链项目的人。他是和孟岩一起提出通证这个概念的4个人物之一。钢铁侠今年2月份在北京参加过一个他的关于通证经济的讲座，收获十分的多，他可以说是钢铁侠关于通证经济的入门老师，之所以在这么早期就看好这个项目，主要就是因为创始人经验丰富，认知水平非常高。&nbsp;1，产品定位&nbsp;通证宝（tokenpos）的定位是区块链数字资产全生命周期管理平台，打造一个token世界的超级pos机。用创始人的话说，就是要打造一个连接现有世界和区块链的桥梁，将两个平行世界连接起来。&nbsp;钢铁侠的理解，他是要做一个数字资产上链的综合服务平台，提供上链、通证化、流转和消费环节的整体解决方案。换句话说就是对现有的公司进行币改，使得原本流通范围不大的、中介化禁锢比较强的虚拟资产进行上链，变为可以跨界流通的穿透性强的数字资产，摆脱传统中介控制和束缚。&nbsp;2，实现方式&nbsp;如上图所示架构，通过通证宝平台对接四方：a，对接资产发行方，提供数字资产发行、管理和应用服务，一键发行，全网推广。b，对接应用场景，提供数字资产的价值支撑。c，对接区块链平台，能够和不同的区块链平台进行对接，通过跨链技术，让智能合约安全的在各个链上运行。d，对接普通消费者，提供现有金融环境和数字资产两种世界的兑换、交易，以及消费服务。&nbsp;钢铁侠理解，他是要打造一个比支付宝、微信支付更大的一个生态系统，除了要涵盖现有的现金、银行卡、支付宝、微信支付等功能外，还要混合token的支付和消费等各种功能和场景。&nbsp;3，应用场景&nbsp;主要应用场景包括如下几个：&nbsp;a，游戏、社区等资产的token化：将各类互联网游戏和社区的现有虚拟资产进行token化，在区块链上进行交易和流转。使得原有传统项目的虚拟资产扩大流通和使用范围、能够进行分割和快速变现。&nbsp;b，数字资产的管理：包括传统货币的资产管理，也包括数字货币的资产管理。钢铁侠理解，这里面最大的应用就是供应链金融的数字资产服务，记得我曾经写过应收账款的通证经济模型，通证宝就可以提供解决应收账款上链的问题，应收账款只是供应链金融的一种，还包括各种其他商业票据、融资租赁等多种，这个行业如果能把这些资产盘活，资产负债表上的所谓流动资产和流动负债就能进一步加快流动，这对实体经济会有很大的帮助。&nbsp;c，各类交易平台：通证宝的应用触角还涉及到数字资产的交易、清算和结算，涵盖了OTC交易，中心化交易所交易和去中心化交易所交易和结算。&nbsp;以上就是通证宝（tokenpos）项目的大概情况，因为项目刚刚融到资，还没有进一步信息，今天只是简单介绍一下，后续会继续跟踪。总体来说，钢铁侠感觉通证宝主要是to B的一个币改综合服务项目。\r\n"
				+ "addproject.vue?ce27:119 未关注题外话：&nbsp;钢铁侠昨天的一篇文章得罪了一批人，截止今天早上8点那篇文章一共收获了126个踩，而且评论区骂人的此起披伏，简直不堪入目。不但打破了钢铁侠写文章以来的最高踩纪录（之前最高好像是30个），这个纪录也能排上币乎公测以来的第二名。钢铁侠要感谢踩我的人，因为你们真是太看得起我了。当一篇文章有几个踩或者10几个踩的时候都是正常，因为芒格说过，很遗憾这个世界不可能每个人都喜欢你，这个时候其实没有什么感觉。当有30个左右踩的时候，开始感觉有些难受了，当达到40-50个踩的时候，感觉非常难受、伤心和痛苦，当达到50-60个踩的时候，负面情绪达到了极限，愤怒、悲伤等各种负面情绪几乎要使人达到崩溃的地步。这就是昨天中午12点左右的感觉。可是中午睡了一觉，3点钟左右再看的时候，踩数又翻了一倍，居然达到了吃惊的108个，但是这个时候突然没有任何负面情绪了，开始感到莫名的兴奋，甚至是开心，正面情绪迅速攀升。&nbsp;为什么会这样，想起来王东岳老师说过，任何人的长期正面和负面情绪之和都是零，零就是无聊，无聊才是这个世界的终极。因此，你能抵抗的负面情绪有多大，正面情绪才会有多大，突然想起范爷的一句话，你能承受多大的诋毁，才能经得起多大的赞美。当一个人的负面情绪达到极限的时候，如果能顺利扛过来，就突破了，突破了之前的心里承受能力的极限。所以，钢铁侠今天要郑重感谢昨天踩我的120多个人，是你们让我顺利突破了心里承受能力的极限，钢铁侠的铠甲更厚重了。————————————————————————————————————下面是正文：钢铁侠今天介绍一个看好的项目，通证宝（tokenpos），这个项目目前还没有白皮书也没有官网，只是在创始人王玮的微信朋友圈里面简单介绍过几次。王玮是志顶科技创始人，他同时拥有金融、数学和计算机背景，可以说是天生适合做区块链项目的人。他是和孟岩一起提出通证这个概念的4个人物之一。钢铁侠今年2月份在北京参加过一个他的关于通证经济的讲座，收获十分的多，他可以说是钢铁侠关于通证经济的入门老师，之所以在这么早期就看好这个项目，主要就是因为创始人经验丰富，认知水平非常高。&nbsp;1，产品定位&nbsp;通证宝（tokenpos）的定位是区块链数字资产全生命周期管理平台，打造一个token世界的超级pos机。用创始人的话说，就是要打造一个连接现有世界和区块链的桥梁，将两个平行世界连接起来。&nbsp;钢铁侠的理解，他是要做一个数字资产上链的综合服务平台，提供上链、通证化、流转和消费环节的整体解决方案。换句话说就是对现有的公司进行币改，使得原本流通范围不大的、中介化禁锢比较强的虚拟资产进行上链，变为可以跨界流通的穿透性强的数字资产，摆脱传统中介控制和束缚。&nbsp;2，实现方式&nbsp;如上图所示架构，通过通证宝平台对接四方：a，对接资产发行方，提供数字资产发行、管理和应用服务，一键发行，全网推广。b，对接应用场景，提供数字资产的价值支撑。c，对接区块链平台，能够和不同的区块链平台进行对接，通过跨链技术，让智能合约安全的在各个链上运行。d，对接普通消费者，提供现有金融环境和数字资产两种世界的兑换、交易，以及消费服务。&nbsp;钢铁侠理解，他是要打造一个比支付宝、微信支付更大的一个生态系统，除了要涵盖现有的现金、银行卡、支付宝、微信支付等功能外，还要混合token的支付和消费等各种功能和场景。&nbsp;3，应用场景&nbsp;主要应用场景包括如下几个：&nbsp;a，游戏、社区等资产的token化：将各类互联网游戏和社区的现有虚拟资产进行token化，在区块链上进行交易和流转。使得原有传统项目的虚拟资产扩大流通和使用范围、能够进行分割和快速变现。&nbsp;b，数字资产的管理：包括传统货币的资产管理，也包括数字货币的资产管理。钢铁侠理解，这里面最大的应用就是供应链金融的数字资产服务，记得我曾经写过应收账款的通证经济模型，通证宝就可以提供解决应收账款上链的问题，应收账款只是供应链金融的一种，还包括各种其他商业票据、融资租赁等多种，这个行业如果能把这些资产盘活，资产负债表上的所谓流动资产和流动负债就能进一步加快流动，这对实体经济会有很大的帮助。&nbsp;c，各类交易平台：通证宝的应用触角还涉及到数字资产的交易、清算和结算，涵盖了OTC交易，中心化交易所交易和去中心化交易所交易和结算。&nbsp;以上就是通证宝（tokenpos）项目的大概情况，因为项目刚刚融到资，还没有进一步信息，今天只是简单介绍一下，后续会继续跟踪。总体来说，钢铁侠感觉通证宝主要是toB的一个币改综合服务项目。";

		Document document = Jsoup.parse(html);

		System.out.println("==================body====================" + document.body().text());
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

	/**
	 * 去除文章中的href链接 将链接转化成公司官网
	 * 
	 * @param str
	 * @return
	 */
	public static String deleContentsHtmlTage(String str) {
		// <a href="http://36kr.com/user/798492498">Odaily星球日报</a>
		// "<a[^>]*href=(\"([^\"]*)\"|\'([^\']*)\'|([^\\s>]*))[^>]*>(.*?)</a>";
		// String regex = "<a[^>]*href=(\"([^\"]*)\"|\'([^\']*)\'|([^\\s>]*))[^>]*>";
		// String replace = "<a href='http://www.qufen.top'>";
		// String replaceAll = str.replaceAll(regex, replace);
		String replaceAll = str;// 放开官网
		return replaceAll;
	}
}
