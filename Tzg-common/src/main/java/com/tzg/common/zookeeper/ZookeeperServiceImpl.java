package com.tzg.common.zookeeper;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * Zookeeper客户端接口实现
 * 
 * @author yangpeiliang
 * @version 1.0
 */
public class ZookeeperServiceImpl implements ZookeeperService {

	protected static Logger logger = Logger.getLogger(ZookeeperServiceImpl.class);

	public ZooKeeper zooKeeper = null;

	private static final int SESSION_TIME_OUT = 2000;

	private CountDownLatch countDownLatch = new CountDownLatch(1);

	private String zookeeperHosts;

	/* (non-Javadoc)
	 * @see com.tzg.common.zookeeper.ZookeeperService#zooKeeperConnection()
	 */
	@Override
	public ZooKeeper zooKeeperConnection() throws Exception {
		zooKeeper = new ZooKeeper(zookeeperHosts, SESSION_TIME_OUT, this);
		countDownLatch.await();
		return zooKeeper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.zookeeper.Watcher#process(org.apache.zookeeper.WatchedEvent)
	 */
	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected) {
			System.out.println("watcher received event");
			countDownLatch.countDown();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzg.common.zookeeper.ZookeeperService#createNode(java.lang.String, byte[])
	 */
	@Override
	public String createNode(String path, byte[] data) throws Exception {
		String res = null;
		try {
			zooKeeper = this.zooKeeperConnection();
			res = zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != zooKeeper) {
				zooKeeper.close();
			}
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzg.common.zookeeper.ZookeeperService#getChildren(java.lang.String)
	 */
	@Override
	public List<String> getChildren(String path) throws Exception {
		List<String> resList = null;
		try {
			zooKeeper = this.zooKeeperConnection();
			resList = zooKeeper.getChildren(path, false);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != zooKeeper) {
				zooKeeper.close();
			}
		}
		return resList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzg.common.zookeeper.ZookeeperService#setData(java.lang.String, byte[], int)
	 */
	@Override
	public Stat setData(String path, byte[] data, int version) throws Exception {
		Stat stat = null;
		try {
			zooKeeper = this.zooKeeperConnection();
			stat = zooKeeper.setData(path, data, version);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != zooKeeper) {
				zooKeeper.close();
			}
		}
		return stat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzg.common.zookeeper.ZookeeperService#getData(java.lang.String)
	 */
	@Override
	public byte[] getData(String path) throws Exception {
		byte[] res = null;
		try {
			zooKeeper = this.zooKeeperConnection();
			res = zooKeeper.getData(path, false, null);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != zooKeeper) {
				zooKeeper.close();
			}
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzg.common.zookeeper.ZookeeperService#deletNode(java.lang.String, int)
	 */
	@Override
	public void deletNode(String path, int version) throws Exception {
		try {
			zooKeeper = this.zooKeeperConnection();
			zooKeeper.delete(path, version);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != zooKeeper) {
				zooKeeper.close();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzg.common.zookeeper.ZookeeperService#isExists(java.lang.String, boolean)
	 */
	@Override
	public boolean isExists(String path, boolean watch) throws Exception {
		Boolean res = Boolean.FALSE;
		try {
			zooKeeper = this.zooKeeperConnection();
			Stat stat = zooKeeper.exists(path, false);
			if (null != stat) {
				res = Boolean.TRUE;
			} else {
				res = Boolean.FALSE;
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if (null != zooKeeper) {
				zooKeeper.close();
			}
		}
		return res;
	}


	public String getZookeeperHosts() {
		return zookeeperHosts;
	}

	public void setZookeeperHosts(String zookeeperHosts) {
		this.zookeeperHosts = zookeeperHosts;
	}
}
