package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-22 下午5:59:31  
 * 程序的简单说明  
 */

public class RequestUploadProductInfoBean extends BaseRequest implements Serializable{
	RequestUploadProductDataBean data =new RequestUploadProductDataBean();

	public RequestUploadProductDataBean getData() {
		return data;
	}

	public void setData(RequestUploadProductDataBean data) {
		this.data = data;
	}
}
