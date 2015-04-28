package com.shenma.yueba.activity;

import java.util.ArrayList;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.adapter.LoginAndRegisterFragmentPagerAdapter;
import com.shenma.yueba.fragment.LoginFragment;
import com.shenma.yueba.fragment.RegisterFragment;
import com.shenma.yueba.util.FontManager;

/**
 * 登录注册界面
 * 
 * @author a
 * 
 */
public class LoginAndRegisterActivity extends FragmentActivity implements OnClickListener {
	private LoginFragment loginFragment;
	private RegisterFragment registerFragment;
	private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
	private ViewPager viewPager;
	private LinearLayout cursor;
	private static final int DELAY_TIME = 3000;
	public static final int SCROLL_WHAT = 0;
	private int cursorWidth;
	private int currentPage = 0;
	private int offset;
	private int screenW;
	private TextView tv_login,tv_register;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.login_and_register_layout);
		super.onCreate(savedInstanceState);
		initView();
		loginFragment = new LoginFragment();
		registerFragment = new RegisterFragment();
		fragmentList.add(loginFragment);
		fragmentList.add(registerFragment);
		LoginAndRegisterFragmentPagerAdapter myFragmentPagerAdapter = new LoginAndRegisterFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentList);
		viewPager = (ViewPager) findViewById(R.id.viewpager_login_register);
		viewPager.setAdapter(myFragmentPagerAdapter);
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

//			private boolean isScrolled = false;

//			public void onPageScrollStateChanged(int arg0) {
//				/*
//				 * 页卡正常滑动时，会经历 1-2-0的三个阶段；
//				 * 
//				 * 页卡在最后一页向右滑，或者第一页向左滑经历 1-0-2-0的阶段；
//				 * 
//				 * 直接调用 setCurrentItem则只是经历 2-0的阶段
//				 */
//				switch (arg0) {
//				/*
//				 * 每一次的滑动arg0都会经历1-2-0的阶段，但是在最后一个页面向右滑，或者第一个页面向左滑时会经历 1-0-2-0的阶段
//				 * 例如： 最后页面右滑时
//				 * ，刚刚开始会是1，这时isScrolled为false，但是没有下一页面，所以此时的页面还是最后一个页面，满足if条件
//				 */
//				case 0:
//
//					if (viewPager.getCurrentItem() == viewPager.getAdapter()
//							.getCount() - 1 && !isScrolled)
//						viewPager.setCurrentItem(0);
//					else if (viewPager.getCurrentItem() == 0 && !isScrolled) {
//						viewPager.setCurrentItem(viewPager.getAdapter()
//								.getCount() - 1);
//					}
//					break;
//				case 1:
//					isScrolled = false;
//					break;
//				case 2:
//					isScrolled = true;
//					break;
//				}
//			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			/*
			 * 页面跳转完成后调用的方法
			 */
			public void onPageSelected(int arg0) {
				if(arg0 == 1){
					Animation animation = new TranslateAnimation(0,screenW/2, 0, 0);
					animation.setDuration(300);
					animation.setFillAfter(true);
					cursor.startAnimation(animation);
					
				}
				if(arg0 ==0){
					Animation animation = new TranslateAnimation(screenW/2,0, 0, 0);
					animation.setDuration(300);
					animation.setFillAfter(true);
					cursor.startAnimation(animation);
				}
			
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});

	}

	private void initView() {
		tv_login = (TextView) findViewById(R.id.tv_login);
		tv_register = (TextView) findViewById(R.id.tv_register);
		tv_login.setOnClickListener(this);
		tv_register.setOnClickListener(this);
		initCursor();
		FontManager.changeFonts(LoginAndRegisterActivity.this, tv_login,tv_register);
		
	}

	private void initCursor() {
		cursor = (LinearLayout) findViewById(R.id.cursor);
		cursorWidth = BitmapFactory.decodeResource(getResources(), R.drawable.cursor)
				.getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenW = dm.widthPixels;
		offset = (screenW / 2 - cursorWidth) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_login://登录
			viewPager.setCurrentItem(0);
			break;
		case R.id.tv_register://注册
			viewPager.setCurrentItem(1);
			break;

		default:
			break;
		}
		
	}
}
