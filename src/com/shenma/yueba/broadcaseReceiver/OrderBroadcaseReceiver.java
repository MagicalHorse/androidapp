package com.shenma.yueba.broadcaseReceiver;

import com.shenma.yueba.baijia.modle.BaiJiaOrderListInfo;
import com.shenma.yueba.util.ToolsUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/******
 * 本类定义 用于接收订单操作的 广播监听
 ****/
public class OrderBroadcaseReceiver extends BroadcastReceiver {
	
	public final static String IntentFilter = "com.baijia.yeba.order";
	OrderBroadcaseListener orderBroadcaseListener;
	
	public OrderBroadcaseReceiver(OrderBroadcaseListener orderBroadcaseListener) {
		this.orderBroadcaseListener = orderBroadcaseListener;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent != null && intent.getAction().equals(IntentFilter)) {
             if(orderBroadcaseListener!=null)
             {
            	 BaiJiaOrderListInfo info=null;
            	 if(intent.getSerializableExtra("Data")!=null && intent.getSerializableExtra("Data") instanceof BaiJiaOrderListInfo)
            	 {
            		 info =(BaiJiaOrderListInfo)intent.getSerializableExtra("Data");
            	 }
            	 
            	 orderBroadcaseListener.falshData(info,ToolsUtil.nullToString(intent.getStringExtra("ControlType")));
             }
		}
	}

	public interface OrderBroadcaseListener {
		void falshData(BaiJiaOrderListInfo info,String str);
	}
}
