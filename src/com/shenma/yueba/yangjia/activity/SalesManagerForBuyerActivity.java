package com.shenma.yueba.yangjia.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.receiver.SalesAdapter;
import com.shenma.yueba.util.FontManager;

/**
 * 销售管理---认证买手
 * @author a
 *
 */

public class SalesManagerForBuyerActivity extends BaseActivityWithTopView {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.sales_manager_layout_for_buyer);
		super.onCreate(savedInstanceState);
	}
	
	private void initView() {
		setTitle("销售管理");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SalesManagerForBuyerActivity.this.finish();
			}
		});
		FontManager.changeFonts(mContext, tv_top_title);
	}
}
