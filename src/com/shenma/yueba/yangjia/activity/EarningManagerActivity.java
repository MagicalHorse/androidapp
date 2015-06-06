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
public class EarningManagerActivity extends BaseActivityWithTopView implements OnClickListener {

	private Income income;//收益数据
	private TextView tv_earnings_today_title;
	private ImageView iv_help;
	private TextView tv_today_income;
	private TextView tv_withdraw_cash_title;
	private TextView tv_withdraw_cash_money;
	private TextView tv_withdraw_cash_history;
	private TextView tv_apply_withdraw;
	private TextView tv_total_income_title;
	private TextView tv_total_income_money;
	private TextView tv_income_detail;

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
		
		
		
		tv_earnings_today_title = getView(R.id.tv_earnings_today_title);
		iv_help = getView(R.id.iv_help);
		tv_today_income = getView(R.id.tv_today_income);
		tv_withdraw_cash_title = getView(R.id.tv_withdraw_cash_title);
		tv_withdraw_cash_money = getView(R.id.tv_withdraw_cash_money);
		tv_withdraw_cash_history = getView(R.id.tv_withdraw_cash_history);
		tv_apply_withdraw = getView(R.id.tv_apply_withdraw);
		tv_total_income_title = getView(R.id.tv_total_income_title);
		tv_total_income_money = getView(R.id.tv_total_income_money);
		tv_income_detail = getView(R.id.tv_income_detail);
		tv_today_income.setText(income!=null?ToolsUtil.nullToString(income.getToday_income()):"");
		tv_total_income_money.setText(income!=null?ToolsUtil.nullToString(income.getTotal_income()):"");
		tv_withdraw_cash_money.setText(income!=null?ToolsUtil.nullToString(income.getRequest_amount()):"");
		tv_income_detail.setOnClickListener(this);
		tv_apply_withdraw.setOnClickListener(this);
		tv_withdraw_cash_history.setOnClickListener(this);
		FontManager.changeFonts(mContext, tv_top_title,tv_earnings_today_title,tv_today_income,tv_withdraw_cash_title,
				tv_withdraw_cash_money,tv_withdraw_cash_history,tv_apply_withdraw,tv_total_income_title
				,tv_total_income_money);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_income_detail://收入明细
			skip(IncomeDetailActivity2.class, false);
			break;
		case R.id.tv_withdraw_cash_history://提现历史
			skip(WithdrawHistoryActivity.class, false);
			break;
		case R.id.tv_apply_withdraw://申请提现
			Intent intent = new Intent(this,ApplyWithdrawActivity.class);
			intent.putExtra("money", tv_withdraw_cash_money.getText().toString().trim());
			startActivity(intent);
		default:
			break;
		}
	}
}
