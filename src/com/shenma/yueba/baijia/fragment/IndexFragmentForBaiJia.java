package com.shenma.yueba.baijia.fragment;

import java.util.ArrayList;
import java.util.List;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.modle.FragmentBean;

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
import android.widget.TextView;

public class IndexFragmentForBaiJia extends Fragment{
	List<FragmentBean> fragment_list=new ArrayList<FragmentBean>();
	List<View> footer_list=new ArrayList<View>();
	IndexFragmentForBaiJia baiJiaFrament;
	ViewPager baijia_fragment_tab1_pagerview;
	LinearLayout baijia_fragment_tab1_head_linearlayout;
	//当前选中的id
	int currid=-1;
	FragmentManager fragmentManager;
	View v;
	ViewPager baijia_head_viewpager;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if(v==null)
		{
			v=inflater.inflate(R.layout.indexfragmentforbaijia_layout, null);
			initView(v);
		}
		ViewGroup vp=(ViewGroup)v.getParent();
		if(vp!=null)
		{
			vp.removeView(v);
		}
		//return super.onCreateView(inflater, container, savedInstanceState);
		return v;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	}
	
	void initView(View v)
	{
		fragmentManager=((FragmentActivity)getActivity()).getSupportFragmentManager();
		Fragment buyerStreetFragment=new BuyerStreetFragment();
		Fragment theySayFragment=new TheySayFragment();
		Fragment myBuyerFragment=new MyBuyerFragment();
		fragment_list.add(new FragmentBean("买手街", -1, buyerStreetFragment));
		fragment_list.add(new FragmentBean("TA们说", -1, theySayFragment));
		fragment_list.add(new FragmentBean("我的买手", -1, myBuyerFragment));
		baijia_fragment_tab1_head_linearlayout=(LinearLayout)v.findViewById(R.id.baijia_fragment_tab1_head_linearlayout);
		for(int i=0;i<fragment_list.size();i++)
		{
			TextView tv=new TextView(getActivity());
			tv.setTag(i);
			tv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int i=(Integer)v.getTag();
					setCurrView(i);
				}
			});
			tv.setGravity(Gravity.CENTER);
			tv.setText(fragment_list.get(i).getName());
			LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			param.weight=1;
			param.gravity=Gravity.CENTER;
			baijia_fragment_tab1_head_linearlayout.addView(tv,param);
		}
		baijia_fragment_tab1_pagerview=(ViewPager)v.findViewById(R.id.baijia_fragment_tab1_pagerview);
		//baijia_fragment_tab1_pagerview.setOffscreenPageLimit(fragment_list.size());
		baijia_fragment_tab1_pagerview.setAdapter(new FragmentPagerAdapter(fragmentManager) {
			
			@Override
			public int getCount() {
				
				return fragment_list.size();
			}

			@Override
			public android.support.v4.app.Fragment getItem(int arg0) {
				
				return (Fragment) fragment_list.get(arg0).getFragment();
			}
			
			
		});
		
		
		baijia_fragment_tab1_pagerview.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
				
			}
		});
	}
	
	void setCurrView(int i)
	{
		View v=((Fragment) fragment_list.get(i).getFragment()).getView();
		baijia_fragment_tab1_pagerview.setCurrentItem(i);
	}
}
