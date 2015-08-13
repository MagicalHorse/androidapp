package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/***
 * 
 * 获取 是否可以养家的 数据对象
 * *****/


public class BaiJiaCheckBuyerStatusBean implements Serializable{

	int  Status;// -1  不可以养家  0  可以提交资料    1  已经是买手 可以进入首页
     String Message="";
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}

}
