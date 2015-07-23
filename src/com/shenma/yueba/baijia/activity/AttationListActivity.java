package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.AttationListAdapter;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.inter.AttationListInter;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.yangjia.modle.AttationAndFansItemBean;
import com.shenma.yueba.yangjia.modle.AttationAndFansListBackBean;
import com.umeng.analytics.MobclickAgent;


/**
 * 关注列表
 * @author a
 *
 */
public class AttationListActivity extends BaseActivityWithTopView implements AttationListInter {
	public static final String TYPE_FANS="FANS";//粉丝
	public static final String TYPE_ATTATION="ATTATION";//关注
	private AttationListAdapter brandAdapter;
	private List<AttationAndFansItemBean> mList = new ArrayList<AttationAndFansItemBean>();
	private View view;
	private PullToRefreshListView pull_refresh_list;
	HttpControl httoControl=new HttpControl();
	boolean showDialog=true;
	int status=0;
	int userID=-1;
	int currpage;
	int UserLevel=-1;
	AttationAndFansListBackBean bean;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		setContentView(R.layout.refresh_listview_with_title_layout);
		super.onCreate(savedInstanceState);
		
		userID=this.getIntent().getIntExtra("userID", -1);
		if(userID<0 || this.getIntent().getStringExtra("TYPE")==null)
		{
			MyApplication.getInstance().showMessage(this, "参数错误");
			finish();
			return;
		}
		if(this.getIntent().getStringExtra("TYPE").equals(TYPE_FANS))//如果是粉丝
		{
			status=1;
		}else if(this.getIntent().getStringExtra("TYPE").equals(TYPE_ATTATION))//如果是关注
		{
			status=0;
		}else
		{
			MyApplication.getInstance().showMessage(this, "参数错误");
			finish();
			return;
		}
		initView();
		requestFalshData();
	}

	private void initView() {
		String title="";
		if(status==0)
		{
			title="关注";
		}else if(status==1)
		{
			title="粉丝";
		}
		setTitle(title);
		FontManager.changeFonts(this, tv_top_title);
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		pull_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		pull_refresh_list.setMode(Mode.PULL_FROM_START);
		brandAdapter=new AttationListAdapter(this, mList,status,pull_refresh_list);
		pull_refresh_list.setAdapter(brandAdapter);
		pull_refresh_list.setOnPullEventListener(new OnPullEventListener<ListView>() {

					@Override
					public void onPullEvent(
							PullToRefreshBase<ListView> refreshView,
							State state, Mode direction) {

						// 设置标签显示的内容
						if (direction == Mode.PULL_FROM_START) {
							pull_refresh_list.getLoadingLayoutProxy()
									.setPullLabel(AttationListActivity.this.getResources().getString(R.string.Refreshonstr));
							pull_refresh_list.getLoadingLayoutProxy().setRefreshingLabel(AttationListActivity.this.getResources().getString(R.string.Refreshloadingstr));
							pull_refresh_list.getLoadingLayoutProxy().setReleaseLabel(AttationListActivity.this.getResources().getString(R.string.Loosentherefresh));
						} else if (direction == Mode.PULL_FROM_END) {
							pull_refresh_list.getLoadingLayoutProxy().setPullLabel(AttationListActivity.this.getResources().getString(R.string.Thedropdownloadstr));
							pull_refresh_list.getLoadingLayoutProxy().setRefreshingLabel(AttationListActivity.this.getResources().getString(R.string.RefreshLoadingstr));
							pull_refresh_list.getLoadingLayoutProxy().setReleaseLabel(AttationListActivity.this.getResources().getString(R.string.Loosentheloadstr));
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
		
	}
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		}
		public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		}


	
	
	@Override
	protected void onDestroy() {
		MyApplication.getInstance().removeActivity(this);//加入回退栈
		super.onDestroy();
	}
	
	
	
	/*****
	 * 请求加载数据
	 * ***/
	public void requestData() {
		sendHttp(userID,UserLevel,status,currpage, 1);
	}

	/*****
	 * 请求刷新数据
	 * ***/
	public void requestFalshData() {
		sendHttp(userID,UserLevel,status,1, 0);
	}
	
	
	void sendHttp(int UserID,int UserLevel,int status,final int page,final int type)
	{
		ToolsUtil.showNoDataView(AttationListActivity.this, false);
		//当前登录的用户id
		int CurrentUserId=Integer.parseInt(SharedUtil.getStringPerfernece(this, SharedUtil.user_id));
		httoControl.getAttationOrFansList(CurrentUserId,UserID, UserLevel, status, page,Constants.PAGESIZE_VALUE, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				pull_refresh_list.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    	pull_refresh_list.onRefreshComplete();
                    }
            }, 100);
			
				currpage=page;
				showDialog = false;
				if (obj != null&& obj instanceof AttationAndFansListBackBean) {
					bean = (AttationAndFansListBackBean) obj;
					if(bean==null || bean.getData()==null || bean.getData().getItems()==null ||bean.getData().getItems().size()==0)
					{
						if(page==1)
						{
							mList.clear();
							pull_refresh_list.setMode(Mode.PULL_FROM_START);
							ToolsUtil.showNoDataView(AttationListActivity.this,true);
							brandAdapter.notifyDataSetChanged();
						}else
						{
							MyApplication.getInstance().showMessage(AttationListActivity.this, AttationListActivity.this.getResources().getString(R.string.lastpagedata_str));
						}
					}else
					{
					   if(page==1)
					   {
						   pull_refresh_list.setMode(Mode.BOTH);
					   }
					   
					   int totalPage = Integer.parseInt(bean.getData().getTotalpaged());

						if (currpage >= totalPage) {
							//MyApplication.getInstance().showMessage(getActivity(), getActivity().getResources().getString(R.string.lastpagedata_str));
							pull_refresh_list.setMode(Mode.BOTH);
						} else {
							pull_refresh_list.setMode(Mode.BOTH);
						}
						switch (type) {
						case 0:
							falshData(bean.getData().getItems());
							break;
						case 1:
							addData(bean.getData().getItems());
							break;
						}
					}

					} else {
						if(page==1)
						{
							ToolsUtil.showNoDataView(AttationListActivity.this, true);
						}
						MyApplication.getInstance().showMessage(AttationListActivity.this, "没有任何数据");
					}

			}
			
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(AttationListActivity.this,msg);
				pull_refresh_list.onRefreshComplete();
			}
		}, this, showDialog);
	}
	
	
	/***
	 * 刷新viewpager数据
	 * ***/
	void falshData(List<AttationAndFansItemBean> bean_list) {
		currpage++;
		if (bean == null) {
			return;
		}
		if (bean_list != null) {
			mList.clear();
			mList.addAll(bean_list);
		}
		if (brandAdapter != null) {
			brandAdapter.notifyDataSetChanged();
		}
		showDialog = false;
	}

	/***
	 * 加载数据
	 * **/
	void addData(List<AttationAndFansItemBean> bean_list) {
		showDialog = false;
		currpage++;
		if (bean == null) {
			return;
		}
		if (bean_list != null) {
			mList.addAll(bean_list);
		}
		if (brandAdapter != null) {
			brandAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void refresh() {
		showDialog = true;
		requestFalshData();
	}
}
