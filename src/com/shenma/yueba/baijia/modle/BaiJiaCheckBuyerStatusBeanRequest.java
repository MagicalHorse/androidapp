package com.shenma.yueba.baijia.modle;

/******
 * 获取 是否可以养家的 数据对象
 * 
 * ***/
public class BaiJiaCheckBuyerStatusBeanRequest extends BaseRequest{
	BaiJiaCheckBuyerStatusBean  data=new BaiJiaCheckBuyerStatusBean();

	public BaiJiaCheckBuyerStatusBean getData() {
		return data;
	}

	public void setData(BaiJiaCheckBuyerStatusBean data) {
		this.data = data;
	}
}
