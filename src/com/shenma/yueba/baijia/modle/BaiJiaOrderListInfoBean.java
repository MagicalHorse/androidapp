package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gyj
 * @version 创建时间：2015-6-2 下午5:58:43 
 * 程序的简单说明 订单列表页（包含分页信息）
 */

public class BaiJiaOrderListInfoBean implements Serializable{
	List<BaiJiaOrderListInfo> items = new ArrayList<BaiJiaOrderListInfo>();
	int pageindex;//
	int pagesize;//
	int totalcount;
	int totalpaged;
	boolean ispaged = false;
	public List<BaiJiaOrderListInfo> getItems() {
		return items;
	}
	public void setItems(List<BaiJiaOrderListInfo> items) {
		this.items = items;
	}
	public int getPageindex() {
		return pageindex;
	}
	public void setPageindex(int pageindex) {
		this.pageindex = pageindex;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}
	public int getTotalpaged() {
		return totalpaged;
	}
	public void setTotalpaged(int totalpaged) {
		this.totalpaged = totalpaged;
	}
	public boolean isIspaged() {
		return ispaged;
	}
	public void setIspaged(boolean ispaged) {
		this.ispaged = ispaged;
	}

}
