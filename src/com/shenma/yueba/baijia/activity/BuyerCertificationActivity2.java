package com.shenma.yueba.baijia.activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;

import com.shenma.yueba.R;

/**
 * 身份认证
 * 
 * @author a
 * 
 */
public class BuyerCertificationActivity2 extends BaseActivityWithTopView
		 {

	DrawerLayout drawer_layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.buyer_certification_layout2);
		super.onCreate(savedInstanceState);
		initView();
		drawer_layout.setDrawerShadow(R.drawable.gray_round_bg, GravityCompat.START);  
		drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭手势滑动
	}

	private void initView() {
		setTitle("身份认证材料");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
	}

	}


