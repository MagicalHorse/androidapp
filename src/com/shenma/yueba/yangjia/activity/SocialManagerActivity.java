package com.shenma.yueba.yangjia.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.BaseFragmentActivity;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.CircleFragmentPagerAdapter;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.yangjia.adapter.MyAttentionAndFansForSocialAdapter;
import com.shenma.yueba.yangjia.fragment.MyAttentionAndFansForSocialFragment;
import com.shenma.yueba.yangjia.fragment.MyCircleForSocialFragment;
import com.shenma.yueba.yangjia.fragment.ProductManagerFragmentForOnLine;

/**
 * 社交管理
 * 
 * @author a
 */
public class SocialManagerActivity extends BaseFragmentActivity implements
		OnClickListener {
	private ArrayList<ImageView> cursorImageList = new ArrayList<ImageView>();
	private ArrayList<TextView> titleTextList = new ArrayList<TextView>();
	private ImageView iv_cursor_left, iv_cursor_center, iv_cursor_right;
	private ViewPager viewpager_main;
	private MyCircleForSocialFragment myCircleForSocialFragment;// 买手街
	private MyAttentionAndFansForSocialFragment myAttentionFragment;//我的关注
	private MyAttentionAndFansForSocialFragment myFansFragment;// 我的粉丝
	private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
	private CircleFragmentPagerAdapter myFragmentPagerAdapter;
	private TextView tv_top_left;
	private TextView tv_top_title;
	private int page =1;
	private int index;
	private TextView tv_my_circle;
	private TextView tv_my_attetion;
	private TextView tv_my_fans;
	@Override
	protected void onCreate(Bundle arg0) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.social_manager_layout);
		initView();
		initFragment();
		initViewPager();
		super.onCreate(arg0);
	}

	

	private void initView() {
		tv_top_left = (TextView) findViewById(R.id.tv_top_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_left.setVisibility(View.VISIBLE);
		tv_top_title.setVisibility(View.VISIBLE);
		tv_top_title.setText("社交管理");
		tv_top_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SocialManagerActivity.this.finish();
			}
		});
		
		tv_my_circle = (TextView) findViewById(R.id.tv_my_circle);
		tv_my_attetion = (TextView) findViewById(R.id.tv_my_attetion);
		tv_my_fans = (TextView) findViewById(R.id.tv_my_fans);
		tv_my_circle.setTextSize(20);
		tv_my_circle.setTextColor(getResources().getColor(R.color.main_color));
		tv_my_circle.setOnClickListener(this);
		tv_my_attetion.setOnClickListener(this);
		tv_my_fans.setOnClickListener(this);
		
		titleTextList.add(tv_my_circle);
		titleTextList.add(tv_my_attetion);
		titleTextList.add(tv_my_fans);
		iv_cursor_left = (ImageView) findViewById(R.id.iv_cursor_left);
		iv_cursor_center = (ImageView) findViewById(R.id.iv_cursor_center);
		iv_cursor_right = (ImageView) findViewById(R.id.iv_cursor_right);
		iv_cursor_left.setVisibility(View.VISIBLE);
		cursorImageList.add(iv_cursor_left);
		cursorImageList.add(iv_cursor_center);
		cursorImageList.add(iv_cursor_right);
		viewpager_main = (ViewPager) findViewById(R.id.viewpager_main);
		FontManager.changeFonts(this, tv_my_circle, tv_my_attetion,
				tv_my_fans,tv_top_left,tv_top_title);
	}

	private void initFragment() {
	    myCircleForSocialFragment= new MyCircleForSocialFragment();
		myAttentionFragment = new MyAttentionAndFansForSocialFragment();
		myFansFragment = new MyAttentionAndFansForSocialFragment();
		fragmentList.add(myCircleForSocialFragment);
		fragmentList.add(myAttentionFragment);
		fragmentList.add(myFansFragment);
		myFragmentPagerAdapter = new CircleFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentList);

	}

	private void initViewPager() {
		viewpager_main.setAdapter(myFragmentPagerAdapter);
		viewpager_main.setCurrentItem(0);
		viewpager_main.setOnPageChangeListener(new OnPageChangeListener() {

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			/*
			 * 页面跳转完成后调用的方法
			 */
			public void onPageSelected(int arg0) {
				setCursorAndText(arg0, cursorImageList, titleTextList);
				index = arg0;
//				switch (index) {
//				case 0:
//					productManagerFragmentForOnLine.getData(index,SocialManagerActivity.this);
//					break;
//				case 1:
//					productManagerFragmentForOnLine2.getData(index,SocialManagerActivity.this);
//					break;
//				case 2 :
//					productManagerFragmentForOnLine3.getData(index,SocialManagerActivity.this);
//					break;
//				default:
//					break;
//				}

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_buyer_street:// 买手街
			viewpager_main.setCurrentItem(0);
			break;
		case R.id.tv_they_say:// 他们说
			viewpager_main.setCurrentItem(1);
			break;
		case R.id.tv_my_buyer:// 我的买手
			viewpager_main.setCurrentItem(2);
			break;
		default:
			break;
		}

	}

}
