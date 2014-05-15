package com.nnk.broad.band.broker.common;

public class StringUtil {

	private StringUtil() {
		throw new AssertionError();
	}

	public static boolean isAnyEmpty(String... args) {
		if (args == null) {
			return true;
		}
		for (String string : args) {
			if (string == null || "".equals(string)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String toString(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}
}
