package com.shenma.yueba.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * 开机情动的监听
 * @author a
 *
 */
public class BootReceiver extends BroadcastReceiver  {

	
	@Override
	public void onReceive(Context context, Intent intent) {
		 if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {//程序启动完成  
			 
//	            Intent newIntent = new Intent(context, XXX);  
//	            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //注意，必须添加这个标记，否则启动会失败  
//	            context.startActivity(newIntent);  
	        }  

	}

}
