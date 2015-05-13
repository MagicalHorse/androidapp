package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-5-12 下午8:21:46  
 * 程序的简单说明  本类定义省市信息接收类
 */

public class ProvinceCityListBeanRequest extends BaseRequest{
	ProvinceCityListBean data=new ProvinceCityListBean();

	public ProvinceCityListBean getData() {
		return data;
	}

	public void setData(ProvinceCityListBean data) {
		this.data = data;
	} 
}
