package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

public class Order implements Serializable{
	
	private String todayorder;//今日订单数
	private String allorder;//总订单数
	public String getTodayorder() {
		return todayorder;
	}
	public void setTodayorder(String todayorder) {
		this.todayorder = todayorder;
	}
	public String getAllorder() {
		return allorder;
	}
	public void setAllorder(String allorder) {
		this.allorder = allorder;
	}
	
	
	
	
	
}
