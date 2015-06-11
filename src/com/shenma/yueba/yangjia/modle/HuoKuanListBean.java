package com.shenma.yueba.yangjia.modle;

import java.util.List;

public class HuoKuanListBean {
	private List<HuoKuanItem> items;
	private String pageindex;
	private String pagesize;
	private String totalcount;
	private String totalpaged;
	
	public List<HuoKuanItem> getItems() {
		return items;
	}
	public void setItems(List<HuoKuanItem> items) {
		this.items = items;
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
	
	
	
	
	
}
