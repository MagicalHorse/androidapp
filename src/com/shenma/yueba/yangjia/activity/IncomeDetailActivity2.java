package com.shenma.yueba.yangjia.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.shenma.yueba.baijia.adapter.MyFragmentPagerAdapter;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.yangjia.fragment.IncomeDetailFragment;
import com.shenma.yueba.yangjia.fragment.WithdrawHistoryFragment;

/**
 * 收入明细 
 * @author a
 */
public class IncomeDetailActivity2 extends BaseFragmentActivity implements
		OnClickListener {
	private TextView tv_can_getmoney;
	private TextView tv_freezing;
	private TextView tv_disabled;
	private ImageView iv_cursor_left, iv_cursor_center, iv_cursor_right;
	private ViewPager viewpager_main;
	private IncomeDetailFragment incomeDetailFragment;// 可提现
	private IncomeDetailFragment incomeDetailFragment2;// 冻结中
	private IncomeDetailFragment incomeDetailFragment3;// 失效
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
		setContentView(R.layout.income_detail_layout2);
		initView();
		initFragment();
		initViewPager();
		//productManagerFragmentForOnLine.getData(0,WithdrawHistoryActivity.this);
		super.onCreate(arg0);
	}

	

	private void initView() {

		tv_top_left = (TextView) findViewById(R.id.tv_top_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_left.setVisibility(View.VISIBLE);
		tv_top_title.setVisibility(View.VISIBLE);
		tv_top_title.setText("收益明細");
		tv_top_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				IncomeDetailActivity2.this.finish();
			}
		});
		
		tv_can_getmoney = (TextView) findViewById(R.id.tv_can_getmoney);
		tv_freezing = (TextView) findViewById(R.id.tv_freezing);
		tv_disabled = (TextView) findViewById(R.id.tv_disabled);
		tv_can_getmoney.setTextSize(20);
		tv_can_getmoney.setTextColor(getResources().getColor(R.color.main_color));
		tv_can_getmoney.setOnClickListener(this);
		tv_freezing.setOnClickListener(this);
		tv_disabled.setOnClickListener(this);
		titleTextList.add(tv_can_getmoney);
		titleTextList.add(tv_freezing);
		titleTextList.add(tv_disabled);
		
		iv_cursor_left = (ImageView) findViewById(R.id.iv_cursor_left);
		iv_cursor_left.setVisibility(View.VISIBLE);
		iv_cursor_center = (ImageView) findViewById(R.id.iv_cursor_center);
		iv_cursor_right = (ImageView) findViewById(R.id.iv_cursor_right);
		cursorImageList.add(iv_cursor_left);
		cursorImageList.add(iv_cursor_center);
		cursorImageList.add(iv_cursor_right);
		viewpager_main = (ViewPager) findViewById(R.id.viewpager_main);
		FontManager.changeFonts(this, tv_can_getmoney, tv_freezing,
				tv_disabled,tv_top_left,tv_top_title);
	}

	private void initFragment() {
		incomeDetailFragment = new IncomeDetailFragment(0);
		incomeDetailFragment2 = new IncomeDetailFragment(1);
		incomeDetailFragment3 = new IncomeDetailFragment(2);
		fragmentList.add(incomeDetailFragment);
		fragmentList.add(incomeDetailFragment2);
		fragmentList.add(incomeDetailFragment3);
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