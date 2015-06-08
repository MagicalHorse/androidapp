package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-6-8 上午11:40:13  
 * 程序的简单说明  创建订单 反馈信息
 */

public class RequestCreatOrderInfoBean extends BaseRequest{
	CreatOrderInfoBean data=new CreatOrderInfoBean();

	public CreatOrderInfoBean getData() {
		return data;
	}

	public void setData(CreatOrderInfoBean data) {
		this.data = data;
	}
}
