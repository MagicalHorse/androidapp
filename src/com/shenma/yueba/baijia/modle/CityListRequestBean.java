package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gyj
 * @version 创建时间：2015-5-10 下午5:55:12 程序的简单说明
 */

public class CityListRequestBean implements Serializable{
	List<CityBeanList> data = new ArrayList<CityBeanList>();
	public List<CityBeanList> getData() {
		return data;
	}
	public void setData(List<CityBeanList> data) {
		this.data = data;
	}
	int statusCode=0;
	String message="";
	boolean isSuccessful=false;
	
	public boolean isSuccessful() {
		return isSuccessful;
	}
	public void setSuccessful(boolean isSuccessful) {
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
	
}
