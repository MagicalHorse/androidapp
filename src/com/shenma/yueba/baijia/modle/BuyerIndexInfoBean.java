package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gyj
 * @version 创建时间：2015-5-10 下午5:55:12 程序的简单说明
 */

public class BuyerIndexInfoBean extends BaseRequest implements Serializable {
	private BuyerIndexInfo data = new BuyerIndexInfo();
	public BuyerIndexInfo getData() {
		return data;
	}

	public void setData(BuyerIndexInfo data) {
		this.data = data;
	}

}
