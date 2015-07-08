package com.shenma.yueba.baijia.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.shenma.yueba.ChatActivity;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.FragmentBean;
import com.shenma.yueba.baijia.modle.MyCircleInfo;
import com.shenma.yueba.baijia.modle.RequestMyCircleInfoBean;
import com.shenma.yueba.baijia.modle.RequestTuiJianCircleInfoBean;
import com.shenma.yueba.baijia.modle.TuiJianCircleInfo;
import com.shenma.yueba.baijia.modle.TuiJianCircleInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;

public class CircleView extends BaseView{
	static CircleView quanziControlView;
	Activity activity;
	List<FragmentBean> fragment_list=new ArrayList<FragmentBean>();
	List<View> footer_list=new ArrayList<View>();
	//当前选中的id
	int currid=-1;
	View v;
	PullToRefreshGridView baijia_quanzi_layout_tanb1_gridbview;
	LinearLayout showloading_layout_view;
	List<TuiJianCircleInfo> items=new ArrayList<TuiJianCircleInfo>();
	LayoutInflater inflater;
	int currPage=Constants.CURRPAGE_VALUE;
	int pageSize=Constants.PAGESIZE_VALUE;
	boolean showDialog=true;
	boolean isFirst=true;
	HttpControl httpCntrol=new HttpControl();
	public static CircleView the(Activity activity)
	{
		if(quanziControlView==null)
		{
			quanziControlView=new CircleView();
		}
		quanziControlView.activity=activity;
		
		return quanziControlView;
	}

	public View getView(Activity activity)
	{
		this.activity=activity;
		if(v==null)
		{

			inflater=activity.getLayoutInflater();
			v=inflater.inflate(R.layout.circleview_layout, null);
			initPullView();
			initView(v);
			firstInitData(activity);
		}
		baijia_quanzi_layout_tanb1_gridbview.setFocusable(false);
		baijia_quanzi_layout_tanb1_gridbview.setFocusableInTouchMode(false);
		return v;
	}
	
	void initPullView()
	{
		baijia_quanzi_layout_tanb1_gridbview=(PullToRefreshGridView)v.findViewById(R.id.baijia_quanzi_layout_tanb1_gridbview);
		 //设置标签显示的内容
		 //pulltorefreshscrollview.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");   
		 /*pulltorefreshscrollview.getLoadingLayoutProxy().setPullLabel("下拉刷新");  
		 pulltorefreshscrollview.getLoadingLayoutProxy().setRefreshingLabel("刷新中。。。");  
		 pulltorefreshscrollview.getLoadingLayoutProxy().setReleaseLabel("松开刷新");*/
		baijia_quanzi_layout_tanb1_gridbview.setMode(Mode.BOTH);
		 
		baijia_quanzi_layout_tanb1_gridbview.setOnPullEventListener(new OnPullEventListener<GridView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<GridView> refreshView,
					State state, Mode direction) {
				
				if(direction==Mode.PULL_FROM_START)
				{
					baijia_quanzi_layout_tanb1_gridbview.getLoadingLayoutProxy().setPullLabel("上拉刷新");  
					baijia_quanzi_layout_tanb1_gridbview.getLoadingLayoutProxy().setRefreshingLabel("刷新中。。。");  
					baijia_quanzi_layout_tanb1_gridbview.getLoadingLayoutProxy().setReleaseLabel("松开刷新");
				}else if(direction==Mode.PULL_FROM_END)
				{
					baijia_quanzi_layout_tanb1_gridbview.getLoadingLayoutProxy().setPullLabel("下拉加载");  
					baijia_quanzi_layout_tanb1_gridbview.getLoadingLayoutProxy().setRefreshingLabel("加载中。。。");  
					baijia_quanzi_layout_tanb1_gridbview.getLoadingLayoutProxy().setReleaseLabel("松开加载");
				}
			}
		});
		 
		baijia_quanzi_layout_tanb1_gridbview.setOnRefreshListener(new OnRefreshListener2() {

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
	}
	
	void initView(View v)
	{
		baijia_quanzi_layout_tanb1_gridbview=(PullToRefreshGridView)v.findViewById(R.id.baijia_quanzi_layout_tanb1_gridbview);
		showloading_layout_view=(LinearLayout)v.findViewById(R.id.showloading_layout_view);
		
		baijia_quanzi_layout_tanb1_gridbview.setAdapter(baseAdapter);
		baijia_quanzi_layout_tanb1_gridbview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TuiJianCircleInfo myCircleInfo=items.get(arg2);
				Intent intent=new Intent(activity,ChatActivity.class);
				intent.putExtra("Chat_Type", ChatActivity.chat_type_group);
				intent.putExtra("Chat_NAME",myCircleInfo.getName());//圈子名字
				intent.putExtra("circleId", myCircleInfo.getId());
				activity.startActivity(intent);
			}
		});
	}
	
	
	
	
	BaseAdapter baseAdapter=new BaseAdapter()
	{

		@Override
		public int getCount() {
			
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			
			return null;
		}

		@Override
		public long getItemId(int position) {
			
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			Holder holder;
			if(convertView==null)
			{
				holder=new Holder();
				convertView=inflater.inflate(R.layout.circle_item, null);
				holder.v=convertView;
				holder.riv=(RoundImageView)convertView.findViewById(R.id.riv);
				holder.tv_circle_name=(TextView)convertView.findViewById(R.id.tv_circle_name);
				holder.tv_count=(TextView)convertView.findViewById(R.id.tv_count);
				holder.tv_address=(TextView)convertView.findViewById(R.id.tv_address);
				convertView.setTag(holder);
				ToolsUtil.setFontStyle(activity, convertView, R.id.tv_circle_name,R.id.tv_count,R.id.tv_address);
			}else
			{
				holder=(Holder)convertView.getTag();
				
			}
			TuiJianCircleInfo myCircleInfo=items.get(position);
			MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.nullToString(myCircleInfo.getLogo()), holder.riv);
			holder.tv_circle_name.setText(ToolsUtil.nullToString(myCircleInfo.getName()));
			holder.tv_count.setText(myCircleInfo.getMemberCount()+"");
			holder.tv_address.setText(ToolsUtil.nullToString(myCircleInfo.getAddress()));
			return convertView;
		}
		
	};
	
	class Holder
	{
		View v;
		RoundImageView riv;
		TextView tv_circle_name;
		TextView tv_count;
		TextView tv_address;
	}
	
	void requestData()
	{
		sendHttp(currPage,1);
	}
	
	void requestFalshData()
	{
		sendHttp(1,0);
	}
	
	
	void addData(TuiJianCircleInfoBean bean)
	{
		showDialog=false;
		currPage++;
		if(bean.getItems()!=null)
		{
			items.addAll(bean.getItems());
		}
		showloading_layout_view.setVisibility(View.GONE);
		baseAdapter.notifyDataSetChanged();
		//ListUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
	}
	
	void falshData(TuiJianCircleInfoBean bean)
	{
		isFirst=false;
		currPage++;
		items.clear();
		if(bean.getItems()!=null)
		{
			items.addAll(bean.getItems());
		}
		showloading_layout_view.setVisibility(View.GONE);
		baseAdapter.notifyDataSetChanged();
		
	}
	
	
	void sendHttp(final int page,final int type)
	{
		ToolsUtil.showNoDataView(activity, false);
		httpCntrol.getRecommendGroup(page, pageSize, showDialog, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				currPage=page;
				showDialog=false;
				baijia_quanzi_layout_tanb1_gridbview.onRefreshComplete();
				if(obj!=null && obj instanceof RequestTuiJianCircleInfoBean)
				{
					RequestTuiJianCircleInfoBean bean=(RequestTuiJianCircleInfoBean)obj;
					if (bean != null) {
						if(bean.getData()==null || bean.getData().getItems()==null || bean.getData().getItems().size()==0)
						   {
							    if(page==1)
							    {
							    	baijia_quanzi_layout_tanb1_gridbview.setMode(Mode.PULL_FROM_START);
								    ToolsUtil.showNoDataView(activity, true);
							    }
								
						   }else
						   {
							   if(page==1)
							    {
							    	baijia_quanzi_layout_tanb1_gridbview.setMode(Mode.PULL_FROM_START);
							    }
							   int totalPage = bean.getTotalpaged();
								if (currPage >= totalPage) {
									baijia_quanzi_layout_tanb1_gridbview.setMode(Mode.PULL_FROM_START);
									//MyApplication.getInstance().showMessage(activity, activity.getResources().getString(R.string.lastpagedata_str));
								} else {
									baijia_quanzi_layout_tanb1_gridbview.setMode(Mode.BOTH);
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
				MyApplication.getInstance().showMessage(activity, msg);
				baijia_quanzi_layout_tanb1_gridbview.onRefreshComplete();
			}
		},activity );
	}
	
	/*****
	 * 首次加载
	 * ***/
	public void firstInitData(Activity activity)
	{
		this.activity=activity;
		if(isFirst)
		{
			requestFalshData();
		}
		
	}
}
