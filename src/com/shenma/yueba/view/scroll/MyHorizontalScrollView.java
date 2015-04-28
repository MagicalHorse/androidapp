package com.shenma.yueba.view.scroll;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.internal.Utils;
import com.shenma.yueba.R;
import com.shenma.yueba.util.ToolsUtil;
/**
 *@author baiyuliang
 *@date 2014-07-15
 *@blog http://blog.csdn.net/baiyuliang2013
 */
public class MyHorizontalScrollView extends HorizontalScrollView {

	private Context context;
	private List<TextView> listTextView;
	private List<ImageView> listImageView;

	LinearLayout layout;
	LinearLayout root;
	TabOnClickListener tabOnClickListener;
	int id;

	public MyHorizontalScrollView(Context context) {
		super(context);
		init(context);
	}

	public MyHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		setHorizontalScrollBarEnabled(false);
		this.context = context;
		listTextView = new ArrayList<TextView>();
		listImageView = new ArrayList<ImageView>();
		id = -1;

		root = new LinearLayout(context);
		root.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		addView(root);

	}

	//动态添加导航栏文字和下划线布局
	public void addView(String title) {
		id++;
		
		layout = new LinearLayout(context);
		layout.setLayoutParams(new LayoutParams(ToolsUtil.dip(context, 70),LayoutParams.WRAP_CONTENT));
		layout.setOrientation(LinearLayout.VERTICAL);
        //导航文字
		TextView tv = new TextView(context);
		listTextView.add(tv);
		tv.setText(title);
		tv.setTextSize(16);
		tv.setId(id);
		tv.setGravity(Gravity.CENTER);
		tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ToolsUtil.dip(context, 40)));
		tv.setTextColor(getResources().getColor(R.color.black));
		tv.setOnClickListener(new onClickLis(id));
		
		layout.addView(tv);
		//导航图片
		ImageView imageView = new ImageView(context);
		imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ToolsUtil.dip(context, 4)));
		imageView.setVisibility(View.GONE);
		imageView.setImageResource(R.drawable.line_title);
		imageView.setScaleType(ScaleType.MATRIX);
		listImageView.add(imageView);
		
		layout.addView(imageView);
		
		root.addView(layout);
	}

	public void setCurrent(int pos) {
		listTextView.get(pos).setTextColor(getResources().getColor(R.color.red));
		listImageView.get(pos).setVisibility(View.VISIBLE);

		for (int i = 0; i < listTextView.size(); i++) {
			if (i != pos) {
				listTextView.get(i).setTextColor(getResources().getColor(R.color.black));
				listImageView.get(i).setVisibility(View.GONE);
			}
		}
       //每切换一次选项卡或者fragment时，导航移动的距离
		scrollTo(ToolsUtil.dip(context, pos * 70), 0);
	}

	public void setTabOnClickListener(TabOnClickListener tabOnClickListener) {
		this.tabOnClickListener = tabOnClickListener;
	}

	public interface TabOnClickListener {
		public void TabOnClick(int pos);
	}

	class onClickLis implements OnClickListener {
		private int id;

		public onClickLis(int id) {
			this.id = id;
		}

		@Override
		public void onClick(View arg0) {
			setCurrent(id);
			tabOnClickListener.TabOnClick(id);
		}

	}

}
