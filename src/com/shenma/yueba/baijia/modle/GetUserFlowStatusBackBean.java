package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**
 * @author gyj
 * @version 创建时间：2015-5-11 上午11:05:49 程序的简单说明 本类用于 http 用户数据注册信息的返回类对象
 * 
 */

public class GetUserFlowStatusBackBean extends BaseRequest implements
		Serializable {
	private UserFlowStatusBean data;

	public UserFlowStatusBean getData() {
		return data;
	}

	public void setData(UserFlowStatusBean data) {
		this.data = data;
	}

}
