package com.shenma.yueba.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * 偏好单例工具类
 * @author Administrator
 *
 */
public class MyPreference {
	private static SharedPreferences preference;
	private static Editor editor;

	@SuppressLint("CommitPrefEdits")
	public static SharedPreferences getPreference(Context context) {
		if (preference == null) {
			preference = PreferenceManager.getDefaultSharedPreferences(context);
			editor=preference.edit();
		}
		return preference;
	}

	public static void setPreference(SharedPreferences preference) {
		MyPreference.preference = preference;
	}

	public static  void putStringValue(Context context,String key, String value) {
		getPreference(context);
		editor.putString(key, value);
		editor.commit();
	}
	public static  void putBooleanValue(Context context,String key,boolean value) {
		getPreference(context);
		editor.putBoolean(key, value);
		editor.commit();
	}
	public static String getStringValue(Context context,String key){
		getPreference(context);
		return preference.getString(key, "");
	}
	public static boolean getBooleanValue(Context context,String key){
		getPreference(context);
		return preference.getBoolean(key, false);
	}
	public static void putLongValue(Context context,String key, long value) {
		getPreference(context);
		editor.putLong(key, value);
		editor.commit();
	}
	public static long getLongValue(Context context,String key){
		getPreference(context);
		return preference.getLong(key, 0);
	}
	public static void putIntValue(Context context,String key, int value) {
		getPreference(context);
		editor.putInt(key, value);
		editor.commit();
	}
	public static int getIntValue(Context context,String key){
		getPreference(context);
		return preference.getInt(key, 0);
	}
	public static void clearAllSharedPerference(Context context){
		getPreference(context);
		editor.clear();
		editor.commit();
	}
	
}
