package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/*****
 * 本类定义 支付数据对象
 * ***/
public class PayResponseFormBean implements Serializable{
String orderNo=""; //订单号
String desc="";//描述信息
String content="";//主内容
String url="";//回调地址
double price;//支付金额
public String getOrderNo() {
	return orderNo;
}
public void setOrderNo(String orderNo) {
	this.orderNo = orderNo;
}
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}

}
