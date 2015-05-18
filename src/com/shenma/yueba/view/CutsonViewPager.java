package com.shenma.yueba.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-13 下午7:33:52  
 * 程序的简单说明  
 */

public class CutsonViewPager extends ViewPager{
	Context context;
	List<ImageView> imageviewlist=new ArrayList<ImageView>();
	public List<ImageView> getImageviewlist() {
		return imageviewlist;
	}

	public void setImageviewlist(List<ImageView> imageviewlist) {
		this.imageviewlist = imageviewlist;
	}

	public CutsonViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CutsonViewPager(Context context) {
		super(context);
		this.context=context;
		// TODO Auto-generated constructor stub
	}
	
	void initData()
	{
		this.setAdapter(pagerAdapter);
	}

	
	PagerAdapter pagerAdapter=new PagerAdapter()
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageviewlist.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		public Object instantiateItem(android.view.ViewGroup container, int position) {
			View v=imageviewlist.get(position);
			container.addView(v, 0);
			return v;
		};
		
		public void destroyItem(android.view.ViewGroup container, int position, Object object) {
			container.removeView(imageviewlist.get(position));
		};
		
	};
}
