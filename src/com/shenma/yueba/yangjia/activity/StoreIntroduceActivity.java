package com.shenma.yueba.yangjia.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.util.FontManager;


/**
 * 店铺说明
 * @author a
 */
public class StoreIntroduceActivity extends BaseActivityWithTopView {
	private EditText et_modify_info;
	private TextView tv_retain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.store_introduce);
		super.onCreate(savedInstanceState);
		initView();
	}
	private void initView() {
		setTitle("店铺说明");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		setTopRightTextView("完成", new OnClickListener() {
			@Override
			public void onClick(View v) {
				String commitContent = et_modify_info.getText().toString().trim();
				
			}
		});
		et_modify_info = (EditText) findViewById(R.id.et_modify_info);
		et_modify_info.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				tv_retain.setText(300 - s.toString().trim().length()+"字");
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		tv_retain = (TextView) findViewById(R.id.tv_retain);
		FontManager.changeFonts(mContext, et_modify_info,tv_top_title,tv_retain);
	}
}
