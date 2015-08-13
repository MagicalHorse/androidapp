package com.shenma.yueba.baijia.fragment;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaiJiaOrderDetailsActivity;
import com.shenma.yueba.baijia.adapter.BaiJiaOrderListAdapter;
import com.shenma.yueba.baijia.modle.BaiJiaOrderListInfo;
import com.shenma.yueba.baijia.modle.RequestBaiJiaOrderListInfoBean;
import com.shenma.yueba.broadcaseReceiver.OrderBroadcaseReceiver;
import com.shenma.yueba.broadcaseReceiver.OrderBroadcaseReceiver.OrderBroadcaseListener;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;
import com.umeng.socialize.utils.Log;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

/**
 * @author gyj
 * @version 创建时间：2015-6-2 上午11:54:43 程序的简单说明 败家订单列表
 */


public class BaiJiaOrderListFragment extends Fragment implements OrderBroadcaseListener {
	View parentView;
	PullToRefreshListView pull_refresh_list;
	BaiJiaOrderListAdapter baiJiaOrderListAdapter;
	List<BaiJiaOrderListInfo> object_list = new ArrayList<BaiJiaOrderListInfo>();
	int currpage = Constants.CURRPAGE_VALUE;
	int pagersize = Constants.PAGESIZE_VALUE;
	int state = -1;// 订单类型 全部订单 0，待付款 1， 专柜自提 2， 售后 3

	LinearLayout showloading_layout_view;//加载进度条
	HttpControl httpControl = new HttpControl();
	RequestBaiJiaOrderListInfoBean requestBaiJiaOrderListInfoBean;
	boolean ishow = true;
	OrderBroadcaseReceiver orderBroadcaseReceiver;
	boolean isBroadcase=false;
	boolean isruning=false;//是否执行网络访问  true是  false 否
	
	public BaiJiaOrderListFragment(int state) {
		super();
		this.state = state;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		orderBroadcaseReceiver=new OrderBroadcaseReceiver(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (parentView == null) {
			parentView = inflater.inflate(R.layout.baijiaorderlistfragment_layout, null);
			initView();
		    requestFalshData();
		}
		ViewGroup vp = (ViewGroup) parentView.getParent();
		if (vp != null) {
			vp.removeView(parentView);
		}
		registerBroadcase();
		return parentView;
	}

	
	@Override
	public void onResume() {
		super.onResume();
		/*ishow=true;
		requestFalshData();*/
	}
	
	void initView() {
		showloading_layout_view=(LinearLayout)parentView.findViewById(R.id.showloading_layout_view);
		pull_refresh_list = (PullToRefreshListView) parentView.findViewById(R.id.pull_refresh_list);
		
		// 设置标签显示的内容
		baiJiaOrderListAdapter = new BaiJiaOrderListAdapter(object_list,getActivity());
		pull_refresh_list.setAdapter(baiJiaOrderListAdapter);
		pull_refresh_list.setMode(Mode.PULL_FROM_START);
		ToolsUtil.initPullResfresh(pull_refresh_list, getActivity());

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

		pull_refresh_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (object_list != null) {
					BaiJiaOrderListInfo info = object_list.get(arg2 - 1);
					Intent intent = new Intent(getActivity(),
							BaiJiaOrderDetailsActivity.class);
					intent.putExtra("ORDER_ID", info.getOrderNo());
					startActivity(intent);
				}

			}
		});
	}

	/*****
	 * 请求加载数据
	 * ***/
	public void requestData() {
		if(isruning)
		{
			return;
		}
		sendRequestData(currpage, 1);
	}

	/*****
	 * 请求刷新数据
	 * ***/
	public void requestFalshData() {
		if(isruning)
		{
			return;
		}
		sendRequestData(1, 0);
	}

	/******
	 * 与网络通信请求数据
	 * 
	 * @param page
	 *            int 访问页
	 * @param type
	 *            int 0 刷新 1 加载
	 * ***/
	void sendRequestData(final int page, final int type) {
		isruning=true;
		ToolsUtil.showNoDataView(getActivity(),parentView,false);
		Log.i("TAG", "currpage=" + page + "   pagesize=" + pagersize);
		httpControl.getBaijiaOrderList(page, pagersize, state, ishow,
				new HttpCallBackInterface() {

					@Override
					public void http_Success(Object obj) {
						isruning=false;
						if(showloading_layout_view!=null)
						{
							showloading_layout_view.setVisibility(View.GONE);
						}
						ToolsUtil.pullResfresh(pull_refresh_list);
						currpage=page;
						ishow = false;
						if (obj != null&& obj instanceof RequestBaiJiaOrderListInfoBean) {
							requestBaiJiaOrderListInfoBean = (RequestBaiJiaOrderListInfoBean) obj;
							switch (type) {
							case 0:
								falshData(requestBaiJiaOrderListInfoBean);
								break;
							case 1:
								addData(requestBaiJiaOrderListInfoBean);
								break;
							}
							setPageStatus(requestBaiJiaOrderListInfoBean, page);
							
							} else {
								http_Fails(500, getActivity().getResources().getString(R.string.errorpagedata_str));
							}

					}

					@Override
					public void http_Fails(int error, String msg) {
						isruning=false;
						MyApplication.getInstance().showMessage(getActivity(),msg);
						ToolsUtil.pullResfresh(pull_refresh_list);
						if(showloading_layout_view!=null)
						{
							showloading_layout_view.setVisibility(View.GONE);
						}
					}
				}, getActivity());

	}

	
	
	void setPageStatus(RequestBaiJiaOrderListInfoBean data, int page) {
		if (page == 1 && (data.getData()==null || data.getData().getItems() == null || data.getData().getItems().size()==0)) {
			if(pull_refresh_list!=null)
			{
				pull_refresh_list.setMode(Mode.PULL_FROM_START);
			}
			
			ToolsUtil.showNoDataView(getActivity(),parentView,true);
		} else if (page != 1 && (data.getData()==null || data.getData().getItems()== null || data.getData().getItems().size() == 0)) {
			if(pull_refresh_list!=null)
			{
				pull_refresh_list.setMode(Mode.BOTH);
			}
			
			MyApplication.getInstance().showMessage(getActivity(),getActivity().getResources().getString(R.string.lastpagedata_str));
		}else
		{
			if(pull_refresh_list!=null)
			{
				pull_refresh_list.setMode(Mode.BOTH);
			}
		}
	}
	
	
	/***
	 * 刷新viewpager数据
	 * ***/
	void falshData(RequestBaiJiaOrderListInfoBean bean) {
		ishow = false;
		currpage++;
		object_list.clear();
		if (bean.getData()!= null) {
			if (bean.getData().getItems() != null) {
				object_list.addAll(bean.getData().getItems());
			}
		}
		
		if (baiJiaOrderListAdapter != null) {
			baiJiaOrderListAdapter.notifyDataSetChanged();
		}
		
	}

	/***
	 * 加载数据
	 * **/
	void addData(RequestBaiJiaOrderListInfoBean bean) {
		ishow = false;
		currpage++;
		if (bean.getData()!= null) {
			if (bean.getData().getItems() != null) {
				object_list.addAll(bean.getData().getItems());
			}
		}
		
		if (baiJiaOrderListAdapter != null) {
			baiJiaOrderListAdapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unRegisterBroadcase();
	}
	
	
	/****
	 * 注册 订单广播接收器
	 * ***/
	void registerBroadcase()
	{
		if(getActivity()!=null)
		{
			if(!isBroadcase)
			{
				getActivity().registerReceiver(orderBroadcaseReceiver, new IntentFilter(OrderBroadcaseReceiver.IntentFilter));
			}
			
		}
		
	}
	
	
	
	/****
	 * 注销订单广播接收器
	 * ***/
	void unRegisterBroadcase()
	{
		if(getActivity()!=null)
		{
			if(isBroadcase)
			{
				getActivity().unregisterReceiver(orderBroadcaseReceiver);
			}
			
		}
		
	}
	
	@Override
	public void falshData(final BaiJiaOrderListInfo info,String str) {
		ishow = false;
		showloading_layout_view.setVisibility(View.VISIBLE);
		requestFalshData();
	}
	
}
