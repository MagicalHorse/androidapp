package com.shenma.yueba.yangjia.modle;

import java.util.List;

public class CircleListItemBean {

	
	private String pageindex;
	private String pagesize;
	private String totalcount;
	private String totalpaged;
	private String ispaged;
	private List<cirlceItemBean> items;
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
	public List<cirlceItemBean> getItems() {
		return items;
	}
	public void setItems(List<cirlceItemBean> items) {
		this.items = items;
	}
	
	
	
	
	
	
}
