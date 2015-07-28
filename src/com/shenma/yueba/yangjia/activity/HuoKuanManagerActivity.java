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
import com.umeng.analytics.MobclickAgent;

/**
 * 货款管理
 * 
 * @author a
 * 
 */
public class HuoKuanManagerActivity extends BaseActivityWithTopView implements
		OnClickListener, OnProgressBarListener {

	private TextView tv_in_and_out;
	private TextView tv_tatal_money;
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
	private int hadProgress = 0,canProgress = 0,freezeProgress = 0,backProgress = 0;
	private CountDownTimer timer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);// 加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.huokuan_manager);
		super.onCreate(savedInstanceState);
		initView();
		getHuoKuanManagerInfo();
	}


	private void initView() {
		setTitle("货款管理");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HuoKuanManagerActivity.this.finish();
			}
			
		});
		tv_in_and_out = getView(R.id.tv_in_and_out);
		tv_in_and_out.setOnClickListener(this);
		tv_had_withdraw_ratio = getView(R.id.tv_had_withdraw_ratio);
		tv_had_withdraw_money = getView(R.id.tv_had_withdraw_money);
		tv_tatal_money = getView(R.id.tv_tatal_money);
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
			skip(HuoKuanIncomingAndOutgoingsActivity.class, false);
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
					tv_had_withdraw_ratio.setText("已提现货款 "+ToolsUtil.nullToString(bean.getData().getPickedPercent())+"%");
					tv_had_withdraw_money.setText("￥"+ToolsUtil.nullToString(bean.getData().getPickedAmount()));
					try {
						hadProgress = (int) Double.parseDouble(ToolsUtil.nullToString(bean.getData().getPickedPercent()));
//						if(hadProgress == 0){
//							hadProgress = 2;
//						}
					} catch (Exception e) {
						hadProgress = 0;
					}
					try {
						canProgress = (int) Double.parseDouble(ToolsUtil.nullToString(bean.getData().getCanPickPercent()));
//						if(canProgress == 0){
//							canProgress = 2;
//						}
					} catch (Exception e) {
						canProgress = 0;
					}
					try {
						freezeProgress = (int) Double.parseDouble(ToolsUtil.nullToString(bean.getData().getFrozenPercent()));
//						if(freezeProgress == 0){
//							freezeProgress = 2;
//						}
					} catch (Exception e) {
						freezeProgress = 0;
					}
					try {
						backProgress = (int) Double.parseDouble(ToolsUtil.nullToString(bean.getData().getRmaPercent()));
//						if(backProgress == 0){
//							backProgress = 2;
//						}
					} catch (Exception e) {
						backProgress = 0;
					}
					tv_can_withdraw_ratio.setText("可提现货款 "+ToolsUtil.nullToString(bean.getData().getCanPickPercent())+"%");
					tv_can_withdraw_money.setText("￥"+ToolsUtil.nullToString(bean.getData().getCanPickAmount()));
					tv_freeze_ratio.setText("冻结货款 "+ToolsUtil.nullToString(bean.getData().getFrozenPercent())+"%");
					tv_freeze_money.setText("￥"+ToolsUtil.nullToString(bean.getData().getFrozenAmount()));
					tv_back_ratio.setText("退款 "+ToolsUtil.nullToString(bean.getData().getRmaPercent())+"%");
					tv_back_money.setText("￥"+ToolsUtil.nullToString(bean.getData().getRmaAmount()));
					tv_tatal_money.setText("￥"+ToolsUtil.nullToString(bean.getData().getTotalAmount()));
					timer = new CountDownTimer(1000, 10) {
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
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				// TODO Auto-generated method stub
				
			}
		}, HuoKuanManagerActivity.this, true, true);
	}
	
	
	
	
	  @Override
	    protected void onDestroy() {
		  if(timer!=null){
			  timer.cancel();
		  }
	    	MyApplication.getInstance().removeActivity(this);//加入回退栈
	    	super.onDestroy();
	    }
	  
	  public void onResume() {
			super.onResume();
			MobclickAgent.onResume(this);
			}
			public void onPause() {
			super.onPause();
			MobclickAgent.onPause(this);
			}
}
