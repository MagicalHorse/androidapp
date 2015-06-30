package com.shenma.yueba.yangjia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.BaseFragmentActivity;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.FindPasswordActivity;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.yangjia.fragment.ItemCustomerFragment;


/**
 * 销售管理---认证买手
 * 
 * @author a
 * 
 */

public class SalesManagerForBuyerActivity extends BaseFragmentActivity implements
		OnClickListener {
	private static final String[] TITLE = new String[] { "全部订单", "待发货", "待收货",
			"专柜自提" };
	private List<ItemCustomerFragment> fragmentList = new ArrayList<ItemCustomerFragment>();
	private TextView tv_top_left, tv_top_title;
	private ImageView iv_cursor_left;
	private ImageView iv_cursor_left2;
	private ImageView iv_cursor_right2;
	private ImageView iv_cursor_right;
	private TextView tv_all_order;
	private TextView tv_wating_for_pay;
	private TextView tv_get_byself;
	private TextView tv_help;
	private ViewPager sales_manager_pager;
	private ArrayList<ImageView> cursorImageList = new ArrayList<ImageView>();
	private ArrayList<TextView> titleTextList = new ArrayList<TextView>();
	private String customerId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);// 加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sales_manager_layout_for_buyer);
		super.onCreate(savedInstanceState);
		setFragmentList();
		getIntentData();
		initView();
		initViewPager();
		fragmentList.get(0).getData(customerId,0,SalesManagerForBuyerActivity.this);
	}

	private void getIntentData() {
		if(!TextUtils.isEmpty(getIntent().getStringExtra("customerId"))){
			customerId = getIntent().getStringExtra("customerId");
		}
	}

	private void initView() {
		tv_top_left = (TextView) findViewById(R.id.tv_top_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_left.setVisibility(View.VISIBLE);
		tv_top_title.setVisibility(View.VISIBLE);
		tv_top_title.setText("销售管理");
		tv_top_left.setOnClickListener(this);
		iv_cursor_left = (ImageView) findViewById(R.id.iv_cursor_left);
		iv_cursor_left2 = (ImageView) findViewById(R.id.iv_cursor_left2);
		iv_cursor_right2 = (ImageView) findViewById(R.id.iv_cursor_right2);
		iv_cursor_right = (ImageView) findViewById(R.id.iv_cursor_right);
		cursorImageList.add(iv_cursor_left);
		cursorImageList.add(iv_cursor_left2);
		cursorImageList.add(iv_cursor_right2);
		cursorImageList.add(iv_cursor_right);
		iv_cursor_left.setVisibility(View.VISIBLE);
		tv_all_order = (TextView) findViewById(R.id.tv_all_order);
		tv_wating_for_pay = (TextView) findViewById(R.id.tv_wating_for_pay);
		tv_get_byself = (TextView) findViewById(R.id.tv_get_byself);
		tv_help = (TextView) findViewById(R.id.tv_help);
		tv_all_order.setTextSize(20);
		tv_all_order.setTextColor(getResources().getColor(R.color.main_color));
		FontManager.changeFonts(getApplicationContext(), tv_top_title,
				tv_all_order, tv_wating_for_pay, tv_get_byself, tv_help);
		titleTextList.add(tv_all_order);
		titleTextList.add(tv_wating_for_pay);
		titleTextList.add(tv_get_byself);
		titleTextList.add(tv_help);
		tv_all_order.setOnClickListener(this);
		tv_wating_for_pay.setOnClickListener(this);
		tv_get_byself.setOnClickListener(this);
		tv_help.setOnClickListener(this);

		sales_manager_pager = (ViewPager) findViewById(R.id.sales_manager_pager);
	}

	private void initViewPager() {
		sales_manager_pager.setAdapter(new TabPageIndicatorAdapter(
				getSupportFragmentManager()));
		sales_manager_pager.setCurrentItem(0);
		sales_manager_pager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			/*
			 * 页面跳转完成后调用的方法
			 */
			public void onPageSelected(int arg0) {
				fragmentList.get(arg0).getData(customerId,arg0,SalesManagerForBuyerActivity.this);
				for (int i = 0; i < fragmentList.size(); i++) {
					if(arg0 != i){
						fragmentList.get(arg0).tv_nodata.setVisibility(View.GONE);
					}
				}
				setCursorAndText(arg0,cursorImageList,titleTextList);
				
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * 初始化ItemFragment3
	 */
	private void setFragmentList() {
		for (int i = 0; i < TITLE.length; i++) {
			fragmentList.add(new ItemCustomerFragment(i));
		}
	}

	/**
	 * ViewPager适配器
	 * 
	 * @author len
	 * 
	 */
	class TabPageIndicatorAdapter extends FragmentPagerAdapter {
		public TabPageIndicatorAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_top_left:// 返回
			SalesManagerForBuyerActivity.this.finish();
			break;
		case R.id.tv_all_order:// 全部订单
			sales_manager_pager.setCurrentItem(0);
			break;
		case R.id.tv_wating_for_pay:// 代付款
			sales_manager_pager.setCurrentItem(1);
			break;
		case R.id.tv_get_byself:// 专柜自提
			sales_manager_pager.setCurrentItem(2);
			break;
		case R.id.tv_help:// 售后服务
			sales_manager_pager.setCurrentItem(3);
			break;
		default:
			break;
		}

	}
	
}
