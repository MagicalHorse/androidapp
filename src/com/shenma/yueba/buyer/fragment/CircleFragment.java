package com.shenma.yueba.buyer.fragment;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.buyer.activity.MainActivityForBuyer;
import com.shenma.yueba.buyer.adapter.CircleFragmentPagerAdapter;
import com.shenma.yueba.util.CustomProgressDialog;
import com.shenma.yueba.util.FontManager;

/**
 * 圈子
 * 
 * @author a
 * 
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CircleFragment extends Fragment implements OnClickListener {
	private RecommendedCircleFragment recommendedCircleFragment;
	private MyCircleFragment myCircleFragment;
	private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
	private ViewPager viewpager_circle;
	private ImageView iv_cursor_left, iv_cursor_right;
	private Button bt_search, bt_msg;
	private TextView tv_recommended_circle;
	private RelativeLayout rl_my_circle;
	private View view;
	private CircleFragmentPagerAdapter myFragmentPagerAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("CircleFragment", "oncreate");
		
		
		
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("CircleFragment", "oncreateView");
		if (view == null) {
			initViews(inflater);
			initFragment();
			initViewPager();
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}

	private void initViewPager() {
		viewpager_circle.setAdapter(myFragmentPagerAdapter);
		viewpager_circle.setCurrentItem(0);
		viewpager_circle.setOnPageChangeListener(new OnPageChangeListener() {

			// private boolean isScrolled = false;

			// public void onPageScrollStateChanged(int arg0) {
			// /*
			// * 页卡正常滑动时，会经历 1-2-0的三个阶段；
			// *
			// * 页卡在最后一页向右滑，或者第一页向左滑经历 1-0-2-0的阶段；
			// *
			// * 直接调用 setCurrentItem则只是经历 2-0的阶段
			// */
			// switch (arg0) {
			// /*
			// * 每一次的滑动arg0都会经历1-2-0的阶段，但是在最后一个页面向右滑，或者第一个页面向左滑时会经历 1-0-2-0的阶段
			// * 例如： 最后页面右滑时
			// * ，刚刚开始会是1，这时isScrolled为false，但是没有下一页面，所以此时的页面还是最后一个页面，满足if条件
			// */
			// case 0:
			//
			// if (viewPager.getCurrentItem() == viewPager.getAdapter()
			// .getCount() - 1 && !isScrolled)
			// viewPager.setCurrentItem(0);
			// else if (viewPager.getCurrentItem() == 0 && !isScrolled) {
			// viewPager.setCurrentItem(viewPager.getAdapter()
			// .getCount() - 1);
			// }
			// break;
			// case 1:
			// isScrolled = false;
			// break;
			// case 2:
			// isScrolled = true;
			// break;
			// }
			// }

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			/*
			 * 页面跳转完成后调用的方法
			 */
			public void onPageSelected(int arg0) {
				if (arg0 == 1) {
					iv_cursor_right.setVisibility(View.VISIBLE);
					iv_cursor_left.setVisibility(View.INVISIBLE);
				}
				if (arg0 == 0) {
					iv_cursor_right.setVisibility(View.INVISIBLE);
					iv_cursor_left.setVisibility(View.VISIBLE);
				}

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		// viewpager_circle.setOnPageChangeListener(new OnPageChangeListener() {
		// @Override
		// public void onPageSelected(int arg0) {
		// if (arg0 == 1) {
		// iv_cursor_right.setVisibility(View.VISIBLE);
		// iv_cursor_left.setVisibility(View.INVISIBLE);
		// }
		// if (arg0 == 0) {
		// iv_cursor_right.setVisibility(View.INVISIBLE);
		// iv_cursor_left.setVisibility(View.VISIBLE);
		// }
		// }
		//
		// @Override
		// public void onPageScrolled(int arg0, float arg1, int arg2) {
		//
		//
		// }
		//
		// @Override
		// public void onPageScrollStateChanged(int arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

	}

	private void initFragment() {
		recommendedCircleFragment = new RecommendedCircleFragment();
		myCircleFragment = new MyCircleFragment();
		fragmentList.add(recommendedCircleFragment);
		fragmentList.add(myCircleFragment);
		myFragmentPagerAdapter = new CircleFragmentPagerAdapter(
				getChildFragmentManager(), fragmentList);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_recommended_circle:// 推荐圈子
			viewpager_circle.setCurrentItem(0);
			break;
		case R.id.rl_my_circle:// 我的圈子
			viewpager_circle.setCurrentItem(1);
			break;
		default:
			break;
		}

	}

	/**
	 * 初始化view
	 */
	private void initViews(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.circle_fragment_activity, null);
		viewpager_circle = (ViewPager) view.findViewById(R.id.viewpager_circle);
		iv_cursor_left = (ImageView) view.findViewById(R.id.iv_cursor_left);
		iv_cursor_left.setVisibility(View.VISIBLE);
		iv_cursor_right = (ImageView) view.findViewById(R.id.iv_cursor_right);
		bt_search = (Button) view.findViewById(R.id.bt_search);
		bt_msg = (Button) view.findViewById(R.id.bt_msg);
		tv_recommended_circle = (TextView) view
				.findViewById(R.id.tv_recommended_circle);
		rl_my_circle = (RelativeLayout) view.findViewById(R.id.rl_my_circle);
		tv_recommended_circle.setOnClickListener(this);
		rl_my_circle.setOnClickListener(this);
		FontManager.changeFonts(getActivity(), tv_recommended_circle,rl_my_circle);
	}

	@Override
	public void onResume() {
		Log.i("CircleFragment", "onResume");
		super.onResume();
	}
}
