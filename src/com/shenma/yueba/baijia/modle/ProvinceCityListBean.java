package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-12 下午8:22:20  
 * 程序的简单说明  本类定义省市信息资源类
 */

public class ProvinceCityListBean implements Serializable{
	int Id;       
    int ParentId;
    String Name="";// "北京市",
    boolean IsProvince=false;//是否是省
    boolean IsCity=false;//是否是市
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getParentId() {
		return ParentId;
	}
	public void setParentId(int parentId) {
		ParentId = parentId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public boolean isIsProvince() {
		return IsProvince;
	}
	public void setIsProvince(boolean isProvince) {
		IsProvince = isProvince;
	}
	public boolean isIsCity() {
		return IsCity;
	}
	public void setIsCity(boolean isCity) {
		IsCity = isCity;
	}

}
