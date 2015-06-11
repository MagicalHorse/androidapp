package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-27 下午1:35:29  
 * 程序的简单说明   (我的买手)
 */

public class MyProductListInfoBean implements Serializable{
	//活动列表
//List<BannersInfoBean> Banners=new ArrayList<BannersInfoBean>();
//商品信息列表
List<ProductsInfoBean> Products=new ArrayList<ProductsInfoBean>();
public List<ProductsInfoBean> getProducts() {
	return Products;
}
public void setProducts(List<ProductsInfoBean> products) {
	Products = products;
}

}
