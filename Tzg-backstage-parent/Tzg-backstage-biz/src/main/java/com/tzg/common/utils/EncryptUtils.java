package com.tzg.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * 
 * 项目名称：cms-web     
 * 类名称：EncryptUtil     
 * 类描述：     md5 sha1 hash校验算法
 * 创建人：daringyun@gmail.com   
 * 创建时间：2013-11-20 下午6:37:30     
 * 修改人：daringyun@gmail.com    
 * 修改时间：2013-11-20 下午6:37:30     
 * 修改备注：     
 * @version
 */
public class EncryptUtils {
	protected static Logger logger = Logger.getLogger(EncryptUtils.class);
	public static void main(String[] args) {
		//md5加密测试
		String md5_1 = md5("123");
		String md5_2 = md5("abc");
		System.out.println(md5_1 + "\n" + md5_2);
		System.out.println("md5 length: " + md5_1.length());
		//sha加密测试
		String sha_1 = sha("1");
		String sha_2 = sha("abc");
		System.out.println(sha_1 + "\n" + sha_2);
		System.out.println("sha length: " + sha_1.length());
		
		System.out.println(sha256("1"));
	}

	// md5加密
	public static String md5(String inputText) {
		return encrypt(inputText, "md5");
	}

	// sha加密
	public static String sha(String inputText) {
		return encrypt(inputText, "sha-1");
	}
	
	// sha加密
	public static String sha256(String inputText) {
		return encrypt(inputText, "sha-256");
	}

	/**
	 * md5或者sha-1加密
	 * 
	 * @param inputText
	 *            要加密的内容
	 * @param algorithmName
	 *            加密算法名称：不区分大小写
	 *            参考：<a href="http://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#MessageDigest">MessageDigest</a>
	 *            
	 * @return
	 */
	private static String encrypt(String inputText, String algorithmName) {
		if (inputText == null || "".equals(inputText.trim())) {
			throw new IllegalArgumentException("请输入要加密的内容");
		}
		if (algorithmName == null || "".equals(algorithmName.trim())) {
			algorithmName = "md5";
		}
		String encryptText = null;
		try {
			MessageDigest m = MessageDigest.getInstance(algorithmName);
			m.update(inputText.getBytes("UTF8"));
			byte s[] = m.digest();
			// m.digest(inputText.getBytes("UTF8"));
			return hex(s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptText;
	}

	// 返回十六进制字符串
	private static String hex(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1,3));
		}
		return sb.toString();
	}
	
	  /**
		  * 适用于上G大的文件
		  */
		  public static String getFileSha1(String path) throws OutOfMemoryError,IOException {
			  File file=new File(path);
		      FileInputStream in = new FileInputStream(file);
		     MessageDigest messagedigest;
			try {
				messagedigest = MessageDigest.getInstance("SHA-1");
			
		     byte[] buffer = new byte[1024 * 1024 * 10];
		     int len = 0;
		     
		     while ((len = in.read(buffer)) >0) {
		    //该对象通过使用 update（）方法处理数据
		      messagedigest.update(buffer, 0, len);
		     }
		    
		   //对于给定数量的更新数据，digest 方法只能被调用一次。在调用 digest 之后，MessageDigest 对象被重新设置成其初始状态。
		     return hex(messagedigest.digest());
			}   catch (NoSuchAlgorithmException e) {
				logger.info("getFileSha1->NoSuchAlgorithmException###"+e.toString());
					e.printStackTrace();
				}
			catch (OutOfMemoryError e) {
				logger.info("getFileSha1->OutOfMemoryError###"+ e.toString());
					e.printStackTrace();
					throw e;
				}
			finally{
				 in.close();
			}
		     return null;
		  }
		  
		  public static String getFileSha1(File file) throws OutOfMemoryError,IOException {
		      FileInputStream in = new FileInputStream(file);
		     MessageDigest messagedigest;
			try {
				messagedigest = MessageDigest.getInstance("SHA-1");
			
		     byte[] buffer = new byte[1024 * 1024 * 10];
		     int len = 0;
		     
		     while ((len = in.read(buffer)) >0) {
		    //该对象通过使用 update（）方法处理数据
		      messagedigest.update(buffer, 0, len);
		     }
		    
		   //对于给定数量的更新数据，digest 方法只能被调用一次。在调用 digest 之后，MessageDigest 对象被重新设置成其初始状态。
		     return hex(messagedigest.digest());
			}   catch (NoSuchAlgorithmException e) {
				logger.info("getFileSha1->NoSuchAlgorithmException###"+e.toString());
					e.printStackTrace();
				}
			catch (OutOfMemoryError e) {
				logger.info("getFileSha1->OutOfMemoryError###"+ e.toString());
					e.printStackTrace();
					throw e;
				}
			finally{
				 in.close();
			}
		     return null;
		  }
		  
			
}