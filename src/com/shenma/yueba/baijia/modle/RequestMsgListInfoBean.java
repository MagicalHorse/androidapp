package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-7-6 下午6:53:09  
 * 程序的简单说明   败家消息对象
 */

public class RequestMsgListInfoBean extends BaseRequest{

	RequestMsgListInfo data=new RequestMsgListInfo();
	public RequestMsgListInfo getData() {
		return data;
	}
	public void setData(RequestMsgListInfo data) {
		this.data = data;
	}


}
