package com.shenma.yueba.baijia.dialog;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shenma.yueba.R;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-24 上午11:44:52  
 * 程序的简单说明  
 */

public class OrderPayDialog extends AlertDialog{
View ll;
Context context;
ProgressBar orderpay_dialog_layout_progressbar;
TextView orderpay_dialog_layout_textview;
TextView orderpay_dialog_layout_sucess_textview;
OrderPayOnClick_Listener orderPayOnClick_Listener;
Button orderpay_dialog_layout_sucess_button;
Timer timer;
int maxTime=5;
	public OrderPayDialog(Context context,OrderPayOnClick_Listener orderPayOnClick_Listener,boolean cancelsttaus) {
		super(context, R.style.MyDialog);
		//super(context);
		this.context=context;
		this.orderPayOnClick_Listener=orderPayOnClick_Listener;
		this.setCancelable(cancelsttaus);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		ll=(LinearLayout)LinearLayout.inflate(context, R.layout.orderpay_dialog_layout, null);
		
		setContentView(ll);
		initView();
	}
	
	public void showDialog()
	{
		show();
		DisplayMetrics dm=new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width=dm.widthPixels;
		WindowManager.LayoutParams params=this.getWindow().getAttributes();
		params.width=width-(width/3);
		params.height=width-(width/3);
		this.getWindow().setAttributes(params);
	}
	
	void initView()
	{
		orderpay_dialog_layout_progressbar=(ProgressBar)ll.findViewById(R.id.orderpay_dialog_layout_progressbar);
		orderpay_dialog_layout_textview=(TextView)ll.findViewById(R.id.orderpay_dialog_layout_textview);
		orderpay_dialog_layout_sucess_textview=(TextView)ll.findViewById(R.id.orderpay_dialog_layout_sucess_textview);
		orderpay_dialog_layout_sucess_button=(Button)ll.findViewById(R.id.orderpay_dialog_layout_sucess_button);
		orderpay_dialog_layout_sucess_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopTimer();
				if(orderPayOnClick_Listener!=null)
				{
					orderPayOnClick_Listener.on_Click();
				}
			}
		});
	}
	
	/***
	 * 显示查询中
	 * **/
	public void showLoading()
	{
		orderpay_dialog_layout_textview.setVisibility(View.VISIBLE);
		orderpay_dialog_layout_progressbar.setVisibility(View.GONE);
		orderpay_dialog_layout_sucess_textview.setVisibility(View.GONE);
		orderpay_dialog_layout_sucess_button.setVisibility(View.GONE);
	}
	
	/***
	 * 显示支付成功
	 * **/
	public void showSucess()
	{
		startTimer();
		orderpay_dialog_layout_sucess_textview.setText("支付成功");
		orderpay_dialog_layout_textview.setVisibility(View.GONE);
		orderpay_dialog_layout_progressbar.setVisibility(View.GONE);
		orderpay_dialog_layout_sucess_textview.setVisibility(View.VISIBLE);
		orderpay_dialog_layout_sucess_button.setVisibility(View.VISIBLE);
	}
	
	/***
	 * 显示支付失败
	 * **/
	public void showFails()
	{
		startTimer();
		orderpay_dialog_layout_sucess_textview.setText("支付未成功\n可能是网络延时导致,请稍后查询或联系客服人员");
		orderpay_dialog_layout_textview.setVisibility(View.GONE);
		orderpay_dialog_layout_progressbar.setVisibility(View.GONE);
		orderpay_dialog_layout_sucess_textview.setVisibility(View.VISIBLE);
		orderpay_dialog_layout_sucess_button.setVisibility(View.VISIBLE);
	}
	
	
	
	public interface OrderPayOnClick_Listener
	{
		void on_Click();
	}
	
	void startTimer()
	{
		stopTimer();
		timer=new Timer();
		maxTime=5;
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				handler.sendEmptyMessage(200);
			}
		}, 0, 1000);
	}
	
	void stopTimer()
	{
		if(timer!=null)
		{
			timer.cancel();
		}
		timer=null;
	}
	
	Handler handler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			switch(msg.what)
			{
		        case 200:
		        --maxTime;
				if(maxTime<0)
				{
					stopTimer();
					if(orderPayOnClick_Listener!=null)
					{
						orderPayOnClick_Listener.on_Click();
					}
				}else
				{
					orderpay_dialog_layout_sucess_button.setText("("+maxTime+")秒后自动关闭");
				}
				break;
			}
		};
	};
	
	public void cancel() {
		stopTimer();
	};
}
