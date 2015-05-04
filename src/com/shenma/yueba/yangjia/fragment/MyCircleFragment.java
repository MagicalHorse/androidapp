package com.shenma.yueba.yangjia.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.fragment.BaseFragment;

public class MyCircleFragment extends BaseFragment {

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fill_person_data, null);
	}
	
	
}
