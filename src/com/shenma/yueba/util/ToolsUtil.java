package com.shenma.yueba.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

public class ToolsUtil {

	
	public static final Pattern EMOTION_URL = Pattern.compile("\\[(\\S+?)\\]");
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

	
	/**
	 * 获取当前页面的屏幕高度
	 * 
	 * @param cx
	 * @return
	 */
	public static int getDisplayHeight(Context cx) {
			DisplayMetrics dm = new DisplayMetrics();
			dm = cx.getApplicationContext().getResources().getDisplayMetrics();
			int screenHeight = dm.heightPixels;
			return screenHeight;
		}

	/**
	 * 刚进入界面时，隐藏软键盘
	 * 
	 * @param context
	 *            上下文
	 */
	public static void hideSoftInputKeyBoard(Activity context) {
		context.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	
	/**
	 * 判断空间是否可用
	 * 
	 * @param mContext
	 * @return
	 */
	public static boolean isAvailableSpace(Context mContext) {
		if (isMounted(mContext) && isEnoughSpace(mContext)) {
			return true;
		}else{
			return false;
		}
	}

	public static boolean isMounted(Context mContext) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		}
		Toast.makeText(mContext, "SD卡不可用",
				1000).show();
		return false;
	}

	/**
	 * 空闲区限制不能小于5M
	 * 
	 * @param mContext
	 * @return
	 */
	public static boolean isEnoughSpace(Context mContext) {
		if (getSDFreeSize() < 5) {
			Toast.makeText(mContext,"存储空间不足", 1000).show();
			return false;
		}
		return true;
	}

	private static long getSDFreeSize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		long blockSize = sf.getBlockSize();
		long freeBlocks = sf.getAvailableBlocks();
		return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
	}
	
	
	
	/**
	 * 正则表达式抽取内容中的图片本地地址
	 */
	public static List<String> convertNormalStringToSpannableString(
			String content) {
		List<String> listImg = new ArrayList<String>();
		Matcher localMatcher = EMOTION_URL.matcher(content);
		while (localMatcher.find()) {
			int k = localMatcher.start();
			int m = localMatcher.end();
			if (m - k > 4) {
				listImg.add(content.substring(k + 1, m - 1));
			}
		}
		return listImg;
	}

	public static int getImgCountFromContent(String contentStr) {
		return convertNormalStringToSpannableString(contentStr).size();
	}


}
