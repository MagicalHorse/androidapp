package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-17 下午3:53:43  
 * 程序的简单说明  商品详情页  图片与 标签
 */

public class ProductsDetailsTagInfo implements Serializable{

	String Logo="";//图片地址
	List<ProductsDetailsTagsInfo> Tags=new ArrayList<ProductsDetailsTagsInfo>();
	public String getLogo() {
		return Logo;
	}
	public void setLogo(String logo) {
		Logo = logo;
	}
	public List<ProductsDetailsTagsInfo> getTags() {
		return Tags;
	}
	public void setTags(List<ProductsDetailsTagsInfo> tags) {
		Tags = tags;
	}
}
