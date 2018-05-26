package com.tzg.common.utils;


/**
 * 系统参数枚举  yuchen  
 */
public enum EnumSystemparam {
	定期宝清算时间("定期宝清算时间", "fixedbaoClearingTime");
	private String Code;
	private String Name;

	private EnumSystemparam(String Name, String Code) {
		this.Code = Code;
		this.Name = Name;
	}

	public static String getCode(String Name) {
		for (EnumSystemparam item : EnumSystemparam.values()) {
			if (item.Name.equals(Name))
				return item.Code;
		}

		return null;

	}
}