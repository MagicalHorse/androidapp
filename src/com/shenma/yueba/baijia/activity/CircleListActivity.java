package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.shenma.yueba.baijia.adapter.BaiJiaMyCircleAdapter;
import com.shenma.yueba.baijia.modle.MyCircleInfo;
import com.shenma.yueba.baijia.modle.MyCircleInfoBean;
import com.shenma.yueba.baijia.modle.RequestMyCircleInfoBean;
import com.shenma.yueba.baijia.view.MyCircleView;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.umeng.analytics.MobclickAgent;


/**  
 * @author gyj  
 * @version 创建时间：2015-5-20 上午11:24:51  
 * 程序的简单说明  
 */

public class CircleListActivity extends BaseActivityWithTopView{
	private View view;
	private PullToRefreshListView pull_refresh_list;
	LinearLayout showloading_layout_view;
	static MyCircleView myCircleView;
	LayoutInflater inflater;
	BaiJiaMyCircleAdapter myCircleAdapter;
	int currPage=Constants.CURRPAGE_VALUE;
	int pageSize=Constants.PAGESIZE_VALUE;
	boolean showDialog=true;
	HttpControl httpCntrol=new HttpControl();
	List<MyCircleInfo> items=new ArrayList<MyCircleInfo>();
	int userID=-1;
	
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		inflater=this.getLayoutInflater();
		view = inflater.inflate(R.layout.circlelist_layout, null);
		setContentView(view);
		super.onCreate(savedInstanceState);
		userID=this.getIntent().getIntExtra("userID", -1);
		if(userID<0)
		{
			MyApplication.getInstance().showMessage(this, "用户信息错误");
			finish();
			return;
		}
		initPullView();
		requestFalshData();
	}
	
	void initPullView()
	{
		setTitle("我的圈子");
		FontManager.changeFonts(CircleListActivity.this, tv_top_title);
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CircleListActivity.this.finish();
			}
		});
		pull_refresh_list=(PullToRefreshListView)view.findViewById(R.id.pull_refresh_list);
		showloading_layout_view=(LinearLayout)view.findViewById(R.id.showloading_layout_view);
		pull_refresh_list.setMode(Mode.DISABLED);
		 
		pull_refresh_list.setOnPullEventListener(new OnPullEventListener<ListView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
					State state, Mode direction) {
				// 设置标签显示的内容
				if (direction == Mode.PULL_FROM_START) {
					pull_refresh_list.getLoadingLayoutProxy()
							.setPullLabel(CircleListActivity.this.getResources().getString(R.string.Refreshonstr));
					pull_refresh_list.getLoadingLayoutProxy().setRefreshingLabel(CircleListActivity.this.getResources().getString(R.string.Refreshloadingstr));
					pull_refresh_list.getLoadingLayoutProxy().setReleaseLabel(CircleListActivity.this.getResources().getString(R.string.Loosentherefresh));
				} else if (direction == Mode.PULL_FROM_END) {
					pull_refresh_list.getLoadingLayoutProxy().setPullLabel(CircleListActivity.this.getResources().getString(R.string.Thedropdownloadstr));
					pull_refresh_list.getLoadingLayoutProxy().setRefreshingLabel(CircleListActivity.this.getResources().getString(R.string.RefreshLoadingstr));
					pull_refresh_list.getLoadingLayoutProxy().setReleaseLabel(CircleListActivity.this.getResources().getString(R.string.Loosentheloadstr));
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
		myCircleAdapter=new BaiJiaMyCircleAdapter(CircleListActivity.this, items);
		pull_refresh_list.setAdapter(myCircleAdapter);
		pull_refresh_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				MyCircleInfo myCircleInfo=items.get(arg2-1);
				Intent intent=new Intent(CircleListActivity.this,ChatActivity.class);
				intent.putExtra("Chat_Type", ChatActivity.chat_type_group);//类型 圈子 还是私聊
				intent.putExtra("Chat_NAME",myCircleInfo.getName());//圈子名字
				intent.putExtra("circleId",myCircleInfo.getId());
				startActivity(intent);
			}
		});
	}
	
	
	void requestData()
	{
		sendHttp(currPage,1);
	}
	
	void requestFalshData()
	{
		sendHttp(1,0);
	}
	
	
	void addData(MyCircleInfoBean bean)
	{
		currPage++;
		if(bean.getItems()!=null)
		{
			items.addAll(bean.getItems());
		}
		showloading_layout_view.setVisibility(View.GONE);
		myCircleAdapter.notifyDataSetChanged();
		pull_refresh_list.onRefreshComplete();
	}
	
	void falshData(MyCircleInfoBean bean)
	{
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
		
		httpCntrol.getUserGroups(userID,page, pageSize, showDialog, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				pull_refresh_list.onRefreshComplete();
				showDialog=false;
				currPage=page;
				if(obj!=null && obj instanceof RequestMyCircleInfoBean)
				{
					RequestMyCircleInfoBean bean=(RequestMyCircleInfoBean)obj;
					if (bean != null) {
						if(bean.getData()==null || bean.getData().getItems()==null || bean.getData().getItems().size()==0)
						{
							if(page==1)
							{
								items.clear();
								pull_refresh_list.setMode(Mode.PULL_FROM_START);
								ToolsUtil.showNoDataView(CircleListActivity.this, true);
							}else
							{
								MyApplication.getInstance().showMessage(CircleListActivity.this, CircleListActivity.this.getResources().getString(R.string.lastpagedata_str));
							}
						}else
						{
							if(page==1)
							{
								pull_refresh_list.setMode(Mode.BOTH);
							}
							int totalPage = bean.getTotalpaged();
							if (currPage >= totalPage) {
								//MyApplication.getInstance().showMessage(CircleListActivity.this, CircleListActivity.this.getResources().getString(R.string.lastpagedata_str));
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
							ToolsUtil.showNoDataView(CircleListActivity.this,view, true);
						}
						MyApplication.getInstance().showMessage(CircleListActivity.this, "没有任何数据");
					}
				}
				
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(CircleListActivity.this, msg);
				CircleListActivity.this.finish();
			}
		},CircleListActivity.this );
	}
	
	
	
	  @Override
	    protected void onDestroy() {
	    	MyApplication.getInstance().removeActivity(this);//加入回退栈
	    	super.onDestroy();
	    }
	  
	  

		public void onResume() {
			super.onResume();
			MobclickAgent.onResume(this);
			}
			public void onPause() {
			super.onPause();
			MobclickAgent.onPause(this);
			}
}
