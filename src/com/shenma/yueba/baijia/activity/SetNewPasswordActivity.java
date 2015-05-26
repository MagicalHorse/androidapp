package com.shenma.yueba.baijia.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.UserRequestBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.SharedUtil;
/**
 * 设置新密码
 * @author a
 *
 */
public class SetNewPasswordActivity extends BaseActivityWithTopView implements OnClickListener{

	private EditText et_new_password;
	private EditText et_new_repassword;
	private EditText et_old_password;
	private Button bt_sure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_new_password_layout);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setTitle("设置新密码");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SetNewPasswordActivity.this.finish();
			}
		});
		tv_top_right.setText("完成");
		tv_top_right.setVisibility(View.VISIBLE);
		tv_top_right.setOnClickListener(this);
		et_old_password=(EditText)findViewById(R.id.et_old_password);
		et_new_password = (EditText) findViewById(R.id.et_new_password);
		et_new_repassword = (EditText) findViewById(R.id.et_new_repassword);
		//bt_sure = (Button) findViewById(R.id.bt_sure);
		//bt_sure.setOnClickListener(this);
		FontManager.changeFonts(mContext, et_new_password,et_new_repassword,bt_sure,tv_top_title,et_old_password);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_top_right://找回
			//判断用户是否登录
			if(!(MyApplication.getInstance().isUserLogin(SetNewPasswordActivity.this)))
			{
				return;
			}
			String oldpwd=et_old_password.getText().toString().trim();
			String newpwd=et_new_password.getText().toString().trim();
			String renewpwd=et_new_repassword.getText().toString().trim();
			if(TextUtils.isEmpty(oldpwd))
			{
				MyApplication.getInstance().showMessage(SetNewPasswordActivity.this, "旧密码不能为空");
				return;
			}else if(TextUtils.isEmpty(newpwd))
			{
				MyApplication.getInstance().showMessage(SetNewPasswordActivity.this, "新密码不能为空");
				return;
			}else if(!(TextUtils.equals(newpwd,renewpwd)))
			{
				MyApplication.getInstance().showMessage(SetNewPasswordActivity.this, "两次密码输入不一致");
				return;
			}
			String phone=SharedUtil.getStringPerfernece(SetNewPasswordActivity.this, SharedUtil.user_mobile);
			if(phone==null)
			{
				MyApplication.getInstance().showMessage(SetNewPasswordActivity.this, "未获取的注册的手机号码");
				return;
			}
			HttpControl httpControl=new HttpControl();
			httpControl.updateLoginPwd(phone, newpwd, oldpwd, new HttpCallBackInterface() {
				
				@Override
				public void http_Success(Object obj) {
					
					SetNewPasswordActivity.this.finish();
				}
				
				@Override
				public void http_Fails(int error, String msg) {
					
					MyApplication.getInstance().showMessage(SetNewPasswordActivity.this, msg);
				}
			}, SetNewPasswordActivity.this);
			break;

		default:
			break;
		}
		
	}
}
