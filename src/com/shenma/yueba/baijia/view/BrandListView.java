package com.shenma.yueba.baijia.view;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaijiaBrandListActivity;
import com.shenma.yueba.baijia.adapter.BrandAdapter;
import com.shenma.yueba.baijia.modle.BrandInfo;
import com.shenma.yueba.baijia.modle.RequestBrandInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-19 下午2:03:37  
 * 程序的简单说明  
 */

public class BrandListView extends BaseView{
	Activity activity;
	LayoutInflater layoutInflater;
	
	private View view;
	private PullToRefreshGridView pull_refresh_list;
	LinearLayout showloading_layout_view;
	private BrandAdapter brandAdapter;
	int currPage=Constants.CURRPAGE_VALUE;
	int pageSize=Constants.PAGESIZE_VALUE;
	boolean showDialog=true;
	HttpControl httpCntrol=new HttpControl();
	List<BrandInfo> items=new ArrayList<BrandInfo>();
	boolean isfirst=true;
	
	public BrandListView(Activity activity)
	{
		this.activity=activity;
	}
	
	public View getView()
	{
		
		if(view==null)
		{
			layoutInflater=activity.getLayoutInflater();
			initView();
			initPullView();
			firstInitData();
			//requestFalshData();
		}
		return view;
	}
	
	void initView()
	{
		view=layoutInflater.inflate(R.layout.refresh_gridview_without_title_layout, null);
	}
	
	void initPullView()
	{
		pull_refresh_list=(PullToRefreshGridView)view.findViewById(R.id.pull_refresh_gridview);
		pull_refresh_list.setMode(Mode.PULL_FROM_START);
		pull_refresh_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				BrandInfo brandInfo=items.get(arg2);
				Intent intent=new Intent(activity,BaijiaBrandListActivity.class);
				intent.putExtra("BrandList_type", BaijiaBrandListActivity.BrandList_type.Type_Brand);
				intent.putExtra("BrandId", brandInfo.getBrandId());
				intent.putExtra("BrandName", brandInfo.getBrandName());
				activity.startActivity(intent);
			}
		});
		
		showloading_layout_view=(LinearLayout)view.findViewById(R.id.showloading_layout_view);
		ToolsUtil.initPullResfresh(pull_refresh_list, activity);
		 
		pull_refresh_list.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				
				requestFalshData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				Log.i("TAG", "onPullUpToRefresh");
				requestData();
			}
		});
		brandAdapter=new BrandAdapter(activity, items);
		pull_refresh_list.setAdapter(brandAdapter);
	}
	
	
	void requestData()
	{
		sendHttp(currPage,1);
		
	}
	
	void requestFalshData()
	{
		sendHttp(1,0);
	}
	
	
	void addData(RequestBrandInfoBean bean)
	{
		currPage++;
		if(bean.getData()!=null)
		{
			if(bean.getData().getItems()!=null)
			{
				items.addAll(bean.getData().getItems());
			}
		}
		showloading_layout_view.setVisibility(View.GONE);
		if(brandAdapter!=null)
		{
			brandAdapter.notifyDataSetChanged();
		}
		
	}
	
	void falshData(RequestBrandInfoBean bean)
	{
		isfirst=false;
		currPage++;
		items.clear();
		if(bean.getData()!=null)
		{
			if(bean.getData().getItems()!=null)
			{
				items.addAll(bean.getData().getItems());
			}
		}
		showloading_layout_view.setVisibility(View.GONE);
		if(brandAdapter!=null)
		{
			brandAdapter.notifyDataSetChanged();
		}
		
		
	}
	
	/******
	 * @param page int 访问页面
	 * @param type int 0：刷新 1 加载
	 * ***/
	void sendHttp(final int page,final int type)
	{
		ToolsUtil.showNoDataView(activity, view,false);
		httpCntrol.getBrandProductList(page, pageSize, showDialog, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				ToolsUtil.pullResfresh(pull_refresh_list);
				currPage=page;
				showDialog=false;
				if(obj!=null && obj instanceof RequestBrandInfoBean)
				{
					RequestBrandInfoBean bean=(RequestBrandInfoBean)obj;
					switch (type) {
					case 0:
						falshData(bean);
						break;
					case 1:
						addData(bean);
						break;
					}

					setPageStatus(bean, page);
				} else {
					http_Fails(500, activity.getResources()
							.getString(R.string.errorpagedata_str));
				}
				
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				ToolsUtil.pullResfresh(pull_refresh_list);
				MyApplication.getInstance().showMessage(activity, msg);
			}
		},activity );
	}

	
	void setPageStatus(RequestBrandInfoBean data, int page) {
		if (page == 1 && (data.getData() == null
						|| data.getData().getItems() == null || data
						.getData().getItems().size() == 0)) {
			pull_refresh_list.setMode(Mode.PULL_FROM_START);
			ToolsUtil.showNoDataView(activity, view,true);
		} else if (page != 1
				&& (data.getData() == null || data.getData().getItems()==null || data.getData().getItems().size() == 0)) {
			pull_refresh_list.setMode(Mode.BOTH);
			MyApplication.getInstance().showMessage(
					activity,
					activity.getResources().getString(
							R.string.lastpagedata_str));
		} else {
			pull_refresh_list.setMode(Mode.BOTH);
		}
	}
	
	
	@Override
	public void firstInitData() {
		if(isfirst)
		{
			requestFalshData();
		}
	}
}
