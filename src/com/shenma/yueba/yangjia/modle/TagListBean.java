package com.shenma.yueba.yangjia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TagListBean implements Serializable{

	List<Map<String,Double>> tagList = new ArrayList<Map<String,Double>>();

	public List<Map<String, Double>> getTagList() {
		return tagList;
	}

	public void setTagList(List<Map<String, Double>> tagList) {
		this.tagList = tagList;
	}
	
	
	
}
