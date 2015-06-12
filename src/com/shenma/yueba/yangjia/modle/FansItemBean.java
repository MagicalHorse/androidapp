package com.shenma.yueba.yangjia.modle;

import com.shenma.yueba.util.sore.SortModel;

public class FansItemBean extends SortModel{

	
	private String UserLogo;
	private String UserName;
	private String UserId;
	private boolean isChecked;//是否选择
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
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	
	
	
}
