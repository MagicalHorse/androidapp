package com.shenma.yueba.baijia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.SharedUtil;
/**
 * 设置新密码
 * @author a
 *
 */
public class ResetPasswordActivity extends BaseActivityWithTopView implements OnClickListener{

	private EditText et_new_password;
	private EditText et_new_repassword;
	private EditText et_mobile_no;
	private Button bt_sure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_reset_password_layout);
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		
		initView();
	}

	private void initView() {
		if(this.getIntent().getStringExtra(Constants.USER_MOBILE)==null)
		{
			this.finish();
		}
		setTitle("重置密码");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ResetPasswordActivity.this.finish();
			}
		});
		et_mobile_no=(EditText)findViewById(R.id.et_mobile_no);
		et_mobile_no.setText(this.getIntent().getStringExtra(Constants.USER_MOBILE));
		et_new_password = (EditText) findViewById(R.id.et_new_password);
		et_new_repassword = (EditText) findViewById(R.id.et_new_repassword);
		bt_sure = (Button) findViewById(R.id.bt_sure);
		bt_sure.setOnClickListener(this);
		FontManager.changeFonts(mContext, et_new_password,et_new_repassword,bt_sure,tv_top_title,et_mobile_no);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_sure://找回
			//判断用户是否登录
//			if(!(MyApplication.getInstance().isUserLogin(ResetPasswordActivity.this)))
//			{
//				return;
//			}
			String mobile=et_mobile_no.getText().toString().trim();
			String newpwd=et_new_password.getText().toString().trim();
			String renewpwd=et_new_repassword.getText().toString().trim();
			if(TextUtils.isEmpty(mobile))
			{
				MyApplication.getInstance().showMessage(ResetPasswordActivity.this, "手机号不能为空");
				return;
			}else if(TextUtils.isEmpty(newpwd))
			{
				MyApplication.getInstance().showMessage(ResetPasswordActivity.this, "新密码不能为空");
				return;
			}else if(!(TextUtils.equals(newpwd,renewpwd)))
			{
				MyApplication.getInstance().showMessage(ResetPasswordActivity.this, "两次密码输入不一致");
				return;
			}
			String phone=SharedUtil.getStringPerfernece(ResetPasswordActivity.this, SharedUtil.user_mobile);
			if(phone==null)
			{
				MyApplication.getInstance().showMessage(ResetPasswordActivity.this, "未获取的注册的手机号码");
				return;
			}
			HttpControl httpControl=new HttpControl();
			httpControl.resetPassword(phone, newpwd, new HttpCallBackInterface() {
				
				@Override
				public void http_Success(Object obj) {
					
					Intent intent=new Intent(ResetPasswordActivity.this,LoginAndRegisterActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
                    ResetPasswordActivity.this.finish();
                    MyApplication.getInstance().showMessage(ResetPasswordActivity.this, "修改成功,请重新登录");
				}
				
				@Override
				public void http_Fails(int error, String msg) {
					
					MyApplication.getInstance().showMessage(ResetPasswordActivity.this, msg);
				}
			}, ResetPasswordActivity.this);
			break;

		default:
			break;
		}
		
	}
}
