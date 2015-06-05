package com.shenma.yueba.yangjia.activity;

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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.callback.SaveCallback;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.baijia.activity.BuyerCertificationActivity2;
import com.shenma.yueba.util.CustomProgressDialog;
import com.shenma.yueba.util.FileUtils;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.PhotoUtils;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;
import com.shenma.yueba.view.SelectePhotoType;

/**
 * 新增圈子
 * @author a
 *
 */
public class AddCircleActivity extends BaseActivityWithTopView implements OnClickListener{
	
	private TextView tv_cirlce_head_title;
	private TextView tv_cirlce_name_title;
	private EditText et_circle_name;
	private RoundImageView riv_circle_head;
	private RelativeLayout rl_head;
	private String littlePicPath;// 小图路径
	private String littlePicPath_cache;// 裁剪后图片存储的路径
	private CustomProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_circle_layout);
		initView();
		progressDialog = CustomProgressDialog.createDialog(this);
		super.onCreate(savedInstanceState);
	}
	
	
	private void initView() {
		setTitle("新建圈子");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AddCircleActivity.this.finish();
			}
		});
		setTopRightTextView("完成", new OnClickListener() {
			@Override
			public void onClick(View v) {
				uploadImage(progressDialog, littlePicPath_cache);
			}
		});
		tv_cirlce_head_title = getView(R.id.tv_cirlce_head_title);
		tv_cirlce_name_title = getView(R.id.tv_cirlce_name_title);
		et_circle_name = getView(R.id.et_circle_name);
		riv_circle_head = getView(R.id.riv_circle_head);
		rl_head = getView(R.id.rl_head);
		rl_head.setOnClickListener(this);
		FontManager.changeFonts(mContext, tv_cirlce_head_title,tv_cirlce_name_title,
				et_circle_name,riv_circle_head,rl_head);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_head://提交头像
			showBottomDialog();
			break;

		default:
			break;
		}
		
	}
	
	
	
	public void createCircle(){
		HttpControl httpContorl = new HttpControl();
		httpContorl.createCircle(et_circle_name.getText().toString().trim(),littlePicPath_cache.substring(littlePicPath_cache.lastIndexOf("/")+1,littlePicPath_cache.length()), true, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				// TODO Auto-generated method stub
				
			}
		}, AddCircleActivity.this);
	}
	
	
	/**
	 * 弹出底部菜单(相机和图库)
	 */
	protected void showBottomDialog() {
		ToolsUtil.hideSoftInputKeyBoard(AddCircleActivity.this);
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
										.createNewFile(littlePicPath_cache)),5,8),
								PhotoUtils.INTENT_REQUEST_CODE_CROP);
					}
				}
			}
		}
		if (requestCode == PhotoUtils.INTENT_REQUEST_CODE_CROP) {// 裁剪后返回
			if (resultCode == RESULT_OK) {
				Bitmap bm = BitmapFactory.decodeFile(littlePicPath_cache);
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

	
	
	private void uploadImage(String imagePath){
		if(!progressDialog.isShowing()){
			progressDialog.show();
		}
		HttpControl httpControl = new HttpControl();
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
				createCircle();
				}
				
		});
	}
	
}
