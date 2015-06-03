package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-6-2 下午5:57:55  
 * 程序的简单说明  败家请求订单返回信息类
 */

public class RequestBaiJiaOrderListInfoBean extends BaseRequest{
	BaiJiaOrderListInfoBean data=new BaiJiaOrderListInfoBean();

	public BaiJiaOrderListInfoBean getData() {
		return data;
	}

	public void setData(BaiJiaOrderListInfoBean data) {
		this.data = data;
	}
}
