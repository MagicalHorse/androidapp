package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.fragment.BaiJiaOrderListFragment;
import com.shenma.yueba.baijia.modle.FragmentBean;
import com.shenma.yueba.util.FontManager;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-2 上午10:39:58  
 * 程序的简单说明  我的订单主页
 */

public class BaiJiaOrderListActivity extends BaseActivityWithTopView{
FragmentManager fragmentManager;
FrameLayout baijia_main_framelayout;
LinearLayout baijia_main_foot_linearlayout;
List<FragmentBean> fragment_list=new ArrayList<FragmentBean>();
List<View> footer_list=new ArrayList<View>();
int currid=-1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		setContentView(R.layout.baijia_myorder_layout);
		super.onCreate(savedInstanceState);
		initView();
		initaddFooterView();
		
	}
	
	@SuppressLint("NewApi")
	void initView()
	{
		setTitle("我的订单");
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BaiJiaOrderListActivity.this.finish();
			}
		});
		fragmentManager=this.getFragmentManager();
		baijia_main_framelayout=(FrameLayout)findViewById(R.id.baijia_main_framelayout);
		baijia_main_foot_linearlayout=(LinearLayout)findViewById(R.id.baijia_main_foot_linearlayout);
		
		BaiJiaOrderListFragment allorder= new BaiJiaOrderListFragment(0);
		BaiJiaOrderListFragment waitpayorder= new BaiJiaOrderListFragment(0);
		BaiJiaOrderListFragment zitiorder= new BaiJiaOrderListFragment(0);
		BaiJiaOrderListFragment afterorder= new BaiJiaOrderListFragment(0);
		
		fragment_list.add(new FragmentBean("全部",-1,allorder));
		fragment_list.add(new FragmentBean("待付款",-1,waitpayorder));
		fragment_list.add(new FragmentBean("专柜自提",-1,zitiorder));
		fragment_list.add(new FragmentBean("售后",-1,afterorder));
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
			FontManager.changeFonts(getApplication(), tv1);
			LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			param.weight=1;
			baijia_main_foot_linearlayout.addView(ll,param);
			footer_list.add(ll);
			ll.setTag(i);
			ll.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int i=(Integer)v.getTag();
					setCurrView(i);
				}
			});
		}
	}
	
	
	@SuppressLint("NewApi")
	void setCurrView(int i)
	{
		if(currid==-1 && i==0)
		{
			fragmentManager.beginTransaction().add(R.id.baijia_main_framelayout, (Fragment) fragment_list.get(i).getFragment()).commit();
		}
	    else if(currid==i)
		{
			return;
		}
		currid=i;
		setTextColor(i);
		fragmentManager.beginTransaction().replace(R.id.baijia_main_framelayout,(Fragment) fragment_list.get(i).getFragment()).commit();
		
	}
	
	void setTextColor(int value)
	{
		for(int i=0;i<footer_list.size();i++)
		{
			LinearLayout ll =(LinearLayout)footer_list.get(i);
			ImageView iv=(ImageView)ll.findViewById(R.id.imageview);
			TextView tv=(TextView)ll.findViewById(R.id.tv1);
			if(i==value)
			{
			  tv.setTextColor(this.getResources().getColor(R.color.color_deeoyellow));
			  iv.setSelected(true);
				
			}else
			{
				tv.setTextColor(this.getResources().getColor(R.color.black));
				iv.setSelected(false);
			}
			
			
		}
	}
}
