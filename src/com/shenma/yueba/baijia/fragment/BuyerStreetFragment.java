package com.shenma.yueba.baijia.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.FixedSpeedScroller;
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

public class BuyerStreetFragment extends BaseFragment implements
		OnHeaderRefreshListener, OnFooterRefreshListener {
	private View view;
	ListView listview;
	PullToRefreshView mPullToRefreshView;
	private ViewPager viewpager;
	List<HashMap<String, String>> imgurl_list;
	List<View> mListViews;
	private FixedSpeedScroller mScroller;
	private int interver = 2500;// 时间间隔2500毫秒
	private Handler handler = new Handler();
//	List<HashMap<String, Object>> newslist;
	private LinearLayout indexGroup, ll_viewpager_group, ll_viewpager;
	private ArrayList<ImageView> imageList, indexList;
	public static boolean boolPageNotChange = true;// false表明onPageSelected没被调用

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.i("aaaaa", "buyerStree");
		View view = inflater.inflate(R.layout.buyer_street_layout, null, false);
		
		mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.main_pull_refresh_view);
		
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		
		viewpager = (ViewPager) view.findViewById(R.id.pager);
		android.view.ViewGroup.LayoutParams params = viewpager.getLayoutParams();
		params.height = ToolsUtil.getDisplayWidth(getActivity())/7*4;
		viewpager.setLayoutParams(params);
		
		listview = (ListView) view.findViewById(R.id.listview);
		
		String[] imgurl={"http://c.hiphotos.baidu.com/image/w%3D400/sign=c9dfb8697bf0f736d8fe4d013a54b382/a8014c086e061d9568c0b92e78f40ad162d9ca26.jpg",
				"http://img0.bdstatic.com/img/image/shouye/lvyou0424.jpg",
				"http://b.hiphotos.baidu.com/image/w%3D400/sign=e5758968cebf6c81f7372de88c3fb1d7/8694a4c27d1ed21b770343e5af6eddc450da3fe3.jpg",
				"http://s0.hao123img.com/res/r/image/2014-04-29/04ad57f37adab5c6954970a00f73a0b8.jpg"};
		
		imgurl_list=new ArrayList<HashMap<String,String>>();
		for(int i=0;i<4;i++){
			HashMap<String,String> map=new HashMap<String, String>();
			map.put("imgurl", imgurl[i]);
			imgurl_list.add(map);
		}

		
		mListViews = new ArrayList<View>();
		
		for(int i=0;i<imgurl_list.size();i++){
			ImageView iv = new ImageView(getActivity());
			iv.setScaleType(ScaleType.FIT_XY);
			MyApplication.getInstance().getBitmapUtil().display(iv, imgurl_list.get(i).get("imgurl"));
			mListViews.add(iv);
		}


		viewpager.setAdapter(new MyViewPagerAdapter(mListViews));
		setScroller();
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int index) {
				for (int i = 0; i < indexList.size(); i++) {
					indexList.get(i).setBackgroundResource(R.drawable.point_n);
				}
				if (indexList.size() > 0) {
					indexList.get(index % indexList.size())
							.setBackgroundResource(R.drawable.point_y);
				}
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		
		SetListViewHeight.setListViewHeightBasedOnChildren(listview);
		
		indexGroup = (LinearLayout)
				view.findViewById(R.id.main_viewpager_index);
		indexList = new ArrayList<ImageView>();
		createSmallPoint(4);
		return view;
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub

	}

//	private void setViewPager(View view) {
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
//	}

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
		for (int j = 0; j < i; j++) {
			ImageView index = new ImageView(getActivity());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 0, 5, 0);
			if (j == 0) {
				index.setBackgroundResource(R.drawable.point_y);
			} else {
				index.setBackgroundResource(R.drawable.point_n);
			}
			indexGroup.addView(index, params);
			indexList.add(index);
		}
	
	}
	
	@Override
	public void onStart() {
		handler.postDelayed(refresh, interver);
		super.onStart();
	}

	@Override
	public void onStop() {
		handler.removeCallbacks(refresh);
		super.onStop();
	}
	
	private Runnable refresh = new Runnable() {
		@Override
		public void run() {
			// 如果用户没有触摸屏幕
			if (boolPageNotChange) {
				viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
				handler.postDelayed(refresh, interver);
			} else {// 为了用户体验，此处减一秒钟更好一点
				handler.postDelayed(refresh, interver - 1000);
			}
			boolPageNotChange = true;
		}
	};

	
	private void setScroller() {
		// 设置左右滑动的间隔时间
		try {
			Field mField = ViewPager.class.getDeclaredField("mScroller");
			mField.setAccessible(true);
			mScroller = new FixedSpeedScroller(viewpager.getContext(),
					new AccelerateInterpolator());
			mScroller.setmDuration(500);// 可以用setDuration的方式调整速率
			mField.set(viewpager, mScroller);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void onDestroy() {
		if(viewpager!=null){
			viewpager = null;
		}
		super.onDestroy();
	}
}
