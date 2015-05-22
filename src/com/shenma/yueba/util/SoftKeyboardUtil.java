package com.shenma.yueba.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 软键盘工具类
 * @author lucifd
 *
 */
public class SoftKeyboardUtil {

	/**
	 * 清除Activity焦点
	 * @param view
	 */
	public static final void clearFocusable(View view) {
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		view.requestFocus();
	}

	/**
	 * 隐藏软键盘
	 * @param context
	 */
	public static final void hide(Activity context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 显示软键盘
	 * @param context
	 * @param view
	 */
	public static final void show(Activity context, View view) {
		view.setFocusable(true);
		view.requestFocus();
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}
}
