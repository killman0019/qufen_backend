package com.tzg.common.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 鍩轰簬aes鍔犲瘑瑙ｅ瘑宸ュ叿绫�
 * 
 * @author yangpeiliang
 * @version 1.0
 */
public class Aes {

	public static final String DEFAL_AESKEY = "tzgapp";

	/**
	 * 灏哹yte[]杞负鍚勭杩涘埗鐨勫瓧绗︿覆
	 * 
	 * @param bytes
	 *            byte[]
	 * @param radix
	 *            鍙互杞崲杩涘埗鐨勮寖鍥达紝浠嶤haracter.MIN_RADIX鍒癈haracter.MAX_RADIX锛岃秴鍑鸿寖鍥村悗鍙樹负10杩涘埗
	 * @return 杞崲鍚庣殑瀛楃涓�
	 */
	public static String binary(byte[] bytes, int radix) {
		return new BigInteger(1, bytes).toString(radix);// 杩欓噷鐨�1浠ｈ〃姝ｆ暟
	}

	/**
	 * base 64 encode
	 * 
	 * @param bytes
	 *            寰呯紪鐮佺殑byte[]
	 * @return 缂栫爜鍚庣殑base 64 code
	 */
	public static String base64Encode(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	/**
	 * base 64 decode
	 * 
	 * @param base64Code
	 *            寰呰В鐮佺殑base 64 code
	 * @return 瑙ｇ爜鍚庣殑byte[]
	 * @throws Exception
	 */
	public static byte[] base64Decode(String base64Code) throws Exception {
		// return StringUtil.isEmpty(base64Code) ? null : new BASE64Decoder()
		// .decodeBuffer(base64Code);
		return null;
	}

	/**
	 * 鑾峰彇byte[]鐨刴d5鍊�
	 * 
	 * @param bytes
	 *            byte[]
	 * @return md5
	 * @throws Exception
	 */
	public static byte[] md5(byte[] bytes) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(bytes);
		return md.digest();
	}

	/**
	 * 鑾峰彇瀛楃涓瞞d5鍊�
	 * 
	 * @param msg
	 * @return md5
	 * @throws Exception
	 */
	public static byte[] md5(String msg) throws Exception {
		return StringUtil.isEmpty(msg) ? null : md5(msg.getBytes());
	}

	/**
	 * 缁撳悎base64瀹炵幇md5鍔犲瘑
	 * 
	 * @param msg
	 *            寰呭姞瀵嗗瓧绗︿覆
	 * @return 鑾峰彇md5鍚庤浆涓篵ase64
	 * @throws Exception
	 */
	public static String md5Encrypt(String msg) throws Exception {
		return StringUtil.isEmpty(msg) ? null : base64Encode(md5(msg));
	}

	/**
	 * AES鍔犲瘑
	 * 
	 * @param content
	 *            寰呭姞瀵嗙殑鍐呭
	 * @param encryptKey
	 *            鍔犲瘑瀵嗛挜
	 * @return 鍔犲瘑鍚庣殑byte[]
	 * @throws Exception
	 */
	public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(encryptKey.getBytes()));
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		return cipher.doFinal(content.getBytes("utf-8"));
	}

	/**
	 * AES鍔犲瘑涓篵ase 64 code
	 * 
	 * @param content
	 *            寰呭姞瀵嗙殑鍐呭
	 * @param encryptKey
	 *            鍔犲瘑瀵嗛挜
	 * @return 鍔犲瘑鍚庣殑base 64 code
	 * @throws Exception
	 */
	public static String aesEncrypt(String content, String encryptKey) throws Exception {
		return base64Encode(aesEncryptToBytes(content, encryptKey));
	}

	/**
	 * AES瑙ｅ瘑
	 * 
	 * @param encryptBytes
	 *            寰呰В瀵嗙殑byte[]
	 * @param decryptKey
	 *            瑙ｅ瘑瀵嗛挜
	 * @return 瑙ｅ瘑鍚庣殑String
	 * @throws Exception
	 */
	public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(decryptKey.getBytes()));
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encryptBytes);
		return new String(decryptBytes);
	}

	/**
	 * 灏哹ase 64 code AES瑙ｅ瘑
	 * 
	 * @param encryptStr
	 *            寰呰В瀵嗙殑base 64 code
	 * @param decryptKey
	 *            瑙ｅ瘑瀵嗛挜
	 * @return 瑙ｅ瘑鍚庣殑string
	 * @throws Exception
	 */
	public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
		try {
			return StringUtil.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] AES_CBC_Decrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
		Cipher cipher = getCipher(Cipher.DECRYPT_MODE, key, iv);
		return cipher.doFinal(data);
	}

	private static Cipher getCipher(int mode, byte[] key, byte[] iv) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		// 鍥犱负AES鐨勫姞瀵嗗潡澶у皬鏄�128bit(16byte), 鎵�浠ey鏄�128銆�192銆�256bit鏃犲叧
		// System.out.println("cipher.getBlockSize()锛� " + cipher.getBlockSize());

		SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
		cipher.init(mode, secretKeySpec, new IvParameterSpec(iv));

		return cipher;
	}

	public static void main(String[] args) throws Exception {
		try {
			String content = "82D7DDC4-DD08-4461-9B30-D8240B965F66";
			System.out.println("鍔犲瘑鍓嶏細" + content);

			String key = "123456";
			System.out.println("鍔犲瘑瀵嗛挜鍜岃В瀵嗗瘑閽ワ細" + key);

			String encrypt = aesEncrypt(content, key);
			System.out.println("鍔犲瘑鍚庯細" + encrypt);

			String decrypt = aesDecrypt(encrypt, key);
			System.out.println("瑙ｅ瘑鍚庯細" + decrypt);
		} catch (Exception e) {
			System.out.println("dd=====" + e);
		}
	}
}