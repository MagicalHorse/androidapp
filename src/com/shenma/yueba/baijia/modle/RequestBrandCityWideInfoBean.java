package com.shenma.yueba.baijia.modle;


/**  
 * @author gyj  
 * @version 创建时间：2015-6-5 上午10:56:59  
 * 程序的简单说明  (同城 发现 同城界面 获取数据)
 */

public class RequestBrandCityWideInfoBean extends BaseRequest {
	BrandCityWideInfoBean data=new BrandCityWideInfoBean();

	public BrandCityWideInfoBean getData() {
		return data;
	}

	public void setData(BrandCityWideInfoBean data) {
		this.data = data;
	}
}
