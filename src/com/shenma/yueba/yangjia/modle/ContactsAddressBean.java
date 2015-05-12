package com.shenma.yueba.yangjia.modle;

import java.io.Serializable;

/**
 * @author gyj
 * @version 创建时间：2015-5-12 下午2:26:39 
 * 程序的简单说明 本类定义地址信息存储类
 */

public class ContactsAddressBean implements Serializable{
	int id;// 编号,
	int userid;// 用户编号,
	int shippingzipcode;// 邮编,
	String shippingaddress = "";// 联系人地址
	String shippingperson = "";// 联系人
	String shippingphone = "";// 联系人号码
	int shippingprovinceid;// 省编号,
	String shippingprovince = "";// 省份名称
	int shippingcityid;// 市编号,
	String shippingcity = "";// 城市名称
	int shippingdistrictid;// 区编号
	String shippingdistrict = "";// 区名称
	boolean isdefault = false;// 是否默认地址
	String displayaddress = "";// 显示地址

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getShippingzipcode() {
		return shippingzipcode;
	}

	public void setShippingzipcode(int shippingzipcode) {
		this.shippingzipcode = shippingzipcode;
	}

	public String getShippingaddress() {
		return shippingaddress;
	}

	public void setShippingaddress(String shippingaddress) {
		this.shippingaddress = shippingaddress;
	}

	public String getShippingperson() {
		return shippingperson;
	}

	public void setShippingperson(String shippingperson) {
		this.shippingperson = shippingperson;
	}

	public String getShippingphone() {
		return shippingphone;
	}

	public void setShippingphone(String shippingphone) {
		this.shippingphone = shippingphone;
	}

	public int getShippingprovinceid() {
		return shippingprovinceid;
	}

	public void setShippingprovinceid(int shippingprovinceid) {
		this.shippingprovinceid = shippingprovinceid;
	}

	public String getShippingprovince() {
		return shippingprovince;
	}

	public void setShippingprovince(String shippingprovince) {
		this.shippingprovince = shippingprovince;
	}

	public int getShippingcityid() {
		return shippingcityid;
	}

	public void setShippingcityid(int shippingcityid) {
		this.shippingcityid = shippingcityid;
	}

	public String getShippingcity() {
		return shippingcity;
	}

	public void setShippingcity(String shippingcity) {
		this.shippingcity = shippingcity;
	}

	public int getShippingdistrictid() {
		return shippingdistrictid;
	}

	public void setShippingdistrictid(int shippingdistrictid) {
		this.shippingdistrictid = shippingdistrictid;
	}

	public String getShippingdistrict() {
		return shippingdistrict;
	}

	public void setShippingdistrict(String shippingdistrict) {
		this.shippingdistrict = shippingdistrict;
	}

	public boolean isIsdefault() {
		return isdefault;
	}

	public void setIsdefault(boolean isdefault) {
		this.isdefault = isdefault;
	}

	public String getDisplayaddress() {
		return displayaddress;
	}

	public void setDisplayaddress(String displayaddress) {
		this.displayaddress = displayaddress;
	}

}
