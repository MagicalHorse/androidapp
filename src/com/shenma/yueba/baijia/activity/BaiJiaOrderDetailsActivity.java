package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shenma.yueba.ChatActivity;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.BaiJiaOrderListAdapter.OrderControlListener;
import com.shenma.yueba.baijia.adapter.BaijiaOrderDetailsAdapter;
import com.shenma.yueba.baijia.modle.BaiJiaOrdeDetailsInfoBean;
import com.shenma.yueba.baijia.modle.BaiJiaOrderListInfo;
import com.shenma.yueba.baijia.modle.ProductInfoBean;
import com.shenma.yueba.baijia.modle.RequestBaiJiaOrdeDetailsInfoBean;
import com.shenma.yueba.util.ButtonManager;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ListViewUtils;
import com.shenma.yueba.util.ShareUtil;
import com.shenma.yueba.util.ShareUtil.ShareListener;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;
import com.umeng.analytics.MobclickAgent;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-1 下午4:06:26  
 * 程序的简单说明   败家订单详情页面
 */

public class BaiJiaOrderDetailsActivity extends BaseActivityWithTopView implements OnClickListener,OrderControlListener{
View parentView;
ListView baijia_orderdetails_layout_lsitview;
TextView baijia_orderdetails_lianxibuyer_textview;//联系买手
TextView baijia_orderdetails_xjfx_textview;//现金分享
TextView order_no_content;//订单编号
TextView order_wating_content;//订单状态
TextView order_money_count;//订单金额
TextView order_date_count;//订单日期
TextView customer_account_content;//买手昵称
TextView tv_get_address_content;//提货地址
TextView tv_customer_phone_content;//买手手机号
//头像
RoundImageView riv_customer_head;

List<BaiJiaOrdeDetailsInfoBean> obj_list=new ArrayList<BaiJiaOrdeDetailsInfoBean>();
String orderNo=null;
HttpControl httpControl=new HttpControl();
RequestBaiJiaOrdeDetailsInfoBean bean;
BaijiaOrderDetailsAdapter baijiaOrderDetailsAdapter;
LinearLayout baijia_orderdetails_footer_right_linearlayout;//按钮的父对象

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		parentView=this.getLayoutInflater().inflate(R.layout.baijia_orderdetails_layout, null);
		setContentView(parentView);
		super.onCreate(savedInstanceState);
		if(this.getIntent().getStringExtra("ORDER_ID")==null)
		{
			MyApplication.getInstance().showMessage(BaiJiaOrderDetailsActivity.this, "数据错误");
			this.finish();
			return;
		}
		orderNo=this.getIntent().getStringExtra("ORDER_ID");
		initView();
		requestData();
	}
	
	void initView()
	{
		setTitle("订单详情");
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BaiJiaOrderDetailsActivity.this.finish();
			}
		});
		
		baijia_orderdetails_footer_right_linearlayout=(LinearLayout)parentView.findViewById(R.id.baijia_orderdetails_footer_right_linearlayout);
		//订单编号
		order_no_content=(TextView)parentView.findViewById(R.id.order_no_content);
		//订单状态
		order_wating_content=(TextView)parentView.findViewById(R.id.order_wating_content);
		//订单金额
		order_money_count=(TextView)parentView.findViewById(R.id.order_money_count);
		//日期
		order_date_count=(TextView)parentView.findViewById(R.id.order_date_count);
		//买手昵称
		customer_account_content=(TextView)parentView.findViewById(R.id.customer_account_content);
		//提货地址
		tv_get_address_content=(TextView)parentView.findViewById(R.id.tv_get_address_content);
		//买手手机号
		tv_customer_phone_content=(TextView)parentView.findViewById(R.id.tv_customer_phone_content);
		tv_customer_phone_content.setOnClickListener(this);
		//电话图片
		ImageView tv_customer_phone_imageview=(ImageView)parentView.findViewById(R.id.tv_customer_phone_imageview);
		tv_customer_phone_imageview.setOnClickListener(this);
		//头像
		riv_customer_head=(RoundImageView)parentView.findViewById(R.id.riv_customer_head);
		riv_customer_head.setOnClickListener(this);
		baijia_orderdetails_layout_lsitview=(ListView)parentView.findViewById(R.id.baijia_orderdetails_layout_lsitview);
		
		baijia_orderdetails_lianxibuyer_textview=(TextView)parentView.findViewById(R.id.baijia_orderdetails_lianxibuyer_textview);
		baijia_orderdetails_lianxibuyer_textview.setOnClickListener(this);
		baijia_orderdetails_xjfx_textview=(TextView)parentView.findViewById(R.id.baijia_orderdetails_xjfx_textview);
		baijia_orderdetails_xjfx_textview.setOnClickListener(this);
		setFont();
	}
	
	/****
	 * 设置字体样式
	 * ***/
	void setFont()
	{
		ToolsUtil.setFontStyle(this, parentView, R.id.tv_top_title,R.id.order_no_title,R.id.order_no_content,R.id.order_wating_title,R.id.order_wating_content,R.id.order_money_title,R.id.order_money_count,R.id.order_date_title,R.id.order_date_count,R.id.customer_account_title,R.id.customer_account_content,R.id.tv_customer_phone_title,R.id.tv_customer_phone_content,R.id.tv_get_address_title,R.id.tv_get_address_content,R.id.baijia_orderdetails_lianxibuyer_textview,R.id.baijia_orderdetails_xjfx_textview);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.baijia_orderdetails_lianxibuyer_textview://联系买手
			Intent intentbuter=new Intent(BaiJiaOrderDetailsActivity.this,ChatActivity.class);
			intentbuter.putExtra("Chat_Type", ChatActivity.chat_type_private);
			intentbuter.putExtra("Chat_NAME",bean.getData().getBuyerName());//圈子名字
			intentbuter.putExtra("toUser_id", bean.getData().getBuyerId());
			startActivity(intentbuter);
			break;
		case R.id.riv_customer_head://头像
			Intent iconIntent=new Intent(BaiJiaOrderDetailsActivity.this,ShopMainActivity.class);
			startActivity(iconIntent);
			break;
		case R.id.baijia_orderdetails_xjfx_textview://现金分享
			shareUrl();
			break;
		case R.id.tv_customer_phone_content://买手手机号
		case R.id.tv_customer_phone_imageview://买手手机号
			String phoneNo=tv_customer_phone_content.getText().toString().trim();
			if(!phoneNo.equals(""))
			{
				ToolsUtil.callActivity(BaiJiaOrderDetailsActivity.this, phoneNo);
			}
		     break;
		}
		
	}
	
	
	
	
	/********
	 *  分享
	 *  @param content String 内容提示
	 *  @param url String 链接地址
	 *  @param icon String 图片地址
	 * ****/
	void shareUrl()
	{
		final BaiJiaOrdeDetailsInfoBean infobean=bean.getData();
		String name=infobean.getProductName();
		ShareUtil.shareAll(BaiJiaOrderDetailsActivity.this, name, infobean.getShareLink(),ToolsUtil.getImage(ToolsUtil.nullToString(infobean.getProductPic()), 320, 0),new ShareListener() {
			
			@Override
			public void sharedListener_sucess() {
				requestShared(infobean.getOrderNo());
			}
			
			@Override
			public void sharedListener_Fails(String msg) {
				MyApplication.getInstance().showMessage(BaiJiaOrderDetailsActivity.this, msg);
			}
		});
	}
	
	
	void requestShared(final String OrderNo)
	{
		HttpControl httpControl=new HttpControl();
		httpControl.createOrderShare(OrderNo, false, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				if(obj!=null)
				{
					MyApplication.getInstance().showMessage(BaiJiaOrderDetailsActivity.this, "分享成功");
				}else
				{
					http_Fails(500, "分享失败");
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(BaiJiaOrderDetailsActivity.this, msg);
			}
		}, BaiJiaOrderDetailsActivity.this);
	}
	
	BaiJiaOrderListInfo dataTrasition(BaiJiaOrdeDetailsInfoBean baiJiaOrderListInfo)
	{
		BaiJiaOrderListInfo info=new BaiJiaOrderListInfo();
		info.setAddress(baiJiaOrderListInfo.getPickAddress());
		info.setAmount(baiJiaOrderListInfo.getActualAmount());
		info.setBuyerName(baiJiaOrderListInfo.getBuyerName());
		info.setCreateDate(baiJiaOrderListInfo.getCreateDate());
		info.setOrderNo(baiJiaOrderListInfo.getOrderNo());
		info.setOrderProductCount(baiJiaOrderListInfo.getProductCount());
		info.setOrderStatus(baiJiaOrderListInfo.getOrderStatus());
		info.setOrderStatusStr(baiJiaOrderListInfo.getOrderStatusName());
		ProductInfoBean productInfoBean=new ProductInfoBean();
		productInfoBean.setImage(baiJiaOrderListInfo.getProductPic());
		productInfoBean.setName(baiJiaOrderListInfo.getProductName());
		productInfoBean.setPrice(baiJiaOrderListInfo.getPrice());
		productInfoBean.setProductCount(baiJiaOrderListInfo.getProductCount());
		productInfoBean.setProductId(baiJiaOrderListInfo.getProductId());
		info.setProduct(productInfoBean);
		
		return info;
	}
	
	
	
	/*****
	 * 订单详情请求数据
	 * **/
	void requestData()
	{
		httpControl.getBaijiaOrderDetails(orderNo, true, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				if(obj!=null && obj instanceof RequestBaiJiaOrdeDetailsInfoBean)
				{
					bean=(RequestBaiJiaOrdeDetailsInfoBean)obj;
					if(bean.getData()==null)
					{
						http_Fails(500, "获取失败 数据错误");
						return;
					}
					setvalue();
				}else
				{
					http_Fails(500, "获取失败 数据错误");
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(BaiJiaOrderDetailsActivity.this, msg);
				BaiJiaOrderDetailsActivity.this.finish();
			}
		}, BaiJiaOrderDetailsActivity.this);
	}
	
	/**
	 * 负值
	 * **/
	void setvalue()
	{
		BaiJiaOrdeDetailsInfoBean baiJiaOrdeDetailsInfoBean=bean.getData();
		order_no_content.setText(ToolsUtil.nullToString(baiJiaOrdeDetailsInfoBean.getOrderNo()));
		order_wating_content.setText(ToolsUtil.nullToString(baiJiaOrdeDetailsInfoBean.getOrderStatusName()));
		order_money_count.setText("￥"+baiJiaOrdeDetailsInfoBean.getActualAmount());
		order_date_count.setText(ToolsUtil.nullToString(baiJiaOrdeDetailsInfoBean.getCreateDate()));
		customer_account_content.setText(ToolsUtil.nullToString(baiJiaOrdeDetailsInfoBean.getBuyerName()));
		tv_customer_phone_content.setText(ToolsUtil.nullToString(baiJiaOrdeDetailsInfoBean.getBuyerMobile()));
		tv_get_address_content.setText(ToolsUtil.nullToString(baiJiaOrdeDetailsInfoBean.getPickAddress()));
		MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.nullToString(baiJiaOrdeDetailsInfoBean.getBuyerLogo()), riv_customer_head, MyApplication.getInstance().getDisplayImageOptions());
		obj_list.add(baiJiaOrdeDetailsInfoBean);
		//如果当前订单状态是已提货 体显示 分享按钮
		if(bean.getData().getOrderStatus()==16)
		{
			baijia_orderdetails_xjfx_textview.setVisibility(View.VISIBLE);
		}
		
		//根据订单状态 设置按钮
		List<View> view_list=ButtonManager.getButton(this, bean.getData().getOrderStatus(),this);
		baijia_orderdetails_footer_right_linearlayout.removeAllViews();
		if(view_list!=null)
		{
			if(view_list!=null)
			{
				for(int i=0;i<view_list.size();i++)
				{
					View button=view_list.get(i);
					Button btn=(Button)button.findViewById(R.id.baijia_orderdetails_sqtk_button);
					FontManager.changeFonts(this, btn);
					BaiJiaOrdeDetailsInfoBean infobean=bean.getData();
					//数据转换
					BaiJiaOrderListInfo info=dataTrasition(infobean);
					btn.setTag(info);
					LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					param.leftMargin=5;
					baijia_orderdetails_footer_right_linearlayout.addView(button,param);
				}
			}
		}
		baijiaOrderDetailsAdapter=new BaijiaOrderDetailsAdapter(this, obj_list);
		baijia_orderdetails_layout_lsitview.setAdapter(baijiaOrderDetailsAdapter);
		baijiaOrderDetailsAdapter.notifyDataSetChanged();
		
		//ListViewUtils.setListViewHeightBasedOnChildren(baijia_orderdetails_layout_lsitview);
	}

	@Override
	public void orderCotrol_OnRefuces() {
		requestData();
	}
	
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if(arg0==200)
		{
			if(arg2!=null)
			{
				if(arg2.getStringExtra("PAYRESULT").equals("SUCESS"))//如果支付返回成功（即微信通知成功）
				{
					requestData();
				}
			}
		}
	}
	
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		}
		public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		}
	
	
	@Override
	protected void onDestroy() {
		MyApplication.getInstance().removeActivity(this);//加入回退栈
		super.onDestroy();
	}
}