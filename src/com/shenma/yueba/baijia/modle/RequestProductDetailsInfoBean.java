package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-5-29 下午4:00:52  
 * 程序的简单说明  商品详情
 */

public class RequestProductDetailsInfoBean extends BaseRequest{
	ProductsInfoBean Data=new ProductsInfoBean();

	public ProductsInfoBean getData() {
		return Data;
	}

	public void setData(ProductsInfoBean data) {
		Data = data;
	}
}
