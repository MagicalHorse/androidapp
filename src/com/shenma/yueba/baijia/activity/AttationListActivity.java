package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.AttationListAdapter;
import com.shenma.yueba.baijia.modle.AttationListBean;
import com.shenma.yueba.baijia.modle.FansListBean;


/**
 * 关注列表
 * @author a
 *
 */
public class AttationListActivity extends BaseActivityWithTopView {
	private AttationListAdapter brandAdapter;
	private List<AttationListBean> mList = new ArrayList<AttationListBean>();
	private View view;
	private PullToRefreshListView pull_refresh_list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.refresh_listview_with_title_layout);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		pull_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		pull_refresh_list.setMode(Mode.BOTH);
		pull_refresh_list.setAdapter(new AttationListAdapter(this, mList));
		
	}
	
	
	@Override
	protected void onDestroy() {
		MyApplication.getInstance().removeActivity(this);//加入回退栈
		super.onDestroy();
	}
}
