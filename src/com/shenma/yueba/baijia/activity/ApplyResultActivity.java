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
		tv_content = getView(R.id.tv_content);
		tv_comment = getView(R.id.tv_comment);
		tv_confirm = getView(R.id.tv_confirm);
		tv_confirm.setOnClickListener(this);
		super.onCreate(savedInstanceState);
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
