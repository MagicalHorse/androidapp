package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-6-3 下午4:43:18  
 * 程序的简单说明  
 */

public class ProductsDetailsInfoBean {
	int BuyerId;//买手id
	String BuyerName="";// 买手名字
	String CreateTime="";//创建时间
	String BuyerLogo="";// 买手头像
	int TurnCount;//交易量
	String PickAddress="";//提货地址
	double Price;//价格
	String ProductName="";// 产品名字
	int ProductId; //产品编号
	//关注的人
	LikeUsersInfoBean LikeUsers=new LikeUsersInfoBean();
	//买手推荐商品信息
	String[] ProductPic=null;
	public int getBuyerId() {
		return BuyerId;
	}
	public void setBuyerId(int buyerId) {
		BuyerId = buyerId;
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
	
	public String getBuyerLogo() {
		return BuyerLogo;
	}
	public void setBuyerLogo(String buyerLogo) {
		BuyerLogo = buyerLogo;
	}
	public int getTurnCount() {
		return TurnCount;
	}
	public void setTurnCount(int turnCount) {
		TurnCount = turnCount;
	}
	public String getPickAddress() {
		return PickAddress;
	}
	public void setPickAddress(String pickAddress) {
		PickAddress = pickAddress;
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
	public String[] getProductPic() {
		return ProductPic;
	}
	public void setProductPic(String[] productPic) {
		ProductPic = productPic;
	}
	
	
}
