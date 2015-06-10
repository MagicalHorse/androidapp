package com.shenma.yueba.baijia.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import com.shenma.yueba.baijia.modle.MyFavoriteProductListPic;
import com.shenma.yueba.baijia.view.PubuliuManager;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-10 下午3:13:09  
 * 程序的简单说明  
 */

public class ShopPuBuliuFragment extends Fragment implements PubuliuFragmentListener{
int currPage=Constants.CURRPAGE_VALUE;
int pageSize=Constants.PAGESIZE_VALUE;
PubuliuFragmentListener pubuliuFragmentListener;
View parentView;	
PubuliuManager pm;   
HttpControl httpControl=new HttpControl();
    @Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(parentView==null)
		{
			parentView=inflater.inflate(R.layout.pubuliu_layout, null);
			initView();
			onPuBuliuRefersh();
		}
		if(parentView.getParent()!=null)
		{
			((ViewGroup)parentView.getParent()).removeView(parentView);
		}
		
		return parentView;
	}


	void initView()
	{
		pm=new PubuliuManager(getActivity(), parentView);
	}
	
	
	@Override
	public void onPuBuliuRefersh() {
		currPage=Constants.CURRPAGE_VALUE;
		pm.onResher(null);//清空数据
		refreshLoading();
		sendHttp(0);
	}


	@Override
	public void onPuBuliuaddData() {
		refreshLoading();
		sendHttp(1);
	}
	
	/******
	 * 访问网络 
	 * @param type int 0:刷新  1：加载
	 * ****/
	void sendHttp(final int type)
	{
		httpControl.getMyFavoriteProductList(currPage, pageSize, false, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				refreshComplete();
				switch(type)
				{
				case 0:
					onRefresh();
					break;
				case 1:
					onAddData();
					break;
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
	void onRefresh()
	{
		currPage++;
		List<MyFavoriteProductListInfo> item=new ArrayList<MyFavoriteProductListInfo>();
		for(int i=0;i<10;i++)
		{
			MyFavoriteProductListInfo info=new MyFavoriteProductListInfo();
			info.setPrice(1.1);
			info.setName("name"+i);
			MyFavoriteProductListPic pic=new MyFavoriteProductListPic();
			pic.setPic("http://e.hiphotos.baidu.com/image/pic/item/9358d109b3de9c82ad1fe5c96e81800a19d84379.jpg");
			Random random=new Random();
			pic.setRatio(random.nextInt(10));
			info.setPic(pic);
			item.add(info);
		}
		pm.onResher(item);
	}
	
	/***
	 * 加载数据完成
	 * ***/
	void onAddData()
	{
		currPage++;
		List<MyFavoriteProductListInfo> item=new ArrayList<MyFavoriteProductListInfo>();
		for(int i=0;i<10;i++)
		{
			MyFavoriteProductListInfo info=new MyFavoriteProductListInfo();
			info.setPrice(1.1);
			info.setName("name"+i);
			MyFavoriteProductListPic pic=new MyFavoriteProductListPic();
			pic.setPic("http://e.hiphotos.baidu.com/image/pic/item/9358d109b3de9c82ad1fe5c96e81800a19d84379.jpg");
			Random random=new Random();
			pic.setRatio(random.nextInt(10));
			info.setPic(pic);
			item.add(info);
		}
		pm.onaddData(item);
	}
	
	/****
	 * 刷新完成
	 * **/
	void refreshComplete()
	{
		View v=getActivity().findViewById(R.id.shop_main_layout_title_pulltorefreshscrollview);
		if(v!=null && v instanceof PullToRefreshScrollView )
		{
		   ((PullToRefreshScrollView)v).onRefreshComplete();
		}
	}
	
	/****
	 * 刷新完成
	 * **/
	void refreshLoading()
	{
		View v=getActivity().findViewById(R.id.shop_main_layout_title_pulltorefreshscrollview);
		if(v!=null && v instanceof PullToRefreshScrollView )
		{
		   ((PullToRefreshScrollView)v).setRefreshing();
		}
	}
}
