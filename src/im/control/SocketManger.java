package im.control;

import im.form.MessageBean;
import im.form.RequestMessageBean;
import im.form.RoomBean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter.Listener;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

/****
 * 通信管理
 * **/
public class SocketManger {
	static Socket socket;
	static SocketManger socketManger;
	final String URL = "http://182.92.7.70:8001/chat";
	//final String URL = "http://192.168.1.145:8000/chat";
	SocketManagerListener listener;
    List<MessageBean> mssageBean_list=new ArrayList<MessageBean>();
    
	private SocketManger() {
	}

	
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
	 * ***/
	public synchronized void contentSocket(SocketManagerListener listener) {
		this.listener=listener;
		if(socket==null)
		{
			try {
				socket = IO.socket(URL);
				unsetListtener();
				setListtener();
				socket.connect();
				Log.i("TAG", "---->>>socket create");
			} catch (Exception e) {
				e.printStackTrace();
				Log.i("TAG", "---->>>socket error"+e.getMessage());
			}
		}else if(!socket.connected())
		{
			Log.i("TAG", "---->>>socket unconnect");
			socket.connect();
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
	 * ***//*
	public void sendMsg(String fromUserId, String toUserId, String userName,
			String productId, String body, String fromUserType, String type) {
		if(!isConnect())
		{
			contentSocket();---------------------------------------------
		}else
		{
			Map<String, String> data2 = new HashMap<String, String>();
			data2.put("fromUserId", fromUserId);
			data2.put("toUserId", toUserId);
			data2.put("userName", userName);
			data2.put("productId", productId);
			data2.put("body", body);
			data2.put("fromUserType", fromUserType);
			data2.put("type", type);

			socket.emit("sendMessage", new JSONObject(data2), new Ack() {

				@Override
				public void call(Object... arg0) {
					Log.i("TAG", "sendMessage " + arg0.toString());
				}
			});
		}
	}
*/
	/***
	 * 发送信息
	 * @param messageBean MessageBean 发送信息
	 * ***/
	public  void sendMsg(MessageBean messageBean,SocketManagerListener listener) {
		this.listener=listener;
		if(!mssageBean_list.contains(messageBean))
		{
			mssageBean_list.add(messageBean);
		}
		if (isConnect()) {
			// Map<String, String> data2=getMap(messageBean);
			 Gson gson=new Gson();
			 String json=gson.toJson(messageBean);
			 Log.i("TAG",json);
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
			contentSocket(listener);
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

				Log.i("TAG", "---->>>socket Socket.EVENT_CONNECT");
				if (listener != null) {
					listener.connectSucess(arg0);
				}
				/*for(int i=0;i<mssageBean_list.size();i++)
				{
					sendMsg(mssageBean_list.get(i));
				}*/
			}
		});
		// 连接失败监听
		socket.on(Socket.EVENT_CONNECT_ERROR, new Listener() {

			@Override
			public void call(Object... arg0) {
				Log.i("TAG", "---->>>socket Socket.EVENT_CONNECT_ERROR");
				if (listener != null) {
					listener.error(Socket.EVENT_CONNECT_ERROR, arg0);
				}
				
			}
		});

		// 连接超时
		socket.on(Socket.EVENT_CONNECT_TIMEOUT, new Listener() {

			@Override
			public void call(Object... arg0) {
				Log.i("TAG", "---->>>socket Socket.EVENT_CONNECT_TIMEOUT");
				if (listener != null) {
					listener.error(Socket.EVENT_CONNECT_TIMEOUT, arg0);
				}
				
			}
		});

		// 断开连接监听
		socket.on(Socket.EVENT_DISCONNECT, new Listener() {

			@Override
			public void call(Object... arg0) {
				Log.i("TAG", "---->>>socket Socket.EVENT_DISCONNECT");
				if (listener != null) {
					listener.disconnectSucess(arg0);
				}
				
			}
		});

		socket.on("new message", new Listener() {
			
			@Override
			public void call(Object... arg0) {
				if(arg0[0]!=null && arg0[0] instanceof JSONObject)
				{
				JSONObject json=(JSONObject)arg0[0];
				Log.i("TAG", "---->>>socket new message");	
				if (listener != null) {
					
					Gson gson=new Gson();
					Object obj=gson.fromJson(json.toString(), RequestMessageBean.class);
					if(obj!=null && obj instanceof RequestMessageBean)
					{
						RequestMessageBean requestMessageBean=(RequestMessageBean)obj;
						listener.sendMsgListener(requestMessageBean);
					}
					
				}
				}
			}
		});
		// 发送消息监听
		socket.on(Socket.EVENT_MESSAGE, new Listener() {

			@Override
			public void call(Object... arg0) {
				if (listener != null) {
					listener.sendMsgListener(arg0);
				}
				Log.i("TAG", "---->>>socket Socket.EVENT_MESSAGE");
			}
		});

		// 重新连接监听
		socket.on(Socket.EVENT_RECONNECT, new Listener() {

			@Override
			public void call(Object... arg0) {
				if (listener != null) {
					listener.connectSucess(arg0);
				}
				Log.i("TAG", "---->>>socket Socket.EVENT_RECONNECT");
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
		// 发送消息监听
		socket.off(Socket.EVENT_MESSAGE);
		// 重新连接监听
		socket.off(Socket.EVENT_RECONNECT);
	}

	/*public void inroon(String owner, String room_id, String type,
			String userName) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("owner", owner);
		data.put("room_id", room_id);
		data.put("sessionId", "");
		data.put("signValue", "");
		data.put("title", "");
		data.put("token", "");
		data.put("type", type);
		data.put("userName", userName);
		
		 * Gson gson=new Gson(); String json= gson.toJson(bean);
		 
		socket.emit("join room", owner, new JSONObject(data), new Ack() {

			@Override
			public void call(Object... arg0) {
				Log.i("TAG", "join room " + arg0.toString());
			}
		});
	}
*/
	/****
	 * 进入房间
	 * @param owner String 进入者id
	 * @param bean RoomBean 信息
	 * ***/
	public void inroon(String owner,RoomBean bean) {
		//Map<String, String> data2=getMap(bean);
		//Log.i("TAG", new JSONObject(data2).toString());
		Gson gson=new Gson();
		String json=gson.toJson(bean);
		Log.i("TAG", json);
		try {
			Log.i("TAG", "---->>>socket inroom");
			socket.emit("join room", owner, new JSONObject(json), new Ack() {

				@Override
				public void call(Object... arg0) {
					Log.i("TAG", "---->>>socket inroom");
				}
			});
		} catch (Exception e) {
			Log.i("TAG", "---->>>socket inroom error:"+e.getMessage());
		}
	}
	
	
	/****
	 * 推出房间
	 * @param owner String 进入者id
	 * @param bean RoomBean 信息
	 * ***/
	public void outinroon() {
		
		try {
			Log.i("TAG", "---->>>socket inroom");
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

	public interface SocketManagerListener {
		void error(Object... obj);

		void connectSucess(Object... obj);

		void disconnectSucess(Object... obj);

		void sendMsgListener(Object obj);//接收消息
	}
}
