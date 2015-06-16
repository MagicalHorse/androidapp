package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-15 下午2:28:28  
 * 程序的简单说明  品牌详细
 */

public class BrandInfoInfoBean implements Serializable{

	List<BrandInfoInfo> items=new ArrayList<BrandInfoInfo>();
	int pageindex;
    int pagesize;
    int totalcount;
    int totalpaged;
    boolean ispaged=false;
	public List<BrandInfoInfo> getItems() {
		return items;
	}
	public void setItems(List<BrandInfoInfo> items) {
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
