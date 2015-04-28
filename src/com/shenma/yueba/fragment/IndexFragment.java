package com.shenma.yueba.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.adapter.CircleFragmentPagerAdapter;
import com.shenma.yueba.util.FontManager;

/**
 * 主界面
 * 
 * @author a
 */
public class IndexFragment extends BaseFragment {
	private View view;
	private TextView tv_buyer_street;
	private TextView tv_they_say;
	private TextView tv_my_buyer;
	private ImageView iv_cursor_left,iv_cursor_center,iv_cursor_right;
	private Button bt_cart;
	private ViewPager viewpager_main;
	private BuyerStreetFragment buyerStreetFragment;//买手街
	private TheySayFragment theySayFragment;//他们说
	private MyBuyerFragment myBuyerFragment;//我的买手
	private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
	private CircleFragmentPagerAdapter myFragmentPagerAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			initView(inflater);
			initFragment();
			initViewPager();
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}

		return view;
	}

	
	private void initView(LayoutInflater inflater){
		view = inflater.inflate(R.layout.index_fragment_layout, null);
		tv_buyer_street = (TextView) view
				.findViewById(R.id.tv_buyer_street);
		tv_they_say = (TextView) view.findViewById(R.id.tv_they_say);
		tv_my_buyer = (TextView) view.findViewById(R.id.tv_my_buyer);
		bt_cart = (Button) view.findViewById(R.id.bt_cart);
		iv_cursor_left = (ImageView) view.findViewById(R.id.iv_cursor_left);
		iv_cursor_left.setVisibility(View.VISIBLE);
		iv_cursor_center = (ImageView) view.findViewById(R.id.iv_cursor_center);
		iv_cursor_right = (ImageView) view.findViewById(R.id.iv_cursor_right);
		bt_cart = (Button) view.findViewById(R.id.bt_cart);
		bt_cart = (Button) view.findViewById(R.id.bt_cart);
		viewpager_main = (ViewPager) view.findViewById(R.id.viewpager_main);
		FontManager.changeFonts(getActivity(), tv_buyer_street,tv_they_say,tv_my_buyer);
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

	
}
