package com.shenma.yueba.camera2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.util.BitmapUtil;
import com.shenma.yueba.util.Debug;
import com.shenma.yueba.util.FileUtils;
import com.shenma.yueba.util.PathManager;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.yangjia.activity.AddTagActivity;
import com.shenma.yueba.yangjia.activity.EditPicActivity;

@SuppressLint("NewApi")
// 默认的相机为横平，所以Activity设置为横屏，拍出的照片才正确
public class ActivityCapture extends Activity implements View.OnClickListener,
		CaptureSensorsObserver.RefocuseListener {
	private ImageView bnToggleCamera;
	public ImageView bt_light;
	public final static String IMAGE_URI = "iamge_uri";
	public final static String ACTION_CROP_IMAGE = "android.intent.action.CROP";
	public final static String CROP_IMAGE_URI = "crop_image_uri";
	private final int ONE_K = 1024;
	private final int ONE_M = ONE_K * ONE_K;
	private final int MAX_AVATAR_SIZE = 2 * ONE_M; // 2M
	private ImageView bnCapture;
	private ImageView iv_close;
	public final static int REQ_CODE_LOCALE_BG = 202;
	private FrameLayout framelayoutPreview;
	private CameraPreview preview;
	private CameraCropBorderView cropBorderView;
	private Camera camera;
	private PictureCallback pictureCallBack;
	private Camera.AutoFocusCallback focusCallback;
	private CaptureSensorsObserver observer;
	private View focuseView;
	public final static int REQ_CODE_PHOTO_CROP = 102;
	private int currentCameraId;
	private int frontCameraId;
	private boolean _isCapturing;

	CaptureOrientationEventListener _orientationEventListener;
	private int _rotation;
	public static final int kPhotoMaxSaveSideLen = 1600;
	public static final String kPhotoPath = "photo_path";

	// private ShutterCallback _shutterCallback = new ShutterCallback() {
	// @Override
	// public void onShutter() {
	// }
	// };

	final static String TAG = "capture";
	private TextView tv_little_pic;// 显示的小图
	private ImageView tempImageView;// 临时大图

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		observer = new CaptureSensorsObserver(this);
		_orientationEventListener = new CaptureOrientationEventListener(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);
		getViews();
		initViews();
		setListeners();
		setupDevice();
	}

	@Override
	protected void onDestroy() {
		if (null != observer) {
			observer.setRefocuseListener(null);
			observer = null;
		}
		_orientationEventListener = null;

		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseCamera(); // release the camera immediately on pause event
		observer.stop();
		_orientationEventListener.disable();
	}

	@Override
	protected void onResume() {
		super.onResume();
		openCamera();
	}

	private void releaseCamera() {
		if (camera != null) {
			camera.release(); // release the camera for other applications
			camera = null;
		}

		if (null != preview) {
			framelayoutPreview.removeAllViews();
			preview = null;
		}
	}

	protected void getViews() {
		bnToggleCamera = (ImageView) findViewById(R.id.bnToggleCamera);
		tempImageView = (ImageView) findViewById(R.id.tempImageView);
		bt_light = (ImageView) findViewById(R.id.bt_light);
		tv_little_pic = (TextView) findViewById(R.id.tv_little_pic);
		bnCapture = (ImageView) findViewById(R.id.bnCapture);
		framelayoutPreview = (FrameLayout) findViewById(R.id.cameraPreview);
		focuseView = findViewById(R.id.viewFocuse);
		iv_close = (ImageView) findViewById(R.id.iv_close);
	}

	protected void initViews() {
		bnCapture.setRotation(-90);
		bnToggleCamera.setRotation(-90);
		bt_light.setRotation(-90);
		tv_little_pic.setRotation(-90);
	}

	private void getLocalImage(int reqCode) {
		// 抓下异常是防止有的机器不支持ACTION_PICK或ACTION_GET_CONTENT的动作
		try {
			Intent intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			startActivityForResult(intent, reqCode);
		} catch (Exception e1) {
			try {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, reqCode);
			} catch (Exception e2) {
			}
		}
	}

	protected void setListeners() {
		iv_close.setOnClickListener(this);
		tv_little_pic.setOnClickListener(this);
		bnToggleCamera.setOnClickListener(this);
		bt_light.setOnClickListener(this);
		bnCapture.setOnClickListener(this);
		observer.setRefocuseListener(this);
		pictureCallBack = new PictureCallback() {
			@Override
			public void onPictureTaken(byte[] data, final Camera camera) {
				_isCapturing = false;
				Bitmap bitmap = null;
				try {
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
					BitmapFactory
							.decodeByteArray(data, 0, data.length, options);
					// Debug.debug("width--:" + options.outWidth + "  height--:"
					// + options.outHeight);
					options.inJustDecodeBounds = false;
					options.inPreferredConfig = Bitmap.Config.ARGB_4444;
					// 此处就把图片压缩了
					// options.inSampleSize = Math.max(options.outWidth
					// / kPhotoMaxSaveSideLen, options.outHeight
					// / kPhotoMaxSaveSideLen);
					bitmap = BitmapUtil.decodeByteArrayUnthrow(data, options);
					if (null == bitmap) {
						options.inSampleSize = Math.max(2,
								options.inSampleSize * 2);
						bitmap = BitmapUtil.decodeByteArrayUnthrow(data,
								options);
					}
				} catch (Throwable e) {
				}
				if (null == bitmap) {
					Toast.makeText(ActivityCapture.this, "内存不足，保存照片失败！",
							Toast.LENGTH_SHORT).show();
					return;
				}
				// long start = System.currentTimeMillis();
				Bitmap addBitmap = BitmapUtil.rotateAndScale(bitmap, _rotation,
						kPhotoMaxSaveSideLen, true);
				final Bitmap finalBitmap = cropPhotoImage(addBitmap);
				File photoFile = PathManager.getCropPhotoPath();
				boolean successful = BitmapUtil.saveBitmap2file(finalBitmap,
						photoFile, Bitmap.CompressFormat.JPEG, 100);
				while (!successful) {
					successful = BitmapUtil.saveBitmap2file(finalBitmap,
							photoFile, Bitmap.CompressFormat.JPEG, 100);
				}
				if (finalBitmap != null && !finalBitmap.isRecycled()) {
					addBitmap.recycle();
				}
				
				
//---------------------------------------------------------------------------------------------------------
				// tempImageView.setImageBitmap(finalBitmap);
				// Animation animation = AnimationUtils.loadAnimation(
				// getApplication(), R.anim.tempview_show);
				// tempImageView.setAnimation(animation);
				// animation.setAnimationListener(new AnimationListener() {
				//
				// @Override
				// public void onAnimationStart(Animation animation) {
				// // TODO Auto-generated method stub
				//
				// }
				//
				// @Override
				// public void onAnimationRepeat(Animation animation) {
				// // TODO Auto-generated method stub
				//
				// }
				//
				// @Override
				// public void onAnimationEnd(Animation animation) {
				// tv_little_pic.setImageBitmap(finalBitmap);
				// tempImageView.setVisibility(View.GONE);
				// focuseView.setVisibility(View.GONE);
				// // camera.setPreviewCallback(null) ;
				// // camera.stopPreview();
				// // camera.release();
				// // camera.open();
				// // setupDevice();
				// // camera.stopPreview();
				// // camera.release();
				// // openCamera();
				// // getViews();
				// // initViews();
				// // setListeners();
				// // observer = new
				// // CaptureSensorsObserver(ActivityCapture.this);
				// // _orientationEventListener = new
				// // CaptureOrientationEventListener(ActivityCapture.this);
				// // getViews();
				// // initViews();
				// // setListeners();
				// // setupDevice();
				// if (saveBitmapToFile(finalBitmap)) {
				// MyApplication.getInstance().getPublishUtil()
				// .setFrom("camera");
				// Intent intent = new Intent(ActivityCapture.this,
				// EditPicActivity.class);
				// startActivity(intent);
				// finish();
				// }
				// }
				// });
//-------------------------------------------------------------------------------------------------------------
				
				
				if (saveBitmapToFile(finalBitmap)) {
					MyApplication.getInstance().getPublishUtil()
							.setFrom("camera");
					Intent intent = new Intent(ActivityCapture.this,
							EditPicActivity.class);
					startActivity(intent);
					finish();
				}

				//
				// Intent intent = new Intent();
				// intent.putExtra(kPhotoPath, photoFile.getAbsolutePath());
				// ActivityCapture.this.setResult(RESULT_OK, intent);
				// ActivityCapture.this.finish();
			}
		};
		focusCallback = new Camera.AutoFocusCallback() {
			@Override
			public void onAutoFocus(boolean successed, Camera camera) {
				focuseView.setVisibility(View.INVISIBLE);
			}
		};
	}

	private boolean saveBitmapToFile(Bitmap bitmap) {
		File dir = new File(FileUtils.getRootPath() + "/tagPic/");
		if (!dir.exists()) {
			if (!dir.exists())
				dir.mkdir();
		}
		if ("".equals(MyApplication.getInstance().getPublishUtil().getIndex())) {
			MyApplication.getInstance().getPublishUtil().setIndex("0");
		}
		File file = new File(dir, "joybar_camera"
				+ SharedUtil.getUserId(ActivityCapture.this)
				+ MyApplication.getInstance().getPublishUtil().getIndex()
				+ ".png");
		if (file.exists()) {
			file.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	// 根据拍照的图片来剪裁
	private Bitmap cropPhotoImage(Bitmap bmp) {
		int dw = bmp.getWidth();
		int dh = bmp.getHeight();
		int height;
		int width;
		if (dh > dw) {// 图片竖直方向
			// 切图片时按照竖屏来计算
			height = getWindowManager().getDefaultDisplay().getWidth();
			width = getWindowManager().getDefaultDisplay().getHeight();
		} else {// 图片是水平方向
			// 切图片时按照横屏来计算
			width = getWindowManager().getDefaultDisplay().getWidth();
			height = getWindowManager().getDefaultDisplay().getHeight();
		}
		Rect rect = new Rect();
		int left = (width - cropBorderView.getRect().width()) / 2;
		int top = (height - cropBorderView.getRect().height()) / 2;
		int right = left + cropBorderView.getRect().width();
		int bottom = top + cropBorderView.getRect().height();
		rect.set(left, top, right, bottom);
		float scale = 1.0f;
		// 如果图片的宽或者高大于屏幕，则缩放至屏幕的宽或者高
		if (dw > width && dh <= height) {
			scale = width * 1.0f / dw;
		}
		if (dh > height && dw <= width) {
			scale = height * 1.0f / dh;
		}
		// 如果宽和高都大于屏幕，则让其按按比例适应屏幕大小
		if (dw > width && dh > height) {
			scale = Math.max(width * 1.0f / dw, height * 1.0f / dh);
		}
		// 如果图片的宽度和高度都小于屏幕的宽度和高度，则放大至屏幕大小
		if (dw < width && dh < height) {
			scale = width * 1.0f / dw;
		}
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		try {
			Bitmap b2 = Bitmap.createBitmap(bmp, 0, 0, dw, dh, matrix, true);
			if (null != b2 && bmp != b2) {
				bmp.recycle();
				bmp = b2;
			}
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		try {
			Bitmap b3 = Bitmap.createBitmap(bmp, rect.left, rect.top,
					rect.width(), rect.height());
			if (null != b3 && bmp != b3) {
				bmp.recycle();
				bmp = b3;
			}
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		// 将图片压缩至640*640
		try {
			Bitmap b4 = Bitmap.createScaledBitmap(bmp, 640, 640, false);
			if (null != b4 && bmp != b4) {
				bmp.recycle();
				bmp = b4;
			}
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return bmp;
	}

	private void setupDevice() {
		if (android.os.Build.VERSION.SDK_INT > 8) {
			int cameraCount = Camera.getNumberOfCameras();

			if (cameraCount < 1) {
				Toast.makeText(this, "你的设备木有摄像头。。。", Toast.LENGTH_SHORT).show();
				finish();
				return;
			} else if (cameraCount == 1) {
				bnToggleCamera.setVisibility(View.INVISIBLE);
			} else {
				bnToggleCamera.setVisibility(View.VISIBLE);
			}

			currentCameraId = 0;
			frontCameraId = findFrontFacingCamera();
			if (-1 == frontCameraId) {
				bnToggleCamera.setVisibility(View.INVISIBLE);
			}
		}
	}

	private void openCamera() {
		if (android.os.Build.VERSION.SDK_INT > 8) {
			try {
				camera = Camera.open(currentCameraId);
			} catch (Exception e) {
				Toast.makeText(this, "摄像头打开失败", Toast.LENGTH_SHORT).show();
				finish();
				return;
			}
			setCameraDisplayOrientation(this, 0, camera);
		} else {
			try {
				camera = Camera.open();
			} catch (Exception e) {
				Toast.makeText(this, "摄像头打开失败", Toast.LENGTH_SHORT).show();
				finish();
				return;
			}
		}

		Camera.Parameters camParmeters = camera.getParameters();
		List<Size> sizes = camParmeters.getSupportedPreviewSizes();
		// for (Size size : sizes) {
		// Log.v(TAG, "w:" + size.width + ",h:" + size.height);
		// }

		preview = new CameraPreview(this, camera);
		cropBorderView = new CameraCropBorderView(this);
		FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		framelayoutPreview.addView(preview, params1);
		framelayoutPreview.addView(cropBorderView, params2);
		observer.start();
		_orientationEventListener.enable();
		List<String> flashModes = camParmeters.getSupportedFlashModes();
		// Check if camera flash exists
		if (flashModes == null) {
			// Use the screen as a flashlight (next best thing)
			return;
		}
		String flashMode = camParmeters.getFlashMode();
		if (flashModes.contains(Parameters.FLASH_MODE_AUTO)) {
			camParmeters.setFlashMode(Parameters.FLASH_MODE_AUTO);
			camera.setParameters(camParmeters);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bnToggleCamera:
			switchCamera();
			break;
		case R.id.bt_light:// 闪光灯
			turnLightOn(camera, bt_light);
			break;
		case R.id.bnCapture:
			bnCaptureClicked();
			break;
		case R.id.tv_little_pic:// 选择图库
			getLocalImage(REQ_CODE_LOCALE_BG);
			break;
		case R.id.iv_close:// 关闭相机
			ActivityCapture.this.finish();
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	// 横竖屏切换的时候
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	private void switchCamera() {
		if (currentCameraId == 0) {
			currentCameraId = frontCameraId;
		} else {
			currentCameraId = 0;
		}
		releaseCamera();
		openCamera();
	}

	private void bnCaptureClicked() {
		if (_isCapturing) {
			return;
		}
		_isCapturing = true;
		focuseView.setVisibility(View.INVISIBLE);

		try {
			camera.takePicture(null, null, pictureCallBack);
		} catch (RuntimeException e) {
			e.printStackTrace();
			_isCapturing = false;
		}
	}

	/**
	 * A basic Camera preview class
	 */
	public class CameraPreview extends SurfaceView implements
			SurfaceHolder.Callback {
		private SurfaceHolder mHolder;
		private Camera mCamera;

		@SuppressWarnings("deprecation")
		public CameraPreview(Context context, Camera camera) {
			super(context);
			mCamera = camera;

			// Install a SurfaceHolder.Callback so we get notified when the
			// underlying surface is created and destroyed.
			mHolder = getHolder();
			mHolder.addCallback(this);
			// deprecated setting, but required on Android versions prior to 3.0
			mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}

		public void surfaceCreated(SurfaceHolder holder) {
			// The Surface has been created, now tell the camera where to draw
			// the preview.
			try {
				mCamera.setPreviewDisplay(holder);
				mCamera.startPreview();
			} catch (Exception e) {
				Log.d(TAG, "Error setting camera preview: " + e.getMessage());
			}
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			// empty. Take care of releasing the Camera preview in your
			// activity.
			if (camera != null) {
				camera.setPreviewCallback(null);
				camera.stopPreview();// 停止预览
				camera.release(); // 释放摄像头资源
				camera = null;
			}
		}

		private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
			final double ASPECT_TOLERANCE = 0.05;
			double targetRatio = (double) w / h;
			if (sizes == null)
				return null;

			Size optimalSize = null;
			double minDiff = Double.MAX_VALUE;

			int targetHeight = h;

			// Try to find an size match aspect ratio and size
			for (Size size : sizes) {
				double ratio = (double) size.width / size.height;
				if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
					continue;
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}

			// Cannot find the one match the aspect ratio, ignore the
			// requirement
			if (optimalSize == null) {
				minDiff = Double.MAX_VALUE;
				for (Size size : sizes) {
					if (Math.abs(size.height - targetHeight) < minDiff) {
						optimalSize = size;
						minDiff = Math.abs(size.height - targetHeight);
					}
				}
			}

			return optimalSize;
		}

		private Size getOptimalPictureSize(List<Size> sizes, double targetRatio) {
			final double ASPECT_TOLERANCE = 0.05;

			if (sizes == null)
				return null;

			Size optimalSize = null;
			int optimalSideLen = 0;
			double optimalDiffRatio = Double.MAX_VALUE;

			for (Size size : sizes) {

				int sideLen = Math.max(size.width, size.height);
				// LogEx.i("size.width: " + size.width + ", size.height: " +
				// size.height);
				boolean select = false;
				if (sideLen < kPhotoMaxSaveSideLen) {
					if (0 == optimalSideLen || sideLen > optimalSideLen) {
						select = true;
					}
				} else {
					if (kPhotoMaxSaveSideLen > optimalSideLen) {
						select = true;
					} else {
						double diffRatio = Math.abs((double) size.width
								/ size.height - targetRatio);
						if (diffRatio + ASPECT_TOLERANCE < optimalDiffRatio) {
							select = true;
						} else if (diffRatio < optimalDiffRatio
								+ ASPECT_TOLERANCE
								&& sideLen < optimalSideLen) {
							select = true;
						}
					}
				}

				if (select) {
					optimalSize = size;
					optimalSideLen = sideLen;
					optimalDiffRatio = Math.abs((double) size.width
							/ size.height - targetRatio);
				}
			}

			return optimalSize;
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int w,
				int h) {
			// If your preview can change or rotate, take care of those events
			// here.
			// Make sure to stop the preview before resizing or reformatting it.

			Debug.debug("surfaceChanged format:" + format + ", w:" + w + ", h:"
					+ h);
			if (mHolder.getSurface() == null) {
				// preview surface does not exist
				return;
			}

			// stop preview before making changes
			try {
				mCamera.stopPreview();
			} catch (Exception e) {
				// ignore: tried to stop a non-existent preview
			}

			try {
				Camera.Parameters parameters = mCamera.getParameters();

				List<Size> sizes = parameters.getSupportedPreviewSizes();
				Size optimalSize = getOptimalPreviewSize(sizes, w, h);
				parameters
						.setPreviewSize(optimalSize.width, optimalSize.height);
				double targetRatio = (double) w / h;
				sizes = parameters.getSupportedPictureSizes();
				optimalSize = getOptimalPictureSize(sizes, targetRatio);
				parameters
						.setPictureSize(optimalSize.width, optimalSize.height);
				parameters.setRotation(0);
				mCamera.setParameters(parameters);
			} catch (Exception e) {
				Debug.debug(e.toString());
			}

			// set preview size and make any resize, rotate or
			// reformatting changes here

			// start preview with new settings
			try {
				mCamera.setPreviewDisplay(mHolder);
				mCamera.startPreview();
			} catch (Exception e) {
				Debug.debug("Error starting camera preview: " + e.getMessage());
			}
		}
	}

	private int findFrontFacingCamera() {
		int cameraId = -1;
		// Search for the front facing camera
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
				Log.d(TAG, "Camera found");
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}

	private static void setCameraDisplayOrientation(Activity activity,
			int cameraId, Camera camera) {
		CameraInfo info = new CameraInfo();
		Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degrees = 0;
		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}

		int result;
		if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360; // compensate the mirror
		} else { // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}

		// LogEx.i("result: " + result);
		camera.setDisplayOrientation(result);
	}

	@Override
	public void needFocuse() {

		// LogEx.i("_isCapturing: " + _isCapturing);
		if (null == camera || _isCapturing) {
			return;
		}

		// LogEx.i("autoFocus");
		camera.cancelAutoFocus();
		try {
			camera.autoFocus(focusCallback);
		} catch (Exception e) {
			Debug.debug(e.toString());
			return;
		}

		if (View.INVISIBLE == focuseView.getVisibility()) {
			focuseView.setVisibility(View.VISIBLE);
			focuseView.getParent().requestTransparentRegion(preview);
		}
	}

	// 相机旋转监听的类，最后保存图片时用到
	private class CaptureOrientationEventListener extends
			OrientationEventListener {
		public CaptureOrientationEventListener(Context context) {
			super(context);
		}

		@Override
		public void onOrientationChanged(int orientation) {
			if (null == camera)
				return;
			if (orientation == ORIENTATION_UNKNOWN)
				return;

			orientation = (orientation + 45) / 90 * 90;
			if (android.os.Build.VERSION.SDK_INT <= 8) {
				_rotation = (90 + orientation) % 360;
				return;
			}

			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(currentCameraId, info);

			if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
				_rotation = (info.orientation - orientation + 360) % 360;
			} else { // back-facing camera
				_rotation = (info.orientation + orientation) % 360;
			}
		}
	}

	public static void turnLightOn(Camera mCamera, ImageView bt) {
		if (mCamera == null) {
			return;
		}
		Parameters parameters = mCamera.getParameters();
		if (parameters == null) {
			return;
		}
		List<String> flashModes = parameters.getSupportedFlashModes();
		// Check if camera flash exists
		if (flashModes == null) {
			// Use the screen as a flashlight (next best thing)
			return;
		}
		String flashMode = parameters.getFlashMode();
		if (Parameters.FLASH_MODE_OFF.equals(flashMode)) {// 如果是关闭，就设置为自动
			// Turn on the flash
			if (flashModes.contains(Parameters.FLASH_MODE_AUTO)) {
				bt.setBackgroundResource(R.drawable.ic_camera_top_bar_flash_auto_normal);
				parameters.setFlashMode(Parameters.FLASH_MODE_AUTO);
				mCamera.setParameters(parameters);
			} else {
			}
		} else if (Parameters.FLASH_MODE_AUTO.equals(flashMode)) {// 如果是自动，就设置为开启
			// Turn off the flash
			if (flashModes.contains(Parameters.FLASH_MODE_TORCH)) {
				bt.setBackgroundResource(R.drawable.ic_camera_top_bar_flash_on_normal);
				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
				mCamera.setParameters(parameters);
			} else {
				Log.e(TAG, "FLASH_MODE_OFF not supported");
			}
		} else if (Parameters.FLASH_MODE_TORCH.equals(flashMode)) {// 如果是开启，就设置为关闭
			// Turn off the flash
			if (flashModes.contains(Parameters.FLASH_MODE_OFF)) {
				bt.setBackgroundResource(R.drawable.ic_camera_top_bar_flash_off_normal);
				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
				mCamera.setParameters(parameters);
			} else {
				Log.e(TAG, "FLASH_MODE_OFF not supported");
			}

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			// 读取本地相片
			if (requestCode == REQ_CODE_LOCALE_BG) {

				readLocalImage(data);

			} else if (requestCode == REQ_CODE_PHOTO_CROP) {// 裁剪图片后的返回结果

				readCropImage(data);
			}
		}
	}

	/**
	 * 此处写方法描述
	 * 
	 * @Title: readLocalImage
	 * @param data
	 * @return void
	 * @date 2012-12-12 上午11:26:35
	 */
	private void readLocalImage(Intent data) {
		if (data == null) {
			return;
		}

		Uri uri = null;
		uri = data.getData();

		if (uri != null) {

			startPhotoCrop(uri, null, REQ_CODE_PHOTO_CROP); // 图片裁剪
		}
	}

	/**
	 * 此处写方法描述
	 * 
	 * @Title: readCropImage
	 * @param data
	 * @return void
	 * @date 2012-12-12 上午11:27:52
	 */
	@SuppressWarnings("deprecation")
	private void readCropImage(Intent data) {

		if (data == null) {
			return;
		}
		Uri uri = data.getParcelableExtra(CROP_IMAGE_URI);
		// Log.d("lzc", "uri=========================>" + uri.toString());
		// Bitmap photo = getBitmap(uri);
		// Drawable drawable = new BitmapDrawable(photo);
		// // Drawable drawable = bd.;
		// // imageView.setBackgroundDrawable(drawable);
		// if (photo != null) {
		//
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		//
		// byte[] datas = null;
		// try {
		// datas = baos.toByteArray();
		// baos.flush();
		// baos.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//
		// if (datasException(datas)) {
		// return;
		// }
		//
		// // 保存头像
		// // 字符参数部分
		// saveAvatar(datas);
		MyApplication.getInstance().getPublishUtil().setFrom("picture");
		MyApplication.getInstance().getPublishUtil().setUri(uri);
		Intent editPicIntent = new Intent(ActivityCapture.this,
				EditPicActivity.class);
		startActivity(editPicIntent);

	}

	/**
	 * 此处写方法描述
	 * 
	 * @Title: getBitmap
	 * @param intent
	 * @return void
	 * @date 2012-12-13 下午8:22:23
	 */
	private Bitmap getBitmap(Uri uri) {
		Bitmap bitmap = null;
		InputStream is = null;
		try {

			is = getInputStream(uri);

			bitmap = BitmapFactory.decodeStream(is);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignored) {
				}
			}
		}
		return bitmap;
	}

	/**
	 * 获取输入流
	 * 
	 * @Title: getInputStream
	 * @param mUri
	 * @return
	 * @return InputStream
	 * @date 2012-12-14 上午9:00:31
	 */
	private InputStream getInputStream(Uri mUri) throws IOException {
		try {
			if (mUri.getScheme().equals("file")) {
				return new java.io.FileInputStream(mUri.getPath());
			} else {
				return this.getContentResolver().openInputStream(mUri);
			}
		} catch (FileNotFoundException ex) {
			return null;
		}
	}

	/**
	 * 此处写方法描述
	 * 
	 * @Title: handleDatasException
	 * @param datas
	 * @return void
	 * @date 2012-12-12 上午11:32:20
	 */
	private boolean datasException(byte[] datas) {
		// 头像处理异常
		if (datas == null || datas.length <= 0) {

			return true;
		}

		// 头像尺寸不符
		if (datas.length > MAX_AVATAR_SIZE) {

			return true;
		}

		return false;
	}

	/**
	 * 此处写方法描述
	 * 
	 * @Title: saveAvatar
	 * @param datas
	 * @return void
	 * @date 2012-12-12 上午11:30:22
	 */
	private void saveAvatar(byte[] datas) {
		/**
		 * Map<String, String> params = new HashMap<String, String>();
		 * params.put("avtf", "1"); params.put("_w", "" + AVATAR_CROP_WIDTH);
		 * params.put("_h", "" + AVATAR_CROP_HEIGHT); params.put("x", "" +
		 * AVATAR_CROP_STARTX); params.put("y", "" + AVATAR_CROP_STARTY);
		 * params.put("w", "" + AVATAR_CROP_WIDTH); params.put("h", "" +
		 * AVATAR_CROP_HEIGHT);
		 * 
		 * // 文件参数部分 FormFile formFile = new FormFile(datas, "avatar",
		 * "avatar_orig");
		 * formFile.setContentType(FormFile.CONTENT_TYPE_IMAGE_JPEG);
		 * 
		 * // 异步上传任务 // new UploadAvatar(params,formFile).execute(); new
		 * UploadImage(params, formFile).execute();
		 * 
		 * progressDialogAvatar = new ProgressDialog(
		 * Activity_Space_Mine_New.this);
		 * progressDialogAvatar.setCancelable(true); progressDialogAvatar
		 * .setMessage(getString(R.string.information_edit_upload_avatar));
		 * progressDialogAvatar.show();
		 */
	}

	/**
	 * 开始裁剪
	 * 
	 * @Title: startPhotoCrop
	 * @param uri
	 * @param duplicatePath
	 * @param reqCode
	 * @return void
	 * @date 2012-12-12 上午11:15:38
	 */
	private void startPhotoCrop(Uri uri, String duplicatePath, int reqCode) {

		Uri duplicateUri = preCrop(uri, duplicatePath);

		Intent intent = new Intent(ACTION_CROP_IMAGE);
		intent.putExtra(IMAGE_URI, uri);
		intent.putExtra("crop", "false");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		startActivityForResult(intent, reqCode);
		/**
		 * intent.setDataAndType(uri, "image/*"); intent.putExtra("crop",
		 * "true"); intent.putExtra("outputFormat", "JPEG"); // aspectX aspectY
		 * 是宽高的比例 intent.putExtra("aspectX", 1); intent.putExtra("aspectY", 1);
		 * // outputX outputY 是裁剪图片宽高 intent.putExtra("outputX", reqCode ==
		 * REQ_CODE_BG_CROP ? BG_CROP_WIDTH : AVATAR_CROP_WIDTH);
		 * intent.putExtra("outputY", reqCode == REQ_CODE_BG_CROP ?
		 * BG_CROP_HEIGHT : AVATAR_CROP_HEIGHT);
		 * intent.putExtra(MediaStore.EXTRA_OUTPUT, duplicateUri);
		 * intent.putExtra("return-data", true);
		 */

	}

	/**
	 * 剪裁之前的预处理
	 * 
	 * @Title: preCrop
	 * @param uri
	 * @param duplicatePath
	 * @return
	 * @return Uri
	 * @date 2012-12-4 上午10:18:33
	 */
	private Uri preCrop(Uri uri, String duplicatePath) {
		Uri duplicateUri = null;

		if (duplicatePath == null) {
			duplicateUri = getDuplicateUri(uri);
		} else {
			duplicateUri = getDuplicateUri(uri, duplicatePath);
		}

		// rotateImage();

		return duplicateUri;
	}

	/**
	 * 设置获取裁剪后图像的uri
	 * 
	 * @Title: getDuplicateUri
	 * @param uri
	 * @return
	 * @return Uri
	 * @date 2012-11-26 下午12:25:16
	 */
	private Uri getDuplicateUri(Uri uri) {
		Uri duplicateUri = null;

		String uriString = getUriString(uri);

		duplicateUri = getDuplicateUri(uri, uriString);

		return duplicateUri;
	}

	/**
	 * 如果是拍照的话就直接获取了
	 * 
	 * @Title: getDuplicateUri
	 * @param uri
	 * @param uriString
	 * @return
	 * @return Uri
	 * @date 2012-11-28 下午6:30:38
	 */
	private Uri getDuplicateUri(Uri uri, String uriString) {

		Uri duplicateUri = null;
		String duplicatePath = null;
		duplicatePath = uriString.replace(".", "_duplicate.");

		// cropImagePath = uriString;
		// 判断原图是否旋转，旋转了进行修复
		rotateImage(uriString);

		duplicateUri = Uri.fromFile(new File(duplicatePath));

		return duplicateUri;
	}

	/**
	 * 旋转图象
	 * 
	 * @Title: rotateImage
	 * @return void
	 * @date 2012-12-4 上午10:18:53
	 */
	private void rotateImage(String uriString) {

		try {
			ExifInterface exifInterface = new ExifInterface(uriString);

			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			if (orientation == ExifInterface.ORIENTATION_ROTATE_90
					|| orientation == ExifInterface.ORIENTATION_ROTATE_180
					|| orientation == ExifInterface.ORIENTATION_ROTATE_270) {

				String value = String.valueOf(orientation);
				exifInterface
						.setAttribute(ExifInterface.TAG_ORIENTATION, value);
				// exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION,
				// "no");
				exifInterface.saveAttributes();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据Uri获取文件的路径
	 * 
	 * @Title: getUriString
	 * @param uri
	 * @return
	 * @return String
	 * @date 2012-11-28 下午1:19:31
	 */
	private String getUriString(Uri uri) {
		String imgPath = null;
		if (uri != null) {
			String uriString = uri.toString();
			// 小米手机的适配问题，小米手机的uri以file开头，其他的手机都以content开头
			// 以content开头的uri表明图片插入数据库中了，而以file开头表示没有插入数据库
			// 所以就不能通过query来查询，否则获取的cursor会为null。
			if (uriString.startsWith("file")) {
				// uri的格式为file:///mnt....,将前七个过滤掉获取路径
				imgPath = uriString.substring(7, uriString.length());

				return imgPath;
			}
			Cursor cursor = getContentResolver().query(uri, null, null, null,
					null);
			cursor.moveToFirst();
			imgPath = cursor.getString(1); // 图片文件路径

		}
		return imgPath;
	}
}
