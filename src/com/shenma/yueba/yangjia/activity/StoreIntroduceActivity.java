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
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.baijia.modle.BaseRequest;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.umeng.analytics.MobclickAgent;


/**
 * 店铺说明
 * @author a
 */
public class StoreIntroduceActivity extends BaseActivityWithTopView {
	private EditText et_modify_info;
	private TextView tv_retain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
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
				if(TextUtils.isEmpty(et_modify_info.getText().toString().trim())){
					Toast.makeText(mContext, "店铺说明不能为空", 1000).show();
				}
				setStoreIntroduce();
			}
		});
		et_modify_info = (EditText) findViewById(R.id.et_modify_info);
		et_modify_info.setText(SharedUtil.getStringPerfernece(mContext,SharedUtil.user_Description));
		tv_retain = (TextView) findViewById(R.id.tv_retain);
		tv_retain.setText(300 - et_modify_info.getText().toString().trim().length()+"字");
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
		FontManager.changeFonts(mContext, et_modify_info,tv_top_title,tv_retain);
	}
	
	
	
	private void setStoreIntroduce(){
		HttpControl httpControl = new HttpControl();
		httpControl.setStoreIntroduce(et_modify_info.getText().toString().trim(), new HttpCallBackInterface() {
			@Override
			public void http_Success(Object obj) {
				Toast.makeText(mContext, "设置成功", 1000).show();
				SharedUtil.setStringPerfernece(mContext, SharedUtil.user_Description, et_modify_info.getText().toString().trim());
				StoreIntroduceActivity.this.finish();
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				Toast.makeText(mContext, ToolsUtil.nullToString(msg), 1000).show();
			}
		}, StoreIntroduceActivity.this, true, false);
	}
	
	
	@Override
	protected void onDestroy() {
		MyApplication.getInstance().addActivity(this);
		super.onDestroy();
	}
	
	
	  public void onResume() {
			super.onResume();
			MobclickAgent.onResume(this);
			}
			public void onPause() {
			super.onPause();
			MobclickAgent.onPause(this);
			}
}
