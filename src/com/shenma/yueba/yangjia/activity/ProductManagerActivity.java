package com.shenma.yueba.yangjia.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.CircleFragmentPagerAdapter;
import com.shenma.yueba.baijia.modle.BuyerIndexInfo;
import com.shenma.yueba.baijia.modle.BuyerIndexInfoBean;
import com.shenma.yueba.baijia.modle.Favorite;
import com.shenma.yueba.baijia.modle.Income;
import com.shenma.yueba.baijia.modle.Order;
import com.shenma.yueba.baijia.modle.Product;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.yangjia.fragment.ProductManagerFragmentForOnLine;

/**
 * 商品管理
 * 
 * @author a
 */
public class ProductManagerActivity extends FragmentActivity implements
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
	private CircleFragmentPagerAdapter myFragmentPagerAdapter;
	private TextView tv_top_left;
	private TextView tv_top_title;
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
		tv_product_online.setOnClickListener(this);
		tv_product_will_down.setOnClickListener(this);
		tv_product_has_down.setOnClickListener(this);

		iv_cursor_left = (ImageView) findViewById(R.id.iv_cursor_left);
		iv_cursor_left.setVisibility(View.VISIBLE);
		iv_cursor_center = (ImageView) findViewById(R.id.iv_cursor_center);
		iv_cursor_right = (ImageView) findViewById(R.id.iv_cursor_right);
		viewpager_main = (ViewPager) findViewById(R.id.viewpager_main);
		FontManager.changeFonts(this, tv_product_online, tv_product_will_down,
				tv_product_has_down,tv_top_left,tv_top_title);
	}

	private void initFragment() {
		productManagerFragmentForOnLine = new ProductManagerFragmentForOnLine(0);
		productManagerFragmentForOnLine2 = new ProductManagerFragmentForOnLine(1);
		productManagerFragmentForOnLine3 = new ProductManagerFragmentForOnLine(2);
		fragmentList.add(productManagerFragmentForOnLine);
		fragmentList.add(productManagerFragmentForOnLine2);
		fragmentList.add(productManagerFragmentForOnLine3);
		myFragmentPagerAdapter = new CircleFragmentPagerAdapter(
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
				if (arg0 == 0) {
					iv_cursor_left.setVisibility(View.VISIBLE);
					iv_cursor_center.setVisibility(View.INVISIBLE);
					iv_cursor_right.setVisibility(View.INVISIBLE);
				}
				if (arg0 == 1) {
					iv_cursor_center.setVisibility(View.VISIBLE);
					iv_cursor_left.setVisibility(View.INVISIBLE);
					iv_cursor_right.setVisibility(View.INVISIBLE);
				}
				if (arg0 == 2) {
					iv_cursor_center.setVisibility(View.INVISIBLE);
					iv_cursor_left.setVisibility(View.INVISIBLE);
					iv_cursor_right.setVisibility(View.VISIBLE);
				}
				index = arg0;
				switch (index) {
				case 0:
					productManagerFragmentForOnLine.getData(index,ProductManagerActivity.this);
					break;
				case 1:
					productManagerFragmentForOnLine2.getData(index,ProductManagerActivity.this);
					break;
				case 2 :
					productManagerFragmentForOnLine3.getData(index,ProductManagerActivity.this);
					break;
				default:
					break;
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
		case R.id.tv_buyer_street:// 买手街
			viewpager_main.setCurrentItem(0);
			break;
		case R.id.tv_they_say:// 他们说
			viewpager_main.setCurrentItem(1);
			break;
		case R.id.tv_my_buyer:// 我的买手
			viewpager_main.setCurrentItem(2);
			break;
		default:
			break;
		}

	}

}
