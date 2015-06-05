package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-4 下午4:12:34  
 * 程序的简单说明  发现品牌信息
 */

public class BrandInfoBean implements Serializable{
	List<BrandInfo> Items=new ArrayList<BrandInfo>();

	public List<BrandInfo> getItems() {
		return Items;
	}

	public void setItems(List<BrandInfo> items) {
		Items = items;
	}
}
