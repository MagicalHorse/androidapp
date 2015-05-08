package com.shenma.yueba.yangjia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.util.FontManager;

/**
 * 买手认证
 * 
 * @author a
 * 
 */
public class BuyerIndentificationActivity extends BaseActivityWithTopView
		implements OnClickListener {

	private TextView attation_identification_buyer;
	private TextView wechat_identification_buyer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buyer_identification_layout);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setTitle("买手验证");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BuyerIndentificationActivity.this.finish();
			}
		});
		attation_identification_buyer = (TextView) findViewById(R.id.attation_identification_buyer);
		wechat_identification_buyer = (TextView) findViewById(R.id.wechat_identification_buyer);
		attation_identification_buyer.setOnClickListener(this);
		wechat_identification_buyer.setOnClickListener(this);
		FontManager.changeFonts(this, attation_identification_buyer,wechat_identification_buyer);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.attation_identification_buyer:// 已关注认证买手
			Intent intent = new Intent(BuyerIndentificationActivity.this,
					IdentificationBuyerListActivity.class);
			startActivity(intent);
			break;
		case R.id.wechat_identification_buyer:// 微信认证买手

			break;

		default:
			break;
		}

	}
}
