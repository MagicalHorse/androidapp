package com.shenma.yueba.baijia.modle;

import java.util.ArrayList;
import java.util.List;

public class CityListBackBean extends BaseRequest{
	private List<CityListItembean> data = new ArrayList<CityListItembean>();

	public List<CityListItembean> getData() {
		return data;
	}

	public void setData(List<CityListItembean> data) {
		this.data = data;
	}
	
	
	
}
