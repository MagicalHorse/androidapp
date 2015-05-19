package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.fragment.CircleFragment;
import com.shenma.yueba.baijia.fragment.FindFragment;
import com.shenma.yueba.baijia.fragment.IndexFragmentForBaiJia;
import com.shenma.yueba.baijia.fragment.MeFragmentForBaiJia;
import com.shenma.yueba.baijia.fragment.MessageFragment;
import com.shenma.yueba.baijia.modle.FragmentBean;

public class MainActivityForBaiJia extends FragmentActivity {
FrameLayout baijia_main_framelayout;
LinearLayout baijia_main_foot_linearlayout;
List<FragmentBean> fragment_list=new ArrayList<FragmentBean>();
List<View> footer_list=new ArrayList<View>();
//当前选中的id
int currid=-1;
Fragment indexFragmentForBaiJia,circleFragment,messageFragment,findFragment,meFragmentForBaiJia;
FragmentManager fragmentManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baijia_main_layout);
		initView();
		initaddFooterView();
	}

	void initView()
	{
		fragmentManager=getSupportFragmentManager();
		baijia_main_framelayout=(FrameLayout)findViewById(R.id.baijia_main_framelayout);
		baijia_main_foot_linearlayout=(LinearLayout)findViewById(R.id.baijia_main_foot_linearlayout);
		indexFragmentForBaiJia=new IndexFragmentForBaiJia();
		circleFragment=new CircleFragment();
		messageFragment=new MessageFragment();
		findFragment=new FindFragment();
		meFragmentForBaiJia=new MeFragmentForBaiJia();
		
		fragment_list.add(new FragmentBean("主页",R.drawable.tab_index_selector,indexFragmentForBaiJia));
		fragment_list.add(new FragmentBean("圈子",R.drawable.tab_circle_selector,circleFragment));
		fragment_list.add(new FragmentBean("购物车",R.drawable.tab_msg_selector,messageFragment));
		fragment_list.add(new FragmentBean("发现",R.drawable.tab_find_selector,findFragment));
		fragment_list.add(new FragmentBean("我",R.drawable.tab_me_selector,meFragmentForBaiJia));
		
		
	}

	void initaddFooterView()
	{
		for(int i=0;i<fragment_list.size();i++)
		{
			LinearLayout ll=(LinearLayout)LinearLayout.inflate(this, R.layout.tab_image_textview_layout, null);
			ImageView imageview=(ImageView)ll.findViewById(R.id.imageview);
			imageview.setImageResource(fragment_list.get(i).getIcon());
			TextView tv1=(TextView)ll.findViewById(R.id.tv1);
			tv1.setText(fragment_list.get(i).getName());
			LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			param.weight=1;
			baijia_main_foot_linearlayout.addView(ll,param);
			footer_list.add(ll);
			ll.setTag(i);
			ll.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int i=(Integer)v.getTag();
					setCurrView(i);
				}
			});
			if(i==0)
			{
				currid=i;
				fragmentManager.beginTransaction().add(R.id.baijia_main_framelayout, (Fragment) fragment_list.get(i).getFragment()).commit();
			}
		}
	}
	
	
	void setCurrView(int i)
	{
		if(currid==i)
		{
			return;
		}
		currid=i;
		fragmentManager.beginTransaction().replace(R.id.baijia_main_framelayout,(Fragment) fragment_list.get(i).getFragment()).commit();
	}
}
