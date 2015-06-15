package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.FansListAdapter;
import com.shenma.yueba.baijia.modle.FansListBean;


/**
 * 粉丝列表
 * @author a
 *
 */
public class FansActivity extends BaseActivityWithTopView {
	private FansListAdapter brandAdapter;
	private List<FansListBean> mList = new ArrayList<FansListBean>();
	private View view;
	private PullToRefreshListView pull_refresh_list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.refresh_listview_with_title_layout);
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		initView();
	}

	private void initView() {
		pull_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		pull_refresh_list.setMode(Mode.BOTH);
		pull_refresh_list.setAdapter(new FansListAdapter(this, mList));
		
	}
}
