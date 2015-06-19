package com.shenma.yueba.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.constants.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
			switch(resp.errCode)
			{
			case 0://成功
				MyApplication.getInstance().showMessage(WXPayEntryActivity.this, resp.errStr+"  case:"+0);
				break;
			case -1://错误
				MyApplication.getInstance().showMessage(WXPayEntryActivity.this, resp.errStr+"  case:"+-1);
				break;
			case -2://用户取消
				MyApplication.getInstance().showMessage(WXPayEntryActivity.this,"已取消"+"  case:"+-2);
				break;
			}
		}
		
	}
}