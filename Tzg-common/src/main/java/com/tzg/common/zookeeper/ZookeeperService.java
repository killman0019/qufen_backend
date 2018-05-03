package com.tzg.common.zookeeper;

import java.util.List;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * Zookeeper客户端接口
 * 
 * @author yangpeiliang
 * @version 1.0
 */
public interface ZookeeperService extends Watcher {
	
	/**
	 * 获取Zookeeper连接信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public ZooKeeper zooKeeperConnection() throws Exception;

	/**
	 * 根据路径创建节点，并且设置节点数据
	 * 
	 * @param path
	 *            节点路径
	 * @param data
	 *            节点存放的数据
	 * @return
	 * @throws Exception
	 */
	public String createNode(String path, byte[] data) throws Exception;

	/**
	 * 根据路径获取所有孩子节点
	 * 
	 * @param path
	 *            节点路径
	 * @return
	 * @throws Exception
	 */
	public List<String> getChildren(String path) throws Exception;

	/**
	 * 保存数据
	 * 
	 * @param path
	 *            节点路径
	 * @param data
	 *            节点存放的数据
	 * @param version
	 *            版本信息
	 * @return
	 * @throws Exception
	 */
	public Stat setData(String path, byte[] data, int version) throws Exception;

	/**
	 * 根据路径获取节点数据
	 * 
	 * @param path
	 *            节点路径
	 * @return
	 * @throws Exception
	 */
	public byte[] getData(String path) throws Exception;

	/**
	 * 删除节点
	 * 
	 * @param path
	 *            节点路径
	 * @param version
	 *            版本信息
	 * @throws Exception
	 */
	public void deletNode(String path, int version) throws Exception;

	/**
	 * 判断节点是否存在
	 * 
	 * @param path
	 *            节点路径
	 * @param watch
	 * @throws Exception
	 */
	public boolean isExists(String path, boolean watch) throws Exception;
}
