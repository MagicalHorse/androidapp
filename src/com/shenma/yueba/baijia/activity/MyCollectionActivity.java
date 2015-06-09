package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListInfo;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListInfoBean;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListLikeUser;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListPic;
import com.shenma.yueba.baijia.modle.RequestMyFavoriteProductListInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;
import com.umeng.socialize.utils.Log;

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
	int leftHeight;
	int rightHeight;
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
		pubuliy_left_linearlayout=(LinearLayout)findViewById(R.id.pubuliy_left_linearlayout);
		pubuliy_right_linearlayout=(LinearLayout)findViewById(R.id.pubuliy_right_linearlayout);
		shop_main_layout_title_pulltorefreshscrollview=(PullToRefreshScrollView)findViewById(R.id.shop_main_layout_title_pulltorefreshscrollview);
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


       // mImageFetcher.setLoadingImage(R.drawable.empty_photo);

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
	
	void addData(MyFavoriteProductListInfoBean bean)
	{
		currPage++;
		addItem(bean.getItems());
		shop_main_layout_title_pulltorefreshscrollview.onRefreshComplete();
	}
	
	void falshData(MyFavoriteProductListInfoBean bean)
	{
		List<MyFavoriteProductListInfo> items=new ArrayList<MyFavoriteProductListInfo>();
		for(int i=0;i<10;i++)
		{
			MyFavoriteProductListInfo info=new MyFavoriteProductListInfo();
			MyFavoriteProductListLikeUser likeUser=new MyFavoriteProductListLikeUser();
			likeUser.setCount(10);
			info.setLikeUser(likeUser);
			info.setName("name"+i);
			MyFavoriteProductListPic pic=new MyFavoriteProductListPic();
			pic.setPic("http://f.hiphotos.baidu.com/image/pic/item/6f061d950a7b0208f6ad99ef60d9f2d3572cc85c.jpg");
			Random random=new Random();
			int a=random.nextInt(10);
			Log.i("TAG", "a="+a);
			pic.setRatio(a);
			info.setPic(pic);
			info.setPrice(0.00);
			items.add(info);
		}
		bean.setItems(items);
		currPage++;
		pubuliy_right_linearlayout.removeAllViews();
		pubuliy_left_linearlayout.removeAllViews();
		addItem(bean.getItems());
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
    
    
    void addItem(List<MyFavoriteProductListInfo> item)
    {
    	if(item!=null)
    	{
    		for(int i=0;i<item.size();i++)
    		{
    			MyFavoriteProductListInfo bean=item.get(i);
    			int witdh=pubuliy_left_linearlayout.getWidth();
    			MyFavoriteProductListPic myFavoriteProductListPic=bean.getPic();
    			int height=(int)(witdh*myFavoriteProductListPic.getRatio());
    			Log.i("TAG", "height="+height +" witdh="+witdh   +"ration="+myFavoriteProductListPic.getRatio());
    			View parentview=MyCollectionActivity.this.getLayoutInflater().inflate(R.layout.pubuliu_item_layout, null);
    			//价格
    			TextView pubuliu_item_layout_pricevalue_textview=(TextView)parentview.findViewById(R.id.pubuliu_item_layout_pricevalue_textview);
    			pubuliu_item_layout_pricevalue_textview.setText(bean.getPrice()+"");
    			//商品名称
    			TextView pubuliu_item_layout_name_textview=(TextView)parentview.findViewById(R.id.pubuliu_item_layout_name_textview);
    			pubuliu_item_layout_name_textview.setText(bean.getName());
    			//喜欢
    			TextView pubuliu_item_layout_like_textview=(TextView)parentview.findViewById(R.id.pubuliu_item_layout_like_textview);
    			if(bean.getLikeUser()!=null)
    			{
    				MyFavoriteProductListLikeUser likeuser=bean.getLikeUser();
    				pubuliu_item_layout_like_textview.setText(likeuser.getCount()+"");
    			}
    			ImageView iv=(ImageView)parentview.findViewById(R.id.pubuliu_item_layout_imageview);
    			iv.getLayoutParams().height=height;
    			iv.setBackgroundResource(R.color.color_blue);
    			iv.setTag(bean);
    			iv.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						MyApplication.getInstance().showMessage(MyCollectionActivity.this, "点击了");
					}
				});
    			iv.setImageResource(R.drawable.default_pic);
    			iv.setScaleType(ScaleType.FIT_XY);
    			
    			if(true)
    			{
    				pubuliy_left_linearlayout.addView(parentview, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    			}else
    			{
    				pubuliy_right_linearlayout.addView(parentview, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    			}
    			//MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.getImage(ToolsUtil.nullToString(myFavoriteProductListPic.getPic()), 320, 0), iv, MyApplication.getInstance().getDisplayImageOptions());
    			MyApplication.getInstance().getImageLoader().displayImage(myFavoriteProductListPic.getPic(), iv, MyApplication.getInstance().getDisplayImageOptions());
    		}
    	}
    }
}
