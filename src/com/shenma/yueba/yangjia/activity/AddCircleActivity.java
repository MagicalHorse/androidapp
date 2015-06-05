package com.shenma.yueba.yangjia.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.view.RoundImageView;

/**
 * 新增圈子
 * @author a
 *
 */
public class AddCircleActivity extends BaseActivityWithTopView implements OnClickListener{
	
	private TextView tv_cirlce_head_title;
	private TextView tv_cirlce_name_title;
	private EditText et_circle_name;
	private RoundImageView riv_circle_head;
	private RelativeLayout rl_head;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_circle_layout);
		initView();
		super.onCreate(savedInstanceState);
	}
	
	
	private void initView() {
		tv_cirlce_head_title = getView(R.id.tv_cirlce_head_title);
		tv_cirlce_name_title = getView(R.id.tv_cirlce_name_title);
		et_circle_name = getView(R.id.et_circle_name);
		riv_circle_head = getView(R.id.riv_circle_head);
		rl_head = getView(R.id.rl_head);
		rl_head.setOnClickListener(this);
		FontManager.changeFonts(mContext, tv_cirlce_head_title,tv_cirlce_name_title,
				et_circle_name,riv_circle_head,rl_head);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
