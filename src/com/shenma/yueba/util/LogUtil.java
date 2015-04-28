package com.shenma.yueba.util;


import android.util.Log;

public class LogUtil {

//	 public static boolean isShow = false;//上线模式
	public static boolean isShow = true;// 开发模式

	public static void v(String tag, String msg) {
		if (isShow) {
			Log.v(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (isShow) {
			Log.d(tag, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (isShow) {
			Log.i(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (isShow) {
			Log.w(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (isShow) {
			Log.e(tag, msg);
		}
	}
}
