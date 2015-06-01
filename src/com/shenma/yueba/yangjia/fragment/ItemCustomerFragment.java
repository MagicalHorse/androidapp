package com.shenma.yueba.yangjia.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
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
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.fragment.BaseFragment;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.yangjia.activity.OrderDetailActivity;
import com.shenma.yueba.yangjia.adapter.SalesManagerForAttestationBuyerAdapter;
import com.shenma.yueba.yangjia.modle.OrderItem;
import com.shenma.yueba.yangjia.modle.OrderListBackBean;

@SuppressLint("ValidFragment")
public class ItemCustomerFragment extends BaseFragment {
	private View rootView;// 缓存Fragment view
	private PullToRefreshListView rlv;
	private List<OrderItem> mList = new ArrayList<OrderItem>();
	private SalesManagerForAttestationBuyerAdapter adapter;
	private boolean isRefresh = true;
	private int page = 1;
	private int tag = 0;
	private String orderProductType;
	private String status;

	@SuppressLint("ValidFragment")
	public ItemCustomerFragment(int tag) {
		this.tag = tag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(
					R.layout.refresh_listview_without_title_layout, container,
					false);
			rlv = (PullToRefreshListView) rootView
					.findViewById(R.id.pull_refresh_list);
			rlv.setMode(Mode.BOTH);
			rlv.setOnRefreshListener(new OnRefreshListener2() {

				@Override
				public void onPullDownToRefresh(PullToRefreshBase refreshView) {
					page = 1;
					isRefresh = true;
					getDataFromNet(false,getActivity());
				}

				@Override
				public void onPullUpToRefresh(PullToRefreshBase refreshView) {
					page++;
					isRefresh = false;
					getDataFromNet(false,getActivity());
				}
			});
			rlv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent intent = new Intent(getActivity(),
							OrderDetailActivity.class);
					intent.putExtra("orderId", mList.get(arg2).getOrderNo());
					startActivity(intent);

				}
			});
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}

	public void getData(int tag, Context ctx) {
		this.tag = tag;
		if (tag == 0) {// 全部订单
			orderProductType = "";
			status = "";
			if(mList.size() != 0){
				return;
			}
		} else if (tag == 1) {// 待付款
			status = "0";
			orderProductType = "";
			if(mList.size() != 0){
				return;
			}
		} else if (tag == 2) {// 专柜自提
			orderProductType = "4";
			status = "";
			if(mList.size()!= 0){
				return;
			}
		} else if (tag == 3) {
			status = "3";
			orderProductType = "";
			if(mList.size() != 0){
				return;
			}
		}
		getDataFromNet(true,ctx);
	}

	
	
	private void getDataFromNet(boolean showDialog,Context ctx){
		HttpControl hControl = new HttpControl();
		hControl.getOrderList(page, Constants.PageSize, orderProductType,
				status, new HttpCallBackInterface() {

					@Override
					public void http_Success(Object obj) {
						rlv.onRefreshComplete();
						OrderListBackBean bean = (OrderListBackBean) obj;
						if (isRefresh) {
							mList.clear();
							mList.addAll(bean.getData().getOrderlist());
							adapter = new SalesManagerForAttestationBuyerAdapter(
									getActivity(), mList, 0);
							rlv.setAdapter(adapter);
						} else {
							if(bean.getData().getOrderlist()!=null && bean.getData().getOrderlist().size()>0){
								mList.addAll(bean.getData().getOrderlist());
							}else{
								Toast.makeText(getActivity(), "没有更多数据了...", 1000).show();
							}
							
							adapter.notifyDataSetChanged();
						}
					}

					@Override
					public void http_Fails(int error, String msg) {
						rlv.onRefreshComplete();
						Toast.makeText(getActivity(), msg, 1000).show();
					}
				}, ctx,showDialog);
	}
}
