package com.shenma.yueba.yangjia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.yangjia.fragment.ItemCustomerFragment;
import com.viewpagerindicator.TabPageIndicator;

/**
 * 销售管理---认证买手
 * 
 * @author a
 * 
 */

public class SalesManagerForBuyerActivity extends FragmentActivity implements OnClickListener {
	private static final String[] TITLE = new String[] { "全部订单", "待发货", "待收货",
			"专柜自提" };
	private List<ItemCustomerFragment> fragmentList = new ArrayList<ItemCustomerFragment>();
	private int page = 1;
	private int index;
	private TextView tv_top_left, tv_top_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);// 加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sales_manager_layout_for_buyer);
		super.onCreate(savedInstanceState);
		initView();
		setFragmentList();
		setTabPageIndicator(setViewPager());
	}

	private void initView() {
		tv_top_left = (TextView) findViewById(R.id.tv_top_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_left.setVisibility(View.VISIBLE);
		tv_top_title.setVisibility(View.VISIBLE);
		tv_top_title.setText("销售管理");
		tv_top_left.setOnClickListener(this);
	}

	/**
	 * 初始化ItemFragment3
	 */
	private void setFragmentList() {
		for (int i = 0; i < TITLE.length; i++) {
			fragmentList.add(new ItemCustomerFragment());
		}
	}

	private ViewPager setViewPager() {
		// ViewPager的adapter
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new TabPageIndicatorAdapter(
				getSupportFragmentManager()));
		return pager;
	}

	private void setTabPageIndicator(ViewPager pager) {
		// 实例化TabPageIndicator然后设置ViewPager与之关联
		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		// TitlePageIndicator titleIndicator =
		// (TitlePageIndicator)findViewById(R.id.titles);
		// titleIndicator.setViewPager(pager);
		indicator.setCurrentItem(index);
		// 如果我们要对ViewPager设置监听，用indicator设置就行了
		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				index = arg0;
				// setPage(true,true);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
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
		public CharSequence getPageTitle(int position) {
			return TITLE[position % TITLE.length];
		}

		@Override
		public int getCount() {
			return TITLE.length;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_top_left://返回
			SalesManagerForBuyerActivity.this.finish();
			break;

		default:
			break;
		}
		
	}
}
