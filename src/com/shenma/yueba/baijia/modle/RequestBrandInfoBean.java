package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-6-4 下午4:11:33  
 * 程序的简单说明  发现品牌信息对象 
 */

public class RequestBrandInfoBean extends BaseRequest{
	BrandInfoBean data=new BrandInfoBean();

	public BrandInfoBean getData() {
		return data;
	}

	public void setData(BrandInfoBean data) {
		this.data = data;
	}
	
}
