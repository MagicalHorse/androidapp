package com.shenma.yueba.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

public class ToolsUtil {

	
	
	public static int widthPixels;
	public static int heightPixels;

	
	/**
	 * 判断网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	
	
	/**
	 * 验证手机号
	 */
	public static boolean checkPhone(String phone) {
		if (phone == null || "".equals(phone)) {
			return false;
		}
		Pattern pattern = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$");
		Matcher matcher = pattern.matcher(phone);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * 验证邮编
	 */
	public static boolean checkCode(String code) {
		if (code == null || "".equals(code)) {
			return false;
		}
		String strPattern = "[1-9]\\d{5}(?!\\d)";
		Pattern pattern = Pattern.compile(strPattern);
		Matcher matcher = pattern.matcher(code);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 验证Email 邮箱
	 */
	public static boolean checkEmail(String email) {
		if (email == null || "".equals(email)) {
			return false;
		}
		String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern pattern = Pattern.compile(strPattern);
		Matcher matcher = pattern.matcher(email);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	
	


	public static int dip(Context context, int pixels)
	{
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		float scale = displayMetrics.density;
		System.out.println(displayMetrics.density);
		System.out.println(displayMetrics.densityDpi);
		System.out.println(displayMetrics.xdpi);
		System.out.println(displayMetrics.ydpi);

		widthPixels = displayMetrics.widthPixels;
		heightPixels = displayMetrics.heightPixels;
		System.out.println(displayMetrics.widthPixels);
		System.out.println(displayMetrics.heightPixels);

		return (int) (pixels * scale + 0.5f);

	}
	
	
	
	/**
	 * 获取当前页面的屏幕宽度
	 * 
	 * @param cx
	 * @return
	 */
	public static int getDisplayWidth(Context cx) {
			DisplayMetrics dm = new DisplayMetrics();
			dm = cx.getApplicationContext().getResources().getDisplayMetrics();
			int screenWidth = dm.widthPixels;
			return screenWidth;
		}

}
