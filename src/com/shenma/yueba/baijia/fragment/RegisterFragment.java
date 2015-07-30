package com.shenma.yueba.baijia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.FillPersonDataActivity;
import com.shenma.yueba.baijia.modle.CommonBackBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.MyCountDown;
import com.shenma.yueba.util.ToolsUtil;

public class RegisterFragment extends BaseFragment implements OnClickListener {

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
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.register_fragment_layout, null);
			et_mobile = (EditText) view.findViewById(R.id.et_mobile);
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

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_getcode://获取验证码
			if(getCodeString.equals(tv_getcode.getText().toString().trim())){
				String mobile = et_mobile.getText().toString().trim();
				//判断手机号码是否为空
				if(TextUtils.isEmpty(mobile)){
					Toast.makeText(getActivity(), "手机号不能为空", 1000).show();
					return;
				}
				//判断手机号码是否合法  由于现在新手机号有扩充，正则表达式判断可能会有错误
				if(et_mobile.getText().toString().trim().length()!=11){
					Toast.makeText(getActivity(), "请输入正确的手机号", 1000).show();
					break;
				}
				HttpControl httpControl=new HttpControl();
				httpControl.sendPhoeCode(et_mobile.getText().toString().trim(), "0",new HttpCallBackInterface() {
					
					@Override
					public void http_Success(Object obj) {
						Toast.makeText(getActivity(), "获取成功", 1000).show();
						new MyCountDown(maxSecond * 1000, 1000, tv_getcode, getCodeString).start();
					}
					
					@Override
					public void http_Fails(int error, String msg) {
						
						Toast.makeText(getActivity(),msg, 1000).show();
					}
					
					
				}, getActivity());
			}
			break;
		case R.id.tv_confirm://验证验证码
			String mobile = et_mobile.getText().toString().trim();
			//判断手机号码是否为空
			if(TextUtils.isEmpty(mobile)){
				Toast.makeText(getActivity(), "手机号不能为空", 1000).show();
				return;
			}//判断手机号码是否合法
			else if(!ToolsUtil.checkPhone(mobile)){
				Toast.makeText(getActivity(), "请输入正确的手机号", 1000).show();
				return;
			}
			String code = et_code.getText().toString().trim();
			if(TextUtils.isEmpty(code)){
				Toast.makeText(getActivity(), "验证码不能为空", 1000).show();
				return;
			}
			HttpControl httpControl=new HttpControl();
			httpControl.validVerifyCode(et_mobile.getText().toString().trim(), et_code.getText().toString().trim(), new HttpCallBackInterface() {
				
				@Override
				public void http_Success(Object obj) {
					
					Intent intent = new Intent(getActivity(),FillPersonDataActivity.class);
					intent.putExtra(Constants.MOBILE, et_mobile.getText().toString().trim());
					startActivity(intent);
				}
				
				@Override
				public void http_Fails(int error, String msg) {
					
					Toast.makeText(getActivity(),msg, 1000).show();
				}
			}, getActivity());
			break;
		default:
			break;
		}
		
	}
}
