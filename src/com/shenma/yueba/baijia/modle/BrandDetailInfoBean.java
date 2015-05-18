package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gyj
 * @version 创建时间：2015-5-10 下午5:55:12 程序的简单说明
 */

public class BrandDetailInfoBean extends BaseRequest implements Serializable {
	List<BrandDetailInfoItem> data = new ArrayList<BrandDetailInfoItem>();

	public List<BrandDetailInfoItem> getData() {
		return data;
	}

	public void setData(List<BrandDetailInfoItem> data) {
		this.data = data;
	}

}
