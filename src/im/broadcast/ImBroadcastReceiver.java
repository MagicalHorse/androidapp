package im.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/******
 * 本类定义 im的广播监听 类 用于 接收 消息
 * 
 * *****/
public class ImBroadcastReceiver extends BroadcastReceiver{
public final static String IntentFilter="com.shenma.yueba.im";
ImBroadcastReceiverLinstener imBroadcastReceiverLinstener;
public enum RECEIVER_type
{
	connect,//链接成功
	newMessage,//新消息
	roomMessage//房间消息
}


    public ImBroadcastReceiver(ImBroadcastReceiverLinstener imBroadcastReceiverLinstener)
    {
    	this.imBroadcastReceiverLinstener=imBroadcastReceiverLinstener;
    }
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent!=null)
		{
			if(intent.getAction().equals(IntentFilter))
			{
				Object obj=intent.getSerializableExtra("Data");
				if(obj!=null)
				{
					if(imBroadcastReceiverLinstener!=null)
					{
						imBroadcastReceiverLinstener.newMessage(obj);
					}
				}
			}
		}
	}

	public interface ImBroadcastReceiverLinstener
	{
		/*****
		 * 接收到消息
		 * ****/
		void newMessage(Object obj);
	}
}
