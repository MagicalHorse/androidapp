package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-4 下午2:01:59  
 * 程序的简单说明  
 */

public class MyCircleInfo implements Serializable{

	int Id;
	String Name="";
	String Logo="";//圈子头像
	int UnReadCount;//未读数
    String UnReadMessage="";
    String UnReadLastTime="";
    String RoomId="";
    
	public int getUnReadCount() {
		return UnReadCount;
	}
	public void setUnReadCount(int unReadCount) {
		UnReadCount = unReadCount;
	}
	public String getUnReadMessage() {
		return UnReadMessage;
	}
	public void setUnReadMessage(String unReadMessage) {
		UnReadMessage = unReadMessage;
	}
	public String getUnReadLastTime() {
		return UnReadLastTime;
	}
	public void setUnReadLastTime(String unReadLastTime) {
		UnReadLastTime = unReadLastTime;
	}
	public String getRoomId() {
		return RoomId;
	}
	public void setRoomId(String roomId) {
		RoomId = roomId;
	}
	
	public String getLogo() {
		return Logo;
	}
	public void setLogo(String logo) {
		Logo = logo;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}

}
