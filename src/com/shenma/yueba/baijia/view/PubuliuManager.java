package com.shenma.yueba.baijia.view;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.ApproveBuyerDetailsActivity;
import com.shenma.yueba.baijia.modle.BrandCityWideInfo;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListInfo;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListLikeUser;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListPic;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.umeng.socialize.utils.Log;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-9 下午2:48:26  
 * 程序的简单说明  瀑布流管理
 */

public class PubuliuManager {
	Context context;
	View parent;
	//瀑布流 左右布局
	LinearLayout pubuliy_left_linearlayout,pubuliy_right_linearlayout;
	int leftHeight;//左侧高度
	int rightHeight;//右侧高度
	HttpControl httpControl=new HttpControl();
	public PubuliuManager(Context context,View parent)
	{
		this.context=context;
		this.parent=parent;
		initView();
	}
	
	/***
	 * 加载视图
	 * **/
	void initView()
	{
		pubuliy_left_linearlayout=(LinearLayout)parent.findViewById(R.id.pubuliy_left_linearlayout);
		pubuliy_right_linearlayout=(LinearLayout)parent.findViewById(R.id.pubuliy_right_linearlayout);
	}
	
	/****
	 * 设置刷新
	 * @param item List<MyFavoriteProductListInfo>
	 * ***/
	public void onResher(List<MyFavoriteProductListInfo> item)
	{
		leftHeight=0;
		rightHeight=0;
		pubuliy_left_linearlayout.removeAllViews();
		pubuliy_right_linearlayout.removeAllViews();
		addItem(item);
	}
	
	
	/****
	 * 加载数据
	 * @param item List<MyFavoriteProductListInfo>
	 * ****/
	public void onaddData(List<MyFavoriteProductListInfo> item)
	{
		addItem(item);
	}
	
	
	 /*******
     * 设置 瀑布流的 高度
     * 
     * *****/
    void addItem(List<MyFavoriteProductListInfo> item)
    {
    	if(item!=null)
    	{
    		for(int i=0;i<item.size();i++)
    		{
    			MyFavoriteProductListInfo bean=item.get(i);
    			int witdh=pubuliy_left_linearlayout.getWidth();
    			MyFavoriteProductListPic myFavoriteProductListPic=bean.getPic();
    			if(myFavoriteProductListPic==null)
    			{
    				myFavoriteProductListPic=new MyFavoriteProductListPic();
    			}
    			int height=(int)(witdh*myFavoriteProductListPic.getRatio());
    			View parentview=LinearLayout.inflate(context, R.layout.pubuliu_item_layout, null);
    			//价格
    			TextView pubuliu_item_layout_pricevalue_textview=(TextView)parentview.findViewById(R.id.pubuliu_item_layout_pricevalue_textview);
    			pubuliu_item_layout_pricevalue_textview.setText(bean.getPrice()+"");
    			//商品名称
    			TextView pubuliu_item_layout_name_textview=(TextView)parentview.findViewById(R.id.pubuliu_item_layout_name_textview);
    			pubuliu_item_layout_name_textview.setText(bean.getName());
    			//收藏
    			TextView pubuliu_item_layout_like_textview=(TextView)parentview.findViewById(R.id.pubuliu_item_layout_like_textview);
    			pubuliu_item_layout_like_textview.setOnClickListener(onClickListener);
    			
    			pubuliu_item_layout_like_textview.setSelected(bean.isIsFavorite());
    			pubuliu_item_layout_like_textview.setText(Integer.toString(bean.getFavoriteCount()));
    			pubuliu_item_layout_like_textview.setTag(bean);
    			/*if(bean.getLikeUser()!=null)
    			{
    				MyFavoriteProductListLikeUser likeuser=bean.getLikeUser();
    				pubuliu_item_layout_like_textview.setSelected(likeuser.isIsLike());
    				pubuliu_item_layout_like_textview.setText(likeuser.getCount()+"");
    				pubuliu_item_layout_like_textview.setTag(bean);
    			}*/
    			ImageView iv=(ImageView)parentview.findViewById(R.id.pubuliu_item_layout_imageview);
    			if(height>0)
    			{
    				Log.i("TAG", "height="+height +" witdh="+witdh   +"ration="+myFavoriteProductListPic.getRatio());
        			
    				iv.getLayoutParams().height=height;
    			}else
    			{
    				height=witdh;
    				Log.i("TAG", "height="+height +" witdh="+witdh   +"ration="+myFavoriteProductListPic.getRatio());
        			
    				iv.getLayoutParams().height=height;
    			}
    			iv.setTag(bean);
    			iv.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(v.getTag()==null)
						{
							return;
						}
						MyFavoriteProductListInfo bean=(MyFavoriteProductListInfo)v.getTag();
						Intent intent=new Intent(context,ApproveBuyerDetailsActivity.class);
						intent.putExtra("productID", bean.getId());
						context.startActivity(intent);
						//MyApplication.getInstance().showMessage(context, "点击了");
					}
				});
    			iv.setImageResource(R.drawable.default_pic);
    			iv.setScaleType(ScaleType.FIT_XY);
    			android.util.Log.i("TAG", "leftHeight="+leftHeight+"   rightHeight="+rightHeight);
    		    //根据 左右高度判断
    			if(leftHeight<=rightHeight)
    			{
    				leftHeight+=height;
    				pubuliy_left_linearlayout.addView(parentview, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    			}else
    			{
    				rightHeight+=height;
    				pubuliy_right_linearlayout.addView(parentview, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    			}
    			ToolsUtil.setFontStyle(context, parentview, R.id.pubuliu_item_layout_pricevalue_textview,R.id.pubuliu_item_layout_name_textview,R.id.pubuliu_item_layout_like_textview,R.id.pubuliu_item_layout_price_textview);
    			MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.getImage(ToolsUtil.nullToString(myFavoriteProductListPic.getPic()), 320, 0), iv, MyApplication.getInstance().getDisplayImageOptions());
    			//MyApplication.getInstance().getImageLoader().displayImage(myFavoriteProductListPic.getPic(), iv, MyApplication.getInstance().getDisplayImageOptions());
    		}
    	}
    }
    
    OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.pubuliu_item_layout_like_textview:
				/*if(v.getTag()!=null && v.getTag() instanceof MyFavoriteProductListInfo)
				{
					MyFavoriteProductListInfo  bean=(MyFavoriteProductListInfo)v.getTag();
					MyFavoriteProductListLikeUser myFavoriteProductListLikeUser=bean.getLikeUser();
					if(myFavoriteProductListLikeUser.isIsLike())
					{
						submitAttention(0,bean,v);
					}else
					{
						submitAttention(1,bean,v);
					}
					
				}*/
				if(v.getTag()!=null && v.getTag() instanceof MyFavoriteProductListInfo)
				{
					MyFavoriteProductListInfo  bean=(MyFavoriteProductListInfo)v.getTag();
					if(bean.isIsFavorite())
					{
						submitAttention(0,bean,v);
					}else
					{
						submitAttention(1,bean,v);
					}
					
				}
				break;
			}
		}
	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/****
	 * 提交收藏与取消收藏商品
	 * @param type int   0表示取消收藏   1表示收藏
	 * @param brandCityWideInfo BrandCityWideInfo  商品对象
	 * @param v View  商品对象
	 * **/
	void submitAttention(final int Status,final MyFavoriteProductListInfo  bean,final View v)
	{
		httpControl.setFavor(bean.getId(), Status, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				/*if(v!=null && v instanceof TextView)
				{
					MyFavoriteProductListLikeUser myFavoriteProductListLikeUser=bean.getLikeUser();
					if(myFavoriteProductListLikeUser!=null)
					{
						int count=bean.getLikeUser().getCount();
						switch(Status)
						{
						case 0:
							count--;
							if(count<0)
							{
								count=0;
							}
							bean.getLikeUser().setCount(count);
							v.setSelected(false);
							((TextView)v).setText(count+"");
							myFavoriteProductListLikeUser.setIsLike(false);
							break;
						case 1:
							count++;
							bean.getLikeUser().setCount(count);
							((TextView)v).setText(count+"");
							v.setSelected(true);
							myFavoriteProductListLikeUser.setIsLike(true);
							break;
						}
					}
					
				}*/
				
				if(v!=null && v instanceof TextView)
				{
					//MyFavoriteProductListLikeUser myFavoriteProductListLikeUser=bean.getLikeUser();
					int count=bean.getFavoriteCount();
					switch(Status)
					{
					case 0:
						count--;
						if(count<0)
						{
							count=0;
						}
						bean.setFavoriteCount(count);
						v.setSelected(false);
						((TextView)v).setText(count+"");
						bean.setIsFavorite(false);
						break;
					case 1:
						count++;
						bean.setFavoriteCount(count);
						((TextView)v).setText(count+"");
						v.setSelected(true);
						bean.setIsFavorite(true);
						break;
					}
					
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(context, msg);
			}
		}, context);
	}
}
