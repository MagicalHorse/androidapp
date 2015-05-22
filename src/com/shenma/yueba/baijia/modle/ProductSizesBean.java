package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-22 下午5:35:49  
 * 程序的简单说明  商品大小规格
 */

public class ProductSizesBean implements Serializable{
	String name="";//规格名称",
    String Inventory="";//库存"
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInventory() {
		return Inventory;
	}
	public void setInventory(String inventory) {
		Inventory = inventory;
	}

}
