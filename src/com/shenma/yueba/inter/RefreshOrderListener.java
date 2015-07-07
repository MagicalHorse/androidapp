package com.shenma.yueba.inter;

public interface RefreshOrderListener {
	public abstract void refreshOrderList(int index);//0表示刷新全部订单列表，1表示刷新代付款订单列表，2表示专柜自提订单列表，3表示售后中心列表
}
