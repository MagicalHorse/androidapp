package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.BaijiaOrderDetailsAdapter;
import com.shenma.yueba.baijia.modle.BaiJiaOrdeDetailsInfoBean;
import com.shenma.yueba.baijia.modle.RequestBaiJiaOrdeDetailsInfoBean;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-1 下午4:06:26  
 * 程序的简单说明   败家订单详情页面
 */

public class BaiJiaOrderDetailsActivity extends BaseActivityWithTopView implements OnClickListener{
View parentView;
ListView baijia_orderdetails_layout_lsitview;
TextView baijia_orderdetails_lianxibuyer_textview;//联系买手
Button baijia_orderdetails_sqtk_button;//申请退款
Button baijia_orderdetails_ziti_button;//自提
Button baijia_orderdetails_cancellorder_button;//取消订单
Button baijia_orderdetails_pay_button;//付款
TextView baijia_orderdetails_xjfx_textview;//现金分享
TextView order_no_content;//订单编号
TextView order_wating_content;//订单状态
TextView order_money_count;//订单金额
TextView order_date_count;//订单日期
TextView customer_account_content;//买手昵称
TextView tv_customer_phone_content;//买手手机号
//头像
RoundImageView riv_customer_head;

List<BaiJiaOrdeDetailsInfoBean> obj_list=new ArrayList<BaiJiaOrdeDetailsInfoBean>();
String orderNo=null;
HttpControl httpControl=new HttpControl();
RequestBaiJiaOrdeDetailsInfoBean bean;
BaijiaOrderDetailsAdapter baijiaOrderDetailsAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
				
				
			}
		});
		
		//订单编号
		order_no_content=(TextView)parentView.findViewById(R.id.order_no_content);
		//订单状态
		order_wating_content=(TextView)parentView.findViewById(R.id.order_wating_content);
		//订单金额
		order_money_count=(TextView)parentView.findViewById(R.id.order_money_count);
		//日期
		order_date_count=(TextView)parentView.findViewById(R.id.order_money_count);
		//买手昵称
		customer_account_content=(TextView)parentView.findViewById(R.id.customer_account_content);
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
		baijiaOrderDetailsAdapter=new BaijiaOrderDetailsAdapter(this, obj_list);
		baijia_orderdetails_layout_lsitview.setAdapter(baijiaOrderDetailsAdapter);
		baijia_orderdetails_lianxibuyer_textview=(TextView)parentView.findViewById(R.id.baijia_orderdetails_lianxibuyer_textview);
		baijia_orderdetails_lianxibuyer_textview.setOnClickListener(this);
		baijia_orderdetails_sqtk_button=(Button)parentView.findViewById(R.id.baijia_orderdetails_sqtk_button);
		baijia_orderdetails_sqtk_button.setOnClickListener(this);
		baijia_orderdetails_ziti_button=(Button)parentView.findViewById(R.id.baijia_orderdetails_ziti_button);
		baijia_orderdetails_ziti_button.setOnClickListener(this);
		baijia_orderdetails_cancellorder_button=(Button)parentView.findViewById(R.id.baijia_orderdetails_cancellorder_button);
		baijia_orderdetails_cancellorder_button.setOnClickListener(this);
		baijia_orderdetails_pay_button=(Button)parentView.findViewById(R.id.baijia_orderdetails_pay_button);
		baijia_orderdetails_pay_button.setOnClickListener(this);
		baijia_orderdetails_xjfx_textview=(TextView)parentView.findViewById(R.id.baijia_orderdetails_xjfx_textview);
		baijia_orderdetails_xjfx_textview.setOnClickListener(this);
		setFont();
	}
	
	/****
	 * 设置字体样式
	 * ***/
	void setFont()
	{
		ToolsUtil.setFontStyle(this, parentView, R.id.tv_top_title,R.id.order_no_title,R.id.order_no_content,R.id.order_wating_title,R.id.order_wating_content,R.id.order_money_title,R.id.order_money_count,R.id.order_date_title,R.id.order_date_count,R.id.customer_account_title,R.id.customer_account_content,R.id.tv_customer_phone_title,R.id.tv_customer_phone_content,R.id.tv_get_address_title,R.id.tv_get_address_content,R.id.baijia_orderdetails_lianxibuyer_textview,R.id.baijia_orderdetails_ziti_button,R.id.baijia_orderdetails_sqtk_button,R.id.baijia_orderdetails_cancellorder_button,R.id.baijia_orderdetails_pay_button);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.baijia_orderdetails_lianxibuyer_textview://联系买手
			break;
		case R.id.baijia_orderdetails_ziti_button://自提
			break;
		case R.id.baijia_orderdetails_sqtk_button://申请退款
			break;
		case R.id.baijia_orderdetails_cancellorder_button://取消订单
			break;
		case R.id.baijia_orderdetails_pay_button://支付
			break;
		case R.id.riv_customer_head://头像
			Intent iconIntent=new Intent(BaiJiaOrderDetailsActivity.this,ShopMainActivity.class);
			startActivity(iconIntent);
			break;
		case R.id.baijia_orderdetails_xjfx_textview://现金分享
			break;
		case R.id.tv_customer_phone_content://买手手机号
		case R.id.tv_customer_phone_imageview://买手手机号
			String phoneNo=tv_customer_phone_content.getText().toString().trim();
			if(!phoneNo.equals(""))
			{
				//调用拨号键
				Uri telUri = Uri.parse("tel:+phoneNo+");
				Intent intent= new Intent(Intent.ACTION_DIAL, telUri);
				startActivity(intent); 
			}
			break;
		}
		
	}
	
	/*****
	 * 根据类型显示按钮
	 * **/
	void setIsShow(int i)
	{
		switch(i)
		{
		case 0://显示  自提与申请退款
			baijia_orderdetails_ziti_button.setVisibility(View.VISIBLE);
			baijia_orderdetails_sqtk_button.setVisibility(View.VISIBLE);
			baijia_orderdetails_cancellorder_button.setVisibility(View.GONE);
			baijia_orderdetails_pay_button.setVisibility(View.GONE);
			break;
		case 1://显示取消 与付款
			baijia_orderdetails_ziti_button.setVisibility(View.GONE);
			baijia_orderdetails_sqtk_button.setVisibility(View.GONE);
			baijia_orderdetails_cancellorder_button.setVisibility(View.VISIBLE);
			baijia_orderdetails_pay_button.setVisibility(View.VISIBLE);
			break;
		case 2://显示申请退款
			baijia_orderdetails_ziti_button.setVisibility(View.GONE);
			baijia_orderdetails_sqtk_button.setVisibility(View.VISIBLE);
			baijia_orderdetails_cancellorder_button.setVisibility(View.GONE);
			baijia_orderdetails_pay_button.setVisibility(View.GONE);
			break;
		}
	}
	
	/*****
	 * 订单详情请求数据
	 * **/
	void requestData()
	{
		httpControl.getBaijiaOrderDetails(orderNo, true, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				if(obj!=null && obj instanceof RequestBaiJiaOrdeDetailsInfoBean && bean.getData()!=null)
				{
					bean=(RequestBaiJiaOrdeDetailsInfoBean)obj;
					setvalue();
				}else
				{
					http_Fails(500, "获取失败 数据错误");
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(BaiJiaOrderDetailsActivity.this, msg);
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
		order_money_count.setText("￥"+baiJiaOrdeDetailsInfoBean.getPrice());
		order_date_count.setText(ToolsUtil.nullToString(baiJiaOrdeDetailsInfoBean.getCreateDate()));
		customer_account_content.setText(ToolsUtil.nullToString(baiJiaOrdeDetailsInfoBean.getBuyerName()));
		tv_customer_phone_content.setText(ToolsUtil.nullToString(baiJiaOrdeDetailsInfoBean.getBuyerMobile()));
		MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.nullToString(""), riv_customer_head, MyApplication.getInstance().getDisplayImageOptions());
		
		
	}
}
