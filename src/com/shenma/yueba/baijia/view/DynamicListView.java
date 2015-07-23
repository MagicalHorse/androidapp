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
import com.shenma.yueba.baijia.adapter.DynamicAdapter;
import com.shenma.yueba.baijia.adapter.MsgAdapter;
import com.shenma.yueba.baijia.modle.MsgListInfo;
import com.shenma.yueba.baijia.modle.RequestUserDynamicInfoBean;
import com.shenma.yueba.baijia.modle.UserDynamicInfo;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-19 下午2:03:37  
 * 程序的简单说明  
 */

public class DynamicListView extends BaseView{
	Activity activity;
	LayoutInflater layoutInflater;
	boolean showDialog=true;
	boolean isfirstStatus=false;
	// 当前页
	int currpage = Constants.CURRPAGE_VALUE;
	// 每页显示的条数
	int pagesize = Constants.PAGESIZE_VALUE;
	private List<UserDynamicInfo> mList = new ArrayList<UserDynamicInfo>();
	private View view;
	private PullToRefreshListView pull_refresh_list;
	LinearLayout showloading_layout_view;
	DynamicAdapter msgAdapter;
	
	public View getView(Activity activity)
	{
		this.activity=activity;
		if(view==null)
		{
			layoutInflater=activity.getLayoutInflater();
			initView();
			initPullView();
			//firstData();
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
		//pull_refresh_list.setMode(Mode.BOTH);
		pull_refresh_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				/*UserDynamicInfo msgListInfo=mList.get(arg2-1);
				Intent intent=new Intent(activity,ChatActivity.class);
				intent.putExtra("Chat_Type", ChatActivity.chat_type_private);//类型 圈子 还是私聊
				intent.putExtra("Chat_NAME",msgListInfo.getName());//名字
				intent.putExtra("toUser_id",msgListInfo.getId());//touser_id
				activity.startActivity(intent);*/
			}
		});
		
		
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
		msgAdapter=new DynamicAdapter(activity, mList);
		pull_refresh_list.setAdapter(msgAdapter);
	}
	
	
	/*****
	 * 请求加载数据
	 * ***/
	public void requestData() {
		sendHttp(currpage, 1);
	}

	/*****
	 * 请求刷新数据
	 * ***/
	public void requestFalshData() {
		isfirstStatus=true;
		sendHttp(1, 0);
	}
	

	
	/******
	 * 访问网络
	 * @param page int 当问的页数
	 * @param type int 类型
	 * ***/
	void sendHttp(final int page,final int type)
	{
		
		ToolsUtil.showNoDataView(activity, view, false);
		HttpControl httpControl=new HttpControl();
		httpControl.GetBaijiaUserDynamic(page, pagesize, showDialog, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				isfirstStatus=false;
				currpage=page;
				showDialog=false;
				pull_refresh_list.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    	pull_refresh_list.onRefreshComplete();
                    }
            }, 100);
				if(obj!=null && obj instanceof RequestUserDynamicInfoBean)
				{
					RequestUserDynamicInfoBean msgbean=(RequestUserDynamicInfoBean)obj;
					if(msgbean.getData()==null || msgbean.getData().getItems()==null || msgbean.getData().getItems().size()==0)
					{
						if(page==1)
						{
							ToolsUtil.showNoDataView(activity, view, true);
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
						   
						   int totalPage = msgbean.getData().getTotalpaged();

							if (currpage >= totalPage) {
								//MyApplication.getInstance().showMessage(activity, activity.getResources().getString(R.string.lastpagedata_str));
								pull_refresh_list.setMode(Mode.BOTH);
							} else {
								pull_refresh_list.setMode(Mode.BOTH);
							}
							switch (type) {
							case 0:
								falshData(msgbean.getData().getItems());
								break;
							case 1:
								addData(msgbean.getData().getItems());
								break;
							}
						
					}
				}else {
					if(page==1)
					{
						ToolsUtil.showNoDataView(activity, view,  true);
					}
					MyApplication.getInstance().showMessage(activity, "没有任何数据");
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				isfirstStatus=false;
				MyApplication.getInstance().showMessage(activity,msg);
				pull_refresh_list.onRefreshComplete();
			}
		}, activity);
	}
	
	
	/***
	 * 刷新viewpager数据
	 * ***/
	void falshData(List<UserDynamicInfo> msg_list) {
		currpage++;
		if (mList != null) {
			mList.clear();
			mList.addAll(msg_list);
		}
		if (msgAdapter != null) {
			msgAdapter.notifyDataSetChanged();
		}
		showDialog = false;
	}

	/***
	 * 加载数据
	 * **/
	void addData(List<UserDynamicInfo> msg_list) {
		currpage++;
		if (mList != null) {
			mList.addAll(msg_list);
		}
		if (msgAdapter != null) {
			msgAdapter.notifyDataSetChanged();
		}
	}
	@Override
	public void firstInitData(Activity activity) {
		if(isfirstStatus)
		   {
			   return ;
		   }
		   requestFalshData();
	}
}
