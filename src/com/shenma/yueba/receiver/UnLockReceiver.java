package com.shenma.yueba.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UnLockReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 解锁
		if (intent != null
				&& Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {

			// 屏幕已经解锁
		}

	}

}