package com.shenma.yueba.util;

import java.io.Serializable;

public class SizeBean implements Serializable{

	private String name;
	private String Inventory;
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
