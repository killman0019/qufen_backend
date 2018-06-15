package com.tzg.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.apache.poi.ss.formula.functions.Roman;

/*18989309533，18718181505，18306420305，18957100566，18958087303*/
public class sendTelephone {
	public static String sendTele() {

		String num1 = "18989309533";
		String num2 = "18718181505";
		String num3 = "18306420305";
		String num4 = "18957100566";
		String num5 = "18958087303";
		List<String> lists = new ArrayList<String>();
		lists.add(num1);
		lists.add(num2);
		lists.add(num3);
		lists.add(num4);
		lists.add(num5);
		Integer i = RandomUtil.randomNumber(0, 4);

		return lists.get(i);
	}

}
