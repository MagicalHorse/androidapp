package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.MyCircleAdapter;
import com.shenma.yueba.baijia.modle.MyCircleBean;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-20 上午11:24:51  
 * 程序的简单说明  
 */

public class CircleListActivity extends BaseActivityWithTopView{
	PullToRefreshListView pull_refresh_list;
	LinearLayout showloading_layout_view;
	private List<MyCircleBean> mList = new ArrayList<MyCircleBean>();
	MyCircleAdapter myCircleAdapter;
	boolean ishow=false;
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		setContentView(R.layout.circlelistactivity_layout);
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		initView();
		requestFalshData();
	}
	
	void initView()
	{
		setTitle("圈子");
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				CircleListActivity.this.finish();
			}
		});
		showloading_layout_view=(LinearLayout)findViewById(R.id.showloading_layout_view);
		pull_refresh_list=(PullToRefreshListView)findViewById(R.id.circlelistactivity_layout_pulltorefreshlistview);
		pull_refresh_list.setMode(Mode.BOTH);
		pull_refresh_list.setOnPullEventListener(new OnPullEventListener<ListView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
					State state, Mode direction) {
				
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
		
		
		myCircleAdapter=new MyCircleAdapter(CircleListActivity.this, mList);
		pull_refresh_list.setAdapter(myCircleAdapter);
	}
	
	void requestData()
	{
		//pull_refresh_list.setRefreshing();
		new Thread()
		{
			public void run() {
				SystemClock.sleep(8000);
				CircleListActivity.this.runOnUiThread(new Runnable() {
					
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
		//pull_refresh_list.setRefreshing();
		new Thread()
		{
			public void run() {
				SystemClock.sleep(8000);
				CircleListActivity.this.runOnUiThread(new Runnable() {
					
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
			mList.add(new MyCircleBean());
			
		}
		showloading_layout_view.setVisibility(View.GONE);
		myCircleAdapter.notifyDataSetChanged();
		//ListUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pull_refresh_list.onRefreshComplete();
	}
	
	void falshData()
	{
		mList.clear();
		for(int i=0;i<10;i++)
		{
			mList.add(new MyCircleBean());
			
		}
		showloading_layout_view.setVisibility(View.GONE);
		myCircleAdapter.notifyDataSetChanged();
		
		//ListUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pull_refresh_list.onRefreshComplete();
		
	}
}
