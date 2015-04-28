package com.shenma.yueba.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.activity.FillPersonDataActivity;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.MyCountDown;

public class RegisterFragment extends BaseFragment implements OnClickListener, TextWatcher {

	private EditText et_mobile;
	private TextView tv_mobile_title;
	private EditText et_code;
	private TextView tv_getcode;
	private TextView tv_confirm;
	private int maxSecond = 90;
	private String getCodeString = "获取验证码";
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
			view = inflater.inflate(R.layout.register_fragment_layout, null);
			et_mobile = (EditText) view.findViewById(R.id.et_mobile);
			et_mobile.addTextChangedListener(this);
			tv_getcode = (TextView) view
					.findViewById(R.id.tv_getcode);
			et_code = (EditText) view.findViewById(R.id.et_code);
			tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
			tv_mobile_title = (TextView) view.findViewById(R.id.tv_mobile_title);
			tv_confirm.setOnClickListener(this);
			tv_getcode.setOnClickListener(this);
			FontManager.changeFonts(getActivity(), et_mobile,tv_getcode,et_code,tv_confirm,tv_mobile_title);
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
		case R.id.tv_confirm://验证验证码
			Intent intent = new Intent(getActivity(),FillPersonDataActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_getcode://获取验证码
			new MyCountDown(maxSecond * 1000, 1000, tv_getcode, getCodeString)
			.start();
		default:
			break;
		}
		
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		if(arg0.length()>0){
			et_mobile.setVisibility(View.VISIBLE);
		}else{
			et_mobile.setVisibility(View.INVISIBLE);
		}
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
}
