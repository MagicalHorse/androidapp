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
import android.widget.TextView;
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
import com.shenma.yueba.yangjia.adapter.HuoKuanIncomeAndOutGoingAdapter;
import com.shenma.yueba.yangjia.modle.HuoKuanItem;
import com.shenma.yueba.yangjia.modle.HuoKuanListBackBean;
import com.shenma.yueba.yangjia.modle.OrderItem;
import com.shenma.yueba.yangjia.modle.OrderListBackBean;

@SuppressLint("ValidFragment")
public class HuoKuanIncomeAndOutGoingFragment extends BaseFragment {
	private View rootView;// 缓存Fragment view
	private PullToRefreshListView rlv;
	private List<HuoKuanItem> mList = new ArrayList<HuoKuanItem>();
	private HuoKuanIncomeAndOutGoingAdapter adapter;
	private boolean isRefresh = true;
	private int page = 1;
	private int tag = 0;
	private String status;// 0冻结中，1可提现，2已经提现，3退款
	public TextView tv_nodata;

	@SuppressLint("ValidFragment")
	public HuoKuanIncomeAndOutGoingFragment(int tag) {
		this.tag = tag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(
					R.layout.refresh_listview_without_title_layout, container,
					false);
			tv_nodata = (TextView) rootView.findViewById(R.id.tv_nodata);
			rlv = (PullToRefreshListView) rootView
					.findViewById(R.id.pull_refresh_list);
			rlv.setMode(Mode.BOTH);
			rlv.setOnRefreshListener(new OnRefreshListener2() {

				@Override
				public void onPullDownToRefresh(PullToRefreshBase refreshView) {
					page = 1;
					isRefresh = true;
					getDataFromNet(false, getActivity());
				}

				@Override
				public void onPullUpToRefresh(PullToRefreshBase refreshView) {
					page++;
					isRefresh = false;
					getDataFromNet(false, getActivity());
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
		if (tag == 0) {// 可提现
			status = "1";
			if (mList.size() != 0) {
				return;
			}
		} else if (tag == 1) {// 冻结中
			status = "0";
			if (mList.size() != 0) {
				return;
			}
		} else if (tag == 2) {// 已提现
			status = "2";
			if (mList.size() != 0) {
				return;
			}
		} else if (tag == 3) {// 退款
			status = "3";
			if (mList.size() != 0) {
				return;
			}
		}
		getDataFromNet(true, ctx);
	}

	private void getDataFromNet(boolean showDialog, Context ctx) {
		HttpControl httpContorl = new HttpControl();
		httpContorl.getHuoKuanList(page, status, new HttpCallBackInterface() {
			@Override
			public void http_Success(Object obj) {
				rlv.onRefreshComplete();
				HuoKuanListBackBean bean = (HuoKuanListBackBean) obj;
				if (isRefresh) {
					if (bean.getData() != null
							&& bean.getData().getItems() != null
							&& bean.getData().getItems().size() > 0) {
						tv_nodata.setVisibility(View.GONE);
						mList.clear();
						mList.addAll(bean.getData().getItems());
						adapter = new HuoKuanIncomeAndOutGoingAdapter(
								getActivity(), mList, tag);
						rlv.setAdapter(adapter);
					} else {
						tv_nodata.setVisibility(View.VISIBLE);
					}

				} else {
					if (bean.getData().getItems() != null
							&& bean.getData().getItems().size() > 0) {
						mList.addAll(bean.getData().getItems());
					} else {
						Toast.makeText(getActivity(), "没有更多数据了...", 1000)
								.show();
					}

					adapter.notifyDataSetChanged();
				}
			}

			@Override
			public void http_Fails(int error, String msg) {
				rlv.onRefreshComplete();
				Toast.makeText(getActivity(), msg, 1000).show();
			}
		}, getActivity(), true, false);
	}
}
