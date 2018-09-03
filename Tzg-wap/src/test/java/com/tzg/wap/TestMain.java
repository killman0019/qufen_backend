package com.tzg.wap;

import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.SyseUtil;

public class TestMain {

	public static void main(String[] args) {
		String sys = DateUtil.getSpecifiedDayBeforeOrAfter(5);
		SyseUtil.systemErrOutJson(sys);
	}
}
