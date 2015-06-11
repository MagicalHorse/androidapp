package com.shenma.yueba.yangjia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.yangjia.fragment.HuoKuanIncomeAndOutGoingFragment;


/**
 * 货款收支
 * 
 * @author a
 * 
 */

public class HuoKuanIncomingAndOutgoingsActivity extends BaseFragmentActivity implements
		OnClickListener {
	private static final String[] TITLE = new String[] { "可提现", "冻结中", "已提现",
			"退款" };
	private List<HuoKuanIncomeAndOutGoingFragment> fragmentList = new ArrayList<HuoKuanIncomeAndOutGoingFragment>();
	private TextView tv_top_left, tv_top_title;
	private ImageView iv_cursor_left;
	private ImageView iv_cursor_left2;
	private ImageView iv_cursor_right2;
	private ImageView iv_cursor_right;
	private TextView tv_can_withdraw;
	private TextView tv_freeze;
	private TextView tv_had_withdraw;
	private TextView tv_back;
	private ViewPager huokuan_income_and_outgoing_pager;
	private ArrayList<ImageView> cursorImageList = new ArrayList<ImageView>();
	private ArrayList<TextView> titleTextList = new ArrayList<TextView>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);// 加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.huokuan_income_and_expenditure_layout);
		super.onCreate(savedInstanceState);
		setFragmentList();
		initView();
		initViewPager();
		fragmentList.get(0).getData(0,HuoKuanIncomingAndOutgoingsActivity.this);
	}

	private void initView() {
		tv_top_left = (TextView) findViewById(R.id.tv_top_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_left.setVisibility(View.VISIBLE);
		tv_top_title.setVisibility(View.VISIBLE);
		tv_top_title.setText("销售收支");
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
		tv_can_withdraw = (TextView) findViewById(R.id.tv_can_withdraw);
		tv_freeze = (TextView) findViewById(R.id.tv_freeze);
		tv_had_withdraw = (TextView) findViewById(R.id.tv_had_withdraw);
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_can_withdraw.setTextSize(20);
		tv_can_withdraw.setTextColor(getResources().getColor(R.color.main_color));
		FontManager.changeFonts(getApplicationContext(), tv_top_title,
				tv_can_withdraw, tv_freeze, tv_had_withdraw, tv_back);
		titleTextList.add(tv_can_withdraw);
		titleTextList.add(tv_freeze);
		titleTextList.add(tv_had_withdraw);
		titleTextList.add(tv_back);
		tv_can_withdraw.setOnClickListener(this);
		tv_freeze.setOnClickListener(this);
		tv_had_withdraw.setOnClickListener(this);
		tv_back.setOnClickListener(this);

		huokuan_income_and_outgoing_pager = (ViewPager) findViewById(R.id.huokuan_income_and_outgoing_pager);
	}

	private void initViewPager() {
		huokuan_income_and_outgoing_pager.setAdapter(new TabPageIndicatorAdapter(
				getSupportFragmentManager()));
		huokuan_income_and_outgoing_pager.setCurrentItem(0);
		huokuan_income_and_outgoing_pager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			/*
			 * 页面跳转完成后调用的方法
			 */
			public void onPageSelected(int arg0) {
				fragmentList.get(arg0).getData(arg0,HuoKuanIncomingAndOutgoingsActivity.this);
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
			fragmentList.add(new HuoKuanIncomeAndOutGoingFragment(i));
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
			HuoKuanIncomingAndOutgoingsActivity.this.finish();
			break;
		case R.id.tv_can_withdraw://可提现
			huokuan_income_and_outgoing_pager.setCurrentItem(0);
			break;
		case R.id.tv_freeze:// 已冻结
			huokuan_income_and_outgoing_pager.setCurrentItem(1);
			break;
		case R.id.tv_had_withdraw://已提现
			huokuan_income_and_outgoing_pager.setCurrentItem(2);
			break;
		case R.id.tv_back:// 售后服务
			huokuan_income_and_outgoing_pager.setCurrentItem(3);
			break;
		default:
			break;
		}

	}
	
}
