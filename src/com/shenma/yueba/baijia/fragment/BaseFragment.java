package com.shenma.yueba.baijia.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shenma.yueba.util.CustomProgressDialog;

public class BaseFragment extends Fragment {


	private HttpUtils httpUtils;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		httpUtils = new HttpUtils();
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	protected HttpUtils getHttpUtils(){
		if(httpUtils == null){
			httpUtils = new HttpUtils();
		}else{
			return httpUtils;
		}
		return httpUtils;
	}
	
	
	
}
