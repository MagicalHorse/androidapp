package com.shenma.yueba.broadcaseReceiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.NotificationUtils;
import com.shenma.yueba.util.SharedUtil;

public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        String content = bundle.getString(JPushInterface.EXTRA_ALERT);
        Toast.makeText(context, title+"--------"+content, 1000).show();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction()
				+ ", extras: " + printBundle(bundle));
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle
					.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
			// send the Registration Id to your server...
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			// Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " +
			// bundle.getString(JPushInterface.EXTRA_MESSAGE));
			if (bundle.getString(JPushInterface.EXTRA_MESSAGE).contains("-")) {
				if (Constants.PUSH_TAG_ADDFRIEND.equals(bundle.getString(
						JPushInterface.EXTRA_MESSAGE).split("-")[0])) {// 加好友
					if (SharedUtil.getFriendMsgState(context)) {
						NotificationUtils.showNotification(context,
								bundle.getString(JPushInterface.EXTRA_MESSAGE)
										.split("-")[1], Constants.FRIEND_TITLE,Constants.ADD);
					}
				} else if (Constants.PUSH_TAG_SYSTEM.equals(bundle.getString(
						JPushInterface.EXTRA_MESSAGE).split("-")[0])) {// 系统消息
					if (SharedUtil.getSystemMsgState(context)) {
						String data = bundle.getString(JPushInterface.EXTRA_MESSAGE);
						NotificationUtils.showNotification(context,
								bundle.getString(JPushInterface.EXTRA_MESSAGE)
										.split("-")[1], Constants.SYSTEM_TITLE);
					}
				}  else if (Constants.PUSH_TAG_REFUSE_FRIEND.equals(bundle
						.getString(JPushInterface.EXTRA_MESSAGE).split("-")[0])) {// 拒绝加好友
					if (SharedUtil.getFriendMsgState(context)) {
						NotificationUtils.showNotification(context,
								bundle.getString(JPushInterface.EXTRA_MESSAGE)
										.split("-")[1], Constants.FRIEND_TITLE,Constants.REFUSE);
					}
				}
			}
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
			int notifactionId = bundle
					.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			// Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			// //打开自定义的Activity
			// Intent i = new Intent(context, MainActivity.class);
			// i.putExtras(bundle);
			// //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
			// Intent.FLAG_ACTIVITY_CLEAR_TOP );
			// context.startActivity(i);

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
				.getAction())) {
			Log.d(TAG,
					"[MyReceiver] 用户收到到RICH PUSH CALLBACK: "
							+ bundle.getString(JPushInterface.EXTRA_EXTRA));
			// 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
			// 打开一个网页等..

		} else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
				.getAction())) {
			boolean connected = intent.getBooleanExtra(
					JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Log.w(TAG, "[MyReceiver]" + intent.getAction()
					+ " connected state change to " + connected);
		} else {
			Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
		}
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}


}