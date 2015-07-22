package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-27 下午1:50:28  
 * 程序的简单说明  
 */

public class ProductTagsInfoBean implements Serializable{

	float PosX ;//       x坐标
    float PosY   ;//     y坐标
	int Id  ;// 标签id    
    String Name="";// 标签名
    
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public float getPosX() {
		return PosX;
	}
	public void setPosX(float posX) {
		PosX = posX;
	}
	public float getPosY() {
		return PosY;
	}
	public void setPosY(float posY) {
		PosY = posY;
	}

}
