package com.shenma.yueba.util;

import java.util.ArrayList;
import java.util.List;

import com.shenma.yueba.yangjia.modle.TagsBean;

public class ProductImagesBean {

	
	private String ImageUrl;
	private List<TagsBean> Tags;
	public String getImageUrl() {
		return ImageUrl;
	}
	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}
	public List<TagsBean> getTags() {
		if(Tags == null){
			Tags = new ArrayList<TagsBean>();
		}
		return Tags;
	}
	public void setTags(List<TagsBean> tags) {
		Tags = tags;
	}
	
	
	
	
	
}
