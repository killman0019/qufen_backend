package com.tzg.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;

public class SyseUtil {
	@Value("#{paramConfig['DEV_ENVIRONMENT']}")
	private static String devEnvironment;

	public static void systemErrOutJson(Object obj) {

		if (StringUtils.isNotBlank(devEnvironment) && devEnvironment.equals(sysGlobals.DEV_ENVIRONMENT)) {

		} else {
			System.err.println(JSON.toJSONString(obj));
		}

	}
}
