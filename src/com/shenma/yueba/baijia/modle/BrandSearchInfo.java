package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-1 上午11:56:43  
 * 程序的简单说明  品牌列表信息对象
 */

public class BrandSearchInfo implements Serializable{
	int Id;
    String Name=""; //名称
    String Logo="";//头像
	private int getId() {
		return Id;
	}
	private void setId(int id) {
		Id = id;
	}
	private String getName() {
		return Name;
	}
	private void setName(String name) {
		Name = name;
	}
	private String getLogo() {
		return Logo;
	}
	private void setLogo(String logo) {
		Logo = logo;
	}

}
