package com.shenma.yueba.baijia.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.BuyerAdapter;
import com.shenma.yueba.baijia.modle.FragmentBean;
import com.shenma.yueba.baijia.modle.MyHomeProductListInfoBean;
import com.shenma.yueba.baijia.modle.MyProductListInfoBean;
import com.shenma.yueba.baijia.modle.MyRequestProductListInfoBean;
import com.shenma.yueba.baijia.modle.ProductsInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
	// 是否显示进度条
	boolean ishow = false;
	// 商品信息列表
	List<ProductsInfoBean> Products = new ArrayList<ProductsInfoBean>();
	BitmapUtils bitmapUtils;
	BuyerAdapter buyerAdapter;
	boolean isFirst = true;// 是否第一次加载数据

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		if (parentview == null) {
			ishow = true;
			parentview = inflater.inflate(R.layout.mybuyer_home_layout, null);
			bitmapUtils = new BitmapUtils(getActivity());
			initPullView();
			initView(parentview);

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
		baijia_contact_listview = (PullToRefreshListView) parentview.findViewById(R.id.baijia_contact_listview);
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
		showloading_layout_view = (LinearLayout) v.findViewById(R.id.showloading_layout_view);
		showloading_layout_view.setVisibility(View.GONE);
		buyerAdapter = new BuyerAdapter(Products, getActivity());
		baijia_contact_listview.setAdapter(buyerAdapter);
	}

	/******
	 * 上啦加载数据
	 ***/
	void requestData() {
		sendRequestData(currpage, 1);
	}

	/******
	 * 下拉刷新数据
	 ***/
	void requestFalshData() {
		isFirst = false;
		sendRequestData(1, 0);

	}

	/******
	 * 与网络通信请求数据
	 * 
	 * @param page
	 *            int 访问页数
	 * @param type
	 *            int 0 刷新 1 加载
	 ***/
	void sendRequestData(final int page, final int type) {
		Log.i("TAG", "currpage=" + page + "   pagesize=" + pagesize);
		ToolsUtil.showNoDataView(getActivity(), parentview, false);
		httpContril.getMyBuyerListData(page, pagesize, new HttpCallBackInterface() {

			@Override
			public void http_Success(Object obj) {
				ishow = false;
				ToolsUtil.pullResfresh(baijia_contact_listview);
				currpage = page;
				if (obj != null && obj instanceof MyRequestProductListInfoBean) {
					MyRequestProductListInfoBean bean = (MyRequestProductListInfoBean) obj;
					MyHomeProductListInfoBean data = bean.getData();
					switch (type) {
					case 0:
						falshData(data);
						break;
					case 1:
						addData(data);
						break;
					}
					setPageStatus(data, page);
				} else {
					http_Fails(500, getActivity().getResources().getString(R.string.errorpagedata_str));
				}

			}

			@Override
			public void http_Fails(int error, String msg) {
				ToolsUtil.pullResfresh(baijia_contact_listview);
				MyApplication.getInstance().showMessage(getActivity(), msg);
			}
		}, getActivity(), ishow, false);
	}

	void setPageStatus(MyHomeProductListInfoBean data, int page) {
		if (page == 1 && (data.getItems() == null || data.getItems().getProducts() == null
				|| data.getItems().getProducts().size() == 0)) {
			if(baijia_contact_listview!=null)
			{
				baijia_contact_listview.setMode(Mode.PULL_FROM_START);
			}
			ToolsUtil.showNoDataView(getActivity(), parentview, true);
		} else if (page != 1 && (data.getItems().getProducts() == null || data.getItems().getProducts().size() == 0)) {
			if(baijia_contact_listview!=null)
			{
				baijia_contact_listview.setMode(Mode.BOTH);
			}
			
			MyApplication.getInstance().showMessage(getActivity(),
					getActivity().getResources().getString(R.string.lastpagedata_str));
		} else {
			if(baijia_contact_listview!=null)
			{
				baijia_contact_listview.setMode(Mode.BOTH);
			}
			
		}
	}

	/***
	 * 加载数据
	 **/
	void addData(MyHomeProductListInfoBean data) {
		currpage++;
		if (data != null) {
			MyProductListInfoBean item = data.getItems();
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

	/***
	 * 刷新viewpager数据
	 ***/
	void falshData(MyHomeProductListInfoBean data) {
		// showloading_layout_view.setVisibility(View.GONE);
		Products.clear();
		currpage++;
		if (data != null) {
			MyProductListInfoBean item = data.getItems();

			if (item.getProducts() != null) {
				if (item.getProducts().size() > 0) {

					Products.addAll(item.getProducts());
				}
			}
			if (getActivity() != null) {
				buyerAdapter = new BuyerAdapter(Products, getActivity());
				if (baijia_contact_listview != null) {
					baijia_contact_listview.setAdapter(buyerAdapter);
				}
			}
		}
		if (buyerAdapter != null) {
			buyerAdapter.notifyDataSetChanged();
		}

	}

	/*****
	 * 首次加载
	 ***/
	public void firstInitData() {
		if (isFirst) {
			requestFalshData();
		}

	}
}
