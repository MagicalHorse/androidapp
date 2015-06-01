package com.shenma.yueba.baijia.activity;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import android.inputmethodservice.Keyboard.Key;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.event.OnClick;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.CityListBackBean;
import com.shenma.yueba.baijia.modle.CityListItembean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.yangjia.adapter.CityListAdapter;

/**
 * 身份认证
 * 
 * @author a
 * 
 */
public class BuyerCertificationActivity2 extends BaseActivityWithTopView implements OnClickListener
		 {

	DrawerLayout drawer_layout;
	ListView lv;
	private TextView tv_store_info_title;
	private TextView tv_self_get_point_title;
	private TextView tv_store_title;
	private EditText et_store_name;
	private TextView tv_zhuangui_title;
	private EditText et_zhuangui_name;
	private TextView tv_number_title;
	private EditText et_number_name;
	private TextView tv_confirm;
	private EditText et_street;
	private TextView tv_county_town;
	private TextView tv_province;
	private TextView tv_city;
	
	private String province_id;
	private String city_id;
	private String town_id;
	private int tag = 1;//1表示省份，2表示城市，3表示区县
	private List<CityListItembean> mList = new ArrayList<CityListItembean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buyer_certification_layout2);
		super.onCreate(savedInstanceState);
		initView();
		drawer_layout.setDrawerShadow(R.drawable.gray_round_bg, GravityCompat.START);  
		drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭手势滑动
//		drawer_layout.setDrawerListener(new DrawerListener() {
//			/** 
//	         * 当抽屉滑动状态改变的时候被调用 
//	         * 状态值是STATE_IDLE（闲置--0）, STATE_DRAGGING（拖拽的--1）, STATE_SETTLING（固定--2）中之一。 
//	         * 抽屉打开的时候，点击抽屉，drawer的状态就会变成STATE_DRAGGING，然后变成STATE_IDLE 
//	        */  
//			@Override
//			public void onDrawerStateChanged(int arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			 /** 
//	         * 当抽屉被滑动的时候调用此方法 
//	         * arg1 表示 滑动的幅度（0-1） 
//	         */  
//			@Override
//			public void onDrawerSlide(View arg0, float arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//			 /** 
//	         * 当一个抽屉被完全打开的时候被调用 
//	         */  
//			@Override
//			public void onDrawerOpened(View arg0) {
//				drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN); //打开手势滑动
//				
//			}
//			/** 
//	         * 当一个抽屉完全关闭的时候调用此方法 
//	         */  
//			@Override
//			public void onDrawerClosed(View arg0) {
//				// TODO Auto-generated method stub
//				drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭手势滑动
//			}
//		});
	}

	private void initView() {
		setTitle("身份认证材料");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
		lv = (ListView) findViewById(R.id.lv);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if(tag == 1){
					tv_province.setText(mList.get(position).getName());
					province_id =  mList.get(position).getId();
					drawer_layout.closeDrawers();
				}
				if(tag == 2){
					tv_city.setText(mList.get(position).getName());
					city_id =  mList.get(position).getId();
					drawer_layout.closeDrawers();
				}
				if(tag == 3){
					tv_county_town.setText(mList.get(position).getName());
					town_id =  mList.get(position).getId();
					drawer_layout.closeDrawers();
				}
				
			}
		});
		tv_store_info_title = getView(R.id.tv_store_info_title);
		tv_self_get_point_title = getView(R.id.tv_self_get_point_title);
		
		tv_store_title = getView(R.id.tv_store_title);
		et_store_name = getView(R.id.et_store_name);
		tv_zhuangui_title = getView(R.id.tv_zhuangui_title);
		et_zhuangui_name = getView(R.id.et_zhuangui_name);
		tv_number_title = getView(R.id.tv_number_title);
		et_number_name = getView(R.id.et_number_name);
		tv_confirm = getView(R.id.tv_confirm);
		
		et_street = getView(R.id.et_street);
		
		tv_county_town = getView(R.id.tv_county_town);
		tv_province = getView(R.id.tv_province);
		tv_city = getView(R.id.tv_city);
		tv_county_town.setOnClickListener(this);
		tv_province.setOnClickListener(this);
		tv_city.setOnClickListener(this);
		FontManager.changeFonts(mContext, tv_store_info_title,tv_self_get_point_title,
				tv_store_title,et_store_name,tv_zhuangui_title,et_zhuangui_name,
				tv_number_title,et_number_name,tv_confirm,et_street);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_province://省
			tag = 1;
			HttpControl httpControl=new HttpControl();
			httpControl.getCityListById("0", new HttpCallBackInterface() {
				@Override
				public void http_Success(Object obj) {
					CityListBackBean bean = (CityListBackBean) obj;
					mList.clear();
					mList.addAll(bean.getData());
					lv.setAdapter(new CityListAdapter(mContext, mList));
					drawer_layout.openDrawer(Gravity.RIGHT);
				}
				
				@Override
				public void http_Fails(int error, String msg) {
					MyApplication.getInstance().showMessage(BuyerCertificationActivity2.this, msg);
				}
			}, BuyerCertificationActivity2.this);
			break;
		case R.id.tv_city://市
			tag = 2;
			if(!TextUtils.isEmpty(province_id)){
				HttpControl httpControl2=new HttpControl();
				httpControl2.getCityListById(province_id, new HttpCallBackInterface() {
					@Override
					public void http_Success(Object obj) {
						CityListBackBean bean = (CityListBackBean) obj;
						mList.clear();
						mList.addAll(bean.getData());
						lv.setAdapter(new CityListAdapter(mContext, mList));
						drawer_layout.openDrawer(Gravity.RIGHT);
					}
					
					@Override
					public void http_Fails(int error, String msg) {
						MyApplication.getInstance().showMessage(BuyerCertificationActivity2.this, msg);
					}
				}, BuyerCertificationActivity2.this);
				drawer_layout.openDrawer(Gravity.RIGHT);
			}else{
				Toast.makeText(mContext, "请先选择省份", 1000).show();
			}
			break;
		case R.id.tv_county_town://县
			tag = 3;
			if(!TextUtils.isEmpty(city_id)){
				HttpControl httpControl3=new HttpControl();
				httpControl3.getCityListById(city_id, new HttpCallBackInterface() {
					@Override
					public void http_Success(Object obj) {
						CityListBackBean bean = (CityListBackBean) obj;
						mList.clear();
						mList.addAll(bean.getData());
						lv.setAdapter(new CityListAdapter(mContext, mList));
						drawer_layout.openDrawer(Gravity.RIGHT);
					}
					
					@Override
					public void http_Fails(int error, String msg) {
						MyApplication.getInstance().showMessage(BuyerCertificationActivity2.this, msg);
					}
				}, BuyerCertificationActivity2.this);
				drawer_layout.openDrawer(Gravity.RIGHT);
			}else{
				Toast.makeText(mContext, "请先选择城市", 1000).show();
			}
			break;
		case R.id.tv_confirm://提交申请
			
			break;
		default:
			break;
		}
	}

	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.getAction() == KeyEvent.ACTION_UP && keyCode==KeyEvent.KEYCODE_BACK){
			if(drawer_layout.isShown()){
				drawer_layout.closeDrawer(Gravity.RIGHT);
			}else{
				return super.onKeyDown(keyCode, event);
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	}


