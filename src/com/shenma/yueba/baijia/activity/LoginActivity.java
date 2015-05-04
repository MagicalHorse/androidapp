package com.shenma.yueba.baijia.activity;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shenma.yueba.R;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.CustomProgressDialog;
import com.shenma.yueba.util.NetUtils;
import com.shenma.yueba.util.ParserJson;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.util.ToolsUtil;

/***
 * 登陆界面
 * 
 * @author Administrator
 * 
 */
public class LoginActivity extends BaseActivityWithTopView implements
		OnClickListener {
	private EditText et_username, et_password;// 用户名和密码 的输入框
	private TextView tv_forget, tv_register;// 忘记密码 ，立即注册
	private Button bt_login;// 登陆按钮
	private String username;// 用户名
	private String password;// 密码
	private List<String> userList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);
		super.onCreate(savedInstanceState);
		initView();// 获取控件
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		setTitle("登录");
		et_username = getView(R.id.et_username);
		et_password = getView(R.id.et_password);
		tv_forget = getView(R.id.tv_forget);
		tv_register = getView(R.id.tv_register);
		bt_login = getView(R.id.bt_login);
		tv_forget.setOnClickListener(this);
		tv_register.setOnClickListener(this);
		bt_login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_forget:// 忘记密码
			skip(UserFindPasswordBackActivity.class, false);
			break;
		case R.id.tv_register:// 普通注册
			skip(CommonRegisterActivity.class, false);
			break;
		case R.id.bt_login:
			if (!ToolsUtil.isNetworkConnected(mContext)) {//判断网络是否可用
				Toast.makeText(mContext,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
				return;
			}
			username = et_username.getText().toString();
			password = et_password.getText().toString();
			if (TextUtils.isEmpty(username)) {
				Toast.makeText(mContext, "用户名不能为空", Toast.LENGTH_SHORT).show();
				bt_login.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.shake));
			} else if (TextUtils.isEmpty(password)) {
				Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_SHORT).show();
				bt_login.startAnimation(AnimationUtils.loadAnimation(mContext,
						R.anim.shake));
			} else {
				
				
				
//				TaskManagerFactory.createDataTaskManager().addTask(new ITask() {
//					@Override
//					public void onTaskNumChanged(int taskNum) {
//					}
//
//					@Override
//					public void execute() {
//						final String result = NetUtil.login(mContext, username,
//								password);
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								dismissTopDialog();
//								UserBean bean = ParserJson.user_detail(result);
//								if (bean != null && bean.getQuery() != null) {
//									if (ToolsUtil.isQuerySeccess(bean
//											.getQuery())) {
//										// 保存用户信息
//										SharedUtil.setUserInfo(mContext,
//												bean.getUser_list(), username,
//												password);
//										JPushInterface.resumePush(mContext);// 恢复推送
//										skip(MainActivity.class, true);
//									} else {
//										Toast.makeText(mContext,
//												bean.getQuery().getReson(),
//												1000).show();
//									}
//								} else {
//									Toast.makeText(mContext,
//											Constants.connect_error,
//											Toast.LENGTH_SHORT).show();
//								}
//							}
//
//						});
//
//					}
//				});
				
				
				
				RequestParams params = getRequestParams();
				params.addBodyParameter(Constants.USERNAME, username);
				params.addBodyParameter(Constants.PASSWORD, password);
				getHttpUtils().send(HttpMethod.POST,NetUtils.loginUrl, params, new RequestCallBack<String>() {
					@Override
					public void onStart() {
						showDialog();
						super.onStart();
					}
					
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						//网络请求成功
						dismissDialog();
						ParserJson.parserLogin(responseInfo.result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// 网络请求失败
						dismissDialog();
					}
				});
				
			}
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
