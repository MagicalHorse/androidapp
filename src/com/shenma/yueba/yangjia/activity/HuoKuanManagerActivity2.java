package com.shenma.yueba.yangjia.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.baijia.modle.HuoKuanManagerBackBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.view.progressbar.NumberProgressBar;
import com.shenma.yueba.view.progressbar.NumberProgressBar.ProgressTextVisibility;
import com.shenma.yueba.view.progressbar.OnProgressBarListener;

/**
 * 货款管理
 * 
 * @author a
 * 
 */
public class HuoKuanManagerActivity2 extends BaseActivityWithTopView implements
		OnClickListener, OnProgressBarListener {

	private TextView tv_in_and_out;
	private TextView tv_had_withdraw_ratio;
	private TextView tv_had_withdraw_money;
	private NumberProgressBar numberbar_had_withdraw;
	private TextView tv_can_withdraw_ratio;
	private TextView tv_can_withdraw_money;
	private NumberProgressBar numberbar_can_withdraw;
	private TextView tv_freeze_ratio;
	private TextView tv_freeze_money;
	private NumberProgressBar numberbar_freeze;
	private TextView tv_back_ratio;
	private TextView tv_back_money;
	private NumberProgressBar numberbar_back;
	private int hadProgress = 32,canProgress = 40,freezeProgress = 18,backProgress = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);// 加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.huokuan_manager);
		super.onCreate(savedInstanceState);
		initView();
	}


	private void initView() {
		setTitle("货款管理");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HuoKuanManagerActivity2.this.finish();
			}
		});
		tv_in_and_out = getView(R.id.tv_in_and_out);
		tv_in_and_out.setOnClickListener(this);
		tv_had_withdraw_ratio = getView(R.id.tv_had_withdraw_ratio);
		tv_had_withdraw_money = getView(R.id.tv_had_withdraw_money);
		numberbar_had_withdraw = (NumberProgressBar) findViewById(R.id.numberbar_had_withdraw);
		tv_can_withdraw_ratio = getView(R.id.tv_can_withdraw_ratio);
		tv_can_withdraw_money = getView(R.id.tv_can_withdraw_money);
		numberbar_can_withdraw = (NumberProgressBar) findViewById(R.id.numberbar_can_withdraw);
		tv_freeze_ratio = getView(R.id.tv_freeze_ratio);
		tv_freeze_money = getView(R.id.tv_freeze_money);
		numberbar_freeze = (NumberProgressBar) findViewById(R.id.numberbar_freeze);
		tv_back_ratio = getView(R.id.tv_back_ratio);
		tv_back_money = getView(R.id.tv_back_money);
		numberbar_back = (NumberProgressBar) findViewById(R.id.numberbar_back);
		
		
		numberbar_had_withdraw.setOnProgressBarListener(this);
		numberbar_had_withdraw.setReachedBarHeight(40);
		numberbar_had_withdraw.setUnreachedBarHeight(0);
		numberbar_had_withdraw.setProgressTextVisibility(ProgressTextVisibility.Invisible);
		
		
		
		numberbar_can_withdraw.setOnProgressBarListener(this);
		numberbar_can_withdraw.setReachedBarHeight(40);
		numberbar_can_withdraw.setUnreachedBarHeight(0);
		numberbar_can_withdraw.setProgressTextVisibility(ProgressTextVisibility.Invisible);
		
		
		
		numberbar_freeze.setOnProgressBarListener(this);
		numberbar_freeze.setReachedBarHeight(40);
		numberbar_freeze.setUnreachedBarHeight(0);
		numberbar_freeze.setProgressTextVisibility(ProgressTextVisibility.Invisible);
		
		
		numberbar_back.setOnProgressBarListener(this);
		numberbar_back.setReachedBarHeight(40);
		numberbar_back.setUnreachedBarHeight(0);
		numberbar_back.setProgressTextVisibility(ProgressTextVisibility.Invisible);
		
		
		 CountDownTimer timer = new CountDownTimer(1000, 10) {
				@Override
				public void onTick(long millisUntilFinished) {
					numberbar_had_withdraw.setProgress(hadProgress-(int)millisUntilFinished/10);
					numberbar_can_withdraw.setProgress(canProgress-(int)millisUntilFinished/10);
					numberbar_freeze.setProgress(freezeProgress-(int)millisUntilFinished/10);
					numberbar_back.setProgress(backProgress-(int)millisUntilFinished/10);
				}
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					
				}
			}.start();
		
		FontManager
				.changeFonts(mContext, tv_top_title, tv_had_withdraw_ratio,
						tv_had_withdraw_money, tv_can_withdraw_ratio,
						tv_can_withdraw_money, tv_freeze_ratio,
						tv_freeze_money, tv_back_ratio,
						tv_back_money);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_in_and_out://货款收支
			skip(IncomeDetailActivity.class, false);
			break;
		default:
			break;
		}
	}

	@Override
	public void onProgressChange(int current, int max) {
		// TODO Auto-generated method stub

	}
	
	
	private void getHuoKuanManagerInfo(){
		HttpControl httpContorl = new HttpControl();
		httpContorl.getHuoKuanManagerInfo(new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				HuoKuanManagerBackBean bean = (HuoKuanManagerBackBean) obj;
				if(bean!=null && bean.getData()!=null){
					tv_had_withdraw_ratio.setText("￥"+ToolsUtil.nullToString(bean.getData().getPickedAmount()));
					tv_had_withdraw_ratio.setText("已提现货款"+ToolsUtil.nullToString(bean.getData().getPickedAmount()));
					tv_had_withdraw_ratio.setText("已提现货款"+ToolsUtil.nullToString(bean.getData().getPickedAmount()));
					tv_had_withdraw_ratio.setText("已提现货款"+ToolsUtil.nullToString(bean.getData().getPickedAmount()));
					tv_had_withdraw_ratio.setText("已提现货款"+ToolsUtil.nullToString(bean.getData().getPickedAmount()));
					tv_had_withdraw_ratio.setText("已提现货款"+ToolsUtil.nullToString(bean.getData().getPickedAmount()));
					tv_had_withdraw_ratio.setText("已提现货款"+ToolsUtil.nullToString(bean.getData().getPickedAmount()));
					tv_had_withdraw_ratio.setText("已提现货款"+ToolsUtil.nullToString(bean.getData().getPickedAmount()));
					tv_had_withdraw_ratio.setText("已提现货款"+ToolsUtil.nullToString(bean.getData().getPickedAmount()));
					tv_had_withdraw_ratio.setText("已提现货款"+ToolsUtil.nullToString(bean.getData().getPickedAmount()));
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				// TODO Auto-generated method stub
				
			}
		}, HuoKuanManagerActivity2.this, true, true);
	}
}
