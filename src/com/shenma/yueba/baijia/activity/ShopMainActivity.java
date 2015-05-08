package com.shenma.yueba.baijia.activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.fragment.ShopMainFragmentTab1;
import com.shenma.yueba.baijia.fragment.StayLayFragment;
import com.shenma.yueba.view.scroll.CustomScrollView;
import com.shenma.yueba.view.scroll.CustomScrollView.ScrollListener;
/*****
 * 本类定义 店铺商品首页显示页面 
 * 1.显示商家logo  名称  地址   店铺描述 以及 商品图片等信息
 * @author gyj
 * @date 2015-05-05
 * ***/
public class ShopMainActivity extends BaseActivityWithTopView implements ScrollListener {
	//商品logo 
    ImageView shop_main_layout_icon_imageview;
    //店铺名称
    TextView shop_main_layout_name_textview;
    //商场名称
    TextView shop_main_layout_market_textview;
    //私聊按钮
    ImageButton shop_main_siliao_imagebutton;
    //关注按钮
    ImageButton shop_main_attention_imagebutton;
    //关注 值
    TextView shop_main_attention_textview;
    //粉丝值
    TextView shop_main_fans_textview;
    //赞值
    TextView shop_main_praise_textview;
    //商品描述
    TextView shap_main_description1_textview,shap_main_description2_textview,shap_main_description3_textview;
	//滑动滚动视图
    CustomScrollView shop_main_layout_stayscrollview;
    //需要停留的 视图
    LinearLayout shop_content_stayLayout;
    //固定的 头文件
    LinearLayout shop_stayLayout_Linearlayout;
    //主要内容
    FrameLayout shop_stay_context_framelayout;
    //顶部title视图
    RelativeLayout shop_main_layout_title_relativelayout;
    //主页头像与 关注的上半部分内容视图
    LinearLayout shop_main_head_include;
    //当前选择的 条目
    int currid=-1;
    /*RelativeLayout shop_stay_layout_tab1_relativelayout,
	shop_stay_layout_tab2_relativelayout,
	shop_stay_layout_tab3_relativelayout,hid_shop_stay_layout_tab1_relativelayout,hid_shop_stay_layout_tab2_relativelayout,hid_shop_stay_layout_tab3_relativelayout;*/
    FragmentManager fm;
	Map<Integer, Fragment> fragmentmap=new HashMap<Integer, Fragment>();
    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shop_main_layout);
		super.onCreate(savedInstanceState);
		initView();
	}
	
	/****
	 * 初始化视图信息
	 * **/
	@SuppressLint("NewApi")
	void initView()
	{
		setTitle("店铺名称");
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ShopMainActivity.this.finish();
			}
		});
		fm=this.getFragmentManager();
		shop_main_layout_icon_imageview=(ImageView)findViewById(R.id.shop_main_layout_icon_imageview);
		shop_main_layout_name_textview=(TextView)findViewById(R.id.shop_main_layout_name_textview);
		shop_main_layout_market_textview=(TextView)findViewById(R.id.shop_main_layout_market_textview);
		shop_main_siliao_imagebutton=(ImageButton)findViewById(R.id.shop_main_siliao_imagebutton); 
		shop_main_attention_imagebutton=(ImageButton)findViewById(R.id.shop_main_attention_imagebutton); 
		shop_main_attention_textview=(TextView)findViewById(R.id.shop_main_attention_textview); 
		shop_main_fans_textview=(TextView)findViewById(R.id.shop_main_fans_textview); 
		shop_main_praise_textview=(TextView)findViewById(R.id.shop_main_praise_textview); 
		shap_main_description1_textview=(TextView)findViewById(R.id.shap_main_description1_textview); 
		shap_main_description2_textview=(TextView)findViewById(R.id.shap_main_description2_textview); 
		shap_main_description3_textview=(TextView)findViewById(R.id.shap_main_description3_textview); 
		shop_main_layout_stayscrollview=(CustomScrollView)findViewById(R.id.shop_main_layout_stayscrollview);
		shop_content_stayLayout=(LinearLayout)findViewById(R.id.shop_content_stayLayout);
		shop_stayLayout_Linearlayout=(LinearLayout)findViewById(R.id.shop_stayLayout_Linearlayout);
		shop_stay_context_framelayout=(FrameLayout)findViewById(R.id.shop_stay_context_framelayout);
		shop_main_layout_title_relativelayout=(RelativeLayout)findViewById(R.id.shop_main_layout_title_relativelayout);
		shop_main_head_include=(LinearLayout)findViewById(R.id.shop_main_head_include);
		
		
		
		//Tab1页面
		ShopMainFragmentTab1 shopMainFragmentTab1=new ShopMainFragmentTab1();
		//Tab2页面
		ShopMainFragmentTab1 shopMainFragmentTab2=new ShopMainFragmentTab1();
		//Tab3页面
		ShopMainFragmentTab1 shopMainFragmentTab3=new ShopMainFragmentTab1();
		fragmentmap.put(R.id.shop_stay_layout_tab1_relativelayout, shopMainFragmentTab1);
		fragmentmap.put(R.id.shop_stay_layout_tab2_relativelayout, shopMainFragmentTab2);
		fragmentmap.put(R.id.shop_stay_layout_tab3_relativelayout, shopMainFragmentTab3);
		//初始默认选择
		setFragmentContent(R.id.shop_stay_layout_tab1_relativelayout);
		
		shop_main_layout_stayscrollview.setOverScrollMode(View.OVER_SCROLL_NEVER);
		shop_main_layout_stayscrollview.setScrollListener(this);
		DisplayMetrics displayMetrics=new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		shop_stay_context_framelayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, displayMetrics.heightPixels));
		//需要固定的视图
		StayLayFragment stayLayFragment=new StayLayFragment();
	}
	
	
	/****
	 * TAB的点击事件
	 * ****/
	public void shapOnClick(View v)
	{
		switch (v.getId()) {
		case R.id.shop_stay_layout_tab1_relativelayout:
		case R.id.shop_stay_layout_tab2_relativelayout:
		case R.id.shop_stay_layout_tab3_relativelayout:
			setFragmentContent(v.getId());
			break;
		}
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
	}
	
	

	@Override
	public void ScrollChanged(int x, int y) {
		// TODO Auto-generated method stub
		int titleheight=shop_main_layout_title_relativelayout.getHeight();
		int stayheight=shop_main_head_include.getHeight();
		int contextHeight=this.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getHeight();
		Log.i("TAG", "Y:"+y +" titleheight:"+titleheight+"  stayheight:"+stayheight+" contextHeight："+contextHeight +" -"+(contextHeight-titleheight-stayheight));
		if(y >= (stayheight))
		{
			shop_stayLayout_Linearlayout.setVisibility(View.VISIBLE);
		}
		else
		{
			shop_stayLayout_Linearlayout.setVisibility(View.INVISIBLE);
		}
	}
	
	/****
	 * 根据 选择的V 设置 加载的fragment
	 * 
	 * @param int id 当前选择的view 的
	 * ***/
	@SuppressLint("NewApi")
	void setFragmentContent(int id) {
		if (currid == id || id == -1) {
			return;
		}

		else if (currid == -1) {
			Fragment fragment = fragmentmap.get(id);
			if (fragment != null) {
				setShoplayoutTab(id);
				fm.beginTransaction()
						.add(R.id.shop_stay_context_framelayout, fragment)
						.commit();
			}
		} else {
			Fragment fragment = fragmentmap.get(id);
			if (fragment != null) {
				setShoplayoutTab(id);
				fm.beginTransaction()
						.replace(R.id.shop_stay_context_framelayout, fragment)
						.commit();
			}
		}
		currid = id;
	}

	/****
	 * 设置选择TAB的 样式
	 * **/
	void setShoplayoutTab(int id) {
		if (id >0) {
			if (fragmentmap.containsKey(id)) {
				Set<Integer> set = fragmentmap.keySet();
				Iterator<Integer> iterator = set.iterator();
				while (iterator.hasNext()) {
					Integer currid = iterator.next();
					setViewShowORHidden(getViewByViewId(R.id.shop_stay_layout_item_line_view, getViewByViewId(currid, shop_content_stayLayout)), false);
					setViewShowORHidden(getViewByViewId(R.id.shop_stay_layout_item_line_view, getViewByViewId(currid, shop_stayLayout_Linearlayout)), false);

					setTextViewColor(getViewByViewId(R.id.shop_stay_layout_item_textview1, getViewByViewId(currid, shop_content_stayLayout)), this.getResources().getColor(R.color.lightblack));
					setTextViewColor(getViewByViewId(R.id.shop_stay_layout_item_textview1, getViewByViewId(currid, shop_stayLayout_Linearlayout)), this.getResources().getColor(R.color.lightblack));
					setTextViewColor(getViewByViewId(R.id.shop_stay_layout_item_textview2, getViewByViewId(currid, shop_content_stayLayout)), this.getResources().getColor(R.color.lightblack));
					setTextViewColor(getViewByViewId(R.id.shop_stay_layout_item_textview2, getViewByViewId(currid, shop_stayLayout_Linearlayout)), this.getResources().getColor(R.color.lightblack));
				}
				setViewShowORHidden(getViewByViewId(R.id.shop_stay_layout_item_line_view, getViewByViewId(id, shop_content_stayLayout)), true);
				setViewShowORHidden(getViewByViewId(R.id.shop_stay_layout_item_line_view, getViewByViewId(id, shop_stayLayout_Linearlayout)), true);

				setTextViewColor(getViewByViewId(R.id.shop_stay_layout_item_textview1, getViewByViewId(currid, shop_content_stayLayout)), this.getResources().getColor(R.color.black));
				setTextViewColor(getViewByViewId(R.id.shop_stay_layout_item_textview1, getViewByViewId(currid, shop_stayLayout_Linearlayout)), this.getResources().getColor(R.color.black));
				setTextViewColor(getViewByViewId(R.id.shop_stay_layout_item_textview2, getViewByViewId(currid, shop_content_stayLayout)), this.getResources().getColor(R.color.black));
				setTextViewColor(getViewByViewId(R.id.shop_stay_layout_item_textview2, getViewByViewId(currid, shop_stayLayout_Linearlayout)), this.getResources().getColor(R.color.black));
			}
		}
	}
	
	View getViewByViewId(int id,View v)
	{
		if(v==null || id<0)
		{
			return null;
		}
		return v.findViewById(id);
	}
	

	/*****
	 * 设置字体颜色
	 * 
	 * @param View
	 *            tv 视图即TextView
	 * @param color
	 *            颜色值
	 * **/
	void setTextViewColor(View tv, int color) {
		if (tv != null && tv instanceof TextView) {
			((TextView) tv).setTextColor(color);
		}
	}

	/****
	 * 设置视图显示或隐藏
	 * 
	 * @param View
	 *            视图
	 * @param boolean b true 显示 false 隐藏
	 * **/
	void setViewShowORHidden(View v, boolean b) {
		if (v != null) {
			if (b) {
				v.setVisibility(View.VISIBLE);
			} else {
				v.setVisibility(View.INVISIBLE);
			}
		}
	}
}
