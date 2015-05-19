package com.shenma.yueba.baijia.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.BrandAdapter;
import com.shenma.yueba.baijia.modle.BrandListBean;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-19 下午2:03:37  
 * 程序的简单说明  
 */

public class BrandListView {
	static BrandListView msgListView;
	Activity activity;
	LayoutInflater layoutInflater;
	
	private View view;
	private PullToRefreshListView pull_refresh_list;
	LinearLayout showloading_layout_view;
	private BrandAdapter brandAdapter;
	private List<BrandListBean> mList = new ArrayList<BrandListBean>();
	public static BrandListView the()
	{
		if(msgListView==null)
		{
			msgListView=new BrandListView();
		}
		return msgListView;
	}
	
	public View getView(Activity activity)
	{
		this.activity=activity;
		if(view==null)
		{
			layoutInflater=activity.getLayoutInflater();
			initView();
			initPullView();
			requestFalshData();
		}
		return view;
	}
	
	void initView()
	{
		view=layoutInflater.inflate(R.layout.refresh_listview_without_title_layout, null);
	}
	
	void initPullView()
	{
		pull_refresh_list=(PullToRefreshListView)view.findViewById(R.id.pull_refresh_list);
		showloading_layout_view=(LinearLayout)view.findViewById(R.id.showloading_layout_view);
		pull_refresh_list.setMode(Mode.BOTH);
		 
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
		brandAdapter=new BrandAdapter(activity, mList);
		pull_refresh_list.setAdapter(brandAdapter);
	}
	
	
	void requestData()
	{
		pull_refresh_list.setRefreshing();
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
		pull_refresh_list.setRefreshing();
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
			mList.add(new BrandListBean());
			
		}
		showloading_layout_view.setVisibility(View.GONE);
		brandAdapter.notifyDataSetChanged();
		//ListUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pull_refresh_list.onRefreshComplete();
	}
	
	void falshData()
	{
		mList.clear();
		for(int i=0;i<10;i++)
		{
			mList.add(new BrandListBean());
			
		}
		showloading_layout_view.setVisibility(View.GONE);
		brandAdapter.notifyDataSetChanged();
		
		//ListUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pull_refresh_list.onRefreshComplete();
		
	}
}
