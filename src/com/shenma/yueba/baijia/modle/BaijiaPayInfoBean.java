package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-16 上午10:18:11  
 * 程序的简单说明  
 */

public class BaijiaPayInfoBean implements Serializable{
	
	public enum Type
	{
		weixinpay
	}
	
	
	
String extname1="";
String extname2="";
String extname3="";
String extname4="";

/*****
 * @param icon int 图片
 * @param type Type 支付类型
 * @param extname1 String 描述1
 * @param extname1 String 描述2
 * @param extname1 String 描述3
 * @param extname1 String 描述4
 * @param creatOrderInfoBean CreatOrderInfoBean 订单
 * 
 * *****/
public BaijiaPayInfoBean(int icon,Type type,String extname1,String extname2,String extname3,String extname4)
{
	this.icon=icon;
	this.type=type;
	this.extname1=extname1;
	this.extname2=extname2;
	this.extname3=extname3;
	this.extname4=extname4;
	this.extname4=extname4;
}

Type type;
int icon;

public String getExtname1() {
	return extname1;
}
public void setExtname1(String extname1) {
	this.extname1 = extname1;
}
public String getExtname2() {
	return extname2;
}
public void setExtname2(String extname2) {
	this.extname2 = extname2;
}
public String getExtname3() {
	return extname3;
}
public void setExtname3(String extname3) {
	this.extname3 = extname3;
}
public String getExtname4() {
	return extname4;
}
public void setExtname4(String extname4) {
	this.extname4 = extname4;
}
public Type getType() {
	return type;
}
public void setType(Type type) {
	this.type = type;
}
public int getIcon() {
	return icon;
}
public void setIcon(int icon) {
	this.icon = icon;
}
}
