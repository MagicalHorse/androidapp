package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

public class MyCircleBean implements Serializable{

	private String imageUrl;
	private String productName;
	private String msg;
	private String time;
	private String msgCount;
	private String attetionCount;
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMsgCount() {
		return msgCount;
	}
	public void setMsgCount(String msgCount) {
		this.msgCount = msgCount;
	}
	public String getAttetionCount() {
		return attetionCount;
	}
	public void setAttetionCount(String attetionCount) {
		this.attetionCount = attetionCount;
	}

	
	
}
