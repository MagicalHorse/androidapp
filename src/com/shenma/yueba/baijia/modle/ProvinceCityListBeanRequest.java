package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-12 下午8:21:46  
 * 程序的简单说明  本类定义省市信息接收类
 */

public class ProvinceCityListBeanRequest extends BaseRequest implements Serializable{
	List<ProvinceCityListBean> data=new ArrayList<ProvinceCityListBean>();

	public List<ProvinceCityListBean> getData() {
		return data;
	}

	public void setData(List<ProvinceCityListBean> data) {
		this.data = data;
	}

}
