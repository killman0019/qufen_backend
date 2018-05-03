package com.tzg.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 对象转换
 * 
 * @author yangpeiliang
 * 
 */
public class SerializeUtil {

	/**
	 * List转换为byte[]
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] serialize(List<?> value) {
		if (value == null) {
			throw new NullPointerException("Can't serialize null");
		}
		byte[] rv = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream os = null;
		try {
			bos = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bos);
			for (Object obj : value) {
				os.writeObject(obj);
			}
			os.writeObject(null);
			os.close();
			bos.close();
			rv = bos.toByteArray();
		} catch (IOException e) {
			throw new IllegalArgumentException("Non-serializable object", e);
		} finally {
			close(os);
			close(bos);
		}
		return rv;
	}

	/**
	 * byte[]转换为List
	 * 
	 * @param in
	 * @return
	 */
	public static List<?> deserialize(byte[] in) {
		List<Object> list = new ArrayList<Object>();
		ByteArrayInputStream bis = null;
		ObjectInputStream is = null;
		try {
			if (in != null) {
				bis = new ByteArrayInputStream(in);
				is = new ObjectInputStream(bis);
				while (true) {
					Object obj = (Object) is.readObject();
					if (obj == null) {
						break;
					} else {
						list.add(obj);
					}
				}
				is.close();
				bis.close();
			}
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		} finally {
			close(is);
			close(bis);
		}
		return list;
	}

	/**
	 * Object转换为byte[]
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
		} finally {
			close(oos);
			close(baos);
		}
		return null;
	}

	/**
	 * byte[]转换为Object
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
		} finally {
			close(ois);
			close(bais);
		}
		return null;
	}

	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
			}
		}
	}
}
