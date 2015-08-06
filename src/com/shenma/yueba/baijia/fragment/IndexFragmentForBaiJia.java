package com.shenma.yueba.baijia.fragment;

import java.util.ArrayList;
import java.util.List;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.FragmentBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FontManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/******
 * @author gyj
 * @date 2015-05-19 买手主页Fragment 主要布局采用viewPager+Linerlayout实现TAB效果切换数据
 * 
 * ****/
public class IndexFragmentForBaiJia extends Fragment {
	// 存储切换的数据
	List<FragmentBean> fragment_list = new ArrayList<FragmentBean>();
	// 存储Tab切换的视图对象
	List<View> footer_list = new ArrayList<View>();
	ViewPager baijia_fragment_tab1_pagerview;
	LinearLayout baijia_fragment_tab1_head_linearlayout;
	// 当前选中的id
	int currid = -1;
	FragmentManager fragmentManager;
	View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (v == null) {
			v = inflater.inflate(R.layout.indexfragmentforbaijia_layout, null);
			initView(v);
			baijia_fragment_tab1_pagerview.setOffscreenPageLimit(0);
		}
		ViewGroup vp = (ViewGroup) v.getParent();
		if (vp != null) {
			vp.removeView(v);
		}
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}

	/***
	 * 初始化视图
	 * ***/
	void initView(View v) {
		fragmentManager = ((FragmentActivity) getActivity())
				.getSupportFragmentManager();
		Fragment buyerStreetFragment = new BuyerStreetFragment();
		// Fragment theySayFragment=new TheySayFragment();
		Fragment myBuyerFragment = new MyBuyerFragment();
		if(fragment_list!=null){
			fragment_list.clear();
		}
		fragment_list.add(new FragmentBean("买手街", -1, buyerStreetFragment));
		// fragment_list.add(new FragmentBean("TA们说", -1, theySayFragment));
		fragment_list.add(new FragmentBean("我的买手", -1, myBuyerFragment));
		baijia_fragment_tab1_head_linearlayout = (LinearLayout) v
				.findViewById(R.id.baijia_fragment_tab1_head_linearlayout);
		for (int i = 0; i < fragment_list.size(); i++) {
			RelativeLayout rl = (RelativeLayout) RelativeLayout.inflate(
					getActivity(), R.layout.tab_line_layout, null);
			TextView tv = (TextView) rl.findViewById(R.id.tab_line_textview);
			FontManager.changeFonts(getActivity(), tv);
			rl.setTag(i);
			rl.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int i = (Integer) v.getTag();
					setCurrView(i);
				}
			});
			tv.setGravity(Gravity.CENTER);
			tv.setText(fragment_list.get(i).getName());
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			param.weight = 1;
			param.gravity = Gravity.CENTER;
			baijia_fragment_tab1_head_linearlayout.addView(rl, param);
			footer_list.add(rl);
		}
		baijia_fragment_tab1_pagerview = (ViewPager) v
				.findViewById(R.id.baijia_fragment_tab1_pagerview);
		// baijia_fragment_tab1_pagerview.setOffscreenPageLimit(fragment_list.size());
		baijia_fragment_tab1_pagerview.setAdapter(new FragmentPagerAdapter(
				fragmentManager) {

			@Override
			public int getCount() {

				return fragment_list.size();
			}

			@Override
			public android.support.v4.app.Fragment getItem(int arg0) {

				return (Fragment) fragment_list.get(arg0).getFragment();
			}

		});

		baijia_fragment_tab1_pagerview
				.setOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int arg0) {
						//currid = arg0;
						//setTextColor(arg0);
						switch (arg0) {
						case 0:
							setCurrView(0);
							break;
						case 1:
							if (!MyApplication.getInstance().isUserLogin(
									getActivity())) {
								baijia_fragment_tab1_pagerview.setCurrentItem(0, false);
							}else{
								setTextColor(arg0);
								baijia_fragment_tab1_pagerview.setCurrentItem(1, true);
								((MyBuyerFragment)fragment_list.get(arg0).getFragment()).firstInitData();
							}
							break;
						}
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {

					}
				});
		setCurrView(0);
	}

	/***
	 * 设置当前需要显示的 item
	 * ***/
	void setCurrView(int i) {
		/*if (currid == i) {
			return;
		}*/
		currid = i;
		setTextColor(i);
		baijia_fragment_tab1_pagerview.setCurrentItem(i);
	}

	/*****
	 * 设置字体颜色及选中后显示的图片
	 * ***/
	void setTextColor(int value) {
		for (int i = 0; i < footer_list.size(); i++) {
			RelativeLayout rl = (RelativeLayout) footer_list.get(i);
			TextView tv = (TextView) rl.findViewById(R.id.tab_line_textview);
			View v = (View) rl.findViewById(R.id.tab_line_view);
			if (i == value) {
				tv.setTextColor(this.getResources().getColor(
						R.color.color_deeoyellow));
				tv.setTextSize(Constants.title_text_selected_size);
				v.setVisibility(View.VISIBLE);
			} else {
				tv.setTextColor(this.getResources().getColor(
						R.color.text_gray_color));
				tv.setTextSize(Constants.title_text_normal_size);
				v.setVisibility(View.INVISIBLE);
			}

		}
	}
}
