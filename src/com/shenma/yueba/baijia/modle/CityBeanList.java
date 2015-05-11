package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/***
 * 本类定义获取城市列表信息类
 * @author gyj
 * @date 2015-5-9
 * ***/
public class CityBeanList implements Serializable{
String Key;
List<CityBean> cities=new ArrayList<CityBean>();
public List<CityBean> getCities() {
	return cities;
}
public void setCities(List<CityBean> cities) {
	this.cities = cities;
}
public String getKey() {
	return Key;
}
public void setKey(String key) {
	Key = key;
}

}
