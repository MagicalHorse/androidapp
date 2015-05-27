package com.shenma.yueba.baijia.modle;

import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-26 下午7:59:47  
 * 程序的简单说明  商品买手信息
 */

public class ProductBuyersInfoBean {
	int buyer_id;//卖家id,
	String buyer_name="";//卖家名称,
	String buyer_addrss="";//店铺地址,
	String buyer_icon="";//卖家头像地址,
	List<BuyerProductImageBean> products_img=new ArrayList<BuyerProductImageBean>();
	
}
