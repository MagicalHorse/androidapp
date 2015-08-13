package im.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/******
 * 本类定义 im的广播监听 类 用于 接收 消息
 * 
 * *****/
public class ImBroadcastReceiver extends BroadcastReceiver{
public final static String IntentFilter="com.shenma.yueba.im";//加入房间后 获去到的消息的过滤
public final static String IntentFilterRoomMsg="com.shenma.yueba.im.roonmsg";//不在房间内 获取消息的过滤
public final static String IntentFilterClearMsg="com.shenma.yueba.im.roonmsg";//清除消息红色原点的过来
ImBroadcastReceiverLinstener imBroadcastReceiverLinstener;
public static enum RECEIVER_type
{
	circle,//链接成功
	msg,//新消息
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
			}else if(intent.getAction().equals(IntentFilterRoomMsg))
			{
				if(imBroadcastReceiverLinstener!=null)
				{
					Object obj=intent.getSerializableExtra("Data");
					if(obj!=null)
					{
						imBroadcastReceiverLinstener.roomMessage(obj);
					}
				}
			}else if(intent.getAction().equals(IntentFilterClearMsg))
			{
				if(intent.getSerializableExtra("RECEIVER_type")!=null)
				{
					RECEIVER_type type=(RECEIVER_type)intent.getSerializableExtra("RECEIVER_type");
					imBroadcastReceiverLinstener.clearMsgNotation(type);
				}
			}
		}
	}

	public interface ImBroadcastReceiverLinstener
	{
		/*****
		 * 加入 房间内 接收到消息
		 * ****/
		void newMessage(Object obj);
		
		/*****
		 * 不在房间内 接收到消息
		 * ****/
		void roomMessage(Object obj);
		
		/******
		 * 清除原点
		 * @param type RECEIVER_type 对应的 数据类型
		 * ****/
		void clearMsgNotation(RECEIVER_type type);
	}
}
