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
import com.shenma.yueba.ChatActivity;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.CircleInfoActivity;
import com.shenma.yueba.baijia.adapter.BaiJiaMyCircleAdapter;
import com.shenma.yueba.baijia.modle.MyCircleInfo;
import com.shenma.yueba.baijia.modle.MyCircleInfoBean;
import com.shenma.yueba.baijia.modle.RequestMyCircleInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;

/**
 * 我的买手
 * 
 * @author a
 * 
 */

public class MyCircleView extends BaseView{
	private View view;
	private PullToRefreshListView pull_refresh_list;
	LinearLayout showloading_layout_view;
	static MyCircleView myCircleView;
	Activity activity;
	LayoutInflater inflater;
	BaiJiaMyCircleAdapter myCircleAdapter;
	int currPage=Constants.CURRPAGE_VALUE;
	int pageSize=Constants.PAGESIZE_VALUE;
	boolean showDialog=true;
	HttpControl httpCntrol=new HttpControl();
	List<MyCircleInfo> items=new ArrayList<MyCircleInfo>();
	boolean isFirst=true;
	
	public static MyCircleView the(Activity activity)
	{
		if(myCircleView==null)
		{
			myCircleView=new MyCircleView();
		}
		myCircleView.activity=activity;
		return myCircleView;
	}
	
	
	public View getView(Activity activity)
	{
		this.activity=activity;
		if(view == null)
		{
			initView();
			initPullView();
			//requestFalshData();
		}
		return view ;
	}
	
	
	
	void initView()
	{
		inflater=activity.getLayoutInflater();
		view = inflater.inflate(R.layout.refresh_listview_without_title_layout, null);
	}
	
	void initPullView()
	{
		pull_refresh_list=(PullToRefreshListView)view.findViewById(R.id.pull_refresh_list);
		showloading_layout_view=(LinearLayout)view.findViewById(R.id.showloading_layout_view);
		pull_refresh_list.setMode(Mode.BOTH);
		 
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
				
				requestData();
			}
		});
		myCircleAdapter=new BaiJiaMyCircleAdapter(activity, items);
		pull_refresh_list.setAdapter(myCircleAdapter);
		pull_refresh_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				MyCircleInfo myCircleInfo=items.get(arg2-1);
				Intent intent=new Intent(activity,ChatActivity.class);
				intent.putExtra("Chat_Type", ChatActivity.chat_type_group);//类型 圈子 还是私聊
				intent.putExtra("Chat_NAME",myCircleInfo.getName());//圈子名字
				intent.putExtra("circleId",myCircleInfo.getId());//圈子id
				activity.startActivity(intent);
			}
		});
	}
	
	
	void requestData()
	{
		sendHttp(currPage,1);
	}
	
	void requestFalshData()
	{
		Log.i("TAG", "----->>请求数据");
		sendHttp(1,0);
	}
	
	
	void addData(MyCircleInfoBean bean)
	{
		isFirst=false;
		currPage++;
		if(bean.getItems()!=null)
		{
			items.addAll(bean.getItems());
		}
		showloading_layout_view.setVisibility(View.GONE);
		myCircleAdapter.notifyDataSetChanged();
	}
	
	void falshData(MyCircleInfoBean bean)
	{
		isFirst=false;
		currPage++;
		items.clear();
		if(bean.getItems()!=null)
		{
			items.addAll(bean.getItems());
		}
		showloading_layout_view.setVisibility(View.GONE);
		myCircleAdapter.notifyDataSetChanged();
		
	}
	
	/******
	 * @param page int 访问页
	 * @param type int 0：刷新  1：加载
	 * 
	 * **/
	void sendHttp(final int page,final int type)
	{
		ToolsUtil.showNoDataView(activity, false);
		httpCntrol.getMyCircle(page, 10000, showDialog, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				showDialog=false;
				pull_refresh_list.onRefreshComplete();
				currPage=page;
				if(obj!=null && obj instanceof RequestMyCircleInfoBean)
				{
					RequestMyCircleInfoBean bean=(RequestMyCircleInfoBean)obj;
					if (bean != null) {
						if(bean.getData()==null || bean.getData().getItems()==null || bean.getData().getItems().size()==0)
						{
							if(page==1)
							{
								pull_refresh_list.setMode(Mode.PULL_FROM_START);
								ToolsUtil.showNoDataView(activity, true);
							}
						}else
						{
							if(page==1)
							{
								pull_refresh_list.setMode(Mode.PULL_FROM_START);
							}
							int totalPage = bean.getTotalpaged();
							if (currPage >= totalPage) {
								//MyApplication.getInstance().showMessage(activity, activity.getResources().getString(R.string.lastpagedata_str));
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
						}
					} else {
						if(page==1)
						{
							ToolsUtil.showNoDataView(activity, true);
						}
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
	public void firstInitData(Activity activity) {
		/*if(isFirst)
		{
			requestFalshData();
		}*/
		this.activity=activity;
		requestFalshData();
	}
}
