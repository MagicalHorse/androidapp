package com.shenma.yueba.yangjia.activity;

import android.os.Bundle;
import android.view.Window;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;

/**
 * 圈子管理
 * @author a
 *
 */
public class AddCircleActivity extends BaseActivityWithTopView{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_circle_layout);
		super.onCreate(savedInstanceState);
	}
}
