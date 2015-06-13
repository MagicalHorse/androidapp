package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-12 上午11:25:30  
 * 程序的简单说明  商品活动信息
 */

public class ProductsDetailsPromotion implements Serializable{

	int Id;//活动id
    String Name="";//活动名字
    String DescriptionText="";//活动描述
    String TipText="";//活动内容
    String Link="";//
    boolean IsShow=false;
    
	public boolean isIsShow() {
		return IsShow;
	}
	public void setIsShow(boolean isShow) {
		IsShow = isShow;
	}
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
	public String getDescriptionText() {
		return DescriptionText;
	}
	public void setDescriptionText(String descriptionText) {
		DescriptionText = descriptionText;
	}
	public String getTipText() {
		return TipText;
	}
	public void setTipText(String tipText) {
		TipText = tipText;
	}
	public String getLink() {
		return Link;
	}
	public void setLink(String link) {
		Link = link;
	}

}
