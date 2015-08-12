package com.shenma.yueba.yangjia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ListViewUtils;
import com.shenma.yueba.yangjia.adapter.BroadRewardAdapter;
import com.shenma.yueba.yangjia.modle.BroadRewardListBean;
import com.shenma.yueba.yangjia.modle.HistoryItem;
import com.shenma.yueba.yangjia.modle.RewardDetailBackBean;
import com.umeng.analytics.MobclickAgent;

/**
 * 奖励详情
 * 
 * @author a
 * 
 */
public class RewardDetailActivity extends BaseActivityWithTopView{

	private TextView tv_reward_title;
	private TextView tv_reward_introduce;
	private TextView tv_progress_content;
	private TextView tv_progress_title;
	private TextView tv_history_title;
	private ListView lv;
	private PullToRefreshScrollView pulltorefreshscrollview;
	private BroadRewardAdapter adapter;
	private List<HistoryItem> mList = new ArrayList<HistoryItem>();
	private String promotionId;//奖励id
	private TextView tv_nodata;
	private String titleName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reward_detail_layout);
		super.onCreate(savedInstanceState);
		promotionId = getIntent().getStringExtra("promotionId");
		titleName = getIntent().getStringExtra("titleName");
		initView();
		getRewardDetail();
	}

	private void initView() {
		setTitle(titleName!=null?titleName:"");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RewardDetailActivity.this.finish();
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
		tv_nodata = getView(R.id.tv_nodata);
		tv_reward_title = getView(R.id.tv_reward_title);
		tv_reward_introduce = getView(R.id.tv_reward_introduce);
		tv_progress_content = getView(R.id.tv_progress_content);
		tv_progress_title = getView(R.id.tv_progress_title);
		tv_history_title = getView(R.id.tv_history_title);
		lv = getView(R.id.lv);
		adapter = new BroadRewardAdapter(RewardDetailActivity.this, mList);
		lv.setAdapter(adapter);
		ListViewUtils.setListViewHeightBasedOnChildren(lv);
		FontManager.changeFonts(mContext, tv_reward_title,
				tv_reward_introduce, tv_progress_content, tv_progress_title, tv_history_title,tv_nodata,tv_top_title);
	}

	
	
	
	
	public void getRewardDetail() {
		HttpControl httpContorl = new HttpControl();
		httpContorl.getTaskRewardDetail(promotionId, true, new HttpCallBackInterface() {
			@Override
			public void http_Success(Object obj) {
				RewardDetailBackBean bean = (RewardDetailBackBean) obj;
				if(bean.getData()!=null){
					String desc = bean.getData().getDesc();
					String tip = bean.getData().getTip();
					tv_reward_introduce.setText(desc);
					tv_progress_content.setText(tip);
					if(bean.getData().getHistory()!=null && bean.getData().getHistory().size()!=0){
						tv_nodata.setVisibility(View.GONE);
						mList.addAll(bean.getData().getHistory());
						adapter.notifyDataSetChanged();
						ListViewUtils.setListViewHeightBasedOnChildren(lv);
					}else{
						tv_nodata.setVisibility(View.VISIBLE);
					}
				
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				// TODO Auto-generated method stub
				
			}
		}, RewardDetailActivity.this);
	}


	@Override
	protected void onDestroy() {
		MyApplication.getInstance().addActivity(this);
		super.onDestroy();
	}
	
	
	  public void onResume() {
			super.onResume();
			MobclickAgent.onResume(this);
			}
			public void onPause() {
			super.onPause();
			MobclickAgent.onPause(this);
			}
	
}
