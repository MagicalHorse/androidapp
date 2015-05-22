package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**
 * @author gyj
 * @version 创建时间：2015-5-20 下午4:03:01 程序的简单说明
 */

public class UserBean implements Serializable{
	int id;
	String name = "";
	String nickname = "";
	String mobile = "";
	String email = "";
	int level;
	String logo = "";
	String logo_full = "";
	String logobg_s = "";
	String logobg = "";
	String desc = "";
	int gender;
	int favortotal;
	int consumptiontotal;
	int sharetotal;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getLogo_full() {
		return logo_full;
	}
	public void setLogo_full(String logo_full) {
		this.logo_full = logo_full;
	}
	public String getLogobg_s() {
		return logobg_s;
	}
	public void setLogobg_s(String logobg_s) {
		this.logobg_s = logobg_s;
	}
	public String getLogobg() {
		return logobg;
	}
	public void setLogobg(String logobg) {
		this.logobg = logobg;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getFavortotal() {
		return favortotal;
	}
	public void setFavortotal(int favortotal) {
		this.favortotal = favortotal;
	}
	public int getConsumptiontotal() {
		return consumptiontotal;
	}
	public void setConsumptiontotal(int consumptiontotal) {
		this.consumptiontotal = consumptiontotal;
	}
	public int getSharetotal() {
		return sharetotal;
	}
	public void setSharetotal(int sharetotal) {
		this.sharetotal = sharetotal;
	}
}
