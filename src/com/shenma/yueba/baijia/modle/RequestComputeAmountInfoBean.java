package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-6-13 下午2:00:12  
 * 程序的简单说明  计算订单商品金额请求对象
 */

public class RequestComputeAmountInfoBean extends BaseRequest{
	ComputeAmountInfoBean data=new ComputeAmountInfoBean();

	public ComputeAmountInfoBean getData() {
		return data;
	}

	public void setData(ComputeAmountInfoBean data) {
		this.data = data;
	}
	
}
