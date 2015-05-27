package com.shenma.yueba.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.application.MyApplication;

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

	
	
	public static String nullToString(String str) {
		if (str == null) {
			return "";
		} else {
			return str;
		}
	}

	

	/**
	 * 解析表情
	 * 
	 * @param message
	 *            传入的需要处理的String
	 * @return
	 */
	public static CharSequence analysisFace(Context context, String message) {
		String hackTxt;
		if (message.startsWith("[") && message.endsWith("]")) {
			hackTxt = message + " ";
		} else {
			hackTxt = message;
		}
		SpannableString value = SpannableString.valueOf(hackTxt);
		Matcher localMatcher = EMOTION_URL.matcher(value);
		while (localMatcher.find()) {
			String str2 = localMatcher.group(0);
			int k = localMatcher.start();
			int m = localMatcher.end();
			if (m - k < 8) {
				if (MyApplication.getInstance().getFaceMap().containsKey(str2)) {
					int face = MyApplication.getInstance().getFaceMap()
							.get(str2);
					Drawable d = context.getResources().getDrawable(face);
					if (d != null) {
						d.setBounds(0, 0, 25, 25);// 设置表情图片的显示大小
						ImageSpan span = new ImageSpan(d,
								ImageSpan.ALIGN_BOTTOM);
						value.setSpan(span, k, m,
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					}
				}
			}
		}
		return value;
	}
	
	
	/**
	 * 获取当前时间的字符串
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getCurrentTime() {
		return dateToStrLong(new Date(System.currentTimeMillis()));
	}
	
	
	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}
	
	
	/**
	 * 设置textView字体颜色
	 * 
	 * @param tv
	 *            textView控件
	 * @param str1
	 *            第一个字段
	 * @param color1
	 *            第一个字段颜色
	 * @param str2
	 *            第二个字段
	 * @param color2
	 *            第二个字段颜色
	 */
	public static void setTextColor(TextView tv, String str1, String color1,
			String str2, String color2) {
		tv.setText(Html.fromHtml("<font color=\"" + color1 + "\">" + str1
				+ "</font>" + "<font color=\"" + color2 + "\">" + str2
				+ "</font>"));
	}

	/**
	 * 设置红色字在前，黑色字在后
	 * 
	 * @param tv
	 *            TextView控件
	 * @param redStr
	 *            红颜色字
	 * @param normalStr
	 *            黑颜色字
	 */
	public static void setTextColorRedAndBlack(TextView tv, String redStr,
			String normalStr) {
		tv.setText(Html.fromHtml("<font color=\"#C60606\">" + redStr
				+ "</font>" + "<font color=\"#000000\">" + normalStr
				+ "</font>"));
	}

	/**
	 * 设置黑色字在前，红色字在后
	 * 
	 * @param tv
	 * @param normalStr
	 *            黑颜色字
	 * @param redStr
	 *            红颜色字
	 */
	public static void setTextColorBlackAndRed(TextView tv, String normalStr,
			String redStr) {
		tv.setText(Html.fromHtml("<font color=\"#000000\">" + normalStr
				+ "</font>" + "<font color=\"#C60606\">" + redStr + "</font>"));
	}

	/** 
	 * 设置字体颜色为红色
	 * 
	 * @param tv
	 * @param redSt
	 *            红颜色字
	 */
	public static void setTextColorRed(TextView tv, String redStr) {
		tv.setText(Html.fromHtml("<font color=\"#C60606\">" + redStr
				+ "</font>"));
	}

	/**
	 * 获取红色的字体
	 * 
	 * @param tv
	 * @param redStr
	 *            红颜色字
	 */
	public static String getTextColorRed(String redStr) {
		return "<font color=\"#C60606\">" + redStr + "</font>";
	}

}
