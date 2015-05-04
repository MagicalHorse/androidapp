package com.shenma.yueba.yangjia.fragment;

import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.RecommendedCircleAdapter;
import com.shenma.yueba.baijia.fragment.BaseFragment;
import com.shenma.yueba.baijia.modle.RecommendedCircleBean;

/**
 * 推荐的圈子
 * 
 * @author a
 * 
 */
public class RecommendedCircleFragment extends BaseFragment {
	private PullToRefreshGridView gv_recommended_circles;
	private List<RecommendedCircleBean> mList;
	private View view;
	private Handler handler;
	private ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		dialog = new ProgressDialog(getActivity());
		dialog.show();
		super.onCreate(savedInstanceState);
	}

	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		gv_recommended_circles.setAdapter(new RecommendedCircleAdapter(
				getActivity(), mList));
		dialog.dismiss();
		super.onActivityCreated(savedInstanceState);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
			if(view == null){
				
				view = inflater.inflate(R.layout.recommended_circle_fragment, null);
				gv_recommended_circles = (PullToRefreshGridView) view
						.findViewById(R.id.gv_recommended_circles);
				gv_recommended_circles.setMode(Mode.BOTH);
			}
			// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeView(view);
			}
			
			
			return view;
			
		}

}
