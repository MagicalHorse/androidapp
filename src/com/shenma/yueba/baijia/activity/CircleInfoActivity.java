package com.shenma.yueba.baijia.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.UserConfigActivity.ShowMenu;
import com.shenma.yueba.baijia.adapter.MyCircleInfoAdapter;
import com.shenma.yueba.baijia.dialog.QRCodeShareDialog;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FileUtils;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.PhotoUtils;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.MyGridView;
import com.shenma.yueba.view.RoundImageView;
import com.shenma.yueba.view.SelectePhotoType;
import com.shenma.yueba.yangjia.activity.ModifyCircleNameActivity;
import com.shenma.yueba.yangjia.modle.CircleDetailBackBean;
import com.shenma.yueba.yangjia.modle.CircleDetailBean;
import com.shenma.yueba.yangjia.modle.Users;

/**
 * 圈子信息
 * 
 * @author a
 * 
 */

public class CircleInfoActivity extends BaseActivityWithTopView implements
		OnClickListener {

	private TextView tv_cirlce_head_title;
	private TextView tv_cirlce_name_title;
	private TextView tv_circle_title;
	private TextView tv_circle_name;
	private RoundImageView riv_circle_head;
	private MyGridView gv_circle;
	private MyCircleInfoAdapter adapter;
	private List<Users> mList = new ArrayList<Users>();
	private RelativeLayout rl_head;
	private RelativeLayout rl_circle_name;
	private RelativeLayout rl_qrcode;
	private String cricleId;
	private String littlePicPath;//小图路径
	private String littlePicPath_cache;//裁剪后图片存储的路径
	private TextView tv_top_title_below;//标题下面的人数
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.circle_info_layout);
		super.onCreate(savedInstanceState);
		initView();
		getIntentData();
		getCircleDetail(cricleId, mContext, true);
	}

	private void getIntentData() {
		cricleId = getIntent().getStringExtra("circleId");

	}

	private void initView() {
		setTitle("圈子信息");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		gv_circle = getView(R.id.gv_circle);
		riv_circle_head = getView(R.id.riv_circle_head);
		tv_top_title_below = getView(R.id.tv_top_title_below);
		tv_cirlce_head_title = getView(R.id.tv_cirlce_head_title);
		tv_cirlce_name_title = getView(R.id.tv_cirlce_name_title);
		tv_circle_title = getView(R.id.tv_circle_title);
		tv_circle_name = getView(R.id.tv_circle_name);
		rl_head = getView(R.id.rl_head);
		rl_head.setOnClickListener(this);
		rl_circle_name = getView(R.id.rl_circle_name);
		rl_circle_name.setOnClickListener(this);
		rl_qrcode = getView(R.id.rl_qrcode);
		rl_qrcode.setOnClickListener(this);
		adapter = new MyCircleInfoAdapter(mContext, mList);
		gv_circle.setAdapter(adapter);
		FontManager.changeFonts(mContext, tv_cirlce_head_title,
				tv_cirlce_name_title, tv_circle_title, tv_circle_name);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_head:// 更改头像
			ToolsUtil.hideSoftKeyboard(mContext, tv_circle_name);
			showBottomDialog();
			break;
		case R.id.rl_circle_name:// 更改姓名
			Intent modifyCircleNameIntent = new Intent(this,
					ModifyCircleNameActivity.class);
			modifyCircleNameIntent.putExtra("circleId", cricleId);
			modifyCircleNameIntent.putExtra("name", tv_circle_name.getText()
					.toString().trim());
			startActivityForResult(modifyCircleNameIntent,
					Constants.REQUESTCODE);
			break;
//		case R.id.rl_qrcode:// 二维码查看
//			/*
//			 * ArrayList<String> urlList = new ArrayList<String>();
//			 * if(urlList.size() == 0){ Toast.makeText(mContext, "暂无大图展示",
//			 * 1000).show(); return ; }
//			 */
//			QRCodeShareDialog dialog = new QRCodeShareDialog(
//					CircleInfoActivity.this, null);
//			dialog.show();
//			/*
//			 * Intent imageShowIntent = new Intent(mContext,
//			 * ImageShowActivity.class);
//			 * imageShowIntent.putStringArrayListExtra(
//			 * ImageShowActivity.BIGIMAGES,urlList);
//			 * imageShowIntent.putExtra(ImageShowActivity.IMAGE_INDEX, 0);
//			 * startActivity(imageShowIntent);
//			 */
//			break;

		default:
			break;
		}
	}

	/**
	 * 获取圈子详情
	 * 
	 * @param ctx
	 * @param isRefresh
	 * @param showDialog
	 */
	public void getCircleDetail(String circleId, Context ctx, boolean showDialog) {
		HttpControl httpControl = new HttpControl();
		httpControl.getCircleDetail(circleId, showDialog,
				new HttpCallBackInterface() {

					@Override
					public void http_Success(Object obj) {
						CircleDetailBackBean result = (CircleDetailBackBean) obj;
						CircleDetailBean bean = result.getData();
						if (bean != null) {
							tv_top_title_below.setVisibility(View.VISIBLE);
							tv_top_title_below.setText("人数（"+bean.getMemberCount()+"）");
							tv_circle_name.setText(ToolsUtil.nullToString(bean
									.getGroupName()));
							MyApplication
									.getInstance()
									.getImageLoader()
									.displayImage(
											ToolsUtil.getImage(ToolsUtil
													.nullToString(bean
															.getGroupPic()),
													120, 0), riv_circle_head);
							List<Users> users = bean.getUsers();
							adapter.setCircleId(cricleId);
							if (users != null && users.size() > 0) {
								mList.addAll(users);
								mList.addAll(users);
								mList.addAll(users);
								mList.add(new Users());
								mList.add(new Users());
								adapter.notifyDataSetChanged();
							}
						}
					}

					@Override
					public void http_Fails(int error, String msg) {
						Toast.makeText(mContext, msg, 1000).show();
					}
				}, CircleInfoActivity.this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Constants.REQUESTCODE
				&& resultCode == Constants.RESULTCODE) {//修改圈子名称
			if (data != null) {
				String newName = data.getStringExtra("newName");
				tv_circle_name.setText(ToolsUtil.nullToString(newName));
			}
		}
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
				// tv_upload.setVisibility(View.GONE);
				Bitmap bm = BitmapFactory.decodeFile(littlePicPath_cache);
				// iv_pic.setImageBitmap(bm);
				// iv_pic.setVisibility(View.VISIBLE);
				riv_circle_head.setImageBitmap(bm);
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
		super.onActivityResult(requestCode, resultCode, data);

	}

	/**
	 * 弹出底部菜单(相机和图库)
	 */
	protected void showBottomDialog() {
		ToolsUtil.hideSoftInputKeyBoard(CircleInfoActivity.this);
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

}
