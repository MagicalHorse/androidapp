package com.shenma.yueba.yangjia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ListViewUtils;
import com.shenma.yueba.yangjia.adapter.OrderRewardAdapter;
import com.shenma.yueba.yangjia.modle.BroadRewardListBean;

/**
 * 冲单奖励
 * 
 * @author a
 * 
 */
public class OrderRewardActivity extends BaseActivityWithTopView{

	private TextView tv_reward_title;
	private TextView tv_reward_introduce;
	private TextView tv_progress_content;
	private TextView tv_progress_title;
	private TextView tv_history_title;
	private ListView lv;
	private PullToRefreshScrollView pulltorefreshscrollview;
	private OrderRewardAdapter adapter;
	private List<BroadRewardListBean> mList = new ArrayList<BroadRewardListBean>();
	private LinearLayout ll_reward_detail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		MyApplication.getInstance().addActivity(this);//加入回退栈
		setContentView(R.layout.order_reward);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setTitle("冲单奖励");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OrderRewardActivity.this.finish();
			}
		});
		pulltorefreshscrollview = getView(R.id.pulltorefreshscrollview);
		pulltorefreshscrollview.setMode(Mode.PULL_UP_TO_REFRESH);
		pulltorefreshscrollview.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				Toast.makeText(mContext, "加载更多", 1000).show();
				mList.addAll(mList);
				adapter.notifyDataSetChanged();
				ListViewUtils.setListViewHeightBasedOnChildren(lv);
				pulltorefreshscrollview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    	pulltorefreshscrollview.onRefreshComplete();
                    }
            }, 1000);
			}
		});
		tv_reward_title = getView(R.id.tv_reward_title);
		ll_reward_detail = getView(R.id.ll_reward_detail);
		tv_reward_introduce = getView(R.id.tv_reward_introduce);
		tv_progress_content = getView(R.id.tv_progress_content);
		tv_progress_title = getView(R.id.tv_progress_title);
		tv_history_title = getView(R.id.tv_history_title);
		lv = getView(R.id.lv);
		for (int i = 0; i < 10; i++) {
			BroadRewardListBean bean = new BroadRewardListBean();
			bean.setContent("contentn");
			bean.setTime("2012/02/03");
			mList.add(bean);
		}
		adapter = new OrderRewardAdapter(OrderRewardActivity.this, mList);
		lv.setAdapter(adapter);
		ListViewUtils.setListViewHeightBasedOnChildren(lv);
		FontManager.changeFonts(mContext, tv_reward_title,
				tv_reward_introduce, tv_progress_content, tv_progress_title, tv_history_title);
	}

	
//	public void createCircle() {
//		HttpControl httpContorl = new HttpControl();
//		httpContorl.createCircle(et_circle_name.getText().toString().trim(),
//				littlePicPath_cache.substring(
//						littlePicPath_cache.lastIndexOf("/") + 1,
//						littlePicPath_cache.length()), false,
//				new HttpCallBackInterface() {
//
//					@Override
//					public void http_Success(Object obj) {
//						Toast.makeText(mContext, "创建成功", 1000).show();
//						setResult(Constants.RESULTCODE);
//						BoradRewardActivity.this.finish();
//					}
//
//					@Override
//					public void http_Fails(int error, String msg) {
//					
//						Toast.makeText(mContext, msg, 1000).show();
//
//					}
//				}, mContext.getApplicationContext());
//	}


	@Override
	protected void onDestroy() {
		MyApplication.getInstance().addActivity(this);
		super.onDestroy();
	}
	
}
