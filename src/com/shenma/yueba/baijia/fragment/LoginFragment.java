package com.shenma.yueba.baijia.fragment;

import java.util.Map;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.FindPasswordActivity;
import com.shenma.yueba.baijia.activity.MainActivityForBaiJia;
import com.shenma.yueba.baijia.modle.UserRequestBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

public class LoginFragment extends BaseFragment implements OnClickListener {

	private TextView tv_mobile_title;
	private EditText et_mobile;
	private EditText et_password;
	private TextView tv_other;
	private TextView tv_wechat;
	private TextView tv_qq;
	private TextView tv_forget;
	private Button bt_login;
	private View view;
	private UMSocialService mController;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mController = UMServiceFactory.getUMSocialService("com.umeng.login");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.login_fragment_layout, null);
			tv_mobile_title = (TextView) view
					.findViewById(R.id.tv_mobile_title);
			bt_login = (Button) view.findViewById(R.id.bt_login);
			et_mobile = (EditText) view.findViewById(R.id.et_mobile);
			et_password = (EditText) view.findViewById(R.id.et_password);
			tv_forget = (TextView) view.findViewById(R.id.tv_forget);
			tv_other = (TextView) view.findViewById(R.id.tv_other);
			tv_wechat = (TextView) view.findViewById(R.id.tv_wechat);
			tv_qq = (TextView) view.findViewById(R.id.tv_qq);
			tv_forget.setOnClickListener(this);
			bt_login.setOnClickListener(this);
			tv_wechat.setOnClickListener(this);
			tv_qq.setOnClickListener(this);
			FontManager.changeFonts(getActivity(), tv_mobile_title, et_mobile,
					et_password, tv_forget, tv_other, tv_wechat, bt_login);
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_forget:// 找回密码
			Intent intent = new Intent(getActivity(),
					FindPasswordActivity.class);
			startActivity(intent);
			break;
		case R.id.bt_login:// 登录
			if (TextUtils.isEmpty(et_mobile.getText().toString())) {
				Toast.makeText(getActivity(), "手机号不能为空", 1000).show();
				et_mobile.startAnimation(AnimationUtils.loadAnimation(
						getActivity(), R.anim.shake));
			} else if (!ToolsUtil.checkPhone(et_mobile.getText().toString())) {
				Toast.makeText(getActivity(), "手机号格式不正确", 1000).show();
			} else if (TextUtils.isEmpty(et_password.getText().toString())) {
				Toast.makeText(getActivity(), "密码不能为空", 1000).show();
				et_password.startAnimation(AnimationUtils.loadAnimation(
						getActivity(), R.anim.shake));
			} else {
				HttpControl httpControl = new HttpControl();
				httpControl.userLogin(et_mobile.getText().toString().trim(),
						et_password.getText().toString().trim(),
						new HttpCallBackInterface() {

							@Override
							public void http_Success(Object obj) {

								if (obj != null
										&& obj instanceof UserRequestBean) {
									UserRequestBean bean = (UserRequestBean) obj;
									Intent intent = new Intent(getActivity(),
											MainActivityForBaiJia.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(intent);
									getActivity().finish();
								}
							}

							@Override
							public void http_Fails(int error, String msg) {

								MyApplication.getInstance().showMessage(
										getActivity(), msg);
							}
						}, getActivity());
			}
		case R.id.tv_wechat:// 微信登录
			initWeiChatLogin();
		case R.id.tv_qq:// QQ登录
			initQQLogin();
		default:
			break;
		}

	}

	/**
	 * 初始化微信第三方登录
	 */
	public void initWeiChatLogin() {
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(getActivity(),
				"wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
		wxHandler.addToSocialSDK();
		mController.doOauthVerify(getActivity(), SHARE_MEDIA.WEIXIN,
				new UMAuthListener() {
					@Override
					public void onError(SocializeException e,
							SHARE_MEDIA platform) {
					}

					@Override
					public void onComplete(Bundle value, SHARE_MEDIA platform) {
						if (value != null
								&& !TextUtils.isEmpty(value.getString("uid"))) {
							Toast.makeText(getActivity(), "授权成功.",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getActivity(), "授权失败",
									Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onCancel(SHARE_MEDIA platform) {
					}

					@Override
					public void onStart(SHARE_MEDIA platform) {
					}
				});
		mController.doOauthVerify(getActivity(), SHARE_MEDIA.WEIXIN,
				new UMAuthListener() {
					@Override
					public void onStart(SHARE_MEDIA platform) {
						Toast.makeText(getActivity(), "授权开始",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onError(SocializeException e,
							SHARE_MEDIA platform) {
						Toast.makeText(getActivity(), "授权错误",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete(Bundle value, SHARE_MEDIA platform) {
						Toast.makeText(getActivity(), "授权完成",
								Toast.LENGTH_SHORT).show();
						// 获取相关授权信息
						mController.getPlatformInfo(getActivity(),
								SHARE_MEDIA.WEIXIN, new UMDataListener() {
									@Override
									public void onStart() {
										Toast.makeText(getActivity(),
												"获取平台数据开始...",
												Toast.LENGTH_SHORT).show();
									}

									@Override
									public void onComplete(int status,
											Map<String, Object> info) {
										if (status == 200 && info != null) {
											StringBuilder sb = new StringBuilder();
											Set<String> keys = info.keySet();
											for (String key : keys) {
												sb.append(key
														+ "="
														+ info.get(key)
																.toString()
														+ "\r\n");
											}
											Log.d("TestData", sb.toString());
										} else {
											Log.d("TestData", "发生错误：" + status);
										}
									}
								});
					}

					@Override
					public void onCancel(SHARE_MEDIA platform) {
						Toast.makeText(getActivity(), "授权取消",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	private void initQQLogin() {
		// 参数1为当前Activity， 参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(getActivity(),
				"100424468", "c7394704798a158208a74ab60104f0ba");
		qqSsoHandler.addToSocialSDK();

		mController.doOauthVerify(getActivity(), SHARE_MEDIA.QQ,
				new UMAuthListener() {
					@Override
					public void onStart(SHARE_MEDIA platform) {
						Toast.makeText(getActivity(), "授权开始",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onError(SocializeException e,
							SHARE_MEDIA platform) {
						Toast.makeText(getActivity(), "授权错误",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete(Bundle value, SHARE_MEDIA platform) {
						Toast.makeText(getActivity(), "授权完成",
								Toast.LENGTH_SHORT).show();
						// 获取相关授权信息
						mController.getPlatformInfo(getActivity(),
								SHARE_MEDIA.QQ, new UMDataListener() {
									@Override
									public void onStart() {
										Toast.makeText(getActivity(),
												"获取平台数据开始...",
												Toast.LENGTH_SHORT).show();
									}

									@Override
									public void onComplete(int status,
											Map<String, Object> info) {
										if (status == 200 && info != null) {
											StringBuilder sb = new StringBuilder();
											Set<String> keys = info.keySet();
											for (String key : keys) {
												sb.append(key
														+ "="
														+ info.get(key)
																.toString()
														+ "\r\n");
											}
											Log.d("TestData", sb.toString());
										} else {
											Log.d("TestData", "发生错误：" + status);
										}
									}
								});
					}

					@Override
					public void onCancel(SHARE_MEDIA platform) {
						Toast.makeText(getActivity(), "授权取消",
								Toast.LENGTH_SHORT).show();
					}
				});

	}
}
