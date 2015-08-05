package com.shenma.yueba.view;



import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.shenma.yueba.R;
import com.shenma.yueba.util.pop.FacePopupWindow;

public abstract class SelecteTagType {
	private FacePopupWindow menuWindow;
	private Activity activity;
	private View parent;
	private Button bt_quit;
	private Button bt_common_tag;
	private Button bt_brand_tag;
	private int layout = -1;

	public SelecteTagType(Activity activity, View parent) {
		this.activity = activity;
		this.parent = parent;
	}
	public SelecteTagType(Activity activity, View parent,int layout) {
		this.activity = activity;
		this.parent = parent;
		this.layout = layout;
	}
	public void createView() {
		menuWindow = new FacePopupWindow(activity, viewPagewV());
		menuWindow.showAtLocation(parent, Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
	}
	public void canceView() {
		menuWindow.dismiss();
	}
	public View viewPagewV() {
		LayoutInflater inflater = LayoutInflater.from(activity);
		View mMenuView;
		if(layout == -1) {
			mMenuView = inflater.inflate(R.layout.camera_pic_popwindow, null);
		}else{
			mMenuView = inflater.inflate(layout, null);
		}
		
		bt_common_tag = (Button) mMenuView.findViewById(R.id.bt_common_tag);
		bt_brand_tag = (Button) mMenuView.findViewById(R.id.bt_brand_tag);
		bt_quit = (Button) mMenuView.findViewById(R.id.bt_quit);
		bt_common_tag.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onSeleteCommonTag(v);
			}
		});
		bt_quit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onExitClick(v);
			}
		});
		bt_brand_tag.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onSeleteBrandTag(v);
			}
		});
		return mMenuView;
	}
	public abstract void onExitClick(View v);
	public abstract void onSeleteBrandTag(View v);
	public abstract void onSeleteCommonTag(View v);
}
