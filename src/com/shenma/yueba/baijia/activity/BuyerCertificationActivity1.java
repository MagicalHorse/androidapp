package com.shenma.yueba.baijia.activity;

import java.io.File;
import java.io.FileNotFoundException;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.callback.OSSCallback;
import com.alibaba.sdk.android.oss.callback.SaveCallback;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.alibaba.sdk.android.oss.storage.OSSBucket;
import com.alibaba.sdk.android.oss.storage.OSSFile;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.StoreListBackBean;
import com.shenma.yueba.util.CustomProgressDialog;
import com.shenma.yueba.util.FileUtils;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.PhotoUtils;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.view.SelectePhotoType;
import com.umeng.analytics.MobclickAgent;

/**
 * 身份认证
 * 
 * @author a
 * 
 */
public class BuyerCertificationActivity1 extends BaseActivityWithTopView
		implements OnClickListener {

	private int tag = 0;// 0身份证正面，1身份证反面，2证件照片
	private TextView tv_idcard_title;// 身份证信息头
	private TextView tv_get_point;// 自提点
	private EditText et_info;// 信息
	private TextView tv_retain;// 剩余字数
	private TextView tv_next;// 提交
	private TextView tv_name;
	private EditText et_name;//
	private RelativeLayout rl_idcard_positive;
	private RelativeLayout rl_idcard_reverse;
	private ImageView iv_idcard_positive;
	private ImageView iv_idcard_reverse;
	private String littlePicPath;// 小图路径
	private String littlePicPath_cache;// 裁剪后图片存储的路径
	private String pic1, pic2, pic3;
	private RelativeLayout rl_work_card;
	private ImageView iv_work_card;
	private CustomProgressDialog progressDialog;
	private int upPicProgress = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buyer_certification_layout);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setTitle("身份认证材料");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tv_idcard_title = (TextView) findViewById(R.id.tv_idcard_title);
		tv_get_point = (TextView) findViewById(R.id.tv_get_point);
		et_info = (EditText) findViewById(R.id.et_info);
		et_name = (EditText) findViewById(R.id.et_name);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_retain = (TextView) findViewById(R.id.tv_retain);
		rl_idcard_positive = (RelativeLayout) findViewById(R.id.rl_idcard_positive);
		rl_idcard_reverse = (RelativeLayout) findViewById(R.id.rl_idcard_reverse);
		iv_idcard_positive = (ImageView) findViewById(R.id.iv_idcard_positive);
		rl_work_card = (RelativeLayout) findViewById(R.id.rl_work_card);
		iv_work_card = (ImageView) findViewById(R.id.iv_work_card);
		tv_next = (TextView) findViewById(R.id.tv_next);
		tv_next.setOnClickListener(this);
		iv_idcard_reverse = (ImageView) findViewById(R.id.iv_idcard_reverse);
		rl_work_card.setOnClickListener(this);
		rl_idcard_positive.setOnClickListener(this);
		rl_idcard_reverse.setOnClickListener(this);
		FontManager.changeFonts(mContext, tv_idcard_title, tv_get_point,
				et_info, tv_retain, tv_next,tv_name,et_name,tv_top_title);
		MyApplication.getInstance().kuanggaobi = (float) 1.76;
		progressDialog = CustomProgressDialog.createDialog(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_idcard_positive:// 正面
			tag = 0;
			showBottomDialog();
			break;
		case R.id.rl_idcard_reverse:// 反面
			tag = 1;
			showBottomDialog();
			break;
		case R.id.rl_work_card:// 工牌
			tag = 2;
			showBottomDialog();
			break;
		case R.id.tv_next:// 下一步
			if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
				Toast.makeText(mContext, "姓名不能为空", 1000).show();
				return;
			}
			if (TextUtils.isEmpty(pic1)) {
				Toast.makeText(mContext, "身份证正面图片不能为空", 1000).show();
				return;
			}
			if (TextUtils.isEmpty(pic2)) {
				Toast.makeText(mContext, "身份证反面面图片不能为空", 1000).show();
				return;
			}
			if (TextUtils.isEmpty(pic3)) {
				Toast.makeText(mContext, "证件图片不能为空", 1000).show();
				return;
			}
			uploadImage(pic1);// 开始上传图片
			break;
		default:
			break;
		}

	}

	/**
	 * 弹出底部菜单(相机和图库)
	 */
	protected void showBottomDialog() {
		ToolsUtil.hideSoftKeyboard(BuyerCertificationActivity1.this, et_name);
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
										.createNewFile(littlePicPath_cache)),
								8, 5), PhotoUtils.INTENT_REQUEST_CODE_CROP);
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
				if (0 == tag) {
					pic1 = littlePicPath_cache;
					iv_idcard_positive.setImageBitmap(bm);
				} else if (1 == tag) {
					pic2 = littlePicPath_cache;
					iv_idcard_reverse.setImageBitmap(bm);
				} else if (2 == tag) {
					pic3 = littlePicPath_cache;
					iv_work_card.setImageBitmap(bm);
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
									.createNewFile(littlePicPath_cache)),8,5),
							PhotoUtils.INTENT_REQUEST_CODE_CROP);
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	private void uploadImage(String imagePath) {
		if (!progressDialog.isShowing()) {
			progressDialog.show();
		}
		HttpControl httpControl = new HttpControl();
		httpControl.syncUpload(pic1, new SaveCallback() {

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
				switch (upPicProgress) {
				case 0:// 第一张图片上传完毕
					upPicProgress = 1;
					uploadImage(pic2);
					break;
				case 1:// 第二张上传完毕
					upPicProgress = 2;
					uploadImage(pic3);
					break;
				case 2:// 第三张上传完毕
					if (progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					Intent intent = new Intent(mContext,
							BuyerCertificationActivity2.class);
					intent.putExtra(
							"pic1",
							pic1.substring(pic1.lastIndexOf("/") + 1,
									pic1.length()));
					intent.putExtra(
							"pic2",
							pic2.substring(pic2.lastIndexOf("/") + 1,
									pic2.length()));
					intent.putExtra(
							"pic3",
							pic3.substring(pic3.lastIndexOf("/") + 1,
									pic3.length()));
					intent.putExtra(
							"name",et_name.getText().toString().trim());
					startActivity(intent);
				default:
					break;
				}

			}
		});
	}

	
	  @Override
	    protected void onDestroy() {
	    	MyApplication.getInstance().removeActivity(this);//加入回退栈
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
