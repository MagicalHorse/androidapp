package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.MyCircleInfoAdapter;
import com.shenma.yueba.baijia.dialog.QRCodeShareDialog;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.MyGridView;
import com.shenma.yueba.view.RoundImageView;
import com.shenma.yueba.yangjia.activity.ModifyCircleNameActivity;
import com.shenma.yueba.yangjia.modle.CircleDetailBackBean;
import com.shenma.yueba.yangjia.modle.CircleDetailBean;
import com.shenma.yueba.yangjia.modle.Users;

/**
 * 圈子信息
 * 
 * @author a
 * 
 */

public class CircleInfoActivity extends BaseActivityWithTopView implements OnClickListener {

	private TextView tv_cirlce_head_title;
	private TextView tv_cirlce_name_title;
	private TextView tv_circle_title;
	private TextView tv_circle_name;
	private RoundImageView riv_circle_head;
	private MyGridView gv_circle;
	private MyCircleInfoAdapter adapter;
	private List<Users> mList = new ArrayList<Users>();
	private RelativeLayout rl_head;
	private RelativeLayout rl_circle_name;
	private RelativeLayout rl_qrcode;
	private String cricleId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.circle_info_layout);
		super.onCreate(savedInstanceState);
		initView();
		getIntentData();
		getCircleDetail(cricleId, mContext, true);
	}

	private void getIntentData() {
		cricleId = getIntent().getStringExtra("circleId");
		
	}

	private void initView() {
		setTitle("圈子信息");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		gv_circle = getView(R.id.gv_circle);
		riv_circle_head = getView(R.id.riv_circle_head);
		tv_cirlce_head_title = getView(R.id.tv_cirlce_head_title);
		tv_cirlce_name_title = getView(R.id.tv_cirlce_name_title);
		tv_circle_title = getView(R.id.tv_circle_title);
		tv_circle_name = getView(R.id.tv_circle_name);
		rl_head = getView(R.id.rl_head);
		rl_head.setOnClickListener(this);
		rl_circle_name = getView(R.id.rl_circle_name);
		rl_circle_name.setOnClickListener(this);
		rl_qrcode = getView(R.id.rl_qrcode);
		rl_qrcode.setOnClickListener(this);
		adapter = new MyCircleInfoAdapter(mContext, mList);
		gv_circle.setAdapter(adapter);
		FontManager.changeFonts(mContext, tv_cirlce_head_title,tv_cirlce_name_title,
				tv_circle_title,tv_circle_name);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_head://更改头像
			
			break;
		case R.id.rl_circle_name://更改姓名
			Intent modifyCircleNameIntent = new Intent(this,ModifyCircleNameActivity.class);
			modifyCircleNameIntent.putExtra("circleId", cricleId);
			modifyCircleNameIntent.putExtra("name", tv_circle_name.getText().toString().trim());
			startActivity(modifyCircleNameIntent);
			break;
		case R.id.rl_qrcode://二维码查看
			/*ArrayList<String> urlList = new ArrayList<String>();
			if(urlList.size() == 0){
				Toast.makeText(mContext, "暂无大图展示", 1000).show();
				return ;
			}*/
			QRCodeShareDialog dialog=new QRCodeShareDialog(CircleInfoActivity.this, null);
			dialog.show();
			/*Intent imageShowIntent = new Intent(mContext, ImageShowActivity.class);
			imageShowIntent.putStringArrayListExtra(ImageShowActivity.BIGIMAGES,urlList);
			imageShowIntent.putExtra(ImageShowActivity.IMAGE_INDEX, 0);
			startActivity(imageShowIntent);*/
			break;
			
		default:
			break;
		}
	}
	
	
	/**
	 * 获取圈子详情
	 * @param ctx
	 * @param isRefresh
	 * @param showDialog
	 */
	public void getCircleDetail(String circleId,Context ctx,boolean showDialog){
		HttpControl httpControl = new HttpControl();
		httpControl.getCircleDetail(circleId, showDialog, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				CircleDetailBackBean result = (CircleDetailBackBean) obj;
				CircleDetailBean bean = result.getData();
				if(bean!=null){
					tv_circle_name.setText(ToolsUtil.nullToString(bean.getGroupName()));
					MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.getImage(ToolsUtil.nullToString(bean.getGroupPic()), 120, 0), riv_circle_head);
					List<Users> users = bean.getUsers();
					if(users!=null && users.size()>0){
						mList.addAll(users);
						mList.addAll(users);
						mList.addAll(users);
						mList.add(new Users());
						mList.add(new Users());
						adapter.notifyDataSetChanged();
					}
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				Toast.makeText(mContext, msg, 1000).show();
			}
		}, CircleInfoActivity.this);
	}
}
