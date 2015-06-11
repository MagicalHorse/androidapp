package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-6-11 下午2:42:29  
 * 程序的简单说明   (败家 订单详情)
 */

public class RequestBaiJiaOrdeDetailsInfoBean extends BaseRequest{

	BaiJiaOrdeDetailsInfoBean data=new BaiJiaOrdeDetailsInfoBean();

	public BaiJiaOrdeDetailsInfoBean getData() {
		return data;
	}

	public void setData(BaiJiaOrdeDetailsInfoBean data) {
		this.data = data;
	}
}
