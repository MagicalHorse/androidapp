package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-5-29 下午4:00:52  
 * 程序的简单说明  商品详情
 */

public class RequestProductDetailsInfoBean extends BaseRequest{
	ProductsDetailsInfoBean data=new ProductsDetailsInfoBean();

	public ProductsDetailsInfoBean getData() {
		return data;
	}

	public void setData(ProductsDetailsInfoBean data) {
		this.data = data;
	}

	
}
