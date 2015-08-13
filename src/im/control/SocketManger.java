package im.control;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.github.nkzawa.emitter.Emitter.Listener;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.util.SharedUtil;

import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import im.broadcast.ImBroadcastReceiver;
import im.form.MessageBean;
import im.form.RequestMessageBean;
import im.form.RoomBean;

/****
 * 通信管理 本类定义 im 通信 管理类 提供  链接服务器  断开服务器   进入房间 及 发送消息等方法
 * **/
public class SocketManger {
	static Socket socket;
	static SocketManger socketManger;
	final String URL = "http://182.92.7.70:8000/chat";//服务器地址
	//final String URL = "http://192.168.1.145:8000/chat";
    List<MessageBean> mssageBean_list=new ArrayList<MessageBean>();
    String userId=null;
    RoomBean roomBean=null;
	private SocketManger() {
	}

	/****
	 * 获取  SocketManger 对象
	 * ****/
	public static SocketManger the()
	{
		if(socketManger==null)
		{
			socketManger=new SocketManger();
		}
		return socketManger;
	}
	
	/***
	 * 建立通信连接
	 * @param listener  SocketManagerListener 监听对象
	 * ***/
	public synchronized void contentSocket() {
		//如果对象为null 或者 当前没有链接  则进行链接
		if(socket==null)
		{
			try {
				socket = IO.socket(URL);
				//注销事件监听
				unsetListtener();
				//设置事件监听
				setListtener();
				//链接
				socket.connect();
				Log.i("TAG", "---->>>socket create");
			} catch (Exception e) {
				e.printStackTrace();
				Log.i("TAG", "---->>>socket error"+e.getMessage());
			}
		}else if(!socket.connected())
		{
			Log.i("TAG", "---->>>socket unconnect");
			socket.close();
			socket.connect();
		}else if(socket.connected())
		{
			onLineToUserID();
		}
	}

	/***
	 * 断开通信连接
	 * ***/
	public synchronized void disContentSocket() {
		if(socket!=null)
		{
			unsetListtener();
			if (isConnect()) {
				socket.disconnect();
			}
		}
		socket = null;
	}

	
	/***
	 * 发送信息
	 * @param messageBean MessageBean 发送信息
	 * ***/
	public  void sendMsg(MessageBean messageBean) {
		if(!mssageBean_list.contains(messageBean))
		{
			mssageBean_list.add(messageBean);
		}
		if (isConnect()) {
			// Map<String, String> data2=getMap(messageBean);
			 Gson gson=new Gson();
			 String json=gson.toJson(messageBean);
			 Log.i("TAG","---->>>socket  sendMsg--->>"+json);
			 Log.i("TAG","---->>>socket sendMsg--->>user_id"+messageBean.getToUserId());
			try {
				socket.emit("sendMessage", new JSONObject(json), new Ack() {

					@Override
					public void call(Object... arg0) {
						Log.i("TAG", "sendMessage " + arg0.toString());
					}
				});
				if(mssageBean_list.contains(messageBean))
				{
					mssageBean_list.remove(messageBean);
				}
			} catch (Exception e) {
				Log.i("TAG", e.getMessage());
			}
		} else {
			contentSocket();
			Log.i("TAG", "未连接");
		}
	}

	/******
	 * 判断是否建立连接
	 * ****/
	public boolean isConnect() {
		if (socket == null || !(socket.connected())) {
			return false;
		} else {
			return true;
		}
	}

	/******
	 * 监听
	 * **/
	void setListtener() {
		// 连接监听
		socket.on(Socket.EVENT_CONNECT, new Listener() {

			@Override
			public void call(Object... arg0) {
				 Log.i("TAG","---->>>socket  Socket.EVENT_CONNECT");
				//登录
				onLineToUserID();
				inroon(userId, roomBean);
			}
		});
		// 连接失败监听
		socket.on(Socket.EVENT_CONNECT_ERROR, new Listener() {

			@Override
			public void call(Object... arg0) {
				SystemClock.sleep(800);
				Log.i("TAG", "---->>>socket Socket.EVENT_CONNECT_ERROR   arg0:"+arg0);
				contentSocket();
			}
		});

		// 连接超时
		socket.on(Socket.EVENT_CONNECT_TIMEOUT, new Listener() {

			@Override
			public void call(Object... arg0) {
				Log.i("TAG", "---->>>socket Socket.EVENT_CONNECT_TIMEOUT");
				contentSocket();
			}
		});

		// 重新连接监听
		socket.on(Socket.EVENT_RECONNECT,new Listener() {

			@Override
			public void call(Object... arg0) {
				Log.i("TAG", "---->>>socket Socket.EVENT_DISCONNECT");
				//登录
				onLineToUserID();
			}
		});
		
		// 断开连接监听
		socket.on(Socket.EVENT_DISCONNECT, new Listener() {

			@Override
			public void call(Object... arg0) {
				Log.i("TAG", "---->>>socket Socket.EVENT_DISCONNECT");
				
			}
		});

		/*****
		 * 加入房间后 获取到的 信息
		 * ***/
		socket.on("new message", new Listener() {
			
			@Override
			public void call(Object... arg0) {
				if(arg0[0]!=null && arg0[0] instanceof JSONObject)
				{
				JSONObject json=(JSONObject)arg0[0];
				Log.i("TAG", "---->>>socket new message");	
				Gson gson=new Gson();
				Object obj=gson.fromJson(json.toString(), RequestMessageBean.class);
				if(obj!=null && obj instanceof RequestMessageBean)
				{
					RequestMessageBean requestMessageBean=(RequestMessageBean)obj;
					sendMsgImBroadcast(requestMessageBean);
				}
				}
			}
		});
		
		/****
		 * 不在当前房间 接收到的消息
		 * */
         socket.on("room message", new Listener() {
			
			@Override
			public void call(Object... arg0) {
				if(arg0[0]!=null && arg0[0] instanceof JSONObject)
				{
					JSONObject json=(JSONObject)arg0[0];
					Log.i("TAG", "---->>>socket room message");	
					Gson gson=new Gson();
					Object obj=gson.fromJson(json.toString(), RequestMessageBean.class);
					if(obj!=null && obj instanceof RequestMessageBean)
					{
						RequestMessageBean requestMessageBean=(RequestMessageBean)obj;
						Intent intent=new Intent();
						intent.setAction(ImBroadcastReceiver.IntentFilterRoomMsg);
						intent.putExtra("Data", requestMessageBean);
						MyApplication.getInstance().getApplicationContext().sendBroadcast(intent);
					}
				}
			}
		});
		
		
		
		// 发送消息监听
		socket.on(Socket.EVENT_MESSAGE, new Listener() {

			@Override
			public void call(Object... arg0) {
				
				Log.i("TAG", "---->>>socket Socket.EVENT_MESSAGE");
			}
		});

	}
	
	
	
	/******
	 * 注销监听
	 * **/
	void unsetListtener() {
		// 连接监听
		socket.off(Socket.EVENT_CONNECT);
		// 连接失败监听
		socket.off(Socket.EVENT_CONNECT_ERROR);

		// 连接超时
		socket.off(Socket.EVENT_CONNECT_TIMEOUT);

		// 断开连接监听
		socket.off(Socket.EVENT_DISCONNECT);

		socket.off("new message");
		socket.off("room message");
		// 发送消息监听
		socket.off(Socket.EVENT_MESSAGE);
		// 重新连接监听
		socket.off(Socket.EVENT_RECONNECT);
	}

	/****
	 * 进入房间
	 * @param owner String 进入者id
	 * @param bean RoomBean 信息
	 * ***/
	public void inroon(String userId,RoomBean bean) {
		//Map<String, String> data2=getMap(bean);
		//Log.i("TAG", new JSONObject(data2).toString());
		if(userId!=null && bean!=null)
		{
			this.userId=userId;
			roomBean=bean;
			Gson gson=new Gson();
			String json=gson.toJson(bean);
			Log.i("TAG", json);
			try {
				Log.i("TAG", "---->>>socket inroom    json:"+json);
				socket.emit("join room", userId, new JSONObject(json), new Ack() {

					@Override
					public void call(Object... arg0) {
						Log.i("TAG", "---->>>socket inroom");
					}
				});
			} catch (Exception e) {
				Log.i("TAG", "---->>>socket inroom error:"+e.getMessage());
			}
		}
		
	}
	
	
	/****
	 * 设置登录的 用户id
	 * @param owner String 进入者id
	 * @param bean RoomBean 信息
	 * ***/
	public void online(int userId) {
		if(socket.connected())
		{
			try {
				Log.i("TAG", "---->>>socket online  userId:"+userId);
				socket.emit("online", userId, new Ack() {

					@Override
					public void call(Object... arg0) {
						Log.i("TAG", "---->>>socket online");
					}
				});
			} catch (Exception e) {
				Log.i("TAG", "---->>>socket online error:"+e.getMessage());
			}
		}else
		{
			contentSocket();
		}
		
	}
	
	
	/****
	 * 推出房间
	 * @param owner String 进入者id
	 * @param bean RoomBean 信息
	 * ***/
	public void outinroon() {
		
		try {
			roomBean=null;
			Log.i("TAG", "---->>>socket outinroon");
			socket.emit("leaveRoom", new Ack() {

				@Override
				public void call(Object... arg0) {
					Log.i("TAG", "---->>>socket outinroon");
				}
			});
		} catch (Exception e) {
			Log.i("TAG", "---->>>socket outinroon error:"+e.getMessage());
		}
	}
	
	/****
	 * 进行在线登录
	 * *****/
	public void onLineToUserID()
	{
		if(SharedUtil.getStringPerfernece(MyApplication.getInstance().getApplicationContext(), SharedUtil.user_id)!=null)
		{
			String str=SharedUtil.getStringPerfernece(MyApplication.getInstance().getApplicationContext(), SharedUtil.user_id);
			Log.i("TAG", "---->>>socket onLineToUserID user_id:"+str);
			if(!str.equals(""))
			{
				int userid=Integer.parseInt(str);
				online(userid);
			}
			
		}
	}
	
	/******
	 * 发送广播 通知 数据更新
	 * ****/
	void sendMsgImBroadcast(RequestMessageBean bean)
	{
		Intent intent=new Intent();
		intent.setAction(ImBroadcastReceiver.IntentFilter);
		intent.putExtra("Data", bean);
		MyApplication.getInstance().getApplicationContext().sendBroadcast(intent);
	}
}
