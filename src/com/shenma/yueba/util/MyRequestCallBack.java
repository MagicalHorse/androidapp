package com.shenma.yueba.util;

import java.lang.reflect.Type;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.shenma.yueba.baijia.modle.CommonBackBean;

public abstract class MyRequestCallBack extends RequestCallBack {

	private Context ctx;
	private CustomProgressDialog progressDialog;
	public MyRequestCallBack(Context ctx){
		this.ctx = ctx;
	}
	
	@Override
	public void onStart() {
		progressDialog = CustomProgressDialog.createDialog(ctx);
		progressDialog.show();
		super.onStart();
	}
	@Override
	public void onSuccess(ResponseInfo responseInfo) {
		if(progressDialog!=null){
			progressDialog.dismiss();
		}
		CommonBackBean backBean = getCommonData((String)responseInfo.result);
		if(200 == backBean.getStatusCode()){
			onSuccessd((String)responseInfo.result);
		}else{
			Toast.makeText(ctx,backBean.getMessage(), 1000).show();
		}

	}
	@Override
	public void onFailure(HttpException error, String msg) {
		if(progressDialog!=null){
			progressDialog.dismiss();
		}
		Toast.makeText(ctx,"获取失败，请稍后重试", 1000).show();
	}
	
	
	@Override
	public void onCancelled() {
		if(progressDialog!=null){
			progressDialog.dismiss();
		}
		super.onCancelled();
	}
	
	public abstract void onSuccessd(String result);
	
	private CommonBackBean getCommonData(String jsonData){
		CommonBackBean bean = null;
		if(TextUtils.isEmpty(jsonData)){
			return null;
		}else {
			try {
				Type type = new TypeToken<CommonBackBean>() {
				}.getType();
				Gson gson = new Gson();
				bean = gson.fromJson(jsonData, type);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return bean;
		
	
	}
	

}
