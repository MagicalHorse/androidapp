package com.shenma.yueba.baijia.modle;

import im.form.RequestMessageBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-6 下午1:48:39  
 * 程序的简单说明   获取im 某一房间历史消息的对象
 */

public class RequestImMessageInfo implements Serializable{
	List<RequestMessageBean> items=new ArrayList<RequestMessageBean>();
	int pageindex;
	int pagesize;
	int totalcount;
	int totalpaged;
	boolean ispaged=false;
	public List<RequestMessageBean> getItems() {
		return items;
	}
	public void setItems(List<RequestMessageBean> items) {
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
