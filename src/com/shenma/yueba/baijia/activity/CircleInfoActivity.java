package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.MyCircleInfoAdapter;
import com.shenma.yueba.baijia.modle.GridVIewItemBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.view.MyGridView;
import com.shenma.yueba.view.RoundImageView;
import com.shenma.yueba.view.imageshow.ImageShowActivity;
import com.shenma.yueba.yangjia.activity.ModifyCircleNameActivity;

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
	private List<GridVIewItemBean> mList = new ArrayList<GridVIewItemBean>();
	private RelativeLayout rl_head;
	private RelativeLayout rl_circle_name;
	private RelativeLayout rl_qrcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.circle_info_layout);
		super.onCreate(savedInstanceState);
		initView();
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
		GridVIewItemBean bean = new GridVIewItemBean();
		bean.setHead("http://img1.imgtn.bdimg.com/it/u=3176667768,1161431363&fm=21&gp=0.jpg");
		bean.setName("张三");
		GridVIewItemBean bean2 = new GridVIewItemBean();
		bean2.setHead("http://img1.imgtn.bdimg.com/it/u=3176667768,1161431363&fm=21&gp=0.jpg");
		bean2.setName("张三2");
		GridVIewItemBean bean3 = new GridVIewItemBean();
		bean3.setHead("http://img1.imgtn.bdimg.com/it/u=3176667768,1161431363&fm=21&gp=0.jpg");
		bean3.setName("张3");
		mList.add(bean2);
		mList.add(bean);
		mList.add(bean3);
		mList.add(new GridVIewItemBean());
		mList.add(new GridVIewItemBean());
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
			startActivity(modifyCircleNameIntent);
			break;
		case R.id.rl_qrcode://二维码查看
			ArrayList<String> urlList = new ArrayList<String>();
			if(urlList.size() == 0){
				Toast.makeText(mContext, "暂无大图展示", 1000).show();
				return ;
			}
			Intent imageShowIntent = new Intent(mContext, ImageShowActivity.class);
			imageShowIntent.putStringArrayListExtra(ImageShowActivity.BIGIMAGES,urlList);
			imageShowIntent.putExtra(ImageShowActivity.IMAGE_INDEX, 0);
			startActivity(imageShowIntent);
			break;
			
		default:
			break;
		}
	}
}
