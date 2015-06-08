package com.shenma.yueba.yangjia.modle;

import java.util.List;

public class CircleDetailBean {

	private String GroupName;
	private String GroupPic;
	private String MemberCount;

	private List<Users> Users;

	public String getGroupName() {
		return GroupName;
	}

	public void setGroupName(String groupName) {
		GroupName = groupName;
	}

	public String getGroupPic() {
		return GroupPic;
	}

	public void setGroupPic(String groupPic) {
		GroupPic = groupPic;
	}

	public String getMemberCount() {
		return MemberCount;
	}

	public void setMemberCount(String memberCount) {
		MemberCount = memberCount;
	}

	public List<Users> getUsers() {
		return Users;
	}

	public void setUsers(List<Users> users) {
		Users = users;
	}

}
