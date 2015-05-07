package com.shenma.yueba.yangjia.modle;

import java.util.List;


public class SalesManagerForAttestationBuyerListBean {

	private String orderNumber;//订单号
	private String orderState;//订单状态
	private String productCount;//该订单有几种商品
	private String productPrice;//总支付价格
	private List<ProductItemBean> products;//商品列表
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getProductCount() {
		return productCount;
	}
	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public List<ProductItemBean> getProducts() {
		return products;
	}
	public void setProducts(List<ProductItemBean> products) {
		this.products = products;
	}
	
	
	

}
