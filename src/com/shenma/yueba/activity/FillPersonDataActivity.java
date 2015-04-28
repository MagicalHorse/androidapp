package com.shenma.yueba.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.util.FontManager;



/**
 * 注册第二步骤
 * @author a
 *
 */
public class FillPersonDataActivity extends BaseActivityWithTopView {

	private EditText et_username;
	private EditText et_password;
	private EditText et_repassword;
	private TextView tv_city;
	private TextView tv_change_city;
	private Button bt_commit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fill_person_data);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setTitle("完善个人资料");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		et_repassword = (EditText) findViewById(R.id.et_repassword);
		tv_city = (TextView) findViewById(R.id.tv_city);
		tv_change_city = (TextView) findViewById(R.id.tv_change_city);
		bt_commit = (Button) findViewById(R.id.bt_commit);
		FontManager.changeFonts(mContext, tv_top_title, et_username,
				et_password, et_repassword, tv_city, tv_change_city, bt_commit);
	}
}
