package com.tzg.common.utils;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    /**
     * 号码
     */
    public static final String PHONEREGEX = "^1[0-9]{10}$";

    /**
     * 姓名
     */
    public static final String NAMEREGEX = "[\u4E00-\u9FA5]{2,12}";

    /**
     * 邮箱
     */
    public static final String EMAILREGEX = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$";
    
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
    
    public static final String DECIMALS =  "^[0-9]+(.[0-9]{2})?$";

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
     * {@code <img src="image/project/1.html?name=12323243.png"/>}
     * 解析后得到的结果为：12323243.png
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
