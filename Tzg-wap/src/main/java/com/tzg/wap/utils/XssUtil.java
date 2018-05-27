package com.tzg.wap.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.tzg.common.utils.StringUtil;

public class XssUtil {

    private static String  xssCode        = "javascript:|:expression\\(|<script|<iframe|<frame" + "|<img|%3cscript|%3ciframe|%3cframe|%3cimg|onmouseover%3d|"
                                            + "onmouseover=|3c%2fiframe|3c%2fscript|%3C%2Fa%3E|script>|alert.|<|>|INSERT INTO |UPDATE |DELETE |SELECT |GRANT |DROP ";

    private static Pattern xssCodePattern = Pattern.compile(xssCode, Pattern.CASE_INSENSITIVE);

    public void testXssCode() {
        List<String> params = new ArrayList<String>();

        // js
        params.add("TEST <script type='text/javascript'>window.alert('Test');</script>");
        params.add("TEST <SCRIPT type='text/javascript'>window.alert('Test');</script>");
        params.add("TEST window.alert('Test');");
        params.add("TEST </script>");
        params.add("TEST %3Cscript src='http://google.com/.... ");
        params.add("TEST script%3E");
        params.add("<a href='javascript:'");

        // html
        params.add("TEST <p> <div /> </p>");
        params.add("TEST <img src='http://www.google.com.hk/'>");
        params.add("TEST :expression(document.body.offsetWidth?-?110?+?px);");

        // frame
        params.add("TEST <iframe src='http://google.com/'");
        params.add("TEST </iframe>");

        // SQL
        params.add("TEST delete");
        params.add("TEST DeLete");
        params.add("TEST delete |");
        params.add("TEST DeLete |");
        params.add("TEST SELECT ");
        params.add("TEST DROP ");
        params.add("TEST GRANT ");

        for (String val : params) {
            System.out.println("-----------------------------------");
            System.out.print(val);
            if (hasXssCode(val)) {
                System.out.print(" 含有Xss攻击代码");
            } else {
                System.out.println(" 安全");
            }
            System.out.println();
        }
    }

    /**
     * 判断字符串中是否包含Xss攻击代码
     * 
     * @param strVal
     * 需要
     * @return
     */
    public static boolean hasXssCode(String strVal) {
        if (StringUtil.isEmpty(strVal)) {
            return false;
        }

        Matcher matcher = xssCodePattern.matcher(strVal);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public static String stripXSS(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
        // avoid encoded attacks.
        // value = ESAPI.encoder().canonicalize(value);
        // Avoid null characters
        value = StringUtils.trim(value).replaceAll("", "");
        // Avoid anything between script tags
        Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid anything in a src="http://www.yihaomen.com/article/java/..." type of e­xpression
        scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        // Remove any lonesome </script> tag
        scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");
        // Remove any lonesome <script ...> tag
        scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid eval(...) e­xpressions
        scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid e­xpression(...) e­xpressions
        scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid javascript:... e­xpressions
        scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid vbscript:... e­xpressions
        scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid onload= e­xpressions
        scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        return value;
    }
}
