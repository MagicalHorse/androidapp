package com.shenma.yueba.yangjia.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.camera2.ActivityCapture;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.filter.FeatherFilter;
import com.shenma.yueba.filter.IImageFilter;
import com.shenma.yueba.filter.Image;
import com.shenma.yueba.filter.NightVisionFilter;
import com.shenma.yueba.filter.RainBowFilter;
import com.shenma.yueba.filter.SepiaFilter;
import com.shenma.yueba.filter.XRadiationFilter;
import com.shenma.yueba.util.FileUtils;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.TagImageView;
import com.shenma.yueba.yangjia.modle.TagListBean;

/**
 * 编辑图片
 * 
 * @author a
 * 
 */
public class EditPicActivity extends BaseActivityWithTopView implements
		OnClickListener, OnTouchListener {

	private ImageView iv_pic;
	private TextView tv_tishi;
	private TextView tv_next;
	private Uri uri;
	private TagImageView layout_tag_image;
	private List<Map<String, Double>> tagList = new ArrayList<Map<String, Double>>();
	int startx = 0;
	int starty = 0;
	private LinearLayout ll_top;
	private Bitmap result;
	private Bitmap resultCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_pic);
		super.onCreate(savedInstanceState);
		initView();
	

	}
	
	@Override
	protected void onResume() {
		getIntentData();
		LoadImageFilter();
		super.onResume();
	}

	private void getIntentData() {
		if("camera".equals(MyApplication.getInstance().getPublishUtil().getFrom())){//来自相机
			File dir = new File(FileUtils.getRootPath() + "/tagPic/");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String index = MyApplication.getInstance().getPublishUtil().getIndex();
			result = BitmapFactory.decodeFile(FileUtils.getRootPath()
					+ "/tagPic/" + "joybar_camera"+ index+".png");
		}else if("picture".equals(MyApplication.getInstance().getPublishUtil().getFrom())){//来自图库
			Uri uri = MyApplication.getInstance().getPublishUtil().getUri();
			result = getBitmap(uri);
		}else if("publish".equals(MyApplication.getInstance().getPublishUtil().getFrom())){//发布商品返回
			String index = MyApplication.getInstance().getPublishUtil().getIndex();
			result = BitmapFactory.decodeFile(FileUtils.getRootPath()
					+ "/tagPic/" + "tagPic"+ index+".png");
		}
		resultCache = result;
		iv_pic.setImageBitmap(result);
		}

	private void initView() {
		setTitle("编辑图片");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		ll_top = getView(R.id.ll_top);
		layout_tag_image = getView(R.id.layout_tag_image);
		// layout_tag_image.setOnTouchListener(this);
		iv_pic = getView(R.id.iv_pic);
		iv_pic.setOnClickListener(this);
		iv_pic.setOnTouchListener(this);
		LayoutParams params = iv_pic.getLayoutParams();
		params.height = ToolsUtil.getDisplayWidth(mContext);
		params.width = ToolsUtil.getDisplayWidth(mContext);
		iv_pic.setLayoutParams(params);
		tv_tishi = getView(R.id.tv_tishi);
		tv_next = getView(R.id.tv_next);
		tv_next.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_next:// 下一步
			saveBitmapToFile();
			tagList.clear();
			List<Map<String, Integer>> positionList = layout_tag_image
					.getPositionList();
			for (int i = 0; i < positionList.size(); i++) {
				double x = (double) positionList.get(i).get("x")
						/ (double) ToolsUtil.getDisplayWidth(mContext);
				double y = ((double) positionList.get(i).get("y") - ToolsUtil
						.dip2px(EditPicActivity.this, ll_top.getHeight()))
						/ (double) ToolsUtil.getDisplayWidth(mContext);
				Map<String, Double> map = new HashMap<String, Double>();
				if (x < 0) {
					x = 0.0;
				}
				if (x > 1) {
					x = 1.0;
				}
				if (y < 0) {
					y = 0.0;
				}
				if (y > 1) {
					y = 1.0;
				}
				map.put("x", x);
				map.put("y", y);
				tagList.add(map);
			}
			TagListBean bean = new TagListBean();
			bean.setTagList(tagList);
			Intent intent = new Intent(mContext, PublishProductActivity.class);
			intent.putExtra("tagListBean", bean);
			startActivity(intent);
			break;
		case R.id.iv_pic:// 图片的点击事件
			showDialog();
			break;
		default:
			break;
		}
	}

	private void saveBitmapToFile() {
		File dir = new File(FileUtils.getRootPath() + "/tagPic/");
		if (!dir.exists()) {
			if (!dir.exists())
				dir.mkdir();
		}
		File file = new File(dir, "tagPic" + MyApplication.getInstance().getPublishUtil().getIndex() + ".png");
		File file_yuan = new File(dir, "tagPic_yuan" + MyApplication.getInstance().getPublishUtil().getIndex() + ".png");
		if (file.exists()) {
			file.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			FileOutputStream out_yuan = new FileOutputStream(file_yuan);
			EditPicActivity.this.resultCache.compress(
					Bitmap.CompressFormat.PNG, 100, out);
			EditPicActivity.this.result.compress(Bitmap.CompressFormat.PNG,
					100, out_yuan);
			out.flush();
			out.close();
			out_yuan.flush();
			out_yuan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (Constants.REQUESTCODE == requestCode
				&& Constants.RESULTCODE == resultCode) {
			if (data != null) {
				Bundle bundle = data.getExtras();
				String id = bundle.getString("id");
				String name = bundle.getString("name");
				String type = bundle.getString("type");
				layout_tag_image.addTextTag(name, startx, starty);

			}
		}
		super.onActivityResult(requestCode, resultCode, data);
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

	@Override
	public void onBackPressed() {
		finish();
		startActivity(new Intent(EditPicActivity.this, ActivityCapture.class));
		super.onBackPressed();
	}

	/**
	 * 加载图片filter
	 */
	private void LoadImageFilter() {
		Gallery gallery = (Gallery) findViewById(R.id.galleryFilter);
		final ImageFilterAdapter filterAdapter = new ImageFilterAdapter(
				EditPicActivity.this);
		gallery.setAdapter(new ImageFilterAdapter(EditPicActivity.this));
		gallery.setSelection(2);
		gallery.setAnimationDuration(1000);
		gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				if (position == 0) {
					resultCache = result;
					iv_pic.setImageBitmap(resultCache);
				} else {
					IImageFilter filter = (IImageFilter) filterAdapter
							.getItem(position);
					new processImageTask(EditPicActivity.this, filter)
							.execute();
				}
			}
		});
	}

	public class processImageTask extends AsyncTask<Void, Void, Bitmap> {
		private IImageFilter filter;
		private Activity activity = null;

		public processImageTask(Activity activity, IImageFilter imageFilter) {
			this.filter = imageFilter;
			this.activity = activity;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		public Bitmap doInBackground(Void... params) {
			Image img = null;
			try {
				// Bitmap bitmap =
				// BitmapFactory.decodeResource(activity.getResources(),
				// R.drawable.image);
				img = new Image(result);
				if (filter != null) {
					img = filter.process(img);
					img.copyPixelsFromBuffer();
				}
				return img.getImage();
			} catch (Exception e) {
				if (img != null && img.destImage.isRecycled()) {
					img.destImage.recycle();
					img.destImage = null;
					System.gc(); // 提醒系统及时回收
				}
			} finally {
				if (img != null && img.image.isRecycled()) {
					img.image.recycle();
					img.image = null;
					System.gc(); // 提醒系统及时回收
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				super.onPostExecute(result);
				iv_pic.setImageBitmap(result);
				EditPicActivity.this.resultCache = result;
			}
		}
	}

	public class ImageFilterAdapter extends BaseAdapter {
		private class FilterInfo {
			public int filterID;
			public IImageFilter filter;

			public FilterInfo(int filterID, IImageFilter filter) {
				this.filterID = filterID;
				this.filter = filter;
			}
		}

		private Context mContext;
		private List<FilterInfo> filterArray = new ArrayList<FilterInfo>();

		public ImageFilterAdapter(Context c) {
			mContext = c;

			// 99种效果

			// v0.4
			// filterArray.add(new FilterInfo(R.drawable.video_filter1, new
			// VideoFilter(VideoFilter.VIDEO_TYPE.VIDEO_STAGGERED)));
			// filterArray.add(new FilterInfo(R.drawable.video_filter2, new
			// VideoFilter(VideoFilter.VIDEO_TYPE.VIDEO_TRIPED)));
			// filterArray.add(new FilterInfo(R.drawable.video_filter3, new
			// VideoFilter(VideoFilter.VIDEO_TYPE.VIDEO_3X3)));
			// filterArray.add(new FilterInfo(R.drawable.video_filter4, new
			// VideoFilter(VideoFilter.VIDEO_TYPE.VIDEO_DOTS)));
			// filterArray.add(new FilterInfo(R.drawable.tilereflection_filter1,
			// new TileReflectionFilter(20, 8, 45, (byte)1)));
			// filterArray.add(new FilterInfo(R.drawable.tilereflection_filter2,
			// new TileReflectionFilter(20, 8, 45, (byte)2)));
			// filterArray.add(new FilterInfo(R.drawable.fillpattern_filter, new
			// FillPatternFilter(ImageFilterMain.this, R.drawable.texture1)));
			// filterArray.add(new FilterInfo(R.drawable.fillpattern_filter1,
			// new FillPatternFilter(ImageFilterMain.this,
			// R.drawable.texture2)));
			// filterArray.add(new FilterInfo(R.drawable.mirror_filter1, new
			// MirrorFilter(true)));
			// filterArray.add(new FilterInfo(R.drawable.mirror_filter2, new
			// MirrorFilter(false)));
			// filterArray.add(new FilterInfo(R.drawable.ycb_crlinear_filter,
			// new YCBCrLinearFilter(new YCBCrLinearFilter.Range(-0.3f,
			// 0.3f))));
			// filterArray.add(new FilterInfo(R.drawable.ycb_crlinear_filter2,
			// new YCBCrLinearFilter(new YCBCrLinearFilter.Range(-0.276f,
			// 0.163f), new YCBCrLinearFilter.Range(-0.202f, 0.5f))));
			// filterArray.add(new FilterInfo(R.drawable.texturer_filter, new
			// TexturerFilter(new CloudsTexture(), 0.8f, 0.8f)));
			// filterArray.add(new FilterInfo(R.drawable.texturer_filter1, new
			// TexturerFilter(new LabyrinthTexture(), 0.8f, 0.8f)));
			// filterArray.add(new FilterInfo(R.drawable.texturer_filter2, new
			// TexturerFilter(new MarbleTexture(), 1.8f, 0.8f)));
			// filterArray.add(new FilterInfo(R.drawable.texturer_filter3, new
			// TexturerFilter(new WoodTexture(), 0.8f, 0.8f)));
			// filterArray.add(new FilterInfo(R.drawable.texturer_filter4, new
			// TexturerFilter(new TextileTexture(), 0.8f, 0.8f)));
			// filterArray.add(new FilterInfo(R.drawable.hslmodify_filter, new
			// HslModifyFilter(20f)));
			// filterArray.add(new FilterInfo(R.drawable.hslmodify_filter0, new
			// HslModifyFilter(40f)));
			// filterArray.add(new FilterInfo(R.drawable.hslmodify_filter1, new
			// HslModifyFilter(60f)));
			// filterArray.add(new FilterInfo(R.drawable.hslmodify_filter2, new
			// HslModifyFilter(80f)));
			// filterArray.add(new FilterInfo(R.drawable.hslmodify_filter3, new
			// HslModifyFilter(100f)));
			// filterArray.add(new FilterInfo(R.drawable.hslmodify_filter4, new
			// HslModifyFilter(150f)));
			// filterArray.add(new FilterInfo(R.drawable.hslmodify_filter5, new
			// HslModifyFilter(200f)));
			// filterArray.add(new FilterInfo(R.drawable.hslmodify_filter6, new
			// HslModifyFilter(250f)));
			// filterArray.add(new FilterInfo(R.drawable.hslmodify_filter7, new
			// HslModifyFilter(300f)));
			//
			// //v0.3
			// filterArray.add(new FilterInfo(R.drawable.zoomblur_filter, new
			// ZoomBlurFilter(30)));
			// filterArray.add(new FilterInfo(R.drawable.threedgrid_filter, new
			// ThreeDGridFilter(16, 100)));
			// filterArray.add(new FilterInfo(R.drawable.colortone_filter, new
			// ColorToneFilter(Color.rgb(33, 168, 254), 192)));
			// filterArray.add(new FilterInfo(R.drawable.colortone_filter2, new
			// ColorToneFilter(0x00FF00, 192)));//green
			// filterArray.add(new FilterInfo(R.drawable.colortone_filter3, new
			// ColorToneFilter(0xFF0000, 192)));//blue
			// filterArray.add(new FilterInfo(R.drawable.colortone_filter4, new
			// ColorToneFilter(0x00FFFF, 192)));//yellow
			// filterArray.add(new FilterInfo(R.drawable.softglow_filter, new
			// SoftGlowFilter(10, 0.1f, 0.1f)));
			// filterArray.add(new FilterInfo(R.drawable.tilereflection_filter,
			// new TileReflectionFilter(20, 8)));
			// filterArray.add(new FilterInfo(R.drawable.blind_filter1, new
			// BlindFilter(true, 96, 100, 0xffffff)));
			// filterArray.add(new FilterInfo(R.drawable.blind_filter2, new
			// BlindFilter(false, 96, 100, 0x000000)));
			// filterArray.add(new FilterInfo(R.drawable.raiseframe_filter, new
			// RaiseFrameFilter(20)));
			// filterArray.add(new FilterInfo(R.drawable.shift_filter, new
			// ShiftFilter(10)));
			// filterArray.add(new FilterInfo(R.drawable.wave_filter, new
			// WaveFilter(25, 10)));
			// filterArray.add(new FilterInfo(R.drawable.bulge_filter, new
			// BulgeFilter(-97)));
			// filterArray.add(new FilterInfo(R.drawable.twist_filter, new
			// TwistFilter(27, 106)));
			// filterArray.add(new FilterInfo(R.drawable.ripple_filter, new
			// RippleFilter(38, 15, true)));
			// filterArray.add(new FilterInfo(R.drawable.illusion_filter, new
			// IllusionFilter(3)));
			// filterArray.add(new FilterInfo(R.drawable.supernova_filter, new
			// SupernovaFilter(0x00FFFF,20,100)));
			// filterArray.add(new FilterInfo(R.drawable.lensflare_filter, new
			// LensFlareFilter()));
			// filterArray.add(new FilterInfo(R.drawable.posterize_filter, new
			// PosterizeFilter(2)));
			// filterArray.add(new FilterInfo(R.drawable.gamma_filter, new
			// GammaFilter(50)));
			// filterArray.add(new FilterInfo(R.drawable.sharp_filter, new
			// SharpFilter()));

			// v0.2
			// filterArray.add(new FilterInfo(R.drawable.invert_filter, new
			// ComicFilter()));
			// filterArray.add(new FilterInfo(R.drawable.invert_filter, new
			// SceneFilter(5f, Gradient.Scene())));//green
			// filterArray.add(new FilterInfo(R.drawable.invert_filter, new
			// SceneFilter(5f, Gradient.Scene1())));//purple
			// filterArray.add(new FilterInfo(R.drawable.invert_filter, new
			// SceneFilter(5f, Gradient.Scene2())));//blue
			// filterArray.add(new FilterInfo(R.drawable.invert_filter, new
			// SceneFilter(5f, Gradient.Scene3())));
			// filterArray.add(new FilterInfo(R.drawable.invert_filter, new
			// FilmFilter(80f)));
			// filterArray.add(new FilterInfo(R.drawable.invert_filter, new
			// FocusFilter()));
			// filterArray.add(new FilterInfo(R.drawable.invert_filter, new
			// CleanGlassFilter()));
			// filterArray.add(new FilterInfo(R.drawable.invert_filter, new
			// PaintBorderFilter(0x00FF00)));//green
			// filterArray.add(new FilterInfo(R.drawable.invert_filter, new
			// PaintBorderFilter(0x00FFFF)));//yellow
			// filterArray.add(new FilterInfo(R.drawable.invert_filter, new
			// PaintBorderFilter(0xFF0000)));//blue
			// filterArray.add(new FilterInfo(R.drawable.invert_filter, new
			// LomoFilter()));

			// v0.1
			// filterArray.add(new FilterInfo(R.drawable.invert_filter, new
			// InvertFilter()));
			// filterArray.add(new FilterInfo(R.drawable.blackwhite_filter, new
			// BlackWhiteFilter()));
			// filterArray.add(new FilterInfo(R.drawable.edge_filter, new
			// EdgeFilter()));
			// filterArray.add(new FilterInfo(R.drawable.pixelate_filter, new
			// PixelateFilter()));
			// filterArray.add(new FilterInfo(R.drawable.neon_filter, new
			// NeonFilter()));
			// filterArray.add(new FilterInfo(R.drawable.bigbrother_filter, new
			// BigBrotherFilter()));
			// filterArray.add(new FilterInfo(R.drawable.monitor_filter, new
			// MonitorFilter()));
			// filterArray.add(new FilterInfo(R.drawable.relief_filter, new
			// ReliefFilter()));
			// filterArray.add(new
			// FilterInfo(R.drawable.brightcontrast_filter,new
			// BrightContrastFilter()));
			// filterArray.add(new
			// FilterInfo(R.drawable.saturationmodity_filter, new
			// SaturationModifyFilter()));
			// filterArray.add(new FilterInfo(R.drawable.threshold_filter, new
			// ThresholdFilter()));
			// filterArray.add(new FilterInfo(R.drawable.noisefilter, new
			// NoiseFilter()));
			// filterArray.add(new FilterInfo(R.drawable.banner_filter1, new
			// BannerFilter(10, true)));
			// filterArray.add(new FilterInfo(R.drawable.banner_filter2, new
			// BannerFilter(10, false)));
			// filterArray.add(new FilterInfo(R.drawable.rectmatrix_filter, new
			// RectMatrixFilter()));
			// filterArray.add(new FilterInfo(R.drawable.blockprint_filter, new
			// BlockPrintFilter()));
			// filterArray.add(new FilterInfo(R.drawable.brick_filter, new
			// BrickFilter()));
			// filterArray.add(new FilterInfo(R.drawable.gaussianblur_filter,
			// new GaussianBlurFilter()));
			// filterArray.add(new FilterInfo(R.drawable.light_filter, new
			// LightFilter()));
			// filterArray.add(new FilterInfo(R.drawable.mosaic_filter,new
			// MistFilter()));
			// filterArray.add(new FilterInfo(R.drawable.mosaic_filter,new
			// MosaicFilter()));
			// filterArray.add(new FilterInfo(R.drawable.oilpaint_filter, new
			// OilPaintFilter()));
			// filterArray.add(new
			// FilterInfo(R.drawable.radialdistortion_filter,new
			// RadialDistortionFilter()));
			// filterArray.add(new FilterInfo(R.drawable.reflection1_filter,new
			// ReflectionFilter(true)));
			// filterArray.add(new FilterInfo(R.drawable.reflection2_filter,new
			// ReflectionFilter(false)));
			// filterArray.add(new
			// FilterInfo(R.drawable.saturationmodify_filter, new
			// SaturationModifyFilter()));
			// filterArray.add(new FilterInfo(R.drawable.smashcolor_filter,new
			// SmashColorFilter()));
			// filterArray.add(new FilterInfo(R.drawable.tint_filter, new
			// TintFilter()));
			// filterArray.add(new FilterInfo(R.drawable.vignette_filter, new
			// VignetteFilter()));
			// filterArray.add(new FilterInfo(R.drawable.autoadjust_filter,new
			// AutoAdjustFilter()));
			// filterArray.add(new FilterInfo(R.drawable.colorquantize_filter,
			// new ColorQuantizeFilter()));
			// filterArray.add(new FilterInfo(R.drawable.waterwave_filter, new
			// WaterWaveFilter()));
			// filterArray.add(new FilterInfo(R.drawable.vintage_filter,new
			// VintageFilter()));
			// filterArray.add(new FilterInfo(R.drawable.oldphoto_filter,new
			// OldPhotoFilter()));
			filterArray.add(new FilterInfo(R.drawable.saturationmodity_filter,
					null/* 此处会生成原图效果 */));
			filterArray.add(new FilterInfo(R.drawable.sepia_filter,
					new SepiaFilter()));
			filterArray.add(new FilterInfo(R.drawable.rainbow_filter,
					new RainBowFilter()));
			filterArray.add(new FilterInfo(R.drawable.feather_filter,
					new FeatherFilter()));
			filterArray.add(new FilterInfo(R.drawable.xradiation_filter,
					new XRadiationFilter()));
			filterArray.add(new FilterInfo(R.drawable.nightvision_filter,
					new NightVisionFilter()));
			//
		}

		public int getCount() {
			return filterArray.size();
		}

		public Object getItem(int position) {
			return position < filterArray.size() ? filterArray.get(position).filter
					: null;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Bitmap bmImg = BitmapFactory
					.decodeResource(mContext.getResources(),
							filterArray.get(position).filterID);
			int width = 200;// bmImg.getWidth();
			int height = 200;// bmImg.getHeight();
			bmImg.recycle();
			ImageView imageview = new ImageView(mContext);
			imageview.setImageResource(filterArray.get(position).filterID);
			imageview.setLayoutParams(new Gallery.LayoutParams(width, height));
			imageview.setScaleType(ImageView.ScaleType.FIT_XY);// 设置显示比例类型
			return imageview;
		}
	};

	private void showDialog() {
		final AlertDialog dialog = new AlertDialog.Builder(EditPicActivity.this)
				.create();
		dialog.show();
		Window window = dialog.getWindow();
		// 设置布局
		window.setContentView(R.layout.tag_bottom_layout);
		// 设置宽高
		window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		window.setGravity(Gravity.BOTTOM);
		// 设置弹出的动画效果
		window.setWindowAnimations(R.style.AnimBottom);
		// 设置监听
		Button bt_band = (Button) window.findViewById(R.id.bt_band);
		Button bt_product = (Button) window.findViewById(R.id.bt_product);
		bt_band.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentAddTag = new Intent(mContext, AddTagActivity.class);
				intentAddTag.putExtra("type", "1");// 1表示品牌，0表示商品
				startActivityForResult(intentAddTag, Constants.REQUESTCODE);
				dialog.cancel();
			}
		});
		bt_product.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentProduct = new Intent(EditPicActivity.this,
						AddTagActivity.class);
				intentProduct.putExtra("type", "0");// 1表示品牌，0表示商品
				startActivityForResult(intentProduct, Constants.REQUESTCODE);
				dialog.cancel();
			}
		});
		// 因为我们用的是windows的方法，所以不管ok活cancel都要加上“dialog.cancel()”这句话，
		// 不然有程序崩溃的可能，仅仅是一种可能，但我们还是要排除这一点，对吧？
		// 用AlertDialog的两个Button，即使监听里什么也不写，点击后也是会吧dialog关掉的，不信的同学可以去试下
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v.getId() == R.id.iv_pic) {
			startx = (int) event.getRawX();
			starty = (int) event.getRawY()
					- ToolsUtil
							.dip2px(EditPicActivity.this, ll_top.getHeight());
		}
		return false;
	}

	// public void getXY(){
	//
	//
	//
	//
	//
	// if(startx == 0 && starty == 0){
	// return;
	// }
	// float x = startx/ToolsUtil.getDisplayWidth(mContext);
	// float y = (starty - ToolsUtil.dip2px(EditPicActivity.this,
	// ll_top.getHeight()))/ToolsUtil.getDisplayWidth(mContext);
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("x", x+"");
	// map.put("y", y+"");
	// tagList.add(map);
	// }

	// public void get(){
	//
	// List<View> tagsList = layout_tag_image.getTagViewList();
	// for (int i = 0; i < tagsList.size(); i++) {
	// tagsList.get(i)
	// }
	//
	// }

}
