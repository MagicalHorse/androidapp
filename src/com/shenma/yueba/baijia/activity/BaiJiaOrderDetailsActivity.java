package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.BaijiaOrderDetailsAdapter;
import com.shenma.yueba.util.ToolsUtil;

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
List<Object> obj_list=new ArrayList<Object>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		parentView=this.getLayoutInflater().inflate(R.layout.baijia_orderdetails_layout, null);
		setContentView(parentView);
		super.onCreate(savedInstanceState);
		initView();
	}
	
	void initView()
	{
		setTitle("订单详情");
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		obj_list.add(null);
		baijia_orderdetails_layout_lsitview=(ListView)parentView.findViewById(R.id.baijia_orderdetails_layout_lsitview);
		BaijiaOrderDetailsAdapter baijiaOrderDetailsAdapter=new BaijiaOrderDetailsAdapter(this, obj_list);
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
		
		setFont();
	}
	
	/****
	 * 设置字体样式
	 * ***/
	void setFont()
	{
		ToolsUtil.setFontStyle(this, parentView, R.id.tv_top_title, null);
		ToolsUtil.setFontStyle(this, parentView, R.id.order_no_title, null);
		ToolsUtil.setFontStyle(this, parentView, R.id.order_no_content, "1000");
		ToolsUtil.setFontStyle(this, parentView, R.id.order_wating_title, null);
		ToolsUtil.setFontStyle(this, parentView, R.id.order_wating_content, "订单状态");
		ToolsUtil.setFontStyle(this, parentView, R.id.order_money_title, null);
		ToolsUtil.setFontStyle(this, parentView, R.id.order_money_count, "0.0");
		ToolsUtil.setFontStyle(this, parentView, R.id.order_date_title, null);
		ToolsUtil.setFontStyle(this, parentView, R.id.order_date_count, "2015=06-01");
		ToolsUtil.setFontStyle(this, parentView, R.id.customer_account_title, null);
		ToolsUtil.setFontStyle(this, parentView, R.id.customer_account_content, "昵称");
		ToolsUtil.setFontStyle(this, parentView, R.id.tv_customer_phone_title, null);
		ToolsUtil.setFontStyle(this, parentView, R.id.tv_customer_phone_content, "100000");
		ToolsUtil.setFontStyle(this, parentView, R.id.tv_get_address_title,null);
		ToolsUtil.setFontStyle(this, parentView, R.id.tv_get_address_content,"地址");
		ToolsUtil.setFontStyle(this, parentView, R.id.baijia_orderdetails_lianxibuyer_textview,R.id.baijia_orderdetails_ziti_button,R.id.baijia_orderdetails_sqtk_button,R.id.baijia_orderdetails_cancellorder_button,R.id.baijia_orderdetails_pay_button);
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
}
