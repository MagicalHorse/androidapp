package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.swipemenulistview.SwipeMenu;
import com.shenma.yueba.swipemenulistview.SwipeMenuCreator;
import com.shenma.yueba.swipemenulistview.SwipeMenuItem;
import com.shenma.yueba.swipemenulistview.SwipeMenuListView;

public class SwipeListViewActivity extends Activity{
	
	private List<String> data;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.swipe_list);
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		
		data = new ArrayList<String>();
		data.add("aaaaaaaaaaaaaaa");
		data.add("bbbbbbbbbbbbbb");
		
		SwipeMenuCreator creator = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
			//创建一个菜单条
			SwipeMenuItem openItem = new SwipeMenuItem(
			getApplicationContext());
			// 设置菜单的背景
			openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
			0xCE)));
			// 宽度  菜单的宽度是一定要有的，否则不显示菜单，笔者就吃了这样的亏
			openItem.setWidth(dp2px(90));
			// 菜单标题
			openItem.setTitle("Open");
			// 标题大小
			openItem.setTitleSize(18);
			// 标题的颜色
			openItem.setTitleColor(Color.WHITE);
			// 添加到menu
			menu.addMenuItem(openItem);
			
			
			SwipeMenuItem deleteItem = new SwipeMenuItem(
					getApplicationContext());
			// set item background
			deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
					0x3F, 0x25)));
			// set item width
			deleteItem.setWidth(dp2px(90));
			// set a icon
			deleteItem.setIcon(R.drawable.ic_launcher);
			// add to menu
			menu.addMenuItem(deleteItem);
			
		
			
			}

		};
		SwipeMenuListView slv = (SwipeMenuListView) findViewById(R.id.swipeListView);
		slv.setAdapter(new myAdapter());
		slv.setMenuCreator(creator);
	}
	
	
	
	
	
     class myAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(SwipeListViewActivity.this,R.layout.item, null);
			TextView tv = (TextView) view.findViewById(R.id.tv);
			tv.setText(data.get(position));
			return view;
		}
    	 
     }
     
     private int dp2px(int dp) {
 		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
 				getResources().getDisplayMetrics());
 	}
     
     
     
     
     @Override
     protected void onDestroy() {
     	MyApplication.getInstance().removeActivity(this);//加入回退栈
     	super.onDestroy();
     }
}
