package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-27 下午1:50:28  
 * 程序的简单说明  
 */

public class ProductTagsInfoBean implements Serializable{

	int TagId  ;// 标签id    
    String TagName="";// 标签名
    float PosX ;//       x坐标
    float PosY   ;//     y坐标
	public int getTagId() {
		return TagId;
	}
	public void setTagId(int tagId) {
		TagId = tagId;
	}
	public String getTagName() {
		return TagName;
	}
	public void setTagName(String tagName) {
		TagName = tagName;
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
