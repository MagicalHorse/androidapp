package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-6-17 下午2:46:15  
 * 程序的简单说明  败家 获取用户信息
 */

public class RequestUserInfoBean extends BaseRequest{

	UserInfoBean data=new UserInfoBean();

	public UserInfoBean getData() {
		return data;
	}

	public void setData(UserInfoBean data) {
		this.data = data;
	}
}
