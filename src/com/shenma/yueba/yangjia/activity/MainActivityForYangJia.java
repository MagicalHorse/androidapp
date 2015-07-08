package com.shenma.yueba.yangjia.activity;

/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

import java.util.LinkedList;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.camera.CameraAty;
import com.shenma.yueba.camera2.ActivityCapture;
import com.shenma.yueba.util.CustomProgressDialog;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.yangjia.fragment.CartFragment;
import com.shenma.yueba.yangjia.fragment.IndexFragmentForYangJia;
import com.shenma.yueba.yangjia.fragment.MeFragmentForYangJia;
import com.shenma.yueba.yangjia.fragment.MessageFragmentForYangJia;
import com.shenma.yueba.yangjia.fragment.TaskRewardFragment;


/**
 * 养家模式
 * @author a
 *
 */
public final class MainActivityForYangJia extends FragmentActivity {

	static final int MENU_MANUAL_REFRESH = 0;
	static final int MENU_DISABLE_SCROLL = 1;
	static final int MENU_SET_MODE = 2;
	static final int MENU_DEMO = 3;

	
	private LinkedList<String> mListItems;
	private ArrayAdapter<String> mAdapter;
	private FragmentTabHost mTabHost;
//	// 定义数组来存放按钮图片
	private int mImageViewArray[] = { R.drawable.tab_index_selector,
			R.drawable.tab_reward_selector, R.drawable.tab_publish_selector,
			R.drawable.tab_msg_selector, R.drawable.tab_me_selector };
	// Tab选项卡的文字
		private String mTextviewArray[] = { "主页", "红榜", "发布", "消息","我" };
	// 定义数组来存放Fragment界面
	private Class fragmentArray[] = { IndexFragmentForYangJia.class,TaskRewardFragment.class,CartFragment.class,MessageFragmentForYangJia.class,MeFragmentForYangJia.class};

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		
//		CustomProgressDialog progressDialog = CustomProgressDialog.createDialog(this);
//		progressDialog.setMessage("正在加载中...");
//		progressDialog.show();

}
	
	/**
	 * 初始化组件
	 */
	private void initView() {
		TextView tv_center = (TextView) findViewById(R.id.tv_center);
		tv_center.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_UP){//当手抬起的时候出发事件
					showDialog();
				}
				return true;
			}
		});
		
		tv_center.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivityForYangJia.this, "click", 1000).show();
			}
		});
		// 实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		// 得到fragment的个数
		int count = fragmentArray.length;
		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
		}
		mTabHost.getTabWidget().setDividerDrawable(null);  //去掉tab之间的竖线
	}
	
/**
 * 给Tab按钮设置图标和文字
 */
private View getTabItemView(int index) {
	View view = View.inflate(this,R.layout.tab_item_view, null);
	ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
	imageView.setImageResource(mImageViewArray[index]);
//	TextView textView = (TextView) view.findViewById(R.id.textview);
//	textView.setText(mTextviewArray[index]);
//	FontManager.changeFonts(getApplicationContext(), textView);
	return view;
}


private void showDialog() {
	final AlertDialog dialog = new AlertDialog.Builder(MainActivityForYangJia.this)
			.create();
	dialog.show();
	Window window = dialog.getWindow();
	// 设置布局
	window.setContentView(R.layout.alertdialog);
	// 设置宽高
	window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	
	window.setGravity(Gravity.BOTTOM);
	// 设置弹出的动画效果
	window.setWindowAnimations(R.style.AnimBottom);
	// 设置监听
	Button bt_kxp = (Button) window.findViewById(R.id.bt_kxp);
	Button bt_publish = (Button) window.findViewById(R.id.bt_publish);
	bt_kxp.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			dialog.cancel();
			// 跳转到开下票的界面
			Intent intentXP = new Intent(MainActivityForYangJia.this,KaiXiaoPiaoActivity.class);
			startActivity(intentXP);
		}
	});
	bt_publish.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "相机初始化中...", 1000).show();
			// 跳转到到自定义相机
//			Intent intentCamera = new Intent(MainActivityForYangJia.this,CameraAty.class);
//			startActivity(intentCamera);
			Intent intentCamera = new Intent(MainActivityForYangJia.this,ActivityCapture.class);
			startActivity(intentCamera);
			dialog.cancel();
		}
	});
	FontManager.changeFonts(MainActivityForYangJia.this, bt_kxp,bt_publish);
	// 因为我们用的是windows的方法，所以不管ok活cancel都要加上“dialog.cancel()”这句话，
	// 不然有程序崩溃的可能，仅仅是一种可能，但我们还是要排除这一点，对吧？
	// 用AlertDialog的两个Button，即使监听里什么也不写，点击后也是会吧dialog关掉的，不信的同学可以去试下
}

}
