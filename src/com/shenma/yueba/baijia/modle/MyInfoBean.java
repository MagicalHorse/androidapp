package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-25 下午3:22:50  
 * 程序的简单说明  败家用户信息对象
 */

public class MyInfoBean implements Serializable{
	int Id;// 用户id
    String UserName=""; // 用户昵称
    String Logo="";//http://123.57.52.187:9550/customerportrait/default_100x100.jpg",  // 用户头像
    int FollowingCount;  //  关注人数
    int FollowerCount;  // 粉丝人数
    int CommunityCount; // 圈子人数
    int AllOrderCount;  // 全部订单
    int WaitPaymentOrderCount; // 待付款订单数量
    int PickedSelfOrderCount;  // 自提商品数量
    int AfterSaleOrderCount;// 售后数量
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getLogo() {
		return Logo;
	}
	public void setLogo(String logo) {
		Logo = logo;
	}
	public int getFollowingCount() {
		return FollowingCount;
	}
	public void setFollowingCount(int followingCount) {
		FollowingCount = followingCount;
	}
	public int getFollowerCount() {
		return FollowerCount;
	}
	public void setFollowerCount(int followerCount) {
		FollowerCount = followerCount;
	}
	public int getCommunityCount() {
		return CommunityCount;
	}
	public void setCommunityCount(int communityCount) {
		CommunityCount = communityCount;
	}
	public int getAllOrderCount() {
		return AllOrderCount;
	}
	public void setAllOrderCount(int allOrderCount) {
		AllOrderCount = allOrderCount;
	}
	public int getWaitPaymentOrderCount() {
		return WaitPaymentOrderCount;
	}
	public void setWaitPaymentOrderCount(int waitPaymentOrderCount) {
		WaitPaymentOrderCount = waitPaymentOrderCount;
	}
	public int getPickedSelfOrderCount() {
		return PickedSelfOrderCount;
	}
	public void setPickedSelfOrderCount(int pickedSelfOrderCount) {
		PickedSelfOrderCount = pickedSelfOrderCount;
	}
	public int getAfterSaleOrderCount() {
		return AfterSaleOrderCount;
	}
	public void setAfterSaleOrderCount(int afterSaleOrderCount) {
		AfterSaleOrderCount = afterSaleOrderCount;
	}

}
