package com.tzg.common.zookeeper;
import org.apache.curator.framework.CuratorFramework;

/**
 * curator zookeeper client的简单包装
 *
 * @author Wuws
 * @since p2p_cloud_v1.0
 */
 public interface ZKClient
 {
     /**
      * 获取一个CuratorFramework
      * @return
      */
     public CuratorFramework getClient();

    /**
     * 关闭CuratorFramework
     */
     public void close();

 }
