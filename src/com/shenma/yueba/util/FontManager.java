package com.shenma.yueba.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 幼圆字体工具类
 * 
 * @author a
 * 
 */
public class FontManager {
	public static void changeFonts(Context ctx, View... views) {
		Typeface tf = Typeface.createFromAsset(ctx.getAssets(),
				"fonts/youyuan.ttf");
		for (int i = 0; i < views.length; i++) {
			if (views[i] instanceof TextView) {
				((TextView) views[i]).setTypeface(tf);
			} else if (views[i] instanceof Button) {
				((Button) views[i]).setTypeface(tf);
			} else if (views[i] instanceof EditText) {
				((EditText) views[i]).setTypeface(tf);
			}
		}
	}
}
