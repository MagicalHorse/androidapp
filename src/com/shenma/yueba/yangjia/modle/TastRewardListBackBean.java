package com.shenma.yueba.yangjia.modle;

import java.util.List;

import com.shenma.yueba.baijia.modle.BaseRequest;

public class TastRewardListBackBean extends BaseRequest{

	
	
	private List<TaskListItem> data;

	public List<TaskListItem> getData() {
		return data;
	}

	public void setData(List<TaskListItem> data) {
		this.data = data;
	}
	
	
	
}
