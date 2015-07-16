package com.shenma.yueba.yangjia.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.BaseFragmentActivity;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.MyFragmentPagerAdapter;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.yangjia.fragment.ProductManagerFragmentForOnLine;

/**
 * 商品管理
 * 
 * @author a
 */
public class ProductManagerActivity extends BaseFragmentActivity implements
		OnClickListener {
	private TextView tv_product_online;
	private TextView tv_product_will_down;
	private TextView tv_product_has_down;
	private ImageView iv_cursor_left, iv_cursor_center, iv_cursor_right;
	private ViewPager viewpager_main;
	private ProductManagerFragmentForOnLine productManagerFragmentForOnLine;// 买手街
	private ProductManagerFragmentForOnLine productManagerFragmentForOnLine2;// 他们说
	private ProductManagerFragmentForOnLine productManagerFragmentForOnLine3;// 我的买手
	private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
	private MyFragmentPagerAdapter myFragmentPagerAdapter;
	private TextView tv_top_left;
	private TextView tv_top_title;
	private ArrayList<ImageView> cursorImageList = new ArrayList<ImageView>();
	private ArrayList<TextView> titleTextList = new ArrayList<TextView>();
	private int page =1;
	private int index;
	@Override
	protected void onCreate(Bundle arg0) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.product_manager_layout);
		initView();
		initFragment();
		initViewPager();
		productManagerFragmentForOnLine.getData(0,ProductManagerActivity.this);
		super.onCreate(arg0);
	}

	private void initView() {

		tv_top_left = (TextView) findViewById(R.id.tv_top_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_left.setVisibility(View.VISIBLE);
		tv_top_title.setVisibility(View.VISIBLE);
		tv_top_title.setText("商品管理");
		tv_top_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProductManagerActivity.this.finish();
			}
		});
		
		tv_product_online = (TextView) findViewById(R.id.tv_product_online);
		tv_product_will_down = (TextView) findViewById(R.id.tv_product_will_down);
		tv_product_has_down = (TextView) findViewById(R.id.tv_product_has_down);
		tv_product_online.setTextSize(20);
		tv_product_online.setTextColor(getResources().getColor(R.color.main_color));
		tv_product_online.setOnClickListener(this);
		tv_product_will_down.setOnClickListener(this);
		tv_product_has_down.setOnClickListener(this);
		titleTextList.add(tv_product_online);
		titleTextList.add(tv_product_will_down);
		titleTextList.add(tv_product_has_down);
		
		iv_cursor_left = (ImageView) findViewById(R.id.iv_cursor_left);
		iv_cursor_left.setVisibility(View.VISIBLE);
		iv_cursor_center = (ImageView) findViewById(R.id.iv_cursor_center);
		iv_cursor_right = (ImageView) findViewById(R.id.iv_cursor_right);
		cursorImageList.add(iv_cursor_left);
		cursorImageList.add(iv_cursor_center);
		cursorImageList.add(iv_cursor_right);
		viewpager_main = (ViewPager) findViewById(R.id.viewpager_main);
		FontManager.changeFonts(this, tv_product_online, tv_product_will_down,
				tv_product_has_down,tv_top_left,tv_top_title);
	}

	private void initFragment() {
		productManagerFragmentForOnLine = new ProductManagerFragmentForOnLine(1);
		productManagerFragmentForOnLine2 = new ProductManagerFragmentForOnLine(2);
		productManagerFragmentForOnLine3 = new ProductManagerFragmentForOnLine(0);
		fragmentList.add(productManagerFragmentForOnLine);
		fragmentList.add(productManagerFragmentForOnLine2);
		fragmentList.add(productManagerFragmentForOnLine3);
		myFragmentPagerAdapter = new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentList);

	}

	
	
	
	private void initViewPager() {
		viewpager_main.setAdapter(myFragmentPagerAdapter);
		viewpager_main.setCurrentItem(0);
		viewpager_main.setOnPageChangeListener(new OnPageChangeListener() {

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			/*
			 * 页面跳转完成后调用的方法
			 */
			public void onPageSelected(int arg0) {
				setCursorAndText(arg0,cursorImageList,titleTextList);
				if(arg0 == 0){//全部在线商品
					productManagerFragmentForOnLine2.tv_nodata.setVisibility(View.GONE);
//					productManagerFragmentForOnLine3.tv_nodata.setVisibility(View.GONE);
					productManagerFragmentForOnLine.getData(arg0, ProductManagerActivity.this);
				}else if(arg0 == 1){//即将下线商品
					productManagerFragmentForOnLine.tv_nodata.setVisibility(View.GONE);
//					productManagerFragmentForOnLine3.tv_nodata.setVisibility(View.GONE);
					productManagerFragmentForOnLine2.getData(arg0, ProductManagerActivity.this);
				}else if(arg0 == 2){//已经下线
					productManagerFragmentForOnLine2.tv_nodata.setVisibility(View.GONE);
					productManagerFragmentForOnLine.tv_nodata.setVisibility(View.GONE);
					productManagerFragmentForOnLine3.getData(arg0, ProductManagerActivity.this);
				}
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_product_online:// 在线商品
			viewpager_main.setCurrentItem(0);
			break;
		case R.id.tv_product_will_down:// 即将下线
			viewpager_main.setCurrentItem(1);
			break;
		case R.id.tv_product_has_down:// 下线商品
			viewpager_main.setCurrentItem(2);
			break;
		default:
			break;
		}

	}

	
	
	@Override
	protected void onDestroy() {
		MyApplication.getInstance().addActivity(this);
		super.onDestroy();
	}
	
	
}
