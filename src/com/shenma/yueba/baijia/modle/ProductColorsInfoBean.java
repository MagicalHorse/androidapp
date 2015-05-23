package com.shenma.yueba.baijia.modle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gyj
 * @version 创建时间：2015-5-22 下午5:34:16 程序的简单说明 商品颜色数据
 */

public class ProductColorsInfoBean {
	String Color_Str = "";// 颜色名称",
	int mediaId;// 颜色图mediaid" 删除,
	String Color_img = "";// 颜色图URL 规则同下",
	List<ProductSizesBean> Sizes = new ArrayList<ProductSizesBean>();
	public String getColor_Str() {
		return Color_Str;
	}
	public void setColor_Str(String color_Str) {
		Color_Str = color_Str;
	}
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	public String getColor_img() {
		return Color_img;
	}
	public void setColor_img(String color_img) {
		Color_img = color_img;
	}
	public List<ProductSizesBean> getSizes() {
		return Sizes;
	}
	public void setSizes(List<ProductSizesBean> sizes) {
		Sizes = sizes;
	}
}
