package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-4 下午1:58:46  
 * 程序的简单说明  
 */

public class MyCircleInfoBean implements Serializable{
	List<MyCircleInfo> items=new ArrayList<MyCircleInfo>();

	public List<MyCircleInfo> getItems() {
		return items;
	}

	public void setItems(List<MyCircleInfo> items) {
		this.items = items;
	}
}
