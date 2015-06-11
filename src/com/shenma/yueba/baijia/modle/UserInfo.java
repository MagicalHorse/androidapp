package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**
 * @author gyj
 * @version 创建时间：2015-5-11 上午10;//53;//53 程序的简单说明 本类 用于定义 用户信息 详细
 */

public class UserInfo implements Serializable {
	private String Description;//店铺说明
	private String AuditStatus;//审核状态
	boolean isbuyer=false;
	int operate_right ;// null,
	int template_id ;// null,
	int associate_id ;// null,
	String basegroupid ;// null,
	String basegroupname = "";// null,
	int id;// 732,
	String name = "";// 登录名,
	String nickname = "";// 昵称,
	String mobile = "";// 18500518027,
	String email = "";// ,
	int level;// 1,
	String logo = "";// http;////123.57.77.5;//9550/customerportrait/default,
	String logo_full = "";// http;////123.57.77.5;//9550/customerportrait/default_100x100.jpg,
	String logobg_s = "";// http;////123.57.77.5;//9550/customerbg/default,
	String logobg = "";// null,
	String desc = "";// ,
	int gender;// 0,
	int favortotal;// 0,
	int consumptiontotal;// 0,
	int sharetotal;// 0,
	String token = "";// NKVRFwecvPina9HWjmoaPsasXzhScI61q0aveOk%2BOuCp7B19ft0ch7SQuMb70uh0
	UserBuyerBean buyer=new UserBuyerBean();
	public boolean isIsbuyer() {
		return isbuyer;
	}
	public void setIsbuyer(boolean isbuyer) {
		this.isbuyer = isbuyer;
	}
	public int getOperate_right() {
		return operate_right;
	}
	public void setOperate_right(int operate_right) {
		this.operate_right = operate_right;
	}
	public int getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(int template_id) {
		this.template_id = template_id;
	}
	public int getAssociate_id() {
		return associate_id;
	}
	public void setAssociate_id(int associate_id) {
		this.associate_id = associate_id;
	}
	public String getBasegroupid() {
		return basegroupid;
	}
	public void setBasegroupid(String basegroupid) {
		this.basegroupid = basegroupid;
	}
	public String getBasegroupname() {
		return basegroupname;
	}
	public void setBasegroupname(String basegroupname) {
		this.basegroupname = basegroupname;
	}
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public UserBuyerBean getBuyer() {
		return buyer;
	}
	public void setBuyer(UserBuyerBean buyer) {
		this.buyer = buyer;
	}
	public String getAuditStatus() {
		return AuditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		AuditStatus = auditStatus;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	

}
