package com.tzg.common.zookeeper;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 〈一句话功能简述〉
 *
 * @author Wuws
 * @since p2p_cloud_v1.0
 */
public class TestZKClient extends Thread
{

    private static ApplicationContext context;

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException
    {
        context = new ClassPathXmlApplicationContext("classpath*:spring-servlet.xml");

        System.out.println("客户端启动。。。。");
        for (int i = 0; i < 20; i++)
        {
            new TestZKClient("client" + i).start();
        }
        Thread.sleep(2000 * 1000);
        System.out.println("客户端关闭。。。。");

    }

    private String name;

    public TestZKClient(String name)
    {
        this.name = name;
    }

    public void run()
    {
        ZKClient curatorFramework = (ZKClient) context.getBean("zkClientBean");
        
        // 获取一个zookeeper客户端
        CuratorFramework client = curatorFramework.getClient();
        
        // 定义一个锁
        InterProcessMutex lock = new InterProcessMutex(client, "/test_group_002");
        try
        {
            if (lock.acquire(120, TimeUnit.SECONDS))// 等待获取锁
            {
                try
                {
                    System.out.println("----------" + this.name + "正在处理资源----------");
                    Thread.sleep(2 * 10);
                }
                finally
                {
                    /**
                     * 必须释放lock
                     */                    
                    lock.release(); 
                    System.out.println("----------" + this.name + "释放----------");
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

}
