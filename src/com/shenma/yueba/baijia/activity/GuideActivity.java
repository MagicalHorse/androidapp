package com.shenma.yueba.baijia.activity;



import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.GuideAdapter;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.yangjia.activity.MainActivityForYangJia;

/***
 * 引导页，如果是程序第一次安装，就会进入此界面
 * 
 * @author Administrator
 * 
 */
public class GuideActivity extends BaseActivity  {
	private int[] ids = { R.drawable.loading02, R.drawable.loading02};
	private List<View> guides = new ArrayList<View>();
	private ViewPager pager;
	private TextView tv_yuandian;
	private TextView tv_yuandian2;
	private ImageView curDot;
	private Handler handler = new Handler();
	private int offset;// 位移量
	private int curPos = 0;// 记录当前的位置

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guide);
		SharedUtil.setInstallFlag(mContext);// 第一次安装时，置为true
		findView();
		guide();
	}

	private void findView() {
		pager = (ViewPager) findViewById(R.id.contentPager);
		curDot = (ImageView) findViewById(R.id.cur_dot);
		tv_yuandian = (TextView) findViewById(R.id.tv_yuandian);
		tv_yuandian2 = (TextView) findViewById(R.id.tv_yuandian2);
	}

	protected void guide() {
		for (int i = 0; i < ids.length; i++) {// 生成ImageView
			ImageView iv = new ImageView(this);
			iv.setImageResource(ids[i]);
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.FILL_PARENT);
			iv.setLayoutParams(params);
			iv.setScaleType(ScaleType.FIT_XY);
			guides.add(iv);// 加入集合
			iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(curPos == ids.length - 1){
						skip(MainActivityForYangJia.class, false);
						// TODO Auto-generated method stub
//						String name = SharedUtil.getUserName(mContext);
//						String password = SharedUtil.getUserPassword(mContext);
//						if (name != null && !"".equals(name) && password != null
//								&& !"".equals(password)) {
//							NetUtil.login(GuideActivity.this, name, password);// 登陆接口
//							handler.postDelayed(new Runnable() {
//								@Override
//								public void run() {
//									skip(MainActivity.class, true);
//								}
//							}, 1000);
//						} else {
//							handler.postDelayed(new Runnable() {
//
//								@Override
//								public void run() {
//									skip(MainActivity.class, true);
//								}
//							}, 1500);
//						}
					}
				}
				});
		}
		pager.setAdapter(new GuideAdapter(guides));// 设置适配器


		// 获取位移量
		curDot.getViewTreeObserver().addOnPreDrawListener(
				new OnPreDrawListener() {
					@Override
					public boolean onPreDraw() {
						offset = curDot.getWidth();
						return true;
					}
				});

		// 左右滑动
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				moveCursorTo(arg0);
				if (arg0 == ids.length - 1) {// 到最后一张了
					// 设置圆点
					tv_yuandian
							.setBackgroundResource(R.drawable.shape_linearlayout12);
					tv_yuandian2
							.setBackgroundResource(R.drawable.shape_linearlayout13);
				} else if (curPos == ids.length - 1) {
					// 设置圆点
					tv_yuandian
							.setBackgroundResource(R.drawable.shape_linearlayout13);
					tv_yuandian2
							.setBackgroundResource(R.drawable.shape_linearlayout12);
				}
				curPos = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	/**
	 * 移动指针到相邻的位置 指针的索引值
	 * */
	private void moveCursorTo(int position) {
		// 使用绝对位置
		TranslateAnimation anim = new TranslateAnimation(offset * curPos,
				offset * position, 0, 0);
		anim.setDuration(300);
		anim.setFillAfter(true);
		curDot.startAnimation(anim);
	}

	public void onResume() {

	}

	public void onPause() {
	}
}