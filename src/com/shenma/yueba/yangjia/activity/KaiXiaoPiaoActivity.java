package com.shenma.yueba.yangjia.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.util.FontManager;

/**
 * 开小票
 * @author a
 */

public class KaiXiaoPiaoActivity extends BaseActivityWithTopView implements OnClickListener {
	private EditText et_product_name;
	private EditText et_product_style;
	private EditText et_product_color;
	private EditText et_product_number;
	private EditText et_product_price;
	private TextView tv_product_code;
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
		et_product_name = (EditText) findViewById(R.id.et_product_name);
		et_product_style = (EditText) findViewById(R.id.et_product_style);
		et_product_color = (EditText) findViewById(R.id.et_product_color);
		et_product_number = (EditText) findViewById(R.id.et_product_number);
		et_product_price = (EditText) findViewById(R.id.et_product_price);
		tv_product_code = (TextView) findViewById(R.id.tv_product_code);
		tv_facetoface_product = (TextView) findViewById(R.id.tv_facetoface_product);
		FontManager.changeFonts(this, et_product_name,et_product_style,et_product_color,
				et_product_number,et_product_price,tv_product_code,tv_facetoface_product);
		tv_facetoface_product.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
