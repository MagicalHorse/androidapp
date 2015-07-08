package com.shenma.yueba.util;

import java.io.Serializable;

public class SizeBean implements Serializable{

	private String Name;
	private String Inventory;
	
	public String getInventory() {
		return Inventory;
	}
	public void setInventory(String inventory) {
		Inventory = inventory;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}

	

}
