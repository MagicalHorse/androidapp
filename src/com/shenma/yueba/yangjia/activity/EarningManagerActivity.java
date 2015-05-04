package com.shenma.yueba.yangjia.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.util.FontManager;

/**
 * 收益管理
 * @author a
 *
 */
public class EarningManagerActivity extends BaseActivityWithTopView {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.earnings_manager_layout);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setTitle("收益管理");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EarningManagerActivity.this.finish();
			}
		});
		
		
		
		TextView tv_earnings_today_title = getView(R.id.tv_earnings_today_title);
		ImageView iv_help = getView(R.id.iv_help);
		TextView tv_today_income = getView(R.id.tv_today_income);
		TextView tv_withdraw_cash_title = getView(R.id.tv_withdraw_cash_title);
		TextView tv_withdraw_cash_money = getView(R.id.tv_withdraw_cash_money);
		TextView tv_withdraw_cash_history = getView(R.id.tv_withdraw_cash_history);
		TextView tv_apply_withdraw = getView(R.id.tv_apply_withdraw);
		TextView tv_total_income_title = getView(R.id.tv_total_income_title);
		TextView tv_total_income_money = getView(R.id.tv_total_income_money);
		
		FontManager.changeFonts(mContext, tv_earnings_today_title,tv_today_income,tv_withdraw_cash_title,
				tv_withdraw_cash_money,tv_withdraw_cash_history,tv_apply_withdraw,tv_total_income_title
				,tv_total_income_money);
	}
}
