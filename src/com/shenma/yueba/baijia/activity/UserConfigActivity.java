package com.shenma.yueba.baijia.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.util.FontManager;

/*****
 * @author gyj
 * @ date 2015-05-05
 * 用户设置页面 用于查看用户头像 用户昵称 用户地址 用户收藏 账户密码 消息免打扰 以及  切换到我要养家  退出登录等
 * 
 * *****/
public class UserConfigActivity extends BaseActivityWithTopView {

	//头像图片
	ImageView icon_imageview;
	//昵称值
	TextView nickname_textvalue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mefragmentforseller_config_layout);
		super.onCreate(savedInstanceState);
		initView();
	}
	
	/****
	 * 初始化 视图控件 并设置 按键监听
	 * 
	 * **/
	void initView()
	{
		
		setTitle(this.getResources().getString(R.string.user_setconfig_str));
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserConfigActivity.this.finish();
			}
		});
		RelativeLayout icon_layout=(RelativeLayout)findViewById(R.id.user_config_icon_include);
		//图片
		TextView icon_text=(TextView)icon_layout.findViewById(R.id.people_config_str1_textview);
		icon_text.setText(this.getResources().getText(R.string.user_config_icon_str));
		icon_imageview=(ImageView)icon_layout.findViewById(R.id.people_config_str2_imageview);
		icon_imageview.setVisibility(View.VISIBLE);
		
		RelativeLayout nickname_layout=(RelativeLayout)findViewById(R.id.user_config_nickname_include);
		//昵称
		TextView nickname_text=(TextView)nickname_layout.findViewById(R.id.people_config_str1_textview);
		nickname_text.setText(this.getResources().getText(R.string.user_config_nickname_str));
		nickname_textvalue=(TextView)nickname_layout.findViewById(R.id.people_config_str2_textview);
		
		RelativeLayout myaddress_layout=(RelativeLayout)findViewById(R.id.user_config_address_include);
		//我的地址
		TextView myaddress_textview=(TextView)myaddress_layout.findViewById(R.id.people_config_str1_textview);
		myaddress_textview.setText(this.getResources().getText(R.string.user_config_myaddress_str));
		
		RelativeLayout mycollect_layout=(RelativeLayout)findViewById(R.id.user_config_collect_include);
		//我的收藏
		TextView mycollect_textview=(TextView)mycollect_layout.findViewById(R.id.people_config_str1_textview);
		mycollect_textview.setText(this.getResources().getText(R.string.user_config_mycollect_str));
		
		RelativeLayout userpwd_layout=(RelativeLayout)findViewById(R.id.user_config_password_include);
		//账户密码
		TextView userpwd_textview=(TextView)userpwd_layout.findViewById(R.id.people_config_str1_textview);
		userpwd_textview.setText(this.getResources().getText(R.string.user_config_userpwd_str));
		
		RelativeLayout messagednd_layout=(RelativeLayout)findViewById(R.id.user_config_dnd_include);
		//消息免打扰
		TextView messagednd_textview=(TextView)messagednd_layout.findViewById(R.id.people_config_str1_textview);
		messagednd_textview.setText(this.getResources().getText(R.string.user_config_messagednd_str));
		//我要养家按钮
		Button user_config_yangjia_button=(Button)findViewById(R.id.user_config_yangjia_button);
		//退出登录
		Button user_config_exit_button=(Button)findViewById(R.id.user_config_exit_button);
		//设置字体样式
		FontManager.changeFonts(this, tv_top_title,icon_text,nickname_text,nickname_textvalue,myaddress_textview,mycollect_textview,userpwd_textview,messagednd_textview,user_config_yangjia_button,user_config_exit_button);
		//设置按键监听
		icon_layout.setOnClickListener(onClickListener);
		nickname_layout.setOnClickListener(onClickListener);
		myaddress_layout.setOnClickListener(onClickListener);
		mycollect_layout.setOnClickListener(onClickListener);
		userpwd_layout.setOnClickListener(onClickListener);
		messagednd_layout.setOnClickListener(onClickListener);
		user_config_yangjia_button.setOnClickListener(onClickListener);
		user_config_exit_button.setOnClickListener(onClickListener);
		
	}
	
	OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	};
}
