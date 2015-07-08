package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-7-8 下午5:38:26  
 * 程序的简单说明  用户动态对象
 */

public class RequestUserDynamicInfoBean extends BaseRequest{
	UserDynamicInfoBean data=new UserDynamicInfoBean();

	public UserDynamicInfoBean getData() {
		return data;
	}

	public void setData(UserDynamicInfoBean data) {
		this.data = data;
	}
}
