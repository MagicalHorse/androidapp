package com.shenma.yueba.seller.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.buyer.adapter.CircleFragmentPagerAdapter;
import com.shenma.yueba.buyer.fragment.BaseFragment;
import com.shenma.yueba.util.FontManager;

/**
 * 主界面
 * 
 * @author a
 */
public class IndexFragmentForSeller extends BaseFragment implements
		OnClickListener {
	private View view;
	private ImageView iv_cursor_left, iv_cursor_center, iv_cursor_right;
	private Button bt_cart;
	private ViewPager viewpager_main;
	private BuyerStreetFragment buyerStreetFragment;// 买手街
	private TheySayFragment theySayFragment;// 他们说
	private MyBuyerFragment myBuyerFragment;// 我的买手
	private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
	private CircleFragmentPagerAdapter myFragmentPagerAdapter;

	private TextView tv_earnings_title;
	private TextView tv_today_earnings_title;
	private TextView tv_today_earnings_money;
	private TextView tv_today_earnings_yuan;
	private TextView tv_all_earnings_title;
	private TextView tv_all_earnings_money;
	private TextView tv_all_earnings_yuan;

	private TextView tv_fans_title;
	private TextView tv_my_fans_title;
	private TextView tv_my_fans_count;
	private TextView tv_my_circle_title;
	private TextView tv_my_circle_count;

	private TextView tv_products_title;
	private TextView tv_products_online_title;
	private TextView tv_products_online_count;
	private TextView tv_products_offlining_title;
	private TextView tv_products_offlining_count;

	private TextView tv_sales_title;
	private TextView tv_today_sales_title;
	private TextView tv_today_sales_count;
	private TextView tv_all_sales_title;
	private TextView tv_all_sales_count;
	private TextView tv_top_title;
	private Button bt_top_right;

	private TextView tv_share_title;
	private TextView tv_online_share_title;
	private TextView tv_online_share_count;
	private TextView tv_all_share_title;
	private TextView tv_all_share_count;
	
	private LinearLayout ll_sales;
	private LinearLayout ll_earnings;
	private LinearLayout ll_share;
	private LinearLayout ll_products;
	private LinearLayout ll_fans;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			initView(inflater);
			// initFragment();
			// initViewPager();
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}

		return view;
	}

	private void initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.index_fragment_for_seller, null);
		tv_top_title = (TextView) view.findViewById(R.id.tv_top_title);
		tv_top_title.setVisibility(View.VISIBLE);
		tv_top_title.setText("首页");
		bt_top_right = (Button) view.findViewById(R.id.bt_top_right);
		bt_top_right.setVisibility(View.VISIBLE);
		bt_top_right.setBackgroundResource(R.drawable.exit);
		tv_earnings_title = (TextView) view
				.findViewById(R.id.tv_earnings_title);
		tv_today_earnings_title = (TextView) view
				.findViewById(R.id.tv_today_earnings_title);
		tv_today_earnings_money = (TextView) view
				.findViewById(R.id.tv_today_earnings_money);
		tv_today_earnings_yuan = (TextView) view
				.findViewById(R.id.tv_today_earnings_yuan);
		tv_all_earnings_title = (TextView) view
				.findViewById(R.id.tv_all_earnings_title);
		tv_all_earnings_money = (TextView) view
				.findViewById(R.id.tv_all_earnings_money);
		tv_all_earnings_yuan = (TextView) view
				.findViewById(R.id.tv_all_earnings_yuan);

		tv_fans_title = (TextView) view.findViewById(R.id.tv_fans_title);
		tv_my_fans_title = (TextView) view.findViewById(R.id.tv_my_fans_title);
		tv_my_fans_count = (TextView) view.findViewById(R.id.tv_my_fans_count);
		tv_my_circle_title = (TextView) view
				.findViewById(R.id.tv_my_circle_title);
		tv_my_circle_count = (TextView) view
				.findViewById(R.id.tv_my_circle_count);

		tv_products_title = (TextView) view
				.findViewById(R.id.tv_products_title);
		tv_products_online_title = (TextView) view
				.findViewById(R.id.tv_products_online_title);
		tv_products_online_count = (TextView) view
				.findViewById(R.id.tv_products_online_count);
		tv_products_offlining_title = (TextView) view
				.findViewById(R.id.tv_products_offlining_title);
		tv_products_offlining_count = (TextView) view
				.findViewById(R.id.tv_products_offlining_count);

		tv_sales_title = (TextView) view.findViewById(R.id.tv_sales_title);
		tv_today_sales_title = (TextView) view
				.findViewById(R.id.tv_today_sales_title);
		tv_today_sales_count = (TextView) view
				.findViewById(R.id.tv_today_sales_count);
		tv_all_sales_title = (TextView) view
				.findViewById(R.id.tv_all_sales_title);
		tv_all_sales_count = (TextView) view
				.findViewById(R.id.tv_all_sales_count);

		tv_share_title = (TextView) view.findViewById(R.id.tv_share_title);
		tv_online_share_title = (TextView) view
				.findViewById(R.id.tv_online_share_title);
		tv_online_share_count = (TextView) view
				.findViewById(R.id.tv_online_share_count);
		tv_all_share_title = (TextView) view
				.findViewById(R.id.tv_all_share_title);
		tv_all_share_count = (TextView) view
				.findViewById(R.id.tv_all_share_count);

		ll_sales = (LinearLayout) view.findViewById(R.id.ll_sales);
		ll_earnings = (LinearLayout) view
				.findViewById(R.id.ll_earnings);
		ll_share = (LinearLayout) view.findViewById(R.id.ll_share);
		ll_products = (LinearLayout) view
				.findViewById(R.id.ll_products);
		ll_fans = (LinearLayout) view.findViewById(R.id.ll_fans);

		ll_sales.setOnClickListener(this);
		ll_earnings.setOnClickListener(this);
		ll_share.setOnClickListener(this);
		ll_products.setOnClickListener(this);
		ll_fans.setOnClickListener(this);
		
		FontManager.changeFonts(getActivity(), tv_top_title, tv_earnings_title,
				tv_today_earnings_title, tv_today_earnings_money,
				tv_today_earnings_yuan, tv_all_earnings_title,
				tv_all_earnings_money, tv_all_earnings_yuan, tv_fans_title,
				tv_my_fans_title, tv_my_fans_count, tv_my_circle_title,
				tv_my_circle_count, tv_products_title,
				tv_products_online_title, tv_products_online_count,
				tv_products_offlining_title, tv_products_offlining_count,
				tv_sales_title, tv_today_sales_title, tv_today_sales_count,
				tv_all_sales_title, tv_all_sales_count, tv_share_title,
				tv_online_share_title, tv_online_share_count,
				tv_all_share_title, tv_all_share_count);
	}

	private void initFragment() {
		buyerStreetFragment = new BuyerStreetFragment();
		theySayFragment = new TheySayFragment();
		myBuyerFragment = new MyBuyerFragment();
		fragmentList.add(buyerStreetFragment);
		fragmentList.add(theySayFragment);
		fragmentList.add(myBuyerFragment);
		myFragmentPagerAdapter = new CircleFragmentPagerAdapter(
				getChildFragmentManager(), fragmentList);

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
		case R.id.ll_sales:// 销售管理
			break;
		case R.id.ll_earnings://收益管理
			break;
		case R.id.ll_share://分享管理
			break;
		case R.id.ll_products://商品管理
			break;
		case R.id.ll_fans://粉丝管理
			break;
		default:
			break;
		}

	}

}
