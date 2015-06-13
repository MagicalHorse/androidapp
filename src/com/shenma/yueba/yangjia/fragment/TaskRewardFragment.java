package com.shenma.yueba.yangjia.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.fragment.BaseFragment;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.yangjia.activity.BoradRewardActivity;
import com.shenma.yueba.yangjia.activity.OpenRewardActivity;
import com.shenma.yueba.yangjia.activity.OrderRewardActivity;
import com.shenma.yueba.yangjia.activity.RewardDetailActivity;
import com.shenma.yueba.yangjia.adapter.TastRewardAdapter;
import com.shenma.yueba.yangjia.modle.TaskListItem;
import com.shenma.yueba.yangjia.modle.TastRewardListBackBean;

/**
 * 红榜奖励
 * 
 * @author a
 */
public class TaskRewardFragment extends BaseFragment {

	private List<TaskListItem> mList = new ArrayList<TaskListItem>();
	private View view;
	private ListView lv;
	private TastRewardAdapter adapter;
	private TextView  tv_top_title;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getTastReward();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			initView(inflater);
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
//		getIndexInfo(true);//联网获取数据
		return view;
	}

	private void initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.task_reward, null);
		tv_top_title = (TextView) view.findViewById(R.id.tv_top_title);
		tv_top_title.setVisibility(View.VISIBLE);
		tv_top_title.setText("首页");
		lv =  (ListView) view.findViewById(R.id.lv);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent(getActivity(), RewardDetailActivity.class);
				intent.putExtra("promotionId", mList.get(position).getId());
				startActivity(intent);
			}
		});
		FontManager.changeFonts(getActivity(), tv_top_title);
	}
	

	
	public void getTastReward(){
		HttpControl httpControl = new HttpControl();
		httpControl.getTaskRewardList(true, new HttpCallBackInterface() {
			@Override
			public void http_Success(Object obj) {
				TastRewardListBackBean bean = (TastRewardListBackBean) obj;
				if(bean.getData()!=null){
				mList.addAll(bean.getData());
				adapter = new TastRewardAdapter(getActivity(), mList);
				lv.setAdapter(adapter);
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				Toast.makeText(getActivity(), msg, 1000).show();
			}
		}, getActivity());
		
		
	}
	
	
	}

	
