package com.shenma.yueba.yangjia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.yangjia.adapter.IdentificationBuyerAdapter;
import com.shenma.yueba.yangjia.modle.IdentificationBuyerListBean;

/**
 * 已关注的认证买手的界面
 * @author a
 *
 */

public class IdentificationBuyerListActivity extends BaseActivityWithTopView {
	private ListView lv;
	private IdentificationBuyerAdapter adapter;
	private List<IdentificationBuyerListBean> list = new ArrayList<IdentificationBuyerListBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.identificaiton_buyer_list_layout);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setTitle("已关注认证买手");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				IdentificationBuyerListActivity.this.finish();
			}
		});
		lv = (ListView) findViewById(R.id.lv);
		adapter = new IdentificationBuyerAdapter(this, list);
		lv.setAdapter(adapter);
	}
}
