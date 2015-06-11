package com.shenma.yueba.baijia.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.shenma.yueba.baijia.activity.WebActivity;
import com.shenma.yueba.baijia.adapter.BuyerAdapter;
import com.shenma.yueba.baijia.modle.BannersInfoBean;
import com.shenma.yueba.baijia.modle.FragmentBean;
import com.shenma.yueba.baijia.modle.HomeProductListInfoBean;
import com.shenma.yueba.baijia.modle.ProductListInfoBean;
import com.shenma.yueba.baijia.modle.ProductsInfoBean;
import com.shenma.yueba.baijia.modle.RequestProductListInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ListViewUtils;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.FixedSpeedScroller;

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
	View parentview;
	PullToRefreshScrollView pulltorefreshscrollview;
	ViewPager baijiasteetfragmnet_layout_head_viewpager;
	LayoutInflater inflater;
	LinearLayout showloading_layout_view;
	Timer timer;
	CustomPagerAdapter pagerAdapter;
	HttpControl httpContril = new HttpControl();
	// 当前页
	int currpage = Constants.CURRPAGE_VALUE;
	// 每页显示的条数
	int pagesize = Constants.PAGESIZE_VALUE;
	int maxcount = 8;
	//是否显示进度条
	boolean ishow=false;
	List<BannersInfoBean> Banners = new ArrayList<BannersInfoBean>();
	// 商品信息列表
	List<ProductsInfoBean> Products = new ArrayList<ProductsInfoBean>();
	BitmapUtils bitmapUtils;
    List<ImageView> imageViewlist=new ArrayList<ImageView>();
    BuyerAdapter buyerAdapter;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		if (parentview == null) {
			ishow=true;
			parentview = inflater.inflate(R.layout.buyersteetfragment_layout, null);
			bitmapUtils = new BitmapUtils(getActivity());
			initPullView();
			initView(parentview);
			requestFalshData();
		}
		ViewGroup vp = (ViewGroup)parentview.getParent();
		if (vp != null) {
			vp.removeView(parentview);
		}
		focus_textview.setFocusable(true);
		focus_textview.setFocusableInTouchMode(true);
		pulltorefreshscrollview.setFocusable(false);
		pulltorefreshscrollview.setFocusableInTouchMode(false);
		return parentview;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

	}

	void initPullView() {
		pulltorefreshscrollview = (PullToRefreshScrollView)parentview.findViewById(R.id.pulltorefreshscrollview);
		// 设置标签显示的内容
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

				requestFalshData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
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
		showloading_layout_view.setVisibility(View.GONE);
		baijia_contact_listview = (ListView) v
				.findViewById(R.id.baijia_contact_listview);
		baijiasteetfragmnet_layout_head_viewpager = (ViewPager) v
				.findViewById(R.id.baijiasteetfragmnet_layout_head_viewpager);
		baijiasteetfragmnet_layout_head_viewpager
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

		baijiasteetfragmnet_layout_head_viewpager.setOnTouchListener(new OnTouchListener() {

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
		
		baijia_contact_listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Intent intent = new Intent(getActivity(),ShopMainActivity.class);
						startActivity(intent);
					}
				});
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
		currpage = 1;
		currid = -1;
		sendRequestData(0);

	}

	/******
	 * 与网络通信请求数据
	 * 
	 * @param type
	 *            int 0 刷新 1 加载
	 * ***/
	void sendRequestData(final int type) {
		httpContril.getProduceHomeListData(currpage, pagesize,new HttpCallBackInterface() {

					@Override
					public void http_Success(Object obj) {
						pulltorefreshscrollview.onRefreshComplete();
						if (obj != null
								&& obj instanceof RequestProductListInfoBean) {
							RequestProductListInfoBean bean = (RequestProductListInfoBean) obj;
							HomeProductListInfoBean data = bean.getData();
							if (data != null) {
								int totalPage = data.getTotalpaged();
								if (currpage >= totalPage) {
									pulltorefreshscrollview
											.setMode(Mode.PULL_FROM_START);
								} else {
									pulltorefreshscrollview.setMode(Mode.BOTH);
								}
								switch (type) {
								case 0:
									falshData(data);
									break;
								case 1:
									addData(data);
									break;
								}
							} else {
								MyApplication.getInstance().showMessage(
										getActivity(), "没有任何数据");
							}

						}
					}

					@Override
					public void http_Fails(int error, String msg) {
						pulltorefreshscrollview.onRefreshComplete();
						MyApplication.getInstance().showMessage(getActivity(),
								msg);
					}
				}, getActivity(),ishow,true);
	}

	/***
	 * 加载数据
	 * **/
	void addData(HomeProductListInfoBean data) {
		ishow=false;
		showloading_layout_view.setVisibility(View.GONE);
		ProductListInfoBean item = data.getItems();
		if(item.getProducts()!=null)
		{
			Products.addAll(item.getProducts());
		}
		buyerAdapter.notifyDataSetChanged();
		ListViewUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pulltorefreshscrollview.onRefreshComplete();
	}

	/***
	 * 刷新viewpager数据
	 * ***/
	void falshData(HomeProductListInfoBean data) {
		ishow=false;
		showloading_layout_view.setVisibility(View.GONE);
		currid = -1;
		ProductListInfoBean item = data.getItems();
		Banners.clear();
		imageViewlist.clear();
		Products.clear();
		// 获取活动列表
		if (item != null && item.getBanners() != null) {
			Banners = item.getBanners();
			for (int i = 0; i < Banners.size(); i++) {
				ImageView imageView =new ImageView(getActivity());
				imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
				imageView.setImageResource(R.drawable.default_pic);
				imageView.setTag(Banners.get(i));
				initPic(ToolsUtil.nullToString(ToolsUtil.getImage(Banners.get(i).getPic(), 320, 0)), imageView,-1);
				imageViewlist.add(imageView);
				imageView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						BannersInfoBean bannersInfoBean=(BannersInfoBean)v.getTag();
						Intent intent=new Intent(getActivity(),WebActivity.class);
						intent.putExtra("url", bannersInfoBean.getLink());
						startActivity(intent);
					}
				});
			}
		}
		pagerAdapter = new CustomPagerAdapter();
		DisplayMetrics dm=new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width=dm.widthPixels;
		int height=width/2;
		baijiasteetfragmnet_layout_head_viewpager.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
		baijiasteetfragmnet_layout_head_viewpager.setAdapter(pagerAdapter);
		if (imageViewlist.size() > 0) {
			setcurrItem(0);
		}
		pagerAdapter.notifyDataSetChanged();
		startTimeToViewPager();
		
		if(item.getProducts()!=null)
		{
			Products.addAll(item.getProducts());
		}
		buyerAdapter=new BuyerAdapter(Products, getActivity());
		baijia_contact_listview.setAdapter(buyerAdapter);
		//重新计算listview的高度
		ListViewUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pulltorefreshscrollview.onRefreshComplete();

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
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					width, width);
			params.leftMargin = (int) getActivity().getResources()
					.getDimension(R.dimen.shop_main_width3_dimen);
			baijia_head_layout.addView(v, params);
			if (i == value % size) {
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
		baijiasteetfragmnet_layout_head_viewpager.setCurrentItem(value);
		addTabImageView(imageViewlist.size(), value);
	}

	/*****
	 * 启动自动滚动
	 * **/
	void startTimeToViewPager() {
		stopTimerToViewPager();
		if (imageViewlist == null || imageViewlist.size() <= 2) {
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

			if (imageViewlist.size() < 1) {
				return 0;
			} else if (imageViewlist.size() <=2) {
				return imageViewlist.size();
			} else {
				return Integer.MAX_VALUE;
			}
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			int a=position%imageViewlist.size();
			Log.i("TAG", "A:"+a);
			ImageView imageview=imageViewlist.get(a);
			if (imageview.getParent() != null) {
				((ViewGroup) imageview.getParent()).removeView(imageview);
			}
			//imageview.setImageResource(R.drawable.default_pic);
			imageview.setScaleType(ScaleType.FIT_XY);
			imageview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
			container.addView(imageview, 0);
			return imageview;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView imageview=imageViewlist.get(position%imageViewlist.size());
			container.removeView(imageview);
		}

	};

	// 设置滑动速度
	void setViewPagerDuration(int value) {
		try {
			Field field = ViewPager.class.getDeclaredField("mScroller");
			field.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(
					baijiasteetfragmnet_layout_head_viewpager.getContext(),
					new AccelerateInterpolator());
			field.set(baijiasteetfragmnet_layout_head_viewpager, scroller);
			scroller.setmDuration(value);
		} catch (Exception e) {
		}
	}

	
	/****
	 * 加载图片
	 * */
	void initPic(final String url, final ImageView iv, final int image) {
		Log.i("TAG", "URL:"+url);
		MyApplication.getInstance().getImageLoader().displayImage(url, iv, MyApplication.getInstance().getDisplayImageOptions());
	}
}
