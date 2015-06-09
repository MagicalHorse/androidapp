package com.shenma.yueba.baijia.activity;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import me.maxwin.view.XListViewHeader;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.android.bitmapfun.util.ImageFetcher;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.StaggeredAdapter;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListInfoBean;
import com.shenma.yueba.baijia.modle.RequestMyFavoriteProductListInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;

/**  
 * 我的收藏
 * @author gyj  
 * @version 创建时间：2015-5-20 上午11:14:57  
 * 程序的简单说明  
 */

public class MyCollectionActivitybak extends BaseActivityWithTopView{
	XListView xListView;
	private ImageFetcher mImageFetcher;
    private StaggeredAdapter mAdapter = null;
    HttpControl httpCntrol=new HttpControl();
    int currPage=Constants.CURRPAGE_VALUE;
	int pageSize=Constants.PAGESIZE_VALUE;
	boolean showDialog=true;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		setContentView(R.layout.shop_main_layout);
		super.onCreate(savedInstanceState);
		initView();
		requestFalshData();
	}
	
	void initView()
	{
		setTitle("收藏");
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				MyCollectionActivitybak.this.finish();
			}
		});
		
		xListView=(XListView)findViewById(R.id.xListView);
		xListView.addHeaderView(new XListViewHeader(MyCollectionActivitybak.this));
		xListView.setPullLoadEnable(true);
		xListView.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {//刷新
				requestFalshData();
			}
			
			@Override
			public void onLoadMore() {
				requestData();
			}
		});

        mImageFetcher = new ImageFetcher(this, 240);
        mImageFetcher.setLoadingImage(R.drawable.empty_photo);
        mAdapter = new StaggeredAdapter(this, xListView,mImageFetcher);

	}
	
	void requestData()
	{
		sendHttp(1);
	}
	
	void requestFalshData()
	{
		currPage=Constants.CURRPAGE_VALUE;
		sendHttp(0);
	}
	
	void addData(MyFavoriteProductListInfoBean bean)
	{
		currPage++;
		mAdapter.addItemLast(bean.getItems());
		mAdapter.notifyDataSetChanged();
		xListView.stopRefresh();
	}
	
	void falshData(MyFavoriteProductListInfoBean bean)
	{
		currPage++;
		mAdapter.addItemTop(bean.getItems());
        mAdapter.notifyDataSetChanged();
		xListView.stopLoadMore();
		
	}
	
        
    @Override
    protected void onResume() {
        super.onResume();
        mImageFetcher.setExitTasksEarly(false);
    }
    
    /***
     * 加载数据
     * @param type int 0: 刷新  1:加载更多
     * **/
    void sendHttp(final int type)
	{
		httpCntrol.getMyFavoriteProductList(currPage, pageSize, showDialog, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				if(obj!=null && obj instanceof RequestMyFavoriteProductListInfoBean)
				{
					RequestMyFavoriteProductListInfoBean bean=(RequestMyFavoriteProductListInfoBean)obj;
					if (bean != null) {
						if(currPage==1)
						{
							if(bean.getData()==null)
						   {
								MyApplication.getInstance().showMessage(MyCollectionActivitybak.this, "还没有信息");
								return;
						   }
						}
						int totalPage = bean.getData().getTotalpaged();
						if (currPage >= totalPage) {
							//baijia_quanzi_layout_tanb1_gridbview.setMode(Mode.PULL_FROM_START);
						} else {
							//baijia_quanzi_layout_tanb1_gridbview.setMode(Mode.BOTH);
						}
						switch (type) {
						case 0:
							falshData(bean.getData());
							break;
						case 1:
							addData(bean.getData());
							break;
						}
					} else {
						MyApplication.getInstance().showMessage(MyCollectionActivitybak.this, "没有任何数据");
					}
				}
				
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(MyCollectionActivitybak.this, msg);
			}
		},MyCollectionActivitybak.this );
	}
}
