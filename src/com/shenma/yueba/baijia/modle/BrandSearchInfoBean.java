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
List<BrandSearchInfo> item=new ArrayList<BrandSearchInfo>();
int pageindex;
int pagesize;
int totalcount;
int totalpaged;
boolean ispaged=false;
private List<BrandSearchInfo> getItem() {
	return item;
}
private void setItem(List<BrandSearchInfo> item) {
	this.item = item;
}
private int getPageindex() {
	return pageindex;
}
private void setPageindex(int pageindex) {
	this.pageindex = pageindex;
}
private int getPagesize() {
	return pagesize;
}
private void setPagesize(int pagesize) {
	this.pagesize = pagesize;
}
private int getTotalcount() {
	return totalcount;
}
private void setTotalcount(int totalcount) {
	this.totalcount = totalcount;
}
private int getTotalpaged() {
	return totalpaged;
}
private void setTotalpaged(int totalpaged) {
	this.totalpaged = totalpaged;
}
private boolean isIspaged() {
	return ispaged;
}
private void setIspaged(boolean ispaged) {
	this.ispaged = ispaged;
}

}
