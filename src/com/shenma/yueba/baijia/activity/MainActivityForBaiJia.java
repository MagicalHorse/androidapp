package com.shenma.yueba.baijia.activity;

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

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.fragment.CircleFragment;
import com.shenma.yueba.baijia.fragment.FindFragment;
import com.shenma.yueba.baijia.fragment.IndexFragmentForBaiJia;
import com.shenma.yueba.baijia.fragment.MeFragmentForBaiJia;
import com.shenma.yueba.baijia.fragment.MessageFragment;
import com.shenma.yueba.util.CustomProgressDialog;
import com.shenma.yueba.util.DbHelper;
import com.shenma.yueba.util.FontManager;


/**
 * 败家模式
 * @author a
 *
 */
public final class MainActivityForBaiJia extends FragmentActivity {

	static final int MENU_MANUAL_REFRESH = 0;
	static final int MENU_DISABLE_SCROLL = 1;
	static final int MENU_SET_MODE = 2;
	static final int MENU_DEMO = 3;

	
	private LinkedList<String> mListItems;
	private ArrayAdapter<String> mAdapter;
	
	@Inject private DbHelper dbHelper;//数据库帮助类对象
	private FragmentTabHost mTabHost;
//	// 定义数组来存放按钮图片
	private int mImageViewArray[] = { R.drawable.tab_index_selector,
			R.drawable.tab_circle_selector, R.drawable.tab_msg_selector,
			R.drawable.tab_find_selector, R.drawable.tab_me_selector };
	// Tab选项卡的文字
		private String mTextviewArray[] = { "主页", "圈子", "信息", "发现","我" };
	// 定义数组来存放Fragment界面
	private Class fragmentArray[] = { IndexFragmentForBaiJia.class,CircleFragment.class,MessageFragment.class,FindFragment.class,MeFragmentForBaiJia.class};

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Toast.makeText(this, "mainoncrate", 1000).show();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		CustomProgressDialog progressDialog = CustomProgressDialog.createDialog(this);
		progressDialog.setMessage("正在加载中...");
		progressDialog.show();

}
	
	/**
	 * 初始化组件
	 */
	private void initView() {
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
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			public void onTabChanged(String tabId) {
				Toast.makeText(MainActivityForBaiJia.this,"切换了", 1000).show();
				
			}
		});
		
		
	}
	
/**
 * 给Tab按钮设置图标和文字
 */
private View getTabItemView(int index) {
	View view = View.inflate(this,R.layout.tab_item_view, null);
	ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
	imageView.setImageResource(mImageViewArray[index]);
	TextView textView = (TextView) view.findViewById(R.id.textview);
	textView.setText(mTextviewArray[index]);
	FontManager.changeFonts(getApplicationContext(), textView);
	return view;
}



}
