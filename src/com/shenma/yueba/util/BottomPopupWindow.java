package com.shenma.yueba.util;





import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.shenma.yueba.R;

/**
 * 自定义popwindow
 * @author Administrator
 *
 */
@SuppressLint("ViewConstructor")
public class BottomPopupWindow extends PopupWindow {

	private View mMenuView;//笑脸弹出布局

	public BottomPopupWindow(Activity context, View view) {
		super(context);
		mMenuView = view;
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		this.setAnimationStyle(R.style.AnimBottom);
//		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
	}

}
