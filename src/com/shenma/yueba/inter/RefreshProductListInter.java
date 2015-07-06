package com.shenma.yueba.inter;

public interface RefreshProductListInter {
	public abstract void refreshOnLineProduct(int index);//0表示刷新在线商品列表，1表示即将下线商品列表，2表示下线商品列表
}
