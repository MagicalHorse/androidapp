package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-4 下午2:01:59  
 * 程序的简单说明  
 */

public class TuiJianCircleInfo implements Serializable{

	int Id;
	String Name="";
	String Logo="";//圈子头像
	int MemberCount;
    String Address="";//海淀南路100号"
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getLogo() {
		return Logo;
	}
	public void setLogo(String logo) {
		Logo = logo;
	}
	public int getMemberCount() {
		return MemberCount;
	}
	public void setMemberCount(int memberCount) {
		MemberCount = memberCount;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}

}
