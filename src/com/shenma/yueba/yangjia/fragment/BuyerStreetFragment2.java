package com.shenma.yueba.yangjia.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.fragment.BaseFragment;
import com.shenma.yueba.view.SetListViewHeight;
import com.shenma.yueba.view.scroll.PullToRefreshView;
import com.shenma.yueba.view.scroll.PullToRefreshView.OnFooterRefreshListener;
import com.shenma.yueba.view.scroll.PullToRefreshView.OnHeaderRefreshListener;

/**
 * 买手街
 * 
 * @author a
 * 
 */

public class BuyerStreetFragment2 extends BaseFragment implements
		OnHeaderRefreshListener, OnFooterRefreshListener {

	private View view_1;
	ListView listview;
	PullToRefreshView mPullToRefreshView;
	private ViewPager viewpager;
	private TextView img_info;
	List<HashMap<String, String>> imgurl_list;
	List<View> mListViews;
//	List<HashMap<String, Object>> newslist;
	private LinearLayout indexGroup, ll_viewpager_group, ll_viewpager;
	private ArrayList<ImageView> imageList, indexList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view_1 = inflater.inflate(R.layout.buyer_street_layout, null, false);

		mPullToRefreshView = (PullToRefreshView) view_1
				.findViewById(R.id.main_pull_refresh_view);

		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		listview = (ListView) view_1.findViewById(R.id.listview);

		String[] imgurl = {
				"http://s0.hao123img.com/res/r/image/2014-04-29/75ead6b5ce10b25be568b66b2c8cf716.jpg",
				"http://s0.hao123img.com/res/r/image/2014-04-25/0537fe0bbb65ce150e99629c4bdd63c2.jpg",
				"http://s0.hao123img.com/res/r/image/2014-04-09/f1d95a20b79ec5ac3e908a9a9f2b5392.jpg",
				"http://s0.hao123img.com/res/r/image/2014-04-29/04ad57f37adab5c6954970a00f73a0b8.jpg" };
		String[] imginfo = { "何炅40岁生日！", "分手大师-邓超喜感勾引杨幂", "宫锁连城-颠覆前作重刷三观",
				"孟非神秘娇妻曝光" };

		imgurl_list = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < 4; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("imgurl", imgurl[i]);
			map.put("imginfo", imginfo[i]);
			imgurl_list.add(map);
		}

		img_info.setText(imgurl_list.get(0).get("imginfo"));

		mListViews = new ArrayList<View>();

		for (int i = 0; i < imgurl_list.size(); i++) {
			ImageView iv = new ImageView(getActivity());
			iv.setScaleType(ScaleType.FIT_XY);
			MyApplication.getInstance().getBitmapUtil()
					.display(iv, imgurl_list.get(i).get("imgurl"));
			mListViews.add(iv);
		}

//		setViewPager(view_1);
		createSmallPoint(9);
		viewpager.setAdapter(new MyViewPagerAdapter(mListViews));

		SetListViewHeight.setListViewHeightBasedOnChildren(listview);

		return view_1;
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub

	}

	private void setViewPager(View view) {
//		ll_viewpager_group = (LinearLayout) view
//				.findViewById(R.id.ll_viewpager_group);
//		// 屏幕的宽度
//		int width_viewpager = ToolsUtil.getDisplayWidth(getActivity());
//		// 动态构建ll_viewpager
//		ll_viewpager = new LinearLayout(getActivity());
//		LinearLayout.LayoutParams params_viewpager = new LinearLayout.LayoutParams(
//				width_viewpager, width_viewpager / 7 * 3);
//		ll_viewpager.setLayoutParams(params_viewpager);
//		ll_viewpager.setOrientation(LinearLayout.HORIZONTAL);
//		// 加入ll_viewpager_group
//		ll_viewpager_group.addView(ll_viewpager);
//
//		// 生成控件
//		View view_vp = View.inflate(getActivity(),
//				R.layout.viewpager_guang_gao_wei, null);
//		viewpager = (ViewPager) view_vp.findViewById(R.id.main_viewpager);
//		indexGroup = (LinearLayout) view_vp
//				.findViewById(R.id.main_viewpager_index);
//		// 向ll_viewpager填充视图
//		ll_viewpager.addView(view);
//		// 设置监听器
//		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
//			@Override
//			public void onPageSelected(int index) {
//				for (int i = 0; i < indexList.size(); i++) {
//					indexList.get(i).setBackgroundResource(R.drawable.point_n);
//				}
//				if (indexList.size() > 0) {
//					indexList.get(index % indexList.size())
//							.setBackgroundResource(R.drawable.point_y);
//				}
//
//			}
//
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//			}
//		});
	}

	public class MyViewPagerAdapter extends PagerAdapter {
		private List<View> mListViews;

		public MyViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View v, int position, Object object) {
			((ViewPager) v).removeView(mListViews.get(position));
		}

		@Override
		public Object instantiateItem(View v, int position) {
			View view = mListViews.get(position);
			((ViewPager) v).addView(view);
			return view;

		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}
	
	
	// 创建广告位小圆点
	private void createSmallPoint(int i) {
		ImageView index = new ImageView(getActivity());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 0, 5, 0);
		if (i == 0) {
			index.setBackgroundResource(R.drawable.point_y);
		} else {
			index.setBackgroundResource(R.drawable.point_n);
		}
		indexGroup.addView(index, params);
		indexList.add(index);
	}
}
