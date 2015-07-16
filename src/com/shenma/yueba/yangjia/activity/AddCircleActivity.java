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
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.callback.SaveCallback;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.baijia.modle.BaseRequest;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.constants.HttpConstants;
import com.shenma.yueba.util.CustomProgressDialog;
import com.shenma.yueba.util.FileUtils;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ParserJson;
import com.shenma.yueba.util.PhotoUtils;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;
import com.shenma.yueba.view.SelectePhotoType;

/**
 * 新增圈子
 * 
 * @author a
 * 
 */
public class AddCircleActivity extends BaseActivityWithTopView implements
		OnClickListener {

	private RoundImageView riv_circle_head;
	private EditText et_circle_name;
	private LinearLayout ll_logo;
	private String littlePicPath;// 小图路径
	private String littlePicPath_cache;// 裁剪后图片存储的路径
	private CustomProgressDialog progressDialog;
	private TextView tv_create;
	private TextView tv_add_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_circle_layout2);
		super.onCreate(savedInstanceState);
		initView();
		progressDialog = CustomProgressDialog.createDialog(this);
	}

	private void initView() {
		setTitle("新建圈子");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AddCircleActivity.this.finish();
			}
		});
		tv_add_title = getView(R.id.tv_add_title);
		tv_create = getView(R.id.tv_create);
		tv_create.setOnClickListener(this);
		et_circle_name = getView(R.id.et_circle_name);
		riv_circle_head = getView(R.id.riv_circle_head);
		ll_logo = getView(R.id.ll_logo);
		ll_logo.setOnClickListener(this);
		FontManager.changeFonts(mContext, et_circle_name, tv_create,tv_top_title,tv_add_title);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_logo:// 提交头像
			ToolsUtil.hideSoftKeyboard(mContext, ll_logo);
			showBottomDialog();
			break;
		case R.id.tv_create:// 新建圈子
			if (TextUtils.isEmpty(littlePicPath_cache)) {
				Toast.makeText(mContext, "圈子头像不能为空", 1000).show();
				return;
			}
			if (TextUtils.isEmpty(et_circle_name.getText().toString().trim())) {
				Toast.makeText(mContext, "圈子名称不能为空", 1000).show();
				return;
			}
			uploadImage(littlePicPath_cache);
			break;
		default:
			break;
		}

	}

	// public void createCircle2() {
	// HttpUtils httpUtils = new HttpUtils();
	// RequestParams map = new RequestParams();
	// HttpControl httpControl = new HttpControl();
	// map.addBodyParameter(Constants.NAME, et_circle_name.getText()
	// .toString().trim());
	// map.addBodyParameter(Constants.LOGO, littlePicPath_cache.substring(
	// littlePicPath_cache.lastIndexOf("/") + 1,
	// littlePicPath_cache.length()));
	// httpUtils.send(HttpMethod.POST,
	// HttpConstants.METHOD_CIRCLE_GETBUYERGROUPS, map,
	// new RequestCallBack<String>() {
	// @Override
	// public void onSuccess(ResponseInfo<String> responseInfo) {
	// progressDialog.dismiss();
	// BaseRequest resultBean = ParserJson
	// .parserBase(responseInfo.result);
	// if (200 == resultBean.getStatusCode()) {// 返回成功
	// Toast.makeText(mContext, "创建成功", 1000).show();
	// setResult(Constants.RESULTCODE);
	// AddCircleActivity.this.finish();
	// } else {
	// Toast.makeText(mContext, resultBean.getMessage(),
	// 1000).show();
	// }
	// }
	//
	// @Override
	// public void onFailure(HttpException error, String msg) {
	// // progressDialog.dismiss();
	// Toast.makeText(mContext, "创建失败！", 1000).show();
	// }
	// });
	// }

	public void createCircle() {
		HttpControl httpContorl = new HttpControl();
		httpContorl.createCircle(et_circle_name.getText().toString().trim(),
				littlePicPath_cache.substring(
						littlePicPath_cache.lastIndexOf("/") + 1,
						littlePicPath_cache.length()), false,
				new HttpCallBackInterface() {

					@Override
					public void http_Success(Object obj) {
						if (progressDialog.isShowing()) {
							progressDialog.cancel();
						}
						Toast.makeText(mContext, "创建成功", 1000).show();
						setResult(Constants.RESULTCODE);
						AddCircleActivity.this.finish();
					}

					@Override
					public void http_Fails(int error, String msg) {
						if (progressDialog.isShowing()) {
							progressDialog.cancel();
						}
						Toast.makeText(mContext, msg, 1000).show();

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
										.createNewFile(littlePicPath_cache))), PhotoUtils.INTENT_REQUEST_CODE_CROP);
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

	private void uploadImage(String imagePath) {
		if (!progressDialog.isShowing()) {
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
				runOnUiThread(new Runnable() {
					public void run() {
						createCircle();
					}
				});

			}

		});
	}

	
	
	  @Override
	    protected void onDestroy() {
	    	MyApplication.getInstance().removeActivity(this);//加入回退栈
	    	super.onDestroy();
	    }
	
}
