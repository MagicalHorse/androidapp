package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-5 下午2:55:28  
 * 程序的简单说明   产品规格尺寸
 */ 

public class PrioductSizesInfoBean implements Serializable{
	long SizeId;//规格id
    String Size="";//L/M/XL/XXL/XXXL   规格名字
    int Inventory;//库存
	public long getSizeId() {
		return SizeId;
	}
	public void setSizeId(long sizeId) {
		SizeId = sizeId;
	}
	public String getSize() {
		return Size;
	}
	public void setSize(String size) {
		Size = size;
	}
	public int getInventory() {
		return Inventory;
	}
	public void setInventory(int inventory) {
		Inventory = inventory;
	}

}
