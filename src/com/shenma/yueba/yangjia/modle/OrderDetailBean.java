package com.shenma.yueba.yangjia.modle;

import com.shenma.yueba.baijia.modle.BaseRequest;

public class OrderDetailBean extends BaseRequest{

	
	private String OrderNo;	//订单编号
	private String StatusName;//订单状态
	private String TotalAmount; //总金额
	private String RecAmount;  //实付金额
	private String InCome;//买手佣金
	private String CreateTime; //创建时间
	private String CustomerName;//客户名字
	private String CustomerMobile; //客户电话
	private String CustomerAddress;//客户地址
	private String CustomerLogo;//客户头像
	private String CustomerId;//客户id
	public String getOrderNo() {
		return OrderNo;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}
	public String getStatusName() {
		return StatusName;
	}
	public void setStatusName(String statusName) {
		StatusName = statusName;
	}
	public String getTotalAmount() {
		return TotalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		TotalAmount = totalAmount;
	}
	public String getRecAmount() {
		return RecAmount;
	}
	public void setRecAmount(String recAmount) {
		RecAmount = recAmount;
	}
	public String getInCome() {
		return InCome;
	}
	public void setInCome(String inCome) {
		InCome = inCome;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	public String getCustomerMobile() {
		return CustomerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		CustomerMobile = customerMobile;
	}
	public String getCustomerAddress() {
		return CustomerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		CustomerAddress = customerAddress;
	}
	public String getCustomerLogo() {
		return CustomerLogo;
	}
	public void setCustomerLogo(String customerLogo) {
		CustomerLogo = customerLogo;
	}
	public String getCustomerId() {
		return CustomerId;
	}
	public void setCustomerId(String customerId) {
		CustomerId = customerId;
	}
	
	
}
