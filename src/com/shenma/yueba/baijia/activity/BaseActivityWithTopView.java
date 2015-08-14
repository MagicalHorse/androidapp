package com.shenma.yueba.baijia.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.shenma.yueba.R;

/***
 * 此类是窗体顶部的标题栏，包含右面按钮
 * 
 * @author Administrator
 * 
 */
public abstract class BaseActivityWithTopView extends BaseActivity {
	protected TextView tv_top_title;// 标题
	protected TextView tv_top_left;// 左侧TextView
	protected TextView tv_top_right;// 左侧TextView

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tv_top_title = getView(R.id.tv_top_title);
		tv_top_left = getView(R.id.tv_top_left);
		tv_top_right = getView(R.id.tv_top_right);
	}

	// 设置标题
	protected void setTitle(String titleStr) {
		tv_top_title.setVisibility(View.VISIBLE);
		tv_top_title.setText(titleStr);
	}

	// 设置左侧的TextView
	protected void setLeftTextView(String titleStr, OnClickListener onclick) {
		tv_top_left.setVisibility(View.VISIBLE);
		tv_top_left.setText(titleStr);
		tv_top_left.setOnClickListener(onclick);
	}
	
	// 设置左侧的TextView
		protected void setLeftTextView(OnClickListener onclick) {
			tv_top_left.setVisibility(View.VISIBLE);
			tv_top_left.setOnClickListener(onclick);
		}

	// 设置左侧的TextView
	protected void setLeftTextView(String titleStr) {
		tv_top_left.setVisibility(View.VISIBLE);
		tv_top_left.setText(titleStr);
	}

	// 设置右侧的TextView
	protected void setTopRightTextView(String titleStr) {
		if(TextUtils.isEmpty(titleStr)){
			tv_top_right.setVisibility(View.GONE);
		}else{
			tv_top_right.setVisibility(View.VISIBLE);
			tv_top_right.setText(titleStr);
		}
	}

	// 设置右侧的TextView
	protected void setTopRightTextView(String titleStr, OnClickListener onclick) {
		tv_top_right.setVisibility(View.VISIBLE);
		tv_top_right.setText(titleStr);
		tv_top_right.setOnClickListener(onclick);
	}

}
