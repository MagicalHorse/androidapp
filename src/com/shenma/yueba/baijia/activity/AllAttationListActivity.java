package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.AttationListAdapter;
import com.shenma.yueba.baijia.modle.AttationListBean;
import com.shenma.yueba.util.FontManager;


/**
 * 全部关注列表
 * @author a
 *
 */
public class AllAttationListActivity extends BaseActivityWithTopView implements OnClickListener {
	private AttationListAdapter brandAdapter;
	private List<AttationListBean> mList = new ArrayList<AttationListBean>();
	private View view;
	private PullToRefreshListView pull_refresh_list;
	private LinearLayout ll_top_out;
	private View search_view;
	private PopupWindow popupWindow;
	private TextView tv_all_attation_title;
	private TextView tv_all_attation_count;
	private TextView tv_all_friend_title;
	private TextView tv_all_friend_count;
	private TextView tv_zhuangui_title;
	private TextView tv_zhuangui_count;
	private TextView tv_renzheng_title;
	private TextView tv_renzheng_count;
	private TextView tv_other_title;
	private TextView tv_other_count;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.refresh_listview_with_title_layout);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setTitle("全部关注");
		Drawable drawable= getResources().getDrawable(R.drawable.arrow_down);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		tv_top_title.setCompoundDrawables(drawable,null,null,null);
		tv_top_title.setOnClickListener(this);
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AllAttationListActivity.this.finish();
			}
		});
		ll_top_out = (LinearLayout) findViewById(R.id.ll_top_out);
		search_view = View.inflate(AllAttationListActivity.this, R.layout.search_item, null);
		ll_top_out.addView(search_view);
		pull_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		pull_refresh_list.setMode(Mode.BOTH);
		pull_refresh_list.setAdapter(new AttationListAdapter(this, mList));
		initPopwindow();
		FontManager.changeFonts(this, tv_top_title);
	}

	private void initPopwindow() {
		LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View menuView = (View)mLayoutInflater.inflate(R.layout.attation_pop_layout, null, true);//弹出窗口包含的视图
		tv_all_attation_title = (TextView) menuView.findViewById(R.id.tv_all_attation_title);
		tv_all_attation_count = (TextView) menuView.findViewById(R.id.tv_all_attation_count);
		tv_all_friend_title = (TextView) menuView.findViewById(R.id.tv_all_friend_title);
		tv_all_friend_count = (TextView) menuView.findViewById(R.id.tv_all_friend_count);
		tv_zhuangui_title = (TextView) menuView.findViewById(R.id.tv_zhuangui_title);
		tv_zhuangui_count = (TextView) menuView.findViewById(R.id.tv_zhuangui_count);
		tv_renzheng_title = (TextView) menuView.findViewById(R.id.tv_renzheng_title);
		tv_renzheng_count = (TextView) menuView.findViewById(R.id.tv_renzheng_count);
		tv_other_title = (TextView) menuView.findViewById(R.id.tv_other_title);
		tv_other_count = (TextView) menuView.findViewById(R.id.tv_other_count);
		FontManager.changeFonts(this, tv_all_attation_title,tv_all_attation_count,tv_all_friend_title,
				tv_all_friend_count,tv_zhuangui_title,tv_zhuangui_count,tv_renzheng_title,tv_renzheng_count,
				tv_other_title,tv_other_count
				);
		popupWindow = new PopupWindow(menuView, LayoutParams.FILL_PARENT,238, true);
		popupWindow.setAnimationStyle(R.style.Animation_dropdown);//设置窗口显示的动画效果
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_top_title://头部
			if(popupWindow!=null){
				if(popupWindow.isShowing()){
					popupWindow.dismiss();
				}else{
					popupWindow.showAtLocation(findViewById(R.id.parent), Gravity.BOTTOM, 0, 0);//设置窗口显示的位置
				}
			}else{
				initPopwindow();
				if(popupWindow.isShowing()){
					popupWindow.dismiss();
				}else{
					popupWindow.showAtLocation(findViewById(R.id.parent), Gravity.BOTTOM, 0, 0);//设置窗口显示的位置
				}
			}
		default:
			break;
		}
		
	}
	
}
