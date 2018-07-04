package com.tzg.test.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.tzg.entitys.appwelcomeimage.AppwelcomeimageMapper;
import com.tzg.entitys.kff.devaluationModel.DevaluationModel;

public class AppwelcomeimageMapperTest {
    public static void main(String[] args) {
       /* ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring4test/dbsource.xml");
        Map<String, Object> map = new HashMap<String, Object>();
        AppwelcomeimageMapper am = context.getBean(AppwelcomeimageMapper.class);
        map.put("vcFileNameP", "1' and 1=1 '");
        System.out.println(am.findPageCount(map));;*/
    	List<DevaluationModel> models = JSON.parseArray(null, DevaluationModel.class);
    	System.out.println(JSON.toJSON(models));
    	if (CollectionUtils.isNotEmpty(models) && models.size() > 0) {
    		System.out.println("进来了");
    	}
    }
}
