package com.shenma.yueba.util.pop;


import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.shenma.yueba.R;

public abstract class SelectePublicType {
	private FacePopupWindow menuWindow;
	private Activity activity;
	private View parent;
	private Button bt_exit;
	private Button bt_logout;
	private Button bt_quit;
	private int layout = -1;

	public SelectePublicType(Activity activity, View parent) {
		this.activity = activity;
		this.parent = parent;
	}
	public SelectePublicType(Activity activity, View parent,int layout) {
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
	public void setTopBtnText(String txt){
		bt_exit.setText(txt);
	}
	public void setCenterBtnText(String txt){
		bt_logout.setText(txt);
	}
	public void setBottomBtnText(String txt){
		bt_quit.setText(txt);
	}
	public View viewPagewV() {
		LayoutInflater inflater = LayoutInflater.from(activity);
		View mMenuView;
		if(layout == -1) {
			mMenuView = inflater.inflate(R.layout.exit_popwindow, null);
		}else{
			mMenuView = inflater.inflate(layout, null);
		}
		
		bt_exit = (Button) mMenuView.findViewById(R.id.bt_exit);//
		bt_logout = (Button) mMenuView.findViewById(R.id.bt_logout);//
		bt_quit = (Button) mMenuView.findViewById(R.id.bt_quit);//
		bt_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onExitClick(v);
			}
		});
		bt_logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onLogoutClick(v);
			}
		});
		bt_quit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onCanceClick(v);
			}
		});
		return mMenuView;
	}
	public abstract void onExitClick(View v);
	public abstract void onLogoutClick(View v);
	public abstract void onCanceClick(View v);
}
