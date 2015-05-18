package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

public class Income implements Serializable {

	private String today_income;// 今日收益
	private String total_income;// 总收益

	public String getToday_income() {
		return today_income;
	}

	public void setToday_income(String today_income) {
		this.today_income = today_income;
	}

	public String getTotal_income() {
		return total_income;
	}

	public void setTotal_income(String total_income) {
		this.total_income = total_income;
	}

	

}
