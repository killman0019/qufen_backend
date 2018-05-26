package com.tzg.test.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tzg.entitys.appwelcomeimage.AppwelcomeimageMapper;

public class AppwelcomeimageMapperTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring4test/dbsource.xml");
        Map<String, Object> map = new HashMap<String, Object>();
        AppwelcomeimageMapper am = context.getBean(AppwelcomeimageMapper.class);
        map.put("vcFileNameP", "1' and 1=1 '");
        System.out.println(am.findPageCount(map));;
    }
}
