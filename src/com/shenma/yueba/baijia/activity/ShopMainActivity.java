package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.fragment.ShopPuBuliuFragment;
import com.shenma.yueba.baijia.modle.FragmentBean;
import com.shenma.yueba.baijia.modle.RequestUserInfoBean;
import com.shenma.yueba.baijia.modle.UserInfoBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;
/*****
 * 本类定义 店铺商品首页显示页面 
 * 1.显示商家logo  名称  地址   店铺描述 以及 商品图片等信息
 * @author gyj
 * @date 2015-05-05
 * ***/
public class ShopMainActivity extends FragmentActivity {
	//买手logo 
    RoundImageView shop_main_layout_icon_imageview;
    //店铺名称
    TextView shop_main_layout_name_textview;
    //商场名称
    TextView shop_main_layout_market_textview;
    //私聊按钮
    TextView shop_main_siliao_imagebutton;
    //关注按钮
    TextView  shop_main_attention_imagebutton;
    //关注 值
    TextView shop_main_attentionvalue_textview;
    //粉丝值
    TextView shop_main_fansvalue_textview;
    //赞值
    TextView shop_main_praisevalue_textview;
    //主要内容
    FrameLayout shop_main_layout_tabcontent_framelayout;
    //商品描述
    TextView shap_main_description1_textview,shap_main_description2_textview,shap_main_description3_textview;
    FragmentManager fragmentManager;
    LinearLayout shop_main_head_layout_tab_linearlayout;
    //商品  上新
    List<FragmentBean> fragmentBean_list=new ArrayList<FragmentBean>();
    List<View> view_list=new ArrayList<View>();
    PullToRefreshScrollView shop_main_layout_title_pulltorefreshscrollview;
    int currId=-1;
    HttpControl httpControl=new HttpControl();
    UserInfoBean userInfoBean;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	MyApplication.getInstance().addActivity(this);//加入回退栈
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shop_main_layout);
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		
		fragmentManager=getSupportFragmentManager();
		initView();
		getBaijiaUserInfo();
		
	}
	
	/****
	 * 初始化视图信息
	 * **/
	void initView()
	{
		TextView tv_top_left=(TextView)findViewById(R.id.tv_top_left);
		tv_top_left.setVisibility(View.VISIBLE);
		tv_top_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ShopMainActivity.this.finish();
			}
		});
		TextView tv_top_title=(TextView)findViewById(R.id.tv_top_title);
		tv_top_title.setVisibility(View.VISIBLE);
		tv_top_title.setText("店铺名称");

		shop_main_layout_title_pulltorefreshscrollview=(PullToRefreshScrollView)findViewById(R.id.shop_main_layout_title_pulltorefreshscrollview);
		shop_main_layout_title_pulltorefreshscrollview.setMode(Mode.BOTH);
		shop_main_layout_title_pulltorefreshscrollview.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				onRefresh();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				onAddData();
			}
		});
		
		shop_main_layout_tabcontent_framelayout=(FrameLayout)findViewById(R.id.shop_main_layout_tabcontent_framelayout);
		
		
		
		LinearLayout shop_main_circle_layout=(LinearLayout)findViewById(R.id.shop_main_circle_layout);
		shop_main_circle_layout.setOnClickListener(onClickListener);
		shop_main_layout_icon_imageview=(RoundImageView)findViewById(R.id.shop_main_layout_icon_imageview);
		shop_main_layout_name_textview=(TextView)findViewById(R.id.shop_main_layout_name_textview);
		shop_main_layout_market_textview=(TextView)findViewById(R.id.shop_main_layout_market_textview);
		shop_main_siliao_imagebutton=(TextView)findViewById(R.id.shop_main_siliao_imagebutton); 
		shop_main_attention_imagebutton=(TextView)findViewById(R.id.shop_main_attention_imagebutton); 
		shop_main_attentionvalue_textview=(TextView)findViewById(R.id.shop_main_attentionvalue_textview); 
		shop_main_fansvalue_textview=(TextView)findViewById(R.id.shop_main_fansvalue_textview); 
		shop_main_praisevalue_textview=(TextView)findViewById(R.id.shop_main_praisevalue_textview); 
		shap_main_description1_textview=(TextView)findViewById(R.id.shap_main_description1_textview); 
		shap_main_description2_textview=(TextView)findViewById(R.id.shap_main_description2_textview); 
		shap_main_description3_textview=(TextView)findViewById(R.id.shap_main_description3_textview); 
		
		shop_main_head_layout_tab_linearlayout=(LinearLayout)findViewById(R.id.shop_main_head_layout_tab_linearlayout);
		
		
		TextView shop_main_attention_textview=(TextView)findViewById(R.id.shop_main_attention_textview);
		TextView shop_main_fans_textview=(TextView)findViewById(R.id.shop_main_fans_textview);
		TextView shop_main_praise_textview=(TextView)findViewById(R.id.shop_main_praise_textview);
		FontManager.changeFonts(ShopMainActivity.this, shop_main_layout_name_textview,shop_main_layout_market_textview,shop_main_attentionvalue_textview,shop_main_fansvalue_textview,shop_main_praisevalue_textview,shap_main_description1_textview,shap_main_description2_textview,shap_main_description3_textview,shop_main_attention_textview,shop_main_fans_textview,shop_main_praise_textview,shop_main_attention_imagebutton,shop_main_siliao_imagebutton);
	}
	
	
	OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			switch(v.getId())
			{
			case R.id.shop_stay_layout_parent_linearlayout:
				if(v.getTag()!=null && v.getTag() instanceof Integer)
				{
					setItem(false,(Integer)v.getTag());
				}
				break;
			case R.id.shop_main_circle_layout:
				Intent intent=new Intent(ShopMainActivity.this,CircleListActivity.class);
				startActivity(intent);
				break;
			}
		}
	};
    
	
    /****
     * 设置 tab切换文字的信息 以及是否显示底部横条
     * @param v View 父类视图
     * @param str1 信息1
     * @param str2 信息2
     * @param b boolean是否显示 横条 true显示  false不显示
     * */
    void setTabView(View v,String str1,String str2,boolean b)
    {
    	TextView shop_stay_layout_item_textview1=(TextView)v.findViewById(R.id.shop_stay_layout_item_textview1);
    	TextView shop_stay_layout_item_textview2=(TextView)v.findViewById(R.id.shop_stay_layout_item_textview2);
    	View shop_stay_layout_item_line_view=(View)v.findViewById(R.id.shop_stay_layout_item_line_view);
    	shop_stay_layout_item_textview1.setText(str1);
    	shop_stay_layout_item_textview2.setText(str2);
    	if(b)
    	{
    		shop_stay_layout_item_line_view.setVisibility(View.VISIBLE);
    	}else
    	{
    		shop_stay_layout_item_line_view.setVisibility(View.GONE);
    	}
    }
	
    /*****
     * 设置fragment显示的内容
     * @param isfirst boolean true表示第一次添加  false 表示替换
     * */
    void setItem(boolean isfirst,int _id)
    {
    	if(currId==_id)
    	{
    		return;
    	}
    	
    	currId=_id;
    	//设置 TAB 视图
    	for(int i=0;i<view_list.size();i++)
    	{
    		View parent=view_list.get(i);
    		TextView tv1=(TextView)parent.findViewById(R.id.shop_stay_layout_item_textview1);
    		TextView tv2=(TextView)parent.findViewById(R.id.shop_stay_layout_item_textview2);
    		View shop_stay_layout_item_line_view=(View)parent.findViewById(R.id.shop_stay_layout_item_line_view);
    		if(i==currId)
    		{
    			tv1.setTextColor(this.getResources().getColor(R.color.color_deeoyellow));
    			tv2.setTextColor(this.getResources().getColor(R.color.color_deeoyellow));
    			shop_stay_layout_item_line_view.setVisibility(View.VISIBLE);
    		}else
    		{
    			tv1.setTextColor(this.getResources().getColor(R.color.color_gray));
    			tv2.setTextColor(this.getResources().getColor(R.color.color_gray));
    			shop_stay_layout_item_line_view.setVisibility(View.INVISIBLE);
    		}
    	}
    	
    	if(isfirst)
    	{
    		fragmentManager.beginTransaction().add(R.id.shop_main_layout_tabcontent_framelayout, (ShopPuBuliuFragment)fragmentBean_list.get(_id).getFragment()).commit();
    	}else
    	{
    		fragmentManager.beginTransaction().replace(R.id.shop_main_layout_tabcontent_framelayout, (ShopPuBuliuFragment)fragmentBean_list.get(_id).getFragment()).commit();
    	}
    	
    }
    
    
    /******
     * 下拉刷新
     * ***/
    void onRefresh()
    {
    	if(fragmentBean_list!=null && fragmentBean_list.size()>0 && fragmentBean_list.size()>currId)
		{
			if(fragmentBean_list.get(currId).getFragment()!=null && fragmentBean_list.get(currId).getFragment() instanceof ShopPuBuliuFragment)
			{
				ShopPuBuliuFragment fragment=(ShopPuBuliuFragment)fragmentBean_list.get(currId).getFragment();
        		fragment.onPuBuliuRefersh();
			}else
			{
				shop_main_layout_title_pulltorefreshscrollview.onRefreshComplete();
			}
			
		}else
		{
			shop_main_layout_title_pulltorefreshscrollview.onRefreshComplete();
		}
    }
    
    /******
     * 上啦加载刷新
     * ***/
    void onAddData()
    {
    	
    	if(fragmentBean_list!=null && fragmentBean_list.size()>0 && fragmentBean_list.size()>currId)
		{
			if(fragmentBean_list.get(currId).getFragment()!=null && fragmentBean_list.get(currId).getFragment() instanceof ShopPuBuliuFragment)
			{
				ShopPuBuliuFragment fragment=(ShopPuBuliuFragment)fragmentBean_list.get(currId).getFragment();
				fragment.onPuBuliuaddData();
			}else
			{
				shop_main_layout_title_pulltorefreshscrollview.onRefreshComplete();
			}
			
		}else
    	{
    		shop_main_layout_title_pulltorefreshscrollview.onRefreshComplete();
    	}
    }
    
    /*****
     * 下拉刷新监听
     * **/
    public interface PubuliuFragmentListener
	{
		/***
		 * 刷新
		 * **/
		void onPuBuliuRefersh();
		
		/**
		 * 加载
		 * **/
		void onPuBuliuaddData();
	}
    
    
    void getBaijiaUserInfo()
    {
    	httpControl.getBaijiaUserInfo(true, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				if(obj!=null && obj instanceof RequestUserInfoBean)
				{
					RequestUserInfoBean bean=(RequestUserInfoBean)obj;
					if(bean.getData()!=null)
					{
						userInfoBean=bean.getData();
						setHeadValue();
					}else
					{
						http_Fails(500, "数据为空");
					}
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(ShopMainActivity.this, msg);
				//ShopMainActivity.this.finish();
			}
		}, ShopMainActivity.this);
    }
    
    /***
     * 负值
     * **/
    void setHeadValue()
    {
    	MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.nullToString(SharedUtil.getStringPerfernece(ShopMainActivity.this, SharedUtil.user_logo)), shop_main_layout_icon_imageview, MyApplication.getInstance().getDisplayImageOptions());
    	shop_main_layout_name_textview.setText(ToolsUtil.nullToString(userInfoBean.getUserName()));
    	shop_main_layout_market_textview.setText(ToolsUtil.nullToString(userInfoBean.getAddress()));
    	shop_main_attentionvalue_textview.setText(ToolsUtil.nullToString(userInfoBean.getFollowingCount()+""));
    	shop_main_fansvalue_textview.setText(ToolsUtil.nullToString(userInfoBean.getFollowerCount()+""));
    	shop_main_praisevalue_textview.setText(ToolsUtil.nullToString(userInfoBean.getCommunityCount()+""));
    	shap_main_description1_textview.setText(ToolsUtil.nullToString(userInfoBean.getDescription()));
    	ShopPuBuliuFragment shopPuBuliuFragment1=new ShopPuBuliuFragment();
		ShopPuBuliuFragment ShopPuBuliuFragment2=new ShopPuBuliuFragment();
		fragmentBean_list.add(new FragmentBean("商品", -1, shopPuBuliuFragment1));
		fragmentBean_list.add(new FragmentBean("上新", userInfoBean.getNewProductCount(), ShopPuBuliuFragment2));
    	
    	for(int i=0;i<fragmentBean_list.size();i++)
		{
			FragmentBean bean=fragmentBean_list.get(i);
			LinearLayout ll=(LinearLayout)LinearLayout.inflate(ShopMainActivity.this, R.layout.shop_stay_layout, null);
			LinearLayout shop_stay_layout_parent_linearlayout=(LinearLayout)ll.findViewById(R.id.shop_stay_layout_parent_linearlayout);
			shop_stay_layout_parent_linearlayout.setTag(new Integer(i));
			shop_stay_layout_parent_linearlayout.setOnClickListener(onClickListener);
			TextView tv1=(TextView)ll.findViewById(R.id.shop_stay_layout_item_textview1);
			tv1.setText(bean.getName());
    		TextView tv2=(TextView)ll.findViewById(R.id.shop_stay_layout_item_textview2);
    		if(bean.getIcon()>0)
    		{
    		  tv2.setText(bean.getIcon());	
    		}
			FontManager.changeFonts(ShopMainActivity.this, tv1,tv2);
    		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			params.weight=1;
			shop_main_head_layout_tab_linearlayout.addView(ll, params);
			view_list.add(ll);
			if(i==fragmentBean_list.size()-1)
			{
				View shop_stay_layout_tabline_relativelayout=(View)ll.findViewById(R.id.shop_stay_layout_tabline_relativelayout);
				shop_stay_layout_tabline_relativelayout.setVisibility(View.GONE);
			}
		}
    	if(view_list.size()>0)
		{
		   setItem(true,0);
		}
    }
}
