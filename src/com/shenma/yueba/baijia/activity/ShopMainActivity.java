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
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.fragment.ShopMainFragmentTab1;
import com.shenma.yueba.baijia.fragment.StayLayFragment;
import com.shenma.yueba.view.RoundImageView;
import com.shenma.yueba.view.imageshow.CustomImageView;
import com.shenma.yueba.view.scroll.CustomScrollView;
import com.shenma.yueba.view.scroll.CustomScrollView.ScrollListener;
/*****
 * 本类定义 店铺商品首页显示页面 
 * 1.显示商家logo  名称  地址   店铺描述 以及 商品图片等信息
 * @author gyj
 * @date 2015-05-05
 * ***/
public class ShopMainActivity extends BaseActivityWithTopView {
	//商品logo 
    CustomImageView shop_main_layout_icon_imageview;
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
    /*RelativeLayout shop_stay_layout_tab1_relativelayout,
	shop_stay_layout_tab2_relativelayout,
	shop_stay_layout_tab3_relativelayout,hid_shop_stay_layout_tab1_relativelayout,hid_shop_stay_layout_tab2_relativelayout,hid_shop_stay_layout_tab3_relativelayout;*/
    FragmentManager fm;
	Map<Integer, Fragment> fragmentmap=new HashMap<Integer, Fragment>();
    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	MyApplication.getInstance().addActivity(this);//加入回退栈
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
		shop_main_layout_icon_imageview=(CustomImageView)findViewById(R.id.shop_main_layout_icon_imageview);
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
		
		
		
		
		//Tab1页面
		ShopMainFragmentTab1 shopMainFragmentTab1=new ShopMainFragmentTab1();
		//Tab2页面
		ShopMainFragmentTab1 shopMainFragmentTab2=new ShopMainFragmentTab1();
		//Tab3页面
		ShopMainFragmentTab1 shopMainFragmentTab3=new ShopMainFragmentTab1();
		fragmentmap.put(R.id.shop_stay_layout_tab1_relativelayout, shopMainFragmentTab1);
		fragmentmap.put(R.id.shop_stay_layout_tab2_relativelayout, shopMainFragmentTab2);
		fragmentmap.put(R.id.shop_stay_layout_tab3_relativelayout, shopMainFragmentTab3);
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
