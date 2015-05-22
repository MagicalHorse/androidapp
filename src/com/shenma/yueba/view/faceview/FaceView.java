package com.shenma.yueba.view.faceview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;

@SuppressLint("NewApi")
public class FaceView extends LinearLayout {
	private static final int totalFace = 83;
	private static final int pageNum = 21;
	private Context activity;
	private List<String> keys;
	private List<Integer> values;
	private OnChickCallback onChickCallback;
	public void setOnChickCallback(OnChickCallback onChickCallback){
		this.onChickCallback=onChickCallback;
	}
	public FaceView(Context context) {
		super(context);
		this.activity=context;
	}

	public FaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.activity=context;
		Set<String> keySet = MyApplication.getInstance().getFaceMap().keySet();
		Collection<Integer> valueSet= MyApplication.getInstance().getFaceMap().values();
		keys = new ArrayList<String>();
		values=new ArrayList<Integer>();
		values.addAll(valueSet);
		keys.addAll(keySet);
		addView(viewPagewV(), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));  
	}
	public View viewPagewV() {
		LayoutInflater inflater = LayoutInflater.from(activity);
		View mMenuView = inflater.inflate(R.layout.face_dialog, null);
		ViewPager vp = (ViewPager) mMenuView.findViewById(R.id.mViewPager);
		List<View> listView = new ArrayList<View>();
		for (int i = 0; i < FaceUtil.pageSize(totalFace, pageNum); i++) {
			listView.add(gridV(i));
		}
		vp.setAdapter(new FaceViewPagerAdapter(listView));
		return mMenuView;
	}
	public View gridV(final int currsourPage) {
		LayoutInflater inflater = LayoutInflater.from(activity);
		View mMenuView = inflater.inflate(R.layout.alert_dialog, null);
		GridView mGridView = (GridView) mMenuView.findViewById(R.id.FacegridView1);
		List<Integer> integers = FaceUtil.getFace(pageNum, currsourPage + 1,values);
		FaceAdapter faceAdapter = new FaceAdapter(activity, integers);
		mGridView.setAdapter(faceAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				int index=pageNum *((currsourPage + 1) - 1) + arg2;
				if(onChickCallback!=null){
					onChickCallback.onChick(index,values.get(index),keys.get(index));
				}
			}
		});
		return mMenuView;
	}
	public interface OnChickCallback{
		public void onChick(int arg1,int resId,String arg2);
	}
}
