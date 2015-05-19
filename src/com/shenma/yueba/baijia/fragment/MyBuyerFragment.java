package com.shenma.yueba.baijia.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.MyBuyerAdapter;
import com.shenma.yueba.baijia.adapter.MyCircleAdapter;
import com.shenma.yueba.baijia.modle.MyBuyerBean;
import com.shenma.yueba.baijia.modle.MyCircleBean;

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
	LinearLayout showloading_layout_view;
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
			showloading_layout_view=(LinearLayout)view.findViewById(R.id.showloading_layout_view);
			initPullView();
			requestFalshData();
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}
	
	void initPullView()
	{
		pull_refresh_list=(PullToRefreshListView)view.findViewById(R.id.pull_refresh_list);
		 //设置标签显示的内容
		pull_refresh_list.setMode(Mode.BOTH);
		myBuyerAdapter=new MyBuyerAdapter(getActivity(), mList); 
		pull_refresh_list.setAdapter(myBuyerAdapter);
		
		
		pull_refresh_list.setOnPullEventListener(new OnPullEventListener<ListView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
					State state, Mode direction) {
				
				 //设置标签显示的内容
				if(direction==Mode.PULL_FROM_START)
				{
					pull_refresh_list.getLoadingLayoutProxy().setPullLabel("上拉刷新");  
					pull_refresh_list.getLoadingLayoutProxy().setRefreshingLabel("刷新中。。。");  
					pull_refresh_list.getLoadingLayoutProxy().setReleaseLabel("松开刷新");
				}else if(direction==Mode.PULL_FROM_END)
				{
					pull_refresh_list.getLoadingLayoutProxy().setPullLabel("下拉加载");  
					pull_refresh_list.getLoadingLayoutProxy().setRefreshingLabel("加载中。。。");  
					pull_refresh_list.getLoadingLayoutProxy().setReleaseLabel("松开加载");
				}
			}
		});
		 
		pull_refresh_list.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				
				//SystemClock.sleep(100);
				Log.i("TAG", "onPullDownToRefresh");
				//pulltorefreshscrollview.setRefreshing();
				requestFalshData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				//SystemClock.sleep(100);
				//pulltorefreshscrollview.setRefreshing();
				Log.i("TAG", "onPullUpToRefresh");
				requestData();
			}
		});
	}
	
	
	void requestData()
	{
		pull_refresh_list.setRefreshing();
		new Thread()
		{
			public void run() {
				SystemClock.sleep(100);
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						
						addData();
					}
				});
			};
		}.start();
	}
	
	void requestFalshData()
	{
		pull_refresh_list.setRefreshing();
		new Thread()
		{
			public void run() {
				SystemClock.sleep(100);
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						
						falshData();
					}
				});
			};
		}.start();
	}
	
	
	void addData()
	{
		for(int i=0;i<10;i++)
		{
			mList.add(new MyBuyerBean());
			
		}
		showloading_layout_view.setVisibility(View.GONE);
		myBuyerAdapter.notifyDataSetChanged();
		//ListUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pull_refresh_list.onRefreshComplete();
	}
	
	void falshData()
	{
		mList.clear();
		for(int i=0;i<10;i++)
		{
			mList.add(new MyBuyerBean());
			
		}
		showloading_layout_view.setVisibility(View.GONE);
		myBuyerAdapter.notifyDataSetChanged();
		
		//ListUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pull_refresh_list.onRefreshComplete();
		
	}
	
}
