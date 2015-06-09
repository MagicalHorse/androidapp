package com.shenma.yueba.baijia.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
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

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.modle.FragmentBean;
import com.shenma.yueba.baijia.view.DynamicListView;
import com.shenma.yueba.baijia.view.MsgListView;

public class MessageFragment extends Fragment{
	List<FragmentBean> fragment_list=new ArrayList<FragmentBean>();
	MessageFragment baiJiaFrament;
	ViewPager baijia_fragment_tab1_pagerview;
	LinearLayout baijia_fragment_tab1_head_linearlayout;
	//当前选中的id
	int currid=-1;
	View v;
	ViewPager baijia_head_viewpager;
	FragmentManager fragmentManager;
	List<View> footer_list=new ArrayList<View>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if(v==null)
		{
			fragmentManager=this.getChildFragmentManager();
			v=inflater.inflate(R.layout.circlefragment_layout, null);
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
	
	
	@Override
	public void onResume() {
		
		super.onResume();
        
	}
	
	
	void initView(View v)
	{
		/*Fragment recommendedCircleFragment=new RecommendedCircleFragment();
		Fragment myCircleFragment=new MyCircleFragment();
		*/
		fragment_list.add(new FragmentBean("消息", -1, MsgListView.the().getView(getActivity())));
		fragment_list.add(new FragmentBean("动态", -1, DynamicListView.the().getView(getActivity())));
		
		baijia_fragment_tab1_head_linearlayout=(LinearLayout)v.findViewById(R.id.baijia_fragment_tab1_head_linearlayout);
		for(int i=0;i<fragment_list.size();i++)
		{
			RelativeLayout rl=(RelativeLayout)RelativeLayout.inflate(getActivity(), R.layout.tab_line_layout, null);
			TextView tv=(TextView)rl.findViewById(R.id.tab_line_textview);
			rl.setTag(i);
			rl.setOnClickListener(new OnClickListener() {
				
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
			baijia_fragment_tab1_head_linearlayout.addView(rl,param);
			footer_list.add(rl);
		}
		baijia_fragment_tab1_pagerview=(ViewPager)v.findViewById(R.id.baijia_fragment_tab1_pagerview);
		baijia_fragment_tab1_pagerview.setAdapter(new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				
				return arg0==arg1;
			}
			
			@Override
			public int getCount() {
				
				return fragment_list.size();
			}
			
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				
				//return super.instantiateItem(container, position);
				View v=(View)fragment_list.get(position).getFragment();
				container.addView(v,0);
				return v;
			}
			
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				
				super.destroyItem(container, position, object);
				container.removeViewAt(0);
			}
		});
		
		baijia_fragment_tab1_pagerview.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
				setTextColor(arg0);
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
	
	void setCurrView(int i)
	{
		if(currid==i)
		{
			return;
		}
		setTextColor(i);
		baijia_fragment_tab1_pagerview.setCurrentItem(i);
	}
	
	
	@Override
    public void onDetach() {
    	super.onDetach();
    	try {
    	    Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
    	    childFragmentManager.setAccessible(true);
    	    childFragmentManager.set(this, null);

    	} catch (NoSuchFieldException e) {
    	    throw new RuntimeException(e);
    	} catch (IllegalAccessException e) {
    	    throw new RuntimeException(e);
    	}
    
    }
	
	void setTextColor(int value)
	{
		for(int i=0;i<footer_list.size();i++)
		{
			RelativeLayout rl=(RelativeLayout)footer_list.get(i);
			TextView tv=(TextView)rl.findViewById(R.id.tab_line_textview);
			View v=(View)rl.findViewById(R.id.tab_line_view);
			if(i==value)
			{
			  tv.setTextColor(this.getResources().getColor(R.color.color_deeoyellow));
		      v.setVisibility(View.VISIBLE);
			}else
			{
				tv.setTextColor(this.getResources().getColor(R.color.black));
				v.setVisibility(View.INVISIBLE);
			}
			
		}
	}
}
