package com.shenma.yueba.baijia.modle;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-6 下午1:47:44  
 * 程序的简单说明  获取im 某一房间历史消息的对象
 */

public class RequestImMessageInfoBean extends BaseRequest{

	RequestImMessageInfo data =new RequestImMessageInfo();

	public RequestImMessageInfo getData() {
		return data;
	}

	public void setData(RequestImMessageInfo data) {
		this.data = data;
	}
}
