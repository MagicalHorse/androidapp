package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-5 上午11:00:22  
 * 程序的简单说明  
 */

public class BrandCityWideInfo implements Serializable{
	int UserId;//买手的用户编号",
    String UserName="";//买手的用户昵称",
    boolean IsFavorite=false;// 是否已关注(true:已关注, false:未关注)
    String BuyerLogo="";//http://123.57.52.187:9550/customerportrait/default_100x100.jpg",  买手头像
    int AssociateId;//买手编号",
    String Address="";//地址",
    int ProductId;//商品编号",
    String ProductName;//商品名称",
    String CreateTime="";//商品的创建时间",
    String[] Pic=null;
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public boolean isIsFavorite() {
		return IsFavorite;
	}
	public void setIsFavorite(boolean isFavorite) {
		IsFavorite = isFavorite;
	}
	public String getBuyerLogo() {
		return BuyerLogo;
	}
	public void setBuyerLogo(String buyerLogo) {
		BuyerLogo = buyerLogo;
	}
	public int getAssociateId() {
		return AssociateId;
	}
	public void setAssociateId(int associateId) {
		AssociateId = associateId;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
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
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String[] getPic() {
		return Pic;
	}
	public void setPic(String[] pic) {
		Pic = pic;
	}
}
