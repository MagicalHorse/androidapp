package com.shenma.yueba.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.shenma.yueba.util.LogUtil;
import com.shenma.yueba.util.ParserJson;


/**
 * 闹钟监听
 * @author a
 *
 */
public class MyAlertReciver extends BroadcastReceiver {

	private Context context;
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		//做相应的处理 
	}

	
	
	
}
