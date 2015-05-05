package com.shenma.yueba.baijia.fragment;

import com.shenma.yueba.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 我的买手
 * 
 * @author a
 * 
 */

public class MyBuyerFragment extends BaseFragment {
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.refresh_listview_without_title_layout,
				null);
		return view;
	}

}
