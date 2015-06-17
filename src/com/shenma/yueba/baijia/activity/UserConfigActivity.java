package com.shenma.yueba.baijia.activity;

import java.io.File;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.callback.SaveCallback;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.CustomProgressDialog;
import com.shenma.yueba.util.FileUtils;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.PhotoUtils;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;
import com.shenma.yueba.view.SelectePhotoType;
import com.shenma.yueba.view.SwitchButton;
import com.shenma.yueba.view.SwitchButton.OnChangedListener;
import com.shenma.yueba.yangjia.activity.AboutActivity;
import com.shenma.yueba.yangjia.activity.ModifyNickNameActivity;


/*****
 * 用户设置
 * 
 * @author gyj @ date 2015-05-05 用户设置页面 用于查看用户头像 用户昵称 用户地址 用户收藏 账户密码 消息免打扰 以及
 *         切换到我要养家 退出登录等
 * 
 * *****/
public class UserConfigActivity extends BaseActivityWithTopView {

	// 头像图片
	RoundImageView icon_imageview;
	// 昵称值
	TextView nickname_textvalue;

	private String littlePicPath;// 小图路径
	private String littlePicPath_cache;// 裁剪后图片存储的路径
	private CustomProgressDialog progressDialog;

	private TextView tv_about;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);// 加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mefragmentforseller_config_layout);
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);

		initView();
	}

	/****
	 * 初始化 视图控件 并设置 按键监听
	 * 
	 * **/
	void initView() {
		progressDialog = new CustomProgressDialog(mContext);
		setTitle(this.getResources().getString(R.string.user_setconfig_str));
		setLeftTextView(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserConfigActivity.this.finish();
			}
		});
		RelativeLayout icon_layout = (RelativeLayout) findViewById(R.id.user_config_icon_include);
		// 图片
		TextView icon_text = (TextView) icon_layout
				.findViewById(R.id.people_config_str1_textview);
		icon_text.setText(this.getResources().getText(
				R.string.user_config_icon_str));
		tv_about = (TextView) findViewById(R.id.tv_about);
		tv_about.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				skip(AboutActivity.class, true);
			}
		});
		icon_imageview = (RoundImageView) icon_layout
				.findViewById(R.id.people_config_str2_imageview);
		MyApplication
				.getInstance()
				.getImageLoader()
				.displayImage(
						ToolsUtil.nullToString(SharedUtil.getStringPerfernece(
								mContext, SharedUtil.user_logo)),
						icon_imageview);
		icon_imageview.setVisibility(View.VISIBLE);

		RelativeLayout nickname_layout = (RelativeLayout) findViewById(R.id.user_config_nickname_include);
		// 昵称
		TextView nickname_text = (TextView) nickname_layout
				.findViewById(R.id.people_config_str1_textview);
		nickname_text.setText(this.getResources().getText(
				R.string.user_config_nickname_str));
		nickname_textvalue = (TextView) nickname_layout
				.findViewById(R.id.people_config_str2_textview);
		nickname_textvalue.setText(ToolsUtil.nullToString(SharedUtil
				.getStringPerfernece(mContext, SharedUtil.user_names)));

		RelativeLayout myaddress_layout = (RelativeLayout) findViewById(R.id.user_config_address_include);
		// 我的地址
		TextView myaddress_textview = (TextView) myaddress_layout
				.findViewById(R.id.people_config_str1_textview);
		myaddress_textview.setText(this.getResources().getText(
				R.string.user_config_myaddress_str));

		RelativeLayout mycollect_layout = (RelativeLayout) findViewById(R.id.user_config_collect_include);
		// 我的收藏
		TextView mycollect_textview = (TextView) mycollect_layout
				.findViewById(R.id.people_config_str1_textview);
		mycollect_textview.setText(this.getResources().getText(
				R.string.user_config_mycollect_str));

		RelativeLayout userpwd_layout = (RelativeLayout) findViewById(R.id.user_config_password_include);
		// 账户密码
		TextView userpwd_textview = (TextView) userpwd_layout
				.findViewById(R.id.people_config_str1_textview);
		userpwd_textview.setText(this.getResources().getText(
				R.string.user_config_userpwd_str));

		RelativeLayout messagednd_layout = (RelativeLayout) findViewById(R.id.user_config_dnd_include);
		// 消息免打扰
		TextView messagednd_textview = (TextView) messagednd_layout
				.findViewById(R.id.people_config_str1_textview);
		ImageView arrowRight = (ImageView) messagednd_layout.findViewById(R.id.people_config_str1_imageview);
		arrowRight.setVisibility(View.GONE);
		SwitchButton switchButton = (SwitchButton) messagednd_layout.findViewById(R.id.switchButton);
		switchButton.setVisibility(View.VISIBLE);
		switchButton.setOnChangedListener(new OnChangedListener() {
			
			@Override
			public void OnChanged(SwitchButton wiperSwitch, boolean checkState) {
				// TODO Auto-generated method stub
				
			}
		});
		messagednd_textview.setText(this.getResources().getText(
				R.string.user_config_messagednd_str));
		// 退出登录
		Button user_config_exit_button = (Button) findViewById(R.id.user_config_exit_button);
		// 设置字体样式
		FontManager.changeFonts(this, tv_top_title, icon_text, nickname_text,
				nickname_textvalue, myaddress_textview, mycollect_textview,
				userpwd_textview, messagednd_textview, user_config_exit_button,
				tv_about);
		// 设置按键监听
		icon_layout.setOnClickListener(onClickListener);
		nickname_layout.setOnClickListener(onClickListener);
		myaddress_layout.setOnClickListener(onClickListener);
		mycollect_layout.setOnClickListener(onClickListener);
		userpwd_layout.setOnClickListener(onClickListener);
		messagednd_layout.setOnClickListener(onClickListener);
		user_config_exit_button.setOnClickListener(onClickListener);

	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.user_config_icon_include:
				showBottomDialog();
				break;
			case R.id.user_config_collect_include:
				Intent intent3 = new Intent(UserConfigActivity.this,
						MyCollectionActivity.class);
				startActivity(intent3);
			case R.id.user_config_nickname_include:// 修改昵称
				Intent intent = new Intent(mContext,
						ModifyNickNameActivity.class);
				startActivityForResult(intent, Constants.REQUESTCODE);
				break;
			case R.id.user_config_exit_button:// 退出登录
				// Intent intentSelf =
				// getPackageManager().getLaunchIntentForPackage("com.shenma.yueba");//"jp.co.johospace.jorte"就是我们获得要启动应用的包名
				// startActivity(intentSelf);
				MyApplication.removeAllActivity();
				Intent intentLogin = new Intent(mContext, SplashActivity.class);
				startActivity(intentLogin);
				break;
			case R.id.user_config_password_include:// 修改密码
				Intent intentModifyPassword = new Intent(
						UserConfigActivity.this, SetNewPasswordActivity.class);
				startActivity(intentModifyPassword);
			default:
				break;
			}

		}
	};

	/**
	 * 弹出底部菜单(相机和图库)
	 */
	protected void showBottomDialog() {
		ToolsUtil.hideSoftInputKeyBoard(UserConfigActivity.this);
		ShowMenu showMenu = new ShowMenu(mContext, findViewById(R.id.parent),
				R.layout.camera_pic_popwindow);
		showMenu.createView();
	}

	/**
	 * 弹出底部菜单
	 * 
	 * @author
	 */
	class ShowMenu extends SelectePhotoType {
		public ShowMenu(Activity activity, View parent, int popLayout) {
			super(activity, parent, popLayout);
		}

		@Override
		public void onExitClick(View v) {
			canceView();
		}

		@Override
		public void onCamera(View v) {
			if (ToolsUtil.isAvailableSpace(mContext)) {
				littlePicPath = PhotoUtils.takePicture(mContext);
			}
			canceView();
		}

		@Override
		public void onPic(View v) {
			if (ToolsUtil.isAvailableSpace(mContext)) {
				PhotoUtils.selectPhoto(mContext);
			}
			canceView();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PhotoUtils.INTENT_REQUEST_CODE_ALBUM) {
			// 调用相册返回
			if (resultCode == RESULT_OK) {
				if (data.getData() == null) {
					return;
				}
				Uri uri = data.getData();
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor cursor = managedQuery(uri, proj, null, null, null);
				if (cursor != null) {
					int column_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					if (cursor.getCount() > 0 && cursor.moveToFirst()) {
						littlePicPath = cursor.getString(column_index);
						// 裁剪之后存储的路径
						littlePicPath_cache = Environment
								.getExternalStorageDirectory().toString()
								+ File.separator
								+ UUID.randomUUID().toString()
								+ "littlePic.jpg";
						// 裁剪图片
						startActivityForResult(PhotoUtils.getZoomIntent(Uri
								.fromFile(new File(littlePicPath)), Uri
								.fromFile(FileUtils
										.createNewFile(littlePicPath_cache))),
								PhotoUtils.INTENT_REQUEST_CODE_CROP);
					}
				}
			}
		}
		if (requestCode == PhotoUtils.INTENT_REQUEST_CODE_CROP) {// 裁剪后返回
			if (resultCode == RESULT_OK) {
				modifyUserLogo();

			}
		}
		if (requestCode == PhotoUtils.INTENT_REQUEST_CODE_CAMERA) {// 调用相机返回
			if (resultCode == RESULT_OK) {
				if (littlePicPath != null) {
					// 裁剪之后存储的路径
					littlePicPath_cache = Environment
							.getExternalStorageDirectory().toString()
							+ File.separator
							+ UUID.randomUUID().toString()
							+ "littlePic.jpg";
					// 裁剪图片
					startActivityForResult(PhotoUtils.getZoomIntent(Uri
							.fromFile(new File(littlePicPath)), Uri
							.fromFile(FileUtils
									.createNewFile(littlePicPath_cache))),
							PhotoUtils.INTENT_REQUEST_CODE_CROP);
				}
			}
		}
		if (requestCode == Constants.REQUESTCODE
				&& resultCode == Constants.RESULTCODE) {// 修改昵称返回
			String newName = data.getStringExtra("newName");
			nickname_textvalue.setText(ToolsUtil.nullToString(newName));
		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	/**
	 * 修改头像
	 */
	private void modifyUserLogo() {
		if (!progressDialog.isShowing()) {
			progressDialog.show();
		}
		final HttpControl httpControl = new HttpControl();
		httpControl.syncUpload(littlePicPath_cache, new SaveCallback() {

			@Override
			public void onProgress(String arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailure(String arg0, OSSException arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				runOnUiThread(new Runnable() {
					public void run() {
						httpControl.modifyUserLogo(
								littlePicPath_cache.substring(
										littlePicPath_cache.lastIndexOf("/") + 1,
										littlePicPath_cache.length()), false,
								false, new HttpCallBackInterface() {
									@Override
									public void http_Success(Object obj) {
										if (progressDialog.isShowing()) {
											progressDialog.dismiss();
										}
										Toast.makeText(mContext, "修改成功", 1000)
												.show();
										Bitmap bm = BitmapFactory
												.decodeFile(littlePicPath_cache);
										icon_imageview.setImageBitmap(bm);
									}

									@Override
									public void http_Fails(int error, String msg) {
										Toast.makeText(mContext, msg, 1000)
												.show();

									}
								}, UserConfigActivity.this);

					}
				});
			}
		});

	}

}
