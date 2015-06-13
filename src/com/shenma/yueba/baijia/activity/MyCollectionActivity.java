package com.shenma.yueba.baijia.activity;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListInfo;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListInfoBean;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListLikeUser;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListPic;
import com.shenma.yueba.baijia.modle.RequestMyFavoriteProductListInfoBean;
import com.shenma.yueba.baijia.view.PubuliuManager;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.umeng.socialize.utils.Log;

/**  
 * 我的收藏
 * @author gyj  
 * @version 创建时间：2015-5-20 上午11:14:57  
 * 程序的简单说明  
 */

public class MyCollectionActivity extends BaseActivityWithTopView{
    HttpControl httpCntrol=new HttpControl();
    int currPage=Constants.CURRPAGE_VALUE;
	int pageSize=Constants.PAGESIZE_VALUE;
	boolean showDialog=true;
	PullToRefreshScrollView shop_main_layout_title_pulltorefreshscrollview;
	LinearLayout pubuliy_left_linearlayout,pubuliy_right_linearlayout;
	int leftHeight;//左侧高度
	int rightHeight;//右侧高度
	RequestMyFavoriteProductListInfoBean bean;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		setContentView(R.layout.shop_main_layoutbak);
		super.onCreate(savedInstanceState);
		initView();
		requestFalshData();
	}
	
	void initView()
	{
		setTitle("收藏");
		FontManager.changeFonts(MyCollectionActivity.this,tv_top_title);
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				MyCollectionActivity.this.finish();
			}
		});
		
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyCollectionActivity.this.finish();
			}
		});
		LinearLayout pubuliu_layout_include=(LinearLayout)findViewById(R.id.pubuliu_layout_include);
		initPuBuView(pubuliu_layout_include);
		pubuliy_left_linearlayout=(LinearLayout)findViewById(R.id.pubuliy_left_linearlayout);
		pubuliy_right_linearlayout=(LinearLayout)findViewById(R.id.pubuliy_right_linearlayout);
		shop_main_layout_title_pulltorefreshscrollview=(PullToRefreshScrollView)findViewById(R.id.shop_main_layout_title_pulltorefreshscrollview);
		shop_main_layout_title_pulltorefreshscrollview.setMode(Mode.PULL_FROM_START);
		shop_main_layout_title_pulltorefreshscrollview.setOnRefreshListener(new OnRefreshListener2() {

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
		shop_main_layout_title_pulltorefreshscrollview.setRefreshing();
		sendHttp(1);
	}
	
	void requestFalshData()
	{
		shop_main_layout_title_pulltorefreshscrollview.setRefreshing();
		currPage=Constants.CURRPAGE_VALUE;
		sendHttp(0);
	}
	
	
	/****
	 * 添加
	 * ***/
	void addData(MyFavoriteProductListInfoBean bean)
	{
		currPage++;
		onaddData(bean.getItems());
		shop_main_layout_title_pulltorefreshscrollview.onRefreshComplete();
	}
	
	/****
	 * 刷新
	 * ***/
	void falshData(MyFavoriteProductListInfoBean bean)
	{
		currPage++;
		pubuliy_right_linearlayout.removeAllViews();
		pubuliy_left_linearlayout.removeAllViews();
		onResher(bean.getItems());
		shop_main_layout_title_pulltorefreshscrollview.onRefreshComplete();
	}
	
        
    @Override
    protected void onResume() {
        super.onResume();
    }
    
    /***
     * 加载数据
     * @param type int 0: 刷新  1:加载更多
     * **/
    void sendHttp(final int type)
	{
		httpCntrol.getMyFavoriteProductList(currPage, pageSize, showDialog, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				shop_main_layout_title_pulltorefreshscrollview.onRefreshComplete();
				if(obj!=null && obj instanceof RequestMyFavoriteProductListInfoBean)
				{
					bean=(RequestMyFavoriteProductListInfoBean)obj;
					if (bean != null) {
						if(currPage==1)
						{
							if(bean.getData()==null)
						   {
								MyApplication.getInstance().showMessage(MyCollectionActivity.this, "还没有信息");
								return;
						   }
						}
						int totalPage = bean.getData().getTotalpaged();
						if (currPage >= totalPage) {
							shop_main_layout_title_pulltorefreshscrollview.setMode(Mode.PULL_FROM_START);
							MyApplication.getInstance().showMessage(MyCollectionActivity.this, "最后一页数据");
						} else {
							shop_main_layout_title_pulltorefreshscrollview.setMode(Mode.BOTH);
						}
						switch (type) {
						case 0:
							falshData(bean.getData());
							break;
						case 1:
							addData(bean.getData());
							break;
						}
					} else {
						MyApplication.getInstance().showMessage(MyCollectionActivity.this, "没有任何数据");
					}
				}
				
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				shop_main_layout_title_pulltorefreshscrollview.onRefreshComplete();
				MyApplication.getInstance().showMessage(MyCollectionActivity.this, msg);
			}
		},MyCollectionActivity.this );
	}
    
    /*****
     * 初始化瀑布流信息
     * ***/
    void initPuBuView(View parent)
    {
    	LinearLayout pubuliy_left_linearlayout=(LinearLayout)parent.findViewById(R.id.pubuliy_left_linearlayout);
    	LinearLayout pubuliy_right_linearlayout=(LinearLayout)parent.findViewById(R.id.pubuliy_right_linearlayout);
    	
    }
    
    
    /****
	 * 设置刷新
	 * @param item List<MyFavoriteProductListInfo>
	 * ***/
	public void onResher(List<MyFavoriteProductListInfo> item)
	{
		leftHeight=0;
		rightHeight=0;
		pubuliy_left_linearlayout.removeAllViews();
		pubuliy_right_linearlayout.removeAllViews();
		addItem(item);
	}
	
	
	/****
	 * 加载数据
	 * @param item List<MyFavoriteProductListInfo>
	 * ****/
	public void onaddData(List<MyFavoriteProductListInfo> item)
	{
		addItem(item);
	}
	
	
	 /*******
     * 设置 瀑布流的 高度
     * 
     * *****/
    void addItem(List<MyFavoriteProductListInfo> item)
    {
    	if(item!=null)
    	{
    		for(int i=0;i<item.size();i++)
    		{
    			MyFavoriteProductListInfo bean=item.get(i);
    			int witdh=pubuliy_left_linearlayout.getWidth();
    			MyFavoriteProductListPic myFavoriteProductListPic=bean.getPic();
    			int height=(int)(witdh*myFavoriteProductListPic.getRatio());
    			View parentview=LinearLayout.inflate(MyCollectionActivity.this, R.layout.pubuliu_item_layout, null);
    			//价格
    			TextView pubuliu_item_layout_pricevalue_textview=(TextView)parentview.findViewById(R.id.pubuliu_item_layout_pricevalue_textview);
    			pubuliu_item_layout_pricevalue_textview.setText(bean.getPrice()+"");
    			//商品名称
    			TextView pubuliu_item_layout_name_textview=(TextView)parentview.findViewById(R.id.pubuliu_item_layout_name_textview);
    			pubuliu_item_layout_name_textview.setText(bean.getName());
    			//收藏
    			TextView pubuliu_item_layout_like_textview=(TextView)parentview.findViewById(R.id.pubuliu_item_layout_like_textview);
    			
    			Drawable drawable= getResources().getDrawable(R.drawable.collect_backgroud);
    			/// 这一步必须要做,否则不会显示.
    			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
    			pubuliu_item_layout_like_textview.setCompoundDrawables(drawable,null,null,null);

    			pubuliu_item_layout_like_textview.setOnClickListener(onClickListener);
    			if(bean.getLikeUser()!=null)
    			{
    				MyFavoriteProductListLikeUser likeuser=bean.getLikeUser();
    				pubuliu_item_layout_like_textview.setSelected(likeuser.isIsLike());
    				pubuliu_item_layout_like_textview.setText(likeuser.getCount()+"");
    				pubuliu_item_layout_like_textview.setTag(bean);
    			}
    			ImageView iv=(ImageView)parentview.findViewById(R.id.pubuliu_item_layout_imageview);
    			if(height>0)
    			{
    				Log.i("TAG", "height="+height +" witdh="+witdh   +"ration="+myFavoriteProductListPic.getRatio());
        			
    				iv.getLayoutParams().height=height;
    			}else
    			{
    				height=witdh;
    				Log.i("TAG", "height="+height +" witdh="+witdh   +"ration="+myFavoriteProductListPic.getRatio());
        			
    				iv.getLayoutParams().height=height;
    			}
    			iv.setTag(bean);
    			iv.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						MyApplication.getInstance().showMessage(MyCollectionActivity.this, "点击了");
					}
				});
    			iv.setImageResource(R.drawable.default_pic);
    			iv.setScaleType(ScaleType.FIT_XY);
    			android.util.Log.i("TAG", "leftHeight="+leftHeight+"   rightHeight="+rightHeight);
    		    //根据 左右高度判断
    			if(leftHeight<=rightHeight)
    			{
    				leftHeight+=height;
    				pubuliy_left_linearlayout.addView(parentview, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    			}else
    			{
    				rightHeight+=height;
    				pubuliy_right_linearlayout.addView(parentview, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    			}
    			ToolsUtil.setFontStyle(MyCollectionActivity.this, parentview, R.id.pubuliu_item_layout_pricevalue_textview,R.id.pubuliu_item_layout_name_textview,R.id.pubuliu_item_layout_like_textview,R.id.pubuliu_item_layout_price_textview);
    			MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.getImage(ToolsUtil.nullToString(myFavoriteProductListPic.getPic()), 320, 0), iv, MyApplication.getInstance().getDisplayImageOptions());
    			//MyApplication.getInstance().getImageLoader().displayImage(myFavoriteProductListPic.getPic(), iv, MyApplication.getInstance().getDisplayImageOptions());
    		}
    	}
    }
    
    OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.pubuliu_item_layout_like_textview:
				if(v.getTag()!=null && v.getTag() instanceof MyFavoriteProductListInfo)
				{
					MyFavoriteProductListInfo  bean=(MyFavoriteProductListInfo)v.getTag();
					MyFavoriteProductListLikeUser myFavoriteProductListLikeUser=bean.getLikeUser();
					if(myFavoriteProductListLikeUser.isIsLike())
					{
						submitAttention(0,bean,v);
					}else
					{
						submitAttention(1,bean,v);
					}
					
				}
				break;
			}
		}
	};
	
	/****
	 * 提交收藏与取消收藏商品
	 * @param type int   0表示取消收藏   1表示收藏
	 * @param brandCityWideInfo BrandCityWideInfo  商品对象
	 * @param v View  商品对象
	 * **/
	void submitAttention(final int Status,final MyFavoriteProductListInfo  myFavoriteProductListInfo,final View v)
	{
		httpCntrol.setFavor(myFavoriteProductListInfo.getId(), Status, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				if(v!=null && v instanceof TextView)
				{
					MyFavoriteProductListLikeUser myFavoriteProductListLikeUser=myFavoriteProductListInfo.getLikeUser();
					if(myFavoriteProductListLikeUser!=null)
					{
						switch(Status)
						{
						case 0:
							if(bean.getData().getItems().contains(myFavoriteProductListInfo))
							{
								bean.getData().getItems().remove(myFavoriteProductListInfo);
								v.setVisibility(View.GONE);
							}
							v.setSelected(false);
							break;
						case 1:
							v.setSelected(true);
							break;
						}
					}
					
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(MyCollectionActivity.this, msg);
			}
		}, MyCollectionActivity.this);
	}
   
}
