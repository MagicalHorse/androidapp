package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-4 下午1:57:55  
 * 程序的简单说明  我的圈子
 */

public class RequestMyCircleInfoBean extends BaseRequest implements Serializable{
	MyCircleInfoBean  data=new MyCircleInfoBean();
	int pageindex;
	int pagesize;
	int totalcount;
	int totalpaged;
	boolean ispaged=false;
	public MyCircleInfoBean getData() {
		return data;
	}
	public void setData(MyCircleInfoBean data) {
		this.data = data;
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
