package com.shenma.yueba.yangjia.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.CircleInfoActivity;
import com.shenma.yueba.baijia.fragment.BaseFragment;
import com.shenma.yueba.baijia.modle.MyCircleBean;
import com.shenma.yueba.yangjia.adapter.MyCircleForSocialAdapter;

/**
 * 社交管理中的-----我的圈子
 * 
 * @author a
 * 
 */
public class MyCircleForSocialFragment extends BaseFragment {

	private PullToRefreshListView pull_refresh_list;
	private MyCircleForSocialAdapter adapter;
	private List<MyCircleBean> circleList = new ArrayList<MyCircleBean>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(
				R.layout.refresh_listview_without_title_layout, null);
		pull_refresh_list = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		adapter = new MyCircleForSocialAdapter(getActivity(), circleList);
		pull_refresh_list.setAdapter(adapter);
		pull_refresh_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(),CircleInfoActivity.class);
				startActivity(intent);
			}
		});
		return view;
	}
}