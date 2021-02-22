package com.adarsh.io.utils;

import java.text.DecimalFormat;

public class DoubleUtils {

	static final DecimalFormat df = new DecimalFormat("#.##########");

	public static String get(double value) {
		return df.format(value);
	}

}
