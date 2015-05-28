package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-27 下午2:12:29  
 * 程序的简单说明  
 */

public class HomeProductListInfoBean implements Serializable{
	
	
	ProductListInfoBean items=new ProductListInfoBean();
	

	int pageindex;
    int pagesize;
    int totalcount;
    int totalpaged;
    boolean ispaged=false;
	
    public ProductListInfoBean getItems() {
		return items;
	}

	public void setItems(ProductListInfoBean items) {
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
