package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gyj
 * @version 创建时间：2015-5-10 下午5:55:12 程序的简单说明
 */

public class CheckVersionBackBean extends BaseRequest implements Serializable{
	private CheckVersionBean data;

	public CheckVersionBean getData() {
		return data;
	}

	public void setData(CheckVersionBean data) {
		this.data = data;
	}
	
	
}
