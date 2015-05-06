package com.shenma.yueba.yangjia.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;

/**
 * 开小票
 * @author a
 */

public class KaiXiaoPiaoActivity extends BaseActivityWithTopView {
	private EditText et_product_name;
	private EditText et_product_style;
	private EditText et_product_color;
	private EditText et_product_number;
	private EditText et_product_price;
	private TextView tv_product_code;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
	}
}
