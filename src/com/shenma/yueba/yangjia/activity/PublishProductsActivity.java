package com.shenma.yueba.yangjia.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FileUtils;
import com.shenma.yueba.util.PhotoUtils;
import com.shenma.yueba.util.PictureCompress;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.SelectePhotoType;
import com.umeng.analytics.MobclickAgent;


/**
 * 发布宝贝描述
 * @author a
 *
 */
public class PublishProductsActivity extends BaseActivityWithTopView implements
		OnClickListener {
	private static final String TAG = "ReplyActivity";
	private static final int SUCCESS_CODE = 100;
	private static int RESULT_LOAD_IMAGE1 = 1;// 拍照
	private static int RESULT_LOAD_IMAGE2 = 2;// 相册
	private String seq = "";
	private EditText et_content;// 自定义输入框
	private TextView tv_add_pic;
	private TextView tv_finish;
	private String noteContent = "";// 发表的内容
	private String finallyConten = "";// 最终需要发表出去的文本内容
	private ProgressDialog pd_wait;

	private List<String> keys;// 表情列表
	private List<String> imgStr = new ArrayList<String>();
	private List<String> listImgPath = null;
	private List<String> listFileName = new ArrayList<String>();// 保存添加的图片，方便上传之后进行删除
	private InputMethodManager imm;

	private String littlePicPath;//小图路径
	private String littlePicPath_cache;//裁剪后图片存储的路径
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.product_introduce_layout);
		super.onCreate(savedInstanceState);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		initView();
	}

	private void initView() {
		setTitle("宝贝描述");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PublishProductsActivity.this.finish();
			}
		});
		et_content = (EditText) findViewById(R.id.et_content);
		tv_add_pic = (TextView) findViewById(R.id.tv_add_pic);
		tv_finish = (TextView) findViewById(R.id.tv_finish);
		tv_add_pic.setOnClickListener(this);
		tv_finish.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_add_pic:
			showBottomDialog();
			break;
		case R.id.tv_finish:
			noteContent = et_content.getText().toString();
			if (TextUtils.isEmpty(noteContent)) {
				Toast.makeText(PublishProductsActivity.this, "描述文字不能为空",
						Toast.LENGTH_LONG).show();
				return;
			}
			listImgPath = ToolsUtil
					.convertNormalStringToSpannableString(noteContent);
			if (listImgPath == null || listImgPath.size() == 0) {
				finallyConten = noteContent;
				// startReply(finallyConten);
			} else {
				// startUploadImg(listImgPath);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * ---------------------------------弹出底部菜单，添加图片----------------------------
	 * ------------
	 * 
	 * @author
	 * 
	 */

	/**
	 * 弹出底部菜单(相机和图库)
	 */
	protected void showBottomDialog() {
		ToolsUtil.hideSoftInputKeyBoard(PublishProductsActivity.this);
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
				littlePicPath = PhotoUtils
						.takePicture(mContext);
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

	/**
	 * 相册
	 */
	private void callGrallery() {
		if (ToolsUtil.isAvailableSpace(PublishProductsActivity.this)) {
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent, RESULT_LOAD_IMAGE2);
		}
	}

	String takeImage;

	/**
	 * 拍照
	 */
	private void callCamera() {
		if (ToolsUtil.isAvailableSpace(PublishProductsActivity.this)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			takeImage = String.valueOf(System.currentTimeMillis());
			startActivityForResult(intent, RESULT_LOAD_IMAGE1);
		}
	}

	// /**
	// * 上传图片回调用
	// * @author
	// *
	// */
	// int i=0;
	// class UpCallBack implements UploadCallback{
	// @Override
	// public void toastMsg(String msg) {
	// Toast.makeText(ReplyActivity.this, msg, Toast.LENGTH_LONG).show();
	// }
	// @Override
	// public void successResult(String imgId) {
	// i++;
	// if(imgId!=null&&!"".equals(imgId)){
	// imgStr.add(Constants.LoadImage_URL+imgId);
	// }
	// if(imgStr.size()==listImgPath.size()){
	// finallyConten=PublicMethod.getHtmlContext(noteContent.toString(),
	// "center", 280, imgStr);//拼接成html格式的文本发送
	// reportInvitation(finallyConten);
	// }else if(i==listImgPath.size()){
	// pd_wait.dismiss();
	// }
	// }
	//
	// }

	String big_img_path = "";
	String result_img_name = "";

	
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
						//裁剪之后存储的路径
						littlePicPath_cache = Environment
								.getExternalStorageDirectory().toString()
								+ File.separator
								+ UUID.randomUUID().toString()
								+ "littlePic.jpg";
						
						
						
						
						// 裁剪图片
						startActivityForResult(PhotoUtils.getZoomIntent(
								Uri.fromFile(new File(littlePicPath)),
								Uri.fromFile(FileUtils.createNewFile(littlePicPath_cache))),
								PhotoUtils.INTENT_REQUEST_CODE_CROP);
					}
				}
			}
		}
		if (requestCode == PhotoUtils.INTENT_REQUEST_CODE_CROP) {// 裁剪后返回
			if (resultCode == RESULT_OK) {
				//tv_upload.setVisibility(View.GONE);
				Bitmap bm = BitmapFactory.decodeFile(littlePicPath_cache); 
				saveUploadImage(littlePicPath_cache, "littlePic");
				//iv_pic.setImageBitmap(bm); 
				//iv_pic.setVisibility(View.VISIBLE);
				//icon_imageview.setImageBitmap(bm);
			}
		}
		if (requestCode == PhotoUtils.INTENT_REQUEST_CODE_CAMERA) {// 调用相机返回
			if (resultCode == RESULT_OK) {
				if (littlePicPath != null) {
					//裁剪之后存储的路径
					littlePicPath_cache = Environment
							.getExternalStorageDirectory().toString()
							+ File.separator
							+ UUID.randomUUID().toString()
							+ "littlePic.jpg";
					
					
					// 裁剪图片
					startActivityForResult(PhotoUtils.getZoomIntent(
							Uri.fromFile(new File(littlePicPath)),
							Uri.fromFile(FileUtils.createNewFile(littlePicPath_cache))),
							PhotoUtils.INTENT_REQUEST_CODE_CROP);
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	
	}

	/**
	 * 得到大图后压缩保存小图
	 * 
	 * @param bigImgPath
	 *            选择的原图路径
	 * @param resultImgName
	 *            压缩后保存的小图文件名
	 */
	private void saveUploadImage(String bigImgPath, String resultImgName) {
		Bitmap bigBitmap = PictureCompress.zoomImg(
				PictureCompress.startPhotoZoomY(bigImgPath), 100, 100);// 压缩成100*100的小图
		PictureCompress.saveBitmapToSDCard(bigBitmap, resultImgName);// 保存压缩后的小图，需要显示到EditText中，真正上传的是大图，小图不用上传
		inputImgToEditText(bigImgPath);// 在输入框中显示图片
	}

	/**
	 * 将小图显示在EditText中
	 * 
	 * @param showImgPath
	 *            需要显示在EditText的图片路径
	 * @param uploadImgPath
	 *            需要上传的图片路径
	 */
	private void inputImgToEditText(String uploadImgPath) {
		SpannableString ss = new SpannableString(toString(uploadImgPath));
		Drawable d = FileUtils.getImageDrawable(uploadImgPath);
		d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
		ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
		ss.setSpan(span, 0, toString(uploadImgPath).length(),
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		if(et_content.getSelectionStart() != 0){
			et_content.getText().insert(et_content.getSelectionStart(), "\n");
		}
		et_content.getText().insert(et_content.getSelectionStart(), ss);
		et_content.getText().insert(et_content.getSelectionStart(), "\n");
	}

	private String toString(String str) {
		return "[" + str + "]";
	}

	/**
	 * 从intent中获取图片本地地址
	 * 
	 * @param intent
	 * @return
	 */
	private String getImageUrl(Intent intent) {
		String img_path = "";
		Uri uri = intent.getData();
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
		if (actualimagecursor != null) {
			int actual_image_column_index = actualimagecursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			actualimagecursor.moveToFirst();
			img_path = actualimagecursor.getString(actual_image_column_index);
		} else {
			img_path = null;
		}
		return img_path;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onDestroy() {
		MyApplication.getInstance().addActivity(this);
		super.onDestroy();
		deleteImg();
	}

	/**
	 * 上传图片之后删除保存在SD卡中的缓存图
	 */
	private void deleteImg() {
		if (listFileName == null || listFileName.size() == 0) {
			return;
		}
		for (String deleStr : listFileName) {
			PictureCompress.deleteBitmapFromSDCard(deleStr);
		}
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
