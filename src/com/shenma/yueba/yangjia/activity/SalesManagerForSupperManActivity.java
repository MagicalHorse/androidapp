package com.shenma.yueba.yangjia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.receiver.SalesAdapter;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.yangjia.modle.SalesItemBean;

/**
 * 销售管理---达人
 * @author a
 *
 */
public class SalesManagerForSupperManActivity extends BaseActivityWithTopView {

	private PullToRefreshListView pull_refresh_list;
	private SalesAdapter salesAdapter;
	private List<SalesItemBean> mList = new ArrayList<SalesItemBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sales_manager_layout);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setTitle("销售管理");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SalesManagerForSupperManActivity.this.finish();
			}
		});
		
		pull_refresh_list = getView(R.id.pull_refresh_list);
		salesAdapter = new SalesAdapter(mContext, mList);
		pull_refresh_list.setAdapter(salesAdapter);
		FontManager.changeFonts(mContext, tv_top_title);
	}
}
