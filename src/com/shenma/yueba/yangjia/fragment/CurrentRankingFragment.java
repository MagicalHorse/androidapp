package com.shenma.yueba.yangjia.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.fragment.BaseFragment;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.yangjia.adapter.CurrentRankingAdapter;
import com.shenma.yueba.yangjia.modle.CurrentRankingBean;

/**
 * 当前红榜的Fragment
 * 
 * @author a
 * 
 */
public class CurrentRankingFragment extends BaseFragment {

	private View view;
	private TextView tv_my_name;
	private TextView tv_ranking_count;
	private TextView tv_ranking_name;
	private TextView tv_top_sales_money;
	private TextView tv_top_sales_title;
	private ImageView iv_icon;
	private ListView lv_ranking_list;
	private CurrentRankingAdapter adapter;
	private List<CurrentRankingBean> mList = new ArrayList<CurrentRankingBean>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			initView(inflater);
			adapter = new CurrentRankingAdapter(getActivity(), mList);
			lv_ranking_list.setAdapter(adapter);
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}
	
	
	private void initView(LayoutInflater inflater){
		view = inflater.inflate(R.layout.current_ranking_layout, null);
		tv_my_name = (TextView) view.findViewById(R.id.tv_my_name);
		tv_ranking_count = (TextView) view.findViewById(R.id.tv_ranking_count);
		tv_ranking_name = (TextView) view.findViewById(R.id.tv_ranking_name);
		tv_top_sales_money = (TextView) view
				.findViewById(R.id.tv_top_sales_money);
		tv_top_sales_title = (TextView) view
				.findViewById(R.id.tv_top_sales_title);
		iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
		lv_ranking_list = (ListView) view.findViewById(R.id.lv_ranking_list);
		FontManager.changeFonts(getActivity(), tv_my_name,tv_ranking_count,tv_ranking_name,
				tv_top_sales_money,tv_top_sales_title);
	}

}
