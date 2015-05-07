package com.shenma.yueba.yangjia.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.BrandAdapter;
import com.shenma.yueba.baijia.fragment.BaseFragment;
import com.shenma.yueba.yangjia.adapter.SalesManagerForAttestationBuyerAdapter;
import com.shenma.yueba.yangjia.modle.SalesManagerForAttestationBuyerListBean;

public class ItemCustomerFragment extends BaseFragment{
	private View rootView;// 缓存Fragment view
	private PullToRefreshListView rlv;
	private List<SalesManagerForAttestationBuyerListBean> mList = new ArrayList<SalesManagerForAttestationBuyerListBean>();
	private SalesManagerForAttestationBuyerAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.refresh_listview_without_title_layout,
					container, false);
			rlv = (PullToRefreshListView) rootView.findViewById(R.id.pull_refresh_list);
			rlv.setMode(Mode.BOTH);
			rlv.setAdapter(new SalesManagerForAttestationBuyerAdapter(getActivity(), mList));
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}
}
