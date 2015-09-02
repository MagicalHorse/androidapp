package com.shenma.yueba.yangjia.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
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
import com.shenma.yueba.baijia.modle.RequestUploadProductDataBean;
import com.shenma.yueba.camera2.ActivityCapture;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.filter.FeatherFilter;
import com.shenma.yueba.filter.IImageFilter;
import com.shenma.yueba.filter.Image;
import com.shenma.yueba.filter.NightVisionFilter;
import com.shenma.yueba.filter.RainBowFilter;
import com.shenma.yueba.filter.SepiaFilter;
import com.shenma.yueba.filter.XRadiationFilter;
import com.shenma.yueba.util.DialogUtilInter;
import com.shenma.yueba.util.DialogUtils;
import com.shenma.yueba.util.FileUtils;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.util.pop.SelectePublicType;
import com.shenma.yueba.view.SelecteKXPOrPublishType;
import com.shenma.yueba.view.SelecteTagType;
import com.shenma.yueba.view.TagImageView;
import com.shenma.yueba.yangjia.activity.MainActivityForYangJia.ShowMenu;
import com.shenma.yueba.yangjia.modle.TagCacheBean;
import com.shenma.yueba.yangjia.modle.TagListBean;
import com.shenma.yueba.yangjia.modle.TagsBean;
import com.umeng.analytics.MobclickAgent;

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
	private List<TagsBean> tagList = new ArrayList<TagsBean>();
	int startx = 0;
	int starty = 0;
	private LinearLayout ll_top;
	private Bitmap result;
	private Bitmap resultCache;
	private ArrayList<String> tagNameList = new ArrayList<String>();// 姓名的集合
	private List<Map<String, Integer>> positionList = new ArrayList<Map<String, Integer>>();// 位置的集合
	private ArrayList<Integer> tagIdList = new ArrayList<Integer>();// id的集合
	private ArrayList<String> tagTypeList = new ArrayList<String>();// 标签类型的集合

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.edit_pic);
		super.onCreate(savedInstanceState);
		initView();

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (Constants.REQUESTCODE == requestCode
				&& Constants.RESULTCODE == resultCode) {
			if (data != null) {
				Bundle bundle = data.getExtras();
				int TagId = bundle.getInt("id");
				String name = bundle.getString("name");
				String TagType = bundle.getString("type");
				layout_tag_image.addTextTag(name, startx, starty, TagId,
						TagType);
				positionList = layout_tag_image.getPositionList();// 获取标签位置信息
				tagNameList = layout_tag_image.getTagNameList();// 获取标签文字列表
				tagIdList = layout_tag_image.getTagIdList();
				tagTypeList = layout_tag_image.getTagTypeList();
				savetagCacheInfo(Integer.valueOf(MyApplication.getInstance()
						.getPublishUtil().getIndex()));// 缓存标签名称和位置，用于再次回来该页面显示用
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onRestart() {
		Log.i("edit", "onRestart");
		// layout_tag_image.setIToZero();
		// // layout_tag_image.clearViewList();
		// layout_tag_image.clearTagView();
		// layout_tag_image.getPositionList().clear();
		// layout_tag_image.getTagNameList().clear();
		// layout_tag_image.getTagIdList().clear();
		// layout_tag_image.getTagTypeList().clear();
		// // if(MyApplication.getInstance().getPublishUtil().isOtherPic()){
		// ArrayList<List<TagCacheBean>> allTagCacheList = MyApplication
		// .getInstance().getPublishUtil().getTagCacheList();
		// List<TagCacheBean> tagCacheList = allTagCacheList.get(Integer
		// .valueOf(MyApplication.getInstance().getPublishUtil()
		// .getIndex()));
		// for (int i = 0; i < tagCacheList.size(); i++) {
		// String name = tagCacheList.get(i).getName();
		// int x = tagCacheList.get(i).getX();
		// int y = tagCacheList.get(i).getY();
		// String id = tagCacheList.get(i).getId();
		// String type = tagCacheList.get(i).getType();
		// layout_tag_image.addTextTag(name, x, y,id,type);
		// }
		// }

		super.onRestart();
	}

	@Override
	protected void onStart() {
		Log.i("edit", "onStart");
		layout_tag_image.setIToZero();
		// layout_tag_image.clearViewList();
		layout_tag_image.clearTagView();
		layout_tag_image.getPositionList().clear();
		layout_tag_image.getTagNameList().clear();
		layout_tag_image.getTagIdList().clear();
		layout_tag_image.getTagTypeList().clear();
		// if(MyApplication.getInstance().getPublishUtil().isOtherPic()){
		ArrayList<List<TagCacheBean>> allTagCacheList = MyApplication
				.getInstance().getPublishUtil().getTagCacheList();
		List<TagCacheBean> tagCacheList = allTagCacheList.get(Integer
				.valueOf(MyApplication.getInstance().getPublishUtil()
						.getIndex()));
		for (int i = 0; i < tagCacheList.size(); i++) {
			String name = tagCacheList.get(i).getName();
			int x = tagCacheList.get(i).getX();
			int y = tagCacheList.get(i).getY();
			int id = tagCacheList.get(i).getId();
			String type = tagCacheList.get(i).getType();
			layout_tag_image.addTextTag(name, x, y, id, type);
		}
		super.onStart();
	}

	@Override
	protected void onResume() {
		getIntentData();
		LoadImageFilter();
		MyApplication.getInstance().addActivity(mContext);
		super.onResume();
		MobclickAgent.onResume(this);
	}

	private void getIntentData() {
		if ("camera".equals(MyApplication.getInstance().getPublishUtil()
				.getFrom())) {// 来自相机
			File dir = new File(FileUtils.getRootPath() + "/tagPic/");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String index = MyApplication.getInstance().getPublishUtil()
					.getIndex();
			result = BitmapFactory.decodeFile(FileUtils.getRootPath()
					+ "/tagPic/" + "joybar_camera"
					+ SharedUtil.getUserId(mContext) + index + ".png");
			if (resultCache == null) {
				resultCache = result;
			}
		} else if ("picture".equals(MyApplication.getInstance()
				.getPublishUtil().getFrom())) {// 来自图库
			Uri uri = MyApplication.getInstance().getPublishUtil().getUri();
			result = getBitmap(uri);
			if (resultCache == null) {
				resultCache = result;
			}
		} else if ("publish".equals(MyApplication.getInstance()
				.getPublishUtil().getFrom())) {// 发布商品返回
			String index = MyApplication.getInstance().getPublishUtil()
					.getIndex();
			result = BitmapFactory.decodeFile(FileUtils.getRootPath()
					+ "/tagPic/" + "tagPic_yuan"
					+ SharedUtil.getUserId(mContext) + index + ".png");
			if (resultCache == null) {
				resultCache = BitmapFactory.decodeFile(FileUtils.getRootPath()
						+ "/tagPic/" + "tagPic"
						+ SharedUtil.getUserId(mContext) + index + ".png");
			} else {// 新增逻辑
				resultCache = BitmapFactory.decodeFile(FileUtils.getRootPath()
						+ "/tagPic/" + "tagPic"
						+ SharedUtil.getUserId(mContext) + index + ".png");
			}

		}
		iv_pic.setImageBitmap(resultCache);
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
		FontManager.changeFonts(mContext, tv_next, tv_top_title);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_next:// 下一步
			saveBitmapToFile();// 存储图片到本地
			tagList.clear();// 清空要提交的标签tag位置信息
			positionList = layout_tag_image.getPositionList();// 获取标签位置信息
			tagNameList = layout_tag_image.getTagNameList();// 获取标签文字列表
			savetagCacheInfo(Integer.valueOf(MyApplication.getInstance()
					.getPublishUtil().getIndex()));// 缓存标签名称和位置，用于再次回来该页面显示用
			getUploadTagInfo();// 获取要提交的标签信息
			TagListBean resultBean = new TagListBean();
			resultBean.setTagList(setUploadTagInfoToStanderStyle());// 将要提交的标签信息转化成需要的格式
			Intent intent = new Intent(mContext, PublishProductActivity.class);
			intent.putExtra("from", "editPic");
			intent.putExtra("tagListBean", resultBean);
			startActivity(intent);
			break;
		case R.id.iv_pic:// 图片的点击事件
			// showDialog();
			showBottomDialog();
			break;
		default:
			break;
		}
	}

	private List<TagsBean> setUploadTagInfoToStanderStyle() {
		int index = Integer.valueOf(MyApplication.getInstance()
				.getPublishUtil().getIndex());
		List<TagCacheBean> cacheTagList = MyApplication.getInstance()
				.getPublishUtil().getTagCacheList().get(index);
		List<TagsBean> tagLists = new ArrayList<TagsBean>();
		for (int i = 0; i < cacheTagList.size(); i++) {
			TagsBean tagsBean = new TagsBean();
			tagsBean.setPosX("" + tagList.get(i).getPosX());
			tagsBean.setPosY("" + tagList.get(i).getPosY());
			tagsBean.setName(cacheTagList.get(i).getName());
			tagsBean.setSourceId(Integer.valueOf(cacheTagList.get(i).getId()));
			tagsBean.setSourceType(cacheTagList.get(i).getType());
			tagLists.add(tagsBean);
		}
		return tagLists;

		// List<TagsBean> tagLists = new ArrayList<TagsBean>();
		// for (int i = 0; i < tagList.size(); i++) {
		// TagsBean tagsBean = new TagsBean();
		// tagsBean.setPosX("" + tagList.get(i).getPosX());
		// tagsBean.setPosY("" + tagList.get(i).getPosY());
		// tagsBean.setName(tagNameList.get(i));
		// tagsBean.setSourceId(tagList.get(i).getSourceId());
		// tagsBean.setSourceType(tagLists.get(i).getSourceType());
		// tagLists.add(tagsBean);
		// }
		// return tagLists;
	}

	private void getUploadTagInfo() {
		for (int i = 0; i < positionList.size(); i++) {
			double x = (double) positionList.get(i).get("x")
					/ (double) ToolsUtil.getDisplayWidth(mContext);
			double y = ((double) positionList.get(i).get("y") / (double) ToolsUtil
					.getDisplayWidth(mContext));
			TagsBean tagsBean = new TagsBean();
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
			tagsBean.setPosX(x + "");
			tagsBean.setPosY(y + "");
			tagList.add(tagsBean);
		}
	}

	private void savetagCacheInfo(int index) {
		List<TagCacheBean> tagList = new ArrayList<TagCacheBean>();
		tagList.addAll(MyApplication.getInstance().getPublishUtil()
				.getTagCacheList().get(index));
		MyApplication.getInstance().getPublishUtil().getTagCacheList()
				.get(index).clear();
		for (int i = 0; i < tagNameList.size(); i++) {
			TagCacheBean tagCacheBean = new TagCacheBean();
			tagCacheBean.setName(tagNameList.get(i));
			tagCacheBean.setX(positionList.get(i).get("x"));
			tagCacheBean.setY(positionList.get(i).get("y"));

			if (tagIdList.size() > i) {
				tagCacheBean.setId(tagIdList.get(i));
			} else {
				tagCacheBean.setId(tagList.get(i).getId());
			}

			if (tagTypeList.size() > i) {
				tagCacheBean.setType(tagTypeList.get(i));
			} else {
				tagCacheBean.setType(tagList.get(i).getType());
			}
			MyApplication.getInstance().getPublishUtil().getTagCacheList()
					.get(index).add(tagCacheBean);
		}

	}

	private void saveBitmapToFile() {
		String userId =  SharedUtil.getUserId(mContext);
		String index = MyApplication.getInstance().getPublishUtil().getIndex();
		File dir = new File(FileUtils.getRootPath() + "/tagPic/");
		if (!dir.exists()) {
				dir.mkdirs();
		}
		File file = new File(dir, "tagPic" + userId
				+ index
				+ ".png");
		File file_yuan = new File(dir, "tagPic_yuan"
				+ userId
				+ index
				+ ".png");
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
		// finish();
		// startActivity(new Intent(EditPicActivity.this,
		// ActivityCapture.class));
		DialogUtils utils = new DialogUtils();
		utils.alertDialog(mContext, "提示", "您确定要取消发布商品吗？",
				new DialogUtilInter() {
					@Override
					public void dialogCallBack(int... which) {
						MyApplication.getInstance().finishActivity(
								EditPicActivity.class);
						MyApplication.getInstance().finishActivity(
								ActivityCapture.class);
						MyApplication.getInstance().finishActivity(
								PublishProductActivity.class);
					}
				}, true, "确定", "取消", false, true);
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
				intentAddTag.putExtra("type", "1");// 1表示品牌标签，0表示普通标签
				startActivityForResult(intentAddTag, Constants.REQUESTCODE);
				dialog.cancel();
			}
		});
		bt_product.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentProduct = new Intent(EditPicActivity.this,
						AddTagActivity.class);
				intentProduct.putExtra("type", "0");// 1表示品牌标签，0表示普通标签
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

	/**
	 * 弹出选择框(开小票和发布商品)
	 */
	protected void showBottomDialog() {
		ToolsUtil.hideSoftInputKeyBoard(EditPicActivity.this);
		ShowMenu showMenu = new ShowMenu(EditPicActivity.this,
				findViewById(R.id.parent), R.layout.selete_tag_popwindow);
		showMenu.createView();
	}

	/**
	 * 弹出底部菜单
	 * 
	 * @author
	 */
	class ShowMenu extends SelecteTagType {
		public ShowMenu(Activity activity, View parent, int popLayout) {
			super(activity, parent, popLayout);
		}

		@Override
		public void onExitClick(View v) {
			canceView();
		}

		@Override
		public void onSeleteBrandTag(View v) {
			Intent intentAddTag = new Intent(mContext, AddTagActivity.class);
			intentAddTag.putExtra("type", "1");// 1表示品牌标签，0表示普通标签
			startActivityForResult(intentAddTag, Constants.REQUESTCODE);
			canceView();
		}

		@Override
		public void onSeleteCommonTag(View v) {
			Intent intentProduct = new Intent(EditPicActivity.this,
					AddTagActivity.class);
			intentProduct.putExtra("type", "0");// 1表示品牌标签，0表示普通标签
			startActivityForResult(intentProduct, Constants.REQUESTCODE);
			canceView();
		}

	}

	@Override
	protected void onDestroy() {
		MyApplication.getInstance().removeActivity(this);// 加入回退栈
		super.onDestroy();
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
