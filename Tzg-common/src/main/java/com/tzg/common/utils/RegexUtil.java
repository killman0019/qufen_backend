package com.tzg.common.utils;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	/**
	 * 身份证号码
	 */
	public static final String IDCARD = "(^\\d{18}$)|(^\\d{15}$)";
	/**
	 * 手机号码 移动：134、135、136、137、138、139、150、151、157、158、159、182、187、188
	 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、 177 177是电信4G专属号段，联通176，移动178，虚拟运营商170。
	 */
	public static final String PHONEREGEX = "^[1]([2-3][0-9]{1}|45|47|[5][0-3]{1}|[5][5-9]{1}|70|71|73|[7][6-8]{1}|[8][0-9]{1})[0-9]{8}$";

	/**
	 * 姓名
	 */
	public static final String NAMEREGEX = "[\u4E00-\u9FA5]{2,12}(?:·[\u4E00-\u9FA5]{2,12})*";

	/**
	 * 邮箱
	 */
	public static final String EMAILREGEX = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

	/**
	 * 空格
	 */
	public static final String SPACEREGEX = "^[^\\s　]+$";

	/**
	 * 文本域图片
	 */
	public static final String TEXTAREAIMGREGEX = "name=[0-9]+.[a-zA-Z]+";

	/**
	 * 图片
	 */
	public static final String IMGTAGREG = "<img[^>]+src=[\"'][^\"']+\"[^>]*>";

	/**
	 * html
	 */
	public static final String HTMLTAGREG = "<[\\w]+?\\s*[^><]*\\s*>|<\\s*\\/[\\w]+\\s*>";

	/**
	 * 邮编
	 */
	public static final String POSTCODE = "^[1-9][0-9]{5}$";

	public static final String QOUTREGX = "(\"|&quot;|&quot)";

	/**
	 * 用户登录名：只能输入中文、英文、数字：不以数字开头，长度6~45
	 */
	public static final String LOGIN_NAME = "/^[a-zA-Z][a-zA-Z0-9]{5,17}$";

	/**
	 * 数字
	 */
	public static final String NUMBER = "^[0-9]*$";

	public static final String DECIMALS = "^[0-9]+(.[0-9]{2})?$";

	/**
	 * 字母
	 */
	public static final String LETTER = "^[A-Za-z]+$";

	/**
	 * 大写字母
	 */
	public static final String CAPITAL_LETTER = "^[A-Z]+$";

	/**
	 * 小写字母
	 */
	public static final String LOWERCASE_LETTERS = "^[a-z]+$";

	/**
	 * 英文和数字
	 */
	public static final String NUMBER_LETTERS = "^[A-Za-z0-9]+$";

	/**
	 * 年-月-日 正则表达式
	 */
	public static final String DATE_YYYY_MM_DD = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";

	/**
	 * 全英文和数字
	 */
	public static final String CHECKCODE = "^[A-Za-z0-9]+$";
	/**
	 * 《GB_32100-2015_法人和其他组织统一社会信用代码编码规则.》
	 */
	public static final String TONGYISHEHUIXINYONGDAIMA = "/[0-9A-HJ-NPQRTUWXY]{2}\\d{6}[0-9A-HJ-NPQRTUWXY]{10}/";

	/**
	 * 校验value是否匹配regex所指定的格式
	 * 
	 * @param value
	 * @param regex
	 * @return
	 */
	public static Boolean match(String value, String regex) {
		return Pattern.matches(regex, value);
	}

	/**
	 * 格式化关键词模糊查询str，将空格、中英文逗号统一转换为value
	 * 
	 * @param keyWord
	 * @param value
	 * @return
	 */
	public static String formatKeyWord(String keyWord, String value) {
		if (null == keyWord) {
			return null;
		}
		return keyWord.replaceAll("，+", value).replaceAll("\\s+", value).replaceAll("\"+", "\"").replaceAll(",+", value);
	}

	/**
	 * 解析文本中嵌入的图片链接，嵌入的超链接中文件的参数名为name， 例如
	 * {@code <img src="image/project/1.html?name=12323243.png"/>} 解析后得到的结果为：12323243.png
	 * 
	 * @param content
	 * @return 默认返回值为空字符串
	 */
	public static String findImagNameOfText(String content) {
		if (content == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Matcher ma = Pattern.compile(TEXTAREAIMGREGEX).matcher(content);
		while (ma.find()) {
			sb.append(ma.group().split("=")[1]).append(",");
		}
		if (sb.length() > 0) {
			return sb.substring(0, sb.length() - 1);
		}
		return "";

	}

	public static List<String> getImagNameOfText(String content) {
		List<String> imagesList = new ArrayList<String>(0);

		if (isNotBlank(content)) {
			imagesList = asList(findImagNameOfText(content).split(","));
		}

		return imagesList;
	}

	/**
	 * 删除文本中的html和图片标签，只显示标签中的文字
	 * 
	 * @param content
	 * @return
	 */
	public static String removeImagAndHTMLTag(String content) {
		if (content == null) {
			return null;
		}
		return content.replaceAll(IMGTAGREG, "").replaceAll(HTMLTAGREG, "");

	}

	public static String repaceQout(String content) {
		if (content == null) {
			return null;
		}
		return content.replaceAll(QOUTREGX, "\\\"");

	}
}
