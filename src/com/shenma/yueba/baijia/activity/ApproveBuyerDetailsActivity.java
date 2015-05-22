package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnDrawListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.shenma.yueba.R;
import com.shenma.yueba.view.imageshow.CustomImageView;

/**
 * @author gyj
 * @version 创建时间：2015-5-20 下午6:02:36 程序的简单说明 定义认证买手 商品详情页
 */

public class ApproveBuyerDetailsActivity extends BaseActivityWithTopView {
	// 关注图片列表
	LinearLayout approvebuyerdetails_attentionvalue_linerlayout;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.approvebuyerdetails_layout);
		super.onCreate(savedInstanceState);
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
		
		approvebuyerdetails_attentionvalue_linerlayout = (LinearLayout) findViewById(R.id.approvebuyerdetails_attentionvalue_linerlayout);
		approvebuyerdetails_attentionvalue_linerlayout.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
			
			@Override
			public boolean onPreDraw() {
				
				if(approvebuyerdetails_attentionvalue_linerlayout.getWidth()>0)
				{
					addIconView();
					return true;
				}
				return false;
			}
		});
		
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
		new Thread() {
			public void run() {
				SystemClock.sleep(2000);
				for (int i = 0; i < 5; i++) {
					objectlist.add(null);
				}
				ApproveBuyerDetailsActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (customPagerAdapter != null) {
							setcurrItem(0);
							customPagerAdapter.notifyDataSetChanged();
							showloading.setVisibility(View.GONE);
							approvebuyerdetails_srcollview.setVisibility(View.VISIBLE);
							approvebuyerdetails_footer.setVisibility(View.VISIBLE);
						}
					}
				});
			};
		}.start();
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
	
	/***
	 * 添加关注人头像
	 * ***/
	void addIconView()
	{
		if(childWidth>0)
		{
			return;
		}
		int parentwidth=approvebuyerdetails_attentionvalue_linerlayout.getWidth();
		approvebuyerdetails_attentionvalue_linerlayout.removeAllViews();
		int size=0;
		if(10>maxcount)
		{
			size=maxcount;
		}else
		{
			size=10;
		}
		for(int i=0;i<size;i++)
		{
			CustomImageView civ=new CustomImageView(this);
			if(i==maxcount-1)
			{
				civ.setSrc(this, R.drawable.test003, 0);
			}else
			{
				civ.setSrc(this, R.drawable.test002, 0);
			}
			childWidth=(parentwidth)/maxcount;
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(childWidth, childWidth);
			params.leftMargin=2;
			approvebuyerdetails_attentionvalue_linerlayout.addView(civ, params);
		}
	}
}
