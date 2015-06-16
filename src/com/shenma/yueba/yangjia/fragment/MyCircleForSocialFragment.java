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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.CircleInfoActivity;
import com.shenma.yueba.baijia.fragment.BaseFragment;
import com.shenma.yueba.constants.Constants;
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
	public TextView tv_nodata;
	private int page = 1;
	private boolean isRefresh = true;
	private View view;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(
				R.layout.refresh_listview_without_title_layout, null);
		tv_nodata = (TextView) view.findViewById(R.id.tv_nodata);
		pull_refresh_list = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		pull_refresh_list.setAdapter(adapter);
		pull_refresh_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				Intent intent = new Intent(getActivity(),CircleInfoActivity.class);
				intent.putExtra("circleId", mList.get(pos-1).getId());
				intent.putExtra("from", "1");//1表示来自养家中的圈子管理
				getActivity().startActivityForResult(intent, Constants.REQUESTCODE);
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
				if(pull_refresh_list ==null){
					pull_refresh_list = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
				}
				pull_refresh_list.onRefreshComplete();
				CircleListBackBean bean = (CircleListBackBean) obj;
				if (isRefresh) {
					if(bean!=null && bean.getData()!=null && bean.getData().getItems()!=null&& bean.getData().getItems().size()>0){
						tv_nodata.setVisibility(View.GONE);
						mList.clear();
						mList.addAll(bean.getData().getItems());
						adapter = new MyCircleForSocialAdapter(getActivity(), mList);
						pull_refresh_list.setAdapter(adapter);
					}else{
						tv_nodata.setVisibility(View.VISIBLE);
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
				Toast.makeText(getActivity(),msg, 1000).show();
				
			}
		}, ctx);
	}
}
