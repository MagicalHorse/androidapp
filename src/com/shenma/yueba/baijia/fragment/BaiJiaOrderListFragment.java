package com.shenma.yueba.baijia.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.BaiJiaOrderListAdapter;
import com.shenma.yueba.baijia.fragment.BuyerStreetFragment.CustomPagerAdapter;
import com.shenma.yueba.baijia.modle.HomeProductListInfoBean;
import com.shenma.yueba.baijia.modle.ProductListInfoBean;
import com.shenma.yueba.baijia.modle.RequestProductListInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.ListViewUtils;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;

/**
 * @author gyj
 * @version 创建时间：2015-6-2 上午11:54:43 程序的简单说明 败家订单列表
 */

@SuppressLint("ValidFragment")
public class BaiJiaOrderListFragment extends Fragment {
	View parentView;
	PullToRefreshListView pull_refresh_list;
	BaiJiaOrderListAdapter baiJiaOrderListAdapter;
	List<Object> object_list=new ArrayList<Object>();
	int currpage=Constants.CURRPAGE_VALUE;
	int pagersize=Constants.PAGESIZE_VALUE;
	int type=-1;//订单类型 -1 全部
	
	public BaiJiaOrderListFragment(int type)
	{
		this.type=type;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(parentView==null)
		{
			parentView=inflater.inflate(R.layout.baijiaorderlistfragment_layout, null);
			initView();
			requestFalshData();
		}
		ViewGroup vp=(ViewGroup)parentView.getParent();
		if(vp!=null)
		{
			vp.removeView(parentView);
		}
		return parentView;
	}
	
	void initView()
	{
		pull_refresh_list=(PullToRefreshListView)parentView.findViewById(R.id.pull_refresh_list);
		 //设置标签显示的内容
		pull_refresh_list.setMode(Mode.BOTH);
		baiJiaOrderListAdapter=new BaiJiaOrderListAdapter(object_list,getActivity());
		pull_refresh_list.setAdapter(baiJiaOrderListAdapter);
		
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
				
				requestFalshData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				requestData();
			}
		});
	}
	
	/*****
	 *请求加载数据
	 * ***/
	void requestData()
	{
		pull_refresh_list.setRefreshing();
		
		sendRequestData(1);
	}
	
	/*****
	 *请求刷新数据
	 * ***/
	void requestFalshData()
	{
		pull_refresh_list.setRefreshing();
		
		sendRequestData(0);
	}
	
	/******
	 * 与网络通信请求数据
	 * 
	 * @param type
	 *            int 0 刷新 1 加载
	 * ***/
	void sendRequestData(final int type) {
		falshData(null);
	}
	
	/***
	 * 刷新viewpager数据
	 * ***/
	void falshData(HomeProductListInfoBean data) {
		
		pull_refresh_list.onRefreshComplete();
		object_list.add(null);
		if(baiJiaOrderListAdapter!=null)
		{
			baiJiaOrderListAdapter.notifyDataSetChanged();
		}
	}
	
	
	/***
	 * 加载数据
	 * **/
	void addData(HomeProductListInfoBean data) {
		
		pull_refresh_list.onRefreshComplete();
		object_list.add(null);
		if(baiJiaOrderListAdapter!=null)
		{
			baiJiaOrderListAdapter.notifyDataSetChanged();
		}
	}
}
