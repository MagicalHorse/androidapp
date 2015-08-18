package com.shenma.yueba.baijia.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.WebActivity;
import com.shenma.yueba.baijia.adapter.BuyerAdapter;
import com.shenma.yueba.baijia.adapter.ScrollViewPagerAdapter;
import com.shenma.yueba.baijia.modle.BannersInfoBean;
import com.shenma.yueba.baijia.modle.FragmentBean;
import com.shenma.yueba.baijia.modle.HomeProductListInfoBean;
import com.shenma.yueba.baijia.modle.ProductListInfoBean;
import com.shenma.yueba.baijia.modle.ProductsInfoBean;
import com.shenma.yueba.baijia.modle.RequestProductListInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.FixedSpeedScroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BuyerStreetFragment extends Fragment {
	List<FragmentBean> fragment_list = new ArrayList<FragmentBean>();
	List<View> footer_list = new ArrayList<View>();
	// 当前选中的id （ViewPager选中的id）
	int currid = -1;
	FragmentManager fragmentManager;
	PullToRefreshListView baijia_contact_listview;
	// tab原点的 父视图
	LinearLayout baijia_head_layout;
	// 最新上新
	TextView buyersteet_newtextview;
	RelativeLayout baijiasteetfragmnet_layout_head_viewpager_relativelayout;
	View parentview;
	ViewPager baijiasteetfragmnet_layout_head_viewpager;
	LayoutInflater inflater;
	LinearLayout showloading_layout_view;
	Timer timer;
	ScrollViewPagerAdapter pagerAdapter;
	HttpControl httpContril = new HttpControl();
	// 当前页
	int currpage = Constants.CURRPAGE_VALUE;
	// 每页显示的条数
	int pagesize = Constants.PAGESIZE_VALUE;
	int maxcount = 8;
	// 是否显示进度条
	boolean ishow = false;
	List<BannersInfoBean> Banners = new ArrayList<BannersInfoBean>();
	// 商品信息列表
	List<ProductsInfoBean> Products = new ArrayList<ProductsInfoBean>();
	List<View> imageViewlist = new ArrayList<View>();
	BuyerAdapter buyerAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		if (parentview == null) {
			ishow = true;
			parentview = inflater.inflate(R.layout.buyersteetfragment_layout,null);
			initPullView();
			initView(parentview);
			requestFalshData();
		}
		ViewGroup vp = (ViewGroup) parentview.getParent();
		if (vp != null) {
			vp.removeView(parentview);
		}
		return parentview;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

	}

	void initPullView() {
		baijia_contact_listview = (PullToRefreshListView) parentview
				.findViewById(R.id.baijia_contact_listview);
		// 设置标签显示的内容
		baijia_contact_listview.setMode(Mode.PULL_FROM_START);
		baijia_contact_listview.onRefreshComplete();
		ToolsUtil.initPullResfresh(baijia_contact_listview, getActivity());

		baijia_contact_listview.setOnRefreshListener(new OnRefreshListener2() {

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

		LinearLayout head_ll = (LinearLayout) LinearLayout.inflate(
				getActivity(), R.layout.main_head_listview_layout, null);

		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
				AbsListView.LayoutParams.MATCH_PARENT,
				AbsListView.LayoutParams.WRAP_CONTENT);
		head_ll.setLayoutParams(layoutParams);
		ListView lv = baijia_contact_listview.getRefreshableView();
		lv.addHeaderView(head_ll);

		baijiasteetfragmnet_layout_head_viewpager_relativelayout = (RelativeLayout) head_ll
				.findViewById(R.id.baijiasteetfragmnet_layout_head_viewpager_relativelayout);
		buyersteet_newtextview = (TextView) head_ll
				.findViewById(R.id.buyersteet_newtextview);
		baijia_head_layout = (LinearLayout) head_ll
				.findViewById(R.id.baijia_head_layout);
		showloading_layout_view = (LinearLayout) v
				.findViewById(R.id.showloading_layout_view);
		showloading_layout_view.setVisibility(View.GONE);

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

		baijiasteetfragmnet_layout_head_viewpager
				.setOnTouchListener(new OnTouchListener() {

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
		
		buyerAdapter = new BuyerAdapter(Products, getActivity());
		baijia_contact_listview.setAdapter(buyerAdapter);

	}

	/******
	 * 上啦加载数据
	 * ***/
	void requestData() {
		sendRequestData(currpage, 1);
	}

	/******
	 * 下拉刷新数据
	 * ***/
	void requestFalshData() {
		stopTimerToViewPager();
		currid = -1;
		sendRequestData(1, 0);

	}

	/******
	 * 与网络通信请求数据
	 * 
	 * @param page
	 *            int 当前页
	 * @param type
	 *            int 0 刷新 1 加载
	 * ***/
	void sendRequestData(final int page, final int type) {
		ToolsUtil.showNoDataView(getActivity(),parentview,false);// 设置隐藏
		Log.i("TAG", "currpage=" + page + "   pagesize=" + pagesize);
		httpContril.getProduceHomeListData(page, pagesize,
				new HttpCallBackInterface() {

					@Override
					public void http_Success(Object obj) {
						currpage = page;
						ishow = false;
						buyersteet_newtextview.setText("最新上新");
						FontManager.changeFonts(getActivity(),buyersteet_newtextview);
						ToolsUtil.pullResfresh(baijia_contact_listview);
						if (obj != null && obj instanceof RequestProductListInfoBean) {
							RequestProductListInfoBean bean = (RequestProductListInfoBean) obj;
							HomeProductListInfoBean data = bean.getData();
							switch (type) {
							case 0:
								setViewPagerItem(data);
								falshData(data);
								break;
							case 1:
								addData(data);
								break;
							}

							setPageStatus(data, page);
						} else {
							http_Fails(500, getActivity().getResources()
									.getString(R.string.errorpagedata_str));
						}
					}

					@Override
					public void http_Fails(int error, String msg) {
						ToolsUtil.pullResfresh(baijia_contact_listview);
						MyApplication.getInstance().showMessage(getActivity(),
								msg);
					}
				}, getActivity(), ishow, true);
	}

	/***
	 * 加载数据
	 * **/
	void addData(HomeProductListInfoBean data) {
		if (data != null) {

			showloading_layout_view.setVisibility(View.GONE);
			ProductListInfoBean item = data.getItems();
			if (item.getProducts() != null) {
				if (item.getProducts().size() > 0) {
					currpage++;
					Products.addAll(item.getProducts());
				}

			}
		}
		if (buyerAdapter != null) {
			buyerAdapter.notifyDataSetChanged();
		}

		// ListViewUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);

	}

	void setPageStatus(HomeProductListInfoBean data, int page) {
		if (page == 1
				&& (data.getItems() == null
						|| data.getItems().getProducts() == null || data
						.getItems().getProducts().size() == 0)) {
			if(baijia_contact_listview!=null)
			{
				baijia_contact_listview.setMode(Mode.PULL_FROM_START);
			}
			
			ToolsUtil.showNoDataView(getActivity(),parentview,true);
		} else if (page != 1
				&& (data.getItems().getProducts() == null || data.getItems()
						.getProducts().size() == 0)) {
			if(baijia_contact_listview!=null)
			{
				baijia_contact_listview.setMode(Mode.BOTH);
			}
			MyApplication.getInstance().showMessage(
					getActivity(),
					getActivity().getResources().getString(
							R.string.lastpagedata_str));
		} else {
			if(baijia_contact_listview!=null)
			{
				baijia_contact_listview.setMode(Mode.BOTH);
			}
		}
	}

	/***
	 * 刷新viewpager数据
	 * ***/
	void falshData(HomeProductListInfoBean data) {
		Products.clear();
		currpage++;
		if (data != null) {

			ProductListInfoBean item = data.getItems();
			if (item.getProducts() != null) {
				if (item.getProducts().size() > 0) {
					
					Products.addAll(item.getProducts());
				}
			}
		}
		if (buyerAdapter != null) {
			buyerAdapter.notifyDataSetChanged();
		}
	}

	/******
	 * 设置顶部活动内容
	 * ****/
	void setViewPagerItem(HomeProductListInfoBean data) {
		stopTimerToViewPager();
		currid = -1;
		Banners.clear();
		imageViewlist.clear();
		if (data != null) {
			ProductListInfoBean item = data.getItems();

			// 获取活动列表
			if (item != null && item.getBanners() != null) {
				//设置viewpager 大小
				if(item.getBanners().size()>0)
				{
					if(getActivity()!=null)
					{
						DisplayMetrics dm = new DisplayMetrics();
						getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
						int width = dm.widthPixels;
						int height = width / 2;
						baijiasteetfragmnet_layout_head_viewpager_relativelayout.setLayoutParams(new LinearLayout.LayoutParams(width,height));
						((RelativeLayout.LayoutParams) baijia_head_layout.getLayoutParams()).bottomMargin = 40;//设置园点 的位置
					}
					
				}
				Banners = item.getBanners();
				for (int i = 0; i < Banners.size(); i++) {
					ImageView imageView = new ImageView(getActivity());
					imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
					imageView.setScaleType(ScaleType.CENTER_CROP);
					imageView.setTag(Banners.get(i));
					imageViewlist.add(imageView);
					initPic(ToolsUtil.nullToString(ToolsUtil.getImage(Banners.get(i).getPic(), 320, 0)), imageView);
					imageView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							BannersInfoBean bannersInfoBean = (BannersInfoBean) v
									.getTag();
							Intent intent = new Intent(getActivity(),
									WebActivity.class);
							intent.putExtra("url", bannersInfoBean.getLink());
							startActivity(intent);
						}
					});
				}
			}
			if (imageViewlist.size() > 0) {
				pagerAdapter = new ScrollViewPagerAdapter(getActivity(),imageViewlist);
				baijiasteetfragmnet_layout_head_viewpager.setAdapter(pagerAdapter);
				if (imageViewlist.size() > 0) {
					setcurrItem(0);
				}
				startTimeToViewPager();
			}
		}
		if (pagerAdapter != null) {
			pagerAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onResume() {

		super.onResume();
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
		if(size<=1)
		{
			return;
		}
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
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							setViewPagerDuration(1000);
							setcurrItem(currid);
						}
					});
				}
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
	void initPic(final String url, final ImageView iv) {
		MyApplication.getInstance().getBitmapUtil().display(iv, url);
	}
}
