package com.shenma.yueba.yangjia.modle;

import java.io.Serializable;

import com.shenma.yueba.baijia.modle.BaseRequest;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-12 下午2:25:45  
 * 程序的简单说明   本类定义 用于接收创建地址信息反馈信息类
 */

public class ContactsAddressRequestBean extends BaseRequest implements Serializable{
	ContactsAddressBean data=new ContactsAddressBean();

	public ContactsAddressBean getData() {
		return data;
	}

	public void setData(ContactsAddressBean data) {
		this.data = data;
	}
}
