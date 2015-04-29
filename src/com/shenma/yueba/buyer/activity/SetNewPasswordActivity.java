package com.shenma.yueba.buyer.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.shenma.yueba.R;
import com.shenma.yueba.util.FontManager;
/**
 * 设置新密码
 * @author a
 *
 */
public class SetNewPasswordActivity extends BaseActivityWithTopView implements OnClickListener{

	private EditText et_new_password;
	private EditText et_new_repassword;
	private Button bt_sure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.set_new_password_layout);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setTitle("设置新密码");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SetNewPasswordActivity.this.finish();
			}
		});
		et_new_password = (EditText) findViewById(R.id.et_new_password);
		et_new_repassword = (EditText) findViewById(R.id.et_new_repassword);
		bt_sure = (Button) findViewById(R.id.bt_sure);
		bt_sure.setOnClickListener(this);
		FontManager.changeFonts(mContext, et_new_password,et_new_repassword,bt_sure,tv_top_title);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_sure://找回
			
			break;

		default:
			break;
		}
		
	}
}
