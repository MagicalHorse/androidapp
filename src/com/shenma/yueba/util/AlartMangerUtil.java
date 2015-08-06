package com.shenma.yueba.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * 闹钟管理
 * 
 * @author Administrator
 * 
 */
public class AlartMangerUtil {

	public static int heartBeetTimes = 1000 ;

	public static void startHeartAlart(Context ctx) {
		Intent intent = new Intent("HeartSocket");
		PendingIntent pIntent = PendingIntent.getBroadcast(ctx, 0, intent, 0);
		AlarmManager am = (AlarmManager) ctx
				.getSystemService(ctx.ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				heartBeetTimes, pIntent);
	}

	public static void closeHeartAlart(Context ctx) {
		Intent intent = new Intent("HeartSocket");
		PendingIntent pIntent = PendingIntent.getBroadcast(ctx, 0, intent, 0);
		AlarmManager am = (AlarmManager) ctx
				.getSystemService(ctx.ALARM_SERVICE);
		am.cancel(pIntent);
	}

}
