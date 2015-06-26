package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-6-25 下午3:21:53  
 * 程序的简单说明   败家 获取用户信息
 */

public class RequestMyInfoBean extends BaseRequest{
	MyInfoBean data=new MyInfoBean();

	public MyInfoBean getData() {
		return data;
	}

	public void setData(MyInfoBean data) {
		this.data = data;
	}
}
