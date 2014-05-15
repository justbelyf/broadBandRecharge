package com.nnk.broad.band.broker.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static final ThreadLocal<DateFormat> DATE_FORMAT = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			return format;
		}
	};

	public static String format(Date date, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	public static String format(Date date) {
		return DATE_FORMAT.get().format(date);
	}

	public static Date parse(String source) throws ParseException {
		return DATE_FORMAT.get().parse(source);
	}

	public static Date parse(String source, String pattern) throws ParseException {
		DateFormat format = new SimpleDateFormat(pattern);
		return format.parse(source);
	}
}
