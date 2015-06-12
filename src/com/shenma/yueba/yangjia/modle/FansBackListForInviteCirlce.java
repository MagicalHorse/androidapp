package com.shenma.yueba.yangjia.modle;

import java.util.List;

import com.shenma.yueba.baijia.modle.BaseRequest;

public class FansBackListForInviteCirlce extends BaseRequest{

	
	private List<FansItemBean> data;

	public List<FansItemBean> getData() {
		return data;
	}

	public void setData(List<FansItemBean> data) {
		this.data = data;
	}
	
	
}
