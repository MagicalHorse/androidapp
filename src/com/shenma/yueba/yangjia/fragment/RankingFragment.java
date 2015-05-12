package com.shenma.yueba.yangjia.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.CircleFragmentPagerAdapter;
import com.shenma.yueba.baijia.fragment.BaseFragment;
import com.shenma.yueba.inter.PopupWindowInter;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.PopwindowUtil;

public class RankingFragment extends BaseFragment implements OnClickListener, PopupWindowInter {
	private CurrentRankingFragment currentRankingFragment;
	private DynamicListFragment dynamicFragment;
	private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
	private ViewPager viewpager_circle;
	private ImageView iv_cursor_left, iv_cursor_right;
	private Button bt_search, bt_msg;
	private RelativeLayout rl_my_circle;
	private View view;
	private CircleFragmentPagerAdapter myFragmentPagerAdapter;
	private TextView tv_msg;
	private TextView tv_history;
	private TextView tv_list;
	private PopwindowUtil popwindowUtil;
	private List<String> dataList = new ArrayList<String>();
	private View view_line;

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
		currentRankingFragment = new CurrentRankingFragment();
		dynamicFragment = new DynamicListFragment();
		fragmentList.add(currentRankingFragment);
		fragmentList.add(dynamicFragment);
		myFragmentPagerAdapter = new CircleFragmentPagerAdapter(
				getChildFragmentManager(), fragmentList);

	}

	/**
	 * 初始化view
	 */
	private void initViews(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.ranking_layout2, null);
		view_line = view.findViewById(R.id.view_line);
		tv_msg = (TextView) view.findViewById(R.id.tv_ranking);
		tv_history = (TextView) view.findViewById(R.id.tv_history);
		tv_list = (TextView) view.findViewById(R.id.tv_list);
		tv_msg.setOnClickListener(this);
		tv_history.setOnClickListener(this);
		tv_list.setOnClickListener(this);
		viewpager_circle = (ViewPager) view.findViewById(R.id.viewpager_circle);
		iv_cursor_left = (ImageView) view.findViewById(R.id.iv_cursor_left);
		iv_cursor_left.setVisibility(View.VISIBLE);
		iv_cursor_right = (ImageView) view.findViewById(R.id.iv_cursor_right);
		popwindowUtil = new PopwindowUtil();
		popwindowUtil.setPopwindowInter(this);
		dataList.add("今天");
		dataList.add("本周");
		dataList.add("本月");
		FontManager.changeFonts(getActivity(), tv_msg, tv_history,tv_list);
	}

	@Override
	public void onResume() {
		Log.i("CircleFragment", "onResume");
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_msg:// 买手街
			viewpager_circle.setCurrentItem(0);
			break;
		case R.id.tv_dynamic:// 他们说
			viewpager_circle.setCurrentItem(1);
			break;
		case R.id.tv_list://时间选择
			popwindowUtil.initCategrayPopupWindow(getActivity(), dataList, view_line, 0, 0);
		default:
			break;
		}

	}

	@Override
	public void onItemClickListner(View view, int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClickListner(int flag, View view, int position) {
		tv_list.setText(dataList.get(position));
		
	}
}