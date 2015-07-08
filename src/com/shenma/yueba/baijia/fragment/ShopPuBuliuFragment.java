package com.shenma.yueba.baijia.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.ShopMainActivity.PubuliuFragmentListener;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListInfo;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListInfoBean;
import com.shenma.yueba.baijia.modle.RequestMyFavoriteProductListInfoBean;
import com.shenma.yueba.baijia.view.PubuliuManager;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;
import com.umeng.socialize.utils.Log;

/**
 * @author gyj
 * @version 创建时间：2015-6-10 下午3:13:09 程序的简单说明 商品|上新 瀑布流
 */

@SuppressLint("ValidFragment")
public class ShopPuBuliuFragment extends Fragment implements
		PubuliuFragmentListener {
	int currPage = Constants.CURRPAGE_VALUE;
	int pageSize = Constants.PAGESIZE_VALUE;
	PubuliuFragmentListener pubuliuFragmentListener;
	View parentView;
	PubuliuManager pm;
	HttpControl httpControl = new HttpControl();
	int Filter;//int 0:全部商品,1:上新商品 2:我收藏的商品（用于买家显示）
	int userID;
	List<MyFavoriteProductListInfo> item = new ArrayList<MyFavoriteProductListInfo>();
	/*****
	 * @param Filter int 0:全部商品,1:上新商品
	 * ***/
	public ShopPuBuliuFragment(int Filter,int userID) {
		this.Filter = Filter;
		this.userID=userID;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (parentView == null) {
			parentView = inflater.inflate(R.layout.pubuliu_layout, null);
			initView();
			onPuBuliuRefersh();
		}
		if (parentView.getParent() != null) {
			((ViewGroup) parentView.getParent()).removeView(parentView);
		}

		return parentView;
	}

	void initView() {
		pm = new PubuliuManager(getActivity(), parentView);
	}

	@Override
	public void onPuBuliuRefersh() {
		refreshLoading();
		switch(Filter)
		{
		case 0:
		case 1:
			sendHttp(1, 0);
			break;
		case 2:
			sendMyCollectHttp(1, 0);
			break;
		}
		
	}

	@Override
	public void onPuBuliuaddData() {
		refreshLoading();
		
		switch(Filter)
		{
		case 0:
		case 1:
			sendHttp(currPage, 1);
			break;
		case 2:
			sendMyCollectHttp(currPage, 1);
			break;
		}
		
	}

	/******
	 * 访问网络获取 全部商品或上新商品
	 * 
	 * @param page
	 *            访问的页数
	 * @param type
	 *            int 0:刷新 1：加载
	 * ****/
	void sendHttp(final int page,final int type)
	{
		ToolsUtil.showNoDataView(getActivity(), false);
		httpControl.GetBaijiaGetUserProductList(userID,page, pageSize, Filter, false, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				refreshComplete();
				if(obj==null || !(obj instanceof RequestMyFavoriteProductListInfoBean) || ((RequestMyFavoriteProductListInfoBean)obj).getData()==null)
				{
					if(page==1)
					{
						ToolsUtil.showNoDataView(getActivity(), true);
					}
					return;
				}else
				{
					RequestMyFavoriteProductListInfoBean bean=(RequestMyFavoriteProductListInfoBean)obj;
					MyFavoriteProductListInfoBean myFavoriteProductListInfoBean=bean.getData();
					currPage=myFavoriteProductListInfoBean.getPageindex();
				    if(page==1)
				    {
				    	if(myFavoriteProductListInfoBean.getItems()==null || myFavoriteProductListInfoBean.getItems().size()==0)
				    	{
				    		ToolsUtil.showNoDataView(getActivity(), true);
				    	}
				    }
					
				switch(type)
				{
				case 0:
					onRefresh(myFavoriteProductListInfoBean.getItems());
					break;
				case 1:
					onAddData(myFavoriteProductListInfoBean.getItems());
					break;
				}
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				refreshComplete();
				MyApplication.getInstance().showMessage(getActivity(), msg);
			}
		}, getActivity());
	}
	
	
	/***
	 * 刷新数据完成
	 * ***/
	void onRefresh(List<MyFavoriteProductListInfo> _list) {
		currPage++;
		item.clear();
		if(_list==null|| _list.size()<1)
		{
			return;
		}
		/*for (int i = 0; i < _list.size(); i++) {
			MyFavoriteProductListInfo info = _list.get(i);
			info.setPrice(1.1);
			info.setName("name" + i);
			MyFavoriteProductListPic pic = new MyFavoriteProductListPic();
			pic.setPic("http://e.hiphotos.baidu.com/image/pic/item/9358d109b3de9c82ad1fe5c96e81800a19d84379.jpg");
			Random random = new Random();
			pic.setRatio(random.nextInt(10));
			info.setPic(pic);
			item.add(info);
		}*/
		item.addAll(_list);
		pm.onResher(item);
	}

	/***
	 * 加载数据完成
	 * ***/
	void onAddData(List<MyFavoriteProductListInfo> _list) {
		currPage++;
		if(_list==null|| _list.size()<1)
		{
			return;
		}
		/*for (int i = 0; i < 10; i++) {
			MyFavoriteProductListInfo info = new MyFavoriteProductListInfo();
			info.setPrice(1.1);
			info.setName("name" + i);
			MyFavoriteProductListPic pic = new MyFavoriteProductListPic();
			pic.setPic("http://e.hiphotos.baidu.com/image/pic/item/9358d109b3de9c82ad1fe5c96e81800a19d84379.jpg");
			Random random = new Random();
			pic.setRatio(random.nextInt(10));
			info.setPic(pic);
			item.add(info);
		}*/
		item.addAll(_list);
		pm.onaddData(item);
	}

	/****
	 * 刷新完成
	 * **/
	void refreshComplete() {
		View v = getActivity().findViewById(
				R.id.shop_main_layout_title_pulltorefreshscrollview);
		if (v != null && v instanceof PullToRefreshScrollView) {
			((PullToRefreshScrollView) v).onRefreshComplete();
		}
	}

	/****
	 * 刷新完成
	 * **/
	void refreshLoading() {
		View v = getActivity().findViewById(R.id.shop_main_layout_title_pulltorefreshscrollview);
		/*if(v!=null && v instanceof PullToRefreshScrollView ) 
		{
		 ((PullToRefreshScrollView)v).setRefreshing(); 
		 }*/
		 
	}
	
	
	
	/***
     * 加载数据获取我收藏的商品
     * @param page int 访问页
     * @param type int 0: 刷新  1:加载更多
     * **/
    void sendMyCollectHttp(final int page,final int type)
	{
    	ToolsUtil.showNoDataView(getActivity(), false);
    	Log.i("TAG", "currpage="+page+"   pagesize="+pageSize);
    	httpControl.getMyFavoriteProductList(page, pageSize, false, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				refreshComplete();
				if(obj==null || !(obj instanceof RequestMyFavoriteProductListInfoBean) || ((RequestMyFavoriteProductListInfoBean)obj).getData()==null)
				{
					if(page==1)
					{
						ToolsUtil.showNoDataView(getActivity(), true);
					}
					return;
				}else
				{
					RequestMyFavoriteProductListInfoBean bean=(RequestMyFavoriteProductListInfoBean)obj;
					MyFavoriteProductListInfoBean myFavoriteProductListInfoBean=bean.getData();
					currPage=myFavoriteProductListInfoBean.getPageindex();
				    if(page==1)
				    {
				    	if(myFavoriteProductListInfoBean.getItems()==null || myFavoriteProductListInfoBean.getItems().size()==0)
				    	{
				    		ToolsUtil.showNoDataView(getActivity(), true);
				    	}
				    }
					
				switch(type)
				{
				case 0:
					onRefresh(myFavoriteProductListInfoBean.getItems());
					break;
				case 1:
					onAddData(myFavoriteProductListInfoBean.getItems());
					break;
				}
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				refreshComplete();
				MyApplication.getInstance().showMessage(getActivity(), msg);
			}
		}, getActivity());
	}
}
