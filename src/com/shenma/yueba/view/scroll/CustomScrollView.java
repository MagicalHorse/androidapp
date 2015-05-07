package com.shenma.yueba.view.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
/*****
 * 滑动滚动监听的回调  用于实现 标签到达顶点静止的效果
 * ***/
public class CustomScrollView extends ScrollView
{

	private ScrollListener listener = null;
	public CustomScrollView(Context context)
	{
		super(context);
	}

	public CustomScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public CustomScrollView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
	
	public void setScrollListener(ScrollListener listener)
	{
		this.listener = listener;
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt)
	{
		super.onScrollChanged(l, t, oldl, oldt);
		if(listener != null)
		{
			listener.ScrollChanged(getScrollX(), getScrollY());
		}
	}
	
	
	public interface ScrollListener
	{
		void ScrollChanged(int x,int y);
	}

}
