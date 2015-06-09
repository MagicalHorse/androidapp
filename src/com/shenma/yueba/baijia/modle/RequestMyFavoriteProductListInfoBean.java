package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-6-8 下午7:40:47  
 * 程序的简单说明   获取我的收藏商品类别请求数据
 */

public class RequestMyFavoriteProductListInfoBean extends BaseRequest{
	MyFavoriteProductListInfoBean data=new MyFavoriteProductListInfoBean();

	public MyFavoriteProductListInfoBean getData() {
		return data;
	}

	public void setData(MyFavoriteProductListInfoBean data) {
		this.data = data;
	}
}
