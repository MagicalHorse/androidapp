package com.shenma.yueba.baijia.view;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.SameCityAdapter;
import com.shenma.yueba.baijia.modle.BrandCityWideInfo;
import com.shenma.yueba.baijia.modle.RequestBrandCityWideInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.util.ToolsUtil;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-19 下午2:03:37  
 * 程序的简单说明  
 */

public class CityWideListView extends BaseView{
	Activity activity;
	LayoutInflater layoutInflater;
	
	private SameCityAdapter msgAdapter;
	private View view;
	private PullToRefreshListView pull_refresh_list;
	LinearLayout showloading_layout_view;
	int currPage=Constants.CURRPAGE_VALUE;
	int pageSize=Constants.PAGESIZE_VALUE;
	boolean showDialog=true;
	HttpControl httpCntrol=new HttpControl();
	List<BrandCityWideInfo> items=new ArrayList<BrandCityWideInfo>();
	int CityId=0;//当前城市id
	boolean isruning=false;
	public CityWideListView(Activity activity)
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
			//requestFalshData();
		}
		return view;
	}
	
	void initView()
	{
		view=layoutInflater.inflate(R.layout.refresh_listview_without_title_layout, null);
	}
	
	void initPullView()
	{
		pull_refresh_list=(PullToRefreshListView)view.findViewById(R.id.pull_refresh_list);
		pull_refresh_list.setMode(Mode.PULL_FROM_START);
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
		msgAdapter=new SameCityAdapter(activity, items,pull_refresh_list);
		pull_refresh_list.setAdapter(msgAdapter);
	}
	
	void requestData()
	{
		if(isruning)
		{
			return;
		}
		pull_refresh_list.setRefreshing();
		isruning=true;
		sendHttp(currPage,1);
	}
	
	void requestFalshData()
	{
		if(isruning)
		{
			return;
		}
		pull_refresh_list.setRefreshing();
		isruning=true;
		sendHttp(1,0);
	}
	
	
	void addData(RequestBrandCityWideInfoBean bean)
	{
		showDialog=false;
		currPage++;
		if(bean.getData()!=null)
		{
			if(bean.getData().getItems()!=null)
			{
				items.addAll(bean.getData().getItems());
			}
		}
		
		showloading_layout_view.setVisibility(View.GONE);
		if(msgAdapter!=null)
		{
			msgAdapter.notifyDataSetChanged();
		}
		
	}
	
	void falshData(RequestBrandCityWideInfoBean bean)
	{
		showDialog=false;
		
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
		if(msgAdapter!=null)
		{
			msgAdapter.notifyDataSetChanged();
		}
		
	}
	
	/***
	 * @param page int 访问页
	 * @param type 0：刷新 1：加载
	 * **/
	void sendHttp(final int page,final int type)
	{
		ToolsUtil.showNoDataView(activity, view,false);
		String loginid=SharedUtil.getStringPerfernece(MyApplication.getInstance().getApplicationContext(), SharedUtil.user_id);
		int userid=0;
		if(loginid==null || loginid.equals(""))
		{
			userid=0;
		}else
		{
			userid=Integer.parseInt(loginid);
		}
		httpCntrol.getBrandCity_Wide(page, pageSize, CityId,userid,showDialog,new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				currPage=page;
				isruning=false;
				ToolsUtil.pullResfresh(pull_refresh_list);
				
				if(obj!=null && obj instanceof RequestBrandCityWideInfoBean)
				{
					RequestBrandCityWideInfoBean bean=(RequestBrandCityWideInfoBean)obj;
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
				isruning=false;
				ToolsUtil.pullResfresh(pull_refresh_list);
				MyApplication.getInstance().showMessage(activity, msg);
			}
		},activity );
	}

	
	void setPageStatus(RequestBrandCityWideInfoBean data, int page) {
		if (page == 1 && (data.getData() == null
						|| data.getData().getItems() == null || data
						.getData().getItems().size() == 0)) {
			if(pull_refresh_list!=null)
			{
				pull_refresh_list.setMode(Mode.PULL_FROM_START);
			}
			
			ToolsUtil.showNoDataView(activity, view,true);
		} else if (page != 1
				&& (data.getData() == null || data.getData().getItems()==null || data.getData().getItems().size() == 0)) {
			if(pull_refresh_list!=null)
			{
				pull_refresh_list.setMode(Mode.BOTH);
			}
			
			MyApplication.getInstance().showMessage(
					activity,
					activity.getResources().getString(
							R.string.lastpagedata_str));
		} else {
			if(pull_refresh_list!=null)
			{
				pull_refresh_list.setMode(Mode.BOTH);
			}
		}
	}
	
	
	@Override
	public void firstInitData() {
		if(items.size()<=0 && showDialog)
		{
			requestFalshData();
		}
	}
}
