package com.shenma.yueba.baijia.fragment;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.MyBuyerAdapter;
import com.shenma.yueba.baijia.adapter.MyCircleAdapter;
import com.shenma.yueba.baijia.modle.MyBuyerBean;
import com.shenma.yueba.baijia.modle.MyCircleBean;

import android.os.Bundle;
import android.util.Log;
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
	private MyBuyerAdapter myBuyerAdapter;
	private List<MyBuyerBean> mList = new ArrayList<MyBuyerBean>();
	private View view;
	private PullToRefreshListView pull_refresh_list;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("aaaaa", "MyBuyerFragment");
		if (view == null) {
			view = inflater.inflate(R.layout.refresh_listview_without_title_layout, null);
			pull_refresh_list = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
			pull_refresh_list.setMode(Mode.BOTH);
			pull_refresh_list.setAdapter(new MyBuyerAdapter(getActivity(), mList));
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}
	
	
}
