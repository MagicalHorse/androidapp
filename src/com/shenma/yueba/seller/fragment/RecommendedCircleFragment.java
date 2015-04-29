package com.shenma.yueba.seller.fragment;

import java.util.List;

import android.os.Bundle;
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
import com.shenma.yueba.buyer.adapter.RecommendedCircleAdapter;
import com.shenma.yueba.buyer.fragment.BaseFragment;
import com.shenma.yueba.buyer.modle.RecommendedCircleBean;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
			if(view == null){
				
				view = inflater.inflate(R.layout.recommended_circle_fragment, null);
				gv_recommended_circles = (PullToRefreshGridView) view
						.findViewById(R.id.gv_recommended_circles);
				gv_recommended_circles.setMode(Mode.BOTH);
				gv_recommended_circles.setAdapter(new RecommendedCircleAdapter(
						getActivity(), mList));
				gv_recommended_circles
						.setOnRefreshListener(new OnRefreshListener2<GridView>() {

							@Override
							public void onPullDownToRefresh(
									PullToRefreshBase<GridView> refreshView) {
								Toast.makeText(getActivity(), "下拉", Toast.LENGTH_SHORT)
										.show();
								// new GetDataTask(mPullRefreshGridView, mAdapter,
								// mListItems).execute();
							}

							@Override
							public void onPullUpToRefresh(
									PullToRefreshBase<GridView> refreshView) {
								Toast.makeText(getActivity(), "上拉", Toast.LENGTH_SHORT)
										.show();
								// new GetDataTask(mPullRefreshGridView, mAdapter,
								// mListItems).execute();
							}

						});
				// FontManager.changeFonts(getActivity(), tv_mobile_title, et_mobile,
				// et_password, tv_forget, tv_other, tv_wechat, bt_login);
				
			}
			// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeView(view);
			}
			
			
			return view;
			
		}

}
