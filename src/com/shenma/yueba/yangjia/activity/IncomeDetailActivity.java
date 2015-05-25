package com.shenma.yueba.yangjia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.yangjia.adapter.IncomeDetailAdapter;
import com.shenma.yueba.yangjia.modle.IncomeDetailListBean;

/**
 * 收入明细
 * 
 * @author a
 * 
 */
public class IncomeDetailActivity extends BaseActivityWithTopView {

	private TextView tv_total_income_title;
	private TextView tv_total_income;
	private ImageView iv_help;
	private PullToRefreshListView pull_refresh_list;
	private IncomeDetailAdapter  adapter;
	private List<IncomeDetailListBean> mList = new ArrayList<IncomeDetailListBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.income_detail_layout);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setTitle("收入明细");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		iv_help = (ImageView) findViewById(R.id.iv_help);
		tv_total_income_title = (TextView) findViewById(R.id.tv_total_income_title);
		tv_total_income = (TextView) findViewById(R.id.tv_total_income);
		pull_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		adapter = new IncomeDetailAdapter(mContext, mList);
		pull_refresh_list.setAdapter(adapter);
		FontManager.changeFonts(mContext, tv_total_income_title);
	
	
	}
}
