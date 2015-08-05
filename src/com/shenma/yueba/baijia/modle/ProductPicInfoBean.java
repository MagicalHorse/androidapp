package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-27 下午1:46:11  
 * 程序的简单说明  
 */

public class ProductPicInfoBean implements Serializable{
	int Id;//图片id,
	int SourceId;//产品id
	String Name="";// 图片地址,
	//标签列表
	List<ProductsDetailsTagsInfo> Tags=new ArrayList<ProductsDetailsTagsInfo>();
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getSourceId() {
		return SourceId;
	}
	public void setSourceId(int sourceId) {
		SourceId = sourceId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<ProductsDetailsTagsInfo> getTags() {
		return Tags;
	}
	public void setTags(List<ProductsDetailsTagsInfo> tags) {
		Tags = tags;
	}
}
