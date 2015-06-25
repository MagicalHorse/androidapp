package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.BaiJiaPayAdapter;
import com.shenma.yueba.baijia.dialog.OrderPayDialog;
import com.shenma.yueba.baijia.dialog.OrderPayDialog.OrderPayOnClick_Listener;
import com.shenma.yueba.baijia.modle.BaiJiaOrdeDetailsInfoBean;
import com.shenma.yueba.baijia.modle.BaijiaPayInfoBean;
import com.shenma.yueba.baijia.modle.CreatOrderInfoBean;
import com.shenma.yueba.baijia.modle.RequestBaiJiaOrdeDetailsInfoBean;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.wxapi.CreateWeiXinOrderManager;
import com.shenma.yueba.wxapi.WeiXinBasePayManager.WeiXinPayManagerListener;
import com.shenma.yueba.yangjia.modle.OrderDetailBackBean;
import com.shenma.yueba.yangjia.modle.OrderDetailBean;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-16 上午10:03:01  
 * 程序的简单说明  
 */

public class BaijiaPayActivity extends BaseActivityWithTopView implements OrderPayOnClick_Listener{
View parentView;
ListView baijiapay_layout_paytype_listview;
BaiJiaPayAdapter baiJiaPayAdapter;
List<BaijiaPayInfoBean> bean=new ArrayList<BaijiaPayInfoBean>();
CreatOrderInfoBean creatOrderInfoBean;
OrderPayDialog orderPayDialog;
HttpControl httpControl=new HttpControl();
//微信支付 商品名描述
String messageTitle="";
//微信支付商品信息描述
String messageDesc="";
boolean isBroadcast=false;//是否注册广播监听 支付结果

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		parentView=this.getLayoutInflater().inflate(R.layout.baijiapaylayout, null);
		setContentView(parentView);
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		if(this.getIntent().getSerializableExtra("PAYDATA")!=null)
		{
			creatOrderInfoBean=(CreatOrderInfoBean)this.getIntent().getSerializableExtra("PAYDATA");
		}else
		{
			finish();
			MyApplication.getInstance().showMessage(BaijiaPayActivity.this, "数据错误，请从订单页面进入");
			return;
		}
		if(this.getIntent().getStringExtra("MessageTitle")!=null)
		{
			messageTitle=this.getIntent().getStringExtra("MessageTitle");
		}
		if(this.getIntent().getStringExtra("MessageDesc")!=null)
		{
			messageDesc=this.getIntent().getStringExtra("MessageDesc");
		}
		initView();
	}
	
	void initView()
	{
		setTitle("选择付款方式");
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BaijiaPayActivity.this.finish();
			}
		});
		bean.add(new BaijiaPayInfoBean(R.drawable.weixin_icon,BaijiaPayInfoBean.Type.weixinpay,"请支付",Double.toString(creatOrderInfoBean.getTotalAmount()),"微信支付","微信安全支付"));
		baiJiaPayAdapter=new BaiJiaPayAdapter(bean, BaijiaPayActivity.this);
		baijiapay_layout_paytype_listview=(ListView)parentView.findViewById(R.id.baijiapay_layout_paytype_listview);
		baijiapay_layout_paytype_listview.setAdapter(baiJiaPayAdapter);
		baijiapay_layout_paytype_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				BaijiaPayInfoBean baijiaPayInfoBean=bean.get(arg2);
				switch(baijiaPayInfoBean.getType())
				{
				case weixinpay:
					//注册监听 支付结果
					registerBroadcast();
					//启动微信支付
					startWenXinPay();
					break;
				}
			}
		});
	}
	
	
	/****
	 * 广播 接受 支付结果 用于关闭页面及 后台查询确认支付结果
	 * ***/
     BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent==null)
			{
				return;
			}else if(intent.getAction().equals(CreateWeiXinOrderManager.WEIXINACTION_FILTER))//如果接收到 支付的广播
			{
				String resutl_Code=intent.getStringExtra("Resutl_Code");
				//MyApplication.getInstance().showMessage(BaijiaPayActivity.this, "接受到广播 Resutl_Code："+resutl_Code);
				queyPayStatus();
				/*if(resutl_Code==null)
				{
					return;
				}else if(resutl_Code.equals("SUCESS"))
				{
					//与服务器通信 查询 后台支付状态
					queyPayStatus();
				}*/
				unRegisterBroadcast();
			}
		}
	};
	
	
	void queyPayStatus()
	{
		showPayDialogLoading();
		httpControl.getBaijiaOrderDetails(creatOrderInfoBean.getOrderNo(),true,new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				if(obj==null)
				{
					http_Fails(500, "");
				}else if(obj instanceof RequestBaiJiaOrdeDetailsInfoBean && ((RequestBaiJiaOrdeDetailsInfoBean)obj).getData()!=null)
				{
					RequestBaiJiaOrdeDetailsInfoBean orderDetailBackBean=(RequestBaiJiaOrdeDetailsInfoBean)obj;
					BaiJiaOrdeDetailsInfoBean baiJiaOrdeDetailsInfoBean=orderDetailBackBean.getData();
					//MyApplication.getInstance().showMessage(BaijiaPayActivity.this, "查询到订单数据 status:"+baiJiaOrdeDetailsInfoBean.getOrderStatus());
					if(baiJiaOrdeDetailsInfoBean.getOrderStatus()==1)//如果当前订单状态是 已付款
					{
						showSucessDialog();
					}else
					{
						showFailsDailog();
					}
					
				}else
				{
					http_Fails(500, "");
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(BaijiaPayActivity.this, "查询失败，请在订单页面查看订单状态");
				cancelPayDialog();
			}
		}, BaijiaPayActivity.this);
	}
	
	
	/***
	 * 支付注册广播监听
	 * ***/
	void registerBroadcast()
	{
		if(!isBroadcast)
		{
		  IntentFilter filter=new IntentFilter(CreateWeiXinOrderManager.WEIXINACTION_FILTER);
		  BaijiaPayActivity.this.registerReceiver(broadcastReceiver, filter);
		  isBroadcast=true;
		}
	}
	
	/***
	 * 支付注销广播监听
	 * ***/
	void unRegisterBroadcast()
	{
		if(isBroadcast)
		{
			BaijiaPayActivity.this.unregisterReceiver(broadcastReceiver);
			isBroadcast=false;
		}
		
	}
	
	void startWenXinPay()
	{
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("messageTitle", messageTitle);
		map.put("TotalAmount", creatOrderInfoBean.getTotalAmount());
		map.put("OrderNo", creatOrderInfoBean.getOrderNo());
		CreateWeiXinOrderManager cwxm=new CreateWeiXinOrderManager(BaijiaPayActivity.this, new WeiXinPayManagerListener() {
			
			@Override
			public void success(Object obj) {
				
			}
			
			@Override
			public void fails(String msg) {
				MyApplication.getInstance().showMessage(BaijiaPayActivity.this, msg);
			}
		},map );
		cwxm.execute();
	}
	
	@Override
		protected void onDestroy() {
			
			super.onDestroy();
			unRegisterBroadcast();
		}
	
	/***
	 * 显示查询中
	 * **/
	public void showPayDialogLoading()
	{
		if(orderPayDialog==null)
		{
			orderPayDialog=new OrderPayDialog(BaijiaPayActivity.this,this);
		}
		orderPayDialog.showDialog();
		orderPayDialog.showLoading();
		
	}
	
	/***
	 * 显示查询成为（即支付成功）
	 * **/
	public void showSucessDialog()
	{
		if(orderPayDialog==null)
		{
			orderPayDialog=new OrderPayDialog(BaijiaPayActivity.this,this);
		}
		orderPayDialog.showDialog();
		orderPayDialog.showSucess();
		
	}
	
	/***
	 * 显示查询失败
	 * **/
	public void showFailsDailog()
	{
		if(orderPayDialog==null)
		{
			orderPayDialog=new OrderPayDialog(BaijiaPayActivity.this,this);
		}
		orderPayDialog.showDialog();
		orderPayDialog.showFails();
		
	}
	
	
	
	public void cancelPayDialog()
	{
		if(orderPayDialog!=null)
		{
			orderPayDialog.cancel();
		}
	}

	@Override
	public void on_Click() {
		finish();
	}
	
	@Override
		public void finish() {
			super.finish();
			setResult(200, this.getIntent().putExtra("PAYRESULT", "SUCESS"));
		}
}
