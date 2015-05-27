package com.shenma.yueba.baijia.modle;

import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-26 下午7:58:57  
 * 程序的简单说明  
 */

public class ProductListInfoBean {
	//活动信息列表
	List<RecommendimagesInfoBean> recommendimages=new ArrayList<RecommendimagesInfoBean>();
	int currpage;//当前页
	int totalpage;//总页数
	int pagesize;//每页显示的个数
	//买手
	List<ProductBuyersInfoBean> buyers=new ArrayList<ProductBuyersInfoBean>();
}
