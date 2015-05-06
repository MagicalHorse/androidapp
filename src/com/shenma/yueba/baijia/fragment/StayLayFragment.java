package com.shenma.yueba.baijia.fragment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;

@SuppressLint("NewApi")
public class StayLayFragment extends Fragment {
	Activity activity;
	View curridView = null;
	FragmentManager fm;
	Map<View, Fragment> fragmentmap=new HashMap<View, Fragment>();
	RelativeLayout shop_stay_layout_tab1_relativelayout,
			shop_stay_layout_tab2_relativelayout,
			shop_stay_layout_tab3_relativelayout;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = activity;
		fm= this.getFragmentManager();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LinearLayout shop_stay_layout = (LinearLayout) LinearLayout.inflate(
				activity, R.layout.shop_stay_layout, null);
		// return super.onCreateView(inflater, container, savedInstanceState);
		initView(shop_stay_layout);
		return shop_stay_layout;

	}

	void initView(LinearLayout shop_stay_layout) {
		shop_stay_layout_tab1_relativelayout = (RelativeLayout) shop_stay_layout.findViewById(R.id.shop_stay_layout_tab1_relativelayout);
		shop_stay_layout_tab2_relativelayout = (RelativeLayout) shop_stay_layout.findViewById(R.id.shop_stay_layout_tab2_relativelayout);
		shop_stay_layout_tab3_relativelayout = (RelativeLayout) shop_stay_layout.findViewById(R.id.shop_stay_layout_tab3_relativelayout);
		/*shop_stay_layout_tab1_relativelayout.setOnClickListener(onClickListener);
		shop_stay_layout_tab2_relativelayout.setOnClickListener(onClickListener);
		shop_stay_layout_tab3_relativelayout.setOnClickListener(onClickListener);
		//Tab1页面
				ShopMainFragmentTab1 shopMainFragmentTab1=new ShopMainFragmentTab1();
				//Tab2页面
				ShopMainFragmentTab1 shopMainFragmentTab2=new ShopMainFragmentTab1();
				//Tab3页面
				ShopMainFragmentTab1 shopMainFragmentTab3=new ShopMainFragmentTab1();
				fragmentmap.put(shop_stay_layout_tab1_relativelayout, shopMainFragmentTab1);
				fragmentmap.put(shop_stay_layout_tab2_relativelayout, shopMainFragmentTab2);
				fragmentmap.put(shop_stay_layout_tab3_relativelayout, shopMainFragmentTab3);
				//初始默认选择
				setFragmentContent(shop_stay_layout_tab1_relativelayout);*/
	}

	

	
}
