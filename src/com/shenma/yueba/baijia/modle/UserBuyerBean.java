package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**
 * @author gyj
 * @version 创建时间：2015-5-20 下午3:58:45 程序的简单说明
 */

public class UserBuyerBean implements Serializable {
	int Id;
	int storeid;
	int sectionid;
	String storename = "";
	String sectionname = "";
	int favoritedcount;
	int groupcount;
	UserBean user = new UserBean();
	int updatecount;
    int productcount;
    boolean isfavorited=false;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getStoreid() {
		return storeid;
	}
	public void setStoreid(int storeid) {
		this.storeid = storeid;
	}
	public int getSectionid() {
		return sectionid;
	}
	public void setSectionid(int sectionid) {
		this.sectionid = sectionid;
	}
	public String getStorename() {
		return storename;
	}
	public void setStorename(String storename) {
		this.storename = storename;
	}
	public String getSectionname() {
		return sectionname;
	}
	public void setSectionname(String sectionname) {
		this.sectionname = sectionname;
	}
	public int getFavoritedcount() {
		return favoritedcount;
	}
	public void setFavoritedcount(int favoritedcount) {
		this.favoritedcount = favoritedcount;
	}
	public int getGroupcount() {
		return groupcount;
	}
	public void setGroupcount(int groupcount) {
		this.groupcount = groupcount;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public int getUpdatecount() {
		return updatecount;
	}
	public void setUpdatecount(int updatecount) {
		this.updatecount = updatecount;
	}
	public int getProductcount() {
		return productcount;
	}
	public void setProductcount(int productcount) {
		this.productcount = productcount;
	}
	public boolean isIsfavorited() {
		return isfavorited;
	}
	public void setIsfavorited(boolean isfavorited) {
		this.isfavorited = isfavorited;
	}
}
