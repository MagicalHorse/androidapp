package com.shenma.yueba.baijia.activity;

import java.io.File;
import java.util.UUID;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.UserConfigActivity.ShowMenu;
import com.shenma.yueba.util.FileUtils;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.PhotoUtils;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.SelectePhotoType;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 身份认证
 * 
 * @author a
 * 
 */
public class BuyerCertificationActivity extends BaseActivityWithTopView
		implements OnClickListener {

	private boolean isPositive = true;
	private TextView tv_idcard_title;// 身份证信息头
	private TextView tv_get_point;// 自提点
	private EditText et_info;// 信息
	private TextView tv_retain;// 剩余字数
	private TextView tv_commit;// 提交
	private RelativeLayout rl_idcard_positive;
	private RelativeLayout rl_idcard_reverse;
	private ImageView iv_idcard_positive;
	private ImageView iv_idcard_reverse;
	private String littlePicPath;// 小图路径
	private String littlePicPath_cache;// 裁剪后图片存储的路径

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.buyer_certification_layout);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		tv_idcard_title = (TextView) findViewById(R.id.tv_idcard_title);
		tv_get_point = (TextView) findViewById(R.id.tv_get_point);
		et_info = (EditText) findViewById(R.id.et_info);
		tv_retain = (TextView) findViewById(R.id.tv_retain);
		rl_idcard_positive = (RelativeLayout) findViewById(R.id.rl_idcard_positive);
		rl_idcard_reverse = (RelativeLayout) findViewById(R.id.rl_idcard_reverse);
		iv_idcard_positive = (ImageView) findViewById(R.id.iv_idcard_positive);
		iv_idcard_reverse = (ImageView) findViewById(R.id.iv_idcard_reverse);
		rl_idcard_positive.setOnClickListener(this);
		rl_idcard_reverse.setOnClickListener(this);
		FontManager.changeFonts(mContext, tv_idcard_title, tv_get_point,
				et_info, tv_retain, tv_commit);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_idcard_positive:// 正面
			isPositive = true;
			showBottomDialog();
			break;
		case R.id.rl_idcard_reverse:// 反面
			isPositive = false;
			showBottomDialog();
			break;
		default:
			break;
		}

	}

	/**
	 * 弹出底部菜单(相机和图库)
	 */
	protected void showBottomDialog() {
		ToolsUtil.hideSoftInputKeyBoard(BuyerCertificationActivity.this);
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
				// tv_upload.setVisibility(View.GONE);
				Bitmap bm = BitmapFactory.decodeFile(littlePicPath_cache);
				// iv_pic.setImageBitmap(bm);
				// iv_pic.setVisibility(View.VISIBLE);
				// icon_imageview.setImageBitmap(bm);
				if (isPositive) {
					iv_idcard_positive.setImageBitmap(bm);
				} else {
					iv_idcard_reverse.setImageBitmap(bm);
				}
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
}
