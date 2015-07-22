package com.shenma.yueba.baijia.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaijiaBrandListActivity;
import com.shenma.yueba.baijia.adapter.BrandAdapter;
import com.shenma.yueba.baijia.modle.BrandInfo;
import com.shenma.yueba.baijia.modle.BrandInfoBean;
import com.shenma.yueba.baijia.modle.RequestBrandInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-19 下午2:03:37  
 * 程序的简单说明  
 */

public class BrandListView extends BaseView{
	Activity activity;
	LayoutInflater layoutInflater;
	
	private View view;
	private PullToRefreshListView pull_refresh_list;
	LinearLayout showloading_layout_view;
	private BrandAdapter brandAdapter;
	int currPage=Constants.CURRPAGE_VALUE;
	int pageSize=Constants.PAGESIZE_VALUE;
	boolean showDialog=true;
	HttpControl httpCntrol=new HttpControl();
	List<BrandInfo> items=new ArrayList<BrandInfo>();
	boolean isfirst=true;
	
	public View getView(Activity activity)
	{
		this.activity=activity;
		if(view==null)
		{
			layoutInflater=activity.getLayoutInflater();
			initView();
			initPullView();
			firstInitData(activity);
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
		pull_refresh_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				BrandInfo brandInfo=items.get(arg2-1);
				Intent intent=new Intent(activity,BaijiaBrandListActivity.class);
				intent.putExtra("BRANDID", brandInfo.getBrandId());
				activity.startActivity(intent);
			}
		});
		
		showloading_layout_view=(LinearLayout)view.findViewById(R.id.showloading_layout_view);
		//pull_refresh_list.setMode(Mode.PULL_FROM_START);
		 
		pull_refresh_list.setOnPullEventListener(new OnPullEventListener<ListView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
					State state, Mode direction) {
				
				// 设置标签显示的内容
				if (direction == Mode.PULL_FROM_START) {
					pull_refresh_list.getLoadingLayoutProxy().setPullLabel(activity.getResources().getString(R.string.Refreshonstr));
					pull_refresh_list.getLoadingLayoutProxy().setRefreshingLabel(activity.getResources().getString(R.string.Refreshloadingstr));
					pull_refresh_list.getLoadingLayoutProxy().setReleaseLabel(activity.getResources().getString(R.string.Loosentherefresh));
				} else if (direction == Mode.PULL_FROM_END) {
					pull_refresh_list.getLoadingLayoutProxy().setPullLabel(activity.getResources().getString(R.string.Thedropdownloadstr));
					pull_refresh_list.getLoadingLayoutProxy().setRefreshingLabel(activity.getResources().getString(R.string.RefreshLoadingstr));
					pull_refresh_list.getLoadingLayoutProxy().setReleaseLabel(activity.getResources().getString(R.string.Loosentheloadstr));
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
	
	
	void addData(BrandInfoBean bean)
	{
		currPage++;
		if(bean.getItems()!=null)
		{
			items.addAll(bean.getItems());
		}
		showloading_layout_view.setVisibility(View.GONE);
		brandAdapter.notifyDataSetChanged();
	}
	
	void falshData(BrandInfoBean bean)
	{
		isfirst=false;
		currPage++;
		items.clear();
		if(bean.getItems()!=null)
		{
			items.addAll(bean.getItems());
		}
		showloading_layout_view.setVisibility(View.GONE);
		brandAdapter.notifyDataSetChanged();
		
		
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
				pull_refresh_list.onRefreshComplete();
				currPage=page;
				showDialog=false;
				if(obj!=null && obj instanceof RequestBrandInfoBean)
				{
					RequestBrandInfoBean bean=(RequestBrandInfoBean)obj;
					if(bean.getData()==null || bean.getData().getItems()==null || bean.getData().getItems().size()==0)
					{
						if(page==1)
						{
							items.clear();
							pull_refresh_list.setMode(Mode.PULL_FROM_START);
							ToolsUtil.showNoDataView(activity,view ,true);
						}else
						{
							MyApplication.getInstance().showMessage(activity, activity.getResources().getString(R.string.lastpagedata_str));
						}
					}else 
					{
						if(page==1)
						{
							pull_refresh_list.setMode(Mode.BOTH);
						}
						int totalPage = bean.getData().getTotalpaged();
						if (currPage >= totalPage) {
							//MyApplication.getInstance().showMessage(activity, activity.getResources().getString(R.string.lastpagedata_str));
							pull_refresh_list.setMode(Mode.BOTH);
						} else {
							pull_refresh_list.setMode(Mode.BOTH);
						}
						switch (type) {
						case 0:
							falshData(bean.getData());
							break;
						case 1:
							addData(bean.getData());
							break;
						}
						
					}
					} else {
						if(page==1)
						{
							ToolsUtil.showNoDataView(activity,view, true);
						}
						MyApplication.getInstance().showMessage(activity, "没有任何数据");
					}
				
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				pull_refresh_list.onRefreshComplete();
				MyApplication.getInstance().showMessage(activity, msg);
			}
		},activity );
	}

	@Override
	public void firstInitData(Activity activity) {
		this.activity=activity;
		if(isfirst)
		{
			requestFalshData();
		}
	}
}
