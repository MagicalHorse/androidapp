package com.shenma.yueba.baijia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.FindPasswordActivity;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ToolsUtil;

public class LoginFragment extends BaseFragment implements OnClickListener{

	
	private TextView tv_mobile_title;
	private EditText et_mobile;
	private EditText et_password;
	private TextView tv_other;
	private TextView tv_wechat;
	private TextView tv_forget;
	private Button bt_login;
	private View view;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.login_fragment_layout, null);
			tv_mobile_title = (TextView) view.findViewById(R.id.tv_mobile_title);
			bt_login = (Button) view.findViewById(R.id.bt_login);
			et_mobile = (EditText) view.findViewById(R.id.et_mobile);
			et_password = (EditText) view.findViewById(R.id.et_password);
			tv_forget = (TextView) view.findViewById(R.id.tv_forget);
			tv_other = (TextView) view.findViewById(R.id.tv_other);
			tv_wechat = (TextView) view.findViewById(R.id.tv_wechat);
			tv_forget.setOnClickListener(this);
			bt_login.setOnClickListener(this);
			FontManager.changeFonts(getActivity(), tv_mobile_title,et_mobile,et_password,tv_forget,tv_other,tv_wechat,bt_login);
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_forget://找回密码
			Intent intent = new Intent(getActivity(),FindPasswordActivity.class);
			startActivity(intent);
		case R.id.bt_login://登录
			if(TextUtils.isEmpty(et_mobile.getText().toString())){
				Toast.makeText(getActivity(), "手机号不能为空", 1000).show();
				et_mobile.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.shake));
			}else if(!ToolsUtil.checkPhone(et_mobile.getText().toString())){
				Toast.makeText(getActivity(), "手机号格式不正确", 1000).show();
			}else if(TextUtils.isEmpty(et_password.getText().toString())){
				Toast.makeText(getActivity(), "密码不能为空", 1000).show();
				et_password.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.shake));
			}else{
				//开始网络请求
			}
		default:
			break;
		}
		
	}
}
