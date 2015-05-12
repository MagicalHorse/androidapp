package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/****
 * 本类定义城市信息类
 * @author gyj
 * @date 2015-5-09
 * 
 * ***/
public class CityBean implements Serializable{
String name;
int id;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

}
