package com.shenma.yueba.yangjia.fragment;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.DynamicAdapter;
import com.shenma.yueba.baijia.fragment.BaseFragment;
import com.shenma.yueba.baijia.modle.RequestUserDynamicInfoBean;
import com.shenma.yueba.baijia.modle.UserDynamicInfo;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;


/**
 * 动态列表的Fragment
 * @author a
 *
 */
public class DynamicListFragment extends BaseFragment {
	boolean showDialog=true;
	boolean isfirstStatus=false;
	// 当前页
	int currpage = Constants.CURRPAGE_VALUE;
	// 每页显示的条数
	//int pagesize = Constants.PAGESIZE_VALUE;
	int pagesize = 100000;
	private List<UserDynamicInfo> mList = new ArrayList<UserDynamicInfo>();
	private View view;
	private PullToRefreshListView pull_refresh_list;
	LinearLayout showloading_layout_view;
	DynamicAdapter msgAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (view == null) {
			view = inflater.inflate(R.layout.refresh_listview_without_title_layout, null);
			initPullView();
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		firstData();
		Log.i("msg", "msg2");
	}
	
	void initPullView()
	{
		pull_refresh_list=(PullToRefreshListView)view.findViewById(R.id.pull_refresh_list);
		showloading_layout_view=(LinearLayout)view.findViewById(R.id.showloading_layout_view);
		pull_refresh_list.setMode(Mode.BOTH);
		pull_refresh_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				pull_refresh_list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
						
						UserDynamicInfo msgListInfo=mList.get(arg2-1);
						int type=msgListInfo.getType();  
						switch(type)
						{
						case 0://0：关注了某人
							ToolsUtil.forwardShopMainActivity(getActivity(),msgListInfo.getDataId());
							break;
						case 1://1：加入了圈子
							ToolsUtil.forwardCircleActivity(getActivity(),msgListInfo.getDataId(),-1);
							break;
						case 2://2：购买了商品
							ToolsUtil.forwardProductInfoActivity(getActivity(),msgListInfo.getDataId());
							break;
						case 3://3：赞了某件商品
							ToolsUtil.forwardProductInfoActivity(getActivity(),msgListInfo.getDataId());
							break;
						}
						
					}
				});
			}
		});
		ToolsUtil.initPullResfresh(pull_refresh_list, getActivity());
		 
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
		msgAdapter=new DynamicAdapter(getActivity(), mList);
		pull_refresh_list.setAdapter(msgAdapter);
	}
	

	/*****
	 * 请求加载数据
	 * ***/
	public void requestData() {
		isfirstStatus=true;
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
		
		ToolsUtil.showNoDataView(getActivity(), view, false);
		HttpControl httpControl=new HttpControl();
		httpControl.GetBaijiaUserDynamic(page, pagesize, showDialog, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				isfirstStatus=false;
				currpage=page;
				showDialog=false;
				ToolsUtil.pullResfresh(pull_refresh_list);
				if(obj!=null && obj instanceof RequestUserDynamicInfoBean)
				{
					RequestUserDynamicInfoBean msgbean=(RequestUserDynamicInfoBean)obj;
					switch (type) {
					case 0:
						falshData(msgbean);
						break;
					case 1:
						addData(msgbean);
						break;
					}

					setPageStatus(msgbean, page);
				}else {
					http_Fails(500, getActivity().getResources()
							.getString(R.string.errorpagedata_str));
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				isfirstStatus=false;
				MyApplication.getInstance().showMessage(getActivity(),msg);
				ToolsUtil.pullResfresh(pull_refresh_list);
			}
		}, getActivity());
	}
	
	
	
	void setPageStatus(RequestUserDynamicInfoBean data, int page) {
		if (page == 1 && (data.getData() == null
						|| data.getData().getItems() == null || data
						.getData().getItems().size() == 0)) {
			if(pull_refresh_list!=null)
			{
				pull_refresh_list.setMode(Mode.PULL_FROM_START);
			}
			ToolsUtil.showNoDataView(getActivity(), view,true);
		} else if (page != 1
				&& (data.getData() == null || data.getData().getItems()==null || data.getData().getItems().size() == 0)) {
			if(pull_refresh_list!=null)
			{
				pull_refresh_list.setMode(Mode.BOTH);
			}
			MyApplication.getInstance().showMessage(
					getActivity(),
					getActivity().getResources().getString(
							R.string.lastpagedata_str));
		} else {
			if(pull_refresh_list!=null)
			{
				pull_refresh_list.setMode(Mode.BOTH);
			}
		}
	}
	
	/***
	 * 刷新viewpager数据
	 * ***/
	void falshData(RequestUserDynamicInfoBean bean) {
		currpage++;
		mList.clear();
		showDialog = false;
		if (bean.getData() != null) {
			if(bean.getData().getItems()!=null)
			{
				mList.addAll(bean.getData().getItems());
			}
		}
		if (msgAdapter != null) {
			msgAdapter.notifyDataSetChanged();
		}
		
	}

	/***
	 * 加载数据
	 * **/
	void addData(RequestUserDynamicInfoBean bean) {
		currpage++;
		showDialog=false;
		if (bean.getData() != null) {
			if(bean.getData().getItems()!=null)
			{
				mList.addAll(bean.getData().getItems());
			}
		}
		if (msgAdapter != null) {
			msgAdapter.notifyDataSetChanged();
		}
	}
	
	public void firstData()
	{
	   if(isfirstStatus)
	   {
		   return ;
	   }
	   requestFalshData();
	}
}
