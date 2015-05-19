package com.shenma.yueba.baijia.modle;

import android.support.v4.app.Fragment;


public class FragmentBean {
	String name = "";
	int icon;
	Object fragment = null;

	public FragmentBean(String name,int icon,Object fragment)
	{
		this.name=name;
		this.icon=icon;
		this.fragment=fragment;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public Object getFragment() {
		return fragment;
	}

	public void setFragment(Object fragment) {
		this.fragment = fragment;
	}

}
