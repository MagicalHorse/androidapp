package com.shenma.yueba.yangjia.modle;

import com.shenma.yueba.util.sore.SortModel;

public class AttationAndFansItemBean extends SortModel{

	private String UserLogo;//用户头像,
	private String UserName;// 用户昵称
	private String FavoiteCount;//他关注的人数
	private String FansCount;//他的粉丝数
	private String UserId;//他关注的人数
	private String isFavorite;//我是否已经关注
	private String CreateTime;// 创建时间
	public String getUserLogo() {
		return UserLogo;
	}
	public void setUserLogo(String userLogo) {
		UserLogo = userLogo;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getFavoiteCount() {
		return FavoiteCount;
	}
	public void setFavoiteCount(String favoiteCount) {
		FavoiteCount = favoiteCount;
	}
	public String getFansCount() {
		return FansCount;
	}
	public void setFansCount(String fansCount) {
		FansCount = fansCount;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getIsFavorite() {
		return isFavorite;
	}
	public void setIsFavorite(String isFavorite) {
		this.isFavorite = isFavorite;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	

	
}
