package com.shenma.yueba.baijia.activity;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.ProductTagsListAdapter;
import com.shenma.yueba.baijia.adapter.RecommendedCircleAdapter;
import com.shenma.yueba.baijia.modle.RecommendedCircleBean;
import com.shenma.yueba.baijia.modle.TagListBackBean;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;


/**
 * 商品标签列表
 * @author a
 *
 */
public class ProductsTagsListActivity extends BaseActivityWithTopView {
	
	private PullToRefreshGridView gv_recommended_circles;
	private List<TagListBackBean> mList;
	private View view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	setContentView(R.layout.product_tags_list_layout);
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		
		gv_recommended_circles = (PullToRefreshGridView) view
				.findViewById(R.id.gv_recommended_circles);
		gv_recommended_circles.setMode(Mode.BOTH);
		gv_recommended_circles.setAdapter(new ProductTagsListAdapter(
				ProductsTagsListActivity.this, mList));
		gv_recommended_circles
				.setOnRefreshListener(new OnRefreshListener2<GridView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<GridView> refreshView) {
						Toast.makeText(ProductsTagsListActivity.this, "下拉", Toast.LENGTH_SHORT)
								.show();
						// new GetDataTask(mPullRefreshGridView, mAdapter,
						// mListItems).execute();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<GridView> refreshView) {
						Toast.makeText(ProductsTagsListActivity.this, "上拉", Toast.LENGTH_SHORT)
								.show();
						// new GetDataTask(mPullRefreshGridView, mAdapter,
						// mListItems).execute();
					}

				});
	}
	
	



}
