package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**
 * @author gyj
 * @version 创建时间：2015-5-11 上午11:05:49 
 * 程序的简单说明 本类用于 http 用户数据注册信息的返回类对象
 * 
 */

public class UserRequestBean extends BaseRequest implements Serializable{
	UserInfo data = new UserInfo();
	public UserInfo getData() {
		return data;
	}
	public void setData(UserInfo data) {
		this.data = data;
	}

}
