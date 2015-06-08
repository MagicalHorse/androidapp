package com.shenma.yueba.yangjia.modle;

public class Users {

	
	private String UserId;
	private String NickName;
	private String Logo;
	private boolean showDelete;
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public String getLogo() {
		return Logo;
	}
	public void setLogo(String logo) {
		Logo = logo;
	}
	public boolean isShowDelete() {
		return showDelete;
	}
	public void setShowDelete(boolean showDelete) {
		this.showDelete = showDelete;
	}
	
	
	
}
