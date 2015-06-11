package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-26 下午7:56:12  
 * 程序的简单说明   请求主页商品列表信息(我的买手)
 */

public class MyRequestProductListInfoBean extends BaseRequest{
	MyHomeProductListInfoBean data=new MyHomeProductListInfoBean();

	public MyHomeProductListInfoBean getData() {
		return data;
	}

	public void setData(MyHomeProductListInfoBean data) {
		this.data = data;
	}
	
}
