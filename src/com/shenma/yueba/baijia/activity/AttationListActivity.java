package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.AttationListAdapter;
import com.shenma.yueba.baijia.modle.AttationListBean;
import com.shenma.yueba.baijia.modle.BaiJiaOrderListInfoBean;
import com.shenma.yueba.baijia.modle.RequestBaiJiaOrderListInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.yangjia.modle.AttationAndFansItemBean;
import com.shenma.yueba.yangjia.modle.AttationAndFansListBackBean;
import com.umeng.analytics.MobclickAgent;


/**
 * 关注列表
 * @author a
 *
 */
public class AttationListActivity extends BaseActivityWithTopView {
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
		setContentView(R.layout.refresh_listview_with_title_layout);
		super.onCreate(savedInstanceState);
		initView();
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
	}

	private void initView() {
		pull_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		pull_refresh_list.setMode(Mode.DISABLED);
		//pull_refresh_list.setAdapter(new AttationListAdapter(this, mList));
		
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
		httoControl.getAttationOrFansList(UserID, UserLevel, status, page,Constants.PAGESIZE_VALUE, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				pull_refresh_list.onRefreshComplete();
				currpage=page;
				showDialog = false;
				if (obj != null&& obj instanceof AttationAndFansListBackBean) {
					bean = (AttationAndFansListBackBean) obj;
					if(bean==null || bean.getData()==null || bean.getData().getItems()==null ||bean.getData().getItems().size()==0)
					{
						if(page==1)
						{
							pull_refresh_list.setMode(Mode.PULL_FROM_START);
							ToolsUtil.showNoDataView(AttationListActivity.this,true);
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
}
