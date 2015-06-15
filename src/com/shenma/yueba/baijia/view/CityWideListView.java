package com.shenma.yueba.baijia.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.shenma.yueba.baijia.adapter.SameCityAdapter;
import com.shenma.yueba.baijia.modle.BrandCityWideInfo;
import com.shenma.yueba.baijia.modle.BrandCityWideInfoBean;
import com.shenma.yueba.baijia.modle.RequestBrandCityWideInfoBean;
import com.shenma.yueba.baijia.modle.RequestBrandInfoBean;
import com.shenma.yueba.baijia.modle.SameCityBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-19 下午2:03:37  
 * 程序的简单说明  
 */

public class CityWideListView extends BaseView{
	static CityWideListView msgListView;
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
	boolean isfirst=true;
	int CityId=0;//当前城市id
	public static CityWideListView the()
	{
		if(msgListView==null)
		{
			msgListView=new CityWideListView();
		}
		return msgListView;
	}
	
	public View getView(Activity activity)
	{
		this.activity=activity;
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
		showloading_layout_view=(LinearLayout)view.findViewById(R.id.showloading_layout_view);
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
				
				//SystemClock.sleep(myCircleAdapter);
				Log.i("TAG", "onPullDownToRefresh");
				//pulltorefreshscrollview.setRefreshing();
				requestFalshData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				//SystemClock.sleep(myCircleAdapter);
				//pulltorefreshscrollview.setRefreshing();
				Log.i("TAG", "onPullUpToRefresh");
				requestData();
			}
		});
		msgAdapter=new SameCityAdapter(activity, items,pull_refresh_list);
		pull_refresh_list.setAdapter(msgAdapter);
	}
	
	void requestData()
	{
		sendHttp(currPage,1);
	}
	
	void requestFalshData()
	{
		sendHttp(1,0);
	}
	
	
	void addData(BrandCityWideInfoBean brandCityWideInfoBean)
	{
		currPage++;
		if(brandCityWideInfoBean.getItems()!=null)
		{
			items.addAll(brandCityWideInfoBean.getItems());
		}
		showloading_layout_view.setVisibility(View.GONE);
		msgAdapter.notifyDataSetChanged();
		//ListUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pull_refresh_list.onRefreshComplete();
	}
	
	void falshData(BrandCityWideInfoBean brandCityWideInfoBean)
	{
		isfirst=false;
		currPage++;
		items.clear();
		if(brandCityWideInfoBean.getItems()!=null)
		{
			items.addAll(brandCityWideInfoBean.getItems());
		}
		showloading_layout_view.setVisibility(View.GONE);
		msgAdapter.notifyDataSetChanged();
		
		//ListUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pull_refresh_list.onRefreshComplete();
		
	}
	
	/***
	 * @param page int 访问页
	 * @param type 0：刷新 1：加载
	 * **/
	void sendHttp(int page,final int type)
	{
		httpCntrol.getBrandCity_Wide(page, pageSize, CityId, showDialog,new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				pull_refresh_list.onRefreshComplete();
				showDialog=false;
				if(obj!=null && obj instanceof RequestBrandCityWideInfoBean)
				{
					RequestBrandCityWideInfoBean bean=(RequestBrandCityWideInfoBean)obj;
					if (bean != null && bean.getData()!=null) {
						BrandCityWideInfoBean brandCityWideInfoBean=bean.getData();
						currPage=brandCityWideInfoBean.getPageindex();
						if(currPage==1)
						{
							if(bean.getData()==null)
						   {
								MyApplication.getInstance().showMessage(activity, "还没有信息");
								return;
						   }
						}
						
						int totalPage = brandCityWideInfoBean.getTotalpaged();
						if (currPage >= totalPage) {
							pull_refresh_list.setMode(Mode.PULL_FROM_START);
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
					} else {
						MyApplication.getInstance().showMessage(
								activity, "没有任何数据");
					}
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
	public void firstInitData() {
		if(isfirst)
		{
			requestFalshData();
		}
	}
}
