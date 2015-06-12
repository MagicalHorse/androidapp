package com.shenma.yueba.baijia.activity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shenma.yueba.ChatActivity;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.UserIconAdapter;
import com.shenma.yueba.baijia.modle.LikeUsersInfoBean;
import com.shenma.yueba.baijia.modle.ProductsDetailsInfoBean;
import com.shenma.yueba.baijia.modle.ProductsDetailsPromotion;
import com.shenma.yueba.baijia.modle.RequestProductDetailsInfoBean;
import com.shenma.yueba.baijia.modle.UsersInfoBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.FixedSpeedScroller;
import com.shenma.yueba.view.MyGridView;
import com.shenma.yueba.view.RoundImageView;

/**
 * @author gyj
 * @version 创建时间：2015-5-20 下午6:02:36 程序的简单说明 定义认证买手 商品详情页
 */

public class ApproveBuyerDetailsActivity extends BaseActivityWithTopView implements OnClickListener{
	// 当前选中的id （ViewPager选中的id）
	int currid = -1;
	// 滚动图片
	ViewPager appprovebuyer_viewpager;
	// 滚动图像下面的 原点
	LinearLayout appprovebuyer_viewpager_footer_linerlayout;
	// 加载视图
	LinearLayout showloading;
	// 滚动视图 主要内容
	ScrollView approvebuyerdetails_srcollview;
	// 底部购物车父视图
	RelativeLayout approvebuyerdetails_footer;
	TextView approvebuyerdetails_closeingtime_textview;//打烊时间
	TextView approvebuyerdetails_closeinginfo_textview;//打烊信息
	int childWidth = 0;
	int maxcount = 8;
	CustomPagerAdapter customPagerAdapter;
	MyGridView myGirdView;
	// 商品id;
	int productID = -1;
	HttpControl httpControl = new HttpControl();
	// 商品信息对象
	ProductsDetailsInfoBean Data;
	// 头像
	RoundImageView approvebuyerdetails_icon_imageview;
	// 私聊
	LinearLayout approvebuyerdetails_layout_siliao_linerlayout;
	// 收藏
	LinearLayout approvebuyerdetails_layout_shoucang_linerlayout;
	// 加入购物车
	Button approvebuyer_addcartbutton;
	// 直接购买
	Button approvebuyerbuybutton;
	List<View> viewlist = new ArrayList<View>();
	LinearLayout approvebuyerdetails_closeingtime_linearlayout;
	Timer timer;
	RequestProductDetailsInfoBean bean;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.approvebuyerdetails_layout);
		super.onCreate(savedInstanceState);
		productID=this.getIntent().getIntExtra("productID", -1);
		if(productID<0)
		{
			MyApplication.getInstance().showMessage(this, "数据错误,请重试");
			this.finish();
			return;
		}
		//productID = 12947;
		initViews();
		initData();
		setFont();
	}

	private void initViews() {
		setTitle("");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ApproveBuyerDetailsActivity.this.finish();
			}
		});
		//活动父视图
		approvebuyerdetails_closeingtime_linearlayout=(LinearLayout)findViewById(R.id.approvebuyerdetails_closeingtime_linearlayout);
		//打烊时间
		approvebuyerdetails_closeingtime_textview=(TextView)findViewById(R.id.approvebuyerdetails_closeingtime_textview);
		//打烊信息
		approvebuyerdetails_closeinginfo_textview=(TextView)findViewById(R.id.approvebuyerdetails_closeinginfo_textview);
		approvebuyerdetails_footer = (RelativeLayout) findViewById(R.id.approvebuyerdetails_footer);
		approvebuyerdetails_srcollview = (ScrollView) findViewById(R.id.approvebuyerdetails_srcollview);
		showloading = (LinearLayout) findViewById(R.id.showloading);
		appprovebuyer_viewpager_footer_linerlayout = (LinearLayout) findViewById(R.id.appprovebuyer_viewpager_footer_linerlayout);
		appprovebuyer_viewpager = (ViewPager) findViewById(R.id.appprovebuyer_viewpager);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = width / 2;
		appprovebuyer_viewpager
				.setLayoutParams(new RelativeLayout.LayoutParams(width, height));

		appprovebuyer_viewpager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					stopTimerToViewPager();
					break;
				case MotionEvent.ACTION_UP:
					startTimeToViewPager();
					break;
				}
				return false;
			}
		});

		myGirdView = (MyGridView) findViewById(R.id.approvebuyerdetails_attentionvalue_gridview);

		appprovebuyer_viewpager
				.setOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int arg0) {
						currid = arg0;
						setcurrItem(arg0);
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {

					}
				});

		approvebuyerdetails_icon_imageview = (RoundImageView) findViewById(R.id.approvebuyerdetails_icon_imageview);
		approvebuyerdetails_icon_imageview.setOnClickListener(this);

		approvebuyerdetails_layout_siliao_linerlayout = (LinearLayout) findViewById(R.id.approvebuyerdetails_layout_siliao_linerlayout);
		approvebuyerdetails_layout_siliao_linerlayout.setOnClickListener(this);
		approvebuyerdetails_layout_shoucang_linerlayout = (LinearLayout) findViewById(R.id.approvebuyerdetails_layout_shoucang_linerlayout);

		approvebuyer_addcartbutton = (Button) findViewById(R.id.approvebuyer_addcartbutton);
		approvebuyerbuybutton = (Button) findViewById(R.id.approvebuyerbuybutton);
		approvebuyerbuybutton.setOnClickListener(this);
	}

	
	void startActivity()
	{
		Intent intent=new Intent(ApproveBuyerDetailsActivity.this,ChatActivity.class);
		intent.putExtra("DATA", bean);
		startActivity(intent);
	}
	
	/***
	 * 设置文本值
	 * 
	 * @param res
	 *            int 视图id
	 * @param str
	 *            要显示内容 null 不进行负值
	 * ***/
	void setdataValue(int res, String str) {
		View v = findViewById(res);
		if (v != null && v instanceof TextView) {
			if (str != null) {
				((TextView) v).setText(str);
			}
			FontManager.changeFonts(this, ((TextView) v));
		}
	}

	class CustomPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			if (viewlist.size() < 1) {
				return 0;
			} else if (viewlist.size() <= 2) {
				return 2;
			} else {
				return Integer.MAX_VALUE;
			}
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			int a = position % viewlist.size();
			Log.i("TAG", "A:" + a);
			ImageView imageview = (ImageView) viewlist.get(a);
			if (imageview.getParent() != null) {
				((ViewGroup) imageview.getParent()).removeView(imageview);
			}
			// imageview.setImageResource(R.drawable.default_pic);
			imageview.setScaleType(ScaleType.FIT_XY);
			imageview.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));
			// initPic(ToolsUtil.getImage((String)imageview.getTag(), 320,
			// 0),imageview);
			container.addView(imageview, 0);
			return imageview;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView imageview = (ImageView) viewlist.get(position
					% viewlist.size());
			container.removeView(imageview);
		}
	}

	// 加载数据
	public void initData() {
		httpControl.getMyBuyerProductDetails(productID,
				new HttpCallBackInterface() {

					@Override
					public void http_Success(Object obj) {
						if (obj != null
								&& obj instanceof RequestProductDetailsInfoBean) {
							bean = (RequestProductDetailsInfoBean) obj;
							if (bean.getData() == null) {
								http_Fails(500, "数据异常null");
								ApproveBuyerDetailsActivity.this.finish();
								return;
							}
							Data = bean.getData();
							setDatValue();
						}

					}

					@Override
					public void http_Fails(int error, String msg) {
						MyApplication.getInstance().showMessage(
								ApproveBuyerDetailsActivity.this, msg);
						finish();
					}
				}, ApproveBuyerDetailsActivity.this);
	}

	/*****
	 * 设置数据
	 * ****/
	void setDatValue() {

		// 自提地址
		String address = Data.getPickAddress();
		// 成交数据
		int truncount = Data.getTurnCount();
		// 买手头像
		String usericon = Data.getBuyerLogo();
		// 买手昵称
		String username = Data.getBuyerName();
		// 商品发布时间
		String creattime = Data.getCreateTime();
		// 关注人列表
		LikeUsersInfoBean usersInfoList = Data.getLikeUsers();
		// 价格
		double price = Data.getPrice();
		// 商品名称
		String productName = Data.getProductName();
		initPic(usericon, approvebuyerdetails_icon_imageview);
		// 自提地址
		setdataValue(R.id.approvebuyerdetails_addressvalue_textview, address);
		// 成交数量
		setdataValue(R.id.appprovebuyerdetails_textvalue1_textview, truncount
				+ "");
		// 好评率
		setdataValue(R.id.appprovebuyerdetails_text2value_textview, "");

		setdataValue(R.id.approvebuyerdetails_name_textview, username);
		// 金额
		setdataValue(R.id.approvebuyerdetails_price_textview,
				"￥" + Double.toString(price));
		// 商品名称
		setdataValue(R.id.approvebuyerdetails_producename_textview, productName);
		LikeUsersInfoBean likeUsersInfoBean = Data.getLikeUsers();
		if (likeUsersInfoBean != null) {
			//喜欢
			TextView approvebuyerdetails_attention_textview=(TextView)findViewById(R.id.approvebuyerdetails_attention_textview);
			//收藏
			TextView approvebuyerdetails_layout_shoucang_linerlayout_textview=(TextView)findViewById(R.id.approvebuyerdetails_layout_shoucang_linerlayout_textview);
			approvebuyerdetails_layout_shoucang_linerlayout_textview.setOnClickListener(this);
			approvebuyerdetails_layout_shoucang_linerlayout_textview.setTag(Data);
			approvebuyerdetails_layout_shoucang_linerlayout_textview.setSelected(Data.isIsFavorite());
			
			approvebuyerdetails_attention_textview.setSelected(likeUsersInfoBean.isIsLike());
			approvebuyerdetails_attention_textview.setText(likeUsersInfoBean.getCount()+"");
			approvebuyerdetails_attention_textview.setTag(Data);
			approvebuyerdetails_attention_textview.setOnClickListener(this); 
			List<UsersInfoBean> users = likeUsersInfoBean.getUsers();
			if (users != null) {
				myGirdView.setAdapter(new UserIconAdapter(users,ApproveBuyerDetailsActivity.this, myGirdView));
			}
		}

		if (Data.getProductPic() != null && Data.getProductPic().length > 0) {
			String[] str_array = Data.getProductPic();
			for (int i = 0; i < str_array.length; i++) {
				ImageView iv = new ImageView(ApproveBuyerDetailsActivity.this);
				iv.setScaleType(ScaleType.FIT_XY);
				iv.setImageResource(R.drawable.icon11);
				viewlist.add(iv);
				initPic(ToolsUtil.getImage(str_array[i], 320, 0), iv);
			}
			customPagerAdapter = new CustomPagerAdapter();
			appprovebuyer_viewpager.setAdapter(customPagerAdapter);
			setcurrItem(0);
			startTimeToViewPager();
			
			
		}
		ProductsDetailsPromotion productsDetailsPromotion=Data.getIsPromotion();
		if(productsDetailsPromotion!=null)
		{
			approvebuyerdetails_closeingtime_linearlayout.setVisibility(View.VISIBLE);
			approvebuyerdetails_closeingtime_textview.setText(ToolsUtil.nullToString(productsDetailsPromotion.getDescriptionText()));
			approvebuyerdetails_closeinginfo_textview.setText(ToolsUtil.nullToString(productsDetailsPromotion.getTipText()));
		}
		
	}

	@Override
	protected void onResume() {

		super.onResume();
		startTimeToViewPager();
	}

	@Override
	protected void onPause() {

		super.onPause();
		stopTimerToViewPager();
	}

	/***
	 * 添加原点
	 * 
	 * @param size
	 *            int 原点的个数
	 * @param value
	 *            int 当前选中的tab
	 * **/
	void addTabImageView(int size, int value) {
		appprovebuyer_viewpager_footer_linerlayout.removeAllViews();
		for (int i = 0; i < size; i++) {
			View v = new View(ApproveBuyerDetailsActivity.this);
			v.setBackgroundResource(R.drawable.tabround_background);
			int width = (int) ApproveBuyerDetailsActivity.this.getResources()
					.getDimension(R.dimen.shop_main_lineheight8_dimen);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					width, width);
			params.leftMargin = (int) ApproveBuyerDetailsActivity.this
					.getResources()
					.getDimension(R.dimen.shop_main_width3_dimen);
			appprovebuyer_viewpager_footer_linerlayout.addView(v, params);
			if (i == value % size) {
				v.setSelected(true);
			} else {
				v.setSelected(false);
			}
		}
	}

	/****
	 * 设置当前显示的 item
	 * **/
	void setcurrItem(int i) {
		appprovebuyer_viewpager.setCurrentItem(i);
		addTabImageView(viewlist.size(), i);
	}

	/****
	 * 加载图片
	 * */
	void initPic(final String url, final ImageView iv) {
		Log.i("TAG", "URL:" + url);
		MyApplication
				.getInstance()
				.getImageLoader()
				.displayImage(url, iv,
						MyApplication.getInstance().getDisplayImageOptions());
	}

	void setFont() {
		setdataValue(R.id.approvebuyerdetails_closeingtime_textview, null);
		setdataValue(R.id.approvebuyerdetails_closeinginfo_textview, null);
		setdataValue(R.id.tv_top_title, null);
		// 设置昵称
		setdataValue(R.id.approvebuyerdetails_name_textview, null);
		// 好评率
		setdataValue(R.id.appprovebuyerdetails_text2value_textview, null);
		// 成交
		setdataValue(R.id.appprovebuyerdetails_text1, null);
		// 成交额
		setdataValue(R.id.appprovebuyerdetails_textvalue1_textview, "");
		// 好评
		setdataValue(R.id.appprovebuyerdetails_text2, null);
		// 好评值
		setdataValue(R.id.appprovebuyerdetails_textvalue1_textview, "");
		// 金额
		setdataValue(R.id.approvebuyerdetails_price_textview, null);
		// 商品名称
		setdataValue(R.id.approvebuyerdetails_producename_textview, null);
		// 提货提醒
		setdataValue(R.id.approvebuyerdatails_layout_desc1_textview, null);
		// 自提地点
		setdataValue(R.id.approvebuyerdetails_address_textview, null);
		// 自提地址
		setdataValue(R.id.approvebuyerdetails_addressvalue_textview, null);
		// 收藏
		setdataValue(R.id.approvebuyerdetails_layout_shoucang_linerlayout_textview,null);
		// 私聊
		setdataValue(R.id.approvebuyerdetails_layout_siliao_linerlayout_textview,null);
		// 喜欢人数
		setdataValue(R.id.approvebuyerdetails_attention_textview, null);
		FontManager.changeFonts(this, approvebuyer_addcartbutton);
		FontManager.changeFonts(this, approvebuyerbuybutton);

	}

	/*****
	 * 启动自动滚动
	 * **/
	void startTimeToViewPager() {
		stopTimerToViewPager();
		if (viewlist == null || viewlist.size() <= 2) {
			return;
		}
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				currid++;
				ApproveBuyerDetailsActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						setViewPagerDuration(1000);
						setcurrItem(currid);
					}
				});
			}
		}, 2000, 3000);
	}

	/*****
	 * 停止自动滚动
	 * **/
	void stopTimerToViewPager() {
		setViewPagerDuration(500);
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	// 设置滑动速度
	void setViewPagerDuration(int value) {
		try {
			Field field = ViewPager.class.getDeclaredField("mScroller");
			field.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(
					appprovebuyer_viewpager.getContext(),
					new AccelerateInterpolator());
			field.set(appprovebuyer_viewpager, scroller);
			scroller.setmDuration(value);
		} catch (Exception e) {
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.approvebuyerdetails_icon_imageview://头像
			Intent intent=new Intent(ApproveBuyerDetailsActivity.this,ShopMainActivity.class);
			startActivity(intent);
			break;
		case R.id.approvebuyerdetails_layout_siliao_linerlayout:
			startActivity();
			break;
		case R.id.approvebuyerbuybutton:
			startActivity();
			break;
		case R.id.approvebuyerdetails_attention_textview://喜欢或取消喜欢
			if(v.getTag()!=null || v.getTag() instanceof ProductsDetailsInfoBean)
			{
				ProductsDetailsInfoBean Data=(ProductsDetailsInfoBean)v.getTag();
				LikeUsersInfoBean likeUsersInfoBean=Data.getLikeUsers();
				if(likeUsersInfoBean!=null)
				{
					if(likeUsersInfoBean.isIsLike())
					{
						setLikeOrUnLike(Data, 0,(TextView)v);
					}else
					{
						setLikeOrUnLike(Data, 1,(TextView)v);
					}
				}
				
			}
			break;
		case R.id.approvebuyerdetails_layout_shoucang_linerlayout_textview:
			if(v.getTag()!=null && v.getTag() instanceof ProductsDetailsInfoBean)
			{
				ProductsDetailsInfoBean Data=(ProductsDetailsInfoBean)v.getTag();
				if(Data!=null)
				{
					if(Data.isIsFavorite())
					{
						submitAttention(0, Data,v);
					}else
					{
						submitAttention(1, Data,v);
					}
				}
				
			}
			break;
		}
		
	}
	
	
	
	
	/*****
	 * 设置喜欢 或取消喜欢
	 * @param bean  ProductsInfoBean 商品对象
	 * @param Status int 0表示取消喜欢   1表示喜欢
	 * @param v  TextView
	 * ***/
	void setLikeOrUnLike(final ProductsDetailsInfoBean bean,final int Status,final TextView v)
	{
		httpControl.setLike(bean.getProductId(), Status, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				int count=bean.getLikeUsers().getCount();
				switch(Status)
				{
				case 0:
					v.setSelected(false);
					count--;
					if(count<0)
					{
						count=0;
					}
					bean.getLikeUsers().setIsLike(false);
					bean.getLikeUsers().setCount(count);
					v.setText(count+"");
					break;
				case 1:
					count++;
					v.setSelected(true);
					v.setText(count+"");
					bean.getLikeUsers().setIsLike(true);
					bean.getLikeUsers().setCount(count);
					break;
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(ApproveBuyerDetailsActivity.this, msg);
			}
		}, ApproveBuyerDetailsActivity.this);
	}
	
	
	
	/****
	 * 提交收藏与取消收藏商品
	 * @param type int   0表示取消收藏   1表示收藏
	 * @param brandCityWideInfo BrandCityWideInfo  商品对象
	 * **/
	void submitAttention(final int Status,final ProductsDetailsInfoBean bean,final View v)
	{
		httpControl.setFavor(bean.getProductId(), Status, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				if(v!=null && v instanceof TextView)
				{
					switch(Status)
					{
					case 0:
						v.setSelected(false);
						bean.setIsFavorite(false);
						break;
					case 1:
						v.setSelected(true);
						bean.setIsFavorite(true);
						break;
					}
					
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(ApproveBuyerDetailsActivity.this, msg);
			}
		}, ApproveBuyerDetailsActivity.this);
	}
	
}
