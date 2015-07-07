package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-6 下午6:53:53  
 * 程序的简单说明  败家消息对象
 */

public class RequestMsgListInfo implements Serializable{
	List<MsgListInfo> items=new ArrayList<MsgListInfo>();
	
	public List<MsgListInfo> getItems() {
		return items;
	}
	public void setItems(List<MsgListInfo> items) {
		this.items = items;
	}
	int pageindex;
	int pagesize;
	int totalcount;
	int totalpaged;
	boolean ispaged=false;
	
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
