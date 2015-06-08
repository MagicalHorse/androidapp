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
	long SourceId;//产品id
	String Name="";// 图片地址,
	//标签列表
	List<ProductTagsInfoBean> Tags=new ArrayList<ProductTagsInfoBean>();
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public long getSourceId() {
		return SourceId;
	}
	public void setSourceId(long sourceId) {
		SourceId = sourceId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<ProductTagsInfoBean> getTags() {
		return Tags;
	}
	public void setTags(List<ProductTagsInfoBean> tags) {
		Tags = tags;
	}
}
