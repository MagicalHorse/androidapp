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
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ListViewUtils;
import com.shenma.yueba.yangjia.adapter.OrderRewardAdapter;
import com.shenma.yueba.yangjia.modle.BroadRewardListBean;

/**
 * 开店奖励
 * 
 * @author a
 * 
 */
public class OpenRewardActivity extends BaseActivityWithTopView{

	private TextView tv_reward_title;
	private TextView tv_reward_introduce;
	private TextView tv_progress_content;
	private TextView tv_progress_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.open_reward);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setTitle("开店奖励");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OpenRewardActivity.this.finish();
			}
		});
		tv_reward_title = getView(R.id.tv_reward_title);
		tv_reward_introduce = getView(R.id.tv_reward_introduce);
		tv_progress_content = getView(R.id.tv_progress_content);
		tv_progress_title = getView(R.id.tv_progress_title);
		FontManager.changeFonts(mContext, tv_reward_title,
				tv_reward_introduce, tv_progress_content, tv_progress_title);
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


}
