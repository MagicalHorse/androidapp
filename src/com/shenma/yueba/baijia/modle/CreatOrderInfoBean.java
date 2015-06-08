package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-8 上午11:41:05  
 * 程序的简单说明  创建订单反馈详情
 */

public class CreatOrderInfoBean implements Serializable{
	long orderno;//" : 订单号 "115012080504",
    double totalamount;//订单金额 101.00,
    double discountamount;//":优惠的金额
	public long getOrderno() {
		return orderno;
	}
	public void setOrderno(long orderno) {
		this.orderno = orderno;
	}
	public double getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(double totalamount) {
		this.totalamount = totalamount;
	}
	public double getDiscountamount() {
		return discountamount;
	}
	public void setDiscountamount(double discountamount) {
		this.discountamount = discountamount;
	}

	
}
