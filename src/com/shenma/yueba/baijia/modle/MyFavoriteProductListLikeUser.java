package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-8 下午7:51:29  
 * 程序的简单说明  
 */

public class MyFavoriteProductListLikeUser implements Serializable{
	int Count;// 喜欢的格式
	boolean IsLike=false; //我是否喜欢
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	public boolean isIsLike() {
		return IsLike;
	}
	public void setIsLike(boolean isLike) {
		IsLike = isLike;
	}

}
