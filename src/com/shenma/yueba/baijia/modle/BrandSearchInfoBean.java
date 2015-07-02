package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-1 上午11:55:54  
 * 程序的简单说明  品牌信息对象
 */

public class BrandSearchInfoBean implements Serializable{

int pageindex;
int pagesize;
int totalcount;
int totalpaged;
boolean ispaged=false;
List<BrandSearchInfo> items=new ArrayList<BrandSearchInfo>();


public List<BrandSearchInfo> getItems() {
	return items;
}
public void setItems(List<BrandSearchInfo> items) {
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
