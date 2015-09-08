package com.shenma.yueba.yangjia.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.ProductManagerFragmentForOnLineAdapter;
import com.shenma.yueba.baijia.fragment.BaseFragment;
import com.shenma.yueba.baijia.modle.ProductManagerForOnLineBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.inter.RefreshProductListInter;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.yangjia.modle.BuyerProductManagerListBack;

/**
 * 动态列表的Fragment
 * 
 * @author a
 * 
 */
@SuppressLint("ValidFragment")
public class ProductManagerFragmentForOnLine extends BaseFragment implements RefreshProductListInter{
	private int type;// 1表示在线，2表示即将下线，0表示已经下线

	@SuppressLint("ValidFragment")
	public ProductManagerFragmentForOnLine(int type) {
		this.type = type;
	}

	private ProductManagerFragmentForOnLineAdapter adapter;
	private boolean isRefresh = true;
	private List<ProductManagerForOnLineBean> mList = new ArrayList<ProductManagerForOnLineBean>();
	private View view;
	private PullToRefreshListView pull_refresh_list;
	private int page = 1;
	public TextView tv_nodata;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("fragment111", "oncreate");
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
			tv_nodata = (TextView) view.findViewById(R.id.tv_nodata);
			pull_refresh_list = (PullToRefreshListView) view
					.findViewById(R.id.pull_refresh_list);
			pull_refresh_list.setMode(Mode.BOTH);
			adapter = new ProductManagerFragmentForOnLineAdapter(getActivity(),
					mList, type,ProductManagerFragmentForOnLine.this);
			pull_refresh_list.setAdapter(adapter);
			pull_refresh_list.setOnRefreshListener(new OnRefreshListener2() {

				@Override
				public void onPullDownToRefresh(PullToRefreshBase refreshView) {
					page = 1;
					isRefresh = true;
					getProductListForOnLine(isRefresh, page, getActivity(),
							type);
				}

				
				@Override
				public void onPullUpToRefresh(PullToRefreshBase refreshView) {
					page++;
					isRefresh = false;
					getProductListForOnLine(isRefresh, page, getActivity(),
							type);
				}
			});
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}

	
	
	public void getData(int index, Context ctx) {
		switch (index) {
		case 0:// 在线
			if (mList.size() == 0) {
				getProductListForOnLine(isRefresh, page, ctx, 1);
			}
			break;
		case 1:// 即将下线
			if (mList.size() == 0) {
				getProductListForOnLine(isRefresh, page, ctx, 2);
			}
			break;
		case 2:// 已经下线
			if (mList.size() == 0) {
				getProductListForOnLine(isRefresh, page, ctx, 0);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 获取在线商品
	 */
	public void getProductListForOnLine(final boolean isRefresh, int page,
			Context ctx, int status) {
		HttpControl httpControl = new HttpControl();
		httpControl.getBuyerProductListForOnLine(page + "", Constants.PageSize,
				status, new HttpCallBackInterface() {
					@Override
					public void http_Success(Object obj) {
						pull_refresh_list.postDelayed(new Runnable() {
		                    @Override
		                    public void run() {
		                    	pull_refresh_list.onRefreshComplete();
		                    }
		            }, 100);
						BuyerProductManagerListBack bean = (BuyerProductManagerListBack) obj;
						if (isRefresh) {
							mList.clear();
							if(bean.getData().getItems()!=null && bean.getData().getItems().size()>0){
								tv_nodata.setVisibility(View.GONE);
								mList.addAll(bean.getData().getItems());
							}else{
								tv_nodata.setVisibility(View.VISIBLE);
							}
						} else {
							if(bean.getData().getItems().size()==0){
								Toast.makeText(getActivity(), "没有更多数据了...", 1000).show();							
							}
							mList.addAll(bean.getData().getItems());
						}
						adapter.notifyDataSetChanged();
					}

					@Override
					public void http_Fails(int error, String msg) {
						pull_refresh_list.onRefreshComplete();
						Toast.makeText(getActivity(), msg, 1000).show();

					}
				}, ctx, isRefresh, true);

	}

	@Override
	public void refreshOnLineProduct(int flag) {
		if(flag == 0){
			getProductListForOnLine(true, 1, getActivity(), 1);//获取在线商品
		}else if(flag == 1){
			getProductListForOnLine(true, 1, getActivity(), 2);//获取即将下限商品
		}else if(flag == 2){
			getProductListForOnLine(true, 1, getActivity(), 0);//获取下限商品
		}
	
	}

}
