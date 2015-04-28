package com.shenma.yueba.adapter;

import java.util.ArrayList;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

public class CircleFragmentPagerAdapter extends FragmentPagerAdapter {
	private ArrayList<Fragment> listFragments;

	public CircleFragmentPagerAdapter(FragmentManager fm,
			ArrayList<Fragment> al) {
		super(fm);
		listFragments = al;
	}

	public CircleFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return listFragments.get(position);
	}

	@Override
	public int getCount() {
		return listFragments.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}
}


