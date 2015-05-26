package com.shenma.yueba.yangjia.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ToolsUtil;


/**
 * 修改圈子名称
 * @author a
 */
public class ModifyCircleNameActivity extends BaseActivityWithTopView {
	private EditText et_modify_circle_name;
	private TextView tv_retain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.modify_circle_name);
		super.onCreate(savedInstanceState);
		initView();
	}
	private void initView() {
		setTitle("修改圈子名称");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		et_modify_circle_name = (EditText) findViewById(R.id.et_modify_info);
		setTopRightTextView("完成", new OnClickListener() {
			@Override
			public void onClick(View v) {
				String commitContent = et_modify_circle_name.getText().toString().trim();
				if(TextUtils.isEmpty(commitContent)){
					Toast.makeText(mContext, "圈子名称不能为空", 1000).show();
					return ;
				}
			}
		});
		tv_retain = (TextView) findViewById(R.id.tv_retain);
		FontManager.changeFonts(mContext, et_modify_circle_name,tv_top_title,tv_retain);
	}
}
