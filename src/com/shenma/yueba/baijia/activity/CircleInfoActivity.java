package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.MyCircleInfoAdapter;
import com.shenma.yueba.baijia.modle.GridVIewItemBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.view.MyGridView;
import com.shenma.yueba.view.RoundImageView;

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
		setContentView(R.layout.circle_info_layout);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
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
		bean2.setName("张三");
		mList.add(new GridVIewItemBean());
		mList.add(new GridVIewItemBean());
		mList.add(bean2);
		mList.add(bean);
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
			
			break;
		case R.id.rl_qrcode://二维码查看
			
			break;
			
		default:
			break;
		}
	}
}
