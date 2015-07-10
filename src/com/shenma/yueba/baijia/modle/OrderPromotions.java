package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-9 下午5:20:21  
 * 程序的简单说明  
 */

public class OrderPromotions implements Serializable{
	int PromotionId;//:活动id
    String PromotionName="";//活动名字
    double Amount;//活动金额
	public int getPromotionId() {
		return PromotionId;
	}
	public void setPromotionId(int promotionId) {
		PromotionId = promotionId;
	}
	public String getPromotionName() {
		return PromotionName;
	}
	public void setPromotionName(String promotionName) {
		PromotionName = promotionName;
	}
	public double getAmount() {
		return Amount;
	}
	public void setAmount(double amount) {
		Amount = amount;
	}

}
