package com.shenma.yueba.yangjia.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.CircleInfoActivity;
import com.shenma.yueba.baijia.fragment.BaseFragment;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.yangjia.adapter.MyCircleForSocialAdapter;
import com.shenma.yueba.yangjia.adapter.SalesManagerForAttestationBuyerAdapter;
import com.shenma.yueba.yangjia.modle.CircleListBackBean;
import com.shenma.yueba.yangjia.modle.OrderListBackBean;
import com.shenma.yueba.yangjia.modle.CirlceItemBean;

/**
 * 社交管理中的-----我的圈子
 * 
 * @author a
 * 
 */
public class MyCircleForSocialFragment extends BaseFragment {

	private PullToRefreshListView pull_refresh_list;
	private MyCircleForSocialAdapter adapter;
	private List<CirlceItemBean> mList = new ArrayList<CirlceItemBean>();
	private int page = 1;
	private boolean isRefresh = true;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(
				R.layout.refresh_listview_without_title_layout, null);
		pull_refresh_list = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		pull_refresh_list.setAdapter(adapter);
		pull_refresh_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(),CircleInfoActivity.class);
				startActivity(intent);
			}
		});
		pull_refresh_list.setOnRefreshListener(new OnRefreshListener2() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				//下拉刷新
				page = 1;
				isRefresh = true;
				getCircleListFromNet(getActivity(),true,false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				//上拉加载更多
				page++;
				isRefresh = false;
				getCircleListFromNet(getActivity(),false,false);
			}
		});
		return view;
	}
	
	public void getData(Context ctx,boolean isRefresh,boolean showDialog){
		if(mList.size()==0){
			getCircleListFromNet(ctx, isRefresh, showDialog);
		}
	}
	
	
	public void getCircleListFromNet(Context ctx,final boolean isRefresh,boolean showDialog){
		HttpControl httpControl = new HttpControl();
		httpControl.getCircleList(page,showDialog, new HttpCallBackInterface() {
			@Override
			public void http_Success(Object obj) {
				pull_refresh_list.onRefreshComplete();
				CircleListBackBean bean = (CircleListBackBean) obj;
				if (isRefresh) {
					if(bean!=null && bean.getData()!=null && bean.getData().getItems()!=null){
						mList.clear();
						mList.addAll(bean.getData().getItems());
						adapter = new MyCircleForSocialAdapter(getActivity(), mList);
						pull_refresh_list.setAdapter(adapter);
					}
				} else {
					if(bean!=null && bean.getData()!=null && bean.getData().getItems()!=null){
						mList.addAll(bean.getData().getItems());
						adapter.notifyDataSetChanged();
					}else{
						Toast.makeText(getActivity(), "没有更多数据了...", 1000).show();
					}
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				// TODO Auto-generated method stub
				
			}
		}, ctx);
	}
}
