package io.github.iTitus.gimmetime.common.util;

public class StringUtil {

	public static String makeNDigits(String s, int n, char c) {
		while (s.length() < n) {
			s = c + s;
		}
		return s;
	}

}
