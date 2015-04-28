package com.shenma.yueba.activity;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.shenma.yueba.R;

/**
 * 需要集成下拉刷新的activity需要继承的基类
 * 
 * @author a
 * 
 */
public abstract class BasePullToRefreshActivity extends BaseActivityWithTopView {
	protected PullToRefreshListView pull_refresh_list;
	protected LinearLayout ll_top;
	protected LinearLayout ll_bottom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.xlistview_layout);
		super.onCreate(savedInstanceState);
		ll_top = getView(R.id.ll_top);
		ll_bottom = getView(R.id.ll_bottom);
		setTopView(ll_top);
		setBottomView(ll_bottom);
		
		pull_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		pull_refresh_list.setMode(Mode.BOTH);
		pull_refresh_list.setShowIndicator(false);// 不显示右侧指针
//		 pull_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
//		 pull_refresh_list.setShowIndicator(false);// 不显示右侧指针
//		 pull_refresh_list.setMode(Mode.BOTH);// 下拉刷新，上拉刷新都可以
		/**
		 * 设置下拉刷新和上拉加载时的 铃声（可有可无）
		 */

		pull_refresh_list
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
					}
				});

		SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(
				this);
		soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
		soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
		soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
		pull_refresh_list.setOnPullEventListener(soundListener);
		pull_refresh_list.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				onRefreshData(refreshView);

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				onLoadMoreData(refreshView);

			}
		});
	}

	/**
	 * 添加headView
	 * 
	 * @param ll_top
	 */
	protected abstract void setTopView(LinearLayout ll_top);

	/**
	 * 添加footView
	 * 
	 * @param ll_bottom
	 */
	protected abstract void setBottomView(LinearLayout ll_bottom);

	/**
	 * //下拉刷新
	 */
	protected abstract void onRefreshData(
			PullToRefreshBase<ListView> refreshView);

	/**
	 * //加载更多
	 */
	protected abstract void onLoadMoreData(
			PullToRefreshBase<ListView> refreshView);

}
