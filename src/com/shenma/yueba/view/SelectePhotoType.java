package com.shenma.yueba.view;



import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.shenma.yueba.R;
import com.shenma.yueba.util.pop.FacePopupWindow;

public abstract class SelectePhotoType {
	private FacePopupWindow menuWindow;
	private Activity activity;
	private View parent;
	private Button bt_quit;
	private Button bt_pic;
	private Button bt_camera;
	private int layout = -1;

	public SelectePhotoType(Activity activity, View parent) {
		this.activity = activity;
		this.parent = parent;
	}
	public SelectePhotoType(Activity activity, View parent,int layout) {
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
		
		bt_pic = (Button) mMenuView.findViewById(R.id.bt_pic);//���
		bt_camera = (Button) mMenuView.findViewById(R.id.bt_camera);//���
		bt_quit = (Button) mMenuView.findViewById(R.id.bt_quit);// �˳�
		bt_pic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onPic(v);
			}
		});
		bt_quit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onExitClick(v);
			}
		});
		bt_camera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onCamera(v);
			}
		});
		return mMenuView;
	}
	public abstract void onExitClick(View v);
	public abstract void onCamera(View v);
	public abstract void onPic(View v);
}
