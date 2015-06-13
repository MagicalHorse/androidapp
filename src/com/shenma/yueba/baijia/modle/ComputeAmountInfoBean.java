package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-13 下午2:01:14  
 * 程序的简单说明  
 */

public class ComputeAmountInfoBean implements Serializable{

	double price;//价格
    int totalquantity;//商品数量,
    double totalfee;//共运费,
    double totalamount;//总金额,
    double extendprice;// 数量 * 销售价
    double discountamount;// 折扣 50.0,
    double saletotalamount;//折扣后售价 655.00
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getTotalquantity() {
		return totalquantity;
	}
	public void setTotalquantity(int totalquantity) {
		this.totalquantity = totalquantity;
	}
	public double getTotalfee() {
		return totalfee;
	}
	public void setTotalfee(double totalfee) {
		this.totalfee = totalfee;
	}
	public double getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(double totalamount) {
		this.totalamount = totalamount;
	}
	public double getExtendprice() {
		return extendprice;
	}
	public void setExtendprice(double extendprice) {
		this.extendprice = extendprice;
	}
	public double getDiscountamount() {
		return discountamount;
	}
	public void setDiscountamount(double discountamount) {
		this.discountamount = discountamount;
	}
	public double getSaletotalamount() {
		return saletotalamount;
	}
	public void setSaletotalamount(double saletotalamount) {
		this.saletotalamount = saletotalamount;
	}

}
