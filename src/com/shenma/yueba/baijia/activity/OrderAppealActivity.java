package com.shenma.yueba.baijia.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-12 上午9:59:08  
 * 程序的简单说明  订单申诉页面
 */

public class OrderAppealActivity extends BaseActivityWithTopView{
View parementView;
    //买手账号
    TextView orderappeal_layout_buyernovalue_textview;
    //买手电话
    TextView orderappeal_layout_buyermobilevalue_textview;
    //提货地址
    TextView orderappeal_layout_goodsaddressvalue_textview;
    //申诉理由
    EditText orderappeal_layout_appealreason_textview;
    //提货手机号
    EditText affirmorder_item_tihuophonevalue_textview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		parementView=this.getLayoutInflater().inflate(R.layout.orderappeal_layout, null);
		setContentView(parementView);
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		initView();
	}
	
	
	void initView()
	{
		ToolsUtil.setFontStyle(OrderAppealActivity.this, parementView,R.id.tv_top_title,R.id.orderappeal_layout_buyerno_textview,R.id.orderappeal_layout_buyernovalue_textview,R.id.orderappeal_layout_buyermobile_textview,R.id.orderappeal_layout_buyermobilevalue_textview,R.id.orderappeal_layout_goodsaddress_textview,R.id.orderappeal_layout_goodsaddressvalue_textview,R.id.orderappeal_layout_appealreason_textview,R.id.affirmorder_item_tihuophone_textview,R.id.affirmorder_item_tihuophonevalue_textview,R.id.affirmorder_item_tihuophonetitle_textview,R.id.orderappeal_layout_footersubmit_button);
		setTitle("申请申诉");
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				OrderAppealActivity.this.finish();
			}
		});
		
		//买手头像
		RoundImageView orderappeal_layout_buyericon_textview=(RoundImageView)parementView.findViewById(R.id.orderappeal_layout_buyericon_textview);
		//电话图标
		ImageView orderappeal_layout_phoneicon_textview=(ImageView)parementView.findViewById(R.id.orderappeal_layout_phoneicon_textview);
		//买手账号
		orderappeal_layout_buyernovalue_textview=(TextView)parementView.findViewById(R.id.orderappeal_layout_buyernovalue_textview);
		//买手电话
		orderappeal_layout_buyermobilevalue_textview=(TextView)parementView.findViewById(R.id.orderappeal_layout_buyermobilevalue_textview);
		//提货地址
		orderappeal_layout_goodsaddressvalue_textview=(TextView)parementView.findViewById(R.id.orderappeal_layout_goodsaddressvalue_textview);
		//申诉理由
		orderappeal_layout_appealreason_textview=(EditText)parementView.findViewById(R.id.orderappeal_layout_appealreason_textview);
		//提货手机号
		affirmorder_item_tihuophonevalue_textview=(EditText)parementView.findViewById(R.id.affirmorder_item_tihuophonevalue_textview);
		//申诉按钮
		Button orderappeal_layout_footersubmit_button=(Button)parementView.findViewById(R.id.orderappeal_layout_footersubmit_button);
		
		
	}
}
