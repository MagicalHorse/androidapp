package com.shenma.yueba.baijia.modle;

import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-26 下午8:14:48  
 * 程序的简单说明  
 */

public class BuyerProductImageBean {
    int product_id;//商品id
    double prodict_price;//商品价格,
    String produtc_desc="";//商品描述,
    String product_issuetime="";//发布时间,
    String product_icon="";//商品图像地址,
    //喜欢该商品的用户列表
    List<LikedProductusersInfoBean> likedusers=new ArrayList<LikedProductusersInfoBean>();
    //图片的标签
    List<ProductImageTagBean> tags=new ArrayList<ProductImageTagBean>();
}
