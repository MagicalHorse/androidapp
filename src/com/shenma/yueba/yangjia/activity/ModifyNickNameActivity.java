package com.shenma.yueba.yangjia.activity;

import android.content.Context;
import android.content.Intent;
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
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.umeng.analytics.MobclickAgent;

/**
 * 修改昵称
 * 
 * @author a
 */
public class ModifyNickNameActivity extends BaseActivityWithTopView {
	private EditText et_modify_nick_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		MyApplication.getInstance().addActivity(this);//加入回退栈
		setContentView(R.layout.modify_nickname);
		super.onCreate(savedInstanceState);
		initView();
	}


	private void initView() {
		setTitle("修改昵称");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		et_modify_nick_name = (EditText) findViewById(R.id.et_modify_nick_name);
			
		et_modify_nick_name.setText(ToolsUtil.nullToString(SharedUtil.getStringPerfernece(mContext, SharedUtil.user_names)));
		setTopRightTextView("完成", new OnClickListener() {
			@Override
			public void onClick(View v) {
				String newName = et_modify_nick_name.getText()
						.toString().trim();
				if (TextUtils.isEmpty(newName)) {
					Toast.makeText(mContext, "昵称不能为空", 1000).show();
					return;
				}
				modifyNickName();
			}
		});
		FontManager.changeFonts(mContext, et_modify_nick_name, tv_top_title,tv_top_right);
	}

	/**
	 * 获取圈子详情
	 * 
	 * @param ctx
	 * @param isRefresh
	 * @param showDialog
	 */
	public void modifyNickName() {
		HttpControl httpControl = new HttpControl();
		httpControl.modifyNickName(et_modify_nick_name.getText().toString().trim(),
				true,true,new HttpCallBackInterface() {
					@Override
					public void http_Success(Object obj) {
						BaseRequest result = (BaseRequest) obj;
							Toast.makeText(mContext, "修改成功", 1000).show();
							Intent intent = new Intent();
							SharedUtil.setStringPerfernece(mContext, SharedUtil.user_names, et_modify_nick_name.getText().toString().trim());
							intent.putExtra("newName", et_modify_nick_name.getText().toString().trim());
							setResult(Constants.RESULTCODE,intent);
							ModifyNickNameActivity.this.finish();
					}
					@Override
					public void http_Fails(int error, String msg) {
						Toast.makeText(mContext, msg, 1000).show();
					}
				}, ModifyNickNameActivity.this);
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
