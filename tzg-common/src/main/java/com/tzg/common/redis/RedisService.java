package com.tzg.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis客户端接口
 * 
 * @author yangpeiliang
 * @version 1.0
 */
public interface RedisService {

	/**
	 * 存放字符串
	 * 
	 * @param key
	 *            唯一标识
	 * @param value
	 *            存放的字符串
	 * @param seconds
	 *            失效时间
	 */
	public void put(String key, String value, int seconds) throws Exception;

	/**
	 * 取字符串
	 * 
	 * @param key
	 *            唯一标识
	 * @return
	 */
	public String get(String key) throws Exception;

	/**
	 * 存放map
	 * 
	 * @param key
	 *            唯一标识
	 * @param map
	 *            存放的集合
	 * @param seconds
	 *            失效时间
	 */
	public void putMap(String key, Map<String, String> map, int seconds) throws Exception;

	/**
	 * 取key对应的map集合
	 * 
	 * @param key
	 *            唯一标识
	 * @return
	 */
	public Map<String, String> getMap(String key) throws Exception;

	/**
	 * 存放List
	 * 
	 * @param key
	 *            唯一标识
	 * @param list
	 *            存放的集合
	 * @param seconds
	 *            失效时间
	 */
	public void putList(String key, List<?> list, int seconds) throws Exception;

	/**
	 * 取key对应的List集合
	 * 
	 * @param key
	 *            唯一标识
	 * @return
	 */
	public List<?> getList(String key) throws Exception;

	/**
	 * 存放Object
	 * 
	 * @param key
	 *            唯一标识
	 * @param obj
	 *            存放的对象
	 * @param seconds
	 *            失效时间
	 */
	public void putObj(String key, Object obj, int seconds) throws Exception;

	/**
	 * 取key对应的Object
	 * 
	 * @param key
	 *            唯一标识
	 * @return
	 */
	public Object getObj(String key) throws Exception;

	/**
	 * 清空Key数据
	 * 
	 * @param key
	 *            唯一标识
	 */
	public void del(String key) throws Exception;

	/**
	 * 清空所有数据
	 */
	public void flush() throws Exception;
	
	/**
	 * 判断key是否在redis中存在
	 */
	public boolean exists(String key) throws Exception;
	
	/**
	 * 模糊查询redis中的key
	 * KEYS * 匹配数据库中所有 key 。
	 * KEYS h?llo 匹配 hello ， hallo 和 hxllo 等。
	 * KEYS h*llo 匹配 hllo 和 heeeeello 等。
	 * KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。
	 */
	public Set<?> keys(String key) throws Exception;
}
