package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-8 下午5:40:28  
 * 程序的简单说明  用户动态对象
 */

public class UserDynamicInfo implements Serializable{
     int Type;//":             动态类型 0：关注了某人   1：加入了圈子  2：购买了商品   3：赞了某件商品
	String Context="";//加入圈子",  动态描述
	int DataId;//": 资源id   对应类型的商品id，或者用户id，或者圈子id，
	String DataLogo="";//http://123.57.52.187:9550/customerportrait/default_100x100.jpg",   资源的logo
	int UserId;//
	String UserName="";//当前用户的名字",
	String Logo="";//http://123.57.52.187:9550/customerportrait/default_100x100.jpg", 当前用户的logo
	String CreateTime="";//2015-06-24T11:21:02"
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	public String getContext() {
		return Context;
	}
	public void setContext(String context) {
		Context = context;
	}
	public int getDataId() {
		return DataId;
	}
	public void setDataId(int dataId) {
		DataId = dataId;
	}
	public String getDataLogo() {
		return DataLogo;
	}
	public void setDataLogo(String dataLogo) {
		DataLogo = dataLogo;
	}
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getLogo() {
		return Logo;
	}
	public void setLogo(String logo) {
		Logo = logo;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

}
