package com.tzg.wap;

import java.util.regex.Pattern;

public class TestRe {
	// String str = "data:image/jpeg;base64";
	// String str1 =
	// "//^\\s*data:([a-z]+\\/[a-z0-9-+.]+(;[a-z-]+=[a-z0-9-]+)?)?(;base64)?,([a-z0-9!$&',()*+;=\\-._~:@\\/?%\\s]*?)\\s*$/i;";

	public static void main(String[] args) {
		String str = "data:image/jpeg;base64,zzzzzzsdasdadadszzzzzzzzzzz";
		String str1 = "(data:([a-z]+\\/[a-z0-9-+.]+(;[a-z-]+=[a-z0-9-]+)?)?(;base64)?,).*";
		String str2 = "data:([a-z]+\\/[a-z0-9-+.]+(;[a-z-]+=[a-z0-9-]+)?)?(;base64)?,";
		if (str.matches(str1)) {
			System.err.println("成功");
		} else {
			System.err.println("失败");
		}
		System.err.println(str.replaceAll(str2, ""));
		boolean matches = Pattern.matches(str1, str);
		System.err.println(matches);
	}
}
