package com.shenma.yueba.baijia.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.BitmapUtils;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.ShopMainActivity;
import com.shenma.yueba.baijia.modle.FragmentBean;
import com.shenma.yueba.baijia.modle.ImageStringBean;
import com.shenma.yueba.util.ListViewUtils;

public class BuyerStreetFragment extends Fragment{
	List<FragmentBean> fragment_list=new ArrayList<FragmentBean>();
	List<View> footer_list=new ArrayList<View>();
	//当前选中的id
	int currid=-1;
	FragmentManager fragmentManager;
	ListView baijia_contact_listview;
	TextView focus_textview;
	View v;
	PullToRefreshScrollView pulltorefreshscrollview;
	ViewPager baijia_head_viewpager;
	List<ImageStringBean> icon_list=new ArrayList<ImageStringBean>();
	List<Object> obj_list=new ArrayList<Object>();
	LayoutInflater inflater;
	LinearLayout showloading_layout_view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater=inflater;
		if(v==null)
		{
			v=inflater.inflate(R.layout.buyersteetfragment_layout, null);
			initPullView();
			initView(v);
			requestFalshData();
		}
		ViewGroup vp=(ViewGroup)v.getParent();
		if(vp!=null)
		{
			vp.removeView(v);
		}
		//return super.onCreateView(inflater, container, savedInstanceState);
		focus_textview.setFocusable(true);
		focus_textview.setFocusableInTouchMode(true);
		pulltorefreshscrollview.setFocusable(false);
		pulltorefreshscrollview.setFocusableInTouchMode(false);
		return v;
		
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
	}
	
	
	void initPullView()
	{
		pulltorefreshscrollview=(PullToRefreshScrollView)v.findViewById(R.id.pulltorefreshscrollview);
		 //设置标签显示的内容
		 //pulltorefreshscrollview.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");   
		 pulltorefreshscrollview.getLoadingLayoutProxy().setPullLabel("下拉刷新");  
		 pulltorefreshscrollview.getLoadingLayoutProxy().setRefreshingLabel("刷新中。。。");  
		 pulltorefreshscrollview.getLoadingLayoutProxy().setReleaseLabel("松开刷新");
		 pulltorefreshscrollview.setMode(Mode.BOTH);
		 pulltorefreshscrollview.setOnPullEventListener(new OnPullEventListener<ScrollView>() {

			@Override
			public void onPullEvent(
					PullToRefreshBase<ScrollView> refreshView, State state,
					Mode direction) {
				if(direction==Mode.PULL_FROM_START)
				{
					pulltorefreshscrollview.getLoadingLayoutProxy().setPullLabel("上拉刷新");  
					 pulltorefreshscrollview.getLoadingLayoutProxy().setRefreshingLabel("刷新中。。。");  
					 pulltorefreshscrollview.getLoadingLayoutProxy().setReleaseLabel("松开刷新");
				}else if(direction==Mode.PULL_FROM_END)
				{
					pulltorefreshscrollview.getLoadingLayoutProxy().setPullLabel("下拉加载");  
					 pulltorefreshscrollview.getLoadingLayoutProxy().setRefreshingLabel("加载中。。。");  
					 pulltorefreshscrollview.getLoadingLayoutProxy().setReleaseLabel("松开加载");
				}
			}
		});
		 
		 pulltorefreshscrollview.setOnRefreshListener(new OnRefreshListener2() {

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
		focus_textview=(TextView)v.findViewById(R.id.focus_textview);
		String iconurl="http://img4.imgtn.bdimg.com/it/u=716148157,58117191&fm=21&gp=0.jpg";
		showloading_layout_view=(LinearLayout)v.findViewById(R.id.showloading_layout_view);
		for(int i=0;i<5;i++)
		{
			icon_list.add(new ImageStringBean(getActivity(), iconurl));
		}
		baijia_contact_listview=(ListView)v.findViewById(R.id.baijia_contact_listview);
		baijia_contact_listview.setAdapter(baseAdapter);
		
		baijia_head_viewpager=(ViewPager)v.findViewById(R.id.baijia_head_viewpager);
		baijia_head_viewpager.setAdapter(new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				
				return arg0==arg1;
			}
			
			@Override
			public int getCount() {
				
				return icon_list.size();
			}
			
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				ImageStringBean imageStringBean=icon_list.get(position);
				View v=imageStringBean.getIv();
				container.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
				getImageView(icon_list.get(position));
				return v;
				//return super.instantiateItem(container, position);
			}
			
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				ImageStringBean imageStringBean=icon_list.get(position);
				View v=imageStringBean.getIv();
				container.removeView(v);
				//super.destroyItem(container, position, object);
			}
			
		});
		
		baijia_contact_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(getActivity(),ShopMainActivity.class);
				startActivity(intent);
			}
		});
	}
	
	
	void getImageView(ImageStringBean bean)
	{
		BitmapUtils bitmapUtils=new BitmapUtils(getActivity());
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
		bitmapUtils.display(bean.getIv(), bean.getIconurl());
		
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
				convertView=inflater.inflate(R.layout.buyersteetfragment_item, null);
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
		pulltorefreshscrollview.setRefreshing();
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
		pulltorefreshscrollview.setRefreshing();
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
			obj_list.add(null);
			
		}
		showloading_layout_view.setVisibility(View.GONE);
		baseAdapter.notifyDataSetChanged();
		ListViewUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pulltorefreshscrollview.onRefreshComplete();
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
		
		ListViewUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pulltorefreshscrollview.onRefreshComplete();
		
	}
	
	@Override
	public void onResume() {
		
		super.onResume();
		pulltorefreshscrollview.setFocusable(false);
		pulltorefreshscrollview.setFocusableInTouchMode(false);
	}
	
	@Override
	public void onPause() {
		 
		super.onPause();
	}
}
