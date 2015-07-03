package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-2 下午7:29:51  
 * 程序的简单说明  
 */

public class BaiJiaShareInfoBean implements Serializable{
 int id;
 String type="";
 String logo="";
 String name="";
 double price;
 boolean ischeck=false;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getLogo() {
	return logo;
}
public void setLogo(String logo) {
	this.logo = logo;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public boolean isIscheck() {
	return ischeck;
}
public void setIscheck(boolean ischeck) {
	this.ischeck = ischeck;
}
}
