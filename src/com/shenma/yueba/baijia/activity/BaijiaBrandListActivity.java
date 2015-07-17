package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.BrandInfoInfo;
import com.shenma.yueba.baijia.modle.BrandInfoInfoBean;
import com.shenma.yueba.baijia.modle.RequestBrandInfoInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;
import com.umeng.analytics.MobclickAgent;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-15 下午1:59:52  
 * 程序的简单说明  
 */

public class BaijiaBrandListActivity extends BaseActivityWithTopView{
View parenetView;
PullToRefreshGridView brandlist_layout_pullTorefreshgridview;
HttpControl httpControl;
int currPage=Constants.CURRPAGE_VALUE;
int pageSize=Constants.PAGESIZE_VALUE;
boolean showDialog=true;
int BrandId=-1;
List<BrandInfoInfo> object_list=new ArrayList<BrandInfoInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		parenetView=this.getLayoutInflater().inflate(R.layout.brandlist_layout, null);
		setContentView(parenetView);
		super.onCreate(savedInstanceState);
		httpControl=new HttpControl();
		BrandId=this.getIntent().getIntExtra("BRANDID", -1);
		MyApplication.getInstance().addActivity(this);
		if(BrandId<0)
		{
			MyApplication.getInstance().showMessage(BaijiaBrandListActivity.this, "数据错误");
			finish();
			return;
		}
		initView();
		requestFalshData();
	}
	
	void initView()
	{
		setTitle("品牌");
		FontManager.changeFonts(mContext,tv_top_title);
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BaijiaBrandListActivity.this.finish();
			}
		});
		
		brandlist_layout_pullTorefreshgridview=(PullToRefreshGridView)parenetView.findViewById(R.id.brandlist_layout_pullTorefreshgridview);
		brandlist_layout_pullTorefreshgridview.setAdapter(baseAdapter);
		brandlist_layout_pullTorefreshgridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				BrandInfoInfo brandInfoInfo=object_list.get(arg2);
				Intent intent=new Intent(BaijiaBrandListActivity.this,ApproveBuyerDetailsActivity.class);
				intent.putExtra("productID", brandInfoInfo.getProductId());
				startActivity(intent);
			}
		});
	}
	
	
	/*****
	 *请求加载数据
	 * ***/
	void requestData()
	{
		sendHttp(1,1);
	}
	
	/*****
	 *请求刷新数据
	 * ***/
	void requestFalshData()
	{
		currPage=Constants.CURRPAGE_VALUE;
		sendHttp(1,0);
	}
	
	
	
	
	
	BaseAdapter baseAdapter=new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if(convertView==null)
			{
				holder=new Holder();
				holder.imageview=new ImageView(BaijiaBrandListActivity.this);
				holder.imageview.setScaleType(ScaleType.FIT_CENTER);
				//AbsListView.LayoutParams params=new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,AbsListView.LayoutParams.MATCH_PARENT);
				AbsListView.LayoutParams params=new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,BaijiaBrandListActivity.this.getResources().getDimensionPixelSize(R.dimen.shop_main_width150_dimen));
				holder.imageview.setLayoutParams(params);
				holder.imageview.setBackgroundResource(R.drawable.back_background);
				//holder.imageview.setBackgroundResource(R.color.white);
				holder.imageview.setPadding(10, 5, 10, 5);
				convertView=holder.imageview;
				convertView.setTag(holder);
			}else
			{
				holder=(Holder)convertView.getTag();
			}
			BrandInfoInfo bean=object_list.get(position);
			initPic(ToolsUtil.getImage(ToolsUtil.nullToString(bean.getPic()), 320, 0), holder.imageview);
			return convertView;
		}
		
		@Override
		public long getItemId(int position) {
			
			return 0;
		}
		
		@Override
		public Object getItem(int arg0) {
			
			return null;
		}
		
		@Override
		public int getCount() {
			return object_list.size();
		}
	};
	
	class Holder
	{
	  ImageView imageview;
	}
	/*****
	 * 请求数据
	 * @param page int 当前页
	 * @param BrandId int 品牌id
	 * ***/
	void sendHttp(int page,final int type)
	{
		httpControl.getBaijiaBrandDetails(page, pageSize, BrandId, showDialog, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
			brandlist_layout_pullTorefreshgridview.onRefreshComplete();
			if(obj!=null && obj instanceof RequestBrandInfoInfoBean)
			{
				
				RequestBrandInfoInfoBean bean=(RequestBrandInfoInfoBean)obj;
				
				if (bean != null) {
					int totalPage = bean.getTotalpaged();
					if(currPage==1)
					{
						if(bean.getData()==null || bean.getData().getItems()==null)
					   {
							MyApplication.getInstance().showMessage(BaijiaBrandListActivity.this, "还没有订单");
							return;
					   }
					}
					currPage=bean.getPageindex();
					if (currPage >= totalPage) {
						brandlist_layout_pullTorefreshgridview.setMode(Mode.PULL_FROM_START);
					} else {
						brandlist_layout_pullTorefreshgridview.setMode(Mode.BOTH);
					}
					switch (type) {
					case 0:
						falshData(bean.getData().getItems());
						break;
					case 1:
						addData(bean.getData().getItems());
						break;
					}
				} else {
					MyApplication.getInstance().showMessage(BaijiaBrandListActivity.this, "没有任何数据");
				}
			}
			
		}
		
		@Override
		public void http_Fails(int error, String msg) {
			MyApplication.getInstance().showMessage(BaijiaBrandListActivity.this, msg);
			brandlist_layout_pullTorefreshgridview.onRefreshComplete();
		}
	}, BaijiaBrandListActivity.this);
	}
	
	/***
	 * 刷新viewpager数据
	 * ***/
	void falshData(List<BrandInfoInfo> bean) {
		currPage++;
		if(bean==null)
		{
			return;
		}
		if(bean!=null)
		{
			object_list.clear();
			object_list.addAll(bean);
		}
		brandlist_layout_pullTorefreshgridview.onRefreshComplete();
		if(baseAdapter!=null)
		{
			baseAdapter.notifyDataSetChanged();
		}
		showDialog=false;
	}
	
	
	/***
	 * 加载数据
	 * **/
	void addData(List<BrandInfoInfo> bean) {
		currPage++;
		if(bean==null)
		{
			return;
		}
		if(bean!=null)
		{
			object_list.addAll(bean);
		}
		brandlist_layout_pullTorefreshgridview.onRefreshComplete();
		object_list.add(null);
		if(baseAdapter!=null)
		{
			baseAdapter.notifyDataSetChanged();
		}
	}
	
	void initPic(String url,ImageView iv)
	{
		MyApplication.getInstance().getImageLoader().displayImage(url, iv, MyApplication.getInstance().getDisplayImageOptions());
	}
	
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		}
		public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		}
	
	
	
	@Override
	protected void onDestroy() {
		MyApplication.getInstance().removeActivity(this);//加入回退栈
		super.onDestroy();
	}
}