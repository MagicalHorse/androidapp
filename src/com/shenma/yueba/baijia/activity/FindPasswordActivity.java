package com.shenma.yueba.baijia.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.MyCountDown;
/**
 * 找回密码
 * @author a
 *
 */
public class FindPasswordActivity extends BaseActivityWithTopView implements
		OnClickListener, TextWatcher {
	private TextView tv_mobile_title;
	private EditText et_mobile;
	private EditText et_code;
	private Button bt_sure;
	private TextView tv_getcode;// 获取验证码
	private int maxSecond = 90;
	private String getCodeString = "获取验证码";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.find_password_layout);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setTitle("找回密码");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FindPasswordActivity.this.finish();
			}
		});
		tv_mobile_title = (TextView) findViewById(R.id.tv_mobile_title);
		et_mobile = (EditText) findViewById(R.id.et_mobile);
		et_mobile.addTextChangedListener(this);
		tv_getcode = (TextView) findViewById(R.id.tv_getcode);
		et_code = (EditText) findViewById(R.id.et_code);
		bt_sure = (Button) findViewById(R.id.bt_sure);
		bt_sure.setOnClickListener(this);
		tv_getcode.setOnClickListener(this);
		FontManager.changeFonts(mContext, tv_mobile_title, et_mobile,
				tv_getcode, et_code, bt_sure, tv_top_title);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_sure:// 确定找回密码
			skip(SetNewPasswordActivity.class, false);
			break;
		case R.id.tv_getcode:// 获取验证码
			new MyCountDown(maxSecond * 1000, 1000, tv_getcode, getCodeString)
					.start();
			break;
		default:
			break;
		}

	}
	
	@Override
	public void afterTextChanged(Editable arg0) {
		if(arg0.length()>0){
			et_mobile.setVisibility(View.VISIBLE);
		}else{
			et_mobile.setVisibility(View.INVISIBLE);
		}
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
}
