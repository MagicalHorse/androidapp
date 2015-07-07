package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-6 下午4:03:00  
 * 程序的简单说明  获取房间信息对象
 */

public class RequestRoomInfo implements Serializable{
	int id;//房间id
    String title;
    String owner;//创建人di
    //String[] users;//圈内成员id
    String type;
    String customer_id;
    String buyer_id;
    List<Integer> userList=new ArrayList<Integer>();
    
    
	
	public List<Integer> getUserList() {
		return userList;
	}
	public void setUserList(List<Integer> userList) {
		this.userList = userList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/*public String[] getUsers() {
		return users;
	}
	public void setUsers(String[] users) {
		this.users = users;
	}*/
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

}
