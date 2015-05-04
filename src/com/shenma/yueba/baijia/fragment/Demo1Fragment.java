package com.shenma.yueba.baijia.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.MainActivityForBaiJia;
import com.shenma.yueba.util.FontManager;

public class Demo1Fragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.item, null);
		TextView tv = (TextView) view.findViewById(R.id.tv);
		FontManager.changeFonts(getActivity(), tv);
		return view;
	}
}
