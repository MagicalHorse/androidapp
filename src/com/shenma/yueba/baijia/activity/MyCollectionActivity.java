package com.shenma.yueba.baijia.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListInfoBean;
import com.shenma.yueba.baijia.modle.RequestMyFavoriteProductListInfoBean;
import com.shenma.yueba.baijia.view.PubuliuManager;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;

/**  
 * 我的收藏
 * @author gyj  
 * @version 创建时间：2015-5-20 上午11:14:57  
 * 程序的简单说明  
 */

public class MyCollectionActivity extends BaseActivityWithTopView{
    HttpControl httpCntrol=new HttpControl();
    int currPage=Constants.CURRPAGE_VALUE;
	int pageSize=Constants.PAGESIZE_VALUE;
	boolean showDialog=true;
	PullToRefreshScrollView shop_main_layout_title_pulltorefreshscrollview;
	LinearLayout pubuliy_left_linearlayout,pubuliy_right_linearlayout;
	//瀑布流管理
	PubuliuManager pm;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		setContentView(R.layout.shop_main_layoutbak);
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
				
				MyCollectionActivity.this.finish();
			}
		});
		LinearLayout pubuliu_layout_include=(LinearLayout)findViewById(R.id.pubuliu_layout_include);
		pm=new PubuliuManager(MyCollectionActivity.this, pubuliu_layout_include);
		pubuliy_left_linearlayout=(LinearLayout)findViewById(R.id.pubuliy_left_linearlayout);
		pubuliy_right_linearlayout=(LinearLayout)findViewById(R.id.pubuliy_right_linearlayout);
		shop_main_layout_title_pulltorefreshscrollview=(PullToRefreshScrollView)findViewById(R.id.shop_main_layout_title_pulltorefreshscrollview);
		shop_main_layout_title_pulltorefreshscrollview.setMode(Mode.PULL_FROM_START);
		shop_main_layout_title_pulltorefreshscrollview.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				
				requestFalshData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				
				requestData();
			}
		});
	}
	
	void requestData()
	{
		shop_main_layout_title_pulltorefreshscrollview.setRefreshing();
		sendHttp(1);
	}
	
	void requestFalshData()
	{
		shop_main_layout_title_pulltorefreshscrollview.setRefreshing();
		currPage=Constants.CURRPAGE_VALUE;
		sendHttp(0);
	}
	
	
	/****
	 * 添加
	 * ***/
	void addData(MyFavoriteProductListInfoBean bean)
	{
		currPage++;
		pm.onaddData(bean.getItems());
		shop_main_layout_title_pulltorefreshscrollview.onRefreshComplete();
	}
	
	/****
	 * 刷新
	 * ***/
	void falshData(MyFavoriteProductListInfoBean bean)
	{
		currPage++;
		pubuliy_right_linearlayout.removeAllViews();
		pubuliy_left_linearlayout.removeAllViews();
		pm.onResher(bean.getItems());
		shop_main_layout_title_pulltorefreshscrollview.onRefreshComplete();
	}
	
        
    @Override
    protected void onResume() {
        super.onResume();
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
				shop_main_layout_title_pulltorefreshscrollview.onRefreshComplete();
				if(obj!=null && obj instanceof RequestMyFavoriteProductListInfoBean)
				{
					RequestMyFavoriteProductListInfoBean bean=(RequestMyFavoriteProductListInfoBean)obj;
					if (bean != null) {
						if(currPage==1)
						{
							if(bean.getData()==null)
						   {
								MyApplication.getInstance().showMessage(MyCollectionActivity.this, "还没有信息");
								return;
						   }
						}
						int totalPage = bean.getData().getTotalpaged();
						if (currPage >= totalPage) {
							shop_main_layout_title_pulltorefreshscrollview.setMode(Mode.PULL_FROM_START);
							MyApplication.getInstance().showMessage(MyCollectionActivity.this, "最后一页数据");
						} else {
							shop_main_layout_title_pulltorefreshscrollview.setMode(Mode.BOTH);
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
						MyApplication.getInstance().showMessage(MyCollectionActivity.this, "没有任何数据");
					}
				}
				
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				shop_main_layout_title_pulltorefreshscrollview.onRefreshComplete();
				MyApplication.getInstance().showMessage(MyCollectionActivity.this, msg);
			}
		},MyCollectionActivity.this );
	}
    
   
}
