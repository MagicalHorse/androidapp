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
import com.shenma.yueba.yangjia.modle.BuyerProductManagerListBean;

/**
 * 动态列表的Fragment
 * 
 * @author a
 * 
 */
@SuppressLint("ValidFragment")
public class ProductManagerFragmentForOnLine extends BaseFragment {
	private int type;// 0表示在线，1表示即将下线，2表示已经下线

	@SuppressLint("ValidFragment")
	public ProductManagerFragmentForOnLine(int type) {
		this.type = type;
	}

	private ProductManagerFragmentForOnLineAdapter adapter;
	private List<ProductManagerForOnLineBean> mList = new ArrayList<ProductManagerForOnLineBean>();
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
					.setAdapter(new ProductManagerFragmentForOnLineAdapter(
							getActivity(), mList, 0));
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
				getProductListForOnLine(index,ctx);
			}
			break;
		case 1:// 即将下线
			if (mList.size() == 0) {
				getProductListForWillOffLine(index);
			}
			break;
		case 2:// 已经下线
			if (mList.size() == 0) {
				getProductListForHasOffLine(index);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 获取在线商品
	 */
	private void getProductListForOnLine(int page,Context ctx) {
		HttpControl httpControl = new HttpControl();
		httpControl.getBuyerProductListForOnLine(page + "", Constants.PageSize,
				new HttpCallBackInterface() {
					@Override
					public void http_Success(Object obj) {
						BuyerProductManagerListBean bean = (BuyerProductManagerListBean)obj;
						bean.getItems();
						Toast.makeText(getActivity(),"在线成功", 1000).show();
					}

					@Override
					public void http_Fails(int error, String msg) {
						Toast.makeText(getActivity(),"在线失败", 1000).show();

					}
				}, ctx, true, true);

	}

	/**
	 * 获取即将下线商品
	 */
	private void getProductListForWillOffLine(int page) {
		HttpControl httpControl = new HttpControl();
		httpControl.getBuyerProductListForWillOffLine(page + "",
				Constants.PageSize, new HttpCallBackInterface() {
					@Override
					public void http_Success(Object obj) {
						Toast.makeText(getActivity(),"即将下线成功", 1000).show();

					}

					@Override
					public void http_Fails(int error, String msg) {
						Toast.makeText(getActivity(),"即将下线失败", 1000).show();

					}
				}, getActivity(), true, true);

	}

	/**
	 * 获取已经下线商品
	 */
	private void getProductListForHasOffLine(int page) {
		HttpControl httpControl = new HttpControl();
		httpControl.getBuyerProductListForHasOffLine(page + "",
				Constants.PageSize, new HttpCallBackInterface() {
					@Override
					public void http_Success(Object obj) {
						Toast.makeText(getActivity(),"已经下线成功", 1000).show();

					}

					@Override
					public void http_Fails(int error, String msg) {
						Toast.makeText(getActivity(),"已经下线失败", 1000).show();

					}
				}, getActivity(), true, true);

	}

}
