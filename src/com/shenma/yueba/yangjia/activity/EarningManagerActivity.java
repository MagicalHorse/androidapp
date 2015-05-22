package com.shenma.yueba.yangjia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.baijia.modle.Income;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ToolsUtil;

/**
 * 收益管理
 * @author a
 *
 */
public class EarningManagerActivity extends BaseActivityWithTopView {

	private Income income;//收益数据

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.earnings_manager_layout);
		super.onCreate(savedInstanceState);
		getintentData();
		initView();
	}

	/**
	 * 获取传过来的数据
	 */
	private void getintentData() {
		Intent intent = getIntent();
		income = (Income) intent.getExtras().getSerializable("earningData");
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
		
		tv_today_income.setText(income!=null?ToolsUtil.nullToString(income.getToday_income()):"");
		tv_total_income_money.setText(income!=null?ToolsUtil.nullToString(income.getTotal_income()):"");
		tv_withdraw_cash_money.setText(income!=null?ToolsUtil.nullToString(income.getRequest_amount()):"");
		
		FontManager.changeFonts(mContext, tv_top_title,tv_earnings_today_title,tv_today_income,tv_withdraw_cash_title,
				tv_withdraw_cash_money,tv_withdraw_cash_history,tv_apply_withdraw,tv_total_income_title
				,tv_total_income_money);
	}
}
