package com.tzg.rest.utils;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tzg.rest.constant.RestRequestHead;

/**
 * rest 字符串相关工具类
 * @author Administrator
 *
 */
public class RestStringUtils {

    private static Logger logger = LoggerFactory.getLogger(RestStringUtils.class);

    /***
     * 获取UUID
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }


    /***
     * 校验密码是否符合规则
     * @param password
     * @return
     */
    public static boolean verifyPassword(String password) {
        String regPassword = "[a-zA-Z0-9]{6,20}";
        if (StringUtils.isNotEmpty(password)) {
            return password.matches(regPassword);
        }
        return false;
    }

    /**
     * 判断是否是手机号
     *
     * @param mobilePhone 移动号段：134 135 136 137 138 139 147 150 151 152 157 158 159 178 182 183 184 187 188
     *                联通号段：130 131 132 145 155 156 176 185 186
     *                电信号段：133 153 177 180 181 189
     * @return
     */
    public static boolean verifyMobilePhone(String mobilePhone) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[6,7,8])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobilePhone);
        return m.matches();
    }

    /***
     * 校验邮箱是否符合规则
     * @param email
     * @return
     */
    public static boolean verifyEmail(String email) {
        String regEmail = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        if (StringUtils.isNotEmpty(email)) {
            return email.matches(regEmail);
        }
        return false;
    }

  

    /***
     * 获取文件完整URL
     * @param url
     * @param objs
     * @return
     */
    private static String getFileUrl(String url, Object ... objs) {
        return MessageFormat.format(url, objs);
    }


    /***
     * 根据毫秒数生成 yyyy-MM-dd HH:mm:ss格式的字符串
     * @param date
     * @return
     */
    public static String getDateStrByTime(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        }
        return "";
    }

    /***
     * 按照格式生成时间
     * @param dateStr
     * @param format
     * @return
     */
    public static Date getDateByStr(String dateStr, String format) {
        try {
            if (StringUtils.isNotBlank(dateStr)) {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.parse(dateStr);
            }
        } catch (ParseException e) {
            logger.error("日期格式错误", e);
        }
        return null;
    }

   

  

    /***
     * 用户当前app版本是否大于某版本
     * @param userAppVersion
     * @param version
     * @return
     */
    public static boolean isAfterVersion(String userAppVersion, String version) {
        if (StringUtils.isEmpty(userAppVersion)) return false;
        if (userAppVersion.compareToIgnoreCase(version) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /***
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    public static String getFilePostfix(String fileName) {
        if (StringUtils.isNotEmpty(fileName) && fileName.lastIndexOf(".") > -1) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(isAfterVersion("v2.4.2", "v2.4.2"));
        System.out.println(getFileUrl("ef804e42a3674287592845b5772d4df0", "avi"));

        String url = "http://www.xueleyun.com/appcenter/magic2/work/statistics/tagvm?tagId={0}";

        System.out.println(getFileUrl(url,"sss"));


    }
}
