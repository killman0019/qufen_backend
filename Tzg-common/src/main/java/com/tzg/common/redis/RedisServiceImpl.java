package com.tzg.common.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.tzg.common.utils.SerializeUtil;

/**
 * redis客户端接口实现
 * 
 * @author yangpeiliang
 * @version 1.0
 */
public class RedisServiceImpl implements RedisService {

	protected static Logger logger = Logger.getLogger(RedisServiceImpl.class);

	private int maxActive;

	private int maxIdle;

	private int maxWait;

	private String masterHost;

	private String salveHost;

	private JedisPool jedisPool = null;// 非切片连接池

	private ShardedJedisPool shardedJedisPool = null;// 切片连接池

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzg.common.redis.RedisService#put(java.lang.String, java.lang.String, int)
	 */
	@Override
	public void put(String key, String value, int seconds) throws Exception {
		if (key==null || "".equals(key) || value==null || "".equals(value)) {
            return;
        }
		Jedis jedis = null;// 非切片额客户端连接
		try {
			jedisPool = this.initialPool();
			jedis = jedisPool.getResource();
			jedis.set(key, value);
			if(seconds != 0) {
				jedis.expire(key, seconds);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != jedis) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzg.common.redis.RedisService#get(java.lang.String)
	 */
	@Override
	public String get(String key) throws Exception {
		String res = "";
		Jedis shardedJedis = null;// 切片额客户端连接
		try {
			jedisPool = this.initialPool();
			shardedJedis = jedisPool.getResource();
			res = shardedJedis.get(key);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				jedisPool.returnResource(shardedJedis);
			}
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzg.common.redis.RedisService#putMap(java.lang.String, java.util.Map, int)
	 */
	@Override
	public void putMap(String key, Map<String, String> map, int seconds) throws Exception {
		if(key==null || "".equals(key) || null == map || map.size()==0){
			 return;
		}
		Jedis jedis = null;
		try {
			jedisPool = this.initialPool();
			jedis = jedisPool.getResource();
			jedis.hmset(key, map);
			if(seconds != 0) {
				jedis.expire(key, seconds);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != jedis) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzg.common.redis.RedisService#getMap(java.lang.String)
	 */
	@Override
	public Map<String, String> getMap(String key) throws Exception {
		Jedis shardedJedis = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			jedisPool = this.initialPool();
			shardedJedis = jedisPool.getResource();
			map = shardedJedis.hgetAll(key);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				jedisPool.returnResource(shardedJedis);
			}
		}
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzg.common.redis.RedisService#putList(java.lang.String, java.util.List, int)
	 */
	@Override
	public void putList(String key, List<?> list, int seconds) throws Exception {
		if(key==null || "".equals(key) || null == list || list.size()==0){
			 return;
		}
		Jedis jedis = null;
		try {
			jedisPool = this.initialPool();
			jedis = jedisPool.getResource();
			jedis.set(key.getBytes(), SerializeUtil.serialize(list));
			if(seconds != 0) {
				jedis.expire(key.getBytes(), seconds);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != jedis) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzg.common.redis.RedisService#getList(java.lang.String)
	 */
	@Override
	public List<?> getList(String key) throws Exception {
		//ShardedJedis shardedJedis = null;
		Jedis shardedJedis = null;
		List<?> list = null;
		try {
			//shardedJedisPool = initialShardedPool();
			jedisPool = this.initialPool();
			shardedJedis = jedisPool.getResource();
			byte[] in = shardedJedis.get(key.getBytes());
			if (null != in) {
				list = (List<?>) SerializeUtil.deserialize(in);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				jedisPool.returnResource(shardedJedis);
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzg.common.redis.RedisService#putObj(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public void putObj(String key, Object obj, int seconds) throws Exception {
		if (key==null || "".equals(key) || obj==null ) {
            return;
        }
		Jedis jedis = null;
		try {
			jedisPool = this.initialPool();
			jedis = jedisPool.getResource();
			jedis.set(key.getBytes(), SerializeUtil.serialize(obj));
			if(seconds != 0) {
				jedis.expire(key.getBytes(), seconds);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != jedis) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzg.common.redis.RedisService#getObj(java.lang.String)
	 */
	@Override
	public Object getObj(String key) throws Exception {
		Jedis shardedJedis = null;
		Object obj = new Object();
		try {
			jedisPool = this.initialPool();
			shardedJedis = jedisPool.getResource();
			byte[] in = shardedJedis.get(key.getBytes());
			if (null != in) {
				obj = SerializeUtil.unserialize(in);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				jedisPool.returnResource(shardedJedis);
			}
		}
		return obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzg.common.redis.RedisService#del(java.lang.String)
	 */
	@Override
	public void del(String key) throws Exception {
		Jedis jedis = null;
		try {
			jedisPool = this.initialPool();
			jedis = jedisPool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != jedis) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzg.common.redis.RedisService#flush()
	 */
	@Override
	public void flush() throws Exception {
		Jedis jedis = null;
		try {
			jedisPool = this.initialPool();
			jedis = jedisPool.getResource();
			jedis.flushDB();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != jedis) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tzg.common.redis.RedisService#exists(java.lang.String)
	 */
	@Override
	public boolean exists(String key) throws Exception {
		Jedis shardedJedis = null;
		boolean result = false;
		try {
			jedisPool = this.initialPool();
			shardedJedis = jedisPool.getResource();
			if(shardedJedis.exists(key)){
				result = true;
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				jedisPool.returnResource(shardedJedis);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.tzg.common.redis.RedisService#keys(java.lang.String)
	 */
	@Override
	public Set<?> keys(String key) throws Exception {
		Jedis jedis = null;// 非切片额客户端连接
		Set<String> res = null;
		try {
			jedisPool = this.initialPool();
			jedis = jedisPool.getResource();
			res = jedis.keys(key);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != jedis) {
				jedisPool.returnResource(jedis);
			}
		}
		return res;
	}

	/**
	 * 初始化切片池
	 */
	private ShardedJedisPool initialShardedPool() {
		if (null == shardedJedisPool) {
			// 池基本配置
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxActive(maxActive);
			config.setMaxIdle(maxIdle);
			config.setMaxWait(maxWait);
			config.setTestOnBorrow(false);
			List<JedisShardInfo> shards = procJedisShard(salveHost);
			// 构造池
			shardedJedisPool = new ShardedJedisPool(config, shards);
		}
		return shardedJedisPool;
	}

	/**
	 * 初始化非切片池
	 */
	private JedisPool initialPool() {
		if (null == jedisPool) {
			// 池基本配置
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxActive(maxActive);
			config.setMaxIdle(maxIdle);
			config.setMaxWait(maxWait);
			config.setTestOnBorrow(false);
			jedisPool = new JedisPool(config, masterHost.split(":")[0], Integer.parseInt(masterHost.split(":")[1]));

		}
		return jedisPool;
	}

	/**
	 * redis集群配置
	 * 
	 * @param redisConnectInfo
	 * @return
	 */
	private List<JedisShardInfo> procJedisShard(String redisSlave) {
		List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>();
		if (!StringUtils.isEmpty(redisSlave)) {
			String[] ipAndPort = redisSlave.split(",");
			for (String ips : ipAndPort) {
				String[] ip = ips.split(":");
				JedisShardInfo jedisShardInfo = new JedisShardInfo(ip[0], Integer.parseInt(ip[1]), "master");
				jdsInfoList.add(jedisShardInfo);
			}
		}
		return jdsInfoList;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public String getMasterHost() {
		return masterHost;
	}

	public void setMasterHost(String masterHost) {
		this.masterHost = masterHost;
	}

	public String getSalveHost() {
		return salveHost;
	}

	public void setSalveHost(String salveHost) {
		this.salveHost = salveHost;
	}
}
