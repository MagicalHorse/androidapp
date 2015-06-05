package com.shenma.yueba.baijia.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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
import com.shenma.yueba.baijia.adapter.BrandAdapter;
import com.shenma.yueba.baijia.modle.BrandInfo;
import com.shenma.yueba.baijia.modle.BrandInfoBean;
import com.shenma.yueba.baijia.modle.RequestBrandInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-19 下午2:03:37  
 * 程序的简单说明  
 */

public class BrandListView {
	static BrandListView msgListView;
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
	public static BrandListView the()
	{
		if(msgListView==null)
		{
			msgListView=new BrandListView();
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
			requestFalshData();
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
				
				//SystemClock.sleep(100);
				Log.i("TAG", "onPullDownToRefresh");
				//pulltorefreshscrollview.setRefreshing();
				requestFalshData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				//SystemClock.sleep(100);
				//pulltorefreshscrollview.setRefreshing();
				Log.i("TAG", "onPullUpToRefresh");
				requestData();
			}
		});
		brandAdapter=new BrandAdapter(activity, items);
		pull_refresh_list.setAdapter(brandAdapter);
	}
	
	
	void requestData()
	{
		pull_refresh_list.setRefreshing();
		sendHttp(1);
		
	}
	
	void requestFalshData()
	{
		pull_refresh_list.setRefreshing();
		currPage=Constants.CURRPAGE_VALUE;
		sendHttp(0);
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
		//ListUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pull_refresh_list.onRefreshComplete();
	}
	
	void falshData(BrandInfoBean bean)
	{
		currPage++;
		items.clear();
		if(bean.getItems()!=null)
		{
			items.addAll(bean.getItems());
		}
		showloading_layout_view.setVisibility(View.GONE);
		brandAdapter.notifyDataSetChanged();
		
		//ListUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pull_refresh_list.onRefreshComplete();
		
	}
	
	
	void sendHttp(final int type)
	{
		httpCntrol.getBrandProductList(currPage, pageSize, showDialog, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				if(obj!=null && obj instanceof RequestBrandInfoBean)
				{
					RequestBrandInfoBean bean=(RequestBrandInfoBean)obj;
					if (bean != null) {
						if(currPage==1)
						{
							if(bean.getData()==null)
						   {
								MyApplication.getInstance().showMessage(activity, "还没有信息");
								return;
						   }
						}
						int totalPage = bean.getTotalpaged();
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
				
				MyApplication.getInstance().showMessage(activity, msg);
			}
		},activity );
	}
}
