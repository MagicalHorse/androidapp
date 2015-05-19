package com.shenma.yueba.baijia.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.modle.FragmentBean;

public class CircleView {
	static CircleView quanziControlView;
	Activity activity;
	List<FragmentBean> fragment_list=new ArrayList<FragmentBean>();
	List<View> footer_list=new ArrayList<View>();
	//当前选中的id
	int currid=-1;
	View v;
	PullToRefreshGridView baijia_quanzi_layout_tanb1_gridbview;
	LinearLayout showloading_layout_view;
	List<Object> obj_list=new ArrayList<Object>();
	LayoutInflater inflater;
	
	public static CircleView the()
	{
		if(quanziControlView==null)
		{
			quanziControlView=new CircleView();
		}
		return quanziControlView;
	}

	public View getView(Activity activity)
	{
		this.activity=activity;
		if(v==null)
		{

			inflater=activity.getLayoutInflater();
			v=inflater.inflate(R.layout.circleview_layout, null);
			initPullView();
			initView(v);
			requestFalshData();
		}
		baijia_quanzi_layout_tanb1_gridbview.setFocusable(false);
		baijia_quanzi_layout_tanb1_gridbview.setFocusableInTouchMode(false);
		return v;
	}
	
	void initPullView()
	{
		baijia_quanzi_layout_tanb1_gridbview=(PullToRefreshGridView)v.findViewById(R.id.baijia_quanzi_layout_tanb1_gridbview);
		 //设置标签显示的内容
		 //pulltorefreshscrollview.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");   
		 /*pulltorefreshscrollview.getLoadingLayoutProxy().setPullLabel("下拉刷新");  
		 pulltorefreshscrollview.getLoadingLayoutProxy().setRefreshingLabel("刷新中。。。");  
		 pulltorefreshscrollview.getLoadingLayoutProxy().setReleaseLabel("松开刷新");*/
		baijia_quanzi_layout_tanb1_gridbview.setMode(Mode.BOTH);
		 
		baijia_quanzi_layout_tanb1_gridbview.setOnPullEventListener(new OnPullEventListener<GridView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<GridView> refreshView,
					State state, Mode direction) {
				
				if(direction==Mode.PULL_FROM_START)
				{
					baijia_quanzi_layout_tanb1_gridbview.getLoadingLayoutProxy().setPullLabel("上拉刷新");  
					baijia_quanzi_layout_tanb1_gridbview.getLoadingLayoutProxy().setRefreshingLabel("刷新中。。。");  
					baijia_quanzi_layout_tanb1_gridbview.getLoadingLayoutProxy().setReleaseLabel("松开刷新");
				}else if(direction==Mode.PULL_FROM_END)
				{
					baijia_quanzi_layout_tanb1_gridbview.getLoadingLayoutProxy().setPullLabel("下拉加载");  
					baijia_quanzi_layout_tanb1_gridbview.getLoadingLayoutProxy().setRefreshingLabel("加载中。。。");  
					baijia_quanzi_layout_tanb1_gridbview.getLoadingLayoutProxy().setReleaseLabel("松开加载");
				}
			}
		});
		 
		baijia_quanzi_layout_tanb1_gridbview.setOnRefreshListener(new OnRefreshListener2() {

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
	
	void initView(View v)
	{
		baijia_quanzi_layout_tanb1_gridbview=(PullToRefreshGridView)v.findViewById(R.id.baijia_quanzi_layout_tanb1_gridbview);
		showloading_layout_view=(LinearLayout)v.findViewById(R.id.showloading_layout_view);
		
		baijia_quanzi_layout_tanb1_gridbview.setAdapter(baseAdapter);
		baijia_quanzi_layout_tanb1_gridbview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
			}
		});
	}
	
	
	
	
	BaseAdapter baseAdapter=new BaseAdapter()
	{

		@Override
		public int getCount() {
			
			return obj_list.size();
		}

		@Override
		public Object getItem(int position) {
			
			return null;
		}

		@Override
		public long getItemId(int position) {
			
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			Holder holder;
			if(convertView==null)
			{
				holder=new Holder();
				convertView=inflater.inflate(R.layout.circle_item, null);
				holder.v=convertView;
				convertView.setTag(holder);
			}else
			{
				holder=(Holder)convertView.getTag();
				
			}
			
			return convertView;
		}
		
	};
	
	class Holder
	{
		View v;
	}
	
	void requestData()
	{
		baijia_quanzi_layout_tanb1_gridbview.setRefreshing();
		new Thread()
		{
			public void run() {
				SystemClock.sleep(100);
				activity.runOnUiThread(new Runnable() {
					
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
		baijia_quanzi_layout_tanb1_gridbview.setRefreshing();
		new Thread()
		{
			public void run() {
				SystemClock.sleep(100);
				activity.runOnUiThread(new Runnable() {
					
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
			obj_list.add(null);
			
		}
		showloading_layout_view.setVisibility(View.GONE);
		baseAdapter.notifyDataSetChanged();
		//ListUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		baijia_quanzi_layout_tanb1_gridbview.onRefreshComplete();
	}
	
	void falshData()
	{
		obj_list.clear();
		for(int i=0;i<10;i++)
		{
			obj_list.add(null);
			
		}
		showloading_layout_view.setVisibility(View.GONE);
		baseAdapter.notifyDataSetChanged();
		
		//ListUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		baijia_quanzi_layout_tanb1_gridbview.onRefreshComplete();
		
	}
	
}
