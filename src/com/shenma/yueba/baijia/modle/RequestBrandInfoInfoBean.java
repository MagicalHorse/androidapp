package com.shenma.yueba.baijia.modle;

import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-15 下午2:27:40  
 * 程序的简单说明  品牌详细对象
 */

public class RequestBrandInfoInfoBean extends BaseRequest{

	List<BrandInfoInfoBean> data=new ArrayList<BrandInfoInfoBean>();
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

	public List<BrandInfoInfoBean> getData() {
		return data;
	}

	public void setData(List<BrandInfoInfoBean> data) {
		this.data = data;
	}
}
