package com.shenma.yueba.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.adapter.MsgAdapter;
import com.shenma.yueba.modle.MsgBean;
import com.shenma.yueba.util.FontManager;


/**
 * 消息列表Fragment
 * @author a
 *
 */
public class MsgListFragment extends BaseFragment {
	private MsgAdapter msgAdapter;
	private List<MsgBean> mList = new ArrayList<MsgBean>();
	private View view;
	private PullToRefreshListView pull_refresh_list;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		

		if (view == null) {
			view = inflater.inflate(R.layout.msg_list_layout, null);
			pull_refresh_list = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
			pull_refresh_list.setMode(Mode.BOTH);
			pull_refresh_list.setAdapter(new MsgAdapter(getActivity(), mList));
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}
	
	
}
