package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-16 下午6:06:02  
 * 程序的简单说明  
 */

public class BrandInfoInfo implements Serializable{

	int ProductId;//13013,商品编号
    String ProductName="";//苗敏-13013",商品名称
    String StoreItemNo="";//商店编号
    String CreatedDate="";
    String Pic="";//图片
	public int getProductId() {
		return ProductId;
	}
	public void setProductId(int productId) {
		ProductId = productId;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getStoreItemNo() {
		return StoreItemNo;
	}
	public void setStoreItemNo(String storeItemNo) {
		StoreItemNo = storeItemNo;
	}
	public String getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}
	public String getPic() {
		return Pic;
	}
	public void setPic(String pic) {
		Pic = pic;
	}
}
