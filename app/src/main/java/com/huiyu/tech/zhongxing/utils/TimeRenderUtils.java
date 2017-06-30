package com.huiyu.tech.zhongxing.utils;

import android.util.Log;
import android.widget.Toast;

import com.huiyu.tech.zhongxing.models.NoticeModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class TimeRenderUtils {

	private static SimpleDateFormat formatBuilder;
	
	/**
	 * 将带中文格式的时间字符串
	 * @return
	 */
	public static String formatTime(String time, String format){
		String t = "";
		formatBuilder = new SimpleDateFormat("MMM dd,yyyy", Locale.CHINA);
		try {
			Date date = formatBuilder.parse(time);
			t = TimeRenderUtils.getDate(format, date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
	
	public static Date getTime(String format, String time){
		Date date = null;
		formatBuilder = new SimpleDateFormat(format);
		try {
			date = formatBuilder.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getDate(String format, long time) {
		return getDate(format, new Date(time));
	}
	
	public static String getDate(String format) {
	    return getDate(format, new Date());
	}
	
	public static String getDate(String format, Date date){
		formatBuilder = new SimpleDateFormat(format, Locale.getDefault());
		return formatBuilder.format(date);
	}

	public static int getDay() {
		return getDay(new Date());
	}

	public static int getDay(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("d", Locale.getDefault());
		return Integer.valueOf(formatter.format(date));
	}

	public static int getMonth() {
		return getMonth(new Date());
	}

	public static int getMonth(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("M", Locale.getDefault());
		return Integer.valueOf(formatter.format(date));
	}

	public static int getYear() {
		return getYear(new Date());
	}

	public static int getYear(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.getDefault());
		return Integer.valueOf(formatter.format(date));
	}
}
