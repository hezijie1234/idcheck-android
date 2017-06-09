package com.huiyu.tech.zhongxing.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * Preference 集成类
 * 
 * @author ml
 * 
 */
public class SharedPrefUtils {
	/**
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(Context context, String key,
								   String defaultValue) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getString(key, defaultValue);
	}

	/**
	 * @param context
	 * @param name
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(Context context, String name, String key,
								   String defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		return sp.getString(key, defaultValue);
	}

	/**
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setString(Context context, String key, String value) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putString(key, value).commit();
	}

	/**
	 * @param context
	 * @param name
	 * @param key
	 * @param value
	 */
	public static void setString(Context context, String name, String key,
								 String value) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}

	/**
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(Context context, String key,
									 boolean defaultValue) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getBoolean(key, defaultValue);
	}

	/**
	 * @param context
	 * @param name
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Boolean getBoolean(Context context, String name, String key,
									 Boolean defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		return sp.getBoolean(key, defaultValue);
	}

	/**
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setBoolean(Context context, String key, boolean value) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putBoolean(key, value).commit();
	}

	/**
	 * @param context
	 * @param name
	 * @param key
	 * @param value
	 */
	public static void setBoolean(Context context, String name, String key,
								  Boolean value) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}

	/**
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(Context context, String key, int defaultValue) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getInt(key, defaultValue);
	}

	/**
	 * @param context
	 * @param name
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(Context context, String name, String key,
							 int defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		return sp.getInt(key, defaultValue);
	}

	/**
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setInt(Context context, String key, int value) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putInt(key, value).commit();
	}

	/**
	 * @param context
	 * @param name
	 * @param key
	 * @param value
	 */
	public static void setInt(Context context, String name, String key,
							  int value) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		sp.edit().putInt(key, value).commit();
	}

	/**
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static float getFloat(Context context, String key, float defaultValue) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getFloat(key, defaultValue);
	}

	/**
	 * @param context
	 * @param name
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static float getFloat(Context context, String name, String key,
								 float defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		return sp.getFloat(key, defaultValue);
	}

	/**
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setFloat(Context context, String key, float value) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putFloat(key, value).commit();
	}

	/**
	 * @param context
	 * @param name
	 * @param key
	 * @param value
	 */
	public static void setFloat(Context context, String name, String key,
								float value) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		sp.edit().putFloat(key, value).commit();
	}

	/**
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static long getLong(Context context, String key, long defaultValue) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getLong(key, defaultValue);
	}

	/**
	 * @param context
	 * @param name
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static long getLong(Context context, String name, String key,
							   long defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		return sp.getLong(key, defaultValue);
	}

	/**
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setLong(Context context, String key, long value) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putLong(key, value).commit();
	}

	/**
	 * @param context
	 * @param name
	 * @param key
	 * @param value
	 */
	public static void setLong(Context context, String name, String key,
							   long value) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		sp.edit().putLong(key, value).commit();
	}

	/**
	 * 清除默认的Preference中的内容
	 * 
	 * @param context
	 */
	public static void clearPreference(Context context) {
		SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = p.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 判断是否有对应的key
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean hasKey(Context context, String key) {
		return PreferenceManager.getDefaultSharedPreferences(context).contains(
				key);
	}
}
