package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-27 下午1:39:17  
 * 程序的简单说明  商品信息
 */

public class ProductsInfoBean implements Serializable{

	int Buyerid;//买手id
	String BuyerName="";// 买手名字
	String CreateTime="";//创建时间
	String BuyerAddress="";// 地址
	String BuyerLogo="";// 买手头像
	double Price;//价格
	String ProductName="";// 产品名字
	int ProductId; //产品编号
	String ShareLink="";//分享链接
	String ShareDesc="";
	
	
	public String getShareDesc() {
		return ShareDesc;
	}
	public void setShareDesc(String shareDesc) {
		ShareDesc = shareDesc;
	}
	public String getShareLink() {
		return ShareLink;
	}
	public void setShareLink(String shareLink) {
		ShareLink = shareLink;
	}
	//活动信息
    ProductsDetailsPromotion Promotion=new ProductsDetailsPromotion();
	
	
	public ProductsDetailsPromotion getPromotion() {
		return Promotion;
	}
	public void setPromotion(ProductsDetailsPromotion promotion) {
		Promotion = promotion;
	}
	//关注的人
	LikeUsersInfoBean LikeUsers=new LikeUsersInfoBean();
	//买手推荐商品信息
	ProductPicInfoBean ProductPic=new ProductPicInfoBean();
	public int getBuyerid() {
		return Buyerid;
	}
	public void setBuyerid(int buyerid) {
		Buyerid = buyerid;
	}
	public String getBuyerName() {
		return BuyerName;
	}
	public void setBuyerName(String buyerName) {
		BuyerName = buyerName;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getBuyerAddress() {
		return BuyerAddress;
	}
	public void setBuyerAddress(String buyerAddress) {
		BuyerAddress = buyerAddress;
	}
	public String getBuyerLogo() {
		return BuyerLogo;
	}
	public void setBuyerLogo(String buyerLogo) {
		BuyerLogo = buyerLogo;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		Price = price;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public int getProductId() {
		return ProductId;
	}
	public void setProductId(int productId) {
		ProductId = productId;
	}
	public LikeUsersInfoBean getLikeUsers() {
		return LikeUsers;
	}
	public void setLikeUsers(LikeUsersInfoBean likeUsers) {
		LikeUsers = likeUsers;
	}
	public ProductPicInfoBean getProductPic() {
		return ProductPic;
	}
	public void setProductPic(ProductPicInfoBean productPic) {
		ProductPic = productPic;
	}
	

	
}
