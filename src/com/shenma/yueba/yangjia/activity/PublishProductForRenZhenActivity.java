package com.shenma.yueba.yangjia.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.util.FontManager;
import com.umeng.analytics.MobclickAgent;

/**
 * 发布商品（认证买手）
 * @author a
 *
 */
public class PublishProductForRenZhenActivity extends BaseActivityWithTopView implements OnClickListener{

	private TextView tv_publish;//发布
	private TextView tv_price_title;//
	private EditText et_product_introduce;//
	private TextView tv_retain;//还可以输入几个字
	private TextView tv_add;//添加商品属性
	private TextView tv_products_title;//
	private LinearLayout ll_images_container;
	private LinearLayout ll_guige_more;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_product_layout);
		initView();
		super.onCreate(savedInstanceState);
	}
	private void initView() {
		View.inflate(mContext, R.layout.guige_item_layuout, null);
		tv_publish = getView(R.id.tv_publish);
		tv_retain = getView(R.id.tv_retain);
		ll_images_container = getView(R.id.ll_images_container);
		tv_price_title = getView(R.id.tv_price_title);
		et_product_introduce = getView(R.id.et_product_introduce);
		tv_add = getView(R.id.tv_add);
		tv_products_title = getView(R.id.tv_products_title);
		ll_images_container = getView(R.id.ll_images_container);
		ll_guige_more = getView(R.id.ll_guige_more);
		tv_publish.setOnClickListener(this);
		tv_add.setOnClickListener(this);
		FontManager.changeFonts(mContext, tv_publish,tv_price_title,et_product_introduce,tv_add,
				tv_products_title);
	}
	@Override
	public void onClick(View v) {
	
		switch (v.getId()) {
		case R.id.tv_publish://发布商品
			
			break;
		case R.id.tv_add://添加商品属性
			
			break;
		default:
			break;
		}
		
	}
	
	
	
	@Override
	protected void onDestroy() {
		MyApplication.getInstance().addActivity(this);
		super.onDestroy();
	}
	
	
	  public void onResume() {
			super.onResume();
			MobclickAgent.onResume(this);
			}
			public void onPause() {
			super.onPause();
			MobclickAgent.onPause(this);
			}
}
