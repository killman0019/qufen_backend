package com.tzg.common.zookeeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

/**
 * 基于Curator的zookeeper客户端<br/>
 * 
 *
 * @author Wuws
 * @since p2p_cloud_v1.0
 */
public class ZKClientImpl implements ZKClient
{
    private volatile static CuratorFramework client = null;

    /**
     * 初始化
     * 
     * @param zookeeperConnectionString
     *            zookeeper的链接地址 例如： 192.168.1.20:2181 或者 "192.168.1.20:2181,192.168.1.25:2182,192.168.1.28:2183"
     */
    public ZKClientImpl(String zookeeperConnectionString)
    {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        create(zookeeperConnectionString, retryPolicy);
    }

    /**
     * 
     * @param zookeeperConnectionString
     *            zookeeper的链接地址 例如： 192.168.1.20:2181 或者 "192.168.1.20:2181,192.168.1.25:2182,192.168.1.28:2183"
     * @param baseSleepTimeMs
     * @param maxRetries
     */
    public ZKClientImpl(String zookeeperConnectionString, int baseSleepTimeMs, int maxRetries)
    {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
        create(zookeeperConnectionString, retryPolicy);
    }

    private static void create(String zookeeperConnectionString, ExponentialBackoffRetry retryPolicy)
    {
        client = CuratorFrameworkFactory.builder().connectString(zookeeperConnectionString).retryPolicy(retryPolicy)
                .build();
        client.start();
    }

    public CuratorFramework getClient()
    {
        return client;
    }

    public void close()
    {
        CloseableUtils.closeQuietly(client);
    }

}
