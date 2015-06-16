package com.shenma.yueba.yangjia.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ToolsUtil;

/**
 * 开小票
 * @author a
 */

public class KaiXiaoPiaoActivity extends BaseActivityWithTopView implements OnClickListener {
	private EditText et_product_number;
	private EditText et_product_price;
	private TextView tv_tishi;
	private TextView tv_facetoface_product;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.kaixiaopiao_layout);
		super.onCreate(savedInstanceState);
		initView();
	}
	private void initView() {
		setTitle("开小票");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				KaiXiaoPiaoActivity.this.finish();
			}
		});
		et_product_number = (EditText) findViewById(R.id.et_product_number);
		et_product_price = (EditText) findViewById(R.id.et_product_price);
		tv_tishi = (TextView) findViewById(R.id.tv_tishi);
		tv_facetoface_product = (TextView) findViewById(R.id.tv_tishi);
		tv_facetoface_product.setOnClickListener(this);
		FontManager.changeFonts(this,et_product_number,et_product_price,tv_tishi);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_facetoface_product://创建面对面小票
			if(TextUtils.isEmpty(et_product_number.getText().toString())){
				Toast.makeText(mContext, "货号不能为空", 1000).show();
				break;
			}
			if(TextUtils.isEmpty(et_product_price.getText().toString())){
				Toast.makeText(mContext, "售价不能为空", 1000).show();
				break;
			}
			if(!ToolsUtil.isDecimal(et_product_price.getText().toString())){
				Toast.makeText(mContext, "售价不合法", 1000).show();
				break;
			}
			
			break;

		default:
			break;
		}
		
	}
}
