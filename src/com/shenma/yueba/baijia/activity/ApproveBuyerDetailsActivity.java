package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.UserIconAdapter;
import com.shenma.yueba.baijia.modle.LikeUsersInfoBean;
import com.shenma.yueba.baijia.modle.ProductPicInfoBean;
import com.shenma.yueba.baijia.modle.ProductsInfoBean;
import com.shenma.yueba.baijia.modle.RequestProductDetailsInfoBean;
import com.shenma.yueba.baijia.modle.UsersInfoBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.view.MyGridView;
import com.shenma.yueba.view.RoundImageView;

/**
 * @author gyj
 * @version 创建时间：2015-5-20 下午6:02:36 程序的简单说明 定义认证买手 商品详情页
 */

public class ApproveBuyerDetailsActivity extends BaseActivityWithTopView {
	// 滚动图片
	ViewPager appprovebuyer_viewpager;
	//滚动图像下面的 原点
	LinearLayout appprovebuyer_viewpager_footer_linerlayout;
	//加载视图
	LinearLayout showloading;
	//滚动视图 主要内容
	ScrollView approvebuyerdetails_srcollview;
	//底部购物车父视图
	RelativeLayout approvebuyerdetails_footer;
	int childWidth = 0;
	int maxcount = 8;
	CustomPagerAdapter customPagerAdapter;
	List<Object> objectlist = new ArrayList<Object>();
	MyGridView myGirdView;
	//商品id;
	int  productID=-1;
	HttpControl httpControl=new HttpControl();
	//商品信息对象
	ProductsInfoBean Data;
	//头像
	RoundImageView approvebuyerdetails_icon_imageview;
	//私聊
	LinearLayout approvebuyerdetails_layout_siliao_linerlayout;
	//收藏
	LinearLayout approvebuyerdetails_layout_shoucang_linerlayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.approvebuyerdetails_layout);
		super.onCreate(savedInstanceState);
		if(this.getIntent().getSerializableExtra("data")==null)
		{
			MyApplication.getInstance().showMessage(this, "数据错误,请重试");
			this.finish();
			return;
		}
		ProductPicInfoBean productPicInfoBean=(ProductPicInfoBean)this.getIntent().getSerializableExtra("data");
		productID=productPicInfoBean.getId();
		initViews();
		initData();
	}
	
	@SuppressLint("NewApi")
	private void initViews() {
		setTitle("店铺商品详情");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ApproveBuyerDetailsActivity.this.finish();
			}
		});
		approvebuyerdetails_footer=(RelativeLayout)findViewById(R.id.approvebuyerdetails_footer);
		approvebuyerdetails_srcollview=(ScrollView)findViewById(R.id.approvebuyerdetails_srcollview);
		showloading=(LinearLayout)findViewById(R.id.showloading);
		appprovebuyer_viewpager_footer_linerlayout=(LinearLayout)findViewById(R.id.appprovebuyer_viewpager_footer_linerlayout);
		appprovebuyer_viewpager = (ViewPager) findViewById(R.id.appprovebuyer_viewpager);
		customPagerAdapter = new CustomPagerAdapter(objectlist);
		appprovebuyer_viewpager.setAdapter(customPagerAdapter);
		myGirdView=(MyGridView)findViewById(R.id.approvebuyerdetails_attentionvalue_gridview);
		
		appprovebuyer_viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
				setcurrItem(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
				
			}
		});
		//设置关注人
		List<UsersInfoBean> bean=new ArrayList<UsersInfoBean>();
		myGirdView.setAdapter(new UserIconAdapter(bean, ApproveBuyerDetailsActivity.this, myGirdView));
		approvebuyerdetails_icon_imageview=(RoundImageView)findViewById(R.id.approvebuyerdetails_icon_imageview);
		approvebuyerdetails_icon_imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		
		approvebuyerdetails_layout_siliao_linerlayout=(LinearLayout)findViewById(R.id.approvebuyerdetails_layout_siliao_linerlayout);
		approvebuyerdetails_layout_shoucang_linerlayout=(LinearLayout)findViewById(R.id.approvebuyerdetails_layout_shoucang_linerlayout);
	}

	/***
	 * 设置文本值
	 * @param res int 视图id
	 * @param str 要显示内容  null 不进行负值
	 * ***/
	void setdataValue(int res,String str)
	{
		View v=findViewById(res);
		if(v!=null && v instanceof TextView)
		{
			if(str!=null)
			{
				((TextView)v).setText(str);
			}
			FontManager.changeFonts(this, ((TextView)v));
		}
	}
	
	class CustomPagerAdapter extends PagerAdapter {
		List<Object> objectlist = new ArrayList<Object>();
        List<View> viewlist=new ArrayList<View>();
		CustomPagerAdapter(List<Object> objectlist) {
			this.objectlist = objectlist;
		}

		@Override
		public int getCount() {

			return objectlist.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			ImageView v = new ImageView(ApproveBuyerDetailsActivity.this);
			v.setScaleType(ScaleType.FIT_XY);
			v.setImageResource(R.drawable.icon11);
			viewlist.add(v);
			container.addView(v, 0);
			return v;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView(viewlist.get(position));
		}
	}

	// 加载数据
	public void initData() {
		httpControl.getMyBuyerProductDetails(productID, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				if(obj!=null && obj instanceof RequestProductDetailsInfoBean)
				{
					RequestProductDetailsInfoBean bean=(RequestProductDetailsInfoBean)obj;
					if(bean.getData()==null)
					{
						http_Fails(500, "数据异常null");
						return;
					}
					Data=bean.getData();
					setDatValue();
				}
				
			}
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(ApproveBuyerDetailsActivity.this, msg);
				finish();
			}
		}, ApproveBuyerDetailsActivity.this);
	}
	
	
	/*****
	 * 设置数据
	 * ****/
	void setDatValue()
	{
		setdataValue(R.id.tv_top_title, null);
		//地址
		String address=Data.getBuyerAddress();
		//买手头像
		String usericon=Data.getBuyerLogo();
		//买手昵称
		String username=Data.getBuyerName();
		//商品发布时间
		String creattime=Data.getCreateTime();
		//关注人列表
		LikeUsersInfoBean usersInfoList=Data.getLikeUsers();
		//价格
		double price=Data.getPrice();
		//商品描述
		String desc=Data.getProductName();
		initPic(usericon, approvebuyerdetails_icon_imageview);
		
		//设置昵称
		setdataValue(R.id.approvebuyerdetails_name_textview,username);
		//成交
		setdataValue(R.id.appprovebuyerdetails_text1, null);
		//成交额
		setdataValue(R.id.appprovebuyerdetails_textvalue1_textview, "");
		//好评
		setdataValue(R.id.appprovebuyerdetails_text2, null);
		//好评值
		setdataValue(R.id.appprovebuyerdetails_textvalue1_textview, "");
		//金额
		setdataValue(R.id.approvebuyerdetails_price_textview, "￥"+Double.toString(price));
		//商品名称
		setdataValue(R.id.approvebuyerdetails_producename_textview,desc);
		//提货提醒
		setdataValue(R.id.approvebuyerdatails_layout_desc1_textview,null);
		//自提地点
		setdataValue(R.id.approvebuyerdetails_address_textview,null);
		//自提地址
		setdataValue(R.id.approvebuyerdetails_addressvalue_textview,null);
		//收藏
		setdataValue(R.id.approvebuyerdetails_layout_shoucang_linerlayout_textview,null);
		//私聊
		setdataValue(R.id.approvebuyerdetails_layout_siliao_linerlayout_textview,null);
	}
	
	
	/***
	 * 添加原点
	 * @param size int 原点的个数
	 * @param value int 当前选中的tab
	 * **/
	void addTabImageView(int size,int value)
	{
		appprovebuyer_viewpager_footer_linerlayout.removeAllViews();
		for(int i=0;i<size;i++)
		{
			View v=new View(ApproveBuyerDetailsActivity.this);
			v.setBackgroundResource(R.drawable.tabround_background);
			int width=(int)ApproveBuyerDetailsActivity.this.getResources().getDimension(R.dimen.shop_main_lineheight8_dimen);
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(width, width);
			params.leftMargin=(int)ApproveBuyerDetailsActivity.this.getResources().getDimension(R.dimen.shop_main_width3_dimen);
			appprovebuyer_viewpager_footer_linerlayout.addView(v, params);
			if(i==value)
			{
				v.setSelected(true);
			}else
			{
				v.setSelected(false);
			}
		}
	}
	
	/****
	 * 设置当前显示的 item
	 * **/
	void setcurrItem(int i)
	{
		appprovebuyer_viewpager.setCurrentItem(i);
		addTabImageView(objectlist.size(), i);
	}
	
	
	/****
	 * 加载图片
	 * */
	void initPic(final String url, final ImageView iv) {
		Log.i("TAG", "URL:"+url);
		MyApplication.getInstance().getImageLoader().displayImage(url, iv, MyApplication.getInstance().getDisplayImageOptions());
	}
}
