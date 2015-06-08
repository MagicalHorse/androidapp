package com.shenma.yueba.yangjia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.ApplyResultActivity;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.util.FontManager;

/**
 * 申请提现的界面
 * 
 * @author a
 * 
 */

public class ApplyWithdrawActivity extends BaseActivityWithTopView implements
		OnClickListener {

	private TextView tv_retan_money;
	private EditText et_money;
	private TextView tv_yuan;
	private TextView tv_introduce;
	private TextView tv_sure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.applay_withdraw_layout);
		super.onCreate(savedInstanceState);
		initView();
		getIntentData();
	}

	private void getIntentData() {
		setTitle("申请提现");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ApplyWithdrawActivity.this.finish();
			}
		});
		String money = getIntent().getStringExtra("money");
		tv_retan_money.setText("可提现收益" + money + "元");
	}

	private void initView() {
		tv_retan_money = getView(R.id.tv_retan_money);
		et_money = getView(R.id.et_money);
		tv_yuan = getView(R.id.tv_yuan);
		tv_introduce = getView(R.id.tv_introduce);
		tv_sure = getView(R.id.tv_sure);
		tv_sure.setOnClickListener(this);
		FontManager.changeFonts(mContext, tv_retan_money, et_money, tv_yuan,
				tv_introduce);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_sure:// 确认提现
			String money = et_money.getText().toString().trim();
			if (money.matches("[0-9]+")) {
				Intent intent = new Intent(ApplyWithdrawActivity.this,
						ApplyResultActivity.class);
				intent.putExtra("flag", "applaywithdraw");//申请提现
				startActivity(intent);
			} else {
				Toast.makeText(mContext, "请输入整数", 1000).show();
			}
			break;

		default:
			break;
		}
	}
}