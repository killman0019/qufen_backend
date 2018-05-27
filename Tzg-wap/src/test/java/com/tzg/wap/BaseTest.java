package com.tzg.wap;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.tzg.common.utils.SubimitHtml;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.photo.PhotoIview;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.wap.utils.UploadPic;

/**
 * test父类
 * 
 * @author fallenLeaves
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-wap.xml", "classpath*:spring/rmi-service-context.xml" })
public class BaseTest {
	public static void main(String[] args) {
	}

	public static void test(String projectNames) {
		projectNames = "TE-FOOD/(TFD)";

		String[] str = projectNames.split("\\/");
		System.out.println(str[0]);
		System.out.println(str[1]);
	}

}
