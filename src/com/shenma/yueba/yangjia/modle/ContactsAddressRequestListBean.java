package com.shenma.yueba.yangjia.modle;

import java.io.Serializable;

import com.shenma.yueba.baijia.modle.BaseRequest;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-12 下午10:07:19  
 * 程序的简单说明   本类用于接收 获取省市列表的对象
 */

public class ContactsAddressRequestListBean extends BaseRequest implements Serializable{
	ContactsAddressListOitemBean data=new ContactsAddressListOitemBean();

	public ContactsAddressListOitemBean getData() {
		return data;
	}

	public void setData(ContactsAddressListOitemBean data) {
		this.data = data;
	}
}
