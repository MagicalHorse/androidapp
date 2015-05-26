package com.shenma.yueba.yangjia.modle;

import java.util.List;

import com.shenma.yueba.baijia.modle.Product;

public class OrderItem {
	private String OrderNo;
	private String Status;
	private String StatusName;
	private String InCome;
	private String Amount;
	private String CreateTime;
	private List<ProductItemBean> Products;
	public String getOrderNo() {
		return OrderNo;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getStatusName() {
		return StatusName;
	}
	public void setStatusName(String statusName) {
		StatusName = statusName;
	}
	public String getInCome() {
		return InCome;
	}
	public void setInCome(String inCome) {
		InCome = inCome;
	}
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public List<ProductItemBean> getProducts() {
		return Products;
	}
	public void setProducts(List<ProductItemBean> products) {
		Products = products;
	}
	
	
	
	
}
