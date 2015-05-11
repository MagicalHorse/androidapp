package com.shenma.yueba.util;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.shenma.yueba.R;
import com.shenma.yueba.inter.PopupWindowInter;

public class PopwindowUtil implements OnItemClickListener {

	private static PopupWindow categrayPop;// 列表选择popwindow

	private PopupWindowInter inter;
	private int flag = -1;
	/**
	 * 弹出下拉类别选择
	 */
	public void initCategrayPopupWindow(Context ctx, List<String> dataList,
			View positionView) {
		View view = View.inflate(ctx, R.layout.popwindow_bg, null);
		ListView lv = (ListView) view.findViewById(R.id.lv);
		lv.setAdapter(new MyListAdapter(ctx, dataList));
		lv.setOnItemClickListener(this);
		categrayPop = new PopupWindow(view,
				ViewGroup.LayoutParams.WRAP_CONTENT, // 得到pop对象,并设置该pop的样子和宽高
				ViewGroup.LayoutParams.WRAP_CONTENT);
		categrayPop.setFocusable(true);
		categrayPop.setOutsideTouchable(true);
		categrayPop.setBackgroundDrawable(new BitmapDrawable());
		categrayPop.showAsDropDown(positionView, -30, 0);

	}
	
	/**
	 * 弹出下拉类别选择
	 */
	public void initCategrayPopupWindow(Context ctx, List<String> dataList,
			View positionView,int xPosition,int yPosition,int flag) {
		this.flag = flag;
		View view = View.inflate(ctx, R.layout.popwindow_bg, null);
		ListView lv = (ListView) view.findViewById(R.id.lv);
		ViewGroup.LayoutParams params = lv.getLayoutParams();
		params.width = positionView.getWidth();
		lv.setLayoutParams(params);
		lv.setAdapter(new MyListAdapter(ctx, dataList));
		lv.setOnItemClickListener(this);
		categrayPop = new PopupWindow(view,
				ViewGroup.LayoutParams.WRAP_CONTENT, // 得到pop对象,并设置该pop的样子和宽高
				ViewGroup.LayoutParams.WRAP_CONTENT);
		categrayPop.setFocusable(true);
		categrayPop.setOutsideTouchable(true);
		categrayPop.setBackgroundDrawable(new BitmapDrawable());
		categrayPop.showAsDropDown(positionView, xPosition, yPosition);

	}

	
	/**
	 * 弹出下拉类别选择
	 */
	public void initCategrayPopupWindow(Context ctx, List<String> dataList,
			View positionView,int xPosition,int yPosition) {
		this.flag = flag;
		View view = View.inflate(ctx, R.layout.popwindow_bg, null);
		ListView lv = (ListView) view.findViewById(R.id.lv);
		ViewGroup.LayoutParams params = lv.getLayoutParams();
		params.width = positionView.getWidth();
		lv.setLayoutParams(params);
		lv.setAdapter(new MyListAdapter(ctx, dataList));
		lv.setOnItemClickListener(this);
		categrayPop = new PopupWindow(view,
				ViewGroup.LayoutParams.WRAP_CONTENT, // 得到pop对象,并设置该pop的样子和宽高
				ViewGroup.LayoutParams.WRAP_CONTENT);
		categrayPop.setFocusable(true);
		categrayPop.setOutsideTouchable(true);
		categrayPop.setBackgroundDrawable(new BitmapDrawable());
		backgroundAlpha(ctx, 11);
		categrayPop.showAsDropDown(positionView, xPosition, yPosition);

	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		inter.onItemClickListner(view,position);
		inter.onItemClickListner(flag,view,position);
		categrayPop.dismiss();
	}
	
	public void setPopwindowInter(PopupWindowInter inter){
		this.inter = inter;
	}
	
	
	/** 
     * 设置添加屏幕的背景透明度 
     * @param bgAlpha 
     */  
    public void backgroundAlpha(Context ctx,float bgAlpha)  
    {  
        WindowManager.LayoutParams lp = ((Activity)ctx).getWindow().getAttributes();  
        lp.alpha = bgAlpha; //0.0-1.0  
        ((Activity)ctx).getWindow().setAttributes(lp);  
    }  
}
