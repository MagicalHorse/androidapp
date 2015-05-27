package com.shenma.yueba.baijia.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.ShopMainActivity;
import com.shenma.yueba.baijia.modle.FragmentBean;
import com.shenma.yueba.baijia.modle.ImageStringBean;
import com.shenma.yueba.baijia.modle.RequestProductListInfoBean;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ListViewUtils;
import com.shenma.yueba.view.FixedSpeedScroller;
import com.shenma.yueba.view.imageshow.CustomImageView;

public class BuyerStreetFragment extends Fragment {
	List<FragmentBean> fragment_list = new ArrayList<FragmentBean>();
	List<View> footer_list = new ArrayList<View>();
	// 当前选中的id （ViewPager选中的id）
	int currid = -1;
	FragmentManager fragmentManager;
	ListView baijia_contact_listview;
	TextView focus_textview;
	// tab原点的 父视图
	LinearLayout baijia_head_layout;
	View v;
	PullToRefreshScrollView pulltorefreshscrollview;
	ViewPager baijia_head_viewpager;
	List<ImageStringBean> icon_list = new ArrayList<ImageStringBean>();
	List<Object> obj_list = new ArrayList<Object>();
	LayoutInflater inflater;
	LinearLayout showloading_layout_view;
	Timer timer;
	CustomPagerAdapter pagerAdapter;
	HttpControl httpContril=new HttpControl();
	int page=1;
	int pagesize=1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		if (v == null) {
			v = inflater.inflate(R.layout.buyersteetfragment_layout, null);
			initPullView();
			initView(v);
			requestFalshData();
		}
		ViewGroup vp = (ViewGroup) v.getParent();
		if (vp != null) {
			vp.removeView(v);
		}
		// return super.onCreateView(inflater, container, savedInstanceState);
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

	void initPullView() {
		pulltorefreshscrollview = (PullToRefreshScrollView) v.findViewById(R.id.pulltorefreshscrollview);
		// 设置标签显示的内容
		// pulltorefreshscrollview.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");
		pulltorefreshscrollview.getLoadingLayoutProxy().setPullLabel("下拉刷新");
		pulltorefreshscrollview.getLoadingLayoutProxy().setRefreshingLabel("刷新中。。。");
		pulltorefreshscrollview.getLoadingLayoutProxy().setReleaseLabel("松开刷新");
		pulltorefreshscrollview.setMode(Mode.BOTH);
		pulltorefreshscrollview
				.setOnPullEventListener(new OnPullEventListener<ScrollView>() {

					@Override
					public void onPullEvent(
							PullToRefreshBase<ScrollView> refreshView,
							State state, Mode direction) {
						if (direction == Mode.PULL_FROM_START) {
							pulltorefreshscrollview.getLoadingLayoutProxy()
									.setPullLabel("上拉刷新");
							pulltorefreshscrollview.getLoadingLayoutProxy()
									.setRefreshingLabel("刷新中。。。");
							pulltorefreshscrollview.getLoadingLayoutProxy()
									.setReleaseLabel("松开刷新");
						} else if (direction == Mode.PULL_FROM_END) {
							pulltorefreshscrollview.getLoadingLayoutProxy()
									.setPullLabel("下拉加载");
							pulltorefreshscrollview.getLoadingLayoutProxy()
									.setRefreshingLabel("加载中。。。");
							pulltorefreshscrollview.getLoadingLayoutProxy()
									.setReleaseLabel("松开加载");
						}
					}
				});

		pulltorefreshscrollview.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {

				// SystemClock.sleep(100);
				Log.i("TAG", "onPullDownToRefresh");
				// pulltorefreshscrollview.setRefreshing();
				requestFalshData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// SystemClock.sleep(100);
				// pulltorefreshscrollview.setRefreshing();
				Log.i("TAG", "onPullUpToRefresh");
				requestData();
			}
		});
	}

	void initView(View v) {
		baijia_head_layout = (LinearLayout) v
				.findViewById(R.id.baijia_head_layout);
		focus_textview = (TextView) v.findViewById(R.id.focus_textview);
		showloading_layout_view = (LinearLayout) v
				.findViewById(R.id.showloading_layout_view);
		baijia_contact_listview = (ListView) v
				.findViewById(R.id.baijia_contact_listview);
		baijia_contact_listview.setAdapter(baseAdapter);

		baijia_head_viewpager = (ViewPager) v
				.findViewById(R.id.baijia_head_viewpager);
		baijia_head_viewpager
				.setOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int arg0) {
						currid = arg0;
						setcurrItem(arg0);
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {

					}
				});

		baijia_head_viewpager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					stopTimerToViewPager();
					break;
				case MotionEvent.ACTION_UP:
					startTimeToViewPager();
					break;
				}
				return false;
			}
		});

		baijia_contact_listview
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Intent intent = new Intent(getActivity(),
								ShopMainActivity.class);
						startActivity(intent);
					}
				});
	}

	void getImageView(ImageStringBean bean) {
		BitmapUtils bitmapUtils = new BitmapUtils(getActivity());
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
		bitmapUtils.display(bean.getIv(), bean.getIconurl());

	}

	BaseAdapter baseAdapter = new BaseAdapter() {

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
			if (convertView == null) {
				holder = new Holder();
				convertView = inflater.inflate(R.layout.buyersteetfragment_item, null);
				holder.v = convertView;
				holder.customImage=(CustomImageView)v.findViewById(R.id.baijia_tab1_item_icon_imageview);
				holder.baijia_tab1_item_productname_textview=(TextView)v.findViewById(R.id.baijia_tab1_item_productname_textview);
				holder.baijia_tab1_item_productaddress_textview=(TextView)v.findViewById(R.id.baijia_tab1_item_productaddress_textview);
				holder.baijia_tab1_item_time_textview=(TextView)v.findViewById(R.id.baijia_tab1_item_time_textview);
				holder.baijia_tab1_item_productcontent_imageview=(ImageView)v.findViewById(R.id.baijia_tab1_item_productcontent_imageview);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();

			}
			holder.baijia_tab1_item_productname_textview.setText("");
			return convertView;
		}

	};

	
	void setImageView(String url,Bitmap bitmap)
	{
		View v=baijia_contact_listview.findViewWithTag(url);
		if(v!=null && v instanceof CustomImageView)
		{
			if(bitmap==null)
			{
				BitmapDrawable drawable=(BitmapDrawable)getActivity().getResources().getDrawable(R.drawable.test002);
				bitmap=drawable.getBitmap();
			}
			((CustomImageView)v).setSrc(getActivity(), bitmap, 0);
		}
	}
	
	
	class Holder {
		View v;
		CustomImageView customImage;
		TextView baijia_tab1_item_productname_textview,baijia_tab1_item_productaddress_textview,baijia_tab1_item_time_textview;
		ImageView baijia_tab1_item_productcontent_imageview;
	}

	
	/******
	 * 上啦加载数据
	 * ***/
	void requestData() {
		pulltorefreshscrollview.setRefreshing();
		sendRequestData(1);
	}

	
	/******
	 * 下拉刷新数据
	 * ***/
	void requestFalshData() {
		pulltorefreshscrollview.setRefreshing();
		stopTimerToViewPager();
		page=1;
		currid=-1;
		obj_list.clear();
		icon_list.clear();
		sendRequestData(0);
		
	}

	
	/******
	 * 与网络通信请求数据
	 * @param type int 0 刷新  1 加载
	 * ***/
	void sendRequestData(final int type)
	{
          httpContril.getProduceHomeListData(page, pagesize, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				if(obj!=null && obj instanceof RequestProductListInfoBean)
				{
					RequestProductListInfoBean bean=(RequestProductListInfoBean)obj;
					
				}
				/*if(obj!=null && obj instanceof RequestProduceListInfoBean)
				{
					
					if(data!=null)
					{
						int totalPager=data.getTotalpaged();
						int currPager=data.getPageindex();
						if(currPager>=totalPager)
						{
							pulltorefreshscrollview.setMode(Mode.PULL_FROM_START);
						}else
						{
							pulltorefreshscrollview.setMode(Mode.BOTH);
						}
			            switch(type)
			            {
			            case 0:
			            	falshData(data);
			            	break;
			            case 1:
			            	addData(data);
			            	break;
			            }
						
					}else
					{
						MyApplication.getInstance().showMessage(getActivity(), "没有任何数据");
					}
				}*/
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				
				MyApplication.getInstance().showMessage(getActivity(), msg);
			}
		}, getActivity());
	}
	
	/***
	 * 加载数据
	 * **/
	void addData(Object data) {
		showloading_layout_view.setVisibility(View.GONE);
		//addProduceInfoData(data.getProducts());
		baseAdapter.notifyDataSetChanged();
		ListViewUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pulltorefreshscrollview.onRefreshComplete();
	}

	/***
	 * 刷新viewpager数据
	 * ***/
	void falshData(Object data) {
		showloading_layout_view.setVisibility(View.GONE);
		//viewpager
		currid=-1;
		pagerAdapter = new CustomPagerAdapter();
		baijia_head_viewpager.setAdapter(pagerAdapter);
		if(icon_list.size()>0)
		{
			setcurrItem(0);
		}
		pagerAdapter.notifyDataSetChanged();
        startTimeToViewPager();
		//baseAdapter.notifyDataSetChanged();
        ListViewUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pulltorefreshscrollview.onRefreshComplete();
		
		
	}
	
	/****
	 * 数据负值
	 * **/
	void addProduceInfoData(Object beanlist)
	{
		/*if(beanlist!=null)
		{
			for(int i=0;i<beanlist.size();i++)
			{
				obj_list.add(beanlist.get(i));
			}
		}*/
	}

	@Override
	public void onResume() {

		super.onResume();
		pulltorefreshscrollview.setFocusable(false);
		pulltorefreshscrollview.setFocusableInTouchMode(false);
		startTimeToViewPager();
	}

	@Override
	public void onPause() {

		super.onPause();
		stopTimerToViewPager();
	}

	/***
	 * 添加原点
	 * 
	 * @param size
	 *            int 原点的个数
	 * @param value
	 *            int 当前选中的tab
	 * **/
	void addTabImageView(int size, int value) {
		baijia_head_layout.removeAllViews();
		for (int i = 0; i < size; i++) {
			View v = new View(getActivity());
			v.setBackgroundResource(R.drawable.tabround_background);
			int width = (int) getActivity().getResources().getDimension(
					R.dimen.shop_main_lineheight8_dimen);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
			params.leftMargin = (int) getActivity().getResources().getDimension(R.dimen.shop_main_width3_dimen);
			baijia_head_layout.addView(v, params);
			if (i == value%size) {
				v.setSelected(true);
			} else {
				v.setSelected(false);
			}
		}
	}

	/****
	 * 设置当前显示的 item
	 * **/
	void setcurrItem(int value) {
		baijia_head_viewpager.setCurrentItem(value);
		addTabImageView(icon_list.size(), value);
	}

	/*****
	 * 启动自动滚动
	 * **/
	void startTimeToViewPager() {
		stopTimerToViewPager();
		if(icon_list==null || icon_list.size()<=1)
		{
			return;
		}
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				currid++;
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						setViewPagerDuration(1000);
						setcurrItem(currid);
					}
				});
			}
		}, 2000, 3000);
	}

	/*****
	 * 停止自动滚动
	 * **/
	void stopTimerToViewPager() {
		setViewPagerDuration(500);
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	class CustomPagerAdapter extends PagerAdapter {

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == arg1;
		}

		@Override
		public int getCount() {

			if(icon_list.size()<1)
			{
				return 0;
			}
		    else if(icon_list.size()==1)
			{
				return 1;
			}else
			{
				return Integer.MAX_VALUE;
			}
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageStringBean imageStringBean = icon_list.get(position%icon_list.size());
			View v = imageStringBean.getIv();
			if(v.getParent()!=null)
			{
				((ViewGroup)v.getParent()).removeView(v);
			}
			container.addView(v, 0, new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));
			getImageView(imageStringBean);
			return v;
			// return super.instantiateItem(container, position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageStringBean imageStringBean = icon_list.get(position%icon_list.size());
			View v = imageStringBean.getIv();
			container.removeView(v);
			// super.destroyItem(container, position, object);
		}

	};

	// 设置滑动速度
	void setViewPagerDuration(int value) {
		try {
			Field field = ViewPager.class.getDeclaredField("mScroller");
			field.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(
					baijia_head_viewpager.getContext(),
					new AccelerateInterpolator());
			field.set(baijia_head_viewpager, scroller);
			scroller.setmDuration(value);
		} catch (Exception e) {
		}
	}
}
