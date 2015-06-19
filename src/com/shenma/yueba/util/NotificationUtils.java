package com.shenma.yueba.util;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;

import com.shenma.yueba.R;

/**
 * notificaion工具类
 * 
 * @author Administrator
 * 
 */
public class NotificationUtils {

	@SuppressLint("NewApi")
	public static void showNotification(Context ctx, String... text) {
		// 创建一个NotificationManager的引用
		NotificationManager notificationManager = (NotificationManager) ctx
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		// 定义Notification的各种属性
		Notification notification = new Notification(R.drawable.icon, text[1],
				System.currentTimeMillis());
		// FLAG_AUTO_CANCEL 该通知能被状态栏的清除按钮给清除掉
		// FLAG_NO_CLEAR 该通知不能被状态栏的清除按钮给清除掉
		// FLAG_ONGOING_EVENT 通知放置在正在运行
		// FLAG_INSISTENT 是否一直进行，比如音乐一直播放，知道用户响应
//		notification.flags |= Notification.FLAG_ONGOING_EVENT; // 将此通知放到通知栏的"Ongoing"即"正在运行"组中,加上该属性就不可以滑动删除了
		notification.flags |= Notification.FLAG_AUTO_CANCEL; // 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		// DEFAULT_ALL 使用所有默认值，比如声音，震动，闪屏等等
		// DEFAULT_LIGHTS 使用默认闪光提示
		// DEFAULT_SOUNDS 使用默认提示声音
		// DEFAULT_VIBRATE 使用默认手机震动，需加上<uses-permission
		// android:name="android.permission.VIBRATE" />权限
		// 叠加效果常量
		notification.defaults = Notification.DEFAULT_LIGHTS
				| Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
		notification.ledARGB = Color.BLUE;
		notification.ledOnMS = 60000; // 闪光时间，毫秒
		if (text.length >= 2) {
//			// 设置通知的事件消息
//			CharSequence contentTitle = text[1]; // 通知栏标题
//			CharSequence contentText = text[0]; // 通知栏内容
//			Intent notificationIntent = null;
//			if (Constants.FRIEND_TITLE.equals(contentTitle)) {// 好友通知
//				if (text.length == 3) {
//					if (Constants.ADD.equals(text[2])) {// 加好友
//						notificationIntent = new Intent(ctx,
//								FriendMsgActivity.class); // 点击该通知后要跳转的Activity
//					} else if (Constants.AGREE.equals(text[2])) {// 同意
//						if (Constants.defalt_buy_user.equals(SharedUtil
//								.getUserType(ctx))) {// 普通会员
//							notificationIntent = new Intent(ctx,
//									FriendManagerCustomerActivity.class); // 点击该通知后要跳转的Activity
//						} else if (Constants.defalt_user.equals(SharedUtil
//								.getUserType(ctx))) {// 商户
//							notificationIntent = new Intent(ctx,
//									BusinessPartnerActivity.class); // 点击该通知后要跳转的Activity
//						}
//					} else if (Constants.REFUSE.equals(text[2])) {// 拒绝
//						notificationIntent = new Intent(ctx,
//								FriendMsgActivity.class); // 点击该通知后要跳转的Activity
//					}
//				}
//			} else if (Constants.SYSTEM_TITLE.equals(contentTitle)) {// 系统通知
//				notificationIntent = new Intent(ctx,
//						SystemNotificationActivity.class); // 点击该通知后要跳转的Activity
//
//			}else if (Constants.SOCIAL_TITLE.equals(contentTitle)) {// 社交消息
//				if (text.length == 3) {
//					if (Constants.PUSH_ASK.equals(text[2])) {// 询价
//						notificationIntent = new Intent(ctx,
//								OfferInfoActivity.class); // 点击该通知后要跳转的Activity
//					}else if (Constants.PUSH_ORFER.equals(text[2])) {// 报价
//						notificationIntent = new Intent(ctx,
//								NeedInfoActivity.class); // 点击该通知后要跳转的Activity
//						notificationIntent.putExtra("from", "push");
//					}
//				}
//			}
//			PendingIntent contentItent = PendingIntent.getActivity(ctx, 0,
//					notificationIntent, 0);
//			notification.setLatestEventInfo(ctx, contentTitle, contentText,
//					contentItent);
//			// 把Notification传递给NotificationManager
//			notificationManager.notify(0, notification);
		}
		}
	}

