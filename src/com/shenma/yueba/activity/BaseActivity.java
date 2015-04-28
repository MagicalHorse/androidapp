package com.shenma.yueba.activity;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.shenma.yueba.util.CustomProgressDialog;

import roboguice.activity.RoboActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

/**
 * activity的依赖注入基类
 * @author a
 */
public class BaseActivity extends Activity {
	protected BaseActivity mContext;// 上下文
	private HttpUtils http = new HttpUtils();//httpUtils初始化
	private RequestParams params = new RequestParams();//参数对象初始化
	private  CustomProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		dialog = new CustomProgressDialog(this);
	}
	
	protected void showDialog(){
		dialog.show();
	}
	
	
	
	public void dismissDialog(){
		dialog.dismiss();
	}

	protected void skip(Class<?> clazz, boolean isCloseSelf) {
		startActivity(new Intent(mContext, clazz));
		if (isCloseSelf) {
			mContext.finish();
		}
	}

	protected void skipForResult(Class<?> clazz, int requestCode) {
		startActivityForResult(new Intent(mContext, clazz), requestCode);
	}

	protected void skip(String key, String value, Class<?> clazz,
			boolean isCloseSelf) {
		Intent intent = new Intent(mContext, clazz);
		if (!TextUtils.isEmpty(key)) {
			intent.putExtra(key, value);
		}
		startActivity(intent);
		if (isCloseSelf) {
			mContext.finish();
		}
	}

	protected void skipForResult(String key, String value, Class<?> clazz,
			int requestCode) {
		Intent intent = new Intent(mContext, clazz);
		if (!TextUtils.isEmpty(key)) {
			intent.putExtra(key, value);
		}
		startActivityForResult(intent, requestCode);
	}

	protected void skipForResult(String key, boolean value, Class<?> clazz,
			int requestCode) {
		Intent intent = new Intent(mContext, clazz);
		if (!TextUtils.isEmpty(key)) {
			intent.putExtra(key, value);
		}
		startActivityForResult(intent, requestCode);
	}

	protected void skip(String key, int obj, Class<?> clazz, boolean isCloseSelf) {
		Intent intent = new Intent(mContext, clazz);
		if (!TextUtils.isEmpty(key)) {
			intent.putExtra(key, obj);
		}
		startActivity(intent);
		if (isCloseSelf) {
			mContext.finish();
		}
	}

	/**
	 * intent
	 * @param key
	 * @param obj
	 * @param clazz
	 * @param isCloseSelf
	 */
	protected void skip(String key, boolean obj, Class<?> clazz,
			boolean isCloseSelf) {
		Intent intent = new Intent(mContext, clazz);
		if (!TextUtils.isEmpty(key)) {
			intent.putExtra(key, obj);
		}
		startActivity(intent);
		if (isCloseSelf) {
			mContext.finish();
		}
	}

	protected <A extends View> A getView(int id) {
		return (A) findViewById(id);
	}

	protected <A extends View> A getView(int id, View view) {
		return (A) view.findViewById(id);
	}
	
	
	/**
	 * 获取httpUtils对象
	 * @return
	 */
	 protected HttpUtils getHttpUtils(){
		 if(http!=null){
			 return http;
		 }else{
			 return new HttpUtils();
		 }
	 }
	
	 /**
	  * 获取请求参数对象
	  * @return
	  */
	 protected RequestParams getRequestParams(){
		 if(params!=null){
			 return params;
		 }else{
			 return new RequestParams();
		 }
	 }
}
