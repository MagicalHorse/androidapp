package com.shenma.yueba.view.faceview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.util.SmileUtils;


public abstract class Face {
	private FacePopupWindow menuWindow;
	private static final int totalFace = 84;
	private static final int pageNum = 21;
	private Activity activity;
	private View parent;
	private List<String> keys;
	private List<Integer> values;
	public Face(Activity activity,View parent) {
		this.activity=activity;
		this.parent=parent;
		//初始化表情列表
		Set<String> keySet = MyApplication.getInstance().getFaceMap().keySet();
		Collection<Integer> valueSet= MyApplication.getInstance().getFaceMap().values();
		keys = new ArrayList<String>();
		values=new ArrayList<Integer>();
		values.addAll(valueSet);
		keys.addAll(keySet);
	}
	public void createView() {
		menuWindow = new FacePopupWindow(activity,viewPagewV());
		// 设置layout在PopupWindow中显示的位置
		menuWindow.showAtLocation(parent,Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		menuWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				onDisMiss();
			}
		});
	}
	public void cancel(){
		menuWindow.dismiss();
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
				onClick(index,values.get(index),keys.get(index));
			}
		});
		return mMenuView;
	}
	public abstract void onClick(int arg1,int resId,String arg2);
	public abstract void onDisMiss();
}
