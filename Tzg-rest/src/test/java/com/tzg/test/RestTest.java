package com.tzg.test;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.velocity.tools.config.Data;

import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.rest.utils.DateUtil;
import com.tzg.rest.utils.PolicyUtil;

public class RestTest {

	/** 
	 * @Title: main 
	 * @Description: TODO  
	 * @param @param args    
	 * @return void
	 * @see    
	 * @throws 
	 */
	public static void main(String[] args){
	
		/*System.out.println("token:"+AccountTokenUtil.getAccountToken(8669));
		String policy = "";
		System.out.println("encrypted:"+PolicyUtil.encryptPolicy(policy));
		
		BigDecimal amount = BigDecimal.valueOf(1000000);
		System.out.println(formatWanForApp(amount));
		baseTest();*/
		
	//	DateUtil.isToday(new Date().getTime()/1000);
		
		
	}


	public static void baseTest() {
		String s = "123456";
		System.out.println(s.substring(s.length()-4));
		
		String v1="1";
		String v2="2";
		System.out.println(v1.compareTo(v2));
		List<Integer> a = new ArrayList<Integer>(){{
			add(1);add(2);add(3);
		}};
		int i=0;
		String ss="";
		for(Integer ai:a){
			ss+=ai;
			if(i<a.size()-1){
				ss+=",";
			}
			i++;
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(ss);
		String s23="/opt/123/classes/com/a";
		System.out.println(s23.substring(0,s23.indexOf("classes")+8));
		
		
		String intro = "铜掌柜平台予以审核通过。<img src=\"/upload/201503/20150316115309932915.png\"/></span></p><p ";
		String result = intro.replaceAll("(.*?)src=\"(.*?)", "$1src=\"http://127.0.0.1:8088$2");
		System.out.println(result);
		//String uclogin = "http://192.168.2.90/bbs/api/uc.php?time=1434623035&code=9926sKVCJSMwtRBOctrYikt50YbOWcW9ZyeLOSXLEPt1992WwQQXwqMf13R1djfh6gpW%2Bgl4xLhXRJnnme5SSneR7SaD3S7Bia2rGYbkuwRRp6LGlw8WPe9wbLg7jSB1mvn%2BEFAzdxoJf%2F%2FYYH6tL8tAxhj12SPbt%2BH2MwXxAn54";
		//System.out.println(uclogin.substring(uclogin.indexOf("code")+5));
		
		String js = "<script type=\"text/javascript\" src=\"http://192.168.2.90/bbs/api/uc.php?time=1434626754&code=23f7AAyUqdMDiojso8XEqte9KFZVVOkZ1GloWRG2CPZBAQehENQ7Me7cM5s%2BIUmiPKEopU4VUA7XHO8nI8XbZdEnRkikBkUakEthrcd4HDqINFxQ7KWoTv0JRbhVtNDIVfyYPaaOEM%2Bzn%2BKaurI1VAs1A38DaLsMHC%2BNbUvuJfER\" reload=\"1\"></script>";
		String pattern = "src=\"(.+?)\"";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(js);
		while(m.find()){
			System.out.println(m.group(1));
		}

//	    String pattern = "src=\"(.+?)\"";
//		Pattern p = Pattern.compile(pattern);
//		Matcher m = p.matcher($ucsynlogin);
//		String apiToken = "";
//		while(m.find()){
//			apiToken = m.group(1);
//		}
		
		
	}
	
		

    
    public static String formatWanForApp(BigDecimal val){
    	String unit = "元";
    	if (val == null) {
            return "0.00"+unit;
        }
    	if(val.compareTo(new BigDecimal(10000))>=0){
    		unit = "万";
    		val = val.divide(new BigDecimal(10000));
    	}
    	NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(6); //设置数值的小数部分允许的最大位数。
        format.setMinimumFractionDigits(2); //设置数值的小数部分允许的最小位数。
        format.setMaximumIntegerDigits(10); //设置数值的整数部分允许的最大位数。 
        format.setMinimumIntegerDigits(1); //设置数值的整数部分允许的最小位数.
    	return format.format(val.doubleValue())+unit;
    }
}
