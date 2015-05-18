package com.shenma.yueba.baijia.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.shenma.yueba.R;

/**
 * 程序刚启动的时候，加载页面
 * 
 * @author zhou
 * 
 */
public class SplashActivity extends BaseActivity {
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loading_layout);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				skip(GuideActivity.class, true);
			}
		}, 2500);
//		TaskManagerFactory.createDataTaskManager().addTask(new ITask() {
//			@Override
//			public void onTaskNumChanged(int taskNum) {
//			}
//
//			@Override
//			public void execute() {
//				// 如果不是第一次安装
//				if (SharedUtil.getInstallFlag(mContext)) {
//					// String username = SharedUtil.getUserName(mContext);
//					// String password = SharedUtil.getUserPassword(mContext);
//					// if (username != null && !"".equals(username)
//					// && password != null && !"".equals(password)) {
//					// String result = NetUtil.login(SplashActivity.this,
//					// username, password);
//					// UserBean bean = ParserJson.user_detail(result);
//					// if (bean != null && bean.getQuery() != null) {
//					// if (ToolsUtil.isQuerySeccess(bean.getQuery())) {
//					// //保存用户信息
//					// SharedUtil.setUserInfo(mContext,
//					// bean.getUser_list(), username, password);
//					// skip(MainActivity.class, true);
//					// finish();
//					// } else {
//					// skip(LoginActivity.class, true);
//					// }
//					// } else {
//					// skip(LoginActivity.class, true);
//					// }
//					// } else {
//					// handler.postDelayed(new Runnable() {
//					// @Override
//					// public void run() {
//					// skip(LoginActivity.class, true);
//					// }
//					// }, 2500);
//					// }
//					//
//
//				} else {// 第一次安装
//					handler.postDelayed(new Runnable() {
//						@Override
//						public void run() {
//							skip(GuideActivity.class, true);
//						}
//					}, 2500);
//				}
//			}
//		});
	}

		
		

	public void onResume() {
		super.onResume();

	}

	public void onPause() {
		super.onPause();
	}

}
