package com.shenma.yueba.view.faceview;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class FaceViewPagerAdapter extends PagerAdapter {

	private List<View> lists;
	public FaceViewPagerAdapter(List<View> list) {
		this.lists = list;
	}
	@Override
	public int getCount() {
		return lists.size();
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView(lists.get(position));
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager) container).addView(lists.get(position), 0);
		return lists.get(position);
	}
}
