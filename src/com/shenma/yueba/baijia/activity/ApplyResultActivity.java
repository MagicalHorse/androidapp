package com.shenma.yueba.baijia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.yangjia.activity.MainActivityForYangJia;
import com.umeng.analytics.MobclickAgent;

/**
 * 申请结果界面(可共用)
 * @author a
 *
 */
public class ApplyResultActivity extends BaseActivityWithTopView implements OnClickListener{
	private TextView tv_content;//内容
	private TextView tv_comment;//标记
	private TextView tv_confirm;//确定
	private String flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		MyApplication.getInstance().addActivity(this);//加入回退栈
		setContentView(R.layout.apply_result_layout);
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		initView();
		getIntentData();
		FontManager.changeFonts(mContext, tv_comment,tv_content,tv_confirm,tv_top_title);
	}
	private void initView() {
		tv_content = getView(R.id.tv_content);
		tv_comment = getView(R.id.tv_comment);
		tv_confirm = getView(R.id.tv_confirm);
		setTitle("结果详情");
		tv_confirm.setOnClickListener(this);
	}
	private void getIntentData() {
		flag = getIntent().getStringExtra("flag");
		if("applaywithdraw".equals(flag)){//申请提现
			tv_content.setText("提现申请已提交，请等待处理");
			tv_comment.setText("预计1小时内到帐");
		}
		if("withdrawGoods".equals(flag)){//提现货款
			tv_content.setText("货款提现申请已提交，请等待处理");
			tv_comment.setText("");
		}
		if("userCertification".equals(flag)){//身份认证
			tv_content.setText("申请已提交，请等待处理");
			tv_comment.setText("");
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_confirm://确定
			if("withdrawGoods".equals(flag)){//提现货款
				setResult(Constants.RESULTCODE);
				ApplyResultActivity.this.finish();
			}else if("applaywithdraw".equals(flag)){//申请提现
				setResult(Constants.RESULTCODE);
				ApplyResultActivity.this.finish();
			}else{
				finish();
			}
		
			
//			MyApplication.getInstance().removeAllActivity();
//			Intent intent = new Intent(mContext, MainActivityForBaiJia.class);
//			startActivity(intent);
			break;

		default:
			break;
		}
		
	}
	
	
	
	 public void onResume() {
		  super.onResume();
		  MobclickAgent.onResume(this);
		  }
		  public void onPause() {
		  super.onPause();
		  MobclickAgent.onPause(this);
		  }

	
	
	@Override
	public void onBackPressed() {
		if("withdrawGoods".equals(flag)){//提现货款
			setResult(Constants.RESULTCODE);
			ApplyResultActivity.this.finish();
		}
		
			super.onBackPressed();
	}
	
	
//	@Override
//	protected void onDestroy() {
//		MyApplication.getInstance().removeActivity(this);//加入回退栈
//		super.onDestroy();
//	}
}
