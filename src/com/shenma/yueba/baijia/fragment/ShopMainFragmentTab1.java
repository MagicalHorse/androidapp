package com.shenma.yueba.baijia.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint({ "NewApi" })
public class ShopMainFragmentTab1 extends Fragment{
	Activity activity;
	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		this.activity=activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//创建显示的视图
		TextView tv=new TextView(activity);
		tv.setText("11111");
		return tv;
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
	}
	@Override
	public void onResume() {
		
		super.onResume();
	}
	@Override
	public void onPause() {
		
		super.onPause();
	}
	@Override
	public void onStop() {
		
		super.onStop();
	}
	@Override
	public void onDestroy() {
		
		super.onDestroy();
	}
}
