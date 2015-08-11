package com.shenma.yueba.refreshservice;

import java.util.ArrayList;
import java.util.List;

import com.shenma.yueba.inter.HuoKuanManagerRefreshInter;

public class HuoKuanManagerRefreshService {

	private List<HuoKuanManagerRefreshInter> mList = new ArrayList<HuoKuanManagerRefreshInter>();

	public void addToList(HuoKuanManagerRefreshInter inter) {
		if (!mList.contains(inter)) {
			mList.add(inter);
		}
	}

	public void removeFromList(HuoKuanManagerRefreshInter inter) {
		if (mList.contains(inter)) {
			mList.remove(mList.indexOf(inter));
		}
	}

	public void refreshList() {
		for (int i = 0; i < mList.size(); i++) {
			mList.get(i).refresh();
		}
	}
}
