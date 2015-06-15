package com.shenma.yueba.baijia.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaiJiaOrderDetailsActivity;
import com.shenma.yueba.baijia.adapter.BaiJiaOrderListAdapter;
import com.shenma.yueba.baijia.modle.BaiJiaOrderListInfo;
import com.shenma.yueba.baijia.modle.BaiJiaOrderListInfoBean;
import com.shenma.yueba.baijia.modle.RequestBaiJiaOrderListInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.umeng.socialize.utils.Log;

/**
 * @author gyj
 * @version 创建时间：2015-6-2 上午11:54:43 程序的简单说明 败家订单列表
 */

@SuppressLint("ValidFragment")
public class BaiJiaOrderListFragment extends Fragment {
	View parentView;
	PullToRefreshListView pull_refresh_list;
	BaiJiaOrderListAdapter baiJiaOrderListAdapter;
	List<BaiJiaOrderListInfo> object_list=new ArrayList<BaiJiaOrderListInfo>();
	int currpage=Constants.CURRPAGE_VALUE;
	int pagersize=Constants.PAGESIZE_VALUE;
	int type=-1;//订单类型 -1 全部
	HttpControl httpControl=new HttpControl();
	RequestBaiJiaOrderListInfoBean requestBaiJiaOrderListInfoBean;
	boolean ishow=true;
	public BaiJiaOrderListFragment(int type)
	{
		this.type=type;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(parentView==null)
		{
			parentView=inflater.inflate(R.layout.baijiaorderlistfragment_layout, null);
			initView();
			requestFalshData();
		}
		ViewGroup vp=(ViewGroup)parentView.getParent();
		if(vp!=null)
		{
			vp.removeView(parentView);
		}
		return parentView;
	}
	
	void initView()
	{
		pull_refresh_list=(PullToRefreshListView)parentView.findViewById(R.id.pull_refresh_list);
		 //设置标签显示的内容
		baiJiaOrderListAdapter=new BaiJiaOrderListAdapter(object_list,getActivity());
		pull_refresh_list.setAdapter(baiJiaOrderListAdapter);
		pull_refresh_list.setMode(Mode.PULL_FROM_START);
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
				if(object_list!=null)
				{
					BaiJiaOrderListInfo info=object_list.get(arg2-1);
					Intent intent=new Intent(getActivity(),BaiJiaOrderDetailsActivity.class);
					intent.putExtra("ORDER_ID", info.getOrderNo());
					startActivity(intent);
				}
				
			}
		});
	}
	
	/*****
	 *请求加载数据
	 * ***/
	void requestData()
	{
		sendRequestData(currpage,1);
	}
	
	/*****
	 *请求刷新数据
	 * ***/
	void requestFalshData()
	{
		sendRequestData(1,0);
	}
	
	/******
	 * 与网络通信请求数据
	 * @param page int 访问页
	 * @param type int 0 刷新 1 加载
	 * ***/
	void sendRequestData(int page ,final int type) {
		Log.i("TAG", "currpage="+page+"   pagesize="+pagersize);
		httpControl.getBaijiaOrderList(page, pagersize, type, ishow, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				pull_refresh_list.onRefreshComplete();
				if(obj!=null && obj instanceof RequestBaiJiaOrderListInfoBean)
				{
					requestBaiJiaOrderListInfoBean=(RequestBaiJiaOrderListInfoBean)obj;
					BaiJiaOrderListInfoBean bean=requestBaiJiaOrderListInfoBean.getData();
					
					if (bean != null) {
						if(currpage==1)
						{
							if(bean.getItems()==null || bean.getItems().size()==0)
						   {
								MyApplication.getInstance().showMessage(getActivity(), "还没有订单");
								return;
						   }
						}
						currpage=bean.getPageindex();
						int totalPage = bean.getTotalpaged();
						if (currpage >= totalPage) {
							pull_refresh_list.setMode(Mode.PULL_FROM_START);
						} else {
							pull_refresh_list.setMode(Mode.BOTH);
						}
						switch (type) {
						case 0:
							falshData(bean);
							break;
						case 1:
							addData(bean);
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
				MyApplication.getInstance().showMessage(getActivity(), msg);
				pull_refresh_list.onRefreshComplete();
			}
		}, getActivity());
		
	}
	
	/***
	 * 刷新viewpager数据
	 * ***/
	void falshData(BaiJiaOrderListInfoBean bean) {
		currpage++;
		if(bean==null)
		{
			return;
		}
		if(bean.getItems()!=null)
		{
			object_list.clear();
			object_list.addAll(bean.getItems());
		}
		pull_refresh_list.onRefreshComplete();
		if(baiJiaOrderListAdapter!=null)
		{
			baiJiaOrderListAdapter.notifyDataSetChanged();
		}
		ishow=false;
	}
	
	
	/***
	 * 加载数据
	 * **/
	void addData(BaiJiaOrderListInfoBean bean) {
		currpage++;
		if(bean==null)
		{
			return;
		}
		if(bean.getItems()!=null)
		{
			object_list.addAll(bean.getItems());
		}
		pull_refresh_list.onRefreshComplete();
		object_list.add(null);
		if(baiJiaOrderListAdapter!=null)
		{
			baiJiaOrderListAdapter.notifyDataSetChanged();
		}
	}
}
