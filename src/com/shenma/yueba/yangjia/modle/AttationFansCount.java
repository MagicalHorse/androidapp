package com.shenma.yueba.yangjia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-21 上午9:40:28  
 * 程序的简单说明   关注的人的数量详情：仅当获取我的关注时显示
 */

public class AttationFansCount implements Serializable{
	int All;//
	int Friend;
	int DaoGou;
	int RenZhengDaoGou;
	int Daren;
	int Other;
	public int getAll() {
		return All;
	}
	public void setAll(int all) {
		All = all;
	}
	public int getFriend() {
		return Friend;
	}
	public void setFriend(int friend) {
		Friend = friend;
	}
	public int getDaoGou() {
		return DaoGou;
	}
	public void setDaoGou(int daoGou) {
		DaoGou = daoGou;
	}
	public int getRenZhengDaoGou() {
		return RenZhengDaoGou;
	}
	public void setRenZhengDaoGou(int renZhengDaoGou) {
		RenZhengDaoGou = renZhengDaoGou;
	}
	public int getDaren() {
		return Daren;
	}
	public void setDaren(int daren) {
		Daren = daren;
	}
	public int getOther() {
		return Other;
	}
	public void setOther(int other) {
		Other = other;
	}

}
