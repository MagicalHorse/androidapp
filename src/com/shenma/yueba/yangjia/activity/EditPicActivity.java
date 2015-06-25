package com.shenma.yueba.yangjia.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;

/**
 * 编辑图片
 * 
 * @author a
 * 
 */
public class EditPicActivity extends BaseActivityWithTopView implements
		OnClickListener {

	private ImageView iv_pic;
	private TextView tv_tishi;
	private TextView tv_next;
	private LinearLayout images_container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.edit_pic);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		iv_pic = getView(R.id.iv_pic);
		tv_tishi = getView(R.id.tv_tishi);
		images_container = getView(R.id.images_container);
		tv_next = getView(R.id.tv_next);
		tv_next.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_next:// 下一步
			skip(AddTagActivity.class, false);
			break;

		default:
			break;
		}
	}
}
