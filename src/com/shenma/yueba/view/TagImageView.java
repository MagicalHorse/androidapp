package com.shenma.yueba.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;

/**
 * ��ǩ�Զ���ؼ���
 * 
 * @Description
 * @author zhou wan
 * @date 2015-1-30
 * @Copyright: Copyright (c) 2015 Shenzhen Tentinet Technology Co., Ltd. Inc.
 *             All rights reserved.
 */
public class TagImageView extends RelativeLayout {
	
	private int i;
	/** �����Ķ��� */
	private Context context;
	/** ����ӿռ� tag view �ļ��� */
	private List<View> tagViewList;
	private List<Map<String, Integer>> positionList = new ArrayList<Map<String,Integer>>();
	public TagImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	public TagImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public TagImageView(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * ���ӱ�ǩ
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015-1-30,����11:50:02
	 * @updateTime 2015-1-30,����11:50:02
	 * @createAuthor zhou wan
	 * @updateAuthor
	 * @updateInfo
	 * @param content
	 * @param x
	 *            x���λ��
	 * @param y
	 *            y���λ��
	 */
	public void addTextTag(String content, int tagX, int tagY) {
		if (tagViewList == null)
			tagViewList = new ArrayList<View>();
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = mInflater.inflate(R.layout.tag, null);
		TextView text = (TextView) view.findViewById(R.id.tag_text);
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.tag_layout);
		text.setText(content);
		layout.setTag(i++);
		setTagViewOnTouchListener(layout);
		this.addView(layout);
		setTagViewPosition(layout, tagX, tagY);
		tagViewList.add(layout);
		Map<String, Integer> tagsPositonMap = new HashMap<String, Integer>();
		tagsPositonMap.put("x", tagX);
		tagsPositonMap.put("y", tagY);
		positionList.add(tagsPositonMap);
	}

	/**
	 * ����ǩ����OnTouch����
	 * 
	 * @version 1.0
	 * @createTime 2015-2-2,����11:06:11
	 * @updateTime 2015-2-2,����11:06:11
	 * @createAuthor zhou wan
	 * @updateAuthor
	 * @updateInfo (�˴������޸�����,�����޸Ŀɲ�д.)
	 * @param layoutView
	 *            ��ǩView
	 */
	public void setTagViewOnTouchListener(final RelativeLayout layoutView) {
		layoutView.setOnTouchListener(new OnTouchListener() {
			int startx = 0;
			int starty = 0;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (v.getId() == R.id.tag_layout) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						startx = (int) event.getRawX();
						starty = (int) event.getRawY();
						break;
					case MotionEvent.ACTION_MOVE:
						// ��ȡ��ָ�ƶ��ľ���
						int x = (int) event.getRawX();
						int y = (int) event.getRawY();
						int dx = x - startx;
						int dy = y - starty;
						// ����imageView�ڴ����λ��
						setTagViewPosition(v, dx, dy);
						// ��ȡ�ƶ����λ��
						startx = (int) event.getRawX();
						starty = (int) event.getRawY();
						break;
					case MotionEvent.ACTION_UP:
						Toast.makeText(getContext(), ""+layoutView.getTag(), 1000).show();
						for (int i = 0; i < tagViewList.size(); i++) {
								if(i == (Integer)layoutView.getTag()){
									positionList.get(i).put("x", startx);
									positionList.get(i).put("y", starty);
								}
							}
						break;
					}
				}
				return true;
			}
		});

	}

	/**
	 * 
	 * ���ñ�ǩ��λ��
	 * 
	 * @version 1.0
	 * @createTime 2015-1-30,����11:49:33
	 * @updateTime 2015-1-30,����11:49:33
	 * @createAuthor zhou wan
	 * @updateAuthor
	 * @updateInfo
	 * @param v
	 * @param dx
	 *            x�����ƶ�����
	 * @param dy
	 *            y�����ƶ��ľ���
	 */
	private void setTagViewPosition(View tagView, int dx, int dy) {
		int parentWidth = this.getWidth();
		int parentHeight = this.getHeight();

		int left = tagView.getLeft() + dx;
		int top = tagView.getTop() + dy;

		if (left < 0) {
			left = 0;
		} else if ((left + tagView.getWidth()) >= parentWidth) {
			left = parentWidth - tagView.getWidth();
		}
		if (top < 0) {
			top = 0;
		} else if ((top + tagView.getHeight()) >= parentHeight) {
			top = parentHeight - tagView.getHeight();
		}
		int right = left + tagView.getWidth();
		int bottom = top + tagView.getHeight();
		tagView.layout(left, top, right, bottom);
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tagView.getLayoutParams();
		params.leftMargin = left;
		params.topMargin = top;
		tagView.setLayoutParams(params);
	}

	/**
	 * @return the tagViewList
	 */
	public List<View> getTagViewList() {
		return tagViewList;
	}

	/**
	 * @param tagViewList
	 *            the tagViewList to set
	 */
	public void setTagViewList(List<View> tagViewList) {
		this.tagViewList = tagViewList;
	}

	
	/**
	 * @return the positionList
	 */
	public List<Map<String, Integer>> getPositionList() {
		return positionList;
	}


	
	/**
	 * ������б�ǩ
	 * 
	 * @version 1.0
	 * @createTime 2015-2-2,����3:10:15
	 * @updateTime 2015-2-2,����3:10:15
	 * @createAuthor zhou wan
	 * @updateAuthor
	 * @updateInfo (�˴������޸�����,�����޸Ŀɲ�д.)
	 */
	public void clearTagView() {
		if (tagViewList != null && tagViewList.size() > 0) {
			removeViews(1, tagViewList.size());
			tagViewList.clear();
		}
	}
}