package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**
 * @author gyj
 * @version 创建时间：2015-7-17 下午3:55:45 程序的简单说明 商品详情 标签信息
 */

public class ProductsDetailsTagsInfo implements Serializable {
	String Id = "";
	String Name = "";// 名字
	double PosX;
	double PosY;
	int SourceId;
	String SourceType;

	public String getSourceType() {
		return SourceType;
	}

	public void setSourceType(String sourceType) {
		SourceType = sourceType;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public double getPosX() {
		return PosX;
	}

	public void setPosX(double posX) {
		PosX = posX;
	}

	public double getPosY() {
		return PosY;
	}

	public void setPosY(double posY) {
		PosY = posY;
	}

	public int getSourceId() {
		return SourceId;
	}

	public void setSourceId(int sourceId) {
		SourceId = sourceId;
	}

}
