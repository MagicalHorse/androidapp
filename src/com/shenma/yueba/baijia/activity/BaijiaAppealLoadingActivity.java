package com.shenma.yueba.baijia.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-12 下午3:36:11  
 * 程序的简单说明  败家 申诉 中
 */

public class BaijiaAppealLoadingActivity extends BaseActivityWithTopView{
View parentView;
//申诉时间
TextView appealloading_layout_appealtimevalue_textview;
//申诉进度
TextView appealloading_layout_appealprogressvalue_textview;
//申诉理由
TextView appealloading_layout_appealdescvalue_textview;
//买手账号
TextView appealloading_layout_buyernovalue_textview;
//买手电话
TextView appealloading_layout_buyermobilevalue_textview;
//提货地址
TextView appealloading_layout_goodsaddressvalue_textview;
//专员电话
TextView appealloading_layout_commissionermobilevalue_textview;
//联系买手
TextView appealloading_layout_lianxibuyer_textview;
//撤销申诉
Button  appealloading_layout_cancelappeal_button;
//撤销退款
Button  appealloading_layout_cancelpayprice_button;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		parentView=this.getLayoutInflater().inflate(R.layout.appealloading_layout, null);
		setContentView(parentView);
		super.onCreate(savedInstanceState);
		initView();
	}
	
	void initView()
	{
		setTitle("申诉中");
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BaijiaAppealLoadingActivity.this.finish();
			}
		});
		ToolsUtil.setFontStyle(BaijiaAppealLoadingActivity.this, parentView, R.id.appealloading_layout_appealtime_textview,R.id.appealloading_layout_appealprogress_textview,R.id.appealloading_layout_appealdesc_textview,R.id.appealloading_layout_buyerno_textview,R.id.appealloading_layout_buyermobile_textview,R.id.appealloading_layout_goodsaddress_textview,R.id.appealloading_layout_commissionermobile_textview,R.id.appealloading_layout_appealtimevalue_textview,R.id.appealloading_layout_appealprogressvalue_textview,R.id.appealloading_layout_appealdescvalue_textview,R.id.appealloading_layout_buyernovalue_textview,R.id.appealloading_layout_buyermobilevalue_textview,R.id.appealloading_layout_goodsaddressvalue_textview,R.id.appealloading_layout_commissionermobilevalue_textview,R.id.appealloading_layout_lianxibuyer_textview,R.id.appealloading_layout_cancelappeal_button,R.id.appealloading_layout_cancelpayprice_button);
		//买手头像
		RoundImageView appealloading_layout_buyericon_roundimageview=(RoundImageView)findViewById(R.id.appealloading_layout_buyericon_roundimageview);
		//买手电话的 图标
		ImageView appealloading_layout_phoneicon_textview=(ImageView)findViewById(R.id.appealloading_layout_phoneicon_textview);
		//专员电话图标
		ImageView appealloading_layout_commissionerphoneicon_textview=(ImageView)findViewById(R.id.appealloading_layout_commissionerphoneicon_textview);
		//申诉时间
		appealloading_layout_appealtimevalue_textview=(TextView)findViewById(R.id.appealloading_layout_appealtimevalue_textview);
		//申诉进度
		appealloading_layout_appealprogressvalue_textview=(TextView)findViewById(R.id.appealloading_layout_appealprogressvalue_textview);
		//申诉理由
		appealloading_layout_appealdescvalue_textview=(TextView)findViewById(R.id.appealloading_layout_appealdescvalue_textview);
		//买手账号
		appealloading_layout_buyernovalue_textview=(TextView)findViewById(R.id.appealloading_layout_buyernovalue_textview);
		//买手电话
		appealloading_layout_buyermobilevalue_textview=(TextView)findViewById(R.id.appealloading_layout_buyermobilevalue_textview);
		//提货地址
		appealloading_layout_goodsaddressvalue_textview=(TextView)findViewById(R.id.appealloading_layout_goodsaddressvalue_textview);
		//专员电话
		appealloading_layout_commissionermobilevalue_textview=(TextView)findViewById(R.id.appealloading_layout_commissionermobilevalue_textview);
		//联系买手
		appealloading_layout_lianxibuyer_textview=(TextView)findViewById(R.id.appealloading_layout_lianxibuyer_textview);
		//撤销申诉
		appealloading_layout_cancelappeal_button=(Button)findViewById(R.id.appealloading_layout_cancelappeal_button);
		//撤销退款
		appealloading_layout_cancelpayprice_button=(Button)findViewById(R.id.appealloading_layout_cancelpayprice_button);
		
		
	}
}
