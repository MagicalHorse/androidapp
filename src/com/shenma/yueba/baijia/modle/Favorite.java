package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

public class Favorite implements Serializable {

	private String favoritecount;// 粉丝数量
	private String groupcount;// 圈子数量
	public String getFavoritecount() {
		return favoritecount;
	}
	public void setFavoritecount(String favoritecount) {
		this.favoritecount = favoritecount;
	}
	public String getGroupcount() {
		return groupcount;
	}
	public void setGroupcount(String groupcount) {
		this.groupcount = groupcount;
	}
	
	
	
	
}
