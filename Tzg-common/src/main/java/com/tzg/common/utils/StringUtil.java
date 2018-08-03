package com.tzg.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 类StringUtil.java的实现描述：TODO 类实现描述 String的util
 * 
 */
public class StringUtil extends StringUtils {

    public StringUtil() {
    }
    
    
	// 判断Object是空
	public static boolean isEmptyObject(Object obj) {
		if (obj == null) {
			return true;
		}
		if ((obj instanceof List)) {
			return ((List) obj).size() == 0;
		}
		if ((obj instanceof String)) {
			return ((String) obj).trim().equals("");
		}
		return false;
	}

	/**
	 * 判断对象不为空
	 * 
	 * @param obj 对象名
	 * @return 是否不为空
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmptyObject(obj);
	}

    /**
     * 手机号隐藏 例 ：158*****5566
     * 
     * @param phone
     * @return
     */
    public static String hidePhone(String phone) {
        if (phone == null || phone.length() < 3) {
            return "";
        }
        return hideStr(3, 4, 4, phone);
    }

    /**
     * 手机号隐藏 例 ：15***666
     * 
     * @param phone
     * @return
     */
    public static String hidePhone2(String phone) {
        if (phone == null || phone.length() < 3) {
            return "";
        }
        return hideStr(2, 3, 3, phone);
    }

    /**
     * 隐藏姓名 例：*三,*德华
     * @param name
     * @return
     */
    public static String hideName(String name) {
        if (isBlank(name)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (name.length() > 3) {
            sb.append(creatHideChar(2)).append(name.substring(2));//复姓
        } else {
            sb.append(creatHideChar(1)).append(name.substring(1));
        }
        return sb.toString();
    }

    /**
     * 隐藏身份证
     * @param cardCode
     * @return
     */
    public static String hideCardCode(String cardCode) {
        if (isBlank(cardCode)) {
            return "";
        }
        return hideStr(6, 4, 8, cardCode);
    }

    /**
     * 隐藏用户名 例：lu***cy,l路***cy
     *
     * @param loginName
     * @return
     */
    public static String hideLoginName(String loginName) {
        if (isBlank(loginName)) {
            return loginName;
        }
        StringBuilder buf = new StringBuilder();
        int len = loginName.length();
        if (len > 5) {
            //return buf.append(loginName.substring(0, 2)).append(creatHideChar(3)).append(loginName.substring(5)).toString();
            return buf.append(loginName.substring(0, 2)).append(creatHideChar(len - 4)).append(loginName.substring(len - 2)).toString();
        }
        if (len <= 2) {
            return loginName;
        }
        if (len > 2 && len <= 5) {
            return buf.append(loginName.substring(0, 2)).append(creatHideChar(len - 2)).toString();
        }
        return "";
    }

    /**
     * 隐藏邮箱
     * @param email
     * @return
     */
    public static String hideEmail(String email) {
        if (isBlank(email)) {
            return "";
        }
        String prefix = email.substring(0, email.indexOf("@"));
        String suffix = email.substring(email.indexOf("@"));
        if (prefix.length() <= 3) {
            return hideStr(1, 0, prefix.length() - 1, prefix) + suffix;
        }
        return hideStr(prefix.length() - 3, 0, 3, prefix) + suffix;
    }

    /**
     * 截取银行卡后4位
     * @param bankCard
     * @return
     */
    public static String hideBankCard(String bankCard) {
        if (isBlank(bankCard)) {
            return "";
        }
        if (bankCard.length() <= 4) {
            return bankCard;
        }
        return bankCard.substring(bankCard.length() - 4);
    }

    /**
     * 生成*字符窜
     * 
     * @param num
     * @return
     */
    public static String creatHideChar(int num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append("*");
            num--;
        }
        return sb.toString();
    }

    /**
     * 隐藏字符的显示
     * 
     * @param preNum
     *            字符前端要显示的字符数
     * @param sufNum
     *            字符后端要显示的字符数
     * @param str
     * @return
     */
    public static String hideStr(int preNum, int sufNum, int hideNum, String str) {
        if (str == null) {
            return str;
        }
        int len = str.length();
        if (preNum + sufNum > len) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        if (sufNum > 0) {
            sb.append(str.substring(0, preNum)).append(creatHideChar(hideNum)).append(str.substring(len - sufNum));
        } else {
            sb.append(str.substring(0, preNum)).append(creatHideChar(hideNum));
        }
        return sb.toString();
    }

    /**
     * 将byte数组转化为16进制
     * 
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1)
                hex = '0' + hex;
            sb.append(hex);
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int strIndex = i << 1;
            int high = Integer.parseInt(hexStr.substring(strIndex, strIndex + 1), 16);
            int low = Integer.parseInt(hexStr.substring(strIndex + 1, strIndex + 2), 16);
            result[i] = (byte) ((high << 4) + low);
        }
        return result;
    }

    /**
     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
     * 
     * @param char c, 需要判断的字符
     * @return boolean, 返回true,Ascill字符
     */
    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     * 
     * @param String
     *            s ,需要得到长度的字符串
     * @return int, 得到的字符串长度
     */
    public static int lengths(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    /**
     * 截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位
     * 
     * @author patriotlml
     * @param String
     *            origin, 原始字符串
     * @param int len, 截取长度(一个汉字长度按2算的)
     * @return String, 返回的字符串
     */
    public static String substring(String origin, int len) {
        if (origin == null || origin.equals("") || len < 1)
            return "";
        byte[] strByte = new byte[len];
        if (len > lengths(origin)) {
            return origin;
        }
        System.arraycopy(origin.getBytes(), 0, strByte, 0, len);
        int count = 0;
        for (int i = 0; i < len; i++) {
            int value = (int) strByte[i];
            if (value < 0) {
                count++;
            }
        }
        if (count % 2 != 0) {
            // len = (len == 1) ? ++len : --len;
            --len;
        }
        return new String(strByte, 0, len);
    }

    /**
     * 用于转换字符类的list为字符串
     * 
     * @param list
     * @param delimt
     * @return
     */
    public static String list2Str(List<Object> list, String delimt) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuffer ids = new StringBuffer();
        for (Object value : list) {
            ids.append(value).append(delimt);
        }
        return ids.substring(0, ids.length() - 1);

    }

    public static boolean sql_Injection(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        String inj_str = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|"
                         + "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|" + "table|from|grant|use|group_concat|column_name|"
                         + "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|"
                         + "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";
        String arr[] = inj_str.split("\\|");
        for (int i = 0; i < arr.length; i++) {
            if (str.indexOf(arr[i]) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * SQL注入参数处理,替换sql关键字的处理方法不是很好很全面，有待优化。
     * XXX 主要用于安全检测误报问题的临时修复方法
     * @author:  heyiwu 
     * @param str
     * @return
     */
    public static String cleanSqlInjection(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        return StringUtils.replace(str, "(^|\\&)|(\\|)|(\\;)|(\\$)|(\\%)|(\\@)|(\\')|(\\\")|(\\>)|(\\<)|(\\))|(\\()|(\\+)|(\\,)|(\\\\)|(\\#|$)", "");
        /*       String arr[] = inj_str.split("\\|");
               for (String s : arr) {
                   str = StringUtils.replace(str, s, "");
               }
               return str;*/
    }

    public static boolean notNumber(String str) {
        try {
            if (str == null || "".equals(str)) {
                return true;
            }
            String arr[] = str.split(",");
            for (int i = 0; i < arr.length; i++) {
                Integer.parseInt(arr[i]);
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    /**
     * 
     *截取字符传 字符，汉字均视为一个字符
     * @param str
     * @param length
     * @return
     */
    public static String cutString(String str, int length) {
        if (str == null || str.equals("") || length < 1)
            return "";
        if (str.length() < length)
            return str;
        //把字符串转换成char类型的数组
        char[] b = str.toCharArray();
        //取数组里的等长度字符
        String s = new String(b, 0, length);

        return s;
    }

    /**
     * 将二进制转化为16进制字符串
     * 
     * @param bytes
     *            二进制字节数组,由字符串转换而来："".getBytes(charset)
     *            编码、加密时因操作系统实现不同会有所差异，所以转换时最好指定字符集
     */
    public static String toHexString(byte[] bytes) {
        if (null == bytes || bytes.length == 0) {
            return null;
        }
        int len = bytes.length;
        StringBuilder sb = new StringBuilder(len * 2);
        for (int n = 0; n < len; n++) {
            String hex = (java.lang.Integer.toHexString(bytes[n] & 0XFF));
            sb.append(((hex.length() == 1 ? "0" : "") + hex).toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 十六进制字符串转化为2进制
     * 
     * @param hex
     *            十六进制字符串
     * @return
     */
    public static byte[] hex2byte(String hex) {
        if (StringUtils.isBlank(hex)) {
            return null;
        }
        int len = hex.length() / 2;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            int high = Integer.parseInt(hex.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hex.substring(i * 2 + 1, i * 2 + 2), 16);
            bytes[i] = (byte) (high * 16 + low);
        }
        return bytes;
    }
	/**
	 * @Description: 加密银行卡号码 保留后4位，倒数第5位加入一个标志符 用来判断客户端传来的银行卡是否需要解码
	 * @param bankcard 银行卡号
	 * @return String
	 */
	public static String encodeBankCard(String bankcard) {
		if (null == bankcard || bankcard =="") {
			return "银行卡号为空";
		}else if( bankcard.length() < 4){
			return "银行卡号格式错误";
		}

		String lastFourDigit = bankcard.substring(bankcard.length()-4,bankcard.length());
		String specialChar = "S";
		String encryptStr = bankcard.substring(0,bankcard.length()-4) + specialChar ;
		encryptStr = com.tzg.common.bbs.util.Base64.base64_encode(encryptStr);
		return encryptStr + lastFourDigit;
		
	}
	
	/**
	 * 
	* @Description: 解码银行卡号 后四位为真实数字
	* @param token
	* @return    
	* String
	 */
	public static String decodeBankCard(String token) {
		String  bankcard=null;
		try {
			Assert.isBlank(token, "银行卡号为空");
			
		 bankcard =	 com.tzg.common.bbs.util.Base64.base64_decode(token);
			
			
		} catch (Exception e) {
		}
		return bankcard;
	}
    
  	 /**
     * 
     * @Description: 将string转成List集合
     * @param param 字符串参数(必须以","分割，只按","分割处理，如：1,2,3)
     * @return 字符串集合
     */
    public static List<String> stringToList(String param) {
        //参数格式
    	if(StringUtil.notNumber(param)){
    		return null;
    	}
        // 分割字符串为list
        List<String> res = Arrays.asList(param.split(","));
        return res;
    }
    
    /**2个BigDecimal相乘，四舍五入保留2位小数*/
	public static BigDecimal mvtilBigDecimal(BigDecimal d1,BigDecimal d2){
		DecimalFormat df = new DecimalFormat("0.00"); // 保留2位小数
		BigDecimal dd = d1.multiply(d2);
		if(dd.compareTo(new BigDecimal("0"))==0||
				dd.compareTo(new BigDecimal("0.0"))==0||
				dd.compareTo(new BigDecimal("0.00"))==0){
			return new BigDecimal("0.00");
		}
		dd.divide(dd, 2, BigDecimal.ROUND_HALF_UP);
		return new BigDecimal(df.format(dd));
	}
	
	/**2个BigDecimal相除，四舍五入保留2位小数*/
	public static BigDecimal divBigDecimal(BigDecimal d1,BigDecimal d2){
		DecimalFormat df = new DecimalFormat("0.00"); // 保留2位小数
		BigDecimal dd = d1.divide(d2,2, BigDecimal.ROUND_HALF_UP);
		if(dd.compareTo(new BigDecimal("0"))==0||
				dd.compareTo(new BigDecimal("0.0"))==0||
				dd.compareTo(new BigDecimal("0.00"))==0){
			return new BigDecimal("0.00");
		}
		return new BigDecimal(df.format(dd));
	}
	
	/**2个BigDecimal相减，四舍五入保留2位小数*/
	public static BigDecimal subBigDecimal(BigDecimal d1,BigDecimal d2){
		DecimalFormat df = new DecimalFormat("0.00"); // 保留2位小数
		BigDecimal dd = d1.subtract(d2);
		if(dd.compareTo(new BigDecimal("0"))==0||
				dd.compareTo(new BigDecimal("0.0"))==0||
				dd.compareTo(new BigDecimal("0.00"))==0){
			return new BigDecimal("0.00");
		}
		dd.divide(dd, 2, BigDecimal.ROUND_HALF_UP);
		return new BigDecimal(df.format(dd));
	}
	
	/**2个BigDecimal相加，四舍五入保留2位小数*/
	public static BigDecimal addBigDecimal(BigDecimal d1,BigDecimal d2){
		DecimalFormat df = new DecimalFormat("0.00"); // 保留2位小数
		BigDecimal dd = d1.add(d2);
		if(dd.compareTo(new BigDecimal("0"))==0||
				dd.compareTo(new BigDecimal("0.0"))==0||
				dd.compareTo(new BigDecimal("0.00"))==0){
			return new BigDecimal("0.00");
		}
		dd.divide(dd, 2, BigDecimal.ROUND_HALF_UP);
		return new BigDecimal(df.format(dd));
	}
}
