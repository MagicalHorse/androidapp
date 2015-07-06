package im.form;

import im.control.ChatBaseManager;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-3 下午3:56:30  
 * 程序的简单说明  基础信息类
 */

public abstract class  BaseChatBean implements Serializable{

	public enum ChatType
	{
		link_type,//链接信息
		pic_type,//图片信息
		text_trype//文本信息
	}
	
	
	public enum SendStatus
	{
		send_unsend,//未发送
		send_sucess,//发送成功
		send_loading,//发送中
		send_fails//发送失败
	}
	
	String room_No="";//房间号
	int from_id;//发送者id
	int to_id;//接受者id
	Object content;//信息内容
	boolean isoneself=false;//是否是自己发送的信息
	ChatBaseManager baseManager;
	
	SendStatus sendStatus=SendStatus.send_unsend;
	ChatType chattype;//信息类型
	
	
	public ChatType getChattype() {
		return chattype;
	}
	public void setChattype(ChatType chattype) {
		this.chattype = chattype;
	}
	public SendStatus getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(SendStatus sendStatus) {
		this.sendStatus = sendStatus;
	}
	public ChatBaseManager getBaseManager() {
		return baseManager;
	}
	public void setBaseManager(ChatBaseManager baseManager) {
		this.baseManager = baseManager;
	}
	public String getRoom_No() {
		return room_No;
	}
	public void setRoom_No(String room_No) {
		this.room_No = room_No;
	}
	public int getFrom_id() {
		return from_id;
	}
	public void setFrom_id(int from_id) {
		this.from_id = from_id;
	}
	public int getTo_id() {
		return to_id;
	}
	public void setTo_id(int to_id) {
		this.to_id = to_id;
	}
	
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public boolean isIsoneself() {
		return isoneself;
	}
	public void setIsoneself(boolean isoneself) {
		this.isoneself = isoneself;
	}
	
	/******
	 * 发送数据
	 * ***/
	public abstract void sendData();
	/***
	 * 赋值 讲 接收到的消息数据 进行赋值
	 * **/
	public abstract void setValue(Object bean);
}
