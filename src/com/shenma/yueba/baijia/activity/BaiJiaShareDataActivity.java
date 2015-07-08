package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.BaiJiaShareDataAdapter;
import com.shenma.yueba.baijia.modle.BaiJiaShareInfoBean;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListInfo;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListInfoBean;
import com.shenma.yueba.baijia.modle.RequestMyFavoriteProductListInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.umeng.socialize.utils.Log;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-2 下午7:17:51  
 * 程序的简单说明  
 */

public class BaiJiaShareDataActivity extends BaseActivityWithTopView{
	public static final String LINK="link";
	public static final String COLLECT="collect";
	PullToRefreshListView sharedata_layout_pulltorefreshlistview;
	BaiJiaShareDataAdapter adapter;
	List<BaiJiaShareInfoBean> bean_array=new ArrayList<BaiJiaShareInfoBean>();
	int currPage=Constants.CURRPAGE_VALUE;
	int pageSize=Constants.PAGESIZE_VALUE;
	boolean showDialog=true;
	String type=null;
	HttpControl httpControl=new HttpControl();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		setContentView(R.layout.sharedata_layout);
		super.onCreate(savedInstanceState);
		initView();
		type=this.getIntent().getStringExtra("TYPE");
		if(type.equals(LINK))
		{
			
		}else if(type.equals(COLLECT))
		{
			
		}else
		{
			finish();
			return;
		}
		requestFalshData();
	}
	
	void initView()
	{   
		setTitle("分享");
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		TextView tv_top_right=(TextView)findViewById(R.id.tv_top_right);
		tv_top_right.setVisibility(View.VISIBLE);
		tv_top_right.setText("完成");
		tv_top_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//获取已经选择的对象
				List<BaiJiaShareInfoBean>  check_list=getCheckList();
				if(check_list==null || check_list.size()==0)
				{
					MyApplication.getInstance().showMessage(BaiJiaShareDataActivity.this, "没人任何选中项");
				}else
				{
					Intent result_intent=BaiJiaShareDataActivity.this.getIntent();
					result_intent.putParcelableArrayListExtra("RESULT_DATA", (ArrayList<? extends Parcelable>) check_list);
					setResult(300, result_intent);
					finish();
				}
				
			}
		});
		FontManager.changeFonts(this, tv_top_right,tv_top_title);
		sharedata_layout_pulltorefreshlistview=(PullToRefreshListView)findViewById(R.id.sharedata_layout_pulltorefreshlistview);
		adapter=new BaiJiaShareDataAdapter(this,bean_array);
		sharedata_layout_pulltorefreshlistview.setAdapter(adapter);
		
		
		sharedata_layout_pulltorefreshlistview.setMode(Mode.BOTH);
		sharedata_layout_pulltorefreshlistview.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				
				requestFalshData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				
				requestData();
			}
		});
	}
	
	void requestData()
	{
		if(type.equals(COLLECT))
		{
			sendCollectHttp(currPage,1);
		}else if(type.equals(LINK))
		{
			sendLinkeHttp(currPage, 1);
		}
		
	}
	
	void requestFalshData()
	{
		if(type.equals(COLLECT))
		{
			sendCollectHttp(1,0);
		}else if(type.equals(LINK))
		{
			sendLinkeHttp(1, 0);
		}
	}
	
	
	/***
     * 加载数据 收藏数据（我收藏的商品）
     * @param page int 访问页
     * @param type int 0: 刷新  1:加载更多
     * **/
    void sendCollectHttp(final int page,final int type)
	{
    	ToolsUtil.showNoDataView(BaiJiaShareDataActivity.this, false);
    	Log.i("TAG", "currpage="+page+"   pagesize="+pageSize);
    	httpControl.getMyFavoriteProductList(page, pageSize, showDialog, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				sucess(page,type,obj);
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				sharedata_layout_pulltorefreshlistview.onRefreshComplete();
				MyApplication.getInstance().showMessage(BaiJiaShareDataActivity.this, msg);
			}
		},BaiJiaShareDataActivity.this );
    	
    	
    	
	}
    
    /****
	 * 添加
	 * ***/
	void addData(List<BaiJiaShareInfoBean> bean)
	{
		
		currPage++;
		if(bean!=null)
		{
		  bean_array.addAll(bean);
		}
		adapter.notifyDataSetChanged();
		sharedata_layout_pulltorefreshlistview.onRefreshComplete();
	}
	
	/****
	 * 刷新
	 * ***/
	void falshData(List<BaiJiaShareInfoBean> bean)
	{
		currPage++;
		bean_array.clear();
		if(bean!=null)
		{
			bean_array.addAll(bean);
		}
		adapter=new BaiJiaShareDataAdapter(this,bean_array);
		sharedata_layout_pulltorefreshlistview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		sharedata_layout_pulltorefreshlistview.onRefreshComplete();
	}
	
	/**
	 * 获取选中的对象
	 * ***/
	List<BaiJiaShareInfoBean> getCheckList()
	{
		List<BaiJiaShareInfoBean> check_list=new ArrayList<BaiJiaShareInfoBean>();
		for(int i=0;i<bean_array.size();i++)
		{
			if(bean_array.get(i).isIscheck())
			{
				check_list.add(bean_array.get(i));
			}
		}
		return check_list;
	}
	
	
	
	/***
     * 加载数据链接数据（全部商品）
     * @param page int 访问页
     * @param type int 0: 刷新  1:加载更多
     * **/
    void sendLinkeHttp(final int page,final int type)
	{
    	int userID=Integer.parseInt(SharedUtil.getStringPerfernece(this, SharedUtil.user_id));
    	ToolsUtil.showNoDataView(BaiJiaShareDataActivity.this, false);
		httpControl.GetBaijiaGetUserProductList(userID,page, pageSize, 0, showDialog, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				sharedata_layout_pulltorefreshlistview.onRefreshComplete();
				sucess(page,type,obj);
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				sharedata_layout_pulltorefreshlistview.onRefreshComplete();
				MyApplication.getInstance().showMessage(BaiJiaShareDataActivity.this, msg);
			}
		}, BaiJiaShareDataActivity.this);
	}
    
    void sucess(final int page,final int type,Object obj)
    {
    	currPage=page;
		sharedata_layout_pulltorefreshlistview.onRefreshComplete();
		showDialog=false;
		if(obj!=null && obj instanceof RequestMyFavoriteProductListInfoBean)
		{
			RequestMyFavoriteProductListInfoBean bean=(RequestMyFavoriteProductListInfoBean)obj;
			if (bean != null) {
				if(bean.getData()==null || bean.getData().getItems()==null || bean.getData().getItems().size()==0)
				{
					if(page==1)
					{
						sharedata_layout_pulltorefreshlistview.setMode(Mode.PULL_FROM_START);
						ToolsUtil.showNoDataView(BaiJiaShareDataActivity.this, true);
					}
				}else
				{
					int totalPage = bean.getData().getTotalpaged();
					if (currPage >= totalPage) {
						sharedata_layout_pulltorefreshlistview.setMode(Mode.PULL_FROM_START);
						//MyApplication.getInstance().showMessage(BaiJiaShareDataActivity.this, BaiJiaShareDataActivity.this.getResources().getString(R.string.lastpagedata_str));
					} else {
						sharedata_layout_pulltorefreshlistview.setMode(Mode.BOTH);
					}
					List<BaiJiaShareInfoBean> sharebean_list=new ArrayList<BaiJiaShareInfoBean>();
					for(int i=0;i<bean.getData().getItems().size();i++)
					{
						BaiJiaShareInfoBean sharebean=new BaiJiaShareInfoBean();
						MyFavoriteProductListInfo infobean=bean.getData().getItems().get(i);
						sharebean.setId(infobean.getId());
						if(infobean.getPic()!=null)
						{
							sharebean.setLogo(infobean.getPic().getPic());
						}
						sharebean.setName(infobean.getName());
						sharebean.setPrice(infobean.getPrice());
						sharebean_list.add(sharebean);
					}
					switch (type) {
					case 0:
						falshData(sharebean_list);
						break;
					case 1:
						addData(sharebean_list);
						break;
					}
				}
			} else {
				if(page==1)
				{
					ToolsUtil.showNoDataView(BaiJiaShareDataActivity.this, true);
				}
				MyApplication.getInstance().showMessage(BaiJiaShareDataActivity.this, "没有任何数据");
			}
		}
    }
}
