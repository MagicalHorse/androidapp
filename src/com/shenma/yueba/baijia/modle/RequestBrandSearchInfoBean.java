package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-7-1 上午11:54:27  
 * 程序的简单说明  
 */

public class RequestBrandSearchInfoBean extends BaseRequest{

	BrandSearchInfoBean data=new BrandSearchInfoBean();

	public BrandSearchInfoBean getData() {
		return data;
	}

	public void setData(BrandSearchInfoBean data) {
		this.data = data;
	}
}
