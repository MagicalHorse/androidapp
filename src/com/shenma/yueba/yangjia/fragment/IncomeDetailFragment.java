package com.shenma.yueba.yangjia.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.ProductManagerFragmentForOnLineAdapter;
import com.shenma.yueba.baijia.fragment.BaseFragment;
import com.shenma.yueba.baijia.modle.ProductManagerForOnLineBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.yangjia.activity.ProductManagerActivity;
import com.shenma.yueba.yangjia.adapter.IncomeDetailAdapter;
import com.shenma.yueba.yangjia.modle.BuyerProductManagerListBean;
import com.shenma.yueba.yangjia.modle.IncomeDetailListBean;

/**
 * 提现历史的Fragment
 * 
 * @author a
 * 
 */
@SuppressLint("ValidFragment")
public class IncomeDetailFragment extends BaseFragment {
	private int type;// 0表示在线，1表示即将下线，2表示已经下线

	@SuppressLint("ValidFragment")
	public IncomeDetailFragment(int type) {
		this.type = type;
	}

	private int page = 1;
	private IncomeDetailAdapter adapter;
	private List<IncomeDetailListBean> mList = new ArrayList<IncomeDetailListBean>();
	private View view;
	private PullToRefreshListView pull_refresh_list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (type == 0) {// 在线
			Log.i("productstatus", type + "-------");
		} else if (type == 1) {// 即将下线
			Log.i("productstatus", type + "-------");
		} else if (type == 2) {// 已经下线
			Log.i("productstatus", type + "-------");
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser && isVisible()
				&& pull_refresh_list.getVisibility() == View.VISIBLE) {
			Log.i("productstatus", "-------");
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (view == null) {
			view = inflater.inflate(
					R.layout.refresh_listview_without_title_layout, null);
			pull_refresh_list = (PullToRefreshListView) view
					.findViewById(R.id.pull_refresh_list);
			pull_refresh_list.setMode(Mode.BOTH);
			pull_refresh_list
					.setAdapter(new IncomeDetailAdapter(getActivity(), mList, 0));
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}

	public void getData(int index,Context ctx) {
		switch (index) {
		case 0:// 在线
			if (mList.size() == 0) {
				type = 3;
				getIncomeDetail();
			}
			break;
		case 1:// 即将下线
			if (mList.size() == 0) {
				type = 1;
				getIncomeDetail();
			}
			break;
		case 2:// 已经下线
			if (mList.size() == 0) {
				type = 2;
				getIncomeDetail();
			}
			break;
		default:
			break;
		}
	}

	
	
	public void getIncomeDetail(){
		HttpControl httpControl = new HttpControl();
		httpControl.getIncomeDetail(page, Constants.PageSize, type+"", new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				// TODO Auto-generated method stub
				
			}
		}, getActivity());
		
		
	}
	
	

}
