package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**
 * @author gyj
 * @version 创建时间：2015-5-11 上午11:05:49 
 * 程序的简单说明 本类用于 http 用户数据注册信息的返回类对象
 * 
 */

public class UserRequestBean implements Serializable{
	String isSuccessful = "";
	int statusCode = 200;
	String message = "";
	UserInfo data = new UserInfo();
	public String getIsSuccessful() {
		return isSuccessful;
	}
	public void setIsSuccessful(String isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public UserInfo getData() {
		return data;
	}
	public void setData(UserInfo data) {
		this.data = data;
	}

}
