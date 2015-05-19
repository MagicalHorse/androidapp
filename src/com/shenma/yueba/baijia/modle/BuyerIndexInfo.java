package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gyj
 * @version 创建时间：2015-5-10 下午5:55:12 程序的简单说明
 */

public class BuyerIndexInfo extends BaseRequest implements Serializable {

	private String barcode;
	private String shopname;
	private Product product;
	private Favorite favorite;
	private Order order;
	private Income income;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Favorite getFavorite() {
		return favorite;
	}
	public void setFavorite(Favorite favorite) {
		this.favorite = favorite;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Income getIncome() {
		return income;
	}
	public void setIncome(Income income) {
		this.income = income;
	}
	
	
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}


	
	

}
