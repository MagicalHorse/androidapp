package com.shenma.yueba.baijia.fragment;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.ShopMainActivity;
import com.shenma.yueba.baijia.adapter.SearchBuyerAdapter;
import com.shenma.yueba.baijia.modle.BrandSearchInfo;
import com.shenma.yueba.baijia.modle.BrandSearchInfoBean;
import com.shenma.yueba.baijia.modle.RequestBrandSearchInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

/**
 * 买手
 * @author a
 *
 */
public class BuyerFragment extends BaseFragment{

	private SearchBuyerAdapter searchBuyerAdapter;
	private List<BrandSearchInfo> mList = new ArrayList<BrandSearchInfo>();
	private View view;
	private PullToRefreshListView pull_refresh_list;
	LinearLayout showloading_layout_view;
	HttpControl httpControl=new HttpControl();
	int state=1;
	String key="";
	int currPage=0;
	boolean ishowStatus=false;
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
			//requestFalshData();
		}
		
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}
	
	void initPullView()
	{
		pull_refresh_list=(PullToRefreshListView)view.findViewById(R.id.pull_refresh_list);
		showloading_layout_view=(LinearLayout)view.findViewById(R.id.showloading_layout_view);
		pull_refresh_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if((arg2-1)>=0 && (arg2-1)<mList.size())
				{
					BrandSearchInfo brandSearchInfo=mList.get(arg2-1);
					Intent intent=new Intent(getActivity(),ShopMainActivity.class);
					intent.putExtra("DATA", brandSearchInfo.getId());
					getActivity().startActivity(intent);
				}
				
			}
		});
		searchBuyerAdapter=new SearchBuyerAdapter(getActivity(), mList);
		pull_refresh_list.setAdapter(searchBuyerAdapter);
		pull_refresh_list.setMode(Mode.PULL_FROM_START);
		ToolsUtil.initPullResfresh(pull_refresh_list, getActivity());
		 
		pull_refresh_list.setOnRefreshListener(new OnRefreshListener2() {

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
	
	/***
	 * 请求
	 * **/
	 void requestData()
	{
		 if(key.equals(""))
		 {
			 MyApplication.getInstance().showMessage(getActivity(), "请输入关键字");
				pull_refresh_list.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    	pull_refresh_list.onRefreshComplete();
                    }
            }, 100);
			 return;
		 }
		sendHttp(1,currPage, state, key);
	}
	
	/***
	 * 刷新
	 * **/
	void requestFalshData()
	{
		if(key.equals(""))
		 {
			 MyApplication.getInstance().showMessage(getActivity(), "请输入关键字");
			 pull_refresh_list.onRefreshComplete();
			 return;
		 }
		sendHttp(0,1, state, key);
	}
	
	/***
	 * 加载更多数据
	 * **/
	void addData(List<BrandSearchInfo> item)
	{
		ishowStatus=false;
		pull_refresh_list.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				pull_refresh_list.onRefreshComplete();
			}
		}, 100);
		currPage++;
		if(item!=null)
		{
			mList.addAll(item);
		}
		
		showloading_layout_view.setVisibility(View.GONE);
		searchBuyerAdapter.notifyDataSetChanged();
		//ListUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
	}
	
	/***
	 * 刷新数据
	 * **/
	void falshData(List<BrandSearchInfo> item)
	{
		ishowStatus=false;
		pull_refresh_list.postDelayed(new Runnable() {
            @Override
            public void run() {
            	pull_refresh_list.onRefreshComplete();
            }
    }, 100);
		currPage++;
		mList.clear();
		if(item!=null)
		{
			mList.addAll(item);
		}
		
		showloading_layout_view.setVisibility(View.GONE);
		searchBuyerAdapter.notifyDataSetChanged();
		
		//ListUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		
		
	}
	
	/******
	 * 访问网络
	 * @param type int 类型 0：刷新  1： 加载
	 * @param page int 当前页
	 * @param state  int 0: 搜索品牌列表, 1:搜索买手列表
	 * @param key  string类型，关键字
	 * ***/
	void sendHttp(final int type ,final int page,int state,String key)
	{
		ToolsUtil.showNoDataView(getActivity(),view ,false);
		httpControl.searchbrandList(page,Constants.PAGESIZE_VALUE, state,key,ishowStatus, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				ToolsUtil.pullResfresh(pull_refresh_list);
				currPage=page;
				ishowStatus=false;
				if(obj!=null)
				{
					RequestBrandSearchInfoBean bean=(RequestBrandSearchInfoBean)obj;
					BrandSearchInfoBean brandSearchInfoBean=bean.getData();
					if(brandSearchInfoBean==null || brandSearchInfoBean.getItems()==null || brandSearchInfoBean.getItems().size()==0)
					{
						if(page==1)
						{
							pull_refresh_list.setMode(Mode.PULL_FROM_START);
							ToolsUtil.showNoDataView(getActivity(),view ,true);
						}else
						{
							MyApplication.getInstance().showMessage(getActivity(), getActivity().getResources().getString(R.string.lastpagedata_str));
						}
					}else
					{
						if(page==1)
						{
							pull_refresh_list.setMode(Mode.BOTH);
						}
						int totalPage = bean.getData().getTotalpaged();
						if (currPage >= totalPage) {
							//MyApplication.getInstance().showMessage(getActivity(), getActivity().getResources().getString(R.string.lastpagedata_str));
							pull_refresh_list.setMode(Mode.BOTH);
						} else {
							pull_refresh_list.setMode(Mode.BOTH);
						}
						switch(type)
						{
						case 0:
							falshData(brandSearchInfoBean.getItems());
							break;
						case 1:
							addData(brandSearchInfoBean.getItems());
							break;
						}
						
					}
				} else {
					if(page==1)
					{
						ToolsUtil.showNoDataView(getActivity(),view, true);
					}
					MyApplication.getInstance().showMessage(getActivity(), "没有任何数据");
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(getActivity(), msg);
				ToolsUtil.pullResfresh(pull_refresh_list);
			}
		}, getActivity());
	}
	
	public void searchData(String key)
	{
		if(key==null || key.equals(""))
		{
			return;
		}
		ishowStatus=true;
		searchBuyerAdapter=new SearchBuyerAdapter(getActivity(), mList);
		pull_refresh_list.setAdapter(searchBuyerAdapter);
		this.key=key;
		mList.clear();
		requestFalshData();
	}
}
