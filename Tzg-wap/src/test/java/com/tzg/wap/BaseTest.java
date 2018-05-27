package com.tzg.wap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * test父类
 * 
 * @author fallenLeaves
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-wap.xml","classpath*:spring/rmi-service-context.xml" })
public class BaseTest {
    @Test
    public void test() {
        Assert.assertTrue(Boolean.TRUE);
    }
}
