package com.shenma.yueba.yangjia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-12 下午10:17:15  
 * 程序的简单说明  本类定义联系人列表的ITEM类
 */

public class ContactsAddressListOitemBean implements Serializable{
List<ContactsAddressBean> items=new ArrayList<ContactsAddressBean>();
int pageindex;
int pagesize;
int totalcount;
int totalpaged;
boolean spaged=false;
public List<ContactsAddressBean> getItems() {
	return items;
}
public void setItems(List<ContactsAddressBean> items) {
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
public boolean isSpaged() {
	return spaged;
}
public void setSpaged(boolean spaged) {
	this.spaged = spaged;
}

}
