package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-8 上午11:41:05  
 * 程序的简单说明  创建订单反馈详情
 */

public class CreatOrderInfoBean implements Serializable{
	String OrderNo;//" : 订单号 "115012080504",
    double TotalAmount;//订单金额 101.00,
    double DisCountAmount;//":优惠的金额
    double ActualAmount;//实付金额
    
	public double getActualAmount() {
		return ActualAmount;
	}
	public void setActualAmount(double actualAmount) {
		ActualAmount = actualAmount;
	}
	public String getOrderNo() {
		return OrderNo;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}
	public double getTotalAmount() {
		return TotalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		TotalAmount = totalAmount;
	}
	public double getDisCountAmount() {
		return DisCountAmount;
	}
	public void setDisCountAmount(double disCountAmount) {
		DisCountAmount = disCountAmount;
	}
	

	
}
