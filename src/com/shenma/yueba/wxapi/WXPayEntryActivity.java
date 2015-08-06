package com.shenma.yueba.wxapi;


import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.constants.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //android:theme="@android:style/Theme.Translucent
        setTheme(android.R.style.Theme_Translucent);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			//sendBroadCast("SUCESS");
			switch(resp.errCode)
			{
			case 0://成功
				Log.i("order", "--0---"+resp.errCode+""+resp.errStr);
				//MyApplication.getInstance().showMessage(WXPayEntryActivity.this, "  case:"+0);
				
				//MyApplication.getInstance().showMessage(WXPayEntryActivity.this,"支付成功");
				
				//支付成功进行订单查询确认
				finish();
				sendBroadCast("SUCESS");
				break;
			case -1://错误
				Log.i("order", "-   -1---"+resp.errCode+""+resp.errStr);
				String errmsg=resp.errStr;
				if(errmsg==null || errmsg.equals(""))
				{
					errmsg="未知错误";
				}
				MyApplication.getInstance().showMessage(WXPayEntryActivity.this,errmsg);
				finish();
				break;
			case -2://用户取消
				Log.i("order", "--  -2---"+resp.errCode+""+resp.errStr);
				MyApplication.getInstance().showMessage(WXPayEntryActivity.this,"已取消支付");
				finish();
				break;
			}
		}
		
	}
	
	/****
	 * 发送广播通知 成功或失败
	 * **/
	void sendBroadCast(String msg)
	{
		//MyApplication.getInstance().showMessage(this, "发送广播:"+msg);
		Intent intent=new Intent(CreateWeiXinOrderManager.WEIXINACTION_FILTER);
		intent.putExtra("Resutl_Code", msg);
		this.sendBroadcast(intent);
	}
}