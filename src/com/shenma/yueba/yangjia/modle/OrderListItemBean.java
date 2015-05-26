package com.shenma.yueba.yangjia.modle;

import java.util.List;

public class OrderListItemBean {
	private List<OrderItem> orderlist;
	private String pageindex;
	private String pagesize;
	private String totalcount;
	private String totalpaged;
	private String ispaged;
	public List<OrderItem> getOrderlist() {
		return orderlist;
	}
	public void setOrderlist(List<OrderItem> orderlist) {
		this.orderlist = orderlist;
	}
	public String getPageindex() {
		return pageindex;
	}
	public void setPageindex(String pageindex) {
		this.pageindex = pageindex;
	}
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public String getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(String totalcount) {
		this.totalcount = totalcount;
	}
	public String getTotalpaged() {
		return totalpaged;
	}
	public void setTotalpaged(String totalpaged) {
		this.totalpaged = totalpaged;
	}
	public String getIspaged() {
		return ispaged;
	}
	public void setIspaged(String ispaged) {
		this.ispaged = ispaged;
	}
	
	
	
	
	
}
