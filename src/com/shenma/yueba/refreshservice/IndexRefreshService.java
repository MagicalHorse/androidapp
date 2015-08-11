package com.shenma.yueba.refreshservice;

import java.util.ArrayList;
import java.util.List;

import com.shenma.yueba.inter.IndexRefreshInter;

public class IndexRefreshService {

	private List<IndexRefreshInter> mList = new ArrayList<IndexRefreshInter>();

	public void addToList(IndexRefreshInter inter) {
		if (!mList.contains(inter)) {
			mList.add(inter);
		}
	}

	public void removeFromList(IndexRefreshInter inter) {
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
