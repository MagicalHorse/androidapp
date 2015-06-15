package com.shenma.yueba.baijia.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

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
import com.shenma.yueba.baijia.adapter.BuyerAdapter;
import com.shenma.yueba.baijia.modle.BannersInfoBean;
import com.shenma.yueba.baijia.modle.FragmentBean;
import com.shenma.yueba.baijia.modle.MyHomeProductListInfoBean;
import com.shenma.yueba.baijia.modle.MyProductListInfoBean;
import com.shenma.yueba.baijia.modle.MyRequestProductListInfoBean;
import com.shenma.yueba.baijia.modle.ProductsInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ListViewUtils;

public class MyBuyerFragment extends Fragment {
	List<FragmentBean> fragment_list = new ArrayList<FragmentBean>();
	List<View> footer_list = new ArrayList<View>();
	FragmentManager fragmentManager;
	ListView baijia_contact_listview;
	View parentview;
	PullToRefreshScrollView pulltorefreshscrollview;
	LayoutInflater inflater;
	LinearLayout showloading_layout_view;
	Timer timer;
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
	BuyerAdapter buyerAdapter;
	boolean isFirst=true;//是否第一次加载数据
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		if (parentview == null) {
			ishow=true;
			parentview = inflater.inflate(R.layout.mybuyer_home_layout, null);
			bitmapUtils = new BitmapUtils(getActivity());
			initPullView();
			initView(parentview);
			
		}
		ViewGroup vp = (ViewGroup)parentview.getParent();
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
		pulltorefreshscrollview = (PullToRefreshScrollView)parentview.findViewById(R.id.pulltorefreshscrollview);
		// 设置标签显示的内容
		pulltorefreshscrollview.getLoadingLayoutProxy().setPullLabel("下拉刷新");
		pulltorefreshscrollview.getLoadingLayoutProxy().setRefreshingLabel(
				"刷新中。。。");
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
		showloading_layout_view = (LinearLayout) v
				.findViewById(R.id.showloading_layout_view);
		showloading_layout_view.setVisibility(View.GONE);
		baijia_contact_listview = (ListView) v.findViewById(R.id.baijia_contact_listview);
		buyerAdapter=new BuyerAdapter(Products, getActivity());
		baijia_contact_listview.setAdapter(buyerAdapter);
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

	/******
	 * 上啦加载数据
	 * ***/
	void requestData() {
		//pulltorefreshscrollview.setRefreshing();
		sendRequestData(currpage,1);
	}

	/******
	 * 下拉刷新数据
	 * ***/
	void requestFalshData() {
		isFirst=false;
		//pulltorefreshscrollview.setRefreshing();
		sendRequestData(1,0);

	}

	/******
	 * 与网络通信请求数据
	 * @param page int 访问页数
	 * @param type int 0 刷新 1 加载
	 * ***/
	void sendRequestData(int page,final int type) {
		Log.i("TAG", "currpage="+page+"   pagesize="+pagesize);
		httpContril.getMyBuyerListData(page, pagesize,new HttpCallBackInterface() {

					@Override
					public void http_Success(Object obj) {
						pulltorefreshscrollview.onRefreshComplete();
						if (obj != null && obj instanceof MyRequestProductListInfoBean) {
							MyRequestProductListInfoBean bean = (MyRequestProductListInfoBean) obj;
							MyHomeProductListInfoBean data = bean.getData();
							if (data != null) {
								int totalPage = data.getTotalpaged();
								currpage=data.getPageindex();
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
				}, getActivity(),ishow,false);
	}

	/***
	 * 加载数据
	 * **/
	void addData(MyHomeProductListInfoBean data) {
		currpage++;
		ishow=false;
		showloading_layout_view.setVisibility(View.GONE);
		MyProductListInfoBean item = data.getItems();
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
	void falshData(MyHomeProductListInfoBean data) {
		currpage++;
		ishow=false;
		showloading_layout_view.setVisibility(View.GONE);
		MyProductListInfoBean item = data.getItems();
		Banners.clear();
		Products.clear();
		
		if(item.getProducts()!=null)
		{
			Products.addAll(item.getProducts());
		}
		ListViewUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pulltorefreshscrollview.onRefreshComplete();

	}
	
	/*****
	 * 首次加载
	 * ***/
	public void firstInitData()
	{
		if(isFirst)
		{
			requestFalshData();
		}
		
	}
}
