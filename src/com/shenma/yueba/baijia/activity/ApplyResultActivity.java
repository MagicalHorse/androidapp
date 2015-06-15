package com.shenma.yueba.baijia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.yangjia.activity.MainActivityForYangJia;

/**
 * 申请结果界面(可共用)
 * @author a
 *
 */
public class ApplyResultActivity extends BaseActivityWithTopView implements OnClickListener{
	private TextView tv_content;//内容
	private TextView tv_comment;//标记
	private TextView tv_confirm;//确定
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.apply_result_layout);
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		initView();
		getIntentData();
	}
	private void initView() {
		tv_content = getView(R.id.tv_content);
		tv_comment = getView(R.id.tv_comment);
		tv_confirm = getView(R.id.tv_confirm);
		setTitle("结果详情");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ApplyResultActivity.this.finish();
			}
		});
		tv_confirm.setOnClickListener(this);
	}
	private void getIntentData() {
		String flag = getIntent().getStringExtra("flag");
		if("applaywithdraw".equals(flag)){//申请提现
			tv_content.setText("提现申请已提交，请等待处理");
			tv_comment.setText("预计到账时间XXX");
		}
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_confirm://确定
			MyApplication.getInstance().removeAllActivity();
			Intent intent = new Intent(mContext, MainActivityForBaiJia.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}
}
