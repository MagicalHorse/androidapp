package com.shenma.yueba.buyer.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.util.ParserJson;
import com.shenma.yueba.util.ToolsUtil;

/***
 * 找回密码(邮箱和手机号)
 * 
 * @author Administrator
 * 
 */
public class UserFindPasswordBackActivity extends BaseActivityWithTopView
		implements OnClickListener {
	private String[] type = new String[] { "mail", "mobile" };
	private LinearLayout ll_find_email;
	private LinearLayout ll_find_mobile;
	private EditText et_email;
	private EditText et_mobile;
	private Button bt_mobile_submit;
	private Button bt_email_submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.find_password_back_layout);
		super.onCreate(savedInstanceState);
		initView();
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		setLeftTextView("返回", new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ll_find_email = getView(R.id.ll_find_email);
		ll_find_mobile = getView(R.id.ll_find_mobile);
			setTitle("手机号找回密码");
			ll_find_mobile.setVisibility(View.VISIBLE);
			bt_mobile_submit = getView(R.id.bt_mobile_submit);
			et_mobile = getView(R.id.et_mobile);
			bt_mobile_submit.setOnClickListener(this);
		}



	/**
	 * 请求发送邮件
	 */
	private void submitCodeByMail() {
		if ("正在发送邮件".equals(bt_email_submit.getText())) {
			return;
		}
		final String email = String.valueOf(et_email.getText());
		if (email == null || "".equals(email)) {
			Toast.makeText(mContext, "请输入邮箱", Toast.LENGTH_SHORT).show();
		} else {
			bt_email_submit.setText("正在发送邮件");
//			TaskManagerFactory.createDataTaskManager().addTask(new ITask() {
//				@Override
//				public void onTaskNumChanged(int taskNum) {
//				}
//
//				@Override
//				public void execute() {
//					final String result = NetUtil.reset_password_by_email(mContext,
//							email);
//					runOnUiThread(new Runnable() {
//						public void run() {
//							bt_email_submit.setText("找回密码");
//							QueryBean bean = ParserJson.query(result);
//							if (bean != null && bean.getQuery() != null) {
//								if (ToolsUtil.isQuerySeccess(bean.getQuery())) {
//									Toast.makeText(mContext, "邮件发送成，请登录邮箱找回",
//											1000).show();
//								} else {
//									Toast.makeText(mContext,
//											bean.getQuery().getReson(),
//											Toast.LENGTH_SHORT).show();
//								}
//							} else {
//								Toast.makeText(mContext,
//										getString(R.string.request_fail),
//										Toast.LENGTH_SHORT).show();
//							}
//						}
//					});
//				}
//			});
		}
	}

	/**
	 * 获取验证码
	 */
	private void submitCodeByMobile() {
		if ("正在获取验证码".equals(et_mobile.getText().toString())) {
			return;
		}
		final String phone = String.valueOf(et_mobile.getText().toString());
		if (!ToolsUtil.checkPhone(phone)) {
			Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
		} else {
			bt_mobile_submit.setText("正在获取验证码");
//			TaskManagerFactory.createDataTaskManager().addTask(new ITask() {
//				@Override
//				public void onTaskNumChanged(int taskNum) {
//				}
//
//				@Override
//				public void execute() {
//					String result = NetUtil.mobile_validate_code(mContext,
//							et_mobile.getText().toString());
//					final FindPasswordbackBean bean = ParserJson
//							.findPasswordByMobile(result);
//					runOnUiThread(new Runnable() {
//						public void run() {
//							bt_mobile_submit.setText("下一步");
//							if (bean != null && bean.getQuery() != null) {
//								if (ToolsUtil.isQuerySeccess(bean.getQuery())) {
//									Toast.makeText(mContext, "获取成功", 1000)
//											.show();
//									Intent i = new Intent(mContext,
//											UserForgetPhoneCodeActivity.class);
//									i.putExtra("phone", phone);
//									i.putExtra("userId", ToolsUtil
//											.nullToString(bean.getUser_id()));
//									startActivity(i);
//								} else {
//									Toast.makeText(mContext,
//											bean.getQuery().getReson(), 1000)
//											.show();
//								}
//							} else {
//								runOnUiThread(new Runnable() {
//									@Override
//									public void run() {
//										Toast.makeText(
//												mContext,
//												getString(R.string.request_fail),
//												1000).show();
//									}
//								});
//							}
//						}
//					});
//				}
//			});
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_email_submit:// 通过邮箱查找的提交
			submitCodeByMail();
			break;
		case R.id.bt_mobile_submit:// 通过手机号查找的提交
			submitCodeByMobile();
			break;

		default:
			break;
		}
	}
	
	public void onResume() {
		super.onResume();

	}

	public void onPause() {
		super.onPause();
	}
}
