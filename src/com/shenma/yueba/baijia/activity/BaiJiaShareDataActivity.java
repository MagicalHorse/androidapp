package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
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
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FontManager;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-2 下午7:17:51  
 * 程序的简单说明  
 */

public class BaiJiaShareDataActivity extends BaseActivityWithTopView{
	PullToRefreshListView sharedata_layout_pulltorefreshlistview;
	BaiJiaShareDataAdapter adapter;
	List<BaiJiaShareInfoBean> bean_array=new ArrayList<BaiJiaShareInfoBean>();
	int currPage=Constants.CURRPAGE_VALUE;
	int pageSize=Constants.PAGESIZE_VALUE;
	boolean showDialog=true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		setContentView(R.layout.sharedata_layout);
		super.onCreate(savedInstanceState);
		initView();
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
					MyApplication.getInstance().showMessage(BaiJiaShareDataActivity.this, "请先选择分享的数据");
				}else
				{
					setResult(300, BaiJiaShareDataActivity.this.getIntent());
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
		//shop_main_layout_title_pulltorefreshscrollview.setRefreshing();
		sendHttp(currPage,1);
	}
	
	void requestFalshData()
	{
		//shop_main_layout_title_pulltorefreshscrollview.setRefreshing();
		sendHttp(1,0);
	}
	
	
	/***
     * 加载数据
     * @param page int 访问页
     * @param type int 0: 刷新  1:加载更多
     * **/
    void sendHttp(final int page,final int type)
	{
    	List<BaiJiaShareInfoBean> bean=new ArrayList<BaiJiaShareInfoBean>();
    	for(int i=0;i<10;i++)
    	{
    		BaiJiaShareInfoBean baiJiaShareInfoBean=new BaiJiaShareInfoBean();
    		baiJiaShareInfoBean.setName("name"+i);
    		baiJiaShareInfoBean.setPrice(1.00+i);
    		bean.add(baiJiaShareInfoBean);
    	}
    	switch (type) {
		case 0:
			falshData(bean);
			break;
		case 1:
			addData(bean);
			break;
		}
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
}
