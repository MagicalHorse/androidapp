package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-7-7 下午7:35:48  
 * 程序的简单说明  
 */

public class RequestUploadChatImageInfoBean extends BaseRequest{

	RequestUploadChatImageInfo data=new RequestUploadChatImageInfo();

	public RequestUploadChatImageInfo getData() {
		return data;
	}

	public void setData(RequestUploadChatImageInfo data) {
		this.data = data;
	}
}
