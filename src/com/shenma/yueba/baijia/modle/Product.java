package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

public class Product implements Serializable {

	private String onlineCount;// 在线商品数量
	private String soonDownCount;// 即将下线商品数量
	private String downCount;// 下线数量
	public String getOnlineCount() {
		return onlineCount;
	}
	public void setOnlineCount(String onlineCount) {
		this.onlineCount = onlineCount;
	}
	public String getSoonDownCount() {
		return soonDownCount;
	}
	public void setSoonDownCount(String soonDownCount) {
		this.soonDownCount = soonDownCount;
	}
	public String getDownCount() {
		return downCount;
	}
	public void setDownCount(String downCount) {
		this.downCount = downCount;
	}

	
	
}
