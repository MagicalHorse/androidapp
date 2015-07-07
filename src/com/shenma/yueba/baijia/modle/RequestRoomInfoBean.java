package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-7-6 下午4:02:26  
 * 程序的简单说明  获取房间信息对象
 */

public class RequestRoomInfoBean extends BaseRequest{
	RequestRoomInfo data=new RequestRoomInfo();

	public RequestRoomInfo getData() {
		return data;
	}

	public void setData(RequestRoomInfo data) {
		this.data = data;
	}
}
