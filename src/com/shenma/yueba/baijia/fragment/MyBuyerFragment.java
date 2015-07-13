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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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
import com.shenma.yueba.util.ToolsUtil;

public class MyBuyerFragment extends Fragment {
	List<FragmentBean> fragment_list = new ArrayList<FragmentBean>();
	List<View> footer_list = new ArrayList<View>();
	FragmentManager fragmentManager;
	PullToRefreshListView baijia_contact_listview;
	View parentview;
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
		baijia_contact_listview = (PullToRefreshListView)parentview.findViewById(R.id.baijia_contact_listview);
		baijia_contact_listview.setMode(Mode.BOTH);
		baijia_contact_listview.setOnPullEventListener(new OnPullEventListener<ListView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
					State state, Mode direction) {
				
				// 设置标签显示的内容
				if (direction == Mode.PULL_FROM_START) {
					baijia_contact_listview.getLoadingLayoutProxy().setPullLabel(getActivity().getResources().getString(R.string.Refreshonstr));
					baijia_contact_listview.getLoadingLayoutProxy().setRefreshingLabel(getActivity().getResources().getString(R.string.Refreshloadingstr));
					baijia_contact_listview.getLoadingLayoutProxy().setReleaseLabel(getActivity().getResources().getString(R.string.Loosentherefresh));
				} else if (direction == Mode.PULL_FROM_END) {
					baijia_contact_listview.getLoadingLayoutProxy().setPullLabel(getActivity().getResources().getString(R.string.Thedropdownloadstr));
					baijia_contact_listview.getLoadingLayoutProxy().setRefreshingLabel(getActivity().getResources().getString(R.string.RefreshLoadingstr));
					baijia_contact_listview.getLoadingLayoutProxy().setReleaseLabel(getActivity().getResources().getString(R.string.Loosentheloadstr));
				}
			}
		});

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
		showloading_layout_view = (LinearLayout) v
				.findViewById(R.id.showloading_layout_view);
		showloading_layout_view.setVisibility(View.GONE);
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
		sendRequestData(currpage,1);
	}

	/******
	 * 下拉刷新数据
	 * ***/
	void requestFalshData() {
		isFirst=false;
		sendRequestData(1,0);

	}

	/******
	 * 与网络通信请求数据
	 * @param page int 访问页数
	 * @param type int 0 刷新 1 加载
	 * ***/
	void sendRequestData(final int page,final int type) {
		Log.i("TAG", "currpage="+page+"   pagesize="+pagesize);
		ToolsUtil.showNoDataView(getActivity(), false);
		httpContril.getMyBuyerListData(page, pagesize,new HttpCallBackInterface() {

					@Override
					public void http_Success(Object obj) {
						ishow=false;
						baijia_contact_listview.onRefreshComplete();
						currpage=page;
						if (obj != null && obj instanceof MyRequestProductListInfoBean) {
							MyRequestProductListInfoBean bean = (MyRequestProductListInfoBean) obj;
							MyHomeProductListInfoBean data = bean.getData();
							if(data==null || data.getItems()==null)
							{
								if(page==1)
								{
									baijia_contact_listview.setMode(Mode.PULL_FROM_START);
									ToolsUtil.showNoDataView(getActivity(), true);
								}
							}else
							{
								
								int totalPage = data.getTotalpaged();
								if(page==1)
								{
									baijia_contact_listview.setMode(Mode.PULL_FROM_START);
								}
								if(currpage>=totalPage)
								{
									//MyApplication.getInstance().showMessage(getActivity(), getActivity().getResources().getString(R.string.lastpagedata_str));
									baijia_contact_listview.setMode(Mode.PULL_FROM_START);
								}else
								{
									baijia_contact_listview.setMode(Mode.BOTH);
								}
								
								switch (type) {
								case 0:
									falshData(data);
									break;
								case 1:
									addData(data);
									break;
								}
							}
						}
							else {
								MyApplication.getInstance().showMessage(getActivity(), "没有任何数据");
								if(page==1)
								{
									ToolsUtil.showNoDataView(getActivity(), true);
								}
							}
                           
					}

					@Override
					public void http_Fails(int error, String msg) {
						baijia_contact_listview.onRefreshComplete();
						MyApplication.getInstance().showMessage(getActivity(),msg);
					}
				}, getActivity(),ishow,false);
	}

	/***
	 * 加载数据
	 * **/
	void addData(MyHomeProductListInfoBean data) {
		//showloading_layout_view.setVisibility(View.GONE);
		MyProductListInfoBean item = data.getItems();
		if(item.getProducts()!=null)
		{
			if(item.getProducts().size()>0)
			{
				currpage++;
				Products.addAll(item.getProducts());
			}
		}
		buyerAdapter.notifyDataSetChanged();
	}

	/***
	 * 刷新viewpager数据
	 * ***/
	void falshData(MyHomeProductListInfoBean data) {
		//showloading_layout_view.setVisibility(View.GONE);
		MyProductListInfoBean item = data.getItems();
		Banners.clear();
		Products.clear();
		
		if(item.getProducts()!=null )
		{
			if(item.getProducts().size()>0)
			{
				currpage++;
				Products.addAll(item.getProducts());
			}
		}
		buyerAdapter=new BuyerAdapter(Products, getActivity());
		baijia_contact_listview.setAdapter(buyerAdapter);
		buyerAdapter.notifyDataSetChanged();
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
