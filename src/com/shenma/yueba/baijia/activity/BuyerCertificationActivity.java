package com.shenma.yueba.baijia.activity;

import com.shenma.yueba.R;
import com.shenma.yueba.util.FontManager;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 身份认证
 * 
 * @author a
 * 
 */
public class BuyerCertificationActivity extends BaseActivityWithTopView {

	private TextView tv_idcard_title;//身份证信息头
	private TextView tv_get_point;//自提点
	private EditText et_info;//信息
	private TextView tv_retain;//剩余字数
	private TextView tv_commit;//提交

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.buyer_certification_layout);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		tv_idcard_title = (TextView) findViewById(R.id.tv_idcard_title);
		tv_get_point = (TextView) findViewById(R.id.tv_get_point);
		et_info = (EditText) findViewById(R.id.et_info);
		tv_retain = (TextView) findViewById(R.id.tv_retain);
		FontManager.changeFonts(mContext, tv_idcard_title,tv_get_point,et_info,tv_retain,tv_commit);
	}
}
